package com.home.ferrari.vo.file;

import java.io.Serializable;
import java.util.Date;

public class FerrariFiles implements Serializable {

	private static final long serialVersionUID = 8497347446608783199L;

	private Integer fileTypeId;
	private Integer parentFiletypeId;// 父级文件id
	private Integer isFile;// 0文件夹 1文件
	private Integer fileType;// 文件类型 @FerrariFileTypeEnum
	private String fileSuffix;
	private String fileUrls;// 文件路径列表
	private Date createTime;
	private Date modifyTime;

	public FerrariFiles() {

	}

	public FerrariFiles(Integer parentFiletypeId, Integer isFile,
			Integer fileType, String fileSuffix, String fileUrls) {
		this.parentFiletypeId = parentFiletypeId;
		this.isFile = isFile;
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

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
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

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public Integer getParentFiletypeId() {
		return parentFiletypeId;
	}

	public void setParentFiletypeId(Integer parentFiletypeId) {
		this.parentFiletypeId = parentFiletypeId;
	}

	public Integer getIsFile() {
		return isFile;
	}

	public void setIsFile(Integer isFile) {
		this.isFile = isFile;
	}
}
