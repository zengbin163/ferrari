package com.home.ferrari.dao.capacity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.vo.capacity.Capacity;

public interface CapacityDao {

	/**
	 * 查询能力模型树
	 * @param capacityId
	 * @return
	 */
	public List<Capacity> getCapacityList(@Param(value = "capacityId") Integer capacityId);

	public List<Capacity> getCapacitySelect();

	public Integer getCapacityId();

	public Integer updateCapacityAtomId(@Param(value = "atomId") Integer atomId);
	
	public Capacity getCapacityById(@Param(value = "id") Integer id);

	public Integer insertCapacity(Capacity capacity);
	
	public Integer updateCapacity(Capacity capacity);
	
	public Integer deleteCapacityByGroupId(@Param(value = "groupId") Integer groupId);
}
