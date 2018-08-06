/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.cmm.jpa.services.ServiceClient;

/**
 * @author ryu
 *
 */

public class ServiceClientImpl implements ServiceClient {
    private static final String COMMON_STRING_CELULAR = "celular";
    private static final String COMMON_STRING_EMAIL = "correoElectronico";
    private static ServiceClientImpl instance;

    public ServiceClientImpl() {
    }

    public static ServiceClientImpl getInstance() {
        if (null == instance)
            instance = new ServiceClientImpl();
        return instance;
    }

    /**
     * Metodo que consulta en BD un cliente filtrando por celular
     * 
     * @param username
     * @param em
     * @return Cliente
     */
    @Override
    public Cliente getClientbyPhone(String username, EntityManager em)
            throws JPAException {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder
                .createQuery(Cliente.class);

        Root<Cliente> from = criteriaQuery.from(Cliente.class);
        Predicate condition = criteriaBuilder.equal(
                from.get(COMMON_STRING_CELULAR), username);

        criteriaQuery.select(from);
        criteriaQuery.where(condition);
        em.getEntityManagerFactory().getCache().evict(Cliente.class);
        TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);
        Cliente client = null;

        try {
            List<Cliente> clientAux = typedQuery.getResultList();
            if (null != clientAux && !clientAux.isEmpty()) {
                client = clientAux.get(0);
            }
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + this.getClass().getName(), e);
        }

        return client;
    }

    /**
     * Metodo que en consulta en BD un cliente filtrando por email
     * 
     * @param email
     * @param em
     * @return Cliente
     */
    @Override
    public Cliente getClientbyPhoneForMail(String email, EntityManager em)
            throws JPAException {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder
                .createQuery(Cliente.class);

        Root<Cliente> from = criteriaQuery.from(Cliente.class);
        Predicate condition = criteriaBuilder.equal(
                from.get(COMMON_STRING_EMAIL), email);

        criteriaQuery.select(from);
        criteriaQuery.where(condition);
        em.getEntityManagerFactory().getCache().evict(Cliente.class);
        TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);
        Cliente client = null;
        List<Cliente> clientAux;
        try {
            clientAux = typedQuery.getResultList();
            if (!clientAux.isEmpty()) {

                client = clientAux.get(0);

            }
        } catch (NoResultException e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + this.getClass().getName(), e);
        }
        return client;
    }
}
