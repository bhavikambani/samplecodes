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
		n1.next = n2;

		System.out.println(new Solution().hasCycle(root));
	}

	public boolean hasCycle(Node head) {
		boolean hasCycle = false;

		Node slowPinter = head;
		if (head == null)
			return false;
		Node fastPointer = head.next != null ? head.next : null;

		while (!hasCycle) {
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
