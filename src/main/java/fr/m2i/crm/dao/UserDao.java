package fr.m2i.crm.dao;

import java.util.List;

import fr.m2i.crm.exception.DaoException;
import fr.m2i.crm.model.User;

public interface UserDao {
	List<User> getAll() throws DaoException;

	User getById(Integer id) throws DaoException;

	User getByUsername(String username) throws DaoException;

	void createUser(User user) throws DaoException;

	void udpateUser(User user) throws DaoException;

	void deleteUser(User user) throws DaoException;
}
