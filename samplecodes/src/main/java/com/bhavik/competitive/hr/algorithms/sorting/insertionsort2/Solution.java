package com.bhavik.competitive.hr.algorithms.sorting.insertionsort2;

import java.util.Scanner;

/**
 * The Class Solution.
 * 
 * Reference # <url>https://www.hackerrank.com/challenges/insertionsort2</url>
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
		int s = in.nextInt();
		int[] ar = new int[s];
		for (int i = 0; i < s; i++) {
			ar[i] = in.nextInt();
		}
		insertionSortPart2(ar);
		in.close();
	}

	/**
	 * Insertion sort part 2.
	 *
	 * @param num
	 *            the num
	 */
	public static void insertionSortPart2(int[] num) {
		int j;
		int key;
		int i;

		for (j = 1; j < num.length; j++) {
			key = num[j];
			for (i = j - 1; (i >= 0) && (num[i] > key); i--) {
				num[i + 1] = num[i];
			}
			num[i + 1] = key;
			printvalueArrayray(num);
		}

	}

	/**
	 * Prints the valueArrayray.
	 *
	 * @param valueArray
	 *            the value array
	 * @pvalueArrayam valueArray the valueArray
	 */
	private static void printvalueArrayray(int[] valueArray) {
		StringBuilder sb = new StringBuilder("");
		for (int n : valueArray) {
			sb.append(n + " ");
		}
		System.out.println(sb.toString().trim());
	}
}
