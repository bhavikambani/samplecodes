package com.elitecore.src.projects.tree.preorder;

public class Solution {

	public Node generateTree() {

		Node n1 = setNode(1, null, null);
		Node n4 = setNode(4, null, null);
		Node n5 = setNode(5, n1, n4);
		Node n6 = setNode(6, null, null);
		Node n2 = setNode(2, n6, null);
		Node root = setNode(3, n5, n2);

		return root;
	}

    public int hashCode(int hash, String stringVal) {
    	int count = stringVal.length();
	int h = 0;
        int len = count;
	if (h == 0 && len > 0) {
	    int off = 0;
	    char val[] = stringVal.toCharArray();

            for (int i = 0; i < len; i++) {
                h = 31*h + val[off++];
            }
            hash = h;
        }
        return h;
    }
	
	private Node setNode(int data, Node left, Node right) {
		Node n = new Node();
		n.data = data;
		n.left = left;
		n.right = right;

		return n;
	}

	public static void main(String args[]) {
		Solution s = new Solution();
		Node rootNode = s.generateTree();
		s.inOrder(rootNode);

	}

	public void preOrder(Node node) {
		System.out.print(node.data + " ");

		if (node.left != null) {
			preOrder(node.left);
		}

		if (node.right != null) {
			preOrder(node.right);
		}

	}

	public void inOrder(Node root) {

		System.out.print(root.data + " ");
		if (root.left != null) {
			preOrder(root.left);
		}

		if (root.right != null) {
			preOrder(root.right);
		}

	}

}

class Node {
	int data;
	Node left;
	Node right;
}