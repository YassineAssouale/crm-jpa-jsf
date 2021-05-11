package fr.m2i.crm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.m2i.crm.dao.CustomerDao;
import fr.m2i.crm.exception.DaoException;
import fr.m2i.crm.model.Customer;

public class CustomerDaoImpl implements CustomerDao {

	private static final String REQ_SEL_CUSTOMERS = "SELECT id, lastname, firstname, company, mail, phone, mobile, notes, active FROM customers;";
	private static final String REQ_SEL_CUSTOMERS_BY_ID = "SELECT id, lastname, firstname, company, mail, phone, mobile, notes, active FROM customers WHERE id = ?;";
	private static final String REQ_SEL_CUSTOMERS_BY_LASTNAME = "SELECT id, lastname, firstname, company, mail, phone, mobile, notes, active FROM customers WHERE lastname LIKE ?;";
	private static final String REQ_CREATE_CUSTOMER = "INSERT INTO public.customers (lastname, firstname, company, mail, phone, mobile, notes, active) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String REQ_UPDATE_CUSTOMER = "UPDATE public.customers SET lastname=?, firstname=?, company=?, mail=?, phone=?, mobile=?, notes=?, active=? WHERE id=?;";
	private static final String REQ_DEL_CUSTOMER_BY_ID = "DELETE FROM customers WHERE id = ?;";

	private DaoFactory daoFactory;

	CustomerDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Customer> getAllCustomers() throws DaoException {

		Connection connexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Customer> customers = new ArrayList<>();

		try {
			connexion = daoFactory.getConnection();
			ps = DaoUtils.initializePreparedStatement(connexion, REQ_SEL_CUSTOMERS, false, null);
			rs = ps.executeQuery();

			while (rs.next()) {
				customers.add(map(rs));
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DaoUtils.close(rs, ps, connexion);
		}
		return customers;
	}

	@Override
	public Customer getCustomerById(Integer id) throws DaoException {
		return find(REQ_SEL_CUSTOMERS_BY_ID, id);

	}

	@Override
	public Customer getCustomerByLastname(String lastname) throws DaoException {
		return find(REQ_SEL_CUSTOMERS_BY_LASTNAME, lastname);
	}

	@Override
	public void createCustomer(Customer customer) throws DaoException {

		Connection connexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connexion = daoFactory.getConnection();
			ps = DaoUtils.initializePreparedStatement(connexion, REQ_CREATE_CUSTOMER, true, customer.getLastname(),
					customer.getFirstname(), customer.getCompany(), customer.getMail(), customer.getPhone(),
					customer.getMobile(), customer.getNotes(), customer.getActive());
			int statut = ps.executeUpdate();

			if (statut == 0) {
				throw new DaoException("Echec de la création du client, aucune ligne ajoutée dans la table");
			}

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				customer.setId(rs.getInt(1));
			} else {
				throw new DaoException("Echec de la création du client en base, aucun ID auto-généré retourné.");
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DaoUtils.close(ps, connexion);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws DaoException {
		Connection connexion = null;
		PreparedStatement ps = null;

		try {
			connexion = daoFactory.getConnection();
			ps = DaoUtils.initializePreparedStatement(connexion, REQ_UPDATE_CUSTOMER, false, customer.getLastname(),
					customer.getFirstname(), customer.getCompany(), customer.getMail(), customer.getPhone(),
					customer.getMobile(), customer.getNotes(), customer.getActive(), customer.getId());
			int statut = ps.executeUpdate();

			if (statut == 0) {
				throw new DaoException("Echec de la modification du client, aucune ligne modifiée dans la table");
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DaoUtils.close(ps, connexion);
		}
	}

	@Override
	public void deleteCustomer(Customer customer) throws DaoException {
		Connection connexion = null;
		PreparedStatement ps = null;

		try {
			connexion = daoFactory.getConnection();
			ps = DaoUtils.initializePreparedStatement(connexion, REQ_DEL_CUSTOMER_BY_ID, false, customer.getId());
			int statut = ps.executeUpdate();

			if (statut == 0) {
				throw new DaoException("Echec de la suppression du client, aucune ligne supprimée dans la table");
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DaoUtils.close(ps, connexion);
		}
	}

	private static Customer map(ResultSet rs) throws SQLException {
		Customer customer = new Customer(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"),
				rs.getString("company"), rs.getString("mail"), rs.getString("phone"), rs.getString("mobile"),
				rs.getString("notes"), rs.getBoolean("active"));
		return customer;
	}

	private Customer find(String requete, Object... objects) {
		Connection connexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Customer customer = new Customer();

		try {
			connexion = daoFactory.getConnection();
			ps = DaoUtils.initializePreparedStatement(connexion, requete, false, objects);
			rs = ps.executeQuery();

			while (rs.next()) {
				customer = map(rs);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DaoUtils.close(rs, ps, connexion);
		}
		return customer;
	}
}
