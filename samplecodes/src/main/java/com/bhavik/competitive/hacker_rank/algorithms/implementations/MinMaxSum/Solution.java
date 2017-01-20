package com.bhavik.competitive.hacker_rank.algorithms.implementations.MinMaxSum;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		long[] values = new long[5];
		values[0] = in.nextLong();
		values[1] = in.nextLong();
		values[2] = in.nextLong();
		values[3] = in.nextLong();
		values[4] = in.nextLong();
		in.close();

		values = insertionSort(values);
		System.out.println((values[0] + values[1] + values[2] + values[3]) + " "
				+ (values[1] + values[2] + values[3] + values[4]));
	}

	public static long[] insertionSort(long array[]) {
		int n = array.length;
		for (int j = 1; j < n; j++) {
			long key = array[j];
			int i = j - 1;
			while ((i > -1) && (array[i] > key)) {
				array[i + 1] = array[i];
				i--;
			}
			array[i + 1] = key;
		}
		return array;
	}
}