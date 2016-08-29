package com.bhavik.misc.hashing.cuckoohashmap;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * The Class CuckooHashMap.
 * 
 * @author Bhavik Aniruddh Ambani
 *
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
public class CuckooHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

	/** The Constant DEFAULT_INITIAL_CAPACITY. */
	static final int DEFAULT_INITIAL_CAPACITY = 16;

	/** The Constant MAXIMUM_CAPACITY. */
	static final int MAXIMUM_CAPACITY = 1 << 30;

	/** The Constant DEFAULT_LOAD_FACTOR. */
	static final float DEFAULT_LOAD_FACTOR = 0.75f;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Map<Integer, String> map = new CuckooHashMap<Integer, String>();
		int itemCount = 25;
		for (int i = 0; i < itemCount; i++) {
			Integer key = i;
			String val = "Value_" + i;
			map.put(key, val);
		}

		System.out.println(map.get(1));

		for (String v : map.values()) {
			System.out.println(v);
		}
	}

	/**
	 * The Class DefaultHashFunction.
	 *
	 * @param <T>
	 *            the generic type
	 */
	static class DefaultHashFunction<T> implements HashFunction<T> {

		/** The Constant ENGINE. */
		private static final Random ENGINE = new Random();

		/** The rounds. */
		private int rounds;

		/**
		 * Instantiates a new default hash function.
		 */
		public DefaultHashFunction() {
			this(1);
		}

		/**
		 * Instantiates a new default hash function.
		 *
		 * @param rounds
		 *            the rounds
		 */
		public DefaultHashFunction(int rounds) {
			this.rounds = rounds;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.bhavik.misc.hashing.cuckoohashmap.CuckooHashMap.HashFunction#hash
		 * (java.lang.Object, int)
		 */
		public int hash(Object key, int limit) {
			ENGINE.setSeed(key.hashCode());
			int h = ENGINE.nextInt(limit);
			for (int i = 1; i < this.rounds; i++) {
				h = ENGINE.nextInt(limit);
			}

			return h;
		}
	}

	/**
	 * The Class Entry.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 */
	static class Entry<K, V> implements Map.Entry<K, V> {

		/** The key. */
		final K key;

		/** The value. */
		V value;

		/**
		 * Instantiates a new entry.
		 *
		 * @param k
		 *            the k
		 * @param v
		 *            the v
		 */
		Entry(K k, V v) {
			value = v;
			key = k;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public final boolean equals(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry e = (Map.Entry) o;
			Object k1 = getKey();
			Object k2 = e.getKey();
			if (k1 == k2 || (k1 != null && k1.equals(k2))) {
				Object v1 = getValue();
				Object v2 = e.getValue();
				if (v1 == v2 || (v1 != null && v1.equals(v2)))
					return true;
			}
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Map.Entry#getKey()
		 */
		public final K getKey() {
			return CuckooHashMap.unmaskNull(key);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Map.Entry#getValue()
		 */
		public final V getValue() {
			return value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		public final int hashCode() {
			return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Map.Entry#setValue(java.lang.Object)
		 */
		public final V setValue(V newValue) {
			V oldValue = value;
			value = newValue;
			return oldValue;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public final String toString() {
			return getKey() + "=>" + getValue();
		}
	}

	/**
	 * The Interface HashFunction.
	 *
	 * @param <T>
	 *            the generic type
	 */
	static interface HashFunction<T> {

		/**
		 * Hash.
		 *
		 * @param key
		 *            the key
		 * @param limit
		 *            the limit
		 * @return the int
		 */
		public int hash(Object key, int limit);
	}

	/**
	 * Mask null.
	 *
	 * @param <T>
	 *            the generic type
	 * @param key
	 *            the key
	 * @return the t
	 */
	static <T> T maskNull(T key) {
		return key == null ? (T) NULL_KEY : key;
	}

	/**
	 * Unmask null.
	 *
	 * @param <T>
	 *            the generic type
	 * @param key
	 *            the key
	 * @return the t
	 */
	static <T> T unmaskNull(T key) {
		return (key == NULL_KEY ? null : key);
	}

	/** The table. */
	transient Entry<K, V>[] table;

	/** The size. */
	transient int size;

	/** The threshold. */
	int threshold;

	/** The load factor. */
	final float loadFactor;

	/** The hash 1. */
	final transient HashFunction<K> hash1;

	/** The hash 2. */
	final transient HashFunction<K> hash2;

	/** The Constant NULL_KEY. */
	static final Object NULL_KEY = new Object();

	/**
	 * Instantiates a new cuckoo hash map.
	 */
	public CuckooHashMap() {
		this.loadFactor = DEFAULT_LOAD_FACTOR;
		threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
		table = new Entry[DEFAULT_INITIAL_CAPACITY];
		hash1 = new DefaultHashFunction<K>(2);
		hash2 = new DefaultHashFunction<K>(3);
		init();
	}

	/**
	 * Instantiates a new cuckoo hash map.
	 *
	 * @param h1
	 *            the h 1
	 * @param h2
	 *            the h 2
	 */
	public CuckooHashMap(HashFunction<K> h1, HashFunction<K> h2) {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, h1, h2);
	}

	/**
	 * Instantiates a new cuckoo hash map.
	 *
	 * @param initialCapacity
	 *            the initial capacity
	 */
	public CuckooHashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR);
	}

	/**
	 * Instantiates a new cuckoo hash map.
	 *
	 * @param initialCapacity
	 *            the initial capacity
	 * @param loadFactor
	 *            the load factor
	 */
	public CuckooHashMap(int initialCapacity, float loadFactor) {
		this(initialCapacity, loadFactor, new DefaultHashFunction<K>(2), new DefaultHashFunction<K>(3));
	}

	/**
	 * Instantiates a new cuckoo hash map.
	 *
	 * @param initialCapacity
	 *            the initial capacity
	 * @param loadFactor
	 *            the load factor
	 * @param h1
	 *            the h 1
	 * @param h2
	 *            the h 2
	 */
	public CuckooHashMap(int initialCapacity, float loadFactor, HashFunction<K> h1, HashFunction<K> h2) {
		int capacity = 1;
		while (capacity < initialCapacity)
			capacity <<= 1;

		this.loadFactor = loadFactor;
		threshold = (int) (capacity * loadFactor);
		table = new Entry[capacity];
		hash1 = h1;
		hash2 = h2;
		init();
	}

	/**
	 * Instantiates a new cuckoo hash map.
	 *
	 * @param m
	 *            the m
	 */
	public CuckooHashMap(Map<? extends K, ? extends V> m) {
		this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1, DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR);
	}

	/**
	 * Capacity.
	 *
	 * @return the int
	 */
	int capacity() {
		return table.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractMap#entrySet()
	 */
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		Set<java.util.Map.Entry<K, V>> es = new HashSet<java.util.Map.Entry<K, V>>();
		for (Entry<K, V> e : table) {
			if (e != null) {
				es.add(e);
			}
		}

		return es;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractMap#get(java.lang.Object)
	 */
	public V get(Object key) {
		Object k = maskNull(key);

		int hash = hash(hash1, k);
		Object k2;
		Entry<K, V> e = table[hash];
		if (e != null && ((k2 = e.key) == k || k.equals(k2))) {
			return e.value;
		}

		hash = hash(hash2, k);
		e = table[hash];
		if (e != null && ((k2 = e.key) == k || k.equals(k2))) {
			return e.value;
		}

		return null;
	}

	/**
	 * Hash.
	 *
	 * @param func
	 *            the func
	 * @param key
	 *            the key
	 * @return the int
	 */
	private int hash(HashFunction<K> func, Object key) {
		return func.hash(key, table.length);
	}

	/**
	 * Inits the.
	 */
	private void init() {
	}

	/**
	 * Insert entry.
	 *
	 * @param e
	 *            the e
	 * @return true, if successful
	 */
	private boolean insertEntry(Entry<K, V> e) {
		int count = 0;
		Entry<K, V> current = e;
		int index = hash(hash1, current.key);
		while (current != e || count < table.length) {
			Entry<K, V> temp = table[index];
			if (temp == null) {
				table[index] = current;
				return true;
			}

			table[index] = current;
			current = temp;
			if (index == hash(hash1, current.key)) {
				index = hash(hash2, current.key);
			} else {
				index = hash(hash1, current.key);
			}

			++count;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractMap#isEmpty()
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Load factor.
	 *
	 * @return the float
	 */
	float loadFactor() {
		return loadFactor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractMap#put(java.lang.Object, java.lang.Object)
	 */
	public V put(K key, V value) {
		return put(key, value, false);
	}

	/**
	 * Put.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @param isRehash
	 *            the is rehash
	 * @return the v
	 */
	private V put(K key, V value, boolean isRehash) {
		Object k = maskNull(key);

		if (containsKey(k)) {
			return null;
		}

		if (insertEntry(new Entry<K, V>((K) k, value))) {
			if (!isRehash) {
				size++;
			}

			return null;
		}

		rehash(2 * table.length);
		return put((K) k, value);
	}

	/**
	 * Rehash.
	 *
	 * @param newCapacity
	 *            the new capacity
	 */
	private void rehash(int newCapacity) {
		Entry<K, V>[] oldTable = table;
		int oldCapacity = oldTable.length;
		if (oldCapacity >= MAXIMUM_CAPACITY) {
			threshold = Integer.MAX_VALUE;
			return;
		}

		Entry<K, V>[] newTable = new Entry[newCapacity];
		table = newTable;
		for (Entry<K, V> e : oldTable) {
			if (e != null) {
				put(e.key, e.value, true);
			}
		}

		threshold = (int) (newCapacity * loadFactor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractMap#size()
	 */
	public int size() {
		return size;
	}

}