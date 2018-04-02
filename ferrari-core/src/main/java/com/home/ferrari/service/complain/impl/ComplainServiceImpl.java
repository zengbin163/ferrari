package com.home.ferrari.service.complain.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.dao.complain.ComplainDao;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.service.complain.ComplainService;
import com.home.ferrari.status.ComplainStatus;
import com.home.ferrari.util.DateUtil;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.complain.Complain;
import com.home.ferrari.vo.complain.ComplainShop;
import com.home.ferrari.vo.complain.ComplainShopRemaind;
import com.home.ferrari.vo.complain.ComplainTrace;

@Service
public class ComplainServiceImpl implements ComplainService {

	@Autowired
	private ComplainDao complainDao;

	@Override
	public Map<String, Object> createComplain(Integer complainType,Integer complainSubType,
			Integer complainReason, String complainName, String complainPhone,
			Integer operatorId, Integer shopId, String bizOrderId,
			Integer complainDegree, String beComplainName,
			String beComplainJob, String text) {
		Assert.notNull(complainType, "投诉类型不能为空");
		Assert.notNull(complainName, "投诉人名称不能为空");
		Assert.notNull(complainPhone, "投诉人联系方式不能为空");
		Complain complain = new Complain(complainType, complainSubType,
				ComplainStatus.WAITING, complainReason,
				DefaultEnum.NO.getCode(), complainName, complainPhone,
				operatorId, shopId, bizOrderId, complainDegree, beComplainName,
				beComplainJob, text);
		Integer complainId = this.complainDao.insertComplain(complain);
		return ResultUtil.successMap(complainId);
	}

	@Override
	public Map<String, Object> createComplainBatch(List<Complain> complainList) {
		this.complainDao.insertComplainBatch(complainList);
		return ResultUtil.successMap(ResultUtil.DATA_INSERT_SUCC);
	}
	
	@Override
	public Map<String, Object> createComplainTrace(Integer shopId,
			Integer operatorId, Integer complainId, Integer remarkType,
			String remark) {
		ComplainTrace trace = new ComplainTrace(shopId, operatorId, complainId,
				remarkType, remark);
		Integer complainTraceId = this.complainDao.insertComplainTrace(trace);
		return ResultUtil.successMap(complainTraceId);
	}

	@Override
	public Map<String, Object> editComplain(Integer complainId, Integer complainType,Integer complainSubType,
			Integer complainStatus, Integer complainReason, Integer isRight,
			String complainName, String complainPhone, Integer shopId,
			String bizOrderId, Integer complainDegree, String beComplainName,
			String beComplainJob, String text) {
		Complain complain = new Complain(complainType, complainSubType,
				complainStatus, complainReason, isRight, complainName,
				complainPhone, null, shopId, bizOrderId, complainDegree,
				beComplainName, beComplainJob, text);
		complain.setComplainId(complainId);
		return ResultUtil.successMap(this.updateComplain(complain));
	}

	@Override
	public Integer updateComplain(Complain complain) {
		Integer flag = this.complainDao.updateComplain(complain);
		if(flag < 1) {
			throw new FerrariBizException(ResultCode.UPDATE_FAIL, "投诉更新失败");
		}
		return flag;
	}

	@Override
	public Map<String, Object> getComplainList(Integer pageOffset,
			Integer pageSize, Integer complainType, Integer isFixed,
			String beginTime, String endTime, Integer isRight,
			Integer complainStatus, Integer complainDegree, String bizOrderId,
			List<Integer> shopIdList) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Map<String, Object> map = new HashMap<String, Object>();
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t1.modify_time");
		map.put("list", this.complainDao.getComplainList(page,complainType,isFixed, beginTime, endTime, isRight, complainStatus, complainDegree, bizOrderId, shopIdList));
		map.put("sum", this.complainDao.countComplainList(complainType,isFixed,beginTime, endTime, isRight, complainStatus, complainDegree, bizOrderId, shopIdList));
		map.put("statusList", this.complainDao.getComplainGroupList(complainType, isFixed, beginTime, endTime, isRight, complainStatus, complainDegree, bizOrderId, shopIdList));
		return ResultUtil.successMap(map);
	}
	
	@Override
	public Map<String, Object> complainDetail(Integer complainId) {
		Assert.notNull(complainId, "投诉id不能为空");
		return ResultUtil.successMap(this.complainDao.getComplainDetailById(complainId));
	}
	
	@Override
	public Map<String, Object> getComplainTraceListById(Integer complainId) {
		Assert.notNull(complainId, "投诉id不能为空");
		return ResultUtil.successMap(this.complainDao.getComplainTraceListById(complainId));
	}
	
	@Override
	public Map<String, Object> createComplainShop(Integer operatorId, Integer shopId, String remark) {
		Assert.notNull(shopId, "门店id不能为空");
		Assert.notNull(remark, "门店回访记录不能为空");
		ComplainShop shop = new ComplainShop(operatorId, shopId, remark);
		return ResultUtil.successMap(this.complainDao.insertComplainShop(shop));
	}

	public Map<String, Object> insertOrUpdateComplainShopRemaind(
			Integer operatorId, Integer shopId, Integer isRemind,
			String remindTime) {
		Assert.notNull(operatorId, "operatorId不能为空");
		Assert.notNull(shopId, "shopId不能为空");
		Assert.notNull(isRemind, "门店回访提醒开关不能为空");
		Assert.notNull(remindTime, "门店回访提醒时间不能为空");
		ComplainShopRemaind shop = new ComplainShopRemaind(operatorId, shopId,
				isRemind, DateUtil.defaultParseTime(remindTime));
		return ResultUtil.successMap(this.complainDao.insertOrUpdateComplainShopRemaind(shop));
	}

	public Map<String, Object> getComplainShopListByShopId(Integer shopId,
			String shopName, String province, String city) {
		return ResultUtil.successMap(this.complainDao.getComplainShopListByShopId(shopId, shopName, province, city));
	}
	
	public Map<String, Object> getRemindComplainList(Integer pageOffset, Integer pageSize) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, Page.ORDER_BY_MODIFY_TIME);
		Map<String,Object> map = new HashMap<>();
		map.put("list", this.complainDao.getRemindComplainList(page));
		map.put("sum", this.complainDao.countRemindComplainList());
		return ResultUtil.successMap(map);
	}
	
	public Map<String, Object> getRemindShopList(Integer pageOffset,
			Integer pageSize, Integer isThreeDay, String[] shopStatusList,
			String shopName, String province, String city) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t3.remind_time");
		Map<String,Object> map = new HashMap<>();
		map.put("list", this.complainDao.getRemindShopList(page, isThreeDay,
				shopStatusList, shopName, province, city));
		map.put("sum", this.complainDao.countRemindShopList(isThreeDay,
				shopStatusList, shopName, province, city));
		return ResultUtil.successMap(map);
	}

}
