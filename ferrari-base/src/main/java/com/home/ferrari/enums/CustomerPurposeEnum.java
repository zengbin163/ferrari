package com.home.ferrari.enums;


/**
* @ClassName: CustomerPurposeEnum 
* @Description: 客户意向
* @author zengbin
* @date 2016年08月10日
 */
public enum CustomerPurposeEnum {
    
	A("A", "意向客户"), 
	B("B", "待跟进客户"), 
    C("C", "暂时不需要客户"), 
    D("D", "放弃客户"),
    E("E", "未联系上客户"),
    F("F", "成功来厂客户"),
    ;
    
    private CustomerPurposeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    private String code;
    
    private String name;
    
    public String getCode() {
        return code;
    }
    
    public String getName() {
        return name;
    }
    
    public static CustomerPurposeEnum getEnum(String name) {
        for (CustomerPurposeEnum defaultEnum : CustomerPurposeEnum.values()) {
            if (defaultEnum.getName().equals(name)) {
                return defaultEnum;
            }
        }
        return null;
    }
}
