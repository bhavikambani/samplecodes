package com.bhavik.competitive.hacker_rank.java.bignumbers.JavaPrimalityTest;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-primality-test
 * 
 * @author Bhavik Aniruddh Ambani
 * 
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
		BigInteger n = in.nextBigInteger();
		System.out.println(n.isProbablePrime(1) ? "prime" : "not prime");
		in.close();
	}

}
