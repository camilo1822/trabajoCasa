package co.bdigital.admin.ejb.controller;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.bdigital.admin.messaging.services.isb.UnfreezeAccountRequestType;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.cmm.ejb.exception.MiddlewareException;
import co.bdigital.cmm.ejb.generic.ServiceController;
import co.bdigital.cmm.ejb.generic.ServiceControllerHelper;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * Session Bean implementation class UnLockClientFinacle
 * 
 * @author anders.barrios
 * @since 24/07/2017
 * @version 1.0
 */
@Stateless(mappedName = "UnLockClientFinacle")
@LocalBean
public class UnLockClientFinacle implements ServiceController {

    private ServiceControllerHelper sch;

    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;

    private CustomLogger logger;

    /**
     * Metodo para inicializar recursos del EJB.
     */
    @PostConstruct
    void init() {
        logger = new CustomLogger(UnLockClientFinacle.class);
        this.sch = ServiceControllerHelper.getInstance();
    }

    @Override
    public ResponseMessageObjectType executeOperation(
            RequestMessageObjectType request) {

        ResponseMessageObjectType responseFront = null;
        UnfreezeAccountRequestType unfreezeAccountRequestType = null;

        if (!this.sch.validateRequest(
                request.getRequestMessage().getRequestBody().getAny(),
                new UnfreezeAccountRequestType(),
                ConstantADM.SERVICE_OPERATION_UNFREEZENACCOUNT)) {

            return this.sch.responseErrorMessage(
                    request.getRequestMessage().getRequestHeader(),
                    Constant.ERROR_CODE_PARSE_PAYLOAD,
                    Constant.ERROR_MESSAGE_PARSING_ERROR_IN,
                    Constant.COMMON_SYSTEM_MDW, this.em);

        }

        try {
            unfreezeAccountRequestType = (UnfreezeAccountRequestType) this.sch
                    .parsePayload(
                            request.getRequestMessage().getRequestBody()
                                    .getAny(),
                            new UnfreezeAccountRequestType());

            responseFront = this.sch.createBrokerRequestNequiApp(request,
                    unfreezeAccountRequestType, null,
                    ConstantADM.COMMON_UNLOCK_NAME,
                    ConstantADM.COMNON_UNLOCK_NAMESPACE,
                    ConstantADM.COMMON_UNLOCK_OPERATION,
                    ConstantADM.COMMON_UNLOCK_RQ, ConstantADM.COMMON_UNLOCK_RS,
                    this.em);

        } catch (MiddlewareException e) {
            logger.error(ErrorEnum.BROKER_ERROR,
                    Constant.ERROR_MESSAGE_ADMIN_WEB_USER_DOESNT_EXIST, null);

            responseFront = sch.responseErrorMessage(
                    request.getRequestMessage().getRequestHeader(),
                    Constant.ERROR_CODE_COMMUNICATION_IIB,
                    Constant.COMMON_STRING_EMPTY_STRING,
                    Constant.COMMON_SYSTEM_MDW, this.em);
        }

        return responseFront;
    }

}
