package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cache;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.TransaccionPendiente;
import co.bdigital.cmm.jpa.services.GetUnReadMovementsCountService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

public class GetUnReadMovementsCountServiceIMPL
        implements GetUnReadMovementsCountService {

    private static final String METHOD_NAME_GET_UN_READ_MOVEMENTS_COUNT = "getUnReadMovementsCount";
    private static final String METHOD_NAME_GET_UN_READ_PAYMENTS_COUNT = "getUnReadPaymentsCount";
    private static final String METHOD_NAME_GET_TRANSACTION_PENDING = "getTransactionPending";
    private static final String METHOD_NAME_GET_TRANSACTION_PENDING_BY_TRANSACTION_ID = "getTransactionPendingByTransactionId";
    private static final String METHOD_NAME_GET_TRANSACTION_PENDING_WITH_DETAILS_BY_TRANSACTION_ID = "getTransactionPendingWithDetailsByTransactionId";
    private static final String METHOD_NAME_GET_TRANSACTION_PENDING_WITH_DETAILS_BY_TRANSACTION_ID_AND_STATUS = "getTransactionPendingWithDetailsByTransactionIdAndStatus";
    private static GetUnReadMovementsCountServiceIMPL instance;
    public static final String COMMON_STRING_PHONE_NUMBER = "celularDestino";
    public static final String COMMON_STRING_TRANSATION_TYPE = "tipoTransaccion";
    public static final String COMMON_STRING_TRANSACTION_STATUS = "estado";
    public static final String COMMON_STRING_CLIENT_ID = "clienteId";
    public static final String COMMON_STRING_TRANSACTION_DATE = "fechaTransaccion";
    public static final String COMMON_STRING_TRANSACTION_ID = "transactionId";
    public static final String COMMON_STRING_TRANSACTION_PENDIENTE_DETALLE = "transaccionPendienteDetalles";

    public GetUnReadMovementsCountServiceIMPL() {
        // Constructor vacio.
    }

    public static GetUnReadMovementsCountServiceIMPL getInstance() {
        if (null == instance)
            instance = new GetUnReadMovementsCountServiceIMPL();
        return instance;
    }

    @Override

    public String getUnReadMovementsCount(EntityManager em, String phone_number,
            int transaction_type, int transaction_status, Date fromDate,
            Date toDate) throws JPAException {

        String count = ConstantJPA.COMMON_STRING_EMPTY;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder
                    .createQuery(Long.class);
            Root<TransaccionPendiente> from = criteriaQuery
                    .from(TransaccionPendiente.class);

            List<Predicate> predicates = new ArrayList<Predicate>();

            predicates.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRANSATION_TYPE), transaction_type));

            predicates.add(criteriaBuilder
                    .equal(from.get(COMMON_STRING_PHONE_NUMBER), phone_number));

            predicates.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRANSACTION_STATUS),
                    transaction_status));

            Path<Date> dateEntryPath = from.get(COMMON_STRING_TRANSACTION_DATE);

            predicates.add(
                    criteriaBuilder.between(dateEntryPath, fromDate, toDate));

            criteriaQuery.select(criteriaBuilder.count(from));
            criteriaQuery.where(
                    predicates.toArray(new Predicate[predicates.size()]));

            TypedQuery<Long> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setHint("javax.persistence.cache.retrieveMode",
                    CacheRetrieveMode.BYPASS);
            typedQuery.setHint("javax.persistence.cache.storeMode",
                    CacheStoreMode.REFRESH);
            count = typedQuery.getSingleResult().toString();
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName()
                            + METHOD_NAME_GET_UN_READ_MOVEMENTS_COUNT,
                    e);
        }
        return count;
    }

    @Override
    public String getUnReadPaymentsCount(EntityManager em, String clienteId,
            int transaction_type, int transaction_status) throws JPAException {

        String count = ConstantJPA.COMMON_STRING_EMPTY;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder
                    .createQuery(Long.class);
            Root<TransaccionPendiente> from = criteriaQuery
                    .from(TransaccionPendiente.class);

            List<Predicate> predicates = new ArrayList<Predicate>();

            predicates.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRANSATION_TYPE), transaction_type));

            predicates.add(
                    criteriaBuilder.equal(from.get(COMMON_STRING_CLIENT_ID),
                            Integer.parseInt(clienteId)));

            predicates.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRANSACTION_STATUS),
                    transaction_status));

            criteriaQuery.select(criteriaBuilder.count(from));
            criteriaQuery.where(
                    predicates.toArray(new Predicate[predicates.size()]));

            TypedQuery<Long> typedQuery = em.createQuery(criteriaQuery);
            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            count = typedQuery.getSingleResult().toString();
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName()
                            + METHOD_NAME_GET_UN_READ_PAYMENTS_COUNT,
                    e);
        }
        return count;
    }

    @Override
    public List<TransaccionPendiente> getUnReadPayments(EntityManager em,
            String clienteId, int transaction_type, int transaction_status,
            Date fromDate, Date toDate) throws JPAException {

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<TransaccionPendiente> criteriaQuery = criteriaBuilder
                    .createQuery(TransaccionPendiente.class);
            Root<TransaccionPendiente> from = criteriaQuery
                    .from(TransaccionPendiente.class);

            List<Predicate> predicates = new ArrayList<Predicate>();

            predicates.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRANSATION_TYPE), transaction_type));

            predicates.add(
                    criteriaBuilder.equal(from.get(COMMON_STRING_CLIENT_ID),
                            Integer.parseInt(clienteId)));

            predicates.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRANSACTION_STATUS),
                    transaction_status));

            Path<Date> dateEntryPath = from.get(COMMON_STRING_TRANSACTION_DATE);

            predicates.add(
                    criteriaBuilder.between(dateEntryPath, fromDate, toDate));

            criteriaQuery.select(from);
            criteriaQuery.where(
                    predicates.toArray(new Predicate[predicates.size()]));

            TypedQuery<TransaccionPendiente> typedQuery = em
                    .createQuery(criteriaQuery);
            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName()
                            + METHOD_NAME_GET_UN_READ_PAYMENTS_COUNT,
                    e);
        }
    }

    @Override
    public TransaccionPendiente getTransactionPending(EntityManager em,
            String clienteId, int transaction_type, int transaction_status,
            String transactionId) throws JPAException {
        TransaccionPendiente count = null;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<TransaccionPendiente> criteriaQuery = criteriaBuilder
                    .createQuery(TransaccionPendiente.class);
            Root<TransaccionPendiente> from = criteriaQuery
                    .from(TransaccionPendiente.class);

            from.fetch(COMMON_STRING_TRANSACTION_PENDIENTE_DETALLE,
                    JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<Predicate>();

            predicates.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRANSATION_TYPE), transaction_type));

            predicates.add(
                    criteriaBuilder.equal(from.get(COMMON_STRING_CLIENT_ID),
                            Integer.parseInt(clienteId)));

            if (null != transactionId && !transactionId.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        from.get(COMMON_STRING_TRANSACTION_ID), transactionId));
            }

            predicates.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRANSACTION_STATUS),
                    transaction_status));

            criteriaQuery.select(from);
            criteriaQuery.where(
                    predicates.toArray(new Predicate[predicates.size()]));
            criteriaQuery.orderBy(criteriaBuilder
                    .desc(from.get(COMMON_STRING_TRANSACTION_DATE)));

            TypedQuery<TransaccionPendiente> typedQuery = em
                    .createQuery(criteriaQuery);
            typedQuery.setHint("javax.persistence.cache.retrieveMode",
                    CacheRetrieveMode.BYPASS);
            typedQuery.setHint("javax.persistence.cache.storeMode",
                    CacheStoreMode.REFRESH);
            List<TransaccionPendiente> listResult = typedQuery.getResultList();
            if (null == listResult || listResult.isEmpty()) {
                return null;
            }
            count = listResult.get(0);
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName()
                            + METHOD_NAME_GET_TRANSACTION_PENDING,
                    e);
        }
        return count;
    }

    @Override
    public TransaccionPendiente getTransactionPendingByTransactionId(
            EntityManager em, String transactionId) throws JPAException {
        TransaccionPendiente transaccionPendiente = null;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<TransaccionPendiente> criteriaQuery = criteriaBuilder
                    .createQuery(TransaccionPendiente.class);
            Root<TransaccionPendiente> from = criteriaQuery
                    .from(TransaccionPendiente.class);

            List<Predicate> predicates = new ArrayList<Predicate>();

            predicates.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRANSACTION_ID), transactionId));

            criteriaQuery.select(from);
            criteriaQuery.where(
                    predicates.toArray(new Predicate[predicates.size()]));

            TypedQuery<TransaccionPendiente> typedQuery = em
                    .createQuery(criteriaQuery);
            typedQuery.setHint("javax.persistence.cache.retrieveMode",
                    CacheRetrieveMode.BYPASS);
            typedQuery.setHint("javax.persistence.cache.storeMode",
                    CacheStoreMode.REFRESH);
            List<TransaccionPendiente> listResult = typedQuery.getResultList();
            if (null == listResult || listResult.isEmpty()) {
                return null;
            }
            transaccionPendiente = listResult.get(0);
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName()
                            + METHOD_NAME_GET_TRANSACTION_PENDING_BY_TRANSACTION_ID,
                    e);
        }
        return transaccionPendiente;
    }

    @Override
    public TransaccionPendiente getTransactionPendingWithDetailsByTransactionId(
            EntityManager em, String transactionId) throws JPAException {

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<TransaccionPendiente> criteriaQuery = criteriaBuilder
                    .createQuery(TransaccionPendiente.class);
            Root<TransaccionPendiente> from = criteriaQuery
                    .from(TransaccionPendiente.class);

            from.fetch(COMMON_STRING_TRANSACTION_PENDIENTE_DETALLE,
                    JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<Predicate>();

            predicates.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRANSACTION_ID), transactionId));

            criteriaQuery.select(from);
            criteriaQuery.where(
                    predicates.toArray(new Predicate[predicates.size()]));

            TypedQuery<TransaccionPendiente> typedQuery = em
                    .createQuery(criteriaQuery);

            return typedQuery.getSingleResult();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName()
                            + METHOD_NAME_GET_TRANSACTION_PENDING_WITH_DETAILS_BY_TRANSACTION_ID,
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.bdigital.cmm.jpa.services.GetUnReadMovementsCountService#
     * getTransactionPendingWithDetailsByTransactionIdAndStatus(java.lang.
     * String, int, javax.persistence.EntityManager)
     */
    @Override
    public List<TransaccionPendiente> getTransactionPendingWithDetailsByTransactionIdAndStatus(
            String transactionId, int transactionStatus, EntityManager em)
            throws JPAException {

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<TransaccionPendiente> criteriaQuery = criteriaBuilder
                    .createQuery(TransaccionPendiente.class);
            Root<TransaccionPendiente> from = criteriaQuery
                    .from(TransaccionPendiente.class);

            from.fetch(COMMON_STRING_TRANSACTION_PENDIENTE_DETALLE,
                    JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<Predicate>();

            predicates.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRANSACTION_ID), transactionId));

            predicates.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRANSACTION_STATUS),
                    transactionStatus));

            criteriaQuery.select(from);
            criteriaQuery.where(
                    predicates.toArray(new Predicate[predicates.size()]));

            TypedQuery<TransaccionPendiente> typedQuery = em
                    .createQuery(criteriaQuery);

            return typedQuery.getResultList();

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName()
                            + METHOD_NAME_GET_TRANSACTION_PENDING_WITH_DETAILS_BY_TRANSACTION_ID_AND_STATUS,
                    e);
        }
    }

}
