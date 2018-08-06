/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.PushAction;

/**
 * @author hansel.ospino
 *
 */
public interface PushActionJPAService {

    /**
     * <p>
     * Consulta para el push action de un cliente, por tipo y estado desde la
     * fecha especificada.
     * </p>
     * 
     * @param clientId
     *            cliente.
     * @param pushActionStatusId
     *            estado.
     * @param pushActionTypeId
     *            tipo.
     * @param startDate
     *            fecha de inicio para tomar la consulta.
     * @param em
     * @return push actions con los criterios indicados.
     * @throws JPAException
     */
    public List<PushAction> getPushActionByClientAndTypeAndStatusAndStartDate(
            Long clientId, String pushActionStatusId, String pushActionTypeId,
            Date startDate, EntityManager em) throws JPAException;

    /**
     * <p>
     * Consulta de push action por su ID.
     * </p>
     * 
     * @param pushActionId
     * @param em
     * @return
     * @throws JPAException
     */
    public PushAction getPushActionById(Long pushActionId, EntityManager em)
            throws JPAException;

    /**
     * <p>
     * Consulta de push action por ID y por el ID del cliente.
     * </p>
     * 
     * @param pushActionId
     *            ID del push action
     * @param clientId
     *            ID del cliente
     * @param em
     * @return
     * @throws JPAException
     */
    public PushAction getPushActionByIdAndClientId(Long pushActionId,
            Long clientId, EntityManager em) throws JPAException;

    /**
     * <p>
     * Consulta de push action por transaction ID y por el ID del cliente.
     * </p>
     * 
     * @param transactionId
     *            transaction ID del push generado en EASY.
     * @param clientId
     *            ID del cliente
     * @param em
     * @return
     * @throws JPAException
     */
    public PushAction getPushActionByTransactionIdAndClientId(
            String transactionId, Long clientId, EntityManager em)
            throws JPAException;

    /**
     * <p>
     * Consulta de un push action para un cliente y ticket id especificado.
     * </p>
     * 
     * @param ticketId
     * @param clientId
     * @param em
     * @return PushAction para el cliente y el ticket id especificado.
     * @throws JPAException
     */
    public PushAction getPushActionByTicketIdAndClientId(String ticketId,
            Long clientId, EntityManager em) throws JPAException;

    /**
     * <p>
     * Consulta de un push action para un cliente y ticket id especificado.
     * </p>
     * 
     * @param ticketId
     * @param clientId
     * @param trazabilityCode
     * @param em
     * @return PushAction para el cliente y el ticket id especificado.
     * @throws JPAException
     */
    public PushAction getPushActionByTicketIdAndClientIdAndTrazabilityCode(
            String ticketId, Long clientId, String trazabilityCode,
            EntityManager em) throws JPAException;

}
