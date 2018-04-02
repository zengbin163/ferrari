package com.home.ferrari.vo.sms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 每个门店的短信库存
 * 
 * @author zengbin
 *
 */
public class SmsShopTotal implements Serializable {

	private static final long serialVersionUID = 6767129294384091525L;

	private Integer id;
	private Integer adminId; // 管理员id，代表crm门店
	private Long totalNum;// 总的购买短信数
	private Long useNum;// 短信使用了多少条
	private Integer version;
	private Date createTime;
	private Date modifyTime;

	private String crmShopName;// 门店名称
	private String adminName;// 客户经理
	private String loginNo;// 账号
	private Long leftNum;// 剩余短信数

	private BigDecimal totalIncome;// 总收入
	private BigDecimal yearIncome;// 年收入
	private BigDecimal monthIncome;// 最近月收入
	private List<Map<String, BigDecimal>> monthIncomeReport;// 每个月收入
	private List<SmsMeal> mealList;// 套餐列表
	private Integer mealSum;// 套餐总数

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public Long getUseNum() {
		return useNum;
	}

	public void setUseNum(Long useNum) {
		this.useNum = useNum;
	}

	public String getCrmShopName() {
		return crmShopName;
	}

	public void setCrmShopName(String crmShopName) {
		this.crmShopName = crmShopName;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}

	public Long getLeftNum() {
		return leftNum;
	}

	public void setLeftNum(Long leftNum) {
		this.leftNum = leftNum;
	}

	public BigDecimal getYearIncome() {
		return yearIncome;
	}

	public void setYearIncome(BigDecimal yearIncome) {
		this.yearIncome = yearIncome;
	}

	public BigDecimal getMonthIncome() {
		return monthIncome;
	}

	public void setMonthIncome(BigDecimal monthIncome) {
		this.monthIncome = monthIncome;
	}

	public List<Map<String, BigDecimal>> getMonthIncomeReport() {
		return monthIncomeReport;
	}

	public void setMonthIncomeReport(
			List<Map<String, BigDecimal>> monthIncomeReport) {
		this.monthIncomeReport = monthIncomeReport;
	}

	public List<SmsMeal> getMealList() {
		return mealList;
	}

	public void setMealList(List<SmsMeal> mealList) {
		this.mealList = mealList;
	}

	public Integer getMealSum() {
		return mealSum;
	}

	public void setMealSum(Integer mealSum) {
		this.mealSum = mealSum;
	}

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}
}
