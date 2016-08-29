package com.elitecore.datamanager;

import java.io.Serializable;

import com.elitecore.datamanager.exception.DataManagerException;
import com.elitecore.datamanager.exception.DuplicateRecordException;

public interface DAOInterface <DomainObject extends RecordInterface>{

	/**
	 * The insertRecord() method must insert this record into the database give a 
	 * negative or zero value if insert is unsuccessful. The implementation can choose to put the 
	 * inserted value into the TreeMap for in memory access.
	 * Update the lastAccessed time accordingly
	 */
	public abstract int	insertRecord(DomainObject ri)throws  DuplicateRecordException,DataManagerException;


	/**
	 * The updateRecord() must update this record in the database and 
	 * return a negative / zero value if update is unsuccessful. The 
	 * implementation must take care to reflect the update in 
	 * EntityList TreeMap so that inconsistency doesn't 
	 * arise when update is made in the Database and the TreeMap
	 * representation in the memory.
	 * Update the lastAccessed time accordingly
	 */
	public abstract int updateRecord(DomainObject ri)throws DataManagerException,Exception;

	/**The deleteRecord() method must delete this record in the database and 
	 * return a negative/zero value if the delete operation is unsuccessful. This 
	 * implementation must take care to reflect the update in the EntityList 
	 * TreeMap so that inconsistency doesn't arise when update is made in the Database 
	 * and the TreeMap representation in the memory.
	 * Update the lastAccessed time accordingly
	 */
	public abstract int	deleteRecord(DomainObject ri)throws DataManagerException, Exception;
	
	

	
}