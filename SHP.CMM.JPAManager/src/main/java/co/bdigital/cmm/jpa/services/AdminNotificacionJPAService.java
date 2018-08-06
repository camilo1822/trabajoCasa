package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AdminNotificacion;

/**
 * @author john.perez@pragma.com.co
 *
 */
public interface AdminNotificacionJPAService {

    /**
     * Consulta de biometrias administrativas por cliente_Id.
     * 
     * @param em
     * @return
     * @throws JPAException
     */
    public AdminNotificacion getAdminNotificacionByClienteId(Long clienteId,
            EntityManager em) throws JPAException;

    /**
     * Consulta del detalle de notificicaciones push administrativas
     * 
     * @param clienteId
     * @param trnId
     * @param type
     * @param em
     * @return
     * @throws JPAException
     */
    public AdminNotificacion getAdminNotificacion(long clienteId, String trnId,
            String type, EntityManager em) throws JPAException;

}
