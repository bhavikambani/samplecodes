package com.elitecore.src.projects.samplecodes.sparsearray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		int totalStrings = scanner.nextInt();

		List<String> inputString = new ArrayList<String>();

		for (int i = 0; i < totalStrings; i++) {
			inputString.add(scanner.next());
		}

		int queryCoount = scanner.nextInt();
		int[] outputArray = new int[queryCoount];
		for (int i = 0; i < queryCoount; i++) {
			outputArray[i] = Collections.frequency(inputString, scanner.next());
		}
		for (int i = 0; i < queryCoount; i++) {
			System.out.println(outputArray[i]);
		}
	}
}