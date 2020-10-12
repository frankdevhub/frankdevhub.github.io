package com.frankdevhub.site.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.json.JSONException;
import org.json.JSONTokener;

import java.util.Iterator;
import java.util.Map;

public class RequestBodyUtils {

	private static final boolean isJson(String json) {
		try {
			JSONObject.parseObject(json);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean isJsonArray(String json) throws JSONException {
		Object object = new JSONTokener(json).nextValue();
		if (object instanceof org.json.JSONArray)
			return true;
		else
			return false;
	}

	private static String decryptBody(String json) throws JSONException {
		JSONObject obj = JSON.parseObject(json);
		decryptBody(obj);
		return obj.toJSONString();
	}

	public static String parseBody(String json, boolean decrypt) throws JSONException {
		JSONObject obj = JSON.parseObject(json);
		parseBody(obj, decrypt);
		return obj.toJSONString();
	}

	private static void parseBody(JSONArray array, boolean decrypt) throws JSONException {
		Iterator<?> iter = array.iterator();
		while (iter.hasNext()) {
			JSONObject jsonObject = (JSONObject) iter.next();
			parseBody(jsonObject, decrypt);
		}
	}

	private static void parseBody(JSONObject parent, boolean decrypt) throws JSONException {
		Iterator<?> iter = parent.entrySet().iterator();
		while (iter.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) iter.next();
			String e = entry.getKey().toString();
			String v = entry.getValue().toString();
			if (!isJsonArray(v)) {
				if (decrypt)
					parent.put(e, AESEncrypt.decryptBase64(v));
				else
					parent.put(e, AESEncrypt.encryptBase64(v));
			} else {
				System.out.println(v);
				JSONArray array = JSONArray.parseArray(v);
				parseBody(array, decrypt);
				parent.put(e, array);
			}
		}
	}

	private static void decryptBody(JSONObject parent) throws JSONException {
		Iterator<?> iter = parent.entrySet().iterator();
		while (iter.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) iter.next();
			String e = entry.getKey().toString();
			String v = entry.getValue().toString();
			if (!isJsonArray(v))
				parent.put(e, AESEncrypt.decryptBase64(v));
			else {
				v = v.substring(1, v.lastIndexOf("]"));
				JSONObject sub = JSON.parseObject(v);
				parent.put(e, sub);
				decryptBody(sub);
			}
		}
	}
}
