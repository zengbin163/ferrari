package com.home.ferrari.dao.ferrari;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.ferrari.user.FerrariUser;

public interface FerrariUserDao{
	
	public FerrariUser getFerrariUserByMobileAndPassword(@Param(value="mobile") String mobile, @Param(value="password") String password);

	public FerrariUser getFerrariUserById(@Param(value="id") Integer id);

	public FerrariUser getFerrariUserInfoById(@Param(value="id") Integer id);

	public FerrariUser getFerrariUserByMobile(@Param(value="mobile") String mobile);

	public FerrariUser getFerrariUserByMobileAndId(@Param(value="mobile") String mobile, @Param(value="userId") Integer userId);

	public List<FerrariUser> getFerrariUserList(
			@Param(value = "page") Page<?> page,
			@Param(value = "mobile") String mobile,
			@Param(value = "nickName") String nickName,
			@Param(value = "province") String province,
			@Param(value = "city") String city,
			@Param(value = "roleType") Integer roleType,
			@Param(value = "isActive") Integer isActive);

	public Integer countFerrariUserList(
			@Param(value = "mobile") String mobile,
			@Param(value = "nickName") String nickName,
			@Param(value = "province") String province,
			@Param(value = "city") String city,
			@Param(value = "roleType") Integer roleType,
			@Param(value = "isActive") Integer isActive);
	
	public Integer insertFerrariUser(FerrariUser ferrariUser);

	public Integer updateFerrariUser(FerrariUser ferrariUser);
	
	public List<FerrariUser> getAllFerrariUserList();
	
	/**
	 * 统计省代理和市代理的数量
	 * @return
	 */
	public List<Map<String, Object>> sumProxyUserCount();
	
	/**
	 * 校验省市代是否存在
	 * @param province
	 * @param proxyType
	 * @param city
	 * @return
	 */
	public FerrariUser getFerrariUserByProvinceCity(
			@Param(value = "province") String province,
			@Param(value = "proxyType") Integer proxyType,
			@Param(value = "city") String city);

}