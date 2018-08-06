/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Seguridad;
import co.bdigital.cmm.jpa.services.SeguridadJPAService;

/**
 * @author cristian.martinez
 *
 */
public class SeguridadJPAServiceIMPL implements SeguridadJPAService {

	private static final String CELULAR = "celular";

	/**
	 * 
	 */
	public SeguridadJPAServiceIMPL() {
	}

	/**
	 * Consulta un entity <code>Seguridad</code> por el <code>celular</code> de la entity <code>Cliente</code>.
	 * 
	 * @param celular
	 * @param em
	 * @return <code>Seguridad</code>
	 * @throws JPAException
	 */
	@Override
	public Seguridad getSeguridadByClienteId(String celular, EntityManager em) throws JPAException {
		
		CriteriaBuilder criteriaBuilder = null;
		CriteriaQuery<Seguridad> criteriaQuery = null;
		Root<Seguridad> from = null;
		TypedQuery<Seguridad> typedQuery = null;
		Predicate condition1 = null;
		List<Predicate> predicates = null;
		Seguridad seguridad = null;
		
		try {
			
			criteriaBuilder = em.getCriteriaBuilder();
	        criteriaQuery = criteriaBuilder.createQuery(Seguridad.class);
	        from = criteriaQuery.from(Seguridad.class);

	        //Constructing list of parameters
	        predicates = new ArrayList<Predicate>();
	        condition1 = criteriaBuilder.equal(from.get(CELULAR), celular);
	        predicates.add(condition1);

	        criteriaQuery.select(from);
	        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
	        
	        typedQuery = em.createQuery(criteriaQuery);
	        
	        seguridad = typedQuery.getSingleResult();
		
		} catch (NoResultException e) {
			StringBuilder errorString = new StringBuilder(SeguridadJPAServiceIMPL.class.getCanonicalName());
			errorString.append(":getSeguridadByClienteId:");
			throw new JPAException(errorString.toString(), e);
			
		} catch (Exception e) {
			StringBuilder errorString = new StringBuilder(SeguridadJPAServiceIMPL.class.getCanonicalName());
			errorString.append(":getSeguridadByClienteId:");
			throw new JPAException(errorString.toString(), e);
		}
		
		return seguridad;
	}
	
	
	/**
	 * Consulta un entity <code>Seguridad</code> por los atributos de la misma.
	 * 
	 * @param celularIdSeguridad
	 * @param em
	 * @return <code>Seguridad</code>
	 * @throws JPAException
	 */
	@Override
	public Seguridad findSeguridad(String celularIdSeguridad, EntityManager em) throws JPAException {
		
		Seguridad seguridadLocal = null;
		
		try {
			
			seguridadLocal = em.find(Seguridad.class, celularIdSeguridad); 
			
			return seguridadLocal;
			
		} catch (Exception e) {
			StringBuilder errorString = new StringBuilder(SeguridadJPAServiceIMPL.class.getCanonicalName());
			errorString.append(":findSeguridad:");
			throw new JPAException(errorString.toString(), e);
		}
	}
	
	/**
	 * Inserta un entity <code>Seguridad</code> en Base de Datos.
	 * 
	 * @param seguridad
	 * @param em
	 * @throws JPAException
	 */
	@Override
	public void persistSeguridad(Seguridad seguridad, EntityManager em) throws JPAException {
		
		try {
			
			Date fechaActual = new Date();
			Timestamp fechaActualTimestamp = new Timestamp(fechaActual.getTime());
			seguridad.setFechaCreacion(fechaActualTimestamp);
			seguridad.setFechaModificacion(fechaActualTimestamp);
			seguridad.setUsrCreacion("system");
			seguridad.setUsrModificacion("system");
			
			em.persist(seguridad);
			
		} catch (Exception e) {
			StringBuilder errorString = new StringBuilder(SeguridadJPAServiceIMPL.class.getCanonicalName());
			errorString.append(":persistSeguridad:");
			throw new JPAException(errorString.toString(), e);
		}
	}

	
	/**
	 * Actualiza un entity <code>Seguridad</code> en Base de Datos.
	 * 
	 * @param seguridad
	 * @param em
	 * @return <code>Seguridad</code>
	 * @throws JPAException
	 */
	@Override
	public Seguridad mergeSeguridad(Seguridad seguridad, EntityManager em) throws JPAException {
		
		Seguridad seguridadLocal = null;
		
		try {
			
			seguridadLocal = em.merge(seguridad); 
			
			return seguridadLocal;
			
		} catch (Exception e) {
			StringBuilder errorString = new StringBuilder(SeguridadJPAServiceIMPL.class.getCanonicalName());
			errorString.append(":mergeSeguridad:");
			throw new JPAException(errorString.toString(), e);
		}
	}
	
	
	/**
	 * Eliminacion fisica de un entity <code>Seguridad</code> de la Base de Datos.
	 * 
	 * @param seguridad
	 * @param em
	 * @throws JPAException
	 */
	@Override
	public void removeSeguridad(Seguridad seguridad, EntityManager em) throws JPAException {
		
		try {
			
			em.remove(seguridad); 
			
		} catch (Exception e) {
			StringBuilder errorString = new StringBuilder(SeguridadJPAServiceIMPL.class.getCanonicalName());
			errorString.append(":removeSeguridad:");
			throw new JPAException(errorString.toString(), e);
		}
	}
	
}

