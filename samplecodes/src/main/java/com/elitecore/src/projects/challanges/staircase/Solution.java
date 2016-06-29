package com.elitecore.src.projects.challanges.staircase;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int temp = 1;
		for (int i = n; i > 0; i--) {

			for (int j = i-1; j > 0; j--) {
				System.out.print(" ");
			}

			for (int k = 1; k <= temp; k++) {
				System.out.print("#");
			}
			System.out.println();
			temp++;
		}

	}
}
