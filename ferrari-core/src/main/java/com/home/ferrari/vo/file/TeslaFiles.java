package com.home.ferrari.vo.file;

import java.io.Serializable;
import java.util.Date;

public class TeslaFiles implements Serializable {

	private static final long serialVersionUID = 8497347446608783199L;

	private Integer fileTypeId;
	private Integer shopId;// 门店id
	private Integer fileType;
	private String fileSuffix;
	private String fileUrls;// 门店资料
	private Date createTime;
	private Date modifyTime;

	public TeslaFiles() {

	}

	public TeslaFiles(Integer shopId, Integer fileType, String fileSuffix,
			String fileUrls) {
		this.shopId = shopId;
		this.fileType = fileType;
		this.fileSuffix = fileSuffix;
		this.fileUrls = fileUrls;
	}

	public Integer getFileTypeId() {
		return fileTypeId;
	}

	public void setFileTypeId(Integer fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getFileUrls() {
		return fileUrls;
	}

	public void setFileUrls(String fileUrls) {
		this.fileUrls = fileUrls;
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

}
