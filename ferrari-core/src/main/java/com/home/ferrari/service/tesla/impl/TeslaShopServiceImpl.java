package com.home.ferrari.service.tesla.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.home.ferrari.base.ResultCode;
import com.home.ferrari.dao.shop.BrandDao;
import com.home.ferrari.dao.shop.TeslaShopDao;
import com.home.ferrari.dao.shop.TeslaShopTraceDao;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.enums.RoleTypeEnum;
import com.home.ferrari.enums.ShopOperateEnum;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.plugin.session.SessionContainer;
import com.home.ferrari.service.capacity.CapacityModelService;
import com.home.ferrari.service.tesla.TeslaShopService;
import com.home.ferrari.service.tesla.TeslaUserService;
import com.home.ferrari.status.MonitorStatus;
import com.home.ferrari.status.ShopExpandStatus;
import com.home.ferrari.status.ShopStatus;
import com.home.ferrari.util.Base64Util;
import com.home.ferrari.util.MD5Util;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.tesla.shop.TeslaShop;
import com.home.ferrari.vo.tesla.shop.TeslaShopBrand;
import com.home.ferrari.vo.tesla.shop.TeslaShopHardware;
import com.home.ferrari.vo.tesla.shop.TeslaShopInfo;
import com.home.ferrari.vo.tesla.shop.TeslaShopSales;
import com.home.ferrari.vo.tesla.shop.TeslaShopTalent;
import com.home.ferrari.vo.tesla.shop.TeslaShopTrace;
import com.home.ferrari.vo.tesla.shop.TeslaShopWorkshop;
import com.home.ferrari.vo.tesla.user.TeslaUser;

@Service
public class TeslaShopServiceImpl implements TeslaShopService {
	@Autowired
	private BrandDao brandDao;
	@Autowired
	private TeslaShopDao teslaShopDao;
	@Autowired
	private TeslaShopTraceDao teslaShopTraceDao;
	@Autowired
	private TeslaUserService teslaUserService;
	@Autowired
	private CapacityModelService capacityModelService;

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public Map<String, Object> getTeslaShopList(Integer pageOffset,
			Integer pageSize, String shopName, String province, String city,
			String shopStatus, String shopExpandStatus, String monitorStatus,
			Integer roleType, Integer ferrariUserId, Integer shopType, Integer abcType) {
		String[] shopStatusList = null;
		String[] shopExpandStatusList = null;
		String[] monitorStatusList = null;
		Assert.notNull(pageOffset,"pageOffset不能为空");
		Assert.notNull(pageSize,"pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "modify_time");
		if(!StringUtils.isEmpty(shopStatus)) {
			shopStatusList = shopStatus.split(",");
		}
		if(!StringUtils.isEmpty(shopExpandStatus)) {
			shopExpandStatusList = shopExpandStatus.split(",");
		}
		if(!StringUtils.isEmpty(monitorStatus)) {
			monitorStatusList = monitorStatus.split(",");
		}
		List<TeslaShop> shopList = this.teslaShopDao.getTeslaShopList(page,
				shopName, province, city, shopStatusList, shopExpandStatusList,
				monitorStatusList, roleType, ferrariUserId, shopType, abcType);
		Integer sum = this.teslaShopDao.countTeslaShopList(shopName, province,
				city, shopStatusList, shopExpandStatusList, monitorStatusList,
				roleType, ferrariUserId, shopType, abcType);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", shopList);
		map.put("sum", sum);
		return ResultUtil.successMap(map);
	}
	
	@Override
	@Transactional
	public Map<String, Object> createShopByH5(String shopJson) {
		Assert.notNull(shopJson, "参数不能为空");
		String jsonStr = Base64Util.decode(shopJson.replaceAll(" ", "+"));
		TeslaShopInfo teslaShopInfo = JSON.parseObject(jsonStr, TeslaShopInfo.class);
		// 创建店铺基本信息
		TeslaShop teslaShop = teslaShopInfo.getShopBase();
		if (null == teslaShop || StringUtils.isEmpty(teslaShop.getShopName())) {
			throw new FerrariBizException(ResultCode.SHOP_BASE_CANNOT_NULL, "创建店铺时基本信息不能为空");
		}
		if (null != this.teslaShopDao.getTeslaShopByName(teslaShop.getShopName())) {
			throw new FerrariBizException(ResultCode.SHOP_NAME_EXISTS, "该门店名称已存在，shopName=" + teslaShop.getShopName());
		}
		teslaShop.setOperatorId(-10001);
		teslaShop.setShopStatus(ShopStatus.SHOP_ADMIN);
		teslaShop.setShopExpandStatus(ShopExpandStatus.EXPAND_SUCC);
		teslaShop.setMonitorStatus(MonitorStatus.SHOP_SUBMIT);
		Integer flag = this.teslaShopDao.insertTeslaShop(teslaShop);
		if(flag < 1) {
			throw new FerrariBizException(ResultCode.INSERT_SHOP_FAIL, "新增门店基本信息失败，shopName=" + teslaShop.getShopName());
		}
		//创建门店账号
		this.teslaUserService.createAccount(teslaShop.getId(), teslaShop.getShopName(), teslaShop.getShopName(), MD5Util.md5("111111"), null, "梦想还是要有的，万一实现了呢");
		// 新增门店跟踪记录
		this.traceShop(teslaShop.getId(), -10001, "H5微信公众号",
				ShopOperateEnum.CREATE.getCode(),DefaultEnum.NO.getCode(), ShopExpandStatus.EXPAND_SUCC,
				ShopStatus.SHOP_ADMIN, jsonStr);
		return ResultUtil.successMap(teslaShop.getId());
	}

	@Override
	@Transactional
	public Map<String, Object> createShop(String shopJson, Integer ferrariUserId, String ferrariNickName) {
		Assert.notNull(shopJson, "参数不能为空");
		String jsonStr = Base64Util.decode(shopJson.replaceAll(" ", "+"));
		TeslaShopInfo teslaShopInfo = JSON.parseObject(jsonStr, TeslaShopInfo.class);
		// 创建店铺基本信息
		TeslaShop teslaShop = teslaShopInfo.getShopBase();
		if (null == teslaShop || StringUtils.isEmpty(teslaShop.getShopName())) {
			throw new FerrariBizException(ResultCode.SHOP_BASE_CANNOT_NULL, "创建店铺时基本信息不能为空");
		}
		if (null != this.teslaShopDao.getTeslaShopByName(teslaShop.getShopName())) {
			throw new FerrariBizException(ResultCode.SHOP_NAME_EXISTS, "该门店名称已存在，shopName=" + teslaShop.getShopName());
		}
		teslaShop.setOperatorId(ferrariUserId);
		teslaShop.setShopStatus(ShopStatus.SHOP_BD);
		teslaShop.setShopExpandStatus(ShopExpandStatus.EXPAND_ING);
		teslaShop.setMonitorStatus(MonitorStatus.SHOP_BASE_BEGIN);
		Integer flag = this.teslaShopDao.insertTeslaShop(teslaShop);
		if(flag < 1) {
			throw new FerrariBizException(ResultCode.INSERT_SHOP_FAIL, "新增门店基本信息失败，shopName=" + teslaShop.getShopName());
		}
		//创建门店账号
		this.teslaUserService.createAccount(teslaShop.getId(), teslaShop.getShopName(), teslaShop.getShopName(), MD5Util.md5("111111"), null, "梦想还是要有的，万一实现了呢");
		// 新增门店跟踪记录
		this.traceShop(teslaShop.getId(), ferrariUserId, ferrariNickName,
				ShopOperateEnum.CREATE.getCode(),DefaultEnum.NO.getCode(), ShopExpandStatus.EXPAND_ING,
				ShopStatus.SHOP_BD, jsonStr);
		return ResultUtil.successMap(teslaShop.getId());
	}
	
	public Map<String, Object> createShopBatch(List<TeslaShop> shopList, Integer ferrariUserId, String ferrariNickName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (int i = 0; i < shopList.size(); i++) {
			String key = "第" + (i+1) + "条";
			resultMap.put(key, "创建成功");
			// 创建店铺基本信息
			TeslaShop teslaShop = shopList.get(i);
			if (null == teslaShop || StringUtils.isEmpty(teslaShop.getShopName())) {
				resultMap.put(key, "门店基本信息没有填写");
				continue;
			}
			if (null != this.teslaShopDao.getTeslaShopByName(teslaShop.getShopName())) {
				resultMap.put(key, "'" + teslaShop.getShopName() + "'" + "已存在");
				continue;
			}
			teslaShop.setShopStatus(ShopStatus.SHOP_BD);
			teslaShop.setShopExpandStatus(ShopExpandStatus.EXPAND_ING);
			teslaShop.setMonitorStatus(MonitorStatus.SHOP_BASE_BEGIN);
			teslaShop.setOperatorId(ferrariUserId);
			Integer flag = this.teslaShopDao.insertTeslaShop(teslaShop);
			if(flag < 1) {
				resultMap.put(key, "创建失败");
				continue;
			}
			//创建门店账号
			this.teslaUserService.createAccount(teslaShop.getId(), teslaShop.getShopName(), teslaShop.getShopName(), MD5Util.md5("111111"), null, "梦想还是要有的，万一实现了呢");
			// 新增门店跟踪记录
			this.traceShop(teslaShop.getId(), ferrariUserId, ferrariNickName,
					ShopOperateEnum.CREATE.getCode(),DefaultEnum.NO.getCode(), ShopExpandStatus.EXPAND_ING,
					ShopStatus.SHOP_BD, JSON.toJSONString(teslaShop));
		}
		return ResultUtil.successMap(resultMap);
	}
	
	@Transactional
	public Map<String, Object> editShop(String shopJson, Integer ferrariUserId, String ferrariNickName) {
		Assert.notNull(shopJson, "门店信息不能为空");
		String jsonStr = Base64Util.decode(shopJson.replaceAll(" ", "+"));
		TeslaShopInfo teslaShopInfo = JSON.parseObject(jsonStr, TeslaShopInfo.class);
		TeslaShop teslaShop = teslaShopInfo.getShopBase();
		Assert.notNull(teslaShop.getId(), "shopBase的门店id不能为空");
		TeslaShop teslaShopTemp = this.teslaShopDao.getTeslaShopById(teslaShop.getId());
//		Integer roleType = SessionContainer.getSession().getRoleType();
//		if(RoleTypeEnum.ROLE_BD.getCode().equals(roleType) && ShopStatus.SHOP_RETURN.equals(teslaShopTemp.getShopStatus())) {
//			throw new FerrariBizException(ResultCode.SHOP_BD_SUBMIT_AUDIT, ResultCode.SHOP_BD_SUBMIT_AUDIT.getString() + ",shopId=" + teslaShop.getId());
//		}
		Integer flag = 0;
		//创建或更新店铺车辆品牌
		TeslaShopBrand teslaShopBrand = teslaShopInfo.getShopBrand();
		if(!StringUtils.isEmpty(teslaShopBrand.getCarBrands())) {
			this.teslaShopDao.deleteTeslaShopBrandByShopId(teslaShopBrand.getShopId());
			String[] carBrandList = teslaShopBrand.getCarBrands().split(",");//通过","分割
			for (Integer i = 0; i < carBrandList.length; i++) {
				flag = this.teslaShopDao.insertTeslaShopBrand(TeslaShopBrand.create(teslaShopBrand.getShopId(), carBrandList[i]));
				if(flag < 1) {
					throw new FerrariBizException(ResultCode.INSERT_SHOP_BRAND_FAIL, "新增门店车辆品牌失败，shopId=" + teslaShopBrand.getShopId());
				}
			}
		}
		//创建或更新门店车间硬件
		TeslaShopHardware teslaShopHardware = teslaShopInfo.getShopHardware();
		if (null != teslaShopHardware && null != teslaShopHardware.getShopId()) {
			if(this.teslaShopDao.countTeslaShopHardwareByShopId(teslaShopHardware.getShopId()) == 0) {
				flag = this.teslaShopDao.insertTeslaShopHardware(teslaShopHardware);
				if(flag < 1) {
					throw new FerrariBizException(ResultCode.INSERT_SHOP_HARDWARD_FAIL, "新增门店车间硬件失败，shopId=" + teslaShopHardware.getShopId());
				}
			}else{
				flag = this.teslaShopDao.updateTeslaShopHardware(teslaShopHardware);
				if(flag < 1) {
					throw new FerrariBizException(ResultCode.UPDATE_SHOP_HARDWARD_FAIL, "更新门店车间硬件失败，shopId=" + teslaShopHardware.getShopId());
				}
			}
		}
	    //创建或更新门店销售
		TeslaShopSales teslaShopSales = teslaShopInfo.getShopSales();
		if (null != teslaShopSales && null != teslaShopSales.getShopId()) {
			if(this.teslaShopDao.countTeslaShopSalesByShopId(teslaShopSales.getShopId()) == 0) {
				flag = this.teslaShopDao.insertTeslaShopSales(teslaShopSales);
				if(flag < 1) {
					throw new FerrariBizException(ResultCode.INSERT_SHOP_SALES_FAIL, "新增门店销售失败，shopId=" + teslaShopSales.getShopId());
				}
			}else{
				flag = this.teslaShopDao.updateTeslaShopSales(teslaShopSales);
				if(flag < 1) {
					throw new FerrariBizException(ResultCode.UPDATE_SHOP_SALES_FAIL, "更新门店销售失败，shopId=" + teslaShopSales.getShopId());
				}
			}
		}	
		//创建或更新门店技术人才
		TeslaShopTalent teslaShopTalent = teslaShopInfo.getShopTalent();
		if (null != teslaShopTalent && null != teslaShopTalent.getShopId()) {
			if(this.teslaShopDao.countTeslaShopTalentByShopId(teslaShopTalent.getShopId()) == 0) {
				flag = this.teslaShopDao.insertTeslaShopTalent(teslaShopTalent);
				if(flag < 1) {
					throw new FerrariBizException(ResultCode.INSERT_SHOP_TALENT_FAIL, "新增门店技术人才失败，shopId=" + teslaShopTalent.getShopId());
				}
			}else{
				flag = this.teslaShopDao.updateTeslaShopTalent(teslaShopTalent);
				if(flag < 1) {
					throw new FerrariBizException(ResultCode.UPDATE_SHOP_TALENT_FAIL, "更新门店技术人才失败，shopId=" + teslaShopTalent.getShopId());
				}
			}
		}
		//新增门店车间
		TeslaShopWorkshop teslaShopWorkshop = teslaShopInfo.getShopWorkshop();
		if (null != teslaShopWorkshop && null != teslaShopWorkshop.getShopId()) {
			if(this.teslaShopDao.countTeslaShopWorkshopByShopId(teslaShopWorkshop.getShopId()) == 0) {
				flag = this.teslaShopDao.insertTeslaShopWorkshop(teslaShopWorkshop);
				if(flag < 1) {
					throw new FerrariBizException(ResultCode.INSERT_SHOP_WORKSHOP_FAIL, "新增门店车间失败，shopId=" + teslaShopWorkshop.getShopId());
				}
			}else{
				flag = this.teslaShopDao.updateTeslaShopWorkshop(teslaShopWorkshop);
				if(flag < 1) {
					throw new FerrariBizException(ResultCode.UPDATE_SHOP_WORKSHOP_FAIL, "更新门店车间失败，shopId=" + teslaShopWorkshop.getShopId());
				}
			}
		}
		//更新门店基本信息
		teslaShop = teslaShopInfo.getShopBase();
		if(MonitorStatus.SHOP_BASE_BEGIN.equals(teslaShopTemp.getMonitorStatus()) && capacityModelService.isCanCapacity(teslaShop.getId())){
			teslaShop.setMonitorStatus(MonitorStatus.SHOP_CAPACITY);
		}
		flag = this.teslaShopDao.updateTeslaShop(teslaShop);
		if(flag < 1) {
			throw new FerrariBizException(ResultCode.UPDATE_SHOP_FAIL, "更新门店基本信息失败，shopId=" + teslaShop.getId());
		}
		//新增门店跟踪记录
		this.traceShop(teslaShop.getId(), ferrariUserId, ferrariNickName,
				ShopOperateEnum.EDIT.getCode(),DefaultEnum.NO.getCode(),
				ShopExpandStatus.EXPAND_ING,teslaShopTemp.getShopStatus(),
				jsonStr);
		return ResultUtil.successMap(teslaShop.getId());
	}

	@Transactional
	public Map<String, Object> expandShop(Integer shopId, Integer shopExpandStatus, Integer ferrariUserId, String ferrariNickName) {
		TeslaShop queryShop = this.teslaShopDao.getTeslaShopById(shopId);
		if(null==queryShop) {
			throw new FerrariBizException(ResultCode.SHOP_NOT_EXISTS, "门店不存在，shopId=" + shopId);
		}
		if(ShopExpandStatus.EXPAND_SUCC.equals(queryShop.getShopExpandStatus())) {
			logger.error("门店当前属于拓展已成功状态，shopId=" + shopId);
			return ResultUtil.toErrorMap(ResultCode.SHOP_EXPAND_SUCC, "门店当前属于拓展已成功状态，shopId=" + shopId);
		}
		if(ShopExpandStatus.EXPAND_FAIL.equals(queryShop.getShopExpandStatus())) {
			throw new FerrariBizException(ResultCode.SHOP_EXPAND_FAIL, "门店当前属于拓展已失败状态，shopId=" + shopId);
		}
		TeslaShop teslaShop = new TeslaShop();
		teslaShop.setId(shopId);
		teslaShop.setShopExpandStatus(shopExpandStatus);
//		Integer shopStatus = queryShop.getShopStatus();
//		if(ShopExpandStatus.EXPAND_SUCC.equals(shopExpandStatus)) {
//			shopStatus = ShopStatus.SHOP_ADMIN;
//			teslaShop.setShopStatus(shopStatus);
//		}
		this.updateTeslaShop(teslaShop);
		//新增门店跟踪记录
		this.traceShop(shopId, ferrariUserId, ferrariNickName,
				ShopOperateEnum.EXPAND.getCode(), DefaultEnum.NO.getCode(),
				shopExpandStatus, queryShop.getShopStatus(), null);
		return ResultUtil.successMap("success");
	}

	@Transactional
	public Map<String, Object> auditShop(Integer shopId, Integer shopStatus, Integer ferrariUserId, String ferrariNickName, String remark, Integer shopType) {
		TeslaShop queryShop = this.teslaShopDao.getTeslaShopById(shopId);
		if(null==queryShop) {
			throw new FerrariBizException(ResultCode.SHOP_NOT_EXISTS, "门店不存在，shopId=" + shopId);
		}
		Integer isRemark = DefaultEnum.NO.getCode();
		if(!ShopStatus.SHOP_FROZEN.equals(shopStatus)){
			if(!ShopExpandStatus.EXPAND_SUCC.equals(queryShop.getShopExpandStatus())) {
				throw new FerrariBizException(ResultCode.SHOP_EXPAND_MUSTBE_SUCC, "只有门店拓展状态是已成功才能进入服务商门店审核，shopId=" + shopId);
			}
			if(ShopStatus.SHOP_SUCCESS.equals(queryShop.getShopStatus())) {
				throw new FerrariBizException(ResultCode.SHOP_AUDIT_SUCC, "门店审核已通过，不能重复审核，shopId=" + shopId);
			}
		}else{
			isRemark = DefaultEnum.YES.getCode();
			//冻结门店账号
			TeslaUser teslaUser = new TeslaUser();
			teslaUser.setShopId(shopId);
			teslaUser.setIsActive(DefaultEnum.NO.getCode());
			this.teslaUserService.updateTeslaUserByShopId(teslaUser);
		}
		//解冻门店账号
		if(ShopStatus.SHOP_SUCCESS.equals(shopStatus)){
			if(ShopStatus.SHOP_FROZEN.equals(queryShop.getShopStatus())) {
				isRemark = DefaultEnum.YES.getCode();
				TeslaUser teslaUser = new TeslaUser();
				teslaUser.setShopId(shopId);
				teslaUser.setIsActive(DefaultEnum.YES.getCode());
				this.teslaUserService.updateTeslaUserByShopId(teslaUser);
			}
		}
		TeslaShop teslaShop = new TeslaShop();
		teslaShop.setId(shopId);
		teslaShop.setShopStatus(shopStatus);
		if(ShopStatus.SHOP_ADMIN.equals(shopStatus)) {
			teslaShop.setMonitorStatus(MonitorStatus.SHOP_SUBMIT);//能力评估完成并提交门店管理员审核
		}
		Integer operateType = ShopOperateEnum.AUDIT.getCode();
		if(ShopStatus.SHOP_RETURN.equals(shopStatus)) {
			teslaShop.setMonitorStatus(MonitorStatus.SHOP_BACK);//能力评估完成并提交了审核，但是被打回了
			isRemark = DefaultEnum.YES.getCode();
			operateType = ShopOperateEnum.RETURN.getCode();
		}
		if(ShopStatus.SHOP_SUCCESS.equals(shopStatus)){
			teslaShop.setMonitorStatus(MonitorStatus.SHOP_AUDIT_FINISH);
			Assert.notNull(shopType, "门店管理员复核阶段，门店类型不能为空");
			teslaShop.setShopType(shopType.toString());
		}
//		if(ShopStatus.SHOP_ADMIN_RE.equals(shopStatus)){
//			Assert.notNull(shopType, "门店管理员复核阶段，门店类型不能为空");
//			teslaShop.setShopType(shopType.toString());
//		}
		this.updateTeslaShop(teslaShop);
		// 新增门店跟踪记录
		this.traceShop(shopId, ferrariUserId, ferrariNickName,
				operateType, isRemark,
				queryShop.getShopExpandStatus(), shopStatus, remark);
		return ResultUtil.successMap("success");
	}

	public Integer traceShop(Integer shopId, Integer ferrariUserId,
			String ferrariNickName, Integer operateType, Integer isRemark,
			Integer shopExpandStatus, Integer shopStatus, String remark) {
		Assert.notNull(shopId, "门店id不能为空");
		Assert.notNull(ferrariUserId, "服务商操作用户Id不能为空");
		Assert.notNull(shopExpandStatus, "门店拓展状态不能为空");
		Assert.notNull(shopStatus, "门店审批状态不能为空");
		Integer roleType = RoleTypeEnum.ROLE_PROVINCE.getCode();
		if (null != SessionContainer.getSession()) {
			roleType = SessionContainer.getSession().getRoleType();
		}
		TeslaShopTrace teslaShopTrace = new TeslaShopTrace(shopId,
				ferrariUserId, ferrariNickName, operateType, roleType,
				isRemark, shopExpandStatus, shopStatus, remark);
		Integer flag = this.teslaShopTraceDao.insertTeslaShopTrace(teslaShopTrace);
		if(flag < 1) {
			throw new FerrariBizException(ResultCode.INSERT_SHOPTRACE_FAIL, "新增门店跟踪记录失败，shopId=" + shopId);
		}
		return flag;
	}
	
	public Integer updateTeslaShop(TeslaShop teslaShop) {
		Assert.notNull(teslaShop, "teslaShop对象不能为空");
		Integer flag = this.teslaShopDao.updateTeslaShop(teslaShop);
		if(flag < 1) {
			throw new FerrariBizException(ResultCode.UPDATE_FAIL, "更新门店基本信息失败，shopId=" + teslaShop.getId());
		}	
		return flag;
	}
	
	public TeslaShop getTeslaShopById(Integer shopId) {
		Assert.notNull(shopId, "门店id不能为空");
		return this.teslaShopDao.getTeslaShopById(shopId);
	}

	public TeslaShopInfo getTeslaShopInfoById(Integer shopId) {
		Assert.notNull(shopId, "门店id不能为空");
		return this.teslaShopDao.getTeslaShopInfoById(shopId);
	}
	
	public Map<String, Object> detailShop(Integer shopId) {
		Assert.notNull(shopId, "门店id不能为空");
		TeslaShopInfo teslaShopInfo = this.teslaShopDao.getTeslaShopInfoById(shopId);
		teslaShopInfo.setShopBrandList(this.teslaShopDao.getTeslaShopBrandByShopId(shopId));
		teslaShopInfo.setShopTraceList(this.teslaShopTraceDao.getShopRemarkListByShopId(shopId, null));
		return ResultUtil.successMap(teslaShopInfo);
	}
	
	public Map<String, Object> remarkList(Integer shopId, Integer roleType) {
		Assert.notNull(shopId, "门店id不能为空");
		return ResultUtil.successMap(this.teslaShopTraceDao.getShopRemarkListByShopId(shopId, roleType));
	}
	
	public Map<String, Object> getAllBrandList(String brandName) {
		return ResultUtil.successMap(this.brandDao.getAllBrandList(brandName));
	}
	
	public TeslaShop getTeslaShopByName(String shopName) {
		return this.teslaShopDao.getTeslaShopByName(shopName);
	}
	
	public Integer countTeslaShopList(String shopName, String province,
			String city, String[] shopStatusList,
			String[] shopExpandStatusList, String[] monitorStatusList,
			Integer roleType, Integer operatorId, Integer shopType,
			Integer abcType) {
		return this.teslaShopDao.countTeslaShopList(shopName, province, city,
				shopStatusList, shopExpandStatusList, monitorStatusList,
				roleType, operatorId, shopType, abcType);
	}
}
