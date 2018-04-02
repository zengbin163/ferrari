package com.home.ferrari.dao.capacity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.vo.capacity.ShopCapacityInputVal;

public interface ShopCapacityInputValDao {

	public List<ShopCapacityInputVal> getShopCapacityInputValList(@Param(value = "shopId") Integer shopId);
	
	public Integer insertShopCapacityInputVal(ShopCapacityInputVal shopCapacityInputVal);

	public Integer deleteShopCapacityInputValByShopId(@Param(value = "shopId") Integer shopId);
}