package com.home.ferrari.web.site.ferrari;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.ferrari.FerrariMenuService;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/menu")
public class FerrariMenuAction extends BaseAction {
	
	private static final long serialVersionUID = -6750922500048026033L;
	@Autowired
	private FerrariMenuService ferrariMenuService;
	
	@RequestMapping("saveMenu")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> saveMenu() {
		Integer parentMenuId = this.getIntParameter(request, "parentMenuId", null);
		String menuName = this.getFilteredParameter(request, "menuName", 0, null);
		String menuEnName = this.getFilteredParameter(request, "menuEnName", 0, null);
		String menuUrl = this.getFilteredParameter(request, "menuUrl", 0, null);
		Integer isMenu = this.getIntParameter(request, "isMenu", null);
		return this.ferrariMenuService.saveMenu(parentMenuId, menuName, menuEnName, menuUrl, isMenu);
	}

	@RequestMapping("editMenu")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> editMenu() {
		Integer menuId = this.getIntParameter(request, "menuId", null);
		Integer parentMenuId = this.getIntParameter(request, "parentMenuId", null);
		String menuName = this.getFilteredParameter(request, "menuName", 0, null);
		String menuEnName = this.getFilteredParameter(request, "menuEnName", 0, null);
		String menuUrl = this.getFilteredParameter(request, "menuUrl", 0, null);
		Integer isMenu = this.getIntParameter(request, "isMenu", null);
		return this.ferrariMenuService.editMenu(menuId, parentMenuId, menuName, menuEnName, menuUrl, isMenu);
	}
	
	/**
	 * 查询菜单下拉列表
	 */
	@RequestMapping("menuList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> menuList() {
		return this.ferrariMenuService.getFerrariMenuList();
	}
	
	/**
	 * 角色管理
	 * @return
	 */
	@RequestMapping("roleMenuList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> roleMenuList() {
		return this.ferrariMenuService.getFerrariRoleMenuList();
	}
}
