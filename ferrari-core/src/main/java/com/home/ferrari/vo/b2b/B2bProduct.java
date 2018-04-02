package com.home.ferrari.vo.b2b;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class B2bProduct implements Serializable {

	private static final long serialVersionUID = -4392110017064787653L;

	private Integer productId;// 产品id
	private String productName;// 产品名称
	private String keyWords;// 关键字
	private BigDecimal price;// 价格
	private Integer categoryId;// 类目id
	private String brandName;// 品牌名称
	private String mobile;// 联系人手机号码
	private String productModel;// 型号
	private String productArea;// 产地
	private String productColor;// 颜色
	private String productYear;// 年份
	private String pic1Addr;// 图片1
	private String pic2Addr;// 图片2
	private String pic3Addr;// 图片3
	private String pic4Addr;// 图片4
	private String pic5Addr;// 图片5
	private Integer viewCount;// 浏览量
	private Integer version;
	private Integer canShow;// 是否展现，0待上架   1已上架   2已下线
	private Integer isIndex;// 是否上首页 0不上 1上
	private BigDecimal guidePrice;// 指导价
	private String color;// 颜色
	private String trim;// 内饰
	private String transSpecify;// 变速箱规格
	private String trainTeacher;// 培训讲师
	private String trainAddress;// 培训地点
	private String model;// 版本（型号）
	private Integer brandId;// 品牌id
	private Date createTime;
	private Date modifyTime;
	private String text;// 文描
	private String categoryName;// 类目名称
	private String categoryShortName;// 类目简称

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public String getProductArea() {
		return productArea;
	}

	public void setProductArea(String productArea) {
		this.productArea = productArea;
	}

	public String getProductColor() {
		return productColor;
	}

	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}

	public String getProductYear() {
		return productYear;
	}

	public void setProductYear(String productYear) {
		this.productYear = productYear;
	}

	public String getPic1Addr() {
		return pic1Addr;
	}

	public void setPic1Addr(String pic1Addr) {
		this.pic1Addr = pic1Addr;
	}

	public String getPic2Addr() {
		return pic2Addr;
	}

	public void setPic2Addr(String pic2Addr) {
		this.pic2Addr = pic2Addr;
	}

	public String getPic3Addr() {
		return pic3Addr;
	}

	public void setPic3Addr(String pic3Addr) {
		this.pic3Addr = pic3Addr;
	}

	public String getPic4Addr() {
		return pic4Addr;
	}

	public void setPic4Addr(String pic4Addr) {
		this.pic4Addr = pic4Addr;
	}

	public String getPic5Addr() {
		return pic5Addr;
	}

	public void setPic5Addr(String pic5Addr) {
		this.pic5Addr = pic5Addr;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getCanShow() {
		return canShow;
	}

	public void setCanShow(Integer canShow) {
		this.canShow = canShow;
	}

	public Integer getIsIndex() {
		return isIndex;
	}

	public void setIsIndex(Integer isIndex) {
		this.isIndex = isIndex;
	}

	public BigDecimal getGuidePrice() {
		return guidePrice;
	}

	public void setGuidePrice(BigDecimal guidePrice) {
		this.guidePrice = guidePrice;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTrim() {
		return trim;
	}

	public void setTrim(String trim) {
		this.trim = trim;
	}

	public String getTransSpecify() {
		return transSpecify;
	}

	public void setTransSpecify(String transSpecify) {
		this.transSpecify = transSpecify;
	}

	public String getTrainTeacher() {
		return trainTeacher;
	}

	public void setTrainTeacher(String trainTeacher) {
		this.trainTeacher = trainTeacher;
	}

	public String getTrainAddress() {
		return trainAddress;
	}

	public void setTrainAddress(String trainAddress) {
		this.trainAddress = trainAddress;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getCategoryShortName() {
		return categoryShortName;
	}

	public void setCategoryShortName(String categoryShortName) {
		this.categoryShortName = categoryShortName;
	}
}
