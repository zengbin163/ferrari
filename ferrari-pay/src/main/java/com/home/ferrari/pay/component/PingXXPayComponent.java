/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月18日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.pay.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.home.ferrari.base.Result;
import com.home.ferrari.base.ResultCode;
import com.home.ferrari.global.PropertiesValue;
import com.home.ferrari.pay.PayComponent;
import com.home.ferrari.pay.PingppData;
import com.home.ferrari.pay.vo.PayVo;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.exception.RateLimitException;
import com.pingplusplus.model.App;
import com.pingplusplus.model.Charge;

/**
 * @ClassName: PingXXPayComponent
 * @Description: pingxx支付组件
 * @author zengbin
 * @date 2015年12月07日 下午14:33:43
 */
@Component
public class PingXXPayComponent extends PayComponent {
	private static final Logger logger = LoggerFactory.getLogger(PingXXPayComponent.class);
	@Autowired
	private PropertiesValue propertiesValue;
	
	public Result pay(PayVo payVo) {
		Assert.notNull(payVo, "支付输入参数VO为空");
        Pingpp.overrideApiBase(PingppData.getApiBase());
		Pingpp.apiKey = PingppData.getApiKey();
        // 建议使用 PKCS8 编码的私钥，可以用 openssl 将 PKCS1 转成 PKCS8
		Pingpp.privateKey = PingppData.getPKCS8PrivateKey();
        Pingpp.DEBUG = true;

		Charge charge = null;
		Map<String, Object> chargeMap = new HashMap<String, Object>();
		chargeMap.put(AMOUNT, payVo.getAmount());
		chargeMap.put(CURRENCY, payVo.getCurrency());
		chargeMap.put(SUBJECT, payVo.getSubject());
		chargeMap.put(BODY, payVo.getBody());
		String alipayOrderNo = payVo.getOrderNo();
		chargeMap.put(ORDER_NO, alipayOrderNo);
		chargeMap.put(CHANNEL, payVo.getChannel());
		chargeMap.put(CLIENT_IP, payVo.getClientIp());
		Map<String, String> appMap = new HashMap<String, String>();
		appMap.put(APP_ID, payVo.getAppId());
		chargeMap.put(APP_MAP, appMap);
        // extra 取值请查看相应方法说明
        Map<String, Object> extra = new HashMap<>();
        extra.put("success_url", propertiesValue.getFileCrmDomain() + "payresult.html");
        chargeMap.put("extra", extra);
		try {
			charge = Charge.create(chargeMap); //发起交易请求
		} catch (PingppException e) {
			logger.error(String.format("Create PingXX Charge Error, [ApiKey=%s , AppId=%s]", payVo.getApiKey(), payVo.getAppId()), e);
			Result result = Result.ERROR(ResultCode.THIRD_PAY_FAIL.getCode(), e.getMessage());
			return result;
		}
		Result result = Result.CREATE(ResultCode.SUCCESS.getCode(), "第三方预支付创建成功", charge);
		return result;
	}
	
	public static final void main(String []args) {
        Pingpp.overrideApiBase(PingppData.getApiBase());
		Pingpp.apiKey = PingppData.getApiKey();
        // 建议使用 PKCS8 编码的私钥，可以用 openssl 将 PKCS1 转成 PKCS8
        Pingpp.privateKey = PingppData.getPKCS8PrivateKey();
		Pingpp.DEBUG = true;
        
        Charge charge = null;
        String channel = "alipay_pc_direct";

        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", 100);//订单总金额, 人民币单位：分（如订单总金额为 1 元，此处请填 100）
        chargeMap.put("currency", "cny");
        chargeMap.put("subject", "Your Subject");
        chargeMap.put("body", "Your Body");
        String orderNo = new Date().getTime() + "ssssssss";
        chargeMap.put("order_no", orderNo);// 推荐使用 8-20 位，要求数字或字母，不允许其他字符
        chargeMap.put("channel", channel);// 支付使用的第三方支付渠道取值，请参考：https://www.pingxx.com/api#api-c-new
        chargeMap.put("client_ip", "127.0.0.1"); // 发起支付请求客户端的 IP 地址，格式为 IPV4，如: 127.0.0.1
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", "app_aXzL08K4q18KvHuX");
        chargeMap.put("app", app);

        // extra 取值请查看相应方法说明
        Map<String, Object> extra = new HashMap<>();
        extra.put("success_url", "http://120.55.242.10/crm/third/pingxxWebhooks");
        chargeMap.put("extra", extra);

        try {
            //发起交易请求
            charge = Charge.create(chargeMap);
            // 传到客户端请先转成字符串 .toString(), 调该方法，会自动转成正确的 JSON 字符串
            String chargeString = charge.toString();
            System.out.println(chargeString);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (ChannelException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        }

	}

	public Result query(String prepayId){
		String chargeId = prepayId;
		Assert.notNull(chargeId,"chargeId不能为空");
		Pingpp.apiKey = PingppData.getApiKey();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            List<String> expande = new ArrayList<String>();
            expande.add("app");
            param.put("expand", expande);
            Charge charge = Charge.retrieve(chargeId, param);
            if (charge.getApp() instanceof App) {
            	return Result.CREATE(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getString(), charge);
            } else {
            	return Result.ERROR(ResultCode.THIRD_PAY_FAIL.getCode(), String.format("Pingxx查询charge不存在，[chargeId=%s]", chargeId));
            }
        } catch (PingppException e) {
			logger.error(String.format("Query PingXX Charge Error, [chargeId=%s]", chargeId), e);
			return Result.ERROR(ResultCode.THIRD_PAY_FAIL.getCode(), e.getMessage());
        }
	}
}
