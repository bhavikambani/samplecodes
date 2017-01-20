package com.bhavik.competitive.code_chef.practice.CatsandDogs;

import java.util.Scanner;

/**
 * The Class Solution for https://www.codechef.com/problems/CATSDOGS.
 *
 * @author Bhavik Ambani
 */
class Solution {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String args[]) {
		long t, c, d, l;
		Scanner in = new Scanner(System.in);
		t = in.nextLong();
		while (t > 0) {
			c = in.nextLong();
			d = in.nextLong();
			l = in.nextLong();
			if (l % 4 == 0 && c <= (d * 2) && l <= ((c + d) * 4) && l >= (d * 4)) {
				System.out.println("yes");
			} else if (l % 4 == 0 && c > (d * 2) && l <= ((c + d) * 4) && l >= ((d + (c - (d * 2))) * 4)) {
				System.out.println("yes");
			} else {
				System.out.println("no");
			}
			t--;
		}
		in.close();
	}
}