package com.elitecore.src.projects.observer;

public class Test extends Test2 implements Cloneable {
	private int a;

	public Test(int aVal) {
		a = aVal;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		Test t1 = new Test(5);
		Test t2 = (Test) t1.clone();
		System.out.println(t1);
		System.out.println(t2);
	}

	@Override
	public String toString() {
		return "" + a;
	}

}

class Test2 extends Test1 {

}

class Test1 {
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}