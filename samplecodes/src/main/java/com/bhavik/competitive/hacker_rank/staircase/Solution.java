package com.bhavik.competitive.hacker_rank.staircase;

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
		in.close();
		int temp = 1;
		for (int i = n; i > 0; i--) {

			for (int j = i - 1; j > 0; j--) {
				System.out.print(" ");
			}

			for (int k = 1; k <= temp; k++) {
				System.out.print("#");
			}
			System.out.println();
			temp++;
		}

	}
}
