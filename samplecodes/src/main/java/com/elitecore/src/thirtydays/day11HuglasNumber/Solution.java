package com.elitecore.src.thirtydays.day11HuglasNumber;

import java.util.Scanner;
import java.util.TreeSet;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = 6;

		int arr[][] = new int[n][n];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				arr[i][j] = in.nextInt();
			}
		}
		in.close();
		TreeSet<Integer> maxElementSet = new TreeSet<Integer>();

		for (int x = 0; x < n - 2; x++) {
			for (int y = 0; y < n - 2; y++) {
				maxElementSet.add(arr[x][y] + arr[x][y + 1] + arr[x][y + 2]
						+ arr[x + 1][y + 1] + arr[x + 2][y] + arr[x + 2][y + 1]
						+ arr[x + 2][y + 2]);
			}
		}

		System.out.println(maxElementSet.last());
	}
}