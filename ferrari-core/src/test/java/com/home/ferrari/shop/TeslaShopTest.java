package com.home.ferrari.shop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.home.ferrari.util.Base64Util;
import com.home.ferrari.vo.tesla.shop.TeslaShopInfo;

public class TeslaShopTest {
	
	public static final void main(String []args) {
		String json = "{\"shopBase\":{\"province\":\"浙江省\",\"city\":\"杭州市\",\"county\":\"西湖区\",\"shopName\":\"大斌汽车维修中心\",\"shopAddress\":\"余杭塘路219号\",\"openTime\":\"五年时间\",\"manager\":\"曾斌\",\"linkPhone\":\"123456\",\"phone\":\"654321\",\"complaintPhone\":\"987456\",\"dayLinker\":\"曾斌\",\"lightBox\":\"5个灯箱\",\"backWall\":\"5个以上\",\"fieldArea\":3.15,\"workshopArea\":5.12,\"persons\":100,\"subBranchs\":50}}";
		JSONObject rspJson = (JSONObject)JSONObject.parse(json);
		System.out.println(rspJson.get("shopBase"));
		System.out.println(JSONObject.parseObject(json));
		TeslaShopInfo teslaShopInfo = JSON.parseObject(json, TeslaShopInfo.class);
		System.out.println(teslaShopInfo.getShopBase().getProvince());
		
		String encodeString = "eyJwYXJlbnROb2RlSWQiOjEsImdyb3VwSWQiOiIyNTEiLCJoZWFkZXJMaXN0IjpbeyJncm91cElkIjoiMjUxIiwiaGVhZGVyTmFtZSI6IuacieaXoOatpOacjeWKoSIsImlucHV0VHlwZSI6MSwib3JkZXJzIjowLCJoZWFkZXJJZCI6MCwiY2hvb3NlIjoxfSx7Imdyb3VwSWQiOiIyNTEiLCJoZWFkZXJOYW1lIjoi5pyN5Yqh6L2m5Z6LIiwiaW5wdXRUeXBlIjoyLCJvcmRlcnMiOjEsImhlYWRlcklkIjoxLCJjaG9vc2UiOjF9LHsiZ3JvdXBJZCI6IjI1MSIsImhlYWRlck5hbWUiOiLmnI3liqHkurrlkZgiLCJpbnB1dFR5cGUiOjEsIm9yZGVycyI6MiwiaGVhZGVySWQiOjIsImNob29zZSI6MX0seyJncm91cElkIjoiMjUxIiwiaGVhZGVyTmFtZSI6IuacjeWKoeWcuuWcsCIsImlucHV0VHlwZSI6MSwib3JkZXJzIjozLCJoZWFkZXJJZCI6MywiY2hvb3NlIjoxfSx7Imdyb3VwSWQiOiIyNTEiLCJoZWFkZXJOYW1lIjoi6ZSA5ZSu5Lu35qC8IiwiaW5wdXRUeXBlIjoyLCJvcmRlcnMiOjQsImhlYWRlcklkIjo0LCJjaG9vc2UiOjF9XSwiY2FwYWNpdHlMaXN0IjpbeyJpZCI6IjI1MSIsInBhcmVudElkIjoiMSIsImdyb3VwSWQiOiIyNTEiLCJkZWVwIjoiMCIsIm5vZGVDb3VudCI6MCwibmFtZSI6IjIifSx7ImlkIjoiMjUyIiwicGFyZW50SWQiOiIyNTEiLCJncm91cElkIjoiMjUxIiwiZGVlcCI6IjEiLCJub2RlQ291bnQiOjAsIm5hbWUiOiIxIn0seyJpZCI6IjI1MyIsInBhcmVudElkIjoiMjUxIiwiZ3JvdXBJZCI6IjI1MSIsImRlZXAiOiIxIiwibm9kZUNvdW50IjowLCJuYW1lIjoiMSJ9XX0=";
		System.out.println(JSONObject.parseObject(Base64Util.decode(encodeString.replaceAll(" ", "+"))));
	}
	
}
