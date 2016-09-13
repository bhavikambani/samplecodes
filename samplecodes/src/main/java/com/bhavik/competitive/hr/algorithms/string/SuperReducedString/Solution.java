package com.bhavik.competitive.hr.algorithms.string.SuperReducedString;

import java.util.Scanner;
import java.util.Stack;

/**
 * The Class Solution.
 * 
 * Problem Statement # https://www.hackerrank.com/challenges/reduced-string
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
		char[] mainString = in.nextLine().trim().toCharArray();
		in.close();
		Stack<Character> charStack = new Stack<Character>();

		for (int i = 0; i < mainString.length; i++) {
			if (charStack.isEmpty()) {
				charStack.push(mainString[i]);
			} else {
				Character peepChar = charStack.peek();

				if (peepChar != null && peepChar.equals(mainString[i])) {
					charStack.pop();
				} else {
					charStack.push(mainString[i]);
				}
			}
		}

		int stackSize = charStack.size();

		if (stackSize == 0) {
			System.out.println("Empty String");
		} else {
			mainString = new char[stackSize];
			for (int i = stackSize - 1; i >= 0; i--) {
				mainString[i] = charStack.pop();
			}
			System.out.println(new String(mainString));
		}

	}
}