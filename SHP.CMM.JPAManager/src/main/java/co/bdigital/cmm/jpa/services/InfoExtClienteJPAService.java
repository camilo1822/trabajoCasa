/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.InformacionExternaCliente;
import co.bdigital.cmm.jpa.model.Sistema;

/**
 * @author juan.arboleda@pragma.com.co
 *
 */
public interface InfoExtClienteJPAService {

    /**
     * Obtiene informacion de un cliente de la tabla SIS_PERSONA
     * 
     * @param documentId
     * @param em
     * @throws JPAException
     */
    public InformacionExternaCliente getPersonByDocumentId(String documentId,
            EntityManager em) throws JPAException;

    /**
     * Guardar registro en la tabla SIS_PERSONA
     * 
     * @param persona
     * @param em
     * @throws JPAException
     */
    public void saveInformacionExternaCliente(InformacionExternaCliente person,
            EntityManager em) throws JPAException;

    /**
     * Retorna sistema por id
     * 
     * @param sistemaId
     * @param em
     * @throws JPAException
     */
    public Sistema getSistemaById(String sistemaId, EntityManager em)
            throws JPAException;

}
