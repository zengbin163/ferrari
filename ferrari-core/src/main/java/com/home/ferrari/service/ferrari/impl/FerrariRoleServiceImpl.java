package com.home.ferrari.service.ferrari.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.dao.ferrari.FerrariRoleDao;
import com.home.ferrari.dao.ferrari.FerrariRoleMenuDao;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.service.ferrari.FerrariRoleService;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.ferrari.user.FerrariRole;
import com.home.ferrari.vo.ferrari.user.FerrariRoleMenu;

@Service
public class FerrariRoleServiceImpl implements FerrariRoleService {
	@Autowired
	private FerrariRoleDao ferrariRoleDao;
	@Autowired
	private FerrariRoleMenuDao ferrariRoleMenuDao;

	@Override
	@Transactional
	public Map<String, Object> createRole(String roleName, String menuIds, Integer roleType) {
		Assert.notNull(roleName, "角色名称不能为空");
		Assert.notNull(menuIds, "菜单id集合不能为空");
		Assert.notNull(roleType, "角色类型不能为空");
		FerrariRole ferrariRole = this.ferrariRoleDao.getFerrariRoleByName(roleName);
		if(null != ferrariRole) {
			throw new FerrariBizException(ResultCode.ROLE_IS_EXISTS, String.format("角色名称重复,roleName=%s", roleName));
		}
		String []mIds = menuIds.split(",");
		ferrariRole = new FerrariRole(roleName, roleType);
		this.ferrariRoleDao.insertFerrariRole(ferrariRole);
		Integer roleId = ferrariRole.getId();
		if(null == roleId) {
			throw new FerrariBizException(ResultCode.SAVE_FAIL, String.format("新增角色失败,roleName=%s", roleName));
		}
		for (int i = 0; i < mIds.length; i++) {
			FerrariRoleMenu ferrariRoleMenu = new FerrariRoleMenu(roleId, Integer.parseInt(mIds[i]));
			this.ferrariRoleMenuDao.insertFerrariRoleMenu(ferrariRoleMenu);
		}
		return ResultUtil.successMap("success");
	}

	@Override
	@Transactional
	public Map<String, Object> editRole(Integer roleId, String roleName, String menuIds, Integer roleType) {
		Assert.notNull(roleId, "角色id不能为空");
		Assert.notNull(roleName, "角色名称不能为空");
		Assert.notNull(menuIds, "菜单id集合不能为空");
		Assert.notNull(roleType, "角色类型不能为空");
		FerrariRole ferrariRole = new FerrariRole(roleName, roleType);
		ferrariRole.setId(roleId);
		Integer flag = this.ferrariRoleDao.updateFerrariRole(ferrariRole);
		if(flag < 1) {
			throw new FerrariBizException(ResultCode.UPDATE_FAIL, String.format("更新角色失败,roleId=%s", roleId));
		}
		String []mIds = menuIds.split(",");
		this.ferrariRoleMenuDao.deleteFerrariRoleMenu(roleId);
		for (int i = 0; i < mIds.length; i++) {
			FerrariRoleMenu ferrariRoleMenu = new FerrariRoleMenu(roleId, Integer.parseInt(mIds[i]));
			this.ferrariRoleMenuDao.insertFerrariRoleMenu(ferrariRoleMenu);
		}
		return ResultUtil.successMap("success");
	}
	
	public Map<String, Object> getFerrariRoleList() {
		return ResultUtil.successMap(this.ferrariRoleDao.getFerrariRoleList());
	} 
	
	@Transactional
	public Map<String, Object> deleteRole(Integer roleId) {
		Assert.notNull(roleId, "角色id不能为空");
		this.ferrariRoleDao.deleteRoleById(roleId);
		this.ferrariRoleMenuDao.deleteFerrariRoleMenu(roleId);
		return ResultUtil.successMap("success");
	}
}
