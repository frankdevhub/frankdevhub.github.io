package com.frankdevhub.seo.data.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * <p>Title:EFObjectMapper.java</p>  
 * <p>Description: </p>  
 * <p>Copyright: Copyright (c) 2019</p>  
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>  
 * @author frankdevhub   
 * @date:2019-04-20 22:40
 */

public class EFObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = 5817414518216758863L;

	public EFObjectMapper() {
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		registerModule(simpleModule);
		configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
}
