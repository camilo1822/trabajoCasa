package co.bdigital.admin.beans;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import co.bdigital.admin.ejb.controller.UserInfoServiceBean;
import co.bdigital.admin.ejb.controller.view.GetBankCertificateServiceBeanLocal;
import co.bdigital.admin.ejb.controller.view.UserInfoServiceBeanLocal;
import co.bdigital.admin.messaging.services.getbankcertificate.GetBankCertificateRQType;
import co.bdigital.admin.messaging.services.getbankcertificate.GetBankCertificateServiceRequestType;
import co.bdigital.admin.model.ActionRol;
import co.bdigital.admin.model.PermissionByRol;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.UtilADM;
import co.bdigital.cmm.ejb.exception.MiddlewareException;
import co.bdigital.cmm.jpa.model.AwRol;
import co.bdigital.cmm.messaging.general.RequestBody;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;
import co.bdigital.cmm.messaging.general.StatusType;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * 
 * @author juan.arboleda
 *
 */
@ManagedBean(name = "getCertificateMGBean")
@SessionScoped
public class GetCertificateMGBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String document;
    private String documentType;

    /* Arreglo donde se guarda la informacion de las acciones con su id */
    private transient List<ActionRol> actionsRol = null;
    /* Lista de todos los roles */
    private List<AwRol> listAllRol;
    /* Logger */
    private CustomLogger logger;
    /* Permisos de la accion */
    private PermissionByRol permissionRol;
    /* Codigo del pais */
    private String countryId;

    @EJB
    private transient GetBankCertificateServiceBeanLocal getBankCertificateServiceBeanLocal;

    @EJB
    private transient UserInfoServiceBeanLocal userInfoServiceBean;

    @SuppressWarnings("unchecked")
    public GetCertificateMGBean() {
        logger = new CustomLogger(this.getClass());
        this.countryId = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.COUNTRY_ID);
        this.actionsRol = (List<ActionRol>) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.ACTIONS_BY_ROL_SESION);
        permissionRol = (PermissionByRol) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.PERMISSIONS_BY_ROL_SESION);
    }

    /**
     * Metodo para descargar pdf de certificado bancario
     * 
     */
    public void downloadPdf() throws IOException {

        FacesMessage messages = null;
        OutputStream outputStream = null;
        try {
            RequestMessageObjectType request = UtilADM.getRequestObject(
                    ConstantADM.GET_BANK_CERTIFIED_SERVICE_NAME,
                    ConstantADM.SERVICE_OPERATION_GET_BANK_CERTIFIED,
                    this.countryId,
                    ConstantADM.COMMON_STRING_SERVICE_VERSION_ONE_ZERO_ZERO);

            RequestBody requestBody = new RequestBody();
            GetBankCertificateServiceRequestType getBankCertificateServiceRequestType = new GetBankCertificateServiceRequestType();
            GetBankCertificateRQType requestType = new GetBankCertificateRQType();
            getBankCertificateServiceRequestType
                    .setGetBankCertificateRQ(requestType);
            requestType.setPhoneNumber(this.document);

            requestBody.setAny(getBankCertificateServiceRequestType);
            request.getRequestMessage().setRequestBody(requestBody);

            ResponseMessageObjectType responseBroker = getBankCertificateServiceBeanLocal
                    .getCertificateData(request);
            StatusType statusTypeBroker = responseBroker.getResponseMessage()
                    .getResponseHeader().getStatus();
            if (ConstantADM.COMMON_STRING_ZERO
                    .equals(statusTypeBroker.getStatusCode())) {
                HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
                        .getCurrentInstance().getExternalContext()
                        .getResponse();
                httpServletResponse.reset();
                httpServletResponse
                        .setContentType(ConstantADM.APPLICATION_TYPE_PDF);

                httpServletResponse.addHeader(ConstantADM.CONTENT_DISPOSITION,
                        ConstantADM.PDF_CONSTRUCTOR);

                outputStream = httpServletResponse.getOutputStream();

                getBankCertificateServiceBeanLocal.getCertificate(
                        responseBroker, this.countryId, this.document,
                        outputStream);

                FacesContext.getCurrentInstance().responseComplete();

            } else {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        ConstantADM.ERROR_GET_PDF,
                        new MiddlewareException(ConstantADM.ERROR_GET_PDF));
                messages = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        ConstantADM.ERROR_GET_PDF,
                        ConstantADM.ERROR_GET_PDF_DESC);
                FacesContext.getCurrentInstance().addMessage(null, messages);
            }

        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_GET_PDF, e);
            messages = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.ERROR_GET_PDF, ConstantADM.ERROR_GET_PDF_DESC);
            FacesContext.getCurrentInstance().addMessage(null, messages);
        } finally {
            if (null != outputStream) {
                outputStream.close();
            }
        }
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public GetBankCertificateServiceBeanLocal getGetBankCertificateServiceBeanLocal() {
        return getBankCertificateServiceBeanLocal;
    }

    public void setGetBankCertificateServiceBeanLocal(
            GetBankCertificateServiceBeanLocal getBankCertificateServiceBeanLocal) {
        this.getBankCertificateServiceBeanLocal = getBankCertificateServiceBeanLocal;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * @return the actionsRol
     */
    public List<ActionRol> getActionsRol() {
        return actionsRol;
    }

    /**
     * @param actionsRol
     *            the actionsRol to set
     */
    public void setActionsRol(List<ActionRol> actionsRol) {
        this.actionsRol = actionsRol;
    }

    /**
     * @return the listAllRol
     */
    public List<AwRol> getListAllRol() {
        return listAllRol;
    }

    /**
     * @param listAllRol
     *            the listAllRol to set
     */
    public void setListAllRol(List<AwRol> listAllRol) {
        this.listAllRol = listAllRol;
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
