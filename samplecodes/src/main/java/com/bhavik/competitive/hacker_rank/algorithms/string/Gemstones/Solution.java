package com.bhavik.competitive.hacker_rank.algorithms.string.Gemstones;

import java.util.Scanner;
import java.util.Set;
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
		int cases = in.nextInt();
		String[] stringArrays = new String[cases];
		String minLengthString = null;

		for (int i = 0; i < cases; i++) {
			stringArrays[i] = in.next();
			if (minLengthString == null || stringArrays[i].length() < minLengthString.length()) {
				minLengthString = stringArrays[i];
			}
		}
		in.close();
		char[] minStringCharArray = minLengthString.toCharArray();
		Set<Character> uniqueCharSet = new TreeSet<Character>();
		for (int i = 0; i < minLengthString.length(); i++) {
			uniqueCharSet.add(minStringCharArray[i]);
		}
		int totalCount = 0;
		for (Character c : uniqueCharSet) {
			boolean charContains = true;
			for (int j = 0; j < cases; j++) {
				if (stringArrays[j].indexOf(c) == -1) {
					charContains = false;
					break;
				}
			}
			if (charContains) {
				totalCount++;
			}
		}
		System.out.println(totalCount);
	}
}