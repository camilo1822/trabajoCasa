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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.CorreoAdmin;
import co.bdigital.cmm.jpa.model.DivisionGeografica;
import co.bdigital.cmm.jpa.services.CorreoAdminJPAService;

/**
 * @author hansel.ospino@pragma.com.co
 *
 */
public class CorreoAdminJPAServiceIMPL implements CorreoAdminJPAService {

    private static final String COMMON_STRING_STATE = "estado";
    private static final Long COMMON_LONG_ACTIVE_STATE = 1L;
    private static CorreoAdminJPAServiceIMPL instance;
    private static final String COMMON_STRING_TYPE = "tipo";
    private static final String COMMON_STRING_COUNTRY = "divisionGeografica";
    private static final String COMMON_STRING_COUNTRY_CODE = "codigoDivision";
    private static final String COMMON_STRING_ID = "id";

    /*
     * Constructor por defecto
     */
    public CorreoAdminJPAServiceIMPL() {
    }

    public static CorreoAdminJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new CorreoAdminJPAServiceIMPL();
        return instance;
    }

    @Override
    public List<CorreoAdmin> getAllActiveCorreoAdmin(EntityManager em)
            throws JPAException {
        final String metodo = "getAllActiveCorreoAdmin";

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<CorreoAdmin> criteriaQuery = criteriaBuilder
                    .createQuery(CorreoAdmin.class);
            Root<CorreoAdmin> from = criteriaQuery.from(CorreoAdmin.class);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_STATE),
                    COMMON_LONG_ACTIVE_STATE));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<CorreoAdmin> typedQuery = em.createQuery(criteriaQuery);
            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

    @Override
    public List<CorreoAdmin> getAllActiveCorreoAdminByType(String type,
            String countryCode, EntityManager em) throws JPAException {
        final String metodo = "getAllActiveCorreoAdminByType";

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<CorreoAdmin> criteriaQuery = criteriaBuilder
                    .createQuery(CorreoAdmin.class);
            Root<CorreoAdmin> from = criteriaQuery.from(CorreoAdmin.class);
            Join<CorreoAdmin, DivisionGeografica> country = from
                    .join(COMMON_STRING_COUNTRY);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_STATE),
                    COMMON_LONG_ACTIVE_STATE));
            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ID)
                    .get(COMMON_STRING_TYPE), type));
            conditions.add(criteriaBuilder.equal(
                    country.get(COMMON_STRING_COUNTRY_CODE), countryCode));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<CorreoAdmin> typedQuery = em.createQuery(criteriaQuery);
            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

}
