package com.home.ferrari.plugin.third;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.global.PropertiesValue;
import com.home.ferrari.service.sale.TaobaoOrderService;
import com.home.ferrari.status.TaobaoOrderStatus;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;
import com.taobao.api.internal.tmc.TmcClient;

@Service
public class TmallMessageWebSocket implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(TmallMessageWebSocket.class);
	@Autowired
	private TaobaoOrderService taobaoOrderService;
	@Autowired
	private PropertiesValue propertiesValue;

	@Override
	public void afterPropertiesSet() throws Exception {
		if(DefaultEnum.NO.getCode().equals(propertiesValue.getIsProduct())) {
			logger.warn("=================测试环境不接收taobao订单request=================");
			return;
		}
		TmcClient client = new TmcClient(APP.KEY, APP.SECRET, APP.GROUP); // 关于default参考消息分组说明
		client.setMessageHandler(new MessageHandler() {
			public void onMessage(Message message, MessageStatus status) {
				try {
						String msgContent = message.getContent();
					    logger.error("==========Begin,Taobao message,topic=" + message.getTopic() + ",content=" + msgContent);
						JSONObject jsonObject = JSONObject.parseObject(msgContent);
						String taobaoStatus = jsonObject.getString("status");
						String bizOrderId = jsonObject.getString("tid");
						String sellerNick = jsonObject.getString("seller_nick");
						if(TaobaoOrderStatus.WAIT_BUYER_PAY.equals(taobaoStatus) ||
								TaobaoOrderStatus.TRADE_NO_CREATE_PAY.equals(taobaoStatus)) {
								logger.error(String.format("未付款或者未创建支付宝订单不进入系统，bizOrderId=[%s],taobaoOrderStatus=[%s]", bizOrderId, taobaoStatus));
							return;
						}
						Integer isTmall = DefaultEnum.YES.getCode();
						if("杭州星奥商家".equals(sellerNick)) {//淘宝店铺
							isTmall = DefaultEnum.NO.getCode();
						}
						Integer flag = taobaoOrderService.createOrUpdateTaobaoOrder(jsonObject.getString("tid"), taobaoStatus, isTmall);
						if (flag <= 0) {
							status.fail();
						}
				} catch (Exception e) {
					e.printStackTrace();
					/**
					 * 消息处理失败回滚，服务端需要重发 重试注意：不是所有的异常都需要系统重试。<br>
					 * 对于字段不全、主键冲突问题，导致写DB异常，不可重试，否则消息会一直重发<br>
					 * 对于，由于网络问题，权限问题导致的失败，可重试。 <br>
					 * 重试时间 5分钟不等，不要滥用，否则会引起雪崩<br>
					 **/
					status.fail();
				}
			}
		});
		client.connect(APP.WEB_SOCKET);
	}
}
