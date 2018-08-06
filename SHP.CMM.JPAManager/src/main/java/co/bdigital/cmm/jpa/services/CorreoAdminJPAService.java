/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.CorreoAdmin;

/**
 * @author hansel.ospino@pragma.com.co
 *
 */
public interface CorreoAdminJPAService {

    /**
     * <p>
     * Consulta de los correos administrativos activos.
     * </p>
     * 
     * @param em
     * @return
     * @throws JPAException
     */
    public List<CorreoAdmin> getAllActiveCorreoAdmin(EntityManager em)
            throws JPAException;

    /**
     * <p>
     * Consulta para obtener los correos administrativos por tipo.
     * </p>
     * 
     * @param type
     *            columna 'TIPO' de la tabla <code>CORREO_ADMIN</code>.
     * @param countryCode
     * @param em
     * @return
     * @throws JPAException
     */
    public List<CorreoAdmin> getAllActiveCorreoAdminByType(String type,
            String countryCode, EntityManager em) throws JPAException;

}
