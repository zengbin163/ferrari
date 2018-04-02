package com.home.ferrari.web.site.common;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.common.ResourceService;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/resource")
public class ResourceAction extends BaseAction {

	private static final long serialVersionUID = 8848288539621768628L;
	@Autowired
	private ResourceService resourceService;

	@RequestMapping("saveOrUpdateResource")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> saveOrUpdateResource() {
		String resourceKey = this.getFilteredParameter(request, "resourceKey", 0, null);
		String resourceDesc = this.getFilteredParameter(request, "resourceDesc", 0, null);
		Integer resourceValue = this.getIntParameter(request, "resourceValue", null);
		return this.resourceService.createResource(resourceKey, resourceDesc, resourceValue, this.getUserId());
	}
	
	@RequestMapping("resourceList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> resourceList() {
		String resourceKey = this.getFilteredParameter(request, "resourceKey", 0, null);
		return this.resourceService.getResourcesByKeyAndId(resourceKey, this.getCrmAdminId());
	}
	
	@RequestMapping("getResource")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getResource() {
		String resourceKey = this.getFilteredParameter(request, "resourceKey", 0, null);
		Integer resourceValue = this.getIntParameter(request, "resourceValue", null);
		return this.resourceService.getResourcesByKeyVal(resourceKey, resourceValue);
	}

	@RequestMapping("deleteResource")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> deleteResource() {
		String resourceKey = this.getFilteredParameter(request, "resourceKey", 0, null);
		Integer resourceValue = this.getIntParameter(request, "resourceValue", null);
		return this.resourceService.deleteResourcesByKeyVal(resourceKey, resourceValue);
	}
}
