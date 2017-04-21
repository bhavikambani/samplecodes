package com.sapient.sampleCoding;

import java.util.Random;

/**
 * The Class CloneConstructorCall.
 * 
 * @author Bhavik Ambani
 * @created Apr 21, 2017 11:51:11 AM
 */
public class CloneConstructorCall implements Cloneable {

	private int data;

	public CloneConstructorCall() {
		System.out.println("Constructor called.");
		this.data = new Random().nextInt(100000);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return "CloneConstructorCall [data=" + data + "]";
	}

	public static void main(String[] args) {
		CloneConstructorCall cs = new CloneConstructorCall();
		try {
			CloneConstructorCall clone = (CloneConstructorCall) cs.clone();
			System.out.println(clone);
			System.out.println(cs);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
}
