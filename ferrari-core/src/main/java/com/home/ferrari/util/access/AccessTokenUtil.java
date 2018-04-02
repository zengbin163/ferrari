/*
 *@Project: GZJK
 *@Author: 31073
 *@Date: 2015年7月28日
 *@Copyright: 2000-2015 CMCC . All rights reserved.
 */
/**
 *@Project: GZJK
 *@Author: 31073
 *@Date: 2015年7月28日
 *@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.util.access;

import java.io.IOException;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: AccessTokenUtil
 * @Description: TODO
 * @author 31073
 * @date 2015年7月28日 上午10:31:46
 */
public class AccessTokenUtil {

	private static final String authorizeAccessTokenUrl = "https://oauth.taobao.com/token";// 获取用户授权accessToken地址
	private static final String appId = "23363058";
	private static final String appSecret = "ace0425085ed6018fe03e256d2c3dccc";
	private static final String redirect_uri = "http://114.55.35.208:8080/ferrari/tmall/auth";

	private static Logger logger = LoggerFactory.getLogger(AccessTokenUtil.class);

	public static AccessToken getAuthorizeAccessToken(String code) {
		AccessToken retToken = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			httpclient = HttpClients.createDefault();
			String strUrl = authorizeAccessTokenUrl + "?client_id=" + appId + "&client_secret=" + appSecret + "&code=" + code + "&grant_type=authorization_code" + "&redirect_uri=" + redirect_uri;
			HttpPost httppost = new HttpPost(strUrl);
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String retMsg = EntityUtils.toString(entity, "UTF-8");
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					AccessToken token = JSONObject.toJavaObject(JSON.parseObject(retMsg), AccessToken.class);
					return token;
				}else {
					logger.error("Response content: " + retMsg);
				}
			}
		} catch (Exception e) {
			logger.error("------------获取access_token失败-----------------");
			logger.error(e.getMessage());
			logger.error("------------获取access_token失败-----------------");
		} finally {
			// 关闭连接,释放资源
			try {
				if (null != response) {
					response.close();
				}
				if (null != httpclient) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return retToken;
	}

	public static final void main(String []args) {
		String str = "{\"taobao_user_nick\":\"%E6%B5%99%E6%B1%9F%E6%98%9F%E5%A5%A52006\",\"re_expires_in\":0,\"expires_in\":26340883,\"expire_time\":1497256441000,\"r1_expires_in\":26340884,\"w2_valid\":1465722240871,\"w2_expires_in\":0,\"taobao_user_id\":\"2651606630\",\"w1_expires_in\":26340884,\"r1_valid\":1497256441895,\"r2_valid\":1465979640871,\"w1_valid\":1497256441895,\"r2_expires_in\":0,\"token_type\":\"Bearer\",\"refresh_token\":\"6201d08eaa391462ZZ3f628229e82520b48ac1f570831862651606630\",\"refresh_token_valid_time\":1465720440000,\"access_token\":\"6201b082977ab697ZZad83a5e2f6d5d0479788b31c33fb02651606630\"}";
		AccessToken token = JSONObject.toJavaObject(JSON.parseObject(str), AccessToken.class);
		System.out.println(token.getAccess_token());
	}
}
