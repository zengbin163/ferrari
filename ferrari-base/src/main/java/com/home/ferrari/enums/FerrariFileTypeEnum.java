package com.home.ferrari.enums;


/**
* @ClassName: FerrariFileTypeEnum 
* @Description: 服务商文件类型
* @author zengbin
* @date 2016年12月17日
 */
public enum FerrariFileTypeEnum {
    
	PUBLIC_FILES (1, "公共资料"), 
	OPERATE_FILES(2, "运营资料"), 
	FINANCE_FILES(3, "财务资料"), 
	LAW_FILES	 (4, "法律资料"),
	SHOP_FILES	 (5, "门店资料"),
	DESIGN_FILES (6, "设计资料"),
	ADMIN_FILES  (7, "行政资料"),
	OTHERS_FILES (8, "其他资料"),
    ;
    
    private FerrariFileTypeEnum(Integer code, String name) {
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
    
    public static FerrariFileTypeEnum getEnum(String name) {
        for (FerrariFileTypeEnum defaultEnum : FerrariFileTypeEnum.values()) {
            if (defaultEnum.getName().equals(name)) {
                return defaultEnum;
            }
        }
        return null;
    }

    public static FerrariFileTypeEnum getEnumById(Integer code) {
    	for (FerrariFileTypeEnum defaultEnum : FerrariFileTypeEnum.values()) {
    		if (defaultEnum.getCode().equals(code)) {
    			return defaultEnum;
    		}
    	}
    	return null;
    }
}
