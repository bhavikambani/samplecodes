package com.bhavik.competitive.hr.thirtydays.day3;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		scan.close();
		String ans = getNumberMessage(n);
		System.out.println(ans);
	}

	public static String getNumberMessage(int numberVal) {
		String message = null;

		if (numberVal % 2 == 0 && (numberVal >= 2 && numberVal <= 5)) {
			message = "Not Weird";
		} else if (numberVal % 2 == 1) {
			message = "Weird";
		} else if (numberVal % 2 == 0 && (numberVal >= 6 && numberVal <= 20)) {
			message = "Weird";
		} else {
			message = "Not Weird";
		}

		return message;
	}

}
