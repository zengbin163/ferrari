package com.home.ferrari.plugin.third;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TmcUserPermitRequest;
import com.taobao.api.response.TmcUserPermitResponse;

public class TmallTMCPermitApi {

	public static void main(String[] args) throws ApiException {
		TaobaoClient client = new DefaultTaobaoClient(APP.API_REST, APP.KEY, APP.SECRET);
		TmcUserPermitRequest req = new TmcUserPermitRequest();
		req.setTopics("taobao_trade_TradeCreate,taobao_trade_TradeModifyFee,taobao_trade_TradeCloseAndModifyDetailOrder,taobao_trade_TradeClose,taobao_trade_TradeBuyerPay,taobao_trade_TradeSellerShip,taobao_trade_TradeDelayConfirmPay,taobao_trade_TradePartlyRefund,taobao_trade_TradePartlyConfirmPay,taobao_trade_TradeSuccess,taobao_trade_TradeChanged,taobao_trade_TradeAlipayCreate,taobao_trade_TradePartlySellerShip,taobao_refund_RefundCreated,taobao_refund_RefundClosed,taobao_refund_RefundSuccess");
		//		req.setTopics("");
		TmcUserPermitResponse rsp = client.execute(req, APP.TAOBAO_ACCESS_TOKEN);
		System.out.println(rsp.getBody());
	}
}
