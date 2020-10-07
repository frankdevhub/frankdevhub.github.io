package com.frankdevhub.site.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;

public class TencentIpLocator {

	private static final String API_KEY = "EPOBZ-NAQ36-5VZSA-MBIIB-NMB7O-SEBRQ";
	private static final Logger LOG = LoggerFactory.getLogger(TencentIpLocator.class);

	public static String[] getIpLocation(String ip) throws IOException {
		String[] location = null;
		StringBuffer params = new StringBuffer();
		params.append("name=" + URLEncoder.encode("&", "utf-8"));
		params.append("&");
		params.append("ip=" + ip + "");
		params.append("&");
		params.append("key=" + API_KEY + "");

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet("https://apis.map.qq.com/ws/location/v1/ip" + "?" + params);
		CloseableHttpResponse response;

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
				.setSocketTimeout(5000).setRedirectsEnabled(true).build();
		httpGet.setConfig(requestConfig);
		response = httpClient.execute(httpGet);

		HttpEntity responseEntity = response.getEntity();
		System.out.println(String.format("response status:[%s]", response.getStatusLine()));
		String responseText = EntityUtils.toString(responseEntity);
		if (responseEntity != null) {
			System.out.println(String.format("content-length:[%s]", responseEntity.getContentLength()));
			System.out.println(String.format("response context:[%s]", responseText));

			JSONObject responseObj = JSON.parseObject(responseText);
			JSONObject resObj = (JSONObject) responseObj.get("result");
			JSONObject locObj = (JSONObject) resObj.get("location");
			Assert.notNull(locObj, "location json object should not be null");
			BigDecimal lat = (BigDecimal) locObj.get("lat");
			BigDecimal lng = (BigDecimal) locObj.get("lng");
			location = new String[] { lat.toString(), lng.toString() };

			LOG.info(String.format("location:[%s,%s]", lat, lng));
		}

		return location;
	}

	public static String getAddress(String location) throws IOException {
		String address = null;
		Assert.notNull(location, "location should not be null");

		StringBuffer params = new StringBuffer();
		params.append("name=" + URLEncoder.encode("&", "utf-8"));
		params.append("&");
		params.append("location=" + location + "");
		params.append("&");
		params.append("key=" + API_KEY + "");

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet("https://apis.map.qq.com/ws/geocoder/v1" + "?" + params);
		CloseableHttpResponse response;

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
				.setSocketTimeout(5000).setRedirectsEnabled(true).build();
		httpGet.setConfig(requestConfig);
		response = httpClient.execute(httpGet);

		HttpEntity responseEntity = response.getEntity();
		System.out.println(String.format("response status:[%s]", response.getStatusLine()));
		String responseText = EntityUtils.toString(responseEntity);
		if (responseEntity != null) {
			System.out.println(String.format("content-length:[%s]", responseEntity.getContentLength()));
			System.out.println(String.format("response context:[%s]", responseText));

			JSONObject responseObj = JSON.parseObject(responseText);
			JSONObject resObj = (JSONObject) responseObj.get("result");
			address = (String) resObj.get("address");

			LOG.info(String.format("address:[%s]", address));
		}

		return address;
	}

}
