package com.home.ferrari.enums;

/**
* @ClassName: MsgTypeEnum 
* @Description: 消息类型
* @author zengbin
* @date 2016年08月10日
 */
public enum MsgTypeEnum {
    
	QUEST(1, "调研消息"), 
	NORMAL(2, "普通(平台)消息"), 
	CONTRACT(3, "合同消息"), 
    TRAIN(4, "培训消息"), 
    COMPANY(5, "总裁信箱"), 
    ;
    
    private MsgTypeEnum(Integer code, String name) {
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
    
    public static MsgTypeEnum getEnum(String name) {
        for (MsgTypeEnum defaultEnum : MsgTypeEnum.values()) {
            if (defaultEnum.getName().equals(name)) {
                return defaultEnum;
            }
        }
        return null;
    }
}
