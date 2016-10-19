package com.bhavik.competitive.hacker_rank.algorithms.dynamicprogramming.TheMaximumSubarray;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * The Class Solution class implementation of The Maximum sub Array problem of
 * hacker rank.
 * 
 * Ref# https://www.hackerrank.com/challenges/maxsubarray
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class Solution {

	/**
	 * Max.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the int
	 */
	static int max(int x, int y) {
		return (y > x) ? y : x;
	}

	/**
	 * Max subarray contiguous.
	 *
	 * @param arr
	 *            the arr
	 * @param indexMax
	 *            the index max
	 * @return the int
	 */
	static int maxContiguousSubarray(int[] arr, int indexMax) {
		int maxSoFar = arr[0];
		int currMax = arr[0];
		for (int i = 1; i < indexMax; i++) {
			currMax = max(arr[i], currMax + arr[i]);
			maxSoFar = max(maxSoFar, currMax);
		}
		return maxSoFar;
	}

	/**
	 * Max subarray non contiguous.
	 *
	 * @param arr
	 *            the arr
	 * @param indexMax
	 *            the index max
	 * @return the int
	 */
	static int maxSubarrayNonContiguous(int[] arr, int indexMax) {
		int sumMax = 0;
		int max = arr[0];
		boolean negArray = true;
		int res = 0;
		for (int i = 0; i < indexMax; i++) {
			if (arr[i] >= 0) {
				sumMax += arr[i];
				negArray = false;
			}
			if (arr[i] >= max) {
				max = arr[i];
			}
		}
		if (negArray == false)
			res = sumMax;
		if (negArray)
			res = max;

		return res;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int[] testSolutionsC = new int[10];
		int[] testSolutionsNC = new int[10];
		int resultC;
		int resultNC;
		int occurences;
		occurences = Integer.parseInt(in.nextLine().trim());
		for (int i = 0; i < occurences; i++) {
			String inputString;
			int[] array = new int[100000];
			int maxIndex = Integer.parseInt(in.nextLine().trim());
			int arrayIndex = 0;
			inputString = in.nextLine().trim();
			StringTokenizer st = new StringTokenizer(inputString.trim());
			while (st.hasMoreTokens()) {
				array[arrayIndex] = Integer.parseInt(st.nextToken().trim());
				arrayIndex++;
			}
			resultC = maxContiguousSubarray(array, maxIndex);
			resultNC = maxSubarrayNonContiguous(array, maxIndex);
			testSolutionsC[i] = resultC;
			testSolutionsNC[i] = resultNC;
		}
		in.close();
		for (int i = 0; i < occurences; i++) {
			System.out.print(testSolutionsC[i] + " ");
			System.out.print(testSolutionsNC[i]);
			System.out.println();
		}
	}
}