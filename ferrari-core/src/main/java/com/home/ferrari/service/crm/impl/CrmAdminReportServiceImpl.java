package com.home.ferrari.service.crm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.home.ferrari.crmdao.crm.CrmAdminReportDao;
import com.home.ferrari.crmdao.report.CrmCustomerReportDao;
import com.home.ferrari.enums.CrmRoleTypeEnum;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.service.crm.CrmAdminReportService;
import com.home.ferrari.service.crm.CrmCustomerReportService;
import com.home.ferrari.service.crm.CrmUserService;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.crm.CrmCompany;
import com.home.ferrari.vo.crm.CrmProvinceCount;
import com.home.ferrari.vo.crm.CrmUser;

@Service
public class CrmAdminReportServiceImpl implements CrmAdminReportService {
	
	@Autowired
	private CrmUserService crmUserService;
	@Autowired
	private CrmAdminReportDao crmAdminReportDao;
	@Autowired
	private CrmCustomerReportDao crmCustomerReportDao;
	@Autowired
	private CrmCustomerReportService crmCustomerReportService;

	@Override
	public Map<String, Object> crmProvinceReport(Integer pageOffset, Integer pageSize) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		//管理员数（门店总数）
		Integer totalAdminCount = this.crmUserService.countCrmUserListByRoleType(CrmRoleTypeEnum.ROLE_SHOP_ADMIN.getCode());
		//业务员数
		Integer totalBizCount = this.crmUserService.countCrmUserListByRoleType(CrmRoleTypeEnum.ROLE_BIZ_ADMIN.getCode());
		//总客户数
		Integer totalUserCount = this.crmCustomerReportDao.sumTotalCustomer(null, null, null);
		//按照省份分布的管理员数、业务员数、每个省的总客户数
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "count(*)");
		List<CrmProvinceCount> crmProvinceCountList = this.crmAdminReportDao.getCrmProvinceReport(page);
		if(CollectionUtils.isNotEmpty(crmProvinceCountList)) {
			for(CrmProvinceCount count : crmProvinceCountList) {
				count.setBizSum(this.crmAdminReportDao.getCrmProvinceBizAdmin(count.getProvince()));
				count.setCustomerSum(this.crmAdminReportDao.getCrmProvinceCustomer(count.getProvince()));
			}
		}
		//每个月用户登陆次数活跃度
		List<Map<Integer, Integer>> monthLoginList = this.crmAdminReportDao.getCurrentMonthLoginReport();
		//每个月的用户增长
		List<Map<Integer, Integer>> monthNewUserList = this.crmAdminReportDao.getCurrentMonthNewUserReport();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalUserCount", totalUserCount);
		map.put("totalAdminCount", totalAdminCount);
		map.put("totalBizCount", totalBizCount);
		map.put("provinceList", crmProvinceCountList);
		map.put("monthLoginList", monthLoginList);
		map.put("monthNewUserList", monthNewUserList);
		return ResultUtil.successMap(map);
	}

	@Override
	public Map<String, Object> crmShopReport(Integer pageOffset, Integer pageSize, String province, String city, String shopName) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		//全国门店数 = 全国管理员数
		Integer totalShopCount = this.crmUserService.countCrmUserListByRoleType(CrmRoleTypeEnum.ROLE_SHOP_ADMIN.getCode());
		//全国门店潜客数
		Integer totalCustomerCount = this.crmAdminReportDao.countTotalCustomer();
		//根据省市、门店名称搜索crm门店
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t1.crm_shop_name");
		List<CrmCompany> crmCompanyList = this.crmAdminReportDao.getCrmCompanyListReport(page, province, city, shopName);
		if(CollectionUtils.isNotEmpty(crmCompanyList)) {
			for(CrmCompany company : crmCompanyList) {
				List<Integer> listIds = this.getCrmUserIdList(company.getAdminId());
				company.setShopFrequenceByDay(this.crmAdminReportDao.getAvgCustomerByAdminid(company.getAdminId()));
				company.setPurposeCount(this.crmCustomerReportDao.sumPurposeCustomerByBizAdminId(listIds));
				company.setBizTypeCount(this.crmCustomerReportService.getBizTypeReport(null, null, listIds));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalShopCount", totalShopCount);
		map.put("totalCustomerCount", totalCustomerCount);
		map.put("crmCompanyList", crmCompanyList);
		map.put("crmCompanySum", this.crmAdminReportDao.countCrmCompanyListReport(province, city, shopName));
		return map;
	}
	
	private List<Integer> getCrmUserIdList(Integer adminId) {
		List<Integer> list = new ArrayList<Integer>();
		Map<String,Object> map = this.crmUserService.getAdminCrmClerkList(adminId, DefaultEnum.YES.getCode());
		@SuppressWarnings("unchecked")
		List<CrmUser> crmUserList = ( List<CrmUser>)map.get(ResultUtil.DATA);
		if(CollectionUtils.isNotEmpty(crmUserList)) {
			for(CrmUser temp : crmUserList) {
				list.add(temp.getId());
			}
		}
		list.add(adminId);
		return list;
	}

}
