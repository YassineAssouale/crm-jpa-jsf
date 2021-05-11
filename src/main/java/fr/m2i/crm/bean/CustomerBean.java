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
import fr.m2i.crm.dao.impl.DaoFactory;
import fr.m2i.crm.exception.DaoException;
import fr.m2i.crm.model.Customer;

@Named
@SessionScoped
// Linking IHM & Business code of the app
public class CustomerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -826429283022955874L;
	
	private CustomerDao customerDao;
	
	private Customer customer;
	
	/**
	 * Constructor
	 */
    public CustomerBean() {
    	/*
    	 * Faces Servlet save the view in an FacesContext object
    	 * FacesContext contains the components tree of a view and associate errors
    	 * Instanciation daoFactory
    	 */
    	ServletContext servletContext = (ServletContext) FacesContext
			    .getCurrentInstance().getExternalContext().getContext();
		this.customerDao = ((DaoFactory) servletContext.getAttribute("daoFactory")).getCustomerDao();
		this.customer = new Customer();
    }

    /**
     * Creation of a customer
     */
    public void createCustomer() {
    	System.out.println("createCustomer !");
    	try {
	        customerDao.createCustomer(customer);
	        // FacesMessage allows to add a message with critical level
	        FacesMessage message = new FacesMessage("Création du client OK !");
	        /*
	         *  FacesContext contains the components tree of a view and associate errors
	         *   Used to add a FacesMessage (message) into curent context using addMessage method
	         *   Message will be displayed into view
	         *   Null to not attach our message to any existing component, it is a global message
	         */
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        customer = new Customer();
    	} catch (DaoException e) {
    		FacesMessage message = new FacesMessage("Erreur lors de la création du client : " + e.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, message);
    	}
    }
    
    /**
     * Get the customer
     * @return
     */
    public Customer getCustomer() {
        return customer;
    }
    
    /**
     * Specific Phone validation using validator interface implementation : validate() method 
     * tag validator into inc_customer_form view
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void validatePhone(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	// Retrieving the value to be processed from the parameter value
    	String inputValue = (String) value;
        if (!inputValue.matches("^\\d+$")) {
        	// FacesMessage allows to add a message with critical level with categorie defined by Severity
            FacesMessage msg = new FacesMessage("Mauvais format : le numéro de téléphone doit contenir uniquement des chiffres");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        } else if (inputValue.length() < 4) {
        	FacesMessage msg = new FacesMessage("Mauvais format : le numéro de téléphone doit contenir au moins 4 chiffres");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
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
     * Delete a customer
     * @param customer
     */
    public void delete(Customer customer) {
    	System.out.println("deleteCustomer !");
    	try {
	        customerDao.deleteCustomer(customer);
	        FacesMessage message = new FacesMessage("Suppression du client OK !");
	        FacesContext.getCurrentInstance().addMessage(null, message);
    	} catch (DaoException e) {
    		FacesMessage message = new FacesMessage("Erreur lors de la suppression du client : " + e.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, message);
    	}
    }

}
