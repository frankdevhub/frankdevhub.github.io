package com.frankdevhub.seo.utils;

import com.frankdevhub.seo.data.logging.Logger;
import com.frankdevhub.seo.data.logging.LoggerFactory;
import com.frankdevhub.seo.message.MessageMethod;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Title:@ClassName PostUtils.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/7/6 2:42
 * @Version: 1.0
 */
public class PostUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostUtils.class);

    private String usrToken;
    private String url;
    private Properties usrProperties;

    private void readConfiguration() throws Exception {
        LOGGER.begin().headerMethod(MessageMethod.EVENT).info("read usr.properties");
        InputStream inputStream = new ClassPathResource("usr.properties").getInputStream();
        usrProperties = new Properties();
        usrProperties.load(inputStream);

        //get token
        String token = usrProperties.getProperty("token");
        if (StringUtils.isBlank(token))
            throw new Exception("[Error]:token string should not be empty!");
        System.out.println(String.format("your token is:[%s]", token));
        this.usrToken = token;
        //get url;
        String url = usrProperties.getProperty("url");
        if (StringUtils.isBlank(url))
            throw new Exception("[Error]:url string should not be empty!");
        //validate url format
        if (!validateUrlString(url))
            throw new Exception("[Error]:invalid url format,should be as \"www.example.com\"!");
        System.out.println(String.format("your url is:[%s]", url));
        this.url = url;

    }

    private boolean validateUrlString(String url) {
        boolean valid = false;
        String urlRegx = "(^([wW]{3}.|[wW][aA][pP].|[fF][tT][pP].|[fF][iI][lL][eE].)([-A-Za-z0-9#_]+.)([-A-Za-z0-9#_]" +
                "+)[/|&](([-A-Za-z0-9+&@#/%?=~_|!:;]+)?))$";
        Pattern pattern = Pattern.compile(urlRegx);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find())
            return true;
        return valid;
    }


    public void doPostLinks() throws Exception {
        try {
            readConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.begin().headerMethod(MessageMethod.ERROR).error("please check you have put usr.properties " +
                    "under root and make sure you have put a valid token");

        }
    }


}
