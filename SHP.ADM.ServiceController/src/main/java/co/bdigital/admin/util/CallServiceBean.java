package co.bdigital.admin.util;

import co.bdigital.cmm.consumer.exception.RestClientException;
import co.bdigital.cmm.consumer.rest.util.RestClientUtil;
import co.bdigital.cmm.ejb.exception.MiddlewareException;
import co.bdigital.shl.tracer.CustomLogger;
import co.nequi.message.registry.serviceregistry.lookup.LookupResponseType;
import co.nequi.message.registry.serviceregistry.lookup.TimeoutType;

/**
 * 
 * 
 * @author anders.barrios
 * @version 1.0.0
 * @since 11/05/2017
 *
 */
public class CallServiceBean {

    /**
     * Método que ejecuta llamado a los componentes de negocio, mediante
     * endpoint suministrado por el service registry
     * 
     * @param request
     * @param registryRS
     * @param integrationRsClass
     * @param logger
     * @return Service response as {@link Object}
     * @throws MiddlewareException
     */
    protected Object callService(Object request, LookupResponseType registryRS,
            Class<?> integrationRsClass, CustomLogger logger)
            throws MiddlewareException {
        Object messageRS;

        try {

            TimeoutType timeoutType = registryRS.getTimeout();

            Integer timeOutConnection = Integer
                    .parseInt(timeoutType.getConnection());
            Integer readTimeOut = Integer.parseInt(timeoutType.getRead());

            String authBasicUser = registryRS.getEndpoint().getAuthBasicUser();
            String authBasicPwd = registryRS.getEndpoint().getAuthBasicPwd();

            /*
             * Se realiza llamado al componente de negocio a través de
             * restClient
             */
            messageRS = RestClientUtil.sendRequest(request, integrationRsClass,
                    WebConsoleUtil.getEndPoint(registryRS), authBasicUser,
                    authBasicPwd, timeOutConnection, readTimeOut, logger);
        } catch (RestClientException ex) {
            throw new MiddlewareException(ex);
        } catch (Exception ex) {
            throw new MiddlewareException(ex);
        }
        return messageRS;
    }
}