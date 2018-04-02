package com.home.ferrari.dao.capacity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.vo.capacity.CapacityHeader;


public interface CapacityHeaderDao {
	public List<CapacityHeader> getCapacityHeaderListByGroupId(@Param(value = "groupId") Integer groupId);
	public Integer insertCapacityHeader(CapacityHeader capacityHeader);
	public Integer deleteCapacityHeaderByGroupId(@Param(value = "groupId") Integer groupId);
}
