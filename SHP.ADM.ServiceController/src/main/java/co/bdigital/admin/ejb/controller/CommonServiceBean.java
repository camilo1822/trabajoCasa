package co.bdigital.admin.ejb.controller;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.bdigital.admin.ejb.controller.view.CommonServiceBeanLocal;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Parametro;
import co.bdigital.cmm.jpa.service.impl.ParameterJPAServiceIMPL;
import co.bdigital.cmm.jpa.services.ParameterJPAService;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * Session Bean implementation class CommonServiceBean
 * 
 * @author hansel.ospino
 * @since 28/07/2017
 * @version 1.0
 */
@Stateless
@Local(CommonServiceBeanLocal.class)
public class CommonServiceBean implements CommonServiceBeanLocal {

    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;
    private CustomLogger logger;
    private ParameterJPAService parameterJPA;

    public CommonServiceBean() {
        logger = new CustomLogger(CommonServiceBean.class);
        parameterJPA = new ParameterJPAServiceIMPL();
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.bdigital.admin.ejb.controller.view.CommonServiceBeanLocal#
     * getParameters(java.lang.String, java.lang.String)
     */
    public List<Parametro> getParameters(String tipoParametroId,
            String region) {
        List<Parametro> parameters = null;
        try {
            parameters = parameterJPA.getRegionParameter(tipoParametroId,
                    region, em);
        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
        }
        return parameters;
    }

}