package com.bhavik.misc.tree.traversal;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PostOrderIterative<E> {

	public static void main(String[] args) {

	}

	public List<Integer> postorderTraversal(TreeNode root) {
		List<Integer> res = new ArrayList<Integer>();

		if (root == null) {
			return res;
		}

		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(root);

		while (!stack.isEmpty()) {
			TreeNode temp = stack.peek();
			if (temp.getLeftChild() == null && temp.getRightChild() == null) {
				TreeNode pop = stack.pop();
				res.add(pop.getData());
			} else {
				if (temp.getRightChild() != null) {
					stack.push(temp.getRightChild());
					temp.setRightChild(null);
				}

				if (temp.getLeftChild() != null) {
					stack.push(temp.getLeftChild());
					temp.setLeftChild(null);
				}
			}
		}

		return res;
	}
}
