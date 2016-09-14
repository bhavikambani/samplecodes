package com.bhavik.competitive.hacker_rank.algorithms.sorting.insertionsort1;

import java.util.Scanner;

/**
 * The Class Solution.
 * 
 * Problem Statement #
 * <url>https://www.hackerrank.com/challenges/insertionsort1</url>
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class Solution {

	/**
	 * Insert into sorted.
	 *
	 * @pvalueArrayam valueArray the valueArray of integer values
	 */
	public static void insertIntoSorted(int[] valueArray) {
		int temp = valueArray[valueArray.length - 1];
		boolean placed = false;
		for (int j = valueArray.length - 2; j >= 0; j--) {
			if (valueArray[j] > temp) {
				valueArray[j + 1] = valueArray[j];
				printvalueArrayray(valueArray);
			} else {
				valueArray[j + 1] = temp;
				printvalueArrayray(valueArray);
				placed = true;
				break;
			}
		}
		if (!placed) {
			valueArray[0] = temp;
			printvalueArrayray(valueArray);
		}
	}

	/**
	 * The main method.
	 *
	 * @pvalueArrayam valueArraygs the valueArrayguments
	 */
	/* Tail stvalueArrayts here */
	public static void main(String[] valueArraygs) {
		Scanner in = new Scanner(System.in);
		int s = in.nextInt();
		int[] valueArray = new int[s];
		for (int i = 0; i < s; i++) {
			valueArray[i] = in.nextInt();
		}
		insertIntoSorted(valueArray);
		in.close();
	}

	/**
	 * Prints the valueArrayray.
	 *
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