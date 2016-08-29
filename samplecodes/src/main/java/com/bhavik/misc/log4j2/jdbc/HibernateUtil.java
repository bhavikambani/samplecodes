package com.bhavik.misc.log4j2.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.Statistics;

/**
 * This class gurrenty that only one single SessionFactory is instantiated and
 * that the configuration is done thread safe as singleton.
 * 
 * @author vinitshah
 * 
 *         Generated Sep 1, 2011 02:10:00 PM
 *
 */
public class HibernateUtil {
	private static SessionFactory sessionFactory;
	private static SessionFactory sessionFactory1;

	private static final String MODULE = "[HibernateUtil]";
	// mode is used to load HMS component related hibernate configuration
	public static String mode;

	/**
	 * disable constructor to guaranty a single instance
	 */
	protected HibernateUtil() {
	}

	/**
	 * create one time session Factory from static block.
	 */
	public synchronized static SessionFactory createSessionFactory() {

		if (sessionFactory == null) {
			sessionFactory = new Configuration().configure().buildSessionFactory();
			System.out.println(MODULE + "sessionFactory created");
		}

		return sessionFactory;
	}

	public static SessionFactory getInstance() {
		return createSessionFactory();
	}

	public static Session openSession() {

		if (sessionFactory == null) {
			createSessionFactory();
		}

		Statistics stats = sessionFactory.getStatistics();
		stats.setStatisticsEnabled(true);
		for (String s : stats.getSecondLevelCacheRegionNames()) {
			System.out.println("Domain Pojo :" + s + ": with statistics:" + stats.getSecondLevelCacheStatistics(s));
		}
		System.out.println("*******************************************************");
		// System.out.println("SessionImpl:"+stats);

		return sessionFactory.openSession();
	}

	/**
	 * Returns a session from the session context. If there is no session in the
	 * context it opens a session, stores it in the context and returns it. This
	 * factory is intended to be used with a hibernate.cfg.xml including the
	 * following property
	 * <property name="current_session_context_class">thread</property> This
	 * would return the current open session or if this does not exist, will
	 * create a new session
	 *
	 * @return the session
	 */
	public static Session getCurrentSession() {
		// System.out.println(MODULE + "Get CurrentSession");

		/*
		 * This code is to find who has called the method only for debugging and
		 * testing purpose. try { throw new Exception("Who called Me : "); }
		 * catch (Exception e) { //System.out.println("I was called by " +
		 * e.getStackTrace()[2].getClassName() + "." +
		 * e.getStackTrace()[2].getMethodName() + "()!");
		 * System.out.println("I was called by : ", e); }
		 */
		return openSession();
		// return sessionFactory.getCurrentSession();
	}

	/**
	 * closes the session factory
	 */
	public static void close() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
		System.out.println(MODULE + "SessionFactory Close");
		sessionFactory = null;
	}

	/**
	 * Commits the session which is created for saving transaction
	 * 
	 * @param session
	 *            Session object which we want to commit the transaction
	 * @author Bhavik Ambani
	 * @since X.3.7.0.0
	 */
	public static void commit(Session session) {
		if (session == null) {
			throw new NullPointerException("NULL is passed as session object for commiting transaction.");
		}
		if (session.isOpen() && session.getTransaction().isActive()) {
			session.getTransaction().commit();
			session.flush();
			session.close();
		} else {
			System.err.println(MODULE + "Session is not open, hence problem while commiting the transaction");
		}
	}
	
	public static Connection getDatabaseConnection() throws SQLException {
		return openSession().connection();
	}

	/**
	 * Rollback the transaction
	 * 
	 * @param session
	 *            Session object which we want to rollback the transaction
	 * @author Bhavik Ambani
	 * @since X.3.7.0.0
	 */
	public static void rollback(Session session) {
		System.out.println(MODULE + "Roll Back the transaction. Session # " + session);
		if (session == null) {
			throw new NullPointerException("NULL is passed as session object to rollback the transaction.");
		}
		if (session.isOpen() && session.getTransaction().isActive()) {
			session.getTransaction().rollback();
			session.clear();
			session.close();
		} else {
			System.err.println(MODULE + "Session is not open, hence problem while commiting the transaction");
		}

	}

	/*
	 * methods with String parameter is for registration and customization
	 * module, Registration through local Database only
	 * 
	 */
	public synchronized static SessionFactory createSessionFactory(String fileName) {

		if (sessionFactory1 == null) {
			System.out.println(MODULE + "in createSessionFactory");
			sessionFactory1 = new Configuration().configure(new File(fileName)).buildSessionFactory();
			System.out.println(MODULE + "sessionFactory created");
		}

		return sessionFactory1;
	}

	public static Session openSession(String fileName) {

		if (sessionFactory1 == null) {
			createSessionFactory(fileName);
		}
		return sessionFactory1.openSession();
	}

	public static Session getCurrentSession(String fileName) {
		return openSession(fileName);
	}
}
