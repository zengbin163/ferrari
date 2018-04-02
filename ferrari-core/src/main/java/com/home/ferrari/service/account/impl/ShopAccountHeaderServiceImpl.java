package com.home.ferrari.service.account.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.home.ferrari.dao.account.AccountHeaderDao;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.service.account.ShopAccountHeaderService;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.account.AccountShop;

@Service
public class ShopAccountHeaderServiceImpl implements ShopAccountHeaderService {

	@Autowired
	private AccountHeaderDao acountHeaderDao;
	
	public Map<String, Object> getShopAccountList(Integer shopId, Integer pageOffset, Integer pageSize) {
		Assert.notNull(shopId, "shopId不能为空");
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "tt.payment_date");
		List<AccountShop> accountShopList = this.acountHeaderDao.getShopAccountList(page, shopId);
		if(CollectionUtils.isEmpty(accountShopList)) {
			return ResultUtil.successMap(ResultUtil.DATA_NOT_EXISTS);
		}
		for(AccountShop accountShop : accountShopList) {
			AccountShop tempAccShop = this.acountHeaderDao.getAccountShopByShopId(accountShop.getFinalNo(), shopId);
			accountShop.setAccountStatus(tempAccShop.getAccountStatus());
		}
		Map<String, Object> map = new HashMap<>();
		map.put("invoice", this.acountHeaderDao.getAccountInvoiceById(1));
		map.put("list", accountShopList);
		map.put("sum", this.acountHeaderDao.countShopAccountList(shopId));
		return ResultUtil.successMap(map);
	}
}
