package com.home.ferrari.order;

import com.home.ferrari.util.Base64Util;
import com.home.ferrari.util.WebUtil;

public class Base64Test {

	public static void main(String[] args) {
		String json = "6aG26aG26aG26aG2";
		System.out.println(Base64Util.decode(WebUtil.decode(json)));
	}
	
}
