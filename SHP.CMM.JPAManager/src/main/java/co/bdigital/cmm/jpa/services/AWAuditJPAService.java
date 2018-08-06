package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AwAuditoria;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public interface AWAuditJPAService {

    /**
     * Inserta un entity <code>AwAuditoria</code> en Base de Datos.
     * 
     * @param awAudit
     * @param em
     * @throws JPAException
     */
    public void persistAWAudit(AwAuditoria awAudit, EntityManager em)
            throws JPAException;

}
