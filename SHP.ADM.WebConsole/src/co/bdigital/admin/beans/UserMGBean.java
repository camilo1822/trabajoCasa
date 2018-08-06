package co.bdigital.admin.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import co.bdigital.admin.ejb.controller.ChangePasswordServiceBean;
import co.bdigital.admin.ejb.controller.CreateUserServiceBean;
import co.bdigital.admin.ejb.controller.DeleteUserServiceBean;
import co.bdigital.admin.ejb.controller.ParametersServiceBean;
import co.bdigital.admin.ejb.controller.UpdateUserServiceBean;
import co.bdigital.admin.ejb.controller.UserInfoServiceBean;
import co.bdigital.admin.ejb.controller.view.UserInfoServiceBeanLocal;
import co.bdigital.admin.messaging.services.changepassword.ChangePasswordRQType;
import co.bdigital.admin.messaging.services.changepassword.ChangePasswordServiceRequestType;
import co.bdigital.admin.messaging.services.createuser.CreateUserRQType;
import co.bdigital.admin.messaging.services.createuser.CreateUserServiceRequestType;
import co.bdigital.admin.messaging.services.deleteuser.DeleteUserRQType;
import co.bdigital.admin.messaging.services.deleteuser.DeleteUserServiceRequestType;
import co.bdigital.admin.messaging.services.updateuser.UpdateUserRQType;
import co.bdigital.admin.messaging.services.updateuser.UpdateUserServiceRequestType;
import co.bdigital.admin.model.PermissionByRol;
import co.bdigital.admin.model.StatusResponse;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.UserStatusEnum;
import co.bdigital.admin.util.UtilADM;
import co.bdigital.admin.util.WebConsoleUtil;
import co.bdigital.cmm.ejb.generic.ServiceControllerHelper;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.model.AwRol;
import co.bdigital.cmm.jpa.model.AwUsuario;
import co.bdigital.cmm.messaging.general.RequestBody;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;
import co.bdigital.shl.tracer.CustomLogger;

/**
 * 
 * @author juan.molinab
 *
 */
@ManagedBean(name = "userMGBean")
@RequestScoped
public class UserMGBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8898731768843376129L;

    /* Lista de los usuarios existentes */
    private List<AwUsuario> listUser;
    /* Objeto User para modificar o crear */
    private AwUsuario selectedUser;
    /* Lista de los roles */
    private List<SelectItem> selectOneItemRol;
    /* Contraseña del usuario */
    private String password;
    /* Logger */
    private CustomLogger logger;
    /* Lista de los posibles estados de un usuario */
    private List<SelectItem> selectOneItemStatus;
    @EJB
    private transient UserInfoServiceBeanLocal userInfoServiceBean;
    /* Codigo del pais */
    private String countryId;
    /* Usuario en sesion */
    private String userCurrent;
    /* Utilidad para realizar parseo de Objetos en String json */
    private transient ServiceControllerHelper sch;
    /* Informacion de los servicios a consumir */
    private static final String SERVICE_NAME = "UserManagementServices";
    private static final String SERVICE_VERSION = "1.0.0";
    @EJB
    private transient ParametersServiceBean parametersServiceBean;
    @EJB
    private transient CreateUserServiceBean createUserServiceBean;
    @EJB
    private transient UpdateUserServiceBean updateUserServiceBean;
    @EJB
    private transient DeleteUserServiceBean deleteUserServiceBean;
    @EJB
    private transient ChangePasswordServiceBean changePasswordServiceBean;
    private String firstNameToUpdate;
    private String lastNameToUpdate;
    private Long rolToUpdate;
    private String rolToUpdateName;
    private Integer userStatusToUpdate;
    private String mailUserToUpdate;
    private List<AwRol> roles;
    private boolean isAdmin;
    /* Permisos de la accion */
    private PermissionByRol permissionRol;

    /**
     * Constructor de la clase UserMGBean
     */
    public UserMGBean() {
        this.countryId = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.COUNTRY_ID);
        this.userCurrent = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.USER_NAME_SESION);
        logger = new CustomLogger(this.getClass());
        this.sch = ServiceControllerHelper.getInstance();
        this.selectedUser = new AwUsuario();
        this.selectedUser.setAwRol(new AwRol());
        this.listUser = new ArrayList<AwUsuario>();
        this.roles = new ArrayList<AwRol>();
        this.buildSelectOneItemStatus();
        permissionRol = (PermissionByRol) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.PERMISSIONS_BY_ROL_SESION);
        this.isAdmin = false;
    }

    /**
     * Metodo para devolver a la vista el rol con el codigo y descripcion
     * 
     * @return the selectOneItemRol
     */
    public List<SelectItem> getSelectOneItemRol() {
        this.selectOneItemRol = new ArrayList<SelectItem>();
        if (null == this.roles || this.roles.isEmpty()) {
            this.roles = getListRol();
        }
        getListUser();
        for (AwRol rol : this.roles) {
            SelectItem selectItem = null;

            if (!this.isAdmin && rol.getNombre()
                    .contains(ConstantADM.COMMON_STRING_ROL_ADMINISTRADOR)) {
                continue;
            }
            selectItem = new SelectItem(rol.getIdRol(), rol.getNombre());
            this.selectOneItemRol.add(selectItem);
        }
        return this.selectOneItemRol;
    }

    /**
     * Metodo para devolver a la vista el estado con codigo
     * 
     * @return the selectOneItemStatus
     */
    private void buildSelectOneItemStatus() {
        this.selectOneItemStatus = new ArrayList<SelectItem>();
        this.selectOneItemStatus
                .add(new SelectItem(UserStatusEnum.ACTIVE.getValue(),
                        UserStatusEnum.ACTIVE.getDesc()));
        this.selectOneItemStatus
                .add(new SelectItem(UserStatusEnum.INACTIVE.getValue(),
                        UserStatusEnum.INACTIVE.getDesc()));
    }

    /**
     * Metodo para retornar la lista de roles
     * 
     * @return lista de roles
     */
    private List<AwRol> getListRol() {
        List<AwRol> roles = new ArrayList<AwRol>();
        try {
            roles = UtilADM.getListRolSplitProfileName(
                    this.userInfoServiceBean.getListRol(this.countryId));
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_GET_LIST_ROL, e);
        }
        return roles;
    }

    /**
     * @return the selectedUser
     */
    public AwUsuario getSelectedUser() {
        return selectedUser;
    }

    /**
     * @param selectedUser
     *            the selectedUser to set
     */
    public void setSelectedUser(AwUsuario selectedUser) {
        this.selectedUser = selectedUser;
    }

    /**
     * @return the password1
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password1
     *            the password1 to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the listUser
     */
    public List<AwUsuario> getListUser() {
        try {
            this.listUser = getListRolSplitProfileName(
                    this.userInfoServiceBean.getUsers(this.countryId));

            List<AwUsuario> listUserNoAdmin = new ArrayList<AwUsuario>();
            for (AwUsuario user : this.listUser) {
                if (user.getMail().equals(userCurrent)
                        && user.getAwRol().getNombre().contains(
                                ConstantADM.COMMON_STRING_ROL_ADMINISTRADOR)) {
                    this.isAdmin = true;
                }
                if (!user.getAwRol().getNombre().contains(
                        ConstantADM.COMMON_STRING_ROL_ADMINISTRADOR)) {
                    listUserNoAdmin.add(user);
                }
            }
            if (!this.isAdmin) {
                this.listUser = listUserNoAdmin;
            }

        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_GET_LIST_USER, e);
        }
        return listUser;
    }

    /**
     * Metodo para separar el nombre del perfil y no mostrarlo con la region
     * 
     * @param roles
     * @return
     */
    private List<AwUsuario> getListRolSplitProfileName(List<AwUsuario> user) {
        Pattern pattern = Pattern.compile(ConstantADM.STRING_SPLIT_POINT);
        for (AwUsuario userObject : user) {
            String[] split = pattern.split(userObject.getAwRol().getNombre(),
                    0);
            if (split.length != 0) {
                userObject.getAwRol().setNombre(split[0]);
            }
        }
        return user;
    }

    /**
     * Metodo para redireccionar a la pagina de crear usuario
     */
    public void redirectCreateUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        String ruta = UtilADM.basepathlogin()
                + ConstantADM.URL_REDIRECT_CREATE_USER;
        try {
            context.getExternalContext().redirect(ruta);
        } catch (IOException e) {
            logger.error(ConstantADM.ERROR_REDIRECT_CREATE_USER, e);
        }
    }

    /**
     * Metodo para redireccionar a la pagina de la administracion de usuarios
     */
    public void redirectListUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        String ruta = UtilADM.basepathlogin()
                + ConstantADM.URL_REDIRECT_MANAGE_USER;
        try {
            context.getExternalContext().redirect(ruta);
        } catch (IOException e) {
            logger.error(ConstantADM.ERROR_REDIRECT_CREATE_USER, e);
        }
    }

    /**
     * Metodo para guardar un usuario en LDAP y en BD
     */
    public void saveUser() {
        FacesMessage message = null;
        FacesContext contextFaces = FacesContext.getCurrentInstance();

        try {
            RequestMessageObjectType request = UtilADM.getRequestObject(
                    SERVICE_NAME, ConstantADM.SERVICE_OPERATION_CREATE_USER,
                    this.countryId, SERVICE_VERSION);

            RequestBody requestBody = new RequestBody();
            CreateUserServiceRequestType requestType = createUserRQ(
                    this.selectedUser);
            requestBody.setAny(requestType);
            request.getRequestMessage().setRequestBody(requestBody);

            ResponseMessageObjectType response = this.createUserServiceBean
                    .executeOperation(request);

            StatusResponse status = WebConsoleUtil.getResponseLocal(response);
            if (status.getStatusCode().equals(Constant.COMMON_STRING_ZERO)) {
                contextFaces.getExternalContext().getSessionMap()
                        .put(ConstantADM.IS_CHANGE_PASS, false);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantADM.SAVE_EXIT_USER,
                        ConstantADM.SAVE_EXIT_USER_DESC);
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        ConstantADM.SAVE_ERROR_USER,
                        ConstantADM.SAVE_ERROR_USER_DESC);
            }
        } catch (Exception ex) {
            logger.error(ConstantADM.ERROR_EXECUTE_SERVICE_REST + " "
                    + ConstantADM.SERVICE_OPERATION_CREATE_USER, ex);
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.SAVE_ERROR_USER,
                    ConstantADM.SAVE_ERROR_USER_DESC);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Metodo para formar el body del metodo CreateUser
     * 
     * @param userSave
     * @return
     */
    public CreateUserServiceRequestType createUserRQ(AwUsuario userSave) {
        CreateUserServiceRequestType requestType = new CreateUserServiceRequestType();
        CreateUserRQType userRQ = new CreateUserRQType();
        userRQ.setCellphone(userSave.getCelular().toString());
        userRQ.setNames(userSave.getNombres());
        userRQ.setLastName(userSave.getApellidos());
        userRQ.setMail(userSave.getMail());
        userRQ.setRole(Long.toString(userSave.getAwRol().getIdRol()));
        userRQ.setCurrentUser(this.userCurrent);
        requestType.setCreateUserRQ(userRQ);
        return requestType;
    }

    /**
     * Metodo para actualizar un usuario
     */
    public void updateUser() {
        FacesMessage message = null;
        try {
            RequestMessageObjectType request = UtilADM.getRequestObject(
                    SERVICE_NAME, ConstantADM.SERVICE_OPERATION_UPDATE_USER,
                    this.countryId, SERVICE_VERSION);

            RequestBody requestBody = new RequestBody();
            UpdateUserServiceRequestType requestType = updateUserRQ();
            requestBody.setAny(requestType);
            request.getRequestMessage().setRequestBody(requestBody);

            ResponseMessageObjectType response = this.updateUserServiceBean
                    .executeOperation(request);

            StatusResponse status = WebConsoleUtil.getResponseLocal(response);
            if (status.getStatusCode().equals(Constant.COMMON_STRING_ZERO)) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantADM.SAVE_EXIT_USER,
                        ConstantADM.UPDATE_EXIT_USER_DESC);
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantADM.SAVE_ERROR_USER,
                        ConstantADM.UPDATE_ERROR_USER_DESC);
            }
        } catch (Exception ex) {
            logger.error(ConstantADM.ERROR_EXECUTE_SERVICE_REST + " "
                    + ConstantADM.SERVICE_OPERATION_UPDATE_USER, ex);
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Metodo para formar el body del metodo UpdateUser
     * 
     * @param userSave
     * @return
     */
    public UpdateUserServiceRequestType updateUserRQ() {
        UpdateUserServiceRequestType requestType = new UpdateUserServiceRequestType();
        UpdateUserRQType userRQ = new UpdateUserRQType();
        userRQ.setNames(this.firstNameToUpdate);
        userRQ.setLastName(this.lastNameToUpdate);
        userRQ.setMail(this.mailUserToUpdate);
        userRQ.setStatus(String.valueOf(this.userStatusToUpdate));
        userRQ.setCurrentUser(this.userCurrent);
        userRQ.setRole(Long.toString(this.rolToUpdate));
        requestType.setUpdateUserRQ(userRQ);
        return requestType;

    }

    /**
     * Metodo para realizar un borrado logico del usuario
     */
    public void deleteUser() {
        FacesMessage message = null;

        try {
            RequestMessageObjectType request = UtilADM.getRequestObject(
                    SERVICE_NAME, ConstantADM.SERVICE_OPERATION_DELETE_USER,
                    countryId, SERVICE_VERSION);

            RequestBody requestBody = new RequestBody();
            DeleteUserServiceRequestType requestType = this
                    .deleteUserRQ(this.selectedUser.getMail());
            requestBody.setAny(requestType);
            request.getRequestMessage().setRequestBody(requestBody);

            ResponseMessageObjectType response = this.deleteUserServiceBean
                    .executeOperation(request);

            StatusResponse status = WebConsoleUtil.getResponseLocal(response);
            if (status.getStatusCode().equals(Constant.COMMON_STRING_ZERO)) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantADM.SAVE_EXIT_USER,
                        ConstantADM.DELETE_EXIT_USER_DESC);
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantADM.SAVE_ERROR_USER,
                        ConstantADM.DELETE_ERROR_USER_DESC);
            }
        } catch (Exception ex) {
            logger.error(ConstantADM.ERROR_EXECUTE_SERVICE_REST + " "
                    + ConstantADM.SERVICE_OPERATION_UPDATE_USER, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * <p>
     * M&eacute;todo para formar el body de la eliminacion de usuario.
     * </p>
     * 
     * @param mail
     * @return
     */
    public DeleteUserServiceRequestType deleteUserRQ(String mail) {
        DeleteUserServiceRequestType requestType = new DeleteUserServiceRequestType();
        DeleteUserRQType userRQ = new DeleteUserRQType();
        userRQ.setMail(mail);
        userRQ.setCurrentUser(this.userCurrent);
        requestType.setDeleteUserRQ(userRQ);
        return requestType;
    }

    /**
     * <p>
     * M&eacute;todo para realizar el cambio de contraseña del usuario.
     * </p>
     */
    public void changePassword() {
        FacesMessage message = null;
        FacesContext contextFaces = FacesContext.getCurrentInstance();
        try {
            RequestMessageObjectType request = UtilADM.getRequestObject(
                    SERVICE_NAME,
                    ConstantADM.REST_SERVICE_OPERATION_CHANGE_PASS,
                    this.countryId, SERVICE_VERSION);

            RequestBody requestBody = new RequestBody();
            ChangePasswordServiceRequestType requestType = changePassRQ();
            requestBody.setAny(requestType);
            request.getRequestMessage().setRequestBody(requestBody);

            ResponseMessageObjectType response = this.changePasswordServiceBean
                    .executeOperation(request);

            StatusResponse status = WebConsoleUtil.getResponseLocal(response);
            if (status.getStatusCode().equals(Constant.COMMON_STRING_ZERO)) {
                contextFaces.getExternalContext().getSessionMap()
                        .put(ConstantADM.IS_CHANGE_PASS, false);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantADM.SAVE_EXIT_USER,
                        ConstantADM.CHANGE_PASS_EXIT);
                this.redirectHome();
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        ConstantADM.SAVE_ERROR_USER,
                        ConstantADM.CHANGE_PASS_ERROR);
            }
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.SAVE_ERROR_USER, ConstantADM.CHANGE_PASS_ERROR);
            logger.error(
                    ConstantADM.ERROR_EXECUTE_SERVICE_REST
                            + ConstantADM.STRING_EMPTY_VALUE
                            + ConstantADM.REST_SERVICE_OPERATION_CHANGE_PASS,
                    ex);
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * <p>
     * M&eacute;todo para formar el body del mensaje para realizar cambio de
     * password.
     * </p>
     * 
     * @return
     */
    public ChangePasswordServiceRequestType changePassRQ() {
        ChangePasswordServiceRequestType requestType = new ChangePasswordServiceRequestType();
        ChangePasswordRQType changeRQ = new ChangePasswordRQType();
        changeRQ.setEmail(this.userCurrent);
        changeRQ.setCurrentPassword(this.userCurrent);
        changeRQ.setNewPassword(this.password);
        requestType.setChangePasswordRQ(changeRQ);
        return requestType;
    }

    /**
     * <p>
     * M&eacute;todo para redireccionar a la pagina de Home.
     * </p>
     * 
     * @throws IOException
     */
    public void redirectHome() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        String ruta = UtilADM.basepathlogin()
                + ConstantADM.URL_REDIRECT_MANAGE_USER;
        context.getExternalContext().redirect(ruta);
    }

    /**
     * <p>
     * M&eacute;todo para limpiar los campos del formulario de creacion de
     * usuario.
     * </p>
     */
    public void cleanFormCreateUser() {
        RequestContext.getCurrentInstance()
                .reset(ConstantADM.CLEAN_FORM_SAVE_USER);
    }

    /**
     * <p>
     * M&eacute;todo que inicializa los valores de los campos para la ventana de
     * edici&oacute;n de datos del usuario.
     * </p>
     * 
     * @param user
     */
    public void editUser(AwUsuario user) {
        this.firstNameToUpdate = user.getNombres();
        this.lastNameToUpdate = user.getApellidos();
        this.rolToUpdate = user.getAwRol().getIdRol();
        this.rolToUpdateName = user.getAwRol().getNombre();
        this.userStatusToUpdate = (int) (null != user.getEstado()
                && user.getEstado() ? Constant.COMMON_INT_UNO
                        : Constant.INT_ZERO);
        this.mailUserToUpdate = user.getMail();
    }

    /**
     * <p>
     * Utilidad que reinicia el estado de los campos de la ventana de
     * edici&oacute;n de datos del usuario.
     * </p>
     */
    public void resetEditUserDialog() {
        RequestContext.getCurrentInstance()
                .reset(ConstantADM.UPDATE_USER_DIALOG_ID);
    }

    /**
     * @return the selectOneItemStatus
     */
    public List<SelectItem> getSelectOneItemStatus() {
        return selectOneItemStatus;
    }

    /**
     * @param selectOneItemStatus
     *            the selectOneItemStatus to set
     */
    public void setSelectOneItemStatus(List<SelectItem> selectOneItemStatus) {
        this.selectOneItemStatus = selectOneItemStatus;
    }

    /**
     * @return the firstNameToUpdate
     */
    public String getFirstNameToUpdate() {
        return firstNameToUpdate;
    }

    /**
     * @param firstNameToUpdate
     *            the firstNameToUpdate to set
     */
    public void setFirstNameToUpdate(String firstNameToUpdate) {
        this.firstNameToUpdate = firstNameToUpdate;
    }

    /**
     * @return the lastNameToUpdate
     */
    public String getLastNameToUpdate() {
        return lastNameToUpdate;
    }

    /**
     * @param lastNameToUpdate
     *            the lastNameToUpdate to set
     */
    public void setLastNameToUpdate(String lastNameToUpdate) {
        this.lastNameToUpdate = lastNameToUpdate;
    }

    /**
     * @return the rolToUpdate
     */
    public Long getRolToUpdate() {
        return rolToUpdate;
    }

    /**
     * @param rolToUpdate
     *            the rolToUpdate to set
     */
    public void setRolToUpdate(Long rolToUpdate) {
        this.rolToUpdate = rolToUpdate;
    }

    /**
     * @return the userStatusToUpdate
     */
    public Integer getUserStatusToUpdate() {
        return userStatusToUpdate;
    }

    /**
     * @param userStatusToUpdate
     *            the userStatusToUpdate to set
     */
    public void setUserStatusToUpdate(Integer userStatusToUpdate) {
        this.userStatusToUpdate = userStatusToUpdate;
    }

    /**
     * @return the mailUserToUpdate
     */
    public String getMailUserToUpdate() {
        return mailUserToUpdate;
    }

    /**
     * @param mailUserToUpdate
     *            the mailUserToUpdate to set
     */
    public void setMailUserToUpdate(String mailUserToUpdate) {
        this.mailUserToUpdate = mailUserToUpdate;
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
