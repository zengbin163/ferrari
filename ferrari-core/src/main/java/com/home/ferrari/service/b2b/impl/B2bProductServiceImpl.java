package com.home.ferrari.service.b2b.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.home.ferrari.base.ResultCode;
import com.home.ferrari.dao.b2b.B2bProductDao;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.service.b2b.B2bProductService;
import com.home.ferrari.util.Base64Util;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.b2b.B2bProduct;

@Service
public class B2bProductServiceImpl implements B2bProductService {
	
	@Autowired
	private B2bProductDao b2bProductDao;
	
	@Override
	public Map<String, Object> b2bIndex(Integer pageOffset, Integer pageSize, Integer categoryId, Integer isIndex) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("topCategory", this.b2bProductDao.getAllB2BCategory());
		map.put("leftCategory", null);
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t1.modify_time");
		map.put("allProduct", this.b2bProductDao.getAllB2BProduct(page, null, DefaultEnum.YES.getCode(), categoryId, isIndex));
		map.put("allProductCount", this.b2bProductDao.getAllB2BProductCount(null, DefaultEnum.YES.getCode(), categoryId, isIndex));
		return ResultUtil.successMap(map);
	}
	
	@Override
	public Map<String, Object> productDetail(Integer productId, Integer canShow) {
		Assert.notNull(productId, "productId不能为空");
		return ResultUtil.successMap(this.b2bProductDao.getB2BProductDetailById(productId, canShow));
	}

	@Override
	@Transactional
	public Map<String, Object> publishB2BProduct(String json) {
		Assert.notNull(json, "json不能为空");
		String jsonStr = Base64Util.decode(json.replaceAll(" ", "+"));
		B2bProduct b2bProduct = JSONObject.parseObject(jsonStr,B2bProduct.class);
		Integer flag = this.b2bProductDao.insertB2bProduct(b2bProduct);
		if(flag != 1) {
			throw new FerrariBizException(ResultCode.SAVE_FAIL, "B2B商品发布失败，productName=" + b2bProduct.getProductName());
		}
		flag = this.b2bProductDao.insertB2bProductText(b2bProduct);
		if(flag != 1) {
			throw new FerrariBizException(ResultCode.SAVE_FAIL, "B2B商品文描发布失败，productName=" + b2bProduct.getProductName());
		}
		return ResultUtil.successMap(b2bProduct.getProductId());
	}
	
	@Override
	@Transactional
	public Map<String, Object> editB2BProduct(String json) {
		Assert.notNull(json, "json不能为空");
		String jsonStr = Base64Util.decode(json.replaceAll(" ", "+"));
		B2bProduct b2bProduct = JSONObject.parseObject(jsonStr,B2bProduct.class);
		this.updateB2BProduct(b2bProduct);
		if(StringUtils.isNotBlank(b2bProduct.getText())) {
			Integer flag = this.b2bProductDao.updateB2bProductText(b2bProduct);
			if(flag != 1) {
				throw new FerrariBizException(ResultCode.UPDATE_FAIL, "B2B商品文描编辑失败，productId=" + b2bProduct.getProductId());
			}
		}
		return ResultUtil.successMap(b2bProduct.getProductId());
	}

	@Async
	public Map<String, Object> reportB2BProductViewCount(Integer productId) {
		Assert.notNull(productId, "b2b产品id为空");
		B2bProduct dbProduct = this.b2bProductDao.getB2BProductById(productId);
		if(null == dbProduct) {
			throw new FerrariBizException(ResultCode.NOT_EXISTS, "B2B商品不存在，productId=" + productId);
		}
		dbProduct.setViewCount(dbProduct.getViewCount() + 1);
		this.updateB2BProduct(dbProduct);
		return null;
	}
	
	public Map<String, Object> updateB2BProduct(B2bProduct b2bProduct) {
		Assert.notNull(b2bProduct, "b2b产品实体为空");
		Assert.notNull(b2bProduct.getProductId(), "b2b产品id为空");
		B2bProduct dbProduct = this.b2bProductDao.getB2BProductById(b2bProduct.getProductId());
		if(null == dbProduct) {
			throw new FerrariBizException(ResultCode.NOT_EXISTS, "B2B商品不存在，productId=" + b2bProduct.getProductId());
		}
		b2bProduct.setVersion(dbProduct.getVersion()+1);
		Integer flag = this.b2bProductDao.updateB2bProduct(b2bProduct);
		if(flag != 1) {
			throw new FerrariBizException(ResultCode.UPDATE_FAIL, "B2B商品更新失败，productId=" + b2bProduct.getProductId());
		}
		return ResultUtil.successMap(flag);
	}
	
	public Map<String, Object> getB2bBrandList() {
		return ResultUtil.successMap(this.b2bProductDao.getAllB2BBrand());
	}
	
	public Map<String, Object> getB2bCategoryList() {
		return ResultUtil.successMap(this.b2bProductDao.getAllB2BCategory());
	}
	
	public Map<String, Object> getB2bProductList(Integer pageOffset, Integer pageSize, String productName, Integer canShow, Integer categoryId, Integer isIndex) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Map<String, Object> map = new HashMap<>();
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t1.modify_time");
		map.put("list", this.b2bProductDao.getAllB2BProduct(page, productName, canShow, categoryId, isIndex));
		map.put("count", this.b2bProductDao.getAllB2BProductCount(productName, canShow, categoryId, isIndex));
		return ResultUtil.successMap(map);
	}
}
