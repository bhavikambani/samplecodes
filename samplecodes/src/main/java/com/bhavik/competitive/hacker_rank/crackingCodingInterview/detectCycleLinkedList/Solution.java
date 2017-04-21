package com.bhavik.competitive.hacker_rank.crackingCodingInterview.detectCycleLinkedList;

/**
 * The Class Solution.
 * 
 * @author Bhavik Ambani
 * @created Mar 26, 2017 7:26:57 PM
 */
public class Solution {

	public static void main(String[] args) {

		Node root = new Node();
		root.data = 0;

		Node n = new Node();
		n.data = 1;
		root.next = n;

		Node n1 = new Node();
		n1.data = 2;
		n.next = n1;

		Node n2 = new Node();
		n2.data = 3;
		n2.next = n;

		System.out.println(new Solution().hasCycle(root));
	}

	public boolean hasCycle(Node head) {
		boolean hasCycle = false;

		Node slowPinter = head;
		Node fastPointer = head.next != null ? head.next : null;

		while (!hasCycle) {
			System.out.println("A");
			System.out.println(slowPinter);
			System.out.println(fastPointer);
			System.out.println("A");
			if (slowPinter == null || fastPointer == null)
				break;

			if (slowPinter == fastPointer) {
				hasCycle = true;
				break;
			}

			slowPinter = slowPinter.next;
			fastPointer = fastPointer.next != null ? (fastPointer.next.next != null ? fastPointer.next.next : null)
					: null;
		}
		return hasCycle;
	}

	private static class Node {
		int data;
		Node next;

		@Override
		public String toString() {
			return "Node [data=" + data + ", next=" + next + "]";
		}

	}
}
