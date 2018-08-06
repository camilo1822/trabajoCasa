/**
 * 
 */
package co.bdigital.cmm.ldap.service;

import javax.naming.directory.Attribute;

import co.bdigital.cmm.ldap.exception.LDAPException;

/**
 * Servicios de Consulta sobre OpenLDAP
 * 
 * @author ricardo.paredes
 * @since 19/12/2015
 * @version 1.0
 *
 */
public interface LDAPService {
	
	/**
	 * Permite realizar una operación de BIND de una entrie
	 * sobre el arbol de OpenLDAP (Autebticación de usuarios)
	 * 
	 * @param userID
	 * @param passwd
	 * @return
	 * @throws LDAPException
	 */
	public boolean authenticateUserLDAP(String userID, String passwd);

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
	public String getLastLoginTime(String username) throws LDAPException ;
	
	/**
	 * Metodo que devuelve el historico de passwords del ldap
	 * @param userId
	 * @return Attribute
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
