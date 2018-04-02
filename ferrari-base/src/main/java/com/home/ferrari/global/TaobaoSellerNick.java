package com.home.ferrari.global;

import java.util.HashMap;
import java.util.Map;

public class TaobaoSellerNick {
	
	private static Map<String,String> map = new HashMap<>();
	private static Map<String,String> englishMap = new HashMap<>();
	static {
		map.put("阿里车码头旗舰店", "星奥车联");//每周导入的标准洗车订单
		map.put("天猫星奥汽车服务专营店", "星奥汽车服务专营店");//新的天猫店  
		map.put("淘宝星奥汽车服务店", "杭州星奥商家");//新的淘宝店  
		map.put("安装服务虚拟店铺", "安装服务虚拟店铺");//安装服务虚拟店铺
		map.put("村淘星奥车联实体服务商家", "村淘星奥车联实体服务商家");//村淘店
		//map.put("星奥车联汽车服务旗舰店", "星奥车联汽车服务旗舰店");//老的天猫店

		englishMap.put("阿里车码头旗舰店", "alichematou");//每周导入的标准洗车订单
		englishMap.put("天猫星奥汽车服务专营店", "tmallxingao");//新的天猫店  
		englishMap.put("淘宝星奥汽车服务店", "taobaoxingao");//新的淘宝店  
		englishMap.put("安装服务虚拟店铺", "anzhuangfuwu");//安装服务虚拟店铺
		englishMap.put("村淘星奥车联实体服务商家", "cuntaoxingao");//村淘店
		//englishMap.put("星奥车联汽车服务旗舰店", "xingaochelian");//老的天猫店
	}

	public static String getValue(String key) {
		return map.get(key);
	}
	
	public static Map<String,String> map() {
		return TaobaoSellerNick.map;
	}

	public static String getEnglishValue(String key) {
		return englishMap.get(key);
	}
	
	public static Map<String,String> englishMap() {
		return TaobaoSellerNick.englishMap;
	}
}
