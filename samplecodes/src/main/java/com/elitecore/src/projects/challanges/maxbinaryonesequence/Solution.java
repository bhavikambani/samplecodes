package com.elitecore.src.projects.challanges.maxbinaryonesequence;

import java.util.Scanner;

public class Solution {
	public static char[] getBinaryVal(int number) {
		char[] binVals = new char[32];
		int radix = 1;
		for (int i = 31; i >= 0; i--) {
			binVals[i] = ((number & radix) == radix) ? '1' : '0';
			radix = radix << 1;
		}
		//System.out.println(new String(binVals));
		return binVals;
	}

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		char[] binVal = getBinaryVal(n);
		int maxOccurence = 0;
		int currMaxOccur = 0;
		for (int i = 0; i < binVal.length; i++) {
			switch (binVal[i]) {
			case '1':
				currMaxOccur++;
				break;
			case '0':
				if (currMaxOccur > maxOccurence)
					maxOccurence = currMaxOccur;
				currMaxOccur = 0;
				break;
			}
		}
		if (currMaxOccur > maxOccurence) {
			maxOccurence = currMaxOccur;
		}
		System.out.print(maxOccurence);
	}
}
