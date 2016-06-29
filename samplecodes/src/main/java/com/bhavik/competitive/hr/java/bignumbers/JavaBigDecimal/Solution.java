package com.bhavik.competitive.hr.java.bignumbers.JavaBigDecimal;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-bigdecimal
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
		// Set<BigDecimal> bigDecimleSet = new TreeSet<BigDecimal>();
		int testCases = in.nextInt();
		BigDecimal[] bigDecimleValues = new BigDecimal[testCases];
		for (int i = 0; i < testCases; i++) {
			BigDecimal val = in.nextBigDecimal();
			// bigDecimleSet.add(val);
			bigDecimleValues[i] = val;
		}
		Arrays.sort(bigDecimleValues);
		for (int i = testCases - 1; i >= 0; i--) {
			System.out.println(bigDecimleValues[i].toPlainString());
		}

	}
}
