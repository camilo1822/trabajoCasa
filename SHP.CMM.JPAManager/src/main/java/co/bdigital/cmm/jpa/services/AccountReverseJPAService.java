/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.CuentaReverso;

/**
 * @author cristian.martinez@pragma.com.co
 *
 */
public interface AccountReverseJPAService {

    /**
     * Inserta un entity <code>CuentaReverso</code> en Base de Datos.
     * 
     * @param reversoPendiente
     * @param em
     * @throws JPAException
     */
    public void persistAccountReverse(CuentaReverso cuentaReverso,
            EntityManager em) throws JPAException;

    /**
     * Inserta una lista de entities <code>CuentaReverso</code> en Base de
     * Datos.
     * 
     * @param listCuentaReverso
     * @param em
     * @throws JPAException
     */
    public void persistListAccountReverse(
            List<CuentaReverso> listCuentaReverso, EntityManager em)
            throws JPAException;

}
