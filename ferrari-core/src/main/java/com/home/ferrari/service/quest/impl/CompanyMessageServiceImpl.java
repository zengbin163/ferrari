package com.home.ferrari.service.quest.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.ferrari.dao.quest.CompanyMessageDao;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.service.quest.CompanyMessageService;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.quest.CompanyMessage;

@Service
public class CompanyMessageServiceImpl implements CompanyMessageService {
	@Autowired
	private CompanyMessageDao companyMessageDao;

	@Override
	public Map<String, Object> publishCompanyMessage(String title, String text, Integer operatorId, Integer ceoId) {
		CompanyMessage companyMessage = new CompanyMessage();
		companyMessage.setText(text);
		companyMessage.setTitle(title);
		companyMessage.setOperatorId(operatorId);
		companyMessage.setCeoId(ceoId);
		this.companyMessageDao.insertCompanyMessage(companyMessage);
		return ResultUtil.successMap(ResultUtil.DATA_INSERT_SUCC);
	}

	@Override
	public Map<String, Object> getCompanyMessage(Integer pageOffset, Integer pageSize, Integer ceoId) {
		Map<String,Object> map = new HashMap<String, Object>();
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC,
				Page.ORDER_BY_MODIFY_TIME);
		map.put("list", this.companyMessageDao.getCompanyMessage(page,ceoId));
		map.put("sum", this.companyMessageDao.countCompanyMessage(ceoId));
		return ResultUtil.successMap(map);
	}

	@Override
	public Map<String, Object> getQuestRoleTypeByRoleId(Integer roleId) {
		return ResultUtil.successMap(this.companyMessageDao.getQuestRoleTypeByRoleId(roleId));
	}

}
