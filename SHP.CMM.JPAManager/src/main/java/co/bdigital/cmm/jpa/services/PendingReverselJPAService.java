/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.ReversoPendiente;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public interface PendingReverselJPAService {

    /**
     * Consulta de reversos pendientes por estado.
     * 
     * @param em
     * @return
     * @throws JPAException
     */
    public List<ReversoPendiente> getPendingReverseByStatus(String status,
            EntityManager em) throws JPAException;

    /**
     * Inserta un entity <code>ReversoPendiente</code> en Base de Datos.
     * 
     * @param reversoPendiente
     * @param em
     * @return <code>ReversoPendiente<code>
     * @throws JPAException
     */
    public ReversoPendiente persistPendingReverse(
            ReversoPendiente reversoPendiente, EntityManager em)
            throws JPAException;
}
