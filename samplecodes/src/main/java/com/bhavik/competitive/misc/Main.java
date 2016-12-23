package com.bhavik.competitive.misc;

import java.util.Scanner;

public class Main {

	public int factorial(int n) {
		if (n > 0) {
			return n * factorial(n - 1);
		} else {
			return 1;
		}
	}

	public static void main(String[] params) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		Main m = new Main();
		System.out.println(m.factorial(n));
	}
}
