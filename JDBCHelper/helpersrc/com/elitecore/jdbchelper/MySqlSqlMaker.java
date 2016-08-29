package com.elitecore.jdbchelper;

import java.util.StringTokenizer;
import java.util.Date;
import java.util.*;
import java.text.*;

import com.elitecore.utilities.*;

public class MySqlSqlMaker extends SqlMaker{
    public static final String DEFAULT = null;
	
	public static String makeString( String tempString ){
		if( tempString == null )
			return DEFAULT;
		else if( tempString.equals( "<None>" ) )
			return DEFAULT;
		else if( tempString.trim().equals( "" ) )
			return DEFAULT;
		else{
			StringTokenizer st = new StringTokenizer( tempString,"'");
			tempString = st.nextToken();
			
			while( st.hasMoreTokens()){
				tempString +="''" +st.nextToken();
			}
			return ( "\'" + tempString.trim() + "\'" );
		}
	}
  
	public static String makeString( Date tempDate ){
		if( tempDate == null )
			return DEFAULT;
		else{
			String tempString = dateToString(tempDate,"yyyy-MM-dd");
			return ( "\'" + tempString.trim() + "\'" );
		}
	}

	public static String dateToString(java.util.Date date, String format){
		String dateString = null;
		if (date != null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			dateString = sdf.format(date);
		}
		return dateString;
	}	

	public String makeQueryString(Object value){
		return makeString((Object)value);
	}
	public String makeQueryString(String value){
		return makeString((String)value);
	}
	public String makeQueryString(Integer value){
		return makeString((Integer)value);
	}
	public String makeQueryString(Date value){
		return makeString((Date)value);
	}
	public String makeQueryString(int value){
		return makeString((int)value);
	}
	public String makeQueryString(double value){
		return makeString((double)value);
	}
	public String makeQueryString(char value){
		return makeString((char)value);
	}
	
	public static String makeString( Integer value ){
		if( value == null )
			return DEFAULT;
		else if( value.equals( "<None>" ) )
			return DEFAULT;
		else{
			return ( value.toString() );
		}
	}

	public static String makeString( Object value ){
		//System.out.println("MAKE STRING CALLED: " + value.getClass().getName() + " : " + value);
		if (value instanceof Integer){
			value =  (Integer)value;
			return makeString((Integer)value);
		}else if(value instanceof String){
			value =  (String)value;
			return makeString((String)value);
		}else if (value instanceof Date){
			value = (Date)value;
			return makeString((Date)value);
		}else{
			return null;
		}
	}

	/**
	 * This method returns a string value for int value
	 * A very bad work around  done to return null 
	 * if the value is -1 as the underlying generated code
	 */
	public static String makeString( int value ){
		if(value == -10001){
			return DEFAULT;
		}else{
			return ( String.valueOf(value) );
		}
	}
	
	public static String makeString( double value ){
		return ( String.valueOf(value) );
	}
	
	public static String makeString( float value ){
		return ( String.valueOf(value) );
	}
	
	public static String makeString( char temp ){
		if ( Character.isWhitespace(temp)|| Character.isISOControl(temp) )
		    return DEFAULT;
		else
		    return ( "\'" + temp + "\'" );
	}
	
}