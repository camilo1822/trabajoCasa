package co.bdigital.admin.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.nequi.cmm.messaging.general.ResponseMessageObjectType;
import com.nequi.cmm.messaging.general.StatusType;

import co.bdigital.admin.ejb.controller.AuditServiceBean;
import co.bdigital.admin.ejb.controller.ParametersServiceBean;
import co.bdigital.admin.ejb.controller.view.ClientInfoServiceBeanLocal;
import co.bdigital.admin.messaging.services.amazonS3.IntegrationRQ;
import co.bdigital.admin.messaging.services.amazonS3.IntegrationRS;
import co.bdigital.admin.messaging.services.amazonS3.RequestBodyType;
import co.bdigital.admin.messaging.services.amazonS3.ResponseBodyType;
import co.bdigital.admin.messaging.services.biometric.getProfileById.GetProfileByIDServiceRequestType;
import co.bdigital.admin.messaging.services.biometric.getProfileById.GetProfileByIDServiceResponseType;
import co.bdigital.admin.messaging.services.biometric.updateProfile.UpdateProfileDaonServiceRequestType;
import co.bdigital.admin.messaging.services.blockClient.BlockClientServiceRequestType;
import co.bdigital.admin.messaging.services.customerLockManagment.CustomerLockManagmentServiceRequestType;
import co.bdigital.admin.messaging.services.device.sendpush.SendPushServiceRequestType;
import co.bdigital.admin.messaging.services.getVeriAttempts.AttemptsType;
import co.bdigital.admin.messaging.services.getVeriAttempts.GetVeriAttemptsServiceRequestType;
import co.bdigital.admin.messaging.services.getVeriAttempts.GetVeriAttemptsServiceResponseType;
import co.bdigital.admin.messaging.services.getbiometryondemand.GetBiometryOnDemandServiceRequestType;
import co.bdigital.admin.messaging.services.getbiometryondemand.GetBiometryOnDemandServiceResponseType;
import co.bdigital.admin.model.PermissionByRol;
import co.bdigital.admin.model.StatusResponse;
import co.bdigital.admin.util.ConnectionMDW;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.UtilADM;
import co.bdigital.admin.util.WebConsoleUtil;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.model.BloqueoUsuario;
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.cmm.jpa.model.framework.WsProvider;
import co.bdigital.cmm.messaging.general.RequestBody;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.shl.tracer.CustomLogger;

/**
 *
 * @author juan.molinab
 */
@ManagedBean(name = "biometryResetMGBean")
@RequestScoped
public class BiometryResetMGBean implements Serializable {

    /** Numero de serial version **/
    private static final long serialVersionUID = 1L;
    /** Numero del celular para enviar push de verificacion de biometria. **/
    private String phoneNumber;
    /** Codigo del pais **/
    private String countryId;
    /** Lista de los intentos de biometria **/
    private List<AttemptsType> listAttemps;
    /** Url de la imagen nueva enrolada **/
    private String urlImgEnrolNew = "";
    private String urlImgEnrolNewSesion = "";
    /** Url de la imagen antigua enrolada **/
    private String urlImgEnrolOld = "";
    private String urlImgEnrolOldSesion = "";
    /** Usuario en sesion **/
    private String currentUser;
    /** Propiedad para saber si se muestra o no el boton de envio de push **/
    private boolean isShowFirstButton;
    private String isShowFirstButtonSesion;
    /**
     * Propiedad para saber si se muestra o no el boton de aprovar o rechazar
     * biometria
     **/
    private boolean isShowLastButton;
    private String isShowLastButtonSesion;
    /** Logger **/
    private CustomLogger logger;
    /** Permisos de la accion **/
    private PermissionByRol permissionRol;
    /**
     * Objeto con los datos del endpoint de MDW.
     */
    private WsProvider mdwProviderInfo;
    private WsProvider isAwsProviderInfo;
    /** Informacion de los servicios a consumir **/
    private static final String ADMIN_SERVICES_NAME = "AdminServices";
    private static final String ATTEMPTS_SERVICES_NAME = "AttemptsService";
    private static final String UTIL_SERVICES_VERSION = "1.0.0";
    private static final String BIOMETRIY_SERVICES_VERSION = "1.1.0";
    private static final String MAX_RESULT = "1";
    private static final String DEVICE_SERVICES_NAME = "DeviceServices";
    private static final String DEVICE_SERVICES_VERSION = "1.0.0";
    private static final String PROFILE_SERVICES_NAME = "ProfileService";
    private static final String PROFILE_SERVICES_VERSION = "1.1.0";
    private static final String UPDATE_PROFILE_SERVICES_NAME = "UpdateProfileDaonService";
    private static final String UPDATE_PROFILE_SERVICES_VERSION = "1.1.0";
    private static final String USER_SERVICES_NAME = "UserServices/LockUserServices";
    private static final String USER_SERVICES_VERSION = "1.1.0";
    private static final String FILE_S3_SERVICES_NAME = "FileAS3Services";
    private static final String FILE_S3_SERVICES_VERSION = "1.0.0";

    @EJB
    private transient ParametersServiceBean parametersServiceBean;
    @EJB
    private transient AuditServiceBean auditServiceBean;
    @EJB
    private transient ClientInfoServiceBeanLocal clientInfoServiceBean;

    public BiometryResetMGBean() {
        logger = new CustomLogger(this.getClass());
        @SuppressWarnings("unchecked")
        ArrayList<AttemptsType> listAttempsSesion = (ArrayList<AttemptsType>) FacesContext
                .getCurrentInstance().getExternalContext().getSessionMap()
                .get(ConstantADM.LIST_ATTEMPTS_SESION);

        isShowFirstButtonSesion = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.SHOW_FIRST_BUTTON);
        isShowLastButtonSesion = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.SHOW_LAST_BUTTON);
        urlImgEnrolOldSesion = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.URL_IMG_OLD_SESION);
        urlImgEnrolNewSesion = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.URL_IMG_NEW_SESION);

        this.setShowFirstButton(null != isShowFirstButtonSesion
                ? new Boolean(isShowFirstButtonSesion) : Boolean.FALSE);

        this.setShowLastButton(null != isShowLastButtonSesion
                ? new Boolean(isShowLastButtonSesion) : Boolean.FALSE);

        this.setListAttemps(
                null != listAttempsSesion && !listAttempsSesion.isEmpty()
                        ? listAttempsSesion : new ArrayList<AttemptsType>());

        this.setUrlImgEnrolOld(
                null != urlImgEnrolOldSesion && !urlImgEnrolOldSesion.isEmpty()
                        ? urlImgEnrolOldSesion : ConstantADM.STRING_EMPTY);

        this.setUrlImgEnrolNew(
                null != urlImgEnrolNewSesion && !urlImgEnrolNewSesion.isEmpty()
                        ? urlImgEnrolNewSesion : ConstantADM.STRING_EMPTY);

        this.countryId = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(co.bdigital.admin.util.ConstantADM.COUNTRY_ID);

        this.currentUser = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.USER_NAME_SESION);

        permissionRol = (PermissionByRol) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.PERMISSIONS_BY_ROL_SESION);
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber
     *            the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Metodo para limpiar los campos del formulario de envio de push de
     * verificacion de Biometria.
     */
    public void cleanFormBiometryReset() {
        RequestContext.getCurrentInstance().reset("formBiometryReset");
        cleanFields();
    }

    /**
     * Accion de consulta la imagen en base64 de la imagen antigua que tiene
     * enrolada usuario
     */
    public void getUrlImageEnrolOld() {
        FacesMessage message = null;
        cleanFieldsSearch();

        if (ConstantADM.STRING_EMPTY.equals(phoneNumber)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.PHONE_NUMBER_FIELD,
                    ConstantADM.ENTER_NUMBER_PHONE);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap()
                    .put(ConstantADM.NUMBER_PHONE_SESION, phoneNumber);
            try {
                RequestMessageObjectType request = UtilADM.getRequestObject(
                        PROFILE_SERVICES_NAME,
                        ConstantADM.SERVICE_OPERATION_GET_PROFILE_BY_ID,
                        countryId, PROFILE_SERVICES_VERSION);

                RequestBody requestBody = new RequestBody();
                GetProfileByIDServiceRequestType requestType = UtilADM
                        .buildGetProfileByIDRequestType(phoneNumber);
                requestBody.setAny(requestType);
                request.getRequestMessage().setRequestBody(requestBody);

                ConnectionMDW connectionMDW = new ConnectionMDW();
                ResponseMessageObjectType responseMessageObjectType = connectionMDW
                        .callMiddleware(getMdwProviderInfo(), request,
                                PROFILE_SERVICES_NAME,
                                ConstantADM.SERVICE_OPERATION_GET_PROFILE_BY_ID);

                if (null != responseMessageObjectType) {
                    if (Constant.COMMON_STRING_ZERO
                            .equalsIgnoreCase(responseMessageObjectType
                                    .getResponseMessage().getResponseHeader()
                                    .getStatus().getStatusCode())) {

                        // Cargamos la información de la respuesta del servicio
                        GetProfileByIDServiceResponseType responseBody = (GetProfileByIDServiceResponseType) WebConsoleUtil
                                .parsePayload(
                                        responseMessageObjectType
                                                .getResponseMessage()
                                                .getResponseBody().getAny(),
                                        new GetProfileByIDServiceResponseType());

                        // Guardamos la URL de la imagen
                        if (null != responseBody
                                && null != responseBody.getGetProfileByIDRS()
                                && null != responseBody.getGetProfileByIDRS()
                                        .getFace()) {
                            this.urlImgEnrolOld = responseBody
                                    .getGetProfileByIDRS().getFace().getData()
                                    .toString();
                            urlImgEnrolOldSesion = (String) FacesContext
                                    .getCurrentInstance().getExternalContext()
                                    .getSessionMap()
                                    .put(ConstantADM.URL_IMG_OLD_SESION,
                                            urlImgEnrolOld);
                            if (null == urlImgEnrolOld
                                    || urlImgEnrolOld.trim().isEmpty()) {
                                this.setShowFirstButton(Boolean.FALSE);
                                message = UtilADM.buildErrorMessage(null,
                                        FacesMessage.SEVERITY_ERROR,
                                        ConstantADM.ERROR_GET_URL_IMG_OLD);
                                FacesContext.getCurrentInstance()
                                        .addMessage(null, message);
                            } else {
                                this.setShowFirstButton(Boolean.TRUE);
                            }
                        } else {
                            message = UtilADM.buildErrorMessage(null,
                                    FacesMessage.SEVERITY_ERROR,
                                    ConstantADM.ERROR_GET_URL_IMG_OLD);
                            FacesContext.getCurrentInstance().addMessage(null,
                                    message);
                        }

                        this.searchVeriAttempts();

                    } else {
                        if (ConstantADM.ERROR_NOT_FOUND_REGISTRY_DB.equals(
                                responseMessageObjectType.getResponseMessage()
                                        .getResponseHeader().getStatus()
                                        .getStatusCode())) {
                            message = new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    ConstantADM.WORD_ERROR
                                            + responseMessageObjectType
                                                    .getResponseMessage()
                                                    .getResponseHeader()
                                                    .getStatus()
                                                    .getStatusCode(),
                                    ConstantADM.ERROR_FOUND_REGISTRY_DB);
                        } else {
                            message = new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    ConstantADM.WORD_ERROR
                                            + responseMessageObjectType
                                                    .getResponseMessage()
                                                    .getResponseHeader()
                                                    .getStatus()
                                                    .getStatusCode(),
                                    responseMessageObjectType
                                            .getResponseMessage()
                                            .getResponseHeader().getStatus()
                                            .getStatusDesc());
                        }
                        FacesContext.getCurrentInstance().addMessage(null,
                                message);
                    }
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ConstantADM.WORD_ERROR
                                    + ConstantADM.ERROR_CODE_COMMUNICATION_MDW,
                            ConstantADM.ERROR_MESSAGE_REST_COMMUNICATION);
                }
            } catch (Exception ex) {
                logger.error(
                        UtilADM.generateStringConcatenated(
                                ConstantADM.ERROR_EXECUTE_SERVICE_REST,
                                ConstantADM.STRING_SPACE,
                                ConstantADM.SERVICE_OPERATION_GET_BIOMETRY_ON_DEMAND),
                        ex);
                message = UtilADM.buildErrorMessage(null,
                        FacesMessage.SEVERITY_ERROR,
                        ConstantADM.ERROR_GET_BIOMETRY_VERIFICATION_ON_DEMAND);
            }
        }
    }

    public void searchVeriAttempts() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                ConstantADM.WORD_SUCCESS,
                ConstantADM.COMMON_SUCCESS_LIST_ATTEMP_IMG_OLD);

        if (ConstantADM.STRING_EMPTY.equals(phoneNumber)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.PHONE_NUMBER_FIELD,
                    ConstantADM.ENTER_NUMBER_PHONE);
        } else {
            try {
                RequestMessageObjectType request = UtilADM.getRequestObject(
                        ATTEMPTS_SERVICES_NAME,
                        ConstantADM.SERVICE_OPERATION_GET_VERI_ATTEMPTS,
                        countryId, BIOMETRIY_SERVICES_VERSION);

                RequestBody requestBody = new RequestBody();
                GetVeriAttemptsServiceRequestType requestType = UtilADM
                        .buildGetVeriAttemptsServiceRequestType(phoneNumber);
                requestBody.setAny(requestType);
                request.getRequestMessage().setRequestBody(requestBody);

                ConnectionMDW connectionMDW = new ConnectionMDW();
                ResponseMessageObjectType responseMessageObjectType = connectionMDW
                        .callMiddleware(getMdwProviderInfo(), request,
                                ATTEMPTS_SERVICES_NAME,
                                ConstantADM.SERVICE_OPERATION_GET_VERI_ATTEMPTS);

                if (null != responseMessageObjectType) {
                    if (Constant.COMMON_STRING_ZERO
                            .equalsIgnoreCase(responseMessageObjectType
                                    .getResponseMessage().getResponseHeader()
                                    .getStatus().getStatusCode())) {

                        // Cargamos la información de la respuesta del servicio
                        GetVeriAttemptsServiceResponseType responseBody = (GetVeriAttemptsServiceResponseType) WebConsoleUtil
                                .parsePayload(
                                        responseMessageObjectType
                                                .getResponseMessage()
                                                .getResponseBody().getAny(),
                                        new GetVeriAttemptsServiceResponseType());

                        this.listAttemps = responseBody.getGetVeriAttemptsRS()
                                .getAttempts();

                        if (null != listAttemps && !listAttemps.isEmpty()) {

                            FacesContext.getCurrentInstance()
                                    .getExternalContext().getSessionMap()
                                    .put(ConstantADM.SHOW_FIRST_BUTTON,
                                            new Boolean(isShowFirstButton)
                                                    .toString());
                            FacesContext.getCurrentInstance()
                                    .getExternalContext().getSessionMap()
                                    .put(ConstantADM.LIST_ATTEMPTS_SESION,
                                            listAttemps);
                        }
                    } else {
                        this.setShowFirstButton(Boolean.FALSE);
                        message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                ConstantADM.WORD_ERROR
                                        + responseMessageObjectType
                                                .getResponseMessage()
                                                .getResponseHeader().getStatus()
                                                .getStatusCode(),
                                responseMessageObjectType.getResponseMessage()
                                        .getResponseHeader().getStatus()
                                        .getStatusDesc());
                    }
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ConstantADM.WORD_ERROR
                                    + ConstantADM.ERROR_CODE_COMMUNICATION_MDW,
                            ConstantADM.ERROR_MESSAGE_REST_COMMUNICATION);
                }
            } catch (Exception ex) {
                logger.error(
                        UtilADM.generateStringConcatenated(
                                ConstantADM.ERROR_EXECUTE_SERVICE_REST,
                                ConstantADM.STRING_SPACE,
                                ConstantADM.SERVICE_OPERATION_GET_BIOMETRY_ON_DEMAND),
                        ex);
                message = UtilADM.buildErrorMessage(null,
                        FacesMessage.SEVERITY_ERROR,
                        ConstantADM.ERROR_GET_BIOMETRY_VERIFICATION_ON_DEMAND);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Metodo para validar que el ultimo intento de biometria sea fallido
     * 
     * @param date
     * @return
     */
    public boolean validateLastAttemptFail(String status) {
        boolean flag = false;
        if (ConstantADM.COMPLETED_SUCCESS_STATUS.equals(status)) {
            flag = false;
        } else if (ConstantADM.COMPLETED_FAILURE_STATUS.equals(status)) {
            flag = true;
        }
        return flag;
    }

    /**
     * Metodo para enviar push de verificacion de biometria a un cliente.
     */
    public void sendPushBiometryReset() {
        FacesMessage message;
        phoneNumber = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.NUMBER_PHONE_SESION);

        if (ConstantADM.STRING_EMPTY.equals(phoneNumber)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.PHONE_NUMBER_FIELD,
                    ConstantADM.ENTER_NUMBER_PHONE);
        } else {
            try {
                RequestMessageObjectType request = UtilADM.getRequestObject(
                        DEVICE_SERVICES_NAME,
                        ConstantADM.SERVICE_OPERATION_SEND_PUSH, countryId,
                        DEVICE_SERVICES_VERSION);

                RequestBody requestBody = new RequestBody();
                SendPushServiceRequestType requestType = UtilADM
                        .buildSendPushResquestType(phoneNumber,
                                Constant.COMMON_STRING_BIO_RESET);
                requestBody.setAny(requestType);
                request.getRequestMessage().setRequestBody(requestBody);

                ConnectionMDW connectionMDW = new ConnectionMDW();
                ResponseMessageObjectType responseMessageObjectType = connectionMDW
                        .callMiddleware(getMdwProviderInfo(), request,
                                DEVICE_SERVICES_NAME,
                                ConstantADM.SERVICE_OPERATION_SEND_PUSH);

                if (null != responseMessageObjectType) {
                    if (Constant.COMMON_STRING_ZERO
                            .equalsIgnoreCase(responseMessageObjectType
                                    .getResponseMessage().getResponseHeader()
                                    .getStatus().getStatusCode())) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                ConstantADM.WORD_SUCCESS,
                                ConstantADM.SEND_PUSH_SUCCESS);
                    } else {
                        message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                ConstantADM.WORD_ERROR
                                        + responseMessageObjectType
                                                .getResponseMessage()
                                                .getResponseHeader().getStatus()
                                                .getStatusCode(),
                                responseMessageObjectType.getResponseMessage()
                                        .getResponseHeader().getStatus()
                                        .getStatusDesc());
                    }
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ConstantADM.WORD_ERROR
                                    + ConstantADM.ERROR_CODE_COMMUNICATION_MDW,
                            ConstantADM.ERROR_MESSAGE_REST_COMMUNICATION);
                }
            } catch (Exception ex) {
                logger.error(UtilADM.generateStringConcatenated(
                        ConstantADM.ERROR_EXECUTE_SERVICE_REST,
                        ConstantADM.STRING_SPACE,
                        ConstantADM.SERVICE_OPERATION_CREATE_USER), ex);
                message = UtilADM.buildErrorMessage(null,
                        FacesMessage.SEVERITY_ERROR,
                        ConstantADM.ERROR_SEND_PUSH_BIOMETRY_VERIFICATION);
            } finally {
                try {
                    auditServiceBean.insertAudit(
                            ConstantADM.ID_ACTION_BIOMETRY_VERIFICATION,
                            currentUser, UtilADM.getCurrentDate(),
                            UtilADM.createMessageAudit(null, phoneNumber));
                } catch (Exception e) {
                    logger.error(ConstantADM.ERROR_SAVE_AUDIT, e);
                }
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Metodo buscar la nueva imagen enrolada
     */
    public void refreshNewImageEnrol() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                ConstantADM.WORD_SUCCESS, ConstantADM.REFRESH_NEW_IMG_SUCCESS);
        phoneNumber = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.NUMBER_PHONE_SESION);
        String urlFromDB = Constant.COMMON_STRING_EMPTY_STRING;
        this.urlImgEnrolNew = Constant.COMMON_STRING_EMPTY_STRING;

        if (ConstantADM.STRING_EMPTY.equals(phoneNumber)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.PHONE_NUMBER_FIELD,
                    ConstantADM.ENTER_NUMBER_PHONE);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            try {
                RequestMessageObjectType request = UtilADM.getRequestObject(
                        ADMIN_SERVICES_NAME,
                        ConstantADM.SERVICE_OPERATION_GET_BIOMETRY_ON_DEMAND,
                        countryId, UTIL_SERVICES_VERSION);

                RequestBody requestBody = new RequestBody();
                GetBiometryOnDemandServiceRequestType requestType = UtilADM
                        .buildGetBiometryOnDemandResquestType(phoneNumber,
                                MAX_RESULT);
                requestBody.setAny(requestType);
                request.getRequestMessage().setRequestBody(requestBody);

                ConnectionMDW connectionMDW = new ConnectionMDW();
                ResponseMessageObjectType responseMessageObjectType = connectionMDW
                        .callMiddleware(getMdwProviderInfo(), request,
                                ADMIN_SERVICES_NAME,
                                ConstantADM.SERVICE_OPERATION_GET_BIOMETRY_ON_DEMAND);

                if (null != responseMessageObjectType) {
                    if (Constant.COMMON_STRING_ZERO
                            .equalsIgnoreCase(responseMessageObjectType
                                    .getResponseMessage().getResponseHeader()
                                    .getStatus().getStatusCode())) {

                        // Cargamos la información de la respuesta del servicio
                        GetBiometryOnDemandServiceResponseType responseBody = (GetBiometryOnDemandServiceResponseType) WebConsoleUtil
                                .parsePayload(
                                        responseMessageObjectType
                                                .getResponseMessage()
                                                .getResponseBody().getAny(),
                                        new GetBiometryOnDemandServiceResponseType());

                        // Guardamos la URL de la imagen
                        if (null != responseBody
                                && null != responseBody
                                        .getGetBiometryOnDemandRS()
                                && null != responseBody
                                        .getGetBiometryOnDemandRS().getData()
                                && !responseBody.getGetBiometryOnDemandRS()
                                        .getData().isEmpty()) {
                            urlFromDB = responseBody.getGetBiometryOnDemandRS()
                                    .getData().get(0).getUrl();
                            if (null != urlFromDB && !ConstantADM.STRING_EMPTY
                                    .equals(urlFromDB)) {
                                getBase64NewImg(urlFromDB);
                            } else {
                                message = new FacesMessage(
                                        FacesMessage.SEVERITY_ERROR,
                                        ConstantADM.WORD_ERROR,
                                        ConstantADM.NOT_FOUND_NEW_IMG_URL);
                                FacesContext.getCurrentInstance()
                                        .addMessage(null, message);
                            }
                        }

                    } else {
                        message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                ConstantADM.WORD_ERROR
                                        + responseMessageObjectType
                                                .getResponseMessage()
                                                .getResponseHeader().getStatus()
                                                .getStatusCode(),
                                responseMessageObjectType.getResponseMessage()
                                        .getResponseHeader().getStatus()
                                        .getStatusDesc());
                        FacesContext.getCurrentInstance().addMessage(null,
                                message);
                    }
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ConstantADM.WORD_ERROR
                                    + ConstantADM.ERROR_CODE_COMMUNICATION_MDW,
                            ConstantADM.ERROR_MESSAGE_REST_COMMUNICATION);
                }
            } catch (Exception ex) {
                logger.error(
                        UtilADM.generateStringConcatenated(
                                ConstantADM.ERROR_EXECUTE_SERVICE_REST,
                                ConstantADM.STRING_SPACE,
                                ConstantADM.SERVICE_OPERATION_GET_BIOMETRY_ON_DEMAND),
                        ex);
                message = UtilADM.buildErrorMessage(null,
                        FacesMessage.SEVERITY_ERROR,
                        ConstantADM.ERROR_GET_BIOMETRY_VERIFICATION_ON_DEMAND);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    /**
     * Metodo para traer un imagen de s3 en bits por medio de la URL
     * 
     * @param url
     */
    public void getBase64NewImg(String url) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                ConstantADM.WORD_SUCCESS, ConstantADM.REFRESH_NEW_IMG_SUCCESS);
        phoneNumber = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.NUMBER_PHONE_SESION);

        if (ConstantADM.STRING_EMPTY.equals(phoneNumber)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.PHONE_NUMBER_FIELD,
                    ConstantADM.ENTER_NUMBER_PHONE);
        } else {
            try {

                HashMap<String, String> getKeyAndBucketImg = getKeyAndBucketImg(
                        url);

                IntegrationRQ request = UtilADM.getRequestIntegration(
                        String.valueOf(new Date().getTime()),
                        FILE_S3_SERVICES_NAME,
                        ConstantADM.SERVICE_OPERATION_GET_FILE, countryId,
                        FILE_S3_SERVICES_VERSION);
                RequestBodyType requestBodyType = UtilADM
                        .getRequestBodyIntegratioGetFile(getKeyAndBucketImg);

                request.getIntegrationRequest().setBody(requestBodyType);

                ConnectionMDW connectionMDW = new ConnectionMDW();
                IntegrationRS responseMessageObjectType = connectionMDW
                        .callIntegrationServices(getIsAwsProviderInfo(),
                                request, FILE_S3_SERVICES_NAME,
                                ConstantADM.SERVICE_OPERATION_GET_FILE);

                if (null != responseMessageObjectType) {
                    if (Constant.COMMON_STRING_ZERO.equalsIgnoreCase(
                            responseMessageObjectType.getIntegrationResponse()
                                    .getHeader().getStatus().getCode())) {

                        // Cargamos la información de la respuesta del servicio
                        ResponseBodyType responseBody = responseMessageObjectType
                                .getIntegrationResponse().getBody();

                        if (null != responseBody
                                && null != responseBody.getGetFileResponse()) {
                            this.urlImgEnrolNew = responseBody
                                    .getGetFileResponse().getFileBase64();

                            if (null != urlImgEnrolNew
                                    && !ConstantADM.STRING_EMPTY
                                            .equals(urlImgEnrolNew)) {
                                this.isShowLastButton = true;
                                FacesContext.getCurrentInstance()
                                        .getExternalContext().getSessionMap()
                                        .put(ConstantADM.SHOW_LAST_BUTTON,
                                                new Boolean(isShowLastButton)
                                                        .toString());
                                urlImgEnrolNewSesion = (String) FacesContext
                                        .getCurrentInstance()
                                        .getExternalContext().getSessionMap()
                                        .put(ConstantADM.URL_IMG_NEW_SESION,
                                                urlImgEnrolNew);
                            } else {
                                message = new FacesMessage(
                                        FacesMessage.SEVERITY_ERROR,
                                        ConstantADM.WORD_ERROR,
                                        ConstantADM.NOT_FOUND_NEW_IMG_URL);
                            }
                        }

                    } else {
                        message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                ConstantADM.WORD_ERROR
                                        + responseMessageObjectType
                                                .getIntegrationResponse()
                                                .getHeader().getStatus()
                                                .getCode(),
                                responseMessageObjectType
                                        .getIntegrationResponse().getHeader()
                                        .getStatus().getDescription());
                    }
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ConstantADM.WORD_ERROR
                                    + ConstantADM.ERROR_CODE_COMMUNICATION_MDW,
                            ConstantADM.ERROR_MESSAGE_REST_COMMUNICATION);
                }
            } catch (Exception ex) {
                logger.error(
                        UtilADM.generateStringConcatenated(
                                ConstantADM.ERROR_EXECUTE_SERVICE_REST,
                                ConstantADM.STRING_SPACE,
                                ConstantADM.SERVICE_OPERATION_GET_BIOMETRY_ON_DEMAND),
                        ex);
                message = UtilADM.buildErrorMessage(null,
                        FacesMessage.SEVERITY_ERROR,
                        ConstantADM.ERROR_GET_BIOMETRY_VERIFICATION_ON_DEMAND);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Metodo para obtener la clave y el bucket de una imagen
     * 
     * @param url
     * @return
     */
    public HashMap<String, String> getKeyAndBucketImg(String url) {
        HashMap<String, String> map = new HashMap<String, String>();
        String[] parts = url.split(ConstantADM.WORD_SLASH_SPLIT_URL);
        map.put(ConstantADM.MAP_KEY_URL_S3, parts[parts.length - 1]);
        map.put(ConstantADM.MAP_BUCKET_URL_S3, parts[parts.length - 2]);
        return map;
    }

    /**
     * Metodo para actualizar la biometria de rostro en daon
     */
    public void updateNewBiometryApprove() {
        FacesMessage message = null;
        StatusResponse status = new StatusResponse();
        phoneNumber = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.NUMBER_PHONE_SESION);

        if (ConstantADM.STRING_EMPTY.equals(phoneNumber)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.PHONE_NUMBER_FIELD,
                    ConstantADM.ENTER_NUMBER_PHONE);
        } else {
            try {
                RequestMessageObjectType request = UtilADM.getRequestObject(
                        UPDATE_PROFILE_SERVICES_NAME,
                        ConstantADM.SERVICE_OPERATION_DAON_UPDATE_PROFILE,
                        countryId, UPDATE_PROFILE_SERVICES_VERSION);

                RequestBody requestBody = new RequestBody();
                UpdateProfileDaonServiceRequestType requestType = UtilADM
                        .buildUpdateProfileDaonRequestType(phoneNumber,
                                ConstantADM.ACTION_UPDATE_PROFILE_DAON,
                                Constant.COMMON_STRING_FALSE,
                                this.urlImgEnrolNew,
                                ConstantADM.IMG_TYPE_UPDATE_PROFILE_DAON);
                requestBody.setAny(requestType);
                request.getRequestMessage().setRequestBody(requestBody);

                ConnectionMDW connectionMDW = new ConnectionMDW();
                ResponseMessageObjectType responseMessageObjectType = connectionMDW
                        .callMiddleware(getMdwProviderInfo(), request,
                                UPDATE_PROFILE_SERVICES_NAME,
                                ConstantADM.SERVICE_OPERATION_DAON_UPDATE_PROFILE);

                if (null != responseMessageObjectType) {
                    if (Constant.COMMON_STRING_ZERO
                            .equalsIgnoreCase(responseMessageObjectType
                                    .getResponseMessage().getResponseHeader()
                                    .getStatus().getStatusCode())) {
                        boolean isBlockClient = false;
                        try {
                            isBlockClient = getBlockAdminClient(phoneNumber);
                        } catch (Exception e) {
                            logger.error(ConstantADM.ERROR_GET_CLIENT_BY_NUMBER,
                                    e);
                        }

                        if (isBlockClient) {
                            StatusType statusCustomerLock = manageCustomerLock(
                                    ConstantADM.CUSTOMER_LOCK_ACTION_DELETE,
                                    phoneNumber);
                            if (Constant.COMMON_STRING_ZERO.equals(
                                    statusCustomerLock.getStatusCode())) {
                                cleanFields();
                                message = new FacesMessage(
                                        FacesMessage.SEVERITY_INFO,
                                        ConstantADM.WORD_SUCCESS,
                                        ConstantADM.UPDATE_PROFILE_DAON_IMG);
                            } else {
                                message = new FacesMessage(
                                        FacesMessage.SEVERITY_ERROR,
                                        ConstantADM.WORD_ERROR
                                                + statusCustomerLock
                                                        .getStatusCode(),
                                        statusCustomerLock.getStatusDesc());
                            }
                        } else {
                            cleanFields();
                            message = new FacesMessage(
                                    FacesMessage.SEVERITY_INFO,
                                    ConstantADM.WORD_SUCCESS,
                                    ConstantADM.UPDATE_PROFILE_DAON_IMG);
                        }
                    } else {
                        message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                ConstantADM.WORD_ERROR
                                        + responseMessageObjectType
                                                .getResponseMessage()
                                                .getResponseHeader().getStatus()
                                                .getStatusCode(),
                                responseMessageObjectType.getResponseMessage()
                                        .getResponseHeader().getStatus()
                                        .getStatusDesc());
                    }
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ConstantADM.WORD_ERROR
                                    + ConstantADM.ERROR_CODE_COMMUNICATION_MDW,
                            ConstantADM.ERROR_MESSAGE_REST_COMMUNICATION);
                }
            } catch (Exception ex) {
                logger.error(
                        UtilADM.generateStringConcatenated(
                                ConstantADM.ERROR_EXECUTE_SERVICE_REST,
                                ConstantADM.STRING_SPACE,
                                ConstantADM.SERVICE_OPERATION_GET_BIOMETRY_ON_DEMAND),
                        ex);
                message = UtilADM.buildErrorMessage(status,
                        FacesMessage.SEVERITY_ERROR,
                        ConstantADM.ERROR_GET_BIOMETRY_VERIFICATION_ON_DEMAND);
            } finally {
                try {
                    auditServiceBean.insertAudit(ConstantADM.ID_RESET_BIOMETRIA,
                            currentUser, UtilADM.getCurrentDate(),
                            UtilADM.createMessageAudit(status, phoneNumber
                                    + ConstantADM.DESC_ACTION_UPDATE_BIOMETRIA));
                } catch (Exception e) {
                    logger.error(ConstantADM.ERROR_SAVE_AUDIT, e);
                }
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Metodo para saber si el usuario tiene bloqueo administrativo o no
     * 
     * @param phoneNumber
     * @return
     * @throws Exception
     */
    public boolean getBlockAdminClient(String phoneNumber) throws Exception {
        Cliente cliente = null;
        boolean isBlock = false;
        cliente = clientInfoServiceBean.getClientByPhoneNumber(phoneNumber);
        if (cliente != null && null != cliente.getBloqueoUsuarios()
                && !cliente.getBloqueoUsuarios().isEmpty()) {
            List<BloqueoUsuario> listBlock = cliente.getBloqueoUsuarios();
            for (BloqueoUsuario bloqueoUsuario : listBlock) {
                if (ConstantADM.CUSTOMER_LOCK_TYPE
                        .equals(bloqueoUsuario.getId().getTipoBloqueo())) {
                    isBlock = true;
                    break;
                }
            }
        }
        return isBlock;
    }

    /**
     * Metodo para resetear la biometria cuando el usuario no esta aprovisionado
     */
    public void resetWithoutOTP() {
        FacesMessage message = null;
        StatusType status = new StatusType();
        phoneNumber = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.NUMBER_PHONE_SESION);
        if (ConstantADM.STRING_EMPTY.equals(phoneNumber)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.PHONE_NUMBER_FIELD,
                    ConstantADM.ENTER_NUMBER_PHONE);
        } else {
            try {
                status = manageCustomerLock(
                        ConstantADM.CUSTOMER_LOCK_ACTION_CREATE, phoneNumber);
                if (Constant.COMMON_STRING_ZERO
                        .equals(status.getStatusCode())) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            ConstantADM.WORD_SUCCESS,
                            ConstantADM.RESET_WITHOUT_OTP_SUCCESS);
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ConstantADM.WORD_ERROR + status.getStatusCode(),
                            status.getStatusDesc());
                }
            } catch (Exception e) {
                logger.error(
                        UtilADM.generateStringConcatenated(
                                ConstantADM.ERROR_EXECUTE_SERVICE_REST,
                                ConstantADM.STRING_SPACE,
                                ConstantADM.SERVICE_OPERATION_CUSTOMER_LOCK),
                        e);
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        ConstantADM.WORD_ERROR,
                        UtilADM.generateStringConcatenated(
                                ConstantADM.ERROR_EXECUTE_SERVICE_REST,
                                ConstantADM.STRING_SPACE,
                                ConstantADM.SERVICE_OPERATION_CUSTOMER_LOCK));
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Metodo para gestionar los bloqueos administrativos, sea para bloquear o
     * desbloquear
     * 
     * @param action
     * @param phone
     * @return
     */
    public StatusType manageCustomerLock(String action, String phone) {
        StatusType status = new StatusType();

        try {
            RequestMessageObjectType request = UtilADM.getRequestObject(
                    USER_SERVICES_NAME,
                    ConstantADM.SERVICE_OPERATION_CUSTOMER_LOCK, countryId,
                    USER_SERVICES_VERSION);

            RequestBody requestBody = new RequestBody();
            CustomerLockManagmentServiceRequestType requestType = UtilADM
                    .buildCustomerLockManagmentResquestType(phone,
                            ConstantADM.CUSTOMER_LOCK_TYPE, action);
            requestBody.setAny(requestType);
            request.getRequestMessage().setRequestBody(requestBody);

            ConnectionMDW connectionMDW = new ConnectionMDW();
            ResponseMessageObjectType responseMessageObjectType = connectionMDW
                    .callMiddleware(getMdwProviderInfo(), request,
                            USER_SERVICES_NAME,
                            ConstantADM.SERVICE_OPERATION_CUSTOMER_LOCK);

            status = responseMessageObjectType.getResponseMessage()
                    .getResponseHeader().getStatus();
        } catch (Exception ex) {
            logger.error(UtilADM.generateStringConcatenated(
                    ConstantADM.ERROR_EXECUTE_SERVICE_REST,
                    ConstantADM.STRING_SPACE,
                    ConstantADM.SERVICE_OPERATION_CREATE_USER), ex);
            status.setStatusCode(ConstantADM.WORD_ERROR);
            status.setStatusDesc(ex.getMessage());
        } finally {
            try {
                auditServiceBean.insertAudit(ConstantADM.ID_RESET_BIOMETRIA,
                        currentUser, UtilADM.getCurrentDate(),
                        UtilADM.createMessageAudit(new StatusResponse(),
                                phoneNumber
                                        + ConstantADM.DESC_ACTION_BLOCK_USER));
            } catch (Exception e) {
                logger.error(ConstantADM.ERROR_SAVE_AUDIT, e);
            }
        }
        return status;
    }

    /**
     * 
     */
    public void blockClient() {
        FacesMessage message = null;
        StatusResponse status = new StatusResponse();
        phoneNumber = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.NUMBER_PHONE_SESION);
        if (ConstantADM.STRING_EMPTY.equals(phoneNumber)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.PHONE_NUMBER_FIELD,
                    ConstantADM.ENTER_NUMBER_PHONE);
        } else {
            try {
                RequestMessageObjectType request = UtilADM.getRequestObject(
                        USER_SERVICES_NAME,
                        ConstantADM.SERVICE_OPERATION_BLOCK_CLIENT, countryId,
                        USER_SERVICES_VERSION);

                RequestBody requestBody = new RequestBody();
                BlockClientServiceRequestType requestType = UtilADM
                        .buildBlockClientRequestType(phoneNumber,
                                ConstantADM.BLOCK_CLIENT_PANIC);
                requestBody.setAny(requestType);
                request.getRequestMessage().setRequestBody(requestBody);

                ConnectionMDW connectionMDW = new ConnectionMDW();
                ResponseMessageObjectType responseMessageObjectType = connectionMDW
                        .callMiddleware(getMdwProviderInfo(), request,
                                USER_SERVICES_NAME,
                                ConstantADM.SERVICE_OPERATION_BLOCK_CLIENT);

                if (null != responseMessageObjectType) {
                    if (Constant.COMMON_STRING_ZERO
                            .equalsIgnoreCase(responseMessageObjectType
                                    .getResponseMessage().getResponseHeader()
                                    .getStatus().getStatusCode())) {
                        cleanFields();
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                ConstantADM.WORD_SUCCESS,
                                ConstantADM.BLOCK_CLIENT_PANIC_SUCCESS);
                    } else {
                        message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                ConstantADM.WORD_ERROR
                                        + responseMessageObjectType
                                                .getResponseMessage()
                                                .getResponseHeader().getStatus()
                                                .getStatusCode(),
                                responseMessageObjectType.getResponseMessage()
                                        .getResponseHeader().getStatus()
                                        .getStatusDesc());
                    }
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ConstantADM.WORD_ERROR
                                    + ConstantADM.ERROR_CODE_COMMUNICATION_MDW,
                            ConstantADM.ERROR_MESSAGE_REST_COMMUNICATION);
                }
            } catch (Exception ex) {
                logger.error(UtilADM.generateStringConcatenated(
                        ConstantADM.ERROR_EXECUTE_SERVICE_REST,
                        ConstantADM.STRING_SPACE,
                        ConstantADM.SERVICE_OPERATION_CREATE_USER), ex);
                status.setStatusCode(ConstantADM.WORD_ERROR);
                status.setStatusDesc(ex.getMessage());
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Metodo para limpiar las variables con informacion de la transaccion
     */
    public void cleanFields() {
        this.listAttemps = new ArrayList<AttemptsType>();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put(ConstantADM.LIST_ATTEMPTS_SESION, listAttemps);
        this.urlImgEnrolNew = "";
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put(ConstantADM.URL_IMG_NEW_SESION, urlImgEnrolNew);
        this.urlImgEnrolOld = "";
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put(ConstantADM.URL_IMG_OLD_SESION, urlImgEnrolOld);
        this.isShowFirstButton = false;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put(ConstantADM.SHOW_FIRST_BUTTON, Boolean.FALSE.toString());
        this.isShowLastButton = false;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put(ConstantADM.SHOW_LAST_BUTTON, Boolean.FALSE.toString());
        this.phoneNumber = "";
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put(ConstantADM.NUMBER_PHONE_SESION, phoneNumber);
    }

    /**
     * Metodo para limpiar las variables con informacion de la transaccion
     */
    public void cleanFieldsSearch() {
        this.listAttemps = new ArrayList<AttemptsType>();
        this.urlImgEnrolNew = "";
        this.urlImgEnrolOld = "";
        this.isShowFirstButton = false;
        this.isShowLastButton = false;
    }

    /**
     * <p>
     * M&eacute;todo que carga los datos de conexi&oacute;n para los servicios
     * de MDW.
     * </p>
     * 
     * @return WsProvider
     */
    private WsProvider getMdwProviderInfo() {
        if (null == this.mdwProviderInfo) {
            this.mdwProviderInfo = this.parametersServiceBean
                    .getMdwServicesEndPoint(Constant.COMMON_SYSTEM_MDW);
        }
        return this.mdwProviderInfo;
    }

    /**
     * <p>
     * Metodo que carga los datos de conexion para los servicios de Integracion
     * de MDW.
     * </p>
     * 
     * @return WsProvider
     */
    private WsProvider getIsAwsProviderInfo() {
        if (null == this.isAwsProviderInfo) {
            this.isAwsProviderInfo = this.parametersServiceBean
                    .getMdwServicesEndPoint(
                            ConstantADM.COMMON_SYSTEM_INTEGRATION);
        }
        return this.isAwsProviderInfo;
    }

    /**
     * @return the urlImgEnrolOld
     */
    public String getUrlImgEnrolNew() {
        return urlImgEnrolNew;
    }

    /**
     * @param urlImgEnrolOld
     *            the urlImgEnrolOld to set
     */
    public void setUrlImgEnrolNew(String urlImgEnrolNew) {
        this.urlImgEnrolNew = urlImgEnrolNew;
    }

    /**
     * @return the listAttemps
     */
    public List<AttemptsType> getListAttemps() {
        return listAttemps;
    }

    /**
     * @param listAttemps
     *            the listAttemps to set
     */
    public void setListAttemps(List<AttemptsType> listAttemps) {
        this.listAttemps = listAttemps;
    }

    /**
     * @return the isShowFirstButton
     */
    public boolean isShowFirstButton() {
        return isShowFirstButton;
    }

    /**
     * @param isShowFirstButton
     *            the isShowFirstButton to set
     */
    public void setShowFirstButton(boolean isShowFirstButton) {
        this.isShowFirstButton = isShowFirstButton;
    }

    /**
     * @return the urlImgEnrolOld
     */
    public String getUrlImgEnrolOld() {
        return urlImgEnrolOld;
    }

    /**
     * @param urlImgEnrolOld
     *            the urlImgEnrolOld to set
     */
    public void setUrlImgEnrolOld(String urlImgEnrolOld) {
        this.urlImgEnrolOld = urlImgEnrolOld;
    }

    /**
     * @return the isShowLastButton
     */
    public boolean isShowLastButton() {
        return isShowLastButton;
    }

    /**
     * @param isShowLastButton
     *            the isShowLastButton to set
     */
    public void setShowLastButton(boolean isShowLastButton) {
        this.isShowLastButton = isShowLastButton;
    }

    /**
     * @return the permissionRol
     */
    public PermissionByRol getPermissionRol() {
        return permissionRol;
    }

    /**
     * @param permissionRol
     *            the permissionRol to set
     */
    public void setPermissionRol(PermissionByRol permissionRol) {
        this.permissionRol = permissionRol;
    }

}
