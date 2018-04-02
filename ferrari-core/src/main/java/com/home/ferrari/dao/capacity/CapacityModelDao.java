package com.home.ferrari.dao.capacity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.vo.capacity.CapacityModel;

public interface CapacityModelDao {

	public Integer insertCapacityModel(CapacityModel capacityModel);

	public Integer updateCapacityModelByCapacityModelId(CapacityModel capacityModel);

	public CapacityModel getCapacityModelByCapacityModelId(@Param(value = "capacityModelId") Integer capacityModelId);
	
	public List<CapacityModel> getCapacityModelList();
}
