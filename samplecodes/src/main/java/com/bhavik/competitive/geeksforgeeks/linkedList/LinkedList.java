package com.bhavik.competitive.geeksforgeeks.linkedList;

/**
 * The Class LinkedList.
 *
 * @param <T>
 *            the generic type for which we want to create {@link LinkedList}
 *            object
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class LinkedList<T> {

	/** The root node. */
	private Node<T> rootNode = null;

	/** The last node. */
	private Node<T> lastNode = null;

	/** The node count. */
	private int nodeCount = 0;

	/**
	 * Adds the.
	 *
	 * @param node
	 *            the node
	 */
	public void add(Node<T> node) {
		if (rootNode == null) {
			this.rootNode = node;
			rootNode.setNextNode(null);
			rootNode.setPrevNode(null);
			this.lastNode = node;
			nodeCount = 1;
		} else {
			node.setNextNode(null);
			lastNode.setNextNode(node);
			node.setPrevNode(lastNode);
			lastNode = node;
			nodeCount++;
		}
	}

	/**
	 * Delete.
	 *
	 * @param node
	 *            the node
	 */
	public void delete(Node<T> node) {
		Node<T> currentNode = rootNode;

		if (node == null) {
			System.out.println("Entered node is passed as null");
			return;
		}

		if (currentNode == null || nodeCount == 0) {
			System.out.println("LinkedList is empty");
			return;
		}

		if (rootNode.equals(node)) {
			rootNode = null;
			nodeCount = 0;
		}
		boolean nodeFound = false;
		while (currentNode.getNextNode() != null) {
			currentNode = currentNode.getNextNode();
			if (currentNode.equals(node)) {
				nodeFound = true;
				Node<T> nextNode = currentNode.getNextNode();
				Node<T> prevNode = currentNode.getPrevNode();
				if (prevNode != null) {
					prevNode.setNextNode(currentNode.getNextNode());
				}
				if (nextNode != null) {
					nextNode.setPrevNode(currentNode.getPrevNode());
				}
				System.gc();
			}

		}
		System.out.println("Node " + (nodeFound ? "deleted " : "was not deleted") + "successfully.");
	}

	/**
	 * Delete.
	 *
	 * @param data
	 *            the data
	 */
	public void delete(T data) {
		Node<T> node = new Node<T>();
		node.setData(data);
		delete(node);
	}

	/**
	 * Gets the root node.
	 *
	 * @return the root node
	 */
	public Node<T> getRootNode() {
		return rootNode;
	}

	/**
	 * Gets the last node.
	 *
	 * @return the last node
	 */
	public Node<T> getLastNode() {
		return lastNode;
	}

	/**
	 * Gets the node count.
	 *
	 * @return the node count
	 */
	public int getNodeCount() {
		return nodeCount;
	}

	/**
	 * Prints the reverse from last node.
	 *
	 * @param node
	 *            the node
	 */
	public void printReverseFromLastNode(Node<T> node) {
		if (node != null) {
			System.out.print(node.getData() + " ");
			printReverseFromLastNode(node.getPrevNode());
		}

	}

	/**
	 * Prints the reverse.
	 *
	 * @param node
	 *            the node
	 */
	public void printReverse(Node<T> node) {
		if (node != null) {
			printReverse(node.getNextNode());
		}
		if (node != null)
			System.out.print(node.getData() + " ");
	}

	/**
	 * Traverse.
	 */
	public void traverse() {
		Node<T> currentNode = rootNode;
		do {
			System.out.print(currentNode.getData() + " ");
			currentNode = currentNode.getNextNode();
		} while (currentNode != null);
	}
}
