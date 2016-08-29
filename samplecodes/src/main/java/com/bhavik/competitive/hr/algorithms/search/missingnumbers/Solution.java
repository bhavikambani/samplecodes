package com.bhavik.competitive.hr.algorithms.search.missingnumbers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * The Class Solution.
 * 
 * @author bhavik
 */
public class Solution {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int aNumLength = in.nextInt();
		ArrayList<Integer> val1 = new ArrayList<Integer>();
		for (int i = 0; i < aNumLength; i++) {
			val1.add(in.nextInt());
		}

		int bNumLength = in.nextInt();
		ArrayList<Integer> val2 = new ArrayList<Integer>();
		for (int i = 0; i < bNumLength; i++) {
			val2.add(in.nextInt());
		}

		List<Integer> finalList = new ArrayList<Integer>();
		if (val1.size() > val2.size()) {
			for (Integer t : val2) {
				val1.remove(t);
			}
			Collections.sort(val1);
			finalList = val1;
		} else {
			for (Integer t : val1) {
				val2.remove(t);
			}
			Collections.sort(val2);
			finalList = val2;
		}

		StringBuilder sb = new StringBuilder();
		for (Integer i : finalList) {
			sb.append(i + " ");
		}
		System.out.println(sb.toString().trim());
	}

}
