package com.bhavik.competitive.tech_gig.combat.MaxContiguousSubArray;

import java.util.Scanner;

/**
 * The Class CandidateCode will implement the solution of TechGig challenge of
 * max contiguous sub array challenge.
 * 
 * @author Bhavik Ambani
 */
public class CandidateCode {

	/**
	 * The main method.
	 *
	 */
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		int totalNumbers = in.nextInt();
		int[] values = new int[totalNumbers];

		for (int i = 0; i < totalNumbers; i++) {
			values[i] = in.nextInt();
		}

		System.out.println(maxSubArraySum(values));
	}

	/**
	 * Max sub array sum from the array.
	 *
	 * @param values
	 *            the array of numbers which is passed as input
	 * @return the max sum of subarray
	 */
	static int maxSubArraySum(int values[]) {
		int size = values.length;
		int maxFar = 0, maxEnding = 0;

		for (int i = 0; i < size; i++) {
			maxEnding = maxEnding + values[i];
			if (maxEnding < 0)
				maxEnding = 0;
			if (maxFar < maxEnding)
				maxFar = maxEnding;
		}
		return maxFar;
	}
}