/**
 * @(#)ConnectionPool.java
 *
 * Copyright (c) 1999 Elitecore Techonologies Pvt. Ltd. All Rights Reserved.
 *
 * You may study, use, modify, and distribute this software for any
 * purpose provided that this copyright notice appears in all copies.
 *
 * This software is provided WITHOUT WARRANTY either expressed or
 * implied.
 *
 * @author  Ajay Iyer
 * @version 1.1
 * @date    27Apr1999
 * @modified by Abhilash V. Sonwane
 * @modified date 31Mar2001
 */

package com.elitecore.jdbchelper.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import com.elitecore.log.EliteLogger;

/**
 * <p>This class serves as a JDBC connection repository. Since
 * creating database connections is one of the most time
 * intensive aspects of JDBC, we'll create a pool of connections.
 * Each connection can be used and then replaced back into the
 * pool.
 *
 * <p>A properties file 'ConnectionPool.cfg' will be used to
 * specify the types of JDBC connections to create, as well as
 * the minimum and maximum size of the pool. The format of
 * the configuration file is:
 *
 *   #(comment)
 *   JDBCDriver=<JDBC driver name>
 *   JDBCConnectionURL=<JDBC Connection URL>
 *   ConnectionPoolSize=<minimum size of the pool>
 *   ConnectionPoolMax=<maximum size of the pool, or -1 for none>
 *   ConnectionUseCount=<maximum usage count for one connection>
 *   ConnectionTimeout=<maximum idle lifetime (in minutes) of
 *        a connection>
 *  Added by ajay
 *   ConnectionAttempt=<maximum attempts made before returning zero>
 *   <other property for JDBC connection>=<value> //giving problems
 *   Username=<name of user for connecting with db
 *   Password=<passwd of user connecting
 * <p>Any number of additional properties may be given (such
 * as username and password) as required by the JDBC driver.
 *
 */

public class ConnectionPool implements com.elitecore.jdbchelper.timer.TimerListener {
	
  // ConnectionManager Identity
  String connectionManagerIdentity;

  // JDBC Driver name
  String m_JDBCDriver;

  // JDBC Connection URL
  String m_JDBCConnectionURL;

  // Minimum size of the pool
  int m_ConnectionPoolSize;

  // Maximum size of the pool
  int m_ConnectionPoolMax;

  // Maximum number of uses for a single connection, or -1 for
  // none
  int m_ConnectionUseCount;

  // Maximum connection idle time (in miliseconds)
  int m_ConnectionTimeout;

  // Interval in which the time checks for timed out connections(in seconds)
  int m_TimerInterval;

  // Maximum connection attempt
  int m_ConnectionAttempts;
  
  // Add these many connections to pool once connections in use exceeds ConnectionPoolSize
  int m_PoolIncrementSize;

  // Additional JDBC properties
  java.util.Properties m_JDBCProperties; //not used currently

  //user name
  String m_User;

  //password
  String m_Password;

  // The Connection pool. This is a ArrayList of ConnectionObject
  // objects
  LinkedList m_pool;
  
  Map m_pool_inuse;
  
  //volatile int totalConnections = 0;
  
  // The maximum number of simultaneous connections as reported
  // by the JDBC driver
  int m_MaxConnections = -1;
  
  public String m_SqlMaker = null;
  
  public String m_PrimaryKeyUtil = null;
  /**
   * Is this Mysql database
   */
  static boolean isMysql = false;

  // Our Timer object
  com.elitecore.jdbchelper.timer.Timer m_timer;
  
  public static final String MODULE = "[ConnectionPool] ";
  
  public int getConnectionAttempts(){
	  return m_ConnectionAttempts;
  }

  /**
    * <p>Initializes the ConnectionPool object using
    * 'ConnectionPool.cfg' as the configuration file
    *
    * @return true if the ConnectionPool was initialized
    * properly
    */
  public boolean initialize() throws Exception {
      boolean success = initialize("ConnectionPool.cfg");
	  return success;
    }

  /**
    * <p>Initializes the ConnectionPool object with the specified
    * configuration file
    *
    * @param config Configuration file name
    * @return true if the ConnectionPool was initialized
    * properly
    */
  public boolean initialize(String config) throws Exception {
	  
      // Load the configuration parameters. Any leftover parameters
      // from the configuration file will be considered JDBC
      // connection attributes to be used to establish the
      // JDBC connections
      boolean rc = loadConfig(config);
	  
	  if(rc){
  		if(m_JDBCDriver.indexOf("org.gjt.mm.mysql") != -1){
			  isMysql =true;
		}
		EliteLogger.connectionPoolLog.debug(MODULE+ connectionManagerIdentity + ":::EQUALSIGNORECASE" + 
											   m_JDBCDriver.equalsIgnoreCase("org.gjt.mm.mysql.Driver"));
		EliteLogger.connectionPoolLog.debug(MODULE+ connectionManagerIdentity + ":::ISMYSQL: " + isMysql);
	  }

	  if (rc) {
        // Properties were loaded; attempt to create our pool
        // of connections
        createPool();
        // Start our timer so we can timeout connections. The
        // clock cycle will be 20 seconds
        m_timer = new com.elitecore.jdbchelper.timer.Timer(this, m_TimerInterval);
        m_timer.start();
      }
      return rc;
    }

	public void setConnectionManagerIdentity(String cmi){
		  connectionManagerIdentity = cmi;
	}
  
	public String getConnectionManagerIdentity(){
		  return connectionManagerIdentity;
	}

  /**
    * <p>Destroys the pool and it's contents. Closes any open
    * JDBC connections and frees all resources
    */
  public void destroy(){
      
	  try {
        // Stop our timer thread
        if (m_timer != null) {
          //m_timer.stop();
          m_timer.setExit(false);
          m_timer = null;
        }
        // Clear our pool
        if (m_pool != null) {
          // Loop throught the pool and close each connection
          for (Iterator iterator = m_pool.iterator(); iterator.hasNext(); ) {
            close((ConnectionObject)iterator.next());
          }
        }
        m_pool = null;
        
        // Clear our pool - connections in use
        if (m_pool_inuse != null) {
        	
          Collection connectionObjects = m_pool_inuse.values();
          
          // Loop throught the pool and close each connection
          for (Iterator iterator = connectionObjects.iterator(); iterator.hasNext(); ) {
            close((ConnectionObject)iterator.next());
          }
        }
        m_pool_inuse = null;
        
      }catch (Exception ex) {
		  EliteLogger.connectionPoolLog.error(MODULE+connectionManagerIdentity + " Exception in destroy() : " + ex,ex);
      }
    }

  /**
    * <p>Gets an available JDBC Connection. Connections will be
    * created if necessary, up to the maximum number of connections
    * as specified in the configuration file.
    *
    * @return JDBC Connection, or null if the maximum
    * number of connections has been exceeded
    */
  
  public synchronized java.sql.Connection getConnection(){
     
	  Connection con = null;
	  
	  try{
		  // If there is no pool it must have been destroyed
	      if (m_pool == null) {
	        return null;
	      }
	      ConnectionObject connectionObject = null;
	      int poolSize = m_pool.size();
	
	      // Get the next available connection
	      for (int i = 0; i < poolSize; i++) {
	        // Get the ConnectionObject from the pool
	        ConnectionObject co = (ConnectionObject)m_pool.removeLast();
			EliteLogger.connectionPoolLog.debug(MODULE+ "["+connectionManagerIdentity+"] " 
				+ "Iteration "+(i+1)+" : Got " + co);
	        // If this is a valid connection and it is not in use,
	        // grab it
	        if (co.isAvailable()){
	          connectionObject = co;
	          break;
	        }
	        m_pool_inuse.put(connectionObject.con, connectionObject);
	      }
	
	      // No more available connections. If we aren't at the
	      // maximum number of connections, create a new entry
	      // in the pool
	      if (connectionObject == null){
	        if ((m_ConnectionPoolMax < 0) ||
	            ((m_ConnectionPoolMax > 0) &&
	             ( ( m_pool.size()+ m_pool_inuse.size() ) < m_ConnectionPoolMax))) { // (poolSize < m_ConnectionPoolMax)
	
	          // Add a new connection.
	          int i = addConnections();
	          // If a new connection was created, use it
	          if (i >= 0) {
	        	  connectionObject = (ConnectionObject)m_pool.removeLast();
	          }else{
	        	  EliteLogger.connectionPoolLog.debug(MODULE+"Invalid connection index "+i+" returned from addConnection()."); 
	          }
	        }else {
	        	EliteLogger.connectionPoolLog.error(MODULE+"Maximum number of connections exceeded.");
	        }
	      }
	
	      // If we have a connection, set the last time accessed,
	      // the use count, and the in use flag
	      if (connectionObject != null) {
				connectionObject.inUse = true;
				connectionObject.useCount++;
				touch(connectionObject);
				con = connectionObject.con;
				m_pool_inuse.put(connectionObject.con, connectionObject);
	      }
	  	  
	  }catch (Exception e) {
		// TODO: handle exception
		  EliteLogger.connectionPoolLog.error(MODULE+"Error while getConnection() : "+e,e);
	  }finally{
		  logStatistics();
	  }
      return con;
    }

  /**
    * <p>Places the connection back into the connection pool,
    * or closes the connection if the maximum use count has
    * been reached
    *
    * @param Connection object to close
    */
  
  public synchronized void close(java.sql.Connection con){

	  try {
		  // Find the connection in the pool
	      //int index = find(con);
		  ConnectionObject co = (ConnectionObject) m_pool_inuse.remove(con);
	
	      if ( co != null ) {
	        // If the use count exceeds the max, remove it from
	        // the pool.
	        if ( (m_ConnectionUseCount > 0) && (co.useCount >= m_ConnectionUseCount) ) {
	          EliteLogger.connectionPoolLog.debug(MODULE+"Use-count exceeded, so removing & closing it : "+co);
	          close(co);
	        } else {
	          // Clear the use count and reset the time last used
	          touch(co);
	          co.inUse = false;
	          m_pool.addLast(co); 
	          EliteLogger.connectionPoolLog.debug(MODULE+"Closing "+co);
	        }
	      }
	      
	  }catch (Exception e) {
		// TODO: handle exception
		  EliteLogger.connectionPoolLog.error(MODULE+"Exception in closing() : "+e,e);
	  }finally{
		  logStatistics();
	  }
    }

  /**
    * <p>Prints the contents of the connection pool to the
    * standard output device
    */
  public void printPool(){
	 
	  try {
	      EliteLogger.connectionPoolLog.debug(MODULE+"["+connectionManagerIdentity+"]:::--------ConnectionPool--------");
	      if (m_pool != null) {
	        for (int i = 0; i < m_pool.size(); i++) {
	          ConnectionObject co = (ConnectionObject)m_pool.get(i);
	          EliteLogger.connectionPoolLog.debug(MODULE+"["+connectionManagerIdentity+"]:::"+i+"===" + co);
	        }
	      }
	  }catch (Exception e) {
		  EliteLogger.connectionPoolLog.error(MODULE+"Error in printPool() : "+e,e);  
	  }
    }

  /**
    * <p>Removes the ConnectionObject from the pool at the
    * given index
    *
    * @param index Index into the pool ArrayList
    */
  private synchronized void removeFromPool(int index){
	  
	  try{
	      // Make sure the pool and index are valid
	      if (m_pool != null) {
	        if (index < m_pool.size()) {
	          // Get the ConnectionObject and close the connection
	          ConnectionObject co = (ConnectionObject)m_pool.get(index);
	          close(co);
	          // Remove the element from the pool
	          m_pool.remove(index);
	        }
	      }
	  }catch (Exception e) {
		  EliteLogger.connectionPoolLog.error(MODULE+"Error in removeFromPool() : "+e,e);  
	  }

   }
  

  /**
    * <p>Closes the connection in the given ConnectionObject
    *
    * @param connectObject ConnectionObject
    */
  private void close(ConnectionObject connectionObject){
	  
      if (connectionObject != null) {
        if (connectionObject.con != null) {
          try {
            // Close the connection
        	EliteLogger.connectionPoolLog.debug(MODULE+"Closing : "+connectionObject);
            connectionObject.con.close();
          }catch (Exception ex) {
            // Ignore any exceptions during close
        	EliteLogger.connectionPoolLog.debug(MODULE+"Exception in close() : "+ex,ex);  
          }
          // Clear the connection object reference
          connectionObject.con = null;
          //totalConnections --;
        }
      }
    }

  /**
    * <p>Loads the given configuration file.  All global
    * properties (such as JDBCDriver) will be
    * read and removed from the properties list. Any leftover
    * properties will be returned. Returns null if the
    * properties could not be loaded
    *
    * @param name Configuration file name
    * @return true if the configuration file was loaded
    */
  private boolean loadConfig(String name)throws Exception {
	  
      boolean rc = false;
      InputStream in = null;

      try {
	      // Get our class loader
	      ClassLoader cl = getClass().getClassLoader();
	
	      // Attempt to open an input stream to the configuration file.
	      // The configuration file is considered to be a system
	      // resource.
	
	      if (cl != null) {
	        in = cl.getResourceAsStream(name);
	      }else {
	        in = ClassLoader.getSystemResourceAsStream(name);
	      }

	      // If the input stream is null, then the configuration file
	      // was not found
	      if (in == null) {
	        EliteLogger.connectionPoolLog.error(MODULE+"["+connectionManagerIdentity + "] ConnectionPool configuration file '"+name+"' not found");
	        
	      }else{

	          m_JDBCProperties = new java.util.Properties();
	
	          // Load the configuration file into the properties table
	          m_JDBCProperties.load(in);
	
	          // Got the properties. Pull out the properties those we are interested in
	          m_JDBCDriver = consume(m_JDBCProperties, "JDBCDriver");
	          
	          m_JDBCConnectionURL = consume(m_JDBCProperties,
	                                        "JDBCConnectionURL");
	          
			  m_ConnectionPoolSize = consumeInt(m_JDBCProperties,
	                                            "ConnectionPoolSize");
	          
			  m_ConnectionPoolMax = consumeInt(m_JDBCProperties,
	                                           "ConnectionPoolMax");
	          
			  //Convert the connectiontimeout to milliseconds for easier calculation
			  m_ConnectionTimeout = consumeInt(m_JDBCProperties,
	                                           "ConnectionTimeout")*1000;
	          
			  m_TimerInterval = consumeInt(m_JDBCProperties,
	                                           "TimerInterval");
	
			  m_ConnectionAttempts = consumeInt(m_JDBCProperties,"ConnectionAttempts");
	          
			  m_ConnectionUseCount = consumeInt(m_JDBCProperties,
	                                            "ConnectionUseCount");
	          
			  m_User = consume(m_JDBCProperties,"User");
	
			  
			  m_Password = consume(m_JDBCProperties,"Password");
			  
			  m_SqlMaker = consume(m_JDBCProperties,"SqlMaker");
			  
			  m_PrimaryKeyUtil = consume(m_JDBCProperties,"PrimaryKeyUtil");
			  
			  m_PoolIncrementSize = consumeInt(m_JDBCProperties,"PoolIncrementSize");
	
	          rc = true;
			
	      } //  else ends here
	      
  	  }catch(Exception e){
		EliteLogger.connectionPoolLog.error(MODULE+"["+connectionManagerIdentity + "] Exception in loadConfig() : ", e);
	  
  	  } finally {
        // Always close the input stream
        if (in != null) {
          try {
          	in.close();
          }catch (Exception ex) {
          	EliteLogger.connectionPoolLog.error(MODULE+"["+connectionManagerIdentity + "] Exception in loadConfig() while closing stream : ", ex);
          }
        } // if ends here
      } // finally ends here
		
      return rc;
    }

    /**
    * <p>Consumes the given property and returns the value.
    *
    * @param properties Properties table
    * @param key Key of the property to retrieve and remove from
    * the properties table
    * @return Value of the property, or null if not found
    */
   private String consume(java.util.Properties p, String key){
	   
      String s = null;

      if ( p != null && key != null ) {
        // Get the value of the key
        s = p.getProperty(key);
        // If found, remove it from the properties table
        if (s != null) {
          p.remove(key);
        }
      }
	  if(s == null){
		  EliteLogger.connectionPoolLog.error(MODULE+"["+connectionManagerIdentity + "] value is null for key : "+ key);
		  return null;
	  }
      return s.trim(); //in unix sometimes it gives an error of space<ajay>
    }

  /**
    * <p>Consumes the given property and returns the integer
    * value.
    *
    * @param properties Properties table
    * @param key Key of the property to retrieve and remove from
    * the properties table
    * @return Value of the property, or -1 if not found
    */
  private int consumeInt(java.util.Properties p, String key){
	  
      int n = -1;
      // Get the String value
      String value = consume(p, key);

      // Got a value; convert to an integer
      if (value != null) {
        try {
          n = Integer.parseInt(value);
        }catch (Exception ex) { }
      }
      return n;
    }

  /**
    * <p>Creates the initial connection pool. A timer thread
    * is also created so that connection timeouts can be
    * handled.
    *
    * @return true if the pool was created
    */
  private void createPool() throws Exception {
	  
      // Sanity check our properties
      if (m_JDBCDriver == null) {
        throw new Exception("JDBCDriver property not found");
      }
      if (m_JDBCConnectionURL == null) {
        throw new Exception("JDBCConnectionURL property not found");
      }
      if (m_ConnectionPoolSize < 0) {
        throw new Exception("ConnectionPoolSize property not found");
      }
      if (m_ConnectionPoolSize == 0) {
        throw new Exception("ConnectionPoolSize invalid");
      }
      if (m_ConnectionPoolMax < m_ConnectionPoolSize) {
    	EliteLogger.connectionPoolLog.debug(MODULE+"WARNING - ConnectionPoolMax is invalid and will be ignored.");
        m_ConnectionPoolMax = -1;
      }
      if (m_ConnectionTimeout < 0) {
        // Set the default to 5 minutes
        m_ConnectionTimeout = 300*1000;
      }
      if (m_ConnectionAttempts < 0){
        // Set the default to 1 attempt
    	  m_ConnectionAttempts = 1;
      }
      if (m_ConnectionUseCount < 0){
          // Set the default to any number of attempts
    	  m_ConnectionUseCount = -1;
      }
      if (m_PoolIncrementSize <= 0){
          // Set the default to any number of attempts
    	  m_PoolIncrementSize = 1;
      }
      
      if (m_User == null ) {
        throw new Exception("UserName not found");
      }
      if (m_Password == null ) {
        throw new Exception("Password not found");
      }

      // Dump the parameters we are going to use for the pool.
      // We don't know what type of servlet environment we will
      // be running in - this may go to the console or it
      // may be redirected to a log file
      EliteLogger.connectionPoolLog.debug(MODULE+"JDBCDriver = " + m_JDBCDriver);
      EliteLogger.connectionPoolLog.debug(MODULE+"JDBCConnectionURL = " + m_JDBCConnectionURL);
      EliteLogger.connectionPoolLog.debug(MODULE+"ConnectionPoolSize = " + m_ConnectionPoolSize);
      EliteLogger.connectionPoolLog.debug(MODULE+"ConnectionPoolMax = " + m_ConnectionPoolMax);
      EliteLogger.connectionPoolLog.debug(MODULE+"ConnectionUseCount = " + m_ConnectionUseCount);
      EliteLogger.connectionPoolLog.debug(MODULE+"ConnectionTimeout = " + m_ConnectionTimeout/1000+" seconds");
      EliteLogger.connectionPoolLog.debug(MODULE+"ConnectionAttempts = " + m_ConnectionAttempts +" times ");
      EliteLogger.connectionPoolLog.debug(MODULE+"PoolIncrementSize = " + m_PoolIncrementSize);

      // Also dump any additional JDBC properties
      Enumeration enum_Keys = m_JDBCProperties.keys();
      
      while (enum_Keys.hasMoreElements()) {
        String key = (String) enum_Keys.nextElement();
        String value = m_JDBCProperties.getProperty(key);
        EliteLogger.connectionPoolLog.debug(MODULE+"(JDBC Property) " + key + " = " + value);
      }

      // Attempt to create a new instance of the specified
      // JDBC driver. Well behaved drivers will register
      // themselves with the JDBC DriverManager when they
      // are instantiated
      EliteLogger.connectionPoolLog.debug(MODULE+"Registering " + m_JDBCDriver);
      //java.sql.Driver d = (java.sql.Driver)
        //Class.forName(m_JDBCDriver).newInstance();

      // Create the ArrayList for the pool
      m_pool = new LinkedList();
      
      m_pool_inuse = Collections.synchronizedMap(new HashMap());

      EliteLogger.connectionPoolLog.debug(MODULE+"Initializing connection pool...");
      // Bring the pool to the minimum size
      fillPool(m_ConnectionPoolSize);
      EliteLogger.connectionPoolLog.debug(MODULE+"Connection pool initialized.");
    }

  /**
    * <p>Adds a new connection to the pool
    *
    * @return Index of the new pool entry, or -1 if an
    * error has occurred
    */
  private int addConnections(){
	  
      int index = -1;

      try {
        // Calculate the new size of the pool
        int noOfConnectionsToAdd = m_PoolIncrementSize;

        if ( ( m_pool.size() + m_pool_inuse.size() ) >= m_ConnectionPoolMax ) {
        	EliteLogger.connectionPoolLog.debug(MODULE+"["+connectionManagerIdentity+"] Could not add more connections : "
        		+"Total : "+(m_pool.size()+m_pool_inuse.size())+", Max : "+m_ConnectionPoolMax);
        	return -1;
        }
        // If total number of connections about to exceed max size then pass the number of connections we are permitted to add 
        if ( ( noOfConnectionsToAdd + m_pool.size() + m_pool_inuse.size() ) > m_ConnectionPoolMax ){ 
        	noOfConnectionsToAdd = m_ConnectionPoolMax - ( m_pool.size() + m_pool_inuse.size());
        }	
       
        if ( noOfConnectionsToAdd < 1 ) noOfConnectionsToAdd = 1;
        EliteLogger.connectionPoolLog.debug(MODULE+"["+connectionManagerIdentity+"] Filling pool with "+noOfConnectionsToAdd+" new connection/s.");
        // Create a new entry
        
        fillPool(noOfConnectionsToAdd);

        // Set the index pointer to the new connection if one
        // was created
        index = m_pool.size() - 1; 
        EliteLogger.connectionPoolLog.debug(MODULE+"["+connectionManagerIdentity+"] Returning connection index "+noOfConnectionsToAdd+" from addConnection().");
        
      }catch (Exception ex) {
		  EliteLogger.connectionPoolLog.error(MODULE+"["+connectionManagerIdentity+"] Exception in addConnection() : " + ex,ex);
      }
      return index;
    }

  /**
    * <p>Brings the pool to the given size
    */
  private synchronized void fillPool( int fillSize ) throws java.sql.SQLException,java.lang.ClassNotFoundException{
      
	  int i = 0;
	  
	  if ( fillSize <= 0 ){
		  EliteLogger.connectionPoolLog.debug(MODULE+"Connection pool fill size "+fillSize+" is invalid.");
		  logStatistics();
		  return;
	  }
	  
	  // Loop while we need to create more connections
      while ( i < fillSize ) {
    	  
        ConnectionObject co = new ConnectionObject();
        Class.forName( m_JDBCDriver );
		co.con  = DriverManager.getConnection( m_JDBCConnectionURL,m_User,m_Password);

        // Do some sanity checking on the first connection in
        // the pool
        if (m_pool.size() == 0) {

          // Get the maximum number of simultaneous connections
          // as reported by the JDBC driver
          java.sql.DatabaseMetaData md = co.con.getMetaData();
          m_MaxConnections = md.getMaxConnections();
        }

        // Give a warning if the size of the pool will exceed
        // the maximum number of connections allowed by the
        // JDBC driver
        if ((m_MaxConnections > 0) &&
            (( m_pool.size()+ m_pool_inuse.size() ) > m_MaxConnections)) {
        	EliteLogger.connectionPoolLog.debug(MODULE+"WARNING: Size of pool will exceed safe maximum of " +m_MaxConnections);
        }
        // Clear the in use flag
        co.inUse = false;
        // Set the last access time
        touch(co);

        co.id = ( m_pool.size()+ m_pool_inuse.size() );
        m_pool.add(co);
        logStatistics();
        
        i++;
      }
    }

  /**
    * <p>Find the given connection in the pool
    *
    * @return Index into the pool, or -1 if not found
    */
  private int find(java.sql.Connection con){
      int index = -1;
      // Find the matching Connection in the pool
      if ((con != null) && (m_pool != null)){
        for (int i=0; i<m_pool.size(); i++){
			ConnectionObject co = (ConnectionObject)
            m_pool.get(i);
			if (co.con == con) {
				index = i;
				break;
			}
        }
      }
      return index;
    }

  /**
    * <p>Called by the timer each time a clock cycle expires.
    * This gives us the opportunity to timeout connections
    */
  public synchronized void TimerEvent(Object object){
	  
      EliteLogger.connectionPoolLog.debug(MODULE+"Time event started.");
	  
      // No pool means no work
      if (m_pool == null) {
        return;
      }

      // Get the current time in milliseconds
      long now = System.currentTimeMillis();

      /** Remove any connections that are no longer open
       * This Check is not required for the org.gjt.mm.mysql.Driver
       * as the driver itself checks for connection sanity.
       * 
       * More than that this WAS CAUSING A DEADLOCK LIKE SITUATION
       * WHICH STOPPED THE CONNECTION POOL FROM SERVING ANY CONNECTIONS.
       * 
       * So this check should not be made for the org.gjt.mm.mysql.Driver
       * 
       * Should be reviewed for all other drivers
       */
	  
      EliteLogger.connectionPoolLog.debug(MODULE+"["+connectionManagerIdentity+"] Looking up for timed out & closed connections... ");
      
      // Remove if any connection is null or already closed
      if ( m_pool != null && m_pool.size() > 0 ) {
	      for (int i = m_pool.size() - 1; i >= 0; i--) {
	    	  ConnectionObject co = (ConnectionObject)m_pool.get(i);
			  // If the connection is not in use and it has not been
			  // used recently, remove it
			  if (!co.inUse) {
				  try{
					 // Check for any expired connections and remove them
					  if ( m_ConnectionTimeout > 0 &&  (co.lastAccess + m_ConnectionTimeout < now ) ) {
						  removeFromPool(i);
						  EliteLogger.connectionPoolLog.debug(MODULE+"Time Out "+i+ " : "+co);
						  EliteLogger.connectionPoolLog.debug(MODULE+"Connection Timeout : "+m_ConnectionTimeout);
						  EliteLogger.connectionPoolLog.debug(MODULE+"Timestamp [ Last : "+co.lastAccess+", Current : "+now+", Idle : "+(now-co.lastAccess)+" ] ");
					  // Looking up for closed connections...
					  }else if (!isMysql){
						  // If the connection is closed, remove it from the pool
						 if (co.con == null || co.con.isClosed()) {
							 EliteLogger.connectionPoolLog.debug(MODULE+"Connection is closed unexpectedly, so removing from pool : "+co);
							 removeFromPool(i);
						 }
					  }
				  }catch (Exception ex) {
					  EliteLogger.connectionPoolLog.debug(MODULE+"Exception in TimerEvent() : "+ex,ex);
				  }
			  }
	      } // For loop ends here
      }
      
      // Remove if any connection is null or already closed
      if ( m_pool_inuse != null && m_pool_inuse.size() > 0 ) {
    	try {  
	        Collection connectionObjects = m_pool_inuse.keySet();
	        // Loop through the pool and remove null or closed connections
	        for (Iterator iterator = connectionObjects.iterator(); iterator.hasNext(); ) {
	        	Connection connection = (Connection)iterator.next();
	        	if ( connection == null || connection.isClosed() ){
		        	ConnectionObject co = ( ConnectionObject ) m_pool_inuse.get(connection);
		        	EliteLogger.connectionPoolLog.debug(MODULE+"Connection (In-use) is closed unexpectedly, so removing from pool : "+co);
		        	iterator.remove();
	        	}
	        }
    	}catch (Exception e) {
    		EliteLogger.connectionPoolLog.error(MODULE+"["+connectionManagerIdentity
    			+"] Exception in TimerEvent() while looping through in-use connections : " + e,e);
		}
      }
      
      EliteLogger.connectionPoolLog.debug(MODULE+"Looking to fill-up pool if required");
      // Now ensure that the pool is still at it's minimum size
      try {
        if (m_pool != null) {
          if ( ( m_pool.size() + m_pool_inuse.size() ) < m_ConnectionPoolSize ) {
            fillPool(m_ConnectionPoolSize-(m_pool.size()+m_pool_inuse.size()));
          }
        }
      }catch (Exception ex) {
		  EliteLogger.connectionPoolLog.error(MODULE+"["+connectionManagerIdentity+"] Exception in TimerEvent() while filling pool : " + ex,ex);
      }
      
	  logStatistics();
	  EliteLogger.connectionPoolLog.debug(MODULE+"Time event ended.");
    }

  /**
    * <p>Sets the last access time for the given ConnectionObject
    */
  private void touch(ConnectionObject co){
      if (co != null) {
        co.lastAccess = System.currentTimeMillis();
      }
  }

  /**
    * <p>Trace the given string
    */
  private void trace(String s){
      EliteLogger.connectionPoolLog.debug(MODULE+s);
  }
  
  void logStatistics(){
	  EliteLogger.connectionPoolLog.debug(MODULE+"Statistics [ Max : "+m_ConnectionPoolMax
	      +", Total : "+( m_pool.size()+ m_pool_inuse.size() )
	      +", In-use : "+m_pool_inuse.size()
	      +", Available : "+m_pool.size()+" ].");
	  
  }
}

// This package-private class is used to represent a single
// connection object

class ConnectionObject{
  
	// The id column
	public int id;
	
	// The JDBC Connection
	public java.sql.Connection con;

	// True if this connection is currently in use
	public boolean inUse;

	// The last time (in milliseconds) that this connection was used
	public long lastAccess;

	// The number of times this connection has been used
	public int useCount;

	public static final String MODULE = "[ConnectionObject] ";
	
	/**
    * <p>Determine if the connection is available
    *
    * @return true if the connection can be used
	*/
 
  public boolean isAvailable(){
	  
      boolean available = false;
      
      try {
        // To be available, the connection cannot be in use
        // and must be open
        if (con != null) {
			/**
			 * If the database is Mysql
			 * then should not check whether the connection is closed or not
			 * because the driver itself checks whether the connection are 
			 * open or not
			 */
          if (!inUse && (ConnectionPool.isMysql || !con.isClosed())) {
            available = true;
          }
        }
      }catch(Exception ex) {
    	  EliteLogger.connectionPoolLog.error(MODULE+ "Exception in isAvailable() for "+con+" : "+ex,ex); 
      }
      return available;
    }
	
	/**
    * <p>Convert the object contents to a String
    */
	public String toString(){
      //return "Connection [ Id="+id+", Object="+con+", inUse="+inUse+", lastAccess="+lastAccess+", useCount="+useCount+" ] ";
	  return "connection [ Id="+id+", inUse="+inUse+", lastAccess="+lastAccess+", useCount="+useCount+" ] ";
    }
}
