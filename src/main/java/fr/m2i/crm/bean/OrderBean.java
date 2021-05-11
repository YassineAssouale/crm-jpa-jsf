package fr.m2i.crm.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.ServletContext;

import fr.m2i.crm.dao.CustomerDao;
import fr.m2i.crm.dao.OrderDao;
import fr.m2i.crm.dao.impl.DaoFactory;
import fr.m2i.crm.exception.DaoException;
import fr.m2i.crm.model.Customer;
import fr.m2i.crm.model.Order;

@Named
@SessionScoped
public class OrderBean implements Serializable {

	private static final long serialVersionUID = 5902598786618585774L;

	private CustomerDao customerDao;

	private OrderDao orderDao;
	
	private Order order;	
	
	private Customer customer;
	
	private String customerChoice;
	
	private String customerId;
	
	/**
	 * Constructor
	 */
    public OrderBean() {
    	ServletContext servletContext = (ServletContext) FacesContext
			    .getCurrentInstance().getExternalContext().getContext();
		this.orderDao = ((DaoFactory) servletContext.getAttribute("daoFactory")).getOrderDao();
		this.customerDao = ((DaoFactory) servletContext.getAttribute("daoFactory")).getCustomerDao();
		this.order = new Order();
		this.customer = new Customer();
		this.customerChoice = "Yes";
    }

    /**
     * Creation of a customer
     */
    public void createOrder() {
    	System.out.println("createOrder !");
    	try {
    		if(customerChoice.equals("Yes")) {
    			customerDao.createCustomer(this.customer);
    	        FacesMessage messageCustomer = new FacesMessage("Création du client OK !");
    	        FacesContext.getCurrentInstance().addMessage(null, messageCustomer);
    		}
    		else {
    			customer=customerDao.getCustomerById(Integer.valueOf(customerId));
    		}
    		
	        order.setCustomer(this.customer); 
    		
    		orderDao.createOrder(order);
	        FacesMessage messageOrder = new FacesMessage("Création de la commande OK !");
	        FacesContext.getCurrentInstance().addMessage(null, messageOrder);
	        order = new Order();
    	} catch (DaoException e) {
    		FacesMessage message = new FacesMessage("Erreur lors de la création de la commande : " + e.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, message);
    	}
    }
    
    /**
     * Get the order
     * @return
     */
    public Order getOrder() {
        return order;
    }

    public Customer getCustomer() {
        return customer;
    }
    
    /**
     * Get list of orders
     * @return
     */
    public List<Order> getOrders() {
    	List<Order> orders = orderDao.getAllOrders();
    	return orders;
    }
    
    /**
     * Get list of customers
     * @return
     */
    public List<Customer> getCustomers() {
    	List<Customer> customers = customerDao.getAllCustomers();
    	return customers;
    }
    
    /**
     * Get choice
     * @return
     */
    public String getCustomerChoice() {
    	return customerChoice;
    }
 
    public void setCustomerChoice(String customerChoice) {
		this.customerChoice = customerChoice;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
     * Get customer Id
     * @return
     */
    public String getCustomerId() {
    	return customerId;
    }
    
    /**
     * Delete a customer
     * @param customer
     */
    public void delete(Order order) {
    	System.out.println("deleteOrder !");
    	try {
    		orderDao.deleteOrder(order);
	        FacesMessage message = new FacesMessage("Suppression de la commande OK !");
	        FacesContext.getCurrentInstance().addMessage(null, message);
    	} catch (DaoException e) {
    		FacesMessage message = new FacesMessage("Erreur lors de la suppression de la commande : " + e.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, message);
    	}
    }
    /**
     * Specific Phone validation
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void validatePhone(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	String inputValue = (String) value;
        if (!inputValue.matches("^\\d+$")) {
            FacesMessage msg = new FacesMessage("Mauvais format : le numéro de téléphone doit contenir uniquement des chiffres");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        } else if (inputValue.length() < 4) {
        	FacesMessage msg = new FacesMessage("Mauvais format : le numéro de téléphone doit contenir au moins 4 chiffres");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
