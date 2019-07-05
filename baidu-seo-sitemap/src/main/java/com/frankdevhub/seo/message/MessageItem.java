package com.frankdevhub.seo.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

/**
 * <p>Title:MessageItem.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @author frankdevhub
 * @date:2019-04-20 22:47
 */

public class MessageItem extends HashMap<String, Object> {

    private static final long serialVersionUID = -1360411786257624744L;

    public MessageItem() {
    }

    public MessageItem(String value) {
        try {
            ObjectMapper map = new ObjectMapper();
            map.configure(MapperFeature.USE_GETTERS_AS_SETTERS, true);
            MessageItem item = (MessageItem) map.readValue(value, MessageItem.class);
            putAll(item);
        } catch (Exception localException) {
        }
    }

    public String toJson() {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.configure(MapperFeature.USE_GETTERS_AS_SETTERS, true);
        try {
            return objMapper.writeValueAsString(this);
        } catch (JsonProcessingException localJsonProcessingException) {
        }
        return null;
    }
}
