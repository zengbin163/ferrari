package com.home.ferrari.plugin.third;

import java.util.List;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.vo.taobao.TaobaoOrder;
import com.home.ferrari.vo.taobao.TaobaoOrderDetail;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.response.TradeFullinfoGetResponse;

public class TmallFullgetApi {
	public static final void main(String []args) throws ApiException {
		
		//农村淘宝
		//村淘订单编号：72762647871 淘宝订单编号：2749577282027064
		//村淘订单编号：72864267871 淘宝订单编号：2753338893687490
		//村淘订单编号：72777517871 淘宝订单编号：2751021096423659
		
		TmallFullgetApi api = new TmallFullgetApi();
		Long []tmall_ids = {3162230311830890L};
		for(int i=0;i<tmall_ids.length;i++){
			api.getTaobaoOrder(tmall_ids[i], DefaultEnum.YES.getCode());
		}
	}
	
	public TaobaoOrder getTaobaoOrder(Long bizOrderId, Integer isTmall) {
		TaobaoClient client = new DefaultTaobaoClient(APP.API_REST, APP.KEY, APP.SECRET);
		TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
		StringBuffer fieldBuffer = new StringBuffer();
		fieldBuffer.append("tid,seller_nick,buyer_nick,status,payment,post_fee,receiver_name,receiver_state,receiver_address,receiver_zip,receiver_mobile,receiver_phone,");
		fieldBuffer.append("created,pay_time,consign_time,end_time,type,buyer_message,buyer_alipay_no,");
		fieldBuffer.append("orders.oid,orders.status,orders.title,orders.et_shop_name,orders.num,orders.payment,orders.price,orders.total_fee,orders.discount_fee,");
		fieldBuffer.append("orders.pic_path,orders.logistics_company,orders.invoice_no,orders.attr");
		req.setFields(fieldBuffer.toString());
		req.setTid(bizOrderId);
		TradeFullinfoGetResponse rsp = null;
		try {
			if(DefaultEnum.YES.getCode().equals(isTmall)) {
				rsp = client.execute(req, APP.TMALL_ACCESS_TOKEN);
			}else{
				rsp = client.execute(req, APP.TAOBAO_ACCESS_TOKEN);
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		String rspReturn = rsp.getBody();
		System.out.println("================From TmallFullgetApi" + rspReturn);
		if(StringUtils.isEmpty(rspReturn)) {
			return null;
		}
		JSONObject rspJson = (JSONObject)JSONObject.parse(rspReturn);
		String body = rspJson.getString("trade_fullinfo_get_response");
		if(StringUtils.isEmpty(body)) {
			return null;
		}
		JSONObject bodyJson = (JSONObject)JSONObject.parse(body);
		String trade = bodyJson.getString("trade");
		TaobaoOrder taobaoOrder = JSON.parseObject(trade, TaobaoOrder.class);
		JSONObject tradeJson = (JSONObject)JSONObject.parse(trade);
		JSONObject detailJson = (JSONObject) JSONObject.parse(tradeJson.getString("orders"));
		List<TaobaoOrderDetail> detailList = JSONObject.parseArray(detailJson.getString("order"), TaobaoOrderDetail.class);
		taobaoOrder.setOrderList(detailList);
		return taobaoOrder;
	}
}
