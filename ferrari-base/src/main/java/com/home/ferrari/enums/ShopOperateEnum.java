package com.home.ferrari.enums;


/**
* @ClassName: ShopOperateEnum 
* @Description: 门店操作类型
* @author zengbin
* @date 2016年08月10日
 */
public enum ShopOperateEnum {
    
	SELF(0, "手动创建"), 
	CREATE(1, "创建门店"), 
	EDIT(2, "完善资料"), 
    EXPAND(3, "拓展审批"), 
    AUDIT(4, "门店审批"),
    RETURN(5, "门店退回"),
    CAPACITY(6, "能力评估"),
    ;
    
    private ShopOperateEnum(Integer code, String name) {
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
    
    public static ShopOperateEnum getEnum(Integer code) {
        for (ShopOperateEnum defaultEnum : ShopOperateEnum.values()) {
            if (defaultEnum.getCode().equals(code)) {
                return defaultEnum;
            }
        }
        return null;
    }
}
