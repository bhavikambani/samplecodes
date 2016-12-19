package com.sapient.usecases.uc14;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;
import java.util.TreeSet;

public class SolutionTest {

	private Solution instance = new Solution();

	@Before
	public void startTestCases() {
		System.out.println("Test cases for Finding second largest element from the array started.");
	}

	@After
	public void runAfterTestMethod() {
		System.out.println("Test cases for Finding second largest element from the array completed.");
	}

	@Test
	public void test() {
		int[] randomValues = getRandomNumberArray(500);
		int[] result = getMaxAndSecondMax(randomValues);

		assertArrayEquals(result, instance.findLargestAndSecondLargest(randomValues));
	}

	private int[] getMaxAndSecondMax(int[] values) {
		int[] result = new int[2];
		TreeSet<Integer> numbers = new TreeSet<Integer>();
		for (int i = 0; i < values.length; i++) {
			numbers.add(values[i]);
		}
		result[0] = numbers.last();
		numbers.remove(numbers.last());
		result[1] = numbers.last();
		System.out.println(Arrays.toString(result));
		return result;
	}

	private int[] getRandomNumberArray(int maxRange) {
		int[] randomValues = new int[maxRange];
		Random r = new Random();
		for (int i = 0; i < maxRange; i++) {
			randomValues[i] = r.nextInt(maxRange);
		}
		System.out.println(Arrays.toString(randomValues));
		return randomValues;
	}
}
