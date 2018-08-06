/**
 * 
 */
package co.bdigital.cmm.ejb.util;

import java.util.Arrays;
import java.util.Map;

import org.json.JSONObject;

import com.ibm.xml.crypto.util.Base64;

import co.bdigital.cmm.ejb.exception.MiddlewareException;
import co.bdigital.cmm.ejb.generic.ServiceControllerHelper;
import co.bdigital.cmm.messaging.esb.RequestHeaderOutMessageType;
import co.bdigital.cmm.messaging.esb.ResponseHeaderOutMessageType;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;
import co.bdigital.shl.tracer.CustomLogger;

/**
 * @author cristian.martinez@pragma.com.co
 *
 */
public class MessageTracerUtil {

    /**
     * Constructor Privado
     */
    private MessageTracerUtil() {

    }

    /**
     * Método utilidad para trazar los JSON de Respuesta
     * 
     * @param response
     * @param logger
     * @param keyLog
     *            : Variable que se utiliza para iniciar el LOG.
     * @param valueLog
     *            : El valor que se desea colocar despues de la variable
     *            <code>keyLog</code>.
     */
    public static void traceResponseMessage(ResponseMessageObjectType response,
            CustomLogger logger, String keyLog, String valueLog) {

        if ((logger.getLogger().isInfoEnabled()) && (logger.getIsTraceable())) {

            StringBuilder traceInfo = MessageTracerUtil.buildResponseTraceInfo(
                    response, Boolean.FALSE);

            if (null != keyLog) {

                traceInfo.append(keyLog);
                traceInfo.append(valueLog + Constant.CLOSE);
            }

            logger.info(traceInfo.toString());

        }
    }

    /**
     * Método que forma una traza de Request Message y la registra en el log si
     * esta habilitado
     * 
     * @param request
     * @param logger
     */
    public static void traceRequestMessage(RequestMessageObjectType request,
            CustomLogger logger) {

        if (logger.getIsTraceable()) {

            StringBuilder traceInfo = MessageTracerUtil.buildRequestTraceInfo(
                    request, Boolean.FALSE);

            logger.info(traceInfo.toString());
        }

    }

    /**
     * Método que forma una traza de Request Message y la registra en el log si
     * esta habilitado para mensaje muy largos
     * 
     * @param request
     * @param logger
     */
    public static void traceRequestLargeMessage(
            RequestMessageObjectType request, CustomLogger logger) {

        if (logger.getIsTraceable()) {

            StringBuilder traceInfo = MessageTracerUtil.buildRequestTraceInfo(
                    request, Boolean.TRUE);

            logger.info(traceInfo.toString());
        }
    }

    /**
     * Método para trazar los JSON de Respuesta
     * 
     * @param response
     * @param logger
     */
    public static void traceResponseMessage(ResponseMessageObjectType response,
            CustomLogger logger) {

        if (logger.getIsTraceable()) {

            StringBuilder traceInfo = buildResponseTraceInfo(response,
                    Boolean.FALSE);

            logger.info(traceInfo.toString());
        }
    }

    /**
     * Método para trazar los JSON de Respuesta muy largas.
     * 
     * @param response
     * @param logger
     */
    public static void traceResponseLargeMessage(
            ResponseMessageObjectType response, CustomLogger logger) {

        if (logger.getIsTraceable()) {

            StringBuilder traceInfo = buildResponseTraceInfo(response,
                    Boolean.TRUE);

            logger.info(traceInfo.toString());
        }
    }

    /**
     * <p>
     * M&eacute;todo que construye la traza para el log del response.
     * </p>
     * 
     * @param response
     * @param largeMessage
     *            flag que indica que es un response con un body muy largo y que
     *            debe ser recortado para agregarlo a la traza del log.
     * @return texto con la traza construida.
     */
    private static StringBuilder buildResponseTraceInfo(
            ResponseMessageObjectType response, boolean largeMessage) {
        StringBuilder traceInfo = new StringBuilder(Constant.RESPONSE_INIT);
        traceInfo.append(response.getResponseMessage().getResponseHeader()
                .getDestination().getServiceName());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.STATUSCODE);
        traceInfo.append(response.getResponseMessage().getResponseHeader()
                .getStatus().getStatusCode());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.MESSAGEID);
        traceInfo.append(response.getResponseMessage().getResponseHeader()
                .getMessageID());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.CLIENTID);
        traceInfo.append(response.getResponseMessage().getResponseHeader()
                .getClientID());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.OPERATION);
        traceInfo.append(response.getResponseMessage().getResponseHeader()
                .getDestination().getServiceOperation());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.RSDATE);
        traceInfo.append(response.getResponseMessage().getResponseHeader()
                .getResponseDate());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.RSREGION);
        traceInfo.append(response.getResponseMessage().getResponseHeader()
                .getDestination().getServiceRegion());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.RSVERSION);
        traceInfo.append(response.getResponseMessage().getResponseHeader()
                .getDestination().getServiceVersion());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.PAYLOAD_RS);
        if (response.getResponseMessage().getResponseBody() != null
                && response.getResponseMessage().getResponseBody().getAny() != null) {

            String any = response.getResponseMessage().getResponseBody()
                    .getAny().toString();
            if (largeMessage
                    && any.length() > Constant.COMMON_INT_TRACE_MESSAGE_ANY_MAX_LENGTH)
                any = any.substring(0,
                        Constant.COMMON_INT_TRACE_MESSAGE_ANY_MAX_LENGTH);

            traceInfo.append(Base64.encode(any.getBytes()));
            
            traceInfo.append(Constant.CLOSE);
        } else {
            traceInfo.append(Constant.NO_BODY);
        }
        return traceInfo;
    }

    /**
     * <p>
     * M&eacute;todo que construye la traza para el log del request.
     * </p>
     * 
     * @param request
     * @param largeMessage
     *            flag que indica que es un request con un body muy largo y que
     *            debe ser recortado para agregarlo a la traza del log.
     * @return texto con la traza construida.
     */
    private static StringBuilder buildRequestTraceInfo(
            RequestMessageObjectType request, boolean largeMessage) {
        StringBuilder traceInfo = new StringBuilder(Constant.REQUEST_INIT);
        traceInfo.append(request.getRequestMessage().getRequestHeader()
                .getDestination().getServiceName());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.MESSAGEID);
        traceInfo.append(request.getRequestMessage().getRequestHeader()
                .getMessageID());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.CLIENTID);
        traceInfo.append(request.getRequestMessage().getRequestHeader()
                .getClientID());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.OPERATION);
        traceInfo.append(request.getRequestMessage().getRequestHeader()
                .getDestination().getServiceOperation());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.RQDATE);
        traceInfo.append(request.getRequestMessage().getRequestHeader()
                .getRequestDate());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.RQREGION);
        traceInfo.append(request.getRequestMessage().getRequestHeader()
                .getDestination().getServiceRegion());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.RQVERSION);
        traceInfo.append(request.getRequestMessage().getRequestHeader()
                .getDestination().getServiceVersion());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.PAYLOAD_RQ);

        if (request.getRequestMessage().getRequestBody() != null
                && request.getRequestMessage().getRequestBody().getAny() != null) {
            String any = request.getRequestMessage().getRequestBody().getAny()
                    .toString();
            if (largeMessage
                    && any.length() > Constant.COMMON_INT_TRACE_MESSAGE_ANY_MAX_LENGTH)
                any = any.substring(0,
                        Constant.COMMON_INT_TRACE_MESSAGE_ANY_MAX_LENGTH);

            traceInfo.append(Base64.encode(any.getBytes()));
            traceInfo.append(Constant.CLOSE);
        } else {
            traceInfo.append(Constant.NO_BODY);
        }

        return traceInfo;
    }
    
    /**
     * Método que forma una traza de Request Message y la registra en el log si
     * esta habilitado
     * 
     * @param request
     * @param logger
     */
    public static void traceResponseMessage(ResponseHeaderOutMessageType response,
            CustomLogger logger, Boolean isMask) {

        if (logger.getIsTraceable()) {
            StringBuilder traceInfo;
            if (isMask) {
                traceInfo = MessageTracerUtil.buildResponseTraceInfoWithMask(
                        response, Boolean.FALSE, logger);

            } else {

                traceInfo = MessageTracerUtil.buildResponseTraceInfo(
                        response, Boolean.FALSE);
            }

            logger.info(traceInfo.toString());
        }

    }
    
    /**
     * <p>
     * M&eacute;todo que construye la traza para el log del response.
     * </p>
     * 
     * @param response
     * @param largeMessage
     *            flag que indica que es un response con un body muy largo y que
     *            debe ser recortado para agregarlo a la traza del log.
     * @return texto con la traza construida.
     */
    private static StringBuilder buildResponseTraceInfo(
            ResponseHeaderOutMessageType response, boolean largeMessage) {
        StringBuilder traceInfo = new StringBuilder(Constant.RESPONSE_BROKER);
        traceInfo.append(Constant.NAME);
        traceInfo.append(response.getResponseHeaderOut().getHeader()
                .getDestination().getName());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.STATUSCODE);
        traceInfo.append(response.getResponseHeaderOut().getHeader()
                .getResponseStatus().getStatusCode());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.MESSAGEID);
        traceInfo.append(
                response.getResponseHeaderOut().getHeader().getMessageId());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.OPERATION);
        traceInfo.append(response.getResponseHeaderOut().getHeader()
                .getDestination().getOperation());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.RSDATE);
        traceInfo.append(
                response.getResponseHeaderOut().getHeader().getInvokerDateTime());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.RQNAMESPACE);
        traceInfo.append(response.getResponseHeaderOut().getHeader()
                .getDestination().getNamespace());
        traceInfo.append(Constant.CLOSE);

        traceInfo.append(Constant.PAYLOAD_RS);

        if (null != response.getResponseHeaderOut().getBody()
                && null != response.getResponseHeaderOut().getBody().getAny()) {

            String any = parseJSONToString(
                    response.getResponseHeaderOut().getBody().getAny());

            if (largeMessage && any
                    .length() > Constant.COMMON_INT_TRACE_MESSAGE_ANY_MAX_LENGTH) {

                any = any.substring(0,
                        Constant.COMMON_INT_TRACE_MESSAGE_ANY_MAX_LENGTH);
            }

            traceInfo.append(Base64.encode(any.getBytes()));
            traceInfo.append(Constant.CLOSE);
        } else {
            traceInfo.append(Constant.NO_BODY);
        }
        return traceInfo;
    }
    
    /**
     * Método que forma una traza de Request Message y la registra en el log si
     * esta habilitado
     * 
     * @param request
     * @param logger
     * @param isMask
     */
    public static void traceRequestMessage(RequestHeaderOutMessageType request,
            CustomLogger logger,Boolean isMask) {

        if (logger.getIsTraceable()) {
            StringBuilder traceInfo;
            if (isMask) {
                traceInfo = MessageTracerUtil.buildRequestTraceInfoWithMask(
                        request, Boolean.FALSE, logger);

            } else {

                traceInfo = MessageTracerUtil.buildRequestTraceInfo(request,
                        Boolean.FALSE);
            }

            logger.info(traceInfo.toString());
        }

    }
    
    /**
     * <p>
     * Método que construye la traza para el log del request.
     * </p>
     * 
     * @param request
     * @param largeMessage
     * @return texto con la traza construida.
     */
    private static StringBuilder buildRequestTraceInfo(
            RequestHeaderOutMessageType request, Boolean largeMessage) {

        StringBuilder traceInfo = new StringBuilder(Constant.REQUEST_BROKER);
        traceInfo.append(Constant.NAME);
        traceInfo.append(request.getRequestHeaderOut().getHeader()
                .getDestination().getName());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.MESSAGEID);
        traceInfo.append(
                request.getRequestHeaderOut().getHeader().getMessageId());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.OPERATION);
        traceInfo.append(request.getRequestHeaderOut().getHeader()
                .getDestination().getOperation());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.RQDATE);
        traceInfo.append(
                request.getRequestHeaderOut().getHeader().getInvokerDateTime());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.RSNAMESPACE);
        traceInfo.append(request.getRequestHeaderOut().getHeader()
                .getDestination().getNamespace());
        traceInfo.append(Constant.CLOSE);

        traceInfo.append(Constant.PAYLOAD_RQ);

        if (null != request.getRequestHeaderOut().getBody()
                && null != request.getRequestHeaderOut().getBody().getAny()) {

            String any = parseJSONToString(
                    request.getRequestHeaderOut().getBody().getAny());

            if (largeMessage && any
                    .length() > Constant.COMMON_INT_TRACE_MESSAGE_ANY_MAX_LENGTH) {

                any = any.substring(Constant.COMMON_INT_ZERO,
                        Constant.COMMON_INT_TRACE_MESSAGE_ANY_MAX_LENGTH);
            }

            traceInfo.append(Base64.encode(any.getBytes()));
            traceInfo.append(Constant.CLOSE);
        } else {
            traceInfo.append(Constant.NO_BODY);
        }
        return traceInfo;
    }
    
    /**
     * * <p>
     * Método que construye la traza para el log del request.
     * </p>
     * 
     * @param request
     * @param largeMessage
     * @param logger
     * @return
     */
    private static StringBuilder buildRequestTraceInfoWithMask(
            RequestHeaderOutMessageType request, Boolean largeMessage,
            CustomLogger logger) {

        StringBuilder traceInfo = new StringBuilder(Constant.REQUEST_BROKER);
        traceInfo.append(Constant.NAME);
        traceInfo.append(request.getRequestHeaderOut().getHeader()
                .getDestination().getName());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.MESSAGEID);
        traceInfo.append(
                request.getRequestHeaderOut().getHeader().getMessageId());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.OPERATION);
        traceInfo.append(request.getRequestHeaderOut().getHeader()
                .getDestination().getOperation());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.RQDATE);
        traceInfo.append(
                request.getRequestHeaderOut().getHeader().getInvokerDateTime());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.RSNAMESPACE);
        traceInfo.append(request.getRequestHeaderOut().getHeader()
                .getDestination().getNamespace());
        traceInfo.append(Constant.CLOSE);

        traceInfo.append(Constant.PAYLOAD_RQ);

        if (null != request.getRequestHeaderOut().getBody()
                && null != request.getRequestHeaderOut().getBody().getAny()) {

            String any = parseJSONToString(
                    request.getRequestHeaderOut().getBody().getAny());

            if (largeMessage && any
                    .length() > Constant.COMMON_INT_TRACE_MESSAGE_ANY_MAX_LENGTH) {

                any = any.substring(Constant.COMMON_INT_ZERO,
                        Constant.COMMON_INT_TRACE_MESSAGE_ANY_MAX_LENGTH);
            }

            try {
                traceInfo.append(ServiceControllerHelper.maskProperties(any,
                        Arrays.asList(Constant.COMMON_STRING_TOKEN),
                        Constant.DEFAULT_MASK));
            } catch (MiddlewareException e) {
                traceInfo.append(any);
                logger.error(Constant.COMMON_STRING_ERROR_MESSAGE_MASK, e);
            }
            traceInfo.append(Constant.CLOSE);
        } else {
            traceInfo.append(Constant.NO_BODY);
        }
        return traceInfo;
    }
    
    /**
     * <p>
     * M&eacute;todo que construye la traza para el log del response.
     * </p>
     * 
     * @param response
     * @param largeMessage
     *            flag que indica que es un response con un body muy largo y que
     *            debe ser recortado para agregarlo a la traza del log.
     * @param logger           
     * @return texto con la traza construida.
     */
    private static StringBuilder buildResponseTraceInfoWithMask(
            ResponseHeaderOutMessageType response, boolean largeMessage,
            CustomLogger logger) {
        StringBuilder traceInfo = new StringBuilder(Constant.RESPONSE_BROKER);
        traceInfo.append(Constant.NAME);
        traceInfo.append(response.getResponseHeaderOut().getHeader()
                .getDestination().getName());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.STATUSCODE);
        traceInfo.append(response.getResponseHeaderOut().getHeader()
                .getResponseStatus().getStatusCode());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.MESSAGEID);
        traceInfo.append(
                response.getResponseHeaderOut().getHeader().getMessageId());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.OPERATION);
        traceInfo.append(response.getResponseHeaderOut().getHeader()
                .getDestination().getOperation());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.RSDATE);
        traceInfo.append(
                response.getResponseHeaderOut().getHeader().getInvokerDateTime());
        traceInfo.append(Constant.CLOSE);
        traceInfo.append(Constant.RQNAMESPACE);
        traceInfo.append(response.getResponseHeaderOut().getHeader()
                .getDestination().getNamespace());
        traceInfo.append(Constant.CLOSE);

        traceInfo.append(Constant.PAYLOAD_RS);

        if (null != response.getResponseHeaderOut().getBody()
                && null != response.getResponseHeaderOut().getBody().getAny()) {

            String any = parseJSONToString(
                    response.getResponseHeaderOut().getBody().getAny());

            if (largeMessage && any
                    .length() > Constant.COMMON_INT_TRACE_MESSAGE_ANY_MAX_LENGTH) {

                any = any.substring(0,
                        Constant.COMMON_INT_TRACE_MESSAGE_ANY_MAX_LENGTH);
            }

            try {
                traceInfo.append(ServiceControllerHelper.maskProperties(any,
                        Arrays.asList(Constant.COMMON_STRING_TOKEN),
                        Constant.DEFAULT_MASK));
            } catch (MiddlewareException e) {
                traceInfo.append(any);
                logger.error(Constant.COMMON_STRING_ERROR_MESSAGE_MASK, e);
            }
            traceInfo.append(Constant.CLOSE);
        } else {
            traceInfo.append(Constant.NO_BODY);
        }
        return traceInfo;
    }
    
    /**
     * Método para parsear un Objeto (<code>Map</code>, <code>Object</code>) a
     * un <code>String</code>.
     * 
     * @param <code>objectJSON</code>
     * @return <code>String</code>
     */
    private static String parseJSONToString(Object objectJSON) {

        Map<String, String> objectMap;
        JSONObject jsonObj;

        if (objectJSON instanceof Map) {

            objectMap = (Map<String, String>) objectJSON;
            jsonObj = new JSONObject(objectMap);

        } else {

            jsonObj = new JSONObject(objectJSON);
        }

        return jsonObj.toString();

    }
}
