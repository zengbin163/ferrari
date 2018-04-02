package com.home.ferrari.account;

import java.util.HashSet;
import java.util.Set;

import com.home.ferrari.vo.account.AccountList;

public class AccountHeaderTest {

	public static void main(String[] args) {
		AccountList list1 = new AccountList();
		list1.setFinalNo("123");
		list1.setSerialNo("123");
		AccountList list2 = new AccountList();
		list2.setFinalNo("123");
		list2.setSerialNo("123");
		Set<AccountList> set = new HashSet<>();
		set.add(list1);
		set.add(list2);
		System.out.println(set.size());
	}

}
