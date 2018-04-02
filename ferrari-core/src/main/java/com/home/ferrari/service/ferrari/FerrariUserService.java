package com.home.ferrari.service.ferrari;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.home.ferrari.plugin.session.Session;
import com.home.ferrari.vo.ferrari.user.FerrariUser;

public interface FerrariUserService {
	
	public Map<String, Object> login(String mobile, String password, String ipAddress, HttpServletResponse response);

	public Map<String, Object> password(String mobile, String oldPass, String newPass);

	public Map<String, Object> logout(String sessionId);

	/**
	 * 创建账号
	 * @param ferrariUser
	 * @param roleIds  角色id集合，通过","分隔
	 * @return
	 */
	public Map<String, Object> createUser(FerrariUser ferrariUser, String roleIds);

	public Map<String, Object> updateFerrariUser(FerrariUser ferrariUser);

	public Map<String, Object> editFerrariUser(FerrariUser ferrariUser, String roleIds);

	public Map<String, Object> getFerrariUserById(Integer id);

	public Map<String, Object> getFerrariUserProxyById(Integer id);
	
	public Map<String, Object> getFerrariUserList(Integer pageOffset, Integer pageSize, String mobile, String nickName,  Integer roleType);

	public Map<String, Object> getFerrariProxyUserList(Integer pageOffset, Integer pageSize, String province, String mobile);
	
	public Map<String, Object> getFerrariUserMenuList(Integer userId);
	
	public void hasAuthority(String requestUrl, Session session);
	
	/**
	 * 查询省市代理是否存在
	 * @param province
	 * @param city
	 * @return
	 */
	public Map<String, Object> validateFerrariUserExists(String province, String city);

}
