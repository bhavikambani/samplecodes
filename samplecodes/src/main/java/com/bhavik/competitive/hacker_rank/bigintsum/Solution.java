package com.bhavik.competitive.hacker_rank.bigintsum;

import java.util.Scanner;

/**
 * The Class Solution.
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class Solution {

	/**
	 * Solve me first.
	 *
	 * @param a
	 *            the a
	 * @param b
	 *            the b
	 * @return the int
	 */
	static int solveMeFirst(int a, int b) {
		return a + b;

	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int a;
		a = in.nextInt();
		int b;
		b = in.nextInt();
		in.close();
		int sum;
		sum = solveMeFirst(a, b);
		System.out.println(sum);
	}
}
