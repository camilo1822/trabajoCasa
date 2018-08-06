package co.bdigital.cmm.jpa.service.impl;

import java.math.BigDecimal;
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
import co.bdigital.cmm.jpa.model.InformacionExternaCliente;
import co.bdigital.cmm.jpa.model.Sistema;
import co.bdigital.cmm.jpa.services.InfoExtClienteJPAService;

/**
 * @author juan.arboleda@pragma.com.co
 *
 */
public class InfoExtClienteJPAServiceIMPL implements InfoExtClienteJPAService {

    private static InfoExtClienteJPAServiceIMPL instance;
    public static final String COMMON_STRING_NUM_DOC = "cliente";
    public static final String COMMON_STRING_USR_CREACION = "NEQUI";
    public static final String COMMON_DOCUMENT_ID = "numeroId";
    public static final String COMMON_SISTEMA_ID = "sistemaId";

    public static InfoExtClienteJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new InfoExtClienteJPAServiceIMPL();
        return instance;
    }

    public InformacionExternaCliente getPersonByDocumentId(String documentId,
            EntityManager em) throws JPAException {

        final String metodo = "getPersonByDocumentId";
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<InformacionExternaCliente> criteriaQuery = criteriaBuilder
                    .createQuery(InformacionExternaCliente.class);
            Root<InformacionExternaCliente> from = criteriaQuery
                    .from(InformacionExternaCliente.class);
            Predicate condition = criteriaBuilder.equal(
                    from.get(COMMON_STRING_NUM_DOC).get(COMMON_DOCUMENT_ID),
                    documentId);

            criteriaQuery.select(from);
            criteriaQuery.where(condition);

            TypedQuery<InformacionExternaCliente> typedQuery = em
                    .createQuery(criteriaQuery);

            return typedQuery.getSingleResult();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

    public void saveInformacionExternaCliente(InformacionExternaCliente person,
            EntityManager em) throws JPAException {

        final String metodo = "savePerson";
        try {
            Date currentDate = new Date();
            Timestamp time = new Timestamp(currentDate.getTime());
            person.setFechaCreacion(time);
            person.setUsrCreacion(COMMON_STRING_USR_CREACION);
            em.persist(person);
            em.flush();

        } catch (NoResultException e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

    public Sistema getSistemaById(String sistemaId, EntityManager em)
            throws JPAException {
        final String metodo = "getSistemaById";
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Sistema> criteriaQuery = criteriaBuilder
                    .createQuery(Sistema.class);
            Root<Sistema> from = criteriaQuery.from(Sistema.class);
            Predicate condition = criteriaBuilder.equal(
                    from.get(COMMON_SISTEMA_ID), new BigDecimal(sistemaId));

            criteriaQuery.select(from);
            criteriaQuery.where(condition);

            TypedQuery<Sistema> typedQuery = em.createQuery(criteriaQuery);

            return typedQuery.getSingleResult();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

}
