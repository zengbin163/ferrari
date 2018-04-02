package com.home.ferrari.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.vo.common.Resource;

public interface ResourceDao {
	public Integer insertOrUpdateResource(Resource resource);
	public List<Resource> getResourcesByKey(@Param(value = "resourceKey") String resourceKey);
	public List<Resource> getResourcesByKeyAndId(
			@Param(value = "resourceKey") String resourceKey,
			@Param(value = "resourceId") Integer resourceId);
	public Resource getResourcesByKeyVal(
			@Param(value = "resourceKey") String resourceKey,
			@Param(value = "resourceValue") Integer resourceValue);
	public Integer deleteResourcesByKeyVal(
			@Param(value = "resourceKey") String resourceKey,
			@Param(value = "resourceValue") Integer resourceValue);
	public Integer getResourceMaxVal(@Param(value = "resourceKey") String resourceKey);
}
