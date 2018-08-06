package co.bdigital.admin.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import co.bdigital.admin.messaging.services.amazonS3.IntegrationRQ;
import co.bdigital.admin.messaging.services.bco.acs502.BackoutRequestType;
import co.bdigital.admin.messaging.services.bco.acs502.BcoBodyType;
import co.bdigital.admin.messaging.services.bco.acs502.BcoHeaderType;
import co.bdigital.admin.messaging.services.bco.acs502.ConsumerType;
import co.bdigital.admin.messaging.services.bco.acs502.ContainerType;
import co.bdigital.admin.messaging.services.gmf.MessageRQ;
import co.bdigital.admin.messaging.services.gmf.OperationGMFRQType;
import co.bdigital.admin.messaging.services.gmf.RequestMessageType;
import co.bdigital.admin.messaging.services.isb.slack.SendSlackNotificationRQType;
import co.bdigital.admin.messaging.services.isb.slack.SendSlackNotificationServiceRequestType;
import co.bdigital.admin.model.StatusResponse;
import co.bdigital.cmm.ejb.exception.MiddlewareException;
import co.bdigital.cmm.ejb.util.CommonUtil;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.model.Parametro;
import co.bdigital.cmm.jpa.model.PromocionOperacion;
import co.bdigital.cmm.jpa.model.PromocionRegla;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;
import co.nequi.message.integration.services.RequestHeaderType;
import co.nequi.message.integration.services.getpromotiondetail.GetPromotionDetailRequestType;
import co.nequi.message.integration.services.updatepromotionrule.UpdatePromotionRuleRequestType;
import co.nequi.message.registry.serviceregistry.lookup.LookupResponseType;

public class WebConsoleUtil {

    private WebConsoleUtil() {
    }

    /**
     * <p>
     * Metodo que genera el JSON del request a Middleware.
     * </p>
     * 
     * @param request
     * @return
     * @throws MiddlewareException
     */
    public static String generateRequestMessage(
            RequestMessageObjectType request) throws MiddlewareException {
        JSONObject jsonObj = new JSONObject(request);
        Map<String, Object> requestAsMap = new HashMap<>();

        Object anyObject = request.getRequestMessage().getRequestBody()
                .getAny();

        request.getRequestMessage().getRequestBody()
                .setAny(ConstantADM.STRING_EMPTY);

        if (jsonObj != JSONObject.NULL) {

            requestAsMap = WebConsoleUtil.toMapJson(jsonObj);
        }

        JSONObject objectResult = new JSONObject(requestAsMap);

        if (null != anyObject) {
            JSONObject jsonRequestMessage = (JSONObject) objectResult
                    .get(ConstantADM.COMMON_STRING_REQUEST_MESSAGE);
            JSONObject jsonRequestBody = (JSONObject) jsonRequestMessage
                    .get(ConstantADM.COMMON_STRING_REQUEST_BODY);
            jsonRequestBody.remove(ConstantADM.COMMON_STRING_CAPITALIZED_ANY);
            jsonRequestBody.put(ConstantADM.COMMON_STRING_ANY,
                    new JSONObject(anyObject));
        }

        return objectResult.toString();
    }

    /**
     * Metodo que genera el JSON del request al proyecto de integracion
     * 
     * @param request
     * @return
     * @throws MiddlewareException
     */
    public static String generateRequestMessageIntegration(
            IntegrationRQ request) {
        JSONObject jsonObj = new JSONObject(request);
        return jsonObj.toString();
    }

    /**
     * <p>
     * Metodo que recorre el objeto y le coloca la primera letra en
     * may&uacute;scula.
     * </p>
     * 
     * @param jSONObject
     * @return Map
     * @throws MiddlewareException
     */
    private static Map<String, Object> toMapJson(JSONObject jSONObject)
            throws MiddlewareException {

        Map<String, Object> map;
        Iterator<String> keysItr;
        String key;
        Object value;

        try {

            map = new HashMap<>();

            keysItr = jSONObject.keys();

            while (keysItr.hasNext()) {

                key = keysItr.next();
                value = jSONObject.get(key);

                if (value instanceof JSONArray) {

                    value = WebConsoleUtil.toListObjectJson((JSONArray) value);

                } else if (value instanceof JSONObject) {

                    value = WebConsoleUtil.toMapJson((JSONObject) value);
                }

                String formattedKey = key.substring(0, 1).toUpperCase()
                        + key.substring(1);
                map.put(formattedKey, value);
            }

            return map;

        } catch (MiddlewareException ex) {

            throw ex;

        } catch (Exception ex) {

            throw new MiddlewareException(
                    ConstantADM.ERROR_MESSAGE_GENERIC_ERROR, ex);
        }
    }

    /**
     * Parsea un objeto <code>JSONArray</code> a <code>List<Object></code>.
     * 
     * @param jSONArray
     * @return <code>List<Object></code>
     * @throws MiddlewareException
     */
    private static List<Object> toListObjectJson(JSONArray jSONArray)
            throws MiddlewareException {

        List<Object> list;
        Object value;

        try {

            list = new ArrayList<>();

            for (int i = 0; i < jSONArray.length(); i++) {

                value = jSONArray.get(i);

                if (value instanceof JSONArray) {

                    value = WebConsoleUtil.toListObjectJson((JSONArray) value);

                } else if (value instanceof JSONObject) {

                    value = WebConsoleUtil.toMapJson((JSONObject) value);
                }

                list.add(value);
            }

            return list;

        } catch (MiddlewareException ex) {

            throw ex;

        } catch (Exception ex) {

            throw new MiddlewareException(
                    ConstantADM.ERROR_MESSAGE_GENERIC_ERROR, ex);
        }
    }

    /**
     * Metodo para devolver un statusCode, y statusDesc
     * 
     * @param json
     * @return
     * @throws MiddlewareException
     */
    public static StatusResponse getResponseRest(JSONObject json) {
        StatusResponse status = new StatusResponse();
        if (json != JSONObject.NULL) {
            JSONObject jsonRsponseMessage = (JSONObject) json
                    .get(ConstantADM.COMMON_STRING_RESPONSE_MESSAGE);
            JSONObject jsonResponseHeader = (JSONObject) jsonRsponseMessage
                    .get(ConstantADM.COMMON_STRING_RESPONSE_HEADER);
            JSONObject jsonResponseStatus = (JSONObject) jsonResponseHeader
                    .get(ConstantADM.COMMON_STRING_RESPONSE_STATUS);
            status.setStatusCode((String) jsonResponseStatus
                    .get(ConstantADM.COMMON_STRING_RESPONSE_STATUS_COD));
            status.setStatusDesc((String) jsonResponseStatus
                    .get(ConstantADM.COMMON_STRING_RESPONSE_STATUS_DES));
        }
        return status;
    }

    /**
     * Metodo para devolver un statusCode, y statusDesc en los servicios de
     * integracion
     * 
     * @param json
     * @return
     */
    public static StatusResponse getResponseRestIntegration(JSONObject json) {
        StatusResponse status = new StatusResponse();
        if (json != JSONObject.NULL) {
            JSONObject jsonRsponseMessage = (JSONObject) json.get(
                    ConstantADM.COMMON_STRING_RESPONSE_INTEGRATION_MESSAGE);
            JSONObject jsonResponseHeader = (JSONObject) jsonRsponseMessage
                    .get(ConstantADM.COMMON_STRING_RESPONSE_INTEGRATION_HEADER);
            JSONObject jsonResponseStatus = (JSONObject) jsonResponseHeader
                    .get(ConstantADM.COMMON_STRING_RESPONSE_INTEGRATION_STATUS);
            status.setStatusCode((String) jsonResponseStatus.get(
                    ConstantADM.COMMON_STRING_RESPONSE_INTEGRATION_STATUS_COD));
            status.setStatusDesc((String) jsonResponseStatus.get(
                    ConstantADM.COMMON_STRING_RESPONSE_INTEGRATION_STATUS_DES));
        }
        return status;
    }

    /**
     * Metodo para devolver la estructura de respuesta cuando se invoca un ejb
     * local
     * 
     * @param response
     * @return
     */
    public static StatusResponse getResponseLocal(
            ResponseMessageObjectType response) {
        StatusResponse status = new StatusResponse();
        status.setStatusCode(response.getResponseMessage().getResponseHeader()
                .getStatus().getStatusCode());
        status.setStatusDesc(response.getResponseMessage().getResponseHeader()
                .getStatus().getStatusDesc());
        return status;

    }

    /**
     * método para parsear payload Any al Objeto del segundo parametro
     * 
     * @param any
     * @param clase
     * @return
     * @throws MiddlewareException
     */
    public static Object parsePayload(JSONObject any, Object clase)
            throws MiddlewareException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(any.toString(), clase.getClass());
        } catch (IOException e) {

            StringBuilder errorMessage = new StringBuilder(
                    ConstantADM.ERROR_PARSING);
            errorMessage.append(clase.getClass().getName());
            throw new MiddlewareException(errorMessage.toString(), e);
        }

    }

    /**
     * Metodo que convierte un objeto <code>any</code> en un objeto
     * <code>JSONObject</code>.
     * 
     * @param any
     * @return <code>JSONObject</code>
     */
    public static JSONObject getJSONObject(Object any) {

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
     * Metodo para devolver el contenido del response body.
     * 
     * @param json
     * @return
     * @throws MiddlewareException
     */
    public static JSONObject getResponseAny(JSONObject json) {
        JSONObject any = null;
        if (json != JSONObject.NULL) {
            JSONObject jsonResponseMessage = (JSONObject) json
                    .get(ConstantADM.COMMON_STRING_RESPONSE_MESSAGE);
            JSONObject jsonResponseHeader = (JSONObject) jsonResponseMessage
                    .get(ConstantADM.COMMON_STRING_RESPONSE_BODY);
            any = (JSONObject) jsonResponseHeader
                    .get(ConstantADM.COMMON_STRING_RESPONSE_ANY);
        }
        return any;
    }

    /**
     * Metodo para devolver el contenido del response body del proyecto de
     * integracion.
     * 
     * @param json
     * @return
     * @throws MiddlewareException
     */
    public static JSONObject getResponseAnyIntegration(JSONObject json) {
        JSONObject jsonResponseHeader = null;
        if (json != JSONObject.NULL) {
            JSONObject jsonResponseMessage = (JSONObject) json.get(
                    ConstantADM.COMMON_STRING_RESPONSE_INTEGRATION_MESSAGE);
            jsonResponseHeader = (JSONObject) jsonResponseMessage
                    .get(ConstantADM.COMMON_STRING_RESPONSE_INTEGRATION_BODY);
        }
        return jsonResponseHeader;
    }

    /**
     * <p>
     * Metodo que busca un parametro por nombre y codigo del pais.
     * </p>
     * 
     * @param parameters
     * @param parameterName
     * @param countryCode
     * @return {@link Parametro} parametro que corresponde al nombre y region
     *         dados.
     */
    public static Parametro getParameterByNameAndCountryCode(
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
     * Metodo que genera el JSON del objeto especificado.
     * 
     * @param obj
     * @return {@link String} cadena con el JSON del objeto <code>obj</code>.
     */
    public static String generateObjectToString(Object obj) {
        JSONObject jsonObj = new JSONObject(obj);
        return jsonObj.toString();
    }

    /**
     * <p>
     * Metodo que construye el request para Backout.
     * </p>
     * 
     * @param messageId
     * @param name
     * @param region
     * @param consumerTypeId
     * @param consumerTypeName
     * @param containerTypeId
     * @param containerTypeName
     * @return {@link BackoutRequestType}
     */
    public static BackoutRequestType buildBackoutRequestType(String messageId,
            String name, String region, String consumerTypeId,
            String consumerTypeName, String containerTypeId,
            String containerTypeName) {

        BackoutRequestType backoutRequestType;
        BcoHeaderType requestHeaderOutType;
        BcoBodyType bcoBodyType;
        ConsumerType consumerType;
        ContainerType containerType;

        requestHeaderOutType = new BcoHeaderType();

        consumerType = new ConsumerType();
        consumerType.setId(consumerTypeId);
        consumerType.setName(consumerTypeName);

        requestHeaderOutType.setConsumer(consumerType);

        requestHeaderOutType.setConsumerDate(getCurrentTimeStamp());

        containerType = new ContainerType();
        containerType.setId(containerTypeId);
        containerType.setName(containerTypeName);

        requestHeaderOutType.setContainer(containerType);

        requestHeaderOutType.setMessageID(messageId);
        requestHeaderOutType.setName(name);
        requestHeaderOutType.setRegion(region);

        // Se configura el Request Completo
        backoutRequestType = new BackoutRequestType();
        backoutRequestType.setHeader(requestHeaderOutType);

        // Se Configura el Body
        bcoBodyType = new BcoBodyType();
        backoutRequestType.setBody(bcoBodyType);

        return backoutRequestType;

    }

    /**
     * 
     * @return Timestamp Actual
     */
    public static String getCurrentTimeStamp() {
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat(
                ConstantADM.COMMON_FORMAT_DATE_TO_FRONT);
        return format.format(curDate);
    }

    /**
     * Metodo para crear objeto Request de integracion
     * <code>GetPromotionDetailRequestType</code>.
     * 
     * @param service
     * @param operation
     * @param type
     * @param region
     * @return <code>GetPromotionDetailRequestType</code>
     */
    public static GetPromotionDetailRequestType getGetPromotionDetailRequestType(
            String service, String operation, String type, String region,
            String idPromotion) {

        GetPromotionDetailRequestType getPromotionDetailRequestType;

        getPromotionDetailRequestType = new GetPromotionDetailRequestType();

        getPromotionDetailRequestType.setOperation(operation);
        getPromotionDetailRequestType.setRegion(region);
        getPromotionDetailRequestType.setService(service);
        getPromotionDetailRequestType.setType(type);
        getPromotionDetailRequestType.setIdPromotion(idPromotion);

        return getPromotionDetailRequestType;
    }

    /**
     * 
     * Metodo para crear objeto Request de integracion
     * <code>UpdatePromotionRuleRequestType</code>.
     * 
     * @param id
     * @param description
     * @param fromDate
     * @param toDate
     * @param minValue
     * @param maxValue
     * @param value
     * @param valueType
     * @param accountingAccount
     * @param availableValue
     * @param ocurrence
     * @param status
     * @param notificationType
     * @param subject
     * @param message
     * @return
     */

    public static UpdatePromotionRuleRequestType getUpdatePromotionRuleRequestType(
            String id, String description, String fromDate, String toDate,
            String minValue, String maxValue, String value, String valueType,
            String accountingAccount, String availableValue, String ocurrence,
            String status, String notificationType, String subject,
            String message, String maximumBudget, String frequencyMinimum,
            String frequencyRestart) {

        UpdatePromotionRuleRequestType updatePromotionRuleRequestType;

        updatePromotionRuleRequestType = new UpdatePromotionRuleRequestType();

        updatePromotionRuleRequestType.setAccountingAccount(accountingAccount);
        updatePromotionRuleRequestType.setAvailableValue(availableValue);
        updatePromotionRuleRequestType.setDescription(description);
        updatePromotionRuleRequestType.setFromDate(fromDate);
        updatePromotionRuleRequestType.setId(id);
        updatePromotionRuleRequestType.setToDate(toDate);
        updatePromotionRuleRequestType.setMaxValue(availableValue);
        updatePromotionRuleRequestType.setMessage(message);
        updatePromotionRuleRequestType.setMinValue(minValue);
        updatePromotionRuleRequestType.setMaxValue(maxValue);
        updatePromotionRuleRequestType.setNotificationType(notificationType);
        updatePromotionRuleRequestType.setStatus(status);
        updatePromotionRuleRequestType.setOcurrence(ocurrence);
        updatePromotionRuleRequestType.setSubject(subject);
        updatePromotionRuleRequestType.setValueType(valueType);
        updatePromotionRuleRequestType.setValue(value);
        updatePromotionRuleRequestType.setMaximumBudget(maximumBudget);
        if (ConstantADM.STRING_EMPTY.equals(frequencyMinimum)) {
            updatePromotionRuleRequestType.setFrequencyMinimum(null);
        } else {
            updatePromotionRuleRequestType
                    .setFrequencyMinimum(frequencyMinimum);
        }
        if (ConstantADM.STRING_EMPTY.equals(frequencyRestart)) {
            updatePromotionRuleRequestType.setFrequencyRestart(null);
        } else {
            updatePromotionRuleRequestType
                    .setFrequencyRestart(frequencyRestart);
        }

        return updatePromotionRuleRequestType;
    }

    /**
     * Metodo que retorna el request que se envia al componente de integracion
     * 
     * @param service
     * @param operation
     * @param type
     * @param region
     * @return IntegrationRQ
     */
    public static co.nequi.message.integration.services.IntegrationRQ requestMessage(
            String service, String operation, String version, String region) {
        return buildRequestMessage(service, operation, version, region);
    }

    /**
     * Metodo que construye el request que se envia al componente de integracion
     * 
     * @param service
     * @param operation
     * @param type
     * @param region
     * @return IntegrationRQ
     */
    private static co.nequi.message.integration.services.IntegrationRQ buildRequestMessage(
            String service, String operation, String version, String region) {

        co.nequi.message.integration.services.IntegrationRQ integrationRQ;
        co.nequi.message.integration.services.RequestBodyType requestBodyType;
        co.nequi.message.integration.services.RequestHeaderType requestHeaderType;

        co.nequi.message.integration.services.IntegrationRequestType integrationRequestType;
        co.nequi.message.integration.services.ContainerType containerType;
        co.nequi.message.integration.services.ConsumerType consumerType;

        integrationRQ = new co.nequi.message.integration.services.IntegrationRQ();
        integrationRequestType = new co.nequi.message.integration.services.IntegrationRequestType();

        requestHeaderType = new co.nequi.message.integration.services.RequestHeaderType();

        requestHeaderType.setChannel(ConstantADM.COMMON_STRING_CHANNEL);

        consumerType = new co.nequi.message.integration.services.ConsumerType();

        consumerType.setId(ConstantADM.COMMON_STRING_CONSUMER_ID_SHP);
        consumerType
                .setDescription(ConstantADM.COMMON_STRING_CONSUMER_NAME_ADM);

        requestHeaderType.setConsumer(consumerType);

        containerType = new co.nequi.message.integration.services.ContainerType();
        containerType.setId(ConstantADM.COMMON_STRING_CONTAINER_ID_WAS);
        containerType.setName(ConstantADM.COMMON_STRING_CONTAINER_NAME_WAS);
        containerType.setType(ConstantADM.COMMON_STRING_CHANNEL);

        co.nequi.message.integration.services.DestinationType destinationType = new co.nequi.message.integration.services.DestinationType();
        destinationType.setContainer(containerType);
        destinationType.setRegion(region);

        requestHeaderType.setDestination(destinationType);

        co.nequi.message.integration.services.ServiceType serviceType = new co.nequi.message.integration.services.ServiceType();
        serviceType.setName(service);
        serviceType.setOperation(operation);
        serviceType.setVersion(version);

        destinationType.setService(serviceType);

        Date currentDate = new Date();
        Timestamp time = new Timestamp(currentDate.getTime());
        requestHeaderType.setRequestDateTime(time.toString());
        requestHeaderType.setMessageId(time.toString());
        integrationRequestType.setHeader(requestHeaderType);

        requestBodyType = new co.nequi.message.integration.services.RequestBodyType();

        integrationRequestType.setBody(requestBodyType);

        integrationRQ.setIntegrationRequest(integrationRequestType);

        return integrationRQ;
    }

    /**
     * Metodo para obtener endpoint
     * 
     * @param host
     * @param port
     * @param context
     * @return
     */
    public static String getWSEndpoint(String host, Integer port,
            String context) {
        StringBuilder sb = new StringBuilder();
        sb.append(host);
        if (null != port) {
            sb.append(ConstantADM.COMMON_STRING_DOT);
            sb.append(port);
        }
        sb.append(context);
        return sb.toString();
    }

    /**
     * Método que construye un <b>RegistryRQ</b> a partir de un
     * <b>RequestHeaderType</b>
     * 
     * @param header
     * @param name
     * @param operation
     * @param region
     * @param version
     * @return
     */
    public static co.nequi.message.registry.serviceregistry.RegistryRQ getRegistryRQ(
            RequestHeaderType header, String name, String operation,
            String region, String version) {

        co.nequi.message.registry.serviceregistry.RegistryRQ registryRQ = new co.nequi.message.registry.serviceregistry.RegistryRQ();
        co.nequi.message.registry.serviceregistry.RegistryRequestType registryRequestType = new co.nequi.message.registry.serviceregistry.RegistryRequestType();
        co.nequi.message.registry.serviceregistry.HeaderRequestType headerRequestType = new co.nequi.message.registry.serviceregistry.HeaderRequestType();
        co.nequi.message.registry.serviceregistry.ConsumerType consumerType = new co.nequi.message.registry.serviceregistry.ConsumerType();
        co.nequi.message.registry.serviceregistry.ContainerType containerType = new co.nequi.message.registry.serviceregistry.ContainerType();
        co.nequi.message.registry.serviceregistry.BodyRequestType bodyRequestType = new co.nequi.message.registry.serviceregistry.BodyRequestType();
        co.nequi.message.registry.serviceregistry.lookup.LookupRequestType lookupRequestType = new co.nequi.message.registry.serviceregistry.lookup.LookupRequestType();

        registryRQ.setRegistryRequest(registryRequestType);
        registryRequestType.setHeader(headerRequestType);

        headerRequestType.setMessageId(header.getMessageId());
        headerRequestType.setMessageDate(header.getRequestDateTime());

        headerRequestType.setType(Constant.COMMON_STRING_EMPTY_STRING);
        headerRequestType.setOperation(Constant.COMMON_STRING_EMPTY_STRING);

        headerRequestType.setConsumer(consumerType);

        /* Atibutos del consumer */
        consumerType.setId(ConstantADM.COMMON_STRING_CONSUMER_ID);
        consumerType.setName(ConstantADM.COMMON_STRING_CONSUMER_NAME);

        consumerType.setContainer(containerType);
        containerType.setId(ConstantADM.COMMON_STRING_CONTAINER_ID_AS_001);
        containerType.setName(ConstantADM.COMMON_STRING_CONTAINER_NAME);

        /* Atributos del body */
        registryRequestType.setBody(bodyRequestType);

        bodyRequestType.setLookupRequest(lookupRequestType);
        lookupRequestType.setName(name);
        lookupRequestType.setOperation(operation);
        lookupRequestType.setRegion(region);
        lookupRequestType.setVersion(version);

        lookupRequestType
                .setClassification(ConstantADM.INTEGRATION_CLASSIFICATION);
        return registryRQ;
    }

    /**
     * Método que construye un <b>RegistryRQ</b> a partir de un
     * <b>RequestHeaderType</b>
     * 
     * @param header
     * @param name
     * @param operation
     * @param region
     * @param version
     * @return
     */
    public static co.nequi.message.registry.serviceregistry.RegistryRQ getRegistryRQ(
            co.bdigital.admin.messaging.services.gmf.RequestHeaderType header,
            String name, String operation, String region, String version) {

        co.nequi.message.registry.serviceregistry.RegistryRQ registryRQ = new co.nequi.message.registry.serviceregistry.RegistryRQ();
        co.nequi.message.registry.serviceregistry.RegistryRequestType registryRequestType = new co.nequi.message.registry.serviceregistry.RegistryRequestType();
        co.nequi.message.registry.serviceregistry.HeaderRequestType headerRequestType = new co.nequi.message.registry.serviceregistry.HeaderRequestType();
        co.nequi.message.registry.serviceregistry.ConsumerType consumerType = new co.nequi.message.registry.serviceregistry.ConsumerType();
        co.nequi.message.registry.serviceregistry.ContainerType containerType = new co.nequi.message.registry.serviceregistry.ContainerType();
        co.nequi.message.registry.serviceregistry.BodyRequestType bodyRequestType = new co.nequi.message.registry.serviceregistry.BodyRequestType();
        co.nequi.message.registry.serviceregistry.lookup.LookupRequestType lookupRequestType = new co.nequi.message.registry.serviceregistry.lookup.LookupRequestType();

        registryRQ.setRegistryRequest(registryRequestType);
        registryRequestType.setHeader(headerRequestType);

        headerRequestType.setMessageId(header.getMessageID());
        headerRequestType.setMessageDate(header.getRequestDate());

        headerRequestType.setType(Constant.COMMON_STRING_EMPTY_STRING);
        headerRequestType.setOperation(Constant.COMMON_STRING_EMPTY_STRING);

        headerRequestType.setConsumer(consumerType);

        /* Atibutos del consumer */
        consumerType.setId(ConstantADM.COMMON_STRING_CONSUMER_ID);
        consumerType.setName(ConstantADM.COMMON_STRING_CONSUMER_NAME);

        consumerType.setContainer(containerType);
        containerType.setId(ConstantADM.COMMON_STRING_CONTAINER_ID_AS_001);
        containerType.setName(ConstantADM.COMMON_STRING_CONTAINER_NAME);

        /* Atributos del body */
        registryRequestType.setBody(bodyRequestType);

        bodyRequestType.setLookupRequest(lookupRequestType);
        lookupRequestType.setName(name);
        lookupRequestType.setOperation(operation);
        lookupRequestType.setRegion(region);
        lookupRequestType.setVersion(version);

        lookupRequestType
                .setClassification(ConstantADM.BUSINESS_CLASSIFICATION);
        return registryRQ;
    }

    /**
     * Método que construye un <b>endPoit</b> a partir de un <b>RegistryRS</b>
     * 
     * @param registryRS
     * @return
     */
    public static String getEndPoint(LookupResponseType registryRS) {
        StringBuilder bf = new StringBuilder();

        bf.append(registryRS.getEndpoint().getProtocol());
        bf.append(ConstantADM.COMMON_STRING_DOT);
        bf.append(ConstantADM.COMMON_STRING_SLASH);
        bf.append(ConstantADM.COMMON_STRING_SLASH);
        bf.append(registryRS.getEndpoint().getHost());

        /* Se verifica si el registry tiene puerto de conexión */
        if (null != registryRS.getEndpoint().getPort()
                && !Constant.COMMON_STRING_EMPTY_STRING
                        .equals(registryRS.getEndpoint().getPort())) {
            bf.append(ConstantADM.COMMON_STRING_DOT);
            bf.append(registryRS.getEndpoint().getPort());
        }

        /* Se verifica si el conext comienza NO comienza con SLASH */
        if (!registryRS.getEndpoint().getContext()
                .startsWith(ConstantADM.COMMON_STRING_SLASH)) {
            bf.append(ConstantADM.COMMON_STRING_SLASH);
        }

        bf.append(registryRS.getEndpoint().getContext());

        return bf.toString();

    }

    /**
     * método para parsear payload Any al Objeto del segundo parametro
     * 
     * @param any
     * @param clase
     * @return
     * @throws MiddlewareException
     */
    public static Object parsePayload(Object any, Object clase)
            throws MiddlewareException {

        JSONObject jsonString = getJSONObject(any);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString.toString(), clase.getClass());
        } catch (IOException e) {

            StringBuilder errorMessage = new StringBuilder(
                    ConstantADM.ERROR_PARSING);
            errorMessage.append(clase.getClass().getName());
            throw new MiddlewareException(errorMessage.toString(), e);
        }

    }

    /**
     * 
     * @param arg
     * @return String con los parámetros concatenados
     */
    public static String buildString(String... arg) {
        if (null != arg) {
            StringBuilder sb = new StringBuilder();
            for (String str : arg) {
                sb.append(str);
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * Metodo que convierte una fecha string a un formato especifico
     * 
     * @param <code>date</code>
     * @param <code>formatSource</code>
     *            formato original
     * @param <code>formatTarget</code>
     *            formato destino
     * @return
     * @throws <code>CommonUtilException</code>
     */
    public static String formatDateStringToString(String date,
            String formatSource, String formatTarget)
            throws MiddlewareException {

        try {

            if (null != date) {
                SimpleDateFormat simpleDateFormatSource = new SimpleDateFormat(
                        formatSource);
                Date d1 = simpleDateFormatSource.parse(date);

                SimpleDateFormat simpleDateFormatTarget = new SimpleDateFormat(
                        formatTarget);
                return simpleDateFormatTarget.format(d1);
            }
        } catch (Exception e) {
            throw new MiddlewareException(e);
        }
        return ConstantADM.STRING_EMPTY;
    }

    /**
     * Método genérico para formatear fecha a un formato especifico.
     * 
     * @param date
     * @param format
     * @param logger
     * @return
     */
    public static String formatDateToString(Date date, String format)
            throws MiddlewareException {

        SimpleDateFormat simpleDateFormat;
        String formattedTime = null;
        Locale locale = new Locale(ConstantADM.COMMON_STRING_ES_LOWER_CASE,
                ConstantADM.COMMON_STRING_ES_UPPER_CASE);
        try {

            if (null != date) {
                simpleDateFormat = new SimpleDateFormat(format, locale);
                formattedTime = simpleDateFormat.format(date);
                return formattedTime;
            }
        } catch (Exception e) {
            throw new MiddlewareException(e);
        }
        return ConstantADM.STRING_EMPTY;
    }

    /**
     * 
     * @param messageId
     * @param requestDateTime
     * @param name
     * @param operation
     * @param region
     * @param version
     * @return RegistryRQ
     */
    public static co.nequi.message.registry.serviceregistry.RegistryRQ getRegistryRQ(
            String messageId, String requestDateTime, String name,
            String operation, String region, String version) {

        co.nequi.message.registry.serviceregistry.RegistryRQ registryRQ = new co.nequi.message.registry.serviceregistry.RegistryRQ();
        co.nequi.message.registry.serviceregistry.RegistryRequestType registryRequestType = new co.nequi.message.registry.serviceregistry.RegistryRequestType();
        co.nequi.message.registry.serviceregistry.HeaderRequestType headerRequestType = new co.nequi.message.registry.serviceregistry.HeaderRequestType();
        co.nequi.message.registry.serviceregistry.ConsumerType consumerType = new co.nequi.message.registry.serviceregistry.ConsumerType();
        co.nequi.message.registry.serviceregistry.ContainerType containerType = new co.nequi.message.registry.serviceregistry.ContainerType();
        co.nequi.message.registry.serviceregistry.BodyRequestType bodyRequestType = new co.nequi.message.registry.serviceregistry.BodyRequestType();
        co.nequi.message.registry.serviceregistry.lookup.LookupRequestType lookupRequestType = new co.nequi.message.registry.serviceregistry.lookup.LookupRequestType();

        registryRQ.setRegistryRequest(registryRequestType);
        registryRequestType.setHeader(headerRequestType);

        headerRequestType.setMessageId(messageId);
        headerRequestType.setMessageDate(requestDateTime);

        headerRequestType.setType(Constant.COMMON_STRING_EMPTY_STRING);
        headerRequestType.setOperation(Constant.COMMON_STRING_EMPTY_STRING);

        headerRequestType.setConsumer(consumerType);

        /* Atibutos del consumer */
        consumerType.setId(ConstantADM.COMMON_STRING_CONSUMER_ID);
        consumerType.setName(ConstantADM.COMMON_STRING_CONSUMER_NAME);

        consumerType.setContainer(containerType);
        containerType.setId(ConstantADM.COMMON_STRING_CONTAINER_ID_AS_001);
        containerType.setName(ConstantADM.COMMON_STRING_CONTAINER_NAME);

        /* Atributos del body */
        registryRequestType.setBody(bodyRequestType);

        bodyRequestType.setLookupRequest(lookupRequestType);
        lookupRequestType.setName(name);
        lookupRequestType.setOperation(operation);
        lookupRequestType.setRegion(region);
        lookupRequestType.setVersion(version);

        lookupRequestType
                .setClassification(ConstantADM.INTEGRATION_CLASSIFICATION);
        return registryRQ;
    }

    /**
     * Utilidad que construye la petici�n para el componente de integración
     * LDAP 401.
     * 
     * @param messageId
     * @param channel
     * @param service
     * @param operation
     * @param region
     * @param version
     * @return {@link co.bdigital.admin.messaging.integration.ldap401.v100.RequestMessageObjectType}
     */
    public static co.bdigital.admin.messaging.integration.ldap401.v100.RequestMessageObjectType getLdap401RequestMessage(
            String messageId, String channel, String service, String operation,
            String region, String version) {

        co.bdigital.admin.messaging.integration.ldap401.v100.RequestMessageObjectType integrationRQ;
        co.bdigital.admin.messaging.integration.ldap401.v100.RequestMessageType integrationRequestType = new co.bdigital.admin.messaging.integration.ldap401.v100.RequestMessageType();
        co.bdigital.admin.messaging.integration.ldap401.v100.RequestHeaderObjectType requestHeader = new co.bdigital.admin.messaging.integration.ldap401.v100.RequestHeaderObjectType();
        co.bdigital.admin.messaging.integration.ldap401.v100.RequestBodyObjectType requestBody = new co.bdigital.admin.messaging.integration.ldap401.v100.RequestBodyObjectType();

        co.bdigital.admin.messaging.integration.ldap401.v100.ContainerType containerType;
        co.bdigital.admin.messaging.integration.ldap401.v100.ConsumerType consumerType;

        integrationRQ = new co.bdigital.admin.messaging.integration.ldap401.v100.RequestMessageObjectType();

        requestHeader.setChannel(channel);

        consumerType = new co.bdigital.admin.messaging.integration.ldap401.v100.ConsumerType();

        consumerType.setId(ConstantADM.COMMON_STRING_CONSUMER_ID_SHP);
        consumerType
                .setDescription(ConstantADM.COMMON_STRING_CONSUMER_NAME_ADM);

        requestHeader.setConsumer(consumerType);

        containerType = new co.bdigital.admin.messaging.integration.ldap401.v100.ContainerType();
        containerType.setId(ConstantADM.COMMON_STRING_CONTAINER_ID_WAS);
        containerType.setName(ConstantADM.COMMON_STRING_CONTAINER_NAME_WAS);
        containerType.setType(ConstantADM.COMMON_STRING_CHANNEL);

        co.bdigital.admin.messaging.integration.ldap401.v100.DestinationType destinationType = new co.bdigital.admin.messaging.integration.ldap401.v100.DestinationType();
        destinationType.setContainer(containerType);
        destinationType.setRegion(region);

        requestHeader.setDestination(destinationType);

        co.bdigital.admin.messaging.integration.ldap401.v100.ServiceType serviceType = new co.bdigital.admin.messaging.integration.ldap401.v100.ServiceType();
        serviceType.setName(service);
        serviceType.setOperation(operation);
        serviceType.setVersion(version);

        destinationType.setService(serviceType);

        Date currentDate = new Date();
        Timestamp time = new Timestamp(currentDate.getTime());
        requestHeader.setRequestDateTime(time.toString());
        requestHeader.setMessageId(messageId);
        integrationRequestType.setHeader(requestHeader);

        integrationRequestType.setBody(requestBody);

        integrationRQ.setIntegrationRequest(integrationRequestType);

        return integrationRQ;
    }

    /**
     * 
     * @param args
     * @return args strings concatenated.
     */
    public static String generateString(String... args) {
        StringBuilder bf = new StringBuilder();
        if (null != args) {
            for (String arg : args) {
                bf.append(arg);
            }
        }
        return bf.toString();
    }

    /**
     * Metodo para constrir un objeto MessageRQ
     * 
     * @param accountNumber
     * @return MessageRQ
     */
    public static MessageRQ getMessageRQ(String accountNumber) {
        MessageRQ messageRQ = new MessageRQ();
        OperationGMFRQType operationGMFRQType = null;
        RequestMessageType requestMessageType = new RequestMessageType();
        co.bdigital.admin.messaging.services.gmf.RequestHeaderType requestHeaderType = new co.bdigital.admin.messaging.services.gmf.RequestHeaderType();
        co.bdigital.admin.messaging.services.gmf.RequestBodyType requestBodyType = new co.bdigital.admin.messaging.services.gmf.RequestBodyType();
        operationGMFRQType = new OperationGMFRQType();
        operationGMFRQType.setAccountNumber(accountNumber);
        requestBodyType.setOperationGMFRQ(operationGMFRQType);
        co.bdigital.admin.messaging.services.gmf.ChannelType channelType = new co.bdigital.admin.messaging.services.gmf.ChannelType();
        channelType.setId(ConstantADM.COMMON_STRING_CHANNEL_WEB);
        channelType.setName(ConstantADM.COMMON_STRING_CHANNEL_WEB);
        co.bdigital.admin.messaging.services.gmf.ConsumerType consumerType = new co.bdigital.admin.messaging.services.gmf.ConsumerType();
        consumerType.setId(ConstantADM.COMMON_STRING_CONSUMER_ID);
        consumerType.setName(ConstantADM.COMMON_STRING_CONSUMER_NAME);
        co.bdigital.admin.messaging.services.gmf.ContainerType containerType = new co.bdigital.admin.messaging.services.gmf.ContainerType();
        containerType.setId(ConstantADM.COMMON_STRING_CHANNEL_WEB);
        containerType.setName(ConstantADM.COMMON_STRING_CHANNEL_WEB);
        co.bdigital.admin.messaging.services.gmf.DestinationType destinationType = new co.bdigital.admin.messaging.services.gmf.DestinationType();
        destinationType.setServiceName(ConstantADM.GMF_SERVICES);
        destinationType
                .setServiceOperation(ConstantADM.SERVICE_OPERATION_UNCHECK);
        destinationType.setServiceRegion(ConstantADM.COMMON_STRING_REGION_COL);
        destinationType.setServiceVersion(
                ConstantADM.COMMON_STRING_SERVICE_VERSION_ONE_ZERO_ZERO);
        requestHeaderType.setContainer(containerType);
        requestHeaderType.setConsumer(consumerType);
        requestHeaderType.setChannel(channelType);
        requestHeaderType.setDestination(destinationType);
        Date date = new Date();
        requestHeaderType.setMessageID(String.valueOf(date.getTime()));
        requestHeaderType.setRequestDate(String.valueOf(date.getTime()));
        requestMessageType.setBody(requestBodyType);
        requestMessageType.setHeader(requestHeaderType);
        messageRQ.setRequestMessage(requestMessageType);
        return messageRQ;
    }

    /**
     * Metodo para reemplazar caracteres especiales y acentos
     * 
     * @param stringData
     * @param normalCharacters
     * @param specialCharacters
     * @return String without special characters
     */
    public static String replaceAccentAndSpecialCharacter(String stringData,
            String normalCharacters, String specialCharacters) {
        if (null != stringData && !stringData.isEmpty()) {
            char[] arrayData = stringData.toCharArray();
            for (int i = ConstantADM.COMMON_INT_ZERO; i < stringData
                    .length(); i++) {
                int position = specialCharacters.indexOf(arrayData[i]);
                if (position > -1) {
                    arrayData[i] = normalCharacters.charAt(position);
                }
            }
            return String.valueOf(arrayData);
        }
        return stringData;
    }

    /**
     * Metodo para reemplazar caracteres especiales y acentos
     * 
     * @param stringData
     * @param normalCharacters
     * @param specialCharacters
     * @param charReplace
     * @return String without special characters
     */
    public static String replaceAccentAndSpecialCharacter(String stringData,
            String normalCharacters, String specialCharacters,
            char charReplace) {

        // Se reemplazan caracteres especiales especificados dentro de esta
        // utilidad.
        stringData = replaceAccentAndSpecialCharacter(stringData,
                normalCharacters, specialCharacters);

        // Se reemplazan caracteres especiales por caracter enviado como
        // parametro.
        stringData = CommonUtil.replaceAccentAndSpecialCharacter(stringData,
                charReplace);

        return stringData;

    }

    /**
     * <p>
     * Metodo que construye el request para consumir el servicio de broker para
     * enviar notificaciones a Slack.
     * </p>
     * 
     * @param slackChannel
     *            canal de slack.
     * @param message
     *            mensaje a notificar en slack.
     * @return SendSlackNotificationServiceRequestType objeto request para el
     *         servicio de broker de notificacion a Slack.
     */
    public static SendSlackNotificationServiceRequestType buildSendSlackNotificationServiceRequestType(
            String slackChannel, String message) {

        SendSlackNotificationRQType sendSlackNotificationRQType;
        SendSlackNotificationServiceRequestType sendSlackNotificationServiceRequestType;

        sendSlackNotificationServiceRequestType = new SendSlackNotificationServiceRequestType();
        sendSlackNotificationRQType = new SendSlackNotificationRQType();

        sendSlackNotificationRQType.setChannel(slackChannel);
        sendSlackNotificationRQType.setText(message);

        sendSlackNotificationServiceRequestType
                .setSendSlackNotificationRQ(sendSlackNotificationRQType);

        return sendSlackNotificationServiceRequestType;
    }

    /**
     * Metodo que devuelve parametro por Nombre
     * 
     * @param parameters
     * @param name
     * @param defaultvalue
     * @return Parametro
     */
    public static String getParameterByName(List<Parametro> parameters,
            String name, String defaultvalue) {
        if (null != parameters && !parameters.isEmpty()) {
            for (Parametro parametro : parameters) {
                if (parametro.getNombre().equalsIgnoreCase(name)) {
                    return parametro.getValor();
                }
            }
        }
        return defaultvalue;
    }

    /**
     * Metodo que construye objeto PromocionRegla
     * 
     * @param id
     * @param description
     * @param promocionOperacion
     * @return PromocionRegla
     */
    public static PromocionRegla getPromocionRegla(String id,
            String description, PromocionOperacion promocionOperacion) {
        PromocionRegla promocionRegla = new PromocionRegla();
        promocionRegla.setAsunto(ConstantADM.STRING_EMPTY);
        promocionRegla.setCuentaContable(ConstantADM.STRING_EMPTY);
        promocionRegla.setColaJms(ConstantADM.STRING_EMPTY);
        promocionRegla.setDescripcion(description);
        promocionRegla.setEstado(ConstantADM.COMMON_BIG_DECIMAL_ZERO);
        Date currentDate = new Date();
        Timestamp time = new Timestamp(currentDate.getTime());
        promocionRegla.setFechaCreacion(time);
        promocionRegla.setFechaInicio(time);
        promocionRegla.setFechaFinal(time);
        promocionRegla
                .setPresupuestoMaximo(ConstantADM.COMMON_BIG_DECIMAL_ZERO);
        promocionRegla.setPromocionOperacion(promocionOperacion);
        Long idPromocionregla = Long.valueOf(id) + ConstantADM.COMMON_LONG_ONE;
        promocionRegla.setPromocionReglaId(idPromocionregla);
        promocionRegla.setTipoNofificacion(
                ConstantADM.COMMON_STRING_NOTIFICATION_TYPE_PUSH);
        promocionRegla
                .setTipoValor(ConstantADM.COMMON_STRING_VALUE_TYPE_PERCENTAGE);
        promocionRegla.setValor(ConstantADM.COMMON_BIG_DECIMAL_ZERO);
        promocionRegla.setValorDisponible(ConstantADM.COMMON_BIG_DECIMAL_ZERO);
        promocionRegla.setValorMax(ConstantADM.COMMON_BIG_DECIMAL_ZERO);
        promocionRegla.setValorMin(ConstantADM.COMMON_BIG_DECIMAL_ZERO);
        return promocionRegla;
    }
}
