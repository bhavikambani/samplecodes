package com.bhavik.competitive.hacker_rank.algorithms.implementations.ExtraLongFactorials;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * The Class Solution implementation of.
 * 
 * <url>https://www.hackerrank.com/challenges/extra-long-factorials</url>
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
		String n = in.next();
		in.close();
		System.out.println(bigIntFactorial(new BigInteger(n)));
	}

	public static BigInteger bigIntFactorial(BigInteger bigInt) {
		if (bigInt.intValue() > 1) {
			return bigInt.multiply(bigIntFactorial(bigInt.subtract(new BigInteger("1"))));
		} else {
			return new BigInteger("1");
		}
	}
}