package com.bhavik.competitive.hacker_rank.SumThemAll;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {

		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] numbers = new int[n];
		for (int i = 0; i < n; i++) {
			numbers[i] = scn.nextInt();
		}
		System.out.println(sum(numbers));
	}

	static int sum(int[] numbers) {
		int sum = 0;
		for (int i = 0; i < numbers.length; i++) {
			sum += numbers[i];
		}
		return sum;
	}
	
	
	static int score(String s){
		int score=0;
		char[] charArray = s.toCharArray();
		for (int i = 0 ; i < charArray.length;i++){
			switch (charArray[i]) {
			case 'a':
			case 'e':
			case 'i':
			case 'o':
			case 'u':
				score+=1;
				break;
			default:
				score+=2;
				break;
			}
		}
		return score;
	}
}
