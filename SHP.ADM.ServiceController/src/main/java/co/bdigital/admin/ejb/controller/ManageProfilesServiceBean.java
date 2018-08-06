package co.bdigital.admin.ejb.controller;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.bdigital.admin.ejb.controller.view.ManageProfilesServiceBeanLocal;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AwAccionesPorRole;
import co.bdigital.cmm.jpa.model.AwRol;
import co.bdigital.cmm.jpa.service.impl.AWProfileJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.AWRolJPAServiceIMPL;
import co.bdigital.cmm.jpa.services.AWProfileJPAService;
import co.bdigital.cmm.jpa.services.AWRolJPAService;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * 
 * @author juan.molinab
 *
 */
@Stateless
@Local(ManageProfilesServiceBeanLocal.class)
public class ManageProfilesServiceBean
        implements ManageProfilesServiceBeanLocal {

    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;
    private AWProfileJPAService profileJPA;
    private AWRolJPAService rolJPA;
    private CustomLogger logger;

    public ManageProfilesServiceBean() {
        logger = new CustomLogger(ManageProfilesServiceBean.class);
        profileJPA = new AWProfileJPAServiceIMPL();
        rolJPA = new AWRolJPAServiceIMPL();
    }

    /**
     * Metodo para crear un nuevo perfil
     * 
     * @return boolean - true si pudo insertar - false si no pudo insertar
     * @throws Exception
     */
    public AwRol createProfile(AwRol rol) throws Exception {
        AwRol getRol = null;
        try {
            getRol = profileJPA.createProfile(rol, em);
        } catch (Exception e) {
            logger.error(ErrorEnum.DB_ERROR,
                    Constant.ERROR_MESSAGE_DB_CREATE_PROFILE, e);
        }
        return getRol;
    }

    /**
     * Metodo para asociar los permisos a un rol
     * 
     * @param actionByRol
     * @return
     * @throws Exception
     */
    public boolean addPermissionsToProfile(List<AwAccionesPorRole> actionByRol)
            throws Exception {
        boolean isCreate = false;
        for (AwAccionesPorRole awAccionesPorRole : actionByRol) {
            isCreate = profileJPA.addPermissionsToProfile(awAccionesPorRole,
                    em);
            if (false == isCreate) {
                isCreate = false;
                break;
            }
        }
        return isCreate;
    }

    /**
     * Consulta de rol por la llave primaria <code>idRol</code>.
     * 
     * @param awRolId
     * @param em
     * @return <code>AwRol</code>
     * @throws JPAException
     */
    public AwRol findAwRolById(Long awRolId) throws JPAException {
        return rolJPA.findAwRolById(awRolId, em);
    }

    /**
     * Metodo para eliminar todos los permisos de un rol determinado
     * 
     * @param idRol
     * @param em
     * @return
     * @throws JPAException
     */
    public boolean deleteAllPermissionsByRol(long idRol) throws Exception {
        boolean isDelete = false;
        try {
            isDelete = profileJPA.deleteAllPermissionsByRol(idRol, em);
        } catch (Exception e) {
            logger.error(ErrorEnum.DB_ERROR,
                    Constant.ERROR_MESSAGE_DB_DELETE_PERMISSIONS_BY_ROL, e);
        }
        return isDelete;
    }

}
