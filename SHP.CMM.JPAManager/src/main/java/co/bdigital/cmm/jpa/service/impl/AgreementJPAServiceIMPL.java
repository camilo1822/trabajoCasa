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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Contrato;
import co.bdigital.cmm.jpa.model.DivisionGeografica;
import co.bdigital.cmm.jpa.services.AgreementJPAService;

/**
 * @author daniel.pareja@pragma.com.co
 *
 */
public class AgreementJPAServiceIMPL implements AgreementJPAService {

    // ESTADO DEL CONTRATO, 4 (Activo).
    private static final int CONTRACT_STATUSID = 4;
    private static final int COMMON_STRING_ONE = 1;
    private static final int COMMON_STRING_ZER0 = 0;
    private static final String COMMON_STRING_STATUS_ID = "estadoId";
    private static final String COMMON_STRING_COUNTRY = "divisionGeografica";
    private static final String COMMON_STRING_DIVISION_CODE = "codigoDivision";
    private static final String COMMON_STRING_ORDER = "fechaCreacion";
    private static final String COMMON_CONTRACT_TYPE = "tipoContrato";
    private static final String COMMON_CONTRACT_ID = "contratoId";
    private static final String COMMON_STATUS = "estadoId";

    /**
     * 
     */
    public AgreementJPAServiceIMPL() {
    }

    @Override
    public Contrato getContractByID(String contractID, String typeID,
            EntityManager em) throws JPAException {

        final String metodo = "getContractByID";

        try {
            if (contractID != null && !contractID.trim().equalsIgnoreCase("")) {

                System.out.println("VALIDA ID " + contractID);
                CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
                CriteriaQuery<Contrato> criteriaQuery = criteriaBuilder
                        .createQuery(Contrato.class);
                Root<Contrato> from = criteriaQuery.from(Contrato.class);

                List<Predicate> conditions = new ArrayList<Predicate>();

                conditions.add(criteriaBuilder
                        .equal(from.get(COMMON_CONTRACT_ID), contractID));

                if (null != typeID && !typeID.trim().isEmpty()) {
                    conditions.add(criteriaBuilder
                            .equal(from.get(COMMON_CONTRACT_TYPE), typeID));
                }

                criteriaQuery.select(from);
                criteriaQuery.where(conditions.toArray(new Predicate[] {}));

                TypedQuery<Contrato> typedQuery = em.createQuery(criteriaQuery);
                Contrato result = null;
                result = typedQuery.getSingleResult();

                return result;
            } else {
                CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
                CriteriaQuery<Contrato> criteriaQuery = criteriaBuilder
                        .createQuery(Contrato.class);
                Root<Contrato> from = criteriaQuery.from(Contrato.class);

                criteriaQuery.where(criteriaBuilder
                        .equal(from.get(COMMON_STATUS), CONTRACT_STATUSID));
                criteriaQuery.select(from);

                TypedQuery<Contrato> typedQuery = em.createQuery(criteriaQuery);
                Contrato result = null;
                result = typedQuery.getSingleResult();

                return result;
            }

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }

    }

    @Override
    public Contrato getLastContractVersionByRegionID(String countryCode,
            String typeId, EntityManager em) throws JPAException {

        final String metodo = "getlastContractVersionByRegionID";

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Contrato> criteriaQuery = criteriaBuilder
                    .createQuery(Contrato.class);
            Root<Contrato> from = criteriaQuery.from(Contrato.class);

            Join<Contrato, DivisionGeografica> countryJoin = from
                    .join(COMMON_STRING_COUNTRY);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_STATUS_ID), CONTRACT_STATUSID));

            conditions.add(criteriaBuilder.equal(
                    countryJoin.get(COMMON_STRING_DIVISION_CODE), countryCode));

            if (null != typeId && !typeId.trim().isEmpty()) {
                conditions.add(criteriaBuilder
                        .equal(from.get(COMMON_CONTRACT_TYPE), typeId));
            }

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));
            criteriaQuery.orderBy(
                    criteriaBuilder.desc(from.get(COMMON_STRING_ORDER)));

            TypedQuery<Contrato> typedQuery = em.createQuery(criteriaQuery)
                    .setMaxResults(COMMON_STRING_ONE)
                    .setFirstResult(COMMON_STRING_ZER0);

            return typedQuery.getSingleResult();

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

}
