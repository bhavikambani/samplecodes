package com.sapient.usecases.uc10_CustomConcurrentHashMap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * The Class CustomConcurrentHashMap.
 * 
 * @author Bhavik Ambani
 *
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
@SuppressWarnings("unchecked")
public class CustomConcurrentHashMap<K, V> implements Map<K, V> {

	/** The initial size. */
	private final int initialSize = 32;

	/** The num of segments. */
	private final int numOfSegments = 16;

	/** The segment size. */
	private final int segmentSize = initialSize / numOfSegments;

	/** The sync objects. */
	private final Object[] syncObjects = new Object[numOfSegments];

	/** The table. */
	private final Node<K, V>[] table = (Node<K, V>[]) new Node<?, ?>[initialSize];

	/** The size. */
	private volatile int size;

	/**
	 * Instantiates a new custom concurrent hash map.
	 */
	public CustomConcurrentHashMap() {
		for (int i = 0; i < numOfSegments; i++) {
			syncObjects[i] = new Object();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#size()
	 */
	public int size() {
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public V get(Object key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		int h = hash(key);
		V value = null;
		if (table[h] != null) {
			Node<K, V> node = table[h];
			do {
				if (key == null && node.getKey() == null) {
					value = node.getValue();
					break;
				} else if (key != null && key.equals(node.getKey())) {
					value = node.getValue();
					break;
				}
				node = node.next;
			} while (node != null);
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public V put(K key, V value) {
		if (key == null || value == null) {
			throw new IllegalArgumentException();
		}
		int h = hash(key);
		int index = h / segmentSize;
		V oldValue = null;
		synchronized (syncObjects[index]) {
			if (table[h] == null) {
				table[h] = new Node<K, V>(key, value);
				size++;
			} else {
				Node<K, V> node = table[h];
				Node<K, V> prevNode = null;
				do {
					if (key == null && node.getKey() == null) {
						oldValue = node.setValue(value);
						break;
					} else if (key != null && key.equals(node.getKey())) {
						oldValue = node.setValue(value);
						break;
					}
					prevNode = node;
					node = node.next;
				} while (node != null);
				if (node == null) {
					prevNode.next = new Node<K, V>(key, value);
					size++;
				}
			}
		}
		return oldValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public V remove(Object key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		int h = hash(key);
		int index = h / segmentSize;
		V value = null;
		synchronized (syncObjects[index]) {
			if (table[h] != null) {
				Node<K, V> node = table[h];
				Node<K, V> prevNode = null;
				do {
					if (key == null && node.getKey() == null) {
						value = node.getValue();
						break;
					} else if (key != null && key.equals(node.getKey())) {
						value = node.getValue();
						break;
					}
					prevNode = node;
					node = node.next;
				} while (node != null);
				if (node != null) {
					if (prevNode == null) {
						table[h] = node.next;
						node.next = null;
					} else {
						prevNode.next = node.next;
						node.next = null;
					}
					size--;
				}
			}
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		int h = hash(key);
		boolean found = false;
		if (table[h] != null) {
			Node<K, V> node = table[h];
			do {
				if (key == null && node.getKey() == null) {
					found = true;
					break;
				} else if (key != null && key.equals(node.getKey())) {
					found = true;
					break;
				}
				node = node.next;
			} while (node != null);
		}
		return found;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#entrySet()
	 */
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends K, ? extends V> m) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#clear()
	 */
	public void clear() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#keySet()
	 */
	public Set<K> keySet() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#values()
	 */
	public Collection<V> values() {
		return null;
	}

	/**
	 * Hash.
	 *
	 * @param key
	 *            the key
	 * @return the int
	 */
	private int hash(Object key) {
		int h = (key == null) ? 0 : key.hashCode() & (initialSize - 1);
		return h;
	}

	/**
	 * The Class Node.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 */
	private static class Node<K, V> implements Map.Entry<K, V> {

		/** The key. */
		private final K key;

		/** The value. */
		private V value;

		/** The next. */
		private Node<K, V> next;

		/**
		 * Instantiates a new node.
		 *
		 * @param key
		 *            the key
		 * @param value
		 *            the value
		 */
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Map.Entry#getKey()
		 */
		public K getKey() {
			return key;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Map.Entry#getValue()
		 */
		public V getValue() {
			return value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Map.Entry#setValue(java.lang.Object)
		 */
		public V setValue(V value) {
			V oldValue = this.value;
			this.value = value;
			return oldValue;
		}
	}
}
