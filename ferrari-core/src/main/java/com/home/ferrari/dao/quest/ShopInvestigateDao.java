package com.home.ferrari.dao.quest;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.quest.QuestInvestigateCount;
import com.home.ferrari.vo.quest.ShopInvestigate;

public interface ShopInvestigateDao {
	
	public Integer insertShopInvestigate(ShopInvestigate shopInvestigate);
	
	public Integer updateShopInvestigate(ShopInvestigate shopInvestigate);

	public Integer updateShopInvestigateIsRead(@Param(value = "id") Integer id);
	
	public ShopInvestigate getShopInvestigateByShopIdAndInvestigateId(@Param(value = "shopId") Integer shopId, @Param(value = "investigateId") Integer investigateId, @Param(value = "subMsgType") Integer subMsgType);

	public List<Map<String, Object>> getAgreeListByInvestigateId(@Param(value = "investigateId") Integer investigateId, @Param(value = "shopId") Integer shopId);
	
	public List<Map<String, Object>> getReadListByInvestigateId(@Param(value = "investigateId") Integer investigateId, @Param(value = "shopId") Integer shopId);
	
	public List<ShopInvestigate> getShopInvestigateListByInvestigateIdAndReply(@Param(value = "investigateId") Integer investigateId, @Param(value = "isReply") Integer isReply);
	
	public ShopInvestigate getShopInvestigateDetailById(@Param(value = "shopInvestigateId") Integer shopInvestigateId); 

	public List<QuestInvestigateCount> getShopInvestigateListByShopId(@Param(value = "page") Page<?> page, @Param(value = "shopId") Integer shopId, @Param(value = "isReply") Integer isReply, @Param(value = "platform") Integer platform); 

	public Integer countShopInvestigateListByShopId(@Param(value = "shopId") Integer shopId, @Param(value = "isReply") Integer isReply, @Param(value = "platform") Integer platform); 
	
	public List<Map<String, Object>> getExportMessageByMsgType(@Param(value = "page") Page<?> page, @Param(value = "msgType") Integer msgType);
}
