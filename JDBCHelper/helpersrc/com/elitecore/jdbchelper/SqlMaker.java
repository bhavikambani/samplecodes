package com.elitecore.jdbchelper;

import java.util.StringTokenizer;
import java.util.Date;

import com.elitecore.utilities.*;

public abstract class SqlMaker{
	
    public static final String DEFAULT = null;
	
	public abstract String makeQueryString(Object value);
	public abstract String makeQueryString(Date value);
	public abstract String makeQueryString(String value);
	public abstract String makeQueryString(Integer value);
	public abstract String makeQueryString(int value);
	public abstract String makeQueryString(char value);
	public abstract String makeQueryString(double value);
	
	public static String makeString(String tempString){
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
  
	public static String makeString( Integer value ){
		if( value == null )
			return DEFAULT;
		else if( value.equals( "<None>" ) )
			return DEFAULT;
		else{
			return ( value.toString() );
		}
	}

	public static String makeString( Date tempDate ){
		if( tempDate == null )
			return DEFAULT;
		else{
			//String tempString = DateDifference.dateToString(tempDate,"yyyy-MM-dd");
			String tempString = tempDate.toString();
			return ( "\'" + tempString.trim() + "\'" );
		}
	}

	public static String makeString( Object value ){
		String retString = null;
		if (value instanceof Integer){
			retString = makeString((Integer)value);
		}else if(value instanceof String){
			retString = makeString((String)value);
		}else if (value instanceof java.util.Date){
			retString = makeString((java.util.Date)value);
		}
		return retString;
		
	}

	public static String makeString( int value ){
		if( value <= 0 )
			return DEFAULT;
		else{
			return ( String.valueOf(value) );
		}
	}
	public static String makeString( char temp ){
		if ( Character.isWhitespace(temp)|| Character.isISOControl(temp) )
		    return DEFAULT;
		else
		    return ( "\'" + temp + "\'" );
	}

	public static String addWhere(String sqlString,String logicalOperator,String fieldName,
                           String operand,String fieldValue ){
        String space = " ";
        String quotes = "\'";
        sqlString = sqlString+space+logicalOperator+space+fieldName+space+operand+space+quotes+fieldValue+quotes+space;
        return sqlString;
    }
    
	public static boolean compare( String oldString, String newString ) {
		if( oldString == null && newString != null )		// THIS IS MOST USEFUL CASE
			return true;
		else if( oldString != null && newString == null )
			return false;
		else if( oldString == null && newString == null )
			return false;
		else return ( !((oldString.trim()).equals( newString.trim() )) );
    }

	public static String getTruncatedString(String targetString, int noOfChars, String postFix){ 
		String truncatedString = targetString;
		if (targetString.length() > noOfChars ) { 
			truncatedString = targetString.substring(0,(noOfChars - postFix.length() ));
			truncatedString = truncatedString + postFix;
		}
		return truncatedString;
	}

}