
/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.UserPwdReset;

/**
 * @author hildebrando_rios
 *
 */
public interface ServiceValidate {
	
	/**
	 * @param email
	 * @param controlCode
	 * @param em
	 * @return UserPwdReset
	 * @throws JPAException
	 */
	public UserPwdReset getResetPasswordCodeValidation(String email, String controlCode, EntityManager em) throws JPAException;
}
