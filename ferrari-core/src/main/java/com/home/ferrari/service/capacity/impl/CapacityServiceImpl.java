package com.home.ferrari.service.capacity.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.home.ferrari.base.ResultCode;
import com.home.ferrari.dao.capacity.CapacityDao;
import com.home.ferrari.dao.capacity.CapacityHeaderDao;
import com.home.ferrari.dao.capacity.ShopCapacityDao;
import com.home.ferrari.dao.capacity.ShopCapacityHeaderDao;
import com.home.ferrari.dao.capacity.ShopCapacityInputValDao;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.enums.ShopOperateEnum;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.plugin.session.SessionContainer;
import com.home.ferrari.service.capacity.CapacityService;
import com.home.ferrari.service.tesla.TeslaShopService;
import com.home.ferrari.status.MonitorStatus;
import com.home.ferrari.status.ShopExpandStatus;
import com.home.ferrari.status.ShopStatus;
import com.home.ferrari.util.Base64Util;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.capacity.Capacity;
import com.home.ferrari.vo.capacity.CapacityHeader;
import com.home.ferrari.vo.capacity.CapacityOutput;
import com.home.ferrari.vo.capacity.ShopCapacity;
import com.home.ferrari.vo.capacity.ShopCapacityHeader;
import com.home.ferrari.vo.capacity.ShopCapacityInputVal;
import com.home.ferrari.vo.capacity.ShopCapacityOutput;
import com.home.ferrari.vo.ferrari.user.FerrariUser;
import com.home.ferrari.vo.tesla.shop.TeslaShop;

@Service
public class CapacityServiceImpl implements CapacityService {
	
	@Autowired
	private CapacityDao capacityDao;
	@Autowired
	private ShopCapacityDao shopCapacityDao;
	@Autowired
	private CapacityHeaderDao capacityHeaderDao;
	@Autowired
	private ShopCapacityHeaderDao shopCapacityHeaderDao;
	@Autowired
	private ShopCapacityInputValDao shopCapacityInputValDao;
	@Autowired
	private TeslaShopService teslaShopService;

	public Map<String, Object> getCapacityId() {
	    Integer capacityId = this.capacityDao.getCapacityId();
	    capacityId = capacityId + 1;
	    Integer flag = this.capacityDao.updateCapacityAtomId(capacityId);
	    if(flag < 1) {
	    	System.out.println("查询id失败");
	    	return this.getCapacityId();
	    }else{
			return ResultUtil.successMap(capacityId);
	    }
	}

	@Override
	@Transactional
	public Map<String, Object> createCapacityGroup(String json) {
		Assert.notNull(json, "json不能为空");
		String jsonStr = Base64Util.decode(json.replaceAll(" ", "+"));
		JSONObject jsonObj = JSONObject.parseObject(jsonStr);
		Integer groupId = jsonObj.getInteger("groupId");
		Assert.notNull(groupId, "groupId不能为空");
		Integer parentNodeId = jsonObj.getInteger("parentNodeId");
		Assert.notNull(parentNodeId, "parentNodeId不能为空");
		//处理输入类型
		this.capacityHeaderDao.deleteCapacityHeaderByGroupId(groupId);
		List<CapacityHeader> headerList = JSONArray.parseArray(jsonObj.getString("headerList"),CapacityHeader.class);
		for(CapacityHeader header : headerList) {
			Integer flag = this.capacityHeaderDao.insertCapacityHeader(header);
			if(flag < 1) {
				throw new FerrariBizException(ResultCode.SAVE_FAIL,
						"新增能力header失败，groupId=" + groupId + ",parentNodeId" + parentNodeId);
			}
		}
		//处理能力模型
		this.capacityDao.deleteCapacityByGroupId(groupId);
		List<Capacity> list = JSONArray.parseArray(jsonObj.getString("capacityList"),Capacity.class);
		for (Capacity c : list) {
			Integer flag = this.capacityDao.insertCapacity(c);
			if(flag < 1) {
				throw new FerrariBizException(ResultCode.SAVE_FAIL,
						"新增能力模型失败，groupId=" + groupId + ",parentNodeId" + parentNodeId);
			}
		}
		return ResultUtil.successMap("创建成功");
	}

	@Override
	@Transactional
	public Map<String, Object> createShopCapacityGroup(String json) {
		Assert.notNull(json, "json不能为空");
		String jsonStr = Base64Util.decode(json.replaceAll(" ", "+"));
		JSONObject bigJson = JSONObject.parseObject(jsonStr);
		Integer shopId = bigJson.getInteger("shopId");
		Assert.notNull(shopId, "门店id不能为空");
		//删除header
		this.shopCapacityHeaderDao.deleteShopCapacityHeaderByShopId(shopId);
		//删除capacity
		this.shopCapacityDao.deleteShopCapacityByShopId(shopId);
		JSONArray parentNodeArray = JSONArray.parseArray(bigJson.getString("parentNodeList"));
		for (int i = 0; i < parentNodeArray.size(); i++) {
			JSONObject parentNodeJson = parentNodeArray.getJSONObject(i);
			Integer parentNodeId = parentNodeJson.getInteger("parentNodeId");
			Assert.notNull(parentNodeId, "parentNodeId不能为空");
			//插入capacityId=1 2 3
			ShopCapacity sc = new ShopCapacity(shopId, parentNodeId, 0,
					parentNodeJson.getString("name"),
					parentNodeJson.getInteger("deep"),
					parentNodeJson.getInteger("nodeCount"), null, 0);
			Integer tempFlag = this.shopCapacityDao.insertShopCapacity(sc);
			if(tempFlag < 1) {
				throw new FerrariBizException(ResultCode.SAVE_FAIL, "新增一级能力模型失败，shopId=" + shopId);
			}
			JSONArray groupArray = JSONArray.parseArray(parentNodeJson.getString("groupList"));
			for (int j = 0; j < groupArray.size(); j++) {
				JSONObject groupJson = groupArray.getJSONObject(j);
				Integer groupId = groupJson.getInteger("groupId");
				Assert.notNull(groupId, "groupId不能为空");
				//插入header
				List<ShopCapacityHeader> headerList = JSONArray.parseArray(groupJson.getString("shopHeaderList"),ShopCapacityHeader.class);
				for(ShopCapacityHeader header : headerList) {
					Integer flag = this.shopCapacityHeaderDao.insertShopCapacityHeader(header);
					if(flag < 1) {
						throw new FerrariBizException(ResultCode.SAVE_FAIL,"新增门店能力header失败，groupId=" + groupId + ",shopId=" + shopId);
					}
				}
				//插入capacity
				List<ShopCapacity> list = JSONArray.parseArray(groupJson.getString("shopCapacityList"),ShopCapacity.class);
				for (ShopCapacity c : list) {
					Integer flag = this.shopCapacityDao.insertShopCapacity(c);
					if(flag < 1) {
						throw new FerrariBizException(ResultCode.SAVE_FAIL, "新增能力模型失败，groupId=" + groupId + ",shopId=" + shopId);
					}
				}
			}
		}
		//处理用户输入的能力模型数据值
		this.shopCapacityInputValDao.deleteShopCapacityInputValByShopId(shopId);
		List<ShopCapacityInputVal> valueList = JSONArray.parseArray(bigJson.getString("inputValueList"),ShopCapacityInputVal.class);
		for(ShopCapacityInputVal shopCapacityInputVal : valueList) {
			Integer flag = this.shopCapacityInputValDao.insertShopCapacityInputVal(shopCapacityInputVal);
			if(flag < 1) {
				throw new FerrariBizException(ResultCode.SAVE_FAIL,"新增能力模型值失败,shopId=" + shopId);
			}
		}
		//查询门店信息
		TeslaShop teslaShopTemp = this.teslaShopService.getTeslaShopById(shopId);
		//修改门店为拓展成功&&更新能力评估监控状态
		TeslaShop teslaShop = new TeslaShop();
		teslaShop.setId(shopId);
		teslaShop.setShopExpandStatus(ShopExpandStatus.EXPAND_SUCC);
		if(MonitorStatus.SHOP_CAPACITY.equals(teslaShopTemp.getMonitorStatus())){
			teslaShop.setMonitorStatus(MonitorStatus.SHOP_FINISH);
		}
		this.teslaShopService.updateTeslaShop(teslaShop);
		//记录日志
		FerrariUser ferrariUser = SessionContainer.getSession().getFerrariUser();
		Integer ferrariUserId = null;
		String ferrariNickName = null;
		if(null!=ferrariUser) {
			ferrariUserId = ferrariUser.getId();
			ferrariNickName = ferrariUser.getNickName();
		}else{
			ferrariUserId = SessionContainer.getSession().getTeslaUser().getId();
			ferrariNickName = SessionContainer.getSession().getTeslaUser().getNickName();
		}
		this.teslaShopService.traceShop(shopId, ferrariUserId, ferrariNickName,
				ShopOperateEnum.CAPACITY.getCode(), DefaultEnum.NO.getCode(),
				ShopExpandStatus.EXPAND_SUCC, ShopStatus.SHOP_ADMIN, "提交能力评估");
		return ResultUtil.successMap("创建成功");
	}
	
	@Override
	public Map<String, Object> getCapacityList(Integer parentCapacityId) {
		Assert.notNull(parentCapacityId, "父级能力模型id不能为空");
		List<Capacity> capacityList = this.capacityDao.getCapacityList(parentCapacityId);
		if(CollectionUtils.isEmpty(capacityList)) {
			return ResultUtil.successMap(ResultUtil.DATA_NOT_EXISTS);
		}
		Set<Integer> groupIds = new HashSet<Integer>();
		for(Capacity capacity : capacityList) {
			if(null!=capacity.getGroupId()){
				groupIds.add(capacity.getGroupId());
			}
		}
		List<CapacityOutput> outputList = new ArrayList<CapacityOutput>();
		for(Integer groupId : groupIds) {
			Capacity capacityT = this.capacityDao.getCapacityById(groupId);
			if(null==capacityT) {
				continue;
			}
			List<Capacity> cList = new ArrayList<Capacity>();
			for(Capacity capacity : capacityList) {
				if(groupId.equals(capacity.getGroupId())) {
					cList.add(capacity);
				}
			}
			CapacityOutput cOutput = new CapacityOutput();
			cOutput.setCapacityList(cList);
			cOutput.setGroupId(groupId);
			cOutput.setParentId(capacityT.getParentId());
			cOutput.setHeaderList(this.capacityHeaderDao.getCapacityHeaderListByGroupId(groupId));
			outputList.add(cOutput);
		}
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("tables", outputList);
		return ResultUtil.successMap(output);
	}
	
	public Map<String, Object> getShopCapacityList(Integer parentCapacityId, Integer shopId) {
		Assert.notNull(parentCapacityId, "父级能力模型id不能为空");
		Assert.notNull(shopId, "门店id不能为空");
		List<ShopCapacity> capacityList = this.shopCapacityDao.getShopCapacityList(parentCapacityId, shopId);
		if(CollectionUtils.isEmpty(capacityList)) {
			return ResultUtil.successMap(ResultUtil.DATA_NOT_EXISTS);
		}
		Set<Integer> groupIds = new HashSet<Integer>();
		for(ShopCapacity capacity : capacityList) {
			if (null != capacity.getGroupId()) {
				groupIds.add(capacity.getGroupId());
			}
		}
		List<ShopCapacityOutput> outputList = new ArrayList<ShopCapacityOutput>();
		for(Integer groupId : groupIds) {
			ShopCapacity shopCapacityT = this.shopCapacityDao.getShopCapacityById(groupId, shopId);
			if(null==shopCapacityT) {
				continue;
			}
			List<ShopCapacity> cList = new ArrayList<ShopCapacity>();
			for(ShopCapacity capacity : capacityList) {
				if(groupId.equals(capacity.getGroupId())) {
					cList.add(capacity);
				}
			}
			ShopCapacityOutput cOutput = new ShopCapacityOutput();
			cOutput.setShopCapacityList(cList);
			cOutput.setGroupId(groupId);
			cOutput.setParentId(shopCapacityT.getParentCapacityId());
			cOutput.setShopHeaderList(this.shopCapacityHeaderDao.getShopCapacityHeaderListByGroupId(groupId, shopId));
			outputList.add(cOutput);
		}
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("tables", outputList);
		output.put("valus", this.shopCapacityInputValDao.getShopCapacityInputValList(shopId));
		return ResultUtil.successMap(output);
	}
	
	public Map<String, Object> deleteCapacityByGroupId(Integer groupId) {
		Assert.notNull(groupId, "groupId不能为空");
		Integer flag = this.capacityDao.deleteCapacityByGroupId(groupId);
		if(flag < 1) {
			throw new FerrariBizException(ResultCode.DELETE_FAIL, "能力模型删除失败");
		}
		return ResultUtil.successMap("删除成功");
	}
	
	public Map<String, Object> getCapacitySelect() {
		return ResultUtil.successMap(this.capacityDao.getCapacitySelect());
	}
	
	public Map<String, Object> getCapacitySelectLeaf(Integer capacityId) {
		List<Capacity> capacityList = this.capacityDao.getCapacityList(capacityId);
		if(CollectionUtils.isEmpty(capacityList)) {
			return ResultUtil.successMap(ResultUtil.DATA_NOT_EXISTS);
		}
		List<Capacity> tempCapacityList = new ArrayList<Capacity>();
		for(Capacity capacity : capacityList) {
			if(DefaultEnum.YES.getCode().equals(capacity.getIsLeaf())) {
				tempCapacityList.add(capacity);
			}
		}
		return ResultUtil.successMap(tempCapacityList);
	}
}
