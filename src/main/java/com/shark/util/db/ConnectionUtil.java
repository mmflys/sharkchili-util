package com.shark.util.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connection util that connect to mysql database.
 * @Author: SuLiang
 * @Date: 2018/9/27 0027
 */
public class ConnectionUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionUtil.class);

	private String username;
	private String password;
	private String url;
	private String driver;

	/**
	 *
	 * @param username username
	 * @param password password
	 * @param url url
	 * @param driver driver
	 */
	private ConnectionUtil(String username, String password, String url, String driver) {
		this.username = username;
		this.password = password;
		this.url = url;
		this.driver = driver;
	}

	/**
	 * A static method that get a instance of ${@link ConnectionUtil}
	 * @param username username
	 * @param password password
	 * @param url url
	 * @param driver driver
	 * @return A ConnectionUtil
	 */
	public static ConnectionUtil create(String username, String password, String url, String driver){
		return new ConnectionUtil(username, password, url, driver);
	}

	/**
	 * Get a connection.
	 * @return if connect to database successfully,return a connection,else return null.
	 */
	public Connection getConnection(){
		try {
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url, username, password);
			LOGGER.info("Connect to database success,", connection);
			return connection;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
