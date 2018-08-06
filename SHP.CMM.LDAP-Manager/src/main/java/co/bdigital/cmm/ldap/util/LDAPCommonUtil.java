/**
 * 
 */
package co.bdigital.cmm.ldap.util;

/**
 * @author cristian.martinez@pragma.com.co
 * 
 *         Clase utilitaria.
 *
 */
public class LDAPCommonUtil {

    /**
     * Constructor Privado
     */
    private LDAPCommonUtil() {

    }

    /**
     * Metodo para concatenar String con un StringBuilder.
     * 
     * @param arg
     * @return String con los parámetros concatenados
     */
    public static String buildString(String... arg) {
        if (null != arg) {
            StringBuilder sb = new StringBuilder();
            for (String str : arg) {
                sb.append(str);
            }
            return sb.toString();
        }
        return null;
    }

}
