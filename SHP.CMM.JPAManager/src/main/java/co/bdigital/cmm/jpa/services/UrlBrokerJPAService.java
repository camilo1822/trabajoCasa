/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.framework.WsProvider;

/**
 * @author daniel.pareja@pragma.com.co
 *
 */
public interface UrlBrokerJPAService {

    /**
     * Consulta url para conexion con broker.
     * 
     * @param em
     * @return
     * @throws JPAException
     */
    public WsProvider getBrokerEndpoint(EntityManager em) throws JPAException;

    /**
     * <p>
     * M&eacute;todo que consulta los datos del proveedor por nombre.
     * </p>
     * 
     * @param wsServiceName
     *            nombre del nombre.
     * @param em
     * @return WsProvider
     * @throws JPAException
     */
    public WsProvider getWsProviderInfo(String wsServiceName, EntityManager em)
            throws JPAException;

}
