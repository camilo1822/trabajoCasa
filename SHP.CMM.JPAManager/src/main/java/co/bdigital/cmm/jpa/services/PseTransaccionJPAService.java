/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.PseTransaccion;

/**
 * @author hansel.ospino
 *
 */
public interface PseTransaccionJPAService {

    /**
     * <p>
     * Consulta todas las transacciones con el <code>transactionStatus</code>
     * indicado.
     * </p>
     * 
     * @param transactionStatusId
     *            El ID del transactionStatus esta referenciado en la constante
     *            de ServiceInterface.
     * @param startDate
     *            fecha de inicio para la carga de las transacciones.
     * @return Transacciones activas con el <code>transactionStatus</code>
     *         especificado.
     * @throws JPAException
     */
    public List<PseTransaccion> getPseTransaccionsByTransactionStatusId(
            String transactionStatusId, Date startDate, EntityManager em)
            throws JPAException;

    /**
     * <p>
     * Consulta las transacciones por estado (atributo <code>estado</code>).
     * </p>
     * 
     * @param estado
     * @param em
     * @return
     * @throws JPAException
     */
    public List<PseTransaccion> getPseTransaccionsByEstado(String estado,
            EntityManager em) throws JPAException;

    /**
     * <p>
     * Consulta las transacciones en cualquiera de los estados indicados y por
     * tipo de transacci&oacute;n.
     * </p>
     * 
     * @param estados
     *            listado de estados.
     * @param tipoTransaccion
     *            tipo de transacci&oacute;n
     * @param em
     * @return transacciones en cualquiera de los estados indicados en
     *         <code>estados</code> y del tipo de transacci&oacute;n indicada.
     * @throws JPAException
     */
    public List<PseTransaccion> getPseTransaccionsInEstadosAndTransactionType(
            List<String> estados, String tipoTransaccion, EntityManager em)
            throws JPAException;

    /**
     * <p>
     * M&eacute;todo que consulta una recarga PSE por ticketId.
     * </p>
     * 
     * @param ticketId
     * @param em
     * @return Transacci&oacute;n PSE.
     * @throws JPAException
     */
    public PseTransaccion getPseTransaccionCashInByTicketId(String ticketId,
            EntityManager em) throws JPAException;

    /**
     * <p>
     * M&eacute;todo que consulta una transacci&oacute;n por ticket ID y cuya
     * fecha de modificaci&oacute;n sea mayor o igual a la fecha especificada.
     * </p>
     * 
     * @param ticketId
     * @param transactionType
     * @param startDate
     * @param em
     * @return PseTransaccion transaccion con el ticketId indicado y con la
     *         restriccion de la fecha.
     * @throws JPAException
     */
    public PseTransaccion getPseTransaccionCashInByTicketIdAndStartDate(
            String ticketId, Date startDate, EntityManager em)
            throws JPAException;

    /**
     * <p>
     * Consulta las transacciones en el estado especificado y que la diferencia
     * entre la fecha actual y la fecha de modificacion de cada registro esté
     * dentro del rango de tiempo y que su división modular sea por la cantidad
     * de minutos indicada.
     * </p>
     * 
     * @param transactionStatusId
     *            estado de la transacci&oacute;n.
     * @param rangeDelimiter
     *            rango de minutos que delimitan la diferencia entre la fecha de
     *            modificacion y la fecha actual.
     * @param minutesDelimiter
     *            delimitador de minutos para indicar la frecuencia.
     * @param transactionType
     *            tipo de transacci&oacute;n.
     * @param em
     * @return PseTransaccion transacciones con las condiciones especificadas.
     * @throws JPAException
     */
    public List<PseTransaccion> getPseTransaccionsByStatusIdAndRangeDeliminterAndMinutesDelimiter(
            String transactionStatusId, String rangeDelimiter,
            String minutesDelimiter, String transactionType, EntityManager em)
            throws JPAException;

    /**
     * <p>
     * M&eacute;todo que consulta las transacciones en el estado y tipo de
     * transacci&oacute;n especificado y con los estados de marcaci&oacute;n
     * indicados
     * </p>
     * 
     * @param estados
     *            Posibles estados de marcaci&oacute;n que se van a tener en
     *            cuenta en la consulta.
     * @param tipoTransaccion
     *            ID del tipo de transacci&oacute;n (<code>ttId</code>)
     * @param idEstadoTransaccion
     *            ID del estado de la transacci&oacute;n ( <code>etId</code>).
     * @param em
     * @return Transacciones PSE con el tipo de transacci&oacute;, estado de
     *         transacci&oaacute; y con los posibles estados de marcaci&oacute;n
     *         especificados.
     * @throws JPAException
     */
    public List<PseTransaccion> getPseTransaccionsInEstadosAndTransactionStateAndTransactionType(
            List<String> estados, String tipoTransaccion,
            String idEstadoTransaccion, EntityManager em) throws JPAException;

    /**
     * <p>
     * M&eacute;todo que consulta una transacci&oacute;n PSE por ticketId y por
     * tipo de transacci&oacute;n.
     * </p>
     * 
     * @param ticketId
     * @param tipoTransaccion
     *            tipo de transacci&oacute;n. Valores: 1 - Recarga, 2 - Pago.
     * @param trazabilityCode
     *            CUS de la transacci&oacute;n.
     * @param em
     * @return Transacci&oacute;n PSE.
     * @throws JPAException
     */
    public PseTransaccion getPseTransaccionByTicketIdAndTransactionTypeAndTrazabilityCode(
            String ticketId, String tipoTransaccion, String trazabilityCode,
            EntityManager em) throws JPAException;

    /**
     * <p>
     * M&eacute;todo que consulta una transacci&oacute;n por ticket ID y cuya
     * fecha de modificaci&oacute;n sea mayor o igual a la fecha especificada.
     * </p>
     * 
     * @param ticketId
     * @param transactionType
     * @param trazabilityCode
     *            CUS de la transacci&oacute;n.
     * @param startDate
     * @param em
     * @return PseTransaccion transaccion con el ticketId indicado y con la
     *         restriccion de la fecha.
     * @throws JPAException
     */
    public PseTransaccion getPseTransaccionByTicketIdAndTransactionTypeAndTrazabilityCodeAndStartDate(
            String ticketId, String transactionType, String trazabilityCode,
            Date startDate, EntityManager em) throws JPAException;

}
