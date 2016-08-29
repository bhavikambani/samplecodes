package com.elitecore.jdbchelper;

import java.sql.Connection;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.elitecore.jdbchelper.jdbc.ConnectionPool;
import com.elitecore.log.EliteLogger;

public class ConnectionManager extends Object{
    /**
     * The default instance of the Connection Manager
     */
	private static ConnectionManager instance=null;
    
    /**
     * The List of the Connection Manager instances enable as defined in the 
     * ConnectionManager.properties
     */	
	private static Hashtable connectionManagerList = null;
	
    /**
     * The identity of the Connection Manager.
     * This is the key defined in the ConnectionManager.properties
     */	
	private String connectionManagerIdentity = "default";

	private ConnectionPool m_connectionPool;
	private SqlMaker sqlMaker = null;
	private PrimaryKeyUtil primaryKeyUtil = null;
	
	public static final String MODULE = "[ConnectionManager] ";
											 
	static{
		ConnectionManager.createConnectionManagers();
	}
	
	/**
	 * Default constructor, loads "ConnectionPool.cfg"
	 */
	public ConnectionManager(){
        init("ConnectionPool.cfg");
    }
	
	public ConnectionManager(String propertiesName){
        init(propertiesName);
    }
	

	/**
	 * Read the "ConnectionManager.properties" to create Connection Managers as
	 * listed in databaseList.
	 * If the configuration file name is "ConnectionPool.cfg" it is treated as a default 
	 * instance.
	 * If no connectionmanager.properties is present then the default instance will be as 
	 * defined in the configuration file "ConnectionPool.cfg"
	 * 
	 */
	private static synchronized  void createConnectionManagers(){
		ResourceBundle connectionManagerProperties = null;
		try{
			/**
			 * Gets the the database list from the ConnectionManager.properties file
			 */
			connectionManagerProperties = ResourceBundle.getBundle("ConnectionManager");
		}catch(MissingResourceException mre){
			EliteLogger.connectionPoolLog.error(MODULE+ "ConnectionManager.properties not found : " + mre,mre);
			connectionManagerProperties = null;
		}
		if(connectionManagerProperties != null){
			connectionManagerList = new Hashtable();
			StringTokenizer databaseList = new StringTokenizer(connectionManagerProperties.getString("databaseList"),",");
			while(databaseList.hasMoreElements()){
				String databaseName = (String)databaseList.nextElement();
				String databaseNameProperties = (String)connectionManagerProperties.getString(databaseName);
				
				ConnectionManager connectionManager = new ConnectionManager(databaseNameProperties);
				connectionManager.connectionManagerIdentity = databaseName;
				connectionManager.m_connectionPool.setConnectionManagerIdentity(databaseName);
				connectionManagerList.put(databaseName,connectionManager);
				
				if(databaseNameProperties.equals("ConnectionPool.cfg")){
					instance = connectionManager;
				}
			}
		}
		if(instance == null){
			instance = new ConnectionManager();
		}
	}

	/**
	 * Initializes the ConnectionPool as defined in the properties file
	 */
	public void init(String propertiesName){
		EliteLogger.connectionPoolLog.info(MODULE+"Initializing ConnectionManager...");
		// Create our connection pool
		m_connectionPool = new com.elitecore.jdbchelper.jdbc.ConnectionPool();
		// Initialize the connection pool. This will start all
		// of the connections as specified in the connection
		// pool configuration file
		try {
			m_connectionPool.initialize(propertiesName);
		}catch (Exception ex) {
			EliteLogger.connectionPoolLog.error(MODULE+"["+connectionManagerIdentity+"] Exception in init() : " + ex,ex);
		}
	}

	/**
	 * Gets the shared instance as configured in the ConnectionManager.properties file
	 * if the database name is null then it will give the default Connection Manager
	 * with properties as defined in ConnectionPool.cfg
	 */
	public static synchronized ConnectionManager getSharedInstance(String databaseName){
		ConnectionManager cm = null;
		if(databaseName == null){
			cm = instance;
		}else{
			cm = (ConnectionManager)ConnectionManager.connectionManagerList.get(databaseName);
		}
		return cm;
    }
	
    /**
     * Gets the default Connection Manager
	 * with properties as defined in ConnectionPool.cfg
     */
	public static synchronized ConnectionManager getSharedInstance(){
		return ConnectionManager.getSharedInstance(null);
    }

	public SqlMaker getSqlMaker(){
		try{
			if(sqlMaker == null){
				sqlMaker = (SqlMaker)Class.forName(m_connectionPool.m_SqlMaker).newInstance();
			}
		}catch(Exception e){
			EliteLogger.connectionPoolLog.error(MODULE+ connectionManagerIdentity + ":::COULD NOT LOAD SQLMAKER CLASS: " + e,e);
		}
		return sqlMaker;
	}
	
	public PrimaryKeyUtil getPrimaryKeyUtil(){
		try{
			if(primaryKeyUtil == null){
				primaryKeyUtil = (PrimaryKeyUtil)Class.forName(m_connectionPool.m_PrimaryKeyUtil).newInstance();
			}
		}catch(Exception e){
			EliteLogger.connectionPoolLog.error(MODULE+ connectionManagerIdentity + ":::COULD NOT LOAD PRIMARYKEYUTIL CLASS: " + e,e);
		}
		return primaryKeyUtil;
	}
	
    public Connection getConnection(){

    	boolean attempt = true;
        int i=1;
        int maxConnectionAttempts=1;
        
        try {
        	maxConnectionAttempts = m_connectionPool.getConnectionAttempts();
        	
	        while (attempt) {
				Connection con = m_connectionPool.getConnection();
				i++;
				if (con == null){
				    EliteLogger.connectionPoolLog.error(MODULE+"Error in getting Connection");
				    if (i <= maxConnectionAttempts){
				    	EliteLogger.connectionPoolLog.error(MODULE+"["+connectionManagerIdentity+"] Trying again at attempt "+ i);
				    	continue;
				    }
				}
				EliteLogger.connectionPoolLog.debug(MODULE+"["+connectionManagerIdentity+"] Got connection : "+con);
				//new Exception().printStackTrace(System.out);
				return con;
	        }
        }catch(Exception e){
        	EliteLogger.connectionPoolLog.error(MODULE+"["+connectionManagerIdentity+"] Exception in getConnection() : "+e,e);
        }
        return null;
    }
	// we can rely on the close method as connection validity is checked for both
    // accounts
    public void close(Connection con){
    	try{
    		EliteLogger.connectionPoolLog.debug(MODULE+"["+connectionManagerIdentity+"] Closing connection : " + con);
	        m_connectionPool.close(con);
    	}catch(Exception e){
    		EliteLogger.connectionPoolLog.error(MODULE+"["+connectionManagerIdentity+"] Exception in close() for connection "+con+" : "+e,e);
    	}
    }
    
	public void destroy(){
      // Tear down our connection pool if it was created
      if (m_connectionPool != null) {
        m_connectionPool.destroy();
      }
    }	
}