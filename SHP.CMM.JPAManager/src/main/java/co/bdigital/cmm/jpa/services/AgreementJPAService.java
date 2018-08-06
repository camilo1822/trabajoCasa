/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Contrato;

/**
 * @author ricardo.paredes
 *
 */
public interface AgreementJPAService {

    /**
     * Consulta un contrato por el ID.
     * 
     * @param contractID
     * @return
     * @throws JPAException
     */
    public Contrato getContractByID(String contractID, String typeID,
            EntityManager em) throws JPAException;

    /**
     * <p>
     * Consulta de la última versión del contrato para el país especificado
     * </p>
     * 
     * @param countryCode
     *            País
     * @param em
     * @return Última versión de contrato para el país especificado.
     * @throws JPAException
     */
    public Contrato getLastContractVersionByRegionID(String countryCode,
            String typeId, EntityManager em) throws JPAException;

}
