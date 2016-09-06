package com.bhavik.competitive.hr.algorithms.string.CaesarCipher;

import java.util.Scanner;

/**
 * The Class Solution.
 * 
 * Problem Statement # https://www.hackerrank.com/challenges/caesar-cipher-1
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
		String s = in.next().trim();
		int k = in.nextInt();
		in.close();
		System.out.println(getCeaserCipher(s, k));
	}

	/**
	 * Gets the ceaser cipher.
	 *
	 * @param orgString
	 *            the org string
	 * @param rotateNumber
	 *            the rotate number
	 * @return the ceaser cipher
	 */
	public static String getCeaserCipher(String orgString, int rotateNumber) {
		StringBuilder sb = new StringBuilder(orgString.length());
		char[] orgStringArr = orgString.toCharArray();
		/*
		 * For A-Z 65-90 For a-z 97-122
		 */
		for (int i = 0; i < orgStringArr.length; i++) {
			int tmp = orgStringArr[i];
			if (tmp >= 65 && tmp <= 90) {
				tmp = tmp + rotateNumber;
				if (tmp > 90) {
					sb.append((char) (64 + (tmp - 90)));
				} else {
					sb.append((char) tmp);
				}
			} else if (tmp >= 97 && tmp <= 122) {
				tmp = tmp + rotateNumber;
				if (tmp > 122) {
					sb.append((char) (96 + (tmp - 122)));
				} else {
					sb.append((char) tmp);
				}
			} else {
				sb.append(orgStringArr[i]);
			}
		}
		return sb.toString().trim();
	}
}
