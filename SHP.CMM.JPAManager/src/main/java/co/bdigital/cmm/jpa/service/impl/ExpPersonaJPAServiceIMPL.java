package co.bdigital.cmm.jpa.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.ExpPersona;
import co.bdigital.cmm.jpa.model.ExpPersonaDetalle;
import co.bdigital.cmm.jpa.services.ExpPersonaJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * @author johnpere@bancolombia.com
 *
 */
public class ExpPersonaJPAServiceIMPL implements ExpPersonaJPAService {

    private static ExpPersonaJPAServiceIMPL instance;
    public static final String COMMON_STRING_NUM_DOC = "numeroDoc";
    public static final String COMMON_STRING_TIPO = "tipo";
    public static final String COMMON_STRING_GRUPO = "grupo";
    public static final String COMMON_STRING_EXP_PERSONA_DETALLES = "expPersonaDetalles";

    public static ExpPersonaJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new ExpPersonaJPAServiceIMPL();
        return instance;
    }

    @Override
    public ExpPersona getExpPersonaByNumeroDoc(String numDoc, EntityManager em)
            throws JPAException {
        final String metodo = "getExpPersonaByNumeroDoc";
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<ExpPersona> criteriaQuery = criteriaBuilder
                    .createQuery(ExpPersona.class);

            Root<ExpPersona> from = criteriaQuery.from(ExpPersona.class);

            Predicate condition = criteriaBuilder.equal(
                    from.get(COMMON_STRING_NUM_DOC), numDoc);

            criteriaQuery.select(from);
            criteriaQuery.where(condition);

            TypedQuery<ExpPersona> typedQuery = em.createQuery(criteriaQuery);

            return typedQuery.getSingleResult();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

    @Override
    public ExpPersona persistExpPersona(ExpPersona expPersona, EntityManager em)
            throws JPAException {
        try {

            Date fechaActual = new Date();
            Timestamp fechaActualTimestamp = new Timestamp(
                    fechaActual.getTime());
            expPersona.setFechaCreacion(fechaActualTimestamp);
            expPersona.setFechaModificacion(fechaActualTimestamp);
            expPersona.setUsrCreacion(ConstantJPA.COMMON_STRING_NEQUI_UPPER);

            em.persist(expPersona);

            return expPersona;

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    ExpPersonaJPAServiceIMPL.class.getCanonicalName());
            errorString.append(ConstantJPA.PERSIST_EXP_PERSONA);
            throw new JPAException(errorString.toString(), e);
        }
    }

    public ExpPersonaDetalle persistExpPersonaDetalle(
            ExpPersonaDetalle expPersonaDetalle, EntityManager em)
            throws JPAException {
        try {

            Date fechaActual = new Date();
            Timestamp fechaActualTimestamp = new Timestamp(
                    fechaActual.getTime());
            expPersonaDetalle.setFechaCreacion(fechaActualTimestamp);
            expPersonaDetalle.setFechaModificacion(fechaActualTimestamp);
            expPersonaDetalle
                    .setUsrCreacion(ConstantJPA.COMMON_STRING_NEQUI_UPPER);

            em.persist(expPersonaDetalle);

            return expPersonaDetalle;

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    ExpPersonaJPAServiceIMPL.class.getCanonicalName());
            errorString.append(ConstantJPA.PERSIST_EXP_PERSONA);
            throw new JPAException(errorString.toString(), e);
        }
    }

}
