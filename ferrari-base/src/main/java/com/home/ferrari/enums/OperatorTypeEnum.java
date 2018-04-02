package com.home.ferrari.enums;

/**
 * @ClassName: OperatorTypeEnum
 * @Description: 操作者类型
 * @author zengbin
 * @date 2016年08月17日
 */
public enum OperatorTypeEnum {
	SYSTEM(1, "系统"), VENTOR(2, "服务商"), SHOP(3, "门店"), ;

	private OperatorTypeEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	private Integer code;

	private String name;

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static OperatorTypeEnum getEnum(Integer code) {
		for (OperatorTypeEnum roleEnum : OperatorTypeEnum.values()) {
			if (roleEnum.getCode().equals(code)) {
				return roleEnum;
			}
		}
		return null;
	}
}
