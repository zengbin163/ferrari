package com.home.ferrari.status;

public class ComplainStatus {
	public static final Integer WAITING = 700; // 待处理
	public static final Integer DOING = 701; // 处理中（客服手动切换/门店发起申诉）
	public static final Integer DONE = 702; // 已完成（解决成功/解决失败）
	public static final Integer CLOSE = 703; // 已关闭

	public static String getDesc(Integer status) {
		String desc = null;
		switch (status) {
		case 700:
			desc = "待处理";
			break;
		case 701:
			desc = "处理中";
			break;
		case 702:
			desc = "已完成";
			break;
		case 703:
			desc = "已关闭";
			break;
		default:
			break;
		}
		return desc;
	}
}
