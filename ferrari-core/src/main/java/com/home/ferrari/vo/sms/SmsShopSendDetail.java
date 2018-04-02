package com.home.ferrari.vo.sms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class SmsShopSendDetail implements Serializable {

	private static final long serialVersionUID = 295844653654669378L;

	private String crmShopName;// 门店名称
	private String crmShopCompany;// 门店公司名称
	private String adminName;// 门店公司名称
	private String mobile;// 联系电话
	private Long totalNum;// 累积购买多少条
	private Long useNum;// 短信使用了多少条
	private Long leftNum;// 短信剩余了多少条
	private BigDecimal accumulateCost;// 累积花费

	private List<SmsSendRecord> smsSendRecordList;// 短信发送记录
	private Integer smsSendRecordSum;// 短信发送总数

	public String getCrmShopName() {
		return crmShopName;
	}

	public void setCrmShopName(String crmShopName) {
		this.crmShopName = crmShopName;
	}

	public String getCrmShopCompany() {
		return crmShopCompany;
	}

	public void setCrmShopCompany(String crmShopCompany) {
		this.crmShopCompany = crmShopCompany;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getUseNum() {
		return useNum;
	}

	public void setUseNum(Long useNum) {
		this.useNum = useNum;
	}

	public Long getLeftNum() {
		return leftNum;
	}

	public void setLeftNum(Long leftNum) {
		this.leftNum = leftNum;
	}

	public List<SmsSendRecord> getSmsSendRecordList() {
		return smsSendRecordList;
	}

	public void setSmsSendRecordList(List<SmsSendRecord> smsSendRecordList) {
		this.smsSendRecordList = smsSendRecordList;
	}

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public BigDecimal getAccumulateCost() {
		return accumulateCost;
	}

	public void setAccumulateCost(BigDecimal accumulateCost) {
		this.accumulateCost = accumulateCost;
	}

	public Integer getSmsSendRecordSum() {
		return smsSendRecordSum;
	}

	public void setSmsSendRecordSum(Integer smsSendRecordSum) {
		this.smsSendRecordSum = smsSendRecordSum;
	}

}
