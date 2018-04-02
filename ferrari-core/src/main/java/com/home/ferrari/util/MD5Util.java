/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.StringUtils;

/** 
* @ClassName: MD5Util 
* @Description: MD5算法工具类
* @author zengbin
* @date 2015年11月10日 上午9:48:14 
*/
public class MD5Util {
    
    public static String md5(String pass) {
    	if(StringUtils.isEmpty(pass)) {
    		return pass;
    	}
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(pass.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; ++i) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }
    
    public static final void main(String []args){
    	System.out.println(MD5Util.md5("61a99aaf550ae276db0f21b4b07f3b8a_1493881747_bd9ww44935c3f9a3376349ad1c74gg667"));
    }
}
