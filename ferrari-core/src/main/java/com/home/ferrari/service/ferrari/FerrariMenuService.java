package com.home.ferrari.service.ferrari;

import java.util.Map;

public interface FerrariMenuService {

	/**
	 * 新增菜单
	 * @param parentMenuId
	 * @param menuName
	 * @param menuEnName
	 * @param menuUrl
	 * @param isMenu
	 * @return
	 */
	public Map<String, Object> saveMenu(Integer parentMenuId, String menuName,
			String menuEnName, String menuUrl, Integer isMenu);

	/**
	 * 更新菜单
	 * @param menuId
	 * @param parentMenuId
	 * @param menuName
	 * @param menuEnName
	 * @param menuUrl
	 * @param isMenu
	 * @return
	 */
	public Map<String, Object> editMenu(Integer menuId, Integer parentMenuId,
			String menuName, String menuEnName, String menuUrl, Integer isMenu);
	
	/**
	 * 查询菜单下拉列表
	 */
	public Map<String, Object> getFerrariMenuList();
	
	/**
	 * 查询角色菜单列表
	 * @return
	 */
	public Map<String, Object> getFerrariRoleMenuList();

}
