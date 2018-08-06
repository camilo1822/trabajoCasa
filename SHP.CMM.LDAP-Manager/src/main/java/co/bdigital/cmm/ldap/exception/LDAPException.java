/**
 * 
 */
package co.bdigital.cmm.ldap.exception;

/**
 * Clase de Mapeo de Excepciones de operaciones contra 
 * OpenLDAP
 * 
 * @author ricardo.paredes
 * @since 19/12/2015
 * @version 1.0
 *
 */
public class LDAPException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public LDAPException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public LDAPException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public LDAPException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LDAPException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public LDAPException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
