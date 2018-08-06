package co.bdigital.admin.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.nequi.cmm.messaging.general.ResponseMessageObjectType;

import co.bdigital.admin.ejb.controller.AuditServiceBean;
import co.bdigital.admin.ejb.controller.ParametersServiceBean;
import co.bdigital.admin.messaging.services.device.sendpush.SendPushServiceRequestType;
import co.bdigital.admin.messaging.services.getbiometryondemand.DataType;
import co.bdigital.admin.messaging.services.getbiometryondemand.GetBiometryOnDemandServiceRequestType;
import co.bdigital.admin.messaging.services.getbiometryondemand.GetBiometryOnDemandServiceResponseType;
import co.bdigital.admin.model.PermissionByRol;
import co.bdigital.admin.model.StatusResponse;
import co.bdigital.admin.util.ConnectionMDW;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.UtilADM;
import co.bdigital.admin.util.WebConsoleUtil;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.model.framework.WsProvider;
import co.bdigital.cmm.messaging.general.RequestBody;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.shl.tracer.CustomLogger;

/**
 *
 * @author hansel.ospino
 */
@ManagedBean(name = ConstantADM.BIOMETRY_VERIFICATION_MANAGED_BEAN)
@SessionScoped
public class BiometryVerificationMGBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Numero del celular para enviar push de verificacion de biometria.
     **/
    private String phoneNumber;
    /** Codigo del pais **/
    private String countryId;
    /** Logger **/
    private CustomLogger logger;
    /** Informacion de los servicios a consumir **/
    private static final String DEVICE_SERVICES_NAME = "DeviceServices";
    private static final String DEVICE_SERVICES_VERSION = "1.0.0";
    private static final String ADMIN_SERVICES_NAME = "AdminServices";
    private static final String UTIL_SERVICES_VERSION = "1.0.0";
    private static final String MAX_RESULT = "0";
    /** Usuario en sesion **/
    private String currentUser;
    /** Permisos de la accion **/
    private PermissionByRol permissionRol;
    @EJB
    private transient AuditServiceBean auditServiceBean;
    /**
     * Listado de detalles de verificación de biometría.
     */
    private List<DataType> biometryVerificationList;
    @EJB
    private transient ParametersServiceBean parametersServiceBean;
    /**
     * Objeto con los datos del endpoint de MDW.
     */
    private WsProvider mdwProviderInfo;

    public BiometryVerificationMGBean() {
        logger = new CustomLogger(this.getClass());
        this.countryId = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(co.bdigital.admin.util.ConstantADM.COUNTRY_ID);
        this.currentUser = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.USER_NAME_SESION);
        this.biometryVerificationList = new ArrayList<DataType>();
        permissionRol = (PermissionByRol) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.PERMISSIONS_BY_ROL_SESION);
    }

    /**
     * Metodo para enviar push de verificacion de biometria a un cliente.
     */
    public void sendPushBiometryVerification() {
        FacesMessage message;

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
                                Constant.COMMON_STRING_BIO);
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
                                        + (!responseMessageObjectType
                                                .getResponseMessage()
                                                .getResponseHeader().getStatus()
                                                .getStatusCode().isEmpty()
                                                        ? responseMessageObjectType
                                                                .getResponseMessage()
                                                                .getResponseHeader()
                                                                .getStatus()
                                                                .getStatusCode()
                                                        : ConstantADM.ERROR_CODE_COMMUNICATION_MDW),
                                (!responseMessageObjectType.getResponseMessage()
                                        .getResponseHeader().getStatus()
                                        .getStatusCode().isEmpty()
                                                ? responseMessageObjectType
                                                        .getResponseMessage()
                                                        .getResponseHeader()
                                                        .getStatus()
                                                        .getStatusDesc()
                                                : ConstantADM.ERROR_MESSAGE_REST_COMMUNICATION));
                    }
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ConstantADM.WORD_ERROR
                                    + ConstantADM.ERROR_CODE_COMMUNICATION_MDW,
                            ConstantADM.ERROR_MESSAGE_REST_COMMUNICATION);
                }
            } catch (Exception ex) {
                logger.error(
                        ConstantADM.ERROR_EXECUTE_SERVICE_REST + " "
                                + ConstantADM.SERVICE_OPERATION_CREATE_USER,
                        ex);
                message = UtilADM.buildErrorMessage(null,
                        FacesMessage.SEVERITY_ERROR,
                        ConstantADM.ERROR_SEND_PUSH_BIOMETRY_VERIFICATION);
            } finally {
                try {
                    auditServiceBean.insertAudit(
                            ConstantADM.ID_ACTION_BIOMETRY_VERIFICATION,
                            currentUser, UtilADM.getCurrentDate(),
                            UtilADM.createMessageAudit(new StatusResponse(),
                                    phoneNumber));
                } catch (Exception e) {
                    logger.error(ConstantADM.ERROR_SAVE_AUDIT, e);
                }
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Metodo para limpiar los campos del formulario de envio de push de
     * verificacion de Biometria.
     */
    public void cleanFormBiometryVerification() {
        RequestContext.getCurrentInstance().reset("formBiometryVerification");
        getBiometryVerificationList().clear();
        this.setPhoneNumber(ConstantADM.STRING_EMPTY);

    }

    /**
     * Accion de consulta de listado de detalles de verificación de biometria.
     */
    public void biometryVerificationList() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                ConstantADM.WORD_SUCCESS,
                ConstantADM.COMMON_SUCCESS_DEFAULT_MESSAGE);

        if (ConstantADM.STRING_EMPTY.equals(phoneNumber)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.PHONE_NUMBER_FIELD,
                    ConstantADM.ENTER_NUMBER_PHONE);
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

                        this.biometryVerificationList.clear();

                        if (null != responseBody.getGetBiometryOnDemandRS()) {
                            this.biometryVerificationList = responseBody
                                    .getGetBiometryOnDemandRS().getData();
                        }

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

                    }
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ConstantADM.WORD_ERROR
                                    + ConstantADM.ERROR_CODE_COMMUNICATION_MDW,
                            ConstantADM.ERROR_MESSAGE_REST_COMMUNICATION);
                }
            } catch (Exception ex) {
                logger.error(
                        ConstantADM.ERROR_EXECUTE_SERVICE_REST + " "
                                + ConstantADM.SERVICE_OPERATION_GET_BIOMETRY_ON_DEMAND,
                        ex);
                message = UtilADM.buildErrorMessage(null,
                        FacesMessage.SEVERITY_ERROR,
                        ConstantADM.ERROR_GET_BIOMETRY_VERIFICATION_ON_DEMAND);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
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
     * @return the biometryVerificationList
     */
    public List<DataType> getBiometryVerificationList() {
        return biometryVerificationList;
    }

    /**
     * @param biometryVerificationList
     *            the biometryVerificationList to set
     */
    public void setBiometryVerificationList(
            List<DataType> biometryVerificationList) {
        this.biometryVerificationList = biometryVerificationList;
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
