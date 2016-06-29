package com.bhavik.competitive.hr.java.bignumbers.JavaBigInteger;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-biginteger
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
		BigInteger bi1 = in.nextBigInteger();
		BigInteger bi2 = in.nextBigInteger();
		System.out.println(bi1.add(bi2));
		System.out.println(bi1.multiply(bi2));
	}
}
