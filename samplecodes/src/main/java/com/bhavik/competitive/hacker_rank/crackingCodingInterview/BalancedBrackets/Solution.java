package com.bhavik.competitive.hacker_rank.crackingCodingInterview.BalancedBrackets;

import java.util.Scanner;
import java.util.Stack;

/**
 * The Class Solution.
 * 
 * @author Bhavikkumar Ambani
 * @createdOn June 30, 2017
 */
public class Solution {

	/**
	 * Checks if is balanced.
	 *
	 * @param expression
	 *            the expression
	 * @return true, if is balanced
	 */
	public static boolean isBalanced(String expression) {
		if (expression == null || expression.trim().length() == 0 || "".equals(expression))
			return false;
		boolean balanced = true;
		char[] expressionChars = expression.toCharArray();
		Stack<Character> bracesStack = new Stack<>();

		for (int i = 0; i < expressionChars.length; i++) {
			switch (expressionChars[i]) {
			case '{':
			case '(':
			case '[':
				bracesStack.push(expressionChars[i]);
				break;
			case ']':
			case '}':
			case ')':
				if (bracesStack.isEmpty()) {
					balanced = false;
					break;
				} else {
					Character pop = bracesStack.pop();
					switch (expressionChars[i]) {
					case '}':
						if (pop != '{')
							balanced = false;
						break;
					case ')':
						if (pop != '(')
							balanced = false;
						break;
					case ']':
						if (pop != '[')
							balanced = false;
						break;
					default:
						break;
					}
				}
				break;
			default:
				break;
			}
			if (!balanced)
				break;
		}
		if (!bracesStack.isEmpty())
			balanced = false;
		return balanced;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();
		for (int a0 = 0; a0 < t; a0++) {
			String expression = in.next();
			System.out.println((isBalanced(expression)) ? "YES" : "NO");
		}
		in.close();
	}
}