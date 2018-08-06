package co.bdigital.cmm.jpa.service.impl;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.framework.WsProvider;
import co.bdigital.cmm.jpa.services.LdapJPAService;

/**
 * @author daniel.pareja@pragma.com.co
 *
 */
public class LdapJPAServiceIMPL implements LdapJPAService {
private static final String PARAMETER = "Ldap_Auth";
	@Override
	public WsProvider getLdapData(EntityManager em) throws JPAException {
		String metodo = "getLdapData";
		try {
			return em.find(WsProvider.class, PARAMETER);
		} catch (Exception e) {
			throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
					.getName() + metodo, e);
		}

	}

}
