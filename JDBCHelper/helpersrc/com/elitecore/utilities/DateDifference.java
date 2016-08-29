package com.elitecore.utilities;

import java.util.*;
import java.text.*;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DateDifference{
	private static final int MSINSECOND = 1000;
	private static final int MSINMINUTE = 60000;//1000*60
	private static final int MSINHOUR = 3600000;//1000*60*60
	private static final int MSINDAY = 86400000;//1000*60*60*24
	private static SimpleDateFormat defaultDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat indianFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat reportFormat = new SimpleDateFormat("MMM d,yyyy");
	private static SimpleDateFormat mysqlFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	
	static{
		String defaultDateFormatStr = System.getProperty("defaultDateFormat"); 
		System.out.println("defaultDateFormatStr::" + defaultDateFormatStr);
		if(defaultDateFormatStr != null){
			defaultDateFormat = new SimpleDateFormat(defaultDateFormatStr);
		}
	}
	
	public static void setDefaultDateFormat( String ddfs ){
		String defaultDateFormatStr = System.getProperty("defaultDateFormat"); 
		System.out.println("defaultDateFormatStr::" + defaultDateFormatStr);
		if(defaultDateFormatStr != null){
			defaultDateFormat = new SimpleDateFormat(defaultDateFormatStr);
		}
	}
		
	public static double getDaysDifference( Date startDate, Date endDate ){
		long startMS = startDate.getTime( );
		long endMS = endDate.getTime( );
		long resultMS = startMS-endMS;
		double days = ( double )( resultMS / DateDifference.MSINDAY );
		return days;
	}

	public static double getMinutesDifference(Date startDate,Date endDate){
		long startMS = startDate.getTime();
		long endMS = endDate.getTime();
		long resultMS = startMS-endMS;
		double minutes = (double)(resultMS / DateDifference.MSINMINUTE);
		return minutes;
	}	
	public static double getHoursDifference(Date startDate,Date endDate){
		long startMS = startDate.getTime();
		long endMS = endDate.getTime();
		long resultMS = startMS - endMS;
		double hours = (double)(resultMS / DateDifference.MSINHOUR);
		return hours;
	}
	
	
	public static Date stringToDate(String dateString){
		Date date = null;
		if (dateString != null){
			defaultDateFormat.setLenient(true);
			ParsePosition pos = new ParsePosition(0);
			date = defaultDateFormat.parse(dateString, pos);
		}
		return date;
	}
	
	public static Date stringToDate(String dateString,String format){
		Date date = null;
		if (dateString != null){
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			formatter.setLenient(true);
			ParsePosition pos = new ParsePosition(0);
			date = formatter.parse(dateString, pos);
		}
		return date;
	}	

	public static Date stringToDate(String dateString, SimpleDateFormat formatter){
		Date date = null;
		if (dateString != null){
			formatter.setLenient(true);
			ParsePosition pos = new ParsePosition(0);
			date = formatter.parse(dateString, pos);
		}
		return date;
	}	

	public static String dateToString(java.util.Date date){
		String dateString = null;
		if (date != null){
			dateString = defaultDateFormat.format(date);
		}
		return dateString;
	}	

	public static String dateToMysqlString(java.util.Date date){
		String dateString = null;
		if (date != null){
			dateString = mysqlFormat.format(date);
		}
		return dateString;
	}	

	public static String dateToTimeString(java.util.Date date){
		String dateString = null;
		if (date != null){
			dateString = timeFormat.format(date);
		}
		return dateString;
	}
	
	public static String dateToString(java.util.Date date, String format){
		String dateString = null;
		if (date != null){
			SimpleDateFormat defaultDateFormat = new SimpleDateFormat(format);
			dateString = defaultDateFormat.format(date);

		}
		return dateString;
	}	


	public static String changeStringFormat(String str){
		String retStr = null ;
		try{
			java.util.Date dt = stringToDate(str,indianFormat);
			retStr = dateToString(dt,"yyyy/MM/dd");
		}catch(Exception e){ 
			retStr = null ;
		}	
		return retStr ;
	} 
	
	public static String getReportFormatDate(String date){ 
		java.util.Date dt = stringToDate(date,indianFormat);
		return reportFormat.format(dt);
	}
	
	public static String getCalendarFormatString(String date){
		java.util.Date dt = stringToDate(date,indianFormat);
		return reportFormat.format(dt);
	}

		
	public static void main(String[] args){
	}
	
}