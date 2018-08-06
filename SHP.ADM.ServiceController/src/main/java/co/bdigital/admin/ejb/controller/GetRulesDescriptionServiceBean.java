package co.bdigital.admin.ejb.controller;

import java.util.List;

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

import co.bdigital.admin.ejb.controller.view.GetRulesDescriptionServiceBeanLocal;
import co.bdigital.admin.messaging.services.getrulesdescription.DescriptionType;
import co.bdigital.admin.messaging.services.getrulesdescription.GetRulesDescriptionRequestType;
import co.bdigital.admin.util.CallServiceBean;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.ServiceControllerBean;
import co.bdigital.admin.util.WebConsoleUtil;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;
import co.nequi.message.integration.services.IntegrationRQ;
import co.nequi.message.integration.services.IntegrationRS;
import co.nequi.message.registry.serviceregistry.RegistryRS;

/**
 * 
 * @author juan.arboleda
 *
 */
@Stateless
@Local(GetRulesDescriptionServiceBeanLocal.class)
public class GetRulesDescriptionServiceBean extends CallServiceBean
        implements GetRulesDescriptionServiceBeanLocal {

    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;
    private CustomLogger logger;
    @EJB
    private ServiceControllerBean serviceLocatorBean;
    private EntityManagerFactory entityManagerFactory;

    private EntityManager emFRM;

    @Resource(name = "getRulesDescriptionIsTraceable")
    private Boolean getRulesDescriptionIsTraceable;

    @Resource(name = "getRulesDescriptionIsDebugable")
    private Boolean getRulesDescriptionIsDebugable;

    /**
     * Metodo para inicializar recursos del EJB.
     */
    @PostConstruct
    void init() {
        logger = new CustomLogger(GetRulesDescriptionServiceBean.class);
        this.entityManagerFactory = Persistence
                .createEntityManagerFactory(Constant.FRM_MANAGER);
        logger = new CustomLogger(GetRulesDescriptionServiceBean.class,
                getRulesDescriptionIsTraceable, getRulesDescriptionIsDebugable);
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
    public List<DescriptionType> getRulesDescription(String region) {

        GetRulesDescriptionRequestType getRulesDescriptionRequestType = null;
        List<DescriptionType> descriptionType = null;
        try {
            this.emFRM = this.entityManagerFactory.createEntityManager();
            getRulesDescriptionRequestType = new GetRulesDescriptionRequestType();
            getRulesDescriptionRequestType.setRegion(region);
            IntegrationRQ integrationRQ = WebConsoleUtil.requestMessage(
                    ConstantADM.COMMON_STRING_OP_GET_SERVICE,
                    ConstantADM.COMMON_STRING_OP_GET_RULES,
                    ConstantADM.COMMON_STRING_SERVICE_VERSION, region);
            integrationRQ.getIntegrationRequest().getBody()
                    .setGetRulesDescriptionRequest(
                            getRulesDescriptionRequestType);
            RegistryRS registryRS = serviceLocatorBean.lookup(integrationRQ,
                    ConstantADM.COMMON_STRING_SERVICE_NAME_PROM_SERVICES,
                    ConstantADM.COMMON_STRING_GET_RULES_DESCRIPTION,
                    ConstantADM.COMMON_STRING_SERVICE_REGION_CORE,
                    ConstantADM.COMMON_STRING_SERVICE_VERSION_ONE_ZERO_ZERO,
                    emFRM);
            IntegrationRS integrationResponse = (IntegrationRS) callService(
                    integrationRQ, registryRS.getRegistryResponse().getBody()
                            .getLookupResponse(),
                    IntegrationRS.class, logger);
            descriptionType = integrationResponse.getIntegrationResponse()
                    .getBody().getGetRulesDescriptionResponse()
                    .getDescription();

        } catch (Exception e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
        }
        return descriptionType;
    }

}
