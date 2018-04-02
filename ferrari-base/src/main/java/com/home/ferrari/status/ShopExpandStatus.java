package com.home.ferrari.status;

import java.io.Serializable;

public class ShopExpandStatus implements Serializable {

	private static final long serialVersionUID = -8732233875450832055L;
	public static final Integer EXPAND_WAIT = 	200; // 待拓展
	public static final Integer EXPAND_ING = 	201; // 拓展中
	public static final Integer EXPAND_SUCC = 	202; // 拓展成功
	public static final Integer EXPAND_FAIL = 	203; // 拓展失败
	
}
