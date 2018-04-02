package com.home.ferrari.plugin.third;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TopAuthTokenCreateRequest;
import com.taobao.api.response.TopAuthTokenCreateResponse;

public class AuthAccessTokenApi {

	public static void main(String[] args) throws ApiException {
		/**
		 * 获取code
		 * @code https://oauth.taobao.com/authorize?response_type=code&client_id=23633851&redirect_uri=http://114.55.225.207&state=1212&view=web
		 */
		TaobaoClient client = new DefaultTaobaoClient(APP.API_RESTS, APP.KEY, APP.SECRET);
		TopAuthTokenCreateRequest req = new TopAuthTokenCreateRequest();
		req.setCode("iskI5xpSEnJfZgjgtz4YNcPp3831939");
//		req.setUuid("abc");
		TopAuthTokenCreateResponse rsp = client.execute(req);
		System.out.println(rsp.getBody());
	}

}
