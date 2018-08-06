/**
 * 
 */
package co.bdigital.cmm.consumer.service;

import co.bdigital.cmm.consumer.exception.RestClientException;
import co.bdigital.cmm.messaging.esb.RequestHeaderOutMessageType;
import co.bdigital.cmm.messaging.esb.ResponseHeaderOutMessageType;

/**
 * @author ricardo.paredes Interfaz de cliente Rest
 */
public interface RestClient {

    /**
     * Interfaz para request hacia Broker
     * 
     * @param request
     * @return
     * @throws RestClientException
     */
    @Deprecated
    public ResponseHeaderOutMessageType executeOperation(
            RequestHeaderOutMessageType request) throws RestClientException;

    /**
     * Interfaz para request hacia Broker con parametros de Timeout
     * 
     * @param request
     * @param endPoint
     * @param connectTimeout
     * @param requestTimeout
     * @return
     * @throws RestClientException
     */
    public ResponseHeaderOutMessageType executeOperation(
            RequestHeaderOutMessageType request, String endPoint,
            String connectTimeout, String requestTimeout)
            throws RestClientException;

}
