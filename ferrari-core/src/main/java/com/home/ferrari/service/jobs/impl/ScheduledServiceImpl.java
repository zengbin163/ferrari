package com.home.ferrari.service.jobs.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.home.ferrari.dao.sale.SaleOrderDao;
import com.home.ferrari.global.PropertiesValue;
import com.home.ferrari.service.jobs.ScheduledService;
import com.home.ferrari.util.LoggerUtil;
import com.home.ferrari.vo.sale.SaleOrder;

/**
 * 定时任务服务
 * @author zengbin
 *
 */
@Service
public class ScheduledServiceImpl implements ScheduledService {
	@Autowired
	private PropertiesValue propertiesValue;
	@Autowired
	private SaleOrderDao saleOrderDao;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledServiceImpl.class);
    
	@Override
	@Scheduled(cron="0/20 * * * * ?")
	public void acceptTimeoutTask() {
		if(!isExecute()){
			return;
		}
		LoggerUtil.WARN(logger, "=============门店接单超时JOB启动============");
		List<SaleOrder> saleOrderList = this.saleOrderDao.getUnAcceptSaleOrderList(propertiesValue.getAcceptTimeout());
		if(CollectionUtils.isEmpty(saleOrderList)) {
			return;
		}
		for(SaleOrder saleOrder : saleOrderList) {
			LoggerUtil.WARN(logger, "BizOrderId=" + saleOrder.getBizOrderId() + "订单门店接单超时");
		}
		LoggerUtil.WARN(logger, "=============门店接单超时JOB结束============");
	}


	private boolean isExecute(){
		return propertiesValue.getIsExecuteMachine()==1 ? true : false;
	}
	
}
