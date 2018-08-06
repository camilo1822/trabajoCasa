/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.CifInformacion;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public interface CifinInformationJPAService {

    /**
     * Obtiene un entity <code>CifInformacion</code> en Base de Datos.
     * 
     * @param documentType
     * @param documentNumber
     * @param em
     * @throws JPAException
     */
    public CifInformacion getCifInformacion(String documentType,
            String documentNumber, EntityManager em) throws JPAException;

    /**
     * Inserta un entity <code>CifInformacion</code> en Base de Datos.
     * 
     * @param cifinInformacion
     * @param em
     * @throws JPAException
     */
    public void persistCifInformacion(CifInformacion cifinInformacion,
            EntityManager em) throws JPAException;

    /**
     * Actualiza un entity <code>CifInformacion</code> en Base de Datos.
     * 
     * @param cifinInformacion
     * @param em
     * @throws JPAException
     */
    public void mergeCifInformacion(CifInformacion cifinInformacion,
            EntityManager em) throws JPAException;

}
