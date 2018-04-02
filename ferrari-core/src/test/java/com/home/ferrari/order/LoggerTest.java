package com.home.ferrari.order;

import junit.framework.TestCase;

import org.junit.Test;

import com.home.ferrari.util.Base64Util;
import com.home.ferrari.util.WebUtil;

public class LoggerTest extends TestCase {

	@Test
	public void testLogger() {
		String text = Base64Util.decode(WebUtil.decode("5Y+v5Y+v5Y+v5Y+v5Y+v5Y+v5Y+v5Y+v5Y+v5YS/56eR"));
		String text2 = WebUtil.decode(Base64Util.decode("5Y+v5Y+v5Y+v5Y+v5Y+v5Y+v5Y+v5Y+v5Y+v5YS/56eR"));
		System.out.println(text);
		System.out.println(text2);
	}

}
