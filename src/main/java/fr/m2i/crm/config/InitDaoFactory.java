package fr.m2i.crm.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import fr.m2i.crm.dao.impl.DaoFactory;

public class InitDaoFactory implements ServletContextListener {
	
	private static final String ATT_DAO_FACTORY = "daoFactory";

	private DaoFactory daoFactory;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		//Récupération du ServletContext lors du chargement de l'application
		ServletContext servletContext = event.getServletContext();
		// Instanciation de la DaoFactory
		this.daoFactory = DaoFactory.getInstance();
		// Enregistrement dans un attribut ayant pour portée toute l'application
		servletContext.setAttribute(ATT_DAO_FACTORY, this.daoFactory);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// Rien à réaliser lors de la fermeture de l'application
	}
}