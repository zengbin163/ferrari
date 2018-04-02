package com.home.ferrari.web.site.crm;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.crm.CrmComplainService;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/complain")
public class CrmComplainAction extends BaseAction {

	private static final long serialVersionUID = 2477151474792449583L;
	@Autowired
	private CrmComplainService crmComplainService;
	
	/**
	 * 创建反馈
	 * @return
	 */
	@RequestMapping("create")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> create() {
		String remark = this.getFilteredParameter(request, "remark", 0, null);
		return this.crmComplainService.saveComplain(remark);
	}
	
	/**
	 * 反馈列表
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> list() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		return this.crmComplainService.getCrmComplainList(pageOffset, pageSize, null);
	}
	
	/**
	 * 反馈详情
	 * @return
	 */
	@RequestMapping("detail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> detail() {
		Integer complainId = this.getIntParameter(request, "complainId", null);
		return this.crmComplainService.getCrmComplainById(complainId);
	}
}
