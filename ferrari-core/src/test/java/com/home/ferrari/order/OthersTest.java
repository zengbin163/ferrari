package com.home.ferrari.order;

import com.home.ferrari.util.DateUtil;

public class OthersTest {

	public static void main(String[] args) {

		String begin = "2015-12-08 10:06:11";
		String end = "2016-12-08 10:06:11";

		String preBeginTime = DateUtil.getPreTime(begin, end);
		System.out.println(preBeginTime);
	}
}
