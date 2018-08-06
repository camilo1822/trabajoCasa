package co.bdigital.cmm.jpa.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.TransaccionPendiente;

public interface GetUnReadMovementsCountService {

    /**
     * Metodo que retorna el n�mero de peticiones pendientes
     * 
     * @param em
     * @param client_id
     * @param transaction_id
     * @param fromDate
     * @param toDate
     * @return String
     * @throws JPAException
     */

    public String getUnReadMovementsCount(EntityManager em, String phone_number,
            int transaction_id, int state, Date fromDate, Date toDate)
            throws JPAException;

    /**
     * Metodo que retorna el n�mero de pagos pendientes
     * 
     * @param em
     * @param clienteId
     * @param transaction_type
     * @param transaction_status
     * @return String
     * @throws JPAException
     */
    public String getUnReadPaymentsCount(EntityManager em, String clienteId,
            int transaction_type, int transaction_status) throws JPAException;

    /**
     * Metodo que retorna los pagos pendientes
     * 
     * @param em
     * @param clienteId
     * @param transaction_type
     * @param transaction_status
     * @param fromDate
     * @param toDate
     * @return List<TransaccionPendiente>
     * @throws JPAException
     */
    public List<TransaccionPendiente> getUnReadPayments(EntityManager em,
            String clienteId, int transaction_type, int transaction_status,
            Date fromDate, Date toDate) throws JPAException;

    /**
     * Metodo que retorna la transaccion pendiente
     * 
     * @param em
     * @param clienteId
     * @param transaction_type
     * @param transaction_status
     * @param transactionId
     * @return TransaccionPendiente
     * @throws JPAException
     */
    public TransaccionPendiente getTransactionPending(EntityManager em,
            String clienteId, int transaction_type, int transaction_status,
            String transactionId) throws JPAException;

    /**
     * Metodo que retorna la transaccion pendiente por transactionId
     * 
     * @param em
     * @param client_id
     * @param transaction_id
     * @return
     * @throws JPAException
     */
    public TransaccionPendiente getTransactionPendingByTransactionId(
            EntityManager em, String transactionId) throws JPAException;

    /**
     * Método que retorna la transacción pendiente y los detalles a partir de un
     * transactionId
     * 
     * @param em
     * @param transactionId
     * @return <code>TransaccionPendiente</code>
     * @throws JPAException
     */
    public TransaccionPendiente getTransactionPendingWithDetailsByTransactionId(
            EntityManager em, String transactionId) throws JPAException;

    /**
     * 
     * @param transactionId
     * @param transactionStatus
     * @return {@link List} of {@link TransaccionPendiente} by
     *         <code>transactionId</code> and <code>transactionStatus</code>
     * @param em
     * @throws JPAException
     */
    public List<TransaccionPendiente> getTransactionPendingWithDetailsByTransactionIdAndStatus(
            String transactionId, int transactionStatus, EntityManager em)
            throws JPAException;
}
