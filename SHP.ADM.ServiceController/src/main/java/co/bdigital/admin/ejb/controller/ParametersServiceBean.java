/**
 * 
 */
package co.bdigital.admin.ejb.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Parametro;
import co.bdigital.cmm.jpa.model.framework.WsProvider;
import co.bdigital.cmm.jpa.service.impl.ParameterJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.UrlBrokerJPAServiceIMPL;
import co.bdigital.cmm.jpa.services.ParameterJPAService;
import co.bdigital.cmm.jpa.services.UrlBrokerJPAService;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * @author hansel.ospino
 *
 */
@Singleton
public class ParametersServiceBean {

    private CustomLogger logger;

    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;

    private EntityManager emFRM;

    private UrlBrokerJPAService urlBrokerJPAService;

    private ParameterJPAService parameterJPAService;

    @PostConstruct
    void init() {
        this.logger = new CustomLogger(ParametersServiceBean.class);
        this.urlBrokerJPAService = new UrlBrokerJPAServiceIMPL();
        this.parameterJPAService = new ParameterJPAServiceIMPL();

        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("FRMManager");
        emFRM = emf.createEntityManager();
    }

    /**
     * 
     * @param system
     *            código del sistema
     * @return
     */
    public WsProvider getMdwServicesEndPoint(String system) {
        try {
            return urlBrokerJPAService.getWsProviderInfo(system, emFRM);
        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
        }
        return null;
    }

    /**
     * 
     * @param parameterType
     * @param region
     * @return
     */
    public List<Parametro> getRegionParameter(String parameterType,
            String region) {
        try {
            return parameterJPAService.getRegionParameter(parameterType, region,
                    em);
        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
        }
        return null;

    }

    /**
     * 
     * @param parameterType
     * @return
     */
    public List<Parametro> getParameterByType(String parameterType) {
        try {
            return parameterJPAService.getParamByType(parameterType, em);
        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
        }
        return null;

    }

}
