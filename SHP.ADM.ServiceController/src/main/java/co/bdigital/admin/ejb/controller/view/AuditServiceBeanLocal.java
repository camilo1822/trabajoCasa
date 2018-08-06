package co.bdigital.admin.ejb.controller.view;

import java.sql.Timestamp;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public interface AuditServiceBeanLocal {

    /**
     * Metodo para insertar auditoria
     * 
     * @param acctionId
     * @param email
     * @param date
     * @param description
     * @throws Exception
     */
    public void insertAudit(long acctionId, String email, Timestamp date,
            String description);

}
