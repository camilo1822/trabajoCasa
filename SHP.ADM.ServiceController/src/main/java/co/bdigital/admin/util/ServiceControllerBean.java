package co.bdigital.admin.util;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;

import co.bdigital.admin.messaging.services.gmf.MessageRQ;
import co.bdigital.cmm.consumer.rest.util.RestClientUtil;
import co.bdigital.cmm.ejb.exception.MiddlewareException;
import co.bdigital.cmm.jpa.model.framework.WsProvider;
import co.bdigital.cmm.jpa.service.impl.UrlBrokerJPAServiceIMPL;
import co.bdigital.cmm.jpa.services.UrlBrokerJPAService;
import co.bdigital.shl.tracer.CustomLogger;
import co.nequi.message.integration.services.IntegrationRQ;
import co.nequi.message.registry.serviceregistry.RegistryRQ;
import co.nequi.message.registry.serviceregistry.RegistryRS;

/**
 * EJB ServiceLocator para realizar lookup a servicios REST
 * 
 * @author anders.barrios
 * @since 25/04/2017
 * @version 1.0.0
 */
@Singleton
public class ServiceControllerBean {

    private Integer connectTimeoutLookup;
    private Integer readTimeoutLookup;
    private String endPointServiceRegistry;
    private String authBasicUser;
    private String authBasicPwd;
    private static CustomLogger logger;
    private UrlBrokerJPAService urlBrokerJPAService;

    @PostConstruct
    void init() {
        logger = new CustomLogger(ServiceControllerBean.class);
        this.urlBrokerJPAService = new UrlBrokerJPAServiceIMPL();
    }

    /**
     * Método que consulta al serviceRegistry para resolver el endpoit del
     * servicio que se solicita
     * 
     * @param request
     * @param serviceName
     * @param operation
     * @param serviceRegion
     * @param serviceVersion
     * @return
     * @throws MiddlewareException
     */
    public co.nequi.message.registry.serviceregistry.RegistryRS lookup(
            IntegrationRQ request, String serviceName, String operation,
            String serviceRegion, String serviceVersion, EntityManager emFRM)
            throws MiddlewareException {

        try {

            if (null == connectTimeoutLookup || null == readTimeoutLookup
                    || null == endPointServiceRegistry || null == authBasicUser
                    || null == authBasicPwd) {
                WsProvider wsProvider = urlBrokerJPAService.getWsProviderInfo(
                        ConstantADM.SERVICE_REGISTRY_NAME, emFRM);

                connectTimeoutLookup = wsProvider.getWsConnectionTimeout()
                        .intValue();

                readTimeoutLookup = wsProvider.getWsReadTimeout().intValue();
                endPointServiceRegistry = WebConsoleUtil.getWSEndpoint(
                        wsProvider.getWsHost(),
                        wsProvider.getWsPort().intValue(),
                        wsProvider.getWsContext());
                authBasicPwd = wsProvider.getAuthBasicPwd();
                authBasicUser = wsProvider.getAuthBasicUser();
            }
            
            RegistryRQ registryRQ = WebConsoleUtil.getRegistryRQ(
                    request.getIntegrationRequest().getHeader(), serviceName,
                    operation, serviceRegion, serviceVersion);

            RegistryRS registryRS = (RegistryRS) RestClientUtil.sendRequest(
                    registryRQ, RegistryRS.class, endPointServiceRegistry,
                    authBasicUser, authBasicPwd, connectTimeoutLookup,
                    readTimeoutLookup, logger);

            return registryRS;
        } catch (Exception ex) {
            throw new MiddlewareException(ConstantADM.ERROR_MESSAGE_LOOKUP, ex);
        }
    }

    /**
     * Método que consulta al serviceRegistry para resolver el endpoit del
     * servicio que se solicita
     * 
     * @param request
     * @param serviceName
     * @param operation
     * @param serviceRegion
     * @param serviceVersion
     * @return
     * @throws MiddlewareException
     */
    public co.nequi.message.registry.serviceregistry.RegistryRS lookup(
            MessageRQ request, String serviceName, String operation,
            String serviceRegion, String serviceVersion, EntityManager emFRM)
            throws MiddlewareException {

        try {

            if (null == connectTimeoutLookup || null == readTimeoutLookup
                    || null == endPointServiceRegistry || null == authBasicUser
                    || null == authBasicPwd) {
                WsProvider wsProvider = urlBrokerJPAService.getWsProviderInfo(
                        ConstantADM.SERVICE_REGISTRY_NAME, emFRM);

                connectTimeoutLookup = wsProvider.getWsConnectionTimeout()
                        .intValue();

                readTimeoutLookup = wsProvider.getWsReadTimeout().intValue();
                endPointServiceRegistry = WebConsoleUtil.getWSEndpoint(
                        wsProvider.getWsHost(),
                        wsProvider.getWsPort().intValue(),
                        wsProvider.getWsContext());
                authBasicPwd = wsProvider.getAuthBasicPwd();
                authBasicUser = wsProvider.getAuthBasicUser();
            }

            RegistryRQ registryRQ = WebConsoleUtil.getRegistryRQ(
                    request.getRequestMessage().getHeader(), serviceName,
                    operation, serviceRegion, serviceVersion);

            RegistryRS registryRS = (RegistryRS) RestClientUtil.sendRequest(
                    registryRQ, RegistryRS.class, endPointServiceRegistry,
                    authBasicUser, authBasicPwd, connectTimeoutLookup,
                    readTimeoutLookup, logger);

            return registryRS;
        } catch (Exception ex) {
            throw new MiddlewareException(ConstantADM.ERROR_MESSAGE_LOOKUP, ex);
        }
    }

}
