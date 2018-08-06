package co.bdigital.admin.ejb.controller.view;

import java.util.List;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AwAccionesPorRole;
import co.bdigital.cmm.jpa.model.AwRol;

public interface ManageProfilesServiceBeanLocal {

    /**
     * Metodo para crear un nuevo perfil
     * 
     * @return boolean - true si pudo insertar - false si no pudo insertar
     * @throws Exception
     */
    AwRol createProfile(AwRol rol) throws Exception;

    /**
     * Metodo para asociar los permisos a un rol
     * 
     * @param actionByRol
     * @return
     * @throws Exception
     */
    boolean addPermissionsToProfile(List<AwAccionesPorRole> actionByRol)
            throws Exception;

    /**
     * Consulta de rol por la llave primaria <code>idRol</code>.
     * 
     * @param awRolId
     * @param em
     * @return <code>AwRol</code>
     * @throws JPAException
     */
    AwRol findAwRolById(Long awRolId) throws Exception;

    /**
     * Metodo para eliminar todos los permisos de un rol determinado
     * 
     * @param idRol
     * @param em
     * @return
     * @throws JPAException
     */
    boolean deleteAllPermissionsByRol(long idRol) throws Exception;

}
