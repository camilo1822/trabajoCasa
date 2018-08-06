/**
 * 
 */
package co.bdigital.cmm.jpa.util;

/**
 * <p>
 * Utilidades de proyecto JPA.
 * </p>
 * 
 * @author eduardo.altamar@pragma.com.co
 *
 */
public class JPAUtil {

    /**
     * <p>
     * Metodo que genera un string con la concatenación de todos los strings del
     * array <code>parameters</code>.
     * </p>
     * 
     * @param parameters
     * @return
     */
    public static String generateStringConcatenated(String... parameters) {
        StringBuilder trace = new StringBuilder();

        if (null != parameters
                && ConstantJPA.COMMON_INT_ZERO < parameters.length) {
            for (String string : parameters) {
                trace.append(string);
            }
        }

        return trace.toString();
    }

    /**
     * <p>
     * Constructor privado.
     * </p>
     */
    private JPAUtil() {

    }
}
