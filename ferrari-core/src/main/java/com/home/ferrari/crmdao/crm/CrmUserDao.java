package com.home.ferrari.crmdao.crm;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.crm.CrmLoginLog;
import com.home.ferrari.vo.crm.CrmRoleMenu;
import com.home.ferrari.vo.crm.CrmUser;
import com.home.ferrari.vo.crm.CrmUserBizType;

public interface CrmUserDao {
	
	public Integer insertCrmUser(CrmUser crmUser);
	
	public Integer insertCrmUserBizType(CrmUserBizType crmUserBizType);
	
	public List<CrmUserBizType> getCrmUserBizTypeList(@Param(value="crmUserId") Integer crmUserId);

	public Integer updateCrmUser(CrmUser crmUser);

	public Integer deleteCrmUser(@Param(value="crmUserId") Integer crmUserId);

	public Integer deleteCrmUserBizType(@Param(value="crmUserId") Integer crmUserId);

	public CrmUser getCrmUserByLoginNoAndPassword(@Param(value="loginNo") String loginNo, @Param(value="password") String password);

	public CrmUser getCrmUserByLoginNo(@Param(value="loginNo") String loginNo);

	public CrmUser getCrmUserByLoginNoAndId(@Param(value="loginNo") String loginNo, @Param(value="crmUserId") Integer crmUserId);

	public CrmUser getCrmUserByMobileAndId(@Param(value="mobile") String mobile, @Param(value="crmUserId") Integer crmUserId);

	public CrmUser getCrmUserByUserNameAndCrmShopNameAndId(
			@Param(value = "crmShopName") String crmShopName,
			@Param(value = "userName") String userName,
			@Param(value = "crmUserId") Integer crmUserId);

	public CrmUser getCrmUserById(@Param(value="crmUserId") Integer crmUserId);

	public CrmUser getCrmUserInfoById(@Param(value="crmUserId") Integer crmUserId);

	public CrmUser getCrmUserByShopName(@Param(value="shopName") String shopName);

	public List<CrmUser> getCrmUserByCrmShopName(@Param(value = "crmShopName") String crmShopName);
	
	public CrmUser getCrmUserByCrmShopNameAndUserName(
			@Param(value = "crmShopName") String crmShopName,
			@Param(value = "userName") String userName);

	public CrmUser getCrmUserByMobile(@Param(value="mobile") String mobile);

	public CrmUser getCrmUserByShopId(@Param(value="shopId") Integer shopId);

	public Integer insertCrmLoginLog(CrmLoginLog crmLoginLog);
	
	public List<CrmRoleMenu> getCrmUserMenuList(@Param(value="crmUserId") Integer crmUserId);
	public List<CrmRoleMenu> getCrmSystemMenuList();

	public List<CrmUser> getCrmUserListByRoleType(@Param(value="roleType") Integer roleType);
	public Integer countCrmUserListByRoleType(@Param(value="roleType") Integer roleType);

	public List<CrmUser> getCrmUserList(@Param(value = "page") Page<?> page,
			@Param(value = "roleType") Integer roleType,
			@Param(value = "adminId") Integer adminId,
			@Param(value = "shopIdList") List<Integer> shopIdList);
	public Integer countCrmUserList(
			@Param(value = "roleType") Integer roleType,
			@Param(value = "adminId") Integer adminId,
			@Param(value = "shopIdList") List<Integer> shopIdList);
	
	public List<CrmUser> getCrmClerkList(
			@Param(value = "page") Page<?> page,
			@Param(value = "adminId") Integer adminId,
			@Param(value = "crmShopName") String crmShopName,
			@Param(value = "isActive") Integer isActive);
	public Integer countCrmClerkList(@Param(value = "adminId") Integer adminId,
			@Param(value = "crmShopName") String crmShopName,
			@Param(value = "isActive") Integer isActive);

	public List<CrmUser> getCrmAdminList(
			@Param(value = "page") Page<?> page,
			@Param(value = "isActive") Integer isActive,
			@Param(value = "crmProvince") String crmProvince,
			@Param(value = "crmCity") String crmCity,
			@Param(value = "crmShopName") String crmShopName);
	public Integer countCrmAdminList(
			@Param(value = "isActive") Integer isActive,
			@Param(value = "crmProvince") String crmProvince,
			@Param(value = "crmCity") String crmCity,
			@Param(value = "crmShopName") String crmShopName);
	
	/**
	 * 查询业务员是否属于管理员这个公司下面
	 * @param adminId
	 * @param crmUserId
	 * @return
	 */
	public Integer validateBizAdminBelongCompany(
			@Param(value = "adminId") Integer adminId,
			@Param(value = "crmUserId") Integer crmUserId);
}
