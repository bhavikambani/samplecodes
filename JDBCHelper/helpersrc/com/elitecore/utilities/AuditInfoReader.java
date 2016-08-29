package com.elitecore.utilities;
/**
 * Copyright (c) 1999 eLiteCore Technologies Pvt. Ltd. All Rights Reserved.
*/
import java.util.*;
import java.io.*;
import com.elitecore.log.*;


public class AuditInfoReader{
	
	private static ResourceBundle myresources;
	
	public static String FILENAME = "DataBaseLogger";
	
	public static String DATABASEDRIVER = null ;
	public static String CONNECTOROPTION = null ;
	public static String JDBCURL = null;
	public static String USERNAME = null ;
	public static String PASSWORD = null ;
	public static String TABLE = null ;
	public static String PRIORITY = null;
		
	static{
		initialize("DataBaseLogger");
	}

	public static void initialize(String propertyFile) throws MissingResourceException{
		
		FILENAME = propertyFile ;
		String holder = null;
        myresources = ResourceBundle.getBundle(FILENAME, Locale.getDefault());
				
		CONNECTOROPTION = getParameter("connectoroption");
		JDBCURL = getParameter("jdbcurl");
		USERNAME = getParameter("username");
		PASSWORD = getParameter("password");
		TABLE = getParameter("table");
		DATABASEDRIVER = getParameter("databasedriver");
		PRIORITY = getParameter("priority");
		
	}

	private static String getParameter(String parmName) {
		String param = null;
		try{
			param = myresources.getString(parmName) ;
		}catch(Exception e){
			EliteLogger.genLog.error("Exception in getting parameter: " + parmName + " " + e,e);
			param = null;
		}
		if (param != null)
			return param.trim();
		else
			return param;
	}
	
}