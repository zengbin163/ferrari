package com.home.ferrari.plugin.exception;

import com.home.ferrari.base.ResultCode;

/** 
* @ClassName: ApolloBizException 
* @Description: 业务异常
* @author zengbin
* @date 2015年11月10日 下午4:01:00 
*/
public class FerrariBizException extends RuntimeException {
    
    private static final long serialVersionUID = 604122701395795861L;
    private ResultCode resultCode;
    
    public FerrariBizException() {
        super();
    }
    
    public FerrariBizException(String message) {
        super(String.format("===[errorMessage ：  %s]", message));
    }
    
    public FerrariBizException(ResultCode resultCode , String message) {
    	super(String.format("===[errorCode ： %s ]===[errorMessage ：  %s]===", resultCode.getCode(), message));
    	this.resultCode = resultCode;
    }

    public FerrariBizException(ResultCode resultCode , Integer userId , String message) {
        super(String.format("===[errorCode ： %s ]===[userId ： %s]===[errorMessage ：  %s]===", resultCode.getCode(), userId , message));
        this.resultCode = resultCode;
    }
    
    public FerrariBizException(ResultCode resultCode , String mobile , String message) {
        super(String.format("===[errorCode ： %s ]===[mobile ： %s]===[errorMessage ：  %s]===", resultCode.getCode(), mobile , message));
        this.resultCode = resultCode;
    }

    public FerrariBizException(ResultCode resultCode , Integer userId , String message, Throwable cause) {
        super(String.format("===[errorCode ： %s ]===[userId ： %s]===[errorMessage ：  %s]===", resultCode.getCode() , userId , message), cause);
        this.resultCode = resultCode;
    }
    
    public FerrariBizException(ResultCode resultCode , String mobile , String message, Throwable cause) {
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
