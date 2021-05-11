package fr.m2i.crm.dao;

import java.util.List;

import fr.m2i.crm.exception.DaoException;
import fr.m2i.crm.model.Customer;

public interface CustomerDao {
	List<Customer> getAllCustomers() throws DaoException;

	Customer getCustomerById(Integer id) throws DaoException;

	Customer getCustomerByLastname(String lastname) throws DaoException;

	void createCustomer(Customer customer) throws DaoException;

	void updateCustomer(Customer customer) throws DaoException;

	void deleteCustomer(Customer customer) throws DaoException;
}
