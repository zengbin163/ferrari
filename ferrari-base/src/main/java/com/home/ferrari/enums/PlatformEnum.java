package com.home.ferrari.enums;


/**
* @ClassName: PlatformEnum 
* @Description: 平台类型
* @author zengbin
* @date 2016年08月10日
 */
public enum PlatformEnum {
    
    FERRARI(0, "法拉利"), 
    TESLA(1, "特斯拉"),
    CRM(2, "crm平台"),
    ;
    
    private PlatformEnum(Integer code, String name) {
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
    
    public static PlatformEnum getEnum(Integer code) {
        for (PlatformEnum defaultEnum : PlatformEnum.values()) {
            if (defaultEnum.getCode().equals(code)) {
                return defaultEnum;
            }
        }
        return null;
    }
}
