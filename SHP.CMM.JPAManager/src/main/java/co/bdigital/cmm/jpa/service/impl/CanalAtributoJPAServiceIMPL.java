package co.bdigital.cmm.jpa.service.impl;

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
import co.bdigital.cmm.jpa.model.CanalAtributo;
import co.bdigital.cmm.jpa.services.CanalAtributoJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * 
 * @author daniel.pareja
 *
 */
public class CanalAtributoJPAServiceIMPL implements CanalAtributoJPAService {

    public static final String COMMON_STRING_CANAL_ID = "canalId";
    public static final String COMMON_STRING_ID = "id";

    @Override
    public List<CanalAtributo> getCanalAtributoByCanalId(String canalId,
            EntityManager em) throws JPAException {
        final String metodo = "getCanalAtributoByCanalId";

        List<CanalAtributo> atributos = null;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<CanalAtributo> criteriaQuery = criteriaBuilder
                    .createQuery(CanalAtributo.class);

            Root<CanalAtributo> from = criteriaQuery.from(CanalAtributo.class);
            Predicate condition1 = criteriaBuilder.equal(
                    from.get(COMMON_STRING_ID).get(COMMON_STRING_CANAL_ID),
                    canalId);

            criteriaQuery.select(from);
            criteriaQuery.where(condition1);

            TypedQuery<CanalAtributo> typedQuery = em
                    .createQuery(criteriaQuery);
            typedQuery.setHint(ConstantJPA.CACHE_RETRIEVE_MODE,
                    CacheRetrieveMode.BYPASS);
            typedQuery.setHint(ConstantJPA.CACHE_STORE_MODE,
                    CacheStoreMode.REFRESH);
            atributos = typedQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return atributos;
    }

}
