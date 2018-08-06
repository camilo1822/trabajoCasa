package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.DivisionGeografica;
import co.bdigital.cmm.jpa.model.OperadorRecarga;
import co.bdigital.cmm.jpa.model.TipoOperador;
import co.bdigital.cmm.jpa.services.OperadorRecargaJPAService;

/**
 * @author john.perez@pragma.com.co
 *
 */
public class OperadorRecargaJPAServiceIMPL
        implements OperadorRecargaJPAService {

    private static OperadorRecargaJPAServiceIMPL instance;

    private static final String COMMON_STRING_UNO = "1";
    private static final String COMMON_STRING_ESTADO = "estado";
    private static final String COMMON_STRING_COUNTRY = "divisionGeografica";
    private static final String COMMON_STRING_COUNTRY_CODE = "codigoDivision";
    private static final String COMMON_STRING_OPERATOR_TYPE = "tipoOperador";
    private static final String COMMON_STRING_OPERATOR_TYPE_ID = "tipoOperadorId";
    private static final String COMMON_STRING_OPERATOR_NAME = "nombreOperador";
    private static final String COMMON_STRING_ID = "id";
    private static final String COMMON_STRING_OPERATOR_ID = "idOperador";

    public OperadorRecargaJPAServiceIMPL() {
    }

    public static OperadorRecargaJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new OperadorRecargaJPAServiceIMPL();
        return instance;
    }

    /**
     * Consulta de Operadores por pais y tipo.
     * 
     * @param countryCode
     * @param operatorType
     * @param em
     * @return List<OperadorRecarga>
     * @throws JPAException
     */
    @Override
    public List<OperadorRecarga> getListOperators(String countryCode,
            String operatorType, EntityManager em) throws JPAException {
        final String metodo = "getListOperators";
        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<OperadorRecarga> criteriaQuery = criteriaBuilder
                    .createQuery(OperadorRecarga.class);
            Root<OperadorRecarga> from = criteriaQuery
                    .from(OperadorRecarga.class);

            Join<OperadorRecarga, DivisionGeografica> country = from
                    .join(COMMON_STRING_COUNTRY);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ESTADO),
                    COMMON_STRING_UNO));

            conditions.add(criteriaBuilder.equal(
                    country.get(COMMON_STRING_COUNTRY_CODE), countryCode));
            Join<OperadorRecarga, TipoOperador> type = from
                    .join(COMMON_STRING_OPERATOR_TYPE);

            conditions.add(criteriaBuilder.equal(
                    type.get(COMMON_STRING_OPERATOR_TYPE_ID),
                    null != operatorType && !operatorType.isEmpty()
                            ? operatorType : COMMON_STRING_UNO));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            criteriaQuery.orderBy(
                    criteriaBuilder.asc(from.get(COMMON_STRING_OPERATOR_NAME)));

            TypedQuery<OperadorRecarga> typedQuery = em
                    .createQuery(criteriaQuery);

            return typedQuery.getResultList();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

    /**
     * Consulta de Operadores por id, pais y tipo.
     * 
     * @param countryCode
     * @param operatorType
     * @param id
     * @param em
     * @return List<OperadorRecarga>
     * @throws JPAException
     */
    @Override
    public List<OperadorRecarga> getListOperatorsByIdAndType(String countryCode,
            String operatorType, String id, EntityManager em)
            throws JPAException {
        final String metodo = "getListOperators";
        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<OperadorRecarga> criteriaQuery = criteriaBuilder
                    .createQuery(OperadorRecarga.class);
            Root<OperadorRecarga> from = criteriaQuery
                    .from(OperadorRecarga.class);

            Join<OperadorRecarga, DivisionGeografica> country = from
                    .join(COMMON_STRING_COUNTRY);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ESTADO),
                    COMMON_STRING_UNO));

            conditions.add(criteriaBuilder.equal(
                    country.get(COMMON_STRING_COUNTRY_CODE), countryCode));
            Join<OperadorRecarga, TipoOperador> type = from
                    .join(COMMON_STRING_OPERATOR_TYPE);

            conditions.add(criteriaBuilder.equal(
                    type.get(COMMON_STRING_OPERATOR_TYPE_ID),
                    null != operatorType && !operatorType.isEmpty()
                            ? operatorType : COMMON_STRING_UNO));

            conditions.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_ID).get(COMMON_STRING_OPERATOR_ID),
                    id));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            criteriaQuery.orderBy(
                    criteriaBuilder.asc(from.get(COMMON_STRING_OPERATOR_NAME)));

            TypedQuery<OperadorRecarga> typedQuery = em
                    .createQuery(criteriaQuery);

            return typedQuery.getResultList();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

}
