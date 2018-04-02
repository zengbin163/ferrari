package com.home.ferrari.plugin.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.home.ferrari.dao.shop.TeslaShopDao;
import com.home.ferrari.enums.CachePrefixEnum;
import com.home.ferrari.vo.tesla.shop.TeslaShop;

/**
 * 门店缓存服务
 * @author zengbin
 *
 */
@Service
public class TeslaShopCacheService implements InitializingBean {
	
	@Autowired
	private TeslaShopDao teslaShopDao;
	@Autowired
	private RedisService redisService;

	private static final Logger logger = LoggerFactory.getLogger(TeslaShopCacheService.class);
	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.warn("================begin initialize tesla shop information=============");
		List<TeslaShop> shopList = this.teslaShopDao.getAllShops();
		if(CollectionUtils.isEmpty(shopList)) {
			logger.warn("================tesla shop information is not configure=============");
			return;
		}else{
			logger.warn("================tesla has " + shopList.size() + " shops=============");
		}
		for(TeslaShop shop : shopList) {
			this.redisService.setObj(CachePrefixEnum.PREFIX_TESLA_SHOP_INFO_.toString() + shop.getId(), shop);
		}
	}

	public TeslaShop getShop(Integer id) {
		Object obj = this.redisService.getObj(CachePrefixEnum.PREFIX_TESLA_SHOP_INFO_.toString() + id);
		if(null == obj) {
			return this.teslaShopDao.getTeslaShopById(id);
		}
		return (TeslaShop) obj;
	}

	public TeslaShop getShopByName(String shopName) {
		if(StringUtils.isEmpty(shopName)) {
			return null;
		}
		return this.teslaShopDao.getTeslaShopByName(shopName);
	}
}
