package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.framework.WsProvider;

public interface LdapJPAService {
	
	/**
	 * Método que retorna los datos de autenticación de LDAP
	 * @param em
	 * @return
	 * @throws JPAException
	 */
	public WsProvider getLdapData(EntityManager em)throws JPAException;

}


