package com.home.ferrari.status;

import java.io.Serializable;

/**
 * 账单状态
 * @author zengbin
 *
 */
public class AccountStatus implements Serializable {

	private static final long serialVersionUID = -8732233875450832055L;
	public static final Integer WAIT_ACCOUNT = 	600; // 等待对账
	public static final Integer ACCOUNTING = 	601; // 对账中       (门店：待门店核对详单)
	public static final Integer FINANCE_CHECK = 602; // 待财务审核
	public static final Integer FINANCE_REMIT = 603; // 待财务打款
	public static final Integer FINISH =        604; // 结束
	
}
