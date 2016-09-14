package com.bhavik.competitive.hacker_rank.thirtydays.day2;

import java.util.Scanner;

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
		Scanner scan = new Scanner(System.in);
		scan.useDelimiter("\n");

		float iVal;
		float dVal;
		float sVal;

		iVal = scan.nextFloat();
		dVal = scan.nextFloat();
		sVal = scan.nextFloat();
		scan.close();

		int totalCost = Math.round(iVal + (iVal * dVal / 100) + (iVal * sVal / 100));

		System.out.println("The total meal cost is " + totalCost + " dollars.");

	}
}