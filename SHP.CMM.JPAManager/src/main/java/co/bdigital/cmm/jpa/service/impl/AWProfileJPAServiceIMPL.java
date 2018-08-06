package co.bdigital.cmm.jpa.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AwAccionesPorRole;
import co.bdigital.cmm.jpa.model.AwRol;
import co.bdigital.cmm.jpa.services.AWProfileJPAService;

/**
 * 
 * @author juan.molinab
 *
 */
public class AWProfileJPAServiceIMPL implements AWProfileJPAService {

    private static final String SENTENCE_SQL_SELECT_PERMISSIONS_BY_ROL = "SELECT aw FROM AwAccionesPorRole aw WHERE aw.id.idRol = :id";
    private static final String ID = "id";

    /**
     * Metodo para crear un nuevo perfil (rol)
     * 
     * @param rol
     * @param em
     * @return
     * @throws JPAException
     */
    public AwRol createProfile(AwRol rol, EntityManager em) throws JPAException {
        final String metodo = "createProfile";
        AwRol getRol = null;
        try {
            getRol = em.merge(rol);
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
        return getRol;
    }

    /**
     * Metodo para asociar los permisos a un rol
     * 
     * @param actionByRol
     * @param em
     * @return
     * @throws JPAException
     */
    public boolean addPermissionsToProfile(AwAccionesPorRole actionByRol,
            EntityManager em) throws JPAException {
        final String metodo = "addPermissionsToProfile";
        boolean isCreate = false;
        try {
            em.persist(actionByRol);
            isCreate = true;
        } catch (Exception e) {
            isCreate = false;
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
        return isCreate;
    }

    /**
     * Metodo para eliminar todos los permisos de un rol determinado
     * 
     * @param idRol
     * @param em
     * @return
     * @throws JPAException
     */
    public boolean deleteAllPermissionsByRol(long idRol, EntityManager em)
            throws JPAException {
        final String metodo = "deleteAllPermissionsByRol";
        boolean isDelete = false;
        try {
            String selectQuery = SENTENCE_SQL_SELECT_PERMISSIONS_BY_ROL;
            List<AwAccionesPorRole> actionsByRol = em.createQuery(selectQuery)
                    .setParameter(ID, idRol).getResultList();
            for (AwAccionesPorRole actionByRol : actionsByRol) {
                em.remove(actionByRol);
            }
            isDelete = true;

        } catch (Exception e) {
            isDelete = false;
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
        return isDelete;
    }

}
