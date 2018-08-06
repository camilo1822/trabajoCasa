/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.cmm.jpa.services.ClientEmailJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * @author daniel.pareja@pragma.com.co
 * 
 * @author eduardo.altamar@pragma.com.co
 * @version 1.0.1
 */
public class ClientEmailJPAServiceIMPL implements ClientEmailJPAService {

    private static ClientEmailJPAServiceIMPL instance = null;

    /**
     * Metodo singleton
     * 
     * @return <code>ClientEmailJPAServiceIMPL</code>>
     */
    public static ClientEmailJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new ClientEmailJPAServiceIMPL();
        return instance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.bdigital.cmm.jpa.services.ClientEmailJPAService#getClientByEmail(java.
     * lang.String, javax.persistence.EntityManager)
     */
    @Override
    public Cliente getClientByEmail(String email, EntityManager em)
            throws JPAException {

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder
                    .createQuery(Cliente.class);

            Root<Cliente> from = criteriaQuery.from(Cliente.class);
            Predicate condition = criteriaBuilder.equal(
                    from.get(ConstantJPA.COMMON_STRING_CORREO_ELECTRONICO),
                    email);
            criteriaQuery.select(from);
            criteriaQuery.where(condition);
            em.getEntityManagerFactory().getCache().evict(Cliente.class);
            TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);
            Cliente cliente = null;

            // si llegan varios resultados tomará solo el primero
            List<Cliente> resultado = new ArrayList<Cliente>();

            resultado = typedQuery.getResultList();
            if (null == resultado || resultado.isEmpty()) {
                return null;
            }
            cliente = resultado.get(0);

            return cliente;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName()
                            + ConstantJPA.METHOD_GET_CUSTOMER_BY_EMAIL,
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.bdigital.cmm.jpa.services.ClientEmailJPAService#existEmail(java.lang.
     * String, javax.persistence.EntityManager)
     */
    @Override
    public boolean existEmail(String email, EntityManager em)
            throws JPAException {

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder
                    .createQuery(Cliente.class);

            Root<Cliente> from = criteriaQuery.from(Cliente.class);

            Expression<String> emailExpression = from
                    .get(ConstantJPA.COMMON_STRING_CORREO_ELECTRONICO);

            Predicate condition = criteriaBuilder.equal(
                    criteriaBuilder.lower(emailExpression),
                    email.toLowerCase());

            criteriaQuery.select(from);
            criteriaQuery.where(condition);

            em.getEntityManagerFactory().getCache().evict(Cliente.class);
            TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);

            List<Cliente> resultado = new ArrayList<Cliente>();
            resultado = typedQuery.getResultList();

            return null != resultado && !resultado.isEmpty();

        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + ConstantJPA.METHOD_EXIST_EMAIL,
                    e);
        }
    }

}
