package com.home.ferrari.sms;

import com.alibaba.fastjson.JSONObject;
import com.home.ferrari.plugin.sms.MandaoSmsClient;

public class SmsTest {

	public static void main(String[] args) throws Exception {
		String JSON_STRING = "{\"id\":\"ch_bXPSiHa9yz188SCCG4vvfXjT\",\"object\":\"charge\",\"created\":1496499424,\"livemode\":false,\"paid\":false,\"refunded\":false,\"app\":\"app_1Gqj58ynP0mHeX1q\",\"channel\":\"alipay_qr\",\"order_no\":\"149649942288487nine7\",\"client_ip\":\"127.0.0.1\",\"amount\":100,\"amount_settle\":100,\"currency\":\"cny\",\"subject\":\"YourSubject\",\"body\":\"YourBody\",\"time_paid\":null,\"time_expire\":1496585824,\"time_settle\":null,\"transaction_no\":null,\"refunds\":{\"object\":\"list\",\"url\":\"/v1/charges/ch_bXPSiHa9yz188SCCG4vvfXjT/refunds\",\"has_more\":false,\"data\":[]},\"amount_refunded\":0,\"failure_code\":null,\"failure_msg\":null,\"metadata\":{},\"credential\":{\"object\":\"credential\",\"alipay_qr\":\"http://sissi.pingxx.com/mock.php?ch_id=ch_bXPSiHa9yz188SCCG4vvfXjT&channel=alipay_qr\"},\"extra\":{},\"description\":null}";
		JSONObject json = JSONObject.parseObject(JSON_STRING);
		System.out.println(json.getString("credential"));
		System.out.println(JSONObject.parseObject(json.getString("credential")).getString("alipay_qr"));
	}

}
