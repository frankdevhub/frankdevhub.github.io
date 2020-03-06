package com.frankdevhub.site.core.generators;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class EFGenerator implements KeyGenerator<String> {

    protected static String[] characters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    protected static String[] chars = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    protected static String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    protected static String generateCharacter(int length) {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < length; ++i) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(characters[(x % 52)]);
        }
        return shortBuffer.toString();
    }

    protected static String generateChars(int length) {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < length; ++i) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[(x % 62)]);
        }
        return shortBuffer.toString();
    }

    protected static String generateNumbers(int length) {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < length; ++i) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(numbers[(x % 10)]);
        }
        return shortBuffer.toString();
    }

    protected String getTimestamp14() {
        Date curDate = new Date();
        String sdf = new SimpleDateFormat("yyyyMMddHHmmss").format(curDate);
        return sdf;
    }

    protected String getTimestamp17() {
        Date curDate = new Date();
        String sdf = new SimpleDateFormat("yyyyMMddHHmmss").format(curDate);
        String dateStr4SSS = new SimpleDateFormat("SSS").format(curDate);
        return sdf + dateStr4SSS;
    }

    protected String shortChar() {
        return generateCharacter(8);
    }

    protected String shortMix() {
        return generateChars(8);
    }

    protected String shortNum() {
        return generateNumbers(8);
    }

    @Override
    public String generateKey() {
        return null;
    }
}
