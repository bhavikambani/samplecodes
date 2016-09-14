package com.bhavik.competitive.hacker_rank.algorithms.string.RichieRich;

import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * The Class Solution.
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
		// int n = in.nextInt();
		// int k = in.nextInt();
		String number = in.next();
		in.close();
		System.out.println(isPalindrome(number));
	}

	/**
	 * Checks if is palindrome.
	 *
	 * @param s
	 *            the s
	 * @return true, if is palindrome
	 */
	private static int isPalindrome(String s) {
		int n = s.length();
		int nonPalCount = 0;
		for (int i = 0; i < (n / 2); ++i) {
			if (s.charAt(i) != s.charAt(n - i - 1)) {
				nonPalCount++;
			}
		}
		return nonPalCount;
	}
}