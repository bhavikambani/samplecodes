package com.sapient.usecases.uc6MergeSort;

/**
 * The Class MergeSortImpl implements the Merge Sorg ALgorithm of UseCase 6 of
 * ACE Java.
 * 
 * Ref# https://vox.publicis.sapient.com/docs/DOC-152047
 * 
 * @author Bhavik Ambani
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MergeSortImpl {

	/**
	 * Main sort method which will be called from the external world.
	 *
	 * @param array
	 *            the array object which contans the list of elements to be
	 *            sorted
	 */
	public void sort(Comparable[] array) {
		Comparable[] b = new Comparable[array.length];
		sort(array, b, 0, array.length - 1);
	}

	/**
	 * Sort.
	 *
	 * @param arrayA
	 *            the array A
	 * @param arrayB
	 *            the array B
	 * @param low
	 *            the low
	 * @param high
	 *            the high
	 */
	private void sort(Comparable[] arrayA, Comparable[] arrayB, int low, int high) {
		if (low >= high)
			return;
		int mid = low + (high - low) / 2;
		sort(arrayA, arrayB, low, mid);
		sort(arrayA, arrayB, mid + 1, high);
		merge(arrayA, arrayB, low, high, mid);
	}

	/**
	 * Merge.
	 *
	 * @param arrayA
	 *            the array A
	 * @param arrayB
	 *            the array B
	 * @param low
	 *            the low
	 * @param high
	 *            the high
	 * @param mid
	 *            the mid
	 */
	private void merge(Comparable[] arrayA, Comparable[] arrayB, int low, int high, int mid) {
		int i = low;
		int j = mid + 1;

		for (int k = low; k <= high; k++) {
			if (i <= mid && j <= high) {
				if (arrayA[i].compareTo(arrayA[j]) >= 0) {
					arrayB[k] = arrayA[j++];
				} else {
					arrayB[k] = arrayA[i++];
				}
			} else if (j > high && i <= mid) {
				arrayB[k] = arrayA[i++];
			} else if (i > mid && j <= high) {
				arrayB[k] = arrayA[j++];
			}
		}
		for (int n = low; n <= high; n++) {
			arrayA[n] = arrayB[n];
		}
	}
}
