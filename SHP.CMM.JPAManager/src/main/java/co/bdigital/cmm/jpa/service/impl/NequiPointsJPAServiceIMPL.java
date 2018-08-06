package co.bdigital.cmm.jpa.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.DiasAtencion;
import co.bdigital.cmm.jpa.model.DivisionGeografica;
import co.bdigital.cmm.jpa.model.Promocion;
import co.bdigital.cmm.jpa.model.PuntosNequi;
import co.bdigital.cmm.jpa.services.NequiPointsJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;
import co.bdigital.cmm.jpa.util.JPAUtil;

/**
 * Metodo que implementa la interface de consulta de puntos nequi.
 * 
 * @author john_perez
 *
 */
public class NequiPointsJPAServiceIMPL implements NequiPointsJPAService {

    /**
     * Metodo que consulta los puntos nequi.
     * 
     * @param longitud
     * @param latitud
     * @param radio
     * @param filter
     * @return List<PuntosNequi>
     * @throws JPAException
     */
    @Override
    public List<PuntosNequi> getNequipoints(Double longitud, Double latitud,
            Double radio, Double filter, String regionId, EntityManager em)
            throws JPAException {
        List<PuntosNequi> puntosList = new ArrayList<>();
        final String metodo = "getNequipoints";
        try {
            if (ConstantJPA.COMMON_INT_ZERO == filter) {
                Query query = em.createNativeQuery(
                        ConstantJPA.QUERY_SQL_GETPUNTOSNEQUI_SINFILTRO,
                        PuntosNequi.class);
                query.setHint(ConstantJPA.CACHE_RETRIEVE_MODE,
                        CacheRetrieveMode.BYPASS);
                query.setHint(ConstantJPA.CACHE_RETRIEVE_MODE,
                        CacheStoreMode.REFRESH);
                query.setParameter(1, latitud);
                query.setParameter(2, longitud);
                query.setParameter(3, radio);
                query.setParameter(4, regionId);
                puntosList = query.getResultList();
            } else {
                Query query = em.createNativeQuery(
                        ConstantJPA.QUERY_SQL_GETPUNTOSNEQUI,
                        PuntosNequi.class);
                query.setHint(ConstantJPA.CACHE_RETRIEVE_MODE,
                        CacheRetrieveMode.BYPASS);
                query.setHint(ConstantJPA.CACHE_RETRIEVE_MODE,
                        CacheStoreMode.REFRESH);
                query.setParameter(1, latitud);
                query.setParameter(2, longitud);
                query.setParameter(3, radio);
                query.setParameter(4, filter);
                query.setParameter(5, regionId);
                puntosList = query.getResultList();
            }
        } catch (Exception ee) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, ee);
        }
        return puntosList;
    }

    /**
     * Metodo que consulta la informacion de un punto Nequi.
     * 
     * @param idPoint
     * @param em
     * @return PuntosNequi
     * @throws JPAException
     */
    @Override
    public PuntosNequi infoNequiPoint(Double idPoint, EntityManager em)
            throws JPAException {
        final String metodo = "infoNequiPoint";
        PuntosNequi puntoNequi = new PuntosNequi();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PuntosNequi> criteriaQuery = criteriaBuilder
                    .createQuery(PuntosNequi.class);

            Root<PuntosNequi> from = criteriaQuery.from(PuntosNequi.class);
            Predicate condition = criteriaBuilder.equal(
                    from.get(ConstantJPA.COMMON_STRING_COD_INTERNO), idPoint);

            criteriaQuery.select(from);
            criteriaQuery.where(condition);

            TypedQuery<PuntosNequi> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setHint(ConstantJPA.CACHE_RETRIEVE_MODE,
                    CacheRetrieveMode.BYPASS);
            typedQuery.setHint(ConstantJPA.CACHE_RETRIEVE_MODE,
                    CacheStoreMode.REFRESH);
            puntoNequi = typedQuery.getSingleResult();
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return puntoNequi;
    }

    /**
     * Metodo que consulta los puntos Nequi de acuerdo a los filtros de
     * busqueda.
     * 
     * @param filter
     * @param typeLocation
     * @param em
     * @return List<PuntosNequi>
     * @throws JPAException
     */
    @Override
    public List<PuntosNequi> filterNequiPoint(String filter, EntityManager em,
            int pageIndex, int pageLength, String regionId)
            throws JPAException {
        final String metodo = "filterNequiPoint";
        List<PuntosNequi> puntosNequi = new ArrayList<>();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PuntosNequi> criteriaQuery = criteriaBuilder
                    .createQuery(PuntosNequi.class);

            Root<PuntosNequi> from = criteriaQuery.from(PuntosNequi.class);
            Join<PuntosNequi, DivisionGeografica> countryJoin = from
                    .join(ConstantJPA.COMMON_STRING_COUNTRY_CODE);

            List<Predicate> predicates = new ArrayList<>();

            Predicate condition1 = criteriaBuilder.or(
                    criteriaBuilder.like(
                            criteriaBuilder.upper(from.<String> get(
                                    ConstantJPA.COMMON_STRING_NOMBRE)),
                            ConstantJPA.COMMON_STRING_PERCENTAGE_CHARACTER
                                    + filter.toUpperCase()
                                    + ConstantJPA.COMMON_STRING_PERCENTAGE_CHARACTER),
                    criteriaBuilder.like(
                            criteriaBuilder.upper(from.<String> get(
                                    ConstantJPA.COMMON_STRING_NOMENCLATURA)),
                            ConstantJPA.COMMON_STRING_PERCENTAGE_CHARACTER
                                    + filter.toUpperCase()
                                    + ConstantJPA.COMMON_STRING_PERCENTAGE_CHARACTER));
            predicates.add(condition1);
            predicates.add(criteriaBuilder.equal(
                    countryJoin.get(ConstantJPA.COMMON_STRING_DIVISION_CODE),
                    regionId));
            criteriaQuery.select(from);

            criteriaQuery.where(
                    predicates.toArray(new Predicate[predicates.size()]));
            TypedQuery<PuntosNequi> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setHint(ConstantJPA.CACHE_RETRIEVE_MODE,
                    CacheRetrieveMode.BYPASS);
            typedQuery.setHint(ConstantJPA.CACHE_RETRIEVE_MODE,
                    CacheStoreMode.REFRESH);

            if (pageLength != 0) {
                typedQuery.setFirstResult((pageIndex - 1) * pageLength);
                typedQuery.setMaxResults(pageLength);
            }

            puntosNequi = typedQuery.getResultList();
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return puntosNequi;
    }

    /**
     * Metodo que busca las promociones existentes por un solo punto nequi
     * 
     * @param idPoint
     * @param em
     * @return
     * @throws JPAException
     */
    public List<Promocion> infoSalesByPoint(Double idPoint, EntityManager em)
            throws JPAException {

        final String metodo = "infoSalesByPoint";
        List<Promocion> promocion = new ArrayList<>();

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Promocion> criteriaQuery = criteriaBuilder
                    .createQuery(Promocion.class);

            Root<Promocion> from = criteriaQuery.from(Promocion.class);
            Predicate condition = criteriaBuilder.equal(
                    from.get(ConstantJPA.COMMON_STRING_ID)
                            .get(ConstantJPA.COMMON_STRING_COD_INTERNO_PUNTO),
                    idPoint);

            criteriaQuery.select(from);
            criteriaQuery.where(condition);

            TypedQuery<Promocion> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setHint(ConstantJPA.CACHE_RETRIEVE_MODE,
                    CacheRetrieveMode.BYPASS);
            typedQuery.setHint(ConstantJPA.CACHE_RETRIEVE_MODE,
                    CacheStoreMode.REFRESH);
            promocion = typedQuery.getResultList();
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return promocion;
    }

    /**
     * Metodo para devolver las promociones existentes en una lista de puntos
     * 
     * @param idsPoint
     * @param em
     * @return
     * @throws JPAException
     */
    public List<Promocion> infoSalesByListPoint(List<Long> idsPoint,
            EntityManager em) throws JPAException {
        final String metodo = "infoSalesByPoint";
        Date currentDate = new Date();
        Timestamp currentDateTimestamp = new Timestamp(currentDate.getTime());
        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<Promocion> criteriaQuery = criteriaBuilder
                    .createQuery(Promocion.class);

            Root<Promocion> from = criteriaQuery.from(Promocion.class);

            List<Predicate> conditions = new ArrayList<>();

            conditions.add(from.get(ConstantJPA.COMMON_STRING_ID)
                    .get(ConstantJPA.COMMON_STRING_COD_INTERNO_PUNTO)
                    .in(idsPoint));

            ParameterExpression<Date> parameter = criteriaBuilder
                    .parameter(Date.class);

            Predicate startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(
                    from.get(ConstantJPA.COMMON_STRING_DATE_FINISH)
                            .as(java.sql.Date.class),
                    parameter);
            Predicate endDatePredicate = criteriaBuilder.lessThanOrEqualTo(
                    from.get(ConstantJPA.COMMON_STRING_DATE_START)
                            .as(java.sql.Date.class),
                    parameter);

            Predicate startDateOrPredicate = criteriaBuilder.or(
                    startDatePredicate,
                    from.get(ConstantJPA.COMMON_STRING_DATE_FINISH).isNull());
            Predicate endDateOrPredicate = criteriaBuilder.or(endDatePredicate,
                    from.get(ConstantJPA.COMMON_STRING_DATE_START).isNull());

            Predicate andDate = criteriaBuilder.and(startDateOrPredicate,
                    endDateOrPredicate);
            conditions.add(andDate);

            Predicate conditionEquals = criteriaBuilder.equal(
                    from.get(ConstantJPA.COMMON_STRING_PROM_ACTIVE),
                    ConstantJPA.COOMON_STRING_PROM_ACTIVE_YES);
            conditions.add(conditionEquals);

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            em.getEntityManagerFactory().getCache().evict(Promocion.class);
            TypedQuery<Promocion> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setParameter(parameter, currentDateTimestamp,
                    TemporalType.DATE);
            typedQuery.setParameter(parameter, currentDateTimestamp,
                    TemporalType.DATE);

            return typedQuery.getResultList();

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

    /**
     * Metodo para contar el numero de comercios enviados en la lista que tienen
     * promocion. Solo se cuenta una promocion por comercio
     * 
     * @param idsPoint
     * @param em
     * @return
     * @throws JPAException
     */
    public Long countDistinctSalesByPoints(List<Long> idsPoint,
            EntityManager em) throws JPAException {
        final String metodo = "countDistinctSalesByPoints";
        Date currentDate = new Date();
        Timestamp currentDateTimestamp = new Timestamp(currentDate.getTime());
        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder
                    .createQuery(Long.class);

            List<Predicate> conditions = new ArrayList<>();
            Root<Promocion> from = criteriaQuery.from(Promocion.class);
            criteriaQuery.select(criteriaBuilder
                    .countDistinct(from.get(ConstantJPA.COMMON_STRING_ID)
                            .get(ConstantJPA.COMMON_STRING_COD_INTERNO_PUNTO)));
            conditions.add(from.get(ConstantJPA.COMMON_STRING_ID)
                    .get(ConstantJPA.COMMON_STRING_COD_INTERNO_PUNTO)
                    .in(idsPoint));
            ParameterExpression<Date> parameter = criteriaBuilder
                    .parameter(Date.class);

            Predicate startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(
                    from.get(ConstantJPA.COMMON_STRING_DATE_FINISH)
                            .as(java.sql.Date.class),
                    parameter);
            Predicate endDatePredicate = criteriaBuilder.lessThanOrEqualTo(
                    from.get(ConstantJPA.COMMON_STRING_DATE_START)
                            .as(java.sql.Date.class),
                    parameter);

            Predicate startDateOrPredicate = criteriaBuilder.or(
                    startDatePredicate,
                    from.get(ConstantJPA.COMMON_STRING_DATE_FINISH).isNull());
            Predicate endDateOrPredicate = criteriaBuilder.or(endDatePredicate,
                    from.get(ConstantJPA.COMMON_STRING_DATE_START).isNull());

            Predicate andDate = criteriaBuilder.and(startDateOrPredicate,
                    endDateOrPredicate);
            conditions.add(andDate);

            Predicate conditionEquals = criteriaBuilder.equal(
                    from.get(ConstantJPA.COMMON_STRING_PROM_ACTIVE),
                    ConstantJPA.COOMON_STRING_PROM_ACTIVE_YES);
            conditions.add(conditionEquals);

            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<Long> typedQuery = em.createQuery(criteriaQuery);

            typedQuery.setParameter(parameter, currentDateTimestamp,
                    TemporalType.DATE);
            typedQuery.setParameter(parameter, currentDateTimestamp,
                    TemporalType.DATE);
            return typedQuery.getSingleResult();

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.bdigital.cmm.jpa.services.DiasAtencionJPAService#
     * getAttendaceDaysAndSchedulesByNequiPoints(java.util.List,
     * javax.persistence.EntityManager)
     */
    @Override
    public List<DiasAtencion> getAttendaceDaysAndSchedulesByNequiPointsAndDayType(
            List<Long> nequiPoints, String dayType, EntityManager em)
            throws JPAException {
        try {
            TypedQuery<DiasAtencion> query = em.createNamedQuery(
                    ConstantJPA.SQL_NAMED_QUERY_FIND_ALL_ATTENDACES_DAYS_AND_SCHEDULES_BY_NEQUI_POINTS_AND_DAY_TYPE,
                    DiasAtencion.class);

            query.setParameter(ConstantJPA.COMMON_STRING_NEQUI_POINTS,
                    nequiPoints);
            query.setParameter(ConstantJPA.COMMON_STRING_DAY_OF_WEEK_SHORT_FORM,
                    dayType);
            return query.getResultList();

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    JPAUtil.generateStringConcatenated(
                            this.getClass().getName(),
                            ConstantJPA.COMMON_STRING_SPACE,
                            ConstantJPA.GET_ATTENDANCES_DAYS_AND_SCHEDULES_BY_NEQUI_POINTS_AND_DAY_TYPE_METHOD_NAME),
                    e);
        }
    }
}
