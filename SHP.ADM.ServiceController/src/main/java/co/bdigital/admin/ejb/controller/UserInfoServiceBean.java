package co.bdigital.admin.ejb.controller;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.bdigital.admin.ejb.controller.view.UserInfoServiceBeanLocal;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.model.AwRol;
import co.bdigital.cmm.jpa.model.AwUsuario;
import co.bdigital.cmm.jpa.service.impl.AWRolJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.AWUserJPAServiceIMPL;
import co.bdigital.cmm.jpa.services.AWRolJPAService;
import co.bdigital.cmm.jpa.services.AWUserJPAService;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * 
 * @author juan.molinab
 *
 */
@Stateless(mappedName = "UserInfoServiceBean")
@Local(UserInfoServiceBeanLocal.class)
public class UserInfoServiceBean implements UserInfoServiceBeanLocal {

    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;
    private AWUserJPAService userJPA;
    private AWRolJPAService rolJPA;
    private CustomLogger logger;

    public UserInfoServiceBean() {
        logger = new CustomLogger(UserInfoServiceBean.class);
        userJPA = new AWUserJPAServiceIMPL();
        rolJPA = new AWRolJPAServiceIMPL();
    }

    /**
     * Metodo para devolver la lista de usuarios del sistema
     * 
     * @param countryId
     */
    public List<AwUsuario> getUsers(String countryId) {

        List<AwUsuario> users = null;
        try {
            users = userJPA.getAWUsers(countryId, em);
        } catch (Exception e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
        }
        return users;
    }

    /**
     * Metodo para devolver la lista de roles
     * 
     * @param countryId
     * @return
     * @throws Exception
     */
    public List<AwRol> getListRol(String countryId) {

        List<AwRol> rol = null;
        try {
            rol = rolJPA.getListRol(countryId, em);
        } catch (Exception e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
        }
        return rol;
    }

    /**
     * Metodo para encontrar un usuario por medio de su email
     * 
     * @param email
     * @return
     * @throws Exception
     */
    public AwUsuario getUserByEmail(String email) {

        AwUsuario user = null;
        try {
            user = userJPA.getUserByEmail(email, em);
        } catch (Exception e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
        }

        return user;
    }

}
