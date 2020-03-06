package com.frankdevhub.site.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encrypt {
    private static MessageDigest _mdInst = null;
    private static char hexDigits[] = {'8', '9', '7', '4', '5', '0', '2', '6', '3', '1', 'C', 'D', 'A', 'B', 'E', 'F'};
    private static char hexDigits2[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static MessageDigest getMdInst() {
        if (_mdInst == null) {
            try {
                _mdInst = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return _mdInst;
    }

    public static String md5Encode(String inStr) {
        try {
            byte[] btInput = inStr.getBytes("UTF-8");
            getMdInst().update(btInput);
            byte[] md = getMdInst().digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits2[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits2[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
