/**
 * 
 */
package co.bdigital.cmm.ejb.generic;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.commons.beanutils.converters.StringConverter;
import org.apache.commons.lang.SerializationException;
import org.apache.commons.lang.SerializationUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ibm.xml.crypto.util.Base64;

import co.bdigital.cmm.consumer.exception.RestClientException;
import co.bdigital.cmm.consumer.service.impl.RestClientImpl;
import co.bdigital.cmm.ejb.exception.MiddlewareException;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.ejb.util.ContextUtilHelper;
import co.bdigital.cmm.ejb.util.MessageTracerUtil;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.HomologaError;
import co.bdigital.cmm.jpa.model.Parametro;
import co.bdigital.cmm.jpa.model.Seguridad;
import co.bdigital.cmm.jpa.model.SeguridadDfh;
import co.bdigital.cmm.jpa.service.impl.HomologadorServiceImpl;
import co.bdigital.cmm.jpa.service.impl.SeguridadDfhJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.SeguridadJPAServiceIMPL;
import co.bdigital.cmm.jpa.services.HomologadorService;
import co.bdigital.cmm.jpa.services.SeguridadDfhJPAService;
import co.bdigital.cmm.jpa.services.SeguridadJPAService;
import co.bdigital.cmm.messaging.esb.BodyType;
import co.bdigital.cmm.messaging.esb.HeaderRequestType;
import co.bdigital.cmm.messaging.esb.HeaderResponseType;
import co.bdigital.cmm.messaging.esb.MessageContextType;
import co.bdigital.cmm.messaging.esb.PropertyType;
import co.bdigital.cmm.messaging.esb.RequestHeaderOutMessageType;
import co.bdigital.cmm.messaging.esb.RequestHeaderOutType;
import co.bdigital.cmm.messaging.esb.ResponseHeaderOutMessageType;
import co.bdigital.cmm.messaging.esb.ResponseHeaderOutType;
import co.bdigital.cmm.messaging.esb.ResponseStatusType;
import co.bdigital.cmm.messaging.esb.SecurityCredentialType;
import co.bdigital.cmm.messaging.general.ProtectedMsgServiceRequestType;
import co.bdigital.cmm.messaging.general.RequestHeader;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.RequestMessageType;
import co.bdigital.cmm.messaging.general.ResponseBody;
import co.bdigital.cmm.messaging.general.ResponseHeader;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageType;
import co.bdigital.cmm.messaging.general.StatusType;
import co.bdigital.shl.backout.service.pojo.MessageBroker;
import co.bdigital.shl.common.service.bean.view.ResourceLocator;
import co.bdigital.shl.common.service.pojo.ResourcesPojo;
import co.bdigital.shl.security.manager.bean.view.SecurityManager;
import co.bdigital.shl.security.manager.exception.SecurityManagerSHLException;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * @author ricardo.paredes
 *
 */
public class ServiceControllerHelper {
    private static final String CLOSE = "]";
    private static final String REQUEST_INIT = "[REQUEST][ServiceName:";
    private static final String MESSAGEID = "[MessageID";
    private static final String CLIENTID = "[clientID:";
    private static final String OPERATION = "[Operation:";
    private static final String RQDATE = "[Request Date:";
    private static final String PAYLOAD_RQ = "[PAYLOAD RQ: ]";
    private static final String RQ = "RQ";
    private static final String RS = "RS";
    private static final String SUCCESS_CODE = "0";
    private static final String SUCCESS_DESC = "SUCCESS";
    private static final String APP = "APP";
    private static final String ERROR_PARSING = "Error al Parsear el JSON:";
    private static final String DEFAULT_CODE = "500";
    private static final String DEFAULT_MSG = "¡Ups! Tenemos un problema, vuélvelo a intentar.";
    private static final String STRING_LIST = "data";
    private static final String REGION_BROKER = "Region";
    private static final String SYSTEM_CODE = "MDW";
    private static final String ERROR_HOMOLOGATING_CODE = "Error al homologar codigo inexistente:";

    private static final String STRING_STATE_1010 = "1010";
    private static final String STRING_STATE_1012 = "1012";
    private static final String STRING_STATE_1013 = "1013";

    private static final String STRING_SECURITY_BROKER = "BROKER";
    private static final String STRING_SECURITY_TOKEN = "TOKEN";

    private HomologadorService homologador;
    private CustomLogger logger;

    private static ServiceControllerHelper instance;
    private static SecurityCredentialType securityCredentialType;
    private SecurityManager securityManager = null;
    private ContextUtilHelper contextUtilHelper = null;
    private SeguridadJPAService seguridadJPAService;
    private SeguridadDfhJPAService seguridadDFHJPAService;
    private ResourceLocator resourceLocator;

    /**
     * 
     * @return
     */
    public static ServiceControllerHelper getInstance() {
        if (instance == null)
            instance = new ServiceControllerHelper();

        return instance;
    }

    private ServiceControllerHelper() {
        this.contextUtilHelper = ContextUtilHelper.getInstance();
        this.securityManager = this.contextUtilHelper
                .instanceSecurityManagerRemote();
        this.seguridadJPAService = new SeguridadJPAServiceIMPL();
        this.seguridadDFHJPAService = new SeguridadDfhJPAServiceIMPL();
        logger = new CustomLogger(this.getClass());
    }

    /**
     * 
     * @param requestHeader
     * @param name
     * @param namespace
     * @param operation
     */
    public HeaderRequestType configureRequestESBHeader(
            RequestHeader requestHeader, String name, String namespace,
            String operation) {

        HeaderRequestType headerRequestType = new HeaderRequestType();
        headerRequestType.setInvokerDateTime(getCurrentTimeStamp());
        headerRequestType.setMessageId(requestHeader.getMessageID());
        headerRequestType.setSecurityCredential(getSecurityCredentials());
        headerRequestType.setSystemId(requestHeader.getChannel());

        co.bdigital.cmm.messaging.esb.DestinationType destinationType = new co.bdigital.cmm.messaging.esb.DestinationType();
        destinationType.setName(name);
        destinationType.setNamespace(namespace);
        destinationType.setOperation(operation);

        headerRequestType.setDestination(destinationType);

        return headerRequestType;

    }

    /**
     * 
     * @return Timestamp Actual
     */
    public String getCurrentTimeStamp() {
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat(
                Constant.COMMON_FORMAT_DATE_TO_FRONT);
        return format.format(curDate);
    }

    /**
     * 
     * @param destination
     * @return
     */
    private static SecurityCredentialType getSecurityCredentials() {
        if (null == securityCredentialType) {
            securityCredentialType = new SecurityCredentialType();
            securityCredentialType.setUserName(STRING_SECURITY_BROKER);
            securityCredentialType.setUserToken(STRING_SECURITY_TOKEN);

        }
        return securityCredentialType;
    }

    /**
     * método para parsear payload Any al Objeto del segundo parametro
     * 
     * @param any
     * @param clase
     * @return
     * @throws MiddlewareException
     */
    public Object parsePayload(Object any, Object clase)
            throws MiddlewareException {

        JSONObject jsonString = this.getJSONObject(any);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString.toString(), clase.getClass());
        } catch (IOException e) {

            StringBuilder errorMessage = new StringBuilder(ERROR_PARSING);
            errorMessage.append(clase.getClass().getName());
            logger.error(ErrorEnum.MIDDLEWARE_ERROR, errorMessage.toString(),
                    e);
            throw new MiddlewareException(errorMessage.toString(), e);
        }

    }

    /**
     * método para parsear payload Any al Objeto del segundo parametro
     * 
     * @param any
     * @param clase
     * @return
     * @throws MiddlewareException
     */
    public Object parsePayloadMessage(String any, Object clase)
            throws MiddlewareException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(any, clase.getClass());
        } catch (IOException e) {

            StringBuilder errorMessage = new StringBuilder(ERROR_PARSING);
            errorMessage.append(clase.getClass().getName());
            logger.error(ErrorEnum.MIDDLEWARE_ERROR, errorMessage.toString(),
                    e);
            throw new MiddlewareException(errorMessage.toString(), e);
        }

    }

    /**
     * Valida si se puede parsear el payload del servicio
     * 
     * @param any
     * @param clase
     * @param operacion
     * @return
     */
    public boolean validateRequest(Object any, Object clase, String operacion) {
        logger.debug(Constant.COMMON_STRING_PAYLOAD + any.toString());
        try {
            return parsePayload(any, clase) != null;
        } catch (MiddlewareException e) {
            logger.error(ERROR_PARSING + operacion, e);

        }
        return false;
    }

    /**
     * Valida si se puede parsear el payload del servicio, recibe como parametro
     * el <code>CustomLogger</code> especifico del Bean.
     * 
     * @param any
     * @param clase
     * @param operacion
     * @param loggerParam
     * @return <code>boolean</code>
     */
    public boolean validateRequest(Object any, Object clase, String operacion,
            CustomLogger loggerParam) {
        loggerParam.debugIsDebugable(
                Constant.COMMON_STRING_PAYLOAD + any.toString());
        try {
            return parsePayload(any, clase) != null;
        } catch (MiddlewareException e) {
            loggerParam.error(ERROR_PARSING + operacion, e);

        }
        return false;
    }

    public String parsePayloadToString(RequestMessageObjectType request) {
        StringBuilder traceInfo = new StringBuilder(REQUEST_INIT);
        traceInfo.append(request.getRequestMessage().getRequestHeader()
                .getDestination().getServiceName() + CLOSE);
        traceInfo.append(MESSAGEID);
        traceInfo.append(
                request.getRequestMessage().getRequestHeader().getMessageID()
                        + CLOSE);
        traceInfo.append(CLIENTID);
        traceInfo.append(
                request.getRequestMessage().getRequestHeader().getClientID()
                        + CLOSE);
        traceInfo.append(OPERATION);
        traceInfo.append(request.getRequestMessage().getRequestHeader()
                .getDestination().getServiceOperation() + CLOSE);
        traceInfo.append(RQDATE);
        traceInfo.append(
                request.getRequestMessage().getRequestHeader().getRequestDate()
                        + CLOSE);
        traceInfo.append(PAYLOAD_RQ);
        traceInfo.append(Base64.encode(request.getRequestMessage()
                .getRequestBody().getAny().toString().getBytes()) + CLOSE);

        return traceInfo.toString();

    }

    /**
     * método para parsear payload Any al Objeto del segundo parametro
     * 
     * @param any
     * @param clase
     * @param name
     * @return
     * @throws MiddlewareException
     */
    public Object parsePayloadModifiy(Object any, Object clase, String name)
            throws MiddlewareException {

        Map<String, String> anyMap = (Map<String, String>) any;
        JSONObject jsonString = new JSONObject(anyMap);

        org.json.JSONArray dataRS = new org.json.JSONArray();
        if (jsonString.getJSONObject(name).length() != 0) {
            if (!(jsonString.getJSONObject(name)
                    .get(STRING_LIST) instanceof org.json.JSONArray)) {
                JSONObject getRS = jsonString.getJSONObject(name);
                JSONObject alone = getRS.getJSONObject(STRING_LIST);
                if (alone.length() == 0) {
                    jsonString.getJSONObject(name).put(STRING_LIST,
                            new org.json.JSONArray());
                    for (Object object : jsonString.getJSONObject(name)
                            .keySet()) {
                        if (!(STRING_LIST
                                .equalsIgnoreCase(object.toString()))) {
                            if (getRS.get(object
                                    .toString()) instanceof org.json.JSONObject) {
                                if (getRS.getJSONObject(object.toString())
                                        .length() == 0) {
                                    getRS.put(object.toString(),
                                            Constant.COMMON_STRING_EMPTY_STRING);
                                }
                            } else {
                                if (getRS.get(object.toString()).toString()
                                        .length() == 0) {
                                    getRS.put(object.toString(),
                                            Constant.COMMON_STRING_EMPTY_STRING);
                                }
                            }
                        }
                    }
                } else {
                    dataRS.put(alone);
                    jsonString.getJSONObject(name).put(STRING_LIST, dataRS);
                }
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString.toString(), clase.getClass());
        } catch (IOException e) {
            StringBuilder errorMessage = new StringBuilder(ERROR_PARSING);
            errorMessage.append(clase.getClass().getName());
            logger.error(ErrorEnum.MIDDLEWARE_ERROR, errorMessage.toString(),
                    e);
            throw new MiddlewareException(errorMessage.toString(), e);
        }

    }

    /**
     * Genera un mensaje de Response a Front Homologando el error que se le
     * envíe.
     * 
     * @param requestHeader
     * @param errorCode
     * @param errorMessage
     * @param system
     * @return
     */
    public ResponseMessageObjectType responseErrorMessage(
            RequestHeader requestHeader, String errorCode, String errorMessage,
            String system, EntityManager em) {

        return buildResponseErrorMessage(requestHeader, errorCode, errorMessage,
                system, null, em);
    }

    /**
     * Genera un mensaje de Response a Front Homologando el error (si existe)
     * que se le envíe. De lo contrario genera un Response exitoso.
     * 
     * @param requestHeader
     * @param errorCode
     * @param errorMessage
     * @param system
     * @return <code>ResponseMessageObjectType</code>
     */
    public ResponseMessageObjectType responseMessage(
            RequestHeader requestHeader, String errorCode, String errorMessage,
            String system, EntityManager em) {

        return buildResponseErrorMessage(requestHeader, errorCode, errorMessage,
                system, null, em);
    }

    /**
     * Configura y setea el RespnseHeader de Front y Homologa los codigos de
     * respuesta.
     * 
     * @param requestHeader
     * @param responseStatus
     * @param em
     * @return {@link ResponseHeader}
     */
    public ResponseHeader configureResponseFrontHeader(
            RequestHeader requestHeader, ResponseStatusType responseStatus,
            EntityManager em) {
        return configureResponseFrontHeader(requestHeader, responseStatus, true,
                em);
    }

    /**
     * Configura y setea el ResponseHeader de Front y Homologa los codigos de
     * respuesta.
     * 
     * @param requestHeader
     * @param responseStatus
     * @param homologate
     *            -- indica si se debe homologar la respuesta
     * @param em
     * @return {@link ResponseHeader}
     */
    public ResponseHeader configureResponseFrontHeader(
            RequestHeader requestHeader, ResponseStatusType responseStatus,
            boolean homologate, EntityManager em) {

        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setChannel(requestHeader.getChannel());
        responseHeader.setClientID(requestHeader.getClientID());
        responseHeader.setMessageID(requestHeader.getMessageID());
        responseHeader.setResponseDate(getCurrentTimeStamp());
        responseHeader.setDestination(requestHeader.getDestination());

        StatusType statusType = null;
        if (responseStatus.getStatusCode().equals(SUCCESS_CODE)) {
            statusType = new StatusType();
            statusType.setStatusCode(SUCCESS_CODE);
            statusType.setStatusDesc(SUCCESS_DESC);
        } else if (null != requestHeader.getBlockingNequiAccessMode()
                && Constant.COMMON_STRING_TRUE.equalsIgnoreCase(
                        requestHeader.getBlockingNequiAccessMode().trim())) {
            statusType = new StatusType();
            statusType.setStatusCode(Constant.MAINTENANCE_CODE);
            statusType.setStatusDesc(null != requestHeader
                    .getBlockingNequiAccessModeMessage()
                    && !requestHeader.getBlockingNequiAccessModeMessage().trim()
                            .isEmpty()
                                    ? requestHeader
                                            .getBlockingNequiAccessModeMessage()
                                            .trim()
                                    : Constant.COMMON_MESSAGE_BLOCKING_NEQUI_ACCESS_MODE);

        } else if (null != requestHeader.getMaintenanceMode()
                && Constant.COMMON_STRING_TRUE.equalsIgnoreCase(
                        requestHeader.getMaintenanceMode().trim())) {
            statusType = new StatusType();
            statusType.setStatusCode(Constant.MAINTENANCE_CODE);
            statusType.setStatusDesc(null != requestHeader
                    .getMaintenanceModeMessage()
                    && !requestHeader.getMaintenanceModeMessage().trim()
                            .isEmpty()
                                    ? requestHeader.getMaintenanceModeMessage()
                                            .trim()
                                    : Constant.COMMON_MESSAGE_MAINTENANCE_MODE);

        } else {
            if (homologate) {
                statusType = getErrorHomologation(responseStatus, requestHeader,
                        null, em);
            } else {
                statusType = new StatusType();
                statusType.setStatusCode(responseStatus.getStatusCode());
                statusType.setStatusDesc(responseStatus.getStatusDesc());
            }
        }
        responseHeader.setStatus(statusType);

        return responseHeader;
    }

    /**
     * Metodo que configura y setea el <code>ResponseMessageObjectType</code> de
     * Front y homologa los codigos de respuesta.
     * 
     * @param statusCode
     * @param statusDesc
     * @param system
     * @param request
     * @param em
     * @return <code>ResponseMessageObjectType</code>
     */
    public ResponseMessageObjectType configureResponseFrontMessageType(
            String statusCode, String statusDesc, String system,
            RequestMessageObjectType request, EntityManager em) {

        ResponseMessageType responseMessageType;
        ResponseStatusType responseStatusType;
        ResponseHeader responseHeader;
        ResponseMessageObjectType responseMessageObjectType;
        ResponseBody responseBody;

        responseMessageType = new ResponseMessageType();
        responseStatusType = new ResponseStatusType();
        responseMessageObjectType = new ResponseMessageObjectType();
        responseBody = new ResponseBody();

        responseStatusType.setStatusCode(statusCode);
        responseStatusType.setStatusDesc(statusDesc);
        responseStatusType.setSystem(system);

        responseHeader = this.configureResponseFrontHeader(
                request.getRequestMessage().getRequestHeader(),
                responseStatusType, em);

        responseMessageType.setResponseHeader(responseHeader);
        responseMessageType.setResponseBody(responseBody);

        responseMessageObjectType.setResponseMessage(responseMessageType);

        return responseMessageObjectType;
    }

    /**
     * 
     * @param responseStatus
     * @param requestHeader
     * @param destinationSystem
     *            sistema destino para mostrar el error. (Opcional)
     * @param em
     * @return
     */
    private StatusType getErrorHomologation(ResponseStatusType responseStatus,
            RequestHeader requestHeader, String destinationSystem,
            EntityManager em) {

        if (null == destinationSystem) {
            destinationSystem = APP;
        }

        homologador = new HomologadorServiceImpl();
        StatusType status = new StatusType();
        try {
            HomologaError homologaError = homologador.getErrorHomologation(
                    responseStatus.getStatusCode(), responseStatus.getSystem(),
                    destinationSystem, em);
            status.setStatusCode(
                    homologaError.getCatalogoError2().getId().getCodigo());
            status.setStatusDesc(
                    homologaError.getCatalogoError2().getDescripcion());
            return status;

        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR, generateString(
                    Constant.ERROR_MESSAGE_HOMOLOGATION_ERROR,
                    Constant.COMMON_STRING_BLANK_SPACE, DEFAULT_MSG,
                    Constant.COMMON_STRING_BLANK_SPACE, Constant.GUION_MEDIO,
                    Constant.COMMON_STRING_BLANK_SPACE, ERROR_HOMOLOGATING_CODE,
                    Constant.COMMON_STRING_BLANK_SPACE, Constant.GUION_MEDIO,
                    Constant.COMMON_STRING_BLANK_SPACE,
                    Constant.COMMON_STRING_CODIGO,
                    Constant.COMMON_STRING_BLANK_SPACE,
                    responseStatus.getStatusCode(),
                    Constant.COMMON_STRING_BLANK_SPACE, Constant.GUION_MEDIO,
                    Constant.COMMON_STRING_BLANK_SPACE,
                    Constant.COMMON_STRING_SISTEMA,
                    Constant.COMMON_STRING_BLANK_SPACE,
                    responseStatus.getSystem(),
                    Constant.COMMON_STRING_BLANK_SPACE, Constant.GUION_MEDIO,
                    Constant.COMMON_STRING_BLANK_SPACE,
                    Constant.COMMON_STRING_DESCRIPTION,
                    Constant.COMMON_STRING_BLANK_SPACE,
                    responseStatus.getStatusDesc()), e);
            status.setStatusCode(DEFAULT_CODE);
            status.setStatusDesc(DEFAULT_MSG);
            return status;
        }

    }

    /**
     * 
     * @param requestHeader
     * @param operationNameRQ
     * @param operationNameRS
     * @return
     */
    public MessageContextType configureMessageContext(String operationNameRQ,
            String operationNameRS) {

        MessageContextType messageContextType = new MessageContextType();
        PropertyType propertyTypeRQ = new PropertyType();
        PropertyType propertyTypeRS = new PropertyType();

        propertyTypeRQ.setKey(RQ);
        propertyTypeRQ.setValue(operationNameRQ);
        propertyTypeRS.setKey(RS);
        propertyTypeRS.setValue(operationNameRS);

        messageContextType.getProperty().add(propertyTypeRQ);
        messageContextType.getProperty().add(propertyTypeRS);

        return messageContextType;

    }

    /**
     * Metodo que adiciona las propiedades de internacionalizacion a un
     * <code>MessageContextType</code> del BROKER.
     * 
     * @param messageContextType
     * @param serviceRegion
     * @return <code>MessageContextType</code>
     */
    public MessageContextType addPropertyInternationalizationMessageContext(
            MessageContextType messageContextType, String serviceRegion) {

        PropertyType propertyTypeRQ;

        if (null == messageContextType) {

            messageContextType = new MessageContextType();
        }

        propertyTypeRQ = new PropertyType();

        propertyTypeRQ.setKey(REGION_BROKER);
        propertyTypeRQ.setValue(serviceRegion);

        messageContextType.getProperty().add(propertyTypeRQ);

        return messageContextType;

    }

    /**
     * Metodo que adiciona las propiedades AppType a un
     * <code>MessageContextType</code> del BROKER.
     * 
     * @param messageContextType
     * @param appTypeValue
     * @return
     */
    public MessageContextType addPropertyAppTypeMessageContext(
            MessageContextType messageContextType, String appTypeValue) {

        PropertyType propertyTypeRQ;

        if (null == messageContextType) {
            messageContextType = new MessageContextType();
        }

        propertyTypeRQ = new PropertyType();

        propertyTypeRQ.setKey(Constant.COMMON_STRING_APP_TYPE);
        propertyTypeRQ.setValue(appTypeValue);

        messageContextType.getProperty().add(propertyTypeRQ);

        return messageContextType;

    }

    /**
     * Metodo que adiciona una propiedad a un <code>MessageContextType</code>
     * del BROKER.
     * 
     * @param messageContextType
     * @param key
     * @param value
     * @return <code>MessageContextType</code>
     */
    public MessageContextType addPropertyMessageContext(
            MessageContextType messageContextType, String key, String value) {

        PropertyType propertyTypeRQ;

        if (null == messageContextType) {

            messageContextType = new MessageContextType();
        }

        propertyTypeRQ = new PropertyType();

        propertyTypeRQ.setKey(key);
        propertyTypeRQ.setValue(value);

        messageContextType.getProperty().add(propertyTypeRQ);

        return messageContextType;

    }

    /**
     * Metodo que adiciona una lista de propiedades a un
     * <code>MessageContextType</code> del BROKER.
     * 
     * @param messageContextType
     * @param propertyMap
     * @return <code>MessageContextType</code>
     */
    public MessageContextType addPropertyMessageContext(
            MessageContextType messageContextType,
            Map<String, String> propertyMap) {

        if (null != propertyMap) {

            if (null == messageContextType) {

                messageContextType = new MessageContextType();
            }

            for (Map.Entry<String, String> entry : propertyMap.entrySet()) {

                this.addPropertyMessageContext(messageContextType,
                        entry.getKey(), entry.getValue());
            }
        }

        return messageContextType;
    }

    /**
     * retorna el email enmascarado con asteríscos a partir de la posición
     * inicial recibida la cantidad indicada , si no es posible reemplazar los
     * caracteres indicados retorna el resto del usuario en asteríscos.
     * 
     * En cualquiera de los casos seguido por @ y el dominio del correo ej:
     * 
     * correoMuyLargo@mail.com -----> co*****@mail.com co@mail.com ----->
     * co*****@mail.com
     * 
     * 
     * @param mail
     * @param initialChars
     * @param maskedChars
     * @return
     */
    public String maskEmail(String mail, int initialChars, int maskedChars) {
        String[] splittedString = mail.split(Constant.COMMON_STRING_AT);
        String mailUser = splittedString[0];
        String mailDomain = splittedString[1];
        StringBuilder maskedString = new StringBuilder();

        if (mailUser.length() <= initialChars) {
            maskedString.append(mailUser);
        } else {
            maskedString.append(
                    mailUser.substring(Constant.COMMON_INT_ZERO, initialChars));
        }

        maskedString.append(Constant.COMMON_STRING_ASTERISK);
        maskedString.append(Constant.COMMON_STRING_AT);
        maskedString.append(mailDomain);
        return maskedString.toString();
    }

    /**
     * Recibe un texto cifrado en AES y con clave secreta Diffie Hellman y
     * retorna el texto descifrado.
     * 
     * @param infoCipher
     * @param seguridad
     * @param clientPublicKey
     * @return <code>String</code>
     * @throws SecurityManagerSHLException
     */
    public String decryptAESDiffieHellman(String infoCipher,
            Seguridad seguridad, String clientPublicKey)
            throws SecurityManagerSHLException {

        String resultDescifrado = null;

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_INICIO
                + Constant.DIFFIE_HELLMAN_DECRYPT_AES);

        if (seguridad == null) {

            SecurityManagerSHLException securityManagerSHLException = new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_WITHOUT_SECRET_KEY);
            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY,
                    securityManagerSHLException);
            throw securityManagerSHLException;

        } else if (!seguridad.getClavePublicaCliente()
                .equals(clientPublicKey)) {

            SecurityManagerSHLException securityManagerSHLException = new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_PUBLIC_KEY_NOT_MATCH);
            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY,
                    securityManagerSHLException);
            throw securityManagerSHLException;

        } else {

            resultDescifrado = this.securityManager.decryptAESDiffieHellman(
                    seguridad.getClaveSecreta(), infoCipher);

        }

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_FIN
                + Constant.DIFFIE_HELLMAN_DECRYPT_AES);

        return resultDescifrado;

    }

    /**
     * Recibe un texto cifrado en AES y con clave secreta Diffie Hellman y
     * retorna el texto descifrado.
     * 
     * @param celular
     * @param clientPublicKey
     * @param em
     * @param infoCipher
     * @return <code>String</code>
     * @throws SecurityManagerSHLException
     */
    public String decryptAESDiffieHellman(String celular,
            String clientPublicKey, EntityManager em, String infoCipher)
            throws SecurityManagerSHLException {

        Seguridad seguridad = null;
        String resultDescifrado = null;

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_INICIO
                + Constant.DIFFIE_HELLMAN_DECRYPT_AES);

        try {

            seguridad = this.seguridadJPAService.findSeguridad(celular, em);

            resultDescifrado = this.decryptAESDiffieHellman(infoCipher,
                    seguridad, clientPublicKey);

        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY_DB_FIND_SEGURIDAD, e);
            throw new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_DB_FIND_SEGURIDAD, e);

        } catch (SecurityManagerSHLException e) {
            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY_DECRYPT_AES_DIFFIE_HELLMAN,
                    e);
            throw e;

        } catch (Exception e) {
            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY_DECRYPT_AES_DIFFIE_HELLMAN_GENERAL,
                    e);
            throw new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_DECRYPT_AES_DIFFIE_HELLMAN_GENERAL,
                    e);
        }

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_FIN
                + Constant.DIFFIE_HELLMAN_DECRYPT_AES);

        return resultDescifrado;

    }

    /**
     * Recibe un texto plano donde utiliza la clave secreta Diffie Hellman y
     * retorna el texto cifrado con el algoritmo AES.
     * 
     * @param infoCipher
     * @param seguridad
     * @param clientPublicKey
     * @return <code>String</code>
     * @throws SecurityManagerSHLException
     */
    public String encryptAESDiffieHellman(String infoPlainText,
            Seguridad seguridad, String clientPublicKey)
            throws SecurityManagerSHLException {

        String resultCipher = null;

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_INICIO
                + Constant.DIFFIE_HELLMAN_ENCRYPT_AES);

        if (seguridad == null) {

            SecurityManagerSHLException securityManagerSHLException = new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_WITHOUT_SECRET_KEY);
            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY,
                    securityManagerSHLException);
            throw securityManagerSHLException;

        } else if (!seguridad.getClavePublicaCliente()
                .equals(clientPublicKey)) {

            SecurityManagerSHLException securityManagerSHLException = new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_PUBLIC_KEY_NOT_MATCH);
            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY,
                    securityManagerSHLException);
            throw securityManagerSHLException;

        } else {

            resultCipher = this.securityManager.encryptAESDiffieHellman(
                    seguridad.getClaveSecreta(), infoPlainText);

        }

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_FIN
                + Constant.DIFFIE_HELLMAN_ENCRYPT_AES);

        return resultCipher;

    }

    /**
     * Recibe un texto plano donde utiliza la clave secreta Diffie Hellman y
     * retorna el texto cifrado con el algoritmo AES.
     * 
     * @param celular
     * @param clientPublicKey
     * @param em
     * @param infoPlainText
     * @return <code>String</code>
     * @throws SecurityManagerSHLException
     */
    public String encryptAESDiffieHellman(String celular,
            String clientPublicKey, EntityManager em, String infoPlainText)
            throws SecurityManagerSHLException {

        Seguridad seguridad = null;
        String resultCipher = null;

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_INICIO
                + Constant.DIFFIE_HELLMAN_ENCRYPT_AES);

        try {

            seguridad = this.seguridadJPAService.findSeguridad(celular, em);

            resultCipher = this.encryptAESDiffieHellman(infoPlainText,
                    seguridad, clientPublicKey);

        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY_DB_FIND_SEGURIDAD, e);
            throw new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_DB_FIND_SEGURIDAD, e);

        } catch (SecurityManagerSHLException e) {
            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY_ENCRYPT_AES_DIFFIE_HELLMAN,
                    e);
            throw e;

        } catch (Exception e) {
            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY_ENCRYPT_AES_DIFFIE_HELLMAN_GENERAL,
                    e);
            throw new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_ENCRYPT_AES_DIFFIE_HELLMAN_GENERAL,
                    e);
        }

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_FIN
                + Constant.DIFFIE_HELLMAN_ENCRYPT_AES);

        return resultCipher;

    }

    /**
     * Metodo que retorna un objeto <code>Object</code> apartir del Info
     * encriptado del ProtectedMsgRQ.
     * 
     * @param request
     * @param varPhone
     * @param clase
     * @param em
     * @return <code>Object</code>
     * @throws SecurityManagerSHLException
     */
    public Object getJSONObjectDecryptAESDiffieHellman(
            RequestMessageObjectType request, String varPhone, Object clase,
            EntityManager em) throws SecurityManagerSHLException {

        ProtectedMsgServiceRequestType protectedMsgServiceResponseType = null;
        String clientIDCelular = null;
        String clientPublicKey = null;
        String resultDecoded = null;
        String infoCipher = null;
        JSONObject jSONObject = null;
        ObjectMapper objectMapper = null;
        JSONObject resultDecodedJSONObject = null;
        Object objectReturn = null;

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_INICIO
                + Constant.DIFFIE_HELLMAN_GET_JSON_OBJECT_DECRYPT_AES);

        try {

            protectedMsgServiceResponseType = (ProtectedMsgServiceRequestType) this
                    .parsePayload(
                            request.getRequestMessage().getRequestBody()
                                    .getAny(),
                            new ProtectedMsgServiceRequestType());

            clientIDCelular = request.getRequestMessage().getRequestHeader()
                    .getClientID();

            clientPublicKey = request.getRequestMessage().getRequestHeader()
                    .getSecurity().getPublicKey();

            infoCipher = protectedMsgServiceResponseType.getProtectedMsgRQ()
                    .getInfo();

            resultDecoded = this.decryptAESDiffieHellman(clientIDCelular,
                    clientPublicKey, em, infoCipher);

            resultDecodedJSONObject = new JSONObject(resultDecoded);
            resultDecodedJSONObject.put(varPhone,
                    protectedMsgServiceResponseType.getProtectedMsgRQ()
                            .getPhoneNumber());
            jSONObject = new JSONObject();
            jSONObject.put(protectedMsgServiceResponseType.getProtectedMsgRQ()
                    .getType(), resultDecodedJSONObject);

            objectMapper = new ObjectMapper();
            objectReturn = objectMapper.readValue(jSONObject.toString(),
                    clase.getClass());

            logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_FIN
                    + Constant.DIFFIE_HELLMAN_GET_JSON_OBJECT_DECRYPT_AES);

            return objectReturn;

        } catch (IOException | MiddlewareException e) {

            StringBuilder errorMessage = new StringBuilder(ERROR_PARSING);
            errorMessage.append(clase.getClass().getName());
            logger.error(ErrorEnum.SECURITY_ERROR, errorMessage.toString(), e);
            throw new SecurityManagerSHLException(errorMessage.toString(), e);
        }

    }

    /**
     * Metodo que retorna el objeto <code>JSONObject</code> Any cifrado en un
     * <code>String</code>.
     * 
     * @param request
     * @param any
     * @param em
     * @return <code>String</code>
     * @throws SecurityManagerSHLException
     */
    public String getStringEncryptAESDiffieHellman(
            RequestMessageObjectType request, Object any, EntityManager em)
            throws SecurityManagerSHLException {

        String clientIDCelular = null;
        String clientPublicKey = null;
        String encryptedObj = null;
        Map<String, String> anyMap = null;
        JSONObject jsonObj = null;

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_INICIO
                + Constant.DIFFIE_HELLMAN_GET_STRING_ENCRYPT_AES);

        try {

            // Se convierte el ANY a un objecto Json, para luego encriptarlo
            if (any instanceof Map) {

                anyMap = (Map<String, String>) any;
                jsonObj = new JSONObject(anyMap);

            } else {

                jsonObj = new JSONObject(any);
            }

            clientIDCelular = request.getRequestMessage().getRequestHeader()
                    .getClientID();

            clientPublicKey = request.getRequestMessage().getRequestHeader()
                    .getSecurity().getPublicKey();

            encryptedObj = this.encryptAESDiffieHellman(clientIDCelular,
                    clientPublicKey, em, jsonObj.toString());

            logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_FIN
                    + Constant.DIFFIE_HELLMAN_GET_STRING_ENCRYPT_AES);

            return encryptedObj;

        } catch (Exception e) {

            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY_ENCRYPT_AES_DIFFIE_HELLMAN_GENERAL,
                    e);
            throw new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_ENCRYPT_AES_DIFFIE_HELLMAN_GENERAL,
                    e);
        }

    }

    /**
     * Metodo que convierte un objeto <code>any</code> en un objeto
     * <code>JSONObject</code>.
     * 
     * @param any
     * @return <code>JSONObject</code>
     */
    private JSONObject getJSONObject(Object any) {

        JSONObject jsonObj;
        Map<String, String> anyMap;

        if (any instanceof Map) {

            anyMap = (Map<String, String>) any;
            jsonObj = new JSONObject(anyMap);

        } else {

            jsonObj = new JSONObject(any);
        }

        return jsonObj;

    }

    /**
     * Configura los valotes por defecto de la libreria <code>beanutils</code>.
     */
    private void setDefaultValues() {

        logger.debug(Constant.SERVICE_CONTROLLER_HELPER_UTILITY_INICIO
                + Constant.SERVICE_CONTROLLER_HELPER_UTILITY_SET_DEFAULT_VALUES);

        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
        ConvertUtils.register(new SqlDateConverter(null), java.sql.Date.class);
        ConvertUtils.register(new SqlTimestampConverter(null),
                java.sql.Timestamp.class);
        ConvertUtils.register(new LongConverter(null), Long.class);
        ConvertUtils.register(new FloatConverter(null), Float.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        ConvertUtils.register(new StringConverter(null), String.class);
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);

        logger.debug(Constant.SERVICE_CONTROLLER_HELPER_UTILITY_FIN
                + Constant.SERVICE_CONTROLLER_HELPER_UTILITY_SET_DEFAULT_VALUES);

    }

    /**
     * Copia el valor de las propiedades desde el objeto origen
     * <code>originObject</code>, al objeto destino <code>targetObject</code>.
     * Si y solo si las propiedades tienen el mismo nombre y el mismo tipo.
     * 
     * @param targetObject
     * @param originObject
     * @throws SecurityManagerSHLException
     */
    public void copyPropertiesTargetObjectToOriginObject(Object targetObject,
            Object originObject) throws SecurityManagerSHLException {

        logger.debug(Constant.SERVICE_CONTROLLER_HELPER_UTILITY_INICIO
                + Constant.SERVICE_CONTROLLER_HELPER_UTILITY_COPY_PROPERTIES_TARGET_OBJECT_TO_ORIGIN_OBJECT);

        try {

            setDefaultValues();
            BeanUtils.copyProperties(targetObject, originObject);

            logger.debug(Constant.SERVICE_CONTROLLER_HELPER_UTILITY_FIN
                    + Constant.SERVICE_CONTROLLER_HELPER_UTILITY_COPY_PROPERTIES_TARGET_OBJECT_TO_ORIGIN_OBJECT);

        } catch (IllegalAccessException | InvocationTargetException e) {

            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SERVICE_CONTROLLER_HELPER_UTILITY_BEAN_UTILS_COPY_PROPERTIES,
                    e);
            throw new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SERVICE_CONTROLLER_HELPER_UTILITY_BEAN_UTILS_COPY_PROPERTIES,
                    e);

        } catch (Exception e) {

            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SERVICE_CONTROLLER_HELPER_UTILITY_GENERAL,
                    e);
            throw new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SERVICE_CONTROLLER_HELPER_UTILITY_GENERAL,
                    e);
        }

    }

    /**
     * Copia el valor de las propiedades desde el objeto origen
     * <code>originObject</code>, al objeto destino <code>targetObject</code>.
     * Si y solo si las propiedades tienen el mismo nombre y el mismo tipo.
     * 
     * @param targetObject
     * @param originObject
     * @throws MiddlewareException
     */
    public void copyPropertiesTargetObjectToOriginObjectCommon(
            Object targetObject, Object originObject)
            throws MiddlewareException {

        logger.debug(Constant.SERVICE_CONTROLLER_HELPER_UTILITY_INICIO
                + Constant.SERVICE_CONTROLLER_HELPER_UTILITY_COPY_PROPERTIES_TARGET_OBJECT_TO_ORIGIN_OBJECT_COMMON);

        try {

            setDefaultValues();
            BeanUtils.copyProperties(targetObject, originObject);

            logger.debug(Constant.SERVICE_CONTROLLER_HELPER_UTILITY_FIN
                    + Constant.SERVICE_CONTROLLER_HELPER_UTILITY_COPY_PROPERTIES_TARGET_OBJECT_TO_ORIGIN_OBJECT_COMMON);

        } catch (IllegalAccessException | InvocationTargetException e) {

            logger.error(
                    Constant.ERROR_MESSAGE_SERVICE_CONTROLLER_HELPER_UTILITY_BEAN_UTILS_COPY_PROPERTIES,
                    e);
            throw new MiddlewareException(
                    Constant.ERROR_MESSAGE_SERVICE_CONTROLLER_HELPER_UTILITY_BEAN_UTILS_COPY_PROPERTIES,
                    e);

        } catch (Exception e) {

            logger.error(
                    Constant.ERROR_MESSAGE_SERVICE_CONTROLLER_HELPER_UTILITY_GENERAL,
                    e);
            throw new MiddlewareException(
                    Constant.ERROR_MESSAGE_SERVICE_CONTROLLER_HELPER_UTILITY_GENERAL,
                    e);
        }

    }

    /**
     * Método para parsear un Objeto (<code>Map</code>, <code>Object</code>) a
     * un <code>String</code>.
     * 
     * @param <code>objectJSON</code>
     * @return <code>String</code>
     */
    public String parseJSONToString(Object objectJSON) {

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

    /**
     * Parsea un objeto <code>Any</code> a <code>Map<String, Object></code>. El
     * Map sobreescribe el
     * <code>responseMessageObjectType.getResponseMessage().getResponseBody().setAny(Map)</code>
     * .
     * 
     * @param responseMessageObjectType
     * @param requestMessageObjectType
     * @param em
     * @return <code>ResponseMessageObjectType</code>
     */
    public ResponseMessageObjectType parseResponseAnyToMapJson(
            ResponseMessageObjectType responseMessageObjectType,
            RequestMessageObjectType requestMessageObjectType,
            EntityManager em) {

        Object objectAny;

        try {

            objectAny = responseMessageObjectType.getResponseMessage()
                    .getResponseBody().getAny();

            if (!(objectAny instanceof Map) && !(objectAny instanceof String)) {

                // Se Parsea el ObjectAny a MapJson.
                Map<String, Object> retMap = this
                        .getObjectJsonToMapJson(objectAny);

                responseMessageObjectType.getResponseMessage().getResponseBody()
                        .setAny(retMap);

            }

            return responseMessageObjectType;

        } catch (MiddlewareException e) {

            String planeRequestHerader = this
                    .parsePayloadToString(requestMessageObjectType);
            logger.error(ErrorEnum.MIDDLEWARE_ERROR, planeRequestHerader, e);

            return this.responseErrorMessage(
                    requestMessageObjectType.getRequestMessage()
                            .getRequestHeader(),
                    Constant.ERROR_CODE_PARSE_PAYLOAD,
                    Constant.ERROR_MESSAGE_PARSING_ERROR_OUT, SYSTEM_CODE, em);
        }
    }

    /**
     * Parsea un objeto <code>anyObjectJson</code> a
     * <code>Map<String, Object></code>.
     * 
     * @param objectAny
     * @return <code>Map<String, Object></code>
     * @throws MiddlewareException
     */
    public Map<String, Object> getObjectJsonToMapJson(Object objectAny)
            throws MiddlewareException {

        Map<String, Object> mapAny;
        JSONObject json;

        try {

            mapAny = new HashMap<String, Object>();
            json = this.getJSONObject(objectAny);

            if (json != JSONObject.NULL) {

                mapAny = this.toMapJson(json);
            }

            return mapAny;

        } catch (Exception ex) {

            StringBuilder errorMessage = new StringBuilder(ERROR_PARSING);
            errorMessage.append(this.getClass().getName());
            logger.error(ErrorEnum.MIDDLEWARE_ERROR, errorMessage.toString(),
                    ex);
            throw new MiddlewareException(errorMessage.toString(), ex);
        }
    }

    /**
     * Parsea un objeto <code>JSONObject</code> a
     * <code>Map<String, Object></code>.
     * 
     * @param jSONObject
     * @return <code>Map<String, Object></code>
     * @throws MiddlewareException
     */
    private Map<String, Object> toMapJson(JSONObject jSONObject)
            throws MiddlewareException {

        Map<String, Object> map;
        Iterator<String> keysItr;
        String key;
        Object value;

        try {

            map = new HashMap<String, Object>();

            keysItr = jSONObject.keys();

            while (keysItr.hasNext()) {

                key = keysItr.next();
                value = jSONObject.get(key);

                if (value instanceof JSONArray) {

                    value = this.toListObjectJson((JSONArray) value);

                } else if (value instanceof JSONObject) {

                    value = this.toMapJson((JSONObject) value);
                }

                map.put(key, value);
            }

            return map;

        } catch (Exception ex) {

            StringBuilder errorMessage = new StringBuilder(ERROR_PARSING);
            errorMessage.append(this.getClass().getName());
            logger.error(ErrorEnum.MIDDLEWARE_ERROR, errorMessage.toString(),
                    ex);
            throw new MiddlewareException(errorMessage.toString(), ex);
        }
    }

    /**
     * Parsea un objeto <code>JSONArray</code> a <code>List<Object></code>.
     * 
     * @param jSONArray
     * @return <code>List<Object></code>
     * @throws MiddlewareException
     */
    private List<Object> toListObjectJson(JSONArray jSONArray)
            throws MiddlewareException {

        List<Object> list;
        Object value;

        try {

            list = new ArrayList<>();

            for (int i = 0; i < jSONArray.length(); i++) {

                value = jSONArray.get(i);

                if (value instanceof JSONArray) {

                    value = this.toListObjectJson((JSONArray) value);

                } else if (value instanceof JSONObject) {

                    value = this.toMapJson((JSONObject) value);
                }

                list.add(value);
            }

            return list;

        } catch (Exception ex) {

            StringBuilder errorMessage = new StringBuilder(ERROR_PARSING);
            errorMessage.append(this.getClass().getName());
            logger.error(ErrorEnum.MIDDLEWARE_ERROR, errorMessage.toString(),
                    ex);
            throw new MiddlewareException(errorMessage.toString(), ex);
        }
    }

    /**
     * Metodo para formar la cabecera que se enviara a broker
     * 
     * @param channel
     * @param destination
     * @param messageId
     * @param name
     * @param namespace
     * @param operation
     * @return
     */
    public HeaderRequestType configureRequestESBHeader(String channel,
            co.bdigital.cmm.messaging.general.DestinationType destination,
            String messageId, String name, String namespace, String operation) {

        HeaderRequestType headerRequestType = new HeaderRequestType();
        headerRequestType.setInvokerDateTime(getCurrentTimeStamp());
        headerRequestType.setMessageId(messageId);
        headerRequestType.setSecurityCredential(getSecurityCredentials());
        headerRequestType.setSystemId(channel);

        co.bdigital.cmm.messaging.esb.DestinationType destinationType = new co.bdigital.cmm.messaging.esb.DestinationType();
        destinationType.setName(name);
        destinationType.setNamespace(namespace);
        destinationType.setOperation(operation);

        headerRequestType.setDestination(destinationType);

        return headerRequestType;

    }

    /**
     * Metodo que sirve para sumar un parametro en MIN a una fecha.
     * 
     * @param fecha
     * @param idTransaction
     * @return Date
     */
    public Date formatDate(String fecha, String parametro) {

        fecha = fecha.substring(0, fecha.length() - Constant.COMMON_INT_THREE);

        DateFormat formatter = new SimpleDateFormat(
                Constant.COMMON_FORMAT_DATE_FROM_BD);
        Date date = null;
        try {
            date = formatter.parse(fecha);
            return new Date(date.getTime()
                    + TimeUnit.MINUTES.toMillis(Integer.parseInt(parametro)));
        } catch (ParseException e) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    Constant.ERROR_MESSAGE_FORMAT_DATE, e);
            return null;
        }
    }

    /**
     * 
     * Metodo que sirver para formatear un fecha
     * 
     * @param inputDate
     * @return String
     */
    public String formatDateLargeFormat(String inputDate) {

        String formattedTime = null;

        if (null != inputDate && !inputDate.isEmpty()) {

            SimpleDateFormat sdf = new SimpleDateFormat(
                    Constant.COMMON_STRING_DATE_LARGE_FORMAT);

            Date date;
            try {
                date = sdf.parse(inputDate);

                SimpleDateFormat output = new SimpleDateFormat(
                        Constant.COMMON_FORMAT_DATE_TO_FRONT);

                formattedTime = output.format(date);
            } catch (ParseException e) {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        Constant.ERROR_MESSAGE_FORMAT_DATE, e);

            }
        }
        return formattedTime;
    }

    /*
     * Metodo generico para formatear fecha
     * 
     * @param date
     * 
     * @return String
     */
    public String formatDateToFront(Date date) {

        if (null != date) {

            try {
                return new SimpleDateFormat(
                        Constant.COMMON_FORMAT_DATE_TO_FRONT).format(date);

            } catch (Exception e) {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        Constant.ERROR_MESSAGE_FORMAT_DATE, e);
            }

        }
        return Constant.COMMON_STRING_EMPTY_STRING;
    }

    /**
     * Metodo generico para formatear fecha
     * 
     * @param date
     * 
     * @return String
     */
    public String formatDateToString(Date date, String format) {

        if (null != date) {

            try {
                return new SimpleDateFormat(format).format(date);

            } catch (Exception e) {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        Constant.ERROR_MESSAGE_FORMAT_DATE, e);
            }

        }
        return Constant.COMMON_STRING_EMPTY_STRING;
    }

    /**
     * Metodo generico para formatear fecha
     * 
     * @param date
     *            : "18/02/2016"
     * @param format
     *            : "dd/MM/yyyy"
     * 
     * @return Date
     */
    public Date formatStringToDate(String date, String format) {

        if (null != date) {

            try {
                return new SimpleDateFormat(format).parse(date);

            } catch (Exception e) {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        Constant.ERROR_MESSAGE_FORMAT_DATE, e);
            }

        }
        return null;
    }

    /**
     * Codigo para calcular la diferencia entre fechas
     * 
     * @param date1
     * @param date2
     * @return Map<TimeUnit,Long>
     */
    public Map<TimeUnit, Long> computeDiff(Date date1, Date date2) {

        long diffInMillies = date2.getTime() - date1.getTime();

        List<TimeUnit> units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);

        Map<TimeUnit, Long> result = new LinkedHashMap<>();
        long milliesRest = diffInMillies;

        for (TimeUnit unit : units) {
            long diff = unit.convert(milliesRest, TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;
            result.put(unit, diff);
        }
        return result;
    }

    /**
     * Metodo que sirve para dar formato a una fecha que va hacia la capa FRONT.
     * 
     * @param fecha
     * @return String
     * @throws ParseException
     */
    public String formatDate(String fecha) throws ParseException {

        DateFormat formatter = new SimpleDateFormat(
                Constant.COMMON_FORMAT_DATE_TO_FRONT);
        Date date = formatter.parse(fecha);

        DateFormatSymbols sym = DateFormatSymbols
                .getInstance(new Locale(Constant.COMMON_STRING_LOCALE_ES_LOWER,
                        Constant.COMMON_STRING_LOCALE_ES_UPPER));
        sym.setMonths(new String[] { Constant.COMMON_STRING_ENERO,
                Constant.COMMON_STRING_FEBRERO, Constant.COMMON_STRING_MARZO,
                Constant.COMMON_STRING_ABRIL, Constant.COMMON_STRING_MAYO,
                Constant.COMMON_STRING_JUNIO, Constant.COMMON_STRING_JULIO,
                Constant.COMMON_STRING_AGOSTO,
                Constant.COMMON_STRING_SEPTIEMBRE,
                Constant.COMMON_STRING_OCTUBRE,
                Constant.COMMON_STRING_NOVIEMBRE,
                Constant.COMMON_STRING_DICIEMBRE });
        SimpleDateFormat newFormat = new SimpleDateFormat(
                Constant.COMMON_STRING_DATE_TO_STATUS_PAYMENT, sym);

        String finalString = newFormat.format(date);
        return finalString;
    }

    /**
     * Metodo para crear request Nequi generico hacia broker
     * 
     * @param requestMessageObjectType
     * @param anyObjectRequest
     * @param anyObjectResponse
     * @param name
     * @param nameSpace
     * @param operation
     * @param requestName
     * @param responseName
     * @param em
     * @return
     */
    public ResponseMessageObjectType createBrokerRequestNequiApp(
            RequestMessageObjectType requestMessageObjectType,
            Object anyObjectRequest, Object anyObjectResponse, String name,
            String nameSpace, String operation, String requestName,
            String responseName, EntityManager em) {

        return createBrokerRequest(requestMessageObjectType, anyObjectRequest,
                anyObjectResponse, name, nameSpace, operation, requestName,
                responseName, em, Constant.COMMON_STRING_NEQUI);

    }

    /**
     * Metodo para crear request Merchant generico hacia broker
     * 
     * @param request
     * @param anyObjectRequest
     * @param anyObjectResponse
     * @param name
     * @param nameSpace
     * @param operation
     * @param requestName
     * @param responseName
     * @param em
     * @return
     */
    public ResponseMessageObjectType createBrokerRequestMerchantApp(
            RequestMessageObjectType requestMessageObjectType,
            Object anyObjectRequest, Object anyObjectResponse, String name,
            String nameSpace, String operation, String requestName,
            String responseName, EntityManager em) {

        return createBrokerRequest(requestMessageObjectType, anyObjectRequest,
                anyObjectResponse, name, nameSpace, operation, requestName,
                responseName, em, Constant.COMMON_STRING_MERCHANT);

    }

    /**
     * Metodo para crear request generico hacia broker
     * 
     * @param request
     * @param anyObjectRequest
     * @param anyObjectResponse
     * @param name
     * @param nameSpace
     * @param operation
     * @param requestName
     * @param responseName
     * @param em
     * @return
     */
    public ResponseMessageObjectType createBrokerRequest(
            RequestMessageObjectType requestMessageObjectType,
            Object anyObjectRequest, Object anyObjectResponse, String name,
            String nameSpace, String operation, String requestName,
            String responseName, EntityManager em, String appTypeValue) {

        RequestHeaderOutMessageType requestESB;
        ResponseMessageType responseMessageType;
        ResponseMessageObjectType responseFront;
        ResponseHeaderOutMessageType responseESB;
        ResourcesPojo resourcesPojo;
        ResponseHeader responseHeader;
        ResponseBody responseBody;

        RequestMessageType requestMessage = requestMessageObjectType
                .getRequestMessage();
        RequestHeader requestHeader = requestMessage.getRequestHeader();
        String messageId = requestHeader.getMessageID();

        // ******************** REQUEST ESB ******************
        // Se configura del Header ESB
        requestESB = this.buildBrokerRequestMessage(requestMessageObjectType,
                name, nameSpace, operation, requestName, responseName,
                appTypeValue, anyObjectRequest);
        // *****************************************************

        // ************* Envío de Mensaje al IIB ****************

        try {

            this.resourceLocator = this.contextUtilHelper
                    .instanceResourceLocatorRemote();

            // TimeOut por operacion
            resourcesPojo = this.resourceLocator.getConnectionData(name,
                    operation);

            if (resourcesPojo == null) {
                logger.error(Constant.ERROR_MESSAGE_SINGLETON,
                        new MiddlewareException(
                                Constant.ERROR_MESSAGE_SINGLETON));
                return this.responseErrorMessage(
                        requestMessageObjectType.getRequestMessage()
                                .getRequestHeader(),
                        Constant.ERROR_CODE_SINGLETON,
                        Constant.ERROR_MESSAGE_SINGLETON,
                        Constant.COMMON_SYSTEM_MDW, em);
            }

            MessageTracerUtil.traceRequestMessage(requestESB, logger, Boolean.TRUE);

            responseESB = RestClientImpl.getInstance().executeOperation(
                    requestESB, resourcesPojo.getBrokerUrl(),
                    resourcesPojo.getBrokerConnectionTimeOut(),
                    resourcesPojo.getBrokerReadTimeOut());

            if (null == responseESB) {

                logger.error(ErrorEnum.BROKER_ERROR,
                        Constant.ERROR_MESSAGE_THERE_WAS_NO_RESPONSE_FROM_THE_IIB,
                        new MiddlewareException(
                                Constant.ERROR_MESSAGE_THERE_WAS_NO_RESPONSE_FROM_THE_IIB));

                return this.responseErrorMessage(
                        requestMessageObjectType.getRequestMessage()
                                .getRequestHeader(),
                        Constant.ERROR_CODE_COMMUNICATION_IIB,
                        Constant.ERROR_MESSAGE_THERE_WAS_NO_RESPONSE_FROM_THE_IIB,
                        Constant.COMMON_SYSTEM_MDW, em);

            }

            MessageTracerUtil.traceResponseMessage(responseESB, logger, Boolean.TRUE);

            if (null == responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()) {
                responseESB.getResponseHeaderOut().getHeader()
                        .getResponseStatus().setStatusCode(DEFAULT_CODE);
            }

            if (!responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()
                    .equals(Constant.COMMON_STRING_ZERO)) {

                logger.error(ErrorEnum.BROKER_ERROR,
                        generateString(
                                responseESB.getResponseHeaderOut().getHeader()
                                        .getResponseStatus().getStatusCode(),
                                Constant.COMMON_STRING_BLANK_SPACE,
                                responseESB.getResponseHeaderOut().getHeader()
                                        .getResponseStatus().getStatusDesc()),
                        null);

            }

        } catch (RestClientException e) {
            logger.error(ErrorEnum.BROKER_ERROR,
                    Constant.ERROR_MESSAGE_IIB_COMMUNICATION, e);
            return this.responseErrorMessage(
                    requestMessageObjectType.getRequestMessage()
                            .getRequestHeader(),
                    Constant.ERROR_CODE_COMMUNICATION_IIB,
                    Constant.COMMON_STRING_EMPTY_STRING,
                    Constant.COMMON_SYSTEM_MDW, em);
        }

        // ********** Mapeo de Respuesta ****************
        // HEADER
        responseMessageType = new ResponseMessageType();
        responseHeader = this.configureResponseFrontHeader(
                requestMessageObjectType.getRequestMessage().getRequestHeader(),
                responseESB.getResponseHeaderOut().getHeader()
                        .getResponseStatus(),
                em);
        responseMessageType.setResponseHeader(responseHeader);

        // BODY
        responseBody = new ResponseBody();

        responseBody.setAny(anyObjectResponse != null ? anyObjectResponse
                : responseESB.getResponseHeaderOut().getBody().getAny());

        responseMessageType.setResponseBody(responseBody);

        // ***************************************************
        responseFront = new ResponseMessageObjectType();
        responseFront.setResponseMessage(responseMessageType);

        return responseFront;

    }

    /**
     * Metodo para crear request generico hacia broker
     * 
     * @param request
     * @param anyObjectRequest
     * @param anyObjectResponse
     * @param name
     * @param nameSpace
     * @param operation
     * @param requestName
     * @param responseName
     * @param em
     * @return
     */
    public ResponseHeaderOutMessageType createBrokerRequestWithoutHomologation(
            RequestMessageObjectType requestMessageObjectType,
            Object anyObjectRequest, Object anyObjectResponse, String name,
            String nameSpace, String operation, String requestName,
            String responseName, EntityManager em, String appTypeValue) {

        return this.createBrokerRequestWithoutHomologation(
                requestMessageObjectType, anyObjectRequest, null,
                anyObjectResponse, name, nameSpace, operation, requestName,
                responseName, em, appTypeValue);

    }

    /**
     * Metodo para crear request generico hacia broker
     * 
     * @param request
     * @param anyObjectRequest
     * @param copyAnyObjectRequest
     * @param anyObjectResponse
     * @param name
     * @param nameSpace
     * @param operation
     * @param requestName
     * @param responseName
     * @param em
     * @return
     */
    public ResponseHeaderOutMessageType createBrokerRequestWithoutHomologation(
            RequestMessageObjectType requestMessageObjectType,
            Object anyObjectRequest, Object copyAnyObjectRequest,
            Object anyObjectResponse, String name, String nameSpace,
            String operation, String requestName, String responseName,
            EntityManager em, String appTypeValue) {

        RequestHeaderOutMessageType requestESB;
        ResponseHeaderOutMessageType responseESB = new ResponseHeaderOutMessageType();
        ResourcesPojo resourcesPojo;

        RequestMessageType requestMessage = requestMessageObjectType
                .getRequestMessage();
        RequestHeader requestHeader = requestMessage.getRequestHeader();
        String messageId = requestHeader.getMessageID();

        // ******************** REQUEST ESB ******************
        // Se configura del Header ESB
        requestESB = this.buildBrokerRequestMessage(requestMessageObjectType,
                name, nameSpace, operation, requestName, responseName,
                appTypeValue, anyObjectRequest);
        // *****************************************************

        // ************* Envío de Mensaje al IIB ****************

        try {

            this.resourceLocator = this.contextUtilHelper
                    .instanceResourceLocatorRemote();
            resourcesPojo = this.resourceLocator.getConnectionData(name,
                    operation);

            if (resourcesPojo == null) {
                logger.error(Constant.ERROR_MESSAGE_SINGLETON,
                        new MiddlewareException(
                                Constant.ERROR_MESSAGE_SINGLETON));
            }

            logger.info(Constant.COMMON_MESSAGE_OPERATION_BROKER + operation);

            logger.info(
                    generateString(Constant.MESSAGEID, messageId,
                            Constant.CLOSE, Constant.COMMON_STRING_BLANK_SPACE,
                            Constant.COMMON_STRING_PAYLOAD,
                            this.parseJSONToString(null != copyAnyObjectRequest
                                    ? copyAnyObjectRequest
                                    : anyObjectRequest)));

            responseESB = RestClientImpl.getInstance().executeOperation(
                    requestESB, resourcesPojo.getBrokerUrl(),
                    resourcesPojo.getBrokerConnectionTimeOut(),
                    resourcesPojo.getBrokerReadTimeOut());

            if (null != responseESB) {
                logger.info(Constant.RESPONSE_BROKER
                        + this.parseJSONToString(responseESB));
            } else {
                logger.info(
                        Constant.RESPONSE_BROKER + Constant.COMMON_STRING_NULL);
            }

            if (null == responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()) {
                responseESB.getResponseHeaderOut().getHeader()
                        .getResponseStatus().setStatusCode(DEFAULT_CODE);
            }

            if (!responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()
                    .equals(Constant.COMMON_STRING_ZERO)) {

                logger.error(ErrorEnum.BROKER_ERROR,
                        generateString(
                                responseESB.getResponseHeaderOut().getHeader()
                                        .getResponseStatus().getStatusCode(),
                                Constant.COMMON_STRING_BLANK_SPACE,
                                responseESB.getResponseHeaderOut().getHeader()
                                        .getResponseStatus().getStatusDesc()),
                        null);

            }

        } catch (RestClientException e) {
            logger.error(ErrorEnum.BROKER_ERROR,
                    Constant.ERROR_MESSAGE_IIB_COMMUNICATION, e);

            return getResponseHeaderOutMessageType(Constant.COMMON_STRING_IIB,
                    Constant.ERROR_CODE_IIB_TIME_OUT_WITHOUT_SYSTEM,
                    Constant.ERROR_MESSAGE_IIB_COMMUNICATION,
                    requestMessageObjectType.getRequestMessage()
                            .getRequestHeader().getMessageID(),
                    requestMessageObjectType.getRequestMessage()
                            .getRequestHeader().getRequestDate());

        }

        return responseESB;
    }

    /**
     * @param system
     * @return
     */
    public ResponseHeaderOutMessageType getResponseHeaderOutMessageType(
            String system, String code, String description, String messageId,
            String invokeDateTime) {

        ResponseHeaderOutType responseHeaderOut;
        HeaderResponseType headerResponseType;
        ResponseStatusType responseStatusType;
        ResponseHeaderOutMessageType responseHeaderOutMessageType;

        responseHeaderOutMessageType = new ResponseHeaderOutMessageType();
        responseHeaderOut = new ResponseHeaderOutType();
        headerResponseType = new HeaderResponseType();
        headerResponseType.setSystemId(system);
        headerResponseType.setInvokerDateTime(invokeDateTime);
        headerResponseType.setMessageId(messageId);
        responseStatusType = new ResponseStatusType();
        responseStatusType.setStatusCode(code);
        responseStatusType.setStatusDesc(description);

        headerResponseType.setResponseStatus(responseStatusType);
        responseHeaderOut.setHeader(headerResponseType);
        responseHeaderOut.setBody(new BodyType());
        responseHeaderOutMessageType.setResponseHeaderOut(responseHeaderOut);

        return responseHeaderOutMessageType;
    }

    /**
     * Metodo que ejecuta una operacion en BROKER, dependiendo de la informacion
     * que recibe como parametro.
     * 
     * @param messageBroker
     * @param anyObject
     * @param request
     * @param response
     * @param name
     * @param nameSpace
     * @param operation
     * @param em
     * @return <code>ResponseMessageObjectType</code>
     */
    public ResponseMessageObjectType createBrokerRequestWithMessageBroker(
            MessageBroker messageBroker, Object anyObject, String request,
            String response, String name, String nameSpace, String operation,
            EntityManager em) {

        RequestHeaderOutMessageType requestESB;
        ResponseMessageType responseMessageType;
        ResponseMessageObjectType responseMessageObjectType;
        RequestHeaderOutType requestHeaderOutType;
        HeaderRequestType headerRequestType;
        BodyType bodyType;
        ResponseHeaderOutMessageType responseESB;
        ResourcesPojo resourcesPojo;
        ResponseHeader responseHeader;
        ResponseBody responseBody;
        RequestHeader requestHeader;
        Date dateServer;

        String messageId = messageBroker.getMessageId();

        // ****************************** REQUEST ESB
        // ****************************************
        // Se configura del Header ESB
        requestHeaderOutType = new RequestHeaderOutType();
        headerRequestType = this.configureRequestESBHeader(new RequestHeader(),
                name, nameSpace, operation);

        dateServer = new Date();
        headerRequestType.setMessageId(messageBroker.getMessageId());
        headerRequestType.setSystemId(messageBroker.getChannel());

        requestHeaderOutType.setHeader(headerRequestType);

        // Configuramos la operación:
        requestHeaderOutType.getHeader().setMessageContext(
                this.configureMessageContext(request, response));

        // Configuramos las propiedades de internacionalizacion:
        this.addPropertyInternationalizationMessageContext(
                requestHeaderOutType.getHeader().getMessageContext(),
                messageBroker.getRegion());

        // Configuramos propiedad AppType al Broker.
        this.addPropertyAppTypeMessageContext(
                requestHeaderOutType.getHeader().getMessageContext(),
                messageBroker.getAppType());

        // Se Configura el Body
        bodyType = new BodyType();
        bodyType.setAny(anyObject);
        requestHeaderOutType.setBody(bodyType);
        // Se configura el RequestESB Completo
        requestESB = new RequestHeaderOutMessageType();
        requestESB.setRequestHeaderOut(requestHeaderOutType);
        // ************************************************************************************

        // Se configura el RequestHeader
        requestHeader = new RequestHeader();
        requestHeader.setChannel(messageBroker.getChannel());
        requestHeader.setClientID(messageBroker.getClientID());
        requestHeader.setMessageID(headerRequestType.getMessageId());
        requestHeader.setRequestDate(dateServer.toString());

        // ****************************** Envío de Mensaje al IIB
        // ****************************

        try {

            this.resourceLocator = this.contextUtilHelper
                    .instanceResourceLocatorRemote();
            resourcesPojo = this.resourceLocator.getConnectionData(name,
                    operation);

            if (resourcesPojo == null) {
                logger.error(Constant.ERROR_MESSAGE_SINGLETON,
                        new MiddlewareException(
                                Constant.ERROR_MESSAGE_SINGLETON));

                return this.responseErrorMessage(requestHeader,
                        Constant.ERROR_CODE_SINGLETON,
                        Constant.ERROR_MESSAGE_SINGLETON,
                        Constant.COMMON_SYSTEM_MDW, em);
            }

            logger.info(Constant.COMMON_MESSAGE_OPERATION_BROKER + operation);
            logger.info(generateString(Constant.MESSAGEID, messageId,
                    Constant.CLOSE, Constant.COMMON_STRING_BLANK_SPACE,
                    Constant.COMMON_STRING_PAYLOAD,
                    this.parseJSONToString(anyObject)));

            responseESB = RestClientImpl.getInstance().executeOperation(
                    requestESB, resourcesPojo.getBrokerUrl(),
                    resourcesPojo.getBrokerConnectionTimeOut(),
                    resourcesPojo.getBrokerReadTimeOut());

            if (null == responseESB) {

                logger.error(ErrorEnum.BROKER_ERROR,
                        Constant.ERROR_MESSAGE_THERE_WAS_NO_RESPONSE_FROM_THE_IIB,
                        null);

                return this.responseErrorMessage(requestHeader,
                        Constant.ERROR_CODE_COMMUNICATION_IIB,
                        Constant.ERROR_MESSAGE_THERE_WAS_NO_RESPONSE_FROM_THE_IIB,
                        Constant.COMMON_SYSTEM_MDW, em);

            }

            logger.info(Constant.RESPONSE_BROKER
                    + this.parseJSONToString(responseESB));

            if (null == responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()) {
                responseESB.getResponseHeaderOut().getHeader()
                        .getResponseStatus().setStatusCode(DEFAULT_CODE);
            }

            if (!responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()
                    .equals(Constant.COMMON_STRING_ZERO)) {

                logger.error(ErrorEnum.BROKER_ERROR,
                        generateString(
                                responseESB.getResponseHeaderOut().getHeader()
                                        .getResponseStatus().getStatusCode(),
                                Constant.COMMON_STRING_BLANK_SPACE,
                                responseESB.getResponseHeaderOut().getHeader()
                                        .getResponseStatus().getStatusDesc()),
                        null);

            }

        } catch (RestClientException e) {
            logger.error(ErrorEnum.BROKER_ERROR,
                    Constant.ERROR_MESSAGE_IIB_COMMUNICATION, e);
            return this.responseErrorMessage(requestHeader,
                    Constant.ERROR_CODE_COMMUNICATION_IIB,
                    Constant.COMMON_STRING_EMPTY_STRING,
                    Constant.COMMON_SYSTEM_MDW, em);
        }

        // ****************************** Mapeo de Respuesta
        // *********************************
        // HEADER
        responseMessageType = new ResponseMessageType();
        responseHeader = this.configureResponseFrontHeader(requestHeader,
                responseESB.getResponseHeaderOut().getHeader()
                        .getResponseStatus(),
                em);
        responseMessageType.setResponseHeader(responseHeader);

        // BODY
        responseBody = new ResponseBody();

        responseBody
                .setAny(responseESB.getResponseHeaderOut().getBody().getAny());
        responseMessageType.setResponseBody(responseBody);

        // ************************************************************************************
        responseMessageObjectType = new ResponseMessageObjectType();
        responseMessageObjectType.setResponseMessage(responseMessageType);

        return responseMessageObjectType;

    }

    /**
     * Metodo que homologa el estado del Push Action. Si no encuentra su
     * homologacion, retorna <code>null</code>.
     * 
     * @param state
     * @return <code>String</code>
     */
    public String homologateStatePushAction(String state) {

        String stateHomologated;

        switch (state) {

        case STRING_STATE_1010:
            stateHomologated = Constant.COMMON_STRING_DOS;
            break;

        case STRING_STATE_1012:
            stateHomologated = Constant.COMMON_STRING_TRES;
            break;

        case STRING_STATE_1013:
            stateHomologated = Constant.COMMON_STRING_CUATRO;
            break;

        default:
            stateHomologated = null;
            break;

        }

        return stateHomologated;

    }

    /**
     * Metodo para validar una repuesta exitosa
     * 
     * @param response
     * @return <code>boolean</code>
     */
    public boolean successResponse(ResponseMessageObjectType response) {

        return Constant.COMMON_STRING_ZERO.equals(response.getResponseMessage()
                .getResponseHeader().getStatus().getStatusCode());

    }

    // Seguridad DFH - CCIO

    /**
     * Metodo que retorna un objeto <code>Object</code> apartir del Info
     * encriptado del ProtectedMsgRQ.
     * 
     * @param request
     * @param varPhone
     * @param clase
     * @param em
     * @return <code>Object</code>
     * @throws SecurityManagerSHLException
     */
    public Object getJSONObjectDecryptAESDiffieHellmanDFH(
            RequestMessageObjectType request, String varPhone, Object clase,
            EntityManager em) throws SecurityManagerSHLException {

        ProtectedMsgServiceRequestType protectedMsgServiceResponseType = null;
        String clientIDCelular = null;
        String clientPublicKey = null;
        String resultDecoded = null;
        String infoCipher = null;
        JSONObject jSONObject = null;
        ObjectMapper objectMapper = null;
        JSONObject resultDecodedJSONObject = null;
        Object objectReturn = null;

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_INICIO
                + Constant.DIFFIE_HELLMAN_GET_JSON_OBJECT_DECRYPT_AES);

        try {

            protectedMsgServiceResponseType = (ProtectedMsgServiceRequestType) this
                    .parsePayload(
                            request.getRequestMessage().getRequestBody()
                                    .getAny(),
                            new ProtectedMsgServiceRequestType());

            clientIDCelular = request.getRequestMessage().getRequestHeader()
                    .getClientID();

            clientPublicKey = request.getRequestMessage().getRequestHeader()
                    .getSecurity().getPublicKey();

            infoCipher = protectedMsgServiceResponseType.getProtectedMsgRQ()
                    .getInfo();

            resultDecoded = this.decryptAESDiffieHellmanDFH(clientIDCelular,
                    clientPublicKey, em, infoCipher);

            resultDecodedJSONObject = new JSONObject(resultDecoded);
            resultDecodedJSONObject.put(varPhone,
                    protectedMsgServiceResponseType.getProtectedMsgRQ()
                            .getPhoneNumber());
            jSONObject = new JSONObject();
            jSONObject.put(protectedMsgServiceResponseType.getProtectedMsgRQ()
                    .getType(), resultDecodedJSONObject);

            objectMapper = new ObjectMapper();
            objectReturn = objectMapper.readValue(jSONObject.toString(),
                    clase.getClass());

            logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_FIN
                    + Constant.DIFFIE_HELLMAN_GET_JSON_OBJECT_DECRYPT_AES);

            return objectReturn;

        } catch (IOException | MiddlewareException e) {

            StringBuilder errorMessage = new StringBuilder(ERROR_PARSING);
            errorMessage.append(clase.getClass().getName());
            logger.error(ErrorEnum.SECURITY_ERROR, errorMessage.toString(), e);
            throw new SecurityManagerSHLException(errorMessage.toString(), e);
        }

    }

    /**
     * Recibe un texto plano donde utiliza la clave secreta Diffie Hellman y
     * retorna el texto cifrado con el algoritmo AES.
     * 
     * @param infoCipher
     * @param seguridadDfh
     * @param clientPublicKey
     * @return <code>String</code>
     * @throws SecurityManagerSHLException
     */
    public String encryptAESDiffieHellmanDFH(String infoPlainText,
            SeguridadDfh seguridadDfh, String clientPublicKey)

            throws SecurityManagerSHLException {

        String resultCipher = null;

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_INICIO
                + Constant.DIFFIE_HELLMAN_ENCRYPT_AES);

        if (seguridadDfh == null) {

            SecurityManagerSHLException securityManagerSHLException = new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_WITHOUT_SECRET_KEY);
            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY,
                    securityManagerSHLException);
            throw securityManagerSHLException;

        } else if (!seguridadDfh.getClavePublicaCliente()
                .equals(clientPublicKey)) {

            SecurityManagerSHLException securityManagerSHLException = new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_PUBLIC_KEY_NOT_MATCH);
            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY,
                    securityManagerSHLException);
            throw securityManagerSHLException;

        } else {

            resultCipher = this.securityManager.encryptAESDiffieHellman(
                    seguridadDfh.getClaveSecreta(), infoPlainText);

        }

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_FIN
                + Constant.DIFFIE_HELLMAN_ENCRYPT_AES);

        return resultCipher;

    }

    /**
     * Recibe un texto plano donde utiliza la clave secreta Diffie Hellman y
     * retorna el texto cifrado con el algoritmo AES.
     * 
     * @param celular
     * @param clientPublicKey
     * @param em
     * @param infoPlainText
     * @return <code>String</code>
     * @throws SecurityManagerSHLException
     */
    public String encryptAESDiffieHellmanDFH(String celular,
            String clientPublicKey, EntityManager em, String infoPlainText)
            throws SecurityManagerSHLException {

        SeguridadDfh seguridadDfh = null;
        String resultCipher = null;

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_INICIO
                + Constant.DIFFIE_HELLMAN_ENCRYPT_AES);

        try {

            seguridadDfh = this.seguridadDFHJPAService.findSeguridadDfh(celular,
                    em);

            resultCipher = this.encryptAESDiffieHellmanDFH(infoPlainText,
                    seguridadDfh, clientPublicKey);

        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY_DB_FIND_SEGURIDAD, e);
            throw new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_DB_FIND_SEGURIDAD, e);

        } catch (SecurityManagerSHLException e) {
            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY_ENCRYPT_AES_DIFFIE_HELLMAN,
                    e);
            throw e;

        } catch (Exception e) {
            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY_ENCRYPT_AES_DIFFIE_HELLMAN_GENERAL,
                    e);
            throw new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_ENCRYPT_AES_DIFFIE_HELLMAN_GENERAL,
                    e);
        }

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_FIN
                + Constant.DIFFIE_HELLMAN_ENCRYPT_AES);

        return resultCipher;

    }

    /**
     * Metodo que retorna el objeto <code>JSONObject</code> Any cifrado en un
     * <code>String</code>.
     * 
     * @param request
     * @param any
     * @param em
     * @return <code>String</code>
     * @throws SecurityManagerSHLException
     */
    public String getStringEncryptAESDiffieHellmanDFH(
            RequestMessageObjectType request, Object any, EntityManager em)
            throws SecurityManagerSHLException {

        String clientIDCelular = null;
        String clientPublicKey = null;
        String encryptedObj = null;
        Map<String, String> anyMap = null;
        JSONObject jsonObj = null;

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_INICIO
                + Constant.DIFFIE_HELLMAN_GET_STRING_ENCRYPT_AES);

        try {

            // Se convierte el ANY a un objecto Json, para luego encriptarlo
            if (any instanceof Map) {

                anyMap = (Map<String, String>) any;
                jsonObj = new JSONObject(anyMap);

            } else {

                jsonObj = new JSONObject(any);
            }

            clientIDCelular = request.getRequestMessage().getRequestHeader()
                    .getClientID();

            clientPublicKey = request.getRequestMessage().getRequestHeader()
                    .getSecurity().getPublicKey();

            encryptedObj = this.encryptAESDiffieHellmanDFH(clientIDCelular,
                    clientPublicKey, em, jsonObj.toString());

            logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_FIN
                    + Constant.DIFFIE_HELLMAN_GET_STRING_ENCRYPT_AES);

            return encryptedObj;

        } catch (Exception e) {

            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY_ENCRYPT_AES_DIFFIE_HELLMAN_GENERAL,
                    e);
            throw new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_ENCRYPT_AES_DIFFIE_HELLMAN_GENERAL,
                    e);
        }

    }

    /**
     * Recibe un texto cifrado en AES y con clave secreta Diffie Hellman y
     * retorna el texto descifrado.
     * 
     * @param infoCipher
     * @param seguridadDfh
     * @param clientPublicKey
     * @return <code>String</code>
     * @throws SecurityManagerSHLException
     */
    public String decryptAESDiffieHellmanDFH(String infoCipher,
            SeguridadDfh seguridadDfh, String clientPublicKey)

            throws SecurityManagerSHLException {

        String resultDescifrado = null;

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_INICIO
                + Constant.DIFFIE_HELLMAN_DECRYPT_AES);

        if (seguridadDfh == null) {

            SecurityManagerSHLException securityManagerSHLException = new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_WITHOUT_SECRET_KEY);
            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY,
                    securityManagerSHLException);
            throw securityManagerSHLException;

        } else if (!seguridadDfh.getClavePublicaCliente()
                .equals(clientPublicKey)) {

            SecurityManagerSHLException securityManagerSHLException = new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_PUBLIC_KEY_NOT_MATCH);
            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY,
                    securityManagerSHLException);
            throw securityManagerSHLException;

        } else {

            resultDescifrado = this.securityManager.decryptAESDiffieHellman(
                    seguridadDfh.getClaveSecreta(), infoCipher);

        }

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_FIN
                + Constant.DIFFIE_HELLMAN_DECRYPT_AES);

        return resultDescifrado;

    }

    /**
     * Recibe un texto cifrado en AES y con clave secreta Diffie Hellman y
     * retorna el texto descifrado.
     * 
     * @param celular
     * @param clientPublicKey
     * @param em
     * @param infoCipher
     * @return <code>String</code>
     * @throws SecurityManagerSHLException
     */
    public String decryptAESDiffieHellmanDFH(String celular,
            String clientPublicKey, EntityManager em, String infoCipher)
            throws SecurityManagerSHLException {

        SeguridadDfh seguridadDfh = null;
        String resultDescifrado = null;

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_INICIO
                + Constant.DIFFIE_HELLMAN_DECRYPT_AES);

        try {

            seguridadDfh = this.seguridadDFHJPAService.findSeguridadDfh(celular,
                    em);

            resultDescifrado = this.decryptAESDiffieHellmanDFH(infoCipher,
                    seguridadDfh, clientPublicKey);

        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY_DB_FIND_SEGURIDAD, e);
            throw new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_DB_FIND_SEGURIDAD, e);

        } catch (SecurityManagerSHLException e) {
            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY_DECRYPT_AES_DIFFIE_HELLMAN,
                    e);
            throw e;

        } catch (Exception e) {
            logger.error(ErrorEnum.SECURITY_ERROR,
                    Constant.ERROR_MESSAGE_SECURITY_DECRYPT_AES_DIFFIE_HELLMAN_GENERAL,
                    e);
            throw new SecurityManagerSHLException(
                    Constant.ERROR_MESSAGE_SECURITY_DECRYPT_AES_DIFFIE_HELLMAN_GENERAL,
                    e);
        }

        logger.debug(Constant.DIFFIE_HELLMAN_SERVICE_CONTROLLER_HELPER_FIN
                + Constant.DIFFIE_HELLMAN_DECRYPT_AES);

        return resultDescifrado;

    }

    /**
     * Metodo que devuelve parametro por codigo
     * 
     * @param parameters
     * @param code
     * @return Parametro
     */
    public Parametro getParameterByCode(List<Parametro> parameters,
            String code) {
        if (null != parameters) {
            for (Parametro parametro : parameters) {
                if (parametro.getParametroId().equals(code)) {
                    return parametro;
                }
            }
        }
        return null;
    }

    /**
     * Metodo que devuelve parametro por Nombre
     * 
     * @param parameters
     * @param name
     * @return Parametro
     */
    public Parametro getParameterByName(List<Parametro> parameters,
            String name) {
        if (null != parameters) {
            for (Parametro parametro : parameters) {
                if (parametro.getNombre().equalsIgnoreCase(name)) {
                    return parametro;
                }
            }
        }
        return null;
    }

    /**
     * Metodo que devuelve parametro por Orden
     * 
     * @param parameters
     * @param order
     * @return Parametro
     */
    public Parametro getParameterByOrder(List<Parametro> parameters,
            int order) {
        if (null != parameters) {
            for (Parametro parametro : parameters) {
                if (parametro.getOrden() == order) {
                    return parametro;
                }
            }
        }
        return null;
    }

    /**
     * <p>
     * Metodo que busca un parametro por nombre y codigo del pais.
     * </p>
     * 
     * @param parameters
     * @param parameterName
     * @param countryCode
     * @return {@link List} {@link Parametro}
     */
    public Parametro getParameterByNameAndCountryCode(
            List<Parametro> parameters, String parameterName,
            String countryCode) {
        Parametro parameterFound = null;
        if (null != parameters) {
            for (Parametro parameter : parameters) {
                if (parameterName.equalsIgnoreCase(parameter.getNombre())
                        && countryCode.equals(parameter.getDivisionGeografica()
                                .getCodigoDivision())) {
                    parameterFound = parameter;
                }
            }
        }
        return parameterFound;
    }

    /**
     * Metodo que le adiciona horas y minutos a una fecha que llega como tipo
     * <code>Long</code>. Retorna la nueva fecha de tipo <code>Date</code>.
     * 
     * @param fechaTime
     * @param hourMillis
     * @param minutesMillis
     * @return <code>Date</code>
     */
    public Date getDateAddHoursAndMinutes(Long fechaTime, long hourMillis,
            long minutesMillis) {

        Date dateAddHoursAndMinutes;

        dateAddHoursAndMinutes = new Date(
                fechaTime + TimeUnit.HOURS.toMillis(hourMillis)
                        + TimeUnit.MINUTES.toMillis(minutesMillis));

        return dateAddHoursAndMinutes;
    }

    /**
     * <p>
     * Metodo que construye el request enviado a broker.
     * </p>
     * 
     * @param requestMessageObjectType
     * @param name
     * @param nameSpace
     * @param operation
     * @param requestName
     * @param responseName
     * @param appTypeValue
     * @param anyObjectRequest
     * @return
     */
    public RequestHeaderOutMessageType buildBrokerRequestMessage(
            RequestMessageObjectType requestMessageObjectType, String name,
            String nameSpace, String operation, String requestName,
            String responseName, String appTypeValue, Object anyObjectRequest) {
        RequestHeaderOutMessageType requestESB;
        RequestHeaderOutType requestHeaderOutType;
        HeaderRequestType headerRequestType;
        BodyType bodyType;

        requestHeaderOutType = new RequestHeaderOutType();
        headerRequestType = this.configureRequestESBHeader(
                requestMessageObjectType.getRequestMessage().getRequestHeader(),
                name, nameSpace, operation);
        requestHeaderOutType.setHeader(headerRequestType);

        // Configuramos la operación:
        requestHeaderOutType.getHeader().setMessageContext(
                this.configureMessageContext(requestName, responseName));

        // Configuramos las propiedades de internacionalizacion:
        this.addPropertyInternationalizationMessageContext(
                requestHeaderOutType.getHeader().getMessageContext(),
                requestMessageObjectType.getRequestMessage().getRequestHeader()
                        .getDestination().getServiceRegion());

        // Configuramos propiedad AppType al Broker.
        this.addPropertyAppTypeMessageContext(
                requestHeaderOutType.getHeader().getMessageContext(),
                appTypeValue);

        // Se Configura el Body
        bodyType = new BodyType();
        bodyType.setAny(anyObjectRequest);
        requestHeaderOutType.setBody(bodyType);
        // Se configura el RequestESB Completo
        requestESB = new RequestHeaderOutMessageType();
        requestESB.setRequestHeaderOut(requestHeaderOutType);

        return requestESB;
    }

    /**
     * Genera un mensaje de Response homologando al sistema destino que se le
     * indique.
     * 
     * @param requestHeader
     * @param errorCode
     * @param errorMessage
     * @param system
     * @param destinationSystem
     *            Sistema destino a donde se va a mostrar el error a homologar.
     * @param em
     * @return
     */
    public ResponseMessageObjectType responseErrorMessage(
            RequestHeader requestHeader, String errorCode, String errorMessage,
            String system, String destinationSystem, EntityManager em) {

        return buildResponseErrorMessage(requestHeader, errorCode, errorMessage,
                system, destinationSystem, em);
    }

    /**
     * <p>
     * Metodo que construye el mensaje tomando el sistema destino para
     * homologar.
     * </p>
     * 
     * @param requestHeader
     * @param errorCode
     * @param errorMessage
     * @param system
     * @param destinationSystem
     *            sistema destino al cual se va a homologar el error.
     * @param em
     * @return
     */
    private ResponseMessageObjectType buildResponseErrorMessage(
            RequestHeader requestHeader, String errorCode, String errorMessage,
            String system, String destinationSystem, EntityManager em) {
        ResponseMessageObjectType response = new ResponseMessageObjectType();
        ResponseMessageType responseMessageType = new ResponseMessageType();

        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setChannel(requestHeader.getChannel());
        responseHeader.setClientID(requestHeader.getClientID());
        responseHeader.setDestination(requestHeader.getDestination());
        responseHeader.setMessageID(requestHeader.getMessageID());
        responseHeader.setResponseDate(getCurrentTimeStamp());

        StatusType statusType = null;
        if (errorCode.equals(SUCCESS_CODE)) {
            statusType = new StatusType();
            statusType.setStatusCode(SUCCESS_CODE);
            statusType.setStatusDesc(SUCCESS_DESC);
        } else if (null != requestHeader.getBlockingNequiAccessMode()
                && Constant.COMMON_STRING_TRUE.equalsIgnoreCase(
                        requestHeader.getBlockingNequiAccessMode().trim())) {
            statusType = new StatusType();
            statusType.setStatusCode(Constant.MAINTENANCE_CODE);
            statusType.setStatusDesc(null != requestHeader
                    .getBlockingNequiAccessModeMessage()
                    && !requestHeader.getBlockingNequiAccessModeMessage().trim()
                            .isEmpty()
                                    ? requestHeader
                                            .getBlockingNequiAccessModeMessage()
                                            .trim()
                                    : Constant.COMMON_MESSAGE_BLOCKING_NEQUI_ACCESS_MODE);

        } else if (null != requestHeader.getMaintenanceMode()
                && Constant.COMMON_STRING_TRUE.equalsIgnoreCase(
                        requestHeader.getMaintenanceMode().trim())) {
            statusType = new StatusType();
            statusType.setStatusCode(Constant.MAINTENANCE_CODE);
            statusType.setStatusDesc(null != requestHeader
                    .getMaintenanceModeMessage()
                    && !requestHeader.getMaintenanceModeMessage().trim()
                            .isEmpty()
                                    ? requestHeader.getMaintenanceModeMessage()
                                            .trim()
                                    : Constant.COMMON_MESSAGE_MAINTENANCE_MODE);
        } else {
            ResponseStatusType responseStatus = new ResponseStatusType();
            responseStatus.setStatusCode(errorCode);
            responseStatus.setSystem(system);
            responseStatus.setStatusDesc(errorMessage);
            statusType = getErrorHomologation(responseStatus, requestHeader,
                    destinationSystem, em);
        }
        responseHeader.setStatus(statusType);
        ResponseBody responseBody = new ResponseBody();
        responseBody.setAny(Constant.COMMON_STRING_EMPTY_STRING);
        responseMessageType.setResponseHeader(responseHeader);
        responseMessageType.setResponseBody(responseBody);
        response.setResponseMessage(responseMessageType);

        return response;
    }

    /**
     * <p>
     * Metodo que consume un servicio de broker pero que retorna el mensaje
     * directamente sin homologacion.
     * </p>
     * 
     * @param messageBroker
     * @param anyObjectRequest
     * @param request
     * @param response
     * @param name
     * @param nameSpace
     * @param operation
     * @param em
     * @return ResponseHeaderOutMessageType
     */
    public ResponseHeaderOutMessageType createBrokerRequestWithoutHomologation(
            MessageBroker messageBroker, Object anyObjectRequest,
            String request, String response, String name, String nameSpace,
            String operation, EntityManager em) {
        RequestHeaderOutMessageType requestESB;
        RequestHeaderOutType requestHeaderOutType;
        HeaderRequestType headerRequestType;
        BodyType bodyType;
        ResponseHeaderOutMessageType responseESB = null;
        ResourcesPojo resourcesPojo;
        RequestHeader requestHeader;
        Date dateServer;

        String messageId = messageBroker.getMessageId();

        // ****************************** REQUEST ESB
        // ****************************************
        // Se configura del Header ESB
        requestHeaderOutType = new RequestHeaderOutType();
        headerRequestType = this.configureRequestESBHeader(new RequestHeader(),
                name, nameSpace, operation);

        dateServer = new Date();
        headerRequestType.setMessageId(messageBroker.getMessageId());
        headerRequestType.setSystemId(messageBroker.getChannel());

        requestHeaderOutType.setHeader(headerRequestType);

        // Configuramos la operación:
        requestHeaderOutType.getHeader().setMessageContext(
                this.configureMessageContext(request, response));

        // Configuramos las propiedades de internacionalizacion:
        this.addPropertyInternationalizationMessageContext(
                requestHeaderOutType.getHeader().getMessageContext(),
                messageBroker.getRegion());

        // Configuramos propiedad AppType al Broker.
        this.addPropertyAppTypeMessageContext(
                requestHeaderOutType.getHeader().getMessageContext(),
                messageBroker.getAppType());

        // Se Configura el Body
        bodyType = new BodyType();
        bodyType.setAny(anyObjectRequest);
        requestHeaderOutType.setBody(bodyType);
        // Se configura el RequestESB Completo
        requestESB = new RequestHeaderOutMessageType();
        requestESB.setRequestHeaderOut(requestHeaderOutType);
        // ************************************************************************************

        // Se configura el RequestHeader
        requestHeader = new RequestHeader();
        requestHeader.setChannel(messageBroker.getChannel());
        requestHeader.setClientID(messageBroker.getClientID());
        requestHeader.setMessageID(headerRequestType.getMessageId());
        requestHeader.setRequestDate(dateServer.toString());

        // ************* Envío de Mensaje al IIB ****************

        try {

            this.resourceLocator = this.contextUtilHelper
                    .instanceResourceLocatorRemote();
            resourcesPojo = this.resourceLocator.getConnectionData(name,
                    operation);

            if (resourcesPojo == null) {
                logger.error(Constant.ERROR_MESSAGE_SINGLETON,
                        new MiddlewareException(
                                Constant.ERROR_MESSAGE_SINGLETON));
            }

            logger.info(Constant.COMMON_MESSAGE_OPERATION_BROKER + operation);
            logger.info(generateString(Constant.MESSAGEID, messageId,
                    Constant.CLOSE, Constant.COMMON_STRING_BLANK_SPACE,
                    Constant.COMMON_STRING_PAYLOAD,
                    this.parseJSONToString(anyObjectRequest)));

            responseESB = RestClientImpl.getInstance().executeOperation(
                    requestESB, resourcesPojo.getBrokerUrl(),
                    resourcesPojo.getBrokerConnectionTimeOut(),
                    resourcesPojo.getBrokerReadTimeOut());

            if (null != responseESB) {
                logger.info(Constant.RESPONSE_BROKER
                        + this.parseJSONToString(responseESB));
            } else {
                logger.info(
                        Constant.RESPONSE_BROKER + Constant.COMMON_STRING_NULL);
            }

            if (null == responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()) {
                responseESB.getResponseHeaderOut().getHeader()
                        .getResponseStatus().setStatusCode(DEFAULT_CODE);
            }

            if (!responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()
                    .equals(Constant.COMMON_STRING_ZERO)) {

                logger.error(ErrorEnum.BROKER_ERROR,
                        generateString(
                                responseESB.getResponseHeaderOut().getHeader()
                                        .getResponseStatus().getStatusCode(),
                                Constant.COMMON_STRING_BLANK_SPACE,
                                responseESB.getResponseHeaderOut().getHeader()
                                        .getResponseStatus().getStatusDesc()),
                        null);

            }

        } catch (RestClientException e) {
            logger.error(ErrorEnum.BROKER_ERROR,
                    Constant.ERROR_MESSAGE_IIB_COMMUNICATION, e);
        }

        return responseESB;
    }

    /**
     * <p>
     * M&eacute;todo que obtiene el valor del par&aacute;metro especificado por
     * su nombre y regi&oacute;n.
     * </p>
     * <p>
     * <strong>NOTA:</strong> El valor del parámetro es nulo si en la lista
     * <code>parameters</code> no se encuetra un par&aacute;metro con el nombre
     * <code>parameterName</code> y regi&oacute;n <code>countryCode</code>.
     * </p>
     * 
     * @param parameters
     * @param parameterName
     * @param countryCode
     * @return valor del par&aacute;metro con el nombre y regi&oacute;n
     *         especificado.
     */
    public String getParameterValueByNameAndCountryCode(
            List<Parametro> parameters, String parameterName,
            String countryCode) {
        String parameterValue = null;
        Parametro parameterFound = this.getParameterByNameAndCountryCode(
                parameters, parameterName, countryCode);
        if (null != parameterFound) {
            parameterValue = parameterFound.getValor();
        }
        return parameterValue;
    }

    /**
     * <p>
     * Metodo que construye el request enviado a broker.
     * </p>
     * 
     * @param requestMessageObjectType
     * @param name
     * @param nameSpace
     * @param operation
     * @param requestName
     * @param responseName
     * @param appTypeValue
     * @param anyObjectRequest
     * @param messageContextAdditionalKeys
     *            Mapa con claves y sus valores para agregarlos al
     *            messagecontext de broker.
     * @return
     */
    public RequestHeaderOutMessageType buildBrokerRequestMessage(
            RequestMessageObjectType requestMessageObjectType, String name,
            String nameSpace, String operation, String requestName,
            String responseName, String appTypeValue, Object anyObjectRequest,
            Map<String, String> messageContextAdditionalKeys) {
        RequestHeaderOutMessageType requestESB;
        RequestHeaderOutType requestHeaderOutType;
        HeaderRequestType headerRequestType;
        BodyType bodyType;

        requestHeaderOutType = new RequestHeaderOutType();
        headerRequestType = this.configureRequestESBHeader(
                requestMessageObjectType.getRequestMessage().getRequestHeader(),
                name, nameSpace, operation);
        requestHeaderOutType.setHeader(headerRequestType);

        // Configuramos la operación:
        requestHeaderOutType.getHeader().setMessageContext(
                this.configureMessageContext(requestName, responseName));

        // Configuramos las propiedades de internacionalizacion:
        this.addPropertyInternationalizationMessageContext(
                requestHeaderOutType.getHeader().getMessageContext(),
                requestMessageObjectType.getRequestMessage().getRequestHeader()
                        .getDestination().getServiceRegion());

        // Configuramos propiedad AppType al Broker.
        this.addPropertyAppTypeMessageContext(
                requestHeaderOutType.getHeader().getMessageContext(),
                appTypeValue);

        if (null != messageContextAdditionalKeys
                && !messageContextAdditionalKeys.isEmpty()) {
            for (Map.Entry<String, String> messageContextKey : messageContextAdditionalKeys
                    .entrySet()) {
                this.addPropertiesMessageContext(
                        requestHeaderOutType.getHeader().getMessageContext(),
                        messageContextKey.getKey(),
                        messageContextKey.getValue());
            }
        }

        // Se Configura el Body
        bodyType = new BodyType();
        bodyType.setAny(anyObjectRequest);
        requestHeaderOutType.setBody(bodyType);
        // Se configura el RequestESB Completo
        requestESB = new RequestHeaderOutMessageType();
        requestESB.setRequestHeaderOut(requestHeaderOutType);

        return requestESB;
    }

    /**
     * Metodo que adiciona las propiedades <code>key</code> y <code>valor</code>
     * a un <code>MessageContextType</code> del BROKER.
     * 
     * @param messageContextType
     * @param key
     *            llave
     * @param value
     *            valor
     * @return
     */
    public MessageContextType addPropertiesMessageContext(
            MessageContextType messageContextType, String key, String value) {

        PropertyType propertyTypeRQ;

        if (null == messageContextType) {
            messageContextType = new MessageContextType();
        }

        propertyTypeRQ = new PropertyType();

        propertyTypeRQ.setKey(key);
        propertyTypeRQ.setValue(value);

        messageContextType.getProperty().add(propertyTypeRQ);

        return messageContextType;

    }

    /**
     * Metodo para crear request generico hacia broker
     * 
     * @param request
     * @param anyObjectRequest
     * @param anyObjectResponse
     * @param name
     * @param nameSpace
     * @param operation
     * @param requestName
     * @param responseName
     * @param requestMessageObjectType
     * @param appTypeValue
     * @param messageContextAdditionalKeys
     * @param em
     * @return
     */
    public ResponseMessageObjectType createBrokerRequest(
            RequestMessageObjectType requestMessageObjectType,
            Object anyObjectRequest, Object anyObjectResponse, String name,
            String nameSpace, String operation, String requestName,
            String responseName, EntityManager em, String appTypeValue,
            Map<String, String> messageContextAdditionalKeys) {

        RequestHeaderOutMessageType requestESB;
        ResponseMessageType responseMessageType;
        ResponseMessageObjectType responseFront;
        ResponseHeaderOutMessageType responseESB;
        ResourcesPojo resourcesPojo;
        ResponseHeader responseHeader;
        ResponseBody responseBody;

        RequestMessageType requestMessage = requestMessageObjectType
                .getRequestMessage();
        RequestHeader requestHeader = requestMessage.getRequestHeader();
        String messageId = requestHeader.getMessageID();

        // ******************** REQUEST ESB ******************
        // Se configura del Header ESB
        requestESB = this.buildBrokerRequestMessage(requestMessageObjectType,
                name, nameSpace, operation, requestName, responseName,
                appTypeValue, anyObjectRequest, messageContextAdditionalKeys);
        // *****************************************************

        // ************* Envío de Mensaje al IIB ****************

        try {

            this.resourceLocator = this.contextUtilHelper
                    .instanceResourceLocatorRemote();
            resourcesPojo = this.resourceLocator.getConnectionData(name,
                    operation);

            if (resourcesPojo == null) {
                logger.error(Constant.ERROR_MESSAGE_SINGLETON,
                        new MiddlewareException(
                                Constant.ERROR_MESSAGE_SINGLETON));
                return this.responseErrorMessage(
                        requestMessageObjectType.getRequestMessage()
                                .getRequestHeader(),
                        Constant.ERROR_CODE_SINGLETON,
                        Constant.ERROR_MESSAGE_SINGLETON,
                        Constant.COMMON_SYSTEM_MDW, em);
            }

            logger.info(Constant.COMMON_MESSAGE_OPERATION_BROKER + operation);
            logger.info(generateString(Constant.MESSAGEID, messageId,
                    Constant.CLOSE, Constant.COMMON_STRING_BLANK_SPACE,
                    Constant.COMMON_STRING_PAYLOAD,
                    this.parseJSONToString(anyObjectRequest)));

            responseESB = RestClientImpl.getInstance().executeOperation(
                    requestESB, resourcesPojo.getBrokerUrl(),
                    resourcesPojo.getBrokerConnectionTimeOut(),
                    resourcesPojo.getBrokerReadTimeOut());

            if (null == responseESB) {

                logger.error(ErrorEnum.BROKER_ERROR,
                        Constant.ERROR_MESSAGE_THERE_WAS_NO_RESPONSE_FROM_THE_IIB,
                        null);

                return this.responseErrorMessage(
                        requestMessageObjectType.getRequestMessage()
                                .getRequestHeader(),
                        Constant.ERROR_CODE_COMMUNICATION_IIB,
                        Constant.ERROR_MESSAGE_THERE_WAS_NO_RESPONSE_FROM_THE_IIB,
                        Constant.COMMON_SYSTEM_MDW, em);

            }

            logger.info(Constant.RESPONSE_BROKER
                    + this.parseJSONToString(responseESB));

            if (!responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()
                    .equals(Constant.COMMON_STRING_ZERO)) {

                logger.error(ErrorEnum.BROKER_ERROR,
                        responseESB.getResponseHeaderOut().getHeader()
                                .getResponseStatus().getStatusCode()
                                + Constant.COMMON_STRING_BLANK_SPACE
                                + responseESB.getResponseHeaderOut().getHeader()
                                        .getResponseStatus().getStatusDesc(),
                        null);

            }

        } catch (RestClientException e) {
            logger.error(ErrorEnum.BROKER_ERROR,
                    Constant.ERROR_MESSAGE_IIB_COMMUNICATION, e);
            return this.responseErrorMessage(
                    requestMessageObjectType.getRequestMessage()
                            .getRequestHeader(),
                    Constant.ERROR_CODE_COMMUNICATION_IIB,
                    Constant.COMMON_STRING_EMPTY_STRING,
                    Constant.COMMON_SYSTEM_MDW, em);
        }

        // ********** Mapeo de Respuesta ****************
        // HEADER
        responseMessageType = new ResponseMessageType();
        responseHeader = this.configureResponseFrontHeader(
                requestMessageObjectType.getRequestMessage().getRequestHeader(),
                responseESB.getResponseHeaderOut().getHeader()
                        .getResponseStatus(),
                em);
        responseMessageType.setResponseHeader(responseHeader);

        // BODY
        responseBody = new ResponseBody();

        responseBody.setAny(anyObjectResponse != null ? anyObjectResponse
                : responseESB.getResponseHeaderOut().getBody().getAny());

        responseMessageType.setResponseBody(responseBody);

        // ***************************************************
        responseFront = new ResponseMessageObjectType();
        responseFront.setResponseMessage(responseMessageType);

        return responseFront;

    }

    /**
     * Metodo para crear request Nequi generico hacia broker
     * 
     * @param request
     * @param anyObjectRequest
     * @param anyObjectResponse
     * @param name
     * @param nameSpace
     * @param operation
     * @param requestName
     * @param responseName
     * @param requestMessageObjectType
     * @param messageContextAdditionalProperties
     * @param em
     * @return
     */
    public ResponseMessageObjectType createBrokerRequestNequiApp(
            RequestMessageObjectType requestMessageObjectType,
            Object anyObjectRequest, Object anyObjectResponse, String name,
            String nameSpace, String operation, String requestName,
            String responseName,
            Map<String, String> messageContextAdditionalProperties,
            EntityManager em) {

        return createBrokerRequest(requestMessageObjectType, anyObjectRequest,
                anyObjectResponse, name, nameSpace, operation, requestName,
                responseName, em, Constant.COMMON_STRING_NEQUI,
                messageContextAdditionalProperties);

    }

    /**
     * Metodo para validar si los campos del request vienen vacios
     * 
     * @param clase
     * @param validateObject
     * @return Boolean
     * @throws MiddlewareException
     */
    public Boolean validateEmptyFields(Class clase, Object validateObject)
            throws MiddlewareException {
        Method[] userMethods = clase.getMethods();
        try {
            for (Method method : userMethods) {
                Object fieldValue;

                if (isGetter(method)) {

                    fieldValue = method.invoke(validateObject, null);

                    if (null == fieldValue || fieldValue.toString().isEmpty()) {
                        return false;
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new MiddlewareException(e);
        }
        return true;
    }

    /**
     * Metodo que valida si en metodo es get.
     * 
     * @param method
     * @return boolean
     */
    public static boolean isGetter(Method method) {
        if (!method.getName().startsWith(Constant.COMMON_STRING_GET))
            return false;
        if (Constant.INT_ZERO != method.getParameterTypes().length)
            return false;
        return !void.class.equals(method.getReturnType());
    }

    /**
     * <p>
     * Genera una copia del objecto <code>originObject</code> que es por valor,
     * en lugar de una copia por referencia.
     * </p>
     * 
     * @param originObject
     *            Objeto original a copiar.
     * @return Objeto duplicado a partir del objeto original.
     * @throws MiddlewareException
     */
    public Object generateObjectCopy(Object originObject)
            throws MiddlewareException {

        logger.debug(Constant.SERVICE_CONTROLLER_HELPER_UTILITY_INICIO
                + Constant.SERVICE_CONTROLLER_HELPER_UTILITY_GENERATE_OBJECT_COPY);

        try {

            Object targetObject = SerializationUtils
                    .clone((Serializable) originObject);

            logger.debug(Constant.SERVICE_CONTROLLER_HELPER_UTILITY_FIN
                    + Constant.SERVICE_CONTROLLER_HELPER_UTILITY_GENERATE_OBJECT_COPY);

            return targetObject;

        } catch (SerializationException e) {

            logger.error(
                    Constant.ERROR_MESSAGE_SERVICE_CONTROLLER_HELPER_UTILITY_GENERATE_OBJECT_COPY,
                    e);
            throw new MiddlewareException(
                    Constant.ERROR_MESSAGE_SERVICE_CONTROLLER_HELPER_UTILITY_GENERATE_OBJECT_COPY,
                    e);

        } catch (Exception e) {

            logger.error(
                    Constant.ERROR_MESSAGE_SERVICE_CONTROLLER_HELPER_UTILITY_GENERAL,
                    e);
            throw new MiddlewareException(
                    Constant.ERROR_MESSAGE_SERVICE_CONTROLLER_HELPER_UTILITY_GENERAL,
                    e);
        }

    }

    /**
     * <p>
     * M&eacute;todo que env&iacute;a la petici&oacute;n para consumir un
     * servicio de broker.
     * </p>
     * 
     * @param requestMessageObjectType
     * @param anyObjectRequest
     *            Objeto que contiene el payload del request a broker.
     * @param anyObjectResponse
     * @param name
     * @param nameSpace
     * @param operation
     * @param requestName
     * @param responseName
     * @param anyObjectRequestToLogger
     *            Objeto que contiene el payload del request a broker y que es
     *            para fines de <strong>impresi&oacute;n</strong> en el log del
     *            WAS.
     * @param em
     * @return
     */
    public ResponseMessageObjectType createBrokerRequestNequiApp(
            RequestMessageObjectType requestMessageObjectType,
            Object anyObjectRequest, Object anyObjectResponse, String name,
            String nameSpace, String operation, String requestName,
            String responseName, Object anyObjectRequestToLogger,
            EntityManager em) {

        return createBrokerRequest(requestMessageObjectType, anyObjectRequest,
                anyObjectResponse, name, nameSpace, operation, requestName,
                responseName, em, Constant.COMMON_STRING_NEQUI,
                anyObjectRequestToLogger);

    }

    /**
     * <p>
     * M&eacute;todo para crear request generico hacia broker.
     * </p>
     * 
     * @param requestMessageObjectType
     * @param anyObjectRequest
     *            Objeto que contiene el payload del request a broker.
     * @param anyObjectResponse
     * @param name
     * @param nameSpace
     * @param operation
     * @param requestName
     * @param responseName
     * @param em
     * @param appTypeValue
     * @param anyObjectRequestToLogger
     *            Objeto que contiene el payload del request a broker y que es
     *            para fines de <strong>impresi&oacute;n</strong> en el log del
     *            WAS.
     * @return
     */
    public ResponseMessageObjectType createBrokerRequest(
            RequestMessageObjectType requestMessageObjectType,
            Object anyObjectRequest, Object anyObjectResponse, String name,
            String nameSpace, String operation, String requestName,
            String responseName, EntityManager em, String appTypeValue,
            Object anyObjectRequestToLogger) {

        RequestHeaderOutMessageType requestESB;
        ResponseMessageType responseMessageType;
        ResponseMessageObjectType responseFront;
        ResponseHeaderOutMessageType responseESB;
        ResourcesPojo resourcesPojo;
        ResponseHeader responseHeader;
        ResponseBody responseBody;

        RequestMessageType requestMessage = requestMessageObjectType
                .getRequestMessage();
        RequestHeader requestHeader = requestMessage.getRequestHeader();
        String messageId = requestHeader.getMessageID();

        // ******************** REQUEST ESB ******************
        // Se configura del Header ESB
        requestESB = this.buildBrokerRequestMessage(requestMessageObjectType,
                name, nameSpace, operation, requestName, responseName,
                appTypeValue, anyObjectRequest);
        // *****************************************************

        // ************* Envío de Mensaje al IIB ****************

        try {

            this.resourceLocator = this.contextUtilHelper
                    .instanceResourceLocatorRemote();
            resourcesPojo = this.resourceLocator.getConnectionData(name,
                    operation);

            if (resourcesPojo == null) {
                logger.error(Constant.ERROR_MESSAGE_SINGLETON,
                        new MiddlewareException(
                                Constant.ERROR_MESSAGE_SINGLETON));
                return this.responseErrorMessage(
                        requestMessageObjectType.getRequestMessage()
                                .getRequestHeader(),
                        Constant.ERROR_CODE_SINGLETON,
                        Constant.ERROR_MESSAGE_SINGLETON,
                        Constant.COMMON_SYSTEM_MDW, em);
            }

            logger.info(Constant.COMMON_MESSAGE_OPERATION_BROKER + operation);
            logger.info(generateString(Constant.MESSAGEID, messageId,
                    Constant.CLOSE, Constant.COMMON_STRING_BLANK_SPACE,
                    Constant.COMMON_STRING_PAYLOAD,
                    this.parseJSONToString(null != anyObjectRequestToLogger
                            ? anyObjectRequestToLogger : anyObjectRequest)));

            responseESB = RestClientImpl.getInstance().executeOperation(
                    requestESB, resourcesPojo.getBrokerUrl(),
                    resourcesPojo.getBrokerConnectionTimeOut(),
                    resourcesPojo.getBrokerReadTimeOut());

            if (null == responseESB) {

                logger.error(ErrorEnum.BROKER_ERROR,
                        Constant.ERROR_MESSAGE_THERE_WAS_NO_RESPONSE_FROM_THE_IIB,
                        null);

                return this.responseErrorMessage(
                        requestMessageObjectType.getRequestMessage()
                                .getRequestHeader(),
                        Constant.ERROR_CODE_COMMUNICATION_IIB,
                        Constant.ERROR_MESSAGE_THERE_WAS_NO_RESPONSE_FROM_THE_IIB,
                        Constant.COMMON_SYSTEM_MDW, em);

            }

            logger.info(Constant.RESPONSE_BROKER
                    + this.parseJSONToString(responseESB));

            if (null == responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()) {
                responseESB.getResponseHeaderOut().getHeader()
                        .getResponseStatus().setStatusCode(DEFAULT_CODE);
            }

            if (!responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()
                    .equals(Constant.COMMON_STRING_ZERO)) {

                logger.error(ErrorEnum.BROKER_ERROR,
                        generateString(
                                responseESB.getResponseHeaderOut().getHeader()
                                        .getResponseStatus().getStatusCode(),
                                Constant.COMMON_STRING_BLANK_SPACE,
                                responseESB.getResponseHeaderOut().getHeader()
                                        .getResponseStatus().getStatusDesc()),
                        null);

            }

        } catch (RestClientException e) {
            logger.error(ErrorEnum.BROKER_ERROR,
                    Constant.ERROR_MESSAGE_IIB_COMMUNICATION, e);
            return this.responseErrorMessage(
                    requestMessageObjectType.getRequestMessage()
                            .getRequestHeader(),
                    Constant.ERROR_CODE_COMMUNICATION_IIB,
                    Constant.COMMON_STRING_EMPTY_STRING,
                    Constant.COMMON_SYSTEM_MDW, em);
        }

        // ********** Mapeo de Respuesta ****************
        // HEADER
        responseMessageType = new ResponseMessageType();
        responseHeader = this.configureResponseFrontHeader(
                requestMessageObjectType.getRequestMessage().getRequestHeader(),
                responseESB.getResponseHeaderOut().getHeader()
                        .getResponseStatus(),
                em);
        responseMessageType.setResponseHeader(responseHeader);

        // BODY
        responseBody = new ResponseBody();

        responseBody.setAny(anyObjectResponse != null ? anyObjectResponse
                : responseESB.getResponseHeaderOut().getBody().getAny());

        responseMessageType.setResponseBody(responseBody);

        // ***************************************************
        responseFront = new ResponseMessageObjectType();
        responseFront.setResponseMessage(responseMessageType);

        return responseFront;

    }

    /**
     * <p>
     * M&eacute;todo que dada una clase, se genera un listado de todos sus
     * campos, pero excluyendo aquellos del listado <code>fieldsToExclude</code>
     * .
     * </p>
     * <p>
     * <strong>NOTA:</strong> si no se especifica un listado de campos a
     * excluir, se extraen todos los campos de la clase sin excepci&oacute;n.
     * </p>
     * 
     * @param objectClass
     *            clase a la cual se le extraer&aacute;n los campos.
     * @param fieldsToExclude
     *            listado de campos a excluir en la extracci&oacute;n.
     * @return listado de campos extra&iacute;dos de la clase excluyendo los
     *         indicados en el listado <code>fieldsToExclude</code>.
     */
    public List<Field> generateFieldsToValidateFromClass(Class<?> objectClass,
            String... fieldsToExclude) {

        Field[] fields = objectClass.getDeclaredFields();
        List<Field> fieldsConverted = new ArrayList<>(Arrays.asList(fields));
        Iterator<Field> fieldIterator = fieldsConverted.iterator();
        /*
         * Se procesa el listado de campos para excluir aquellos que no se
         * desean validar.
         */
        while (fieldIterator.hasNext()) {
            Field field = fieldIterator.next();
            for (int i = 0; i < fieldsToExclude.length; i++) {
                if (field.getName()
                        .equalsIgnoreCase(fieldsToExclude[i].trim())) {
                    fieldIterator.remove();
                    break;
                }
            }
        }

        return fieldsConverted;
    }

    public Boolean validateFieldsByGetters(Class<?> objectClass,
            Object objectToValidate, List<Field> fieldsToValidate)
            throws MiddlewareException {
        Method[] methods = objectClass.getDeclaredMethods();

        for (Method method : methods) {
            try {
                if (isGetter(method)) {
                    for (Field fieldConverted : fieldsToValidate) {
                        if (method.getName().substring(3)
                                .equalsIgnoreCase(fieldConverted.getName())) {
                            Object value = method.invoke(objectToValidate);

                            if (null == value || (value.getClass()
                                    .equals(String.class)
                                    && value.toString().trim().isEmpty())) {
                                logger.debug(
                                        Constant.COMMON_STRING_EMPTY_FIELD_VALIDATION_ERROR
                                                + fieldConverted.getName());
                                return false;
                            }

                            break;
                        }
                    }
                }
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        Constant.ERROR_MESSAGE_EMPTY_FIELDS_VALIDATION, e);
                throw new MiddlewareException(e);
            }
        }
        return true;
    }

    /**
     * <p>
     * M&eacute;todo que genera el listado de campos que se permitir&aacute;n
     * vac&iacute;os en el objeto espeficicado dada una enumeraci&oacute;n con
     * los nombres de los campos deseados. .
     * </p>
     * 
     * @return array con los nombres de los campos que se permiten vac&iacute;os
     *         en el objeto.
     */
    public String[] generateAllowedEmptyFieldsList(Enum<?>[] fieldsAllowed) {
        String[] emptyFieldsAllowedToReturn = new String[fieldsAllowed.length];

        for (int i = 0; i < fieldsAllowed.length; i++) {
            emptyFieldsAllowedToReturn[i] = fieldsAllowed[i].name()
                    .toLowerCase();
        }

        return emptyFieldsAllowedToReturn;
    }

    /**
     * <p>
     * Utilidad que recibe una imagen como arreglo de bytes y la gira en espejo.
     * </p>
     * 
     * Copyright © 2017 Daon. All rights reserved.
     * 
     * @param imageDataToFlip
     *            arreglo de bytes que representa la imagen a voltear.
     * @return arreglo de bytes que representa la imagen en espejo.
     * @throws MiddlewareException
     */
    public byte[] mirrorImage(byte[] imageDataToFlip)
            throws MiddlewareException {
        byte[] output = null;

        try {
            InputStream inputStream = new ByteArrayInputStream(imageDataToFlip);
            BufferedImage image = ImageIO.read(inputStream);

            AffineTransform transform = AffineTransform.getScaleInstance(-1.0,
                    1.0);
            transform.translate(-image.getWidth(null), 0);
            AffineTransformOp transformOp = new AffineTransformOp(transform,
                    AffineTransformOp.TYPE_BICUBIC);
            BufferedImage newImage = new BufferedImage(image.getWidth(),
                    image.getHeight(), image.getType());
            newImage = transformOp.filter(image, newImage);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(newImage, "jpg", outputStream);
            outputStream.flush();
            output = outputStream.toByteArray();
            outputStream.close();

        } catch (IOException e) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    Constant.ERROR_MESSAGE_MIRRORING_IMAGE_PROCESS_ERROR, e);
            throw new MiddlewareException(e);
        }

        return output;
    }

    /**
     * Configura y setea el RespnseHeader de Front y Homologa los codigos de
     * respuesta.
     * 
     * @param requestHeader
     * @param responseStatus
     * @param em
     * @return
     */
    public ResponseHeader configureResponseFrontHeader(
            RequestHeader requestHeader, String statusCode, String system,
            String statusDesc, EntityManager em) {

        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setChannel(requestHeader.getChannel());
        responseHeader.setClientID(requestHeader.getClientID());
        responseHeader.setMessageID(requestHeader.getMessageID());
        responseHeader.setResponseDate(getCurrentTimeStamp());
        responseHeader.setDestination(requestHeader.getDestination());

        StatusType statusType = null;
        if (statusCode.equals(SUCCESS_CODE)) {
            statusType = new StatusType();
            statusType.setStatusCode(SUCCESS_CODE);
            statusType.setStatusDesc(SUCCESS_DESC);
        } else if (null != requestHeader.getBlockingNequiAccessMode()
                && Constant.COMMON_STRING_TRUE.equalsIgnoreCase(
                        requestHeader.getBlockingNequiAccessMode().trim())) {
            statusType = new StatusType();
            statusType.setStatusCode(Constant.MAINTENANCE_CODE);
            statusType.setStatusDesc(null != requestHeader
                    .getBlockingNequiAccessModeMessage()
                    && !requestHeader.getBlockingNequiAccessModeMessage().trim()
                            .isEmpty()
                                    ? requestHeader
                                            .getBlockingNequiAccessModeMessage()
                                            .trim()
                                    : Constant.COMMON_MESSAGE_BLOCKING_NEQUI_ACCESS_MODE);

        } else if (null != requestHeader.getMaintenanceMode()
                && Constant.COMMON_STRING_TRUE.equalsIgnoreCase(
                        requestHeader.getMaintenanceMode().trim())) {
            statusType = new StatusType();
            statusType.setStatusCode(Constant.MAINTENANCE_CODE);
            statusType.setStatusDesc(null != requestHeader
                    .getMaintenanceModeMessage()
                    && !requestHeader.getMaintenanceModeMessage().trim()
                            .isEmpty()
                                    ? requestHeader.getMaintenanceModeMessage()
                                            .trim()
                                    : Constant.COMMON_MESSAGE_MAINTENANCE_MODE);

        } else {
            statusType = getErrorHomologation(statusCode, system, statusDesc,
                    requestHeader, null, em);
        }
        responseHeader.setStatus(statusType);

        return responseHeader;
    }

    /**
     * 
     * @param statusCode
     * @param system
     * @param statusDesc
     * @param requestHeader
     * @param destinationSystem
     *            sistema destino para mostrar el error. (Opcional)
     * @param em
     * @return
     */
    private StatusType getErrorHomologation(String statusCode, String system,
            String statusDesc, RequestHeader requestHeader,
            String destinationSystem, EntityManager em) {

        if (null == destinationSystem) {
            destinationSystem = APP;
        }

        homologador = new HomologadorServiceImpl();
        StatusType status = new StatusType();
        try {
            HomologaError homologaError = homologador.getErrorHomologation(
                    statusCode, system, destinationSystem, em);
            status.setStatusCode(
                    homologaError.getCatalogoError2().getId().getCodigo());
            status.setStatusDesc(
                    homologaError.getCatalogoError2().getDescripcion());
            return status;

        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR, generateString(
                    Constant.ERROR_MESSAGE_HOMOLOGATION_ERROR,
                    Constant.COMMON_STRING_BLANK_SPACE, DEFAULT_MSG,
                    Constant.COMMON_STRING_BLANK_SPACE,
                    Constant.COMMON_STRING_BLANK_SPACE, ERROR_HOMOLOGATING_CODE,
                    Constant.COMMON_STRING_BLANK_SPACE, Constant.GUION_MEDIO,
                    Constant.COMMON_STRING_BLANK_SPACE,
                    Constant.COMMON_STRING_CODIGO,
                    Constant.COMMON_STRING_BLANK_SPACE, statusCode,
                    Constant.COMMON_STRING_BLANK_SPACE, Constant.GUION_MEDIO,
                    Constant.COMMON_STRING_BLANK_SPACE,
                    Constant.COMMON_STRING_SISTEMA,
                    Constant.COMMON_STRING_BLANK_SPACE, system,
                    Constant.COMMON_STRING_BLANK_SPACE, Constant.GUION_MEDIO,
                    Constant.COMMON_STRING_BLANK_SPACE,
                    Constant.COMMON_STRING_DESCRIPTION,
                    Constant.COMMON_STRING_BLANK_SPACE, statusDesc), e);
            status.setStatusCode(DEFAULT_CODE);
            status.setStatusDesc(DEFAULT_MSG);
            return status;
        }

    }

    public String objectToString(Object object) throws JPAException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = Constant.COMMON_STRING_EMPTY_STRING;
        try {
            jsonInString = mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new JPAException(generateString(Constant.ERROR_PARSING), e);
        }
        return jsonInString;
    }

    @SuppressWarnings("unchecked")
    public <T> T parsePayload(String any, Class<?> clazz) throws JPAException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = objectToArray(any,
                    Constant.COMMON_ARRAY_GENERAL_INFO);

            return (T) mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new JPAException(
                    generateString(Constant.ERROR_PARSING, clazz.getName()), e);
        }

    }

    public String objectToArray(String json, String[] array) {
        String before = null;
        String after = null;
        String half = null;
        StringBuilder strbuild;
        StringBuilder stringBuilder;
        int position;
        int positionAuxiliar;
        for (int i = Constant.COMMON_INT_ZERO; i < array.length; i++) {
            position = json.indexOf(array[i]);
            if (position >= Constant.COMMON_INT_ZERO) {
                positionAuxiliar = position + array[i].length()
                        + Constant.COMMON_INT_TWO;

                if (json.charAt(
                        positionAuxiliar) != Constant.COMMON_CHAR_LEFT_SQUARE) {
                    before = json.substring(Constant.COMMON_INT_ZERO,
                            positionAuxiliar);
                    position = json.indexOf(Constant.COMMON_STRING_RIGHT_KEY,
                            position);
                    half = Constant.COMMON_STRING_LEFT_KEY + json.substring(
                            positionAuxiliar + Constant.COMMON_INT_ONE,
                            position);
                    strbuild = new StringBuilder();
                    strbuild.append(Constant.COMMON_STRING_RIGHT_KEY);
                    strbuild.append(Constant.COMMON_STRING_RIGHT_SQUARE);
                    strbuild.append(
                            json.substring(position + Constant.COMMON_INT_ONE));
                    after = strbuild.toString();

                    stringBuilder = new StringBuilder();
                    stringBuilder.append(before);
                    stringBuilder.append(Constant.COMMON_STRING_LEFT_SQUARE);
                    stringBuilder.append(half);
                    stringBuilder.append(after);
                    json = stringBuilder.toString();
                }

            }
        }
        return json;
    }

    /**
     * Método que construye un String a partir de la concatenación de sus
     * parámetros
     * 
     * @param args
     * @return
     */
    public String generateString(String... args) {

        StringBuilder bf = new StringBuilder();

        if (null != args) {
            for (String arg : args) {
                bf.append(arg);
            }
        }
        return bf.toString();
    }

    /**
     * 
     * @param any
     * @param clazz
     * @return
     * @throws JPAException
     */
    @SuppressWarnings("unchecked")
    public <T> T parseStringToObject(String any, Class<?> clazz)
            throws JPAException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            return (T) mapper.readValue(any, clazz);
        } catch (IOException e) {
            throw new JPAException(
                    generateString(Constant.ERROR_PARSING, clazz.getName()), e);
        }

    }

    /**
     * Valida si se puede parsear el payload del servicio
     * 
     * @param any
     * @param clase
     * @param operacion
     * @return
     */
    public boolean validateRequestMessage(String any, Object clase,
            String operacion) {

        logger.debug(Constant.COMMON_STRING_PAYLOAD + any.toString());

        try {
            return null != parsePayloadMessage(any, clase);
        } catch (MiddlewareException e) {
            logger.error(ERROR_PARSING + operacion, e);
        }
        return false;
    }

    /**
     * Metodo que construye un mensaje de error cuando el cliente no existe en
     * la tabla CLIENTE de la BD.
     * 
     * @param customLogger
     * @param request
     * @param em
     * @return
     */
    public ResponseMessageObjectType responseErrorMessageClientNotExist(
            CustomLogger customLogger, RequestMessageObjectType request,
            EntityManager em) {

        StringBuilder messageError = new StringBuilder(
                Constant.ERROR_MESSAGE_DB_QUERY);

        messageError.append(Constant.ERROR_MESSAGE_SECURITY_CLIENT_NO_EXIST);
        MiddlewareException middlewareException = new MiddlewareException(
                messageError.toString());
        customLogger.error(messageError.toString(), middlewareException);

        return responseErrorMessage(
                request.getRequestMessage().getRequestHeader(),
                Constant.ERROR_CODE_SYSTEM_BD_NOT_CLIENT,
                messageError.toString(), Constant.COMMON_SYSTEM_BD, em);

    }
    
    /**
     * Metodo que permite reemplazar en el <code>jsonAsString</code> los
     * atributos dados en <code>keys</code> por una mascara por defecto
     * (&quot;****&quot;).
     * 
     * @param jsonAsString
     * @param keys
     * @return json result as String.
     * @throws MiddlewareException
     */
    public static String maskProperties(String jsonAsString, List<String> keys)
            throws MiddlewareException {
        JSONObject json = new JSONObject(jsonAsString);
        replaceJsonValuesByAttributes(json, keys,
                Constant.COMMON_STRING_ASTERISK);
        return json.toString();
    }

    /**
     * Metodo que permite reemplazar en el <code>jsonAsString</code> los
     * atributos dados en <code>keys</code> por el valor dado (<code>mask</code>
     * ).
     * 
     * @param jsonAsString
     * @param keys
     * @param mask
     * @return json result as String.
     * @throws MiddlewareException
     */
    public static String maskProperties(String jsonAsString, List<String> keys,
            String mask) throws MiddlewareException {
        JSONObject json = new JSONObject(jsonAsString);
        replaceJsonValuesByAttributes(json, keys, mask);
        return json.toString();
    }

    /**
     * Metodo que permite reemplazar en el <code>jsonObject</code> los atributos
     * dados en <code>keys</code> por el valor dado (<code>mask</code>).
     * 
     * @param jsonObject
     * @param keys
     * @param mask
     * @return json result as String.
     * @throws MiddlewareException
     */
    public static String maskProperties(JSONObject jsonObject,
            List<String> keys, String mask) throws MiddlewareException {
        JSONObject json = new JSONObject(jsonObject.toString());
        replaceJsonValuesByAttributes(json, keys, mask);
        return json.toString();
    }

    /**
     * Metodo para reemplazar en el json los valores de los atributos con el
     * valor deseado (<code>mask</code>).
     * 
     * @param jsonObject
     * @param keys
     * @param mask
     * @throws MiddlewareException
     */
    private static void replaceJsonValuesByAttributes(JSONObject jsonObject,
            List<String> keys, String mask) throws MiddlewareException {

        Iterator<String> keysItr;
        String key;
        Object value;

        try {

            keysItr = jsonObject.keys();

            while (keysItr.hasNext()) {

                key = keysItr.next();
                value = jsonObject.get(key);

                if (value instanceof JSONArray) {

                    JSONArray valueAsArray = (JSONArray) value;

                    for (int i = 0; i < valueAsArray.length(); i++) {

                        value = valueAsArray.get(i);

                        if ((value instanceof JSONArray
                                || value instanceof JSONObject)) {

                            replaceJsonValuesByAttributes((JSONObject) value,
                                    keys, mask);

                        }

                    }

                } else if (value instanceof JSONObject) {

                    replaceJsonValuesByAttributes((JSONObject) value, keys,
                            mask);
                } else {
                    replaceValue(jsonObject, keys, mask, key);
                }

            }

        } catch (MiddlewareException ex) {
            throw ex;
        } catch (Exception ex) {
            StringBuilder errorMessage = new StringBuilder(
                    Constant.ERROR_PARSING);
            errorMessage.append(ServiceControllerHelper.class.getName());
            throw new MiddlewareException(errorMessage.toString(), ex);
        }
    }

    /**
     * Metodo para validar si la llave corresponde al listado y reemplazar su
     * valor por el atributo <code>mask</code>.
     * 
     * @param jsonObject
     *            objeto al cual se le reemplazara su valor.
     * @param keys
     *            listado de llaves de muestra.
     * @param mask
     *            nuevo valor a asignar.
     * @param key
     *            llave actual en el objeto {@link JSONObject}.
     */
    private static void replaceValue(JSONObject jsonObject, List<String> keys,
            String mask, String key) {
        for (String keyToSearch : keys) {
            if (keyToSearch.trim().equalsIgnoreCase(key.trim())) {
                jsonObject.put(key, mask);
            }
        }
    }
}
