package com.frankdevhub.seo;

import com.frankdevhub.seo.data.logging.Logger;
import com.frankdevhub.seo.data.logging.LoggerFactory;
import com.frankdevhub.seo.message.MessageMethod;
import com.frankdevhub.seo.utils.PostUtils;

/**
 * <p>Title:@ClassName BaiDuSeoApp.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/7/6 1:58
 * @Version: 1.0
 */
public class BaiDuSeoApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaiDuSeoApp.class);

    public static void main(String[] args) throws Exception {
        LOGGER.begin().headerAction(MessageMethod.EVENT).info("application start...");
        PostUtils utils = new PostUtils();
        utils.doPostLinks();
        LOGGER.begin().headerAction(MessageMethod.EVENT).info("application end...");
    }
}
