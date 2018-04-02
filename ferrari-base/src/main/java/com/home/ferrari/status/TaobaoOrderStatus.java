package com.home.ferrari.status;

import java.io.Serializable;

/**
 * 买手提现申请状态
 * 
 * @author zengbin
 *
 */
public class TaobaoOrderStatus implements Serializable {
	private static final long serialVersionUID = -3355953255390589795L;
	public static final String TRADE_NO_CREATE_PAY = "TRADE_NO_CREATE_PAY"; // (没有创建支付宝交易)<br>
	public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY"; // (等待买家付款)<br>
	public static final String SELLER_CONSIGNED_PART = "SELLER_CONSIGNED_PART"; // (卖家部分发货)<br>
	public static final String WAIT_SELLER_SEND_GOODS = "WAIT_SELLER_SEND_GOODS"; // (等待卖家发货,即:买家已付款)<br>
	public static final String WAIT_BUYER_CONFIRM_GOODS = "WAIT_BUYER_CONFIRM_GOODS"; // (等待买家确认收货,即:卖家已发货)<br>
	public static final String TRADE_BUYER_SIGNED = "TRADE_BUYER_SIGNED"; // (买家已签收,货到付款专用)<br>
	public static final String TRADE_FINISHED = "TRADE_FINISHED"; // (交易成功)<br>
	public static final String TRADE_CLOSED = "TRADE_CLOSED"; // (付款以后用户退款成功，交易自动关闭)<br>
	public static final String TRADE_CLOSED_BY_TAOBAO = "TRADE_CLOSED_BY_TAOBAO"; // (付款以前，卖家或买家主动关闭交易)<br>
	public static final String PAY_PENDING = "PAY_PENDING"; // (国际信用卡支付付款确认中)<br>
	public static final String WAIT_PRE_AUTH_CONFIRM = "WAIT_PRE_AUTH_CONFIRM"; // (0元购合约中)<br>
}
