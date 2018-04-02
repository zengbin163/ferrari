package com.home.ferrari.service.crm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.crmdao.crm.CrmUserDao;
import com.home.ferrari.dao.common.ResourceDao;
import com.home.ferrari.dao.ferrari.FerrariUserDao;
import com.home.ferrari.dao.shop.TeslaShopDao;
import com.home.ferrari.dao.tesla.TeslaUserDao;
import com.home.ferrari.enums.CachePrefixEnum;
import com.home.ferrari.enums.CrmRoleTypeEnum;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.enums.PlatformEnum;
import com.home.ferrari.global.GlobalConstants;
import com.home.ferrari.global.PropertiesValue;
import com.home.ferrari.plugin.cache.RedisService;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.plugin.exception.TeslaBizException;
import com.home.ferrari.plugin.session.Session;
import com.home.ferrari.plugin.session.SessionContainer;
import com.home.ferrari.service.common.ResourceService;
import com.home.ferrari.service.crm.CrmUserService;
import com.home.ferrari.service.ferrari.FerrariUserService;
import com.home.ferrari.service.tesla.TeslaUserService;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.util.WebUtil;
import com.home.ferrari.vo.common.Resource;
import com.home.ferrari.vo.crm.CrmLoginLog;
import com.home.ferrari.vo.crm.CrmRoleMenu;
import com.home.ferrari.vo.crm.CrmUser;
import com.home.ferrari.vo.crm.CrmUserBizType;
import com.home.ferrari.vo.ferrari.user.FerrariUser;
import com.home.ferrari.vo.tesla.shop.TeslaShop;
import com.home.ferrari.vo.tesla.user.TeslaUser;

@Service
public class CrmUserServiceImpl implements CrmUserService {
	
	@Autowired
	private CrmUserDao crmUserDao;
	@Autowired
	private TeslaUserDao teslaUserDao;
	@Autowired
	private FerrariUserDao ferrariUserDao;
	@Autowired
	private TeslaShopDao teslaShopDao;
	@Autowired
	private RedisService redisService;
	@Autowired
	private PropertiesValue propertiesValue;
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private FerrariUserService ferrariUserService;
	@Autowired
	private TeslaUserService teslaUserService;

	@Override
	public Map<String, Object> passport(String sessionId, Integer platform, String ipAddress, HttpServletResponse response) {
		Assert.notNull(sessionId, "sessionId不能为空");
		Assert.notNull(platform, "platform不能为空，并且只能为0和1");
		if (FERRARI2CRM.equals(platform)) {
            Object obj = this.redisService.getObj(CachePrefixEnum.PREFIX_SESSION_ + sessionId);
            Session session = (Session)obj;
            FerrariUser ferrariUser = session.getFerrariUser();
            if(null == ferrariUser) {
    			throw new FerrariBizException(ResultCode.PASSPORT_ERROR, ResultCode.PASSPORT_ERROR.getString());
            }
            CrmUser crmUser = new CrmUser();
            crmUser.setId(ferrariUser.getId());//crm系统管理员一律都是平台的id
            crmUser.setMobile(ferrariUser.getMobile());
    		String crmSessionId = WebUtil.getSessionId(ferrariUser.getMobile(), PlatformEnum.CRM, ipAddress, propertiesValue.getDesKey());
    		crmUser.setSessionId(crmSessionId);
    		//角色类型
    		Integer roleType = CrmRoleTypeEnum.ROLE_SYSTEM_ADMIN.getCode();
    		crmUser.setRoleType(roleType);
    		//查询用户拥有的菜单权限
    		List<CrmRoleMenu> crmRoleMenuList = this.crmUserDao.getCrmSystemMenuList();
    		crmUser.setCrmRoleMenuList(crmRoleMenuList);
    		//插入登录日志
    		CrmLoginLog crmLoginLog = new CrmLoginLog(-1000, ferrariUser.getMobile(), crmSessionId, ipAddress);
    		this.crmUserDao.insertCrmLoginLog(crmLoginLog);
    		//缓存登录信息
    		Session crmSession = new Session(ferrariUser.getMobile(), PlatformEnum.CRM, crmUser, roleType);
    		this.redisService.setObj(CachePrefixEnum.PREFIX_SESSION_ + crmSessionId, crmSession);
    		this.setCookie(crmSessionId, response);
    		return ResultUtil.successMap(crmUser);            
		} else if (TESLA2CRM.equals(platform)) {
            Object obj = this.redisService.getObj(CachePrefixEnum.PREFIX_SESSION_ + sessionId);
            Session session = (Session)obj;
            TeslaUser teslaUser = session.getTeslaUser();
            if(null == teslaUser) {
    			throw new FerrariBizException(ResultCode.PASSPORT_ERROR, ResultCode.PASSPORT_ERROR.getString());
            }
            CrmUser crmUser = this.crmUserDao.getCrmUserByShopName(teslaUser.getMobile());
            if(null == crmUser || DefaultEnum.NO.getCode().equals(crmUser.getIsActive())) {
    			throw new FerrariBizException(ResultCode.PASSPORT_CRM_NOT_LINK, ResultCode.PASSPORT_CRM_NOT_LINK.getString());
            }
            return this.login(crmUser.getMobile(), crmUser.getPassword(), ipAddress, response);
		} else if (CRM2FERRARI.equals(platform)) {
            Object obj = this.redisService.getObj(CachePrefixEnum.PREFIX_SESSION_ + sessionId);
            Session session = (Session)obj;
            CrmUser crmUser = session.getCrmUser();
            if(null == crmUser) {
    			throw new FerrariBizException(ResultCode.PASSPORT_ERROR, ResultCode.PASSPORT_ERROR.getString());
            }
    		String mobile = crmUser.getMobile();
    		FerrariUser ferrariUser = this.ferrariUserDao.getFerrariUserByMobile(mobile);
            if(null == ferrariUser) {
    			throw new FerrariBizException(ResultCode.PASSPORT_ERROR, ResultCode.PASSPORT_ERROR.getString());
            }    		
            return this.ferrariUserService.login(mobile, ferrariUser.getPassword(), ipAddress, response);
		} else if (CRM2TESLA.equals(platform)) {
            Object obj = this.redisService.getObj(CachePrefixEnum.PREFIX_SESSION_ + sessionId);
            Session session = (Session)obj;
            CrmUser crmUser = session.getCrmUser();
            if(null == crmUser) {
    			throw new FerrariBizException(ResultCode.PASSPORT_ERROR, ResultCode.PASSPORT_ERROR.getString());
            }
    		String mobile = crmUser.getShopName();
    		TeslaUser teslaUser = this.teslaUserDao.getTeslaUserByMobile(mobile);
            if(null == teslaUser) {
    			throw new FerrariBizException(ResultCode.PASSPORT_ERROR, ResultCode.PASSPORT_ERROR.getString());
            }    		
    		return this.teslaUserService.login(mobile, teslaUser.getPassword(), ipAddress, response);
		} else {
			throw new FerrariBizException(ResultCode.PASSPORT_PLATFORM_ERROR, "platform错误，只能是0、1、2、3");
		}
	}

	@Override
	public Map<String, Object> validateTeslaUser(String shopName, String password) {
		Assert.notNull(shopName, "门店账号不能为空");
		Assert.notNull(password, "门店账号密码不能为空");
		TeslaUser teslaUser = this.teslaUserDao.getTeslaUserByMobileAndPassword(shopName, password);
		if(null == teslaUser) {
			throw new TeslaBizException(ResultCode.LOGIN_ERROR, shopName, ResultCode.LOGIN_ERROR.toString());
		}	
		return ResultUtil.successMap(this.teslaShopDao.getTeslaShopByName(shopName));
	}
	
	@Override
	public Integer saveCrmUser(CrmUser crmUser) {
		CrmUser crmUser1 = this.crmUserDao.getCrmUserByMobile(crmUser.getMobile());
		if(null!=crmUser1) {
			throw new FerrariBizException(ResultCode.MOBILE_IS_EXISTS, "手机号码已经注册");
		}
		CrmUser crmUser2 = this.crmUserDao.getCrmUserByCrmShopNameAndUserName(crmUser.getCrmShopName(), crmUser.getUserName());
		if(null!=crmUser2) {
			throw new FerrariBizException(ResultCode.USERNAME_IS_EXISTS, "同一个公司下面的用户名已存在");
		}
		CrmUser crmUser3 = this.crmUserDao.getCrmUserByShopId(crmUser.getShopId());
		if(null!=crmUser3) {
			throw new FerrariBizException(ResultCode.SHOPID_IS_EXISTS, "门店id已经注册");
		}
		
		this.crmUserDao.insertCrmUser(crmUser);
		Integer crmUserId = crmUser.getId();
		String loginNo = WebUtil.getCrmLoginNo(crmUserId);
		crmUser.setLoginNo(loginNo);
		this.crmUserDao.updateCrmUser(crmUser);
		return crmUserId;
	}
	
	public Integer saveCrmUserBizType(CrmUserBizType crmUserBizType) {
		return this.crmUserDao.insertCrmUserBizType(crmUserBizType);
	}
	
	public Map<String, Object> updateCrmUser(CrmUser crmUser) {
		CrmUser crmUserDB = this.crmUserDao.getCrmUserByLoginNoAndId(crmUser.getLoginNo(), crmUser.getId());
		if(crmUserDB!=null) {
			throw new FerrariBizException(ResultCode.LOGINCODE_IS_EXISTS, crmUser.getLoginNo(), ResultCode.LOGINCODE_IS_EXISTS.toString());
		}
		crmUserDB = this.crmUserDao.getCrmUserByMobileAndId(crmUser.getMobile(), crmUser.getId());
		if(crmUserDB!=null) {
			throw new FerrariBizException(ResultCode.MOBILE_IS_EXISTS, crmUser.getMobile(), ResultCode.MOBILE_IS_EXISTS.toString());
		}
		CrmUser tempCrmUser = this.crmUserDao.getCrmUserById(crmUser.getId());
		crmUserDB = this.crmUserDao.getCrmUserByUserNameAndCrmShopNameAndId(
				tempCrmUser.getCrmShopName(), crmUser.getUserName(),
				crmUser.getId());
		if(crmUserDB!=null) {
			throw new FerrariBizException(ResultCode.USERNAME_IS_EXISTS, crmUser.getUserName(), ResultCode.USERNAME_IS_EXISTS.toString());
		}
		return ResultUtil.successMap(this.crmUserDao.updateCrmUser(crmUser));
	}

	@Override
	public Map<String, Object> editCrmUser(CrmUser crmUser, String oldPassword, String newPassword) {
		CrmUser crmUserDB = this.crmUserDao.getCrmUserByLoginNoAndPassword(crmUser.getLoginNo(), oldPassword);
		if(null == crmUserDB) {
			throw new FerrariBizException(ResultCode.OLD_LOGIN_ERROR, crmUser.getLoginNo(), ResultCode.OLD_LOGIN_ERROR.toString());
		}
		return ResultUtil.successMap(this.crmUserDao.updateCrmUser(crmUser));
	}

	@Override
	public Map<String, Object> login(String loginNo, String password, String ipAddress, HttpServletResponse response) {
		Assert.notNull(loginNo, "登录账号不能为空");
		Assert.notNull(password, "登录密码不能为空");
		loginNo = loginNo.trim();
		CrmUser crmUser = this.crmUserDao.getCrmUserByLoginNoAndPassword(loginNo, password);
		if(null == crmUser) {
			throw new FerrariBizException(ResultCode.LOGIN_ERROR, loginNo, ResultCode.LOGIN_ERROR.toString());
		}
		String sessionId = WebUtil.getSessionId(loginNo, PlatformEnum.CRM, ipAddress, propertiesValue.getDesKey());
		crmUser.setSessionId(sessionId);
		//角色类型
		Integer roleType = crmUser.getRoleType();
		//查询用户拥有的菜单权限
		List<CrmRoleMenu> crmRoleMenuList = this.crmUserDao.getCrmUserMenuList(crmUser.getId());
		crmUser.setCrmRoleMenuList(crmRoleMenuList);
		//插入登录日志
		CrmLoginLog crmLoginLog = new CrmLoginLog(crmUser.getId(), loginNo, sessionId, ipAddress);
		this.crmUserDao.insertCrmLoginLog(crmLoginLog);
		//缓存登录信息
		Session session = new Session(loginNo, PlatformEnum.CRM, crmUser, roleType);
		this.redisService.setObj(CachePrefixEnum.PREFIX_SESSION_ + sessionId, session);
		this.setCookie(sessionId, response);
		return ResultUtil.successMap(crmUser);
	}
	

	@Override
	public Map<String, Object> linkShop(String shopName, String shopPassword, Integer crmUserId) {
		Assert.notNull(shopName, "门店登陆账号不能为空");
		Assert.notNull(shopPassword, "门店密码不能为空");
		Assert.notNull(crmUserId, "crm账号id不能为空");
		TeslaUser teslaUser = this.teslaUserDao.getTeslaUserByMobileAndPassword(shopName, shopPassword);
		if(null == teslaUser) {
			throw new TeslaBizException(ResultCode.LOGIN_ERROR, shopName, ResultCode.LOGIN_ERROR.toString());
		}
		CrmUser crmUserDb = this.crmUserDao.getCrmUserByShopName(shopName);
		if (null != crmUserDb) {
			if(DefaultEnum.YES.getCode().equals(crmUserDb.getIsActive())) {
				throw new FerrariBizException(ResultCode.CRM_IS_LINK, ResultCode.CRM_IS_LINK.getString() + crmUserDb.getLoginNo());
			}else{
				throw new FerrariBizException(ResultCode.CRM_IS_LINK_FROZEN, ResultCode.CRM_IS_LINK_FROZEN.getString() + crmUserDb.getLoginNo());
			}
		}
		TeslaShop shop = this.teslaShopDao.getTeslaShopByName(shopName);
		CrmUser crmUser = new CrmUser();
		crmUser.setId(crmUserId);
		crmUser.setShopId(shop.getId());
		crmUser.setShopName(shop.getShopName());
		crmUser.setShopCompany(shop.getCompanyName());
		crmUser.setShopAddress(shop.getShopAddress());
		this.crmUserDao.updateCrmUser(crmUser);
		return ResultUtil.successMap(ResultUtil.DATA_UPDATE_SUCC);
	}

	@Override
	public Map<String, Object> reLinkShop(String newShopName,
			String newShopPassword, String oldShopName,
			String oldShopPassword, Integer crmUserId) {
		Assert.notNull(oldShopName, "原门店登陆账号不能为空");
		Assert.notNull(oldShopPassword, "原门店密码不能为空");
		Assert.notNull(newShopName, "新的门店登陆账号不能为空");
		Assert.notNull(newShopPassword, "新的门店密码不能为空");
		Assert.notNull(crmUserId, "crm账号id不能为空");
		TeslaUser teslaUser = this.teslaUserDao.getTeslaUserByMobileAndPassword(oldShopName, oldShopPassword);
		if(null == teslaUser) {
			throw new TeslaBizException(ResultCode.OLD_LOGIN_ERROR, oldShopName, ResultCode.OLD_LOGIN_ERROR.toString());
		}
		TeslaUser teslaUser2 = this.teslaUserDao.getTeslaUserByMobileAndPassword(newShopName, newShopPassword);
		if(null == teslaUser2) {
			throw new TeslaBizException(ResultCode.NEW_LOGIN_ERROR, oldShopName, ResultCode.NEW_LOGIN_ERROR.toString());
		}
		CrmUser crmUserDb = this.crmUserDao.getCrmUserByShopName(oldShopName);
		if(null==crmUserDb || !crmUserId.equals(crmUserDb.getId())) {
			throw new TeslaBizException(ResultCode.OLD_LINK_ERROR, oldShopName, ResultCode.OLD_LINK_ERROR.toString());
		}
		CrmUser crmUserDb2 = this.crmUserDao.getCrmUserByShopName(newShopName);
		if (null != crmUserDb2) {
			if(DefaultEnum.YES.getCode().equals(crmUserDb2.getIsActive())) {
				throw new FerrariBizException(ResultCode.NEW_LINK_ERROR, ResultCode.NEW_LINK_ERROR.getString() + crmUserDb2.getLoginNo());
			}else{
				throw new FerrariBizException(ResultCode.NEW_LINK_ERROR_FROZEN, ResultCode.NEW_LINK_ERROR_FROZEN.getString() + crmUserDb2.getLoginNo());
			} 
		}
		
		TeslaShop shop = this.teslaShopDao.getTeslaShopByName(newShopName);
		CrmUser crmUser = new CrmUser();
		crmUser.setId(crmUserId);
		crmUser.setShopId(shop.getId());
		crmUser.setShopName(shop.getShopName());
		crmUser.setShopCompany(shop.getCompanyName());
		crmUser.setShopAddress(shop.getShopAddress());
		this.crmUserDao.updateCrmUser(crmUser);
		return ResultUtil.successMap(ResultUtil.DATA_UPDATE_SUCC);
	}

	@Override
	public Map<String, Object> frozenOrActive(Integer crmUserId, Integer isActive) {
		Assert.notNull(crmUserId, "crm账号id不能为空");
		Assert.notNull(isActive, "激活或者冻结状态不能为空");
		CrmUser crmUser = new CrmUser();
		crmUser.setId(crmUserId);
		crmUser.setIsActive(isActive);
		this.crmUserDao.updateCrmUser(crmUser);
		return ResultUtil.successMap(ResultUtil.DATA_UPDATE_SUCC);
	}
	
	@Override
	public Map<String, Object> openOrCloseSms(Integer crmUserId, Integer canSms) {
		Assert.notNull(crmUserId, "crm账号id不能为空");
		Assert.notNull(canSms, "开启或者关闭短信开关不能为空");
		CrmUser crmUser = new CrmUser();
		crmUser.setId(crmUserId);
		crmUser.setCanSms(canSms);
		this.crmUserDao.updateCrmUser(crmUser);
		return ResultUtil.successMap(ResultUtil.DATA_UPDATE_SUCC);
	}

	@Override
	public Map<String, Object> deleteCrmUser(Integer crmUserId) {
		Assert.notNull(crmUserId, "crm账号id不能为空");
		return ResultUtil.successMap(this.crmUserDao.deleteCrmUser(crmUserId));
	}

	@Override
	public CrmUser getCrmUserByLoginNo(String loginNo) {
		Assert.notNull(loginNo, "登录账号不能为空");
		return this.crmUserDao.getCrmUserByLoginNo(loginNo);
	}

	@Override
	public CrmUser getCrmUserByLoginNoAndId(String loginNo, Integer crmUserId) {
		Assert.notNull(loginNo, "登录账号不能为空");
		Assert.notNull(crmUserId, "用户id不能为空");
		return this.crmUserDao.getCrmUserByLoginNoAndId(loginNo, crmUserId);
	}

	@Override
	public CrmUser getCrmUserById(Integer crmUserId) {
		Assert.notNull(crmUserId, "用户id不能为空");
		return this.crmUserDao.getCrmUserById(crmUserId);
	}

	@Override
	public CrmUser getCrmUserInfoById(Integer crmUserId) {
		Assert.notNull(crmUserId, "用户id不能为空");
		return this.crmUserDao.getCrmUserInfoById(crmUserId);
	}
	
	public Integer countCrmUserListByRoleType(Integer roleType) {
		return this.crmUserDao.countCrmUserListByRoleType(roleType);
	}
	
	private void setCookie(String sessionId, HttpServletResponse response) {
        Cookie cookie = new Cookie(GlobalConstants.SESSION_ID, sessionId);
        cookie.setMaxAge(2592000);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
	}

	@Override
	public Map<String, Object> getCrmUserListByRoleType(Integer roleType) {
		Assert.notNull(roleType, "角色类型不能为空");
		return ResultUtil.successMap(this.crmUserDao.getCrmUserListByRoleType(roleType));
	}

	@Override
	public Map<String, Object> getCrmUserList(Integer pageOffset,
			Integer pageSize, Integer roleType, Integer adminId,
			String province, String city, String shopName) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		List<Integer> shopIdList = null;
		if(StringUtils.isNotBlank(shopName)) {
			shopIdList = this.teslaShopDao.getTeslaShopIds(shopName, province, city);
		}
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t1.modify_time");
		Map<String, Object> map = new HashMap<String, Object>();
		List<CrmUser> crmUserList = this.crmUserDao.getCrmUserList(page, roleType, adminId, shopIdList);
		if(CollectionUtils.isNotEmpty(crmUserList)) {
			for(CrmUser crmUser : crmUserList) {
				crmUser.setBizTypeList(this.getCrmUserBizTypeList(crmUser.getId()));
			}
		}
		map.put("list", crmUserList);
		map.put("sum", this.crmUserDao.countCrmUserList(roleType, adminId, shopIdList));
		return ResultUtil.successMap(map);
	}
	
	public Map<String, Object> getCrmClerkList(Integer pageOffset, Integer pageSize, Integer adminId, String crmShopName) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Map<String, Object> map = new HashMap<String, Object>();
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t1.modify_time");
		map.put("list", this.crmUserDao.getCrmClerkList(page, adminId, crmShopName, DefaultEnum.YES.getCode()));
		map.put("sum", this.crmUserDao.countCrmClerkList(adminId, crmShopName, DefaultEnum.YES.getCode()));
		return ResultUtil.successMap(map);
	}

	public Map<String, Object> getAdminCrmClerkList(Integer adminId, Integer isActive) {
		Assert.notNull(adminId, "adminId不能为空");
		return ResultUtil.successMap(this.crmUserDao.getCrmClerkList(null, adminId, null, isActive));
	}
	
	public List<CrmUserBizType> getCrmUserBizTypeList(Integer crmUserId) {
		List<CrmUserBizType> bizTypeList = this.crmUserDao.getCrmUserBizTypeList(crmUserId);
		if(CollectionUtils.isNotEmpty(bizTypeList)) {
			for(CrmUserBizType bizType : bizTypeList) {
				Resource resource = this.resourceDao.getResourcesByKeyVal(ResourceService.CUSTOMER_REQUIRE_KEY, bizType.getBizType());
				if(null!=resource){
					bizType.setResourceDesc(resource.getResourceDesc());
				}
			}
		}
		return bizTypeList;
	}

	public Integer deleteCrmUserBizType(Integer crmUserId) {
		Assert.notNull(crmUserId, "用户id不能为空");
		return this.crmUserDao.deleteCrmUserBizType(crmUserId);
	}
	
	public List<CrmUser> getCrmUserByCrmShopName(String crmShopName) {
		Assert.notNull(crmShopName, "crm门店名称不能为空");
		return this.crmUserDao.getCrmUserByCrmShopName(crmShopName);
	}
	
	public List<Integer> getCrmUserIdListByRoleType(Integer adminId, Integer isPerson) {
		Integer roleType = SessionContainer.getSession().getRoleType();
		List<Integer> list = new ArrayList<Integer>();
		if (CrmRoleTypeEnum.ROLE_SYSTEM_ADMIN.getCode().equals(roleType)) {
			return null;
		} else if (CrmRoleTypeEnum.ROLE_SHOP_ADMIN.getCode().equals(roleType)) {
			if (DefaultEnum.NO.getCode().equals(isPerson) || null == isPerson) {//管理员身份
				Map<String,Object> map = this.getAdminCrmClerkList(adminId, DefaultEnum.YES.getCode());
				 @SuppressWarnings("unchecked")
				List<CrmUser> crmUserList = ( List<CrmUser>)map.get(ResultUtil.DATA);
				if(CollectionUtils.isNotEmpty(crmUserList)) {
					for(CrmUser temp : crmUserList) {
						list.add(temp.getId());
					}
				}
			} else {
				list.add(adminId);
			}
			return list;
		} else {
			list.add(adminId);
			return list;
		}
	}

	public Integer getCrmUserIdByRoleType() {
		Integer roleType = SessionContainer.getSession().getRoleType();
		if(CrmRoleTypeEnum.ROLE_SYSTEM_ADMIN.getCode().equals(roleType)) {
			return null;
		}else{
			return SessionContainer.getSession().getCrmUser().getId();
		}
	}
	
	public Map<String, Object> getCrmAdminList(Integer pageOffset,
			Integer pageSize, Integer isActive, String crmProvince,
			String crmCity, String crmShopName) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Map<String, Object> map = new HashMap<String, Object>();
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t1.modify_time");
		map.put("list", this.crmUserDao.getCrmAdminList(page, isActive, crmProvince, crmCity, crmShopName));
		map.put("sum", this.crmUserDao.countCrmAdminList(isActive, crmProvince, crmCity, crmShopName));
		return ResultUtil.successMap(map);
	}
	
	public boolean validateBizAdminBelongCompany(Integer adminId, Integer crmUserId) {
		Assert.notNull(adminId, "管理员id不能为空");
		Assert.notNull(crmUserId, "业务员id不能为空");
		Integer count = this.crmUserDao.validateBizAdminBelongCompany(adminId, crmUserId);
		return count > 0 ? true : false;
	}
	
	public CrmUser getCrmUserByCrmShopNameAndUserName(String crmShopName, String userName) {
		Assert.notNull(crmShopName, "crm门店不能为空");
		Assert.notNull(userName, "用户名不能为空");
		return this.crmUserDao.getCrmUserByCrmShopNameAndUserName(crmShopName, userName);
	}
	
	public List<Integer> getAdminBizTypeList() {
		Integer adminId = SessionContainer.getSession().getCrmUser().getId();
		List<Resource> resourceList = this.resourceDao.getResourcesByKeyAndId(ResourceService.CUSTOMER_REQUIRE_KEY, adminId);
		List<Integer> list = new ArrayList<Integer>();
		for(Resource resource : resourceList) {
			list.add(resource.getResourceValue());
		}
		return list;
	}
	
	public String isCustBizTypeRight(List<Integer> customerBizTypeList, Integer crmUserId) {
		Assert.notEmpty(customerBizTypeList, "客户业务类型不能为空");
		Assert.notNull(crmUserId, "业务员id不能为空");
		//查询管理员业务类型，客户业务类型要属于管理员
		List<Integer> adminBizTypeList = this.getAdminBizTypeList();
		if(!adminBizTypeList.containsAll(customerBizTypeList)) {
			return "有客户业务类型不属于管理员业务类型";
		}
		//如果管理员是业务员的话
		CrmUser ccUser = this.crmUserDao.getCrmUserById(crmUserId);
		if(null == ccUser.getAdminId()) {
			return null;
		}
		//查询业务员业务类型，业务员业务类型和客户业务类型要有交集
		List<CrmUserBizType> bizTypeList = this.getCrmUserBizTypeList(crmUserId);
		if(CollectionUtils.isNotEmpty(bizTypeList)) {
			for(CrmUserBizType userBizType : bizTypeList) {
				for(Integer customerBizTypeId : customerBizTypeList) {
					if(customerBizTypeId.equals(userBizType.getBizType())) {
						return null;
					}
				}
			}
			return "客户业务类型不属于业务员业务类型";
		}else{
			return "业务员业务类型不存在";
		}
	}
}
