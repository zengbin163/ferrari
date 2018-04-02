package com.home.ferrari.service.complain.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.home.ferrari.dao.complain.ComplainReportDao;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.service.complain.ComplainReportService;
import com.home.ferrari.util.ResultUtil;

@Service
public class ComplainReportServiceImpl implements ComplainReportService {

	@Autowired
	private ComplainReportDao complainReportDao;

	@Override
	public Map<String, Object> reportStatistics(Integer shopId) { 
		List<Map<String, Object>> listmap1 = this.complainReportDao.getDegreeReport(shopId);
		List<Map<String, Object>> listmap2 = this.complainReportDao.getStatusReport(shopId);
		List<Map<String, Object>> listmap3 = this.complainReportDao.getAreaReport(shopId);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("degreeReport", listmap1);
		map.put("statusReport", listmap2);
		map.put("areaReport", listmap3);
		return ResultUtil.successMap(map);
	}

	@Override
	public Map<String, Object> getShopReport(Integer pageOffset,
			Integer pageSize, String province, String city, String bizOrderId) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Map<String,Object> map = new HashMap<>();
		map.put("sum", this.complainReportDao.countShopReport(province, city, bizOrderId));
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "count(*)");
		map.put("list", this.complainReportDao.getShopReport(page, province, city, bizOrderId));
		return ResultUtil.successMap(map);
	}
}
