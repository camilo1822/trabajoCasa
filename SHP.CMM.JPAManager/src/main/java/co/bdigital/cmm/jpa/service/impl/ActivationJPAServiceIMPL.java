/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Activacion;
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.cmm.jpa.services.ActivationJPAService;

/**
 * @author daniel.pareja@pragma.com.co
 *
 */
public class ActivationJPAServiceIMPL implements ActivationJPAService {

    public static final String COMMON_STRING_CLIENTE_ID = "cliente";
    public static final String COMMON_STRING_TOKEN_EMAIL = "tokenEmail";

    /**
	 * 
	 */
    public ActivationJPAServiceIMPL() {
    }

    @Override
    public Activacion getActivation(Cliente cliente, EntityManager em)
            throws JPAException {
        final String metodo = "getActivation";

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Activacion> criteriaQuery = criteriaBuilder
                    .createQuery(Activacion.class);

            Root<Activacion> from = criteriaQuery.from(Activacion.class);
            Predicate condition = criteriaBuilder.equal(
                    from.get(COMMON_STRING_CLIENTE_ID), cliente);
            criteriaQuery.select(from);
            criteriaQuery.where(condition);
            em.getEntityManagerFactory().getCache().evict(Cliente.class);
            TypedQuery<Activacion> typedQuery = em.createQuery(criteriaQuery);
            Activacion activacion = null;

            try {
                activacion = typedQuery.getSingleResult();
            } catch (Exception ee) {
                return null;
            }

            return activacion;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

    @Override
    public Activacion getActivationByToken(String token, EntityManager em)
            throws JPAException {
        final String metodo = "getActivationByToken";

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Activacion> criteriaQuery = criteriaBuilder
                    .createQuery(Activacion.class);

            Root<Activacion> from = criteriaQuery.from(Activacion.class);
            Predicate condition = criteriaBuilder.equal(
                    from.get(COMMON_STRING_TOKEN_EMAIL), token);
            criteriaQuery.select(from);
            criteriaQuery.where(condition);
            em.getEntityManagerFactory().getCache().evict(Activacion.class);
            TypedQuery<Activacion> typedQuery = em.createQuery(criteriaQuery);
            Activacion activacion = null;

            try {
                activacion = typedQuery.getSingleResult();
            } catch (Exception ee) {
                return null;
            }

            return activacion;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

}
