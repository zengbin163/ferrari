package com.home.ferrari.status;

import java.io.Serializable;

/**
 * 门店订单状态
 * 
 * @author zengbin
 *
 */
public class ShopOrderStatus implements Serializable {
	private static final long serialVersionUID = -3355953255390589795L;
	public static final Integer WAIT_PAY = -100;// 待买家支付（买家未付款）
	public static final Integer TAOBAO_CLOSED = -200;// 淘宝订单已关闭
	public static final Integer WAIT_DISTRIBUTE = 100;// 待分配门店
	public static final Integer WAIT_ACCEPT = 101;// 待门店接单
	public static final Integer WAIT_SERVICE = 102;// 待门店服务
	public static final Integer FINISH = 200;// 已完成
	public static final Integer ERROR = 400;// 异常单
}
