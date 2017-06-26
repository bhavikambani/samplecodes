package com.bhavik.competitive.hacker_rank.algorithms.implementations.BreakingTheRecords;

import java.util.Scanner;

/**
 * @author Bhavikkumar Ambani
 * @createdOn June 26, 2017
 *
 */
public class Solution {

	static int[] getRecord(int[] s) {
		int[] result = { 0, 0 };
		int minVal = s[0], maxVal = s[0];

		for (int i = 1; i < s.length; i++) {
			if (s[i] < minVal) {
				result[1]++;
				minVal = s[i];
			} else if (s[i] > maxVal) {
				result[0]++;
				maxVal = s[i];
			}
		}
		return result;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[] s = new int[n];
		for (int s_i = 0; s_i < n; s_i++) {
			s[s_i] = in.nextInt();
		}
		int[] result = getRecord(s);
		String separator = "", delimiter = " ";
		for (Integer val : result) {
			System.out.print(separator + val);
			separator = delimiter;
		}
		System.out.println("");
	}
}