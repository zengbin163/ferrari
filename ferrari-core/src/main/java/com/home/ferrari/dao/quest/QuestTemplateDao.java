package com.home.ferrari.dao.quest;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.quest.QuestTemplate;
import com.home.ferrari.vo.tesla.shop.TeslaShop;

public interface QuestTemplateDao {
	
	public Integer insertQuestTemplate(QuestTemplate questTemplate);
	
	public List<QuestTemplate> getQuestTemplateListByType(@Param(value = "page") Page<?> page, @Param(value = "tempType") Integer tempType, @Param(value = "questType") Integer questType);

	public Integer countQuestTemplateListByType(@Param(value = "tempType") Integer tempType, @Param(value = "questType") Integer questType);
	
	public List<Map<String, Object>> countQuestTemplateGroupByType();

	public Integer deleteQuestTemplateById(@Param(value = "id") Integer id);

	public Integer updateQuestTemplateById(QuestTemplate questTemplate);
	
	public QuestTemplate getQuestTemplateById(@Param(value = "id") Integer id);
	
	public List<TeslaShop> getQuestShopList(
			@Param(value = "page") Page<?> page,
			@Param(value = "province") String province,
			@Param(value = "city") String city,
			@Param(value = "shopType") Integer shopType,
			@Param(value = "capacityName") String capacityName,
			@Param(value = "brandName") String brandName,
	        @Param(value = "shopName")  String shopName);
	
	public Integer countQuestShopList(
			@Param(value = "province") String province,
			@Param(value = "city") String city,
			@Param(value = "shopType") Integer shopType,
			@Param(value = "capacityName") String capacityName,
			@Param(value = "brandName") String brandName,
	        @Param(value = "shopName")  String shopName);
}
