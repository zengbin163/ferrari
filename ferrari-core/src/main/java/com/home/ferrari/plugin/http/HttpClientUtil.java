package com.home.ferrari.plugin.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * HttpClient工具类
 *
 */
public class HttpClientUtil {
	/**
	 * doGet
	 * 
	 * @param requestUrl
	 * @param paramStr
	 * @return
	 */
	public static JSONObject doGet(String requestUrl, String paramStr) {
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		String tempTitle = null;
		try {
			HttpGet httpGet = new HttpGet(requestUrl + paramStr);
			// 执行get请求
			HttpResponse httpResponse;
			httpResponse = closeableHttpClient.execute(httpGet);
			// 获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			// 判断响应实体是否为空
			if (entity != null) {
				JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(entity, "UTF-8"));
				return jsonObject;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(tempTitle);
			e.printStackTrace();
		} finally {
			try {
				// 关闭流并释放资源
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * doPost
	 * 
	 * @param object
	 * @return
	 
	public static JSONObject doPost(RequestObject object) {
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		String tempTitle = null;
		try {
			HttpPost httpPost = new HttpPost(object.toUrl());
			// 执行get请求
			HttpResponse httpResponse;
			httpResponse = closeableHttpClient.execute(httpPost);
			// 获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			// 判断响应实体是否为空
			if (entity != null) {
				JSONObject jsonObject = JSONObject.parseObject(EntityUtils
						.toString(entity, "UTF-8"));
				return jsonObject;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(tempTitle);
			e.printStackTrace();
		} finally {
			try {
				// 关闭流并释放资源
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	*/
}
