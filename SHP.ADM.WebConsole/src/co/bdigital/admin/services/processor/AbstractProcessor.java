/**
 * 
 */
package co.bdigital.admin.services.processor;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import co.bdigital.cmm.ejb.exception.MiddlewareException;
import co.bdigital.cmm.ejb.generic.ServiceController;
import co.bdigital.cmm.messaging.esb.BodyType;
import co.bdigital.cmm.messaging.general.DestinationType;
import co.bdigital.cmm.messaging.general.RequestHeader;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseBody;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;

/**
 * @author ricardo.paredes
 *
 */
public abstract class AbstractProcessor {

    private static final String ERROR_PARSING = "Parsing Error:";

    /**
     * 
     * @param request
     * @return
     */
    public abstract ResponseMessageObjectType processOperation(
            RequestMessageObjectType request, ServiceController serviceBean);

    // /**
    // *
    // * @param invalidRequest
    // * @param transactionService
    // * @return
    // */
    // public ResponseMessageObjectType getErrorResponse(
    // EnumErrorType invalidRequest, String transactionService) {
    // // TODO Auto-generated method stub
    // return null;
    // }

    public boolean validateRequest(RequestMessageObjectType request) {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * 
     * @param body
     * @return
     */
    protected ResponseBody mappingResponseBody(BodyType body) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setAny(body.getAny());
        return responseBody;
    }

    /**
     * Método q u¿
     * 
     * 
     * @param requestHeader
     * @param serviceName
     * @param serviceOperation
     */
    public RequestHeader setHeaderOperation(RequestHeader requestHeader,
            String serviceName, String serviceOperation) {

        if (requestHeader.getDestination() == null) {

            DestinationType destination = new DestinationType();
            destination.setServiceName(serviceName);
            destination.setServiceOperation(serviceOperation);
            requestHeader.setDestination(destination);
        }
        return requestHeader;
    }

    /**
     * método para parsear payload Any al Objeto del segundo parametro
     * 
     * @param any
     * @param class1
     * @return
     * @throws MiddlewareException
     */
    public Object parsePayload(Object any, Object clase)
            throws MiddlewareException {

        @SuppressWarnings("unchecked")
        Map<String, String> anyMap = (Map<String, String>) any;
        JSONObject jsonString = new JSONObject(anyMap);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString.toString(), clase.getClass());
        } catch (IOException e) {
            StringBuilder errorMessage = new StringBuilder(ERROR_PARSING);
            errorMessage.append(clase.getClass().getName());
            throw new MiddlewareException(errorMessage.toString(), e);
        }

    }

}
