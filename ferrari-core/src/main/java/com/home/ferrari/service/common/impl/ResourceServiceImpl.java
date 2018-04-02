package com.home.ferrari.service.common.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.home.ferrari.dao.common.ResourceDao;
import com.home.ferrari.service.common.ResourceService;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.common.Resource;

@Service
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public Map<String, Object> createResource(String resourceKey, String resourceDesc, Integer resourceValue, Integer resourceId) {
		Assert.notNull(resourceKey, "resourceKey不能为空");
		Assert.notNull(resourceDesc, "resourceDesc不能为空");
		Assert.notNull(resourceId, "resourceId不能为空");
		if(null == resourceValue) {
			Integer maxValue = this.resourceDao.getResourceMaxVal(resourceKey);
			resourceValue = maxValue + 1;
		}
		Resource resource = new Resource(resourceKey, resourceDesc, resourceValue, resourceId);
		return ResultUtil.successMap(this.resourceDao.insertOrUpdateResource(resource));
	}

	@Override
	public Map<String, Object> getResourcesByKey(String resourceKey) {
		Assert.notNull(resourceKey, "resourceKey不能为空");
		return ResultUtil.successMap(this.resourceDao.getResourcesByKey(resourceKey));
	}

	@Override
	public Map<String, Object> getResourcesByKeyAndId(String resourceKey, Integer resourceId) {
		Assert.notNull(resourceKey, "resourceKey不能为空");
		Assert.notNull(resourceId, "resourceId不能为空");
		return ResultUtil.successMap(this.resourceDao.getResourcesByKeyAndId(resourceKey, resourceId));
	}

	@Override
	public Map<String, Object> getResourcesByKeyVal(String resourceKey,
			Integer resourceValue) {
		Assert.notNull(resourceKey, "resourceKey不能为空");
		Assert.notNull(resourceValue, "resourceValue不能为空");
		return ResultUtil.successMap(this.resourceDao.getResourcesByKeyVal(resourceKey, resourceValue));
	}

	@Override
	public Map<String, Object> deleteResourcesByKeyVal(String resourceKey,
			Integer resourceValue) {
		Assert.notNull(resourceKey, "resourceKey不能为空");
		Assert.notNull(resourceValue, "resourceValue不能为空");
		this.resourceDao.deleteResourcesByKeyVal(resourceKey, resourceValue);
		return ResultUtil.successMap(null);
	}
	
	@Override
	public Resource getResourceByKeyAndDesc(String resourceKey, String resourceDesc){
		Assert.notNull(resourceKey, "resourceKey不能为空");
		Assert.notNull(resourceDesc, "resourceDesc不能为空");
		List<Resource> resourceList = this.resourceDao.getResourcesByKey(resourceKey);
		if(CollectionUtils.isEmpty(resourceList)) {
			return null;
		}
		for(Resource resource : resourceList) {
			if(resourceDesc.equals(resource.getResourceDesc())) {
				return resource;
			}
		}
		return null;
	}

}
