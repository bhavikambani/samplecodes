package com.bhavik.competitive.hr.panagramstring;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in).useDelimiter("\n");
		System.out.println(isPanagram(scanner.nextLine().trim().toUpperCase()));
	}

	public static String isPanagram(String inputString) {
		char[] inputStringChars = inputString.toCharArray();
		char[] alphabets = new char[26];
		for (int i = 0; i < inputStringChars.length; i++) {
			if ((inputStringChars[i] - 65) >= 0
					&& (inputStringChars[i] - 65) <= 97) {
				alphabets[inputStringChars[i] - 65] = '1';
			}
		}
		for (int i = 0; i < alphabets.length; i++) {
			if (alphabets[i] == '\u0000') {
				return "not pangram";
			}
		}
		return "pangram";
	}
}
