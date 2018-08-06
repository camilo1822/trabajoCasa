package co.bdigital.cmm.consumer.rest.util;

import java.net.SocketTimeoutException;

import javax.ws.rs.core.MediaType;

import org.apache.wink.client.ClientConfig;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.ClientRuntimeException;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.apache.wink.client.handlers.BasicAuthSecurityHandler;

import co.bdigital.cmm.consumer.exception.RestClientException;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

public class RestClientUtil {

    private static final String TIMEOUT_ERROR = "Rest TimeOut Error";
    private static final String END_POINT = "END POINT:";
    private static final String CAUSE = "CAUSE:";

    private static final String RESPONSE_MIDDLEWARE = "[RESPONSE-REST: ";
    private static final String PARSING_RESPONSE_ERROR_REQUIRED_FIELD_NOT_FOUND = "Parsing Rest Response Error: required field in response Rest message not found.";
    private static final String COMMON_STRING_EMPTY_STRING = "";
    private static final String INTERNAL_ERROR_REST_CLIENT = "Error Rest_Client: Ocurrió un error al ejectutar RestClientUtil";

    public static Object sendRequest(Object request,
            Class<?> responseObjectClass, String endPoint, CustomLogger logger)
            throws RestClientException {

        try {

            logger.info(END_POINT + endPoint);

            org.apache.wink.client.ClientConfig clientConfig = new org.apache.wink.client.ClientConfig();

            // clientConfig.connectTimeout(connectTimeout);
            // clientConfig.readTimeout(requestTimeout);

            clientConfig.applications();
            org.apache.wink.client.RestClient client = new org.apache.wink.client.RestClient(
                    clientConfig);
            org.apache.wink.client.Resource resource = client
                    .resource(endPoint);

            ClientResponse response = (ClientResponse) resource
                    .contentType("application/json").accept("*/*")
                    .post(request);
            logger.debug(RESPONSE_MIDDLEWARE + response);
            Object responseObject = response.getEntity(responseObjectClass);
            return responseObject;
        } catch (ClientRuntimeException ex) {

            logger.info(CAUSE + ex.getCause());

            if (ex.getCause() instanceof org.codehaus.jackson.map.JsonMappingException) {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        PARSING_RESPONSE_ERROR_REQUIRED_FIELD_NOT_FOUND, ex);
                throw new RestClientException(
                        PARSING_RESPONSE_ERROR_REQUIRED_FIELD_NOT_FOUND, ex);
            } else {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR, TIMEOUT_ERROR, ex);
                throw new RestClientException(TIMEOUT_ERROR, ex);
            }
        } catch (Exception ex) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR, TIMEOUT_ERROR, ex);
            throw new RestClientException(TIMEOUT_ERROR, ex);
        }

    }

    /**
     * Método para consumir un servicio Rest, este metodo realiza autenticación
     * Basic si se poseen las credenciales
     * 
     * @param request
     * @param responseObjectClass
     * @param endPoint
     * @param authBasicUser
     * @param authBasicPwd
     * @param connectTimeout
     * @param readTimeout
     * @param logger
     * @return
     * @throws RestClientException
     */
    public static Object sendRequest(Object request,
            Class<?> responseObjectClass, String endPoint, String authBasicUser,
            String authBasicPwd, Integer connectTimeout, Integer readTimeout,
            CustomLogger logger) throws RestClientException {

        Resource resourceContentType;
        Resource resourceAccept;

        try {

            logger.info(END_POINT + endPoint);

            ClientConfig clientConfig = new ClientConfig();

            clientConfig.connectTimeout(connectTimeout);
            clientConfig.readTimeout(readTimeout);
            clientConfig.applications();

            /* Se valida si requiere autenticación */
            if (null != authBasicUser
                    && !COMMON_STRING_EMPTY_STRING.equals(authBasicUser)
                    && null != authBasicPwd
                    && !COMMON_STRING_EMPTY_STRING.equals(authBasicPwd)) {
                BasicAuthSecurityHandler basicAuthHandler = new BasicAuthSecurityHandler();
                basicAuthHandler.setUserName(authBasicUser);
                basicAuthHandler.setPassword(authBasicPwd);
                clientConfig.handlers(basicAuthHandler);
            }

            RestClient client = new RestClient(clientConfig);

            org.apache.wink.client.Resource resource = client
                    .resource(endPoint);

            resourceContentType = resource
                    .contentType(MediaType.APPLICATION_JSON);

            resourceAccept = resourceContentType.accept(MediaType.WILDCARD);

            ClientResponse response = (ClientResponse) resourceAccept
                    .post(request);

            logger.debug(RESPONSE_MIDDLEWARE + response);

            Object responseObject = response.getEntity(responseObjectClass);

            return responseObject;
        } catch (ClientRuntimeException ex) {

            logger.info(CAUSE + ex.getCause());

            if (ex.getCause() instanceof org.codehaus.jackson.map.JsonMappingException) {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        PARSING_RESPONSE_ERROR_REQUIRED_FIELD_NOT_FOUND, ex);
                throw new RestClientException(
                        PARSING_RESPONSE_ERROR_REQUIRED_FIELD_NOT_FOUND, ex);
            } else if (null != ex.getCause() && ex.getCause()
                    .getCause() instanceof SocketTimeoutException) {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR, TIMEOUT_ERROR, ex);
                throw new RestClientException(TIMEOUT_ERROR, ex);
            } else {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        INTERNAL_ERROR_REST_CLIENT, ex);
                throw new RestClientException(INTERNAL_ERROR_REST_CLIENT, ex);
            }

        } catch (Exception ex) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR, INTERNAL_ERROR_REST_CLIENT,
                    ex);
            throw new RestClientException(INTERNAL_ERROR_REST_CLIENT, ex);
        }

    }

}
