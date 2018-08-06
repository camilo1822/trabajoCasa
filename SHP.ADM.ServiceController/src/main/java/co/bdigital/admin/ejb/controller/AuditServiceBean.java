package co.bdigital.admin.ejb.controller;

import java.sql.Timestamp;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.bdigital.admin.ejb.controller.view.AuditServiceBeanLocal;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.model.AwAccion;
import co.bdigital.cmm.jpa.model.AwAuditoria;
import co.bdigital.cmm.jpa.model.AwUsuario;
import co.bdigital.cmm.jpa.service.impl.AWAccionJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.AWAuditJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.AWUserJPAServiceIMPL;
import co.bdigital.cmm.jpa.services.AWAuditJPAService;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
@Stateless(mappedName = "AuditServiceBean")
@Local(AuditServiceBeanLocal.class)
public class AuditServiceBean implements AuditServiceBeanLocal {

    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;
    private AWAuditJPAService userJPA;
    private CustomLogger logger;

    @PostConstruct
    void init() {
        this.logger = new CustomLogger(AuditServiceBean.class);
        userJPA = new AWAuditJPAServiceIMPL();
    }

    /*
     * Metodo que escribe la auditoria en base de datos
     * 
     * @see
     * co.bdigital.admin.ejb.controller.view.AuditServiceBeanLocal#insertAudit
     * (co.bdigital.cmm.jpa.model.AwAuditoria)
     */
    @Override
    public void insertAudit(long acctionId, String email, Timestamp date,
            String description) {
        try {

            AwAccion awAction = AWAccionJPAServiceIMPL.getInstance()
                    .getAWAccion(acctionId, em);

            if (null != awAction) {

                AwUsuario awUser = AWUserJPAServiceIMPL.getInstance()
                        .getUserByEmail(email, em);

                if (null != awUser) {
                    AwAuditoria awAudit = new AwAuditoria();
                    awAudit.setAwAccion(awAction);
                    awAudit.setAwUsuario(awUser);
                    awAudit.setDescripcion(description);
                    awAudit.setFecha(date);
                    userJPA.persistAWAudit(awAudit, em);

                } else {
                    logger.error(ErrorEnum.DB_ERROR,
                            Constant.ERROR_MESSAGE_DB_QUERY_NO_RESULTS, null);
                }

            } else {
                logger.error(ErrorEnum.DB_ERROR,
                        Constant.ERROR_MESSAGE_DB_QUERY_NO_RESULTS, null);
            }

        } catch (Exception e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
        }

    }
}
