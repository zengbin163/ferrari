package com.home.ferrari.enums;

/**
 * @ClassName: UploadPictureEnum
 * @Description: 上传图片类型
 * @author zengbin
 * @date 2015年11月25日
 */
public enum UploadPictureEnum {

	PICTURE_FERRARI(1, "服务商", "/ferrari/"), 
	PICTURE_TESLA(2, "门店", "/tesla/"), 
	PICTURE_QUEST(3, "问卷", "/quest/"),
	PICTURE_B2B(4, "B2B", "/b2b/"),
	PICTURE_QR(5, "QR", "/qr/"),
	;

	private UploadPictureEnum(Integer code, String desc, String path) {
		this.code = code;
		this.desc = desc;
		this.path = path;
	}

	private Integer code;

	private String desc;

	private String path;

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public String getPath() {
		return path;
	}

	public static UploadPictureEnum getEnum(Integer code) {
		for (UploadPictureEnum uploadPictureEnum : UploadPictureEnum.values()) {
			if (uploadPictureEnum.getCode().equals(code)) {
				return uploadPictureEnum;
			}
		}
		return null;
	}
}
