package com.bhavik.competitive.hacker_rank.algorithms.string.CommonChild;

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
		Scanner sc = new Scanner(System.in);
		char[] stringVal1 = sc.nextLine().toCharArray();
		char[] stringVal2 = sc.nextLine().toCharArray();
		int stringIntArray[][] = new int[stringVal1.length + 1][stringVal1.length + 1];
		for (int i = 0; i <= stringVal1.length; i++) {
			for (int j = 0; j <= stringVal2.length; j++) {
				if (i == 0 || j == 0)
					stringIntArray[i][j] = 0;
				else if (stringVal1[i - 1] == stringVal2[j - 1]) {
					stringIntArray[i][j] = stringIntArray[i - 1][j - 1] + 1;
				} else {
					stringIntArray[i][j] = Math.max(stringIntArray[i - 1][j], stringIntArray[i][j - 1]);
				}
			}
		}
		sc.close();
		System.out.println(stringIntArray[stringVal1.length][stringVal2.length]);
	}
}