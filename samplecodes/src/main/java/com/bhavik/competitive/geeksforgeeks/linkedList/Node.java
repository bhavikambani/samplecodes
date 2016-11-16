package com.bhavik.competitive.geeksforgeeks.linkedList;

import java.io.Serializable;

/**
 * The Class Node.
 *
 * @param <T>
 *            the generic type
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class Node<T> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2800898818497467807L;

	/** The data. */
	private T data;

	/** The next node. */
	private Node<T> nextNode;

	/** The prev node. */
	private Node<T> prevNode;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((nextNode == null) ? 0 : nextNode.hashCode());
		result = prime * result + ((prevNode == null) ? 0 : prevNode.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node<T> other = (Node<T>) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Node [data=" + data + ", nextNode=" + nextNode + ", prevNode=" + prevNode + "]";
	}

	/**
	 * Instantiates a new node.
	 */
	public Node() {
	}

	/**
	 * Instantiates a new node.
	 *
	 * @param data
	 *            the data
	 * @param nextNode
	 *            the next node
	 * @param prevNode
	 *            the prev node
	 */
	public Node(T data, Node<T> nextNode, Node<T> prevNode) {
		super();
		this.data = data;
		this.nextNode = nextNode;
		this.prevNode = prevNode;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data
	 *            the new data
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * Gets the next node.
	 *
	 * @return the next node
	 */
	public Node<T> getNextNode() {
		return nextNode;
	}

	/**
	 * Sets the next node.
	 *
	 * @param nextNode
	 *            the new next node
	 */
	public void setNextNode(Node<T> nextNode) {
		this.nextNode = nextNode;
	}

	/**
	 * Gets the prev node.
	 *
	 * @return the prev node
	 */
	public Node<T> getPrevNode() {
		return prevNode;
	}

	/**
	 * Sets the prev node.
	 *
	 * @param prevNode
	 *            the new prev node
	 */
	public void setPrevNode(Node<T> prevNode) {
		this.prevNode = prevNode;
	}

}
