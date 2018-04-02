package com.home.ferrari.service.quest;

import java.util.Map;

public interface CompanyMessageService {
	public Map<String, Object> publishCompanyMessage(String title, String text, Integer operatorId, Integer ceoId);

	public Map<String, Object> getCompanyMessage(Integer pageOffset, Integer pageSize, Integer ceoId);

	public Map<String, Object> getQuestRoleTypeByRoleId(Integer roleId);

}
