package com.bhavik.competitive.hr.algorithms.string.alternatingcharacters;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/alternating-characters
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
			System.out.println(getAlternatingCharsCount(in.next()));
		}
		in.close();
	}

	/**
	 * Gets the alternating chars count.
	 *
	 * @param string
	 *            the string
	 * @return the alternating chars count
	 */
	public static int getAlternatingCharsCount(String string) {
		int alternatingCharsCount = 0;
		char[] stringArray = string.toCharArray();
		for (int i = 0; i < stringArray.length - 1; i++) {
			if (stringArray[i] == stringArray[i + 1]) {
				alternatingCharsCount++;
			}
		}
		return alternatingCharsCount;
	}

}
