package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AwUsuario;

/**
 * 
 * @author juan.molinab
 *
 */
public interface AWUserJPAService {

    /**
     * Metodo para devolver la lista de usuarios
     * 
     * @param countryId
     * @param em
     * @return
     * @throws JPAException
     */
    public List<AwUsuario> getAWUsers(String countryId, EntityManager em)
            throws JPAException;

    /**
     * Metodo para encontrar un usuario por medio de su email
     * 
     * @param email
     * @param em
     * @return
     * @throws JPAException
     */
    public AwUsuario getUserByEmail(String email, EntityManager em)
            throws JPAException;

    /**
     * <p>
     * Metodo que permite crear un usuario de administrativa.
     * </p>
     * 
     * @param awUser
     * @param creationUser
     * @param em
     * @return
     * @throws JPAException
     */
    public AwUsuario createUser(AwUsuario awUser, String creationUser,
            EntityManager em) throws JPAException;

    /**
     * <p>
     * Metodo que permite actualizar un usuario de administrativa.
     * </p>
     * 
     * @param awUser
     * @param em
     * @return
     * @throws JPAException
     */
    public AwUsuario mergeUser(AwUsuario awUser, EntityManager em)
            throws JPAException;

}
