package com.bhavik.competitive.hacker_rank.tutorials.crackingTheCodingInterview.TimeComplexityPrimality;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

/**
 * The Class Solution implementation to find whether number provided is prime or
 * not.
 * 
 * Ref# https://www.hackerrank.com/challenges/ctci-big-o
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
		int p = in.nextInt();
		BigInteger bi = null;
		for (int a0 = 0; a0 < p; a0++) {
			int n = in.nextInt();
			bi = new BigInteger(String.valueOf(n));
			System.out.println(bi.isProbablePrime(1) ? "Prime" : "Not Prime");
		}
		in.close();
	}

}

/** Class MillerRabin **/
class MillerRabin {
	/** Function to check if prime or not **/
	public boolean isPrime(long n, int iteration) {
		/** base case **/
		if (n == 0 || n == 1)
			return false;
		/** base case - 2 is prime **/
		if (n == 2)
			return true;
		/** an even number other than 2 is composite **/
		if (n % 2 == 0)
			return false;

		long s = n - 1;
		while (s % 2 == 0)
			s /= 2;

		Random rand = new Random();
		for (int i = 0; i < iteration; i++) {
			long r = Math.abs(rand.nextLong());
			long a = r % (n - 1) + 1, temp = s;
			long mod = modPow(a, temp, n);
			while (temp != n - 1 && mod != 1 && mod != n - 1) {
				mod = mulMod(mod, mod, n);
				temp *= 2;
			}
			if (mod != n - 1 && temp % 2 == 0)
				return false;
		}
		return true;
	}

	/** Function to calculate (a ^ b) % c **/
	public long modPow(long a, long b, long c) {
		long res = 1;
		for (int i = 0; i < b; i++) {
			res *= a;
			res %= c;
		}
		return res % c;
	}

	/** Function to calculate (a * b) % c **/
	public long mulMod(long a, long b, long mod) {
		return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).mod(BigInteger.valueOf(mod)).longValue();
	}
	/** Main function **/

}