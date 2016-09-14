package com.bhavik.competitive.hacker_rank.algorithms.string.palindromindex;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/palindrome-index
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
			System.out.println(getPaliIndex(in.next().toCharArray()));
		}
		in.close();
	}

	public static int getPaliIndex(char[] string) {

		int paliIndex = -1;
		int totalCount = 0;
		int midIndex = string.length / 2;
		for (int i = 0, j = string.length - 1; i < midIndex; i++, j--) {
			if (string[i] == string[j]) {
				continue;
			} else if (string[i + 1] == string[j]) {
				totalCount++;
				paliIndex = i;
				i++;
			} else if (string[i] == string[j - 1]) {
				totalCount++;
				paliIndex = j;
				j--;
			} else {
				paliIndex = -1;
				break;
			}
		}
		if (totalCount > 1) {
			return -1;
		}
		return paliIndex;
	}
}
