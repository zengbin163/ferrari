package com.home.ferrari.plugin.third;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.util.DateUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TradesSoldGetResponse;

public class TmallSoldgetApi {
	public static final void main(String []args) throws ApiException {
		TmallSoldgetApi api = new TmallSoldgetApi();
		List<String> ids = api.getSoldTmallOrderList("2016-08-25 00:00:00", "2016-08-25 23:59:59", 0L, 100L, DefaultEnum.YES.getCode());
		for(String id : ids) {
			System.out.println(id);
		}
	}
	
	public List<String> getSoldTmallOrderList(String startCreated, String endCreated, Long pageNo, Long pageSize, Integer isTmall) {
		Assert.notNull(startCreated, "startCreated不能为空");
		Assert.notNull(endCreated, "endCreated不能为空");
		Assert.notNull(pageNo, "pageNo不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		TaobaoClient client = new DefaultTaobaoClient(APP.API_REST, APP.KEY, APP.SECRET);
		TradesSoldGetRequest req = new TradesSoldGetRequest();
		req.setFields("tid,type,status,payment,orders,rx_audit_status");
		req.setStartCreated(DateUtil.defaultParseTime(startCreated));
		req.setEndCreated(DateUtil.defaultParseTime(endCreated));
		req.setType("eticket");
		req.setPageNo(pageNo);
		req.setPageSize(pageSize);
		req.setUseHasNext(true);
		TradesSoldGetResponse rsp = null;
		try {
			if(DefaultEnum.YES.getCode().equals(isTmall)) {
				rsp = client.execute(req, APP.TMALL_ACCESS_TOKEN);
			}else{
				rsp = client.execute(req, APP.TAOBAO_ACCESS_TOKEN);
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		rsp.getBody();
		String rspReturn = rsp.getBody();
		if(StringUtils.isEmpty(rspReturn)) {
			return null;
		}
		JSONObject rspJson = (JSONObject)JSONObject.parse(rspReturn);
		String body = rspJson.getString("trades_sold_get_response");
		if(StringUtils.isEmpty(body)) {
			return null;
		}
		JSONObject bodyJson = (JSONObject)JSONObject.parse(body);
		String trades = bodyJson.getString("trades");
		JSONObject tradesJson = (JSONObject)JSONObject.parse(trades);
		String trade = tradesJson.getString("trade");
		JSONArray array = JSONObject.parseArray(trade);
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject tradeJSON = (JSONObject) array.get(i);
			String taobaoOrderId = tradeJSON.getString("tid");
			ids.add(taobaoOrderId);
		}
		return ids;
	}

}
