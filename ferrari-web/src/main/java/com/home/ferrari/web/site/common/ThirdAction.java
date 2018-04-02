package com.home.ferrari.web.site.common;

import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.sms.CrmSmsService;
import com.home.ferrari.util.AesUtil;
import com.home.ferrari.util.MD5Util;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.web.BaseAction;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.EventData;
import com.pingplusplus.model.Webhooks;

@Controller
@RequestMapping("/third")
public class ThirdAction extends BaseAction {

	private static final long serialVersionUID = 1320095606288367315L;
	private static final String cKey = "xacl_007ppy_17rs";
	@Autowired
	private CrmSmsService crmSmsService;

	@RequestMapping("redirect")
	@ResponseBody
	@LoginRequired(needLogin = false)
	public Map<String, Object> redirect() {
		String shopId = this.getFilteredParameter(request, "shopId", 0, null);
		try {
			String userId = AesUtil.Encrypt(shopId, cKey);
			Long time = System.currentTimeMillis()/1000;
			String sign = MD5Util.md5("" + userId + "_" + time + "_" + "bd9ww44935c3f9a3376349ad1c74gg667");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userid", userId);
			map.put("sign", sign);
			map.put("t", time);
			return ResultUtil.successMap(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultUtil.successMap(null);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("pingxxWebhooks")
	@ResponseBody
	@LoginRequired(needLogin = false)
	public Map<String, Object> pingxxWebhooks() throws Exception {
		request.setCharacterEncoding("UTF8");
		// 获取头部所有信息
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			System.out.println(key + " " + value);
		}
		// 获得 http body 内容
		BufferedReader reader = request.getReader();
		StringBuffer buffer = new StringBuffer();
		String string;
		while ((string = reader.readLine()) != null) {
			buffer.append(string);
		}
		reader.close();
		// 解析异步通知数据
		Event event = Webhooks.eventParse(buffer.toString());
		if ("charge.succeeded".equals(event.getType())) {
			EventData eventData = event.getData();
			JSONObject json = JSONObject.parseObject(eventData.toString());
			JSONObject json2 = JSONObject.parseObject(json.getString("object"));
			String order_no = json2.getString("order_no");
			if(!this.crmSmsService.paySuccess(Integer.valueOf(order_no))) {
				return null;
			}
			response.setStatus(200);
		} else if ("refund.succeeded".equals(event.getType())) {
			response.setStatus(200);
		} else {
			response.setStatus(500);
		}
		return ResultUtil.successMap(null);
	}

}
