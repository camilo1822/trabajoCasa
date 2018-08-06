package co.bdigital.admin.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import co.bdigital.admin.messaging.services.getVeriAttempts.AttemptsType;
import co.bdigital.admin.messaging.validatecustomer.CustomerType;
import co.bdigital.admin.model.PermissionByRol;
import co.bdigital.shl.tracer.CustomLogger;

/**
 *
 * @author juan.molinab
 */
public class AuthorizationListener implements PhaseListener {

    /**
     * 
     */
    private static final long serialVersionUID = 6394696824187241944L;
    private CustomLogger logger;

    /**
     * Constructor de la clase AuthorizationListener
     */
    public AuthorizationListener() {
        logger = new CustomLogger(this.getClass());
    }

    @Override
    public void afterPhase(PhaseEvent event) {

        FacesContext facesContext = event.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();
        boolean isLoginPage = (currentPage.lastIndexOf(ConstantADM.URL_LOGIN) > -1) ? true
                : false;
        boolean isChangePassPage = (currentPage
                .lastIndexOf(ConstantADM.URL_CHANGE_PASS) > -1) ? true : false;
        HttpSession session = (HttpSession) facesContext.getExternalContext()
                .getSession(false);

        if (null != session) {
            updateFirstVarSesion(false);
            boolean isChangePassword = (boolean) (session
                    .getAttribute(ConstantADM.IS_CHANGE_PASS) != null ? session
                    .getAttribute(ConstantADM.IS_CHANGE_PASS) : false);
            boolean isFirstChangePass = (boolean) (session
                    .getAttribute(ConstantADM.FIRST_CHANGE_PASS) != null ? session
                    .getAttribute(ConstantADM.FIRST_CHANGE_PASS) : false);
            Object usuario = session.getAttribute(ConstantADM.USER_NAME_SESION);

            if (isChangePassword && !isChangePassPage) {
                isFirstChangePass = true;
            }
            /**
             * Se borra la lista de Control de Sesion cuando nos dirigimos a una
             * pagina diferente
             **/

            if (!isLoginPage && usuario != null) {
                boolean isControlListPage = (currentPage
                        .lastIndexOf(ConstantADM.URL_CONTROL_LIST) > -1) ? true
                        : false;
                boolean isResetBiometryPage = (currentPage
                        .lastIndexOf(ConstantADM.URL_RESET_BIOMETRY) > -1) ? true
                        : false;
                @SuppressWarnings("unchecked")
                HashMap<String, PermissionByRol> mapPermissionByRol = (HashMap<String, PermissionByRol>) FacesContext
                        .getCurrentInstance().getExternalContext()
                        .getSessionMap()
                        .get(ConstantADM.MAP_PERMISSIONS_BY_ROL_SESION);
                setPermissionByRolInView(mapPermissionByRol, currentPage);
                if (!isControlListPage) {
                    @SuppressWarnings("unchecked")
                    ArrayList<CustomerType> customerListSesion = (ArrayList<CustomerType>) FacesContext
                            .getCurrentInstance().getExternalContext()
                            .getSessionMap()
                            .get(ConstantADM.CUSTOMER_LIST_CONTROL);
                    if (null != customerListSesion
                            && !customerListSesion.isEmpty()) {
                        FacesContext.getCurrentInstance().getExternalContext()
                                .getSessionMap()
                                .put(ConstantADM.CUSTOMER_LIST_CONTROL, null);
                    }
                }
                if (!isResetBiometryPage) {
                    @SuppressWarnings("unchecked")
                    ArrayList<AttemptsType> listAttemptsSesion = (ArrayList<AttemptsType>) FacesContext
                            .getCurrentInstance().getExternalContext()
                            .getSessionMap()
                            .get(ConstantADM.LIST_ATTEMPTS_SESION);
                    String showFirstButon = (String) FacesContext
                            .getCurrentInstance().getExternalContext()
                            .getSessionMap().get(ConstantADM.SHOW_FIRST_BUTTON);
                    String showLastButon = (String) FacesContext
                            .getCurrentInstance().getExternalContext()
                            .getSessionMap().get(ConstantADM.SHOW_LAST_BUTTON);
                    String urlImgOld = (String) FacesContext
                            .getCurrentInstance().getExternalContext()
                            .getSessionMap()
                            .get(ConstantADM.URL_IMG_OLD_SESION);
                    String urlImgNew = (String) FacesContext
                            .getCurrentInstance().getExternalContext()
                            .getSessionMap()
                            .get(ConstantADM.URL_IMG_NEW_SESION);
                    if (null != showFirstButon
                            && Boolean.TRUE.toString().equalsIgnoreCase(
                                    showFirstButon)) {
                        FacesContext
                                .getCurrentInstance()
                                .getExternalContext()
                                .getSessionMap()
                                .put(ConstantADM.SHOW_FIRST_BUTTON,
                                        Boolean.FALSE.toString());
                    }
                    if (null != showLastButon
                            && Boolean.TRUE.toString().equalsIgnoreCase(
                                    showLastButon)) {
                        FacesContext
                                .getCurrentInstance()
                                .getExternalContext()
                                .getSessionMap()
                                .put(ConstantADM.SHOW_LAST_BUTTON,
                                        Boolean.FALSE.toString());
                    }
                    if (null != listAttemptsSesion
                            && !listAttemptsSesion.isEmpty()) {
                        FacesContext.getCurrentInstance().getExternalContext()
                                .getSessionMap()
                                .put(ConstantADM.LIST_ATTEMPTS_SESION, null);
                    }
                    if (null != urlImgOld
                            && !ConstantADM.STRING_EMPTY.equals(urlImgOld)) {
                        FacesContext
                                .getCurrentInstance()
                                .getExternalContext()
                                .getSessionMap()
                                .put(ConstantADM.URL_IMG_OLD_SESION,
                                        ConstantADM.STRING_EMPTY);
                    }
                    if (null != urlImgNew
                            && !ConstantADM.STRING_EMPTY.equals(urlImgNew)) {
                        FacesContext
                                .getCurrentInstance()
                                .getExternalContext()
                                .getSessionMap()
                                .put(ConstantADM.URL_IMG_NEW_SESION,
                                        ConstantADM.STRING_EMPTY);
                    }
                }
            }

            if (!isLoginPage && usuario == null) {
                NavigationHandler nh = facesContext.getApplication()
                        .getNavigationHandler();
                nh.handleNavigation(facesContext, null,
                        ConstantADM.URL_LOGIN_AUTHO);
            }

            // Se redirecciona al password para obligar al cambio, antes de
            // entrar a
            // otro lado
            if (isChangePassword && usuario != null && isFirstChangePass) {
                FacesContext context = FacesContext.getCurrentInstance();
                String ruta = UtilADM.basepathlogin()
                        + ConstantADM.URL_REDIRECT_CHANGE_PASS;
                try {
                    context.getExternalContext().redirect(ruta);
                } catch (IOException e) {
                    logger.error(ConstantADM.ERROR_REDIRECT_CHANGE_PASS, e);
                }
                FacesContext.getCurrentInstance().getExternalContext()
                        .getSessionMap()
                        .put(ConstantADM.FIRST_CHANGE_PASS, false);
            }
        }
    }

    /**
     * Metodo para ejecutar antes de realizar cualquier peticion
     */
    public void beforePhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        HttpSession session = (HttpSession) facesContext.getExternalContext()
                .getSession(false);
        if (session == null) {
            updateFirstVarSesion(true);
        }
        boolean variable = getFirsVarSesion();
        if (null == session && variable) {
            NavigationHandler nh = facesContext.getApplication()
                    .getNavigationHandler();
            nh.handleNavigation(facesContext, null, ConstantADM.URL_LOGIN_AUTHO);
        }
    }

    /**
     * Metodo para actualizar un flag de sesion, para cuando esta expire
     * 
     * @param varSesion
     */
    public void updateFirstVarSesion(boolean varSesion) {
        FacesContext contextFaces = FacesContext.getCurrentInstance();
        contextFaces.getExternalContext().getRequestMap()
                .put(ConstantADM.FIRST_VAR_SESION, varSesion);
    }

    /**
     * Metodo que retorna flag de sesion para saber cuando la sesion expira
     * 
     * @return
     */
    public boolean getFirsVarSesion() {
        FacesContext contextFaces = FacesContext.getCurrentInstance();
        boolean firstVarSesion = false;
        try {
            firstVarSesion = (boolean) contextFaces.getExternalContext()
                    .getRequestMap().get(ConstantADM.FIRST_VAR_SESION);
        } catch (Exception e) {
            firstVarSesion = false;
        }
        return firstVarSesion;
    }

    /**
     * Metodo para guardar en sesion el permiso de rol en la pagina solicitada
     * 
     * @param map
     */
    public void setPermissionByRolInView(
            Map<String, PermissionByRol> mapPermissionByRol, String currentPage) {
        int lastIndex = currentPage.lastIndexOf("/") + 1;
        String page = currentPage.substring(lastIndex);
        PermissionByRol rol = mapPermissionByRol.get(page);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put(ConstantADM.PERMISSIONS_BY_ROL_SESION, rol);
    }

    @Override
    public PhaseId getPhaseId() {

        return PhaseId.RESTORE_VIEW;
    }
}
