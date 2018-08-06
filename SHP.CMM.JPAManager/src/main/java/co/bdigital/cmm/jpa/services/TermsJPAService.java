/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Termino;

/**
 * @author daniel.pareja@pragma.com.co
 *
 */
public interface TermsJPAService {

    /**
     * Consulta de términos y condiciones por país.
     * 
     * @param em
     * @param region
     * @return
     * @throws JPAException
     */
    public List<Termino> listTerms(EntityManager em, String region)
            throws JPAException;

}
