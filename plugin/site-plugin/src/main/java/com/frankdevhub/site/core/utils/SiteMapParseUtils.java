package com.frankdevhub.site.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;

public class SiteMapParseUtils {

	private String hostName = "localhost";
	private String port = "4000";
	private String domain = "www.frankdevhub.site";
	private String baiduToken = "kRx8oJmXvt1F0miA";

	private final static String REGEX_IP = "^((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}$";
	private final static String REGEX_HTTP = "([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)";

	private final Logger LOG = LoggerFactory.getLogger(SiteMapParseUtils.class);

	public SiteMapParseUtils(String hostName, String port, String domain) {
		if (null != hostName)
			this.hostName = hostName;
		if (null != port)
			this.port = port;
		this.domain = domain;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Object, Object> submitXMLDocument(File file) throws Exception {
		Map<Object, Object> res = new HashMap<>();
		Map<Object, Object> parse = parseXMLDocument(file);
		res = pushToBaiduPlatform((List) parse.get("list"));
		return res;
	}

	@SuppressWarnings("unchecked")
	public Map<Object, Object> parseXMLDocument(File file) throws DocumentException {
		Map<Object, Object> res = new HashMap<>();
		long start = System.currentTimeMillis();
		Document doc = new SAXReader().read(file);
		String xpath = "//urlset";
		Element root = (Element) doc.selectSingleNode(xpath);
		Assert.notNull(root, "cannot search root node for curent sitemap. xapth=//urlset");

		String namespace = root.getNamespaceURI();
		xpath = "//*[local-name()='url' and namespace-uri()='" + namespace + "']" + "/*[local-name()='loc']";
		List<Node> nodes = doc.selectNodes(xpath);
		int size = nodes.size();
		List<String> urls = new ArrayList<>();

		for (Node n : nodes) {
			System.out.println("page url: " + n.getText());
			urls.add(n.getText());
		}
		long end = System.currentTimeMillis();
		double cost = (end - start) / 1000;
		res.put("timecost", cost);
		res.put("size", size);
		res.put("list", urls);
		return res;
	}

	private void validatePostHost() {
		if (!this.hostName.equals("localhost")) {
			boolean find = Pattern.matches(REGEX_IP, this.hostName);
			Assert.isTrue(find, "invalid hostname:[" + this.hostName + "]");
		}
		if (null != this.port)
			Integer.parseInt(this.port);
	}

	private boolean validateLinkUrl(String url) {
		boolean valid = false;
		String regex = new StringBuffer(REGEX_HTTP).append("(").append(this.hostName).append(":").append(this.port)
				.append("/").append(")").toString();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(url);
		valid = m.find();
		return valid;
	}

	public Map<Object, Object> pushToBaiduPlatform(List<String> urls) throws Exception {
		validatePostHost();
		Map<Object, Object> res = new HashMap<>();

		int originCount = urls.size();
		res.put("origin_count", originCount);
		int submitCount = 0;
		int succeedCount = 0;
		int failureCount = 0;
		long start = System.currentTimeMillis();
		StringBuffer paramBuffer = new StringBuffer();
		for (String url : urls) {
			if (validateLinkUrl(url)) {
				submitCount++;
				url = url.replaceFirst(hostName + ":" + port, domain);
				paramBuffer.append(url).append("\n");
			}
		}
		LOG.info("submitCount = " + submitCount);

		Assert.notNull(this.baiduToken, "baidu seo token should not be null");
		String requestUrl = "http://data.zz.baidu.com/urls?site=" + this.domain + "&" + "token=" + this.baiduToken;
		LOG.info("requestUrl = " + requestUrl);

		String parameter = paramBuffer.toString();
		LOG.info("parameter = " + parameter);

		URLConnection conn = new URL(requestUrl).openConnection();
		conn.setRequestProperty("Host", "data.zz.baidu.com");
		conn.setRequestProperty("User-Agent", "curl/7.12.1");
		conn.setRequestProperty("Content-Length", "83");
		conn.setRequestProperty("Content-Type", "text/plain");
		conn.setDoInput(true);
		conn.setDoOutput(true);

		String result = null;
		PrintWriter out = new PrintWriter(conn.getOutputStream());
		out.print(parameter);
		out.flush();

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = in.readLine()) != null)
			result += line;
		if ("null".equals(result.substring(0, 4)))
			result = result.replaceFirst("null", "").trim();
		System.out.println("result = " + result);

		JSONObject obj = JSONObject.parseObject(result);
		Object success_obj = obj.get("success");
		Object remain_obj = obj.get("remain");
		if (null != success_obj && null != remain_obj) {
			succeedCount = Integer.parseInt(success_obj.toString());
			failureCount = originCount - succeedCount;
		} else {
			Object error_obj = obj.get("error");
			throw new Exception(error_obj.toString());
		}

		if (null != in)
			in.close();
		if (null != out)
			out.close();

		in = null;
		out = null;

		long end = System.currentTimeMillis();
		double timecost = (end - start) / 1000;

		res.put("submit_count", submitCount);
		res.put("succeed_count", succeedCount);
		res.put("failure_count", failureCount);
		res.put("timecost", timecost);

		return res;
	}
}
