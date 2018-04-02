package com.home.ferrari.dao.account;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.account.AccountHeader;
import com.home.ferrari.vo.account.AccountInvoice;
import com.home.ferrari.vo.account.AccountList;
import com.home.ferrari.vo.account.AccountShop;
import com.home.ferrari.vo.account.AccountUpload;

public interface AccountHeaderDao {

	public Integer insertAccountHeader(AccountHeader accountHeader);

	public void insertAccountListBatch(List<AccountList> list);

	public Integer insertAccountShopBatch(List<AccountShop> list);

	public AccountHeader getAccountHeaderByFinalNo(@Param(value = "finalNo") String finalNo);

	public List<AccountHeader> getAccountHeaderList(@Param(value = "page") Page<?> page);
	public Integer countAccountHeaderList();
	
	public AccountList getAccountListBySerialNo(@Param(value = "finalNo") String finalNo, @Param(value = "serialNo") String serialNo);

	public Integer countAccountShopList(@Param(value = "finalNo") String finalNo, @Param(value = "accountStatus") Integer accountStatus);
	
	public Integer insertOrUpdateAccountInvoice(AccountInvoice accountInvoice);

	public AccountInvoice getAccountInvoiceById(@Param(value = "id") Integer id);
	
	public List<AccountShop> getAccountShopGroupList(
			@Param(value = "page") Page<?> page,
			@Param(value = "finalNo") String finalNo,
			@Param(value = "accountStatus") Integer accountStatus);
	public Integer countAccountShopGroupList(
			@Param(value = "finalNo") String finalNo,
			@Param(value = "accountStatus") Integer accountStatus);
	
	public AccountShop getAccountShopByShopId(@Param(value = "finalNo") String finalNo, @Param(value = "shopId") Integer shopId);
	public AccountShop getAccountShopById(@Param(value = "id") Integer id);

	public List<AccountList> getAccountListByShopId(@Param(value = "page") Page<?> page, @Param(value = "finalNo") String finalNo, @Param(value = "shopId") Integer shopId);
	public Integer countAccountListByShopId(@Param(value = "finalNo") String finalNo, @Param(value = "shopId") Integer shopId);

	public BigDecimal sumShopAccountSettleFeeByShopId(@Param(value = "finalNo") String finalNo, @Param(value = "shopId") Integer shopId);
	
	public Integer updateAccountHeaderByFinalNo(
			@Param(value = "finalNo") String finalNo,
			@Param(value = "accountStatus") Integer accountStatus);
	public Integer updateAccountShopByFinalNo(
			@Param(value = "finalNo") String finalNo,
			@Param(value = "accountStatus") Integer accountStatus);
	public Integer updateAccountShop(AccountShop accountShop);
	
	public List<AccountShop> getShopAccountList(@Param(value = "page") Page<?> page, @Param(value = "shopId") Integer shopId);
	public Integer countShopAccountList(@Param(value = "shopId") Integer shopId);
	
	public Integer getUNFinishAccountShopByFinalNo(@Param(value = "finalNo") String finalNo);

	public BigDecimal sumAccountFeeByFinalNo(@Param(value = "finalNo") String finalNo);
	
	public Integer insertOrUpdateAccountUpload(AccountUpload accountUpload);
	public AccountUpload getAccountUploadDetail(@Param(value = "finalNo") String finalNo);
}
