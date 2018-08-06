package co.bdigital.cmm.jpa.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cache;
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
import co.bdigital.cmm.jpa.services.ClientByDocumentJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * @author juan.arboleda
 *
 */
public class ClientByDocumentJPAServiceIMPL
        implements ClientByDocumentJPAService {

    public static final String COMMON_STRING_PHONE = "celular";
    public static final String COMMON_STRING_TIPO_ID = "tipoId";
    public static final String COMMON_STRING_REGION = "paisId";
    public static final String COMMON_STRING_STADO = "estadoId";
    public static final String COMMON_STRING_CUENTA = "numeroCuenta";
    public static final BigDecimal COMMON_BIG_DECIMAL_CLOSE_STATUS = new BigDecimal(
            200);
    public static final String COMMON_STRING_MODIFICATION_DATE_ORDER = "fechaModificacion";

    private static ClientByDocumentJPAServiceIMPL instance = null;

    public static ClientByDocumentJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new ClientByDocumentJPAServiceIMPL();
        return instance;
    }

    @Override
    public Cliente findClientByDocumentId(String documentId, String region,
            EntityManager em) throws JPAException {
        final String metodo = "findClientByDocumentId";
        Cliente cliente = null;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder
                    .createQuery(Cliente.class);

            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            Root<Cliente> from = criteriaQuery.from(Cliente.class);
            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_PHONE),
                    documentId));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);
            cliente = typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return cliente;
    }

    @Override
    public Cliente findClientCloseAcountByDocumentId(String documentId,
            String region, EntityManager em) throws JPAException {
        final String metodo = "findClientCloseAcountByDocumentId";
        List<Cliente> cliente = null;
        Cliente last = null;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder
                    .createQuery(Cliente.class);

            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            Root<Cliente> from = criteriaQuery.from(Cliente.class);
            List<Predicate> conditions = new ArrayList<Predicate>();

            Expression<String> path = from.get(COMMON_STRING_PHONE);
            conditions.add(criteriaBuilder.like(path, documentId + "%"));

            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_STADO),
                    COMMON_BIG_DECIMAL_CLOSE_STATUS));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            criteriaQuery.orderBy(criteriaBuilder
                    .desc(from.get(COMMON_STRING_MODIFICATION_DATE_ORDER)));

            TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setMaxResults(ConstantJPA.COMMON_INT_ONE);

            cliente = typedQuery.getResultList();
            last = cliente.get(0);

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return last;
    }

    @Override
    public Cliente getClientByAccountNumber(String accountNumber, String region,
            EntityManager em) throws JPAException {
        final String metodo = "getClientByAccountNumber";
        Cliente cliente = null;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder
                    .createQuery(Cliente.class);
            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            Root<Cliente> from = criteriaQuery.from(Cliente.class);
            List<Predicate> conditions = new ArrayList<Predicate>();
            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_CUENTA),
                    accountNumber));
            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_REGION),
                    region));
            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);
            cliente = typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return cliente;
    }

}
