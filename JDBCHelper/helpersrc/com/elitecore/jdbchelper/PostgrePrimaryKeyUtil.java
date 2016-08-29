package com.elitecore.jdbchelper;

public class PostgrePrimaryKeyUtil extends PrimaryKeyUtil
{
	public int getNextIntPrimaryKey(String s, String s1)
    {
		return getIntPrimaryKey(s, s1);
    }

	public String getNextPrimaryKey(String s, String s1)
    {
		return getPrimaryKey(s, s1);
    }	 
	public static int getIntPrimaryKey(String s, String s1)
	{
		return getNextKey(s);
	}
	public static String getPrimaryKey(String s, String s1)
    {
        String s2 = null;
        return s2;
    }
}