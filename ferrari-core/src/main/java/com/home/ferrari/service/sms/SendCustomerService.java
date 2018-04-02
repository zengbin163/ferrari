package com.home.ferrari.service.sms;

public interface SendCustomerService {
	
	public void sendSmsByMobiles(Integer adminId, Integer sessionUserId, Integer sessionRoleType, String []mobiles, String smsContent);
	
	/**
	 * 根据省市查询发送的客户经理的手机号码列表
	 * @param province
	 * @param city
	 * @return
	 */
	public String getSendManagerMobiles(String province, String city, String crmShopName);
}
