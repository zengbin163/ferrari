package com.home.ferrari.dao.sale;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.vo.sale.SaleOrderTrace;

public interface SaleOrderTraceDao {
	public Integer insertSaleOrderTrace(SaleOrderTrace saleOrderTrace);
	
	public List<SaleOrderTrace> getSaleOrderTraceListByBizOrderId(@Param(value = "bizOrderId") String bizOrderId);
}
