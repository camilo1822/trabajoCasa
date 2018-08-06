/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import co.bdigital.cmm.jpa.model.DivisionGeografica;
import co.bdigital.cmm.jpa.model.Parametro;
import co.bdigital.cmm.jpa.services.ParameterJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * @author daniel.pareja@pragma.com.co
 *
 */
public class ParameterJPAServiceIMPL implements ParameterJPAService {

    public static final String COMMON_STRING_JOIN = "tipoParametro";
    public static final String COMMON_STRING_COLUMN = "tipoParametroId";
    public static final String COMMON_STRING_ORDER = "orden";
    private static final String COMMON_STRING_COUNTRY = "divisionGeografica";
    private static final String COMMON_STRING_COUNTRY_CODE = "codigoDivision";
    private static ParameterJPAServiceIMPL instance;

    /*
     * Constructor por defecto
     */
    public ParameterJPAServiceIMPL() {
    }

    public static ParameterJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new ParameterJPAServiceIMPL();
        return instance;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Parametro> getParamByType(String tipoParametroId,
            EntityManager em) throws JPAException {
        final String metodo = "getParamByType";

        try {
            Query query = em.createNamedQuery(
                    ConstantJPA.QUERY_PARAMETRO_BY_TIPOPARAMETROID);
            query.setParameter(ConstantJPA.COMMON_PARAMETER_TIPOPARAMETROID,
                    tipoParametroId);
            em.getEntityManagerFactory().getCache().evict(Parametro.class);

            // si llegan varios resultados tomará solo el primero
            List<Parametro> resultado = new ArrayList<Parametro>();

            resultado = (List<Parametro>) query.getResultList();
            if (null == resultado || resultado.isEmpty()) {
                return null;
            }
            return resultado;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

    @Override
    public List<Parametro> getRegionParameter(String tipoParametroId,
            String countryCode, EntityManager em) throws JPAException {
        final String metodo = "getRegionParameter";

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<Parametro> criteriaQuery = criteriaBuilder
                    .createQuery(Parametro.class);

            Root<Parametro> from = criteriaQuery.from(Parametro.class);
            Join<Parametro, DivisionGeografica> country = from
                    .join(COMMON_STRING_COUNTRY);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_JOIN).get(COMMON_STRING_COLUMN),
                    tipoParametroId));

            conditions.add(criteriaBuilder.equal(
                    country.get(COMMON_STRING_COUNTRY_CODE), countryCode));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            criteriaQuery.orderBy(
                    criteriaBuilder.asc(from.get(COMMON_STRING_ORDER)));

            // se evita cache
            em.getEntityManagerFactory().getCache().evict(Parametro.class);
            TypedQuery<Parametro> typedQuery = em.createQuery(criteriaQuery);

            // si llegan varios resultados tomará solo el primero
            List<Parametro> resultado = new ArrayList<Parametro>();

            resultado = typedQuery.getResultList();
            if (null == resultado || resultado.isEmpty()) {
                return null;
            }
            return resultado;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

    @Override
    public Parametro getParameterByCode(String parameterCode, EntityManager em)
            throws JPAException {

        Parametro parameterResult = null;

        try {

            parameterResult = em.find(Parametro.class, parameterCode);

            return parameterResult;

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    SeguridadJPAServiceIMPL.class.getCanonicalName());
            errorString.append(":findParameter:");
            throw new JPAException(errorString.toString(), e);
        }

    }
}
