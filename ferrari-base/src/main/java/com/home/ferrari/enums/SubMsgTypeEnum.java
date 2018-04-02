package com.home.ferrari.enums;

/**
* @ClassName: SubMsgTypeEnum 
* @Description: 子消息类型
* @author zengbin
* @date 2016年08月10日
 */
public enum SubMsgTypeEnum {
    
	ALL_SHOP(1, "所有门店"), 
	ALL_STAFF(2, "所有星奥员工"), 
	SHOP_LIST(3, "按照条件搜索门店 "), 
	RESULT_LIST(4, "按照搜索结果 "), 
	MY_SHOP(5, "我管辖的门店"), 
	CUNANDCHE(6, "村淘&车码头"), 
	CUNTAO(7, "村淘"), 
	XINGAO(8, "星奥"), 
	CHE(9, "车码头"), 
	A(10, "A"), 
	B(11, "B"), 
	C(12, "C"), 
    ;
    
    private SubMsgTypeEnum(Integer code, String name) {
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
    
    public static SubMsgTypeEnum getEnum(String name) {
        for (SubMsgTypeEnum defaultEnum : SubMsgTypeEnum.values()) {
            if (defaultEnum.getName().equals(name)) {
                return defaultEnum;
            }
        }
        return null;
    }
}
