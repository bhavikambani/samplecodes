package com.elitecore.src.thirtydays.day2;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		scan.useDelimiter("\n");

		float iVal;
		float dVal;
		float sVal;

		iVal = scan.nextFloat();
		dVal = scan.nextFloat();
		sVal = scan.nextFloat();

		int totalCost = Math.round(iVal + (iVal * dVal / 100)
				+ (iVal * sVal / 100));

		System.out.println("The total meal cost is " + totalCost + " dollars.");

		scan.close();
	}
}