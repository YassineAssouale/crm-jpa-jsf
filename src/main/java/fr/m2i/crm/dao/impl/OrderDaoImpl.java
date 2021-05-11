package fr.m2i.crm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.m2i.crm.dao.OrderDao;
import fr.m2i.crm.exception.DaoException;
import fr.m2i.crm.model.Customer;
import fr.m2i.crm.model.Order;

public class OrderDaoImpl implements OrderDao {

	private static final String REQ_SEL_ORDERS = "SELECT id, label, adr_et, number_of_days, tva, status, type, notes, customer_id FROM orders;";
	private static final String REQ_SEL_ORDER_BY_ID = "SELECT id, label, adr_et, number_of_days, tva, status, type, notes, customer_id FROM orders WHERE id = ?;";
	private static final String REQ_CREATE_ORDER = "INSERT INTO orders (customer_id, label, adr_et, number_of_days, tva, status, type, notes) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String REQ_UPDATE_ORDER = "UPDATE orders SET customer_id=?, label=?, adr_et=?, number_of_days=?, tva=?, status=?, type=?, notes=? WHERE id=?;";
	private static final String REQ_DEL_ORDER = "DELETE FROM orders WHERE id = ?;";

	private DaoFactory daoFactory;

	OrderDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public List<Order> getAllOrders() throws DaoException {

		Connection connexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Order> orders = new ArrayList<>();

		try {
			connexion = daoFactory.getConnection();
			ps = DaoUtils.initializePreparedStatement(connexion, REQ_SEL_ORDERS, false, null);
			rs = ps.executeQuery();

			while (rs.next()) {
				Order order = map(rs);
				orders.add(order);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DaoUtils.close(rs, ps, connexion);
		}
		return orders;
	}

	@Override
	public Order getById(Integer id) throws DaoException {
		return find(REQ_SEL_ORDER_BY_ID, id);

	}

	@Override
	public void createOrder(Order order) throws DaoException {

		Connection connexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connexion = daoFactory.getConnection();
			ps = DaoUtils.initializePreparedStatement(connexion, REQ_CREATE_ORDER, true, order.getCustomer().getId(),
					order.getLabel(), order.getAdrEt(), order.getNumberOfDays(), order.getTva(), order.getStatus(),
					order.getType(), order.getNotes());
			int statut = ps.executeUpdate();

			if (statut == 0) {
				throw new DaoException("Echec de la création de la commande, aucune ligne ajoutée dans la table");
			}

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				order.setId(rs.getInt(1));
			} else {
				throw new DaoException("Echec de la création de la commande en base, aucun ID auto-généré retourné.");
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DaoUtils.close(ps, connexion);
		}
	}

	@Override
	public void updateOrder(Order order) throws DaoException {
		Connection connexion = null;
		PreparedStatement ps = null;

		try {
			connexion = daoFactory.getConnection();
			ps = DaoUtils.initializePreparedStatement(connexion, REQ_UPDATE_ORDER, false, order.getCustomer().getId(),
					order.getLabel(), order.getAdrEt(), order.getNumberOfDays(), order.getTva(), order.getStatus(),
					order.getType(), order.getNotes(), order.getId());
			int statut = ps.executeUpdate();

			if (statut == 0) {
				throw new DaoException("Echec de la modification de la commande, aucune ligne modifiée dans la table");
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DaoUtils.close(ps, connexion);
		}
	}

	@Override
	public void deleteOrder(Order order) throws DaoException {
		Connection connexion = null;
		PreparedStatement ps = null;

		try {
			connexion = daoFactory.getConnection();
			ps = DaoUtils.initializePreparedStatement(connexion, REQ_DEL_ORDER, false, order.getId());
			int statut = ps.executeUpdate();

			if (statut == 0) {
				throw new DaoException("Echec de la suppression de la commande, aucune ligne supprimée dans la table");
			} else {
				order.setId(null);
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DaoUtils.close(ps, connexion);
		}
	}

	private static Order map(ResultSet rs) throws SQLException {
		Order order = new Order(rs.getInt("id"), rs.getString("label"), rs.getDouble("adr_et"),
				rs.getDouble("number_of_days"), rs.getDouble("tva"), rs.getString("status"), rs.getString("type"),
				rs.getString("notes"), null);
		Customer customer = DaoFactory.getInstance().getCustomerDao().getCustomerById(rs.getInt("customer_id"));
		order.setCustomer(customer);

		return order;
	}

	private Order find(String requete, Object... objects) {
		Connection connexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Order order = new Order();

		try {
			connexion = daoFactory.getConnection();
			ps = DaoUtils.initializePreparedStatement(connexion, requete, false, objects);
			rs = ps.executeQuery();
			while (rs.next()) {
				order = map(rs);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DaoUtils.close(rs, ps, connexion);
		}
		return order;
	}
}
