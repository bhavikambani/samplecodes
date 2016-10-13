package com.bhavik.competitive.hacker_rank.algorithms.implementations.RepeatedString;

import java.util.Scanner;

/**
 * The Class Solution of the problem of counting number of a in the sequence of
 * string.
 * 
 * Ref# https://www.hackerrank.com/challenges/repeated-string
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
		String s = in.next();
		long n = in.nextLong();
		in.close();

		char[] sChars = s.toCharArray();
		int countInSingleString = 0;
		for (int i = 0; i < sChars.length; i++) {
			if (sChars[i] == 'a')
				countInSingleString++;
		}
		if (countInSingleString == 0) {
			System.out.println(0);
		} else {
			long totalSets = n / sChars.length;
			if (n == (totalSets * sChars.length)) {
				System.out.println(countInSingleString * totalSets);
			} else {
				char[] substring = s.substring(0, (int) (n - totalSets * sChars.length)).toCharArray();
				long totalCount = countInSingleString * totalSets;
				for (int i = 0; i < substring.length; i++) {
					if (substring[i] == 'a')
						totalCount++;
				}
				System.out.println(totalCount);
			}
		}
	}
}
