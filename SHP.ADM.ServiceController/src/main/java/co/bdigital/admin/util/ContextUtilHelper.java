/**
 * 
 */
package co.bdigital.admin.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import co.bdigital.shl.tracer.CustomLogger;

/**
 * @author ricardo.paredes
 *
 */
public class ContextUtilHelper {

    private static final String ENV = "java:comp/env";
    private static final String ERROR_CONFIG = "Config Error : Environment entries failure";
    private static CustomLogger logger;
    private static ContextUtilHelper instance;

    /**
     * Devuelve una instancia de la clase ContextUtilHelper
     * 
     * @return ContextUtilHelper
     */
    public static ContextUtilHelper getInstance() {
        if (instance == null)
            instance = new ContextUtilHelper();

        return instance;
    }

    /**
	 * 
	 */
    private ContextUtilHelper() {
        logger = new CustomLogger(this.getClass());
    }

    /**
     * Devuelve un Valor de Environment Entrie (web.xml)
     * 
     * @param variable
     * @return
     */
    public boolean readBooleanVariable(String variable) {
        Context envEntryContext = null;
        InitialContext initialContext = null;
        try {
            initialContext = new InitialContext();
            envEntryContext = (Context) initialContext.lookup(ENV);
            return (Boolean) envEntryContext.lookup(variable);
        } catch (NamingException e) {
            logger.error(ERROR_CONFIG, e);
            return false;
        } finally {
            this.closeContext(envEntryContext);
            this.closeContext(initialContext);
        }
    }

    /**
     * <p>
     * M&eacute;todo que cierra el contexto.
     * </p>
     * 
     * @param context
     *            contexto a cerrar.
     */
    private void closeContext(Context context) {
        try {
            if (null != context) {
                context.close();
            }
        } catch (NamingException e) {
            logger.error(ERROR_CONFIG, e);
        }
    }

}
