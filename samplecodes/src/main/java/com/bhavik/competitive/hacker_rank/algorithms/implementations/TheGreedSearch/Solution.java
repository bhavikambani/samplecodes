package com.bhavik.competitive.hacker_rank.algorithms.implementations.TheGreedSearch;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();
		for (int a0 = 0; a0 < t; a0++) {
			int R = in.nextInt();
			int C = in.nextInt();
			String G[] = new String[R];
			for (int G_i = 0; G_i < R; G_i++) {
				G[G_i] = in.next();
			}
			System.out.println("G # " + Arrays.toString(G));
			int r = in.nextInt();
			int c = in.nextInt();
			String P[] = new String[r];
			for (int P_i = 0; P_i < r; P_i++) {
				P[P_i] = in.next();
			}
			in.close();
			System.out.println("P # " + Arrays.toString(P));
		}
	}

	public static boolean isGridMatched(String[] baseString, String[] searchString) {
		boolean isGridMatched = false;

		for (int i = 0; i < baseString.length; i++) {
			@SuppressWarnings("unused")
			int searchIndex = baseString[i].indexOf(searchString[0]);
		}

		return isGridMatched;
	}
}
