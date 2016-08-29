package com.elitecore.log4j;
// Here is a code example to configure the JDBCAppender without a configuration-file

import java.sql.Driver;
import java.sql.DriverManager;

import com.elitecore.log4j.jdbchandler.JDBCAppender;
import com.elitecore.log4j.jdbchandler.JDBCIDHandler;
import com.elitecore.log4j.jdbchandler.LogType;
import com.elitecore.utilities.AuditInfoReader;

public class Log4JHandler
{
	// Create a category instance for this class
	//static Category cat = Category.getInstance(Log4JHandler.class.getName());
	static JDBCAppender ja = null;
	 
	static{
		// Ensure to have all necessary drivers installed !
		try
		{
			Driver d = (Driver)(Class.forName(AuditInfoReader.DATABASEDRIVER).newInstance());
			DriverManager.registerDriver(d);
			
			// A JDBCIDHandler
		MyIDHandler idhandler = new MyIDHandler();

		// Create a new instance of JDBCAppender
		ja = new JDBCAppender();

		// Set options with method setOption()
		ja.setOption(JDBCAppender.CONNECTOR_OPTION, AuditInfoReader.CONNECTOROPTION);
		ja.setOption(JDBCAppender.URL_OPTION, AuditInfoReader.JDBCURL);
		ja.setOption(JDBCAppender.USERNAME_OPTION, AuditInfoReader.USERNAME);
		ja.setOption(JDBCAppender.PASSWORD_OPTION, AuditInfoReader.PASSWORD);

		ja.setOption(JDBCAppender.TABLE_OPTION, AuditInfoReader.TABLE);

		ja.setLogType("logid", LogType.EMPTY, "");
		ja.setLogType("action", LogType.MSG, "");
		}
		catch(Exception e){System.out.println("Exception in driver registration:"+e);}
	}
	
	public static JDBCAppender getAppender(){
		return ja;
	}
		
}
// Implement a sample JDBCIDHandler
class MyIDHandler implements JDBCIDHandler
{
	private static long id = 0;

	public synchronized Object getID()
   {
		return new Long(++id);
   }
}

