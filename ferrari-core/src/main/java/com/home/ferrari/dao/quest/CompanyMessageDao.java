package com.home.ferrari.dao.quest;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.quest.CompanyMessage;
import com.home.ferrari.vo.quest.QuestRoleType;

public interface CompanyMessageDao {

	public Integer insertCompanyMessage(CompanyMessage companyMessage);

	public List<CompanyMessage> getCompanyMessage(
			@Param(value = "page") Page<?> page,
			@Param(value = "ceoId") Integer ceoId);

	public Integer countCompanyMessage(@Param(value = "ceoId") Integer ceoId);

	public List<QuestRoleType> getQuestRoleTypeByRoleId(@Param(value = "roleId") Integer roleId);
	
}
