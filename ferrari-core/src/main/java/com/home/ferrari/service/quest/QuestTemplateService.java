package com.home.ferrari.service.quest;

import java.util.List;
import java.util.Map;

import com.home.ferrari.vo.quest.QuestInvestigate;
import com.home.ferrari.vo.quest.QuestTemplate;
import com.home.ferrari.vo.quest.ShopInvestigate;
import com.home.ferrari.vo.tesla.shop.TeslaShop;

public interface QuestTemplateService {

	public Map<String, Object> saveQuestTemplate(QuestTemplate questTemplate);

	public Map<String, Object> getQuestTemplateListByType(Integer pageOffset, Integer pageSize, Integer tempType, Integer questType);

	public Map<String, Object> deleteQuestTemplateById(Integer id);

	public Map<String, Object> getQuestTemplateById(Integer id);

	public Map<String, Object> updateQuestTemplateById(QuestTemplate questTemplate);

	public Map<String, Object> getQuestShopList(Integer pageOffset, Integer pageSize, String province, String city, String capacityName, String brandName, String shopName);

	public List<TeslaShop> getMessageShopList(String province, String city, String capacityName, Integer shopType);
	
	public Integer saveQuestInvestigate(QuestInvestigate questInvestigate);
	
	public QuestInvestigate getQuestInvestigateById(Integer investigateId);

	public Map<String, Object> saveShopInvestigate(ShopInvestigate shopInvestigate);

	public Map<String, Object> updateShopInvestigate(ShopInvestigate shopInvestigate);
	
	public Map<String, Object> deleteInvestigateById(Integer investigateId);
	
	/**
	 * 根据已读未读，是否回复查询消息
	 * @param investigateId
	 * @param isReply 0未回复  1已回复   2未阅读   3已阅读
	 * @return
	 */
	public Map<String, Object> getShopInvestigateListByQuestIdAndReply(Integer investigateId, Integer isReply);
	
	public Map<String, Object> getShopInvestigateDetailById(Integer shopInvestigateId);
	
	public Map<String, Object> getShopInvestigateListByShopId(Integer pageOffset, Integer pageSize, Integer shopId, Integer isReply, Integer platform); 
	
	/**
	 * 发送消息
	 * @param operatorId 操作人
	 * @param shopIds 门店id集合或者星奥收件人id集合
	 * @param questId 消息模板id
	 * @param msgType 消息类型
	 * @param days 有效天数
	 * @param title 消息标题
	 * @param text 消息内容
	 * @return
	 */
	public Map<String, Object> sendMessage(Integer operatorId, String shopIds,
			Integer questId, Integer msgType, Long days, String title,
			String text, Integer subMsgType, String province, String city,
			String capacityName, Integer shopType, Integer isSms);
     
	/**
	 * 给其他门店发消息
	 * @param operatorId
	 * @param shopIds
	 * @param investigateId
	 * @param msgType
	 * @return
	 */
	public Map<String, Object> sendOthersMessage(Integer operatorId,
			String shopIds, Integer investigateId, Integer msgType,
			Integer subMsgType, String province, String city,
			String capacityName, Integer shopType, Integer isSms);

	/**
	 * 按照消息类型查询消息
	 * @param msgType
	 * @return
	 */
	public Map<String, Object> messageList(Integer pageOffset, Integer pageSize, Integer msgType, Integer roleType, Integer operatorId);
	
	/**
	 * 阅读消息
	 * @param shopInvestigateId
	 * @return
	 */
	public Map<String, Object> readMsg(Integer shopInvestigateId);

	/**
	 * 查询消息体的统计结果
	 * @param investigateId
	 * @param isReply 0未回复  1已回复   2未阅读   3已阅读
	 * @return
	 */
	public Map<String, Object> msgStat(Integer investigateId, Integer isReply);
	
	/**
	 * 查询消息体详情
	 * @param investigateId
	 * @return
	 */
	public Map<String,Object> msgBody(Integer investigateId);
	
	/**
	 * 按照消息类型导出消息
	 * @param msgType
	 * @return
	 */
	public List<Map<String, Object>> getExportMessageByMsgType(Integer pageOffset, Integer pageSize, Integer msgType);
}
