package com.bhavik.competitive.hacker_rank.algorithms.sorting.CorrectnessandtheLoopInvariant;

import java.util.Scanner;

/**
 * The Class Solution.
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class Solution {

	/**
	 * Insertion sort.
	 *
	 * @param A
	 *            the a
	 */
	public static void insertionSort(int[] A) {
		for (int i = 0; i < A.length; i++) {
			int value = A[i];
			int j = i - 1;
			while (j >= 0 && A[j] > value) {
				A[j + 1] = A[j];
				j--;
			}
			A[j + 1] = value;
		}
		printArray(A);
	}

	/**
	 * Prints the array.
	 *
	 * @param ar
	 *            the ar
	 */
	static void printArray(int[] ar) {
		for (int n : ar) {
			System.out.print(n + " ");
		}
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[] ar = new int[n];
		for (int i = 0; i < n; i++) {
			ar[i] = in.nextInt();
		}
		insertionSort(ar);
	}
}