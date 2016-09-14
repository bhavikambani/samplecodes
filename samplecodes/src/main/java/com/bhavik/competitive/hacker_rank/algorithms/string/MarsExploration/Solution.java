package com.bhavik.competitive.hacker_rank.algorithms.string.MarsExploration;

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
		String SOS = "SOS";
		Scanner in = new Scanner(System.in);
		char[] S = in.next().trim().toCharArray();
		in.close();
		int stringLength = S.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < stringLength / 3; i++) {
			sb.append(SOS);
		}
		char[] expectedString = sb.toString().toCharArray();
		int numberOfChangedLetters = 0;
		for (int i = 0; i < stringLength; i++) {
			if (S[i] != expectedString[i]) {
				numberOfChangedLetters++;
			}
		}
		System.out.println(numberOfChangedLetters);
	}

}
