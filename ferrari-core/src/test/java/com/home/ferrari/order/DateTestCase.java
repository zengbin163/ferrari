/*
 *@Project: GZJK
 *@Author: zengbin
 *@Date: 2015年11月14日
 *@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.order;

import com.home.ferrari.util.DateUtil;

/**
 * @ClassName: OrderTestCase
 * @author zengbin
 * @date 2015年11月14日 下午3:32:53
 */
public class DateTestCase {

	public static void main(String[] args) {
		String d1 = "2016-01-02 01:07:02";    //createUTC":1451668022000,"createTime":"2016-01-02 01:07:02"
		System.out.println(DateUtil.defaultParseDate(d1));
		System.out.println(DateUtil.defaultFormatDate(DateUtil.defaultParseDate(d1)));
	}
}
