package com.bhavik.competitive.hacker_rank.algorithms.implementations.GradingStudents;

import java.util.Scanner;

/**
 * The Class Solution of Grading Students problem of hackerrank.
 * 
 * Ref# https://www.hackerrank.com/challenges/grading
 * 
 * @author Bhavik Ambani
 * @created Mar 25, 2017 9:37:28 AM
 */

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		for (int a0 = 0; a0 < n; a0++) {
			int grade = in.nextInt();
			if (grade < 38) {
				System.out.println(grade);
			} else {
				int mode = grade % 5;
				if ((5 - mode) < 3) {
					System.out.println(grade + 5 - mode);
				} else {
					System.out.println(grade);
				}
			}
		}
		in.close();
	}
}
