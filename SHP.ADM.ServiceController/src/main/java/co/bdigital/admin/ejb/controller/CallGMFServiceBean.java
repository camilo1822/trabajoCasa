package co.bdigital.admin.ejb.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import co.bdigital.admin.ejb.controller.view.CallGMFServiceBeanLocal;
import co.bdigital.admin.messaging.services.gmf.MessageRQ;
import co.bdigital.admin.messaging.services.gmf.MessageRS;
import co.bdigital.admin.util.CallServiceBean;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.ServiceControllerBean;
import co.bdigital.admin.util.WebConsoleUtil;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.cmm.jpa.service.impl.ClientByDocumentJPAServiceIMPL;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;
import co.nequi.message.registry.serviceregistry.RegistryRS;

/**
 * 
 * @author juan.arboleda
 *
 */
@Stateless
@Local(CallGMFServiceBeanLocal.class)
public class CallGMFServiceBean extends CallServiceBean
        implements CallGMFServiceBeanLocal {

    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;
    private CustomLogger logger;
    @EJB
    private ServiceControllerBean serviceLocatorBean;

    private EntityManagerFactory entityManagerFactory;

    private EntityManager emFRM;

    @Resource(name = "callGMFIsTraceable")
    private Boolean callGMFIsTraceable;

    @Resource(name = "callGMFIsDebugable")
    private Boolean callGMFIsDebugable;

    /**
     * Metodo para inicializar recursos del EJB.
     */
    @PostConstruct
    void init() {
        logger = new CustomLogger(CallGMFServiceBean.class);
        this.entityManagerFactory = Persistence
                .createEntityManagerFactory(Constant.FRM_MANAGER);
        logger = new CustomLogger(CallGMFServiceBean.class, callGMFIsTraceable,
                callGMFIsDebugable);
    }

    @PreDestroy
    void shutdown() {

        if ((null != this.emFRM) && (this.emFRM.isOpen())) {

            this.emFRM.close();
        }
        if ((null != this.entityManagerFactory)
                && (this.entityManagerFactory.isOpen())) {

            this.entityManagerFactory.close();
        }
    }

    @Override
    public String callGMF(String accountNumber, String option) {

        String response = null;
        try {
            this.emFRM = this.entityManagerFactory.createEntityManager();
            MessageRQ messageRQ = WebConsoleUtil.getMessageRQ(accountNumber);

            RegistryRS registryRS = serviceLocatorBean.lookup(messageRQ,
                    ConstantADM.GMF_SERVICES, option,
                    ConstantADM.COMMON_STRING_SERVICE_REGION_CORE,
                    ConstantADM.COMMON_STRING_SERVICE_VERSION_ONE_ZERO_ZERO,
                    emFRM);

            MessageRS integrationResponse = (MessageRS) callService(messageRQ,
                    registryRS.getRegistryResponse().getBody()
                            .getLookupResponse(),
                    MessageRS.class, logger);

            if (ConstantADM.STATUS_CODE_SUCCESS.equals(integrationResponse
                    .getResponseMessage().getHeader().getStatus().getCode())) {
                if (ConstantADM.SERVICE_OPERATION_UNCHECK.equals(option)) {
                    return ConstantADM.MESSAGE_SUCCESS_UNMARK;
                } else {
                    return ConstantADM.MESSAGE_SUCCESS_MARK;
                }

            }

            response = integrationResponse.getResponseMessage().getHeader()
                    .getStatus().getDescription();

        } catch (Exception e) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    ConstantADM.ERROR_MESSAGE_REST_COMMUNICATION, e);
        }
        return response;
    }

    @Override
    public Cliente clientByAccount(String accountNumber, String region) {
        Cliente client = null;
        try {
            client = ClientByDocumentJPAServiceIMPL.getInstance()
                    .getClientByAccountNumber(accountNumber, region, em);
        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR,
                    ConstantADM.ERROR_FOUND_REGISTRY_DB, e);
        }
        return client;
    }

}
