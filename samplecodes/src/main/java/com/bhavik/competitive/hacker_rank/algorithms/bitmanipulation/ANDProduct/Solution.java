package com.bhavik.competitive.hacker_rank.algorithms.bitmanipulation.ANDProduct;

import java.util.Scanner;

/**
 * The Class Solution.
 * 
 * @author Bhavik Aniruddh Ambani
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
		int cases = in.nextInt();

		for (int i = 0; i < cases; i++) {
			long a = in.nextLong();
			long b = in.nextLong();
			long bitWiseAndVal = a;
			if ((String.valueOf(a).length() != String.valueOf(b).length())) {
				System.out.println(0);
			} else {
				for (long j = a + 1; j <= b; j++) {
					// System.out.println(Long.toBinaryString(j));
					bitWiseAndVal &= j;
				}
				System.out.println(bitWiseAndVal);
			}
		}
		in.close();
	}
}