package com.bhavik.competitive.hacker_rank.algorithms.bitmanipulation.FlippingBits;

import java.io.IOException;
import java.util.Scanner;

/**
 * The Class Solution of Flipping Bits algorithm problem statement can be found
 * below URL.
 * 
 * Ref# https://www.hackerrank.com/challenges/flipping-bits
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class Solution {

	/**
	 * Flip the bits.
	 *
	 * @param n
	 *            the n
	 * @return the long
	 */
	private static long flipTheBits(long n) {
		long mask = 0xffffffffL;
		long result = n ^ mask;
		return result;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws NumberFormatException
	 *             the number format exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		int testCases;
		long[] flippingBitsResult;
		Scanner in = new Scanner(System.in);
		testCases = in.nextInt();
		flippingBitsResult = new long[testCases];
		for (int i = 0; i < testCases; i++) {
			long n = in.nextLong();
			flippingBitsResult[i] = flipTheBits(n);
		}
		in.close();
		for (int i = 0; i < testCases; i++) {
			System.out.println(flippingBitsResult[i]);
		}
	}
}