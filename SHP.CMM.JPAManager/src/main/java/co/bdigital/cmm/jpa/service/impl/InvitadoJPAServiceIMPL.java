package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Invitado;
import co.bdigital.cmm.jpa.services.InvitadoJPAService;

public class InvitadoJPAServiceIMPL implements InvitadoJPAService {

    private static InvitadoJPAServiceIMPL instance;
    public static final String COMMON_STRING_ID = "id";
    public static final String COMMON_STRING_CELULAR = "celular";
    public static final String COMMON_STRING_PAIS_ID = "paisId";

    public InvitadoJPAServiceIMPL() {
    }

    public static InvitadoJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new InvitadoJPAServiceIMPL();
        return instance;
    }

    @Override
    public Invitado getInvitationById(EntityManager em, String id,
            String paisId) throws JPAException {
        final String metodo = "getInvitationById";
        Invitado invitado = null;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Invitado> criteriaQuery = criteriaBuilder
                    .createQuery(Invitado.class);
            Root<Invitado> from = criteriaQuery.from(Invitado.class);

            List<Predicate> predicates = new ArrayList<Predicate>();

            predicates
                    .add(criteriaBuilder.equal(from.get(COMMON_STRING_ID), id));

            predicates.add(criteriaBuilder
                    .equal(from.get(COMMON_STRING_PAIS_ID), paisId));

            criteriaQuery.select(from);
            criteriaQuery.where(
                    predicates.toArray(new Predicate[predicates.size()]));

            TypedQuery<Invitado> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setHint("javax.persistence.cache.retrieveMode",
                    CacheRetrieveMode.BYPASS);
            typedQuery.setHint("javax.persistence.cache.storeMode",
                    CacheStoreMode.REFRESH);
            List<Invitado> listResult = typedQuery.getResultList();
            if (null == listResult || listResult.isEmpty()) {
                return null;
            }
            invitado = listResult.get(0);
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return invitado;
    }

    @Override
    public Invitado getInvitationByPhoneNumber(EntityManager em,
            String phoneNumber, String paisId) throws JPAException {
        final String metodo = "getInvitationByPhoneNumber";
        Invitado invitado = null;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Invitado> criteriaQuery = criteriaBuilder
                    .createQuery(Invitado.class);
            Root<Invitado> from = criteriaQuery.from(Invitado.class);

            List<Predicate> predicates = new ArrayList<Predicate>();

            predicates.add(criteriaBuilder
                    .equal(from.get(COMMON_STRING_CELULAR), phoneNumber));

            predicates.add(criteriaBuilder
                    .equal(from.get(COMMON_STRING_PAIS_ID), paisId));

            criteriaQuery.select(from);
            criteriaQuery.where(
                    predicates.toArray(new Predicate[predicates.size()]));

            TypedQuery<Invitado> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setHint("javax.persistence.cache.retrieveMode",
                    CacheRetrieveMode.BYPASS);
            typedQuery.setHint("javax.persistence.cache.storeMode",
                    CacheStoreMode.REFRESH);
            invitado = typedQuery.getSingleResult();
            if (null == invitado) {
                return null;
            }
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return invitado;
    }

    @Override
    public Invitado getAvalaibleInvitationCode(EntityManager em, String paisId)
            throws JPAException {
        final String metodo = "getAvalaibleInvitationCode";
        Invitado invitado = null;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Invitado> criteriaQuery = criteriaBuilder
                    .createQuery(Invitado.class);
            Root<Invitado> from = criteriaQuery.from(Invitado.class);

            List<Predicate> predicates = new ArrayList<Predicate>();

            predicates.add(from.get(COMMON_STRING_CELULAR).isNull());

            predicates.add(criteriaBuilder
                    .equal(from.get(COMMON_STRING_PAIS_ID), paisId));

            criteriaQuery.select(from);
            criteriaQuery.where(
                    predicates.toArray(new Predicate[predicates.size()]));

            TypedQuery<Invitado> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setHint("javax.persistence.cache.retrieveMode",
                    CacheRetrieveMode.BYPASS);
            typedQuery.setHint("javax.persistence.cache.storeMode",
                    CacheStoreMode.REFRESH);
            List<Invitado> listResult = typedQuery.getResultList();
            if (null == listResult || listResult.isEmpty()) {
                return null;
            }
            invitado = listResult.get(0);
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return invitado;
    }

}
