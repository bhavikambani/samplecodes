package com.bhavik.competitive.projects.samplecodes.stack.matchingparenthesis;

import java.util.Scanner;
import java.util.Stack;

public class Solution {

	/*
	public static void main(String args[]) throws FileNotFoundException {
		// Scanner scanner = new Scanner(new
		// File("/home/bhavik/Documents/input"));
		Scanner scanner = new Scanner(System.in);
		int testCases = scanner.nextInt();
		for (int i = 0; i < testCases && scanner.hasNext(); i++) {
			String string = scanner.next().trim();
			char[] paramthesString = string.toCharArray();
			if (palindromParamthaces(paramthesString)) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}
	}
	*/
	
	public static void main(String args[]){
		System.out.println(factorial(new Scanner(System.in).nextInt()));
	}

	public static int factorial(int a) {
		if (a <= 0) {
			return 1;
		} else {
			return a * factorial(a-1);
		}
	}

	public static boolean palindromParamthaces(char[] values) {
		Stack<Character> bracesStack = new Stack<Character>();
		for (int i = 0; i < values.length; i++) {
			switch (values[i]) {
			case '(':
			case '{':
			case '[':
				bracesStack.push(values[i]);
				break;
			case ')':
				if (bracesStack.isEmpty() || bracesStack.pop() != '(')
					return false;
				break;
			case '}':
				if (bracesStack.isEmpty() || bracesStack.pop() != '{')
					return false;
				break;
			case ']':
				if (bracesStack.isEmpty() || bracesStack.pop() != '[')
					return false;
				break;
			}
		}
		return true;
	}
}
