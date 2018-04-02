package com.home.ferrari.plugin.third;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TmcGroupAddRequest;
import com.taobao.api.response.TmcGroupAddResponse;

public class TmallGroupAddApi {
	public static final void main(String[] args) throws ApiException {
		TaobaoClient client = new DefaultTaobaoClient(APP.API_REST, APP.KEY,
				APP.SECRET);
		TmcGroupAddRequest request = new TmcGroupAddRequest();
		request.setGroupName("ferrari");
		request.setNicks("杭州星奥商家");
		TmcGroupAddResponse rsp = client.execute(request);
		System.out.println(rsp.getBody());
		
//		TaobaoClient client = new DefaultTaobaoClient(APP.API_REST, APP.KEY,APP.SECRET);
//		TmcGroupsGetRequest req = new TmcGroupsGetRequest();
//		req.setGroupNames("ferrari");
//		req.setPageNo(1L);
//		req.setPageSize(40L);
//		TmcGroupsGetResponse rsp = client.execute(req);
//		System.out.println(rsp.getBody());
	}

}
