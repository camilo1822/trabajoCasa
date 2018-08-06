package co.bdigital.admin.beans;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.nequi.cmm.messaging.general.ResponseMessageObjectType;

import co.bdigital.admin.ejb.controller.ParametersServiceBean;
import co.bdigital.admin.ejb.controller.view.AuditServiceBeanLocal;
import co.bdigital.admin.ejb.controller.view.CloseAccountServiceBeanLocal;
import co.bdigital.admin.messaging.closeaccount.CloseAccountRQType;
import co.bdigital.admin.messaging.closeaccount.CloseAccountServiceRequestType;
import co.bdigital.admin.messaging.services.bco.acs502.AccountDetailType;
import co.bdigital.admin.messaging.services.bco.acs502.AccountType;
import co.bdigital.admin.messaging.services.bco.acs502.MassiveCloseAccountRQType;
import co.bdigital.admin.messaging.services.bco.acs502.MassiveCloseAccountServiceRequestType;
import co.bdigital.admin.model.PermissionByRol;
import co.bdigital.admin.model.StatusResponse;
import co.bdigital.admin.util.ConnectionMDW;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.UtilADM;
import co.bdigital.admin.util.WebConsoleUtil;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.model.Parametro;
import co.bdigital.cmm.jpa.model.framework.WsProvider;
import co.bdigital.cmm.messaging.general.RequestBody;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.shl.tracer.CustomLogger;

/**
 *
 * @author juan.molinab
 */
@ManagedBean(name = "closeAccountMGBean")
@SessionScoped
public class CloseAccountMGBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @EJB
    private transient CloseAccountServiceBeanLocal closeAccountServiceBean;
    /** Numero del celular para cerrar la cuenta **/
    private String phoneNumber;
    /** Codigo del pais **/
    private String countryId;
    /** Logger **/
    private CustomLogger logger;
    /** Lista de los roles **/
    private List<SelectItem> selectOneItemReason;
    /** Razon del cierre de cuenta para Easy Solution **/
    private String reasonCode;
    /** Descripcion de la razon del cierre de cuenta **/
    private String descReasonCode;
    /** Map con la informacion de las razones de cierre **/
    private Map<String, String> mapReasonCode;
    /** Informacion de los servicios a consumir **/
    private static final String SERVICE_NAME = "AccountServices";
    private static final String SERVICE_VERSION = "1.5.0";
    /** Usuario en sesion **/
    private String userCurrent;
    /** Permisos de la accion **/
    private PermissionByRol permissionRol;
    @EJB
    private transient AuditServiceBeanLocal auditServiceBean;
    @EJB
    private transient ParametersServiceBean parametersServiceBean;
    private List<Parametro> reasons;
    private String closureNotes;
    /**
     * Objeto con los datos del endpoint de MDW.
     */
    private WsProvider mdwProviderInfo;
    private String realPath;
    private List<Parametro> accountsFileColumnOrder;
    private String columnsAllowed;

    public CloseAccountMGBean() {
        logger = new CustomLogger(this.getClass());
        this.countryId = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(co.bdigital.admin.util.ConstantADM.COUNTRY_ID);
        this.userCurrent = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.USER_NAME_SESION);
        mapReasonCode = new HashMap<String, String>();
        permissionRol = (PermissionByRol) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.PERMISSIONS_BY_ROL_SESION);
        this.reasons = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        if (null == this.accountsFileColumnOrder
                || this.accountsFileColumnOrder.isEmpty()) {
            this.accountsFileColumnOrder = this.getListAccountsFileOrder();
        }
        this.columnsAllowed = this.buildColumnsAllowedMessage();
    }

    /**
     * 
     */
    private String buildColumnsAllowedMessage() {
        StringBuilder columnsMessage = new StringBuilder();
        for (int i = 0; i < this.accountsFileColumnOrder.size(); i++) {
            columnsMessage
                    .append(this.accountsFileColumnOrder.get(i).getNombre());
            if (i == this.accountsFileColumnOrder.size() - 2) {
                columnsMessage.append(ConstantADM.STRING_SPLIT_POINT_DATE);
                columnsMessage.append(ConstantADM.COMMON_STRING_AND_TRANSLATED);
                columnsMessage.append(ConstantADM.STRING_SPLIT_POINT_DATE);
            } else if (i != this.accountsFileColumnOrder.size() - 1) {
                columnsMessage.append(ConstantADM.STRING_SPLIT_POINT_DATE);
                columnsMessage.append(ConstantADM.COMMA);
                columnsMessage.append(ConstantADM.STRING_SPLIT_POINT_DATE);
            }
        }
        return columnsMessage.toString();
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
     * @return the reasonCode
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * @param reasonCode
     *            the reasonCode to set
     */
    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    /**
     * @return the descReasonCode
     */
    public String getDescReasonCode() {
        return descReasonCode;
    }

    /**
     * @param descReasonCode
     *            the descReasonCode to set
     */
    public void setDescReasonCode(String descReasonCode) {
        this.descReasonCode = descReasonCode;
    }

    /**
     * @return the reasons
     */
    public List<Parametro> getReasons() {
        return reasons;
    }

    /**
     * @param reasons
     *            the reasons to set
     */
    public void setReasons(List<Parametro> reasons) {
        this.reasons = reasons;
    }

    /**
     * Metodo para cerrar la cuenta de un cliente
     */
    public void closeAccount() {
        FacesMessage message;
        descReasonCode = mapReasonCode.get(reasonCode);

        if (ConstantADM.STRING_EMPTY.equals(phoneNumber)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.PHONE_NUMBER_FIELD,
                    ConstantADM.ENTER_NUMBER_PHONE);
        } else if (ConstantADM.STRING_EMPTY.equals(reasonCode)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.REASON_CODE_FIELD,
                    ConstantADM.ENTER_REASON_CODE);
        } else {
            try {
                RequestMessageObjectType request = UtilADM.getRequestObject(
                        SERVICE_NAME,
                        ConstantADM.SERVICE_OPERATION_CLOSE_ACCOUNT, countryId,
                        SERVICE_VERSION);

                RequestBody requestBody = new RequestBody();
                CloseAccountServiceRequestType requestType = closeAccountRQ();
                requestBody.setAny(requestType);
                request.getRequestMessage().setRequestBody(requestBody);

                ConnectionMDW connectionMDW = new ConnectionMDW();
                ResponseMessageObjectType responseMessageObjectType = connectionMDW
                        .callMiddleware(getMdwProviderInfo(), request,
                                SERVICE_NAME,
                                ConstantADM.SERVICE_OPERATION_CLOSE_ACCOUNT);

                if (null != responseMessageObjectType) {
                    if (Constant.COMMON_STRING_ZERO
                            .equalsIgnoreCase(responseMessageObjectType
                                    .getResponseMessage().getResponseHeader()
                                    .getStatus().getStatusCode())) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                ConstantADM.CLOSE_EXIT_ACCOUNT,
                                this.phoneNumber);
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
                logger.error(UtilADM.generateStringConcatenated(
                        ConstantADM.ERROR_EXECUTE_SERVICE_REST,
                        ConstantADM.STRING_SPLIT_POINT_DATE,
                        ConstantADM.SERVICE_OPERATION_CREATE_USER), ex);
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        ConstantADM.WORD_ERROR
                                + ConstantADM.ERROR_CODE_COMMUNICATION_MDW,
                        ConstantADM.ERROR_MESSAGE_REST_COMMUNICATION);
            } finally {
                try {
                    auditServiceBean.insertAudit(
                            ConstantADM.ID_ACTION_CLOSE_ACCOUNT, userCurrent,
                            UtilADM.getCurrentDate(),
                            UtilADM.createMessageAudit(new StatusResponse(),
                                    phoneNumber));
                } catch (Exception e) {
                    logger.error(ConstantADM.ERROR_SAVE_AUDIT, e);
                }
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        this.phoneNumber = "";
        this.reasonCode = "";
    }

    /**
     * Metodo para formar el body del metodo closeAccount
     * 
     * @return
     */
    public CloseAccountServiceRequestType closeAccountRQ() {
        CloseAccountServiceRequestType requestType = new CloseAccountServiceRequestType();
        CloseAccountRQType closeAccountRQ = new CloseAccountRQType();
        closeAccountRQ.setPhoneNumber(phoneNumber);
        closeAccountRQ.setSoftToken(ConstantADM.STRING_EMPTY);
        closeAccountRQ.setPhoneNumberDestination(ConstantADM.STRING_EMPTY);
        closeAccountRQ.setCode(reasonCode);

        if (null != closureNotes && !closureNotes.isEmpty()) {
            closeAccountRQ.setDescription(closureNotes);
        } else {
            closeAccountRQ.setDescription(descReasonCode);
        }
        requestType.setCloseAccountRQ(closeAccountRQ);
        return requestType;
    }

    /**
     * Metodo para devolver a la vista el rol con el codigo y descripcion
     * 
     * @return the selectOneItemRol
     */
    public List<SelectItem> getSelectOneItemReason() {
        this.selectOneItemReason = new ArrayList<SelectItem>();
        this.reasons = getListReasonCode();
        for (Parametro reason : reasons) {
            mapReasonCode.put(reason.getValor(), reason.getNombre());
            SelectItem selectItem = new SelectItem(reason.getValor(),
                    reason.getNombre());
            this.selectOneItemReason.add(selectItem);
        }
        return this.selectOneItemReason;
    }

    /**
     * Metodo para cargar los codigos de razon de cierre de cuenta en Finacle.
     * 
     * @return {@link List} listado de {@link Parametro}
     */
    private List<Parametro> getListReasonCode() {
        List<Parametro> reason = new ArrayList<Parametro>();
        try {
            reason = this.closeAccountServiceBean.listReasonCode(
                    ConstantADM.TYPE_PARAMETER_DELETETION_ACCOUNT_FINACLE_REASON_CODE,
                    countryId);
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_LOADING_CONFIGURATION_PARAMETERS, e);
        }
        return reason;
    }

    /**
     * Metodo para limpiar los campos del formulario de cierre de cuenta
     */
    public void cleanFormCloseAccount() {
        RequestContext.getCurrentInstance().reset("formCloseAccount");
        this.setPhoneNumber(ConstantADM.STRING_EMPTY);
        this.setReasonCode(ConstantADM.STRING_EMPTY);
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
     * Metodo para cargar el documento CSV
     * 
     * @param event
     * @throws IOException
     */
    public void loadData(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
        ExternalContext ec = FacesContext.getCurrentInstance()
                .getExternalContext();
        String path = ec.getRealPath(ConstantADM.WORD_SLASH_SPLIT_URL);

        if (null != this.realPath && !this.realPath.trim().isEmpty()) {
            UtilADM.deleteFile(this.realPath);
        }

        this.realPath = UtilADM.generateStringConcatenated(path, File.separator,
                ConstantADM.RESOURCES_PATH, File.separator,
                ConstantADM.FILE_PATH, File.separator, file.getFileName());
        if (null != this.realPath && !this.realPath.trim().isEmpty()) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap()
                    .put(ConstantADM.PATH_FILE_UPLOAD, this.realPath);
        }
        FacesMessage message;
        try {
            this.saveFileCSV(this.realPath, file);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    ConstantADM.WORD_SUCCESS, ConstantADM.FILE_UPLOAD_SUCCESS);
        } catch (IllegalArgumentException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.WORD_ERROR, e.getMessage());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Metodo para guardar temporalmente el archivo SCV
     * 
     * @param realPath
     * @param file
     */
    public void saveFileCSV(String realPath, UploadedFile file) {

        BufferedReader br = null;
        String line = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        FileOutputStream out = null;
        FileReader reader = null;
        int rowsCont = 0;

        try {
            bis = new BufferedInputStream(file.getInputstream());
            out = new FileOutputStream(realPath);
            bos = new BufferedOutputStream(out);
            int contador = 0;
            while ((contador = bis.read()) != -1) {
                bos.write(contador);
            }
            bos.flush();

            reader = new FileReader(realPath);
            br = new BufferedReader(reader);
            while ((line = br.readLine()) != null) {
                rowsCont++;
                String[] columnData = line.split(ConstantADM.COMMA);
                if (this.accountsFileColumnOrder.size() != columnData.length) {
                    String message = ConstantADM.ERROR_INVALID_COLUMNS_UPLOAD_FILE;
                    message = message.replace(
                            ConstantADM.COMMON_STRING_REPLACE_KEY_FIRST,
                            Integer.toString(columnData.length));
                    message = message.replace(
                            ConstantADM.COMMON_STRING_REPLACE_KEY_SECOND,
                            this.columnsAllowed);
                    throw new IllegalArgumentException(message);
                }
                if (ConstantADM.MASSIVE_CLOSE_ACCOUNT_MAX_LENGTH < rowsCont) {
                    String message = ConstantADM.MASSIVE_CLOSE_ACCOUNT_MAX_LENGTH_ERROR;
                    throw new IllegalArgumentException(message);
                }
            }
        } catch (IOException e) {
            logger.error(ConstantADM.ERROR_READ_UPLOAD_FILE, e);
            throw new IllegalArgumentException(
                    ConstantADM.ERROR_READ_UPLOAD_FILE);
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                logger.error(ConstantADM.ERROR_CLOSING_RESOURCES_TO_UPLOAD_FILE,
                        e);
            }
            try {
                if (null != br) {
                    br.close();
                }
            } catch (IOException e) {
                logger.error(ConstantADM.ERROR_CLOSING_RESOURCES_TO_UPLOAD_FILE,
                        e);
            }
            try {
                if (null != bos) {
                    bos.close();
                }
            } catch (IOException e) {
                logger.error(ConstantADM.ERROR_CLOSING_RESOURCES_TO_UPLOAD_FILE,
                        e);
            }
            try {
                if (null != out) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error(ConstantADM.ERROR_CLOSING_RESOURCES_TO_UPLOAD_FILE,
                        e);
            }
            try {
                if (null != bis) {
                    bis.close();
                }
            } catch (IOException e) {
                logger.error(ConstantADM.ERROR_CLOSING_RESOURCES_TO_UPLOAD_FILE,
                        e);
            }

        }
    }

    public void processMassiveCloseAccount() {

        FacesMessage message = null;
        try {
            this.realPath = (String) FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap()
                    .get(ConstantADM.PATH_FILE_UPLOAD);

            MassiveCloseAccountServiceRequestType massiveCloseAccountRequest = new MassiveCloseAccountServiceRequestType();
            MassiveCloseAccountRQType massiveCloseAccountRq = null;
            if (null != realPath && !this.realPath.trim().isEmpty()) {
                massiveCloseAccountRq = this
                        .buildMassiveCloseAccountRq(this.realPath);
                massiveCloseAccountRequest
                        .setMassiveCloseAccountRQ(massiveCloseAccountRq);

                StatusResponse massiveCloseAccountResponseStatus = this.closeAccountServiceBean
                        .massiveCloseAccount(massiveCloseAccountRequest,
                                this.countryId);

                if (massiveCloseAccountResponseStatus.getStatusCode()
                        .equals(ConstantADM.STATUS_CODE_SUCCESS)) {
                    // Se elimina el archivo de la carpeta temporal
                    UtilADM.deleteFile(this.realPath);

                    message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            ConstantADM.WORD_SUCCESS,
                            UtilADM.generateStringConcatenated(
                                    ConstantADM.FILE_PROCESSING_SUCCESS,
                                    ConstantADM.STRING_SPLIT_POINT_DATE,
                                    ConstantADM.BACKOUT_PROCESS_IN_EXECUTION));
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ConstantADM.WORD_ERROR
                                    + massiveCloseAccountResponseStatus
                                            .getStatusCode(),
                            massiveCloseAccountResponseStatus.getStatusDesc());
                }

                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        ConstantADM.WORD_ERROR, ConstantADM.CHOOSE_FILE_UPLOAD);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }

        } catch (IllegalArgumentException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.WORD_ERROR, e.getMessage());
        }
    }

    /**
     * Metodo para construir la petici�n con la información para el cierre de
     * cuenta masivo.
     * 
     * @param realPath
     *            Ruta del archivo con la información de las cuentas a cerrar.
     * @return {@link MassiveCloseAccountRQType} cuerpo para la petici�n del
     *         servicio de cierre de cuenta masivo.
     */
    public MassiveCloseAccountRQType buildMassiveCloseAccountRq(
            String realPath) {
        FileReader reader = null;
        BufferedReader br = null;
        String line = null;

        Map<String, String> reasonCodes = new HashMap<String, String>();

        MassiveCloseAccountRQType massiveCloseAccountRq = new MassiveCloseAccountRQType();
        AccountType accounts = new AccountType();
        massiveCloseAccountRq.setAccountDetails(accounts);
        massiveCloseAccountRq.setEmailToNotify(this.userCurrent);

        try {

            Parametro phoneNumberParameter = WebConsoleUtil
                    .getParameterByNameAndCountryCode(
                            this.accountsFileColumnOrder,
                            ConstantADM.TYPE_PARAMETER_DELETETION_ACCOUNT_FILE_PHONE_NUMBER_COLUMN,
                            this.countryId);

            final int phoneNumberIndex = Integer
                    .parseInt(phoneNumberParameter.getValor());

            Parametro reasonCodeParameter = WebConsoleUtil
                    .getParameterByNameAndCountryCode(
                            this.accountsFileColumnOrder,
                            ConstantADM.TYPE_PARAMETER_DELETETION_ACCOUNT_FILE_REASON_CODE_COLUMN,
                            this.countryId);

            final int reasonCodeIndex = Integer
                    .parseInt(reasonCodeParameter.getValor());

            Parametro descriptionCodeParameter = WebConsoleUtil
                    .getParameterByNameAndCountryCode(
                            this.accountsFileColumnOrder,
                            ConstantADM.TYPE_PARAMETER_DELETETION_ACCOUNT_FILE_DESCRIPCION_COLUMN,
                            this.countryId);

            final int descriptionCodeIndex = Integer
                    .parseInt(descriptionCodeParameter.getValor());

            reader = new FileReader(realPath);
            br = new BufferedReader(reader);

            while ((line = br.readLine()) != null) {

                String[] columnData = line.split(ConstantADM.COMMA);

                if (this.accountsFileColumnOrder.size() != columnData.length) {
                    String message = ConstantADM.ERROR_INVALID_COLUMNS_UPLOAD_FILE;
                    message = message.replace(
                            ConstantADM.COMMON_STRING_REPLACE_KEY_FIRST,
                            Integer.toString(columnData.length));
                    message = message.replace(
                            ConstantADM.COMMON_STRING_REPLACE_KEY_SECOND,
                            this.columnsAllowed);
                    throw new IllegalArgumentException(message);
                } else {
                    String phoneNumber = columnData[phoneNumberIndex];
                    String reasonCode = columnData[reasonCodeIndex];
                    String descripcion = columnData[descriptionCodeIndex];

                    AccountDetailType accountToClose = new AccountDetailType();
                    accountToClose.setPhoneNumber(phoneNumber);
                    accountToClose.setReasonCode(reasonCode);

                    if (null != descripcion && !descripcion.isEmpty()) {
                        accountToClose.setReasonDescription(descripcion);

                    } else {
                        if (!reasonCodes.containsKey(reasonCode)) {
                            for (Parametro reason : this.reasons) {
                                if (reasonCode.equals(reason.getValor())) {
                                    reasonCodes.put(reasonCode,
                                            reason.getNombre());
                                    break;
                                }
                            }
                        }

                        accountToClose.setReasonDescription(
                                reasonCodes.get(reasonCode));
                    }

                    accounts.getAccounts().add(accountToClose);
                }
            }
            return massiveCloseAccountRq;

        } catch (FileNotFoundException e) {
            logger.error(ConstantADM.FILE_NOT_FOUNT_UPLOAD, e);
            throw new IllegalArgumentException(
                    ConstantADM.FILE_NOT_FOUNT_UPLOAD);
        } catch (IOException e) {
            logger.error(ConstantADM.ERROR_READ_FILE_UPLOAD, e);
            throw new IllegalArgumentException(
                    ConstantADM.ERROR_READ_UPLOAD_FILE);
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                logger.error(ConstantADM.ERROR_CLOSING_RESOURCES_TO_UPLOAD_FILE,
                        e);
            }
            try {
                if (null != br) {
                    br.close();
                }
            } catch (IOException e) {
                logger.error(ConstantADM.ERROR_CLOSING_RESOURCES_TO_UPLOAD_FILE,
                        e);
            }
        }
    }

    /**
     * Metodo para cargar el orden de las columnas del archivo CSV con las
     * cuentas a cerrar.
     * 
     * @return {@link List} listado de {@link Parametro}
     */
    private List<Parametro> getListAccountsFileOrder() {
        List<Parametro> parameters = new ArrayList<Parametro>();
        try {
            parameters = this.closeAccountServiceBean.listReasonCode(
                    ConstantADM.TYPE_PARAMETER_DELETETION_ACCOUNT_FILE_COLUMN_ORDER,
                    countryId);
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_LOADING_CONFIGURATION_PARAMETERS, e);
        }
        return parameters;
    }

    /**
     * @return the closureNotes
     */
    public String getClosureNotes() {
        return closureNotes;
    }

    /**
     * @param closureNotes
     *            the closureNotes to set
     */
    public void setClosureNotes(String closureNotes) {
        this.closureNotes = closureNotes;
    }

}
