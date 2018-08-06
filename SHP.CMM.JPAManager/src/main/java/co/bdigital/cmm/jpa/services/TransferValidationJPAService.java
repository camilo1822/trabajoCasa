/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;

/**
 * @author daniel.pareja@pragma.com.co
 *
 */
public interface TransferValidationJPAService {
	
	/**
	 * 
	 * @param em
	 * @param trnId
	 * @return
	 * @throws JPAException
	 */
	public String getTransactionStatus (EntityManager em, String trnId) throws JPAException;
	
}
