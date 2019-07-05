package com.frankdevhub.seo.message.logging;


import com.frankdevhub.seo.message.IMessageSender;
import com.frankdevhub.seo.message.MessageBuilder;
import com.frankdevhub.seo.message.MessageItem;
import com.frankdevhub.seo.message.MessageMethod;

import java.util.Date;

/**
 * <p>Title:ELoggerImpl.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @author frankdevhub
 * @date:2019-04-20 22:54
 */

public class ELoggerImpl extends MessageBuilder {
    private static final String COLUMN_CHAIN = "@chain";
    private static final String COLUMN_CLASS = "@class";
    private static final String COLUMN_LEVEL = "@level";
    private static final String COLUMN_LOG_TYPE = "@logType";
    private static final String COLUMN_OPERATOR = "@operator";
    private static final String COLUMN_TEXT = "@text";
    private static final String LEVEL_ERROR = "error";
    private static final String LEVEL_INFO = "info";
    private static final String LEVEL_WARN = "warn";
    private static final String SYS_LOG = "sysLog";
    private Class<?> class1;

    public ELoggerImpl(IMessageSender sender, Class<?> class1) {
        super(sender);
        this.class1 = class1;
    }


    public void error(String text) {
        try {
            System.out.println(headerLevel("error").headerText(text).getSource().toJson());
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    public ELoggerImpl field(String key, Boolean boolValue) {
        super.field(key, boolValue);
        return this;
    }

    public ELoggerImpl field(String key, Date dateValue) {
        super.field(key, dateValue);
        return this;
    }

    public ELoggerImpl field(String key, Integer intValue) {
        super.field(key, intValue);
        return this;
    }

    public ELoggerImpl field(String key, Object objValue) {
        super.field(key, objValue);
        return this;
    }

    public ELoggerImpl field(String key, String strValue) {
        super.field(key, strValue);
        return this;
    }

    public MessageItem getSource() {
        headerClass();
        headerOperator();
        headerChain();
        return super.getSource();
    }

    public ELoggerImpl headerAction(MessageMethod action) {
        super.headerMethod(action);
        return this;
    }

    private void headerChain() {
        if (!getMessage().containsKey("@chain")) {
            try {
                headers("@chain", "UPLOAD");
            } catch (Exception localException) {
            }
        }
    }

    private ELoggerImpl headerClass() {
        if (null != this.class1) {
            super.headers("@class", this.class1.getName());
        }
        return this;
    }

    private ELoggerImpl headerLevel(String level) {
        super.headers("@level", level);
        return this;
    }

    public ELoggerImpl headerMethod(MessageMethod method) {
        super.headerMethod(method);

        return this;
    }

    private void headerOperator() {
        if (!getMessage().containsKey("@operator")) {
            try {
                headerOperator("USER");
            } catch (Exception localException) {
            }
        }
    }

    public ELoggerImpl headerOperator(String operator) {
        super.headers("@operator", operator);
        return this;
    }

    public ELoggerImpl headerSysLog() {
        super.headers("@logType", "sysLog");
        return this;
    }

    private ELoggerImpl headerText(String text) {
        super.headers("@text", text);
        return this;
    }

    public ELoggerImpl headerType(String type) {
        super.headerType(type);
        return this;
    }

    public void info(String text) {
        try {
            System.out.println(headerLevel("info").headerText(text).getSource().toJson());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void warn(String text) {
        try {
            System.out.println(headerLevel("warn").headerText(text).getSource().toJson());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
