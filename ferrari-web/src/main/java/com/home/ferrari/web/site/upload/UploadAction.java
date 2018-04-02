package com.home.ferrari.web.site.upload;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.base.ResultCode;
import com.home.ferrari.enums.AbcTypeEnum;
import com.home.ferrari.enums.ComplainReasonEnum;
import com.home.ferrari.enums.CustomerUploadStatusEnum;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.enums.OrderSourceEnum;
import com.home.ferrari.enums.ShopTypeEnum;
import com.home.ferrari.global.TaobaoSellerNick;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.plugin.exception.FerrariSysException;
import com.home.ferrari.plugin.session.SessionContainer;
import com.home.ferrari.plugin.upload.UploadService;
import com.home.ferrari.service.account.AccountHeaderService;
import com.home.ferrari.service.common.ResourceService;
import com.home.ferrari.service.complain.ComplainService;
import com.home.ferrari.service.crm.CrmCustomerService;
import com.home.ferrari.service.crm.CrmUserService;
import com.home.ferrari.service.sale.SaleOrderService;
import com.home.ferrari.service.tesla.TeslaShopService;
import com.home.ferrari.status.ComplainStatus;
import com.home.ferrari.status.ShopOrderStatus;
import com.home.ferrari.status.TaobaoOrderStatus;
import com.home.ferrari.util.Base64Util;
import com.home.ferrari.util.DateUtil;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.util.WebUtil;
import com.home.ferrari.vo.account.AccountHeader;
import com.home.ferrari.vo.account.AccountList;
import com.home.ferrari.vo.common.Resource;
import com.home.ferrari.vo.complain.Complain;
import com.home.ferrari.vo.crm.CrmBatch;
import com.home.ferrari.vo.crm.CrmCustBizType;
import com.home.ferrari.vo.crm.CrmCustRemaind;
import com.home.ferrari.vo.crm.CrmCustTrace;
import com.home.ferrari.vo.crm.CrmCustomer;
import com.home.ferrari.vo.crm.CrmUser;
import com.home.ferrari.vo.sale.SaleOrder;
import com.home.ferrari.vo.sale.SaleOrderDetail;
import com.home.ferrari.vo.tesla.shop.TeslaShop;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping(value = "upload", method = RequestMethod.POST)
@Scope("prototype")
public class UploadAction extends BaseAction {

	private static final long serialVersionUID = 6752033388180657308L;

	@Autowired
	private UploadService uploadService;
	@Autowired
	private ComplainService complainService;
	@Autowired
	private TeslaShopService teslaShopService;
	@Autowired
	private AccountHeaderService accountHeaderService;
	@Autowired
	private SaleOrderService saleOrderService;
	@Autowired
	private CrmUserService crmUserService;
	@Autowired
	private CrmCustomerService crmCustomerService;
	@Autowired
	private ResourceService resourceService;

	/**
	 * 上传图片
	 * @param file
	 * @param fileType
	 * @param needCompress
	 * @return
	 */
	@RequestMapping(value = "upload")
	@ResponseBody
	public Map<String, Object> upload(
			@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "fileType", required = true) Integer fileType,
			@RequestParam(value = "needCompress", required = true) Integer needCompress) {
		return this.uploadService.upload(needCompress, fileType, file);
	}

	/**
	 * 上传文件
	 * @param file
	 * @param fileType
	 * @return
	 */
	@RequestMapping(value = "uploadFile")
	@ResponseBody
	public Map<String, Object> uploadFile(@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "fileType", required = true) Integer fileType) {
		return this.uploadService.uploadFile(file, fileType);
	}

	/**
	 * 门店批量导入
	 * @param excelFile
	 * @return
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "excelUpload")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> excelUpload(@RequestParam(value = "excelFile", required = true) MultipartFile excelFile) {
		InputStream is = null;
		HSSFWorkbook hssfWorkbook = null;
		try {
			is = excelFile.getInputStream();
			hssfWorkbook = new HSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FerrariSysException(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
		// 循环工作表Sheet
		List<TeslaShop> shopList = new ArrayList<TeslaShop>();
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);//处理第一个sheet
		// 循环行Row
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			}
			HSSFCell cell0 = hssfRow.getCell(0);
			HSSFCell cell1 = hssfRow.getCell(1);
			HSSFCell cell2 = hssfRow.getCell(2);
			HSSFCell cell3 = hssfRow.getCell(3);
			HSSFCell cell4 = hssfRow.getCell(4);
			HSSFCell cell5 = hssfRow.getCell(5);
			HSSFCell cell6 = hssfRow.getCell(6);
			HSSFCell cell7 = hssfRow.getCell(7);
			HSSFCell cell8 = hssfRow.getCell(8);
			HSSFCell cell9 = hssfRow.getCell(9);
			HSSFCell cell10 = hssfRow.getCell(10);
			HSSFCell cell11 = hssfRow.getCell(11);
			HSSFCell cell12 = hssfRow.getCell(12);
			HSSFCell cell13 = hssfRow.getCell(13);
			HSSFCell cell14 = hssfRow.getCell(14);
			HSSFCell cell15 = hssfRow.getCell(15);
			HSSFCell cell16 = hssfRow.getCell(16);
			HSSFCell cell17 = hssfRow.getCell(17);
			HSSFCell cell18 = hssfRow.getCell(18);
			TeslaShop teslaShop = new TeslaShop();
			teslaShop.setShopName(getValue(cell0));
			teslaShop.setProvince(getValue(cell1));
			teslaShop.setCity(getValue(cell2));
			teslaShop.setCounty(getValue(cell3));
			teslaShop.setShopAddress(getValue(cell4));
			teslaShop.setOpenTime(getValue(cell5));
			teslaShop.setManager(getValue(cell6));
			teslaShop.setLinkPhone(getValue(cell7));
			teslaShop.setPhone(getValue(cell8));
			teslaShop.setComplaintPhone(getValue(cell9));
			teslaShop.setDayLinker(getValue(cell10));
			teslaShop.setLightBox(getValue(cell11));
			teslaShop.setBackWall(getValue(cell12));
			teslaShop.setFieldArea(Double.valueOf(getValue(cell13)));
			teslaShop.setWorkshopArea(Double.valueOf(getValue(cell14)));
			teslaShop.setPersons(Integer.valueOf(getValue(cell15)));
			teslaShop.setSubBranchs(Integer.valueOf(getValue(cell16)));
			String shopTypes = getValue(cell17);
			if(StringUtils.isNotBlank(shopTypes)) {
				String []sts = shopTypes.split(",");
				StringBuffer sb = new StringBuffer();
				for(int i=0;i<sts.length;i++) {
					sb.append(ShopTypeEnum.getEnum(sts[i]).getCode());
					if(i<sts.length-1){
						sb.append(",");
					}
				}
				teslaShop.setShopType(sb.toString());
			}
			teslaShop.setAbcType((cell18==null)?null:AbcTypeEnum.getEnum(getValue(cell18)).getCode());
			shopList.add(teslaShop);
		}
		return this.teslaShopService.createShopBatch(shopList, getFerrariUser().getId(), getFerrariUser().getMobile());
	}
	
	/**
	 * 批量导入账期账单
	 * @param excelFile
	 * @return
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "accountUpload")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> accountUpload(@RequestParam(value = "accountExcelFile", required = true) MultipartFile accountExcelFile) {
		InputStream is = null;
		HSSFWorkbook hssfWorkbook = null;
		try {
			is = accountExcelFile.getInputStream();
			hssfWorkbook = new HSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FerrariSysException(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);//处理第一个sheet
		//创建header
		HSSFRow hssfRowHeader = hssfSheet.getRow(1);
		String finalNo = getValue(hssfRowHeader.getCell(0));
		String totalFee = getValue(hssfRowHeader.getCell(1));
		String paymentDate = getValue(hssfRowHeader.getCell(2));
		AccountHeader accountHeader = this.accountHeaderService.getAccountHeaderByFinalNo(finalNo);
		if (null != accountHeader) {
			return ResultUtil.toErrorMap(ResultCode.ACCOUNT_EXISTS, ResultCode.ACCOUNT_EXISTS.getString());
		}
		// 循环行Row
		Set<AccountList> accountListArray = new HashSet<>();
		for (int rowNum = 3; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			}
			HSSFCell cell0 = hssfRow.getCell(0);
			HSSFCell cell1 = hssfRow.getCell(1);
			HSSFCell cell2 = hssfRow.getCell(2);
			HSSFCell cell3 = hssfRow.getCell(3);
			HSSFCell cell4 = hssfRow.getCell(4);
			HSSFCell cell5 = hssfRow.getCell(5);
			HSSFCell cell6 = hssfRow.getCell(6);
			HSSFCell cell7 = hssfRow.getCell(7);
			if(null==cell0) {
				continue;
			}
			AccountList accountList = new AccountList();
			accountList.setFinalNo(finalNo);
			accountList.setSerialNo(getValue(cell0));
			accountList.setProductName(getValue(cell1));
			accountList.setCheckTime(getDateValue(cell2));
			accountList.setShopName(getValue(cell3).replace("星奥车联-", ""));
			accountList.setCity(getValue(cell4));
			accountList.setBuyerNick(getValue(cell5));
			accountList.setCarModel(getValue(cell6));
			accountList.setSettleFee(new BigDecimal(getValue(cell7)));
			accountListArray.add(accountList);
		}
		return this.accountHeaderService.saveAccount(finalNo, new BigDecimal(totalFee), paymentDate, accountListArray);
	}
	
	/**
	 * 批量导入订单投诉
	 * @param excelFile
	 * @return
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "orderComplainUpload")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> orderComplainUpload(@RequestParam(value = "orderComplainExcelFile", required = true) MultipartFile orderComplainExcelFile) {
		InputStream is = null;
		HSSFWorkbook hssfWorkbook = null;
		try {
			is = orderComplainExcelFile.getInputStream();
			hssfWorkbook = new HSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FerrariSysException(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);//处理第一个sheet
		// 循环行Row
		List<Complain> complainList = new ArrayList<>();
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			}
			HSSFCell cell0 = hssfRow.getCell(0);
			HSSFCell cell1 = hssfRow.getCell(1);
			HSSFCell cell2 = hssfRow.getCell(2);
			HSSFCell cell3 = hssfRow.getCell(3);
			HSSFCell cell4 = hssfRow.getCell(4);
			HSSFCell cell5 = hssfRow.getCell(5);
			HSSFCell cell6 = hssfRow.getCell(6);
			HSSFCell cell7 = hssfRow.getCell(7);//投诉日期
			Complain complain = new Complain();
			String complainName = getValue(cell0);
			if(StringUtils.isEmpty(complainName)) {
				continue;
			}
			complain.setComplainName(complainName);
			complain.setComplainPhone(getValue(cell1));
			complain.setBizOrderId(getValue(cell2));
			complain.setComplainType(1);
			complain.setIsRight(DefaultEnum.NO.getCode());
			complain.setComplainStatus(ComplainStatus.WAITING);
			Integer shopId = null;
			String shopName = getValue(cell3);
			TeslaShop teslaShop = this.teslaShopService.getTeslaShopByName(shopName);
			if (null != teslaShop) {
				shopId = teslaShop.getId();
			}
			complain.setShopId(shopId);
			String degree = getValue(cell4);
			complain.setComplainDegree("高".equals(degree) ? 1 : 2);
			Integer reasonId = null;
			ComplainReasonEnum reasonEnum = ComplainReasonEnum.getEnum(getValue(cell5));
			if(null!=reasonEnum){
				reasonId = reasonEnum.getCode();
			}
			complain.setComplainReason(reasonId);
			complain.setText(Base64Util.encode(WebUtil.encode(getValue(cell6))));
			complain.setCreateTime(getDateValue(cell7));;
			complain.setModifyTime(getDateValue(cell7));;
			complainList.add(complain);
		}
		return this.complainService.createComplainBatch(complainList);
	}
	
	/**
	 * 批量导入非订单投诉
	 * @param excelFile
	 * @return
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "notOrderComplainUpload")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> notOrderComplainUpload(@RequestParam(value = "notOrderComplainExcelFile", required = true) MultipartFile notOrderComplainExcelFile) {
		InputStream is = null;
		HSSFWorkbook hssfWorkbook = null;
		try {
			is = notOrderComplainExcelFile.getInputStream();
			hssfWorkbook = new HSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FerrariSysException(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);//处理第一个sheet
		// 循环行Row
		List<Complain> complainList = new ArrayList<>();
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			}
			HSSFCell cell0 = hssfRow.getCell(0);
			HSSFCell cell1 = hssfRow.getCell(1);
			HSSFCell cell2 = hssfRow.getCell(2);
			HSSFCell cell3 = hssfRow.getCell(3);
			HSSFCell cell4 = hssfRow.getCell(4);
			HSSFCell cell5 = hssfRow.getCell(5);
			HSSFCell cell6 = hssfRow.getCell(6);
			HSSFCell cell7 = hssfRow.getCell(7);
			HSSFCell cell8 = hssfRow.getCell(8);
			HSSFCell cell9 = hssfRow.getCell(9);//投诉日期
			Complain complain = new Complain();
			String complainName = getValue(cell0);
			if(StringUtils.isEmpty(complainName)) {
				continue;
			}
			complain.setComplainName(complainName);
			complain.setComplainPhone(getValue(cell1));
			complain.setComplainType(2);
			complain.setIsRight(DefaultEnum.NO.getCode());
			String shopOrPeople = getValue(cell2);
			complain.setComplainSubType("门店".equals(shopOrPeople)?1:2);
			complain.setComplainStatus(ComplainStatus.WAITING);
			Integer shopId = null;
			String shopName = getValue(cell3);
			TeslaShop teslaShop = this.teslaShopService.getTeslaShopByName(shopName);
			if (null != teslaShop) {
				shopId = teslaShop.getId();
			}
			complain.setShopId(shopId);
			complain.setBeComplainName(getValue(cell4));
			complain.setBeComplainJob(getValue(cell5));
			String degree = getValue(cell6);
			complain.setComplainDegree("高".equals(degree) ? 1 : 2);
			Integer reasonId = null;
			ComplainReasonEnum reasonEnum = ComplainReasonEnum.getEnum(getValue(cell7));
			if(null!=reasonEnum){
				reasonId = reasonEnum.getCode();
			}
			complain.setComplainReason(reasonId);
			complain.setText(Base64Util.encode(WebUtil.encode(getValue(cell8))));
			complain.setCreateTime(getDateValue(cell9));;
			complain.setModifyTime(getDateValue(cell9));;
			complainList.add(complain);
		}
		return this.complainService.createComplainBatch(complainList);
	}
	
	/**
	 * 批量导入淘宝订单
	 * @param excelFile
	 * @return
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "taobaoOrderUpload")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> taobaoOrderUpload(@RequestParam(value = "taobaoOrderExcelFile", required = true) MultipartFile taobaoOrderExcelFile) {
		InputStream is = null;
		HSSFWorkbook hssfWorkbook = null;
		try {
			is = taobaoOrderExcelFile.getInputStream();
			hssfWorkbook = new HSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FerrariSysException(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);//处理第一个sheet
		// 循环行Row
		String orderFomat = DateUtil.formatTime(DateUtil.TIME_PATTERN1, new Date());
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			}
			HSSFCell cell0 = hssfRow.getCell(0);//淘宝订单号（非淘宝订单填空）
			HSSFCell cell1 = hssfRow.getCell(1);//订单来源
			HSSFCell cell2 = hssfRow.getCell(2);//卖家昵称
			HSSFCell cell3 = hssfRow.getCell(3);//买家昵称
			HSSFCell cell4 = hssfRow.getCell(4);//订单金额
			HSSFCell cell5 = hssfRow.getCell(5);//商品单价
			HSSFCell cell6 = hssfRow.getCell(6);//数量
			HSSFCell cell7 = hssfRow.getCell(7);//订单创建时间
			HSSFCell cell8 = hssfRow.getCell(8);//商品名称
			HSSFCell cell9 = hssfRow.getCell(9);//门店名称
			HSSFCell cell10 = hssfRow.getCell(10);//收货人名称
			HSSFCell cell11 = hssfRow.getCell(11);//收货人电话
			HSSFCell cell12 = hssfRow.getCell(12);//收货人地址
			HSSFCell cell13 = hssfRow.getCell(13);//物流公司
			HSSFCell cell14 = hssfRow.getCell(14);//物流单号
			//订单编号
			String bizOrderId = getValue(cell0);
			if(StringUtils.isEmpty(bizOrderId)) {
				bizOrderId = "SDDR" + orderFomat + rowNum;
			}
			//订单来源
			OrderSourceEnum orderSourceEnum = OrderSourceEnum.getEnum(getValue(cell1));
			Integer orderSource = 0;
			if(null!=orderSourceEnum){
				orderSource = orderSourceEnum.getCode();
			}
			//卖家昵称
			String taobaoSellerName = getValue(cell2);
			if(StringUtils.isEmpty(taobaoSellerName)) {
				continue;
			}
			String sellerNick = TaobaoSellerNick.getValue(taobaoSellerName);
			//买家昵称
			String buyerNick = getValue(cell3);
			//订单金额
			BigDecimal payment = new BigDecimal(getValue(cell4));
			//商品单价
			BigDecimal productPrice = new BigDecimal(getValue(cell5));
			//商品数量
			Integer num = new Integer(getValue(cell6));
			//订单创建时间
			Date orderCreate = getDateValue(cell7);
			//商品名称
			String productName = getValue(cell8);
			//门店名称
			String shopName = getValue(cell9).replace("星奥车联-", "");
			TeslaShop teslaShop = this.teslaShopService.getTeslaShopByName(shopName);
			//收货人名称
			String receiverName = getValue(cell10);
			//收货人电话
			String receiverPhone = getValue(cell11);
			//收货人地址
			String receiverAddress = getValue(cell12);
			//物流公司
			String logisticsCompany = getValue(cell13);
			//物流单号
			String invoiceNo = getValue(cell14);
			SaleOrder saleOrder = new SaleOrder();
			if(null!=teslaShop) {
				saleOrder.setShopId(teslaShop.getId());
			}
			saleOrder.setBizOrderId(bizOrderId);
			saleOrder.setOrderSource(orderSource);
			saleOrder.setSellerNick(sellerNick);
			saleOrder.setBuyerNick(buyerNick);
			saleOrder.setTaobaoOrderStatus(TaobaoOrderStatus.TRADE_FINISHED);
			saleOrder.setShopOrderStatus(ShopOrderStatus.FINISH);
			saleOrder.setPayment(payment);
			saleOrder.setPostFee(new BigDecimal(0));
			saleOrder.setReceiverName(receiverName);
			saleOrder.setReceiverState(null);
			saleOrder.setReceiverAddress(receiverAddress);
			saleOrder.setReceiverZip(null);
			saleOrder.setReceiverMobile(receiverPhone);
			saleOrder.setReceiverPhone(receiverPhone);
			saleOrder.setOrderCreated(orderCreate);
			saleOrder.setOrderPay(null);
			saleOrder.setOrderConsign(null);
			saleOrder.setOrderEnd(null);
			saleOrder.setOrderType("eticket");
			saleOrder.setBuyerMessage(null);
			saleOrder.setBuyerAlipayNo(null);
			//订单明细
			List<SaleOrderDetail> saleDetailList = new ArrayList<SaleOrderDetail>();
			SaleOrderDetail saleDetail = new SaleOrderDetail();
			saleDetail.setBizOrderId(bizOrderId);
			saleDetail.setBizDetailId(bizOrderId);
			saleDetail.setTaobaoDetailStatus(TaobaoOrderStatus.TRADE_FINISHED);
			saleDetail.setProductName(productName);
			saleDetail.setEtShopName(getValue(cell9));
			saleDetail.setShopName(shopName);
			saleDetail.setNum(num);
			saleDetail.setPayment(payment);
			saleDetail.setPrice(productPrice);
			saleDetail.setTotalFee(payment);
			saleDetail.setDiscountFee(new BigDecimal(0));
			saleDetail.setPicPath(null);
			saleDetail.setLogisticsCompany(logisticsCompany);
			saleDetail.setInvoiceNo(invoiceNo);
			saleDetailList.add(saleDetail);
			saleOrder.setOrderDetailList(saleDetailList);
			this.saleOrderService.saveSaleOrder(saleOrder, saleDetailList);
		}
		return ResultUtil.successMap(ResultUtil.DATA_INSERT_SUCC);
	}
	
	/**
	 * 批量导入潜客
	 * @param excelFile
	 * @return
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "customerUpload")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> customerUpload(@RequestParam(value = "customerExcelFile", required = true) MultipartFile customerExcelFile) {
		if(null == SessionContainer.getSession()) {
			throw new FerrariBizException(ResultCode.NO_LOGIN, "用户未登陆");
		}
		InputStream is = null;
		HSSFWorkbook hssfWorkbook = null;
		try {
			is = customerExcelFile.getInputStream();
			hssfWorkbook = new HSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FerrariSysException(ResultCode.SYSTEM_ERROR, e.getMessage());
		}
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);//处理第一个sheet
		// 循环行Row
		String systemBatchCode = null;
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			}
			HSSFCell cell0 = hssfRow.getCell(0);//潜客批次号（首次上传传空，追加上传不能传空）
			HSSFCell cell1 = hssfRow.getCell(1);//录入日期
			HSSFCell cell2 = hssfRow.getCell(2);//客户名称
			HSSFCell cell3 = hssfRow.getCell(3);//客户等级
			HSSFCell cell4 = hssfRow.getCell(4);//联系方式
			HSSFCell cell5 = hssfRow.getCell(5);//车牌
			HSSFCell cell6 = hssfRow.getCell(6);//上牌日期
			HSSFCell cell7 = hssfRow.getCell(7);//品牌
			HSSFCell cell8 = hssfRow.getCell(8);//车系
			HSSFCell cell9 = hssfRow.getCell(9);//排量
			HSSFCell cell10 = hssfRow.getCell(10);//年份
			HSSFCell cell11 = hssfRow.getCell(11);//联系日期
			HSSFCell cell12 = hssfRow.getCell(12);//介绍人
			HSSFCell cell13 = hssfRow.getCell(13);//业务员
			HSSFCell cell14 = hssfRow.getCell(14);//业务类型：维修/保险/新车销售
			HSSFCell cell15 = hssfRow.getCell(15);//客户意向：A/B/C/D/E/F
			HSSFCell cell16 = hssfRow.getCell(16);//跟进记录
			HSSFCell cell17 = hssfRow.getCell(17);//下次跟进日期（下次提醒日期）
			HSSFCell cell18 = hssfRow.getCell(18);//潜客主键id（首次上传传空，追加上传不能传空）
			HSSFCell cell20 = hssfRow.getCell(20);//VIN码
			HSSFCell cell21 = hssfRow.getCell(21);//发送机号
			CrmCustomer crmCustomer = new CrmCustomer();
			
			//潜客主键id（首次上传传空，追加上传不能传空）
			String customerId = getValue(cell18);
			//如果customerId为空插入，不为空更新
			Integer custId = null;
			if(!StringUtils.isEmpty(customerId)) {
				custId = Integer.valueOf(customerId);
				crmCustomer.setId(custId);
			}
			
			//导入失败原因
			String uploadReason = "";
			//潜客上传批次号（首次上传传空，追加上传不能传空）
			String batchCode = getValue(cell0);
			if(StringUtils.isEmpty(batchCode) && StringUtils.isEmpty(systemBatchCode)) {
				systemBatchCode = this.crmCustomerService.createCrmBatch(batchCode);
			}else{
				CrmBatch batch = this.crmCustomerService.getCrmBatchByBatchCode(batchCode);
				if(null==batch) {
					this.crmCustomerService.createCrmBatch(batchCode);
				}
			}
			
			crmCustomer.setCrmBatchCode(StringUtils.isEmpty(batchCode) ? systemBatchCode : batchCode);
			//录入日期
			Date inputDate = getInputDateValue(cell1);
			crmCustomer.setCreateTime(inputDate);
			//客户名称
			String customerName = getValue(cell2);
			crmCustomer.setCustomerName(customerName);
			if(StringUtils.isEmpty(customerName)) {
				continue;
			}
			//客户等级
			String level = getValue(cell3);
			crmCustomer.setLevel(level);
			//联系方式
			String linkPhone = getValue(cell4);
			crmCustomer.setLinkPhone(linkPhone);
			//车牌
			String licensePlate = getValue(cell5);
			//1.导入时同车牌的客户信息自动跳过，并将结果反馈给用户
			if(StringUtils.isNotBlank(licensePlate)) {
				CrmCustomer tempCust = this.crmCustomerService.getCrmCustomerByLicensePlate(this.getCrmUser().getId(), licensePlate, custId);
				if(null!=tempCust) {
					uploadReason = "车牌为" + licensePlate + "的客户在同一个公司已存在，客户名称为：" + tempCust.getCustomerName();
				}
			}
			crmCustomer.setLicensePlate(licensePlate);
			//上牌日期
			String licenseTime = getValue(cell6);
			crmCustomer.setLicenseTime(licenseTime);
			//品牌
			String crmBrandName = getValue(cell7);
			crmCustomer.setCrmBrandName(crmBrandName);
			//车系
			String carSeries = getValue(cell8);
			crmCustomer.setCarSeries(carSeries);
			//排量
			String displacement = getValue(cell9);
			crmCustomer.setDisplacement(displacement);
			//年份
			String year = getValue(cell10);
			crmCustomer.setYear(year);
			//联系日期
			Date linkTime = getInputDateValue(cell11);
			crmCustomer.setLinkTime(linkTime);
			//介绍人
			String introducer = getValue(cell12);
			crmCustomer.setIntroducer(introducer);
			//业务员
			String crmUserName = getValue(cell13);
			//管理员
			CrmUser admin = this.getCrmUser();
			//2. 对于指定了业务员的记录，导入时检查业务员是否存在，不存在则导入失败，记录失败原因为：业务员不存在
			Integer adminId = admin.getId();
			Integer crmUserId = null;
			if(StringUtils.isNotBlank(crmUserName)) {
				CrmUser crmUser = this.crmUserService.getCrmUserByCrmShopNameAndUserName(admin.getCrmShopName(), crmUserName);
				if (null == crmUser) {
					if(StringUtils.isEmpty(uploadReason)){
						uploadReason = uploadReason + "业务员不存在";
					}else{
						uploadReason = uploadReason + "；业务员不存在";
					}
				} else if(null != crmUser && DefaultEnum.NO.getCode().equals(crmUser.getIsActive())){
					if(StringUtils.isEmpty(uploadReason)){
						uploadReason = uploadReason + "业务员已冻结";
					}else{
						uploadReason = uploadReason + "；业务员已冻结";
					}
				} else {
					crmUserId = crmUser.getId();
					crmCustomer.setCrmUserId(crmUserId);
					boolean flag = this.crmUserService.validateBizAdminBelongCompany(adminId, crmUserId);
					if(!flag) {
						if(StringUtils.isEmpty(uploadReason)){
							uploadReason = uploadReason + "业务员不属于当前客户经理所属的公司";
						}else{
							uploadReason = uploadReason + "；业务员不属于当前客户经理所属的公司";
						}
					}
				}
				crmCustomer.setCrmUserName(crmUserName);
			} else {
				crmCustomer.setCrmUserId(adminId);
			}
			//客户需求：维修/保险/新车销售
			String bizTypeName = getValue(cell14);
			String []bizTypes = bizTypeName.split("/");
			List<Integer> bizTypeList = new ArrayList<Integer>();
			for (int i = 0; i < bizTypes.length; i++) {
				String bizTypeStr = bizTypes[i];
				Resource resource = this.resourceService.getResourceByKeyAndDesc(ResourceService.CUSTOMER_REQUIRE_KEY, bizTypeStr);
				if (null == resource) {
					uploadReason = uploadReason + "；" + bizTypeStr + "业务类型不存在";
				}else{
					bizTypeList.add(resource.getResourceValue());
				}
			}
			if(null != crmUserId) {
				String bizTypeUploadReason = this.crmUserService.isCustBizTypeRight(bizTypeList, crmUserId);
				if(StringUtils.isNotBlank(bizTypeUploadReason)) {
					if(StringUtils.isEmpty(uploadReason)){
						uploadReason = uploadReason + bizTypeUploadReason;
					}else{
						uploadReason = uploadReason + "；" + bizTypeUploadReason;
					}
				}
			}
			//客户意向：A/B/C/D/E/F
			String purpose = getValue(cell15);
			crmCustomer.setPurpose(purpose);
			//导入状态 0导入失败   1导入成功未分配    2已分配
			if(StringUtils.isNotBlank(uploadReason)) {
				crmCustomer.setUploadReason(uploadReason);
				crmCustomer.setUploadStatus(CustomerUploadStatusEnum.UPLOAD_FAIL.getCode());
			}else{
				crmCustomer.setUploadStatus(CustomerUploadStatusEnum.UPLOAD_NOT_DISTRIBUTE.getCode());
			}

			Integer operatorId = -1000;
			if(null!=SessionContainer.getSession() && null!=SessionContainer.getSession().getCrmUser()) {
				operatorId = SessionContainer.getSession().getCrmUser().getId();
			}
			crmCustomer.setCrmOperatorId(operatorId);
			String vin = getValue(cell20);
			String engine = getValue(cell21);
			crmCustomer.setVin(vin);
			crmCustomer.setEngine(engine);
			//保存潜客
			this.crmCustomerService.saveOrUpdateCrmCustomer(crmCustomer);
			if(null==custId) {
				custId = crmCustomer.getId();
			}
			//保存业务类型
			if(!CollectionUtils.isEmpty(bizTypeList)) {
				if(null==custId) {
					this.crmCustomerService.deleteCrmCustBizType(custId);
				}	
				for(Integer bizType : bizTypeList){
					CrmCustBizType crmCustBizType = new CrmCustBizType();
					crmCustBizType.setCrmCustomerId(custId);
					crmCustBizType.setBizType(bizType);
					this.crmCustomerService.saveCrmCustBizType(crmCustBizType);
				}
			}
			//保存跟进记录
			String remark = getValue(cell16);
			if(StringUtils.isNotBlank(remark)) {
				CrmCustTrace crmCustTrace = new CrmCustTrace();
				crmCustTrace.setCrmCustomerId(custId);
				crmCustTrace.setCrmUserId(operatorId);
				crmCustTrace.setRemark(remark);
				this.crmCustomerService.saveCrmCustTrace(crmCustTrace);
			}
			//保存下次跟进日期（下次提醒日期）
			Date remindTime = getInputDateValue(cell17);
			if(null != remindTime) {
				CrmCustRemaind crmCustRemaind = new CrmCustRemaind();
				crmCustRemaind.setCrmCustomerId(custId);
				crmCustRemaind.setCrmUserId(operatorId);
				crmCustRemaind.setIsRemind(DefaultEnum.YES.getCode());
				crmCustRemaind.setRemindTime(remindTime);
				this.crmCustomerService.saveCrmCustRemaind(crmCustRemaind);
			}
		}
		return ResultUtil.successMap(ResultUtil.DATA_INSERT_SUCC);
	}

	private String getValue(HSSFCell hssfCell) {
		if(null==hssfCell) {
			return null;
		}
		// 返回字符串类型的值
		hssfCell.setCellType(CellType.STRING);
		return String.valueOf(hssfCell.getStringCellValue());
	}
	
	private Date getDateValue(HSSFCell hssfCell) {
		if(null==hssfCell) {
			return null;
		}
		return hssfCell.getDateCellValue();
	}

	@SuppressWarnings("deprecation")
	private Date getInputDateValue(HSSFCell hssfCell) {
		if(null==hssfCell) {
			return null;
		}
	    switch (hssfCell.getCellType()) {
		    case Cell.CELL_TYPE_STRING:
				hssfCell.setCellType(CellType.STRING);
		        return DateUtil.defaultParseDate(String.valueOf(hssfCell.getStringCellValue()));
		    case Cell.CELL_TYPE_NUMERIC:
	            return hssfCell.getDateCellValue();
		    default:
		        return null;
	    }	
	 }
	
	public static final void main(String []args) {
		String s1 = "星奥车联-A加印象店";
		String s2 = "A加印象店";
		System.out.println(s1.replace("星奥车联-", ""));
		System.out.println(s2.replace("星奥车联-", ""));
	}
}
