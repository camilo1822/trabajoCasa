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

import co.bdigital.admin.ejb.controller.view.GetPromotionServiceBeanLocal;
import co.bdigital.admin.util.CallServiceBean;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.ServiceControllerBean;
import co.bdigital.admin.util.WebConsoleUtil;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Parametro;
import co.bdigital.cmm.jpa.model.PromocionComercio;
import co.bdigital.cmm.jpa.model.PromocionOperacion;
import co.bdigital.cmm.jpa.model.PromocionRegla;
import co.bdigital.cmm.jpa.model.PromocionUsuario;
import co.bdigital.cmm.jpa.service.impl.ParameterJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.PromocionUsuarioJPAServiceIMPL;
import co.bdigital.cmm.jpa.services.ParameterJPAService;
import co.bdigital.cmm.jpa.services.PromocionUsuarioJPAService;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;
import co.nequi.message.integration.services.IntegrationRQ;
import co.nequi.message.integration.services.IntegrationRS;
import co.nequi.message.integration.services.getpromotiondetail.GetPromotionDetailRequestType;
import co.nequi.message.integration.services.getpromotiondetail.RulesType;
import co.nequi.message.registry.serviceregistry.RegistryRS;

/**
 * 
 * @author juan.molinab
 *
 */
@Stateless
@Local(GetPromotionServiceBeanLocal.class)
public class GetPromotionServiceBean extends CallServiceBean
        implements GetPromotionServiceBeanLocal {

    private CustomLogger logger;
    @EJB
    private ServiceControllerBean serviceLocatorBean;
    private EntityManagerFactory entityManagerFactory;

    private PromocionUsuarioJPAService promocionUsuarioJPAService;

    private ParameterJPAService parameterJPA;

    private EntityManager emFRM;

    @PersistenceContext(unitName = ConstantADM.COMMON_STRING_JPA_MANAGER)
    private EntityManager em;

    @Resource(name = ConstantADM.COMMON_STRING_GET_PROMOTION_IS_TRACEABLE)
    private Boolean getPromtionIsTraceable;

    @Resource(name = ConstantADM.COMMON_STRING_GET_PROMOTION_IS_DEBUGABLE)
    private Boolean getPromtionIsDebugable;

    /**
     * Metodo para inicializar recursos del EJB.
     */
    @PostConstruct
    void init() {
        logger = new CustomLogger(GetPromotionServiceBean.class);
        this.entityManagerFactory = Persistence
                .createEntityManagerFactory(Constant.FRM_MANAGER);
        logger = new CustomLogger(GetPromotionServiceBean.class,
                getPromtionIsTraceable, getPromtionIsDebugable);
        this.promocionUsuarioJPAService = new PromocionUsuarioJPAServiceIMPL();
        this.parameterJPA = new ParameterJPAServiceIMPL();
    }

    @PreDestroy
    void shutdown() {
        if ((null != this.entityManagerFactory)
                && (this.entityManagerFactory.isOpen())) {

            this.entityManagerFactory.close();
        }

        if ((null != this.emFRM) && (this.emFRM.isOpen())) {

            this.emFRM.close();
        }
    }

    @Override
    public RulesType getPromotion(String service, String region, String type,
            String operation, String idPromotion, String descrptionRule) {

        GetPromotionDetailRequestType getPromotionDetailRequestType;
        RulesType rulesType = null;
        List<RulesType> listRules = null;

        try {
            this.emFRM = this.entityManagerFactory.createEntityManager();
            getPromotionDetailRequestType = WebConsoleUtil
                    .getGetPromotionDetailRequestType(service, operation, type,
                            region, idPromotion);
            IntegrationRQ integrationRQ = WebConsoleUtil.requestMessage(
                    ConstantADM.COMMON_STRING_OP_GET_SERVICE,
                    ConstantADM.COMMON_STRING_OP_GET_PROMOTION,
                    ConstantADM.COMMON_STRING_SERVICE_VERSION, region);
            integrationRQ.getIntegrationRequest().getBody()
                    .setGetPromotionDetailRequest(
                            getPromotionDetailRequestType);

            RegistryRS registryRS = serviceLocatorBean.lookup(integrationRQ,
                    ConstantADM.COMMON_STRING_SERVICE_NAME_PROM_SERVICES,
                    ConstantADM.COMMON_STRING_GET_OPERATION,
                    ConstantADM.COMMON_STRING_SERVICE_REGION_CORE,
                    ConstantADM.COMMON_STRING_SERVICE_VERSION_ONE_ZERO_ZERO,
                    emFRM);

            IntegrationRS integrationResponse = (IntegrationRS) callService(
                    integrationRQ, registryRS.getRegistryResponse().getBody()
                            .getLookupResponse(),
                    IntegrationRS.class, logger);

            listRules = integrationResponse.getIntegrationResponse().getBody()
                    .getGetPromotionDetailResponse().getRules();

            for (int i = 0; i < listRules.size(); i++) {
                if (listRules.get(i).getDescription().equals(descrptionRule)) {
                    rulesType = listRules.get(i);
                }
            }

        } catch (Exception e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
        } finally {
            if ((null != this.emFRM) && (this.emFRM.isOpen())) {

                this.emFRM.close();
            }
        }
        return rulesType;
    }

    @Override
    public Boolean deletePromotions(String promocionOperacionId) {
        boolean response = false;
        try {
            this.promocionUsuarioJPAService
                    .deleteAllPromocionUsuarioByPromocionOperacion(
                            promocionOperacionId, this.em);
            response = true;
        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR,
                    Constant.ERROR_MESSAGE_DELETE_DB_QUERY, e);
        }
        return response;
    }

    @Override
    public Boolean persistPromotions(List<PromocionUsuario> promocionUsuario,
            String promocionOperacionId) {
        boolean response = false;
        try {
            deletePromotions(promocionOperacionId);
            for (PromocionUsuario promocion : promocionUsuario) {
                this.promocionUsuarioJPAService
                        .persistPromocionUsuario(promocion, em);
            }
            response = true;
        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR,
                    Constant.ERROR_MESSAGE_INSERT_DB_QUERY, e);
        }
        return response;

    }

    @Override
    public PromocionOperacion getPromocionOperacion(String id) {
        PromocionOperacion promocionOperacion = null;
        try {
            promocionOperacion = this.promocionUsuarioJPAService
                    .getPromocionOperacionById(id, em);
        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
        }
        return promocionOperacion;
    }

    @Override
    public String getPromocionUsuarioSize(String id) {
        try {
            return this.promocionUsuarioJPAService.getPromocionUsuarioSize(id,
                    em);
        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
            return null;
        }
    }

    @Override
    public Boolean deletePromotionComercio(String promocionOperacionId) {
        boolean response = false;
        try {
            this.promocionUsuarioJPAService
                    .deleteAllPromocionComercioByPromocionOperacion(
                            promocionOperacionId, this.em);
            response = true;
        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR,
                    Constant.ERROR_MESSAGE_DELETE_DB_QUERY, e);
        }
        return response;
    }

    @Override
    public Boolean persistPromotionComercio(
            List<PromocionComercio> promocionComercio,
            String promocionOperacionId) {
        boolean response = false;
        try {
            deletePromotionComercio(promocionOperacionId);
            for (PromocionComercio promocion : promocionComercio) {
                this.promocionUsuarioJPAService
                        .persistPromocionComercio(promocion, em);
            }
            response = true;
        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR,
                    Constant.ERROR_MESSAGE_INSERT_DB_QUERY, e);
        }
        return response;

    }

    @Override
    public List<PromocionComercio> getPromocionComercio(String id) {
        List<PromocionComercio> promocionComercio = null;
        try {
            promocionComercio = this.promocionUsuarioJPAService
                    .getPromocionComercio(id, em);
        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
        }
        return promocionComercio;
    }

    @Override
    public List<PromocionUsuario> getPromocionUsuarioLimit(String id,
            String region) {
        List<PromocionUsuario> promocionUsuario = null;
        try {
            List<Parametro> parameters = parameterJPA.getRegionParameter(
                    ConstantADM.COMMON_STRING_WEB_ADMIN_PARAMETERS_ID, region,
                    em);

            String limit = WebConsoleUtil.getParameterByName(parameters,
                    ConstantADM.COMMON_STRING_USERS_LIMIT_PARAMETER_NAME,
                    ConstantADM.COMMON_STRING_USERS_LIMIT_DEFAULT);

            promocionUsuario = this.promocionUsuarioJPAService
                    .getPromocionUsuarioLimit(id, limit, em);
        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
        }
        return promocionUsuario;
    }

    @Override
    public Boolean persistPromocionRegla(String name,
            PromocionOperacion promocionOperacion) {
        boolean response = false;
        try {
            String id = this.promocionUsuarioJPAService
                    .getPromocionReglaMaxId(em);
            PromocionRegla promocionRegla = WebConsoleUtil.getPromocionRegla(id,
                    name, promocionOperacion);
            this.promocionUsuarioJPAService
                    .persistPromocionRegla(promocionRegla, em);
            response = true;
        } catch (Exception e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
            return false;
        }
        return response;
    }

    @Override
    public PromocionOperacion getPromocionOperacionByService(String servicio,
            String region) {
        PromocionOperacion promocionOperacion = null;
        try {
            promocionOperacion = this.promocionUsuarioJPAService
                    .getPromocionOperacionByService(servicio, region, em);

        } catch (Exception e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
        }
        return promocionOperacion;
    }

}
