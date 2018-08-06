package co.bdigital.admin.ejb.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.bdigital.admin.ejb.controller.view.NequiPointsServiceBeanLocal;
import co.bdigital.admin.messaging.services.isb.slack.SendSlackNotificationServiceRequestType;
import co.bdigital.admin.util.CallServiceBean;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.ServiceControllerBean;
import co.bdigital.admin.util.WebConsoleUtil;
import co.bdigital.cmm.ejb.generic.ServiceControllerHelper;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.model.Parametro;
import co.bdigital.cmm.jpa.service.impl.ParameterJPAServiceIMPL;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * 
 * @author juan.arboleda
 *
 */
@Stateless
@Local(NequiPointsServiceBeanLocal.class)
public class NequiPointsServiceBean extends CallServiceBean
        implements NequiPointsServiceBeanLocal {

    private CustomLogger logger;
    @EJB
    private ServiceControllerBean serviceLocatorBean;

    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;

    @Resource(name = "getPromtionIsTraceable")
    private Boolean getPromtionIsTraceable;

    @Resource(name = "getPromtionIsDebugable")
    private Boolean getPromtionIsDebugable;

    private ServiceControllerHelper sch;

    /**
     * Metodo para inicializar recursos del EJB.
     */
    @PostConstruct
    void init() {
        logger = new CustomLogger(UnLockClientFinacle.class);
        this.sch = ServiceControllerHelper.getInstance();
    }

    @Override
    public boolean slackNotification(RequestMessageObjectType request,
            String message, String region) {

        try {
            SendSlackNotificationServiceRequestType sendSlackNotificationRequestType;

            List<Parametro> parameters = ParameterJPAServiceIMPL.getInstance()
                    .getParamByType(ConstantADM.COMMON_STRING_SLACK_PARAMETERS,
                            this.em);

            Parametro slackChannel = WebConsoleUtil
                    .getParameterByNameAndCountryCode(parameters,
                            ConstantADM.COMMON_STRING_SLACK_CHANNEL_NAME,
                            region);
            // Llama servicio en BROKER para enviar Slack (sendMigration).
            sendSlackNotificationRequestType = WebConsoleUtil
                    .buildSendSlackNotificationServiceRequestType(
                            slackChannel.getValor(), message);

            ResponseMessageObjectType responseFront = sch
                    .createBrokerRequestNequiApp(request,
                            sendSlackNotificationRequestType, null,
                            ConstantADM.NAME_NOTIFICATION,
                            ConstantADM.NAMESPACE_UTIL_SERVICES_NOTIFICATION,
                            ConstantADM.OPERATION_SEND_SLACK_NOTIFICATION,
                            ConstantADM.OPERATION_SEND_SLACK_NOTIFICATION_RQ,
                            ConstantADM.OPERATION_SEND_SLACK_NOTIFICATION_RS,
                            this.em);

            String response = responseFront.getResponseMessage()
                    .getResponseHeader().getStatus().getStatusCode();

            if (ConstantADM.COMMON_STRING_ZERO.equals(response))
                return true;

        } catch (Exception e) {
            logger.error(ErrorEnum.BROKER_ERROR,
                    Constant.ERROR_MESSAGE_ADMIN_WEB_USER_DOESNT_EXIST, null);
            return false;
        }
        return false;
    }

}
