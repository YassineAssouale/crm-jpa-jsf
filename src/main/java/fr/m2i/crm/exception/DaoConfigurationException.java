package fr.m2i.crm.exception;

public class DaoConfigurationException extends RuntimeException {

	private static final long serialVersionUID = -6917898603307632397L;

	public DaoConfigurationException(String message) {
		super(message);
	}

	public DaoConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoConfigurationException(Throwable cause) {
		super(cause);
	}
}
