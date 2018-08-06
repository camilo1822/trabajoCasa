package co.bdigital.admin.ejb.controller.view;

import java.util.List;

import co.bdigital.cmm.jpa.model.AwRol;
import co.bdigital.cmm.jpa.model.AwUsuario;

/**
 * 
 * @author juan.molinab
 *
 */
public interface UserInfoServiceBeanLocal {

    /**
     * Metodo para devolver una lista de usuarios
     * 
     * @param countryId
     * @return
     * @throws Exception
     */
    public List<AwUsuario> getUsers(String countryId);

    /**
     * Metodo para devolver la lista de usuarios del sistema
     * 
     * @param countryId
     * @return
     * @throws Exception
     */
    public List<AwRol> getListRol(String countryId);

    /**
     * Metodo para encontrar un usuario por medio de su email
     * 
     * @param email
     * @return
     * @throws Exception
     */
    public AwUsuario getUserByEmail(String email);
}
