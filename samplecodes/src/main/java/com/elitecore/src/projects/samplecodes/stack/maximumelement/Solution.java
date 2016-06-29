package com.elitecore.src.projects.samplecodes.stack.maximumelement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;

public class Solution {

	public static void main(String args[]) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("/home/bhavik/Downloads/Important/Input.txt")).useDelimiter("\n");
		long totalTestCases = scanner.nextInt();
		MyStack longStack = new MyStack();
		for (long i = 0; i < totalTestCases && scanner.hasNext(); i++) {
			String input = scanner.next();
			String inputValues[] = input.split(" ");
			int operation = Integer.parseInt(inputValues[0]);
			switch (operation) {
			case 1:
				longStack.push(Long.parseLong(inputValues[1]));
				break;
			case 2:
				longStack.pop();
				break;
			case 3:
				System.out.println(longStack.max());
				break;
			}
		}
	}
}

class MyStack {
	LinkedList<Long> stackList = new LinkedList<Long>();
	TreeSet<Long> numberSet = new TreeSet<Long>();

	@Override
	public String toString() {
		return (stackList == null || stackList.size() == 0) ? "[ Empty Stack ]" : stackList.toString();
	}

	public void push(long a) {
		stackList.addFirst(new Long(a));
		numberSet.add(a);
	}

	public long pop() {
		long tmp = (long) stackList.getFirst();
		Long removeFirst = stackList.removeFirst();
		if (!stackList.contains(removeFirst)){
			numberSet.remove(removeFirst);
		}
		return tmp;

	}

	public long max() {
		return numberSet.last();
	}
}