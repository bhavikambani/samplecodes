package com.bhavik.competitive.hacker_rank.crackingCodingInterview.IsThisABinarySearchTree;

/**
 * @author Bhavikkumar Ambani
 * @createdOn June 29, 2017
 *
 */
public class Solution {

	public static void main(String[] args) {

		Solution s = new Solution();
		Node rootNode = s.createBST();
		System.out.println(s.checkBST(rootNode));
	}

	Node createBST() {
		Node root = new Node(100);

		Node n1 = new Node(1);
		Node n10 = new Node(10);
		Node n15 = new Node(15);
		Node n7 = new Node(7);
		Node n121 = new Node(121);
		Node n101 = new Node(101);

		root.left = n15;
		root.right = n121;
		n15.left = n7;
		n7.right = n10;
		n7.left = n1;
		n15.left = n7;
		n101.left = n121;

		return root;
	}

	boolean checkBST(Node root) {
		boolean isBTree = true;
		if (root == null)
			return true;
		if (root.left != null) {
			if (root.data >= root.left.data) {
				isBTree = isBTree && checkBST(root.left);
			} else {
				isBTree = false;
			}
		}
		if (root.right != null) {
			if (root.data <= root.right.data) {
				isBTree = isBTree && checkBST(root.right);
			} else {
				isBTree = false;
			}
		}
		return isBTree;
	}

}

class Node {
	int data;
	Node left;
	Node right;

	public Node(int data) {
		this.data = data;
		left = null;
		right = null;
	}
}
