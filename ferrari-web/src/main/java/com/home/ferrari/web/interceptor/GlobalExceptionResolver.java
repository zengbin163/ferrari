package com.home.ferrari.web.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.home.ferrari.base.ResultCode;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.plugin.exception.FerrariSysException;
import com.home.ferrari.plugin.exception.TeslaBizException;
import com.home.ferrari.plugin.exception.TeslaSysException;
import com.home.ferrari.util.LoggerUtil;
import com.home.ferrari.util.ResultUtil;

public class GlobalExceptionResolver implements HandlerExceptionResolver, Serializable  {
	
	private static final long serialVersionUID = 5397821535379381427L;
	private String defaultErrorView;
	
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) {
        if (exception.getClass().isAssignableFrom(IllegalArgumentException.class)) {
        	String detailMessage = exception.getMessage();
        	if(StringUtils.isEmpty(detailMessage)) {
        		detailMessage = ResultCode.ILLEGAL_ARGUMENT.getString();
        	}
            LoggerUtil.ERROR(logger, String.format("[=========IllegalArgumentException======params : %s ; message : %s] ", ResultCode.ILLEGAL_ARGUMENT , detailMessage), exception);
        	String errorMsg = JSON.toJSONString(ResultUtil.toErrorMap(ResultCode.ILLEGAL_ARGUMENT , detailMessage), SerializerFeature.WriteMapNullValue).toString();
        	this.printJson(response, errorMsg);
        	return null;
        }else if (exception.getClass().isAssignableFrom(NoSuchMethodException.class)) {
        	String detailMessage = exception.getMessage();
        	if(StringUtils.isEmpty(detailMessage)) {
        		detailMessage = ResultCode.ILLEGAL_ARGUMENT.getString();
        	}
            LoggerUtil.ERROR(logger, String.format("[=========NoSuchMethodException======params : %s ; message : %s] ", ResultCode.NOT_EXISTS , detailMessage), exception);
        	String errorMsg = JSON.toJSONString(ResultUtil.toErrorMap(ResultCode.NOT_EXISTS , detailMessage), SerializerFeature.WriteMapNullValue).toString();
        	this.printJson(response, errorMsg);
        	return null;
        }else if (exception.getClass().isAssignableFrom(FerrariSysException.class)) {
        	FerrariSysException ex = (FerrariSysException)exception;
            LoggerUtil.ERROR(logger, String.format("[=========FerrariSysException======ResultCode : %s] ", ex.getResultCode()), exception);
            String errorMsg = JSON.toJSONString(ResultUtil.toErrorMap(ex.getResultCode() , ex.getResultCode().getString()), SerializerFeature.WriteMapNullValue).toString();
        	this.printJson(response, errorMsg);
            return null;
        }else if (exception.getClass().isAssignableFrom(FerrariBizException.class)) {
        	FerrariBizException ex = (FerrariBizException)exception;
            LoggerUtil.ERROR(logger, String.format("[=========FerrariBizException======ResultCode : %s] ", ex.getResultCode()), exception);
            String errorMsg = JSON.toJSONString(ResultUtil.toErrorMap(ex.getResultCode() , ex.getResultCode().getString()), SerializerFeature.WriteMapNullValue).toString();
        	this.printJson(response, errorMsg);
            return null;
        }else if (exception.getClass().isAssignableFrom(TeslaSysException.class)) {
        	TeslaSysException ex = (TeslaSysException)exception;
            LoggerUtil.ERROR(logger, String.format("[=========TeslaSysException======ResultCode : %s] ", ex.getResultCode()), exception);
            String errorMsg = JSON.toJSONString(ResultUtil.toErrorMap(ex.getResultCode() , ex.getResultCode().getString()), SerializerFeature.WriteMapNullValue).toString();
        	this.printJson(response, errorMsg);
            return null;
        }else if (exception.getClass().isAssignableFrom(TeslaBizException.class)) {
        	TeslaBizException ex = (TeslaBizException)exception;
            LoggerUtil.ERROR(logger, String.format("[=========TeslaBizException======ResultCode : %s] ", ex.getResultCode()), exception);
            String errorMsg = JSON.toJSONString(ResultUtil.toErrorMap(ex.getResultCode() , ex.getResultCode().getString()), SerializerFeature.WriteMapNullValue).toString();
        	this.printJson(response, errorMsg);
            return null;
        } else {
            LoggerUtil.ERROR(logger, String.format("[=========SystemException======ResultCode : %s] ", ResultCode.SYSTEM_ERROR), exception);
            String errorMsg = JSON.toJSONString(ResultUtil.toErrorMap(ResultCode.SYSTEM_ERROR , ResultCode.SYSTEM_ERROR.getString()), SerializerFeature.WriteMapNullValue).toString();
        	this.printJson(response, errorMsg);
            return null;
        }
	}
	
	private void printJson(HttpServletResponse response, String jsonData) {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Charset", "UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            LoggerUtil.ERROR(logger, String.format(e.getMessage()));
        }
        out.write(jsonData);
        out.flush();
        out.close();
	}

	public String getDefaultErrorView() {
		return defaultErrorView;
	}

	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}
}
