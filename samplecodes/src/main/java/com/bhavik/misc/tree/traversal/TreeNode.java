package com.bhavik.misc.tree.traversal;

public class TreeNode< {

	private int data;
	private TreeNode leftChild;
	private TreeNode rightChild;

	public TreeNode(int data, TreeNode leftChild, TreeNode rightChild) {
		this.data = data;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	@Override
	public String toString() {
		return "TreeNode [data=" + data + ", leftChild=" + leftChild + ", rightChild=" + rightChild + "]";
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public TreeNode getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(TreeNode leftChild) {
		this.leftChild = leftChild;
	}

	public TreeNode getRightChild() {
		return rightChild;
	}

	public void setRightChild(TreeNode rightChild) {
		this.rightChild = rightChild;
	}

}
