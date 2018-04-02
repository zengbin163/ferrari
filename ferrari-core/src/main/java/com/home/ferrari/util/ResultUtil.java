/*
 * @Project: GZJK
 * @Author: bin
 * @Date: 2015年6月23日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.home.ferrari.base.ResultCode;

/** 
* @ClassName: ResultUtils 
* @Description: 结论工具类
* @author bin
* @date 2015年6月23日 下午4:58:51 
*/
public class ResultUtil {
    
    public static final String RETURN_KEY = "recode";
    public static final String MSG = "msg";
    public static final String DATA = "data";
    public static final String LIST = "list";
    public static final String SUM = "sum";
    
    public static final String DATA_IS_EXISTS = "数据已存在";
    public static final String DATA_NOT_EXISTS = "数据不存在";
    public static final String DATA_INSERT_SUCC = "数据插入成功";
    public static final String DATA_UPDATE_SUCC = "数据更新成功";
    public static final String DATA_DOWNLOAD_SUCC = "数据下载成功";
    public static final String SEND_SUCCESS = "信息发送成功";
    public static final String VALIDATE_SUCCESS = "信息验证成功";
    
    public static Map<String, Object> toErrorMap(ResultCode resultCode, String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(RETURN_KEY, resultCode.getCode());
        if (StringUtils.isEmpty(message)) {
            map.put(MSG, resultCode.getString());
        } else {
            map.put(MSG, message);
        }
        return map;
    }
    
    
    public static Map<String, Object> successMap(Object obj) {
    	Map<String, Object> paraMap = new HashMap<String,Object>();
    	paraMap.put(RETURN_KEY, ResultCode.SUCCESS.getCode());
    	paraMap.put(DATA, obj);
    	paraMap.put(MSG, ResultCode.SUCCESS.getString());
    	return paraMap;
    }
    
}
