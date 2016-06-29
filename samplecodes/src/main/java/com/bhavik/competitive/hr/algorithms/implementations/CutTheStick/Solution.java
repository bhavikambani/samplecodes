package com.bhavik.competitive.hr.algorithms.implementations.CutTheStick;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * The Class Solution.
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
		List<Integer> arr = new ArrayList<Integer>();
		TreeSet<Integer> uniqueNumbers = new TreeSet<Integer>();
		for (int arr_i = 0; arr_i < n; arr_i++) {
			int val = in.nextInt();
			arr.add(val);
			uniqueNumbers.add(val);
		}
		in.close();
		System.out.println(n);
		uniqueNumbers.remove(uniqueNumbers.last());
		for (Integer a : uniqueNumbers) {
			n -= Collections.frequency(arr, a);
			System.out.println(n);
		}
	}
}
