/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.dao.ferrari;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.vo.ferrari.user.FerrariRoleMenu;


/** 
* @ClassName: FerrariRoleMenuDao 
* @Description: 角色功能权限DAO
* @author zengbin
* @date 2016年05月01日 下午3:23:23 
*/
public interface FerrariRoleMenuDao {
	public Integer insertFerrariMenu(FerrariRoleMenu ferrariRoleMenu);
	public Integer updateFerrariMenu(FerrariRoleMenu ferrariRoleMenu);
    public List<FerrariRoleMenu> getFerrariMenuList();
	public List<FerrariRoleMenu> getFerrariUserMenuList(Integer userId);
    public Integer insertFerrariRoleMenu(FerrariRoleMenu ferrariRoleMenu);
    public Integer deleteFerrariRoleMenu(@Param(value = "roleId") Integer roleId);
    public Integer hasMenuAuthority(@Param(value = "menuUrl") String menuUrl, @Param(value = "mobile") String mobile);
    public List<FerrariRoleMenu> getFerrariMenuListByRoleId(@Param(value = "roleId") Integer roleId);
}
