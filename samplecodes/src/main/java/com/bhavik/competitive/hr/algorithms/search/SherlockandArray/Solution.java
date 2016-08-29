package com.bhavik.competitive.hr.algorithms.search.SherlockandArray;

import java.util.Scanner;

/**
 * The Class Solution. <br>
 * https://www.hackerrank.com/challenges/sherlock-and-array
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
		Scanner sc = new Scanner(System.in);
		int totalCases = sc.nextInt();
		for (int i = 0; i < totalCases; i++) {
			int totalNumbers = sc.nextInt();
			long totalSum = 0;
			int[] vals = new int[totalNumbers];
			for (int j = 0; j < totalNumbers; j++) {
				vals[j] = sc.nextInt();
				totalSum += vals[j];
			}
			System.out.println(isSherlockArray(vals, totalSum) ? "YES" : "NO");
		}
		sc.close();
	}

	/**
	 * Checks if is sherlock array.
	 *
	 * @param vals
	 *            the vals
	 * @param totalSum
	 *            the total sum
	 * @return true, if is sherlock array
	 */
	public static boolean isSherlockArray(int[] vals, long totalSum) {
		long leftSum = 0;
		long rightSum = totalSum;

		for (int i = 0; i < vals.length; i++) {
			rightSum = rightSum - vals[i];
			if (leftSum == rightSum) {
				return true;
			}
			leftSum += vals[i];
		}
		return false;
	}
}
