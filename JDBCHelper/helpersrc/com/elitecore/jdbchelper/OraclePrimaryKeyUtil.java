package com.elitecore.jdbchelper;

import com.elitecore.jdbchelper.*;

import com.elitecore.log.EliteLogger;
			   
import java.sql.*;


public class OraclePrimaryKeyUtil extends PrimaryKeyUtil{
	
	
	public static int getIntPrimaryKey(String tablename,String fieldname){
		//int newId = -1;
		return getNextKey(tablename);
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
	
	public synchronized static int getNextKey(String tablename){
		SqlReader sqlReader = null;
		ResultSetWrapper rsw = null;
		int newId = -1;
		String seqName = tablename + "_seq";
		tablename = tablename.toLowerCase();
		String idQuery = "select " + seqName + ".nextval nextval from dual";
		EliteLogger.connectionPoolLog.debug("PRIMARY KEY QUERY FOR " + tablename + " " + idQuery);
		String updateIdQuery = null;
		try{
			sqlReader = new SqlReader(false);
			rsw = sqlReader.getInstanceResultSetWrapper(idQuery,5);
			if (rsw.next()){
				newId = rsw.getInt("nextval");
			}
			EliteLogger.connectionPoolLog.debug("PRIMARY KEY VALUE: " + newId);
		}catch(Exception e){
			EliteLogger.connectionPoolLog.error("Exception in getting next primary key: " + e,e);
		}finally{
			try{
				sqlReader.close();
				rsw.close();
			}catch(Exception e){
				EliteLogger.connectionPoolLog.error("EXCEPTION IN CLOSING CONNECTION: " + e,e);
			}
		}
		return newId;
	}
	/*
	public static void main(String[] args){
		EliteLogger.connectionPoolLog.debug("LIVE: " + PrimaryKeyUtil.getNextKey("tblcmaaliveuser"));
		EliteLogger.connectionPoolLog.debug("Connected: " + PrimaryKeyUtil.getNextKey("tblcmaaconnecteduser"));
	}*/
}