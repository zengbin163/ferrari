package com.home.ferrari.order;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.home.ferrari.base.ResultCode;

public class JsonTestCase {

	protected static Map<String, Object> toJsonString(String key, Object obj) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put(key, obj);
		return json;
	}

	protected static String returnFastJSON(Object obj) {
		return ("{\"recode\":\""
				+ ResultCode.SUCCESS.getCode()
				+ "\","
				+ "\"data\":"
				+ JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue)
						.toString() + ",\"msg\":\""
				+ ResultCode.SUCCESS.getString() + "\"}");
	}

	public static final void main(String[] args) {
	}

}
