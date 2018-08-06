package co.bdigital.cmm.jpa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Cache;
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
import co.bdigital.cmm.jpa.model.AwRol;
import co.bdigital.cmm.jpa.services.AWRolJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * 
 * @author juan.molinab
 *
 */
public class AWRolJPAServiceIMPL implements AWRolJPAService {

    public static final String COMMON_COUNTRY_ID = "paisId";

    /**
     * Metodo para devolver la lista de roles de los usuarios
     * 
     * @param countryId
     * @param em
     * @return
     * @throws JPAException
     */
    public List<AwRol> getListRol(String countryId, EntityManager em)
            throws JPAException {
        final String metodo = "getListRol";

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<AwRol> criteriaQuery = criteriaBuilder
                    .createQuery(AwRol.class);
            Root<AwRol> from = criteriaQuery.from(AwRol.class);
            Predicate condition = criteriaBuilder.equal(
                    from.get(COMMON_COUNTRY_ID), countryId);

            criteriaQuery.select(from);
            criteriaQuery.where(condition);

            TypedQuery<AwRol> typedQuery = em.createQuery(criteriaQuery);
            return typedQuery.getResultList();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

    /**
     * Consulta de rol por la llave primaria <code>idRol</code>.
     * 
     * @param awRolId
     * @param em
     * @return <code>AwRol</code>
     * @throws JPAException
     */
    @Override
    public AwRol findAwRolById(Long awRolId, EntityManager em)
            throws JPAException {

        final String metodo = "findAwRolById";
        AwRol rol;
        Map<String, Object> properties;
        Cache cache;

        try {

            cache = em.getEntityManagerFactory().getCache();
            cache.evict(AwRol.class);

            em.setProperty(ConstantJPA.CACHE_RETRIEVE_MODE,
                    CacheRetrieveMode.BYPASS);
            em.setProperty(ConstantJPA.CACHE_STORE_MODE, CacheStoreMode.REFRESH);

            properties = new HashMap<String, Object>();
            properties.put(ConstantJPA.CACHE_RETRIEVE_MODE,
                    CacheRetrieveMode.BYPASS);

            properties
                    .put(ConstantJPA.CACHE_STORE_MODE, CacheStoreMode.REFRESH);

            rol = em.find(AwRol.class, awRolId, properties);

        } catch (IllegalArgumentException e) {

            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);

        } catch (Exception e) {

            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);

        }

        return rol;
    }

}
