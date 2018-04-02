/*
 * @Project: GZJK
 * @Author: bin
 * @Date: 2015年6月19日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.home.ferrari.base.ResultCode;
import com.home.ferrari.enums.CrmRoleTypeEnum;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.plugin.exception.FerrariSysException;
import com.home.ferrari.plugin.session.SessionContainer;
import com.home.ferrari.util.WebUtil;
import com.home.ferrari.vo.crm.CrmUser;
import com.home.ferrari.vo.ferrari.user.FerrariUser;
import com.home.ferrari.vo.tesla.user.TeslaUser;
import com.home.ferrari.web.filter.SimplePropertyExcludeFilter;

/***
 * 
* @ClassName: BaseAction 
* @Description: BaseAction，所有action的父类
* @author zengbin
* @date 2015年10月28日 下午5:02:22
 */
public abstract class BaseAction implements Serializable{
    
    private static final long serialVersionUID = 6441661476094389876L;
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    protected Object result;
    
    protected static final int DEFAULT_VALUE = 0;
    
    protected FerrariUser getFerrariUser() {
    	if(null == SessionContainer.getSession()) {
    		throw new FerrariSysException(ResultCode.NO_LOGIN.getString());
    	}
        return SessionContainer.getSession().getFerrariUser();
    }

    protected Integer getRoleType() {
    	Integer roleType = SessionContainer.getSession().getRoleType();
    	if(null == roleType) {
    		throw new FerrariBizException(ResultCode.ROLETYPE_NOT_EXISTS, ResultCode.ROLETYPE_NOT_EXISTS.getString());
    	}
    	return roleType;
    }
    
    protected TeslaUser getTeslaUser(){
    	if(null == SessionContainer.getSession()) {
    		throw new FerrariSysException(ResultCode.NO_LOGIN.getString());
    	}
    	return SessionContainer.getSession().getTeslaUser();
    }
    
    protected Integer getShopId() {
    	if(null != SessionContainer.getSession()) {
    		TeslaUser teslaUser = SessionContainer.getSession().getTeslaUser();
			if (null != teslaUser) {
				return teslaUser.getShopId();
			}
    	}
    	return null;    
    }
    
    protected CrmUser getCrmUser(){
    	if(null == SessionContainer.getSession()) {
    		throw new FerrariSysException(ResultCode.NO_LOGIN.getString());
    	}
    	return SessionContainer.getSession().getCrmUser();
    }
    
    protected Integer getCrmAdminId() {
		Integer adminId = this.getUserId();
		if (null != this.getCrmUser()
				&& CrmRoleTypeEnum.ROLE_BIZ_ADMIN.getCode().equals(this.getCrmUser().getRoleType())) {
			adminId = this.getCrmUser().getAdminId();
		}
		return adminId;
    }

	protected Integer getUserId() {
		if (null == SessionContainer.getSession()) {
			throw new FerrariSysException(ResultCode.NO_LOGIN.getString());
		}
		if (null != SessionContainer.getSession().getFerrariUser()) {
			return SessionContainer.getSession().getFerrariUser().getId();
		} else if (null != SessionContainer.getSession().getTeslaUser()) {
			return SessionContainer.getSession().getTeslaUser().getId();
		} else if (null != SessionContainer.getSession().getCrmUser()) {
			return SessionContainer.getSession().getCrmUser().getId();
		} else {
			return 0;
		}
	}
    
    protected void returnFastJSON(Object obj) {
        if (obj != null) {
            WebUtil.returnJSON(response,("{\"recode\":\"" + ResultCode.SUCCESS.getCode() + "\"," + "\"data\":" + 
            		JSON.toJSONString(obj,
									SerializerFeature.WriteMapNullValue, 		//输出空置字段
									SerializerFeature.WriteNullListAsEmpty,		//list字段如果为null，输出为[]，而不是null
									SerializerFeature.WriteNullNumberAsZero,	//数值字段如果为null，输出为0，而不是null
									SerializerFeature.WriteNullBooleanAsFalse,	//Boolean字段如果为null，输出为false，而不是null
									SerializerFeature.WriteNullStringAsEmpty)	//字符类型字段如果为null，输出为""，而不是null
									.toString() + ",\"msg\":\"" + ResultCode.SUCCESS.getString() + "\"}"), "json");
        } else {
            WebUtil.returnJSON(response,("{\"recode\":\"" + ResultCode.NOT_EXISTS.getCode() + "\"," + "\"msg\":\"" + ResultCode.NOT_EXISTS.getString() + "\"}"), "json");
        }
    }
    
	/**
	 * <pre>
	 *     需要哪些属性的对象或者集合转化为JSON（支持对象实体、集合List、Map）
	 * </pre>
	 * @param obj  单个对象/集合/Map
	 * @param clazz  对象的Class，比如HashMap.class
	 * @param properties  哪些属性是需要包括进来的，可以通过逗号分隔或者字符串数组，如："name","age"   或者    String []strs = {"name","age"}
	 */
	protected void  returnFastJsonIncludeProperties(Object obj, Class<?> clazz, String ...properties){
	       if(obj != null){
	           SimplePropertyPreFilter filter = new SimplePropertyPreFilter(clazz, properties);
	            WebUtil.returnJSON(response,("{\"recode\":\"" + ResultCode.SUCCESS.getCode() + "\"," + "\"data\":" + 
	            		JSON.toJSONString(obj, filter, 
								SerializerFeature.WriteMapNullValue,
								SerializerFeature.WriteNullListAsEmpty,
								SerializerFeature.WriteNullNumberAsZero,
								SerializerFeature.WriteNullBooleanAsFalse,
								SerializerFeature.WriteNullStringAsEmpty).toString() + ",\"msg\":\"" + ResultCode.SUCCESS.getString() + "\"}"), "json");
	        }else{
	            WebUtil.returnJSON(response,("{\"recode\":\"" + ResultCode.NOT_EXISTS.getCode() + "\"," + "\"msg\":\"" + ResultCode.NOT_EXISTS.getString() + "\"}"), "json");
	        }
	}

	/**
	 * <pre>
	 *     需要排除哪些属性的对象或者集合转化为JSON（支持对象实体、集合List、Map）
	 * </pre>
	 * @param obj  单个对象/集合/Map
	 * @param clazz  对象的Class，比如HashMap.class
	 * @param properties  哪些属性是需要排除的，可以通过逗号分隔或者字符串数组，如："name","age"   或者    String []strs = {"name","age"}
	 */
	protected void  returnFastJsonExcludeProperties(Object obj, Class<?> clazz, String ...properties){
	    if(obj != null){
	        SimplePropertyExcludeFilter filter = new SimplePropertyExcludeFilter(clazz, properties);
            WebUtil.returnJSON(response,("{\"recode\":\"" + ResultCode.SUCCESS.getCode() + "\"," + "\"data\":" +
            		JSON.toJSONString(obj, filter, 
            				SerializerFeature.WriteMapNullValue,
							SerializerFeature.WriteNullListAsEmpty,
							SerializerFeature.WriteNullNumberAsZero,
							SerializerFeature.WriteNullBooleanAsFalse,
							SerializerFeature.WriteNullStringAsEmpty).toString() + ",\"msg\":\"" + ResultCode.SUCCESS.getString() + "\"}"), "json");
	    }else{
            WebUtil.returnJSON(response,("{\"recode\":\"" + ResultCode.NOT_EXISTS.getCode() + "\"," + "\"msg\":\"" + ResultCode.NOT_EXISTS.getString() + "\"}"), "json");
	    }
	}
    
    protected String getFilteredParameter(HttpServletRequest request, String name, int maxLength, String defaultString) {
        String[] temp = request.getParameterValues(name);
        if ((temp != null) && (temp.length > 0) && (!(temp[0].equals("")))) {
			try {
				String tempString = URLDecoder.decode(temp[0], "utf-8"); //解码
	            String ret = WebUtil.escapeString(tempString);
	            if (maxLength > 0) {
	                ret = StringUtils.substring(ret, 0, maxLength);
	            }
	            return ret;
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return defaultString;
    }
    
    protected Integer getIntParameter(HttpServletRequest request, String name, Integer defaultNum) {
        String[] temp = request.getParameterValues(name);
        if ((temp != null) && (temp.length > 0) && (!(temp[0].equals("")))) {
            Integer num = defaultNum;
            try {
                num = Integer.valueOf(temp[0]);
            } catch (Exception localException) {
            }
            return num;
        }
        return defaultNum;
    }
    
    protected Long getLongParameter(HttpServletRequest request, String name, Long defaultNum) {
    	String[] temp = request.getParameterValues(name);
    	if ((temp != null) && (temp.length > 0) && (!(temp[0].equals("")))) {
    		Long num = defaultNum;
    		try {
    			num = Long.valueOf(temp[0]);
    		} catch (Exception localException) {
    		}
    		return num;
    	}
    	return defaultNum;
    }

    protected BigDecimal getDecimalParameter(HttpServletRequest request, String name, BigDecimal defaultNum) {
         String[] temp = request.getParameterValues(name);
         if ((temp != null) && (temp.length > 0) && (!(temp[0].equals("")))) {
             BigDecimal num = defaultNum;
             try {
                 num = new BigDecimal(temp[0]);
             } catch (Exception localException) {
             }
             return num;
         }
         return defaultNum;
    }
    
    public String getIpAddr() { 
        String ip = request.getHeader("x-forwarded-for"); 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        } 
        return ip; 
    }
    
	/**
	 * 获取服务器的路径(tomcat/webapp)
	 * @param request
	 * @return
	 */
	protected String getServerPath() {
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		return basePath;
	}
	
	/**
	 * 获取服务器的contextPath; 类似于 http://127.0.0.1:8080/ferrari
	 * @return
	 */
	protected String getContextPath() {
		return this.getServerPath() + "/ferrari";
	}
    
    public Object getResult() {
        return result;
    }
    
    public void setResult(Object result) {
        this.result = result;
    }

}
