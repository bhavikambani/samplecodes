package com.bhavik.competitive.misc.reductionCost;

import java.util.Scanner;

public class Main {

	public int reductionCost(int[] values) {
		int reductionCost = 0;
		int temp = 1;
		for (int i = values.length - 1; i > 0; i--) {
			reductionCost += values[i] * temp++;
		}
		reductionCost += values[0] * --temp;
		return reductionCost;
	}

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int totalNumbers = in.nextInt();
		int[] values = new int[totalNumbers];
		for (int i = 0; i < totalNumbers; i++) {
			values[i] = in.nextInt();
		}
		System.out.println(new Main().reductionCost(values));
	}
}
