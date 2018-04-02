package com.home.ferrari.service.sms.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.crmdao.crm.CrmUserDao;
import com.home.ferrari.crmdao.sms.CrmSmsDao;
import com.home.ferrari.enums.CrmRoleTypeEnum;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.plugin.sms.MandaoSmsClient;
import com.home.ferrari.service.sms.SendCustomerService;
import com.home.ferrari.util.MD5Util;
import com.home.ferrari.vo.crm.CrmUser;
import com.home.ferrari.vo.sms.SmsRecord;
import com.home.ferrari.vo.sms.SmsShopTotal;
import com.home.ferrari.vo.sms.SmsTotal;

@Service
public class SendCustomerServiceImpl implements SendCustomerService {
	
	@Autowired
	private CrmSmsDao crmSmsDao;
	@Autowired
	private CrmUserDao crmUserDao;
	
	public void sendSmsByMobiles(Integer adminId, Integer sessionUserId, Integer sessionRoleType, String []mobileList, String smsContent) {
		StringBuffer mobiles = new StringBuffer();
		List<SmsRecord> smsRecordList = new ArrayList<SmsRecord>();
		int i = 1;
		String groupCode = MD5Util.md5(UUID.randomUUID().toString()).toUpperCase();
		//计算发送因子，按照文字计算比例
		Integer seed = this.calculateSmsCount(smsContent);
		for(String mobile : mobileList) {
			mobiles.append(mobile);
			if(i < mobileList.length) {
				mobiles.append(",");
			}
			++i;
			SmsRecord smsRecord = new SmsRecord();
			smsRecord.setAdminId(adminId);
			smsRecord.setCrmUserId(sessionUserId);
			smsRecord.setCustomerId(-10000);
			smsRecord.setReceiveMobile(mobile);
			smsRecord.setSmsContent(smsContent);
			smsRecord.setGroupCode(groupCode);
			smsRecord.setGroupSeed(seed);
			if(CrmRoleTypeEnum.ROLE_SYSTEM_ADMIN.getCode().equals(sessionRoleType)) {
				smsRecord.setSmsType(1);
			} else {
				smsRecord.setSmsType(2);
			}
			smsRecordList.add(smsRecord);
		}
		//更新库存
		this.stock(adminId, mobileList.length * seed, smsRecordList, sessionRoleType);
		//发送短信
		this.sendMsg(mobiles.toString(), smsContent, sessionRoleType);
	}
	
	public String getSendManagerMobiles(String province, String city, String crmShopName) {
		List<CrmUser> adminList = this.crmUserDao.getCrmAdminList(null, DefaultEnum.YES.getCode(), province, city, crmShopName);
		StringBuffer mobiles = new StringBuffer();
		int i = 1;
		if(CollectionUtils.isNotEmpty(adminList)) {
			for(CrmUser admin : adminList) {
				if(StringUtils.isNotBlank(admin.getMobile())) {
					mobiles.append(admin.getMobile());
					if(i < adminList.size()) {
						mobiles.append(",");
					}
					++i;
				}
			}
		}
		return mobiles.toString();
	}
	
	@Transactional
	private void stock(Integer adminId, Integer sendNum, List<SmsRecord> smsRecordList, Integer sessionRoleType) {
		if(CrmRoleTypeEnum.ROLE_SYSTEM_ADMIN.getCode().equals(sessionRoleType)) {
			//扣减星奥发送库存
			SmsTotal smsTotal = this.crmSmsDao.getSmsTotalById();
			Long xaLeftNum = smsTotal.getXaTotalNum() - smsTotal.getXaUseNum();
			if (null == smsTotal || sendNum > xaLeftNum) {
				throw new FerrariBizException(ResultCode.SMS_STOCK_NOT_ENOUGH, "星奥短信总库存量不足");
			}
			Long xaUseNum = smsTotal.getXaUseNum() + sendNum;
			SmsTotal smsTotalDB = new SmsTotal();
			smsTotalDB.setId(1);
			smsTotalDB.setXaUseNum(xaUseNum);
			smsTotalDB.setVersion(smsTotal.getVersion() + 1);
			this.crmSmsDao.updateSmsTotal(smsTotalDB);
		} else {
			//更新门店发送量 t_sms_shoptotal
			SmsShopTotal smsShopTotalDb = this.crmSmsDao.getSmsShopTotalByAdminId(adminId);
			if(null == smsShopTotalDb) {
				throw new FerrariBizException(ResultCode.SMS_SHOP_MEAL_NOT_BUY, "门店未购买短信套餐");
			}else{
				Long leftNum = smsShopTotalDb.getTotalNum() - smsShopTotalDb.getUseNum();
				if(Long.parseLong(sendNum.toString()) > leftNum) {
					throw new FerrariBizException(ResultCode.SMS_SHOP_STOCK_NOT_ENOUGH, "门店短信总库存量不足");
				}
				SmsShopTotal smsShopTotal = new SmsShopTotal();
				smsShopTotal.setId(smsShopTotalDb.getId());
				smsShopTotal.setUseNum(Long.parseLong(sendNum.toString()) + smsShopTotalDb.getUseNum());
				smsShopTotal.setVersion(smsShopTotalDb.getVersion() + 1);
				this.crmSmsDao.updateSmsShopTotal(smsShopTotal);
			}
		}
		//插入发送记录 t_sms_record
		this.crmSmsDao.insertSmsRecordBatch(smsRecordList);
	}
	
	@Async
	public void sendMsg(String mobiles, String smsContent, Integer sessionRoleType) {
		String sn = MandaoSmsClient.YX_SN;
		String pwd = MandaoSmsClient.YX_PW;
		if(CrmRoleTypeEnum.ROLE_SYSTEM_ADMIN.getCode().equals(sessionRoleType)) {
			sn = MandaoSmsClient.TZ_SN;
			pwd = MandaoSmsClient.TZ_PW;
		}
		//发送短信
		MandaoSmsClient client = null;
		try {
			client = new MandaoSmsClient(sn, pwd);
			String content = java.net.URLEncoder.encode(smsContent + "【xacl】",  "utf-8");
			client.send(mobiles, content, "", "", "", "");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public Integer calculateSmsCount(String smsContent) {
		if (StringUtils.isBlank(smsContent)) {
			return 0;
		}
		smsContent = smsContent + "【xacl】";
		// 70个字以内算一条，超过70个字67个字算一条
		Integer length = smsContent.length();
		if (length <= 70) {
			return 1;
		} else {
			return (length % 67) == 0 ? (length / 67) : (length / 67 + 1);
		}
	}
	
	public static final void main(String []arg) {	
		int length = 12;
		System.out.println(length % 67 == 0 ? length / 67 : length / 67 + 1);
	}
}
