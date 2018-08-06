/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.NotificacionMeta;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public interface GoalNotificationJPAService {

    /**
     * Consulta para notificaciones de Metas por fecha de creacion
     * 
     * @param limit
     * @param status
     * @param status2
     * @param textFilter
     * @param textfilter2
     * @param em
     * @return
     * @throws JPAException
     */
    public List<NotificacionMeta> getGoalNotificationsByStatus(int limit,
            long status, long status2, String textFilter, String textfilter2,
            EntityManager em) throws JPAException;

}
