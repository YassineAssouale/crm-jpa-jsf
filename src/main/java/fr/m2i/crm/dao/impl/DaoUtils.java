package fr.m2i.crm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoUtils {

	/**
	 * Initialise une requête préparée basée sur la connexion passée en argument,
	 * avec la requête SQL et les objets donnés
	 */
	public static PreparedStatement initializePreparedStatement(Connection connexion, String sql,
			boolean returnGeneratedKeys, Object... objets) throws SQLException {
		PreparedStatement preparedStatement = connexion.prepareStatement(sql,
				returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		for (int i = 0; objets != null && i < objets.length; i++) {
			preparedStatement.setObject(i + 1, objets[i]);
		}
		return preparedStatement;
	}

	/**
	 * Fermeture du resultset
	 */
	public static void close(ResultSet resultSet) {
		if (null != resultSet) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println("Echec de la fermeture du ResultSet : " + e.getMessage());
			}
		}
	}

	/**
	 * Fermeture du Statement
	 */
	public static void close(Statement statement) {
		if (null != statement) {
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println("Echec de la fermeture du Statement : " + e.getMessage());
			}
		}
	}

	/**
	 * Fermeture de la connexion
	 */
	public static void close(Connection connexion) {
		if (null != connexion) {
			try {
				connexion.close();
			} catch (SQLException e) {
				System.out.println("Echec de la fermeture de la connexion : " + e.getMessage());
			}
		}
	}

	/**
	 * Fermeture du statement et de la connexion
	 */
	public static void close(Statement statement, Connection connexion) {
		close(statement);
		close(connexion);
	}

	/**
	 * Fermeture du ResultSet, du Statement et de la connexion
	 */
	public static void close(ResultSet resultSet, Statement statement, Connection connexion) {
		close(resultSet);
		close(statement);
		close(connexion);
	}

}
