package com.elitecore.src.projects.samplecodes;

import java.util.ArrayList;
import java.util.List;

public class LonelyInteger {

	static int lonelyinteger(int[] a) {
		List<Integer> intList = new ArrayList<Integer>();
		int lonelyInt = 0;
		for (int i = 0; i < a.length; i++) {
			int val = a[i];
			if (intList.contains(val)) {
				intList.remove(new Integer(val));
			} else {
				intList.add(val);
			}
		}
		if (intList.size() > 0) {
			lonelyInt = intList.get(0);
		}
		return lonelyInt;
	}

	public static void main(String args[]) {

		System.out.println(lonelyinteger(new int[] { 1, 1, 3, 5, 5 }));

	}
}
