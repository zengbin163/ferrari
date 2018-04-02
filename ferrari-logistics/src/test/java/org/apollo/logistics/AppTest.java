package org.apollo.logistics;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	public static final void main(String[] args) {
		String sss = "维修";
		String []s = sss.split("/");
		for(int i=0;i<s.length;i++) {
			System.out.println(s[i]);
		}
	}
}
