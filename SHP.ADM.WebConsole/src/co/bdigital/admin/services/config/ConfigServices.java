/**
 * 
 */
package co.bdigital.admin.services.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * @author hansel.ospino Clase de configuración de los Servicios Rest de la web
 *         administrativa.
 *
 */
public class ConfigServices extends Application {

    /**
     * Metodo para agregar Interfaces de Servicios Rest. Se deben agregar las
     * clases que expongan servicios Rest
     */
    public Set<Class<?>> getClasses() {

        Set<Class<?>> classes = new HashSet<Class<?>>();

        classes.add(co.bdigital.admin.services.usermanagementservices.UserManagementServices.class);

        return classes;
    }
}
