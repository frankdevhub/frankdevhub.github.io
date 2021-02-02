package com.frankdevhub.site.entities;

import java.util.Properties;

@SuppressWarnings("all")
public class ConfigProperties extends Properties {

	public synchronized ConfigProperties setProperty(String key, String value) {
		super.setProperty(key, value);
		return this;
	}

	public synchronized ConfigProperties put(Object key, Object value) {
		super.put(key, value);
		return this;
	}

}