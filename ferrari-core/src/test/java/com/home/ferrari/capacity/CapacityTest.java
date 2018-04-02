package com.home.ferrari.capacity;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.home.ferrari.vo.capacity.Capacity;
import com.home.ferrari.vo.capacity.CapacityHeader;

public class CapacityTest {

	public static void main(String[] args) {
		String json = "{\"groupId\":1,\"header\":{\"headerName\":\"x\",\"inputType\":1,\"order\":1},\"capacityList\":[{\"id\":1,\"name\":\"y\",\"inputType\":1,\"order\":1},{\"id\":2,\"name\":\"z\",\"inputType\":2,\"order\":2}]}";
		JSONObject jsonObj = JSONObject.parseObject(json);
		System.out.println(jsonObj.get("groupId"));
		System.out.println(JSONObject.parseObject(jsonObj.getString("header"),CapacityHeader.class));
		List<Capacity> list = JSONArray.parseArray(jsonObj.getString("capacityList"),Capacity.class);
		System.out.println(list);
		System.out.println("11111111111111111111111");
	}

}
