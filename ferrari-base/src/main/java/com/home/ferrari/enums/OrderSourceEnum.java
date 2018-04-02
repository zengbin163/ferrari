package com.home.ferrari.enums;


/**
* @ClassName: OrderSourceEnum 
* @Description: 订单来源
* @author zengbin
* @date 2017年04月02日
 */
public enum OrderSourceEnum {
    
	TMALL(1, "天猫"), 
	TAOBAO(2, "淘宝"), 
    CUNTAO(3, "村淘"), 
    ALI_CHEZHUKA(4, "阿里车主卡"),
    VIRTUAL_SHOP_ORDER(5, "虚拟门店订单"),
    ;
    
    private OrderSourceEnum(Integer code, String name) {
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
    
    public static OrderSourceEnum getEnum(String name) {
        for (OrderSourceEnum defaultEnum : OrderSourceEnum.values()) {
            if (defaultEnum.getName().equals(name)) {
                return defaultEnum;
            }
        }
        return null;
    }
}
