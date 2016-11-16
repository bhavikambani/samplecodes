package com.bhavik.competitive.geeksforgeeks.linkedList;

public class InsertNode {

	public static void main(String[] args) {

		LinkedList<Integer> myList = new LinkedList<Integer>();
		for (int i = 0; i < 10; i++) {
			Node<Integer> n = new Node<Integer>();
			n.setData(i);
			myList.add(n);
		}

		myList.traverse();
		System.out.println();
		myList.printReverse(myList.getRootNode());
		System.out.println();
		myList.printReverseFromLastNode(myList.getLastNode());
		Node<Integer> n = new Node<Integer>();
		n.setData(4);
		myList.delete(n);
		myList.traverse();
		myList.delete(5);
		myList.traverse();


	}

}
