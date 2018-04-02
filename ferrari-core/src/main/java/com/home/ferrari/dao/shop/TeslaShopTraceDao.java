package com.home.ferrari.dao.shop;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.vo.tesla.shop.TeslaShopTrace;

public interface TeslaShopTraceDao {
	
	public Integer insertTeslaShopTrace(TeslaShopTrace teslaShopTrace);
	
	public List<TeslaShopTrace> getShopRemarkListByShopId(@Param(value = "shopId") Integer shopId, @Param(value = "roleType") Integer roleType);

}
