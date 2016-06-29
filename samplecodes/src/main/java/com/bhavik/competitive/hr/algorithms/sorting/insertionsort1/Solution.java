package com.bhavik.competitive.hr.algorithms.sorting.insertionsort1;

import java.util.Scanner;

/**
 * The Class Solution.
 * 
 * 5 2 4 6 8 3
 * 
 * @see https://www.hackerrank.com/challenges/insertionsort1
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
		int totalNumbers = in.nextInt();
		int[] numbers = new int[totalNumbers];

		for (int i = 0; i < totalNumbers; i++) {
			numbers[i] = in.nextInt();
		}
		System.out.println("Min number # " + getUnorderedNumber(numbers));
	}

	public static int getUnorderedNumber(int[] values) {
		int unorderedNumber = values[0];
		for (int i = 0; i < values.length - 1; i++) {
			if (values[i] > values[i + 1]) {
				unorderedNumber = values[i + 1];
				values[i + 1] = values[i];
				break;
			}
		}
		return unorderedNumber;
	}
}
