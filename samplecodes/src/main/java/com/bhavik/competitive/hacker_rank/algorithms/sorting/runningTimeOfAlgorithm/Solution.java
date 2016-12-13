package com.sapient.study.hr.algorithms.runningTimeOfAlgorithm;

import java.util.Scanner;

/**
 * The Class Solution which implements the solution of problem Running Time of
 * Algorithms.
 * 
 * Ref# https://www.hackerrank.com/challenges/runningtime
 * 
 * @author Bhavik Ambani
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
		int noOfElementsToBeSorted = in.nextInt();
		int[] values = new int[noOfElementsToBeSorted];
		int noOfShiftsToSortArray = 0;
		for (int i = 0; i < noOfElementsToBeSorted; i++) {
			values[i] = in.nextInt();
		}
		in.close();
		for (int i = 1; i < noOfElementsToBeSorted; i++) {
			int j = i - 1;
			while (j >= 0 && values[j + 1] < values[j]) {
				if (values[j + 1] < values[j]) {
					int tmp = values[j + 1];
					values[j + 1] = values[j];
					values[j] = tmp;
					noOfShiftsToSortArray++;
				}
				j--;
			}
		}
		System.out.println(noOfShiftsToSortArray);
	}
}