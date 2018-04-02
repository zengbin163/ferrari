package com.home.ferrari.service.complain;

import java.util.List;
import java.util.Map;

import com.home.ferrari.vo.complain.Complain;

public interface ComplainService {

	public Map<String, Object> createComplain(Integer complainType,
			Integer complainSubType, Integer complainReason,
			String complainName, String complainPhone, Integer operatorId,
			Integer shopId, String bizOrderId, Integer complainDegree,
			String beComplainName, String beComplainJob, String text);
	
	public Map<String, Object> createComplainBatch(List<Complain> complainList);

	public Map<String, Object> createComplainTrace(Integer shopId,
			Integer operatorId, Integer complainId, Integer remarkType,
			String remark);

	public Map<String, Object> editComplain(Integer id, Integer complainType,
			Integer complainSubType, Integer complainStatus,
			Integer complainReason, Integer isRight, String complainName,
			String complainPhone, Integer shopId, String bizOrderId,
			Integer complainDegree, String beComplainName,
			String beComplainJob, String text);
	
	public Integer updateComplain(Complain complain);
	
	public Map<String, Object> getComplainList(Integer pageOffset,
			Integer pageSize, Integer complainType, Integer isFixed,
			String beginTime, String endTime, Integer isRight,
			Integer complainStatus, Integer complainDegree, String bizOrderId,
			List<Integer> shopIdList);

	public Map<String, Object> complainDetail(Integer complainId);

	public Map<String, Object> getComplainTraceListById(Integer complainId);
	
	public Map<String, Object> createComplainShop(Integer operatorId, Integer shopId, String remark);

	public Map<String, Object> insertOrUpdateComplainShopRemaind(
			Integer operatorId, Integer shopId, Integer isRemind,
			String remindTime);

	public Map<String, Object> getComplainShopListByShopId(Integer shopId,
			String shopName, String province, String city);
	
	public Map<String, Object> getRemindComplainList(Integer pageOffset, Integer pageSize);
	
	public Map<String, Object> getRemindShopList(Integer pageOffset,
			Integer pageSize, Integer isThreeDay, String[] shopStatusList,
			String shopName, String province, String city);

}
