package com.home.ferrari.service.ferrari;

import java.util.Map;

public interface FerrariRoleService {
	
	/**
	 * 创建角色
	 * @param roleName 角色名称
	 * @param menuIds 根据","分隔的菜单id
	 * @param roleType @RoleTypeEnum
	 */
	public Map<String, Object> createRole(String roleName, String menuIds, Integer roleType);

	/**
	 * 编辑角色
	 * @param roleId 角色id
	 * @param roleName 角色名称
	 * @param menuIds 根据","分隔的菜单id
	 */
	public Map<String, Object> editRole(Integer roleId, String roleName, String menuIds, Integer roleType);

	/**
	 * 查询角色下拉列表
	 * @return
	 */
	public Map<String, Object> getFerrariRoleList();

	public Map<String, Object> deleteRole(Integer roleId);

}
