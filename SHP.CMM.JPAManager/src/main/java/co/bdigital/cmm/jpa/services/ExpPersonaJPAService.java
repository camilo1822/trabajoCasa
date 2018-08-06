package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.ExpPersona;
import co.bdigital.cmm.jpa.model.ExpPersonaDetalle;

/**
 * @author johnpere@bancolombia.com.co
 *
 */
public interface ExpPersonaJPAService {

    /**
     * Metodo que permite consultar la informacion de una persona de Experian en
     * BD.
     * 
     * @param numDoc
     * @param em
     * @return <code>ExpPersona</code>
     * @throws JPAException
     */
    public ExpPersona getExpPersonaByNumeroDoc(String numDoc, EntityManager em)
            throws JPAException;

    /**
     * Inserta un entity <code>ExpPersona</code> en Base de Datos.
     * 
     * @param expPersona
     * @param em
     * @return <code>ExpPersona</code>
     * @throws JPAException
     */
    public ExpPersona persistExpPersona(ExpPersona expPersona, EntityManager em)
            throws JPAException;

    /**
     * Inserta un entity <code>ExpPersonaDetalle</code> en Base de Datos.
     * 
     * @param expPersonaDetalle
     * @param em
     * @return <code>ExpPersonaDetalle</code>
     * @throws JPAException
     */
    public ExpPersonaDetalle persistExpPersonaDetalle(
            ExpPersonaDetalle expPersonaDetalle, EntityManager em)
            throws JPAException;
}
