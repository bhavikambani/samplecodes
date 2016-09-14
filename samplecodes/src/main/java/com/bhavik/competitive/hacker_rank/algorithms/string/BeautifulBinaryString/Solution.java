package com.bhavik.competitive.hacker_rank.algorithms.string.BeautifulBinaryString;

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
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		char[] binaryString = in.next().toCharArray();
		in.close();
		int swapCounter = 0;
		for (int i = 0; i < n - 2; i++) {
			if (binaryString[i] == '0' && binaryString[i + 2] == '0') {
				binaryString[i + 2] = '1';
				swapCounter++;
			}
		}
		System.out.println(swapCounter);
	}
}