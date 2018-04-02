package com.home.ferrari.dao.capacity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.vo.capacity.CapacityShop;

public interface CapacityShopDao {

	public Integer insertCapacityShop(CapacityShop capacityShop);

	public Integer updateCapacityShop(CapacityShop capacityShop);

	public CapacityShop getCapacityShopById(
			@Param(value = "shopId") Integer shopId,
			@Param(value = "capacityModelId") Integer capacityModelId);

	public List<CapacityShop> getCapacityShopListById(@Param(value = "shopId") Integer shopId);
}
