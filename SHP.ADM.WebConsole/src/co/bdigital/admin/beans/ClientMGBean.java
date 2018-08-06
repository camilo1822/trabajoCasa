package co.bdigital.admin.beans;

import java.awt.Desktop.Action;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.RegexValidator;

import co.bdigital.admin.ejb.controller.ParametersServiceBean;
import co.bdigital.admin.ejb.controller.UnLockClientFinacle;
import co.bdigital.admin.ejb.controller.view.ClientInfoServiceBeanLocal;
import co.bdigital.admin.ejb.controller.view.CommonServiceBeanLocal;
import co.bdigital.admin.messaging.services.getcustomerlocks.GetCustomerLocksServiceRequestType;
import co.bdigital.admin.messaging.services.getcustomerlocks.GetCustomerLocksServiceResponseType;
import co.bdigital.admin.messaging.services.getcustomerlocks.LockType;
import co.bdigital.admin.messaging.services.isb.UnfreezeAccountRQ;
import co.bdigital.admin.messaging.services.isb.UnfreezeAccountRequestType;
import co.bdigital.admin.messaging.services.updateProfileStatus.UpdateProfileStatusRequestType;
import co.bdigital.admin.messaging.services.updateProfileStatus.UpdateProfileStatusResponseType;
import co.bdigital.admin.model.ClientBlock;
import co.bdigital.admin.model.PermissionByRol;
import co.bdigital.admin.model.StatusResponse;
import co.bdigital.admin.util.ConnectionMDW;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.UtilADM;
import co.bdigital.admin.util.WebConsoleUtil;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.cmm.jpa.model.Parametro;
import co.bdigital.cmm.jpa.model.framework.WsProvider;
import co.bdigital.cmm.messaging.general.RequestBody;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;
import co.bdigital.shl.tracer.CustomLogger;

/**
 * 
 * @author juan.molinab
 *
 */
@ManagedBean(name = "clientMGBean")
@SessionScoped
public class ClientMGBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /** Numero del documento para buscar el usuario **/
    private String numberDocument;
    /** Tipo de documento con el que se realizara la busqueda **/
    private String documentType;
    /** Lista para almacenar el usuario que se va a buscar **/
    private List<Cliente> userSearch;
    /** Usuario seleccionado para editar **/
    private Cliente selectedUser;
    /** Lista de los posibles estados de un usuario **/
    private List<SelectItem> selectOneItemTypeId;
    /** EJB para buscar el cliente y actualizarlo **/
    @EJB
    private transient ClientInfoServiceBeanLocal clientInfoServiceBean;
    /** Codigo del pais **/
    private String countryId;
    /** Permisos de la accion **/
    private PermissionByRol permissionRol;
    /** Lista de los bloqueos de un usuario **/
    private List<ClientBlock> blocksUser;

    /** Lista bloqueos que se permiten desbloquear desde finacle **/
    private List<Parametro> unLockCodeFinacleList;

    private WsProvider mdwProviderInfo;

    @EJB
    private transient ParametersServiceBean parametersServiceBean;

    @EJB
    private transient UnLockClientFinacle unLockClientFinacle;

    private CustomLogger logger;
    @EJB
    private transient CommonServiceBeanLocal commonServiceBean;

    public ClientMGBean() {
        logger = new CustomLogger(this.getClass());

        selectedUser = new Cliente();
        this.countryId = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.COUNTRY_ID);
        permissionRol = (PermissionByRol) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.PERMISSIONS_BY_ROL_SESION);

    }

    public String getNumberDocument() {
        return numberDocument;
    }

    public void setNumberDocument(String numberDocument) {
        this.numberDocument = numberDocument;
    }

    public void searchUser() {
        userSearch = searchUser(this.numberDocument);
    }

    public List<Cliente> getUserSearch() {
        return userSearch;
    }

    public void setUserSearch(List<Cliente> userSearch) {
        this.userSearch = userSearch;
    }

    public Cliente getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Cliente selectedUser) {
        this.selectedUser = selectedUser;
        this.blocksUser = getListClientBlock(this.selectedUser.getCelular());

    }

    /**
     * Metodo para retornar la lista de tipos de documentos.
     * 
     * @return lista de tipos de documentos.
     */
    private List<Parametro> documentTypes() {
        List<Parametro> documentTypes = null;
        try {
            documentTypes = this.commonServiceBean.getParameters(
                    ConstantADM.TYPE_PARAMETER_ID_DOCUMENT_TYPES, countryId);
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_GET_LIST_DOCUMENT_TYPES, e);
        }
        return documentTypes;
    }

    /**
     * @return the typeId
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * @param typeId
     *            the typeId to set
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    /**
     * @return the countryId
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * @param countryId
     *            the countryId to set
     */
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    /**
     * Metodo para limpiar los campos del formulario de busqueda
     */
    public void cleanSearch() {
        userSearch = null;
        numberDocument = null;
        documentType = null;
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

    /**
     * @return the blocksUser
     */
    public List<ClientBlock> getBlocksUser() {
        return blocksUser;
    }

    /**
     * @param blocksUser
     *            the blocksUser to set
     */
    public void setBlocksUser(List<ClientBlock> blocksUser) {
        this.blocksUser = blocksUser;
    }

    public List<Parametro> getUnLockCodeFinacleList() {
        return unLockCodeFinacleList;
    }

    public void setUnLockCodeFinacleList(
            List<Parametro> unLockCodeFinacleList) {
        this.unLockCodeFinacleList = unLockCodeFinacleList;
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
     * Metodo para buscar un cliente por el numero de documento
     * 
     * @param numberDocument
     * @return El cliente con sus datos
     */
    public List<Cliente> searchUser(String numberDocument) {
        List<Cliente> user = new ArrayList<Cliente>();
        Cliente userSearch = clientInfoServiceBean.getClientByNumDoc(
                numberDocument, documentType, this.countryId);

        if (null != userSearch) {
            user.add(userSearch);
        } else {
            FacesMessage message;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    ConstantADM.INVALID_USER, ConstantADM.USER_NOT_FOUND);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return user;
    }

    /**
     * Metodo para actualizar los datos del cliente
     * 
     * @param actionEvent
     */
    public void btnUpdateUser(Action actionEvent) {

        if (null != selectedUser) {
            StatusResponse response = clientInfoServiceBean
                    .updateClientInfo(selectedUser, this.countryId);
            FacesMessage message;
            if (ConstantADM.STATUS_CODE_SUCCESS
                    .equals(response.getStatusCode())) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantADM.PROCESS_SUCCESS, ConstantADM.USER_UPDATED);
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        response.getStatusCode(), response.getStatusDesc());
            }
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    /**
     * Metodo para devolver a la vista los tipos de documento con su codigo
     * 
     * @return the selectOneItemTypeId
     */
    public List<SelectItem> getSelectOneItemTypeId() {
        this.selectOneItemTypeId = new ArrayList<SelectItem>();
        List<Parametro> documentTypes = this.documentTypes();

        if (null != documentTypes && !documentTypes.isEmpty()) {
            for (Parametro documentType : documentTypes) {
                SelectItem selectItemDocumentType = new SelectItem(
                        documentType.getNombre(), documentType.getValor());
                this.selectOneItemTypeId.add(selectItemDocumentType);
            }
        }
        return this.selectOneItemTypeId;
    }

    /**
     * Accion de consulta de listado de los bloqueos del usuario.
     */
    public List<ClientBlock> getListClientBlock(String phoneNumber) {

        List<ClientBlock> listLocks = new ArrayList<ClientBlock>();

        try {
            RequestMessageObjectType request = UtilADM.getRequestObject(
                    ConstantADM.USER_SERVICES_LOCKUSER_NAME,
                    ConstantADM.SERVICE_OPERATION_GET_CUSTOMER_LOCKS, countryId,
                    ConstantADM.UTIL_SERVICES_VERSION_1_0_0);

            RequestBody requestBody = new RequestBody();

            GetCustomerLocksServiceRequestType requestType = UtilADM
                    .buildGetCustomerLocksServiceRequestType(phoneNumber);

            requestBody.setAny(requestType);
            request.getRequestMessage().setRequestBody(requestBody);

            ConnectionMDW connectionMDW = new ConnectionMDW();
            com.nequi.cmm.messaging.general.ResponseMessageObjectType responseMessageObjectType = connectionMDW
                    .callMiddleware(getMdwProviderInfo(), request,
                            ConstantADM.USER_SERVICES_LOCKUSER_NAME,
                            ConstantADM.SERVICE_OPERATION_GET_CUSTOMER_LOCKS);

            if (null != responseMessageObjectType) {
                if (Constant.COMMON_STRING_ZERO
                        .equalsIgnoreCase(responseMessageObjectType
                                .getResponseMessage().getResponseHeader()
                                .getStatus().getStatusCode())) {

                    getUnLockCodeFinacle();

                    // Cargamos la información de la respuesta del servicio
                    GetCustomerLocksServiceResponseType responseBody = (GetCustomerLocksServiceResponseType) WebConsoleUtil
                            .parsePayload(
                                    responseMessageObjectType
                                            .getResponseMessage()
                                            .getResponseBody().getAny(),
                                    new GetCustomerLocksServiceResponseType());

                    if (null != responseBody.getGetCustomerLocksRS()) {
                        List<LockType> locks = responseBody
                                .getGetCustomerLocksRS().getLocks();
                        if (null != locks) {
                            String unlock = null;
                            for (LockType lockType : locks) {
                                ClientBlock clientBlock = new ClientBlock();
                                clientBlock.setTipoBloqueo(lockType.getId());
                                clientBlock.setFechaBloqueo(lockType.getDate());
                                clientBlock.setDesc(lockType.getDescription());
                                clientBlock.setTypeLock(lockType.getTypeLock());

                                unlock = findLockCode(unLockCodeFinacleList,
                                        lockType.getId(),
                                        clientBlock.getTypeLock());

                                clientBlock.setUnLock(unlock);

                                listLocks.add(clientBlock);

                            }
                        }

                    }
                }
            }
        } catch (Exception ex) {
            logger.error(ConstantADM.ERROR_GET_LIST_BLOCK, ex);
        }
        return listLocks;
    }

    /**
     * Método que consulta los códigos de bloqueo que permiten desbloquearse en
     * finacle
     */
    private void getUnLockCodeFinacle() {
        if (null == unLockCodeFinacleList || unLockCodeFinacleList.isEmpty()) {
            this.unLockCodeFinacleList = this.parametersServiceBean
                    .getRegionParameter(
                            ConstantADM.UNLOCK_FINACLE_TYPE_PARAMETER_ID,
                            countryId);
        }

    }

    /**
     * Métod que verifica en una lista de parámetros(Códigos que puedes ser
     * desbloqueados en finacle) si existe un código registrado
     * 
     * @param listParameter
     * @param code
     * @param typeLock
     * @return
     */
    private String findLockCode(List<Parametro> listParameter, String code,
            String typeLock) {
        if (null != listParameter && !listParameter.isEmpty()) {

            /*
             * Si es un bloqueo de finacle se valida solamente que contenga el
             * <code>ReasonCode<code>, ya que el servicio que consulta los
             * bloqueos en finacle retorna el ReasonCode y el statusCode
             * concatenados
             */
            if (ConstantADM.COMMON_STRING_LOCK_FINACLE
                    .equalsIgnoreCase(typeLock)) {
                for (Parametro p : listParameter) {
                    if (code.contains(p.getValor())) {
                        return ConstantADM.COMMON_STRING_TRUE;
                    }
                }
            } else {
                for (Parametro p : listParameter) {
                    if (p.getValor().equalsIgnoreCase(code)) {
                        return ConstantADM.COMMON_STRING_TRUE;
                    }
                }
            }
        }
        return ConstantADM.COMMON_STRING_FALSE;
    }

    /**
     * Este método permite levantar el bloqueo de un cliente
     * 
     */
    public void unLock(int index) {

        /* Se valida el tipo de bloqueo para llamar al servicio respectivo */
        if (ConstantADM.COMMON_STRING_LOCK_FINACLE
                .equalsIgnoreCase(this.blocksUser.get(index).getTypeLock())) {
            unLockFinacle(index);
        } else if (ConstantADM.COMMON_STRING_LOCK_DB
                .equalsIgnoreCase(this.blocksUser.get(index).getTypeLock())) {
            unLockDB(index);
        }

    }

    /**
     * Método que llama a servicio para levantar bloqueo de base de datos
     * 
     * @param index
     */
    private void unLockDB(int index) {
        FacesMessage message = null;

        try {
            RequestMessageObjectType request = UtilADM.getRequestObject(
                    ConstantADM.USER_SERVICES_PROFILE_NAME,
                    ConstantADM.SERVICE_OPERATION_UPDATE_PROFILE_STATUS,
                    countryId, ConstantADM.UTIL_SERVICES_VERSION_1_1_0);

            RequestBody requestBody = new RequestBody();

            UpdateProfileStatusRequestType requestType = UtilADM
                    .buildUpdateProfileStatusRequestType(
                            this.selectedUser.getCelular(),
                            ConstantADM.COMMON_STRING_FALSE);

            requestBody.setAny(requestType);
            request.getRequestMessage().setRequestBody(requestBody);

            ConnectionMDW connectionMDW = new ConnectionMDW();
            com.nequi.cmm.messaging.general.ResponseMessageObjectType responseMessageObjectType = connectionMDW
                    .callMiddleware(getMdwProviderInfo(), request,
                            ConstantADM.USER_SERVICES_PROFILE_NAME,
                            ConstantADM.SERVICE_OPERATION_UPDATE_PROFILE_STATUS);

            if (null != responseMessageObjectType) {
                if (Constant.COMMON_STRING_ZERO
                        .equalsIgnoreCase(responseMessageObjectType
                                .getResponseMessage().getResponseHeader()
                                .getStatus().getStatusCode())) {

                    // Cargamos la información de la respuesta del servicio
                    UpdateProfileStatusResponseType responseBody = (UpdateProfileStatusResponseType) WebConsoleUtil
                            .parsePayload(
                                    responseMessageObjectType
                                            .getResponseMessage()
                                            .getResponseBody().getAny(),
                                    new UpdateProfileStatusResponseType());

                    // Se remueve el registro de la pantalla
                    this.blocksUser.remove(index);

                    message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            ConstantADM.TITLE_UNLOCK_SUCCESS,
                            ConstantADM.MESSAGE_UNLOCK_SUCCESS);

                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ConstantADM.TITLE_UNLOCK_ERROR,
                            ConstantADM.MESSAGE_UNLOCK_ERROR);

                }
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        ConstantADM.WORD_ERROR
                                + ConstantADM.ERROR_CODE_COMMUNICATION_MDW,
                        ConstantADM.ERROR_MESSAGE_REST_COMMUNICATION);
            }
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception ex) {
            logger.error(ConstantADM.ERROR_GET_LIST_BLOCK, ex);
        }
    }

    /**
     * Método que llama a servicio para levantar bloqueo de finacle
     * 
     * @param index
     */
    private void unLockFinacle(int index) {
        /* Informacion de los servicios a consumir */
        final String SERVICE_NAME = "AccountServices";
        final String SERVICE_VERSION = "1.0";

        FacesMessage message = null;
        try {
            RequestMessageObjectType request = UtilADM.getRequestObject(
                    SERVICE_NAME, ConstantADM.SERVICE_OPERATION_UPDATE_USER,
                    this.countryId, SERVICE_VERSION);

            RequestBody requestBody = new RequestBody();

            UnfreezeAccountRequestType unfreezeAccountRequestType = new UnfreezeAccountRequestType();
            UnfreezeAccountRQ unfreezeAccountRQ = new UnfreezeAccountRQ();

            String reasonCode = this.blocksUser.get(index).getTipoBloqueo();
            reasonCode = reasonCode.split(ConstantADM.COMMON_STRING_DASH)[0];

            unfreezeAccountRQ
                    .setAccountNumber(this.selectedUser.getNumeroCuenta());
            unfreezeAccountRQ.setReasonCode(reasonCode);

            unfreezeAccountRequestType.setUnfreezeAccountRQ(unfreezeAccountRQ);

            requestBody.setAny(unfreezeAccountRequestType);
            request.getRequestMessage().setRequestBody(requestBody);

            ResponseMessageObjectType response = unLockClientFinacle
                    .executeOperation(request);

            StatusResponse status = WebConsoleUtil.getResponseLocal(response);

            if (Constant.COMMON_STRING_ZERO.equals(status.getStatusCode())) {

                // Se remueve el registro de la pantalla
                this.blocksUser.remove(index);

                message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantADM.TITLE_UNLOCK_SUCCESS,
                        ConstantADM.MESSAGE_UNLOCK_SUCCESS);
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        ConstantADM.TITLE_UNLOCK_ERROR,
                        ConstantADM.MESSAGE_UNLOCK_ERROR);
            }

            FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (Exception ex) {
            StringBuilder sb = new StringBuilder();
            sb.append(ConstantADM.ERROR_EXECUTE_SERVICE_REST);
            sb.append(ConstantADM.STRING_SPACE);
            sb.append(ConstantADM.SERVICE_OPERATION_UPDATE_USER);

            logger.error(sb.toString(), ex);
        }
    }

    /**
     * Metodo para validar el documento de identidad de cada pais
     * 
     * @param context
     * @param component
     * @param value
     */
    public void validateDocument(FacesContext context, UIComponent component,
            Object value) {
        RegexValidator regexValidator;
        if (ConstantADM.COMMON_STRING_REGION_COL.equals(this.countryId)) {
            regexValidator = new RegexValidator();
            regexValidator.setPattern(
                    ConstantADM.REGULAR_EXPRESION_COLOMBIA_DOCUMENT);
            regexValidator.validate(context, component, value);
        } else {
            regexValidator = new RegexValidator();
            regexValidator
                    .setPattern(ConstantADM.REGULAR_EXPRESION_PANAMA_DOCUMENT);
            regexValidator.validate(context, component, value);
        }
    }

}
