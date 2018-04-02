package com.home.ferrari.service.ferrari.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.dao.ferrari.FerrariRoleDao;
import com.home.ferrari.dao.ferrari.FerrariRoleMenuDao;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.service.ferrari.FerrariMenuService;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.ferrari.user.FerrariRole;
import com.home.ferrari.vo.ferrari.user.FerrariRoleMenu;

@Service
public class FerrariMenuServiceImpl implements FerrariMenuService {
	
	@Autowired
	private FerrariRoleDao ferrariRoleDao;
	@Autowired
	private FerrariRoleMenuDao ferrariRoleMenuDao;
	
	@Override
	public Map<String, Object> saveMenu(Integer parentMenuId, String menuName,
			String menuEnName, String menuUrl, Integer isMenu) {
		
		FerrariRoleMenu menu = new FerrariRoleMenu(parentMenuId, menuName, menuEnName, menuUrl, isMenu);
		Integer menuId = this.ferrariRoleMenuDao.insertFerrariMenu(menu);
		if(menuId < 0) {
			throw new FerrariBizException(ResultCode.SAVE_FAIL, "新增菜单失败");
		}
		return ResultUtil.successMap(menuId);
	}
	
	public Map<String, Object> editMenu(Integer menuId, Integer parentMenuId,
			String menuName, String menuEnName, String menuUrl, Integer isMenu) {
		FerrariRoleMenu menu = new FerrariRoleMenu(menuId, parentMenuId,
				menuName, menuEnName, menuUrl, isMenu);
		Integer flag = this.ferrariRoleMenuDao.updateFerrariMenu(menu);
		if(flag < 0) {
			throw new FerrariBizException(ResultCode.UPDATE_FAIL, "更新菜单失败");
		}
		return ResultUtil.successMap(flag);
	}

	@Override
	public Map<String, Object> getFerrariMenuList() {
		return ResultUtil.successMap(this.ferrariRoleMenuDao.getFerrariMenuList());
	}
	
	@Override
	public Map<String, Object> getFerrariRoleMenuList() {
		List<FerrariRole> roleList = this.ferrariRoleDao.getFerrariRoleList();
		if(CollectionUtils.isEmpty(roleList)) {
			throw new FerrariBizException(ResultCode.ROLE_NOT_EXISTS, "角色信息未配置");
		}
		for(FerrariRole role : roleList) {
			role.setRoleMenuList(this.ferrariRoleMenuDao.getFerrariMenuListByRoleId(role.getId()));
		}
		return ResultUtil.successMap(roleList);
	}

}
