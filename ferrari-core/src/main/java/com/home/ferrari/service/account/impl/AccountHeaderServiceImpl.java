package com.home.ferrari.service.account.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.dao.account.AccountHeaderDao;
import com.home.ferrari.dao.shop.TeslaShopDao;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.service.account.AccountHeaderService;
import com.home.ferrari.status.AccountStatus;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.account.AccountHeader;
import com.home.ferrari.vo.account.AccountInvoice;
import com.home.ferrari.vo.account.AccountList;
import com.home.ferrari.vo.account.AccountShop;
import com.home.ferrari.vo.account.AccountUpload;
import com.home.ferrari.vo.tesla.shop.TeslaShop;

@Service
public class AccountHeaderServiceImpl implements AccountHeaderService {

	@Autowired
	private AccountHeaderDao acountHeaderDao;
	@Autowired
	private TeslaShopDao teslaShopDao;
	
	@Override
	@Transactional
	public Map<String, Object> saveAccount(String finalNo, BigDecimal totalFee, String paymentDate, Set<AccountList> accountListSet) {
		Assert.notNull(finalNo, "结算单号不能为空");
		Assert.notNull(totalFee, "总金额不能为空");
		Assert.notNull(paymentDate, "账期不能为空");
		Assert.notEmpty(accountListSet, "核销流水账不能为空");
		StringBuffer msgList = new StringBuffer("导入");
		msgList.append(accountListSet.size());
		msgList.append("条数据，");
		List<AccountList> tempAccountList = new ArrayList<>();
		Map<Integer,String> map = new HashMap<>();
		BigDecimal tempTotalFee = BigDecimal.ZERO;
		int fail = 0;
		int succ = 0;
		StringBuffer failList = new StringBuffer();
		for (AccountList subAccount : accountListSet) {
			tempTotalFee = tempTotalFee.add(subAccount.getSettleFee());
			AccountList subAccountDB = this.acountHeaderDao.getAccountListBySerialNo(finalNo, subAccount.getSerialNo());
			if(null!=subAccountDB) {
				++fail;
				failList.append("核销流水编号:");
				failList.append(subAccount.getSerialNo());
				failList.append(" 在excel已重复，导入失败，");
			}else{
				++succ;
				TeslaShop shop = this.teslaShopDao.getTeslaShopByName(subAccount.getShopName());
				if(null!=shop){
					Integer shopId = shop.getId();
					subAccount.setShopId(shopId);
					tempAccountList.add(subAccount);
					map.put(shopId, shop.getShopName());
				}
			}
		}
		msgList.append("成功了");
		msgList.append(succ);
		msgList.append("条，失败了");
		msgList.append(fail);
		msgList.append("条");
		if(CollectionUtils.isEmpty(tempAccountList)) {
			return ResultUtil.toErrorMap(ResultCode.UPLOAD_FAIL, msgList.toString());
		}
		if(tempTotalFee.compareTo(totalFee) != 0) {
			return ResultUtil.toErrorMap(ResultCode.UPLOAD_FAIL, "导入失败，输入总金额为:" + totalFee + ";通过汇总后总金额为:" + tempTotalFee);
		}
		AccountHeader accountHeader = this.acountHeaderDao.getAccountHeaderByFinalNo(finalNo);
		if(null==accountHeader) {
			accountHeader = new AccountHeader(finalNo, totalFee, paymentDate, AccountStatus.WAIT_ACCOUNT);
			//插入结算单
			this.acountHeaderDao.insertAccountHeader(accountHeader);
		}
		//插入核销的流水
		this.acountHeaderDao.insertAccountListBatch(tempAccountList);
		//插入核销的门店
		List<AccountShop> accountShopList = new ArrayList<>();
		for(Map.Entry<Integer,String> entry : map.entrySet()) {
			AccountShop accountShop = new AccountShop(finalNo, entry.getKey(), entry.getValue(), AccountStatus.WAIT_ACCOUNT);
			accountShopList.add(accountShop);
		}
		this.acountHeaderDao.insertAccountShopBatch(accountShopList);
		//插入导入信息
		AccountUpload accountUpload = new AccountUpload(finalNo, succ, fail, failList.toString());
		this.insertOrUpdateAccountUpload(accountUpload);
		return ResultUtil.successMap(msgList.toString());
	}
	
	public Map<String, Object> getAccountHeader(Integer pageOffset, Integer pageSize) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, Page.ORDER_BY_CREATE_TIME);
		List<AccountHeader> headerList = this.acountHeaderDao.getAccountHeaderList(page);
		for(AccountHeader header : headerList) {
			Integer all = this.acountHeaderDao.countAccountShopList(header.getFinalNo(), null);
			Integer already = this.acountHeaderDao.countAccountShopList(header.getFinalNo(), AccountStatus.FINISH);
			header.setAleadyFinish(already);
			header.setTotalShop(all);
			if(0!=all) {
				header.setProgress(new BigDecimal(already).divide(new BigDecimal(all),2,BigDecimal.ROUND_HALF_UP));
			} else {
				header.setProgress(BigDecimal.ZERO);
			}
			header.setTotalActualFee(this.acountHeaderDao.sumAccountFeeByFinalNo(header.getFinalNo()));
			AccountUpload upload = this.acountHeaderDao.getAccountUploadDetail(header.getFinalNo());
			if (null != upload) {
				header.setUploadAll(upload.getSuccess() + upload.getFail());
				header.setUploadSucc(upload.getSuccess());
				header.setUploadFail(upload.getFail());
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sum", this.acountHeaderDao.countAccountHeaderList());
		map.put("list", headerList);
		return ResultUtil.successMap(map);
	}

	@Override
	public Map<String, Object> saveOrUpdateAccountInvoice(String invoiceTitle,
			String taxIdentify, String billingAddress, String billingAccount,
			String billingPhone, String receiverAddress, String receiverPhone,
			String accountRemark) {
		AccountInvoice accountInvoice = new AccountInvoice(invoiceTitle,
				taxIdentify, billingAddress, billingAccount, billingPhone,
				receiverAddress, receiverPhone, accountRemark);
		this.acountHeaderDao.insertOrUpdateAccountInvoice(accountInvoice);
		return ResultUtil.successMap(ResultUtil.DATA_INSERT_SUCC);
	}
	
	public Map<String, Object> getAccountInvoice() {
		return ResultUtil.successMap(this.acountHeaderDao.getAccountInvoiceById(1));
	}
	
	public Map<String, Object> getAccountHeaderDetail(String finalNo, Integer accountStatus, Integer pageOffset, Integer pageSize) {
		Assert.notNull(finalNo, "结算单号不能为空");
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		AccountHeader header = this.acountHeaderDao.getAccountHeaderByFinalNo(finalNo);
		if(null==header) {
			return ResultUtil.successMap(ResultUtil.DATA_NOT_EXISTS);
		}else{
//			if (null != accountStatus && !accountStatus.equals(header.getAccountStatus())) {
//				return ResultUtil.successMap(ResultUtil.DATA_NOT_EXISTS);
//			}
		}
		Page<?> page = new Page<>(pageOffset, pageSize);
		Integer accountShopSum = this.acountHeaderDao.countAccountShopGroupList(finalNo, accountStatus);
		List<AccountShop> accountShopList = this.acountHeaderDao.getAccountShopGroupList(page, finalNo, accountStatus);
		BigDecimal totalActualFee = BigDecimal.ZERO;
		for(AccountShop accountShop : accountShopList) {
			AccountShop accountShopTemp = this.acountHeaderDao.getAccountShopByShopId(finalNo, accountShop.getShopId());
			if (null != accountShopTemp) {
				accountShop.setAccountStatus(accountShopTemp.getAccountStatus());
				accountShop.setAccountFee(accountShopTemp.getAccountFee());
				accountShop.setLogisticsCompany(accountShopTemp.getLogisticsCompany());
				accountShop.setLogisticsNo(accountShopTemp.getLogisticsNo());
				accountShop.setLogisticsRemark(accountShopTemp.getLogisticsRemark());
				totalActualFee = totalActualFee.add(accountShopTemp.getAccountFee());
			}
		}
		header.setAccountShopList(accountShopList);
		header.setAccountShopSum(accountShopSum);
		header.setTotalActualFee(totalActualFee);
		return ResultUtil.successMap(header);
	}
	
	public Map<String, Object> getAccountShopDetail(String finalNo, Integer shopId, Integer pageOffset, Integer pageSize) {
		Assert.notNull(finalNo, "结算单号不能为空");
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Assert.notNull(shopId, "门店id不能为空");
		AccountShop accountShop = this.acountHeaderDao.getAccountShopByShopId(finalNo, shopId);
		if(accountShop != null){
			accountShop.setShopSettleFee(this.acountHeaderDao.sumShopAccountSettleFeeByShopId(finalNo, shopId));
			accountShop.setShopAccountListSum(this.acountHeaderDao.countAccountListByShopId(finalNo, shopId));
			Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, Page.ORDER_BY_MODIFY_TIME);
			accountShop.setShopAccountList(this.acountHeaderDao.getAccountListByShopId(page, finalNo, shopId));
		}
		return ResultUtil.successMap(accountShop);
	}
	
	public Map<String, Object> updateAccountShop(Integer id,
			Integer accountStatus, BigDecimal accountFee, String financeUrl,
			String logisticsCompany, String logisticsNo, String logisticsRemark) {
		AccountShop accountShop = new AccountShop(id, accountStatus,
				accountFee, financeUrl, logisticsCompany, logisticsNo,
				logisticsRemark);
		Integer flag = this.acountHeaderDao.updateAccountShop(accountShop);
		if(flag<1) {
			throw new FerrariBizException(ResultCode.UPDATE_FAIL, "更新门店核销单信息失败，accountShopId=" + id);
		}
		AccountShop accountShopTemp = this.acountHeaderDao.getAccountShopById(id);
		if(AccountStatus.FINANCE_REMIT <= accountShopTemp.getAccountStatus()) {
			if (StringUtils.isNotBlank(logisticsCompany)
					|| StringUtils.isNotBlank(logisticsNo)
					|| StringUtils.isNotBlank(logisticsRemark)) {
				throw new FerrariBizException(ResultCode.LOGOSTICS_CAN_CHANGE, "物流信息禁止更新");
			}
		}
		if(AccountStatus.FINISH.equals(accountStatus)) {
			Integer unFinish = this.acountHeaderDao.getUNFinishAccountShopByFinalNo(accountShopTemp.getFinalNo());
			if(0==unFinish) {
				this.acountHeaderDao.updateAccountHeaderByFinalNo(accountShopTemp.getFinalNo(),AccountStatus.FINISH);
			}
		}
		return ResultUtil.successMap(ResultUtil.DATA_UPDATE_SUCC);
	}
	
	public Map<String, Object> beginAccount(String finalNo) {
		Assert.notNull(finalNo, "核销单号不能为空");
		AccountInvoice accountInvoice = this.acountHeaderDao.getAccountInvoiceById(1);
		if(null==accountInvoice) {
			throw new FerrariBizException(ResultCode.INVOICE_ISNULL, "发票信息为空");
		}
		this.acountHeaderDao.updateAccountHeaderByFinalNo(finalNo, AccountStatus.ACCOUNTING);
		this.acountHeaderDao.updateAccountShopByFinalNo(finalNo, AccountStatus.ACCOUNTING);
		return ResultUtil.successMap(ResultUtil.DATA_UPDATE_SUCC);
	}
	
	public AccountHeader getAccountHeaderByFinalNo(String finalNo) {
		Assert.notNull(finalNo, "核销单号不能为空");
		return this.acountHeaderDao.getAccountHeaderByFinalNo(finalNo);
	}
	
	public void insertOrUpdateAccountUpload(AccountUpload accountUpload) {
		this.acountHeaderDao.insertOrUpdateAccountUpload(accountUpload);
	}
	
	public Map<String, Object> getAccountUploadDetail(String finalNo) {
		Assert.notNull(finalNo, "核销单号不能为空");
		return ResultUtil.successMap(this.acountHeaderDao.getAccountUploadDetail(finalNo));
	}
}
