package com.bhavik.competitive.hr.algorithms.string.BeautifulBinaryString;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		char[] binaryString = in.next().toCharArray();
		int swapCounter = 0;
		for (int i = 0; i < n - 2; i++) {
			if (binaryString[i] == '0' && binaryString[i + 2] == '0') {
				binaryString[i + 2] = '1';
				swapCounter++;
			}
		}
		System.out.println(swapCounter);
	}
}