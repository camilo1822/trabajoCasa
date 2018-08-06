/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Help;

/**
 * @author daniel.pareja@pragma.com.co
 *
 */
public interface HelpJPAService {
	
	/**
	 * Metodo que retorna una lista de ayudas friltrados por el id
	 * @param em
	 * @param helpId
	 * @return
	 * @throws JPAException
	 */
	public List<Help> getHelpById (EntityManager em, String helpId) throws JPAException;
	
}
