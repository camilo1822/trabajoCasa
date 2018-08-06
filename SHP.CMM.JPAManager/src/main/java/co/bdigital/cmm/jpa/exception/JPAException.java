/**
 * 
 */
package co.bdigital.cmm.jpa.exception;

/**
 * @author ricardo.paredes
 *
 */
public class JPAException extends Exception {

	
	private static StringBuilder errorType;
	private static final String DATABASE_OPERATION_ERROR = "[DATABASE OPERATION ERROR]:";
	
	
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;






	

	/**
	 * 
	 */
	public JPAException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public JPAException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public JPAException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public JPAException(EnumJPAException tipo ,String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	/**
	 * @param message
	 * @param cause
	 */
	public JPAException(String message, Throwable cause) {
		super(message, cause);
		errorType = new StringBuilder(DATABASE_OPERATION_ERROR);
		errorType.append(message);
	}
	
	/**
	 * Retorn el error que se construyó en el Constructor
	 * @return
	 */
	public String getErrorDesc(){
		return errorType.toString();
	}
	

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public JPAException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
