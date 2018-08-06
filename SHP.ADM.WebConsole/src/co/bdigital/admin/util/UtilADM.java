package co.bdigital.admin.util;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import co.bdigital.admin.ejb.controller.view.NequiPointsServiceBeanLocal;
import co.bdigital.admin.ejb.controller.view.UpdatePromotionServiceBeanLocal;
import co.bdigital.admin.messaging.services.amazonS3.ConsumerType;
import co.bdigital.admin.messaging.services.amazonS3.ContainerType;
import co.bdigital.admin.messaging.services.amazonS3.GetFileRequestType;
import co.bdigital.admin.messaging.services.amazonS3.IntegrationRQ;
import co.bdigital.admin.messaging.services.amazonS3.IntegrationRequestType;
import co.bdigital.admin.messaging.services.amazonS3.RequestBodyType;
import co.bdigital.admin.messaging.services.amazonS3.RequestHeaderType;
import co.bdigital.admin.messaging.services.amazonS3.ServiceType;
import co.bdigital.admin.messaging.services.biometric.getProfileById.GetProfileByIDRQType;
import co.bdigital.admin.messaging.services.biometric.getProfileById.GetProfileByIDServiceRequestType;
import co.bdigital.admin.messaging.services.biometric.updateProfile.FaceDataType;
import co.bdigital.admin.messaging.services.biometric.updateProfile.UpdateProfileDaonRQType;
import co.bdigital.admin.messaging.services.biometric.updateProfile.UpdateProfileDaonServiceRequestType;
import co.bdigital.admin.messaging.services.biometric.updateProfile.VoiceDataType;
import co.bdigital.admin.messaging.services.blockClient.BlockClientRQType;
import co.bdigital.admin.messaging.services.blockClient.BlockClientServiceRequestType;
import co.bdigital.admin.messaging.services.customerLockManagment.CustomerLockManagmentRQType;
import co.bdigital.admin.messaging.services.customerLockManagment.CustomerLockManagmentServiceRequestType;
import co.bdigital.admin.messaging.services.device.sendpush.SendPushServiceRQType;
import co.bdigital.admin.messaging.services.device.sendpush.SendPushServiceRequestType;
import co.bdigital.admin.messaging.services.getVeriAttempts.GetVeriAttemptsRQType;
import co.bdigital.admin.messaging.services.getVeriAttempts.GetVeriAttemptsServiceRequestType;
import co.bdigital.admin.messaging.services.getbiometryondemand.GetBiometryOnDemandRQType;
import co.bdigital.admin.messaging.services.getbiometryondemand.GetBiometryOnDemandServiceRequestType;
import co.bdigital.admin.messaging.services.getcustomerlocks.GetCustomerLocksRQType;
import co.bdigital.admin.messaging.services.getcustomerlocks.GetCustomerLocksServiceRequestType;
import co.bdigital.admin.messaging.services.isb.slack.SendSlackNotificationRQType;
import co.bdigital.admin.messaging.services.isb.slack.SendSlackNotificationServiceRequestType;
import co.bdigital.admin.messaging.services.updateProfileStatus.UpdateProfileStatusRQType;
import co.bdigital.admin.messaging.services.updateProfileStatus.UpdateProfileStatusRequestType;
import co.bdigital.admin.model.StatusResponse;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.model.AwRol;
import co.bdigital.cmm.jpa.model.PromocionComercio;
import co.bdigital.cmm.jpa.model.PromocionComercioPK;
import co.bdigital.cmm.jpa.model.PromocionOperacion;
import co.bdigital.cmm.jpa.model.PromocionUsuario;
import co.bdigital.cmm.jpa.model.framework.WsProvider;
import co.bdigital.cmm.messaging.general.DestinationType;
import co.bdigital.cmm.messaging.general.RequestBody;
import co.bdigital.cmm.messaging.general.RequestHeader;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.RequestMessageType;
import co.bdigital.shl.tracer.CustomLogger;

public class UtilADM {

    /** Logger **/
    private static CustomLogger logger = new CustomLogger(UtilADM.class);

    private UtilADM() {

    }

    public static String basepathlogin() {

        return ConstantADM.BASE_PATH_LOGIN;
    }

    public static String basePath() {

        return ConstantADM.BASE_PATH;
    }

    /**
     * Metodo para validar que el tipo de documento este correcto en el archivo
     * 
     * @param documentType
     * @return
     */
    public static boolean validateDocumentType(String documentType) {
        if (documentType.equals(ConstantADM.DOCUMENT_TYPE_CC)
                || documentType.equals(ConstantADM.DOCUMENT_TYPE_TI)
                || documentType.equals(ConstantADM.DOCUMENT_TYPE_PP)) {
            return true;
        }
        return false;
    }

    /**
     * Metodo para validar que un dato sea tipo numerico
     * 
     * @param number
     * @return
     */
    public static boolean isNumeric(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Metodo para devolver la url completa, con el contexto
     * 
     * @return
     */
    public static String getUrlContextPath() {
        HttpServletRequest req = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();
        String url = req.getRequestURL().toString();
        String urlContext = url.substring(ConstantADM.COMMON_ZERO,
                url.length() - req.getRequestURI().length())
                + req.getContextPath() + ConstantADM.WORD_SLASH_SPLIT_URL;
        return urlContext;
    }

    /**
     * Metodo para devolver la url del servidor sin el contexto
     * 
     * @return
     */
    public static String getUrlWithoutContextPath() {
        HttpServletRequest req = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();
        String url = req.getRequestURL().toString();
        String urlContext = url.substring(ConstantADM.COMMON_ZERO,
                url.length() - req.getRequestURI().length())
                + ConstantADM.WORD_SLASH_SPLIT_URL;
        return urlContext;
    }

    /**
     * Metodo para formar el encabezado de la mensajeria para los servicios rest
     * 
     * @param serviceName
     * @param serviceOperacion
     * @param serviceRegion
     * @param serviceVersion
     * @return
     */
    public static RequestMessageObjectType getRequestObject(String serviceName,
            String serviceOperacion, String serviceRegion,
            String serviceVersion) {
        RequestMessageObjectType request = new RequestMessageObjectType();
        RequestMessageType responseMessageType = new RequestMessageType();
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat(
                ConstantADM.REST_SERVICE_DATE_FORMAT);

        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setChannel(ConstantADM.REST_SERVICE_CHANEL);
        requestHeader.setClientID(ConstantADM.STRING_EMPTY);
        requestHeader.setMessageID(String.valueOf(new Date().getTime()));
        requestHeader.setRequestDate(dateFormat.format(currentDate));

        DestinationType destinationType = new DestinationType();
        destinationType.setServiceName(serviceName);
        destinationType.setServiceOperation(serviceOperacion);
        destinationType.setServiceRegion(serviceRegion);
        destinationType.setServiceVersion(serviceVersion);
        requestHeader.setDestination(destinationType);

        responseMessageType.setRequestHeader(requestHeader);
        request.setRequestMessage(responseMessageType);

        return request;
    }

    /**
     * Metodo para formar el encabezado de la mensajeria para los servicios rest
     * de integracion
     * 
     * @param messageId
     * @param serviceName
     * @param serviceOperacion
     * @param serviceRegion
     * @param serviceVersion
     * @return
     */
    public static IntegrationRQ getRequestIntegration(String messageId,
            String serviceName, String serviceOperacion, String serviceRegion,
            String serviceVersion) {
        IntegrationRQ integrationRQ = new IntegrationRQ();
        IntegrationRequestType integrationRequestType = new IntegrationRequestType();
        RequestHeaderType requestHeaderType = new RequestHeaderType();
        ConsumerType consumerType = new ConsumerType();
        consumerType.setDescription("");
        consumerType.setId("");
        requestHeaderType.setConsumer(consumerType);
        requestHeaderType.setChannel(ConstantADM.REST_SERVICE_CHANEL);
        requestHeaderType.setMessageId(messageId);
        requestHeaderType.setRequestDateTime(getCurrentDate().toString());
        co.bdigital.admin.messaging.services.amazonS3.DestinationType destinationType = new co.bdigital.admin.messaging.services.amazonS3.DestinationType();
        ServiceType serviceType = new ServiceType();
        serviceType.setName(serviceName);
        serviceType.setOperation(serviceOperacion);
        serviceType.setVersion(serviceVersion);
        destinationType.setService(serviceType);
        destinationType.setRegion(serviceRegion);
        ContainerType containerType = new ContainerType();
        containerType.setId("");
        containerType.setName("");
        containerType.setType("");
        destinationType.setContainer(containerType);
        requestHeaderType.setDestination(destinationType);
        integrationRequestType.setHeader(requestHeaderType);
        integrationRQ.setIntegrationRequest(integrationRequestType);
        return integrationRQ;
    }

    /**
     * Metodo para obtener la fecha actual del sistema
     * 
     * @return
     */
    public static Timestamp getCurrentDate() {
        Date currentDate = new Date();
        Timestamp date = new Timestamp(currentDate.getTime());
        return date;
    }

    /**
     * Metodo para obtener la fecha actual en formato ddMMyyyy
     * 
     * @return
     */
    public static String getFormatedCurrentDate(Date currentDate) {
        SimpleDateFormat formatterAll = new SimpleDateFormat(
                ConstantADM.FORMAT_DATE_NEQUI_POINTS);

        return formatterAll.format(currentDate);
    }

    /**
     * Metodo para crear la descripcion del mensaje que se enviara a la
     * auditoria
     * 
     * @param status
     * @return
     */
    public static String createMessageAudit(StatusResponse status,
            String data) {
        StringBuilder message = new StringBuilder();
        message.append(ConstantADM.DATA_AUDIT);
        message.append(data);
        message.append(ConstantADM.STRING_SPACE);
        message.append(ConstantADM.COMMON_STRING_DASH);
        message.append(ConstantADM.STRING_SPACE);
        message.append(ConstantADM.COMMON_STRING_RESPONSE_STATUS_COD);
        message.append(ConstantADM.COMMON_STRING_DOT);
        message.append(ConstantADM.STRING_SPACE);
        message.append(status.getStatusCode());
        message.append(ConstantADM.STRING_SPACE);
        message.append(ConstantADM.COMMON_STRING_DASH);
        message.append(ConstantADM.STRING_SPACE);
        message.append(ConstantADM.COMMON_STRING_RESPONSE_STATUS_DES);
        message.append(ConstantADM.COMMON_STRING_DOT);
        message.append(ConstantADM.STRING_SPACE);
        message.append(status.getStatusDesc());
        return message.toString();
    }

    /**
     * <p>
     * Utilidad para contruir request de envio de push.
     * </p>
     * 
     * @param phoneNumber
     * @param type
     * @return objeto request para el servicio de MDW de envio de push.
     */
    public static SendPushServiceRequestType buildSendPushResquestType(
            String phoneNumber, String type) {
        SendPushServiceRequestType sendPushRequestType = new SendPushServiceRequestType();
        SendPushServiceRQType sendPushRq = new SendPushServiceRQType();
        sendPushRequestType.setSendPushRQ(sendPushRq);

        sendPushRq.setPhoneNumber(phoneNumber);
        sendPushRq.setType(type);

        return sendPushRequestType;
    }

    /**
     * Utilidad que construye el request body para el servicio de bloqueo
     * administrativo de un cliente
     * 
     * @param phoneNumber
     * @param lockType
     * @param action
     * @return
     */
    public static CustomerLockManagmentServiceRequestType buildCustomerLockManagmentResquestType(
            String phoneNumber, String lockType, String action) {
        CustomerLockManagmentServiceRequestType customerLockManagmentRequestType = new CustomerLockManagmentServiceRequestType();
        CustomerLockManagmentRQType customerLockRq = new CustomerLockManagmentRQType();
        customerLockManagmentRequestType
                .setCustomerLockManagmentRQ(customerLockRq);

        customerLockRq.setPhoneNumber(phoneNumber);
        customerLockRq.setLockType(lockType);
        customerLockRq.setAction(action);

        return customerLockManagmentRequestType;
    }

    /**
     * Utilidad que construye el request body para el servicio de bloqueo de un
     * cliente
     * 
     * @param phoneNumber
     * @param lockType
     * @return
     */
    public static BlockClientServiceRequestType buildBlockClientRequestType(
            String phoneNumber, String lockType) {
        BlockClientServiceRequestType blockClientRequestType = new BlockClientServiceRequestType();
        BlockClientRQType blockClientRq = new BlockClientRQType();
        blockClientRequestType.setBlockClientRQ(blockClientRq);

        blockClientRq.setPhoneNumber(phoneNumber);
        blockClientRq.setLockType(lockType);

        return blockClientRequestType;
    }

    /**
     * 
     * @param httpClient
     * @param urlService
     * @param jsonRequest
     * @param basicAuthUser
     * @param basicAuthPass
     * @return JSONObject
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static JSONObject executeRequest(HttpClient httpClient,
            String urlService, String jsonRequest, String basicAuthUser,
            String basicAuthPass) throws UnsupportedEncodingException,
            IOException, ClientProtocolException {
        HttpPost requestConnection = new HttpPost(urlService);
        StringBuilder authStr = new StringBuilder();
        authStr.append(basicAuthUser);
        authStr.append(Constant.COLON);
        authStr.append(basicAuthPass);
        String encoding = Base64
                .encodeBase64String(authStr.toString().getBytes());

        String str = generateStringConcatenated(ConstantADM.BASIC_WORD,
                encoding);

        requestConnection.addHeader(ConstantADM.BASIC_AUTHORIZATION_WORD, str);

        StringEntity params = new StringEntity(jsonRequest);
        requestConnection.addHeader(ConstantADM.REST_SERVICE_CONTENT_TYPE,
                ConstantADM.REST_SERVICE_TYPE_APPLICATION);
        requestConnection.setEntity(params);
        logger.info(ConstantADM.URL_WEB_ADMIN + urlService);
        logger.info(ConstantADM.REQUEST_WEB_ADMIN + jsonRequest);
        HttpResponse response = null;
        BufferedReader rd = null;
        StringBuffer result = null;
        InputStreamReader inputStreamReader = null;
        try {
            response = httpClient.execute(requestConnection);
            inputStreamReader = new InputStreamReader(
                    response.getEntity().getContent());
            rd = new BufferedReader(inputStreamReader);
            result = new StringBuffer();
            String line = ConstantADM.STRING_EMPTY;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            logger.info(ConstantADM.RESPONSE_WEB_ADMIN + result.toString());
            return new JSONObject(result.toString());
        } finally {
            if (null != rd) {
                rd.close();
            }
            if (null != inputStreamReader) {
                inputStreamReader.close();
            }
            requestConnection.abort();
        }
    }

    /**
     * <p>
     * Utilidad que construye el request body para el servicio de consulta de
     * detalles de verificaci&oacute;n de biometr&iacute;a.
     * </p>
     * 
     * @param phoneNumber
     *            n&uacute;mero de celular a consultar los detalles de
     *            verificaci&oacute;n de biometr&iacute;a.
     * @return GetBiometryOnDemandServiceRequestType
     */
    public static GetBiometryOnDemandServiceRequestType buildGetBiometryOnDemandResquestType(
            String phoneNumber, String maxResult) {
        GetBiometryOnDemandServiceRequestType getBiometryOnDemandRequestType = new GetBiometryOnDemandServiceRequestType();
        GetBiometryOnDemandRQType getBiometryOnDemandRq = new GetBiometryOnDemandRQType();
        getBiometryOnDemandRequestType
                .setGetBiometryOnDemandRQ(getBiometryOnDemandRq);

        getBiometryOnDemandRq.setPhoneNumber(phoneNumber);
        getBiometryOnDemandRq.setMaxResult(maxResult);

        return getBiometryOnDemandRequestType;
    }

    /**
     * <p>
     * Utilidad que construye el request body para el servicio de consulta de
     * bloqueos del Cliente
     * </p>
     * 
     * @param phoneNumber
     *            n&uacute;mero de celular a consultar los detalles
     * @return GetCustomerLocksServiceRequestType
     */
    public static GetCustomerLocksServiceRequestType buildGetCustomerLocksServiceRequestType(
            String phoneNumber) {
        GetCustomerLocksServiceRequestType getCustomerLocksServiceRequestType = new GetCustomerLocksServiceRequestType();
        GetCustomerLocksRQType getCustomerLocksRQType = new GetCustomerLocksRQType();
        getCustomerLocksServiceRequestType
                .setGetCustomerLocksRQ(getCustomerLocksRQType);

        getCustomerLocksRQType.setPhoneNumber(phoneNumber);

        return getCustomerLocksServiceRequestType;
    }

    /**
     * Este método construye una petición para actualizar el perfil de un
     * cliente
     * 
     * @param phoneNumber
     * @param isProfileBlocked
     * @return
     */
    public static UpdateProfileStatusRequestType buildUpdateProfileStatusRequestType(
            String phoneNumber, String isProfileBlocked) {
        // GetCustomerLocksServiceRequestType
        UpdateProfileStatusRequestType UpdateProfileStatusRequestType = new UpdateProfileStatusRequestType();
        UpdateProfileStatusRQType updateProfileStatusRQType = new UpdateProfileStatusRQType();
        updateProfileStatusRQType.setPhoneNumber(phoneNumber);
        updateProfileStatusRQType.setIsProfileBlocked(isProfileBlocked);

        UpdateProfileStatusRequestType
                .setUpdateProfileStatusRQ(updateProfileStatusRQType);

        return UpdateProfileStatusRequestType;

    }

    /**
     * Utilidad que construye el request body para el servicio que devuelve la
     * imagen del bucket de S3 en base64
     * 
     * @param getKeyAndBucketImg
     * @return
     */
    public static RequestBodyType getRequestBodyIntegratioGetFile(
            HashMap<String, String> getKeyAndBucketImg) {
        RequestBodyType requestBodyType = new RequestBodyType();
        GetFileRequestType getFileRequestType = new GetFileRequestType();
        getFileRequestType.setBucket(
                getKeyAndBucketImg.get(ConstantADM.MAP_BUCKET_URL_S3));
        getFileRequestType
                .setFileKey(getKeyAndBucketImg.get(ConstantADM.MAP_KEY_URL_S3));
        requestBodyType.setGetFileRequest(getFileRequestType);

        return requestBodyType;
    }

    /**
     * Utilidad que construye el request body para el servicio de consulta del
     * perfil de daon por ID
     * 
     * @param phoneNumber
     * @param maxResult
     * @return
     */
    public static GetProfileByIDServiceRequestType buildGetProfileByIDRequestType(
            String phoneNumber) {
        GetProfileByIDServiceRequestType getProfileByIDRequestType = new GetProfileByIDServiceRequestType();
        GetProfileByIDRQType getProfileByIDRq = new GetProfileByIDRQType();
        getProfileByIDRequestType.setGetProfileByIDRQ(getProfileByIDRq);

        getProfileByIDRq.setPhoneNumber(phoneNumber);

        return getProfileByIDRequestType;
    }

    /**
     * Utilidad que construye el request body para el servicio de actualizacion
     * del perfil en daon
     * 
     * @param phoneNumber
     * @param action
     * @param flagCreate
     * @param data
     * @return
     */
    public static UpdateProfileDaonServiceRequestType buildUpdateProfileDaonRequestType(
            String phoneNumber, String action, String flagCreate, String data,
            String imageType) {
        UpdateProfileDaonServiceRequestType updateProfileDaonRequestType = new UpdateProfileDaonServiceRequestType();
        UpdateProfileDaonRQType updateProfileDaonRq = new UpdateProfileDaonRQType();
        updateProfileDaonRequestType
                .setUpdateProfileDaonRQ(updateProfileDaonRq);

        updateProfileDaonRq.setPhoneNumber(phoneNumber);
        updateProfileDaonRq.setAction(action);
        updateProfileDaonRq.setFlagCreate(flagCreate);
        FaceDataType faceData = new FaceDataType();
        faceData.setData(data);
        faceData.setImageType(imageType);
        updateProfileDaonRq.setFaceData(faceData);
        ArrayList<VoiceDataType> voiceDataList = new ArrayList<VoiceDataType>();
        VoiceDataType voiceData = new VoiceDataType();
        voiceData.setData(ConstantADM.STRING_EMPTY);
        voiceData.setFormat(ConstantADM.STRING_EMPTY);
        voiceDataList.add(voiceData);
        updateProfileDaonRq.setVoiceData(voiceDataList);
        updateProfileDaonRq.setFaceData(faceData);
        updateProfileDaonRq.setFaceData(faceData);
        updateProfileDaonRq.setFaceData(faceData);
        updateProfileDaonRq.setUuid(ConstantADM.STRING_EMPTY);
        updateProfileDaonRq.setSoftToken(ConstantADM.STRING_EMPTY);
        updateProfileDaonRq.setVersionSO(ConstantADM.STRING_EMPTY);
        updateProfileDaonRq.setSistemaOperativo(ConstantADM.STRING_EMPTY);
        updateProfileDaonRq.setModel(ConstantADM.STRING_EMPTY);
        updateProfileDaonRq.setManufacturer(ConstantADM.STRING_EMPTY);

        return updateProfileDaonRequestType;
    }

    /**
     * Utilidad que construye el request body para el servicio de consulta de
     * los intentos exitosos y fallidos de un usuario en DAON
     * 
     * @param phoneNumber
     * @return
     */
    public static GetVeriAttemptsServiceRequestType buildGetVeriAttemptsServiceRequestType(
            String phoneNumber) {
        GetVeriAttemptsServiceRequestType getVeriAttemptsRequestType = new GetVeriAttemptsServiceRequestType();
        GetVeriAttemptsRQType getVeriAttemptsRq = new GetVeriAttemptsRQType();
        getVeriAttemptsRequestType.setGetVeriAttemptsRQ(getVeriAttemptsRq);

        getVeriAttemptsRq.setPhoneNumber(phoneNumber);

        return getVeriAttemptsRequestType;
    }

    /**
     * @param status
     * @return
     */
    public static FacesMessage buildErrorMessage(StatusResponse status,
            Severity level, String defaultErrorMessage) {
        FacesMessage message;
        message = new FacesMessage(level,
                ConstantADM.WORD_ERROR
                        + (null == status || null == status.getStatusCode()
                                || status.getStatusCode().isEmpty()
                                        ? ConstantADM.STATUS_CODE_DEFAULT_ERROR
                                        : status.getStatusCode()),
                null == status || null == status.getStatusDesc()
                        || status.getStatusDesc().isEmpty()
                                ? defaultErrorMessage : status.getStatusDesc());
        return message;
    }

    /**
     * <p>
     * M&eacute;todo que construye la URL para consumir los servicios de
     * middleware.
     * </p>
     * 
     * @param host
     * @param port
     * @param context
     * @return URL para consumir los servicios de middleware.
     */
    public static String getUrlRestServicesEndPoint(String host, String port,
            String context) {
        StringBuilder url = new StringBuilder();
        url.append(host);
        url.append(Constant.COLON);
        url.append(port);
        url.append(context);
        return url.toString();
    }

    /**
     * <p>
     * M&eacute;todo que construye la URL para consumir los servicios de
     * middleware.
     * </p>
     * 
     * @param mdwServicesInfo
     *            datos para el endpoint de los servicios de middleware.
     * @return URL para consumir los servicios de middleware.
     */
    public static String getUrlRestServicesEndPoint(
            WsProvider mdwServicesInfo) {
        if (null == mdwServicesInfo) {
            return null;
        }
        return getUrlRestServicesEndPoint(mdwServicesInfo.getWsHost(),
                mdwServicesInfo.getWsPort().toPlainString(),
                mdwServicesInfo.getWsContext());
    }

    /**
     * <p>
     * Utilidad que construye el endpoint del servicio a consumir en MDW.
     * </p>
     * 
     * @param mdwServicesInfo
     *            informaci&oacute;n de contexto.
     * @param serviceName
     *            nombre de servicio
     * @param serviceOperation
     *            nombre de operaci&oacute;n
     * @return
     */
    public static String buildUrlRestServicesEndPoint(
            WsProvider mdwServicesInfo, String serviceName,
            String serviceOperation) {
        if (null == mdwServicesInfo) {
            return null;
        }
        String url = getUrlRestServicesEndPoint(mdwServicesInfo.getWsHost(),
                mdwServicesInfo.getWsPort().toPlainString(),
                mdwServicesInfo.getWsContext());

        if (null == url) {
            return null;
        }

        StringBuilder urlService = new StringBuilder();
        urlService.append(url);
        urlService.append(serviceName);
        urlService.append(Constant.SLASH);
        urlService.append(serviceOperation);
        return urlService.toString();
    }

    /**
     * Metodo para obtener una imagen y codificarla a base64
     * 
     * @param urlImage
     *            url de la imagen que se va a convertir
     * @throws IOException
     */
    public static String getBase64Image(String urlImage) throws IOException {
        String base64 = "";
        Image image = null;
        URL url = new URL(urlImage);
        image = ImageIO.read(url);
        byte[] imageBytes = IOUtils.toByteArray(url);
        base64 = Base64.encodeBase64String(imageBytes);
        return base64;
    }

    /**
     * Metodo para separar el nombre del perfil y no mostrarlo con la region
     * 
     * @param roles
     * @return
     */
    public static List<AwRol> getListRolSplitProfileName(List<AwRol> roles) {
        List<AwRol> rolesSplit = new ArrayList<AwRol>();
        Pattern pattern = Pattern.compile(ConstantADM.STRING_SPLIT_POINT);
        for (AwRol awRol : roles) {
            String[] split = pattern.split(awRol.getNombre(), 0);
            if (split.length != 0) {
                awRol.setNombre(split[0]);
            }

            rolesSplit.add(awRol);
        }
        return rolesSplit;
    }

    /**
     * Utilidad para eliminar un archivo.
     * 
     * @param path
     *            {@link String} con la ruta del archivo a eliminar.
     */
    public static void deleteFile(String path) {
        if (null != path) {
            File previousFile = new File(path);
            if (null != previousFile && previousFile.exists()) {
                previousFile.delete();
            }
        }
    }

    /**
     * <p>
     * Metodo que genera un string con la concatenación de todos los strings del
     * array <code>parameters</code>.
     * </p>
     * 
     * @param parameters
     *            Listado de cadenas a concatenar.
     * @return String concatenado.
     */
    public static String generateStringConcatenated(String... parameters) {
        StringBuilder trace = new StringBuilder();

        if (null != parameters
                && ConstantADM.COMMON_INT_ZERO < parameters.length) {
            for (String string : parameters) {
                trace.append(string);
            }
        }

        return trace.toString();
    }

    /**
     * Metodo para construir objeto RequestMessageObjectType para slack
     * 
     * @param serviceName
     * @param country
     * @param serviceVersion
     * @param message
     * @return
     */
    public static RequestMessageObjectType buildRequestMessageObjectType(
            String serviceName, String country, String serviceVersion,
            String message) {

        RequestMessageObjectType request = UtilADM.getRequestObject(serviceName,
                ConstantADM.SERVICE_OPERATION_UPDATE_USER, country,
                serviceVersion);

        RequestBody requestBody = new RequestBody();
        SendSlackNotificationServiceRequestType sendSlackNotificationRequestType = new SendSlackNotificationServiceRequestType();
        SendSlackNotificationRQType sendSlackNotificationRQType = new SendSlackNotificationRQType();
        sendSlackNotificationRQType
                .setChannel(ConstantADM.COMMON_STRING_CHANNEL_WEB);
        sendSlackNotificationRQType.setText(message);
        sendSlackNotificationRequestType
                .setSendSlackNotificationRQ(sendSlackNotificationRQType);
        requestBody.setAny(sendSlackNotificationRequestType);
        request.getRequestMessage().setRequestBody(requestBody);

        return request;

    }

    /**
     * Metodo para enviar mensaje a slack
     * 
     * @param country
     * @param userCurrent
     * @param message
     * @param nequiPointsServiceBeanLocal
     * @param error
     * @param region
     * @return
     */
    public static boolean sendSlack(String country, String userCurrent,
            String message,
            NequiPointsServiceBeanLocal nequiPointsServiceBeanLocal,
            String error) {
        RequestMessageObjectType request = buildRequestMessageObjectType(
                ConstantADM.COMMON_STRING_UTIL_SERVICES, country,
                ConstantADM.COMMON_STRING_UTIL_SERVICES_VERSION, message);
        StringBuilder slackMessage = new StringBuilder();
        slackMessage.append(country);
        slackMessage.append(ConstantADM.STRING_EMPTY_VALUE);
        slackMessage.append(userCurrent);
        slackMessage.append(error);
        slackMessage.append(message);
        String messageSlack = slackMessage.toString();

        return nequiPointsServiceBeanLocal.slackNotification(request,
                messageSlack, country);
    }

    /**
     * Metodo para comparar si la frequencia de reinicio esta dentro del
     * intervalo de la promoci�n
     * 
     * @param from
     * @param to
     * @param frequencyRestart
     * @return
     */
    public static boolean daysDiference(Date from, Date to,
            String frequencyRestart) {
        boolean result = false;
        long inicio = from.getTime();
        long fin = to.getTime();
        long resta = fin - inicio;
        long days = resta / ConstantADM.HOURS_IN_A_DAY
                / ConstantADM.MINUTES_IN_AN_HOUR
                / ConstantADM.MINUTES_IN_AN_HOUR / ConstantADM.ONE_THOUSAND;
        if (days >= Long.parseLong(frequencyRestart)) {
            result = true;
        }
        return result;
    }

    /**
     * Metodo para actualizar reglas de las promociones
     * 
     * @param updatePromotionServiceBeanLocal
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
     * @param region
     * @param maximumBudget
     * @param frequencyMinimum
     * @param frequencyRestart
     * @return
     */
    public static FacesMessage updateRules(
            UpdatePromotionServiceBeanLocal updatePromotionServiceBeanLocal,
            String id, String description, String fromDate, String toDate,
            String minValue, String maxValue, String value, String valueType,
            String accountingAccount, String availableValue, String ocurrence,
            String status, String notificationType, String subject,
            String message, String region, String maximumBudget,
            String frequencyMinimum, String frequencyRestart) {
        boolean isUpdate = updatePromotionServiceBeanLocal.updatePromotion(id,
                description, fromDate, toDate, minValue, maxValue, value,
                valueType, accountingAccount, availableValue, ocurrence, status,
                notificationType, subject, message, region, maximumBudget,
                frequencyMinimum, frequencyRestart);
        if (isUpdate) {
            return new FacesMessage(FacesMessage.SEVERITY_INFO,
                    ConstantADM.SUCCESS_UPDATE_PROMOTION,
                    ConstantADM.SUCCESS_UPDATE_PROMOTION_DESC);
        } else {
            return new FacesMessage(FacesMessage.SEVERITY_INFO,
                    ConstantADM.ERROR_UPDATE_PROMOTION,
                    ConstantADM.ERROR_UPDATE_PROMOTION_DESC);
        }
    }

    /**
     * Metodo para construir objeto PromocionUsuario
     * 
     * @param document
     * @param documentType
     * @param phone
     * @param promocionOperacion
     * @return PromocionUsuario
     */
    public static PromocionUsuario getPromocionUsuario(String document,
            String documentType, String phone,
            PromocionOperacion promocionOperacion) {
        PromocionUsuario promocionUsuario = new PromocionUsuario();
        promocionUsuario.setCelular(phone);
        promocionUsuario.setTipoIdentificacion(documentType);
        promocionUsuario.setIdentificacion(document);
        promocionUsuario.setPromocionOperacion(promocionOperacion);
        return promocionUsuario;
    }

    /**
     * Metodo para construir objeto PromocionComercio
     * 
     * @param comerce
     * @param terminal
     * @param status
     * @param promocionOperaionId
     * @param promocionOperacion
     * @return PromocionComercio
     */
    public static PromocionComercio getPromocionComercio(String comerce,
            String terminal, String status, String promocionOperaionId,
            PromocionOperacion promocionOperacion) {
        PromocionComercio promocionComercio = new PromocionComercio();
        promocionComercio.setEstado(new BigDecimal(status));
        promocionComercio.setPromocionOperacion(promocionOperacion);
        PromocionComercioPK promocionComercioPK = new PromocionComercioPK();
        promocionComercioPK.setComercioId(comerce);
        promocionComercioPK
                .setPromocionOperacionId(Long.parseLong(promocionOperaionId));
        promocionComercioPK.setTerminalId(terminal);
        promocionComercio.setId(promocionComercioPK);

        return promocionComercio;
    }

}