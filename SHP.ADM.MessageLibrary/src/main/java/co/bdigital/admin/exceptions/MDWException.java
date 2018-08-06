/**
 * 
 */
package co.bdigital.admin.exceptions;

/**
 * @author ricardo.paredes
 *
 */
public class MDWException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3747520207136249313L;

	/**
	 * 
	 */
	public MDWException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public MDWException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public MDWException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MDWException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MDWException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
