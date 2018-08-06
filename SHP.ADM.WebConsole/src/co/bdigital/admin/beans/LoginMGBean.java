package co.bdigital.admin.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import co.bdigital.admin.ejb.controller.AuditServiceBean;
import co.bdigital.admin.ejb.controller.UserInfoServiceBean;
import co.bdigital.admin.ejb.controller.view.AuditServiceBeanLocal;
import co.bdigital.admin.ejb.controller.view.UserInfoServiceBeanLocal;
import co.bdigital.admin.model.ActionRol;
import co.bdigital.admin.model.PermissionByRol;
import co.bdigital.admin.model.UserLogin;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.UtilADM;
import co.bdigital.cmm.jpa.model.AwAccion;
import co.bdigital.cmm.jpa.model.AwAccionesPorRole;
import co.bdigital.cmm.jpa.model.AwUsuario;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 *
 * @author juan.molinab
 */
@ManagedBean(name = "loginMGBean")
@SessionScoped
public class LoginMGBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private CustomLogger logger;
    private UserLogin user;
    private String userName;
    private String region;
    @EJB
    private transient UserInfoServiceBeanLocal userInfoServiceBean;
    @EJB
    private transient AuditServiceBeanLocal auditServiceBean;

    public LoginMGBean() {

        logger = new CustomLogger(this.getClass());
        if (null == this.user) {
            this.user = new UserLogin();
        }
    }

    public UserLogin getUser() {
        return user;
    }

    public void setUser(UserLogin user) {
        this.user = user;
    }

    /**
     * Metodo para autenticar el usuario contra el repositorio federado del WAS
     * 
     */
    public void login() {
        UserLogin currentUser = this.user;
        String urlServlet = ConstantADM.URL_SERVLET_AUTHENTICATION;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesMessage message;
        boolean loggedIn;
        StringBuilder ruta = new StringBuilder();

        facesContext.getExternalContext().getRequestMap()
                .put(ConstantADM.J_USER_NAME, currentUser.getUserName());
        facesContext.getExternalContext().getRequestMap()
                .put(ConstantADM.J_PASS, currentUser.getPassword());

        try {
            facesContext.getExternalContext().dispatch(urlServlet);
        } catch (IOException e) {
            logger.error(ConstantADM.LOGIN_SERVLET_ERROR_AUTHENTICATION, e);
        }

        loggedIn = (Boolean) facesContext.getExternalContext().getRequestMap()
                .get(ConstantADM.IS_LOGIN);

        if (loggedIn) {
            boolean isChangePassword = false;

            AwUsuario userByUserName = this
                    .getUserByEmail(currentUser.getUserName());

            /*
             * Si el usuario esta inactivo (1 - Inactivo, 0 - Activo)
             */
            if (userByUserName.getEstado()) {

                this.closeSession();

                loggedIn = Boolean.FALSE;
                facesContext.getExternalContext().getSessionMap()
                        .put(ConstantADM.USER_NAME_SESION, null);
                message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                        ConstantADM.LOGIN_ERROR, ConstantADM.DISABLED_USER);

            } else {

                facesContext.getExternalContext().getSessionMap().put(
                        ConstantADM.USER_NAME_SESION,
                        currentUser.getUserName());
                setUserName(currentUser.getUserName());

                // Preguntamos si toca realizar cambio de password
                if (userByUserName.getMustChangePwd()) {
                    isChangePassword = true;
                }
                facesContext.getExternalContext().getSessionMap()
                        .put(ConstantADM.IS_CHANGE_PASS, isChangePassword);
                List<AwAccion> actions = userByUserName.getAwRol()
                        .getAwAccions();
                List<ActionRol> actionsByRol = getActionByRol(actions,
                        userByUserName.getAwRol().getAwAccionesPorRoles());
                // Se obtiene el pais del usuario en sesion y se guarda en
                // sesion
                FacesContext.getCurrentInstance().getExternalContext()
                        .getSessionMap()
                        .put(ConstantADM.COUNTRY_ID, userByUserName
                                .getDivisionGeografica().getCodigoDivision());
                setRegion(userByUserName.getDivisionGeografica()
                        .getCodigoDivision());
                // Ordenamos el arbol en orden ascendente
                if (!actionsByRol.isEmpty()) {
                    Collections.sort(actionsByRol, new Comparator<ActionRol>() {
                        public int compare(ActionRol o1, ActionRol o2) {
                            return o1.getName().compareTo(o2.getName());
                        }
                    });
                }
                // Se obtiene los privilegios de navegacion del usuario en
                // sesion

                facesContext.getExternalContext().getSessionMap()
                        .put(ConstantADM.ACTIONS_BY_ROL_SESION, actionsByRol);
                // Guardamos evento en Auditoria
                try {
                    this.auditServiceBean.insertAudit(
                            ConstantADM.ID_ACTION_LOGIN,
                            currentUser.getUserName(), UtilADM.getCurrentDate(),
                            ConstantADM.DESC_ACTION_LOGIN);
                } catch (Exception e) {
                    logger.error(ConstantADM.ERROR_SAVE_AUDIT, e);
                }
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantADM.WELCOME_MESSAGE, currentUser.getUserName());
                // Logica cuando se debe cambiar la contraseña
                if (!isChangePassword) {
                    if (!actionsByRol.isEmpty()) {
                        String urlRedirect = ConstantADM.CONTEXT_URL_PAGES
                                + actionsByRol.get(0).getUrl();
                        ruta.append(UtilADM.basepathlogin())
                                .append(urlRedirect);
                    } else {
                        this.closeSession();
                        loggedIn = Boolean.FALSE;
                        facesContext.getExternalContext().getSessionMap()
                                .put(ConstantADM.USER_NAME_SESION, null);
                        message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                                ConstantADM.LOGIN_ERROR,
                                ConstantADM.NOT_FOUND_ROL_USER);
                    }

                } else {
                    ruta.append(UtilADM.basepathlogin())
                            .append(ConstantADM.URL_REDIRECT_CHANGE_PASS);
                    facesContext.getExternalContext().getSessionMap()
                            .put(ConstantADM.FIRST_CHANGE_PASS, true);
                }
            }

        } else {

            facesContext.getExternalContext().getSessionMap()
                    .put(ConstantADM.USER_NAME_SESION, null);
            message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    ConstantADM.LOGIN_ERROR, ConstantADM.WRONG_USER);
        }

        this.user = new UserLogin();
        facesContext.addMessage(null, message);
        requestContext.addCallbackParam(ConstantADM.LOGGED_IN, loggedIn);
        requestContext.addCallbackParam(ConstantADM.URL, ruta.toString());

    }

    private void closeSession() {
        HttpServletRequest origRequest = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();

        try {
            origRequest.logout();
        } catch (ServletException e) {
            logger.error(ErrorEnum.LDAP_ERROR,
                    ConstantADM.LOGIN_SERVLET_ERROR_AUTHENTICATION
                            + e.getMessage(),
                    e);
        }

        HttpSession session = origRequest.getSession();

        if (null != session) {
            session.invalidate();
        }
    }

    /**
     * <p>
     * Metodo que se encarga de invalidar la sesi&oacute;n HTTP.
     * </p>
     */
    private void sessionLogout() {
        ExternalContext context = FacesContext.getCurrentInstance()
                .getExternalContext();

        if (null != context) {
            context.invalidateSession();
        }
    }

    /**
     * Metodo para salir de la aplicacion e invalidar la sesion del usuario
     */
    public void logout() {

        StringBuilder ruta = new StringBuilder();
        ruta.append(UtilADM.basepathlogin()).append(ConstantADM.URL_LOGIN);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String urlServlet = ConstantADM.URL_SERVLET_LOGOUT;
        String rutaRedirect = UtilADM.basepathlogin() + ConstantADM.URL_LOGIN;

        try {
            facesContext.getExternalContext().dispatch(urlServlet);
            facesContext.getExternalContext().redirect(rutaRedirect);
        } catch (IOException e) {
            logger.error(ConstantADM.LOGGED_OUT, e);
        }
        this.sessionLogout();

    }

    /**
     * Metodo para traer un usuario por su email
     * 
     * @param email
     * @return
     */
    public AwUsuario getUserByEmail(String email) {

        try {
            return this.userInfoServiceBean.getUserByEmail(email);
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_GET_USER_BY_EMAIL, e);
        }
        return null;
    }

    /**
     * Metodo para devolver las acciones por rol en un objecto tipo ActionRol
     * 
     * @param actions
     * @return
     */
    public List<ActionRol> getActionByRol(List<AwAccion> actions,
            List<AwAccionesPorRole> listActionsByRol) {

        PermissionByRol permissions = null;
        HashMap<String, PermissionByRol> mapPermissionByRol = new HashMap<>();
        List<ActionRol> listActions = new ArrayList<>();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        for (AwAccion act : actions) {
            permissions = new PermissionByRol();
            for (AwAccionesPorRole actionRol : listActionsByRol) {
                if (act.getIdAccion() == actionRol.getAwAccion()
                        .getIdAccion()) {
                    if (null != actionRol.getConsulta()) {
                        permissions.setRead(actionRol.getConsulta().intValue());
                    }
                    if (null != actionRol.getModificacion()) {
                        permissions.setWrite(
                                actionRol.getModificacion().intValue());
                    }
                    mapPermissionByRol.put(act.getValor(), permissions);
                    listActions.add(new ActionRol(act.getNombre(),
                            act.getValor(), act.getIdAccion()));
                    break;
                }
            }
        }

        // Se guarda en sesion los privilegios con su url de accion
        // correspondiente
        facesContext.getExternalContext().getSessionMap().put(
                ConstantADM.MAP_PERMISSIONS_BY_ROL_SESION, mapPermissionByRol);

        return listActions;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region
     *            the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

}
