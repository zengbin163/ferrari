package com.home.ferrari.service.capacity.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.dao.capacity.CapacityModelDao;
import com.home.ferrari.dao.capacity.CapacityShopDao;
import com.home.ferrari.dao.shop.TeslaShopDao;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.service.capacity.CapacityModelService;
import com.home.ferrari.status.MonitorStatus;
import com.home.ferrari.util.Base64Util;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.capacity.CapacityModel;
import com.home.ferrari.vo.capacity.CapacityShop;
import com.home.ferrari.vo.tesla.shop.TeslaShop;
import com.home.ferrari.vo.tesla.shop.TeslaShopBrand;
import com.home.ferrari.vo.tesla.shop.TeslaShopInfo;

@Service
public class CapacityModelServiceImpl implements CapacityModelService {

	@Autowired
	private TeslaShopDao teslaShopDao;
	@Autowired
	private CapacityModelDao capacityModelDao;
	@Autowired
	private CapacityShopDao capacityShopDao;
	
	@Override
	public Map<String, Object> createOrUpdateCapacityModel(Integer capacityModelId, String json) {
		Assert.notNull(capacityModelId, "能力模型id不能为空");
		Assert.notNull(json, "能力模型json不能为空");
		String jsonStr = Base64Util.decode(json.replaceAll(" ", "+"));
		CapacityModel cm = this.capacityModelDao.getCapacityModelByCapacityModelId(capacityModelId);
		if(null == cm) {
			CapacityModel capacityModel = new CapacityModel(capacityModelId, jsonStr, 1);
			Integer flag = this.capacityModelDao.insertCapacityModel(capacityModel);
			if(flag < 1) {
				throw new FerrariBizException(ResultCode.SAVE_FAIL, "新增能力模型失败");
			}
		}else{
			CapacityModel capacityModel = new CapacityModel(capacityModelId, jsonStr, cm.getVersion()+1);
			Integer flag = this.capacityModelDao.updateCapacityModelByCapacityModelId(capacityModel);
			if(flag < 1) {
				throw new FerrariBizException(ResultCode.UPDATE_FAIL, "更新能力模型失败");
			}
		}
		return ResultUtil.successMap(capacityModelId);
		
	}
	
	public Map<String, Object> listCapacityModel() {
		List<CapacityModel> modelList = this.capacityModelDao.getCapacityModelList();
		if(CollectionUtils.isEmpty(modelList)) {
			return ResultUtil.successMap(ResultUtil.DATA_NOT_EXISTS);
		}
		List<CapacityModel> cmList = new ArrayList<CapacityModel>();
		for(CapacityModel cm : modelList) {
			cm.setJson(Base64Util.encode(cm.getJson()));
			cmList.add(cm);
		}
		return ResultUtil.successMap(cmList);
	}
	
	@Transactional
	public Map<String, Object> createOrUpdateCapacityShop(Integer shopId, Integer capacityModelId, String searchKey, String json, Integer version) {
		Assert.notNull(shopId, "门店id不能为空");
		Assert.notNull(capacityModelId, "capacityId不能为空");
//		Assert.notNull(searchKey, "能力模型searchKey不能为空");
		Assert.notNull(json, "能力模型json不能为空");
		Assert.notNull(version, "version不能为空");
		//录入能力模型必须门店六大块完善好了才能录入
		isCanCapacity(shopId);
		CapacityShop cs = this.capacityShopDao.getCapacityShopById(shopId, capacityModelId);
		String jsonStr = Base64Util.decode(json.replaceAll(" ", "+"));
		if (null == cs) {
			CapacityShop capacityShop = new CapacityShop(shopId, capacityModelId, searchKey, jsonStr, version);
			Integer flag = this.capacityShopDao.insertCapacityShop(capacityShop);
			if(flag < 1) {
				throw new FerrariBizException(ResultCode.SAVE_FAIL, "新增门店能力模型失败");
			}
		}else{
			CapacityShop capacityShop = new CapacityShop(shopId, capacityModelId, searchKey, jsonStr, version);
			Integer flag = this.capacityShopDao.updateCapacityShop(capacityShop);
			if(flag < 1) {
				throw new FerrariBizException(ResultCode.UPDATE_FAIL, "更新门店能力模型失败");
			}
		}	
		//更新能力评估监控状态
		TeslaShop teslaShop = new TeslaShop();
		teslaShop.setId(shopId);
		teslaShop.setMonitorStatus(MonitorStatus.SHOP_FINISH);
		this.teslaShopDao.updateTeslaShop(teslaShop);
		return ResultUtil.successMap("success");
	}

	public Map<String, Object> getCapacityShopById(Integer shopId, Integer capacityModelId) {
		Assert.notNull(shopId, "门店id不能为空");
		Assert.notNull(capacityModelId, "capacityModelId不能为空");
		return ResultUtil.successMap(this.capacityShopDao.getCapacityShopById(shopId, capacityModelId));
	}
	
	public Map<String, Object> getCapacityShopListById(Integer shopId) {
		Assert.notNull(shopId, "门店id不能为空");
		List<CapacityShop> shopList = this.capacityShopDao.getCapacityShopListById(shopId);
		if(CollectionUtils.isEmpty(shopList)) {
			return ResultUtil.successMap(ResultUtil.DATA_NOT_EXISTS);
		}
		List<CapacityShop> csList = new ArrayList<CapacityShop>();
		for(CapacityShop cs : shopList) {
			cs.setJson(Base64Util.encode(cs.getJson()));
			csList.add(cs);
		}
		return ResultUtil.successMap(csList);
	}

	public boolean isCanCapacity(Integer shopId) {
		Assert.notNull(shopId, "门店id不能为空");
		TeslaShopInfo shopInfo = this.teslaShopDao.getTeslaShopInfoById(shopId);
		if(null == shopInfo) {
			throw new FerrariBizException(ResultCode.SHOP_BASE_NOTEXISTS,
					ResultCode.SHOP_BASE_NOTEXISTS.getString() + "，shopId = " + shopId);
		}
		if(null == shopInfo.getShopBase()) {
			throw new FerrariBizException(ResultCode.SHOP_BASE_NOTEXISTS,
					ResultCode.SHOP_BASE_NOTEXISTS.getString() + "，shopId = " + shopId);
		}
		if(null == shopInfo.getShopHardware()) {
			throw new FerrariBizException(ResultCode.SHOP_HARDWARD_NOTEXISTS,
					ResultCode.SHOP_HARDWARD_NOTEXISTS.getString() + "，shopId = " + shopId);
		}
		if(null == shopInfo.getShopSales()) {
			throw new FerrariBizException(ResultCode.SHOP_SALES_NOTEXISTS,
					ResultCode.SHOP_SALES_NOTEXISTS.getString() + "，shopId = " + shopId);
		}
		if(null == shopInfo.getShopTalent()) {
			throw new FerrariBizException(ResultCode.SHOP_TALENT_NOTEXISTS,
					ResultCode.SHOP_TALENT_NOTEXISTS.getString() + "，shopId = " + shopId);
		}
		if(null == shopInfo.getShopWorkshop()) {
			throw new FerrariBizException(ResultCode.SHOP_WORKSHOP_NOTEXISTS,
					ResultCode.SHOP_WORKSHOP_NOTEXISTS.getString() + "，shopId = " + shopId);
		}
		List<TeslaShopBrand> brandList =this.teslaShopDao.getTeslaShopBrandByShopId(shopId);
		if(CollectionUtils.isEmpty(brandList)) {
			throw new FerrariBizException(ResultCode.SHOP_BRAND_NOTEXISTS,
					ResultCode.SHOP_BRAND_NOTEXISTS.getString() + "，shopId = " + shopId);
		}
		return true;
	}
	
	public boolean isCapacityFinish(Integer shopId) {
		Assert.notNull(shopId, "门店id不能为空");
		List<CapacityShop> shopList = this.capacityShopDao.getCapacityShopListById(shopId);
		return CollectionUtils.isEmpty(shopList) ? false : true;
	}

}
