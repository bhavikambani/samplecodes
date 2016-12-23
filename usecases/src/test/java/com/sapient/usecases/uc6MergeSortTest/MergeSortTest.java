package com.sapient.usecases.uc6MergeSortTest;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sapient.usecases.uc6MergeSort.MergeSortImpl;

/**
 * The Class MergeSortTest will implements the test cases of the MergSort
 * Algorithm implementation.
 * 
 * @author Bhavik Ambani
 */
public class MergeSortTest {

	/** The merge sort engine. */
	private MergeSortImpl mergeSortEngine;

	/**
	 * Inits the test case required parameters.
	 */
	@Before
	public void init() {
		mergeSortEngine = new MergeSortImpl();
		System.out.println("Before test case execution,merge sort instance initialized");
	}

	/**
	 * Test numbers.
	 */
	@Test
	public void testNumbers() {
		Integer[] intValues = new Integer[] { 12, 5, 8, 65, 98, 659, 45, 21, 659, 3, 45, 657, 951, 12, 17, 133 };
		Integer[] copyOfIntValues = Arrays.copyOf(intValues, intValues.length);
		Arrays.sort(copyOfIntValues);
		mergeSortEngine.sort(intValues);
		Assert.assertArrayEquals(intValues, copyOfIntValues);

		Long[] longValues = new Long[] { Long.MAX_VALUE, 58434848646L, 8246464L, 65456445648L, Long.MIN_VALUE };
		Long[] copyOfLongValues = Arrays.copyOf(longValues, longValues.length);
		Arrays.sort(copyOfLongValues);
		mergeSortEngine.sort(longValues);
		Assert.assertArrayEquals(longValues, copyOfLongValues);
	}

	/**
	 * Test alphabets.
	 */
	@Test
	public void testAlphabets() {
		Character[] charValues = new Character[] { 'b', 'r', 'a', 'y', 'o', 'z', 'e', 'r', 'w', 'p' };
		Character[] copyOfCharValues = Arrays.copyOf(charValues, charValues.length);
		Arrays.sort(copyOfCharValues);
		mergeSortEngine.sort(charValues);
		Assert.assertArrayEquals(charValues, copyOfCharValues);
	}

}
