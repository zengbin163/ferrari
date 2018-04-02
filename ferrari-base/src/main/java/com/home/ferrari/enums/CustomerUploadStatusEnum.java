package com.home.ferrari.enums;

/**
* @ClassName: UploadFailStatusEnum 
* @Description: 导入失败原因
* @author zengbin
* @date 2015年11月9日
 */
public enum CustomerUploadStatusEnum {
    UPLOAD_FAIL(0,"导入失败"),
    UPLOAD_NOT_DISTRIBUTE(1,"导入成功未分配"),
    DISTRIBUTED(2,"已分配"),
    ;
    
    private CustomerUploadStatusEnum(Integer code, String name) {
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
    
    public static CustomerUploadStatusEnum getEnum(Integer code) {
        for (CustomerUploadStatusEnum prefixEnum : CustomerUploadStatusEnum.values()) {
            if (prefixEnum.getCode().equals(code)) {
                return prefixEnum;
            }
        }
        return null;
    }
}
