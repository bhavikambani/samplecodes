package com.elitecore.datamanager;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;

public class CacheManager {

	private RecordAdapter recordAdapterInstance;
	private HashMap recordCacheMap;
	private LinkedList mruRecordsList;
	private HashMap recordKeyMap;
	private HashMap mruListMap;
	private int cacheSize;


	public CacheManager(RecordAdapter recordadapter, int i) {
		recordAdapterInstance = null;
		recordCacheMap = null;
		mruRecordsList = null;
		recordKeyMap = null;
		mruListMap = null;
		recordAdapterInstance = recordadapter;
		//System.out.println("Class Name: " + ((Object) (recordadapter)).getClass().getName());
		recordCacheMap = new HashMap();
		mruRecordsList = new LinkedList();
		recordKeyMap = new HashMap();
		mruListMap = new HashMap();
		cacheSize = i;
	}

	public CacheManager(RecordAdapter recordadapter) {
		this(recordadapter, 50);
	}

	public RecordAdapter getRecordByPrimaryKey(Object obj) {
		RecordAdapter recordadapter = null;
		if(!recordCacheMap.containsKey(obj)) {
			synchronized(recordCacheMap) {
				//System.out.println("BEFORE BLOCK MRURECORDSLIST.SIZE() :" + mruRecordsList.size() + " : " + recordCacheMap.size());
				try {
					recordadapter = recordAdapterInstance.getSQLRecordAdapterByPrimaryKey(obj);
				}
				catch(Exception exception) {
					((Throwable) (exception)).printStackTrace();
				}
				if(recordadapter != null && cacheSize > 0) {
					if(mruRecordsList.size() >= cacheSize) {
						recordCacheMap.remove(mruRecordsList.getLast());
						mruRecordsList.removeLast();
					}
					recordCacheMap.put(obj, ((Object) (recordadapter)));
					mruRecordsList.addFirst(obj);
				}
			}
		} else {
			synchronized(recordCacheMap) {
				//System.out.println("FETCHING RECORD FROM RECORDCACHEMAP: " + recordCacheMap.size());
				recordadapter = (RecordAdapter)recordCacheMap.get(obj);
				//System.out.println(((Object) (recordadapter)));
				mruRecordsList.remove(obj);
				mruRecordsList.addFirst(obj);
			}
		}
		//System.out.println("RA OBJECT FROM CACHEMANAGER.GETRECORDBYPRIMARYKEY() : " + recordadapter);
		return recordadapter;
	}

	public RecordAdapter getRecordByKey(String s, Object obj) {
		//System.out.println("getRecordByKey: " + s);
		HashMap hashmap = (HashMap)recordKeyMap.get(((Object) (s)));
		LinkedList linkedlist = (LinkedList)mruListMap.get(((Object) (s)));
		RecordAdapter recordadapter = null;
		//System.out.println("recordCacheMapByKey from alternate MAP: " + hashmap);
		if(hashmap == null) {
			//System.out.println("recordCacheMapByKey is null");
			hashmap = new HashMap();
			linkedlist = new LinkedList();
			recordKeyMap.put(s,hashmap);
			mruListMap.put(s,linkedlist);
		}
		if(!hashmap.containsKey(obj)) {
			synchronized(hashmap) {
				try {
					Object obj1 = recordAdapterInstance.getSQLRecordAdapterByKey(s, obj);
					//System.out.println("KEY: " + obj1);
					if(obj1 != null){
						recordadapter = getRecordByPrimaryKey(((Object) (new Integer(((Number)obj1).intValue()))));
					}
				}
				catch(Exception exception) {
					//System.out.println("Exception occured at CacheManager.getRecordByKey : " + exception);
					((Throwable) (exception)).printStackTrace();
				}
				if(recordadapter != null && cacheSize > 0) {
					if(linkedlist.size() >= cacheSize) {
						hashmap.remove(linkedlist.getLast());
						linkedlist.removeLast();
					}
					hashmap.put(obj, ((Object) (recordadapter)));
					linkedlist.addFirst(obj);
				}
			}
		} else {
			synchronized(hashmap) {
				recordadapter = (RecordAdapter)hashmap.get(obj);
				linkedlist.remove(obj);
				linkedlist.addFirst(obj);
			}
		}
		//System.out.println("RA OBJECT FROM CACHEMANAGER.GETRECORDBYKEY() : " + recordadapter);
		return recordadapter;
	}

	public void updateCacheOnInsert(Object obj, RecordAdapter recordadapter) {
		synchronized(recordCacheMap) {
			if(cacheSize > 0) {
				if(mruRecordsList.size() >= cacheSize) {
					recordCacheMap.remove(mruRecordsList.getLast());
					mruRecordsList.removeLast();
				}
				recordCacheMap.put(obj, ((Object) (recordadapter)));
				mruRecordsList.addFirst(obj);
			}
		}
	}

	public void updateCacheOnUpdate(Object obj, RecordAdapter recordadapter) {
		//System.out.println("UPDATECACHEONUPDATE: " + obj + " : " + recordCacheMap.size());
		synchronized(recordCacheMap) {
			if(cacheSize > 0) {
				if(mruRecordsList.size() >= cacheSize) {
					recordCacheMap.remove(mruRecordsList.getLast());
					mruRecordsList.removeLast();
				}
				recordCacheMap.remove(obj);
				recordCacheMap.put(obj, ((Object) (recordadapter)));
				mruRecordsList.remove(obj);
				mruRecordsList.addFirst(obj);
				//System.out.println("CACHE UPDATED: " + recordadapter);
				//System.out.println("OBJECT FROM CACHE : " + (RecordAdapter)recordCacheMap.get(obj));
			}
		}
	}

	public void updateCacheOnDelete(Object obj) {
		synchronized(recordCacheMap) {
			// if(cacheSize > 0 && mruRecordsList.size() >= cacheSize) {
			/*
			 * CHANGE BY DHAVAL: BECAUSE ABOVE CONDITION WILL NOT DELTE FROM MAP.
			 * IT WILL DELETE ONLY IF CACHE SIZE IS EXCEED.
			 */
			if(cacheSize > 0){
				recordCacheMap.remove(obj);
				mruRecordsList.remove(obj);
			}
			recordKeyMap.clear();
			mruListMap.clear();
		}
	}
}
