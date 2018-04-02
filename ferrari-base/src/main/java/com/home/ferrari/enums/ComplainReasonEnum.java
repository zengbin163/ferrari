package com.home.ferrari.enums;

/**
* @ClassName: ComplainReasonEnum 
* @Description: 投诉原因
* @author zengbin
* @date 2017年02月16日
 */
public enum ComplainReasonEnum {
    
	SHOP_NOT_ACCEPT(1, "门店不接待"), 
	SHOP_CANNOT_USE(2, "门店不会使用"), 
    BAD_SERVICE(3, "服务差"), 
    REMOVE_COOPER(4, "解除合作"),
    CLOSE_SHOP(5, "门店关闭"),
    CLEAN_SHOP(6, "清洗不干净"),
    OTHERS(7, "其他"),
    ;
    
    private ComplainReasonEnum(Integer code, String name) {
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
    
    public static ComplainReasonEnum getEnumByCode(Integer code) {
    	for (ComplainReasonEnum defaultEnum : ComplainReasonEnum.values()) {
    		if (defaultEnum.getCode().equals(code)) {
    			return defaultEnum;
    		}
    	}
    	return null;
    }
    
    public static ComplainReasonEnum getEnum(String name) {
        for (ComplainReasonEnum defaultEnum : ComplainReasonEnum.values()) {
            if (defaultEnum.getName().equals(name)) {
                return defaultEnum;
            }
        }
        return null;
    }
}
