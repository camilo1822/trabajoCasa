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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.ImagenS3;
import co.bdigital.cmm.jpa.services.DocumentValidationJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * @author juan.arboleda
 *
 */
public class DocumentValidationJPAServiceIMPL
        implements DocumentValidationJPAService {

    private static DocumentValidationJPAServiceIMPL instance;
    private static final String COMMON_STRING_CREATION_DATE_ORDER = "fechaCreacion";

    /**
     * @return VerificacionBiometriaJPAServiceIMPL
     */
    public static DocumentValidationJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new DocumentValidationJPAServiceIMPL();
        return instance;
    }

    @Override
    public List<ImagenS3> getStatus(String clientId, String tipoImagen,
            EntityManager em) throws JPAException {
        try {

            if (!ConstantJPA.COMMON_STRING_EMPTY.equals(clientId)) {
                CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
                CriteriaQuery<ImagenS3> criteriaQuery = criteriaBuilder
                        .createQuery(ImagenS3.class);
                Root<ImagenS3> from = criteriaQuery.from(ImagenS3.class);
                List<Predicate> conditions = new ArrayList<Predicate>();

                conditions.add(criteriaBuilder.equal(
                        from.get(ConstantJPA.COMMON_STRING_CLIENT)
                                .get(ConstantJPA.COMMON_STRING_CLIENT_ID),
                        Long.parseLong(clientId.trim())));

                conditions.add(criteriaBuilder.equal(
                        from.get(ConstantJPA.COMMON_STRING_TYPE_IMAGE),
                        tipoImagen.trim()));

                criteriaQuery.select(from);
                criteriaQuery.where(conditions.toArray(new Predicate[] {}));

                criteriaQuery.orderBy(criteriaBuilder
                        .desc(from.get(COMMON_STRING_CREATION_DATE_ORDER)));

                TypedQuery<ImagenS3> typedQuery = em.createQuery(criteriaQuery);

                typedQuery.setMaxResults(ConstantJPA.COMMON_INT_ONE);

                List<ImagenS3> result = new ArrayList<ImagenS3>();
                result = typedQuery.getResultList();

                return result;

            }

        } catch (NoResultException e) {
            return null;
        }
        return null;
    }

    @Override
    public List<ImagenS3> getAllStatus(String clientId, String tipoImagen,
            EntityManager em) throws JPAException {
        try {

            if (!ConstantJPA.COMMON_STRING_EMPTY.equals(clientId)) {
                CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
                CriteriaQuery<ImagenS3> criteriaQuery = criteriaBuilder
                        .createQuery(ImagenS3.class);
                Root<ImagenS3> from = criteriaQuery.from(ImagenS3.class);
                List<Predicate> conditions = new ArrayList<Predicate>();

                conditions.add(criteriaBuilder.equal(
                        from.get(ConstantJPA.COMMON_STRING_CLIENT)
                                .get(ConstantJPA.COMMON_STRING_CLIENT_ID),
                        Long.parseLong(clientId.trim())));

                Predicate exp1 = criteriaBuilder.equal(
                        from.get(ConstantJPA.COMMON_STRING_TYPE_IMAGE),
                        ConstantJPA.COMMON_STRING_ZERO);
                Predicate exp2 = criteriaBuilder.equal(
                        from.get(ConstantJPA.COMMON_STRING_TYPE_IMAGE),
                        ConstantJPA.COMMON_STRING_ONE);
                conditions.add(criteriaBuilder.or(exp1, exp2));

                criteriaQuery.select(from);
                criteriaQuery.where(conditions.toArray(new Predicate[] {}));

                criteriaQuery.orderBy(criteriaBuilder
                        .desc(from.get(ConstantJPA.COMMON_STRING_TYPE_IMAGE)));

                TypedQuery<ImagenS3> typedQuery = em.createQuery(criteriaQuery);

                List<ImagenS3> result = new ArrayList<ImagenS3>();
                result = typedQuery.getResultList();

                return result;
            }

        } catch (NoResultException e) {
            return null;
        }
        return null;
    }

}
