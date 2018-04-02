package com.home.ferrari.dao.capacity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.vo.capacity.ShopCapacityHeader;

public interface ShopCapacityHeaderDao {
	public List<ShopCapacityHeader> getShopCapacityHeaderListByGroupId(
			@Param(value = "groupId") Integer groupId,
			@Param(value = "shopId") Integer shopId);

	public Integer insertShopCapacityHeader(
			ShopCapacityHeader shopCapacityHeader);

	public Integer deleteShopCapacityHeaderByShopId(@Param(value = "shopId") Integer shopId);
}
