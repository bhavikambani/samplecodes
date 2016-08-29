package com.bhavik.competitive.hr.algorithms.search.MaximumSubarraySum;

import java.util.Scanner;
import java.util.TreeSet;

/**
 * The Class Solution.
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class Solution {

	/**
	 * Gets the max sub array sum.
	 *
	 * @param set
	 *            the set
	 * @param modVal
	 *            the mod val
	 * @return the max sub array sum
	 */
	static long getMaxSubArraySum(long[] set, long modVal) {
		int c = set.length;
		TreeSet<Long> totalSumValues = new TreeSet<Long>();
		for (int i = 0; i < (1 << c); i++) {
			long tempVal = 0;
			for (int j = 0; j < c; j++) {
				if ((i & (1 << j)) > 0) {
					tempVal += set[j];
				}
			}
			totalSumValues.add(tempVal % modVal);
		}
		return totalSumValues.last();
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int testCases = sc.nextInt();

		for (int i = 0; i < testCases; i++) {
			int totalNums = sc.nextInt();
			long modVal = sc.nextLong();
			long[] vals = new long[totalNums];

			for (int j = 0; j < totalNums; j++) {
				vals[j] = sc.nextLong();
			}
			long maxSubVal = getMaxSubArraySum(vals, modVal);
			System.out.println(maxSubVal);
		}
	}

}
