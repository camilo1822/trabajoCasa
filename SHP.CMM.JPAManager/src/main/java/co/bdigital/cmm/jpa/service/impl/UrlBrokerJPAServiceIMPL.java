package co.bdigital.cmm.jpa.service.impl;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.framework.WsProvider;
import co.bdigital.cmm.jpa.services.UrlBrokerJPAService;

/**
 * @author daniel.pareja@pragma.com.co
 *
 */
public class UrlBrokerJPAServiceIMPL implements UrlBrokerJPAService {

    public UrlBrokerJPAServiceIMPL() {

    }

    /**
     * Metodo que consulta datos del Broker
     */
    @Override
    public WsProvider getBrokerEndpoint(EntityManager em) throws JPAException {
        final String metodo = "getBrokerUrl";

        try {

            WsProvider wsProvider = em.find(WsProvider.class, "IIB");

            return wsProvider;

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

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
    @Override
    public WsProvider getWsProviderInfo(String wsServiceName, EntityManager em)
            throws JPAException {
        final String metodo = "getWsProviderInfo";

        try {

            WsProvider wsProvider = em.find(WsProvider.class, wsServiceName);

            return wsProvider;

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

}
