package com.bhavik.competitive.hacker_rank.algorithms.implementations.BiggerIsGreater;

import java.util.Arrays;
import java.util.Scanner;

/**
 * The Class Solution of problem having lexographical maximum string.
 * 
 * Ref# https://www.hackerrank.com/challenges/bigger-is-greater
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
		int testCases = in.nextInt();
		for (int i = 0; i < testCases; i++) {
			String mainString = in.next();
			System.out.println(lexographicalGreaterString(mainString));
		}
		in.close();
	}

	/**
	 * Lexographical greater string.
	 *
	 * @param a
	 *            the a
	 * @return the string
	 */
	public static String lexographicalGreaterString(String a) {
		String out = new String();
		char[] firstCharArray = a.toCharArray();
		int firstCharArrayLen = firstCharArray.length;
		int swap2 = 0;
		for (int i = firstCharArrayLen - 2; i >= 0; i--) {
			int index = i;
			char aVal = firstCharArray[index];
			for (int j = index + 1; j <= firstCharArrayLen - 1; j++) {
				if (aVal < firstCharArray[j]) {
					int swap1 = i;
					int ctr = i;
					int tmp = i;
					char base = 'a';
					int cnt = 1;
					for (int m = tmp + 1; m < firstCharArrayLen; m++) {
						if (firstCharArray[m] > firstCharArray[swap1] && firstCharArray[m] < base || cnt == 1) {
							base = firstCharArray[m];
							swap2 = m;
							cnt++;
						}
					}
					char temp = firstCharArray[swap1];
					firstCharArray[swap1] = firstCharArray[swap2];
					firstCharArray[swap2] = temp;

					for (char k : firstCharArray) {
						out = out + k;
					}
					out = SubstringSort(out, ctr);
					return out;
				}
			}
		}
		return "no answer";
	}

	/**
	 * Substring sort.
	 *
	 * @param string
	 *            the a
	 * @param ctr
	 *            the ctr
	 * @return the string
	 */
	public static String SubstringSort(String string, int ctr) {
		String subString1 = string.substring(ctr + 1, string.length());
		String subString2 = string.substring(0, ctr + 1);
		char[] sub1Arr = subString1.toCharArray();
		String out = "";
		Arrays.sort(sub1Arr);
		for (char k : sub1Arr) {
			out = out + k;
		}
		out = subString2 + out;
		return out;
	}
}