package com.home.ferrari.service.crm.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.home.ferrari.crmdao.crm.CrmComplainDao;
import com.home.ferrari.crmdao.crm.CrmUserDao;
import com.home.ferrari.enums.CrmRoleTypeEnum;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.plugin.session.SessionContainer;
import com.home.ferrari.service.crm.CrmComplainService;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.crm.CrmComplain;
import com.home.ferrari.vo.crm.CrmUser;

@Service
public class CrmComplainServiceImpl implements CrmComplainService {

	@Autowired
	private CrmComplainDao crmComplainDao;
	@Autowired
	private CrmUserDao crmUserDao;
	
	@Override
	public Map<String, Object> saveComplain(String remark) {
		CrmUser crmUser = SessionContainer.getSession().getCrmUser();
		Integer userId = crmUser.getId();
		Integer roleType = crmUser.getRoleType();
		String crmShopName = crmUser.getCrmShopName();
		if(CrmRoleTypeEnum.ROLE_BIZ_ADMIN.getCode().equals(roleType)) {
			crmUser = this.crmUserDao.getCrmUserInfoById(crmUser.getAdminId());
			crmShopName = crmUser.getCrmShopName();
		}
		CrmComplain crmComplain = new CrmComplain();
		crmComplain.setCrmShopName(crmShopName);
		crmComplain.setOperatorId(userId);
		crmComplain.setRemark(remark);
		return ResultUtil.successMap(this.crmComplainDao.insertCrmComplain(crmComplain));
	}

	@Override
	public Map<String, Object> getCrmComplainList(Integer pageOffset, Integer pageSize, Integer operatorId) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, Page.ORDER_BY_CREATE_TIME);
		List<CrmComplain> complainList = this.crmComplainDao.getCrmComplainList(page, operatorId);
		if(CollectionUtils.isNotEmpty(complainList)) {
			for(CrmComplain complain : complainList){
				Integer tempOperatorId = complain.getOperatorId();
				CrmUser crmUser = this.crmUserDao.getCrmUserInfoById(tempOperatorId);
				if (null != crmUser) {
					complain.setOperatorName(crmUser.getUserName());
					complain.setOperatorPhone(crmUser.getMobile());
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", complainList);
		map.put("sum", this.crmComplainDao.countCrmComplainList(operatorId));
		return ResultUtil.successMap(map);
	}

	public Map<String, Object> getCrmComplainById(@Param(value="complainId") Integer complainId) {
		Assert.notNull(complainId, "反馈id不能为空");
		return ResultUtil.successMap(this.crmComplainDao.getCrmComplainById(complainId));
	}

}
