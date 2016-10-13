package com.bhavik.competitive.hacker_rank.StairCase;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		StairCase(n);

	}

	static void StairCase(int n) {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if ((i + j) > n) {
					System.out.print("#");
				} else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
}