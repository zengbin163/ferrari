package com.home.ferrari.web.site.complain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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
@RequestMapping("/complain")
public class ComplainAction extends BaseAction {

	private static final long serialVersionUID = -2389762532099714415L;

	@Autowired
	private ComplainService complainService;
	
	/**
	 * 创建投诉
	 * @return
	 */
	@RequestMapping("createComplain")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> createComplain() {
		Integer complainType = this.getIntParameter(request, "complainType", null);
		Integer complainSubType = this.getIntParameter(request, "complainSubType", null);
		Integer complainReason = this.getIntParameter(request, "complainReason", null);
		String complainName = this.getFilteredParameter(request, "complainName", 0, null);
		String complainPhone = this.getFilteredParameter(request, "complainPhone", 0, null);
		Integer shopId = this.getIntParameter(request, "shopId", null);
		String bizOrderId = this.getFilteredParameter(request, "bizOrderId", 0, null);
		Integer complainDegree = this.getIntParameter(request, "complainDegree", null);
		String beComplainName = this.getFilteredParameter(request, "beComplainName", 0, null);
		String beComplainJob = this.getFilteredParameter(request, "beComplainJob", 0, null);
		String text = this.getFilteredParameter(request, "text", 0, null);
		return this.complainService.createComplain(complainType,
				complainSubType, complainReason, complainName, complainPhone,
				this.getFerrariUser().getId(), shopId, bizOrderId,
				complainDegree, beComplainName, beComplainJob, text);
	}

	/**
	 * 编辑投诉
	 * @return
	 */
	@RequestMapping("editComplain")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> editComplain() {
		Integer complainId = this.getIntParameter(request, "complainId", null);
		Integer complainType = this.getIntParameter(request, "complainType", null);
		Integer complainSubType = this.getIntParameter(request, "complainSubType", null);
		Integer complainReason = this.getIntParameter(request, "complainReason", null);
		String complainName = this.getFilteredParameter(request, "complainName", 0, null);
		String complainPhone = this.getFilteredParameter(request, "complainPhone", 0, null);
		Integer shopId = this.getIntParameter(request, "shopId", null);
		String bizOrderId = this.getFilteredParameter(request, "bizOrderId", 0, null);
		Integer complainDegree = this.getIntParameter(request, "complainDegree", null);
		String beComplainName = this.getFilteredParameter(request, "beComplainName", 0, null);
		String beComplainJob = this.getFilteredParameter(request, "beComplainJob", 0, null);
		String text = this.getFilteredParameter(request, "text", 0, null);
		return this.complainService.editComplain(complainId, complainType,
				complainSubType, null, complainReason, null, complainName,
				complainPhone, shopId, bizOrderId, complainDegree,
				beComplainName, beComplainJob, text);
	}
	
	/**
	 * 记录投诉备注
	 * @return
	 */
	@RequestMapping("recordTrace")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> recordTrace() {
		Integer complainId = this.getIntParameter(request, "complainId", null);
		Integer isRight = this.getIntParameter(request, "isRight", null);//是否发起申诉  0未发起申诉   1已发起申诉
		String remark = this.getFilteredParameter(request, "remark", 0, null);
		Assert.notNull(complainId, "投诉id不能为空");
		Assert.notNull(isRight, "是否发起申诉不能为空");
		Integer operatorId = null;
		Integer shopId = null;
		Integer remarkType = 1; //备注类型 1客服备注  2门店备注  3门店申诉备注
		if (null != this.getFerrariUser()) {
			operatorId = this.getFerrariUser().getId();
		} else {
			operatorId = this.getTeslaUser().getId();
			shopId = this.getTeslaUser().getShopId();
			if (DefaultEnum.NO.getCode().equals(isRight)) {
				remarkType = 2;
			} else {
				remarkType = 3;
				//新增申诉备注修改modifyTime
				Complain complain = new Complain();
				complain.setComplainId(complainId);
				this.complainService.updateComplain(complain);
			}
		}
		return this.complainService.createComplainTrace(shopId, operatorId, complainId, remarkType, remark);
	}
	
	/**
	 * 修改投诉状态
	 * @return
	 */
	@RequestMapping("alterStatus")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> alterStatus() {
		Integer complainId = this.getIntParameter(request, "complainId", null);
		Integer complainStatus = this.getIntParameter(request, "complainStatus", null);
		Complain complain = new Complain();
		complain.setComplainId(complainId);
		complain.setComplainStatus(complainStatus);
		return ResultUtil.successMap(this.complainService.updateComplain(complain));
	}
	
	/**
	 * 投诉解决成功或者失败
	 * @return
	 */
	@RequestMapping("fixComplain")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> fixComplain() {
		Integer complainId = this.getIntParameter(request, "complainId", null);
		Integer isFixed = this.getIntParameter(request, "isFixed", null);
		Complain complain = new Complain();
		complain.setComplainId(complainId);
		complain.setIsFixed(isFixed);
		complain.setComplainStatus(ComplainStatus.DONE);
		return ResultUtil.successMap(this.complainService.updateComplain(complain));
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
		Integer complainType = this.getIntParameter(request, "complainType", null);//投诉类型  1订单类投诉 2非订单类投诉
		Integer isFixed = this.getIntParameter(request, "isFixed", null);//是否解决  0解决失败   1解决成功
		String beginTime = this.getFilteredParameter(request, "beginTime", 0, null);
		String endTime = this.getFilteredParameter(request, "endTime", 0, null);
		Integer isRight = this.getIntParameter(request, "isRight", null);
		Integer complainStatus = this.getIntParameter(request, "complainStatus", null);
		Integer complainDegree = this.getIntParameter(request, "complainDegree", null);
		String bizOrderId = this.getFilteredParameter(request, "bizOrderId", 0, null);
		String shopIds = this.getFilteredParameter(request, "shopIds", 0, null);
		List<Integer> shopIdList = null;
		if(StringUtils.isNotBlank(shopIds)) {
			shopIdList = new ArrayList<>();
			String []ids = shopIds.split(",");
			for(int i=0;i<ids.length;i++) {
				shopIdList.add(Integer.parseInt(ids[i]));
			}
		}
		return this.complainService.getComplainList(pageOffset, pageSize,
				complainType, isFixed, beginTime, endTime, isRight,
				complainStatus, complainDegree, bizOrderId, shopIdList);
	}
	
	/**
	 * 查询投诉详情
	 * @return
	 */
	@RequestMapping("complainDetail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> complainDetail() {
		Integer complainId = this.getIntParameter(request, "complainId", null);
		return this.complainService.complainDetail(complainId);
	}
	
	/**
	 * 查询投诉的备注列表
	 * @return
	 */
	@RequestMapping("traceList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> traceList() {
		Integer complainId = this.getIntParameter(request, "complainId", null);
		return this.complainService.getComplainTraceListById(complainId);
	}

	/**
	 * 查询4/12小时未处理的投诉记录
	 * @return
	 */
	@RequestMapping("getRemindComplainList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getRemindComplainList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		return this.complainService.getRemindComplainList(pageOffset, pageSize);
	}
}
