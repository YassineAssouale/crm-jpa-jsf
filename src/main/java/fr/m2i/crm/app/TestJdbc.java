package fr.m2i.crm.app;

import java.util.List;

import fr.m2i.crm.dao.UserDao;
import fr.m2i.crm.dao.impl.DaoFactory;
import fr.m2i.crm.model.User;

public class TestJdbc {

	public static void main(String[] args) throws Exception {

		DaoFactory daoFactory = DaoFactory.getInstance();
		/*
		 * //CUSTOMERS CustomerDao customerDao = daoFactory.getCustomerDao();
		 * 
		 * Customer customer = customerDao.getCustomerByLastname("GILBERT");
		 * System.out.println(customer.toString());
		 * 
		 * Customer customerToCreate = new Customer();
		 * customerToCreate.setFirstname("Obi-Wan");
		 * customerToCreate.setLastname("KENOBI"); customerToCreate.setCompany("Jedi");
		 * customerToCreate.setMail("obi-wan.kenobi@free.fr");
		 * customerToCreate.setPhone("0233445566");
		 * customerToCreate.setMobile("0677889933");
		 * customerToCreate.setNotes("notes..."); customerToCreate.setActive(false);
		 * customerDao.createCustomer(customerToCreate);
		 * 
		 * Customer customerToDelete = customerDao.getCustomerById(2);
		 * System.out.println(customer.toString());
		 * customerDao.deleteCustomer(customerToDelete);
		 * 
		 * customerToCreate.setId(1);
		 * customerToCreate.setCompany("Nouvelle entreprise");
		 * customerToCreate.setNotes("Mes notes");
		 * customerDao.updateCustomer(customerToCreate);
		 * 
		 * List<Customer> customers = customerDao.getAllCustomers(); for (Customer
		 * currentCustomer : customers) {
		 * System.out.println(currentCustomer.toString()); }
		 */

		/*
		 * //ORDERS OrderDao orderDao = daoFactory.getOrderDao(); List<Order> orders =
		 * orderDao.getAllOrders(); orders.forEach(param ->
		 * System.out.println(param.toString()));
		 * 
		 * System.out.println(orderDao.getById(2).toString());
		 * 
		 * Order order = new Order(); order.setAdrEt(450.0); order.setLabel("label");
		 * order.setNotes("notes"); order.setNumberOfDays(10.0);
		 * order.setStatus("status"); order.setTva(20.0); order.setType("type");
		 * 
		 * Customer customerToCreate = new Customer();
		 * customerToCreate.setFirstname("Obi-Wan");
		 * customerToCreate.setLastname("KENOBI"); customerToCreate.setCompany("Jedi");
		 * customerToCreate.setMail("obi-wan.kenobi@free.fr");
		 * customerToCreate.setPhone("0233445566");
		 * customerToCreate.setMobile("0677889933");
		 * customerToCreate.setNotes("notes..."); customerToCreate.setActive(false);
		 * customerToCreate.setId(1);
		 * 
		 * order.setCustomer(customerToCreate);
		 * 
		 * orderDao.createOrder(order);
		 * 
		 * Order orderToModify = new Order(); orderToModify.setAdrEt(450.0);
		 * //order.setCustomer(customer); orderToModify.setLabel("label2");
		 * orderToModify.setNotes("notes2"); orderToModify.setNumberOfDays(10.0);
		 * orderToModify.setStatus("status2"); orderToModify.setTva(20.0);
		 * orderToModify.setType("type2"); orderToModify.setCustomer(customerToCreate);
		 * orderToModify.setId(2); orderDao.updateOrder(orderToModify);
		 * 
		 * 
		 * Order orderToDelete = new Order(); orderToDelete.setId(23);
		 * orderDao.deleteOrder(orderToDelete);
		 * 
		 * CustomerDao customerDao = daoFactory.getCustomerDao(); List<Customer>
		 * customers = customerDao.getAllCustomers(); customers.forEach(param ->
		 * System.out.println(param.toString()));
		 */
		// USERS
		UserDao userDao = daoFactory.getUserDao();

		List<User> users = userDao.getAll();
		users.forEach(param -> System.out.println(param.toString()));

		System.out.println(userDao.getById(29).toString());

		System.out.println(userDao.getByUsername("fano2").toString());

		/*
		 * User userToCreate = new User(0, "username", "password", "test@test.fr");
		 * userDao.createUser(userToCreate);
		 */
		userDao.deleteUser(new User(34, "username", "", ""));

		User userToCreate = new User(31, "username2", "password2", "test2@test.fr");
		userDao.createUser(userToCreate);
	}
}
