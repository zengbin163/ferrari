package com.home.ferrari.enums;

/**
* @ClassName: CachePrefixEnum 
* @Description: 缓存里面各种前缀
* @author zengbin
* @date 2015年11月9日
 */
public enum CachePrefixEnum {
    
    PREFIX_SESSION_(101,"用户登录session前缀"),
    PREFIX_TESLA_USER_INFO_(102,"特斯拉用户信息前缀"),
    PREFIX_FERRARI_USER_INFO_(103,"法拉利用户信息前缀"),
    
    PREFIX_TESLA_SHOP_INFO_(201, "特斯拉门店信息"),

    PREFIX_SMS_NO_(301, "短信验证码"),
    ;
    
    private CachePrefixEnum(Integer code, String name) {
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
    
    public static CachePrefixEnum getEnum(Integer code) {
        for (CachePrefixEnum prefixEnum : CachePrefixEnum.values()) {
            if (prefixEnum.getCode().equals(code)) {
                return prefixEnum;
            }
        }
        return null;
    }
}
