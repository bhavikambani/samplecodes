package com.bhavik.competitive.hacker_rank.algorithms.implementations.SockMerchant;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * The Class Solution of Sock Merchant problem of Algorithms section.
 * 
 * Ref# https://www.hackerrank.com/challenges/sock-merchant
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
		int n = in.nextInt();
		Set<Integer> sockPairs = new HashSet<Integer>();
		int sockMerchantCount = 0;
		for (int c_i = 0; c_i < n; c_i++) {
			int temp = in.nextInt();
			if (sockPairs.contains(temp)) {
				sockPairs.remove(temp);
				sockMerchantCount++;
			} else {
				sockPairs.add(temp);
			}
		}
		in.close();
		System.out.println(sockMerchantCount);

	}
}