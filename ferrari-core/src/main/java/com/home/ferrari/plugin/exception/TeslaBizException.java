package com.home.ferrari.plugin.exception;

import com.home.ferrari.base.ResultCode;

/** 
* @ClassName: TeslaBizException 
* @Description: 业务异常
* @author zengbin
* @date 2015年11月10日 下午4:01:00 
*/
public class TeslaBizException extends RuntimeException {
    
    private static final long serialVersionUID = 604122701395795861L;
    private ResultCode resultCode;
    
    public TeslaBizException() {
        super();
    }
    
    public TeslaBizException(String message) {
        super(String.format("===[errorMessage ：  %s]", message));
    }

    public TeslaBizException(ResultCode resultCode , String message) {
    	super(String.format("===[errorCode ： %s ]===[errorMessage ：  %s]===", resultCode.getCode(), message));
    	this.resultCode = resultCode;
    }
    
    public TeslaBizException(ResultCode resultCode , Integer userId , String message) {
        super(String.format("===[errorCode ： %s ]===[userId ： %s]===[errorMessage ：  %s]===", resultCode.getCode(), userId , message));
        this.resultCode = resultCode;
    }
    
    public TeslaBizException(ResultCode resultCode , String mobile , String message) {
        super(String.format("===[errorCode ： %s ]===[mobile ： %s]===[errorMessage ：  %s]===", resultCode.getCode(), mobile , message));
        this.resultCode = resultCode;
    }

    public TeslaBizException(ResultCode resultCode , Integer userId , String message, Throwable cause) {
        super(String.format("===[errorCode ： %s ]===[userId ： %s]===[errorMessage ：  %s]===", resultCode.getCode() , userId , message), cause);
        this.resultCode = resultCode;
    }
    
    public TeslaBizException(ResultCode resultCode , String mobile , String message, Throwable cause) {
        super(String.format("===[errorCode ： %s ]===[mobile ： %s]===[errorMessage ：  %s]===", resultCode.getCode() , mobile , message), cause);
        this.resultCode = resultCode;
    }
    
    public ResultCode getResultCode() {
        return resultCode;
    }
    
    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
