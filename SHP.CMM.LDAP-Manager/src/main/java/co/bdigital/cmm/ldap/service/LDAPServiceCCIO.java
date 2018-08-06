/**
 * 
 */
package co.bdigital.cmm.ldap.service;

import javax.naming.directory.Attribute;

import co.bdigital.cmm.ldap.exception.LDAPException;

/**
 * @author hildebrando_rios
 *
 */
public interface LDAPServiceCCIO {

	/**
	 * Permite realizar una operación de BIND de una entrada
	 * sobre el arbol de Merchant(Autebticación de usuarios)
	 * 
	 * @param user
	 * @param password
	 * @return
	 * @throws LDAPException
	 */
	public boolean authenticateUserCCIO(String user, String password);

	/**
	 * Metodo para obtener la fecha de la �ltimo ingreso
	 * 
	 * @param username
	 * @return
	 * @throws LDAPException
	 */
	public String getLastLoginTime(String username) throws LDAPException;
	
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws LDAPException 
	 */
	public String getpwdAccountLockedTime(String username, String password) throws LDAPException ;

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws LDAPException 
	 */
	public int getUserFailedLoginAttempts(String userId) throws LDAPException;
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws LDAPException 
	 */

	public Attribute getArrayHistoricalPassword(String userId) throws LDAPException;
	
	/**
	 * Metodo que consulta la fecha de cambio de password
	 * @param userId
	 * @return String
	 * @throws LDAPException
	 */
	public String getPwdChangedTime(String username) throws LDAPException;
	
	/**
	 * Metodo que consulta la fecha de creacion del usuario en LDAP.
	 * @param username
	 * @return String
	 * @throws LDAPException
	 */
	public String getCreateTimestamp(String username) throws LDAPException;
	
	/**
	 * Metodo que consulta el tiempo de expiracion de un password de la politica del LDAP.
	 * @return String
	 * @throws LDAPException
	 */
	public String getPolicyPwdMaxAge() throws LDAPException;

}
