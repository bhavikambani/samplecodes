package com.elitecore.src.projects.samplecodes.getcomments;

import java.util.Scanner;

public class Solution {

	public static void main(String args[]) {

		Scanner scanner = new Scanner(System.in);

		String inputString;
		StringBuffer buffer = new StringBuffer();
		int lineCount = 1;
		while (scanner.hasNext() && lineCount <= 200) {
			inputString = scanner.next();

			if (inputString != null) {
				if (inputString.contains("//")) { // Get start index
					buffer.append(inputString.substring(
							inputString.indexOf("//"), inputString.length())
							+ "");
				} else if (inputString.contains("/*")) {
					// buffer.append(inputString);
					buffer.append(inputString.substring(
							inputString.indexOf("/*"), inputString.length()));
					while (!scanner.next().contains("*/")) {
						buffer.append(inputString);
					}
				}
			}
		}
		System.out.println(buffer);
	}
}
