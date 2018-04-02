package com.home.ferrari.service.sms;

import java.util.Map;

import com.home.ferrari.vo.sms.SmsMeal;
import com.home.ferrari.vo.sms.SmsTotal;

public interface CrmSmsService {
	
	/**
	 * 从第三方刷新星奥短信总库存
	 * @return
	 */
	public Map<String, Object> refreshSmsTotal();
	
	/**
	 * 查询总的星奥短信量和总的营销短信量
	 * @return
	 */
	public SmsTotal getSmsTotalById();
	
	/**
	 * 更新星奥短信总库存
	 * @param totalNum
	 * @param saleNum
	 * @param version
	 * @return
	 */
	public Integer updateSmsTotal(Long totalNum, Long saleNum, Long xaTotalNum,
			Long xaUseNum, Long version);
	
	/**
	 * 刷新星奥用到的库存量
	 * @param xaUseNum
	 * @return
	 */
	public Integer refreshXASmsTotal(Long xaUseNum);

	/**
	 * 查询星奥短信总库存量
	 * @return
	 */
	public Map<String, Object> getSmsTotalReport(Integer year, Integer pageOffset, Integer pageSize);
	
	/**
	 * 保存套餐
	 * @param smsMeal
	 * @return
	 */
	public Map<String, Object> saveSmsMeal(SmsMeal smsMeal);
	
	/**
	 * 编辑套餐
	 * @param smsMeal
	 * @return
	 */
	public Map<String, Object> editSmsMeal(SmsMeal smsMeal);
	
	/**
	 * 查询套餐详情
	 * @param id
	 * @return
	 */
	public Map<String, Object> detailSmsMeal(Integer id, Integer pageOffset, Integer pageSize);
	
	/**
	 * 套餐上架或者下架
	 * @param id
	 * @param uploadStatus
	 * @return
	 */
	public Map<String, Object> activeOrFrozenSmsMeal(Integer id, Integer uploadStatus);

	/**
	 * 查询星奥短信总量详情
	 * @param year
	 * @param keyword
	 * @return
	 */
	public Map<String, Object> getSmsTotalReportDetail(Integer year, String keyword, Integer pageOffset, Integer pageSize);
	
	/**
	 * 门店短信发送详单
	 * @param admin
	 * @return
	 */
	public Map<String, Object> getShopSmsSendDetail(Integer adminId, Integer pageOffset, Integer pageSize);

	/**
	 * 根据发送者查询短信发送详单
	 * @param senderId
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getShopSmsSendDetailBySenderId(Integer senderId, Integer smsType, Integer pageOffset, Integer pageSize);

	/**
	 * 某个分组发送短信详情
	 * @param admin
	 * @return
	 */
	public Map<String, Object> getGroupSmsSendDetail(String groupCode, Integer pageOffset, Integer pageSize);
	
	/**
	 * 查询星奥短信账单详情
	 * @param accountYear
	 * @param buyYear
	 * @param buyMonth
	 * @param keyword
	 * @return
	 */
	public Map<String, Object> getSmsAccountReportDetail(Integer accountYear, Integer buyYear, Integer buyMonth, String crmShopName, String mealName, Integer pageOffset, Integer pageSize);

	/**
	 * 开发票
	 * @param id
	 * @return
	 */
	public Map<String, Object> openInvoice(Integer id);
	
	/**
	 * 门店经理，查询门店短信总库存量
	 * @param year
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getShopSmsTotalReport(Integer adminId, Integer year, Integer pageOffset, Integer pageSize);
	
	/**
	 * 门店经理，查询门店账单详情
	 * @param adminId
	 * @param year
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getShopSmsAccountDetail(Integer adminId, Integer year, Integer pageOffset, Integer pageSize);
	
	/**
	 * 门店经理，门店管理员下单
	 * @param mealId
	 * @param adminId
	 * @param buyNum
	 * @param ipAddress
	 * @return
	 */
	public Map<String, Object> createOrder(Integer mealId, Integer adminId, Integer buyNum, String ipAddress);
	
	/**
	 * 支付成功回写
	 * @param saleOrderId
	 */
	public boolean paySuccess(Integer saleOrderId); 
	
}
