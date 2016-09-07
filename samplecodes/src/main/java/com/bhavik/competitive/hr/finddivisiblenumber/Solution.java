package com.bhavik.competitive.hr.finddivisiblenumber;

import java.util.Scanner;

public class Solution {

	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		int totalCases = scanner.nextInt();
		// System.out.println(totalCases);
		for (int i = 0; i < totalCases && scanner.hasNext(); i++) {
			System.out.println(getDevisibleNumbers(scanner.nextLong()));
		}
	}

	public static int getDevisibleNumbers(long numStr) {
		int devisibleNumbers = 0;
		char[] numArray = String.valueOf(numStr).toCharArray();
		long numVal = Long.valueOf(numStr);
		for (int i = 0; i < numArray.length; i++) {
			// System.out.println(new Integer(numArray[i]) - 48);
			if (numArray[i] != '0' && !Double.isNaN(numArray[i])) {
				if ((numVal % (new Integer(numArray[i]) - 48)) == 0) {
					devisibleNumbers++;
				}
			}
		}
		return devisibleNumbers;
	}
}
