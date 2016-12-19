package com.sapient.usecases.uc14;

/**
 * Problem # FInd the lrgest and second largest element from the unsorted array
 * of numbers in the list
 * 
 * @author Bhavik Ambani
 *
 */
public class Solution {

	public int[] findLargestAndSecondLargest(int[] values) {
		int[] result = new int[2];
		result[0] = values[0];
		result[1] = values[0];

		for (int i = 0; i < values.length; i++) {
			if (values[i] > result[1]) {
				if (values[i] >= result[0]) {
					result[1] = result[0];
					result[0] = values[i];
				} else {
					result[1] = values[i];
				}
			}
		}
		return result;
	}

}
