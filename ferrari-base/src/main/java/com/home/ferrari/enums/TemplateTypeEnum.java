package com.home.ferrari.enums;


/**
* @ClassName: TemplateTypeEnum 
* @Description: 消息类型
* @author zengbin
* @date 2016年08月10日
 */
public enum TemplateTypeEnum {
    
	QUEST(1, "问卷调研"), 
	CONTRACT(2, "合同模板"), 
    TRAIN(3, "培训模板"), 
    ;
    
    private TemplateTypeEnum(Integer code, String name) {
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
    
    public static TemplateTypeEnum getEnum(String name) {
        for (TemplateTypeEnum defaultEnum : TemplateTypeEnum.values()) {
            if (defaultEnum.getName().equals(name)) {
                return defaultEnum;
            }
        }
        return null;
    }
}
