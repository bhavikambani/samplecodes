package com.bhavik.competitive.hr.algorithms.dynamicprogramming.candies;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/candies
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class Solution {

	/** The ranks. */
	private int[] ranks;

	/** The candies. */
	private int[] candies;

	/**
	 * Run.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void run() throws IOException {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		ranks = new int[n];
		candies = new int[n];
		Arrays.fill(candies, -1);

		for (int i = 0; i < n; i++) {
			ranks[i] = sc.nextInt();
		}

		if (n == 1) {
			System.out.println("1");
			return;
		}

		int ind = 0;
		while (ind < ranks.length - 1) {
			int a = ind;

			while (ind < ranks.length - 1 && ranks[ind] >= ranks[ind + 1]) {
				ind++;
			}
			if (a != ind) {
				fill(ind, a, -1);
			}

			a = ind;
			while (ind < ranks.length - 1 && ranks[ind] <= ranks[ind + 1]) {
				ind++;
			}
			if (a != ind) {
				fill(a, ind, 1);
			}
		}

		long sum = 0;
		for (int i = 0; i < n; i++) {
			sum += candies[i];
		}
		System.out.println(sum);
	}

	/**
	 * Fill.
	 *
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @param step
	 *            the step
	 */
	public void fill(int start, int end, int step) {
		int v = 1;
		candies[start] = 1;
		for (int i = start + step; i != end; i += step) {
			v = incV(i, step, v);
			candies[i] = v;
		}
		v = incV(end, step, v);
		if (candies[end] == -1) {
			candies[end] = v;
		} else {
			candies[end] = Math.max(candies[end], v);
		}
	}

	/**
	 * Inc v.
	 *
	 * @param i
	 *            the i
	 * @param step
	 *            the step
	 * @param v
	 *            the v
	 * @return the int
	 */
	public int incV(int i, int step, int v) {
		if (ranks[i] == ranks[i - step]) {
			v = 1;
		} else {
			v += 1;
		}
		return v;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		new Solution().run();
	}
}