package com.bhavik.competitive.hacker_rank.tutorials.crackingTheCodingInterview.arrays.leftRotation;

import java.util.Scanner;

/**
 * The Class Solution of left rotation of array.
 * 
 * Ref# https://www.hackerrank.com/challenges/ctci-array-left-rotation
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class Solution {

	/**
	 * Array left rotation.
	 *
	 * @param a
	 *            the a
	 * @param n
	 *            the n
	 * @param k
	 *            the k
	 * @return the int[]
	 */
	public static int[] arrayLeftRotation(int[] a, int n, int k) {
		int[] newArray = new int[n];

		for (int i = 0; i < n; i++) {
			int newIndex = i - k;
			if (newIndex < 0)
				newIndex = n + newIndex;
			newArray[newIndex] = a[i];
		}
		return newArray;
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
		int k = in.nextInt();
		int a[] = new int[n];
		for (int a_i = 0; a_i < n; a_i++) {
			a[a_i] = in.nextInt();
		}
		in.close();
		

		int[] output = new int[n];
		output = arrayLeftRotation(a, n, k);
		for (int i = 0; i < n; i++)
			System.out.print(output[i] + " ");

		System.out.println();

	}
}
