package com.bhavik.competitive.hacker_rank.angryprofessor;

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
		int t = in.nextInt();
		for (int a0 = 0; a0 < t; a0++) {
			int n = in.nextInt();
			int k = in.nextInt();
			int presentCount = 0;
			for (int a_i = 0; a_i < n; a_i++) {
				if (in.nextInt() <= 0) {
					presentCount++;
				}
			}
			in.close();
			if (presentCount >= k) {
				System.out.println("NO");
			} else {
				System.out.println("YES");
			}
		}
	}
}
