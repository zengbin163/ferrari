package com.home.ferrari.web.site.quest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.enums.PlatformEnum;
import com.home.ferrari.service.quest.CompanyMessageService;
import com.home.ferrari.service.quest.QuestTemplateService;
import com.home.ferrari.vo.quest.ShopInvestigate;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/shopQuest")
public class ShopQuestAction extends BaseAction {

	private static final long serialVersionUID = -3842521455643985485L;
	@Autowired
	private QuestTemplateService questTemplateService;
	@Autowired
	private CompanyMessageService companyMessageService;

	/**
	 * 门店端查询问卷列表
	 * @return
	 */
	@RequestMapping("getShopInvestigate")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getShopInvestigate() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer isReply = this.getIntParameter(request, "isReply", null);//isReply 0未回复 1已回复 2未阅读 3已阅读
		Integer platform = PlatformEnum.TESLA.getCode();
		Integer shopId = 0;
		if(this.getFerrariUser()!=null) {
			platform = PlatformEnum.FERRARI.getCode();
			shopId = this.getFerrariUser().getId();
		}else{
			shopId = this.getTeslaUser().getShopId();
		}
		return this.questTemplateService.getShopInvestigateListByShopId(
				pageOffset, pageSize, shopId, isReply, platform);
	}

	/**
	 * 门店回复问卷
	 * @return
	 */
	@RequestMapping("shopReply")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> shopReply() {
		Integer shopInvestigateId = this.getIntParameter(request, "shopInvestigateId", null);
		Integer isAgree = this.getIntParameter(request, "isAgree", null);
		String replyTitle = this.getFilteredParameter(request, "replyTitle", 0, null);
		String replyText = this.getFilteredParameter(request, "replyText", 0, null);
		String shopPrice = this.getFilteredParameter(request, "shopPrice", 0, "0");
		Integer operatorId = null;
		if (this.getTeslaUser() != null) {
			operatorId = this.getTeslaUser().getId();
		}else{
			operatorId = this.getFerrariUser().getId();
		}
		ShopInvestigate shopInvestigate = new ShopInvestigate(null, null,
				operatorId, null, null, DefaultEnum.YES.getCode(), isAgree,
				shopPrice == null ? null : new BigDecimal(shopPrice),
				replyTitle, replyText, new Date(), null, null);
		shopInvestigate.setId(shopInvestigateId);
		return this.questTemplateService.updateShopInvestigate(shopInvestigate);
	}
	
	/**
	 * 给总裁发消息
	 * @return
	 */
	@RequestMapping("companyMessage")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> companyMessage() {
		String title = this.getFilteredParameter(request, "title", 0, null);
		String text = this.getFilteredParameter(request, "text", 0, null);
		Integer ceoId = this.getIntParameter(request, "ceoId", null);
		Integer operatorId = -1000;
		if(null!=this.getFerrariUser()) {
			operatorId = this.getFerrariUser().getId();
		}else{
			operatorId = this.getTeslaUser().getId();
		}
		return this.companyMessageService.publishCompanyMessage(title, text, operatorId, ceoId);
	}
	
	/**
	 * 总裁消息列表
	 * @return
	 */
	@RequestMapping("companyMessageList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> companyMessageList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer ceoId = this.getFerrariUser().getId();
		return this.companyMessageService.getCompanyMessage(pageOffset, pageSize, ceoId);
	}
}
