package com.home.ferrari.dao.b2b;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.b2b.B2bBrand;
import com.home.ferrari.vo.b2b.B2bCategory;
import com.home.ferrari.vo.b2b.B2bProduct;

public interface B2bProductDao {
	public Integer insertB2bProduct(B2bProduct b2bProduct);
	public Integer insertB2bProductText(B2bProduct b2bProduct);
	public Integer updateB2bProduct(B2bProduct b2bProduct);
	public Integer updateB2bProductText(B2bProduct b2bProduct);
	public List<B2bBrand> getAllB2BBrand();
	public List<B2bCategory> getAllB2BCategory();
	public List<B2bProduct> getAllB2BProduct(
			@Param(value = "page") Page<?> page,
			@Param(value = "productName") String productName,
			@Param(value = "canShow") Integer canShow,
			@Param(value = "categoryId") Integer categoryId,
			@Param(value = "isIndex") Integer isIndex);
	public int getAllB2BProductCount(
			@Param(value = "productName") String productName,
			@Param(value = "canShow") Integer canShow,
			@Param(value = "categoryId") Integer categoryId,
			@Param(value = "isIndex") Integer isIndex);
	public B2bProduct getB2BProductDetailById(
			@Param(value = "productId") Integer productId,
			@Param(value = "canShow") Integer canShow);
	public B2bProduct getB2BProductById(Integer productId);
	
}
