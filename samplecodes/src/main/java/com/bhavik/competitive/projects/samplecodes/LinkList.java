package com.bhavik.competitive.projects.samplecodes;

public class LinkList {

	public static void main(String[] args) {
		LinkList ll = new LinkList();
		Node n = new Node();
		n.data = 5;

		// ll.Insert(n,200);
		Node x = ll.Insert(n, 201);

		ll.Print(x);
	}

	private Node n;

	public LinkList() {
		n = new Node(0);
		Node n1 = new Node(1);
		n.setNextNode(n1);
		Node n2 = new Node(2);
		n1.setNextNode(n2);
		Node n3 = new Node(3);
		n2.setNextNode(n3);
		Node n4 = new Node(4);
		n3.setNextNode(n4);
		Node n5 = new Node(5);
		n4.setNextNode(n5);
	}

	Node Insert(Node head, int data) {
		Node n;
		if (head.next == null) {
			n = new Node();
			n.data = data;
			head.next = n;
			return head;
		} else {
			n = Insert(head.next, data);
		}
		return head;
	}

	void Print(Node head) {
		if (head != null) {
			System.out.println(head.data);
		}
		if (head.next != null) {
			Print(head.next);
		}
	}

	void PrintNthNode(Node node, int index) {

	}
}
