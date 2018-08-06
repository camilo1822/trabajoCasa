package co.bdigital.cmm.consumer.service.impl;

import javax.ws.rs.core.MediaType;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.ClientRuntimeException;

import co.bdigital.cmm.consumer.exception.RestClientException;
import co.bdigital.cmm.consumer.service.RestClient;
import co.bdigital.cmm.messaging.esb.RequestHeaderOutMessageType;
import co.bdigital.cmm.messaging.esb.ResponseHeaderOutMessageType;
import co.bdigital.shl.common.service.pojo.ResourcesPojo;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * @author ricardo.paredes
 *
 */
public class RestClientImpl implements RestClient {

    // Dirección en la cual se encuentran disponibles las operaciones del ws
    private static int DEFAULT_TIMEOUT = 25000;
    private String endPoint;
    // Tiempo (ms) limite para establecer conexión con un servicio
    private int connectTimeout = DEFAULT_TIMEOUT;
    // Tiempo (ms) limite para obtener la respuesta de un servicio
    private int requestTimeout = DEFAULT_TIMEOUT;
    private static RestClientImpl instance;
    // Logs para los RQ y RS
    private static CustomLogger logger;
    private static final String RESPONSE_BROKER = "[RESPONSE-BROKER: ";
    private static final String TIMEOUT_ERROR = "Broker TimeOut Error";
    private static final String PARSING_RESPONSE_ERROR_REQUIRED_FIELD_NOT_FOUND = "Parsing Broker Response Error: required field in response broker message not found.";
    private static final String PARSING_RESPONSE_ERROR_UNRECOGNIZED_FIELD_NOT_EXPECTED = "Parsing Broker Response Error: unrecognized field in response broker message was not expected.";
    private static final String CHARSET_UTF_8 = ";charset=UTF-8";

    /**
     * metodo para obtener instancia
     * 
     * @param locator
     * @return
     */
    public static RestClientImpl getInstance(ResourcesPojo locator) {
        if (null == instance) {
            instance = new RestClientImpl(locator);
        } else {
            if (null != locator.getBrokerReadTimeOut()) {
                instance.setRequestTimeout(
                        Integer.parseInt(locator.getBrokerReadTimeOut()));
            }
            if (null != locator.getBrokerConnectionTimeOut()) {
                instance.setConnectTimeout(
                        Integer.parseInt(locator.getBrokerConnectionTimeOut()));
            }
            if (null != locator.getBrokerUrl()
                    && !locator.getBrokerUrl().trim().isEmpty()) {
                instance.setEndPoint(locator.getBrokerUrl().trim());
            }
        }
        return instance;

    }

    /**
     * @return
     */
    public static RestClientImpl getInstance() {
        if (null == instance) {
            instance = new RestClientImpl();
        }
        return instance;
    }

    private RestClientImpl(ResourcesPojo locator) {
        logger = new CustomLogger(this.getClass());
        this.endPoint = locator.getBrokerUrl();
        if (null != locator.getBrokerConnectionTimeOut())
            this.connectTimeout = Integer
                    .parseInt(locator.getBrokerConnectionTimeOut());
        if (null != locator.getBrokerReadTimeOut())
            this.requestTimeout = Integer
                    .parseInt(locator.getBrokerReadTimeOut());

    }

    private RestClientImpl() {
        logger = new CustomLogger(this.getClass());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.bdigital.mdw.consumer.service.RestClient#executeOperation(co.bdigital
     * .mdw.messaging.esb.RequestHeaderOutMessageType)
     */
    @Override
    @Deprecated
    public ResponseHeaderOutMessageType executeOperation(
            RequestHeaderOutMessageType request) throws RestClientException {

        return (ResponseHeaderOutMessageType) sendRequest(request,
                ResponseHeaderOutMessageType.class, getEndPoint(),
                getConnectTimeout(), getRequestTimeout());
    }

    @Override
    public ResponseHeaderOutMessageType executeOperation(
            RequestHeaderOutMessageType request, String endPoint,
            String connectTimeout, String requestTimeout)
            throws RestClientException {

        int connectTimeoutInt = DEFAULT_TIMEOUT;
        int requestTimeoutInt = DEFAULT_TIMEOUT;

        try {
            connectTimeoutInt = Integer.parseInt(connectTimeout);
            requestTimeoutInt = Integer.parseInt(requestTimeout);
        } catch (Exception e) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR, TIMEOUT_ERROR, e);
        }

        return (ResponseHeaderOutMessageType) sendRequest(request,
                ResponseHeaderOutMessageType.class, endPoint, connectTimeoutInt,
                requestTimeoutInt);
    }

    /**
     * Cliente Rest hacia Broker
     * 
     * @param request
     * @param responseObjectClass
     * @param endPoint
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws RestClientException
     */
    private Object sendRequest(Object request, Class<?> responseObjectClass,
            String endPoint, int connectTimeout, int readTimeout)
            throws RestClientException {

        try {
            org.apache.wink.client.ClientConfig clientConfig = new org.apache.wink.client.ClientConfig();

            clientConfig.connectTimeout(connectTimeout);
            clientConfig.readTimeout(readTimeout);

            clientConfig.applications();
            org.apache.wink.client.RestClient client = new org.apache.wink.client.RestClient(
                    clientConfig);
            org.apache.wink.client.Resource resource = client
                    .resource(endPoint);

            ClientResponse response = (ClientResponse) resource
                    .contentType(MediaType.APPLICATION_JSON + CHARSET_UTF_8)
                    .accept(MediaType.WILDCARD).post(request);

            logger.debug(RESPONSE_BROKER + response);

            Object responseObject = response.getEntity(responseObjectClass);

            return responseObject;

        } catch (ClientRuntimeException ex) {
            if (ex.getCause() instanceof org.codehaus.jackson.map.JsonMappingException) {
                logger.error(ErrorEnum.BROKER_ERROR,
                        PARSING_RESPONSE_ERROR_REQUIRED_FIELD_NOT_FOUND, ex);
                throw new RestClientException(
                        PARSING_RESPONSE_ERROR_REQUIRED_FIELD_NOT_FOUND, ex);
            } else if (ex
                    .getCause() instanceof org.codehaus.jackson.map.exc.UnrecognizedPropertyException) {
                logger.error(ErrorEnum.BROKER_ERROR,
                        PARSING_RESPONSE_ERROR_UNRECOGNIZED_FIELD_NOT_EXPECTED,
                        ex);
                throw new RestClientException(
                        PARSING_RESPONSE_ERROR_UNRECOGNIZED_FIELD_NOT_EXPECTED,
                        ex);
            } else {
                logger.error(ErrorEnum.BROKER_ERROR, TIMEOUT_ERROR, ex);
                throw new RestClientException(TIMEOUT_ERROR, ex);
            }
        } catch (Exception ex) {
            logger.error(ErrorEnum.BROKER_ERROR, TIMEOUT_ERROR, ex);
            throw new RestClientException(TIMEOUT_ERROR, ex);
        }

    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

}
