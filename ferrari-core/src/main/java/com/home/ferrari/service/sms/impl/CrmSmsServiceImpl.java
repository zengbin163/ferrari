package com.home.ferrari.service.sms.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.home.ferrari.base.Result;
import com.home.ferrari.base.ResultCode;
import com.home.ferrari.crmdao.crm.CrmUserDao;
import com.home.ferrari.crmdao.sms.CrmSmsDao;
import com.home.ferrari.dao.ferrari.FerrariUserDao;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.pay.component.PingXXPayComponent;
import com.home.ferrari.pay.vo.PayVo;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.plugin.sms.MandaoSmsClient;
import com.home.ferrari.service.sms.CrmSmsService;
import com.home.ferrari.status.PayStatus;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.crm.CrmUser;
import com.home.ferrari.vo.ferrari.user.FerrariUser;
import com.home.ferrari.vo.sms.SmsMeal;
import com.home.ferrari.vo.sms.SmsSaleOrder;
import com.home.ferrari.vo.sms.SmsSendRecord;
import com.home.ferrari.vo.sms.SmsSendRecordDetail;
import com.home.ferrari.vo.sms.SmsShopSendDetail;
import com.home.ferrari.vo.sms.SmsShopTotal;
import com.home.ferrari.vo.sms.SmsShopTotalRecord;
import com.home.ferrari.vo.sms.SmsTotal;
import com.pingplusplus.model.Charge;

@Service
public class CrmSmsServiceImpl implements CrmSmsService {

	@Autowired
	private CrmUserDao crmUserDao;
	@Autowired
	private FerrariUserDao ferrariUserDao;
	@Autowired
	private CrmSmsDao crmSmsDao;
	@Autowired
	private PingXXPayComponent pingXXPayComponent;
	
	@Override
	public Map<String, Object> refreshSmsTotal() {
		MandaoSmsClient client = null;
		try {
			client = new MandaoSmsClient(MandaoSmsClient.YX_SN, MandaoSmsClient.YX_PW);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		//从第三方短信平台拉取当前账户剩余短信数
		String totalSmsForStr = client.getBalance();
		try {
			client = new MandaoSmsClient(MandaoSmsClient.TZ_SN, MandaoSmsClient.TZ_PW);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//从第三方短信平台拉取当前账户剩余短信数
		String xaTotalSmsForStr = client.getBalance();
		Long totalSmsFromThird = StringUtils.isBlank(totalSmsForStr) ? 0L : Long.parseLong(totalSmsForStr);
		Long xaTotalSmsFromThird = StringUtils.isBlank(xaTotalSmsForStr) ? 0L : Long.parseLong(xaTotalSmsForStr);
		SmsTotal smsTotal = this.crmSmsDao.getSmsTotalById();
		if (null == smsTotal) {
			smsTotal = new SmsTotal();
			smsTotal.setTotalNum(totalSmsFromThird);
			smsTotal.setSaleNum(0L);
			smsTotal.setXaTotalNum(xaTotalSmsFromThird);
			smsTotal.setXaUseNum(0L);
			this.crmSmsDao.insertSmsTotal(smsTotal);
		} else {
			Long totalNum = totalSmsFromThird;
			Long xaTotalNum = xaTotalSmsFromThird;
			Long version = smsTotal.getVersion();
			this.updateSmsTotal(totalNum, null, xaTotalNum, null, version);
		}
		return ResultUtil.successMap(ResultUtil.DATA_UPDATE_SUCC);
	}
	
	@Override
	public SmsTotal getSmsTotalById() {
		return this.crmSmsDao.getSmsTotalById();
	}
	
	@Override
	public Integer updateSmsTotal(Long totalNum, Long saleNum, Long xaTotalNum,
			Long xaUseNum, Long version) {
		SmsTotal smsTotal = new SmsTotal();
		smsTotal.setId(1);
		smsTotal.setTotalNum(totalNum);
		smsTotal.setSaleNum(saleNum);
		smsTotal.setXaTotalNum(xaTotalNum);
		smsTotal.setXaUseNum(xaUseNum);
		smsTotal.setVersion(version + 1);
		return this.crmSmsDao.updateSmsTotal(smsTotal);
	}
	
	@Override
	public Integer refreshXASmsTotal(Long xaUseNum) {
		SmsTotal smsTotalDb = this.crmSmsDao.getSmsTotalById();
		return this.updateSmsTotal(null, null, null, xaUseNum, smsTotalDb.getVersion());
	}
	
	@Override
	public Map<String, Object> getSmsTotalReport(Integer year, Integer pageOffset, Integer pageSize) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		SmsTotal smsTotal = this.crmSmsDao.getSmsTotalById();
		Long totalNum = 900000L; //营销总量
		Long xaTotalNum = 100000L;//星奥总量
		Long leftNum = smsTotal.getTotalNum(); //营销剩余量
		Long xaLeftNum = smsTotal.getXaTotalNum();//星奥剩余量
		Long xaUseNum = xaTotalNum-xaLeftNum; //星奥已用量
		Map<String, BigDecimal> map = this.crmSmsDao.sumShopTotal();
		BigDecimal consumeNum = map.get("useNumTotal");
		BigDecimal notConsumeNum = map.get("totalNum").subtract(map.get("useNumTotal"));
		smsTotal.setConsumedNum(consumeNum);
		smsTotal.setNotConsumeNum(notConsumeNum);
		smsTotal.setSaleNum(smsTotal.getSaleNum());
		smsTotal.setNotSaleNum(leftNum);
		smsTotal.setTotalNum(totalNum);
		
		smsTotal.setXaUseNum(xaUseNum);
		smsTotal.setXaLeftNum(xaLeftNum);
		smsTotal.setXaTotalNum(xaTotalNum);

		smsTotal.setTotalIncome(this.crmSmsDao.getSaleOrderIncome(null, null, null));
		smsTotal.setYearIncome(this.crmSmsDao.getSaleOrderIncome(year, null, null));
		//smsTotal.setMonthIncome(this.crmSmsDao.getSaleOrderIncome(DateUtil.getCurrentYear(), DateUtil.getCurrentMonth(), null));
		smsTotal.setMonthIncomeReport(this.crmSmsDao.getMonthSaleOrderIncome(year, null));
		Page<?> page = new Page<>(pageOffset, pageSize);
		smsTotal.setMealList(this.crmSmsDao.getSmsMealList(page, null));
		smsTotal.setMealSum(this.crmSmsDao.countSmsMealList(null));
		return ResultUtil.successMap(smsTotal);
	}

	@Override
	public Map<String, Object> saveSmsMeal(SmsMeal smsMeal) {
		return ResultUtil.successMap(this.crmSmsDao.insertSmsMeal(smsMeal));
	}

	@Override
	public Map<String, Object> editSmsMeal(SmsMeal smsMeal) {
		SmsMeal smsMealDb = this.crmSmsDao.getSmsMealById(smsMeal.getId());
		smsMeal.setVersion(smsMealDb.getVersion() + 1);
		return ResultUtil.successMap(this.crmSmsDao.updateSmsMeal(smsMeal));
	}

	@Override
	public Map<String, Object> detailSmsMeal(Integer id, Integer pageOffset, Integer pageSize) {
		Assert.notNull(id, "套餐id不能为空");
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize);
		SmsMeal smsMeal = this.crmSmsDao.getSmsMealById(id);
		if(null != smsMeal) {
			smsMeal.setMealSaleOrderList(this.crmSmsDao.getMealSaleOrderList(page, null, null, null, id, null, null));
			smsMeal.setMealSaleOrderSum(this.crmSmsDao.countMealSaleOrderList(null, null, null, id, null, null));
		}
		return ResultUtil.successMap(smsMeal);
	}

	@Override
	public Map<String, Object> activeOrFrozenSmsMeal(Integer id, Integer uploadStatus) {
		Assert.notNull(id, "套餐id不能为空");
		Assert.notNull(uploadStatus, "uploadStatus不能为空，0下架，1上架");
		SmsMeal smsMeal = new SmsMeal();
		smsMeal.setId(id);
		smsMeal.setUploadStatus(uploadStatus);
		return this.editSmsMeal(smsMeal);
	}
	
	@Override
	public Map<String, Object> getSmsTotalReportDetail(Integer year, String keyword, Integer pageOffset, Integer pageSize) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, BigDecimal> map = this.crmSmsDao.sumShopTotal();
		resultMap.put("consumedNum", map.get("useNumTotal"));
		resultMap.put("yearConsumedNum", this.crmSmsDao.sumYearShopTotal(year));
		resultMap.put("monthUseNumReport", this.crmSmsDao.sumMonthShopTotal(year));
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t2.create_time");
		resultMap.put("smsTotalList", this.crmSmsDao.getShopTotalList(page, keyword));
		resultMap.put("smsTotalSum", this.crmSmsDao.countShopTotalList(keyword));
		return ResultUtil.successMap(resultMap);
	}
	
	@Override
	public Map<String, Object> getShopSmsSendDetail(Integer adminId, Integer pageOffset, Integer pageSize) {
		Assert.notNull(adminId, "门店管理员id不能为空");
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		SmsShopSendDetail smsShopSendDetail = this.crmSmsDao.getShopSmsSendDetail(adminId);
		if(null == smsShopSendDetail) {
			return ResultUtil.successMap(smsShopSendDetail);
		}
		BigDecimal accCost = this.crmSmsDao.getSaleOrderIncome(null, null, adminId);
		smsShopSendDetail.setAccumulateCost(accCost);
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t2.create_time");
		smsShopSendDetail.setSmsSendRecordList(this.getShopSmsSendRecord(page, adminId, null, null));
		smsShopSendDetail.setSmsSendRecordSum(this.crmSmsDao.countShopSmsSendRecord(adminId, null, null));
		return ResultUtil.successMap(smsShopSendDetail);
	}
	
	public Map<String, Object> getShopSmsSendDetailBySenderId(Integer senderId, Integer smsType, Integer pageOffset, Integer pageSize) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Map<String, Object> map = new HashMap<>();
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t2.create_time");
		map.put("list", this.getShopSmsSendRecord(page, null, senderId, smsType));
		map.put("sum", this.crmSmsDao.countShopSmsSendRecord(null, senderId, smsType));
		return ResultUtil.successMap(map);
	}
	
	private List<SmsSendRecord> getShopSmsSendRecord(Page<?> page, Integer adminId, Integer senderId, Integer smsType) {
		List<SmsSendRecord> sendRecordList = this.crmSmsDao.getShopSmsSendRecord(page, adminId, senderId, smsType);
		if(CollectionUtils.isEmpty(sendRecordList)) {
			return null;
		}
		for(SmsSendRecord sendRecord : sendRecordList) {
			if(1 == sendRecord.getSmsType()) {
				FerrariUser ferrariUser = this.ferrariUserDao.getFerrariUserById(sendRecord.getCrmUserId());
				if (null != ferrariUser) {
					sendRecord.setSenderLoginNo(ferrariUser.getMobile());
					sendRecord.setSenderUserName(ferrariUser.getNickName());
				}
			} else {
				CrmUser crmUser = this.crmUserDao.getCrmUserById(sendRecord.getCrmUserId());
				if (null != crmUser) {
					sendRecord.setSenderLoginNo(crmUser.getMobile());
					sendRecord.setSenderUserName(crmUser.getUserName());
				}
			}
		}
		return sendRecordList;
	}

	@Override
	public Map<String, Object> getGroupSmsSendDetail(String groupCode, Integer pageOffset, Integer pageSize) {
		Assert.notNull(groupCode, "短信分组code不能为空");
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		SmsSendRecordDetail smsSendRecordDetail = this.crmSmsDao.getGroupSmsSendDetail(groupCode);
		if(null != smsSendRecordDetail) {
			if(1 == smsSendRecordDetail.getSmsType()) {
				FerrariUser ferrariUser = this.ferrariUserDao.getFerrariUserById(smsSendRecordDetail.getCrmUserId());
				if (null != ferrariUser) {
					smsSendRecordDetail.setSenderLoginNo(ferrariUser.getMobile());
					smsSendRecordDetail.setSenderUserName(ferrariUser.getNickName());
				}
			} else {
				CrmUser crmUser = this.crmUserDao.getCrmUserById(smsSendRecordDetail.getCrmUserId());
				if (null != crmUser) {
					smsSendRecordDetail.setSenderLoginNo(crmUser.getMobile());
					smsSendRecordDetail.setSenderUserName(crmUser.getUserName());
				}
			}
			Page<?> page = new Page<>(pageOffset, pageSize);
			smsSendRecordDetail.setTargetList(this.crmSmsDao.getGroupSmsSendTarget(page, groupCode));
			smsSendRecordDetail.setTargetSum(this.crmSmsDao.countGroupSmsSendTarget(groupCode));
		}
		return ResultUtil.successMap(smsSendRecordDetail);
	}
	
	@Override
	public Map<String, Object> getSmsAccountReportDetail(Integer accountYear, Integer buyYear, Integer buyMonth, String crmShopName, String mealName, Integer pageOffset, Integer pageSize) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("totalIncome", this.crmSmsDao.getSaleOrderIncome(null, null, null));
		resultMap.put("yearIncome", this.crmSmsDao.getSaleOrderIncome(accountYear, null, null));
		//resultMap.put("monthIncome", this.crmSmsDao.getSaleOrderIncome(DateUtil.getCurrentYear(), DateUtil.getCurrentMonth(), null));
		resultMap.put("monthIncomeReport", this.crmSmsDao.getMonthSaleOrderIncome(accountYear, null));
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t2.create_time");
		resultMap.put("mealSaleOrderList", this.crmSmsDao.getMealSaleOrderList(page, buyYear, buyMonth, null, null, crmShopName, mealName));
		resultMap.put("mealSaleOrderSum", this.crmSmsDao.countMealSaleOrderList(buyYear, buyMonth, null, null, crmShopName, mealName));
		return ResultUtil.successMap(resultMap);
	}
	
	@Override
	public Map<String, Object> openInvoice(Integer id) {
		Assert.notNull(id, "订单id不能为空");
		SmsSaleOrder saleOrder = new SmsSaleOrder();
		saleOrder.setId(id);
		saleOrder.setIsInvoice(DefaultEnum.YES.getCode());
		return ResultUtil.successMap(this.crmSmsDao.updateSmsSaleOrder(saleOrder));
	}
	
	@Override
	public Map<String, Object> getShopSmsTotalReport(Integer adminId, Integer year, Integer pageOffset, Integer pageSize) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Assert.notNull(adminId, "adminId不能为空");
		SmsShopTotal smsShopTotal = this.crmSmsDao.getSmsShopTotalByAdminId(adminId);
		if (null == smsShopTotal) {
			smsShopTotal = new SmsShopTotal();
		} else {
			smsShopTotal.setTotalIncome(this.crmSmsDao.getSaleOrderIncome(null, null, adminId));
			smsShopTotal.setYearIncome(this.crmSmsDao.getSaleOrderIncome(year, null, adminId));
			//smsShopTotal.setMonthIncome(this.crmSmsDao.getSaleOrderIncome(DateUtil.getCurrentYear(), DateUtil.getCurrentMonth(), adminId));
			smsShopTotal.setMonthIncomeReport(this.crmSmsDao.getMonthSaleOrderIncome(year, adminId));
		}
		Page<?> page = new Page<>(pageOffset, pageSize);
		smsShopTotal.setMealList(this.crmSmsDao.getSmsMealList(page, DefaultEnum.YES.getCode()));
		smsShopTotal.setMealSum(this.crmSmsDao.countSmsMealList(DefaultEnum.YES.getCode()));

		return ResultUtil.successMap(smsShopTotal);
	}
	
	@Override
	public Map<String, Object> getShopSmsAccountDetail(Integer adminId, Integer year, Integer pageOffset, Integer pageSize) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Assert.notNull(adminId, "adminId不能为空");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("totalIncome", this.crmSmsDao.getSaleOrderIncome(null, null, adminId));
		resultMap.put("yearIncome", this.crmSmsDao.getSaleOrderIncome(year, null, adminId));
		//resultMap.put("monthIncome", this.crmSmsDao.getSaleOrderIncome(DateUtil.getCurrentYear(), DateUtil.getCurrentMonth(), adminId));
		resultMap.put("monthIncomeReport", this.crmSmsDao.getMonthSaleOrderIncome(year, adminId));
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t2.create_time");
		resultMap.put("mealSaleOrderList", this.crmSmsDao.getMealSaleOrderList(page, null, null, adminId, null, null, null));
		resultMap.put("mealSaleOrderSum", this.crmSmsDao.countMealSaleOrderList(null, null, adminId, null, null, null));
		return ResultUtil.successMap(resultMap);

	}
	
	@Override
	@Transactional
	public Map<String, Object> createOrder(Integer mealId, Integer adminId, Integer buyNum, String ipAddress) {
		Assert.notNull(mealId, "套餐id不能为空");
		Assert.notNull(adminId, "门店管理员id不能为空");
		Assert.notNull(buyNum, "购买数量不能为空");
		SmsMeal smsMealDb = this.crmSmsDao.getSmsMealById(mealId);
		if(null == smsMealDb) {
			throw new FerrariBizException(ResultCode.SMS_MEAL_NOT_EXISTS, "短信套餐不存在，mealId=" + mealId);
		}
		if(DefaultEnum.NO.getCode().equals(smsMealDb.getUploadStatus())) {
			throw new FerrariBizException(ResultCode.SMS_MEAL_SALE_OUT, "短信套餐已下架，mealId=" + mealId);
		}
		SmsTotal smsTotalDb = this.crmSmsDao.getSmsTotalById();
		//购买的数量
		Long buySmsNum = buyNum * smsMealDb.getSmsCount();
		//平台已售数量
		Long totalSaleNum = smsTotalDb.getSaleNum();
		//如果已售+购买的数量 > 平台的总量  表示库存不足
		if((buySmsNum + totalSaleNum) > smsTotalDb.getTotalNum()) {
			throw new FerrariBizException(ResultCode.SMS_STOCK_NOT_ENOUGH, "星奥短信总库存量不足");
		}
		BigDecimal payMoney = smsMealDb.getSalePrice().multiply(new BigDecimal(buyNum));
		//创建订单 t_sms_saleorder
		SmsSaleOrder saleOrder = new SmsSaleOrder();
		saleOrder.setMealId(mealId);
		saleOrder.setAdminId(adminId);
		saleOrder.setBuyNum(buyNum);
		saleOrder.setPayMoney(payMoney);
		saleOrder.setIsInvoice(DefaultEnum.NO.getCode());
		saleOrder.setPayStatus(PayStatus.NOT_PAY);
//		saleOrder.setAlipay(alipay);
//		saleOrder.setAlipaySerial(alipaySerial);
		this.crmSmsDao.insertSmsSaleOrder(saleOrder);
		PayVo payVo = new PayVo();
		payVo.setAmount(payMoney);
		payVo.setCurrency("cny");
		payVo.setSubject(smsMealDb.getMealName());
		payVo.setBody(smsMealDb.getRemark());
		payVo.setOrderNo(saleOrder.getId().toString());
		payVo.setChannel("alipay_pc_direct");
		payVo.setClientIp(ipAddress);
		payVo.setAppId("app_aXzL08K4q18KvHuX");
		Result result = this.pingXXPayComponent.pay(payVo);
		Charge charge = null;
		if(ResultCode.SUCCESS.getCode().equals(result.getResultCode())){
			charge = (Charge) result.getResult();
		}else{
			throw new FerrariBizException(ResultCode.THIRD_PAY_FAIL, "pingxx创建Charge失败");
		}
		return ResultUtil.successMap(charge);
	}
	
	@Override
	@Transactional
	public boolean paySuccess(Integer saleOrderId)	{
		Assert.notNull(saleOrderId, "商户订单id不能为空");
		SmsSaleOrder saleOrder = this.crmSmsDao.getSmsSaleOrderById(saleOrderId);
		if(null == saleOrder) {
			throw new FerrariBizException(ResultCode.THIRD_MERCHAT_ORDER_NOTEXISTS, "商户订单不存在，saleOrderId=" + saleOrderId);
		}
		if(PayStatus.PAY_SUCC.equals(saleOrder.getPayStatus())) {
			throw new FerrariBizException(ResultCode.PAY_SUCCESS_CANNOT_HANDER, "支付成功的订单不能重复处理，saleOrderId=" + saleOrderId);
		}
		//更新订单
		SmsSaleOrder smsSaleOrderUp = new SmsSaleOrder();
		smsSaleOrderUp.setId(saleOrderId);
		smsSaleOrderUp.setPayStatus(PayStatus.PAY_SUCC);
		this.crmSmsDao.updateSmsSaleOrder(smsSaleOrderUp);
		//更新套餐的销量 t_sms_meal
		SmsMeal smsMealDb = this.crmSmsDao.getSmsMealById(saleOrder.getMealId());
		if(null == smsMealDb) {
			throw new FerrariBizException(ResultCode.SMS_MEAL_NOT_EXISTS, ResultCode.SMS_MEAL_NOT_EXISTS.getString());
		}
		SmsMeal smsMeal = new SmsMeal();
		smsMeal.setId(saleOrder.getMealId());
		smsMeal.setVersion(smsMealDb.getVersion() + 1);
		smsMeal.setSaleNum(smsMealDb.getSaleNum() + saleOrder.getBuyNum());
		this.crmSmsDao.updateSmsMeal(smsMeal);
		//更新星奥已售库存量 t_sms_total
		SmsTotal smsTotalDb = this.crmSmsDao.getSmsTotalById();
		if(null == smsTotalDb) {
			throw new FerrariBizException(ResultCode.SMS_TOTALSMS_NOT_EXISTS, ResultCode.SMS_TOTALSMS_NOT_EXISTS.getString());
		}
		SmsTotal smsTotal = new SmsTotal();
		smsTotal.setId(smsTotalDb.getId());
		smsTotal.setSaleNum(smsTotalDb.getSaleNum() + (saleOrder.getBuyNum() * smsMealDb.getSmsCount()));
		smsTotal.setVersion(smsTotalDb.getVersion() + 1);
		this.crmSmsDao.updateSmsTotal(smsTotal);
		//更新门店库总存量 t_sms_shoptotal
		SmsShopTotal smsShopTotalDb = this.crmSmsDao.getSmsShopTotalByAdminId(saleOrder.getAdminId());
		if(null == smsShopTotalDb) {
			SmsShopTotal smsShopTotal = new SmsShopTotal();
			smsShopTotal.setAdminId(saleOrder.getAdminId());
			smsShopTotal.setTotalNum(saleOrder.getBuyNum() * smsMealDb.getSmsCount());
			smsShopTotal.setUseNum(0L);
			this.crmSmsDao.insertSmsShopTotal(smsShopTotal);
		}else{
			SmsShopTotal smsShopTotal = new SmsShopTotal();
			smsShopTotal.setId(smsShopTotalDb.getId());
			smsShopTotal.setTotalNum(smsShopTotalDb.getTotalNum() + (saleOrder.getBuyNum() * smsMealDb.getSmsCount()));
			smsShopTotal.setVersion(smsShopTotalDb.getVersion() + 1);
			this.crmSmsDao.updateSmsShopTotal(smsShopTotal);
		}
		//新增星奥购买记录 t_sms_shoptotalrecord
		SmsShopTotalRecord record = new SmsShopTotalRecord();
		record.setAdminId(saleOrder.getAdminId());
		record.setTotalNum(saleOrder.getBuyNum() * smsMealDb.getSmsCount());
		this.crmSmsDao.insertSmsShopTotalRecord(record);
		return true;
	}
	
	public static void main(String[] args) {
		MandaoSmsClient client = null;
		try {
			client = new MandaoSmsClient(MandaoSmsClient.YX_SN, MandaoSmsClient.YX_PW);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		//从第三方短信平台拉取当前账户剩余短信数
		String totalSmsForStr = client.getBalance();
		try {
			client = new MandaoSmsClient(MandaoSmsClient.TZ_SN, MandaoSmsClient.TZ_PW);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//从第三方短信平台拉取当前账户剩余短信数
		String xaTotalSmsForStr = client.getBalance();
		Long totalSmsFromThird = StringUtils.isBlank(totalSmsForStr) ? 0L : Long.parseLong(totalSmsForStr);
		Long xaTotalSmsFromThird = StringUtils.isBlank(xaTotalSmsForStr) ? 0L : Long.parseLong(xaTotalSmsForStr);
		System.out.println(totalSmsFromThird);
		System.out.println(xaTotalSmsFromThird);
	}
}
