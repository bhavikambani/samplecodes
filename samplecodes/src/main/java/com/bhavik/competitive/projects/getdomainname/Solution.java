package com.bhavik.competitive.projects.getdomainname;

import java.util.Scanner;

public class Solution {

	public static void main(String args[]) {

		Scanner scanner = new Scanner(System.in).useDelimiter("\\n");

		int totalLines = scanner.nextInt();

		String inputLine ;
		for (int i = 0; i < totalLines && scanner.hasNext(); i++) {
			//inputLine = scanner.nextLine();
			inputLine = scanner.next();
			inputLine += scanner.nextLine();

			//System.out.println(inputLine);
			//System.out.println(inputLine.indexOf("http"));
			String domainString = inputLine.substring(inputLine.indexOf("http://www"), inputLine.length());
			String domainName1 = domainString.substring(8);
			String domainName = domainString.substring(8).substring(0,domainName1.indexOf('/'));
			System.out.println(domainName);

		}

	}
}
