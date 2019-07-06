package com.frankdevhub.seo.test;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Title:@ClassName UrlTest.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/7/6 11:07
 * @Version: 1.0
 */
public class UrlTest {
    @Test
    public void testUrlRegx() {
        String urlRegx = "(^([wW]{3}.|[wW][aA][pP].|[fF][tT][pP].|[fF][iI][lL][eE].)([-A-Za-z0-9#_]+.)([-A-Za-z0-9#_]" +
                "+)[/|&](([-A-Za-z0-9+&@#/%?=~_|!:;]+)?))$";
        Pattern pattern = Pattern.compile(urlRegx);
        String testUrl = "aawww.frankdevhub.sites/parm1=foo&&parm2=foo";
        Matcher matcher = pattern.matcher(testUrl);
        boolean flag = false;
        if (matcher.find()) {
            String find = matcher.group();
            System.out.print(String.format("[find:]%s", find));
            if (testUrl.equals(find))
                flag = true;
        }

        assert (flag);
    }
}
