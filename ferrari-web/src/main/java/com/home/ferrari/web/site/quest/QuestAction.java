package com.home.ferrari.web.site.quest;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.quest.CompanyMessageService;
import com.home.ferrari.service.quest.QuestTemplateService;
import com.home.ferrari.vo.quest.QuestTemplate;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/quest")
public class QuestAction extends BaseAction {

	private static final long serialVersionUID = -3842521455643985485L;
	@Autowired
	private QuestTemplateService questTemplateService;
	@Autowired
	private CompanyMessageService companyMessageService;

	/**
	 * 发布问卷模板
	 * @return
	 */
	@RequestMapping("publish")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> publish() {
		String title =this.getFilteredParameter(request, "title", 0, null);
		String richText =this.getFilteredParameter(request, "richText", 0, null);
		String price =this.getFilteredParameter(request, "price", 0, "0");
		Integer tempType = this.getIntParameter(request, "tempType", 1); //消息模板  1问卷调研 2合同模板 3培训模板
		Integer questType = this.getIntParameter(request, "questType", null); //0 新产品问卷     1 已上线产品问卷
		Assert.notNull(title, "标题不能为空");
		Assert.notNull(richText, "富文本内容不能为空");
		//Assert.notNull(price, "产品/服务指导价格不能为空");
		Assert.notNull(tempType, "消息模板不能为空");
		//Assert.notNull(questType, "问卷类型不能为空");
		QuestTemplate questTemplate = new QuestTemplate(title, richText,
				new BigDecimal(price), tempType, questType, getFerrariUser().getId(), 1);
		return this.questTemplateService.saveQuestTemplate(questTemplate);
	}
	
	/**
	 * 问卷模板列表
	 * @return
	 */
	@RequestMapping("templateList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> templateList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer tempType = this.getIntParameter(request, "tempType", null); //消息模板  1问卷调研 2合同模板 3培训模板
		Integer questType = this.getIntParameter(request, "questType", null); //0 新产品问卷     1 已上线产品问卷
		//Assert.notNull(questType, "问卷类型不能为空");
		return this.questTemplateService.getQuestTemplateListByType(pageOffset, pageSize, tempType, questType);
	}
	
	/**
	 * 删除问卷模板
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> delete() {
		Integer questId = this.getIntParameter(request, "questId", null);
		return this.questTemplateService.deleteQuestTemplateById(questId);
	}

	/**
	 * 编辑问卷模板
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> update() {
		Integer questId = this.getIntParameter(request, "questId", null);
		String title =this.getFilteredParameter(request, "title", 0, null);
		String richText =this.getFilteredParameter(request, "richText", 0, null);
		String price =this.getFilteredParameter(request, "price", 0, "0");
		Integer tempType = this.getIntParameter(request, "tempType", null);
		Integer questType = this.getIntParameter(request, "questType", null); //0 新产品问卷     1 已上线产品问卷
		Assert.notNull(title, "标题不能为空");
		Assert.notNull(richText, "富文本内容不能为空");
		//Assert.notNull(price, "产品/服务指导价格不能为空");
		//Assert.notNull(questType, "问卷类型不能为空");
		QuestTemplate questTemplate = new QuestTemplate(title, richText,
				new BigDecimal(price), tempType, questType, getFerrariUser().getId(), 1);
		questTemplate.setId(questId);
		return this.questTemplateService.updateQuestTemplateById(questTemplate);
	}

	/**
	 * 查看问卷模板详情
	 * @return
	 */
	@RequestMapping("detail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> detail() {
		Integer questId = this.getIntParameter(request, "questId", null);
		Assert.notNull(questId, "questId不能为空");
		return this.questTemplateService.getQuestTemplateById(questId);
	}
	
	/**
	 * 市场人员根据能力查询门店列表
	 * @return
	 */
	@RequestMapping("shopList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> shopList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		String province =this.getFilteredParameter(request, "province", 0, null);
		String city =this.getFilteredParameter(request, "city", 0, null);
		String capacityName =this.getFilteredParameter(request, "capacityName", 0, null);
		String brandName =this.getFilteredParameter(request, "brandName", 0, null);
		String shopName =this.getFilteredParameter(request, "shopName", 0, null);
		return this.questTemplateService.getQuestShopList(pageOffset, pageSize, province, city, capacityName, brandName, shopName);
	}

	/**
	 * 市场人员批量给门店发送问卷（发送消息）
	 * @return
	 */
	@RequestMapping("saveInvestigate")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> saveInvestigate() {
		String shopIds = this.getFilteredParameter(request, "shopIds", 0, null);
		Integer questId = this.getIntParameter(request, "questId", null);
		Integer msgType = this.getIntParameter(request, "msgType", 1);
		Integer subMsgType = this.getIntParameter(request, "subMsgType", null);
		String province =this.getFilteredParameter(request, "province", 0, null);
		String city =this.getFilteredParameter(request, "city", 0, null);
		String capacityName =this.getFilteredParameter(request, "capacityName", 0, null);
		Integer shopType = this.getIntParameter(request, "shopType", null);
		Long days = this.getLongParameter(request, "days", 0L);
		String title =this.getFilteredParameter(request, "title", 0, null);
		String text =this.getFilteredParameter(request, "text", 0, null);
		Integer isSms = this.getIntParameter(request, "isSms", 0);//0不发短信  1发短信
		return this.questTemplateService.sendMessage(getFerrariUser().getId(),
				shopIds, questId, msgType, days, title, text, subMsgType,
				province, city, capacityName, shopType, isSms);
	}

	/**
	 * 市场人员给其他门店发送同样的问卷
	 * @return
	 */
	@RequestMapping("saveOtherInvestigate")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> saveOtherInvestigate() {
		String shopIds = this.getFilteredParameter(request, "shopIds", 0, null);
		Integer investigateId = this.getIntParameter(request, "investigateId", null);
		Integer msgType = this.getIntParameter(request, "msgType", 1);
		Integer subMsgType = this.getIntParameter(request, "subMsgType", null);
		String province =this.getFilteredParameter(request, "province", 0, null);
		String city =this.getFilteredParameter(request, "city", 0, null);
		String capacityName =this.getFilteredParameter(request, "capacityName", 0, null);
		Integer shopType = this.getIntParameter(request, "shopType", null);
		Integer isSms = this.getIntParameter(request, "isSms", 0);//0不发短信  1发短信
		return this.questTemplateService.sendOthersMessage(getFerrariUser()
				.getId(), shopIds, investigateId, msgType, subMsgType,
				province, city, capacityName, shopType, isSms);
	}
	
	/**
	 * 删除问卷
	 * @return
	 */
	@RequestMapping("deleteInvestigate")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> deleteInvestigate() {
		Integer investigateId = this.getIntParameter(request, "investigateId", null);
		return this.questTemplateService.deleteInvestigateById(investigateId);
	}
	
	/**
	 * 市场人员查看自己给门店发送的问卷回复和未回复
	 * @return
	 */
	@RequestMapping("shopInvestigateList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> shopInvestigateList() {
		Integer investigateId = this.getIntParameter(request, "investigateId", null);
		Integer isReply = this.getIntParameter(request, "isReply", null);//1 已回复    0 未回复
		return this.questTemplateService.getShopInvestigateListByQuestIdAndReply(investigateId, isReply);
	}

	/**
	 * 消息详情
	 * @return
	 */
	@RequestMapping("shopInvestigateDetail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> shopInvestigateDetail() {
		Integer shopInvestigateId = this.getIntParameter(request, "shopInvestigateId", null);
		return this.questTemplateService.getShopInvestigateDetailById(shopInvestigateId);
	}

	/**
	 * 消息体详情
	 * @return
	 */
	@RequestMapping("msgBody")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> msgBody() {
		Integer investigateId = this.getIntParameter(request, "investigateId", null);
		return this.questTemplateService.msgBody(investigateId);
	}

	@RequestMapping("msgList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> msgList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer msgType = this.getIntParameter(request, "msgType", null);
		return this.questTemplateService.messageList(pageOffset, pageSize,
				msgType, this.getFerrariUser().getRoleType(), this.getFerrariUser().getId());
	}
	
	@RequestMapping("readMsg")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> readMsg() {
		Integer shopInvestigateId = this.getIntParameter(request, "shopInvestigateId", null);
		return this.questTemplateService.readMsg(shopInvestigateId);
	}
	
	@RequestMapping("msgStat")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> msgStat() {
		Integer investigateId = this.getIntParameter(request, "investigateId", null);
		Integer isReply = this.getIntParameter(request, "isReply", null);
		return this.questTemplateService.msgStat(investigateId, isReply);
	}
	
	@RequestMapping("msgType")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> msgType() {
		Integer roleType = this.getFerrariUser().getRoleType();
		return this.companyMessageService.getQuestRoleTypeByRoleId(roleType);
	}
}
