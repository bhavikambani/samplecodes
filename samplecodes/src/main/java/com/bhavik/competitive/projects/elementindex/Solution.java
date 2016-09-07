package com.bhavik.competitive.projects.elementindex;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/tutorial-intro
 * 
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
		int V = in.nextInt();
		int arraySize = in.nextInt();
		for (int i = 0; i < arraySize; i++) {
			if (in.nextInt() == V) {
				System.out.println(i);
				break;
			}
		}
	}
}