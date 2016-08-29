package com.elitecore.jdbchelper;

import com.elitecore.log.*;

import java.sql.*;


public abstract class PrimaryKeyUtil{
	
	public static int getIntPrimaryKey(String tablename,String fieldname){
		return 1;
	}
	
	public static String getPrimaryKey(String tablename,String fieldname){
		String newId = "parent";
		return newId;
	}
	
	public synchronized static int getNextKey(String tablename){
		SqlReader sqlReader = null;
		ResultSetWrapper rsw = null;
		int newId = -1;
		try{
			EliteLogger.connectionPoolLog.info("TABLE NAME:"+tablename);
			if(tablename != null){
				tablename = tablename.toLowerCase();
				tablename = tablename + "_seq";
			}
			EliteLogger.connectionPoolLog.info("TABLE WITH SEQ NAME:"+tablename);
		
			String idQuery = "select nextval(" + SqlMaker.makeString(tablename) +") as nextid";
			EliteLogger.connectionPoolLog.info("SEQ QUERY:"+idQuery);
		
		
			sqlReader = new SqlReader(false);
			rsw = sqlReader.getInstanceResultSetWrapper(idQuery);
			if (rsw.next()){
				newId = rsw.getInt("nextid");
			}
		}catch(Exception e){
			/*try{
				conn.rollback();
			}catch(Exception sex){}
			*/
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
	
	
	public abstract int getNextIntPrimaryKey(String tablename,String fieldname);
	public abstract String getNextPrimaryKey(String tablename,String fieldname);
	
	/*
	public static void main(String[] args){
		EliteLogger.logonserverLog.debug("LIVE: " + PrimaryKeyUtil.getNextKey("tblcmaaliveuser"));
		EliteLogger.logonserverLog.debug("Connected: " + PrimaryKeyUtil.getNextKey("tblcmaaconnecteduser"));
	}
	*/
}