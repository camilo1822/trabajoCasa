package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AwAccionesPorRole;
import co.bdigital.cmm.jpa.model.AwRol;

/**
 * 
 * @author juan.molinab
 *
 */
public interface AWProfileJPAService {

    /**
     * Metodo para crear un nuevo perfil (rol)
     * 
     * @param rol
     * @param em
     * @return
     * @throws JPAException
     */
    AwRol createProfile(AwRol rol, EntityManager em) throws JPAException;

    /**
     * Metodo para asociar los permisos a un rol
     * 
     * @param actionByRol
     * @param em
     * @return
     * @throws JPAException
     */
    boolean addPermissionsToProfile(AwAccionesPorRole actionByRol,
            EntityManager em) throws JPAException;

    /**
     * Metodo para eliminar todos los permisos de un rol determinado
     * 
     * @param idRol
     * @param em
     * @return
     * @throws JPAException
     */
    boolean deleteAllPermissionsByRol(long idRol, EntityManager em)
            throws JPAException;

}
