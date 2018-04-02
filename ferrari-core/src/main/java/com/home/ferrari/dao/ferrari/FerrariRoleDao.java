/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.dao.ferrari;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.vo.ferrari.user.FerrariRole;

/**
 * @ClassName: FerrariRoleDao
 * @Description: 角色信息DAO
 * @author zengbin
 * @date 2016年05月01日 下午3:23:23
 */
public interface FerrariRoleDao {
	public FerrariRole getFerrariRoleByName(@Param(value = "roleName") String roleName);
	public FerrariRole getFerrariRoleById(@Param(value = "roleId") Integer roleId);
	public List<FerrariRole> getFerrariRoleList();
	public Integer insertFerrariRole(FerrariRole ferrariRole);
	public Integer updateFerrariRole(FerrariRole ferrariRole);
	public Integer deleteRoleById(@Param(value = "id") Integer id);
}
