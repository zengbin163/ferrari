package com.home.ferrari.dao.quest;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.quest.QuestInvestigate;
import com.home.ferrari.vo.quest.QuestInvestigateCount;


public interface QuestInvestigateDao {
	
	public Integer insertQuestInvestigate(QuestInvestigate questInvestigate);
	
	public QuestInvestigate getQuestInvestigateById(@Param(value = "id") Integer id);

	public Integer deleteQuestInvestigateById(@Param(value = "id") Integer id);

	public QuestInvestigate getQuestInvestigateDetailById(@Param(value = "investigateId") Integer investigateId);

	public List<QuestInvestigateCount> getMessageList(
			@Param(value = "page") Page<?> page,
			@Param(value = "msgType") Integer msgType,
			@Param(value = "roleType") Integer roleType,
			@Param(value = "operatorId") Integer operatorId
			);
	
	public Integer countMessageList(@Param(value = "msgType") Integer msgType,
			@Param(value = "roleType") Integer roleType,
			@Param(value = "operatorId") Integer operatorId);
}
