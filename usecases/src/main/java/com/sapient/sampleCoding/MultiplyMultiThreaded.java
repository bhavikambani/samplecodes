package com.sapient.sampleCoding;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * The Class MultiplyMultiThreaded.
 * 
 * @author Bhavik Ambani
 * @created Apr 13, 2017 11:46:00 AM
 */
public class MultiplyMultiThreaded {

	public static void main(String[] args) {

	}

	public int multiply(int a, int b, int c, int d) {
		int result = 0;

		Callable<Integer> sum1 = new SumCallable(a, b);
		Callable<Integer> sum2 = new SumCallable(c, d);

		return result;
	}

	private static final class SumCallable implements Callable<Integer> {

		private int a;
		private int b;

		public SumCallable(final int a, final int b) {
			this.a = a;
			this.b = b;
		}

		public Integer call() throws Exception {
			return a + b;
		}

	}
}
