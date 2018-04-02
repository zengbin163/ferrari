package com.home.ferrari.service.b2b;

import java.util.Map;

import com.home.ferrari.vo.b2b.B2bProduct;

public interface B2bProductService {
	
	/**
	 * B2B网站首页
	 * @return
	 */
	public Map<String, Object> b2bIndex(Integer pageOffset, Integer pageSize, Integer categoryId, Integer isIndex);

	/**
	 * B2B商品详情页
	 * @return
	 */
	public Map<String, Object> productDetail(Integer productId, Integer canShow);
	
	/**
	 * @param json
	 * 
		{
		    "productName": "B2B卖的产品名称",
		    "keyWords": "关键字1,关键字2",
		    "price": 120.19,
		    "categoryId": 1,
		    "brandId": 2,
		    "mobile": "18867102687",
		    "productModel": "产品型号",
		    "productArea": "产品产地",
		    "productColor": "产品颜色",
		    "productYear": "产品年份",
		    "pic1Addr": "首页的第一张图",
		    "pic2Addr": "",
		    "pic3Addr": "",
		    "pic4Addr": "",
		    "pic5Addr": "",
		    "text":"文描，这个地方要base64"
		}
	 * 
	 * @return
	 */
	public Map<String, Object> publishB2BProduct(String json);

	/**
	 * @param json
	 * 
		{
			"productId":4,
		    "productName": "B2B卖的产品名称",
		    "keyWords": "关键字1,关键字2",
		    "price": 120.19,
		    "categoryId": 1,
		    "brandId": 2,
		    "mobile": "18867102687",
		    "productModel": "产品型号",
		    "productArea": "产品产地",
		    "productColor": "产品颜色",
		    "productYear": "产品年份",
		    "pic1Addr": "首页的第一张图",
		    "pic2Addr": "",
		    "pic3Addr": "",
		    "pic4Addr": "",
		    "pic5Addr": "",
		    "text":"文描，这个地方要base64"
		}
	 * 
	 * @return
	 */
	public Map<String, Object> editB2BProduct(String json);
	
	/**
	 * 上报B2B商品浏览量
	 * @param productId
	 * @return
	 */
	public Map<String, Object> reportB2BProductViewCount(Integer productId);
	
	/**
	 * 更新B2B产品信息
	 * @param b2bProduct
	 * @return
	 */
	public Map<String, Object> updateB2BProduct(B2bProduct b2bProduct);
	
	/**
	 * 查询所有品牌
	 * @return
	 */
	public Map<String, Object> getB2bBrandList();
	
	/**
	 * 查询所有分类
	 * @return
	 */
	public Map<String, Object> getB2bCategoryList();
	
	/**
	 * 查询商品列表
	 * @return
	 */
	public Map<String, Object> getB2bProductList(Integer pageOffset, Integer pageSize, String productName, Integer canShow, Integer categoryId, Integer isIndex);

}
