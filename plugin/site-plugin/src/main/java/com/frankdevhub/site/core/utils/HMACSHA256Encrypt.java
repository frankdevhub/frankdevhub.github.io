package com.frankdevhub.site.core.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMACSHA256Encrypt {

	private static final String ENCODE = "UTF-8";
	private static final String ENCNAME = "MD5";
	private static final String PUBLIC_KEY = "qpda5-eu8uc-bta6c-qrdm4-775v7-nmymb-c1trv";
	private static final String PRIVATE_KEY = "*cVd)s-6FYvS-97zEU-HT^SJ-9qd6&-Cz*md-;fpGt";

	public static String HMACSHA256(String data) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		String key = PRIVATE_KEY;
		SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
		sha256_HMAC.init(secret_key);

		byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
		StringBuilder sb = new StringBuilder();
		for (byte item : array) {
			sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}
}
