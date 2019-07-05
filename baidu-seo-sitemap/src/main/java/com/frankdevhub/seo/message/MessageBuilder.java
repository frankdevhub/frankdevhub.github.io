package com.frankdevhub.seo.message;

import com.frankdevhub.seo.utils.HostUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cglib.beans.BeanMap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>Title:MessageBuilder.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @author frankdevhub
 * @date:2019-04-20 22:49
 */

public class MessageBuilder {
    private static final String COLUMN_SERVER_IP = "@serverIp";
    private static final String COLUMN_TIMESTAMP = "@timestamp";
    private static final String HEAD_METHOD = "@bizAction";
    private static final String HEAD_RESOURCE_TYPE = "@bizType";
    private static final String PREFIX_BOOLEAN = "bool_";
    private static final String PREFIX_DATE = "date_";
    private static final String PREFIX_INT = "int_";
    private static final String PREFIX_OBJ = "obj_";
    private static final String PREFIX_STR = "str_";
    private MessageItem messageItem = new MessageItem();
    private IMessageSender messageSender;

    public MessageBuilder() {
    }

    public MessageBuilder(IMessageSender client) {
        this.messageSender = client;
    }

    private <T> HashMap<String, Object> beanToMap(T bean) {
        HashMap<String, Object> map = new HashMap();
        BeanMap beanMap;
        if (bean != null) {
            beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    public MessageBuilder field(String key, Boolean boolValue) {
        if (StringUtils.isNotEmpty(key)) {
            if (key.startsWith("bool_")) {
                fields(key, boolValue);
            } else {
                fields("bool_" + key, boolValue);
            }
        }
        return this;
    }

    public MessageBuilder field(String key, Date dateValue) {
        if (StringUtils.isNotEmpty(key)) {
            if (key.startsWith("date_")) {
                fields(key, dateValue);
            } else {
                fields("date_" + key, dateValue);
            }
        }
        return this;
    }

    public MessageBuilder field(String key, Integer intValue) {
        if (StringUtils.isNotEmpty(key)) {
            if (key.startsWith("int_")) {
                fields(key, intValue);
            } else {
                fields("int_" + key, intValue);
            }
        }
        return this;
    }

    public MessageBuilder field(String key, Object objValue) {
        if (StringUtils.isNotEmpty(key)) {
            if (key.startsWith("obj_")) {
                fields(key, objValue);
            } else {
                fields("obj_" + key, objValue);
            }
        }
        return this;
    }

    public MessageBuilder field(String key, String strValue) {
        if (StringUtils.isNotEmpty(key)) {
            if (key.startsWith("str_")) {
                fields(key, strValue);
            } else {
                fields("str_" + key, strValue);
            }
        }
        return this;
    }

    protected void fields(String key, Object value) {
        getMessage().put(key, value);
    }

    protected MessageItem getMessage() {
        if (this.messageItem == null) {
            this.messageItem = new MessageItem();
        }
        return this.messageItem;
    }

    public MessageItem getSource() {
        fields("@timestamp", new Date());
        fields("@serverIp", HostUtils.getMyIp());

        return getMessage();
    }

    public MessageBuilder headerMethod(MessageMethod method) {
        headers("@bizAction", method.name());
        return this;
    }

    protected void headers(String key, String value) {
        if (StringUtils.isNotEmpty(key)) {
            getMessage().put(key, value);
        }
    }

    public MessageBuilder headerType(String type) {
        headers("@bizType", type);
        return this;
    }

    public <T> MessageBuilder putBean(T bean) {
        return putBean(bean, null);
    }

    public <T> MessageBuilder putBean(T bean, String suffix) {
        if (bean != null) {
            HashMap<String, Object> beanToMap = beanToMap(bean);
            for (Map.Entry<String, Object> next : beanToMap.entrySet()) {
                String key = (String) next.getKey();
                if (StringUtils.isNotEmpty(suffix)) {
                    key = String.format("%s_%s", new Object[]{key, suffix});
                }
                if (next.getValue() != null) {
                    if ((next.getValue() instanceof String)) {
                        field(key, (String) next.getValue());
                    } else if ((next.getValue() instanceof Boolean)) {
                        field(key, (Boolean) next.getValue());
                    } else if ((next.getValue() instanceof Date)) {
                        field(key, (Date) next.getValue());
                    } else if ((next.getValue() instanceof Integer)) {
                        field(key, (Integer) next.getValue());
                    } else {
                        field(key, next.getValue());
                    }
                }
            }
        }
        return this;
    }

    public void send() {
        if (this.messageSender != null) {
            this.messageSender.sendMessage(getSource());
        }
    }
}

