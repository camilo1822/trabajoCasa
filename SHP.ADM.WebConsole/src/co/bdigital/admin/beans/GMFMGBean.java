package co.bdigital.admin.beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FlowEvent;

import co.bdigital.admin.ejb.controller.view.CallGMFServiceBeanLocal;
import co.bdigital.admin.model.ActionRol;
import co.bdigital.admin.model.PermissionByRol;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.cmm.jpa.model.AwRol;
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.shl.tracer.CustomLogger;

/**
 * 
 * @author juan.arboleda
 *
 */
@ManagedBean(name = "gMFMGBean")
@SessionScoped
public class GMFMGBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private String option;
    private String name;
    private String phone;
    private String id;
    private String type;

    /* Arreglo donde se guarda la informacion de las acciones con su id */
    private transient List<ActionRol> actionsRol = null;
    /* Usuario en sesion */
    private String userCurrent;
    /* Lista de todos los roles */
    private List<AwRol> listAllRol;
    /* Logger */
    private CustomLogger logger;
    /* Permisos de la accion */
    private PermissionByRol permissionRol;
    /* Codigo del pais */
    private String countryId;

    @EJB
    private transient CallGMFServiceBeanLocal callGMFServiceBean;

    @SuppressWarnings("unchecked")
    public GMFMGBean() {
        logger = new CustomLogger(this.getClass());
        this.countryId = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.COUNTRY_ID);
        this.actionsRol = (List<ActionRol>) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.ACTIONS_BY_ROL_SESION);
        this.userCurrent = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.USER_NAME_SESION);
        permissionRol = (PermissionByRol) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.PERMISSIONS_BY_ROL_SESION);
    }

    /**
     * Metodo para realizar operaciones de GMF
     * 
     */
    public void callGMF() {
        FacesMessage messages = null;
        try {
            String response = callGMFServiceBean.callGMF(this.accountNumber,
                    this.option);
            messages = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    ConstantADM.COMMON_STRING_RESPONSE, response);
            FacesContext.getCurrentInstance().addMessage(null, messages);
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_GMF, e);
            messages = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.ERROR_GMF, ConstantADM.ERROR_GMF);
            FacesContext.getCurrentInstance().addMessage(null, messages);
        }
    }

    /**
     * Metodo para mostrar la info de un cliente
     */
    public boolean showClient() {
        Cliente client = callGMFServiceBean.clientByAccount(this.accountNumber,
                this.countryId);
        FacesMessage messages = null;
        boolean response = false;
        if (null != client) {
            StringBuilder stb = new StringBuilder();
            stb.append(client.getNombre1());
            stb.append(ConstantADM.STRING_SPACE);
            stb.append(client.getApellido1());
            String celular = client.getCelular();
            String documento = client.getNumeroId();
            if (ConstantADM.COMMON_STRING_CODE_CLOSE_ACCOUNT
                    .equals(String.valueOf(client.getEstadoId()))) {
                phone = celular.substring(ConstantADM.COMMON_INT_ZERO,
                        celular.indexOf(ConstantADM.COMMON_CHAR_UNDERSCORE));
                id = documento.substring(ConstantADM.COMMON_INT_ZERO,
                        documento.indexOf(ConstantADM.COMMON_CHAR_UNDERSCORE));
            } else {
                phone = celular;
                id = documento;
            }
            name = stb.toString();
            type = client.getTipoId();
            response = true;
        } else {
            messages = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    ConstantADM.ERROR_USER_NOT_FOUND, null);
            FacesContext.getCurrentInstance().addMessage(null, messages);
        }
        return response;

    }

    /**
     * Metodo para controlar las pestañas de promociones
     * 
     * @param event
     * @return
     */
    public String onFlowProcess(FlowEvent event) {
        boolean response = true;
        if (ConstantADM.STRING_CONFIRMATION.equals(event.getNewStep())) {
            response = showClient();
            if (!response) {
                return event.getOldStep();
            }
        }
        return event.getNewStep();
    }

    public CallGMFServiceBeanLocal getCallGMFServiceBean() {
        return callGMFServiceBean;
    }

    public void setCallGMFServiceBean(
            CallGMFServiceBeanLocal callGMFServiceBean) {
        this.callGMFServiceBean = callGMFServiceBean;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String document) {
        this.accountNumber = document;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
