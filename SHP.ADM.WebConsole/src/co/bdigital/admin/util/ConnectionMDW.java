package co.bdigital.admin.util;

import co.bdigital.admin.messaging.services.amazonS3.IntegrationRS;
import co.bdigital.cmm.jpa.model.framework.WsProvider;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

import com.nequi.cmm.bussines.MiddlewareException;
import com.nequi.cmm.general.CallServiceBean;
import com.nequi.cmm.messaging.general.ResponseMessageObjectType;

public class ConnectionMDW extends CallServiceBean {

    /** Logger **/
    private static CustomLogger logger = new CustomLogger(ConnectionMDW.class);

    /**
     * Metodo que permite llamar al servicio de cierre de cuenta en Middleware.
     * 
     * @param port
     * @param connectionTimeout
     * @param readTimeout
     * @param authUser
     * @param authPass
     * @param context
     * @param middlewareCloseAccountRequest
     * @param protocol
     * @param host
     * @return {@link ResponseMessageObjectType}
     */
    public ResponseMessageObjectType callMiddleware(WsProvider wsProvider,
            Object middlewareCloseAccountRequest, String serviceName,
            String serviceOperation) {

        StringBuilder context = new StringBuilder();
        context.append(wsProvider.getWsContext());

        /* Se verifica si el conext comienza NO comienza con SLASH */
        if (!wsProvider.getWsContext().startsWith(
                ConstantADM.WORD_SLASH_SPLIT_URL)) {
            context.append(ConstantADM.WORD_SLASH_SPLIT_URL);
        }

        context.append(serviceName);
        context.append(ConstantADM.WORD_SLASH_SPLIT_URL);
        context.append(serviceOperation);

        String[] providerHost = wsProvider.getWsHost().split(
                ConstantADM.PROVIDER_HOST_DELIMITER);

        String protocol = providerHost[ConstantADM.INDEX_PROTOCOL_PROVIDER_TOKEN];
        String host = providerHost[ConstantADM.INDEX_HOST_PROVIDER_TOKEN];

        try {
            return (ResponseMessageObjectType) callService(
                    middlewareCloseAccountRequest, protocol, host, wsProvider
                            .getWsPort().toPlainString(), context.toString(),
                    wsProvider.getWsConnectionTimeout().toPlainString(),
                    wsProvider.getWsReadTimeout().toPlainString(),
                    wsProvider.getAuthBasicUser(),
                    wsProvider.getAuthBasicPwd(),
                    ResponseMessageObjectType.class, logger);
        } catch (MiddlewareException e) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    ConstantADM.ERROR_MESSAGE_REST_COMMUNICATION, e);
        }
        return null;
    }

    /**
     * Metodo que permite llamar al servicio de cierre de cuenta en Middleware.
     * 
     * @param port
     * @param connectionTimeout
     * @param readTimeout
     * @param authUser
     * @param authPass
     * @param context
     * @param middlewareCloseAccountRequest
     * @param protocol
     * @param host
     * @return {@link IntegrationRS}
     */
    public IntegrationRS callIntegrationServices(WsProvider wsProvider,
            Object middlewareCloseAccountRequest, String serviceName,
            String serviceOperation) {

        StringBuilder context = new StringBuilder();
        context.append(wsProvider.getWsContext());

        /* Se verifica si el conext comienza NO comienza con SLASH */
        if (!wsProvider.getWsContext().startsWith(
                ConstantADM.WORD_SLASH_SPLIT_URL)) {
            context.append(ConstantADM.WORD_SLASH_SPLIT_URL);
        }

        context.append(serviceName);
        context.append(ConstantADM.WORD_SLASH_SPLIT_URL);
        context.append(serviceOperation);

        String[] providerHost = wsProvider.getWsHost().split(
                ConstantADM.PROVIDER_HOST_DELIMITER);

        String protocol = providerHost[ConstantADM.INDEX_PROTOCOL_PROVIDER_TOKEN];
        String host = providerHost[ConstantADM.INDEX_HOST_PROVIDER_TOKEN];

        try {
            return (IntegrationRS) callService(middlewareCloseAccountRequest,
                    protocol, host, wsProvider.getWsPort().toPlainString(),
                    context.toString(), wsProvider.getWsConnectionTimeout()
                            .toPlainString(), wsProvider.getWsReadTimeout()
                            .toPlainString(), wsProvider.getAuthBasicUser(),
                    wsProvider.getAuthBasicPwd(), IntegrationRS.class, logger);
        } catch (MiddlewareException e) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    ConstantADM.ERROR_MESSAGE_REST_COMMUNICATION, e);
        }
        return null;
    }

}
