package com.home.ferrari.service.ferrari.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.dao.ferrari.FerrariLoginLogDao;
import com.home.ferrari.dao.ferrari.FerrariRoleDao;
import com.home.ferrari.dao.ferrari.FerrariRoleMenuDao;
import com.home.ferrari.dao.ferrari.FerrariUserDao;
import com.home.ferrari.dao.ferrari.FerrariUserRoleDao;
import com.home.ferrari.dao.shop.TeslaShopDao;
import com.home.ferrari.enums.CachePrefixEnum;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.enums.PlatformEnum;
import com.home.ferrari.enums.RoleTypeEnum;
import com.home.ferrari.global.GlobalConstants;
import com.home.ferrari.global.PropertiesValue;
import com.home.ferrari.plugin.cache.RedisService;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.plugin.session.Session;
import com.home.ferrari.plugin.session.SessionContainer;
import com.home.ferrari.service.ferrari.FerrariUserService;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.util.WebUtil;
import com.home.ferrari.vo.ferrari.user.FerrariLoginLog;
import com.home.ferrari.vo.ferrari.user.FerrariRole;
import com.home.ferrari.vo.ferrari.user.FerrariRoleMenu;
import com.home.ferrari.vo.ferrari.user.FerrariUser;
import com.home.ferrari.vo.ferrari.user.FerrariUserRole;

@Service
public class FerrariUserServiceImpl implements FerrariUserService {
	
	@Autowired
	private FerrariUserDao ferrariUserDao;
	@Autowired
	private FerrariRoleMenuDao ferrariRoleMenuDao;
	@Autowired
	private FerrariRoleDao ferrariRoleDao;
	@Autowired
	private FerrariUserRoleDao ferrariUserRoleDao;
	@Autowired
	private FerrariLoginLogDao ferrariLoginLogDao;
	@Autowired
	private RedisService redisService;
	@Autowired
	private PropertiesValue propertiesValue;
	@Autowired
	private TeslaShopDao teslaShopDao;
	
	private static final Logger logger = LoggerFactory.getLogger(FerrariUserServiceImpl.class);

	@Override
	public Map<String, Object> login(String mobile, String password, String ipAddress, HttpServletResponse response) {
		Assert.notNull(mobile, "登陆账号不能为空");
		Assert.notNull(password, "密码不能为空");
		mobile = mobile.trim();
		FerrariUser ferrariUser = this.ferrariUserDao.getFerrariUserByMobileAndPassword(mobile, password);
		if(null == ferrariUser) {
			throw new FerrariBizException(ResultCode.LOGIN_ERROR, mobile, ResultCode.LOGIN_ERROR.toString());
		}
		String sessionId = WebUtil.getSessionId(mobile, PlatformEnum.FERRARI, ipAddress, propertiesValue.getDesKey());
		ferrariUser.setSessionId(sessionId);
		//角色类型
		Integer roleType = ferrariUser.getRoleType();
		//查询用户拥有的菜单权限
		List<FerrariRoleMenu> ferrariRoleMenuList = this.ferrariRoleMenuDao.getFerrariUserMenuList(ferrariUser.getId());
		ferrariUser.setFerrariRoleMenuList(ferrariRoleMenuList);
		//插入登录日志
		FerrariLoginLog ferrariLoginLog = new FerrariLoginLog(ferrariUser.getId(), mobile, sessionId, ipAddress);
		this.ferrariLoginLogDao.insertFerrariLoginLog(ferrariLoginLog);
		//缓存登录信息
		Session session = new Session(mobile, PlatformEnum.FERRARI, ferrariUser, roleType);
		this.redisService.setObj(CachePrefixEnum.PREFIX_SESSION_ + sessionId, session);
		this.setCookie(sessionId, response);
		return ResultUtil.successMap(ferrariUser);
	}
	
	public Map<String, Object> password(String mobile, String oldPass, String newPass) {
		Assert.notNull(mobile, "手机号码不能为空");
		Assert.notNull(oldPass, "原始密码不能为空");
		Assert.notNull(newPass, "新密码不能为空");
		FerrariUser ferrariUser = this.ferrariUserDao.getFerrariUserByMobileAndPassword(mobile, oldPass);
		if(null == ferrariUser) {
			throw new FerrariBizException(ResultCode.PASSWORD_ERROR, mobile, ResultCode.PASSWORD_ERROR.toString());
		}
		ferrariUser.setPassword(newPass);
		this.updateFerrariUser(ferrariUser);
		return ResultUtil.successMap(ferrariUser.getId());
	}

	public Map<String, Object> logout(String sessionId) {
		Assert.notNull(sessionId, "sessionId不能为空");
		this.redisService.del(CachePrefixEnum.PREFIX_SESSION_ + sessionId);
		SessionContainer.clear();
		return ResultUtil.successMap(true);
	}
	
	@Transactional
	public Map<String, Object> createUser(FerrariUser ferrariUser, String roleIds) {
		Assert.notNull(ferrariUser.getMobile(), "登陆账号不能为空");
		Assert.notNull(ferrariUser.getPassword(), "登陆密码不能为空");
		Assert.notNull(roleIds, "角色id集合不能为空");
		FerrariUser tempUser = this.ferrariUserDao.getFerrariUserByMobile(ferrariUser.getMobile());
		Integer userId = null;
		if(null == tempUser) {
			/**
			 * 校验省市代
			 */
			String province = ferrariUser.getProvince();
			String city = ferrariUser.getCity();
			if(!StringUtils.isEmpty(city) && !StringUtils.isEmpty(province)) {
				//市代理
				FerrariUser ferrariUserDB = this.ferrariUserDao.getFerrariUserByProvinceCity(province, 1, city);
				if(ferrariUserDB != null) {
					if(DefaultEnum.YES.getCode().equals(ferrariUserDB.getIsActive())) {
						throw new FerrariBizException(ResultCode.CITYPROXY_EXISTS, String.format("市代理已存在，city=%s", city));
					}else{
						throw new FerrariBizException(ResultCode.CITYPROXY_FROZEN_EXISTS, String.format("已冻结市代理已存在，city=%s", city));
					}
				}
			}else if(StringUtils.isEmpty(city) && !StringUtils.isEmpty(province)){
				//省代理
				FerrariUser ferrariUserDB = this.ferrariUserDao.getFerrariUserByProvinceCity(province,2, null);
				if(ferrariUserDB != null) {
					if(DefaultEnum.YES.getCode().equals(ferrariUserDB.getIsActive())) {
						throw new FerrariBizException(ResultCode.PROVINCEPROXY_EXISTS, String.format("省代理已存在，city=%s", city));
					}else{
						throw new FerrariBizException(ResultCode.PROVINCEPROXY_FROZEN_EXISTS, String.format("已冻结省代理已存在，city=%s", city));
					}
				}
			}else{
				//非省市代的普通用户
				;
			}
			this.ferrariUserDao.insertFerrariUser(ferrariUser);
			if(ferrariUser.getId() <= 0) {
				throw new FerrariBizException(ResultCode.SAVE_FAIL, String.format("新增用户失败，nickName=%s", ferrariUser.getNickName()));
			}
			userId = ferrariUser.getId();
		} else {
			if(DefaultEnum.YES.getCode().equals(tempUser.getIsActive())) {
				throw new FerrariBizException(ResultCode.USER_EXIST, "用户已存在，mobile=" + ferrariUser.getMobile());
			}else{
				tempUser.setIsActive(DefaultEnum.YES.getCode());
				this.editFerrariUser(tempUser, roleIds);
			}
			userId = tempUser.getId();
		}
		//插入用户角色信息
		String []rIds = roleIds.split(",");
		for (int i = 0; i < rIds.length; i++) {
			FerrariUserRole fur = this.ferrariUserRoleDao.getFerrariUserRoleByUserId(userId);
			if(null != fur) {
				logger.error(ResultCode.USERROLE_IS_EXISTS.getString() + "  该用户已经拥有角色 roleId=" + fur.getRoleId() + ",userId=" + userId);
				break;
			}
			Integer roleId = Integer.parseInt(rIds[i]);
			FerrariRole ferrariRole = this.ferrariRoleDao.getFerrariRoleById(roleId);
			if(null!=ferrariRole && RoleTypeEnum.ROLE_SYSTEM_CRM.getCode().equals(ferrariRole.getRoleType())) {
				Integer count = this.ferrariUserRoleDao.countFerrariUserByRoleId(roleId);
				if(count > 0) {
					throw new FerrariBizException(ResultCode.CRM_SYSYTEMUSER_IS_EXISTS, ResultCode.CRM_SYSYTEMUSER_IS_EXISTS.getString());
				}
			}
			FerrariUserRole ferrariUserRole = new FerrariUserRole(userId, roleId);
			this.ferrariUserRoleDao.insertFerrariUserRole(ferrariUserRole);
		}
		return ResultUtil.successMap(ferrariUser);
	}
	
	@Transactional
	public Map<String, Object> editFerrariUser(FerrariUser ferrariUser, String roleIds) {
		Integer userId = ferrariUser.getId();
		FerrariUser dbUser = this.ferrariUserDao.getFerrariUserInfoById(userId);
		/**
		 * 校验省市代
		 */
		String province = ferrariUser.getProvince();
		String city = ferrariUser.getCity();
		if (!StringUtils.isEmpty(city) && !StringUtils.isEmpty(province) 
				&& !(province.equals(dbUser.getProvince()) && city.equals(dbUser.getCity()))) {
			//市代理
			FerrariUser ferrariUserDB = this.ferrariUserDao.getFerrariUserByProvinceCity(province, 1 , city);
			if(ferrariUserDB != null) {
				if(DefaultEnum.YES.getCode().equals(ferrariUserDB.getIsActive())) {
					throw new FerrariBizException(ResultCode.CITYPROXY_EXISTS, String.format("市代理已存在，city=%s", city));
				}else{
					throw new FerrariBizException(ResultCode.CITYPROXY_FROZEN_EXISTS, String.format("已冻结市代理已存在，city=%s", city));
				}
			}
		}else if(StringUtils.isEmpty(city) && !StringUtils.isEmpty(province)
				&& !province.equals(dbUser.getProvince())){
			//省代理
			FerrariUser ferrariUserDB = this.ferrariUserDao.getFerrariUserByProvinceCity(province, 2, null);
			if(ferrariUserDB != null) {
				if(DefaultEnum.YES.getCode().equals(ferrariUserDB.getIsActive())) {
					throw new FerrariBizException(ResultCode.PROVINCEPROXY_EXISTS, String.format("省代理已存在，city=%s", city));
				}else{
					throw new FerrariBizException(ResultCode.PROVINCEPROXY_FROZEN_EXISTS, String.format("已冻结省代理已存在，city=%s", city));
				}
			}
		}else{
			//非省市代的普通用户
			;
		}
		//更新
		this.updateFerrariUser(ferrariUser);
		this.ferrariUserRoleDao.deleteFerrariUserRole(userId);
		//插入用户角色信息
		String []rIds = roleIds.split(",");
		for (int i = 0; i < rIds.length; i++) {
			FerrariUserRole fur = this.ferrariUserRoleDao.getFerrariUserRoleByUserId(userId);
			if(null != fur) {
				logger.error(ResultCode.USERROLE_IS_EXISTS.getString() + "  该用户已经拥有角色 roleId=" + fur.getRoleId() + ",userId=" + userId);
				break;
			}			
			FerrariUserRole ferrariUserRole = new FerrariUserRole(userId, Integer.parseInt(rIds[i]));
			this.ferrariUserRoleDao.insertFerrariUserRole(ferrariUserRole);
		}
		return ResultUtil.successMap(ferrariUser);
	}
	
	public Map<String, Object> updateFerrariUser(FerrariUser ferrariUser) {
		FerrariUser tempFerrariUser = this.ferrariUserDao.getFerrariUserInfoById(ferrariUser.getId());//激活或者冻结都会查询出来
		if(null == tempFerrariUser) {
			throw new FerrariBizException(ResultCode.NOT_EXISTS, String.format("用户不存在，id=%s", ferrariUser.getId()));
		}
		if(!StringUtils.isEmpty(ferrariUser.getMobile())) {
			FerrariUser fu = this.ferrariUserDao.getFerrariUserByMobileAndId(ferrariUser.getMobile(), ferrariUser.getId());
			if (null != fu) {
				throw new FerrariBizException(ResultCode.MOBILE_IS_EXISTS, String.format("账号已存在，mobile=%s", ferrariUser.getMobile()));
			}
		}
		ferrariUser.setVersion(tempFerrariUser.getVersion() + 1);
		Integer flag = this.ferrariUserDao.updateFerrariUser(ferrariUser);
		if(flag <= 0) {
			throw new FerrariBizException(ResultCode.UPDATE_FAIL, String.format("更新用户失败，id=%s", ferrariUser.getId()));
		}
		return ResultUtil.successMap(ferrariUser);
	}

	public Map<String, Object> getFerrariUserById(Integer id) {
		Assert.notNull(id, "用户id不能为空");
		return ResultUtil.successMap(this.ferrariUserDao.getFerrariUserById(id));
	}

	public Map<String, Object> getFerrariUserProxyById(Integer id) {
		Assert.notNull(id, "用户id不能为空");
		FerrariUser ferrariUser = this.ferrariUserDao.getFerrariUserById(id);
		if(null!=ferrariUser) {
			ferrariUser.setCitySum(this.ferrariUserDao.countFerrariUserList(null, null, ferrariUser.getProvince(), null, RoleTypeEnum.ROLE_CITY.getCode(), DefaultEnum.YES.getCode()));
			ferrariUser.setShopSum(this.teslaShopDao.countTeslaShopList(null, ferrariUser.getProvince(), ferrariUser.getCity(), null, null, null, null, null, null, null));
		}
		return ResultUtil.successMap(ferrariUser);
	}
	
	public Map<String, Object> getFerrariUserList(Integer pageOffset, Integer pageSize, String mobile, String nickName,  Integer roleType) {
		Assert.notNull(pageOffset,"pageOffset不能为空");
		Assert.notNull(pageSize,"pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, Page.ORDER_BY_ID);
		List<FerrariUser> ferrariUserList = this.ferrariUserDao.getFerrariUserList(page, mobile, nickName, null, null, roleType, null);
		Integer sum = this.ferrariUserDao.countFerrariUserList(mobile, nickName, null, null, roleType, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", ferrariUserList);
		map.put("sum", sum);
		return ResultUtil.successMap(map);
	}
	
	public Map<String, Object> getFerrariProxyUserList(Integer pageOffset, Integer pageSize, String province, String mobile) {
		Assert.notNull(pageOffset,"pageOffset不能为空");
		Assert.notNull(pageSize,"pageSize不能为空");
		if(StringUtils.isEmpty(province)){
			//查询所有省代理
			Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, Page.ORDER_BY_ID);
			List<FerrariUser> ferrariUserList = this.ferrariUserDao.getFerrariUserList(page, mobile, null, null, null, RoleTypeEnum.ROLE_PROVINCE.getCode(), DefaultEnum.YES.getCode());
			if(CollectionUtils.isNotEmpty(ferrariUserList)) {
				for(FerrariUser ferrariUser : ferrariUserList) {
					ferrariUser.setCitySum(this.ferrariUserDao.countFerrariUserList(null, null, ferrariUser.getProvince(), null, RoleTypeEnum.ROLE_CITY.getCode(), DefaultEnum.YES.getCode()));
					ferrariUser.setShopSum(this.teslaShopDao.countTeslaShopList(null, ferrariUser.getProvince(), null, null, null, null, null, null, null, null));
				}
			}
			Integer sum = this.ferrariUserDao.countFerrariUserList(mobile, null, null, null, RoleTypeEnum.ROLE_PROVINCE.getCode(), DefaultEnum.YES.getCode());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", ferrariUserList);
			map.put("sum", sum);
			map.put("proxySum", this.ferrariUserDao.sumProxyUserCount());
			return ResultUtil.successMap(map);
		}else{
			//查询单个省代理
			Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, Page.ORDER_BY_ID);
			List<FerrariUser> ferrariUserList = this.ferrariUserDao.getFerrariUserList(page, null, null, province, null, RoleTypeEnum.ROLE_CITY.getCode(), DefaultEnum.YES.getCode());
			if(CollectionUtils.isNotEmpty(ferrariUserList)) {
				for(FerrariUser ferrariUser : ferrariUserList) {
					ferrariUser.setShopSum(this.teslaShopDao.countTeslaShopList(null, province, ferrariUser.getCity(), null, null, null, null, null, null, null));
				}
			}
			Integer sum = this.ferrariUserDao.countFerrariUserList(null, null, province, null, RoleTypeEnum.ROLE_CITY.getCode(), DefaultEnum.YES.getCode());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", ferrariUserList);
			map.put("sum", sum);
			map.put("proxySum", this.ferrariUserDao.sumProxyUserCount());
			return ResultUtil.successMap(map);
		}
	}

	
	public Map<String, Object> getFerrariUserMenuList(Integer userId) {
		Assert.notNull(userId, "用户id不能为空");
		return ResultUtil.successMap(this.ferrariRoleMenuDao.getFerrariUserMenuList(userId));
	}
	
	public void hasAuthority(String requestUrl, Session session) {
		if(!PlatformEnum.FERRARI.equals(session.getPlatform())) {
			return;
		}
		Integer count = this.ferrariRoleMenuDao.hasMenuAuthority(requestUrl, session.getMobile());
		if(count < 1) {
			throw new FerrariBizException(ResultCode.HAS_NOT_AUTHORITY, session.getMobile(), String.format("用户没有权限？mobile=%s", session.getMobile()));
		}
	}
	
	public Map<String, Object> validateFerrariUserExists(String province, String city) {
		Assert.notNull(province, "省份不能为空");
		if(StringUtils.isEmpty(city)){
			//省代理
			FerrariUser ferrariUserDB = this.ferrariUserDao.getFerrariUserByProvinceCity(province, 2, null);
			if(ferrariUserDB != null) {
				if(DefaultEnum.YES.getCode().equals(ferrariUserDB.getIsActive())) {
					throw new FerrariBizException(ResultCode.PROVINCEPROXY_EXISTS, String.format("省代理已存在，city=%s", city));
				}else{
					throw new FerrariBizException(ResultCode.PROVINCEPROXY_FROZEN_EXISTS, String.format("已冻结省代理已存在，city=%s", city));
				}
			}
		}else{
			//市代理
			FerrariUser ferrariUserDB = this.ferrariUserDao.getFerrariUserByProvinceCity(province, 1 , city);
			if(ferrariUserDB != null) {
				if(DefaultEnum.YES.getCode().equals(ferrariUserDB.getIsActive())) {
					throw new FerrariBizException(ResultCode.CITYPROXY_EXISTS, String.format("市代理已存在，city=%s", city));
				}else{
					throw new FerrariBizException(ResultCode.CITYPROXY_FROZEN_EXISTS, String.format("已冻结市代理已存在，city=%s", city));
				}
			}
		}
		return ResultUtil.successMap("");
	}
	
	private void setCookie(String sessionId, HttpServletResponse response) {
        Cookie cookie = new Cookie(GlobalConstants.SESSION_ID, sessionId);
        cookie.setMaxAge(2592000);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
	}
}
