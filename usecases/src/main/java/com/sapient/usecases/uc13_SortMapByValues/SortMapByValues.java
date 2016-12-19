package com.sapient.usecases.uc13_SortMapByValues;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * The Class SortMapByValues.
 * 
 * @author Bhavik Ambani
 */
public class SortMapByValues {

	/**
	 * Sort by value which will take .
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            the map
	 * @return the map
	 */
	public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {

		Set<Entry<K, V>> set = map.entrySet();
		List<Entry<K, V>> list = new LinkedList<Entry<K, V>>(set);

		Collections.sort(list, new Comparator<Entry<K, V>>() {

			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
