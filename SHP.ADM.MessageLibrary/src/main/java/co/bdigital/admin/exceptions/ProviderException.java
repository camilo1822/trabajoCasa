/**
 * 
 */
package co.bdigital.admin.exceptions;

/**
 * @author ricardo.paredes
 *
 */
public class ProviderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2846121363054247322L;

	/**
	 * 
	 */
	public ProviderException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ProviderException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ProviderException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ProviderException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ProviderException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
