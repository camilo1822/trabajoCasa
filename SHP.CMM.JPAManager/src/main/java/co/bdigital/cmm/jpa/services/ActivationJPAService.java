/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Activacion;
import co.bdigital.cmm.jpa.model.Cliente;

/**
 * @author daniel.pareja@pragma.com.co
 *
 */
public interface ActivationJPAService {

    /**
     * Consulta de activacion de correo electronico.
     * 
     * @param em
     * @return
     * @throws JPAException
     */
    public Activacion getActivation(Cliente cliente, EntityManager em)
            throws JPAException;

    /**
     * Consulta de activacion de correo por Token.
     * 
     * @param em
     * @return
     * @throws JPAException
     */
    public Activacion getActivationByToken(String token, EntityManager em)
            throws JPAException;

}
