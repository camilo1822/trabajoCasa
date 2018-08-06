/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Seguridad;


/**
 * @author cristian.martinez
 *
 */
public interface SeguridadJPAService {
	
	
	/**
	 * Consulta un entity <code>Seguridad</code> por el <code>celular</code> de la entity <code>Cliente</code>.
	 * 
	 * @param celular
	 * @param em
	 * @return <code>Seguridad</code>
	 * @throws JPAException
	 */
	public Seguridad getSeguridadByClienteId(String celular, EntityManager em) throws JPAException ;
	
	
	/**
	 * Consulta un entity <code>Seguridad</code> por los atributos de la misma.
	 * 
	 * @param celularIdSeguridad
	 * @param em
	 * @return <code>Seguridad</code>
	 * @throws JPAException
	 */
	public Seguridad findSeguridad(String celularIdSeguridad, EntityManager em) throws JPAException ;
	
	
	/**
	 * Inserta un entity <code>Seguridad</code> en Base de Datos.
	 * 
	 * @param seguridad
	 * @param em
	 * @throws JPAException
	 */
	public void persistSeguridad(Seguridad seguridad, EntityManager em) throws JPAException;
	
	
	/**
	 * Actualiza un entity <code>Seguridad</code> en Base de Datos.
	 * 
	 * @param seguridad
	 * @param em
	 * @return <code>Seguridad</code>
	 * @throws JPAException
	 */
	public Seguridad mergeSeguridad(Seguridad seguridad, EntityManager em) throws JPAException;
	
	
	/**
	 * Eliminacion fisica de un entity <code>Seguridad</code> de la Base de Datos.
	 * 
	 * @param seguridad
	 * @param em
	 * @throws JPAException
	 */
	public void removeSeguridad(Seguridad seguridad, EntityManager em) throws JPAException;
	
}