/**
 * 
 */
package co.bdigital.cmm.ejb.util;

/**
 * @author cristian.martinez@pragma.com.co
 * 
 *         Clase utilitaria para EJB <code>ServiceLocatorBean</code> que se
 *         encuentra en la FACHADA.
 *
 */
public class ServiceLocatorUtil {

    /**
     * Constructor Privado
     */
    private ServiceLocatorUtil() {

    }

    /**
     * Metodo que construye la KEY para el CACHE de las instancias de los EJBs
     * remotos.
     * 
     * @param nameDomain
     * @param nameOperation
     * @param idRegion
     * @param versionDomain
     * @return <code>String</code>
     */
    public static String getKeyCache(String nameDomain, String nameOperation,
            String idRegion, String versionDomain) {

        StringBuilder keyCache;

        keyCache = new StringBuilder(nameDomain);

        keyCache.append(nameOperation);
        keyCache.append(idRegion);
        keyCache.append(versionDomain);

        return keyCache.toString();

    }

}
