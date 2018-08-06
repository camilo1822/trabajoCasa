/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Cliente;

/**
 * @author daniel.pareja@pragma.com.co
 *
 */
public interface ClientEmailJPAService {

    /**
     * Consulta de términos y condiciones.
     * 
     * @param em
     * @return
     * @throws JPAException
     */
    public Cliente getClientByEmail(String email, EntityManager em)
            throws JPAException;

    /**
     * Metodo para validar si exite un correo en la tabla clientes ignorando
     * case sensitive
     * 
     * @param email
     * @param em
     * @return <code>Boolean</code>
     * @throws JPAException
     */
    public boolean existEmail(String email, EntityManager em)
            throws JPAException;

}
