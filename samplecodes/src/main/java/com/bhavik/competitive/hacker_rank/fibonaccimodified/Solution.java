package com.bhavik.competitive.hacker_rank.fibonaccimodified;

import java.math.BigInteger;
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
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in).useDelimiter("\n");
		String inputString = scanner.nextLine();
		scanner.close();
		String[] numbersStr = inputString.trim().split(" ");

		BigInteger tn = new BigInteger(numbersStr[0]);
		// System.out.println(tn);
		BigInteger tnp1 = new BigInteger(numbersStr[1]);
		// System.out.println(tnp1);
		BigInteger tnp2 = BigInteger.ZERO;
		// System.out.println(tnp2);

		int sequence = Integer.valueOf(numbersStr[2]);
		for (int i = 2; i < sequence; i++) {
			// System.out.println(i);
			tnp2 = tnp1.multiply(tnp1).add(tn);
			tn = tnp1;
			tnp1 = tnp2;
		}
		System.out.println(tnp2);

	}

}
