/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Termino;
import co.bdigital.cmm.jpa.services.TermsJPAService;

/**
 * @author daniel.pareja@pragma.com.co
 *
 */
public class TermsJPAServiceIMPL implements TermsJPAService {
    public static final String COMMON_STRING_DIVISION_GEOGRAFICA = "divisionGeografica";
    public static final String COMMON_STRING_CODIGO_DIVISION = "codigoDivision";
    public static final String COMMON_STRING_FECHA_CREACION = "fechaCreacion";
    /**
     * 
     */
    public TermsJPAServiceIMPL() {
    }

    @Override
    public List<Termino> listTerms(EntityManager em, String region)
            throws JPAException {
        final String metodo = "getTermsByRegion";

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Termino> criteriaQuery = criteriaBuilder
                    .createQuery(Termino.class);
            Root<Termino> from = criteriaQuery.from(Termino.class);
            Predicate condition1 = criteriaBuilder.equal(
                    from.get(COMMON_STRING_DIVISION_GEOGRAFICA).get(COMMON_STRING_CODIGO_DIVISION), region);
            criteriaQuery.where(condition1);
            criteriaQuery.select(from);
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(COMMON_STRING_FECHA_CREACION)));

            TypedQuery<Termino> typedQuery = em.createQuery(criteriaQuery);

            List<Termino> termsList = new ArrayList<Termino>();

            termsList = typedQuery.getResultList();

            return termsList;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

}
