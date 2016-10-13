package com.bhavik.competitive.hacker_rank.algorithms.implementations.NonDivisibleSubset;

import java.util.Scanner;

/**
 * The Class Solution of Divisible Sum Pairs.
 * 
 * Ref# https://www.hackerrank.com/challenges/divisible-sum-pairs
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
		int k = in.nextInt();
		int a[] = new int[n];
		int nonDivisibleSumPairs = 0;
		for (int a_i = 0; a_i < n; a_i++) {
			a[a_i] = in.nextInt();
		}
		in.close();

		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if ((a[i] + a[j]) % k != 0) {
					nonDivisibleSumPairs++;
				}
			}
		}

		System.out.println(nonDivisibleSumPairs);
	}
}