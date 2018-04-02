package com.home.ferrari.service.tesla.impl;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.crmdao.crm.CrmUserDao;
import com.home.ferrari.dao.tesla.TeslaLoginLogDao;
import com.home.ferrari.dao.tesla.TeslaUserDao;
import com.home.ferrari.enums.CachePrefixEnum;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.enums.PlatformEnum;
import com.home.ferrari.global.GlobalConstants;
import com.home.ferrari.global.PropertiesValue;
import com.home.ferrari.plugin.cache.RedisService;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.plugin.exception.TeslaBizException;
import com.home.ferrari.plugin.session.Session;
import com.home.ferrari.plugin.session.SessionContainer;
import com.home.ferrari.service.tesla.TeslaUserService;
import com.home.ferrari.util.MD5Util;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.util.WebUtil;
import com.home.ferrari.vo.crm.CrmUser;
import com.home.ferrari.vo.tesla.user.TeslaLoginLog;
import com.home.ferrari.vo.tesla.user.TeslaUser;

@Service
public class TeslaUserServiceImpl implements TeslaUserService {
	
	@Autowired
	private TeslaUserDao teslaUserDao;
	@Autowired
	private TeslaLoginLogDao teslaLoginLogDao;
	@Autowired
	private RedisService redisService;
	@Autowired
	private PropertiesValue propertiesValue;
	@Autowired
	private CrmUserDao crmUserDao;

	@Override
	public Map<String, Object> login(String mobile, String password, String ipAddress, HttpServletResponse response) {
		Assert.notNull(mobile, "登陆账号不能为空");
		Assert.notNull(password, "密码不能为空");
		mobile = mobile.trim();
		TeslaUser teslaUser = this.teslaUserDao.getTeslaUserByMobileAndPassword(mobile, password);
		if(null == teslaUser) {
			throw new TeslaBizException(ResultCode.LOGIN_ERROR, mobile, ResultCode.LOGIN_ERROR.toString());
		}
		String sessionId = WebUtil.getSessionId(mobile, PlatformEnum.TESLA, ipAddress, propertiesValue.getDesKey());
		teslaUser.setSessionId(sessionId);
		//查询关联的CRM
		CrmUser crmUser = this.crmUserDao.getCrmUserByShopName(mobile);
		if (null == crmUser) {
			teslaUser.setIsLinkCrm(DefaultEnum.NO.getCode());
		} else {
			teslaUser.setIsLinkCrm(DefaultEnum.YES.getCode());
		}
		//插入登录日志
		TeslaLoginLog teslaLoginLog = new TeslaLoginLog(teslaUser.getId(), mobile, sessionId, ipAddress);
		this.teslaLoginLogDao.insertTeslaLoginLog(teslaLoginLog);
		//缓存登录信息
		Session session = new Session(mobile, PlatformEnum.TESLA, teslaUser);
		session.setRoleType(-1);
		this.redisService.setObj(CachePrefixEnum.PREFIX_SESSION_ + sessionId, session);
		this.setCookie(sessionId, response);
		return ResultUtil.successMap(teslaUser);
	}
	
	public Map<String, Object> password(String mobile, String oldPass, String newPass) {
		Assert.notNull(mobile, "手机号码不能为空");
		Assert.notNull(oldPass, "原始密码不能为空");
		Assert.notNull(newPass, "新密码不能为空");
		TeslaUser teslaUser = this.teslaUserDao.getTeslaUserByMobileAndPassword(mobile, oldPass);
		if(null == teslaUser) {
			throw new TeslaBizException(ResultCode.PASSWORD_ERROR, mobile, ResultCode.PASSWORD_ERROR.toString());
		}
		teslaUser.setPassword(newPass);
		this.updateTeslaUser(teslaUser);
		return ResultUtil.successMap(teslaUser.getId());
	}
	
	public Map<String, Object> resetPass(String shopName) {
		Assert.notNull(shopName, "门店名称不能为空");
		TeslaUser teslaUser = this.teslaUserDao.getTeslaUserByMobile(shopName);
		if (teslaUser != null) {
			TeslaUser tempUser = new TeslaUser();
			tempUser.setId(teslaUser.getId());
			tempUser.setPassword(MD5Util.md5("111111"));
			tempUser.setIsActive(DefaultEnum.YES.getCode());
			tempUser.setVersion(teslaUser.getVersion() + 1);
			Integer flag = this.teslaUserDao.updateTeslaUser(tempUser);
			if(flag <= 0) {
				throw new TeslaBizException(ResultCode.UPDATE_FAIL, String.format("更新用户失败，id=%s", teslaUser.getId()));
			}
			return ResultUtil.successMap(tempUser);
		}
		return ResultUtil.toErrorMap(ResultCode.NOT_EXISTS, "账号不存在");
	}
	
	public Map<String, Object> updateTeslaUser(TeslaUser teslaUser) {
		TeslaUser tempTeslaUser = this.teslaUserDao.getTeslaUserById(teslaUser.getId());
		if(null == tempTeslaUser) {
			throw new TeslaBizException(ResultCode.NOT_EXISTS, String.format("用户不存在，id=%s", teslaUser.getId()));
		}
		teslaUser.setVersion(tempTeslaUser.getVersion() + 1);
		Integer flag = this.teslaUserDao.updateTeslaUser(teslaUser);
		if(flag <= 0) {
			throw new TeslaBizException(ResultCode.UPDATE_FAIL, String.format("更新用户失败，id=%s", teslaUser.getId()));
		}
		return ResultUtil.successMap(teslaUser);
	}

	public Map<String, Object> updateTeslaUserByShopId(TeslaUser teslaUser) {
		TeslaUser tempTeslaUser = this.teslaUserDao.getTeslaUserByShopId(teslaUser.getShopId());
		if(null == tempTeslaUser) {
			throw new TeslaBizException(ResultCode.NOT_EXISTS, String.format("用户不存在，id=%s", teslaUser.getId()));
		}
		teslaUser.setVersion(tempTeslaUser.getVersion() + 1);
		Integer flag = this.teslaUserDao.updateTeslaUserByShopId(teslaUser);
		if(flag <= 0) {
			throw new TeslaBizException(ResultCode.UPDATE_FAIL, String.format("更新用户失败，id=%s", teslaUser.getId()));
		}
		return ResultUtil.successMap(teslaUser);
	}

	public Map<String, Object> logout(String sessionId) {
		Assert.notNull(sessionId, "sessionId不能为空");
		this.redisService.del(CachePrefixEnum.PREFIX_SESSION_ + sessionId);
		SessionContainer.clear();
		return ResultUtil.successMap(true);
	}

	public Integer createAccount(Integer shopId, String nickName,
			String mobile, String password, String headerUrl, String signature) {
		TeslaUser teslaUser = new TeslaUser();
		teslaUser.setShopId(shopId);
		teslaUser.setHeaderUrl(headerUrl);
		teslaUser.setMobile(mobile);
		teslaUser.setNickName(nickName);
		teslaUser.setPassword(password);
		teslaUser.setSignature(signature);
		Integer flag = this.teslaUserDao.insertTeslaUser(teslaUser);
		if(flag < 1) {
			throw new FerrariBizException(ResultCode.SAVE_FAIL, "新增门店账号失败，mobile=" + mobile);
		}
		return teslaUser.getId();
	}
	
	private void setCookie(String sessionId, HttpServletResponse response) {
        Cookie cookie = new Cookie(GlobalConstants.SESSION_ID, sessionId);
        cookie.setMaxAge(2592000);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
	}
}
