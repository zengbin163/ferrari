/*
 * @Project: GZJK
 * @Author: bin
 * @Date: 2015年4月21日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.base;

/** 
* @ClassName: ResultCode 
* @Description: 返回码
* @author bin
* @date 2015年4月21日 下午2:31:54 
*/
public enum ResultCode {
    
    SUCCESS(200, "响应正常"), 

    NO_LOGIN(401,"用户未登录"),
    LOGIN_ERROR(402,"账号或者密码错误，或者账号已被冻结"),
    PASSWORD_ERROR(403,"原始密码错误"),
    NOT_EXISTS(404, "数据不存在"), 
    SHOP_CAN_LISTEN(405, "只有门店系统才能开启语音提示"),
    VENTOR_CAN_DISTRIBUTE(406, "只有服务商才能进行订单分配"),
    SHOP_CAN_ACCEPT(407, "只有门店才能进行接单"),
    SHOP_NOT_CORRECT(408, "门店不正确"), 
    SHOP_NOT_EXISTS(409, "门店不存在"), 
    ORDER_NOT_EXISTS(410, "淘宝订单不存在"), 
    ORDERDETAIL_NOT_EXISTS(411, "淘宝订单明细不存在"), 
    MOBILE_IS_EXISTS(412, "手机号码已存在"), 
    NO_LOGIN_NO_COOKIE(413,"Cookie不存在，请用户登录"),
    CRM_IS_LINK(414, "门店已绑定crm账号"), 
    CRM_IS_LINK_FROZEN(415, "门店已绑定被冻结的crm账号，建议解冻crm账号"), 
    OLD_LOGIN_ERROR(416,"原账号或者密码错误"),
    NEW_LOGIN_ERROR(417,"新账号或者密码错误"),
    OLD_LINK_ERROR(418,"原账号未绑定当前账号"),
    NEW_LINK_ERROR(419, "新账号已绑定过门店"), 
    NEW_LINK_ERROR_FROZEN(420, "新账号已绑定过门店，建议解冻crm账号"), 
    SMS_NO_ERROR(421, "短信验证码错误"), 
    USERNAME_IS_EXISTS(422, "同一个公司下面的用户名已存在"), 
    SHOPID_IS_EXISTS(423, "门店id已存在"), 
    LOGINCODE_IS_EXISTS(424, "登录账号已存在"), 
    CLERK_NOT_EXISTS(425, "CRM业务员不存在"), 
    CLERK_IS_FROZEN(426, "CRM业务员被冻结"), 
    CRM_USER_IS_EXISTS(427, "CRM门店对应的管理员已存在"), 
    CRM_SYSYTEMUSER_IS_EXISTS(428, "CRM系统管理员已存在"), 
    CRMUSER_IS_FROZEN(429, "CRM账户被冻结"), 
    CRMUSER_SMS_IS_FROZEN(430, "CRM账户短信发送功能被冻结"), 

	UPDATE_FAIL(500, "更新失败"), 
    SAVE_FAIL(501, "新增失败"), 
    DELETE_FAIL(502, "删除失败"), 
    ILLEGAL_DATA(504,"非法数据"),
    ADMIN_SUPPORT(505,"只有服务商管理员才能进行此操作"),
    USER_EXIST(506,"用户已存在"),
    ILLEGAL_ARGUMENT(510,"参数错误或者缺少必要参数"), 
    SYSTEM_ERROR(555,"系统异常"), 
    
    TAOBAO_ORDER_DISTRIBUTE_ERROR(601, "淘宝订单的店铺订单状态是待分配才能做分配店铺操作"),   
    TAOBAO_ORDER_ACCEPT_ERROR(602, "淘宝订单的店铺订单状态是待接单才能接单操作"),   
    TAOBAO_ORDER_SERVICE_ERROR(603, "淘宝订单的店铺订单状态是待服务才能完成服务操作"),  
    
    ROLE_IS_EXISTS(700, "该角色名称已存在"),
    HAS_NOT_AUTHORITY(701, "用户没权限"),
    ROLE_NOT_EXISTS(702, "角色信息未配置"),
    USERROLE_IS_EXISTS(703, "该用户已经配置好角色"),
    ROLETYPE_NOT_EXISTS(704, "角色类型不存在"),
    PROVINCEPROXY_EXISTS(705, "该省下面的省代理已存在"),
    CITYPROXY_EXISTS(706, "该省市下面的市代理已存在"),
    PROXY_ERROR(707, "角色类型不是省市代理类型"),
    PROVINCEPROXY_FROZEN_EXISTS(708, "该省下面的省代理已存在，但是处于冻结状态"),
    CITYPROXY_FROZEN_EXISTS(709, "该省市下面的市代理已存在，但是处于冻结状态"),
    
    INSERT_SHOP_FAIL(801, "新增门店基本信息失败"),
    INSERT_SHOP_BRAND_FAIL(802, "新增门店品牌信息失败"),
    INSERT_SHOP_HARDWARD_FAIL(803, "新增门店车间硬件失败"),
    INSERT_SHOP_SALES_FAIL(804, "新增门店销售失败"),
    INSERT_SHOP_TALENT_FAIL(805, "新增门店技术人才失败"),
    INSERT_SHOP_WORKSHOP_FAIL(806, "新增门店车间失败"),
    SHOP_EXPAND_SUCC(807, "门店当前属于拓展已成功状态"),
    SHOP_EXPAND_FAIL(808, "门店当前属于拓展已失败状态"),
    SHOP_EXPAND_MUSTBE_SUCC(809, "门店拓展状态必须是已成功"),
    INSERT_SHOPTRACE_FAIL(810, "新增门店跟踪记录失败"),
    SHOP_AUDIT_SUCC(811, "门店审核已通过，不能重复审核"),
    SHOP_BASE_CANNOT_NULL(812, "创建门店基本信息不能为空"),
    UPDATE_SHOP_FAIL(813, "更新门店基本信息失败"),
    UPDATE_SHOP_HARDWARD_FAIL(814, "更新门店车间硬件失败"),
    UPDATE_SHOP_SALES_FAIL(815, "更新门店销售失败"),
    UPDATE_SHOP_TALENT_FAIL(816, "更新门店技术人才失败"),
    UPDATE_SHOP_WORKSHOP_FAIL(817, "更新门店车间失败"),
    SHOP_NAME_EXISTS(818, "该门店名称已存在"),
    CAPACITY_VERSION_ERROR(819, "能力模型版本号大于当前版本号"),
    SHOP_BASE_NOTEXISTS(820, "门店基本信息没配置"),
    SHOP_BRAND_NOTEXISTS(821, "门店品牌信息没配置"),
    SHOP_HARDWARD_NOTEXISTS(822, "门店车间硬件信息没配置"),
    SHOP_SALES_NOTEXISTS(823, "门店销售信息没配置"),
    SHOP_TALENT_NOTEXISTS(824, "门店技术人才信息没配置"),
    SHOP_WORKSHOP_NOTEXISTS(825, "门店车间信息没配置"),
    SHOP_CAPACITY_EXISTS(826, "该门店已经拥有此能力模型"),
    SHOP_ZIP_NOT_EXIST(827, "门店打包下载的zip不存在"),
    SHOP_BD_SUBMIT_AUDIT(828, "BD已提交门店管理员审核"),
    
    SHOP_QUEST_EXPIRE(900, "门店问卷已过期"),
    BREAK_NOT_EXISTS(901, "故障单不存在"),

    ACCOUNT_EXISTS(902, "结算单编号已存在"),
    
    BIZ_USER_NOT_EXISTS(1001, "业务员不存在"),
    BIZ_USER_BE_FROZEN(1002, "业务员被冻结"),
    LICENSE_PLATE_EXISTS(1003, "车牌号已存在"),
    PURPOSE_NOT_CHANGE(1004, "客户意向未做改变"),
    
    SMS_MEAL_NOT_EXISTS(1100, "短信套餐不存在"),
    SMS_TOTALSMS_NOT_EXISTS(1101, "星奥总库存量没配置"),
    SMS_STOCK_NOT_ENOUGH(1102, "星奥短信总库存量不足"),
    SMS_MEAL_SALE_OUT(1103, "短信套餐已下架"),
    SMS_SHOP_MEAL_NOT_BUY(1104, "门店未购买短信套餐"),
    SMS_SHOP_STOCK_NOT_ENOUGH(1105, "门店短信总库存量不足"),
    
    SIGNATURE_VERIFY_ERROR(1000, "签名验证失败"),
    THIRD_PAY_FAIL(10001, "第三方支付异常"),   
    THIRD_MERCHAT_ORDER_NOTEXISTS(10002, "商户订单不存在"),   
    PAY_SUCCESS_CANNOT_HANDER(11000, "支付成功的订单不能重复处理"),   
    THIRD_REFUND_FAIL(11001, "第三方支付退款异常"),   
    
    UPLOAD_FAIL(10003, "导入失败"),
    INVOICE_ISNULL(10004, "发票信息需要维护"),
    LOGOSTICS_CAN_CHANGE(10005, "已经过了物流信息更新点"),
    PASSPORT_PLATFORM_ERROR(10006, "平台码platform错误"),
    PASSPORT_ERROR(10007, "sessionId或者平台码platform错误"),
    PASSPORT_ROLETYPE_ERROR(10008, "星奥平台跳转crm必须是CRM系统管理员角色"),
    PASSPORT_CRM_NOT_LINK(10009, "门店未绑定crm账号，或者绑定的账号被冻结"), 
    ;

    private Integer code;
    private String string;
    
    private ResultCode(Integer code, String string) {
        this.code = code;
        this.string = string;
    }
    
    public static ResultCode getEnum(Integer code) {
        for (ResultCode retEnum : ResultCode.values()) {
            if (retEnum.getCode().equals(code)) {
                return retEnum;
            }
        }
        return null;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getString() {
        return string;
    }
    
    public void setString(String string) {
        this.string = string;
    }
    
}
