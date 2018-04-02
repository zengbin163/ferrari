package com.home.ferrari.service.tesla;

import java.util.List;
import java.util.Map;

import com.home.ferrari.vo.tesla.shop.TeslaShop;
import com.home.ferrari.vo.tesla.shop.TeslaShopInfo;

public interface TeslaShopService {
	
	/**
	 * 根据条件查询门店列表
	 * @param shopStatus 门店审核状态集合，通过","分割
	 * @param shopExpandStatus 门店拓展状态集合，通过","分割
	 * @param monitorStatus 门店监控状态集合，通过","分割
	 * @return
	 */
	public Map<String, Object> getTeslaShopList(Integer pageOffset,
			Integer pageSize, String shopName, String province, String city,
			String shopStatus, String shopExpandStatus, String monitorStatus,
			Integer roleType, Integer ferrariUserId, Integer shopType, Integer abcType);
	
	/**
	 * 创建门店基本信息
	 * @param shopJson
	 * @return
	 */
	public Map<String, Object> createShop(String shopJson, Integer ferrariUserId, String ferrariNickName);

	public Map<String, Object> createShopByH5(String shopJson);
	
	/**
	 * 批量创建门店基本信息
	 * @param shopJson
	 * @param ferrariUserId
	 * @param ferrariNickName
	 * @return
	 */
	public Map<String, Object> createShopBatch(List<TeslaShop> shopList, Integer ferrariUserId, String ferrariNickName);

	/**
	 * 完善门店信息
	 * @param shopJson
	 * @return
	 */
	public Map<String, Object> editShop(String shopJson, Integer ferrariUserId, String ferrariNickName);
	
	/**
	 * 门店拓展
	 * @param shopId
	 * @param shopExpandStatus
	 * @return
	 */
	public Map<String, Object> expandShop(Integer shopId, Integer shopExpandStatus, Integer ferrariUserId, String ferrariNickName);

	/**
	 * 门店审核
	 * @param shopId
	 * @param shopStatus
	 * @return
	 */
	public Map<String, Object> auditShop(Integer shopId, Integer shopStatus,
			Integer ferrariUserId, String ferrariNickName, String remark, Integer shopType);
	
	/**
	 * 门店跟踪
	 * @param shopId
	 * @param ferrariUserId
	 * @param ferrariNickName
	 * @param operateType
	 * @param shopExpandStatus
	 * @param shopStatus
	 * @param remark
	 * @return
	 */
	public Integer traceShop(Integer shopId, Integer ferrariUserId,
			String ferrariNickName, Integer operateType, Integer isRemark,
			Integer shopExpandStatus, Integer shopStatus, String remark);
	
	/**
	 * 更新门店基本信息
	 * @param teslaShop
	 * @return
	 */
	public Integer updateTeslaShop(TeslaShop teslaShop);
	
	/**
	 * 查询门店基本信息
	 * @param shopId
	 * @return
	 */
	public TeslaShop getTeslaShopById(Integer shopId);
	
	/**
	 * 查询门店详情
	 * @param shopId
	 * @return
	 */
	public TeslaShopInfo getTeslaShopInfoById(Integer shopId);
	
	/**
	 * 查询门店详细详情
	 * @param shopId
	 * @return
	 */
	public Map<String, Object> detailShop(Integer shopId);

	/**
	 * 根据门店id查询备注信息
	 * @param shopId
	 * @return
	 */
	public Map<String, Object> remarkList(Integer shopId, Integer roleType);
	
	public Map<String, Object> getAllBrandList(String brandName);
	
	public TeslaShop getTeslaShopByName(String shopName);
	
	/**
	 * 查询门店总数，主要给运营数据报表使用
	 * @param shopName
	 * @param province
	 * @param city
	 * @param shopStatusList
	 * @param shopExpandStatusList
	 * @param monitorStatusList
	 * @param roleType
	 * @param operatorId
	 * @param shopType
	 * @param abcType
	 * @return
	 */
	public Integer countTeslaShopList(String shopName, String province,
			String city, String[] shopStatusList,
			String[] shopExpandStatusList, String[] monitorStatusList,
			Integer roleType, Integer operatorId, Integer shopType,
			Integer abcType);

}
