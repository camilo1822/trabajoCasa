package co.bdigital.cmm.jpa.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AwAuditoria;
import co.bdigital.cmm.jpa.services.AWAuditJPAService;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public class AWAuditJPAServiceIMPL implements AWAuditJPAService {

    /*
     * Metodo que escribe la entidad en base de datos.
     * 
     * @see
     * co.bdigital.cmm.jpa.services.AWAuditJPAService#persistAWAudit(co.bdigital
     * .cmm.jpa.model.AwAuditoria, javax.persistence.EntityManager)
     */
    @Override
    public void persistAWAudit(AwAuditoria awAudit, EntityManager em)
            throws JPAException {
        try {

            if (null != awAudit) {
                if (null == awAudit.getFecha()) {
                    Date fechaActual = new Date();
                    Timestamp fechaActualTimestamp = new Timestamp(
                            fechaActual.getTime());

                    awAudit.setFecha(fechaActualTimestamp);
                }
                em.persist(awAudit);
            }

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    AWAuditJPAServiceIMPL.class.getCanonicalName());
            errorString.append(":persistAWAuditoria:");
            throw new JPAException(errorString.toString(), e);
        }

    }
}
