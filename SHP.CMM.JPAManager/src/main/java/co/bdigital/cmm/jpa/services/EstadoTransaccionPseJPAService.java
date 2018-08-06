/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.EstadoTransaccionPse;

/**
 * @author hansel.ospino@pragma.com.co
 *
 */
public interface EstadoTransaccionPseJPAService {

    /**
     * <p>
     * Consulta el estado de la transaccion PSE por el nombre.
     * </p>
     * 
     * @param nombre
     * @return
     * @throws JPAException
     */
    public EstadoTransaccionPse getEstadoTransaccionPseByNombre(String nombre,
            EntityManager em) throws JPAException;

    /**
     * <p>
     * Consulta tl tipo de estado de transaccion por Id.
     * </p>
     * 
     * @param transactionStatusId
     * @return
     * @throws JPAException
     */
    public EstadoTransaccionPse getEstadoTransaccionPseById(
            String transactionStatusId, EntityManager em) throws JPAException;

    /**
     * <p>
     * Metodo que consulta todos los estados de transacciones PSE.
     * </p>
     * 
     * @param em
     * @return Listado de transacciones PSE registradas
     * @throws JPAException
     */
    public List<EstadoTransaccionPse> getAllEstadoTransaccionPse(
            EntityManager em) throws JPAException;

}
