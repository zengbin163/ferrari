package com.home.ferrari.enums;

/**
* @ClassName: RoleTypeEnum 
* @Description: 角色枚举
* @author zengbin
* @date 2016年04月16日
 */
public enum RoleTypeEnum {
    
    ROLE_SYSTEM	(1, "系统管理员"), 
    ROLE_SHOP	(2, "门店管理员"),
    ROLE_BD		(3, "大区总监"),  //原BD
    ROLE_LAW	(4, "法务"),
    ROLE_FINAN	(5, "财务"),
    ROLE_VI		(6, "VI"),
    ROLE_MANAGER(7, "总裁"),
    ROLE_OPER	(8, "运营数据分析管理"),
    ROLE_PROXY	(9, "省市代"),
    ROLE_AREA_MANAGER(10, "事业部总经理"),
    ROLE_CUSTOMER(11, "行政管理"),
    ROLE_ORDER(12, "线上订单处理管理"),
    ROLE_ELEC(13, "电子商务管理"),
    ROLE_BREAK(14, "故障技术管理"),
    ROLE_COO(15, "COO"),
    ROLE_PROVINCE(16, "省代理"),
    ROLE_CITY(17, "市代理"),
    ROLE_SYSTEM_CRM(18, "CRM系统管理员"),
    ;
    
    private RoleTypeEnum(Integer code, String name) {
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
    
    public static RoleTypeEnum getEnum(Integer code) {
        for (RoleTypeEnum roleEnum : RoleTypeEnum.values()) {
            if (roleEnum.getCode().equals(code)) {
                return roleEnum;
            }
        }
        return null;
    }
}
