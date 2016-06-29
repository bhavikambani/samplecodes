package com.bhavik.competitive.hr.algorithms.bitmanipulation.MaximizingXOR;

import java.util.Scanner;

/**
 * The Class Solution for the solution
 * 
 * @see <a href="https://www.hackerrank.com/challenges/maximizing-xor"
 *      >maximizing-xor</a>
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class Solution {
	/**
	 * Max xor.
	 *
	 * @param l
	 *            the l
	 * @param r
	 *            the r
	 * @return the int
	 */
	static int maxXor(int l, int r) {
		int maxXor = 0;
		for (int i = l; i <= r; i++) {
			for (int j = i; j <= r; j++) {
				if (maxXor < (j ^ i)) {
					maxXor = j ^ i;
				}
			}
		}
		return maxXor;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int res;
		int _l = Integer.parseInt(in.nextLine());
		int _r = Integer.parseInt(in.nextLine());
		in.close();
		res = maxXor(_l, _r);
		System.out.println(res);
	}
}
