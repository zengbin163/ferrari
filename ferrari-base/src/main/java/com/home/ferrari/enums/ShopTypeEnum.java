package com.home.ferrari.enums;


/**
* @ClassName: ShopTypeEnum 
* @Description: 门店类型
* @author zengbin
* @date 2016年08月10日
 */
public enum ShopTypeEnum {
    
	CHEMATOU(1, "车码头"), 
	CUNTAO(2, "村淘"), 
    CHEMATOUCUNTAO(3, "车码头&村淘"), 
    XINGAO(4, "星奥自拓展"),
    ;
    
    private ShopTypeEnum(Integer code, String name) {
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
    
    public static ShopTypeEnum getEnum(String name) {
        for (ShopTypeEnum defaultEnum : ShopTypeEnum.values()) {
            if (defaultEnum.getName().equals(name)) {
                return defaultEnum;
            }
        }
        return null;
    }
}
