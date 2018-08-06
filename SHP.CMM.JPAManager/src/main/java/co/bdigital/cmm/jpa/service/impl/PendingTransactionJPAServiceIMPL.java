package co.bdigital.cmm.jpa.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.TransaccionPendiente;
import co.bdigital.cmm.jpa.services.PendingTransactionJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public class PendingTransactionJPAServiceIMPL implements
        PendingTransactionJPAService {

    private static PendingTransactionJPAServiceIMPL instance;

    private static final String COMMON_STRING_DATE_TRANSACTION = "fechaTransaccion";
    private static final String COMMON_STRING_REFERENCE = "referencia";
    private static final String COMMON_STRING_CLIENT_ID = "clienteId";
    private static final String COMMON_STRING_TRANSACTION_TYPE = "tipoTransaccion";
    private static final String COMMON_STRING_TRANSACTION_GROUP_STATUS = "estado";
    private static final String COMMON_STRING_TRANSACTION_GROUP = "transaccionGrupo";
    private static final int COMMON_STRING_REQUEST_MONEY_TX_TYPE = 31;
    private static final String COMMON_STRING_PENDING_TX = "33";
    private static final String COMMON_STRING_ONE = "1";
    private static final String COMMON_STRING_TWO = "2";
    private static final String COMMON_STRING_PENDING_TRANSACTION = ":getPendingTransaction:";

    public PendingTransactionJPAServiceIMPL() {
    }

    public static PendingTransactionJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new PendingTransactionJPAServiceIMPL();
        return instance;
    }

    @Override
    public List<TransaccionPendiente> getPendingTransaction(long clientId,
            Date fromDate, Date toDate, String queryType, EntityManager em)
            throws JPAException {

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<TransaccionPendiente> criteriaQuery = criteriaBuilder
                    .createQuery(TransaccionPendiente.class);

            Root<TransaccionPendiente> from = criteriaQuery
                    .from(TransaccionPendiente.class);

            List<Predicate> conditions = new ArrayList<Predicate>();

            Path<Date> dateEntryPath = from.get(COMMON_STRING_DATE_TRANSACTION);
            conditions.add(criteriaBuilder.between(dateEntryPath, fromDate,
                    toDate));
            // Solicitudes
            if (COMMON_STRING_ONE.equals(queryType)) {

                conditions.add(criteriaBuilder.equal(
                        from.get(COMMON_STRING_CLIENT_ID), clientId));
                // Con grupo o sin grupo
                conditions.add(criteriaBuilder.or(criteriaBuilder.equal(
                        from.get(COMMON_STRING_TRANSACTION_GROUP).get(
                                COMMON_STRING_TRANSACTION_GROUP_STATUS),
                        COMMON_STRING_PENDING_TX), criteriaBuilder
                        .or(criteriaBuilder.isNull(from
                                .get(COMMON_STRING_TRANSACTION_GROUP)))));
                // Petciones
            } else if (COMMON_STRING_TWO.equals(queryType)) {

                conditions.add(criteriaBuilder.equal(
                        from.get(COMMON_STRING_REFERENCE),
                        String.valueOf(clientId)));
            }

            conditions.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRANSACTION_TYPE),
                    COMMON_STRING_REQUEST_MONEY_TX_TYPE));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));
            criteriaQuery.orderBy(criteriaBuilder.asc(from
                    .get(COMMON_STRING_DATE_TRANSACTION)));

            em.getEntityManagerFactory().getCache()
                    .evict(TransaccionPendiente.class);

            TypedQuery<TransaccionPendiente> typedQuery = em
                    .createQuery(criteriaQuery);

            return typedQuery.getResultList();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    PendingTransactionJPAServiceIMPL.class.getCanonicalName());
            errorString.append(COMMON_STRING_PENDING_TRANSACTION);
            throw new JPAException(errorString.toString(), e);
        }
    }
    
    /**
     * Consulta de transacciones pendientes por cliente tipo tx y estado tx
     * 
     * @param em
     * @param clientId
     * @param transactionType
     * @param transactionStatus
     * @param transactionId
     * 
     * @return List<OperadorRecarga>
     * @throws JPAException
     */
    @Override
    public List<TransaccionPendiente> getPendingTransactions(EntityManager em,
            String clienteId, int transactionType, int transactionStatus,
            String transactionId) throws JPAException {

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<TransaccionPendiente> criteriaQuery = criteriaBuilder
                    .createQuery(TransaccionPendiente.class);
            Root<TransaccionPendiente> from = criteriaQuery
                    .from(TransaccionPendiente.class);

            List<Predicate> predicates = new ArrayList<Predicate>();

            predicates.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRANSACTION_TYPE), transactionType));

            predicates.add(
                    criteriaBuilder.equal(from.get(COMMON_STRING_CLIENT_ID),
                            Integer.parseInt(clienteId)));

            predicates.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRANSACTION_GROUP_STATUS),
                    transactionStatus));

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
            StringBuilder errorString = new StringBuilder(
                    PendingTransactionJPAServiceIMPL.class.getCanonicalName());
            errorString.append(ConstantJPA.GET_PENDING_TRANSACTION);
            throw new JPAException(EnumJPAException.DB_ERROR,
                    errorString.toString(), e);
        }

    }

    /**
     * Método encargado de insertar una transaccion pendiente en BD
     * 
     * @param em
     * @param transaccionPendiente
     * 
     * @throws JPAException
     */
    @Override
    public void persistPendingTransaction(EntityManager em,
            TransaccionPendiente transaccionPendiente) throws JPAException {
        
        try {
            Date fechaActual = new Date();
            Timestamp fechaActualTimestamp = new Timestamp(
                    fechaActual.getTime());
            transaccionPendiente.setFechaTransaccion(fechaActualTimestamp);
            
            em.persist(transaccionPendiente);
            
        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    SeguridadJPAServiceIMPL.class.getCanonicalName());
            errorString.append(ConstantJPA.PERSIST_PENDING_TRANSACTION);
            throw new JPAException(errorString.toString(), e);
        }
    }
    
    /**
     * Método encargado de actualizar transacciones pendientes con un estado
     * especifico
     * 
     * @param em
     * @param pendingTransactionsList
     * @param status
     * 
     * @throws JPAException
     */
    @Override
    public void updatePendingTransactionsWithStatus(EntityManager em,
            List<TransaccionPendiente> pendingTransactionsList, String status)
            throws JPAException {
        try {
            for (TransaccionPendiente transaccionPendiente : pendingTransactionsList) {

                transaccionPendiente.setEstado(new BigDecimal(status));
                em.merge(transaccionPendiente);
            }

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    PendingTransactionJPAServiceIMPL.class.getCanonicalName());
            errorString.append(
                    ConstantJPA.UPDATE_PENDING_TRANSACTIONS_WITH_STATUS);
            throw new JPAException(errorString.toString(), e);
        }
    }
        
}
