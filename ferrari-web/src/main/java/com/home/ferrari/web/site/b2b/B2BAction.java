package com.home.ferrari.web.site.b2b;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.service.b2b.B2bProductService;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.b2b.B2bProduct;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/b2b")
public class B2BAction extends BaseAction{

	private static final long serialVersionUID = -2304470574409480185L;
	
	@Autowired
	private B2bProductService b2bProductService;

	@RequestMapping("b2bIndex")
	@ResponseBody
	@LoginRequired(needLogin = false)
	public Map<String, Object> b2bIndex() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer categoryId = this.getIntParameter(request, "categoryId", null);
		Integer isIndex = this.getIntParameter(request, "isIndex", null); //是否上首页  0不上  1上
		return this.b2bProductService.b2bIndex(pageOffset, pageSize, categoryId, isIndex);
	}

	@RequestMapping("b2bDetail")
	@ResponseBody
	@LoginRequired(needLogin = false)
	public Map<String, Object> b2bDetail() {
		Integer productId = this.getIntParameter(request, "productId", null);
		return this.b2bProductService.productDetail(productId, DefaultEnum.YES.getCode());
	}
	
	@RequestMapping("report")
	@ResponseBody
	@LoginRequired(needLogin = false)
	public Map<String, Object> report() {
		Integer productId = this.getIntParameter(request, "productId", null);
		this.b2bProductService.reportB2BProductViewCount(productId);
		return ResultUtil.successMap(ResultUtil.DATA_UPDATE_SUCC);
	}
	
	@RequestMapping("publish")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> publish() {
		String json = this.getFilteredParameter(request, "json", 0, null);
		return this.b2bProductService.publishB2BProduct(json);
	}

	@RequestMapping("edit")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> edit() {
		String json = this.getFilteredParameter(request, "json", 0, null);
		return this.b2bProductService.editB2BProduct(json);
	}

	@RequestMapping("up")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> up() {
		Integer productId = this.getIntParameter(request, "productId", null);
		B2bProduct b2bProduct = new B2bProduct();
		b2bProduct.setProductId(productId);
		b2bProduct.setCanShow(DefaultEnum.YES.getCode());
		return this.b2bProductService.updateB2BProduct(b2bProduct);
	}

	@RequestMapping("down")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> down() {
		Integer productId = this.getIntParameter(request, "productId", null);
		B2bProduct b2bProduct = new B2bProduct();
		b2bProduct.setProductId(productId);
		b2bProduct.setCanShow(2);
		return this.b2bProductService.updateB2BProduct(b2bProduct);
	}

	@RequestMapping("categoryList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> categoryList() {
		return this.b2bProductService.getB2bCategoryList();
	}
	
	@RequestMapping("brandList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> brandList() {
		return this.b2bProductService.getB2bBrandList();
	}
	
	@RequestMapping("productList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> productList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer canShow = this.getIntParameter(request, "canShow", null);
		String productName = this.getFilteredParameter(request, "productName", 0, null);
		Integer categoryId = this.getIntParameter(request, "categoryId", null);
		Integer isIndex = this.getIntParameter(request, "isIndex", null); //是否上首页  0不上  1上
		return this.b2bProductService.getB2bProductList(pageOffset, pageSize, productName, canShow, categoryId, isIndex);
	}
	
	@RequestMapping("productDetail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> productDetail() {
		Integer productId = this.getIntParameter(request, "productId", null);
		return this.b2bProductService.productDetail(productId, null);
	}

}
