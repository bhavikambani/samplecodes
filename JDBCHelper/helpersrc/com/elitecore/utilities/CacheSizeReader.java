package com.elitecore.utilities;
/**
 * Copyright (c) 1999 eLiteCore Technologies Pvt. Ltd. All Rights Reserved.
*/
import java.util.*;
import java.io.*;
import com.elitecore.log.EliteLogger;


public class CacheSizeReader{
	
	private static ResourceBundle myresources;
	
	public static String FILENAME = "BeanCacheSize";
	public static int DEFAULT_SIZE = 10;
	
	static{
		initialize("BeanCacheSize");
	}

	public static void initialize(String propertyFile) throws MissingResourceException{
		
		FILENAME = propertyFile ;
        myresources = ResourceBundle.getBundle(FILENAME, Locale.getDefault());
	}

	public static String getParameter(String parmName) {
		String param = null;
		try{
			param = myresources.getString(parmName) ;
		}catch(Exception e){
			param = null;
			EliteLogger.genLog.error("Exception in getting parameter: " + parmName + " " + e,e);
		}
		if (param != null)
			return param.trim();
		else
			return param;
	}
	
	public static int getCacheSize(String param){
		int cacheSize = 10;
		String cacheValue = null;
		try{
			cacheValue = getParameter(param);
			if(cacheValue != null){
				cacheSize = Integer.parseInt(cacheValue);
			}
		}catch(Exception e){
			cacheSize = DEFAULT_SIZE;
		}
		return cacheSize;
	}
}