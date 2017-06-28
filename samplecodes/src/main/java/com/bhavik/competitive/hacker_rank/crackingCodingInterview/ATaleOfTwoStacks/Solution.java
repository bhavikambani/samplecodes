package com.bhavik.competitive.hacker_rank.crackingCodingInterview.ATaleOfTwoStacks;

import java.util.Scanner;
import java.util.Stack;

/**
 * The Class Solution.
 * 
 * #Reference :
 * https://www.hackerrank.com/challenges/ctci-queue-using-two-stacks
 * 
 * @author Bhavikkumar Ambani
 * @createdOn June 28, 2017
 */
public class Solution {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		MyQueue<Integer> queue = new MyQueue<Integer>();

		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();

		for (int i = 0; i < n; i++) {
			int operation = scan.nextInt();
			if (operation == 1) { // enqueue
				queue.enqueue(scan.nextInt());
			} else if (operation == 2) { // dequeue
				queue.dequeue();
			} else if (operation == 3) { // print/peek
				System.out.println(queue.peek());
			}
		}
		scan.close();
	}
}

class MyQueue<T> {
	private Stack<T> firstStack;
	private Stack<T> secondStack;
	private T head;

	public MyQueue() {
		firstStack = new Stack<>();
		secondStack = new Stack<>();
	}

	public void enqueue(T value) {
		if (head == null) {
			head = value;
		}
		firstStack.push(value);
	}

	public T peek() {
		return head;
	}

	public T dequeue() {
		T result = null;
		while (!firstStack.isEmpty()) {
			secondStack.push(firstStack.pop());
		}
		result = secondStack.pop();
		head = null;
		if (!secondStack.isEmpty()) {
			head = secondStack.pop();
			firstStack.push(head);
			while (!secondStack.isEmpty()) {
				firstStack.push(secondStack.pop());
			}
		}
		return result;
	}
}
