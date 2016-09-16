package com.bhavik.competitive.hacker_rank.algorithms.string.IceCreamParlor;

import java.util.Scanner;

/**
 * The Class Solution.
 * 
 * Problem statement # https://www.hackerrank.com/challenges/icecream-parlor
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
		int trips = sc.nextInt();
		for (int k = 0; k < trips; k++) {
			int m = sc.nextInt();
			int n = sc.nextInt();
			int[] iceCreamCost = new int[n];

			for (int index = 0; index < n; index++) {
				iceCreamCost[index] = sc.nextInt();
			}

			for (int i = 0; i < n; i++) {
				for (int j = i; j < n; j++) {
					if (i == j)
						continue;
					else {
						if (iceCreamCost[i] + iceCreamCost[j] > m)
							continue;
						else if (iceCreamCost[i] + iceCreamCost[j] == m) {
							System.out.println((i + 1) + " " + (j + 1));
						}
					}
				}
			}
		}
		sc.close();
	}
}