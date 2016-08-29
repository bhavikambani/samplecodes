package com.elitecore.datamanager;



import java.io.Serializable;
import java.util.*;
import com.elitecore.datamanager.exception.*;

public abstract class AbstractDAOAdapter<DomainObject extends RecordInterface> implements DAOInterface<DomainObject>{

	private long lastAccessedTime = System.currentTimeMillis();
	//protected static CacheManager cacheManager = null;


	/**
	 * This method will returns the corresponding instance of the 
	 * table for for the given value of the primary key by getting 
	 * record from the cache
	 */
	
	public RecordInterface getRecordByPrimaryKey(Object primaryKey) throws MethodNotSupportedException{
		throw new MethodNotSupportedException("Method Not Implemented");
		//return cacheManager.getRecordByPrimaryKey(primaryKey);
	}

	/**
	 * This method will returns the corresponding instance of the 
	 * table for for the given value of the give unique key by getting 
	 * record from the cache
	 */
	public RecordInterface getRecordByKey(String keyName, Object keyValue)throws MethodNotSupportedException{
		throw new MethodNotSupportedException("Method  Not Implemented : get data from caching");		
	}

	
	/**
	 * This method will returns the corresponding instance of the 
	 * table for for the given value of the primary key by getting record 
	 * from database
	 */
	public RecordInterface getSQLRecordByPrimaryKey(Object primaryKey) throws MethodNotSupportedException{
		throw new MethodNotSupportedException("Method Not Implemented");
	}

	/**
	 * This method will returns the corresponding instance of the 
	 * table for for the given value of the given unique key by getting 
	 * record from database
	 */
	public Object getSQLRecordByKey(String keyName, Object keyValue) throws MethodNotSupportedException{
		throw new MethodNotSupportedException("Method Not Implemented");
	}

	/**
	 * The getPrimaryKeyCollection() method returns the Collection 
	 * of Primary keys satisfying the condition
	 */
	public  Collection getPrimaryKeyCollection(String filter) throws MethodNotSupportedException {
		throw new MethodNotSupportedException("Method Not Implemented");
	}

	/**
	 * The insertRecord() method must insert this record into the database give a 
	 * negative or zero value if insert is unsuccessful. The implementation can choose to put the 
	 * inserted value into the TreeMap for in memory access.
	 * Update the lastAccessed time accordingly
	 */
	public abstract int	insertRecord(DomainObject ra)throws  DuplicateRecordException,DataManagerException;


	/**
	 * The updateRecord() must update this record in the database and 
	 * return a negative / zero value if update is unsuccessful. The 
	 * implementation must take care to reflect the update in 
	 * EntityList TreeMap so that inconsistency doesn't 
	 * arise when update is made in the Database and the TreeMap
	 * representation in the memory.
	 * Update the lastAccessed time accordingly
	 */
	public abstract int updateRecord(DomainObject ra)throws DataManagerException,Exception;

	
	/**The deleteRecord() method must delete this record in the database and 
	 * return a negative/zero value if the delete operation is unsuccessful. This 
	 * implementation must take care to reflect the update in the EntityList 
	 * TreeMap so that inconsistency doesn't arise when update is made in the Database 
	 * and the TreeMap representation in the memory.
	 * Update the lastAccessed time accordingly
	 */
	public abstract int	deleteRecord(DomainObject ra)throws DataManagerException, Exception;
	
	
	/**
	 * @param PrimaryKey of the database
	 * @return the number of the records deleted.
	 * @throws DataManagerException
	 * @throws Exception
	 */
	public abstract int	deleteRecord(Object PrimaryKey)throws DataManagerException, Exception;
	
		
	
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