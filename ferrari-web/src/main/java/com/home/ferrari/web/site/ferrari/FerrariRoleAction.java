package com.home.ferrari.web.site.ferrari;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.ferrari.FerrariRoleService;
import com.home.ferrari.util.WebUtil;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/role")
public class FerrariRoleAction extends BaseAction {

	private static final long serialVersionUID = 7154841574244870282L;

	@Autowired
	private FerrariRoleService ferrariRoleService;

	/**
	 * 创建角色
	 * @param roleName 角色名称
	 * @param menuIds 根据","分隔的菜单id
	 */
	@RequestMapping("create")
	@ResponseBody
	@LoginRequired(needLogin = true, needAuth=true)
	public Map<String, Object> create() {
		String roleName = WebUtil.decode(this.getFilteredParameter(request, "roleName", 0, null));
		String menuIds = WebUtil.decode(this.getFilteredParameter(request, "menuIds", 0, null));
		Integer roleType = this.getIntParameter(request, "roleType", null);
		return this.ferrariRoleService.createRole(roleName, menuIds, roleType);
	}
	
	/**
	 * 编辑角色
	 * @param roleId 角色id
	 * @param roleName 角色名称
	 * @param menuIds 根据","分隔的菜单id
	 */
	@RequestMapping("edit")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> edit() {
		Integer roleId = this.getIntParameter(request, "roleId", null);
		String roleName = WebUtil.decode(this.getFilteredParameter(request, "roleName", 0, null));
		String menuIds = WebUtil.decode(this.getFilteredParameter(request, "menuIds", 0, null));
		Integer roleType = this.getIntParameter(request, "roleType", null);
		return this.ferrariRoleService.editRole(roleId, roleName, menuIds, roleType);
	}


	/**
	 * 查询角色下拉列表
	 */
	@RequestMapping("roleList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> roleList() {
		return this.ferrariRoleService.getFerrariRoleList();
	}

	/**
	 * 删除角色信息
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> delete() {
		Integer roleId = this.getIntParameter(request, "roleId", null);
		return this.ferrariRoleService.deleteRole(roleId);
	}
	
}
