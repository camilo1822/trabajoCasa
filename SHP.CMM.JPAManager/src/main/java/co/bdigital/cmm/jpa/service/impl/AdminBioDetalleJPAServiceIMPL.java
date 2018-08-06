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

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AdminBioDetalle;
import co.bdigital.cmm.jpa.services.AdminBioDetalleJPAService;

/**
 * @author john.perez@pragma.com.co
 *
 */
public class AdminBioDetalleJPAServiceIMPL implements AdminBioDetalleJPAService {

    public static AdminBioDetalleJPAServiceIMPL instance;
    private static final String COMMON_STRING_ADMIN_NOTIFICACION = "adminNotificacion";
    private static final String COMMON_STRING_ID = "id";
    private static final String COMMON_STRING_CLIENTE_ID = "clienteId";
    private static final String COMMON_STRING_TRN_ID = "trnId";
    private static final String COMMON_STRING_CREATION_DATE_ORDER = "fechaCreacion";

    public static AdminBioDetalleJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new AdminBioDetalleJPAServiceIMPL();
        return instance;
    }

    @Override
    public List<AdminBioDetalle> getListBioDetail(EntityManager em,
            Long clienteId, String trnId, int maxResult) throws JPAException {

        final String metodo = "getListBioDetail";
        List<AdminBioDetalle> listAdminBioDetalle = new ArrayList<AdminBioDetalle>();

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<AdminBioDetalle> criteriaQuery = criteriaBuilder
                    .createQuery(AdminBioDetalle.class);

            Root<AdminBioDetalle> from = criteriaQuery
                    .from(AdminBioDetalle.class);

            List<Predicate> conditions = new ArrayList<Predicate>();

            Predicate condition1 = criteriaBuilder.equal(
                    from.get(COMMON_STRING_ADMIN_NOTIFICACION)
                            .get(COMMON_STRING_ID)
                            .get(COMMON_STRING_CLIENTE_ID), clienteId);
            conditions.add(condition1);
            Predicate condition2 = criteriaBuilder.equal(
                    from.get(COMMON_STRING_ADMIN_NOTIFICACION)
                            .get(COMMON_STRING_ID).get(COMMON_STRING_TRN_ID),
                    trnId);
            conditions.add(condition2);
            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            criteriaQuery.orderBy(criteriaBuilder.desc(from
                    .get(COMMON_STRING_CREATION_DATE_ORDER)));

            TypedQuery<AdminBioDetalle> typedQuery = em
                    .createQuery(criteriaQuery);

            if (maxResult > 0) {
                typedQuery.setMaxResults(maxResult);
            }

            listAdminBioDetalle = typedQuery.getResultList();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
        return listAdminBioDetalle;
    }

}
