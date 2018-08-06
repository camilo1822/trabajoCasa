/**
 * 
 */
package co.bdigital.cmm.ejb.exception;

/**
 * @author ricardo.paredes
 *
 */
public class MiddlewareException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -384200230604181591L;

    /**
     * Constructor
     */
    public MiddlewareException() {
    }

    /**
     * @param message
     */
    public MiddlewareException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public MiddlewareException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public MiddlewareException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public MiddlewareException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
