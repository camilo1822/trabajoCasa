package co.bdigital.cmm.jpa.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Cliente;

/**
 * @author john_perez
 *
 */
public interface ClientByPhoneNumberJPAService {

    /**
     * Consulta de cliente por el número de celular
     * 
     * @param phoneNumber
     * @param em
     * @return Cliente
     * @throws JPAException
     */
    public Cliente getClientByPhoneNumber(String phoneNumber, EntityManager em)
            throws JPAException;

    /**
     * Consulta de cliente por el número de celular y region
     * 
     * @param phoneNumber
     * @param em
     * @return Cliente
     * @param region
     * @throws JPAException
     */
    public Cliente getClientByPhoneNumberAndRegion(String phoneNumber,
            String region, EntityManager em) throws JPAException;

    /**
     * Consulta de cliente por la llave primaria <code>clienteId</code>.
     * 
     * @param clienteId
     * @param em
     * @return <code>Cliente</code>
     * @throws JPAException
     */
    public Cliente findClientByClienteId(Long clienteId, EntityManager em)
            throws JPAException;

    /**
     * Consulta de cliente por documentId
     * 
     * @param documentId
     * @param documentTypes
     * @param em
     * @return <code>Cliente</code>
     * @throws JPAException
     */
    public Cliente findClientByDocumentId(String documentId,
            String documentType, EntityManager em) throws JPAException;

    /**
     * Consulta de cliente por documentId y por region
     * 
     * @param documentId
     * @param documentTypes
     * @param region
     * @param em
     * @return <code>Cliente</code>
     * @throws JPAException
     */
    public Cliente findClientByDocumentIdAndRegion(String documentId,
            String documentType, String region, EntityManager em)
            throws JPAException;

    /**
     * Consulta de cliente por el number de celular sin usar caché L2.
     * 
     * @param phoneNumber
     * @param em
     * @return <code>Cliente</code>
     * @throws JPAException
     */
    public Cliente getClientByPhoneNumberNoCache(String phoneNumber,
            EntityManager em) throws JPAException;

    /**
     * Consulta de cliente por el CIFID
     * 
     * @param cifId
     * @param em
     * @return <code>Cliente</code>
     * @throws JPAException
     */
    public Cliente getClientByCifId(String cifId, EntityManager em)
            throws JPAException;

    /**
     * Método que retorna lista de clientes filtrados por fecha de vinculación
     * 
     * @param vinculationDate
     * @param em
     * @return <code>List<Cliente></code>
     * @throws JPAException
     */
    public List<Cliente> getClientByDate(Date vinculationDate, EntityManager em)
            throws JPAException;
}
