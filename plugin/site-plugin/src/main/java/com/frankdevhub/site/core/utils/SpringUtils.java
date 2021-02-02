package com.frankdevhub.site.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("all")
public class SpringUtils implements ApplicationContextAware {

	@Autowired
	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}

	public static <T> T getBean(@SuppressWarnings("rawtypes") Class T) {
		return (T) applicationContext.getBean(T);
	}

	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}
}
