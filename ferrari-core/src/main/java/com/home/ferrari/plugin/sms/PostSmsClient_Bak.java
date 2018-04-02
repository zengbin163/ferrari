package com.home.ferrari.plugin.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.home.ferrari.util.MD5Util;

/**
 * 短信发送平台
 * @author zengbin
 *
 */
@Service
public class PostSmsClient_Bak {

	private static Logger logger = Logger.getLogger(PostSmsClient_Bak.class);
	private static final String URL = "http://hy.junlongtech.com:8086/getsms";
	public static final String USERNAME = "xingaoqichehy";
	public static final String PASSWORD = "xaqc123456";
	public static final String YX_USERNAME = "xingaoqicheyx";
	public static final String YX_PASSWORD = "xaqcyx123456";
	private static final String EXTEND = "1929";
	private static final String LEVEL = "1";
	private String rtCode;
	private String msgId;

	public PostSmsClient_Bak() {

	}

	@Async
	public void run(String mobiles, String content, String userName, String password) {
		try {
			HttpClient client = new HttpClient();
			PostMethod method = new PostMethod(URL);
			method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			NameValuePair[] param = {
					new NameValuePair("username", userName),
					new NameValuePair("password",MD5Util.md5(password).toUpperCase()),
					new NameValuePair("mobile", mobiles),
					new NameValuePair("content", content),
					new NameValuePair("extend", EXTEND),
					new NameValuePair("level", LEVEL)
			};
			method.setRequestBody(param);
			int statusCode = client.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK) {
				// 读取内容
				InputStream resInputStream = null;
				try {
					resInputStream = method.getResponseBodyAsStream();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// 处理内容
				BufferedReader reader = new BufferedReader(new InputStreamReader(resInputStream));
				String tempBf = null;
				StringBuffer html = new StringBuffer();
				while ((tempBf = reader.readLine()) != null) {
					html.append(tempBf);
				}
				String[] tmp = html.toString().split("&");
				rtCode = tmp[0];
				if (tmp.length == 2) {
					msgId = tmp[1];
				}
				String result = "\n提交结果:" + rtCode + "\n短信唯一标识:" + msgId;
				logger.info(result);
			} else {
				logger.error("失败，statusCode=" + statusCode);
			}
			method.releaseConnection();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) throws HttpException, IOException {
		PostSmsClient_Bak client = new PostSmsClient_Bak();
		client.run("18867102687,18867102687,18867102687", "星奥车联【星奥车联】", PostSmsClient_Bak.YX_USERNAME, PostSmsClient_Bak.YX_PASSWORD);
	}
}
