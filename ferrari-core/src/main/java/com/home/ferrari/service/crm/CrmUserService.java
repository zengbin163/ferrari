package com.home.ferrari.service.crm;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.home.ferrari.vo.crm.CrmUser;
import com.home.ferrari.vo.crm.CrmUserBizType;

public interface CrmUserService {
	
	public static final Integer FERRARI2CRM = 0;
	public static final Integer TESLA2CRM = 1;
	public static final Integer CRM2FERRARI = 2;
	public static final Integer CRM2TESLA = 3;
	
	/**
	 * 免登passport
	 * @param sessionId
	 * @param platform
	 * @return
	 */
	public Map<String, Object> passport(String sessionId, Integer platform, String ipAddress, HttpServletResponse response);
	
	/**
	 * 校验门店账号是否合法
	 * @param shopName
	 * @param password
	 * @return
	 */
	public Map<String, Object> validateTeslaUser(String shopName, String password);

	/**
	 * 新增管理员或者业务员
	 * @param crmUser
	 * @return
	 */
	public Integer saveCrmUser(CrmUser crmUser);

	public Integer saveCrmUserBizType(CrmUserBizType crmUserBizType);

	/**
	 * 修改管理员或业务员信息
	 * @param crmUser
	 * @return
	 */
	public Map<String, Object> updateCrmUser(CrmUser crmUser);
	
	/**
	 * 修改管理员或业务员
	 * @param crmUser
	 * @return
	 */
	public Map<String, Object> editCrmUser(CrmUser crmUser, String oldPassword, String newPassword);

	/**
	 * 管理员或业务员登录
	 * @param loginNo
	 * @param password
	 * @param ipAddress
	 * @param response
	 * @return
	 */
	public Map<String, Object> login(String loginNo, String password, String ipAddress, HttpServletResponse response);

	/**
	 * 业务员链接门店
	 * @param shopName
	 * @param shopPassword
	 * @param crmUserId
	 * @return
	 */
	public Map<String, Object> linkShop(String shopName, String shopPassword, Integer crmUserId);

	/**
	 * 业务员重链门店
	 * @param newShopName
	 * @param newShopPassword
	 * @param oldShopName
	 * @param oldShopPassword
	 * @param crmUserId
	 * @return
	 */
	public Map<String, Object> reLinkShop(String newShopName,
			String newShopPassword, String oldShopName,
			String oldShopPassword, Integer crmUserId);
	
	/**
	 * 冻结或者激活业务员 
	 * @param crmUserId
	 * @param isActive
	 * @return
	 */
	public Map<String, Object> frozenOrActive(Integer crmUserId, Integer isActive);

	/**
	 * 开启或者关闭发送短信 
	 * @param crmUserId
	 * @param canSms
	 * @return
	 */
	public Map<String, Object> openOrCloseSms(Integer crmUserId, Integer canSms);
	
	/**
	 * 删除管理员或者业务员
	 * @param crmUserId
	 * @return
	 */
	public Map<String, Object> deleteCrmUser(Integer crmUserId);
	
	public CrmUser getCrmUserByLoginNo(String loginNo);

	public CrmUser getCrmUserByLoginNoAndId(String loginNo, Integer crmUserId);

	public CrmUser getCrmUserById(Integer crmUserId);

	public CrmUser getCrmUserInfoById(Integer crmUserId);

	public Integer countCrmUserListByRoleType(Integer roleType);
	
	/**
	 * 查询所有管理员或者所有业务员
	 * @param roleType
	 * @return
	 */
	public Map<String, Object> getCrmUserListByRoleType(Integer roleType);

	/**
	 * 查询CRM账号列表
	 * @param pageOffset
	 * @param pageSize
	 * @param roleType
	 * @param adminId
	 * @param shopIdList
	 * @return
	 */
	public Map<String, Object> getCrmUserList(Integer pageOffset,
			Integer pageSize, Integer roleType, Integer adminId,
			String province, String city, String shopName);

	/**
	 * 查询CRM门店或者管理员下面的业务员
	 * @param pageOffset
	 * @param pageSize
	 * @param adminId
	 * @param crmShopName
	 * @return
	 */
	public Map<String, Object> getCrmClerkList(Integer pageOffset,
			Integer pageSize, Integer adminId, String crmShopName);

	/**
	 * 查询管理员下面的所有业务员
	 * @param adminId
	 * @param isActive
	 * @return
	 */
	public Map<String, Object> getAdminCrmClerkList(Integer adminId, Integer isActive);

	public List<CrmUserBizType> getCrmUserBizTypeList(Integer crmUserId);
	
	public Integer deleteCrmUserBizType(Integer crmUserId);
	
	public List<CrmUser> getCrmUserByCrmShopName(String crmShopName);
	
	/**
	 * 如果是客户经理这个角色就查询自身下面 + 自身下全部业务员的潜客
	 * @param adminId
	 * @return
	 */
	public List<Integer> getCrmUserIdListByRoleType(Integer adminId, Integer isPerson);
	
	/**
	 * 如果是客户经理这个角色就查询自身下面
	 * @return
	 */
	public Integer getCrmUserIdByRoleType();
	
	/**
	 * 查询crm管理员
	 * @param pageOffset
	 * @param pageSize
	 * @param isActive
	 * @param crmProvince
	 * @param crmCity
	 * @param crmShopName
	 * @return
	 */
	public Map<String, Object> getCrmAdminList(Integer pageOffset,
			Integer pageSize, Integer isActive, String crmProvince,
			String crmCity, String crmShopName);
	
	/**
	 * 查询业务员是否属于管理员这个公司下面
	 * @param adminId
	 * @param crmUserId
	 * @return
	 */
	public boolean validateBizAdminBelongCompany(Integer adminId,
			Integer crmUserId);
	
	/**
	 * 查询某个公司下面的业务员
	 * @param crmShopName
	 * @param userName
	 * @return
	 */
	public CrmUser getCrmUserByCrmShopNameAndUserName(String crmShopName,
			String userName);
	
	/**
	 * 查询管理员的业务类型
	 * @return
	 */
	public List<Integer> getAdminBizTypeList();
	
	/**
	 * 校验客户的业务类型是否合法
	 * @param customerBizTypeList 客户的业务类型
	 * @param crmUserId 业务员id
	 * @return
	 */
	public String isCustBizTypeRight(List<Integer> customerBizTypeList, Integer crmUserId);
}
