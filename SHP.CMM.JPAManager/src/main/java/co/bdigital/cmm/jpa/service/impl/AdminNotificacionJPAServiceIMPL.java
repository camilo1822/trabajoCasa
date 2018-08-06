package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AdminNotificacion;
import co.bdigital.cmm.jpa.services.AdminNotificacionJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * @author john_perez
 *
 */
public class AdminNotificacionJPAServiceIMPL implements
        AdminNotificacionJPAService {

    private static final int COMMON_STRING_ONE = 1;
    private static final String COMMON_STRING_CLIENTE_ID = "clienteId";
    private static final String COMMON_STRING_ORDER = "fechaCreacion";
    private static final String COMMON_STRING_ID = "id";
    private static final String COMMON_STRING_TRN_ID = "trnId";
    private static final String COMMON_STRING_TYPE = "tipo";
    private static final String COMMON_STRING_CLIENT_ID = "clienteId";
    private static AdminNotificacionJPAServiceIMPL instance;

    /**
     * @return AdminNotificacionJPAServiceIMPL
     */
    public AdminNotificacionJPAServiceIMPL() {

    }

    public static AdminNotificacionJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new AdminNotificacionJPAServiceIMPL();
        return instance;
    }

    @Override
    public AdminNotificacion getAdminNotificacionByClienteId(Long clienteId,
            EntityManager em) throws JPAException {
        final String metodo = "getAdminNotificacionByClienteId";
        AdminNotificacion adminNotificacion = null;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<AdminNotificacion> criteriaQuery = criteriaBuilder
                    .createQuery(AdminNotificacion.class);

            Root<AdminNotificacion> from = criteriaQuery
                    .from(AdminNotificacion.class);
            Predicate condition1 = criteriaBuilder.equal(
                    from.get(COMMON_STRING_ID).get(COMMON_STRING_CLIENTE_ID),
                    clienteId);

            criteriaQuery.select(from);
            criteriaQuery.where(condition1);
            criteriaQuery.orderBy(criteriaBuilder.desc(from
                    .get(COMMON_STRING_ORDER)));

            TypedQuery<AdminNotificacion> typedQuery = em.createQuery(
                    criteriaQuery).setMaxResults(COMMON_STRING_ONE);
            typedQuery.setHint(ConstantJPA.CACHE_RETRIEVE_MODE,
                    CacheRetrieveMode.BYPASS);
            typedQuery.setHint(ConstantJPA.CACHE_STORE_MODE,
                    CacheStoreMode.REFRESH);
            adminNotificacion = typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
        return adminNotificacion;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.bdigital.cmm.jpa.services.AdminNotificacionJPAService#getAdminNotificacion
     * (java.lang.String, java.lang.String, java.lang.String,
     * javax.persistence.EntityManager)
     */
    @Override
    public AdminNotificacion getAdminNotificacion(long clienteId, String trnId,
            String type, EntityManager em) throws JPAException {

        final String metodo = "getAdminNotificacion";
        AdminNotificacion adminNotification;
        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<AdminNotificacion> criteriaQuery = criteriaBuilder
                    .createQuery(AdminNotificacion.class);

            Root<AdminNotificacion> from = criteriaQuery
                    .from(AdminNotificacion.class);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ID)
                    .get(COMMON_STRING_CLIENT_ID), clienteId));

            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_ID)
                    .get(COMMON_STRING_TRN_ID), trnId));

            conditions.add(criteriaBuilder.equal(from.get(COMMON_STRING_TYPE),
                    type));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<AdminNotificacion> typedQuery = em
                    .createQuery(criteriaQuery);

            adminNotification = typedQuery.getSingleResult();

            return adminNotification;

        } catch (NoResultException e) {

            return null;

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }

}
