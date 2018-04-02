package com.home.ferrari.enums;


/**
* @ClassName: AbcTypeEnum 
* @Description: 门店级别
* @author zengbin
* @date 2016年08月10日
 */
public enum AbcTypeEnum {
    
	A(1, "A"), 
	B(2, "B"), 
    C(3, "C"), 
    D(4, "D"),
    ;
    
    private AbcTypeEnum(Integer code, String name) {
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
    
    public static AbcTypeEnum getEnum(String name) {
        for (AbcTypeEnum defaultEnum : AbcTypeEnum.values()) {
            if (defaultEnum.getName().equals(name)) {
                return defaultEnum;
            }
        }
        return null;
    }
}
