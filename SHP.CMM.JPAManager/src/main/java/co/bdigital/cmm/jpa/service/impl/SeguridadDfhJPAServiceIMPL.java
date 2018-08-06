/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.cmm.jpa.model.SeguridadDfh;
import co.bdigital.cmm.jpa.services.SeguridadDfhJPAService;

/**
 * @author cristian.martinez
 *
 */
public class SeguridadDfhJPAServiceIMPL implements SeguridadDfhJPAService {

	private static final String COMMON_STRING_CELULAR_CCIO = "celularCcio";
	private static final String COMMON_STRING_USER_SYSTEM = "system";

	/**
	 * 
	 */
	public SeguridadDfhJPAServiceIMPL() {

	}

	/**
	 * Inserta un entity <code>Seguridad</code> en Base de Datos.
	 * 
	 * @param seguridad
	 * @param em
	 * @throws JPAException
	 */
	@Override
	public void persistSeguridadDfh(SeguridadDfh seguridad, EntityManager em)
			throws JPAException {

		try {

			Date fechaActual = new Date();
			Timestamp fechaActualTimestamp = new Timestamp(
					fechaActual.getTime());
			seguridad.setFechaCreacion(fechaActualTimestamp);
			seguridad.setFechaModificacion(fechaActualTimestamp);
			seguridad.setUsrCreacion(COMMON_STRING_USER_SYSTEM);
			seguridad.setUsrModificacion(COMMON_STRING_USER_SYSTEM);
			em.persist(seguridad);

		} catch (Exception e) {
			StringBuilder errorString = new StringBuilder(
					SeguridadDfhJPAServiceIMPL.class.getCanonicalName());
			errorString.append(":persistSeguridad:");
			throw new JPAException(errorString.toString(), e);
		}
	}


	/**
	 * Eliminacion fisica de un entity <code>Seguridad</code> de la Base de
	 * Datos.
	 * 
	 * @param seguridad
	 * @param em
	 * @throws JPAException
	 */
	@Override
	public void removeSeguridadDfh(SeguridadDfh seguridad, EntityManager em)
			throws JPAException {

		try {

			em.remove(seguridad);

		} catch (Exception e) {
			StringBuilder errorString = new StringBuilder(
					SeguridadDfhJPAServiceIMPL.class.getCanonicalName());
			errorString.append(":removeSeguridad:");
			throw new JPAException(errorString.toString(), e);
		}
	}

	/**
	 * Merge fisico de un entity <code>Seguridad</code> de la Base de
	 * Datos.
	 * 
	 * @param seguridad
	 * @param em
	 * @throws JPAException
	 */
	@Override
	public void mergeSeguridadDfh(SeguridadDfh seguridad, EntityManager em)
			throws JPAException {

		try {

			seguridad.setFechaModificacion(new Timestamp(new Date().getTime()));
			seguridad.setUsrModificacion(COMMON_STRING_USER_SYSTEM);
			em.merge(seguridad);
		} catch (Exception e) {
			StringBuilder errorString = new StringBuilder(
					SeguridadDfhJPAServiceIMPL.class.getCanonicalName());
			errorString.append(":mergeSeguridad:");
			throw new JPAException(errorString.toString(), e);
		}
	}

	@Override
	public SeguridadDfh findSeguridadDfh(String celularIdSeguridad,
			EntityManager em) throws JPAException {


		SeguridadDfh security = null;

		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<SeguridadDfh> criteriaQuery = criteriaBuilder
					.createQuery(SeguridadDfh.class);

			Root<SeguridadDfh> from = criteriaQuery.from(SeguridadDfh.class);
			Predicate condition = criteriaBuilder.equal(from.get(COMMON_STRING_CELULAR_CCIO),
					celularIdSeguridad);
			criteriaQuery.select(from);
			criteriaQuery.where(condition);
			em.getEntityManagerFactory().getCache().evict(Cliente.class);
			TypedQuery<SeguridadDfh> typedQuery = em.createQuery(criteriaQuery);

			try {
				security = typedQuery.getSingleResult();
			} catch (Exception ee) {
				return null;
			}

			return security;
		} catch (Exception e) {
			StringBuilder errorString = new StringBuilder(
					SeguridadDfhJPAServiceIMPL.class.getCanonicalName());
			errorString.append(":findSeguridad:");
			throw new JPAException(errorString.toString(), e);
		}

	
	}

}
