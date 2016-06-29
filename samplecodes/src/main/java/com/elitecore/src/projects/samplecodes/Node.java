package com.elitecore.src.projects.samplecodes;

public class Node {

	int data;
	Node next = null;

	Node() {
	}

	Node(int val) {
		this.data = val;
		this.next = null;
	}

	public void setNextNode(Node n) {
		next = n;
	}
}