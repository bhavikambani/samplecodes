package com.elitecore.datamanager;

	/**
	 * The abstract class RecordAdapter which must be implemented by
	 * all the classes which are relate with each tables in database
	 */
import java.util.*;
import java.sql.*;
import com.elitecore.datamanager.exception.*;

public abstract class RecordAdapter implements RecordInterface{

	private long lastAccessedTime = System.currentTimeMillis();
	//protected static CacheManager cacheManager = null;


	/**
	 * This method will returns the corresponding instance of the 
	 * table for for the given value of the primary key by getting 
	 * record from the cache
	 */
	public static RecordAdapter getRecordByPrimaryKey(Object primaryKey) throws MethodNotSupportedException
	{
		throw new MethodNotSupportedException("Method Not Implemented");
		//return cacheManager.getRecordByPrimaryKey(primaryKey);
	}

	/**
	 * This method will returns the corresponding instance of the 
	 * table for for the given value of the give unique key by getting 
	 * record from the cache
	 */
	public static RecordAdapter getRecordByKey(String keyName, Object keyValue)throws MethodNotSupportedException
	{
		throw new MethodNotSupportedException("Method Not Implemented");
		//return cacheManager.getRecordByKey(keyName, keyValue);
	}

	
	/**
	 * This method will returns the corresponding instance of the 
	 * table for for the given value of the primary key by getting record 
	 * from database
	 */
	public static RecordAdapter getSQLRecordByPrimaryKey(Object primaryKey) throws MethodNotSupportedException{
		throw new MethodNotSupportedException("Method Not Implemented");
	}

	/**
	 * This method will returns the corresponding instance of the 
	 * table for for the given value of the given unique key by getting 
	 * record from database
	 */
	public static Object getSQLRecordByKey(String keyName, Object keyValue) throws MethodNotSupportedException{
		throw new MethodNotSupportedException("Method Not Implemented");
	}

	/**
	 * The getPrimaryKeyCollection() method returns the Collection 
	 * of Primary keys satisfying the condition
	 */
	public static Collection getPrimaryKeyCollection(String filter) throws MethodNotSupportedException{
		throw new MethodNotSupportedException("Method Not Implemented");
	}

	/**
	 * The insertRecord() method must insert this record into the database give a 
	 * negative or zero value if insert is unsuccessful. The implementation can choose to put the 
	 * inserted value into the TreeMap for in memory access.
	 * Update the lastAccessed time accordingly
	 * @throws MethodNotSupportedException 
	 */
	public int insertRecord()throws  DuplicateRecordException,DataManagerException, MethodNotSupportedException {
		throw new MethodNotSupportedException("Method Not Implemented");
	}


	/**
	 * The updateRecord() must update this record in the database and 
	 * return a negative / zero value if update is unsuccessful. The 
	 * implementation must take care to reflect the update in 
	 * EntityList TreeMap so that inconsistency doesn't 
	 * arise when update is made in the Database and the TreeMap
	 * representation in the memory.
	 * Update the lastAccessed time accordingly
	 */
	public int updateRecord()throws DataManagerException,Exception {
		throw new MethodNotSupportedException("Method Not Implemented");
	}

	/**The deleteRecord() method must delete this record in the database and 
	 * return a negative/zero value if the delete operation is unsuccessful. This 
	 * implementation must take care to reflect the update in the EntityList 
	 * TreeMap so that inconsistency doesn't arise when update is made in the Database 
	 * and the TreeMap representation in the memory.
	 * Update the lastAccessed time accordingly
	 */
	public int	deleteRecord()throws DataManagerException, Exception {
		throw new MethodNotSupportedException("Method Not Implemented");
	}
	
	public  RecordAdapter getSQLRecordAdapterByPrimaryKey(Object primaryKey) throws MethodNotSupportedException {
		throw new MethodNotSupportedException("Method Not Implemented");
	}
	
	public Object getSQLRecordAdapterByKey(String keyName, Object keyValue) throws MethodNotSupportedException {
		throw new MethodNotSupportedException("Method Not Implemented");
	}
	/**
	 * This method returns the last accessed time in milliseconds of the object.
	 */
	public long getLastAccessedTime(){
		return lastAccessedTime;
	}

	/**
	 * This method sets the last accessed time in milliseconds of the object.
	 */
	public void setLastAccessedTime(){
		lastAccessedTime = System.currentTimeMillis();
	}
}