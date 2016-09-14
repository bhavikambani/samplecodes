package com.bhavik.competitive.hacker_rank.thirtydays.day3;

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
		int n = scan.nextInt();
		scan.close();
		String ans = getNumberMessage(n);
		System.out.println(ans);
	}

	/**
	 * Gets the number message.
	 *
	 * @param numberVal
	 *            the number val
	 * @return the number message
	 */
	public static String getNumberMessage(int numberVal) {
		String message = null;

		if (numberVal % 2 == 0 && (numberVal >= 2 && numberVal <= 5)) {
			message = "Not Weird";
		} else if (numberVal % 2 == 1) {
			message = "Weird";
		} else if (numberVal % 2 == 0 && (numberVal >= 6 && numberVal <= 20)) {
			message = "Weird";
		} else {
			message = "Not Weird";
		}

		return message;
	}

}
