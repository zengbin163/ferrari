package com.home.ferrari.crmdao.sms;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.sms.SmsMeal;
import com.home.ferrari.vo.sms.SmsRecord;
import com.home.ferrari.vo.sms.SmsSaleOrder;
import com.home.ferrari.vo.sms.SmsSendRecord;
import com.home.ferrari.vo.sms.SmsSendRecordDetail;
import com.home.ferrari.vo.sms.SmsShopSendDetail;
import com.home.ferrari.vo.sms.SmsShopTotal;
import com.home.ferrari.vo.sms.SmsShopTotalRecord;
import com.home.ferrari.vo.sms.SmsTotal;

public interface CrmSmsDao {
	
	public Integer insertSmsTotal(SmsTotal smsTotal);
	
	public Integer updateSmsTotal(SmsTotal smsTotal);

	public SmsTotal getSmsTotalById();
	
	public Integer insertSmsShopTotal(SmsShopTotal smsShopTotal);

	public Integer insertSmsShopTotalRecord(SmsShopTotalRecord smsShopTotalRecord);

	public Integer insertSmsRecordBatch(
			@Param(value = "list") List<SmsRecord> list);

	public Integer updateSmsShopTotal(SmsShopTotal smsShopTotal);
	
	public SmsShopTotal getSmsShopTotalByAdminId(
			@Param(value = "adminId") Integer adminId);
	
	public Map<String, BigDecimal> sumShopTotal();
	
	public Long sumYearShopTotal(@Param(value = "year") Integer year);

	public List<Map<String, BigDecimal>> sumMonthShopTotal(@Param(value = "year") Integer year);
	
	public BigDecimal getSaleOrderIncome(@Param(value = "year") Integer year,
			@Param(value = "month") Integer month,
			@Param(value = "adminId") Integer adminId);

	public List<Map<String, BigDecimal>> getMonthSaleOrderIncome(
			@Param(value = "year") Integer year,
			@Param(value = "adminId") Integer adminId);
	
	public List<SmsMeal> getSmsMealList(@Param(value = "page") Page<?> page, @Param(value = "uploadStatus") Integer uploadStatus);
	
	public Integer countSmsMealList(@Param(value = "uploadStatus") Integer uploadStatus);
	
	public SmsMeal getSmsMealById(@Param(value = "id") Integer id);
	
	public Integer insertSmsMeal(SmsMeal smsMeal);

	public Integer updateSmsMeal(SmsMeal smsMeal);
	
	public List<SmsShopTotal> getShopTotalList(
			@Param(value = "page") Page<?> page,
			@Param(value = "keyword") String keyword);
	public Integer countShopTotalList(@Param(value = "keyword") String keyword);

	public List<SmsSaleOrder> getMealSaleOrderList(
			@Param(value = "page") Page<?> page,
			@Param(value = "buyYear") Integer buyYear,
			@Param(value = "buyMonth") Integer buyMonth,
			@Param(value = "adminId") Integer adminId,
			@Param(value = "mealId") Integer mealId,
			@Param(value = "crmShopName") String crmShopName,
			@Param(value = "mealName") String mealName);

	public Integer countMealSaleOrderList(
			@Param(value = "buyYear") Integer buyYear,
			@Param(value = "buyMonth") Integer buyMonth,
			@Param(value = "adminId") Integer adminId,
			@Param(value = "mealId") Integer mealId,
			@Param(value = "crmShopName") String crmShopName,
			@Param(value = "mealName") String mealName);

	public Integer insertSmsSaleOrder(SmsSaleOrder smsSaleOrder);
	
	public Integer updateSmsSaleOrder(SmsSaleOrder smsSaleOrder);
	
	public SmsSaleOrder getSmsSaleOrderById(@Param(value = "id") Integer id);
	
	public SmsShopSendDetail getShopSmsSendDetail(@Param(value = "adminId") Integer adminId);

	public List<SmsSendRecord> getShopSmsSendRecord(
			@Param(value = "page") Page<?> page,
			@Param(value = "adminId") Integer adminId,
			@Param(value = "senderId") Integer senderId,
			@Param(value = "smsType") Integer smsType);

	public Integer countShopSmsSendRecord(
			@Param(value = "adminId") Integer adminId,
			@Param(value = "senderId") Integer senderId,
			@Param(value = "smsType") Integer smsType);
	
	public SmsSendRecordDetail getGroupSmsSendDetail(@Param(value = "groupCode") String groupCode);
	
	public List<Map<String, Object>> getGroupSmsSendTarget(
			@Param(value = "page") Page<?> page,
			@Param(value = "groupCode") String groupCode);

	public Integer countGroupSmsSendTarget(
			@Param(value = "groupCode") String groupCode);
}
