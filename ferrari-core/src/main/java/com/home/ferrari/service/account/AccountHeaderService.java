package com.home.ferrari.service.account;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import com.home.ferrari.vo.account.AccountHeader;
import com.home.ferrari.vo.account.AccountList;
import com.home.ferrari.vo.account.AccountUpload;

public interface AccountHeaderService {
	
	/**
	 * 保存对账记录和流水
	 * @param finalNo
	 * @param totalFee
	 * @param paymentDate
	 * @param accountList
	 * @return
	 */
	public Map<String, Object> saveAccount(String finalNo, BigDecimal totalFee, String paymentDate, Set<AccountList> accountList);

	/**
	 * 分页查询核销单
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getAccountHeader(Integer pageOffset, Integer pageSize);
	 
	/**
	 * 新增或者更新星奥公司的发票相关信息
	 * @param invoiceTitle
	 * @param taxIdentify
	 * @param billingAddress
	 * @param billingAccount
	 * @param billingPhone
	 * @param receiverAddress
	 * @param receiverPhone
	 * @param accountRemark
	 * @return
	 */
	public Map<String, Object> saveOrUpdateAccountInvoice(String invoiceTitle,
			String taxIdentify, String billingAddress, String billingAccount,
			String billingPhone, String receiverAddress, String receiverPhone,
			String accountRemark);
	
	/**
	 * 查询发票信息
	 * @return
	 */
	public Map<String, Object> getAccountInvoice();

	/**
	 * 查询核销账单详情
	 * @param finalNo
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getAccountHeaderDetail(String finalNo, Integer accountStatus, Integer pageOffset, Integer pageSize);
    
	/**
	 * 查询门店核销账单详情
	 * @param finalNo
	 * @param shopId
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getAccountShopDetail(String finalNo, Integer shopId, Integer pageOffset, Integer pageSize);

	/**
	 * 更新门店核销单信息
	 * @param id
	 * @param accountFee
	 * @param logisticsCompany
	 * @param logisticsNo
	 * @return
	 */
	public Map<String, Object> updateAccountShop(Integer id,
			Integer accountStatus, BigDecimal accountFee, String financeUrl,
			String logisticsCompany, String logisticsNo, String logisticsRemark);
	
	/**
	 * 发起对账
	 * @param finalNo
	 * @return
	 */
	public Map<String, Object> beginAccount(String finalNo);
	
	/**
	 * 根据finalNo查询账单信息
	 * @param finalNo
	 * @return
	 */
	public AccountHeader getAccountHeaderByFinalNo(String finalNo);
	
	/**
	 * 上传情况
	 * @param accountUpload
	 */
	public void insertOrUpdateAccountUpload(AccountUpload accountUpload);
	
	/**
	 * 上传情况详情
	 * @param finalNo
	 * @return
	 */
	public Map<String, Object> getAccountUploadDetail(String finalNo);
}
