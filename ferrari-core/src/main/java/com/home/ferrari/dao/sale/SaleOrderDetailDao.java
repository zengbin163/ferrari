package com.home.ferrari.dao.sale;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.vo.sale.SaleOrderDetail;

public interface SaleOrderDetailDao {

	public Integer insertSaleOrderDetail(SaleOrderDetail saleOrderDetail);

	public Integer updateSaleOrderDetail(SaleOrderDetail saleOrderDetail);
	
	public List<SaleOrderDetail> getSaleOrderDetailListByBizOrderId(@Param(value = "bizOrderId") String bizOrderId);

	public List<SaleOrderDetail> getSaleOrderDetailListByProductName(@Param(value = "productName")String productName);
	
	public SaleOrderDetail getSaleOrderDetailByBizDetailId(@Param(value = "bizDetailId") String bizDetailId);

}
