package com.home.ferrari.service.tesla;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.home.ferrari.vo.tesla.user.TeslaUser;

public interface TeslaUserService {
	
	public 	Map<String, Object> login(String mobile, String password, String ipAddress, HttpServletResponse response);
	
	public Map<String, Object> password(String mobile, String oldPass, String newPass);
	
	public Map<String, Object> resetPass(String shopName);
	
	public Map<String, Object> updateTeslaUser(TeslaUser teslaUser);

	public Map<String, Object> updateTeslaUserByShopId(TeslaUser teslaUser);
	
	public Map<String, Object> logout(String sessionId);
	
	public Integer createAccount(Integer shopId, String nickName,
			String mobile, String password, String headerUrl, String signature);
}
