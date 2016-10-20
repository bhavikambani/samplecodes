package com.bhavik.competitive.hacker_rank.algorithms.search.Pairs;

import java.util.Scanner;
import java.util.TreeSet;

/**
 * The Class Solution of implementation of Algorithm Pairs of Hacker Rank.
 * 
 * Ref# https://www.hackerrank.com/challenges/pairs
 * 
 * 
 * Algorithm : Put all the elements in the {@link TreeSet} and iterate all the
 * elements, if we find any element matching to iterated element + k, then we
 * will increment the counter by one, if the value of current interation integer
 * + k is greater then the last element value, then we will break the loop.
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
		int k = in.nextInt();
		TreeSet<Integer> valueSet = new TreeSet<Integer>();
		for (int i = 0; i < n; i++) {
			valueSet.add(in.nextInt());
		}
		in.close();
		noOfPairs(valueSet, k);
	}

	/**
	 * No of pairs.
	 *
	 * @param valueSet
	 *            the value set
	 * @param k
	 *            the k
	 */
	private static void noOfPairs(TreeSet<Integer> valueSet, int k) {
		int noOfPairs = 0;
		Integer lastVal = valueSet.last();
		for (Integer i : valueSet) {
			if (i + k > lastVal) {
				break;
			}
			// System.out.println(i);
			noOfPairs += valueSet.contains(i + k) ? 1 : 0;
		}

		System.out.println(noOfPairs);

	}
}