package com.home.ferrari.status;

import java.io.Serializable;

public class MonitorStatus implements Serializable {
	private static final long serialVersionUID = 1888633966387758237L;

	public static final Integer SHOP_BASE_BEGIN = 300; // 基础信息填写完成，未开始完善信息
	public static final Integer SHOP_CAPACITY = 301; // 完善信息完成，未开始能力评估
	public static final Integer SHOP_FINISH = 302; // 能力评估完成
	public static final Integer SHOP_SUBMIT = 303; // 能力评估完成并提交了审核
	public static final Integer SHOP_BACK = 304; // 能力评估完成并提交了审核，但是被打回了

	public static final Integer SHOP_AUDIT_FINISH = 306; // 门店审核通过并提交上线
}
