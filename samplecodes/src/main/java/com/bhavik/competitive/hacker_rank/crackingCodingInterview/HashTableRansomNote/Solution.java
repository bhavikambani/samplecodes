package com.bhavik.competitive.hacker_rank.crackingCodingInterview.HashTableRansomNote;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The Class Solution.
 * 
 * @author Bhavikkumar Ambani
 */
public class Solution {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		List<String> magazine = new ArrayList<>();
		String status = "Yes";
		try (Scanner in = new Scanner(System.in)) {
			int m = in.nextInt();
			int n = in.nextInt();
			for (int magazine_i = 0; magazine_i < m; magazine_i++) {
				magazine.add(in.next());
			}
			for (int ransom_i = 0; ransom_i < n; ransom_i++) {
				boolean remove = magazine.remove(in.next());
				if (!remove) {
					status = "No";
					break;
				}
			}
			System.out.println(status);
		}
	}

}
