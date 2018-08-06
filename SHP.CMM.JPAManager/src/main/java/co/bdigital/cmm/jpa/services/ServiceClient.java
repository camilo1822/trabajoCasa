/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Cliente;



/**
 * @author ryu
 *
 */
public interface ServiceClient {
	
	/**
	 * Metodo que consulta en BD un cliente filtrando por celular
	 * @param username
	 * @param em
	 * @return Cliente
	 */
	public Cliente getClientbyPhone(String username, EntityManager em) throws JPAException;
	
	/**
	 * Metodo que consulta en BD un cliente filtrando por email
	 * 
	 * @param email
	 * @param em
	 * @return Cliente
	 */
	public Cliente getClientbyPhoneForMail(String username, EntityManager em) throws JPAException;
}
