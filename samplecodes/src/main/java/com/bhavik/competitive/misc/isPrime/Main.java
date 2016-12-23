package com.bhavik.competitive.misc.isPrime;

import java.util.Stack;

public class Main {

	public static int isPrime(int n) {
		int minPrime = n;
		int temp = n / 2;
		while (temp > 1) {
			if (n % temp == 0 && temp < minPrime) {
				minPrime = temp;
			}
			temp--;
		}
		return minPrime != n ? minPrime : 1;
	}

	public static void main(String args[]) {
		System.out.println(Main.isPrime(10));
		String s = new String();
		char[] charArray = s.toCharArray();
		for (int i = 0 ; i < charArray.length ; i++){
			switch (charArray[i]) {
			case value:
				
				break;

			default:
				break;
			}
		}
		
		Stack<Character> charStack = new Stack<>();
		char pop = charStack.pop();
		
	}
}
