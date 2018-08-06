package co.bdigital.admin.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FlowEvent;

import co.bdigital.admin.ejb.controller.UserInfoServiceBean;
import co.bdigital.admin.ejb.controller.view.ManageProfilesServiceBeanLocal;
import co.bdigital.admin.ejb.controller.view.UserInfoServiceBeanLocal;
import co.bdigital.admin.model.ActionRol;
import co.bdigital.admin.model.PermissionByRol;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.UtilADM;
import co.bdigital.cmm.jpa.model.AwAccionesPorRole;
import co.bdigital.cmm.jpa.model.AwAccionesPorRolePK;
import co.bdigital.cmm.jpa.model.AwRol;
import co.bdigital.shl.tracer.CustomLogger;

/**
 * 
 * @author juan.molinab
 *
 */
@ManagedBean(name = "profileMGBean")
@ViewScoped
public class ProfileMGBean implements Serializable {

    /* Nombre del perfil que se guardara */
    private String nameProfile;
    /* Arreglo donde se guarda la informacion de las acciones con su id */
    private transient List<ActionRol> actionsRol = null;
    /* Arreglo donde se guardan las acciones que podran consulta info */
    private ArrayList<Long> selectedOnlyRead;
    /* Arreglo donde se guardan las acciones que podran modificar info */
    private ArrayList<Long> selectedOnlyWriter;
    /* Cadena para concatenar las acciones en confirmacion de solo lectura */
    private ArrayList<Long> selectedOnlyReadUpdate;
    /* Arreglo donde se guardan las acciones que podran modificar info */
    private ArrayList<Long> selectedOnlyWriterUpdate;
    /* Cadena para concatenar las acciones en confirmacion de solo lectura */
    private String textOnlyRead;
    /* Cadena para concatenar las acciones en confirmacion de solo escritura */
    private String textOnlyWrite;
    /* Usuario en sesion */
    private String userCurrent;
    /* Rol seleccionado para ser modificado */
    private AwRol selectedRol;
    /* Lista de todos los roles */
    private List<AwRol> listAllRol;
    /* Flag para llevar a la primera pestaña cuando se abra dialogo */
    private boolean isFirstOpenTab = false;
    /* Logger */
    private static CustomLogger logger;
    /* Permisos de la accion */
    private PermissionByRol permissionRol;
    /* Codigo del pais */
    private String countryId;
    @EJB
    private transient ManageProfilesServiceBeanLocal manageProfilesServiceBean;
    @EJB
    private transient UserInfoServiceBeanLocal userInfoServiceBean;

    @SuppressWarnings("unchecked")
    public ProfileMGBean() {
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

    @PostConstruct
    public void init() {
        this.listAllRol = getListRol(this.countryId);
    }

    /**
     * Metodo llamado desde el boton de guardar perfil, el cual guarda un nuevo
     * rol
     */
    public void saveProfile() {
        StringBuilder nameProfileBuilder = new StringBuilder(nameProfile);
        nameProfileBuilder.append(ConstantADM.STRING_POINT);
        nameProfileBuilder.append(this.countryId);
        FacesMessage message = null;
        AwRol rol = new AwRol();
        AwRol getRol = null;
        rol.setFechaCreacion(new Date());
        rol.setUsuarioCreacion(userCurrent);
        rol.setNombre(nameProfileBuilder.toString());
        rol.setPaisId(this.countryId);
        try {
            getRol = manageProfilesServiceBean.createProfile(rol);
            savePermissionsByProfile(getRol.getIdRol());
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_INSERT_NEW_ROL, e);
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.ERROR_CREATE_PERMISSIONS_BY_ROL,
                    ConstantADM.ERROR_CREATE_PERMISSIONS_BY_ROL_DESC);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    /**
     * Metodo para actualizar los permisos de un perfil
     */
    public void updateProfile() {
        FacesMessage message = null;
        try {
            boolean isDelete = manageProfilesServiceBean
                    .deleteAllPermissionsByRol(selectedRol.getIdRol());
            if (isDelete) {
                List<AwAccionesPorRole> listActionByRol = getListActionByRol(
                        selectedRol.getIdRol(), selectedOnlyReadUpdate,
                        selectedOnlyWriterUpdate);
                boolean isUpdate = manageProfilesServiceBean
                        .addPermissionsToProfile(listActionByRol);
                if (isUpdate) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            ConstantADM.SUCCESS_UPDATE_PERMISSIONS_BY_ROL,
                            ConstantADM.SUCCESS_UPDATE_PERMISSIONS_BY_ROL_DESC);
                }
            }
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_UPDATE_ROL, e);
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.ERROR_UPDATE_PERMISSIONS_BY_ROL,
                    ConstantADM.ERROR_UPDATE_PERMISSIONS_BY_ROL_DESC);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Metodo para guardar en base de datos las acciones y permisos que tiene un
     * determinado rol
     * 
     * @param idRol
     */
    public void savePermissionsByProfile(long idRol) {
        FacesMessage message = null;
        List<AwAccionesPorRole> listActionByRol = getListActionByRol(idRol,
                selectedOnlyRead, selectedOnlyWriter);
        try {
            boolean isCreate = manageProfilesServiceBean
                    .addPermissionsToProfile(listActionByRol);
            if (true == isCreate) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantADM.SUCCESS_CREATE_PERMISSIONS_BY_ROL,
                        ConstantADM.SUCCESS_CREATE_PERMISSIONS_BY_ROL_DESC);
            }
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_INSERT_PERMISSIONS_BY_ROL, e);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    ConstantADM.ERROR_CREATE_PERMISSIONS_BY_ROL,
                    ConstantADM.ERROR_CREATE_PERMISSIONS_BY_ROL_DESC);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    /**
     * Metodo para obtener una lista de las entidades AwAccionesPorRole para
     * guardar en la tabla intermedia de acciones por roles
     * 
     * @param idRol
     * @return
     */
    public List<AwAccionesPorRole> getListActionByRol(long idRol,
            List<Long> listRead, List<Long> listWrite) {
        List<AwAccionesPorRole> actionsByRol = new ArrayList<AwAccionesPorRole>();
        if (listRead.size() > 0) {
            for (Long idRead : listRead) {
                AwAccionesPorRolePK actionByRolPK = new AwAccionesPorRolePK();
                actionByRolPK.setIdRol(idRol);
                AwAccionesPorRole actionByRol = new AwAccionesPorRole();
                actionByRolPK.setIdAccion(idRead);
                actionByRol.setConsulta(new BigDecimal(1));
                for (Long idWrite : listWrite) {
                    if (idWrite == idRead) {
                        listWrite.remove(idWrite);
                        actionByRol.setModificacion(new BigDecimal(1));
                        break;
                    }
                }
                if (null == actionByRol.getModificacion()) {
                    actionByRol.setModificacion(new BigDecimal(0));
                }
                actionByRol.setId(actionByRolPK);
                actionsByRol.add(actionByRol);
            }

            if (listWrite.size() > 0) {
                for (Long idWrite : listWrite) {
                    AwAccionesPorRolePK actionByRolPK = new AwAccionesPorRolePK();
                    actionByRolPK.setIdRol(idRol);
                    AwAccionesPorRole actionByRol = new AwAccionesPorRole();
                    actionByRolPK.setIdAccion(idWrite);
                    actionByRol.setConsulta(new BigDecimal(0));
                    actionByRol.setModificacion(new BigDecimal(1));
                    actionByRol.setId(actionByRolPK);
                    actionsByRol.add(actionByRol);
                }
            }
        } else if (listWrite.size() > 0) {
            for (Long idWrite : listWrite) {
                AwAccionesPorRolePK actionByRolPK = new AwAccionesPorRolePK();
                actionByRolPK.setIdRol(idRol);
                AwAccionesPorRole actionByRol = new AwAccionesPorRole();
                actionByRolPK.setIdAccion(idWrite);
                actionByRol.setConsulta(new BigDecimal(0));
                actionByRol.setModificacion(new BigDecimal(1));
                actionByRol.setId(actionByRolPK);
                actionsByRol.add(actionByRol);
            }
        }
        return actionsByRol;
    }

    /**
     * Metodo para controlar las pestañas en la creacion de perfil
     * 
     * @param event
     * @return
     */
    public String onFlowProcess(FlowEvent event) {
        if (event.getNewStep().equals("confirmation")) {
            textOnlyRead = concatActionByPermissions(selectedOnlyRead);
            textOnlyWrite = concatActionByPermissions(selectedOnlyWriter);
        }
        return event.getNewStep();
    }

    /**
     * Metodo para controlar las pestañas en la actualizacion de perfil
     * 
     * @param event
     * @return
     */
    public String onFlowProcessUpdate(FlowEvent event) {
        if (event.getNewStep().equals("confirmationUpdate")) {
            textOnlyRead = concatActionByPermissions(selectedOnlyReadUpdate);
            textOnlyWrite = concatActionByPermissions(selectedOnlyWriterUpdate);
        }
        return event.getNewStep();
    }

    /**
     * Metodo para retornar en una cadena las acciones sobre las que se dio
     * permisos
     * 
     * @param permission
     *            id de las acciones de un determinado permiso (lect - escrit)
     * @return
     */
    public String concatActionByPermissions(ArrayList<Long> permission) {
        String text = "";
        for (ActionRol rol : actionsRol) {
            if (permission.isEmpty()) {
                break;
            }
            for (int i = 0; i < permission.size(); i++) {
                long id = permission.get(i);
                if (id == rol.getIdAction()) {
                    text += rol.getName() + ConstantADM.WORD_COMMA;
                }
            }
        }
        if (!permission.isEmpty()) {
            text = text.substring(0, text.length() - 2);
        }

        return text.toString();
    }

    /**
     * Metodo para retornar la lista de roles
     * 
     * @param countryId
     * @return lista de roles
     */
    private List<AwRol> getListRol(String countryId) {
        List<AwRol> roles = new ArrayList<AwRol>();
        try {
            roles = UtilADM.getListRolSplitProfileName(
                    userInfoServiceBean.getListRol(countryId));
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_GET_LIST_ROL, e);
        }
        return roles;
    }

    /**
     * Metodo para buscar un rol con sus acciones y permisos
     * 
     * @param awRolId
     * @return
     */
    public AwRol findAwRolById(Long awRolId) {
        AwRol rol = null;
        try {
            rol = manageProfilesServiceBean.findAwRolById(awRolId);
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_GET_LIST_ROL, e);
        }
        return rol;
    }

    /**
     * Metodo para cargar los permisos por rol cuando se va a editar
     * 
     * @param rol
     */
    public void loadPermissionByRol(AwRol rol) {
        selectedOnlyReadUpdate = new ArrayList<Long>();
        selectedOnlyWriterUpdate = new ArrayList<Long>();
        List<AwAccionesPorRole> actionsByRol = rol.getAwAccionesPorRoles();
        int read = 0;
        int write = 0;
        for (AwAccionesPorRole awAccionesPorRole : actionsByRol) {
            if (null != awAccionesPorRole.getConsulta()) {
                read = Integer
                        .valueOf(awAccionesPorRole.getConsulta().intValue());
            }
            if (ConstantADM.STRING_UNO.equals(read)) {
                selectedOnlyReadUpdate
                        .add(awAccionesPorRole.getAwAccion().getIdAccion());
            }
            if (null != awAccionesPorRole.getModificacion()) {
                write = Integer.valueOf(
                        awAccionesPorRole.getModificacion().intValue());
            }
            if (ConstantADM.STRING_UNO.equals(write)) {
                selectedOnlyWriterUpdate
                        .add(awAccionesPorRole.getAwAccion().getIdAccion());
            }
        }
    }

    /**
     * @return the nameProfile
     */
    public String getNameProfile() {
        return nameProfile;
    }

    /**
     * @param nameProfile
     *            the nameProfile to set
     */
    public void setNameProfile(String nameProfile) {
        this.nameProfile = nameProfile;
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
     * @return the selectedOnlyRead
     */
    public ArrayList<Long> getSelectedOnlyRead() {
        return selectedOnlyRead;
    }

    /**
     * @param selectedOnlyRead
     *            the selectedOnlyRead to set
     */
    public void setSelectedOnlyRead(ArrayList<Long> selectedOnlyRead) {
        this.selectedOnlyRead = selectedOnlyRead;
    }

    /**
     * @return the selectedOnlyWriter
     */
    public ArrayList<Long> getSelectedOnlyWriter() {
        return selectedOnlyWriter;
    }

    /**
     * @param selectedOnlyWriter
     *            the selectedOnlyWriter to set
     */
    public void setSelectedOnlyWriter(ArrayList<Long> selectedOnlyWriter) {
        this.selectedOnlyWriter = selectedOnlyWriter;
    }

    /**
     * @return the textOnlyRead
     */
    public String getTextOnlyRead() {
        return textOnlyRead;
    }

    /**
     * @param textOnlyRead
     *            the textOnlyRead to set
     */
    public void setTextOnlyRead(String textOnlyRead) {
        this.textOnlyRead = textOnlyRead;
    }

    /**
     * @return the textOnlyWrite
     */
    public String getTextOnlyWrite() {
        return textOnlyWrite;
    }

    /**
     * @param textOnlyWrite
     *            the textOnlyWrite to set
     */
    public void setTextOnlyWrite(String textOnlyWrite) {
        this.textOnlyWrite = textOnlyWrite;
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
     * @return the selectedRol
     */
    public AwRol getSelectedRol() {
        if (null != selectedRol) {
            isFirstOpenTab = true;
            textOnlyRead = "";
            textOnlyWrite = "";
            AwRol rol = findAwRolById(selectedRol.getIdRol());
            loadPermissionByRol(rol);
        }
        return selectedRol;
    }

    /**
     * @param selectedRol
     *            the selectedRol to set
     */
    public void setSelectedRol(AwRol selectedRol) {
        this.selectedRol = selectedRol;
    }

    /**
     * @return the selectedOnlyReadUpdate
     */
    public ArrayList<Long> getSelectedOnlyReadUpdate() {
        return selectedOnlyReadUpdate;
    }

    /**
     * @param selectedOnlyReadUpdate
     *            the selectedOnlyReadUpdate to set
     */
    public void setSelectedOnlyReadUpdate(
            ArrayList<Long> selectedOnlyReadUpdate) {
        this.selectedOnlyReadUpdate = selectedOnlyReadUpdate;
    }

    /**
     * @return the selectedOnlyWriterUpdate
     */
    public ArrayList<Long> getSelectedOnlyWriterUpdate() {
        return selectedOnlyWriterUpdate;
    }

    /**
     * @param selectedOnlyWriterUpdate
     *            the selectedOnlyWriterUpdate to set
     */
    public void setSelectedOnlyWriterUpdate(
            ArrayList<Long> selectedOnlyWriterUpdate) {
        this.selectedOnlyWriterUpdate = selectedOnlyWriterUpdate;
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
