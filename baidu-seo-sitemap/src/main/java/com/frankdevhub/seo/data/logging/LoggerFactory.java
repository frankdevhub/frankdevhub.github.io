package com.frankdevhub.seo.data.logging;

/**
 * <p>Title:LoggerFactory.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @author frankdevhub
 * @date:2019-04-20 22:44
 */

public class LoggerFactory {
    public static Logger getLogger(Class<?> class1) {
        return new Logger(class1);
    }

    public static Logger getLogger(Class<?> class1, Boolean log4j) {
        if (true == log4j.booleanValue()) {
            org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(class1);
            return new Logger(logger);
        }
        return getLogger(class1);
    }
}
