/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.EstadoTransaccionPse;
import co.bdigital.cmm.jpa.model.PseTransaccion;
import co.bdigital.cmm.jpa.services.PseTransaccionJPAService;

/**
 * @author hansel.ospino@pragma.com.co
 *
 */
public class PseTransaccionJPAServiceIMPL implements PseTransaccionJPAService {

    private static final String COMMON_STRING_TICKET_ID = "ticketId";
    private static final String COMMON_STRING_TRANSACTION_STATUS_ID = "etId";
    private static final String COMMON_STRING_ESTADO_TRANSACCION = "estado";
    private static final String COMMON_STRING_CREATION_DATE_ID = "fechaCreacion";
    private static final String COMMON_STRING_TRANSACTION_TYPE_ID = "ttId";
    private static final String COMMON_STRING_MODIFICATION_DATE_ID = "fechaModificacion";
    private static final String SENTENCE_SQL_PSE_TRANSACTION_RANGE_TIME_DELIMITER = "SELECT p.* FROM SHBANCA_DIGITAL.PSE_TRANSACCION p WHERE p.et_Id = ? AND p.tt_Id = ? AND (extract( day from (TO_TIMESTAMP(TO_CHAR(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH24:MI'), 'yyyy-MM-dd HH24:MI') - TO_TIMESTAMP(to_char(p.FECHA_MODIFICACION, 'yyyy-MM-dd HH24:MI'), 'yyyy-MM-dd HH24:MI')) ) * 1440) = 0 AND (extract( hour from (TO_TIMESTAMP(TO_CHAR(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH24:MI'), 'yyyy-MM-dd HH24:MI') - TO_TIMESTAMP(to_char(p.FECHA_MODIFICACION, 'yyyy-MM-dd HH24:MI'), 'yyyy-MM-dd HH24:MI')) ) * 60) = 0 aND (extract( minute from (TO_TIMESTAMP(TO_CHAR(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH24:MI'), 'yyyy-MM-dd HH24:MI') - TO_TIMESTAMP(to_char(p.FECHA_MODIFICACION, 'yyyy-MM-dd HH24:MI'), 'yyyy-MM-dd HH24:MI')) ) BETWEEN 2 AND ?) AND MOD(extract( minute from (TO_TIMESTAMP(TO_CHAR(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH24:MI'), 'yyyy-MM-dd HH24:MI') - TO_TIMESTAMP(to_char(p.FECHA_MODIFICACION, 'yyyy-MM-dd HH24:MI'), 'yyyy-MM-dd HH24:MI')) ), ?) = 0 ORDER BY p.FECHA_CREACION DESC";
    private static final String COMMON_STRING_ID = "id";
    private static final String COMMON_STRING_TRAZABILITY_CODE = "trazabilityCode";
    private static final String COMMON_STRING_PSE_TRAMSACTION_CASHIN_TYPE = "1";

    /**
	 * 
	 */
    public PseTransaccionJPAServiceIMPL() {
    }

    @Override
    public List<PseTransaccion> getPseTransaccionsByTransactionStatusId(
            String transactionStatusId, Date startDate, EntityManager em)
            throws JPAException {

        final String metodo = "getPseTransaccionByTransactionStatusId";

        try {

            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PseTransaccion> criteriaQuery = criteriaBuilder
                    .createQuery(PseTransaccion.class);
            Root<PseTransaccion> from = criteriaQuery
                    .from(PseTransaccion.class);

            Join<PseTransaccion, EstadoTransaccionPse> countryJoin = from
                    .join(COMMON_STRING_TRANSACTION_STATUS_ID);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(
                    countryJoin.get(COMMON_STRING_TRANSACTION_STATUS_ID),
                    transactionStatusId));

            conditions
                    .add(criteriaBuilder.lessThanOrEqualTo(
                            from.<Date> get(COMMON_STRING_CREATION_DATE_ID),
                            startDate));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<PseTransaccion> typedQuery = em
                    .createQuery(criteriaQuery);
            return typedQuery.getResultList();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }

    @Override
    public List<PseTransaccion> getPseTransaccionsByEstado(String estado,
            EntityManager em) throws JPAException {

        final String metodo = "getPseTransaccionsByEstado";

        try {

            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PseTransaccion> criteriaQuery = criteriaBuilder
                    .createQuery(PseTransaccion.class);
            Root<PseTransaccion> from = criteriaQuery
                    .from(PseTransaccion.class);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_ESTADO_TRANSACCION), estado));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<PseTransaccion> typedQuery = em
                    .createQuery(criteriaQuery);
            return typedQuery.getResultList();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }

    @Override
    public PseTransaccion getPseTransaccionCashInByTicketId(String ticketId,
            EntityManager em) throws JPAException {
        final String metodo = "getPseTransaccionCashInByTicketId";
        try {
            if (null != ticketId && !ticketId.trim().isEmpty()) {
                Cache cache = em.getEntityManagerFactory().getCache();
                cache.evict(PseTransaccion.class);
                CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
                CriteriaQuery<PseTransaccion> criteriaQuery = criteriaBuilder
                        .createQuery(PseTransaccion.class);
                Root<PseTransaccion> from = criteriaQuery
                        .from(PseTransaccion.class);
                List<Predicate> conditions = new ArrayList<Predicate>();
                conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ID)
                        .get(COMMON_STRING_TICKET_ID), ticketId.trim()));
                conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ID)
                        .get(COMMON_STRING_TRANSACTION_TYPE_ID),
                        COMMON_STRING_PSE_TRAMSACTION_CASHIN_TYPE));
                criteriaQuery.select(from);
                criteriaQuery.where(conditions.toArray(new Predicate[] {}));
                TypedQuery<PseTransaccion> typedQuery = em
                        .createQuery(criteriaQuery);
                PseTransaccion result = null;
                result = typedQuery.getSingleResult();
                return result;
            } else {
                return null;
            }
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

    @Override
    public PseTransaccion getPseTransaccionCashInByTicketIdAndStartDate(
            String ticketId, Date startDate, EntityManager em)
            throws JPAException {
        final String metodo = "getPseTransaccionByTicketIdAndStartDate";
        try {
            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PseTransaccion> criteriaQuery = criteriaBuilder
                    .createQuery(PseTransaccion.class);
            Root<PseTransaccion> from = criteriaQuery
                    .from(PseTransaccion.class);
            List<Predicate> conditions = new ArrayList<Predicate>();
            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ID)
                    .get(COMMON_STRING_TICKET_ID), ticketId.trim()));
            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ID)
                    .get(COMMON_STRING_TRANSACTION_TYPE_ID),
                    COMMON_STRING_PSE_TRAMSACTION_CASHIN_TYPE));
            conditions.add(criteriaBuilder.greaterThanOrEqualTo(
                    from.<Date> get(COMMON_STRING_MODIFICATION_DATE_ID),
                    startDate));
            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));
            TypedQuery<PseTransaccion> typedQuery = em
                    .createQuery(criteriaQuery);
            PseTransaccion result = null;
            result = typedQuery.getSingleResult();
            em.getEntityManagerFactory().getCache().evict(result.getClass());
            return result;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

    @Override
    public List<PseTransaccion> getPseTransaccionsByStatusIdAndRangeDeliminterAndMinutesDelimiter(
            String transactionStatusId, String rangeDelimiter,
            String minutesDeliminter, String transactionType, EntityManager em)
            throws JPAException {

        final String metodo = "getPseTransaccionsByStatusIdAndRangeDeliminterAndMinutesDelimiter";

        try {

            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            Query query = em.createNativeQuery(
                    SENTENCE_SQL_PSE_TRANSACTION_RANGE_TIME_DELIMITER,
                    PseTransaccion.class);
            query.setParameter(1, transactionStatusId);
            query.setParameter(2, transactionType);
            query.setParameter(3, rangeDelimiter);
            query.setParameter(4, minutesDeliminter);
            List<PseTransaccion> result = query.getResultList();
            return result;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

    @Override
    public List<PseTransaccion> getPseTransaccionsInEstadosAndTransactionType(
            List<String> estados, String tipoTransaccion, EntityManager em)
            throws JPAException {

        final String metodo = "getPseTransaccionsInEstadosAndTransactionType";

        try {

            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PseTransaccion> criteriaQuery = criteriaBuilder
                    .createQuery(PseTransaccion.class);
            Root<PseTransaccion> from = criteriaQuery
                    .from(PseTransaccion.class);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ID)
                    .get(COMMON_STRING_TRANSACTION_TYPE_ID), tipoTransaccion));
            conditions.add(from.get(COMMON_STRING_ESTADO_TRANSACCION).in(
                    estados));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));
            criteriaQuery.orderBy(criteriaBuilder.asc(from
                    .get(COMMON_STRING_ESTADO_TRANSACCION)));

            TypedQuery<PseTransaccion> typedQuery = em
                    .createQuery(criteriaQuery);
            return typedQuery.getResultList();

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }

    @Override
    public List<PseTransaccion> getPseTransaccionsInEstadosAndTransactionStateAndTransactionType(
            List<String> estados, String tipoTransaccion,
            String idEstadoTransaccion, EntityManager em) throws JPAException {

        final String metodo = "getPseTransaccionsInEstadosAndTransactionStateAndTransactionType";

        try {

            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PseTransaccion> criteriaQuery = criteriaBuilder
                    .createQuery(PseTransaccion.class);
            Root<PseTransaccion> from = criteriaQuery
                    .from(PseTransaccion.class);

            Join<PseTransaccion, EstadoTransaccionPse> countryJoin = from
                    .join(COMMON_STRING_TRANSACTION_STATUS_ID);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ID)
                    .get(COMMON_STRING_TRANSACTION_TYPE_ID), tipoTransaccion));
            conditions.add(from.get(COMMON_STRING_ESTADO_TRANSACCION).in(
                    estados));
            conditions.add(criteriaBuilder.equal(
                    countryJoin.get(COMMON_STRING_TRANSACTION_STATUS_ID),
                    idEstadoTransaccion));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));
            criteriaQuery.orderBy(criteriaBuilder.desc(from
                    .get(COMMON_STRING_CREATION_DATE_ID)));

            TypedQuery<PseTransaccion> typedQuery = em
                    .createQuery(criteriaQuery);
            return typedQuery.getResultList();

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }

    @Override
    public PseTransaccion getPseTransaccionByTicketIdAndTransactionTypeAndTrazabilityCode(
            String ticketId, String tipoTransaccion, String trazabilityCode,
            EntityManager em) throws JPAException {

        final String metodo = "getPseTransaccionByTicketIdAndTransactionTypeAndTrazabilityCode";

        try {

            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evict(PseTransaccion.class);

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PseTransaccion> criteriaQuery = criteriaBuilder
                    .createQuery(PseTransaccion.class);
            Root<PseTransaccion> from = criteriaQuery
                    .from(PseTransaccion.class);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ID)
                    .get(COMMON_STRING_TICKET_ID), ticketId.trim()));
            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ID)
                    .get(COMMON_STRING_TRANSACTION_TYPE_ID), tipoTransaccion));
            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ID)
                    .get(COMMON_STRING_TRAZABILITY_CODE), trazabilityCode
                    .trim()));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<PseTransaccion> typedQuery = em
                    .createQuery(criteriaQuery);
            PseTransaccion result = null;
            result = typedQuery.getSingleResult();

            return result;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }

    @Override
    public PseTransaccion getPseTransaccionByTicketIdAndTransactionTypeAndTrazabilityCodeAndStartDate(
            String ticketId, String transactionType, String trazabilityCode,
            Date startDate, EntityManager em) throws JPAException {

        final String metodo = "getPseTransaccionByTicketIdAndTransactionTypeAndTrazabilityCodeAndStartDate";

        try {
            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PseTransaccion> criteriaQuery = criteriaBuilder
                    .createQuery(PseTransaccion.class);
            Root<PseTransaccion> from = criteriaQuery
                    .from(PseTransaccion.class);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ID)
                    .get(COMMON_STRING_TICKET_ID), ticketId.trim()));
            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ID)
                    .get(COMMON_STRING_TRANSACTION_TYPE_ID), transactionType));
            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ID)
                    .get(COMMON_STRING_TRAZABILITY_CODE), trazabilityCode
                    .trim()));

            conditions.add(criteriaBuilder.greaterThanOrEqualTo(
                    from.<Date> get(COMMON_STRING_MODIFICATION_DATE_ID),
                    startDate));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<PseTransaccion> typedQuery = em
                    .createQuery(criteriaQuery);
            PseTransaccion result = null;
            result = typedQuery.getSingleResult();
            em.getEntityManagerFactory().getCache().evict(result.getClass());

            return result;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }
}
