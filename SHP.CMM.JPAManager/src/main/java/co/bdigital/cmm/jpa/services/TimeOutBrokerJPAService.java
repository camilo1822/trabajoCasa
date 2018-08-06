/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.framework.ServiceOperation;
import co.bdigital.cmm.jpa.model.framework.ServiceVersion;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public interface TimeOutBrokerJPAService {

    /**
     * Consulta el TimeOut de los servicios de Broker
     * 
     * @param em
     * @return
     * @throws JPAException
     */
    public ServiceVersion getTimeOut(EntityManager em, String namespace)
            throws JPAException;

    /**
     * Consulta el TimeOut de las operaciones de Broker
     * 
     * @param em
     * @param name
     * @param operation
     * @return
     * @throws JPAException
     */
    public ServiceOperation getOperationTimeOut(EntityManager em, String name,
            String operation) throws JPAException;

}
