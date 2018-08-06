package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Cliente;

/**
 * @author juan.arboleda
 *
 */
public interface ClientByDocumentJPAService {

    /**
     * Consulta de cliente por documentId
     * 
     * @param documentId
     * @param documedocumentTypentId
     * @param region
     * @param em
     * @return <code>Cliente</code>
     * @throws JPAException
     */
    public Cliente findClientByDocumentId(String documentId, String region,
            EntityManager em) throws JPAException;

    /**
     * Consulta de cliente con cuenta cerrada
     * 
     * @param documentId
     * @param documedocumentTypentId
     * @param region
     * @param em
     * @return <code>Cliente</code>
     * @throws JPAException
     */
    public Cliente findClientCloseAcountByDocumentId(String documentId,
            String region, EntityManager em) throws JPAException;

    /**
     * Consulta de cliente por numero de cuenta
     * 
     * @param accountNumber
     * @param em
     * @return
     * @throws JPAException
     */
    public Cliente getClientByAccountNumber(String accountNumber, String region,
            EntityManager em) throws JPAException;

}
