package com.frankdevhub.site.core.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class AESEncrypt {

    private static final String PASSWORD = "sjlexpress@admin";
    private static final String ENCRY_ALGORITHM = "AES";
    private static final String CIPHER_MODE = "AES/CBC/PKCS5Padding";
    private static final String IV_ = "HYTbSvg=AS86Gfgh";
    private static final String CHARACTER = "UTF-8";
    private static final int PWD_SIZE = 16;

    private static byte[] pwdHandler(String password) throws UnsupportedEncodingException {
        byte[] data = null;
        if (password != null) {
            byte[] bytes = password.getBytes(CHARACTER);
            if (password.length() < PWD_SIZE) {
                System.arraycopy(bytes, 0, data = new byte[PWD_SIZE], 0, bytes.length);
            } else {
                data = bytes;
            }
        }
        return data;
    }

    public static byte[] encrypt(byte[] clearTextBytes, byte[] pwdBytes) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(pwdBytes, ENCRY_ALGORITHM);
            IvParameterSpec ivspec = new IvParameterSpec(IV_.getBytes());
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivspec);

            byte[] cipherTextBytes = cipher.doFinal(clearTextBytes);
            return cipherTextBytes;

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] cipherTextBytes, byte[] pwdBytes) {

        try {
            SecretKeySpec keySpec = new SecretKeySpec(pwdBytes, ENCRY_ALGORITHM);
            IvParameterSpec ivspec = new IvParameterSpec(IV_.getBytes());
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivspec);

            byte[] clearTextBytes = cipher.doFinal(cipherTextBytes);
            return clearTextBytes;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String encryptBase64(String clearText) {
        try {
            byte[] cipherTextBytes = encrypt(clearText.getBytes(CHARACTER), pwdHandler(PASSWORD));
            BASE64Encoder base64Encoder = new BASE64Encoder();
            String cipherText = base64Encoder.encode(cipherTextBytes);

            return cipherText;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String decryptBase64(String cipherText) {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] cipherTextBytes = base64Decoder.decodeBuffer(cipherText);
            byte[] clearTextBytes = decrypt(cipherTextBytes, pwdHandler(PASSWORD));

            return new String(clearTextBytes, CHARACTER);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encryptHex(String clearText) {
        try {
            byte[] cipherTextBytes = encrypt(clearText.getBytes(CHARACTER), pwdHandler(PASSWORD));
            String cipherText = byte2hex(cipherTextBytes);

            return cipherText;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptHex(String cipherText) {
        try {
            byte[] cipherTextBytes = hex2byte(cipherText);
            byte[] clearTextBytes = decrypt(cipherTextBytes, pwdHandler(PASSWORD));

            return new String(clearTextBytes, CHARACTER);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byte2hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        String tmp;
        for (int n = 0; n < bytes.length; n++) {
            tmp = (Integer.toHexString(bytes[n] & 0XFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString();
    }

    private static byte[] hex2byte(String str) {
        if (str == null || str.length() < 2) {
            return new byte[0];
        }
        str = str.toLowerCase();
        int l = str.length() / 2;
        byte[] result = new byte[l];
        for (int i = 0; i < l; ++i) {
            String tmp = str.substring(2 * i, 2 * i + 2);
            result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
        }
        return result;
    }

}