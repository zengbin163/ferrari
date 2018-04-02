package com.home.ferrari.vo.sms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 星奥短信总库存
 * 
 * @author zengbin
 *
 */
public class SmsTotal implements Serializable {

	private static final long serialVersionUID = 6767129294384091525L;
	private Integer id;
	private Long totalNum;// 短信总条数
	private Long saleNum;// 短信已售条数
	private Long notSaleNum;// 短信未售条数
	private Long xaTotalNum;// 星奥内部短信总条数
	private Long xaUseNum;// 星奥内部短信使用量
	private Long xaLeftNum;// 星奥内部短信剩余量
	private Long version;// 乐观锁
	private Date createTime;
	private Date modifyTime;

	private BigDecimal consumedNum;// 已消耗的短信数
	private BigDecimal notConsumeNum;// 未消耗的短信数
	private BigDecimal totalIncome;// 总收入
	private BigDecimal yearIncome;// 年收入
	private BigDecimal monthIncome;// 最近月收入
	private List<Map<String, BigDecimal>> monthIncomeReport;//每个月收入
	private List<SmsMeal> mealList;// 套餐列表
	private Integer mealSum; //套餐总数

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public Long getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Long saleNum) {
		this.saleNum = saleNum;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public BigDecimal getConsumedNum() {
		return consumedNum;
	}

	public void setConsumedNum(BigDecimal consumedNum) {
		this.consumedNum = consumedNum;
	}

	public BigDecimal getNotConsumeNum() {
		return notConsumeNum;
	}

	public void setNotConsumeNum(BigDecimal notConsumeNum) {
		this.notConsumeNum = notConsumeNum;
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

	public void setMonthIncomeReport(List<Map<String, BigDecimal>> monthIncomeReport) {
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

	public Long getXaTotalNum() {
		return xaTotalNum;
	}

	public void setXaTotalNum(Long xaTotalNum) {
		this.xaTotalNum = xaTotalNum;
	}

	public Long getXaUseNum() {
		return xaUseNum;
	}

	public void setXaUseNum(Long xaUseNum) {
		this.xaUseNum = xaUseNum;
	}

	public Long getNotSaleNum() {
		return notSaleNum;
	}

	public void setNotSaleNum(Long notSaleNum) {
		this.notSaleNum = notSaleNum;
	}

	public Long getXaLeftNum() {
		return xaLeftNum;
	}

	public void setXaLeftNum(Long xaLeftNum) {
		this.xaLeftNum = xaLeftNum;
	}
}
