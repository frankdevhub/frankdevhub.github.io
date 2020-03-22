package com.frankdevhub.site.core.utils;

import java.io.File;
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
import org.springframework.util.Assert;

public class SiteMapParseUtils {

	private String hostName = "localhost";
	private String port = "4000";
	private String domain = "www.frankdevhub.site";

	private final static String REGEX_IP = "^((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}$";
	private final static String REGEX_HTTP = "([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)";

	public SiteMapParseUtils(String hostName, String port, String domain) {
		if (null != hostName)
			this.hostName = hostName;
		if (null != port)
			this.port = port;
		this.domain = domain;
	}

	public Map<Object, Object> submitXMLDocument(File file) throws DocumentException {
		Map<Object, Object> res = new HashMap<>();
		Map<Object, Object> parse = parseXMLDocument(file);
		res = pushToBaiduPlatform((List) parse.get("list"));
		return res;
	}

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

	public Map<Object, Object> pushToBaiduPlatform(List<String> urls) {
		validatePostHost();
		Map<Object, Object> res = new HashMap<>();

		res.put("origin_count", urls.size());
		int submitCount = 0;
		int succeedCount = 0;
		int failureCount = 0;
		long start = System.currentTimeMillis();

		for (String url : urls) {
			if (validateLinkUrl(url))
				submitCount++;
			try {
				pushToBaiduPlatform(url);
				succeedCount++;
			} catch (Exception e) {
				e.printStackTrace();
				failureCount++;
			}
		}

		long end = System.currentTimeMillis();
		double timecost = (end - start) / 1000;

		res.put("submit_count", submitCount);
		res.put("succeed_count", succeedCount);
		res.put("failure_count", failureCount);
		res.put("timecost", timecost);

		return res;
	}

	private boolean pushToBaiduPlatform(String url) {
		boolean succeed = false;

		return succeed;
	}

}
