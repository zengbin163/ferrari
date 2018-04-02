package com.home.ferrari.web.site.shop;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.base.ResultCode;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.enums.ShopOperateEnum;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.service.tesla.TeslaShopService;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.util.WebUtil;
import com.home.ferrari.vo.tesla.shop.TeslaShop;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/shop")
public class ShopAction extends BaseAction {

	private static final long serialVersionUID = -867182622064342887L;
	@Autowired
	private TeslaShopService teslaShopService;	
	
	@RequestMapping("create")
	@ResponseBody
	@LoginRequired(needLogin = true, needAuth = true)
	public Map<String, Object> create() {
		String shopJson = this.getFilteredParameter(request, "shopJson", 0, null);
		return this.teslaShopService.createShop(shopJson, getFerrariUser().getId(), getFerrariUser().getMobile());
	}

	@RequestMapping("createByH5")
	@ResponseBody
	@LoginRequired(needLogin = false, needAuth = false)
	public Map<String, Object> createByH5() {
		String shopJson = this.getFilteredParameter(request, "shopJson", 0, null);
		return this.teslaShopService.createShopByH5(shopJson);
	}

	@RequestMapping("edit")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> edit() {
		String shopJson = this.getFilteredParameter(request, "shopJson", 0, null);
		Integer operatorId = 0;
		String nickName = null;
		if(null!=getFerrariUser()){
			operatorId = getFerrariUser().getId();
			nickName = getFerrariUser().getMobile();
		}else{
			operatorId = getTeslaUser().getId();
			nickName = getTeslaUser().getMobile();
		}
		return this.teslaShopService.editShop(shopJson, operatorId, nickName);
	}

	@RequestMapping("expand")
	@ResponseBody
	@LoginRequired(needLogin = true, needAuth = true)
	public Map<String, Object> expand() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		Integer shopExpandStatus = this.getIntParameter(request, "shopExpandStatus", null);
		return this.teslaShopService.expandShop(shopId, shopExpandStatus, getFerrariUser().getId(), getFerrariUser().getMobile());
	}

	@RequestMapping("audit")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> audit() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		Integer shopStatus = this.getIntParameter(request, "shopStatus", null);
		String remark = WebUtil.decode(this.getFilteredParameter(request, "remark", 0, null));
		Integer shopType = this.getIntParameter(request, "shopType", null);
		return this.teslaShopService.auditShop(shopId, shopStatus, getFerrariUser().getId(), getFerrariUser().getMobile(), remark, shopType);
	}
	
	@RequestMapping("trace")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> trace() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		String remark = WebUtil.decode(this.getFilteredParameter(request, "remark", 0, null));
		TeslaShop teslaShop = this.teslaShopService.getTeslaShopById(shopId);
		if(null == teslaShop) {
			throw new FerrariBizException(ResultCode.SHOP_NOT_EXISTS, getFerrariUser().getId(), "门店不存在，shopId=" + shopId);
		}
		this.teslaShopService.traceShop(shopId, getFerrariUser().getId(),
				getFerrariUser().getMobile(), ShopOperateEnum.SELF.getCode(),
				DefaultEnum.YES.getCode(), teslaShop.getShopExpandStatus(),
				teslaShop.getShopStatus(), remark);
		return ResultUtil.successMap("success");
	}
	
	@RequestMapping("list")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> list() {
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
		Integer roleType = this.getRoleType();
		return this.teslaShopService.getTeslaShopList(pageOffset, pageSize,
				shopName, province, city, shopStatus, shopExpandStatus,
				monitorStatus, roleType, getFerrariUser().getId(), shopType,
				abcType);
	}
	
	@RequestMapping("detail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> detail() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		return this.teslaShopService.detailShop(shopId);
	}

	@RequestMapping("baseShop")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> baseShop() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		return ResultUtil.successMap(this.teslaShopService.getTeslaShopById(shopId));
	}
	
	

	@RequestMapping("brandList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> brandList() {
		String brandName = this.getFilteredParameter(request, "brandName", 0, null);
		return this.teslaShopService.getAllBrandList(brandName);
	}

	@RequestMapping("remarkList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> remarkList() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		Integer roleType = this.getIntParameter(request, "roleType", null);
		return this.teslaShopService.remarkList(shopId, roleType);
	}
	
}
