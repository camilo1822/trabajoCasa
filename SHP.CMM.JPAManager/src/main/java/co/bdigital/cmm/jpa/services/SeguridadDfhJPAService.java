/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.SeguridadDfh;


/**
 * @author cristian.martinez
 *
 */
public interface SeguridadDfhJPAService {
	
	
	/**
	 * Consulta un entity <code>Seguridad</code> por los atributos de la misma.
	 * 
	 * @param celularIdSeguridad
	 * @param em
	 * @return <code>Seguridad</code>
	 * @throws JPAException
	 */
	public SeguridadDfh findSeguridadDfh(String celularIdSeguridad, EntityManager em) throws JPAException ;
	

	/**
	 * Inserta un entity <code>Seguridad</code> en Base de Datos.
	 * 
	 * @param seguridad
	 * @param em
	 * @throws JPAException
	 */
	public void persistSeguridadDfh(SeguridadDfh seguridad, EntityManager em) throws JPAException;
	
	
	/**
	 * Eliminacion fisica de un entity <code>Seguridad</code> de la Base de Datos.
	 * 
	 * @param seguridad
	 * @param em
	 * @throws JPAException
	 */
	public void removeSeguridadDfh(SeguridadDfh seguridad, EntityManager em) throws JPAException;


	/**
	 * 
	 * @param seguridad
	 * @param em
	 * @throws JPAException
	 */
	public void mergeSeguridadDfh(SeguridadDfh seguridad, EntityManager em)
			throws JPAException;




	
}