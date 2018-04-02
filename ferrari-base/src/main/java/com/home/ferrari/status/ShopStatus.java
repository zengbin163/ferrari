package com.home.ferrari.status;

import java.io.Serializable;

public class ShopStatus implements Serializable {
	private static final long serialVersionUID = 1888633966387758237L;

	public static final Integer SHOP_BD = 		100; // BD已提交
	public static final Integer SHOP_ADMIN = 	101; // 待门店管理员审核(待大区审核)
	public static final Integer SHOP_FINANCE = 	102; // 待财务审核
	public static final Integer SHOP_LAW = 		103; // 待法务审核
	public static final Integer SHOP_VI = 		104; // 待VI审核
	public static final Integer SHOP_MANAGER = 	105; // 待总经理审核
	public static final Integer SHOP_SUCCESS = 	106; // 审核通过
	public static final Integer SHOP_ADMIN_RE = 107; // 待门店管理员复核

	public static final Integer SHOP_FROZEN = 404; // 门店被冻结
	public static final Integer SHOP_RETURN = 444;//提交门店管理员审核之后被门店管理员打回
	
}
