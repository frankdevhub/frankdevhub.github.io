package com.frankdevhub.site.core.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.util.Assert;

public class SiteMapParseUtils {

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

	public Map<Object, Object> pushToBaiduPlatform(List<String> urls) {
		Map<Object, Object> res = new HashMap<>();

		return res;
	}

	private boolean pushToBaiduPlatform(String url) {
		boolean succeed = false;
		return succeed;
	}

	public static void main(String[] args) throws Exception {
		File file = new File("D://sitemap.xml");
		if (!file.exists())
			throw new Exception("file not exists");
		new SiteMapParseUtils().parseXMLDocument(file);

	}
}
