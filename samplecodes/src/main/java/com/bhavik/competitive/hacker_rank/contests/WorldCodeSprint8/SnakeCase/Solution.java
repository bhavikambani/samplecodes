package com.bhavik.competitive.hacker_rank.contests.WorldCodeSprint8.SnakeCase;

import java.util.Scanner;

/**
 * The Class Solution for the implementation of Snake Case problem.
 * 
 * Ref#
 * https://www.hackerrank.com/contests/world-codesprint-8/challenges/snake-case
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
		String s = in.next();
		in.close();

		String[] words = s.split("_");
		System.out.println(words.length);
	}
}