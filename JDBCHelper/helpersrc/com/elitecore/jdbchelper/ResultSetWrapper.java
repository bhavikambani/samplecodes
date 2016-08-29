package com.elitecore.jdbchelper;
import com.elitecore.log.*;

import java.math.BigDecimal;
import java.io.*;
import java.sql.*;
import java.util.Map;
import java.util.Calendar;
import java.net.*;

public class ResultSetWrapper implements ResultSet{
	public Connection connection = null; 
	private Statement statement = null;
	private ResultSet resultSet = null;
	private boolean update = false;
	private int resultCount = -1;
	  
	private long createTime = 0;
	
	private boolean closeConnection = false;
	
	/**
	 * Take care to close the ResultSetWrapper
	 * Otherwise it will create garbage collection problems.
	 */	
	
	public ResultSetWrapper(String query)	
		throws java.sql.SQLException,java.lang.NullPointerException{
		closeConnection = true;
		connection = ConnectionManager.getSharedInstance().getConnection();
		//statement = connection.createStatement();
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
												ResultSet.CONCUR_UPDATABLE);
		//statement.setQueryTimeout(300);
		resultSet = statement.executeQuery( query );
		createTime = System.currentTimeMillis();
		EliteLogger.connectionPoolLog.debug("Creating ResultSetWrapper: " + createTime + " " + this.connection);
	}

	public ResultSetWrapper(Connection connection,
							Statement statement,
							ResultSet resultSet){
		this.connection = connection;
		this.statement = statement;
		this.resultSet = resultSet;
		update = false;
		resultCount = -1;
		createTime = System.currentTimeMillis();
		closeConnection = true;
		EliteLogger.connectionPoolLog.debug("Creating ResultSetWrapper: " + createTime + " " + this.connection);
	}
	
	public ResultSetWrapper(Connection connection,
							Statement statement,
							ResultSet resultSet,
							boolean isUpdate,
							int resultCount){
		this.connection = connection;
		this.statement = statement;
		this.resultSet = resultSet;
		this.update = isUpdate;
		this.resultCount = resultCount;
		closeConnection = true;
	}

	public boolean isUpdateResult(){
		return update;
	}
	
	
	public ResultSet getResultSet(){
		return resultSet;
	}
	
	public int getResultCount(){
		return resultCount;
	}
	

	public String toString(){
		return connection.toString()+ ":" + createTime;
	}
		
	public boolean next() throws SQLException {
		return resultSet.next();
	}
	public void closeResultSet() throws SQLException{
		resultSet.close();
	}
	public boolean wasNull()
	throws SQLException{
		return resultSet.wasNull();
	}
	public String getString(int columnIndex)throws SQLException{
		return resultSet.getString(columnIndex);
	}
	public boolean getBoolean(int columnIndex)throws SQLException{
		return resultSet.getBoolean(columnIndex);
	}
	public byte getByte(int columnIndex)throws SQLException{
		return resultSet.getByte(columnIndex);
	}
	public short getShort(int columnIndex)throws SQLException{
		return resultSet.getShort(columnIndex);
	}
	public int getInt(int columnIndex)throws SQLException{
		return resultSet.getInt(columnIndex);
	}
	public long getLong(int columnIndex) throws SQLException{
		return resultSet.getLong(columnIndex);
	}
	public float getFloat(int columnIndex)throws SQLException{
		return resultSet.getFloat(columnIndex);
	}
	public double getDouble(int columnIndex) throws SQLException{
		return resultSet.getDouble(columnIndex);
	}
	public BigDecimal getBigDecimal(int columnIndex,int scale)throws SQLException{
		return resultSet.getBigDecimal(columnIndex,scale);
	}
	public byte[] getBytes(int columnIndex)throws SQLException{
		return resultSet.getBytes(columnIndex);
	}
	public Date getDate(int columnIndex)throws SQLException{
		return resultSet.getDate(columnIndex);
	}
	public Time getTime(int columnIndex)
		throws SQLException{
		return resultSet.getTime(columnIndex);
	}
	public Timestamp getTimestamp(int columnIndex)
		throws SQLException{
		return resultSet.getTimestamp(columnIndex);
	}
	public InputStream getAsciiStream(int columnIndex)
		throws SQLException{
		return resultSet.getAsciiStream(columnIndex);	
	}
	public InputStream getUnicodeStream(int columnIndex)
		throws SQLException{
		return resultSet.getUnicodeStream(columnIndex);
	}
	public InputStream getBinaryStream(int columnIndex)
		throws SQLException{
		return resultSet.getBinaryStream(columnIndex);
	}
	public String getString(String columnName)
		throws SQLException{
		return resultSet.getString(columnName);
	}
	public boolean getBoolean(String columnName)
		throws SQLException{
		return resultSet.getBoolean(columnName);
	}
	public byte getByte(String columnName)
		throws SQLException{
		return resultSet.getByte(columnName);
	}
	public short getShort(String columnName)
		throws SQLException{
		return resultSet.getShort(columnName);
	}
	public int getInt(String columnName)
		throws SQLException{
		return resultSet.getInt(columnName);
	}
	public long getLong(String columnName)
		throws SQLException{
		return resultSet.getLong(columnName);
	}
	public float getFloat(String columnName)
		throws SQLException{
		return resultSet.getFloat(columnName);
	}
	public double getDouble(String columnName)
		throws SQLException{
		return resultSet.getDouble(columnName);
	}
	public BigDecimal getBigDecimal(String columnName,int scale)
		throws SQLException{
		return resultSet.getBigDecimal(columnName,scale);
	}
	public byte[] getBytes(String columnName)
		throws SQLException{
		return resultSet.getBytes(columnName);
	}
	public Date getDate(String columnName)
		throws SQLException{
		return resultSet.getDate(columnName);
	}
	public Time getTime(String columnName)
		throws SQLException{
		return resultSet.getTime(columnName);
	}
	public Timestamp getTimestamp(String columnName)
		throws SQLException{
		return resultSet.getTimestamp(columnName);
	}
	public InputStream getAsciiStream(String columnName)
		throws SQLException{
		return resultSet.getAsciiStream(columnName);
	}
	public InputStream getUnicodeStream(String columnName)
		throws SQLException{
		return resultSet.getUnicodeStream(columnName);
	}
	public InputStream getBinaryStream(String columnName)
		throws SQLException{
		return resultSet.getBinaryStream(columnName);
	}
	public SQLWarning getWarnings()
		throws SQLException{
		return resultSet.getWarnings();
	}
	public void clearWarnings()
		throws SQLException{
		resultSet.clearWarnings();
	}
	public String getCursorName()
		throws SQLException{
		return resultSet.getCursorName();
	}
	public ResultSetMetaData getMetaData()
		throws SQLException{
		return resultSet.getMetaData();
	}
	public Object getObject(int columnIndex)
		throws SQLException{
		return resultSet.getObject(columnIndex);
	}
	public Object getObject(String columnName)
		throws SQLException{
		return resultSet.getObject(columnName);
	}
	public int findColumn(String columnName)
		throws SQLException{
		return resultSet.findColumn(columnName);
	}

	public void close() throws SQLException{
		if(resultSet != null){
			resultSet.close();
			resultSet = null;
		}
		if(statement != null){
			statement.close();
			statement = null;
		}
		if(closeConnection == true && connection !=null){
			ConnectionManager.getSharedInstance().close(connection);
			closeConnection = false;
		}
	}
	
	public void closeAll()	throws SQLException{
		if(resultSet != null){
			resultSet.close();
			resultSet = null;
		}
		if(statement != null){
			statement.close();
			statement = null;
		}
		if(this.closeConnection == true &&  connection != null ){
			ConnectionManager.getSharedInstance().close(connection);
		}
//		EliteLogger.connectionPoolLog.debug("CLOSING THE CONNECTION RSW this.close: " + this);
	}
	public Reader getCharacterStream(int columnIndex)throws SQLException{
		return resultSet.getCharacterStream(columnIndex);
	}
	public Reader getCharacterStream(String columnName)throws SQLException{
		return resultSet.getCharacterStream(columnName);
	}
	public BigDecimal getBigDecimal(int columnIndex)throws SQLException{
		return resultSet.getBigDecimal(columnIndex);
	}
	public BigDecimal getBigDecimal(String columnName)throws SQLException{
		return resultSet.getBigDecimal(columnName);
	}
	public boolean isBeforeFirst()throws SQLException{
		return resultSet.isBeforeFirst();
	}
	public boolean isAfterLast()throws SQLException{
		return resultSet.isAfterLast();
	}
	public boolean isFirst()throws SQLException{
		return resultSet.isFirst();
	}
	public boolean isLast()throws SQLException{
		return resultSet.isLast();
	}
	public void beforeFirst()throws SQLException{
		resultSet.beforeFirst();
	}
	public void afterLast()throws SQLException{
		resultSet.afterLast();
	}
	public boolean first()throws SQLException{
		return resultSet.first();
	}
	public boolean last()throws SQLException{
		return resultSet.last();
	}
	public int getRow()throws SQLException{
		return resultSet.getRow();
	}
	public boolean absolute(int row)throws SQLException{
		return resultSet.absolute(row);
	}
	public boolean relative(int rows)throws SQLException{
		return resultSet.relative(rows);
	}
	public boolean previous()throws SQLException{
		return resultSet.previous();
	}
	public void setFetchDirection(int direction)throws SQLException{
		resultSet.setFetchDirection(direction);
	}
	public int getFetchDirection()throws SQLException{
		return resultSet.getFetchDirection();
	}
	public void setFetchSize(int rows)throws SQLException{
		resultSet.setFetchSize(rows);
	}
	public int getFetchSize()throws SQLException{
		return resultSet.getFetchSize();
	}
	public int getType()throws SQLException{
		return resultSet.getType();
	}
	public int getConcurrency()throws SQLException{
		return resultSet.getConcurrency();
	}
	public boolean rowUpdated()throws SQLException{
		return resultSet.rowUpdated();
	}
	public boolean rowInserted()throws SQLException{
		return resultSet.rowInserted();
	}
	public boolean rowDeleted()throws SQLException{
		return resultSet.rowDeleted();
	}
	public void updateNull(int columnIndex)throws SQLException{
		resultSet.updateNull(columnIndex);
	}
	public void updateBoolean(int columnIndex,boolean x)throws SQLException	{
		resultSet.updateBoolean(columnIndex,x);
	}
	
	public void updateByte(int columnIndex,byte x)throws SQLException{
		resultSet.updateByte(columnIndex,x);
	}
	public void updateShort(int columnIndex,short x)throws SQLException{
		resultSet.updateShort(columnIndex,x);
	}
	public void updateInt(int columnIndex,int x)throws SQLException{
		resultSet.updateInt(columnIndex,x);
	}
	public void updateLong(int columnIndex,long x)throws SQLException{
		resultSet.updateLong(columnIndex,x);
	}
	public void updateFloat(int columnIndex,float x)throws SQLException{
		resultSet.updateFloat(columnIndex,x);
	}
	public void updateDouble(int columnIndex,double x)throws SQLException{
		resultSet.updateDouble(columnIndex,x);
	}
	
	public void updateBigDecimal(int columnIndex,BigDecimal x)throws SQLException{
		resultSet.updateBigDecimal(columnIndex,x);
	}
	public void updateString(int columnIndex,String x)throws SQLException{
		resultSet.updateString(columnIndex,x);
	}
	public void updateBytes(int columnIndex,byte[] x)throws SQLException{
		resultSet.updateBytes(columnIndex,x);
	}
	public void updateDate(int columnIndex,Date x)throws SQLException{
		resultSet.updateDate(columnIndex,x);
	}
	public void updateTime(int columnIndex,Time x)throws SQLException{
		resultSet.updateTime(columnIndex,x);
	}
	public void updateTimestamp(int columnIndex,Timestamp x)throws SQLException{
		resultSet.updateTimestamp(columnIndex,x);
	}
	public void updateAsciiStream(int columnIndex,InputStream x,int length)throws SQLException{
		resultSet.updateAsciiStream(columnIndex,x,length);
	}
	public void updateBinaryStream(int columnIndex,InputStream x,int length)throws SQLException{
		resultSet.updateBinaryStream(columnIndex,x,length);
	}
	public void updateCharacterStream(int columnIndex,Reader x,int length)throws SQLException{
		resultSet.updateCharacterStream(columnIndex,x,length);
	}
	public void updateObject(int columnIndex,Object x,int scale)throws SQLException{
		resultSet.updateObject(columnIndex,x,scale);
	}
	public void updateObject(int columnIndex,Object x)throws SQLException{
		resultSet.updateObject(columnIndex,x);
	}
	public void updateNull(String columnName)throws SQLException{
		resultSet.updateNull(columnName);
	}
	public void updateBoolean(String columnName,boolean x)throws SQLException{
		resultSet.updateBoolean(columnName,x);
	}
	public void updateByte(String columnName,byte x)throws SQLException{
		resultSet.updateByte(columnName,x);
	}
	public void updateShort(String columnName,short x)throws SQLException{
		resultSet.updateShort(columnName,x);
	}
	public void updateInt(String columnName,int x)throws SQLException{
		resultSet.updateInt(columnName,x);
	}
	public void updateLong(String columnName,long x)throws SQLException{
		resultSet.updateLong(columnName,x);
	}
	public void updateFloat(String columnName,float x)throws SQLException{
		resultSet.updateFloat(columnName,x);
	}
	public void updateDouble(String columnName,double x)throws SQLException{
		resultSet.updateDouble(columnName,x);
	}

	public void updateBigDecimal(String columnName,BigDecimal x)throws SQLException{
		resultSet.updateBigDecimal(columnName,x);
	}

	public void updateString(String columnName,String x)throws SQLException{
		resultSet.updateString(columnName,x);
	}

	public void updateBytes(String columnName,byte[] x)throws SQLException{
		resultSet.updateBytes(columnName,x);
	}

	public void updateDate(String columnName,Date x)throws SQLException{
		resultSet.updateDate(columnName,x);
	}

	public void updateTime(String columnName,Time x)throws SQLException{
		resultSet.updateTime(columnName,x);
	}

	public void updateTimestamp(String columnName,Timestamp x)throws SQLException{
		resultSet.updateTimestamp(columnName,x);
	}


	public void updateAsciiStream(String columnName,InputStream x,int length)throws SQLException{
		resultSet.updateAsciiStream(columnName,x,length);
	}

	public void updateBinaryStream(String columnName,InputStream x,int length)throws SQLException{
		resultSet.updateBinaryStream(columnName,x,length);
	}

	public void updateCharacterStream(String columnName,Reader reader,int length)throws SQLException{
		resultSet.updateCharacterStream(columnName,reader,length);
	}

	public void updateObject(String columnName,Object x,int scale)throws SQLException{
		resultSet.updateObject(columnName,x,scale);
	}

	public void updateObject(String columnName,Object x)throws SQLException{
		resultSet.updateObject(columnName,x);
	}

		
	public void insertRow()throws SQLException{
		resultSet.updateRow();
	}

	public void updateRow()throws SQLException{
		resultSet.updateRow();
	}

	public void deleteRow()throws SQLException{
		resultSet.deleteRow();
	}

	public void refreshRow()throws SQLException{
		resultSet.refreshRow();
	}

	public void cancelRowUpdates()throws SQLException{
		resultSet.cancelRowUpdates();
	}

	public void moveToInsertRow()throws SQLException{
		resultSet.moveToInsertRow();
	}

	public void moveToCurrentRow()throws SQLException{
		resultSet.moveToCurrentRow();
	}

	public Statement getStatement()throws SQLException{
			return resultSet.getStatement();
	}

	public Object getObject(int i,Map map)throws SQLException{
			return resultSet.getObject(i,map);
	}

	public Ref getRef(int i)throws SQLException{
			return resultSet.getRef(i);
	}

	public Blob getBlob(int i)throws SQLException{
			return resultSet.getBlob(i);
	}

	public Clob getClob(int i)throws SQLException{
			return resultSet.getClob(i);
	}

	public Array getArray(int i)throws SQLException{
			return resultSet.getArray(i);
	}

	public Object getObject(String colName,Map map)throws SQLException{
			return resultSet.getObject(colName,map);
	}

	public Ref getRef(String colName)throws SQLException{
			return resultSet.getRef(colName);
	}

	public Blob getBlob(String colName)throws SQLException{
			return resultSet.getBlob(colName);
	}

	public Clob getClob(String colName)throws SQLException{
			return resultSet.getClob(colName);
	}

	public Array getArray(String colName)throws SQLException{
			return resultSet.getArray(colName);
	}

	public Date getDate(int columnIndex,Calendar cal)throws SQLException{
			return resultSet.getDate(columnIndex,cal);
	}

	public Date getDate(String columnName,Calendar cal)throws SQLException{
			return resultSet.getDate(columnName,cal);
	}

	public Time getTime(int columnIndex,Calendar cal)throws SQLException{
			return resultSet.getTime(columnIndex,cal);
	}

	public Time getTime(String columnName,Calendar cal)throws SQLException{
			return resultSet.getTime(columnName,cal);
	}

	public Timestamp getTimestamp(int columnIndex,Calendar cal)throws SQLException{
			return resultSet.getTimestamp(columnIndex,cal);
	}

	public Timestamp getTimestamp(String columnName,Calendar cal)throws SQLException{
			return resultSet.getTimestamp(columnName,cal);
	}
	
	public java.net.URL getURL(String url) throws SQLException { 
			return resultSet.getURL(url);
	}
	public void updateRef(int i, java.sql.Ref r) throws SQLException{
			resultSet.updateRef(i, r);
	}
	public java.net.URL getURL(int i) throws SQLException{
			return resultSet.getURL(i);
	}
	public void updateRef(String s, java.sql.Ref r) throws SQLException {
			resultSet.updateRef(s, r);			
	}
	public void updateArray(int i,Array a) throws SQLException {
			resultSet.updateArray(i, a);			
	}
	public void updateArray(String s,Array a) throws SQLException {
			resultSet.updateArray(s, a);			
	}
	public void updateClob(int i, Clob c)throws SQLException {
			resultSet.updateClob(i,c);
	}
	public void updateClob(String s,Clob c) throws SQLException {
			resultSet.updateClob(s,c);
	}
	public void updateBlob(String s,Blob b) throws SQLException {
			resultSet.updateBlob(s,b);
	}
	public void updateBlob(int i,Blob b) throws SQLException {
			resultSet.updateBlob(i ,b);
		}
	
	protected void finalize(){
		try{
//			EliteLogger.connectionPoolLog.debug("finalize CLOSING THE CONNECTION RSW: " + createTime);
			this.closeAll();			
		}catch(Exception e){
		}
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return resultSet.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return resultSet.isWrapperFor(iface);
	}

	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		return resultSet.getRowId(columnIndex);
	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		return resultSet.getRowId(columnLabel);	
		}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		resultSet.updateRowId(columnIndex, x);
	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		resultSet.updateRowId(columnLabel, x);
	}

	@Override
	public int getHoldability() throws SQLException {
		return resultSet.getHoldability();
	}

	@Override
	public boolean isClosed() throws SQLException {
		return resultSet.isClosed();
	}

	@Override
	public void updateNString(int columnIndex, String nString) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNString(String columnLabel, String nString) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}