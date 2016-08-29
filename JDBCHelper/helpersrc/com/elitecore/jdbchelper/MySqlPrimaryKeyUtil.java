package com.elitecore.jdbchelper;


import com.elitecore.jdbchelper.PrimaryKeyUtil;
import com.elitecore.log.*;

import java.sql.*;


public class MySqlPrimaryKeyUtil extends PrimaryKeyUtil{
	
	public static int getIntPrimaryKey(String tablename,String fieldname){
		return PrimaryKeyUtil.getNextKey(tablename);
	}
	public static String getPrimaryKey(String tablename,String fieldname){
		String newId = null;
		return newId;
	}
	
	public int getNextIntPrimaryKey(String tablename,String fieldname){
		return getIntPrimaryKey(tablename,fieldname);
	}
	
	public String getNextPrimaryKey(String tablename,String fieldname){
		return getPrimaryKey(tablename,fieldname);
	}
}