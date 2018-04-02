package com.home.ferrari.web.site.capacity;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.capacity.CapacityModelService;
import com.home.ferrari.service.capacity.CapacityService;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/capacity")
public class CapacityAction extends BaseAction {

	private static final long serialVersionUID = 2543551211182286062L;
	@Autowired
	private CapacityModelService capacityModelService;
	@Autowired
	private CapacityService capacityService;

	@RequestMapping("create")
	@ResponseBody
	@LoginRequired(needLogin = true, needAuth=true)
	public Map<String, Object> create() {
		Integer capacityId = this.getIntParameter(request, "capacityId", null);
		String json = this.getFilteredParameter(request, "json", 0, null);
		return this.capacityModelService.createOrUpdateCapacityModel(capacityId, json);
	}

	@RequestMapping("list")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> list() {
		return this.capacityModelService.listCapacityModel();
	}

	@RequestMapping("shopQuery")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> shopQuery() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		return this.capacityModelService.getCapacityShopListById(shopId);
	}

	@RequestMapping("shopCreate")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> shopCreate() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		Integer capacityId = this.getIntParameter(request, "capacityId", null);
		Integer version = this.getIntParameter(request, "version", null);
		String searchKey = this.getFilteredParameter(request, "searchKey", 0, null);
		String json = this.getFilteredParameter(request, "json", 0, null);
		return this.capacityModelService.createOrUpdateCapacityShop(shopId, capacityId, searchKey, json, version);
	}
	
	@RequestMapping("getCapacityId")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getCapacityId() {
		return this.capacityService.getCapacityId();
	}

	@RequestMapping("createCapacityGroup")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> createCapacityGroup() {
		String jsonGroup = this.getFilteredParameter(request, "jsonGroup", 0, null);
		return this.capacityService.createCapacityGroup(jsonGroup);
	}

	@RequestMapping("createShopCapacityGroup")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> createShopCapacityGroup() {
		String jsonGroup = this.getFilteredParameter(request, "jsonGroup", 0, null);
		return this.capacityService.createShopCapacityGroup(jsonGroup);
	}

	@RequestMapping("getCapacityTree")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getCapacityTree() {
		Integer parentCapacityId = this.getIntParameter(request, "parentCapacityId", null);
		return this.capacityService.getCapacityList(parentCapacityId);
	}
	
	@RequestMapping("getShopCapacityTree")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getShopCapacityTree() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		Integer parentCapacityId = this.getIntParameter(request, "parentCapacityId", null);
		return this.capacityService.getShopCapacityList(parentCapacityId, shopId);
	}
	
	@RequestMapping("deleteCapacityGroup")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> deleteCapacityGroup() {
		Integer groupId = this.getIntParameter(request, "groupId", null);
		return this.capacityService.deleteCapacityByGroupId(groupId);
	}
	
	@RequestMapping("getCapacitySelect")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getCapacitySelect() {
		return this.capacityService.getCapacitySelect();
	}

	@RequestMapping("getCapacitySelectLeaf")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getCapacitySelectLeaf() {
		Integer capacityId = this.getIntParameter(request, "capacityId", null);
		return this.capacityService.getCapacitySelectLeaf(capacityId);
	}
}
