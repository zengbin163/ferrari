package com.home.ferrari.web.site.complain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.service.complain.ComplainService;
import com.home.ferrari.status.ComplainStatus;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.complain.Complain;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/shopComplain")
public class ShopComplainAction extends BaseAction {

	private static final long serialVersionUID = 5775390963830613826L;
	@Autowired
	private ComplainService complainService;

	/**
	 * 创建门店回访记录
	 * @return
	 */
	@RequestMapping("createComplainShop")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> createComplainShop() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		String remark = this.getFilteredParameter(request, "remark", 0, null);
		return this.complainService.createComplainShop(this.getFerrariUser().getId(), shopId, remark);
	}

	/**
	 * 编辑门店回访记录（提醒时间/是否提醒）
	 * @return
	 */
	@RequestMapping("editComplainShopRemaind")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> editComplainShopRemaind() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		Integer isRemind = this.getIntParameter(request, "isRemind", null);
		String remindTime = this.getFilteredParameter(request, "remindTime", 0, null);
		return this.complainService.insertOrUpdateComplainShopRemaind(this
				.getFerrariUser().getId(), shopId, isRemind, remindTime);
	}

	/**
	 * 查询门店回访记录
	 * @return
	 */
	@RequestMapping("complainShopList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> complainShopList() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		String shopName = this.getFilteredParameter(request, "shopName", 0, null);
		String province = this.getFilteredParameter(request, "province", 0, null);
		String city = this.getFilteredParameter(request, "city", 0, null);
		return this.complainService.getComplainShopListByShopId(shopId, shopName, province, city);
	}

	/**
	 * 查询三天内未做门店回访的记录
	 * @return
	 */
	@RequestMapping("getRemindShopList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getRemindShopList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer isThreeDay = this.getIntParameter(request, "isThreeDay", null);
		String shopName = this.getFilteredParameter(request, "shopName", 0, null);
		String province = this.getFilteredParameter(request, "province", 0, null);
		String city = this.getFilteredParameter(request, "city", 0, null);
		String shopStatus = this.getFilteredParameter(request, "shopStatus", 0, null);
		String[] shopStatusList = null;
		if(!StringUtils.isEmpty(shopStatus)) {
			shopStatusList = shopStatus.split(",");
		}
		return this.complainService.getRemindShopList(pageOffset, pageSize,
				isThreeDay, shopStatusList, shopName, province, city);
	}
	
	/**
	 * 查询投诉列表
	 * @return
	 */
	@RequestMapping("complainList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> complainList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer isRight = this.getIntParameter(request, "isRight", null);
		Integer complainStatus = this.getIntParameter(request, "complainStatus", null);
		List<Integer> shopIdList = new ArrayList<>();
		shopIdList.add(this.getTeslaUser().getShopId());
		return this.complainService.getComplainList(pageOffset, pageSize, null,
				null, null, null, isRight, complainStatus, null, null,
				shopIdList);
	}
	
	/**
	 * 发起申诉
	 * @return
	 */
	@RequestMapping("right")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> right() {
		Integer complainId = this.getIntParameter(request, "complainId", null);
		Complain complain = new Complain();
		complain.setComplainId(complainId);
		complain.setIsRight(DefaultEnum.YES.getCode());
		complain.setComplainStatus(ComplainStatus.DOING);
		return ResultUtil.successMap(this.complainService.updateComplain(complain));
	}
	
	
}
