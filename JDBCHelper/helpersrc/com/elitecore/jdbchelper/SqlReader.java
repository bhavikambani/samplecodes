package com.elitecore.jdbchelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.elitecore.jdbchelper.ConnectionManager;
import com.elitecore.log.EliteLogger;

public class SqlReader{
	
	private static boolean DEBUG = true;
	private Connection transaction;
	private boolean connectionClosed = false;
	private long createTime = 0;
	//private static int queryTimeOut = 100;
	public boolean pool;
	//public SqlReader instance;
	
	private static String MODULE = "[SqlReader] ";
	
	public SqlReader(){
		this.pool = false;
		//instance = this;
	}
	
	/**
		If pool is true always get the connection from the connection pool
		else store the connection in the instance itself after getting from 
		the connection pool.
	*/
	public SqlReader(boolean pool){
		this.pool = pool;
		//instance = this;
		createTime = System.currentTimeMillis();
	}
	
	public void setPool(boolean pool){
		this.pool = pool;
	}
	
	public boolean getPool(){
		return pool;
	}
	
	public void close(Connection con){
		if ( con != null ) ConnectionManager.getSharedInstance().close(con);
		connectionClosed = true;
	}
	
	public void close() throws SQLException{
		if ( transaction != null ) ConnectionManager.getSharedInstance().close(transaction);
		transaction = null;
		connectionClosed = true;
	}
	
	public String toString(){
		return "[ SqlReader: " + createTime+ "Connection: " + transaction+" ]";
	}
	
	private Connection getConnection(){
		return ConnectionManager.getSharedInstance().getConnection();
	}
	
	//keep this method
	//temp
	public static ResultSetWrapper getResultSetWrapper(String query)
		throws java.sql.SQLException,java.lang.NullPointerException{
			return new ResultSetWrapper(query);
	}
	public ResultSetWrapper getInstanceResultSetWrapper(String query)
		throws java.sql.SQLException,java.lang.NullPointerException{
		return getInstanceResultSetWrapper(query,5);
	}
	
	public ResultSetWrapper getInstanceResultSetWrapper(String query,int queryTimeOut)
		throws java.sql.SQLException,java.lang.NullPointerException{
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		if (pool){
			transaction = getConnection();
		}else{
			if (transaction == null || transaction.isClosed()){
				trace("Using NEW NEW Connection");
				transaction = getConnection();
				trace("AUTO COMMIT FALSE IS DONE");
				//transaction.setAutoCommit(false);
			}/*else{
				trace("Using OLD OLD Connection");
			}*/
			/*
			if (transaction!=null && !transaction.isClosed()){
				trace("Using OLD OLD Connection");
			}else {
				trace("Using NEW NEW Connection");
				transaction = getConnection();
				transaction.setAutoCommit(false);
			}*/
		}
		//ConnectionManager.getSharedInstance().setQuery(transaction,query);
		stmt = transaction.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		/**
		 * Commenting this for compatibility with postgresql_jdbc5.jar in eliteAAA radius.
		 */
		//stmt.setQueryTimeout(queryTimeOut);
		rs = stmt.executeQuery( query );
		return new ResultSetWrapper(transaction,stmt,rs);
	}
	
	public static ArrayList getResultAsArrayList(String query,int queryTimeOut) throws java.sql.SQLException,java.lang.NullPointerException{
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList temp = null;
		
		try{ 
			con = ConnectionManager.getSharedInstance().getConnection();
			//ConnectionManager.getSharedInstance().setQuery(con,query);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			/**
			 * Commenting this for compatibility with postgresql_jdbc5.jar in eliteAAA radius.
			 */
			//stmt.setQueryTimeout(queryTimeOut);
			rs = stmt.executeQuery(query);
			temp = convertToArrayList(rs);
			
		}finally{
			
			if(rs != null){
				try{
					rs.close();
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Error while closing ResultSet : " + e,e);
				}
			}
			
			if( stmt != null ){
				try{
					stmt.close();
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Error while closing PreparedStatement : " + e,e);
				}
			}
			if(con != null){
				try{
					ConnectionManager.getSharedInstance().close(con);
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Error while closing Connection : " + e,e);
				}
			}
		}

		return temp;
	}
	
	//keep this method
	public ArrayList executeSqlArrayList(String query){
		
		trace("before: "+query);
		// The JDBC Connection object
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList temp = null;
		
		try{
			if (pool){
				con = getConnection();
			}else {
				if (transaction!=null){
					con =transaction;
				}else {
					transaction = con = getConnection();
					transaction.setAutoCommit(false);
				}
			}
			//ConnectionManager.getSharedInstance().setQuery(con,query);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(query);
			temp = convertToArrayList(rs);
			
		}catch(java.sql.SQLException e){
			EliteLogger.connectionPoolLog.error(MODULE+"Error in executing Query : "+ query, e);
			
		}catch(Exception e){
			EliteLogger.connectionPoolLog.error(MODULE+"Error in executing Query : "+ query, e);
			
		}finally {
			
			if(rs != null){
				try{
					rs.close();
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Error while closing ResultSet : " + e,e);
				}
			}
			
			if( stmt != null ){
				try{
					stmt.close();
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Error while closing PreparedStatement : " + e,e);
				}
			}
			if(con != null){
				try{
					ConnectionManager.getSharedInstance().close(con);
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Error while closing Connection : " + e,e);
				}
			}
		}
		return temp;
	}

	public int executeUpdate (String update) throws SQLException{
		return executeUpdate(update,5);
	}	
/**
 * Execute update Query
 */
	public int executeUpdate (String update,int queryTimeOut) throws SQLException{
		
		Statement stmt = null;
		ResultSet rs = null;
		int value;
		
		try{
			EliteLogger.connectionPoolLog.debug(MODULE+"Execute Update: " + transaction);
			if (pool){
				transaction = getConnection();
				EliteLogger.connectionPoolLog.debug(MODULE+"Got the connection object: " + transaction);
			}else{
				if (transaction == null || transaction.isClosed()){
					EliteLogger.connectionPoolLog.debug(MODULE+"Using NEW NEW Connection");			
					transaction = getConnection();
					EliteLogger.connectionPoolLog.debug(MODULE+"Using NEW NEW Connection: " + transaction + ":autocommit: " + transaction.getAutoCommit());
				}/*else{
					EliteLogger.connectionPoolLog.debug("Using OLD OLD Connection: " + transaction + ":autocommit: " + transaction.getAutoCommit());
				}*/
				/*
				if (transaction!=null && !transaction.isClosed()){
					EliteLogger.connectionPoolLog.debug("Using OLD OLD Connection: " + transaction + ":autocommit: " + transaction.getAutoCommit());
				}else {
					EliteLogger.connectionPoolLog.debug("Using NEW NEW Connection");			
					transaction = getConnection();
					EliteLogger.connectionPoolLog.debug("Using NEW NEW Connection: " + transaction + ":autocommit: " + transaction.getAutoCommit());
				}
				*/
			}
			EliteLogger.connectionPoolLog.debug(MODULE+"Connection Object: " + transaction + " " + createTime);
			//ConnectionManager.getSharedInstance().setQuery(transaction,update);
			stmt = transaction.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			/**
			 * Commenting this for compatibility with postgresql_jdbc5.jar in eliteAAA radius.
			 */
			//stmt.setQueryTimeout(queryTimeOut);
			value = stmt.executeUpdate(update);
			EliteLogger.connectionPoolLog.debug(MODULE+"update value: " + value);

		}catch(java.sql.SQLException e){
			EliteLogger.connectionPoolLog.error(MODULE+"SqlReader ExceuteUpdate SQLException Exception:\n " + update, e );
			throw e;
			
		}catch(Exception e){
			EliteLogger.connectionPoolLog.error(MODULE+"SqlReader ExceuteUpdate General Exception:\n " + update, e );
			return -1;
			
		}finally{
			if ( stmt != null ){
				try{
					stmt.close();
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Statement closed in SqlReader execute update : " + update, e );
				}
			}
			if ( transaction != null ){
				try{
					transaction.commit(); //commiting single transaction explicitly
					close(transaction);
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Exception while closing connection : " + transaction, e );
				}
			}
		}
		return value;
	}
/**
 * A method specific for mysql which returns the last insert id
 */
/*
	public int executeInsertWithLastid(String update,int queryTimeOut) throws SQLException{
		Statement stmt = null;
		ResultSet rs = null;
		int value;
		try{
			EliteLogger.connectionPoolLog.debug("Execute Update: " + transaction);
			if (pool){
				transaction = getConnection();
				EliteLogger.connectionPoolLog.debug("Got the connection object: " + transaction);
			}else{
				if (transaction == null || transaction.isClosed()){
					EliteLogger.connectionPoolLog.debug("Using NEW NEW Connection");			
					transaction = getConnection();
					EliteLogger.connectionPoolLog.debug("Using NEW NEW Connection: " + transaction + ":autocommit: " + transaction.getAutoCommit());
				}
			}
			EliteLogger.connectionPoolLog.debug("Connection Object: " + transaction + " " + createTime);
			stmt = transaction.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt.setQueryTimeout(queryTimeOut);
			value = stmt.executeUpdate(update);
			EliteLogger.connectionPoolLog.debug("update value: " + value);
			if(value > 0){
				String lastInsertIdQuery = "SELECT LAST_INSERT_ID()";
				rs = stmt.executeQuery(lastInsertIdQuery);
				if(rs.next()){
					value = rs.getInt(1);
				}
			}			
			if(pool){ 
				transaction.commit(); //commiting single transaction explicitly
				close(transaction);
			}
		}catch(java.sql.SQLException e){
			EliteLogger.connectionPoolLog.error("SqlReader ExecuteUpdate: " + update, e);
			throw e;
		}catch(Exception e){
			EliteLogger.connectionPoolLog.error("SqlReader ExcecuteUpdate General Exception: " + update,e);
			return -1;
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				stmt.close();
			}catch(Exception e){
				EliteLogger.connectionPoolLog.info("Statement closed in SqlReader execute update: ", e);
			}
		}
		return value;
	}
	
	*/
	
	
	public int executeInsertWithLastid(String update) throws SQLException{
		return executeInsertWithLastid(update,5);
	}
	
	public int executeInsertWithLastid(String update,int queryTimeOut) throws SQLException{
		
		Statement stmt = null;
		ResultSet rs = null;
		int nextPrimaryKey = -1;
		int value = -1;
		
		try{
			EliteLogger.connectionPoolLog.debug(MODULE+"Execute Update: " + transaction);
			if (pool){
				transaction = getConnection();
				EliteLogger.connectionPoolLog.debug(MODULE+"Got the connection object: " + transaction);
			}else{
				if (transaction == null || transaction.isClosed()){
					EliteLogger.connectionPoolLog.debug(MODULE+"Using NEW NEW Connection");			
					transaction = getConnection();
					EliteLogger.connectionPoolLog.debug(MODULE+"Using NEW NEW Connection: " + transaction + ":autocommit: " + transaction.getAutoCommit());
				}
			}
			EliteLogger.connectionPoolLog.debug(MODULE+"Connection Object: " + transaction + " " + createTime);
			//ConnectionManager.getSharedInstance().setQuery(transaction,update);
			
			StringTokenizer stk = new StringTokenizer(update);
			String tablename = null;
			stk.nextToken();
			stk.nextToken();
			tablename = stk.nextToken();
			EliteLogger.connectionPoolLog.info(MODULE+"INSERT QUERY FOR TABLE: " + tablename);
			
			String updateQuery = update.toLowerCase();
			int indexOfNull = updateQuery.indexOf("null");
			if(indexOfNull < 0)
			{
				nextPrimaryKey = executeInsert(update,queryTimeOut);
				return nextPrimaryKey;
			}
			nextPrimaryKey = ConnectionManager.getSharedInstance().getPrimaryKeyUtil().getNextIntPrimaryKey(tablename,"");
			EliteLogger.connectionPoolLog.info(MODULE+"NEXT KEY VALUE: " + nextPrimaryKey);
			if (nextPrimaryKey > 0){
				update = update.substring(0,indexOfNull) + nextPrimaryKey + update.substring(indexOfNull+4,update.length());
			
				EliteLogger.connectionPoolLog.info(MODULE+"INSERT QUERY: " + update);
				//transaction.setAutoCommit(false);
				stmt = transaction.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				/**
				 * Commenting this for compatibility with postgresql_jdbc5.jar in eliteAAA radius.
				 */
				//stmt.setQueryTimeout(queryTimeOut);
				value = stmt.executeUpdate(update);
				//transaction.commit();
				//transaction.setAutoCommit(true);
			}
			EliteLogger.connectionPoolLog.debug(MODULE+"update value: " + value);
			/*
			if(value > 0){
				String lastInsertIdQuery = "SELECT LAST_INSERT_ID()";
				rs = stmt.executeQuery(lastInsertIdQuery);
				if(rs.next()){
					value = rs.getInt(1);
				}
			}			
			*/
		}catch(java.sql.SQLException e){
			EliteLogger.connectionPoolLog.error(MODULE+"SqlReader ExecuteUpdate: " + update, e);
			throw e;
			
		}catch(Exception e){
			EliteLogger.connectionPoolLog.error(MODULE+"SqlReader ExcecuteUpdate General Exception: " + update,e);
			return -1;
			
		}finally{
			if(rs != null){
				try{
					rs.close();
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Error while closing ResultSet : " + e,e);
				}
			}
			
			if( stmt != null ){
				try{
					stmt.close();
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Error while closing PreparedStatement : " + e,e);
				}
			}
			if(transaction != null){
				try{
					ConnectionManager.getSharedInstance().close(transaction);
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Error while closing Connection : " + e,e);
				}
			}
		}
		return nextPrimaryKey;
	}

	public int executeInsert(String update,int queryTimeOut) throws SQLException{
		
		Statement stmt = null;
		ResultSet rs = null;
		int nextPrimaryKey = -1;
		int value = -1;
		
		try{
			EliteLogger.connectionPoolLog.debug(MODULE+"Execute Update: " + transaction);
			if (pool){
				transaction = getConnection();
				EliteLogger.connectionPoolLog.debug(MODULE+"Got the connection object: " + transaction);
			}else{
				if (transaction == null || transaction.isClosed()){
					EliteLogger.connectionPoolLog.debug(MODULE+"Using NEW NEW Connection");			
					transaction = getConnection();
					EliteLogger.connectionPoolLog.debug(MODULE+"Using NEW NEW Connection: " + transaction + ":autocommit: " + transaction.getAutoCommit());
				}
			}
			EliteLogger.connectionPoolLog.debug(MODULE+"Connection Object: " + transaction + " " + createTime);
			//ConnectionManager.getSharedInstance().setQuery(transaction,update);
			
			EliteLogger.connectionPoolLog.info(MODULE+"INSERT QUERY: " + update);
			//transaction.setAutoCommit(false);
			stmt = transaction.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			/**
			 * Commenting this for compatibility with postgresql_jdbc5.jar in eliteAAA radius.
			 */
			//stmt.setQueryTimeout(queryTimeOut);
			nextPrimaryKey = stmt.executeUpdate(update);
			//transaction.commit();
			//transaction.setAutoCommit(true);
			
			EliteLogger.connectionPoolLog.debug(MODULE+"update value: " + value);
			/*
			if(value > 0){
				String lastInsertIdQuery = "SELECT LAST_INSERT_ID()";
				rs = stmt.executeQuery(lastInsertIdQuery);
				if(rs.next()){
					value = rs.getInt(1);
				}
			}			
			*/
		
		}catch(java.sql.SQLException e){
			EliteLogger.connectionPoolLog.error(MODULE+"SqlReader ExecuteUpdate: " + update, e);
			throw e;
			
		}catch(Exception e){
			EliteLogger.connectionPoolLog.error(MODULE+"SqlReader ExcecuteUpdate General Exception: " + update,e);
			return -1;
			
		}finally{
			if( rs != null ){
				try{
					rs.close();
				}catch(Exception e){
					EliteLogger.connectionPoolLog.info(MODULE+"Resultset closed in SqlReader execute update: ", e);
				}
			}
			if( stmt != null){
				try{
					stmt.close();
				}catch(Exception e){
					EliteLogger.connectionPoolLog.info(MODULE+"Statement closed in SqlReader execute update: ", e);
				}
			}
			if( transaction != null ){
				try{
					transaction.setAutoCommit(true);
					transaction.commit(); //commiting single transaction explicitly
					
				}catch(Exception e){
					EliteLogger.connectionPoolLog.info(MODULE+"Connection commit in SqlReader execute update: ", e);
				}
				try{
					close(transaction);
				}catch (Exception e) {
					EliteLogger.connectionPoolLog.info(MODULE+"Connection closed in SqlReader execute update: ", e);
				}
			}
		}
		return nextPrimaryKey;
	}
	

/**
 * Execute update Query
 */
	public int executeUpdateSingleStatement ( String update,int queryTimeOut ) throws SQLException{
		Statement stmt = null;
		ResultSet rs = null;
		int value;
		try{
			if (pool){
				transaction = getConnection();
			}else{
				if (transaction == null || transaction.isClosed()){
					EliteLogger.connectionPoolLog.debug(MODULE+"Using NEW NEW Connection");
					transaction = getConnection();
					transaction.setAutoCommit(false);
				}
			}
			//ConnectionManager.getSharedInstance().setQuery(transaction,update);
			stmt = transaction.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			/**
			 * Commenting this for compatibility with postgresql_jdbc5.jar in eliteAAA radius.
			 */
			//stmt.setQueryTimeout(queryTimeOut);
			value = stmt.executeUpdate( update );
			
		}catch(java.sql.SQLException e){
			EliteLogger.connectionPoolLog.error(MODULE+"SqlReader ExceuteUpdate: " + update,e);
			throw e;
			
		}catch(Exception e){
			EliteLogger.connectionPoolLog.error(MODULE+"SqlReader ExceuteUpdate General Exception: " + update,e);
			return -1;
			
		}finally{
			if( stmt != null ){
				try{
					EliteLogger.connectionPoolLog.debug(MODULE+"Closing statement in executeUpdateSingleStatement");
					stmt.close();
				}catch(Exception e){
					EliteLogger.connectionPoolLog.info(MODULE+"Error in closing statement in executeUpdateSingleStatement: ", e);
				}
			}
			if( transaction != null ){
				try{
					transaction.setAutoCommit(true);
					transaction.commit(); //commiting single transaction explicitly
				}catch(Exception e){
					EliteLogger.connectionPoolLog.info(MODULE+"Connection closed in SqlReader execute update: ", e);
				}
				try{
					close(transaction);
				}catch(Exception e){
					EliteLogger.connectionPoolLog.info(MODULE+"Connection closed in SqlReader execute update: ", e);
				}
			}
		}
		return value;
	}
	
/**
 * Execute update Query
 */
	public int executePreparedStatement(String userId,String name) throws SQLException{
		
		PreparedStatement ps = null;
		int value;
		String sql = "insert into tbluser values(?,?)";
		
		try{
			EliteLogger.connectionPoolLog.debug(MODULE+"Execute Update: " + transaction);
			if (pool){
				transaction = getConnection();
				EliteLogger.connectionPoolLog.debug(MODULE+"Got the connection object: " + transaction);
			}else{
				if (transaction == null || transaction.isClosed()){
					EliteLogger.connectionPoolLog.debug(MODULE+"Using NEW NEW Connection");			
					transaction = getConnection();
					//transaction.setAutoCommit(false);
				}/*else{
					EliteLogger.connectionPoolLog.debug("Using OLD OLD Connection");
				}*/
				/*
				if (transaction!=null && !transaction.isClosed()){
					//con = transaction;
					EliteLogger.connectionPoolLog.debug("Using OLD OLD Connection: " + transaction + ":autocommit: " + transaction.getAutoCommit());
				}else {
					EliteLogger.connectionPoolLog.debug("Using NEW NEW Connection");			
					transaction = getConnection();
					//transaction.setAutoCommit(false);
				}*/
			}
			EliteLogger.connectionPoolLog.debug(MODULE+"Connection Object: " + transaction + " " + createTime);
			//ConnectionManager.getSharedInstance().setQuery(transaction,sql);
			ps = transaction.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2,name);
			value = ps.executeUpdate();
			EliteLogger.connectionPoolLog.debug(MODULE+"update value: " + value);
			
		}catch(java.sql.SQLException e){
			EliteLogger.connectionPoolLog.error(MODULE+"SqlReader ExceuteUpdate: " + sql, e);
			throw e;
			
		}catch(Exception e){
			EliteLogger.connectionPoolLog.error(MODULE+"SqlReader ExceuteUpdate General Exception: " + sql,e);
			return -1;
			
		}finally{
			
			if ( ps != null ){
				try{
					ps.close();
				}catch(Exception e){
					EliteLogger.connectionPoolLog.info(MODULE+"Statement closed in SqlReader execute update: ",e);
				}
			}
			if ( transaction != null ){
				try{
					transaction.commit(); //commiting single transaction explicitly
				}catch(Exception e){
					EliteLogger.connectionPoolLog.info(MODULE+"Connection commit in SqlReader execute update: ",e);
				}
				try{
					close(transaction);
				}catch(Exception e){
					EliteLogger.connectionPoolLog.info(MODULE+"Connection closed in SqlReader execute update: ",e);
				}
			}
		}
		return value;
	}
	
	/**
	 * Execute Update Procedure
	 */
	public int executeStoredProcedureUpdate(String storedProcedure,int queryTimeOut){
		
		int updateCount= -1;
		CallableStatement cstatement= null;
		Connection con= null;
		
		try{
			if (pool){
				con = getConnection();
			}else{
				if (transaction == null || transaction.isClosed()){
					con = transaction = getConnection();
					//con.setAutoCommit(false);
				}else{
					con = transaction;
				}
			}
			//ConnectionManager.getSharedInstance().setQuery(con,storedProcedure);
			cstatement = con.prepareCall(storedProcedure);
			/**
			 * Commenting this for compatibility with postgresql_jdbc5.jar in eliteAAA radius.
			 */			
			//cstatement.setQueryTimeout(queryTimeOut);
			boolean isResult = cstatement.execute(storedProcedure);              // execute this statement, will return true if first result is a result set
			if(!isResult){// if it's a result set
				updateCount = cstatement.getUpdateCount();   // update count or -1 if we're done
			}
			
		}catch(SQLException sqlEx){
			EliteLogger.connectionPoolLog.error(MODULE+"Error in storedprocedure: " + storedProcedure,sqlEx);
			
		}catch(NullPointerException e){
			EliteLogger.connectionPoolLog.error(MODULE+"Error in storedprocedure: " + storedProcedure,e);
			
		}finally{
			
			if ( con != null ){
				try{
					con.commit(); //commiting single transaction explicitly
				}catch(Exception e){
					EliteLogger.connectionPoolLog.info(MODULE+"Connection commit in SqlReader execute update: ",e);
				}
				try{
					close(transaction);
				}catch(Exception e){
					EliteLogger.connectionPoolLog.info(MODULE+"Connection closed in SqlReader execute update: ",e);
				}
			}
		}
		return updateCount;
	}	
	// Method is created for executing store procedure with setting array 
	public int executeStoredProcedureUpdate(String storedProcedure,java.sql.Array array,int intParam){
		
		int updateCount= -1;
		CallableStatement cstatement= null;
		Connection con= null;
		try{
			if (pool){
				con = getConnection();
			}else{
				if (transaction == null || transaction.isClosed()){
					con = transaction = getConnection();
					//con.setAutoCommit(false);
				}else{
					con = transaction;
				}
			}
			//ConnectionManager.getSharedInstance().setQuery(con,storedProcedure);
			cstatement = con.prepareCall(storedProcedure);
			cstatement.setArray(1, array);
			cstatement.setInt(2, intParam);
			/**
			 * Commenting this for compatibility with postgresql_jdbc5.jar in eliteAAA radius.
			 */			
			//cstatement.setQueryTimeout(queryTimeOut);
			boolean isResult = cstatement.execute();              // execute this statement, will return true if first result is a result set
			if(!isResult){// if it's a result set
				updateCount = cstatement.getUpdateCount();   // update count or -1 if we're done
			}
			else {
				updateCount = 0;
			}
			
		}catch(SQLException sqlEx){
			EliteLogger.connectionPoolLog.error(MODULE+"Error in storedprocedure: " + storedProcedure,sqlEx);
			updateCount = -1;
		}catch(NullPointerException e){
			EliteLogger.connectionPoolLog.error(MODULE+"Error in storedprocedure: " + storedProcedure,e);
			updateCount = -1;
		}finally{
			
			if ( con != null ){
				try{
					con.commit(); //commiting single transaction explicitly
				}catch(Exception e){
					EliteLogger.connectionPoolLog.info(MODULE+"Connection commit in SqlReader execute update: ",e);
				}
				try{
					close(transaction);
				}catch(Exception e){
					EliteLogger.connectionPoolLog.info(MODULE+"Connection closed in SqlReader execute update: ",e);
				}
			}
		}
		return updateCount;
	}	
/**
	 * Do not call this method in the middle of a transaction 
	 * as this will cause a commit default ( infact a commit is called )
	 * Hence call this method only in the 
	 * begining/end of some operation.
*/
	public void setAutoCommit(boolean flag){
		//Commit  & roll back is responsibility of user
		try{
			if (flag == false){
				pool = false;
			}else{
				//commit is resonsibility of database
				if (transaction != null){
					transaction.setAutoCommit(true);
					close(transaction);
					transaction = null;
				}
				pool = true;
			}
		}catch(SQLException e){
			EliteLogger.connectionPoolLog.error(MODULE+getClass()+"Error occured in commit",e);
		}
	}

	public void rollback(){
		EliteLogger.connectionPoolLog.debug(MODULE+"ROLLBACK CALLED: ");
		try{
			EliteLogger.connectionPoolLog.debug(MODULE+"Using OLD OLD Connection for rollback: " + transaction + ":autocommit: " + transaction.getAutoCommit());
			if(transaction!=null){
				transaction.rollback();
			}
		}catch(SQLException e){
			EliteLogger.connectionPoolLog.error(MODULE+getClass()+"Error occured in rollback ",e);
		}
	}
	
	/**
	 * Here we are giving the connection back to the pool and
	 * making the connection variable null
	 * hence when he comes the next time we know that he
	 * needs auto commit hence we will get a transaction then only
	 * this will allow the connection to be pooled in the true sense
	 */
	public void commit() throws SQLException{
		try{
			if(transaction!=null){
				transaction.commit();
			}
		}catch(SQLException e){
			throw e;
		}
	}

	//keep this method in a utility class
	public static String getNextDate(int part,int counter){
		
		String date = "";
		String sql;
		sql = "SELECT CONVERT(char(12), DATEADD(MM,"+counter+", GETDATE()), 103) date";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try	{
			con = ConnectionManager.getSharedInstance().getConnection();
			//ConnectionManager.getSharedInstance().setQuery(con,sql);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			/**
			 * Commenting this for compatibility with postgresql_jdbc5.jar in eliteAAA radius.
			 */
			//stmt.setQueryTimeout(10);
			rs = stmt.executeQuery( sql );
			rs.next();
			date = rs.getString(1);
			
		}catch(java.sql.SQLException e){
			EliteLogger.connectionPoolLog.error(MODULE+sql);
			EliteLogger.connectionPoolLog.error(MODULE+"\nError in getting date:\n", e);
			
		}catch( Exception ex ){
			EliteLogger.connectionPoolLog.error(MODULE+"Exception in getNextDate() : "+ex,ex);
			
		}finally{
			
			if(rs != null){
				try{
					rs.close();
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Error while closing ResultSet : " + e,e);
				}
			}
			
			if( stmt != null ){
				try{
					stmt.close();
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Error while closing PreparedStatement : " + e,e);
				}
			}
			if(con != null){
				try{
					ConnectionManager.getSharedInstance().close(con);
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Error while closing Connection : " + e,e);
				}
			}
		}
		return date;
	}
	/*
	public static String getCurrentDate(String format){
		String date = "";
		String sql;
		//sql = "SELECT CONVERT(char(12), GETDATE(), 103) date";
		sql = "SELECT to_char(sysdate,'"+format+"') from dual";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String systemDate = null;
		try{
			con = ConnectionManager.getSharedInstance().getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt.setQueryTimeout(10);
			rs = stmt.executeQuery(sql);
			if( rs.next())
				systemDate = rs.getString(1);
		}catch(java.sql.SQLException e){
			EliteLogger.connectionPoolLog.error("\nError in getting date:\n",e);
			EliteLogger.connectionPoolLog.error(sql);
		}
		catch(java.lang.NullPointerException ex){}
		finally{
			if (rs != null){
				try {rs.close();}catch(java.sql.SQLException e1 ){}
			}
			if (stmt != null){
				try {stmt.close();}catch(java.sql.SQLException e1){}
			}
			if (con != null){
				ConnectionManager.getSharedInstance().close(con);
			}
		}
		return systemDate;
	}
	*/
	/**
	 * Mixed value of result set + value not supported
	*/
	public ArrayList executeStoredProcedureQuery(String storedProcedure,int queryTimeOut){

		CallableStatement cstatement= null;
		Connection  con= null;
		ResultSet resultSet = null;
		
		try{
			
			con = getConnection();
			//ConnectionManager.getSharedInstance().setQuery(con,storedProcedure);
			cstatement = con.prepareCall(storedProcedure);
			/**
			 * Commenting this for compatibility with postgresql_jdbc5.jar in eliteAAA radius.
			 */
			//cstatement.setQueryTimeout(queryTimeOut);
			//does not make sense
			//rs = cstatement.executeQuery();
			boolean isResult = cstatement.execute(storedProcedure);              // execute this statement, will return true if first result is a result set
			//May give problems
			//instance.close(con);

			if(isResult){
				// if it's a result set
				//ajay
				//ResultSet resultSet = cstatement.getResultSet(); // get current result set
				resultSet = cstatement.getResultSet(); // get current result set
				ArrayList v = convertToArrayList (resultSet);
				return v;
			}else{// could be an update count or we could be done
				int updateCount = cstatement.getUpdateCount();   // update count or -1 if we're done
				if(updateCount != -1)                           // if this is an update count
				{
					return new ArrayList();
				}
				return null;
			}
		}catch(SQLException sqlEx){
			// catch problems here
			EliteLogger.connectionPoolLog.error(MODULE+"\nError in executing storedProcedure:\n");
			EliteLogger.connectionPoolLog.error(storedProcedure,sqlEx);
			return null;
			
		}catch(java.lang.NullPointerException e){
			EliteLogger.connectionPoolLog.error(MODULE+"\nError in executing storedProcedure:\n");
			EliteLogger.connectionPoolLog.error(storedProcedure,e);
			return null;
			
		}finally{
	
			if(resultSet != null){
				try{
					resultSet.close();
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Error while closing ResultSet : " + e,e);
				}
			}
			
			if( cstatement != null ){
				try{
					cstatement.close();
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Error while closing PreparedStatement : " + e,e);
				}
			}
			if(con != null){
				try{
					ConnectionManager.getSharedInstance().close(con);
				}catch(Exception e){
					EliteLogger.connectionPoolLog.error(MODULE+"Error while closing Connection : " + e,e);
				}
			}
		}
	}


	//converToArrayList
	public static ArrayList convertToArrayList(ResultSet rs){
		//Vector rows = new Vector();
		ArrayList rows = new ArrayList();
		try{
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfCol = rsmd.getColumnCount();

			while(rs.next()){
				//Vector colus = new Vector();
				ArrayList colus = new ArrayList();
				Object temp;
				for( int i=1;i<=numberOfCol; i++){
					temp = (rs.getObject(i));
					if (rs.wasNull()){
						
						switch(rsmd.getColumnType(i)){

						case java.sql.Types.DECIMAL:
							temp = new java.math.BigDecimal(0.0);
							break;
						case java.sql.Types.NUMERIC: //small money 2
							temp = new java.math.BigDecimal(0.0);
							break;
						case java.sql.Types.BIGINT:
							temp = new java.math.BigDecimal(0.0);
							break;
						case java.sql.Types.INTEGER:
							temp = new Integer(0);
							break;
						case java.sql.Types.SMALLINT:
							temp = new Integer(0);
							break;
						case java.sql.Types.TINYINT:
							temp = new Integer(0);
							break;
						case java.sql.Types.FLOAT:
							break;
						case java.sql.Types.REAL:
							temp = new Float(0.0);
							break;
						case java.sql.Types.CHAR:
							break;
						case java.sql.Types.VARCHAR:
							break;
						case java.sql.Types.LONGVARCHAR:
							temp = " ";
							break;
						case java.sql.Types.DATE:
							temp = " ";
							break;
						}
					}
					colus.add(temp);
				}
				colus.trimToSize();
				rows.add(colus);
			}

		}catch(Exception se){
			EliteLogger.connectionPoolLog.debug(MODULE+"Returning ArrayList Exception: ",se);
			return null;
		}
		rows.trimToSize(); //this will trim the ArrayList
		return rows;
	}
	
	private void trace(String message){
		if (DEBUG){
			EliteLogger.connectionPoolLog.debug(MODULE+message);
		}
	}
	protected void finalize(){
		try{
			if(!this.connectionClosed){
				this.close();
			}
		}catch(Exception e){
			EliteLogger.connectionPoolLog.debug(MODULE+"Exception in finalize : "+e,e);
		}
	}
}// end of SqlReader Class