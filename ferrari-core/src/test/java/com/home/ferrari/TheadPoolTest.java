package com.home.ferrari;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.home.ferrari.plugin.http.HttpClientUtil;

public class TheadPoolTest {
	
	public static final void main(String []args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		for(int i=0;i<1000;i++){
			executorService.submit(new CallThreand());
		}
	}
	
}

class CallThreand implements Callable<TheadPoolTest> {

	String reqUrl = "http://139.196.239.206:8080/ferrari/capacity/getCapacityId?sessionId=bW9iaWxlPVFKbWtXRVdwcEJwbWY1ZTkzY0dHUHc9PXxwbGF0Zm9ybT1GRVJSQVJJfGlwQWRkcmVzcz0xMjcuMC4wLjE=&";
	
	@Override
	public TheadPoolTest call() throws Exception {
		HttpClientUtil.doGet(reqUrl, "");
		return null;
	}
	
}
