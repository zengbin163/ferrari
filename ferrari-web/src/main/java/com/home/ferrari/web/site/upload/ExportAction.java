package com.home.ferrari.web.site.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.base.ResultCode;
import com.home.ferrari.crmdao.sms.CrmSmsDao;
import com.home.ferrari.dao.common.ResourceDao;
import com.home.ferrari.enums.AbcTypeEnum;
import com.home.ferrari.enums.ComplainReasonEnum;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.enums.ShopTypeEnum;
import com.home.ferrari.global.PropertiesValue;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.plugin.export.ExportService;
import com.home.ferrari.plugin.session.SessionContainer;
import com.home.ferrari.service.common.ResourceService;
import com.home.ferrari.service.complain.ComplainService;
import com.home.ferrari.service.crm.CrmCustomerService;
import com.home.ferrari.service.crm.CrmUserService;
import com.home.ferrari.service.ferrari.FerrariUserService;
import com.home.ferrari.service.quest.QuestTemplateService;
import com.home.ferrari.service.sale.SaleOrderReportService;
import com.home.ferrari.service.sale.SaleOrderService;
import com.home.ferrari.service.tesla.TeslaShopService;
import com.home.ferrari.status.ComplainStatus;
import com.home.ferrari.util.Base64Util;
import com.home.ferrari.util.DateUtil;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.util.WebUtil;
import com.home.ferrari.vo.common.Resource;
import com.home.ferrari.vo.complain.Complain;
import com.home.ferrari.vo.complain.ComplainTrace;
import com.home.ferrari.vo.crm.CrmCustBizType;
import com.home.ferrari.vo.crm.CrmCustomer;
import com.home.ferrari.vo.ferrari.user.FerrariUser;
import com.home.ferrari.vo.sale.SearchSaleOrder;
import com.home.ferrari.vo.sms.SmsSaleOrder;
import com.home.ferrari.vo.sms.SmsSendRecord;
import com.home.ferrari.vo.sms.SmsShopTotal;
import com.home.ferrari.vo.tesla.shop.TeslaShop;
import com.home.ferrari.vo.tesla.shop.TeslaShopInfo;
import com.home.ferrari.vo.tesla.shop.TeslaShopSales;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping(value = "export")
@Scope("prototype")
public class ExportAction extends BaseAction {

	private static final long serialVersionUID = -6144276125164323417L;
	private static final Logger logger = LoggerFactory.getLogger(ExportAction.class);
	@Autowired
	private ExportService exportService;
	@Autowired
	private SaleOrderReportService saleOrderReportService;
	@Autowired
	private SaleOrderService saleOrderService;
	@Autowired
	private TeslaShopService teslaShopService;
	@Autowired
	private ComplainService complainService;
	@Autowired
	private FerrariUserService ferrariUserService;
	@Autowired
	private PropertiesValue propertiesValue;
	@Autowired
	private QuestTemplateService questTemplateService;
	@Autowired
	private CrmUserService crmUserService;
	@Autowired
	private CrmCustomerService crmCustomerService;
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private CrmSmsDao crmSmsDao;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "exportProvinceExcel")
	@ResponseBody
	public Map<String, Object> exportProvinceExcel() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer percentFlag = this.getIntParameter(request, "percentFlag", null);
		String taobaoSellerNick = WebUtil.decode(this.getFilteredParameter(request, "taobaoSellerNick", 0, null));
		String orderCreatedBegin =this.getFilteredParameter(request, "orderCreatedBegin", 0, null);
		String orderCreatedEnd =this.getFilteredParameter(request, "orderCreatedEnd", 0, null);
		String []rowName = {"类目","GMV","订单总数"};
		Map<String, Object> map = this.saleOrderReportService.provinceReport(percentFlag, pageOffset, pageSize, orderCreatedBegin, orderCreatedEnd);
		Map<String, Object> dataMap = (Map<String, Object>) map.get("data");
		Map<String,Object> totalMap = (Map<String, Object>) dataMap.get("totalReport");
		if(null!=totalMap){
			totalMap.put("province", "全部");
		}	
		List<Map<String, Object>> dataList = (List<Map<String, Object>>) dataMap.get("proviceReport");
		dataList.add(0,totalMap);
//		this.exportService.exportExcel(response, "数据报表", rowName, dataList);
		String []rowName2 = {"淘宝订单id","卖家昵称","买家昵称","下单时间","产品名称","订单总金额","门店名称", "省", "市"};
		Map<String, Object> map2 = this.saleOrderService.searchOrder(pageOffset, pageSize, null, null, null, null, null, null, orderCreatedBegin, orderCreatedEnd, taobaoSellerNick, null, null);
		Map<String, Object> dataMap2 = (Map<String, Object>) map2.get("data");
		List<SearchSaleOrder> orderList = (List<SearchSaleOrder>) dataMap2.get("list");
		List<Map<String, Object>> dataList2 = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(orderList)) {
			for(SearchSaleOrder order : orderList) {
				Map<String, Object> tempTotalMap = new LinkedHashMap<String, Object>();
				tempTotalMap.put("bizOrderId", order.getBizOrderId());
				tempTotalMap.put("sellerNick", order.getSellerNick());
				tempTotalMap.put("buyerNick", order.getBuyerNick());
				tempTotalMap.put("orderCreated", order.getOrderCreated());
				tempTotalMap.put("productName", order.getProductName());
				tempTotalMap.put("totalFee", order.getTotalFee());
				tempTotalMap.put("shopName", order.getShopName());
				tempTotalMap.put("province", order.getProvince());
				tempTotalMap.put("city", order.getCity());
				dataList2.add(tempTotalMap);
			}
		}

		this.exportService.exportExcel2Sheet(response, "数据报表", "订单详情", rowName, rowName2, dataList, dataList2);
		return null;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "exportCityExcel")
	@ResponseBody
	public Map<String, Object> exportCityExcel() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer percentFlag = this.getIntParameter(request, "percentFlag", null);
		String province =this.getFilteredParameter(request, "province", 0, null);
		String orderCreatedBegin =this.getFilteredParameter(request, "orderCreatedBegin", 0, null);
		String orderCreatedEnd =this.getFilteredParameter(request, "orderCreatedEnd", 0, null);
		String []rowName = {"类目","GMV","订单总数"};
		Map<String, Object> map = this.saleOrderReportService.cityReport(percentFlag, pageOffset, pageSize, province, orderCreatedBegin, orderCreatedEnd);
		Map<String, Object> dataMap = (Map<String, Object>) map.get("data");
		Map<String,Object> totalMap = (Map<String, Object>) dataMap.get("provinceReport");
		List<Map<String, Object>> dataList = (List<Map<String, Object>>) dataMap.get("cityReport");
		dataList.add(0,totalMap);
		//this.exportService.exportExcel(response, "数据报表", rowName, dataList);
		String []rowName2 = {"淘宝订单id","卖家昵称","买家昵称","下单时间","产品名称","订单总金额","门店名称", "省", "市"};
		List<SearchSaleOrder> orderList = this.saleOrderService.searchExportOrder(null, null, province, orderCreatedBegin, orderCreatedEnd);
		List<Map<String, Object>> dataList2 = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(orderList)) {
			for(SearchSaleOrder order : orderList) {
				Map<String, Object> tempTotalMap = new LinkedHashMap<String, Object>();
				tempTotalMap.put("bizOrderId", order.getBizOrderId());
				tempTotalMap.put("sellerNick", order.getSellerNick());
				tempTotalMap.put("buyerNick", order.getBuyerNick());
				tempTotalMap.put("orderCreated", order.getOrderCreated());
				tempTotalMap.put("productName", order.getProductName());
				tempTotalMap.put("totalFee", order.getTotalFee());
				tempTotalMap.put("shopName", order.getShopName());
				tempTotalMap.put("province", order.getProvince());
				tempTotalMap.put("city", order.getCity());
				dataList2.add(tempTotalMap);
			}
		}
		this.exportService.exportExcel2Sheet(response, "数据报表", "订单详情", rowName, rowName2, dataList, dataList2);
		return null;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "exportShopExcel")
	@ResponseBody
	public Map<String, Object> exportShopExcel() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer percentFlag = this.getIntParameter(request, "percentFlag", null);
		Integer shopId = this.getIntParameter(request, "shopId", null);
		Integer orderBy = this.getIntParameter(request, "orderBy", null); //0:订单数     1:gmv
		String orderCreatedBegin =this.getFilteredParameter(request, "orderCreatedBegin", 0, null);
		String orderCreatedEnd =this.getFilteredParameter(request, "orderCreatedEnd", 0, null);
		String productName = WebUtil.decode(this.getFilteredParameter(request, "productName", 0, null));
		String []rowName = {"类目","GMV","订单总数"};
		Map<String, Object> map = this.saleOrderReportService.productReport(percentFlag, pageOffset, pageSize, shopId, productName, orderBy, orderCreatedBegin, orderCreatedEnd, null);
		Map<String, Object> dataMap = (Map<String, Object>) map.get("data");
		Map<String,Object> totalMap = (Map<String, Object>) dataMap.get("shopReport");
		List<Map<String, Object>> tempDataList = new ArrayList<Map<String,Object>>();
		Map<String,Object> tempTotalMap = new LinkedHashMap<String, Object>();
		tempTotalMap.put("productName", totalMap.get("shopName"));
		tempTotalMap.put("orderTotalAmount", totalMap.get("orderTotalAmount"));
		tempTotalMap.put("orderTotal", totalMap.get("orderTotal"));
		tempDataList.add(tempTotalMap);
		List<Map<String, Object>> dataList = (List<Map<String, Object>>) dataMap.get("productReport");
		if(!CollectionUtils.isEmpty(dataList)) {
			for(Map<String, Object> mapTemp : dataList) {
				tempTotalMap = new LinkedHashMap<String, Object>();
				tempTotalMap.put("productName", mapTemp.get("productName"));
				tempTotalMap.put("orderTotalAmount", mapTemp.get("orderTotalAmount"));
				tempTotalMap.put("orderTotal", mapTemp.get("orderTotal"));
				tempDataList.add(tempTotalMap);
			}
		}
//		this.exportService.exportExcel(response, "数据报表", rowName, tempDataList);
		String []rowName2 = {"淘宝订单id","卖家昵称","买家昵称","下单时间","产品名称","订单总金额","门店名称", "省", "市"};
		List<SearchSaleOrder> orderList = this.saleOrderService.searchExportOrder(shopId, null, null, orderCreatedBegin, orderCreatedEnd);
		List<Map<String, Object>> dataList2 = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(orderList)) {
			for(SearchSaleOrder order : orderList) {
				Map<String, Object> tempTotalMap2 = new LinkedHashMap<String, Object>();
				tempTotalMap2.put("bizOrderId", order.getBizOrderId());
				tempTotalMap2.put("sellerNick", order.getSellerNick());
				tempTotalMap2.put("buyerNick", order.getBuyerNick());
				tempTotalMap2.put("orderCreated", order.getOrderCreated());
				tempTotalMap2.put("productName", order.getProductName());
				tempTotalMap2.put("totalFee", order.getTotalFee());
				tempTotalMap2.put("shopName", order.getShopName());
				tempTotalMap2.put("province", order.getProvince());
				tempTotalMap2.put("city", order.getCity());
				dataList2.add(tempTotalMap2);
			}
		}
		this.exportService.exportExcel2Sheet(response, "数据报表", "订单详情", rowName, rowName2, tempDataList, dataList2);
		return null;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "exportOrderExcel")
	@ResponseBody
	public Map<String, Object> exportOrderExcel() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer shopOrderStatus = this.getIntParameter(request, "shopOrderStatus", null);
		String buyerNick = WebUtil.decode(this.getFilteredParameter(request, "buyerNick", 0, null));
		String bizOrderId = this.getFilteredParameter(request, "bizOrderId", 0, null);
		Integer shopId = this.getIntParameter(request, "shopId", null);
		String productName = WebUtil.decode(this.getFilteredParameter(request, "productName", 0, null));
		String orderCreatedBegin =this.getFilteredParameter(request, "orderCreatedBegin", 0, null);
		String orderCreatedEnd =this.getFilteredParameter(request, "orderCreatedEnd", 0, null);
		String taobaoSellerNick = WebUtil.decode(this.getFilteredParameter(request, "taobaoSellerNick", 0, null));

		String []rowName = {"淘宝订单id","卖家昵称","买家昵称","下单时间","产品名称","订单总金额","门店名称", "省", "市"};
		Map<String, Object> map = this.saleOrderService.searchOrder(pageOffset, pageSize, shopOrderStatus, shopId, buyerNick, bizOrderId, productName, null, orderCreatedBegin, orderCreatedEnd, taobaoSellerNick, null, null);
		Map<String, Object> dataMap = (Map<String, Object>) map.get("data");
		List<SearchSaleOrder> orderList = (List<SearchSaleOrder>) dataMap.get("list");
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(orderList)) {
			for(SearchSaleOrder order : orderList) {
				Map<String, Object> tempTotalMap = new LinkedHashMap<String, Object>();
				tempTotalMap.put("bizOrderId", order.getBizOrderId());
				tempTotalMap.put("sellerNick", order.getSellerNick());
				tempTotalMap.put("buyerNick", order.getBuyerNick());
				tempTotalMap.put("orderCreated", order.getOrderCreated());
				tempTotalMap.put("productName", order.getProductName());
				tempTotalMap.put("totalFee", order.getTotalFee());
				tempTotalMap.put("shopName", order.getShopName());
				tempTotalMap.put("province", order.getProvince());
				tempTotalMap.put("city", order.getCity());
				dataList.add(tempTotalMap);
			}
		}
		this.exportService.exportExcel(response, "数据报表", rowName, dataList);
		return null;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "exportComplainExcel")
	@ResponseBody
	public Map<String, Object> exportComplainExcel() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		String beginTime = this.getFilteredParameter(request, "beginTime", 0, null);
		String endTime = this.getFilteredParameter(request, "endTime", 0, null);
		String[] rowName = { "投诉日期", "投诉人姓名", "投诉人联系方式", "投诉单类型（订单类、非订单类）",
				"关联订单号（D列为订单类投诉时需填写此列）", "投诉门店名称", "被投诉人姓名（D列为非订单类投诉时需填写此列）",
				"被投诉人职务（D列为非订单类投诉时需填写此列）", "投诉严重程度（低、高两类）", "投诉原因",
				"投诉记录描述（记录和描述在导出时只显示文本内容）", "状态维护",
				"备注（备注按照：备注人：内容的格式显示）（记录和描述在导出时只显示文本内容）" };
		Map<String, Object> map = this.complainService.getComplainList(
				pageOffset, pageSize, null, null, beginTime, endTime, null,
				null, null, null, null);
		Map<String, Object> dataMap = (Map<String, Object>) map.get(ResultUtil.DATA);
		List<Complain> complainList = (List<Complain>) dataMap.get(ResultUtil.LIST);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(complainList)) {
			for(Complain complain : complainList) {
				Map<String, Object> tempTotalMap = new LinkedHashMap<String, Object>();
				tempTotalMap.put("createTime", DateUtil.defaultFormatTime(complain.getCreateTime()));
				tempTotalMap.put("complainName", complain.getComplainName());
				tempTotalMap.put("complainPhone", complain.getComplainPhone());
				tempTotalMap.put("complainType", new Integer(1).equals(complain
						.getComplainType()) ? "订单类投诉" : "非订单类投诉");
				tempTotalMap.put("bizOrderId", complain.getBizOrderId());
				String shopName = null;
				if(null!=complain.getShopId()){
					TeslaShop teslaShop = this.teslaShopService.getTeslaShopById(complain.getShopId());
					if(teslaShop != null) {
						shopName = teslaShop.getShopName();
					}
				}
				tempTotalMap.put("shopName", shopName);
				tempTotalMap.put("beComplainName", complain.getBeComplainName());
				tempTotalMap.put("beComplainJob", complain.getBeComplainJob());
				tempTotalMap.put("complainDegree", new Integer(1).equals(complain
						.getComplainDegree()) ? " 投诉严重程度高" : "投诉严重程度低");
				String reasonName = null;
				ComplainReasonEnum reasonEnum = ComplainReasonEnum.getEnumByCode(complain.getComplainReason());
				if(reasonEnum!=null) {
					reasonName = reasonEnum.getName();
				}
				tempTotalMap.put("complainReason", reasonName);
				String text = WebUtil.decode(Base64Util.decode(complain.getText()));
				tempTotalMap.put("text", text);
				tempTotalMap.put("complainStatus", ComplainStatus.getDesc(complain.getComplainStatus()));
				Map<String,Object> map2 = this.complainService.getComplainTraceListById(complain.getComplainId());
				List<ComplainTrace> remarkList = null;
				if(!ResultUtil.DATA_NOT_EXISTS.equals(map2.get(ResultUtil.DATA))) {
					remarkList = (List<ComplainTrace>)map2.get(ResultUtil.DATA);
				}
				StringBuffer remark = new StringBuffer();
				if(!CollectionUtils.isEmpty(remarkList)) {
					for(ComplainTrace remarkTemp : remarkList) {
						String mobile = null;
						Object obj = this.ferrariUserService.getFerrariUserById(remarkTemp.getOperatorId()).get(ResultUtil.DATA);
						if(!ResultUtil.DATA_NOT_EXISTS.equals(obj)){
							FerrariUser ferrariUser = (FerrariUser) obj;
							mobile = ferrariUser.getMobile();
						}
						String rem = WebUtil.decode(Base64Util.decode(remarkTemp.getRemark()));
						remark.append(mobile).append(":").append(rem).append("   ");
					}
				}
				tempTotalMap.put("remark", remark.toString());
				dataList.add(tempTotalMap);
			}
		}
		this.exportService.exportExcel(response, "投诉数据报表", rowName, dataList);
		return null;
	}	
	
	@RequestMapping(value = "exportMessageExcel")
	@ResponseBody
	public Map<String, Object> exportMessageExcel() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer msgType = this.getIntParameter(request, "msgType", null);
		String []rowName = {"消息名称","门店名称","公司名称","省","市","是否阅读","是否同意","反馈结果内容"};
		List<Map<String, Object>> dataList = this.questTemplateService.getExportMessageByMsgType(pageOffset, pageSize, msgType);
		List<Map<String, Object>> dataList2 = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(dataList)) {
			for(Map<String, Object> map : dataList) {
				Map<String, Object> tempTotalMap = new LinkedHashMap<String, Object>();
				tempTotalMap.put("title", map.get("title"));
				tempTotalMap.put("shopName", map.get("shopName"));
				tempTotalMap.put("companyName", map.get("companyName"));
				tempTotalMap.put("province", map.get("province"));
				tempTotalMap.put("city", map.get("city"));
				Integer isRead = (Integer) map.get("isRead");
				tempTotalMap.put("isRead", (Integer.valueOf("1").equals(isRead) ? "已阅读" : "未阅读"));
				Integer isAgree = (Integer) map.get("isAgree");
				tempTotalMap.put("isAgree", (Integer.valueOf("1").equals(isAgree) ? "已同意" : "未同意"));
				try{
					if(null!=map.get("replyText")){
						tempTotalMap.put("replyText", Base64Util.decode(WebUtil.decode((String)map.get("replyText"))));
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
				dataList2.add(tempTotalMap);
			}
		}	
		
		this.exportService.exportExcel(response, "数据报表", rowName, dataList2);
		return null;
	}
	
	@RequestMapping(value = "exportShopZip")
	@ResponseBody
	public Map<String, Object> exportShopZip() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		TeslaShopInfo teslaShopInfo = this.teslaShopService.getTeslaShopInfoById(shopId);
		TeslaShop teslaShop = teslaShopInfo.getShopBase();
		if(null==teslaShopInfo || null==teslaShop) {
			throw new FerrariBizException(ResultCode.SHOP_NOT_EXISTS, "门店不存在，shopId=" + shopId);
		}
		String domain = propertiesValue.getFileDomain();
		String linuxHome = "/home/ferrari/";
		List<File> fileList = new ArrayList<File>();
		boolean notExistPic = true;
		if(!StringUtils.isEmpty(teslaShop.getLinkerL45Photo())) {
			String tempName = teslaShop.getLinkerL45Photo().replaceAll("_X", "_R");
			File l45File = new File(linuxHome + tempName);
			if(l45File.exists()) {
				fileList.add(l45File);
				notExistPic = false;
			}else{
				logger.error("门店下载图片zip包时，图片不存在，shopId=" + shopId + "，picUrl= " + tempName);
			}
		}
		if(!StringUtils.isEmpty(teslaShop.getLinkerR45Photo())) {
			String tempName = teslaShop.getLinkerR45Photo().replaceAll("_X", "_R");
			File r45File = new File(linuxHome + tempName);
			if(r45File.exists()) {
				fileList.add(r45File);
				notExistPic = false;
			}else{
				logger.error("门店下载图片zip包时，图片不存在，shopId=" + shopId + "，picUrl= " + tempName);
			}
		}
		if(!StringUtils.isEmpty(teslaShop.getFullViewPhoto())) {
			String tempName = teslaShop.getFullViewPhoto().replaceAll("_X", "_R");
			File fullFile = new File(linuxHome + tempName);
			if(fullFile.exists()) {
				fileList.add(fullFile);
				notExistPic = false;
			}else{
				logger.error("门店下载图片zip包时，图片不存在，shopId=" + shopId + "，picUrl= " + tempName);
			}
		}
		if(!StringUtils.isEmpty(teslaShop.getFeaturePhoto())) {
			String tempName = teslaShop.getFeaturePhoto().replaceAll("_X", "_R");
			File featureFile = new File(linuxHome + tempName);
			if(featureFile.exists()) {
				fileList.add(featureFile);
				notExistPic = false;
			}else{
				logger.error("门店下载图片zip包时，图片不存在，shopId=" + shopId + "，picUrl= " + tempName);
			}
		}
		TeslaShopSales teslaShopSales = teslaShopInfo.getShopSales();
		if(null == teslaShopSales) {
			logger.error("门店销售不存在，shopId=" + shopId);
		}else{
			if(!StringUtils.isEmpty(teslaShopSales.getDistrictPhoto())) {
				String tempName = teslaShopSales.getDistrictPhoto().replaceAll("_X", "_R");
				File districtPhoto = new File(linuxHome + tempName);
				if(districtPhoto.exists()) {
					fileList.add(districtPhoto);
					notExistPic = false;
				}else{
					logger.error("门店下载图片zip包时，图片不存在，shopId=" + shopId + "，picUrl= " + tempName);
				}
			}
			if(!StringUtils.isEmpty(teslaShopSales.getCustRestPhoto())) {
				String tempName = teslaShopSales.getCustRestPhoto().replaceAll("_X", "_R");
				File custRestPhoto = new File(linuxHome + tempName);
				if(custRestPhoto.exists()) {
					fileList.add(custRestPhoto);
					notExistPic = false;
				}else{
					logger.error("门店下载图片zip包时，图片不存在，shopId=" + shopId + "，picUrl= " + tempName);
				}
			}
			if(!StringUtils.isEmpty(teslaShopSales.getCustExperPhoto())) {
				String tempName = teslaShopSales.getCustExperPhoto().replaceAll("_X", "_R");
				File custExperPhoto = new File(linuxHome + tempName);
				if(custExperPhoto.exists()) {
					fileList.add(custExperPhoto);
					notExistPic = false;
				}else{
					logger.error("门店下载图片zip包时，图片不存在，shopId=" + shopId + "，picUrl= " + tempName);
				}
			}
		}
		if(notExistPic) {
			return ResultUtil.successMap(ResultUtil.DATA_NOT_EXISTS);
		}
        String zipName = "zip(" + shopId + ")" + DateUtil.currentUTCTime() + ".zip";
        String zipPath = linuxHome + "share/zip/" + zipName;
        this.zip(zipPath, fileList);
        return ResultUtil.successMap(domain + "share/zip/" + zipName);
	}
	
	/**
	 * 压缩指定的单个或多个文件，如果是目录，则遍历目录下所有文件进行压缩
	 * 
	 * @param zipFileName
	 *            ZIP文件名包含全路径
	 * @param files
	 *            文件列表
	 */
	public boolean zip(String zipFileName, List<File> fileList) {
		try {
			createDir(zipFileName);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFileName)));
			for (int i = 0; i < fileList.size(); i++) {
				if (null != fileList.get(i)) {
					out.putNextEntry(new ZipEntry(fileList.get(i).getName())); // 创建zip实体
					FileInputStream in = new FileInputStream(fileList.get(i));
					BufferedInputStream bi = new BufferedInputStream(in);
					int b;
					while ((b = bi.read()) != -1) {
						out.write(b); // 将字节流写入当前zip目录
					}
					in.close(); // 输入流关闭
				}
			}
			out.closeEntry(); // 关闭zip实体
			out.close(); // 输出流关闭
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 目录不存在时，先创建目录
	 * 
	 * @param zipFileName
	 */
	private static void createDir(String zipFileName) {
		String filePath = StringUtils.substringBeforeLast(zipFileName, "/");
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {// 目录不存在时，先创建目录
			targetFile.mkdirs();
		}
	}
	
	@RequestMapping(value = "exportShopListExcel")
	@ResponseBody
	public Map<String, Object> exportShopListExcel() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		String shopName = this.getFilteredParameter(request, "shopName", 0, null);
		String province = this.getFilteredParameter(request, "province", 0, null);
		String city = this.getFilteredParameter(request, "city", 0, null);
		String shopStatus = this.getFilteredParameter(request, "shopStatus", 0, null);
		String shopExpandStatus = this.getFilteredParameter(request, "shopExpandStatus", 0, null);
		String monitorStatus = this.getFilteredParameter(request, "monitorStatus", 0, null);
		Integer shopType = this.getIntParameter(request, "shopType", null);
		Integer abcType = this.getIntParameter(request, "abcType", null);
		Integer roleType = null;
		String[] rowName = { "门店名称", "公司名称", "联系人", "联系电话", "省", "市", "地址",
				"门店性质（村淘、车码头、星奥自营）", "门店类型（A\\B\\C）" };
		Map<String, Object> map = this.teslaShopService.getTeslaShopList(pageOffset, pageSize,
				shopName, province, city, shopStatus, shopExpandStatus,
				monitorStatus, roleType, null, shopType,
				abcType);
		@SuppressWarnings("unchecked")
		Map<String, Object> dataMap = (Map<String, Object>)map.get("data");
		@SuppressWarnings("unchecked")
		List<TeslaShop> shopList = (List<TeslaShop>)dataMap.get("list");
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(shopList)) {
			for(TeslaShop teslaShop : shopList) {
				Map<String, Object> tempTotalMap = new LinkedHashMap<String, Object>();
				tempTotalMap.put("shopName", teslaShop.getShopName());
				tempTotalMap.put("companyName", teslaShop.getCompanyName());
				tempTotalMap.put("linkMan", teslaShop.getManager());
				tempTotalMap.put("linkPhone", teslaShop.getLinkPhone());
				tempTotalMap.put("province", teslaShop.getProvince());
				tempTotalMap.put("city", teslaShop.getCity());
				tempTotalMap.put("address", teslaShop.getShopAddress());
				Integer shopType2 = StringUtils.isEmpty(teslaShop.getShopType()) ? 0 : Integer.valueOf(teslaShop.getShopType());
				if(ShopTypeEnum.CUNTAO.getCode().equals(shopType2)){
					tempTotalMap.put("shopType", "村淘");
				}else if(ShopTypeEnum.CHEMATOU.getCode().equals(shopType2)){
					tempTotalMap.put("shopType", "车码头");
				}else if(ShopTypeEnum.XINGAO.getCode().equals(shopType2)){
					tempTotalMap.put("shopType", "星奥自营");
				}else{
					;
				}
				Integer abcType2 = teslaShop.getAbcType();
				if(AbcTypeEnum.A.getCode().equals(abcType2)){
					tempTotalMap.put("abcType", "A");
				}else if(AbcTypeEnum.B.getCode().equals(abcType2)){
					tempTotalMap.put("abcType", "B");
				}else if(AbcTypeEnum.C.getCode().equals(abcType2)){
					tempTotalMap.put("abcType", "C");
				}else{
					;
				}
				dataList.add(tempTotalMap);
			}
		}	
		
		this.exportService.exportExcel(response, "数据报表", rowName, dataList);
		return null;
	}

	@RequestMapping(value = "exportCustomerListExcel")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> exportCustomerListExcel() {
		String crmBatchCode = this.getFilteredParameter(request, "batchCode", 0, null);
		String[] rowName = {
				"潜客批次号（首次上传传空，追加上传不能传空）",
				"录入日期",
				"客户名称",
				"客户等级（说明：只有低中高三个等级，请在其中选择）",
				"联系方式",
				"车牌",
				"上牌日期",
				"品牌",
				"车系",
				"排量",
				"年份",
				"联系日期",
				"介绍人",
				"业务员（说明：业务员要系统中存在，否则会导入失败）",
				"业务类型：维修/保险/新车销售等（说明：管理员此处填写的业务类型需要与系统中保持一致，系统中不包含将导入失败，多个同时存在请用/分隔）",
				"客户意向：A/B/C/D/E/F：分类说明见Sheet2", 
				"跟进记录",
				"下次跟进日期（下次提醒日期）",
				"潜客主键id（首次上传传空，追加上传不能传空）",
				"导入失败原因（无需填写）" };
		/**
		 * 区分业务员和管理员两个维度
		 */
		List<Integer> crmUserIdList = this.crmUserService.getCrmUserIdListByRoleType(SessionContainer.getSession().getCrmUser().getId(), null);
		List<CrmCustomer> customerList = this.crmCustomerService.getUploadFailCrmCustomer(crmBatchCode, crmUserIdList);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(customerList)) {
			for(CrmCustomer crmCustomer : customerList) {
				Map<String, Object> tempTotalMap = new LinkedHashMap<String, Object>();
				tempTotalMap.put("batchCode", crmCustomer.getCrmBatchCode());
				tempTotalMap.put("createTime", DateUtil.defaultFormatDate(crmCustomer.getCreateTime()));
				tempTotalMap.put("customerName", crmCustomer.getCustomerName());
				tempTotalMap.put("level", crmCustomer.getLevel());
				tempTotalMap.put("linkPhone", crmCustomer.getLinkPhone());
				tempTotalMap.put("licensePlate", crmCustomer.getLicensePlate());
				tempTotalMap.put("licenseTime", crmCustomer.getLicenseTime());
				tempTotalMap.put("branName", crmCustomer.getCrmBrandName());
				tempTotalMap.put("carSerials", crmCustomer.getCarSeries());
				tempTotalMap.put("displacement", crmCustomer.getDisplacement());
				tempTotalMap.put("year", crmCustomer.getYear());
				tempTotalMap.put("linkTime", DateUtil.defaultFormatDate(crmCustomer.getLinkTime()));
				tempTotalMap.put("introducer", crmCustomer.getIntroducer());
				tempTotalMap.put("crmUserName", crmCustomer.getCrmUserName());
				List<CrmCustBizType> bizTypeList = this.crmCustomerService.getCrmCustBizType(crmCustomer.getId());
				if(CollectionUtils.isEmpty(bizTypeList)) {
					tempTotalMap.put("bizTypeList", "");
				}else{
					String name = "";
					for(CrmCustBizType bizType : bizTypeList) {
						Resource resource = this.resourceDao.getResourcesByKeyVal(ResourceService.CUSTOMER_REQUIRE_KEY, bizType.getBizType());
						name = name + resource.getResourceDesc() + "/";
					}
					tempTotalMap.put("bizTypeList", name);
				}
				tempTotalMap.put("purpose", crmCustomer.getPurpose());
				tempTotalMap.put("remark", "");
				tempTotalMap.put("remindTime", "");
				tempTotalMap.put("id", crmCustomer.getId());
				tempTotalMap.put("uploadReason", crmCustomer.getUploadReason());
				dataList.add(tempTotalMap);
			}
		}	
		this.exportService.exportExcel(response, "数据报表", rowName, dataList);
		return null;
	}
	
	/**
	 * 导出所有门店短信使用情况
	 * @return
	 */
	@RequestMapping(value = "exportShopSmsListExcel")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> exportShopSmsListExcel() {
		String[] rowName = { "门店名称", "客户经理", "账号", "已使用（条）", "剩余（条）" };
		List<SmsShopTotal> smsShopTotalList = crmSmsDao.getShopTotalList(null, null);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(smsShopTotalList)) {
			for(SmsShopTotal smsShopTotal : smsShopTotalList) {
				Map<String, Object> tempTotalMap = new LinkedHashMap<String, Object>();
				tempTotalMap.put("crmShopName", smsShopTotal.getCrmShopName());
				tempTotalMap.put("adminName", smsShopTotal.getAdminName());
				tempTotalMap.put("loginNo", smsShopTotal.getLoginNo());
				tempTotalMap.put("useNum", smsShopTotal.getUseNum());
				tempTotalMap.put("leftNum", smsShopTotal.getLeftNum());
				dataList.add(tempTotalMap);
			}
		}	
		this.exportService.exportExcel(response, "数据报表", rowName, dataList);
		return null;
	}

	/**
	 * 导出某个门店短信发送情况
	 * @return
	 */
	@RequestMapping(value = "exportSendSmsExcel")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> exportSendSmsExcel() {
		Integer adminId = this.getIntParameter(request, "adminId", null);
		Assert.notNull(adminId, "adminId不能为空");
		String[] rowName = { "日期", "类型", "目标号码数（个）", "发送人帐号", "发送内容", "扣费条数（条）" };
		List<SmsSendRecord> smsSendRecordList = this.crmSmsDao.getShopSmsSendRecord(null, adminId, null, null);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(smsSendRecordList)) {
			for(SmsSendRecord smsSendRecord : smsSendRecordList) {
				Map<String, Object> tempTotalMap = new LinkedHashMap<String, Object>();
				tempTotalMap.put("date", smsSendRecord.getDate());
				tempTotalMap.put("type", smsSendRecord.getType());
				tempTotalMap.put("targetPhoneNum", smsSendRecord.getTargetPhoneNum());
				tempTotalMap.put("senderLoginNo", smsSendRecord.getSenderLoginNo());
				tempTotalMap.put("sendContent", smsSendRecord.getSendContent());
				tempTotalMap.put("markNum", smsSendRecord.getMarkNum());
				dataList.add(tempTotalMap);
			}
		}	
		this.exportService.exportExcel(response, "数据报表", rowName, dataList);
		return null;
	}
	
	/**
	 * 导出某个门店发送短信的电话号
	 * @return
	 */
	@RequestMapping(value = "exportSendMobileExcel")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> exportSendMobileExcel() {
		String groupCode = this.getFilteredParameter(request, "groupCode", 0, null);
		Assert.notNull(groupCode, "groupCode不能为空");
		String[] rowName = { "发送目标号码", "客户名称"};
		List<Map<String, Object>> mapList = this.crmSmsDao.getGroupSmsSendTarget(null, groupCode);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(mapList)) {
			for(Map<String, Object> map : mapList) {
				Map<String, Object> tempTotalMap = new LinkedHashMap<String, Object>();
				tempTotalMap.put("targetMobile", map.get("targetMobile"));
				tempTotalMap.put("customerName", map.get("customerName"));
				dataList.add(tempTotalMap);
			}
		}	
		this.exportService.exportExcel(response, "数据报表", rowName, dataList);
		return null;
	}

	/**
	 * 导出所有门店订单信息
	 * @return
	 */
	@RequestMapping(value = "exportShopSmsOrderListExcel")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> exportShopSmsOrderListExcel() {
		String[] rowName = { "日期", "门店名称", "客户经理", "帐号", "套餐包", "数量（个）", "实付款（元）", "发票状态"};
		List<SmsSaleOrder> orderList = this.crmSmsDao.getMealSaleOrderList(null, null, null, null, null, null, null);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(orderList)) {
			for(SmsSaleOrder order : orderList) {
				Map<String, Object> tempTotalMap = new LinkedHashMap<String, Object>();
				tempTotalMap.put("date", order.getDate());
				tempTotalMap.put("crmShopName", order.getCrmShopName());
				tempTotalMap.put("adminName", order.getAdminName());
				tempTotalMap.put("loginNo", order.getLoginNo());
				tempTotalMap.put("mealName", order.getMealName());
				tempTotalMap.put("buyNum", order.getBuyNum());
				BigDecimal payMoney = null;
				if (null != order.getPayMoney()) {
					payMoney = order.getPayMoney().divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
				}
				tempTotalMap.put("payMoney", payMoney);
				String invoiceStatus = (DefaultEnum.YES.getCode().equals(order.getIsInvoice())) ? "已开" : "未开";
				tempTotalMap.put("invoiceStatus", invoiceStatus);
				dataList.add(tempTotalMap);
			}
		}	
		this.exportService.exportExcel(response, "数据报表", rowName, dataList);
		return null;
	}

	/**
	 * 导出某个门店购买订单信息
	 * @return
	 */
	@RequestMapping(value = "exportShopSmsOrderExcel")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> exportShopSmsOrderExcel() {
		Integer adminId = this.getIntParameter(request, "adminId", null);
		Assert.notNull(adminId, "adminId不能为空");
		String[] rowName = { "购买日期", "套餐包名称", "短信数量", "购买数量", "付款", "发票状态"};
		List<SmsSaleOrder> orderList = this.crmSmsDao.getMealSaleOrderList(null, null, null, adminId, null, null, null);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(orderList)) {
			for(SmsSaleOrder order : orderList) {
				Map<String, Object> tempTotalMap = new LinkedHashMap<String, Object>();
				tempTotalMap.put("date", order.getDate());
				tempTotalMap.put("mealName", order.getMealName());
				tempTotalMap.put("smsCount", order.getSmsCount());
				tempTotalMap.put("buyNum", order.getBuyNum());
				BigDecimal payMoney = null;
				if (null != order.getPayMoney()) {
					payMoney = order.getPayMoney().divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
				}
				tempTotalMap.put("payMoney", payMoney);
				String invoiceStatus = (DefaultEnum.YES.getCode().equals(order.getIsInvoice())) ? "已开" : "未开";
				tempTotalMap.put("invoiceStatus", invoiceStatus);
				dataList.add(tempTotalMap);
			}
		}	
		this.exportService.exportExcel(response, "数据报表", rowName, dataList);
		return null;
	}

}
