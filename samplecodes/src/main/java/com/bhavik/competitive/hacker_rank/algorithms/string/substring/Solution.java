package com.bhavik.competitive.hacker_rank.algorithms.string.substring;

import java.util.BitSet;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/two-strings
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
		int testCases = in.nextInt();
		for (int i = 0; i < testCases; i++) {
			String str1 = in.next();
			String str2 = in.next();
			if (isSubStr(str1.toCharArray(), str2.toCharArray())) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}
		in.close();
	}

	/**
	 * Checks if is sub str.
	 *
	 * @param str1
	 *            the str1
	 * @param str2
	 *            the str2
	 * @return true, if is sub str
	 */
	private static boolean isSubStr(char[] str1, char[] str2) {
		boolean isSubStr = false;
		BitSet bs = new BitSet(26);
		char[] largestString = str1;
		char[] smallestString = str2;
		if (str1.length < str2.length) {
			largestString = str2;
			smallestString = str1;
		}
		for (int i = 0; i < largestString.length; i++) {
			bs.set(largestString[i] - 97);
		}
		for (int i = 0; i < smallestString.length; i++) {
			if (bs.get(smallestString[i] - 97)) {
				isSubStr = true;
				break;
			}
		}
		return isSubStr;
	}

}
