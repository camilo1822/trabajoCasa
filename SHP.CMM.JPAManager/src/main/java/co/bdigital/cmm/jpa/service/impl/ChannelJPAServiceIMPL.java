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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Canal;
import co.bdigital.cmm.jpa.model.DivisionGeografica;
import co.bdigital.cmm.jpa.services.ChannelJPAService;

public class ChannelJPAServiceIMPL implements ChannelJPAService {

    private static ChannelJPAServiceIMPL instance;
    public static final String COMMON_STRING_ID = "id";
    private static final String COMMON_STRING_COUNTRY = "divisionGeografica";
    private static final String COMMON_STRING_COUNTRY_CODE = "codigoDivision";

    public static ChannelJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new ChannelJPAServiceIMPL();
        return instance;
    }

    @Override
    public Canal getChannelById(EntityManager em, String id)
            throws JPAException {
        final String method = "getChannelById";
        Canal channel = null;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Canal> criteriaQuery = criteriaBuilder
                    .createQuery(Canal.class);
            Root<Canal> from = criteriaQuery.from(Canal.class);

            List<Predicate> predicates = new ArrayList<Predicate>();

            predicates
                    .add(criteriaBuilder.equal(from.get(COMMON_STRING_ID), id));

            criteriaQuery.select(from);
            criteriaQuery.where(predicates.toArray(new Predicate[predicates
                    .size()]));

            TypedQuery<Canal> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setHint("javax.persistence.cache.retrieveMode",
                    CacheRetrieveMode.BYPASS);
            typedQuery.setHint("javax.persistence.cache.storeMode",
                    CacheStoreMode.REFRESH);

            Canal channelResult = typedQuery.getSingleResult();

            channel = channelResult;

        } catch (NoResultException e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + method, e);
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + method, e);
        }
        return channel;
    }

    @Override
    public Canal getChannelByIdAndCountryCode(EntityManager em, String id,
            String countryCode) throws JPAException {
        final String method = "getChannelByIdAndCountryCode";
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Canal> criteriaQuery = criteriaBuilder
                    .createQuery(Canal.class);
            Root<Canal> from = criteriaQuery.from(Canal.class);
            Join<Canal, DivisionGeografica> country = from
                    .join(COMMON_STRING_COUNTRY);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions
                    .add(criteriaBuilder.equal(from.get(COMMON_STRING_ID), id));
            conditions.add(criteriaBuilder.equal(
                    country.get(COMMON_STRING_COUNTRY_CODE), countryCode));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[conditions
                    .size()]));

            TypedQuery<Canal> typedQuery = em.createQuery(criteriaQuery);

            return typedQuery.getSingleResult();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + method, e);
        }
    }

}
