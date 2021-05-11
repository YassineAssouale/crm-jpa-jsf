package fr.m2i.crm.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

import fr.m2i.crm.dao.CustomerDao;
import fr.m2i.crm.dao.OrderDao;
import fr.m2i.crm.dao.UserDao;
import fr.m2i.crm.exception.DaoConfigurationException;

public class DaoFactory {

	private static final String PROPERTIES_FILE = "fr/m2i/crm/dao/dao.properties";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_USERNAME = "username";
	private static final String PROPERTY_PASSWORD = "password";
	/*
	 * Pour une utilisation sans DataSOurce private String url; private String
	 * username; private String password;
	 */
	/*
	 * Basic implementation of javax.sql.DataSource.
	 * Commons-dbcp2 and commons-pool2 packages
	 * To create a pool of connexion
	 * To replace DriverManager. 
	 */
	private static BasicDataSource basicDs;

	/**
	 * Constructeur sans DataSource
	 * 
	 * @param url
	 * @param username
	 * @param password
	 */
	/*
	 * DaoFactory(String url, String username, String password) { this.url = url;
	 * this.username = username; this.password = password; }
	 */
	/**
	 * Constructeur avec DataSource
	 * 
	 * @param basicDataSource
	 */
	DaoFactory(BasicDataSource basicDataSource) {
		basicDs = basicDataSource;
	}

	/**
	 * Récupération de l'instance Factory
	 * 
	 * @return DaoFactory
	 */
	public static DaoFactory getInstance() throws DaoConfigurationException {
		Properties properties = new Properties();
		String url;
		String driver;
		String username;
		String password;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(PROPERTIES_FILE);

		if (fichierProperties == null) {
			throw new DaoConfigurationException("Le fichier properties " + PROPERTIES_FILE + " est introuvable.");
		}

		try {
			properties.load(fichierProperties);
			url = properties.getProperty(PROPERTY_URL);
			driver = properties.getProperty(PROPERTY_DRIVER);
			username = properties.getProperty(PROPERTY_USERNAME);
			password = properties.getProperty(PROPERTY_PASSWORD);
		} catch (IOException e) {
			throw new DaoConfigurationException("Impossible de charger le fichier properties " + PROPERTIES_FILE, e);
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new DaoConfigurationException("Le driver est introuvable dans le classpath.", e);
		}
		// Ajout pour la DataSource
		basicDs = new BasicDataSource();
		basicDs.setDriverClassName(driver);
		basicDs.setUrl(url);
		basicDs.setUsername(username);
		basicDs.setPassword(password);
		basicDs.setInitialSize(10);
		basicDs.setMaxTotal(20); // peut lever une exception si on demande une connection de plus

		return new DaoFactory(basicDs);
	}

	/**
	 * Récupération de la connexion à la BDD
	 * 
	 * @return Connection
	 */
	public Connection getConnection() throws SQLException {
		// return DriverManager.getConnection(url, username, password);
		return basicDs.getConnection();
	}

	/**
	 * Récupération du CustomerDao
	 * 
	 * @return CustomerDao
	 */
	public CustomerDao getCustomerDao() {
		return new CustomerDaoImpl(this);
	}

	/**
	 * Récupération du OrderDao
	 * 
	 * @return OrderDao
	 */
	public OrderDao getOrderDao() {
		return new OrderDaoImpl(this);
	}

	/**
	 * Récupération du UserDao
	 * 
	 * @return UserDao
	 */
	public UserDao getUserDao() {
		return new UserDaoImpl(this);
	}
}
