package com.home.ferrari.dao.capacity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.vo.capacity.ShopCapacity;

public interface ShopCapacityDao {

	/**
	 * 查询门店能力模型树
	 * 
	 * @param capacityId
	 * @param shopId
	 * @return
	 */
	public List<ShopCapacity> getShopCapacityList(
			@Param(value = "capacityId") Integer capacityId,
			@Param(value = "shopId") Integer shopId);

	public ShopCapacity getShopCapacityById(
			@Param(value = "capacityId") Integer capacityId,
			@Param(value = "shopId") Integer shopId);

	public Integer insertShopCapacity(ShopCapacity shopCapacity);

	public Integer deleteShopCapacityByShopId(@Param(value = "shopId") Integer shopId);
}