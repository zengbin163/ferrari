package com.home.ferrari.dao.shop;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.vo.tesla.shop.Brand;

public interface BrandDao {
	public List<Brand> getAllBrandList(@Param(value = "brandName") String brandName);
}
