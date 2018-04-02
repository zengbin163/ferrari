package com.home.ferrari.enums;

/**
* @ClassName: CrmRoleTypeEnum 
* @Description: CRM角色枚举
* @author zengbin
* @date 2016年04月16日
 */
public enum CrmRoleTypeEnum {
    
    ROLE_SYSTEM_ADMIN	(1, "系统管理员"), 
    ROLE_SHOP_ADMIN	(2, "客户经理"),
    ROLE_BIZ_ADMIN		(3, "业务员"),
    ;
    
    private CrmRoleTypeEnum(Integer code, String name) {
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
    
    public static CrmRoleTypeEnum getEnum(Integer code) {
        for (CrmRoleTypeEnum roleEnum : CrmRoleTypeEnum.values()) {
            if (roleEnum.getCode().equals(code)) {
                return roleEnum;
            }
        }
        return null;
    }
}
