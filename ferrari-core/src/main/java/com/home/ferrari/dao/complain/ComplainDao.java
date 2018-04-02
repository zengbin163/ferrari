package com.home.ferrari.dao.complain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.complain.Complain;
import com.home.ferrari.vo.complain.ComplainShop;
import com.home.ferrari.vo.complain.ComplainShopRemaind;
import com.home.ferrari.vo.complain.ComplainTrace;

public interface ComplainDao {
	public Integer insertComplain(Complain complain);
	public Integer insertComplainBatch(List<Complain> list);
	public Integer insertComplainTrace(ComplainTrace complainTrace);
	public Integer updateComplain(Complain complain);
	public List<Complain> getComplainList(@Param(value = "page") Page<?> page,
			@Param(value = "complainType") Integer complainType,
			@Param(value = "isFixed") Integer isFixed,
			@Param(value = "beginTime") String beginTime,
			@Param(value = "endTime") String endTime,
			@Param(value = "isRight") Integer isRight,
			@Param(value = "complainStatus") Integer complainStatus,
			@Param(value = "complainDegree") Integer complainDegree,
			@Param(value = "bizOrderId") String bizOrderId,
			@Param(value = "shopIdList") List<Integer> shopIdList);
	public Integer countComplainList(
			@Param(value = "complainType") Integer complainType,
			@Param(value = "isFixed") Integer isFixed,
			@Param(value = "beginTime") String beginTime,
			@Param(value = "endTime") String endTime,
			@Param(value = "isRight") Integer isRight,
			@Param(value = "complainStatus") Integer complainStatus,
			@Param(value = "complainDegree") Integer complainDegree,
			@Param(value = "bizOrderId") String bizOrderId,
			@Param(value = "shopIdList") List<Integer> shopIdList);
	public List<Map<String, Object>> getComplainGroupList(
			@Param(value = "complainType") Integer complainType,
			@Param(value = "isFixed") Integer isFixed,
			@Param(value = "beginTime") String beginTime,
			@Param(value = "endTime") String endTime,
			@Param(value = "isRight") Integer isRight,
			@Param(value = "complainStatus") Integer complainStatus,
			@Param(value = "complainDegree") Integer complainDegree,
			@Param(value = "bizOrderId") String bizOrderId,
			@Param(value = "shopIdList") List<Integer> shopIdList);
	
	public Complain getComplainDetailById(@Param(value = "complainId")Integer complainId);
	public List<ComplainTrace> getComplainTraceListById(@Param(value = "complainId")Integer complainId);
	
	public Integer insertComplainShop(ComplainShop complainShop);
	public Integer insertOrUpdateComplainShopRemaind(ComplainShopRemaind complainShopRemaind);
	public List<ComplainShop> getComplainShopListByShopId(
			@Param(value = "shopId") Integer shopId,
			@Param(value = "shopName") String shopName,
			@Param(value = "province") String province,
			@Param(value = "city") String city);
	
	public List<Complain> getRemindComplainList(@Param(value = "page") Page<?> page);
	public Integer countRemindComplainList();

	public List<ComplainShop> getRemindShopList(
			@Param(value = "page") Page<?> page,
			@Param(value = "isThreeDay") Integer isThreeDay,
			@Param(value = "shopStatusList") String[] shopStatusList,
			@Param(value = "shopName") String shopName,
			@Param(value = "province") String province,
			@Param(value = "city") String city);
	public Integer countRemindShopList(
			@Param(value = "isThreeDay") Integer isThreeDay,
			@Param(value = "shopStatusList") String[] shopStatusList,
			@Param(value = "shopName") String shopName,
			@Param(value = "province") String province,
			@Param(value = "city") String city);
}
