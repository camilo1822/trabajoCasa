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

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.NotificacionMeta;
import co.bdigital.cmm.jpa.services.GoalNotificationJPAService;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public class GoalNotificationJPAServiceIMPL implements
        GoalNotificationJPAService {

    private static final String COMMON_STRING_DATE = "fechaCreacion";
    private static final String COMMON_STRING_STATUS = "estado";
    private static final String COMMON_STRING_TEXT = "textoNotificacion";
    private static GoalNotificationJPAServiceIMPL instance;

    /**
     * Constructor por defecto
     */
    public GoalNotificationJPAServiceIMPL() {

    }

    public static GoalNotificationJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new GoalNotificationJPAServiceIMPL();
        return instance;
    }

    @Override
    public List<NotificacionMeta> getGoalNotificationsByStatus(int limit,
            long status, long status2, String textFilter, String textfilter2,
            EntityManager em) throws JPAException {

        final String metodo = "getGoalNotificationsByStatus";
        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<NotificacionMeta> criteriaQuery = criteriaBuilder
                    .createQuery(NotificacionMeta.class);

            Root<NotificacionMeta> from = criteriaQuery
                    .from(NotificacionMeta.class);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.or(criteriaBuilder.equal(
                    from.get(COMMON_STRING_STATUS), status), criteriaBuilder
                    .equal(from.get(COMMON_STRING_STATUS), status2)));

            conditions.add(criteriaBuilder.or(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TEXT), textFilter), criteriaBuilder
                    .equal(from.get(COMMON_STRING_TEXT), textfilter2)));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            criteriaQuery.orderBy(criteriaBuilder.asc(from
                    .get(COMMON_STRING_DATE)));

            TypedQuery<NotificacionMeta> typedQuery = em
                    .createQuery(criteriaQuery);
            if (limit > 0)
                typedQuery.setMaxResults(limit);

            return typedQuery.getResultList();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

}
