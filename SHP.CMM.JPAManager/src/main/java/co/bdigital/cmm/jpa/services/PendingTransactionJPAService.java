package co.bdigital.cmm.jpa.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.TransaccionPendiente;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public interface PendingTransactionJPAService {

    /**
     * Consulta de transacciones pendientes
     * 
     * @param clientId
     * @param fromDate
     * @param toDate
     * @param em
     * @return List<OperadorRecarga>
     * @throws JPAException
     */
    public List<TransaccionPendiente> getPendingTransaction(long clientId,
            Date fromDate, Date toDate, String queryType, EntityManager em)
            throws JPAException;
    
    /**
     * Consulta de transacciones pendientes por cliente tipo tx y estado tx
     * 
     * @param em
     * @param clientId
     * @param transactionType
     * @param transactionStatus
     * @param transactionId
     * 
     * @return List<OperadorRecarga>
     * @throws JPAException
     */
    public List<TransaccionPendiente> getPendingTransactions(EntityManager em,
            String clienteId, int transactionType, int transactionStatus,
            String transactionId) throws JPAException;
    
    /**
     * Método encargado de insertar una transaccion pendiente en BD
     * 
     * @param em
     * @param transaccionPendiente
     * 
     * @throws JPAException
     */
    public void persistPendingTransaction(EntityManager em,
            TransaccionPendiente transaccionPendiente) throws JPAException;
    
    /**
     * Método encargado de actualizar transacciones pendientes con un estado
     * especifico
     * 
     * @param em
     * @param pendingTransactionsList
     * @param status
     * 
     * @throws JPAException
     */
    public void updatePendingTransactionsWithStatus(EntityManager em,
            List<TransaccionPendiente> pendingTransactionsList, String status)
            throws JPAException;

}
