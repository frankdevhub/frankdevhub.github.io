package com.frankdevhub.seo.utils;

import com.frankdevhub.seo.data.logging.Logger;
import com.frankdevhub.seo.data.logging.LoggerFactory;
import com.frankdevhub.seo.message.MessageMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:@ClassName SiteMapUtils.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/7/6 3:08
 * @Version: 1.0
 */
public class SiteMapUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteMapUtils.class);

    public static ArrayList<String> readSiteMap() throws IOException, DocumentException {
        LOGGER.begin().headerMethod(MessageMethod.EVENT).info("read sitemap.xml");
        ArrayList<String> siteNodes = new ArrayList<>();
        InputStream stream = new ClassPathResource("sitemap.xml").getInputStream();

        SAXReader reader = new SAXReader();
        Document document = reader.read(stream);
        Element root = document.getRootElement();
        System.out.print(String.format("Root Element:\n %s", root));
        List locNodes = root.selectNodes("//loc");

        if (locNodes.isEmpty())
            throw new DocumentException("there is no site child nodes listed in sitemap.xml");
        int nodeSize = locNodes.size();

        System.out.println(String.format("get <loc> nodes :[%s]", nodeSize));
        return siteNodes;
    }

    public static void main(String[] args) throws IOException, DocumentException {
        SiteMapUtils.readSiteMap();
    }
}
