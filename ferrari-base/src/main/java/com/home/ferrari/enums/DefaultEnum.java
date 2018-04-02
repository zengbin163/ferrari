package com.home.ferrari.enums;

/**
* @ClassName: DefaultEnum 
* @Description: 默认值枚举
* @author zengbin
* @date 2015年11月16日
 */
public enum DefaultEnum {
    
    NO(0, "否"), 
    YES(1, "是");
    
    private DefaultEnum(Integer code, String name) {
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
    
    public static DefaultEnum getEnum(Integer code) {
        for (DefaultEnum defaultEnum : DefaultEnum.values()) {
            if (defaultEnum.getCode().equals(code)) {
                return defaultEnum;
            }
        }
        return null;
    }
}
