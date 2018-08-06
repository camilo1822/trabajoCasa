/**
 * 
 */
package co.bdigital.cmm.ejb.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.json.JSONObject;

import co.bdigital.cmm.messaging.esb.RequestHeaderOutMessageType;
import co.bdigital.shl.backout.service.bean.view.BackoutService;
import co.bdigital.shl.backout.service.bean.view.BackoutSilverPopService;
import co.bdigital.shl.backout.service.bean.view.closeaccount.CloseAccountService;
import co.bdigital.shl.backout.service.bean.view.finalizepsetransaction.FinalizePseTransactionService;
import co.bdigital.shl.backout.service.bean.view.markpsetransaction.MarkPseTransactionService;
import co.bdigital.shl.backout.service.bean.view.psepayment.PsePaymentService;
import co.bdigital.shl.backout.service.bean.view.registernotification.RegisterNotificationService;
import co.bdigital.shl.backout.service.bean.view.updateprofiledaon.UpdateProfileDaonService;
import co.bdigital.shl.backout.service.bean.view.updaterecharge.UpdateRechargeService;
import co.bdigital.shl.backout.service.bean.view.validatepushaction.ValidatePushActionService;
import co.bdigital.shl.backout.service.pojo.CloseAccountMessage;
import co.bdigital.shl.backout.service.pojo.MessageBroker;
import co.bdigital.shl.backout.service.pojo.PsePaymentMessage;
import co.bdigital.shl.backout.service.pojo.RequestMoneyMessage;
import co.bdigital.shl.backout.service.pojo.SendNotificationMessage;
import co.bdigital.shl.backout.service.pojo.UpdateProfileDaonMessage;
import co.bdigital.shl.backout.service.pojo.UpdatePtmRechargeMessage;
import co.bdigital.shl.backout.service.pojo.ValidatePushActionMessage;
import co.bdigital.shl.common.service.bean.view.ResourceLocator;
import co.bdigital.shl.common.service.pojo.CompensationMessage;
import co.bdigital.shl.common.service.pojo.EmailMessage;
import co.bdigital.shl.common.service.pojo.PendingTransactionMessage;
import co.bdigital.shl.psp.service.bean.view.PushActionBeanRemote;
import co.bdigital.shl.security.manager.bean.view.SecurityManager;
import co.bdigital.shl.tracer.CustomLogger;

/**
 * @author cristian.martinez
 *
 */
public class ContextUtilHelper {

    private static final String URL_SECURITY_MANAGER = "java:global/BDIGITAL.SEC/SHP.SEC.SecurityManager-1.0-SNAPSHOT/SecurityManagerBean!co.bdigital.shl.security.manager.bean.view.SecurityManager";
    private static final String URL_RESOURCE_LOCATOR = "java:global/CMM3.BDIGITAL.CommonService/SHP.CMM.CommonService-1.0-SNAPSHOT/ResourceLocatorBean!co.bdigital.shl.common.service.bean.view.ResourceLocator";
    private static final String URL_BACKOUT = "java:global/BCO3.BDIGITAL.ServiceFacade/SHP.BCO.ServiceController-1.0-SNAPSHOT/BackoutServiceBean!co.bdigital.shl.backout.service.bean.view.BackoutService";
    private static final String URL_BACKOUT_SP = "java:global/BCO3.SP.BDIGITAL.ServiceFacade/SHP.BCO.SP.ServiceController-1.0-SNAPSHOT/BackoutSPServiceBean!co.bdigital.shl.backout.service.bean.view.BackoutSilverPopService";
    private static final String URL_UPDATE_RECHARGE = "java:global/BCO3.BDIGITAL.ServiceFacade/SHP.BCO.ServiceController-1.0-SNAPSHOT/UpdateRechargeServiceBean!co.bdigital.shl.backout.service.bean.view.updaterecharge.UpdateRechargeService";
    private static final String URL_PUSH_ACTION_BEAN_REMOTE = "java:global/PSP1.BDIGITAL.ServiceFacade/SHP.PSP.ServiceFacade-1.0-SNAPSHOT/PushActionBean!co.bdigital.shl.psp.service.bean.view.PushActionBeanRemote";
    private static final String ERROR_LOOKUP = "Config Error: Lookup not found. ERROR al obtener referencia del EJB Remoto.";
    private static final String ERROR_LOOKUP_BACKOUT = "Config Error: Lookup not found";
    private static final String ERROR_LOOKUP_BACKOUT_GENERAL = "Config Error GENERAL: Lookup not found";
    private static CustomLogger logger;
    private static ContextUtilHelper instance;
    private SecurityManager securityManager;
    private ResourceLocator resourceLocator;
    private BackoutService backout;
    private RegisterNotificationService registerNotification;
    private CloseAccountService closeAccountService;
    private BackoutSilverPopService backoutSP;
    private UpdateRechargeService rechargeService;
    private PushActionBeanRemote pushActionBeanRemote;
    private FinalizePseTransactionService finalizePseTransactionService;
    private MarkPseTransactionService markPseTransactionService;
    private PsePaymentService psePaymentService;
    private ValidatePushActionService validatePushActionService;
    private UpdateProfileDaonService updateProfileDaonService;

    private static final String URL_REGISTER_NOTIFICATION_BACKOUT = "java:global/BCO3.BDIGITAL.ServiceFacade/SHP.BCO.ServiceController-1.0-SNAPSHOT/RegisterNotificationServiceBean!co.bdigital.shl.backout.service.bean.view.registernotification.RegisterNotificationService";

    private static final String URL_CLOSE_ACCOUNT_BACKOUT = "java:global/BCO.AccountServices.502/BCO.ACS.502.SEC-1.0-SNAPSHOT/CloseAccountServiceBean!co.bdigital.shl.backout.service.bean.view.closeaccount.CloseAccountService";
    private static final String URL_FINALIZE_PSE_TRANSACTION_BACKOUT = "java:global/BCO.PSECashIn.501/BCO.PCI.501.SEC-1.0-SNAPSHOT/FinalizePseTransactionServiceBean!co.bdigital.shl.backout.service.bean.view.finalizepsetransaction.FinalizePseTransactionService";
    private static final String URL_MARK_PSE_TRANSACTION_BACKOUT = "java:global/BCO.PSECashIn.501/BCO.PCI.501.SEC-1.0-SNAPSHOT/MarkPseTransactionServiceBean!co.bdigital.shl.backout.service.bean.view.markpsetransaction.MarkPseTransactionService";
    private static final String URL_PSE_PAYMENT_BACKOUT = "java:global/BCO.PSECashOut.501/BCO.PCO.501.SEC-1.0-SNAPSHOT/PsePaymentServiceBean!co.bdigital.shl.backout.service.bean.view.psepayment.PsePaymentService";
    private static final String URL_VALIDATE_PUSH_ACTION_BACKOUT = "java:global/BCO.PSECashOut.501/BCO.PCO.501.SEC-1.0-SNAPSHOT/ValidatePushActionServiceBean!co.bdigital.shl.backout.service.bean.view.validatepushaction.ValidatePushActionService";
    private static final String URL_UPDATE_PROFILE_DAON_BACKOUT = "java:global/BCO.BiometricServices.501/BCO.BIS.501.SEC-1.0-SNAPSHOT/UpdateProfileDaonServiceBean!co.bdigital.shl.backout.service.bean.view.updateprofiledaon.UpdateProfileDaonService";

    /**
     * Devuelve una instancia de la clase ContextUtilHelper
     * 
     * @return ContextUtilHelper
     */
    public static ContextUtilHelper getInstance() {
        if (instance == null)
            instance = new ContextUtilHelper();

        return instance;
    }

    /**
     * 
     */
    private ContextUtilHelper() {
        logger = new CustomLogger(this.getClass());
    }

    /**
     * 
     * @return <code>SecurityManager</code>
     */
    public SecurityManager instanceSecurityManagerRemote() {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (this.securityManager == null) {

                this.securityManager = (SecurityManager) initialContext
                        .lookup(URL_SECURITY_MANAGER);
            }

        } catch (NamingException e) {

            logger.error(ERROR_LOOKUP, e);

        } catch (Exception e) {

            logger.error(ERROR_LOOKUP, e);

        } finally {

            this.closeInitialContext(initialContext);

        }

        return this.securityManager;

    }

    /**
     * 
     * @return <code>ResourceLocator</code>
     */
    public ResourceLocator instanceResourceLocatorRemote() {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (this.resourceLocator == null) {

                this.resourceLocator = (ResourceLocator) initialContext
                        .lookup(URL_RESOURCE_LOCATOR);
            }

        } catch (NamingException e) {

            logger.error(ERROR_LOOKUP, e);

        } catch (Exception e) {

            logger.error(ERROR_LOOKUP, e);

        } finally {

            this.closeInitialContext(initialContext);

        }

        return this.resourceLocator;

    }

    /**
     * Metodo que registra el objeto Json del error
     * 
     * @param request
     * @return
     * @throws NamingException
     */
    public BackoutService insertMessageErrorRequest(
            RequestHeaderOutMessageType request) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (this.backout == null) {
                this.backout = (BackoutService) initialContext
                        .lookup(URL_BACKOUT);
            }

            if (this.backout != null) {
                JSONObject requestJson = new JSONObject(request);
                this.backout.pushMessageError(requestJson.toString());
            }
        } catch (NamingException e) {
            logger.error(ERROR_LOOKUP_BACKOUT, e);
        } finally {
            this.closeInitialContext(initialContext);
        }
        return this.backout;

    }

    /**
     * Metodo que registra el string con los datos necesarios para mandar
     * notificacion al usuario.
     * 
     * @param request
     * @return
     * @throws NamingException
     */
    public void insertNotificationMessageRequest(EmailMessage message) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (this.backout == null) {
                this.backout = (BackoutService) initialContext
                        .lookup(URL_BACKOUT);
            }

            if (this.backout != null) {
                this.backout.pushNotificacionMessage(message);
            }
        } catch (NamingException e) {
            logger.error(ERROR_LOOKUP_BACKOUT, e);
        } finally {
            this.closeInitialContext(initialContext);
        }

    }

    /**
     * Metodo que envia la peticion de congelamiento de la cuenta de un usuario
     * si no ha sido validado por CIFIN.
     * 
     * @param request
     * @return
     * @throws NamingException
     */
    public void insertFrozenAccountMessageRequest(String frozenAccountParameters) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (this.backout == null) {
                this.backout = (BackoutService) initialContext
                        .lookup(URL_BACKOUT);
            }

            if (this.backout != null) {
                this.backout.pushFrozenAccountMessage(frozenAccountParameters);
            }
        } catch (NamingException e) {
            logger.error(ERROR_LOOKUP_BACKOUT, e);
        } finally {
            this.closeInitialContext(initialContext);
        }

    }

    /**
     * Metodo que envia la peticion para actualizar el estado del usuario
     * 
     * @param request
     * @return
     * @throws NamingException
     */
    public void insertLiteRegistryMessageRequest(String liteRegistryMessage) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (this.backout == null) {
                this.backout = (BackoutService) initialContext
                        .lookup(URL_BACKOUT);
            }

            if (this.backout != null) {
                this.backout.pushLiteRegistryMessage(liteRegistryMessage);
            }
        } catch (NamingException e) {
            logger.error(ERROR_LOOKUP_BACKOUT, e);
        } finally {
            this.closeInitialContext(initialContext);
        }

    }

    /**
     * Metodo que envia la peticion para la compensacion de la vinculacion.
     * 
     * @param compensationMessage
     */
    public void insertCompensationMessageRequest(
            CompensationMessage compensationMessage) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (this.backout == null) {
                this.backout = (BackoutService) initialContext
                        .lookup(URL_BACKOUT);
            }

            if (this.backout != null) {
                this.backout.pushCompensationMessage(compensationMessage);
            }
        } catch (NamingException e) {
            logger.error(ERROR_LOOKUP_BACKOUT, e);
        } finally {
            this.closeInitialContext(initialContext);
        }

    }

    /**
     * Metodo que envia la peticion para valida el Push Action en Broker.
     * 
     * @param validatePushActionMessage
     */
    public void insertValidatePushActionMessageRequest(
            ValidatePushActionMessage validatePushActionMessage) {
        InitialContext initialContext = null;
        try {

            initialContext = new InitialContext();

            if (this.validatePushActionService == null) {
                this.validatePushActionService = (ValidatePushActionService) initialContext
                        .lookup(URL_VALIDATE_PUSH_ACTION_BACKOUT);
            }

            if (this.validatePushActionService != null) {
                this.validatePushActionService
                        .pushValidatePushActionMessage(validatePushActionMessage);
            }
        } catch (NamingException e) {

            logger.error(ERROR_LOOKUP_BACKOUT, e);
        } finally {
            this.closeInitialContext(initialContext);
        }

    }

    /**
     * Metodo que envia la peticion para descogenlar transacciones pendientes.
     * 
     * @param request
     * @return
     * @throws NamingException
     */
    public void insertPendingTransactionMessageRequest(
            PendingTransactionMessage pendingTransactionMessage) {

        InitialContext initialContext = null;
        try {

            initialContext = new InitialContext();

            if (this.backout == null) {
                this.backout = (BackoutService) initialContext
                        .lookup(URL_BACKOUT);
            }

            if (this.backout != null) {
                this.backout
                        .pushPendingTransactionnMessage(pendingTransactionMessage);
            }
        } catch (NamingException e) {
            logger.error(ERROR_LOOKUP_BACKOUT, e);
        } finally {
            this.closeInitialContext(initialContext);
        }

    }

    /**
     * 
     * @return <code>PushActionBeanRemote</code>
     */
    public PushActionBeanRemote instancePushActionBeanRemote() {

        InitialContext initialContext = null;
        try {

            initialContext = new InitialContext();

            if (this.pushActionBeanRemote == null) {

                this.pushActionBeanRemote = (PushActionBeanRemote) initialContext
                        .lookup(URL_PUSH_ACTION_BEAN_REMOTE);
            }

        } catch (NamingException e) {

            logger.error(ERROR_LOOKUP + URL_PUSH_ACTION_BEAN_REMOTE, e);

        } catch (Exception e) {

            logger.error(ERROR_LOOKUP + URL_PUSH_ACTION_BEAN_REMOTE, e);

        } finally {

            this.closeInitialContext(initialContext);

        }

        return this.pushActionBeanRemote;

    }

    /**
     * <p>
     * Método que envia la peticion para confirmar transacción con PSE.
     * </p>
     * 
     * @param request
     * @return
     * @throws NamingException
     */
    public void insertPsePaymentMessageRequest(
            PsePaymentMessage psePaymentMessage) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (this.psePaymentService == null) {
                this.psePaymentService = (PsePaymentService) initialContext
                        .lookup(URL_PSE_PAYMENT_BACKOUT);
            }

            if (this.psePaymentService != null) {
                this.psePaymentService.pushPsePaymentMessage(psePaymentMessage);
            }
        } catch (NamingException e) {
            logger.error(ERROR_LOOKUP_BACKOUT, e);
        } finally {
            this.closeInitialContext(initialContext);
        }

    }

    /**
     * <p>
     * Método que envia la peticion para ejecutar el flujo de segundo llamado a
     * CIFIN.
     * </p>
     * 
     */
    public void insertSecondCalledCIFINMessageRequest(
            MessageBroker messageBroker) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (this.backout == null) {
                this.backout = (BackoutService) initialContext
                        .lookup(URL_BACKOUT);
            }

            if (this.backout != null) {
                this.backout.pushSecondCalledCIFINMessage(messageBroker);
            }

        } catch (NamingException e) {

            logger.error(ERROR_LOOKUP_BACKOUT, e);

        } catch (Exception e) {

            logger.error(ERROR_LOOKUP_BACKOUT_GENERAL, e);

        } finally {

            this.closeInitialContext(initialContext);

        }

    }

    /**
     * Metodo que construye un objeto <code>MessageBroker</code>.
     * 
     * @param appType
     * @param channel
     * @param clientID
     * @param messageId
     * @param region
     * @return <code>MessageBroker</code>
     */
    public MessageBroker getMessageBroker(String appType, String channel,
            String clientID, String messageId, String region) {

        MessageBroker messageBroker;

        messageBroker = new MessageBroker();

        messageBroker.setAppType(appType);
        messageBroker.setChannel(channel);
        messageBroker.setClientID(clientID);
        messageBroker.setMessageId(messageId);
        messageBroker.setRegion(region);

        return messageBroker;
    }

    /**
     * <p>
     * Metodo que env&iacute;a el mensaje a backout para ejecutar la
     * validaci&oacute;n de sistemas externos.
     * </p>
     * 
     * @param messageBroker
     */
    public void insertExternalSystemValidationMessageRequest(
            MessageBroker messageBroker) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (this.backout == null) {
                this.backout = (BackoutService) initialContext
                        .lookup(URL_BACKOUT);
            }

            if (this.backout != null) {
                this.backout.pushExternalSystemValidationMessage(messageBroker);
            }

        } catch (NamingException e) {

            logger.error(ERROR_LOOKUP_BACKOUT, e);

        } catch (Exception e) {

            logger.error(ERROR_LOOKUP_BACKOUT_GENERAL, e);

        } finally {

            this.closeInitialContext(initialContext);

        }

    }

    /**
     * <p>
     * Metodo que env&iacute;a el mensaje a backout para ejecutar la
     * finalizacion de transacciones de pse
     * </p>
     * 
     * @param messageBroker
     */
    public void insertFinalizePseTransactionMessageRequest(
            MessageBroker messageBroker) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (null == this.finalizePseTransactionService) {
                this.finalizePseTransactionService = (FinalizePseTransactionService) initialContext
                        .lookup(URL_FINALIZE_PSE_TRANSACTION_BACKOUT);
            }

            if (null != this.finalizePseTransactionService) {
                this.finalizePseTransactionService
                        .pushFinalizePseTransactionMessage(messageBroker);
            }

        } catch (NamingException e) {

            logger.error(ERROR_LOOKUP_BACKOUT, e);

        } catch (Exception e) {

            logger.error(ERROR_LOOKUP_BACKOUT_GENERAL, e);

        } finally {

            this.closeInitialContext(initialContext);

        }

    }

    /**
     * <p>
     * Metodo que env&iacute;a el mensaje a backout para ejecutar la marcación
     * de transacciones de pse
     * </p>
     * 
     * @param messageBroker
     */
    public void insertMarkPseTransactionMessageRequest(
            MessageBroker messageBroker) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (null == this.markPseTransactionService) {
                this.markPseTransactionService = (MarkPseTransactionService) initialContext
                        .lookup(URL_MARK_PSE_TRANSACTION_BACKOUT);
            }

            if (null != this.markPseTransactionService) {
                this.markPseTransactionService
                        .pushMarkPseTransactionMessage(messageBroker);
            }

        } catch (NamingException e) {

            logger.error(ERROR_LOOKUP_BACKOUT, e);

        } catch (Exception e) {

            logger.error(ERROR_LOOKUP_BACKOUT_GENERAL, e);

        } finally {

            this.closeInitialContext(initialContext);

        }

    }

    /**
     * <p>
     * Metodo que env&iacute;a el mensaje a backout para ejecutar el envio de
     * las notificaciones de Metas
     * </p>
     * 
     * @param sendNotificationMessage
     */
    public void insertGoalNotificationMessageRequest(
            SendNotificationMessage sendNotificationMessage) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (null == this.backout) {
                this.backout = (BackoutService) initialContext
                        .lookup(URL_BACKOUT);
            }

            if (null != this.backout) {
                this.backout
                        .pushGoalNotificationMessage(sendNotificationMessage);
            }

        } catch (NamingException e) {

            logger.error(ERROR_LOOKUP_BACKOUT, e);

        } catch (Exception e) {

            logger.error(ERROR_LOOKUP_BACKOUT_GENERAL, e);

        } finally {

            this.closeInitialContext(initialContext);

        }

    }

    /**
     * <p>
     * Metodo que env&iacute;a el mensaje a backout para ejecutar los reversos
     * pendientes
     * </p>
     * 
     * @param messageBroker
     */
    public void insertReversePaymentMessageRequest(MessageBroker messageBroker) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (null == this.backout) {
                this.backout = (BackoutService) initialContext
                        .lookup(URL_BACKOUT);
            }

            if (null != this.backout) {
                this.backout.pushReversePaymentMessage(messageBroker);
            }

        } catch (NamingException e) {

            logger.error(ERROR_LOOKUP_BACKOUT, e);

        } catch (Exception e) {

            logger.error(ERROR_LOOKUP_BACKOUT_GENERAL, e);

        } finally {

            this.closeInitialContext(initialContext);

        }

    }

    /**
     * <p>
     * Metodo que env&iacute;a el mensaje a backout para la integración con
     * silverpop
     * </p>
     * 
     * @param messageBroker
     */
    public void insertSilverpopIntegrationMessageRequest(
            MessageBroker messageBroker) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (null == this.backoutSP) {
                this.backoutSP = (BackoutSilverPopService) initialContext
                        .lookup(URL_BACKOUT_SP);
            }

            if (null != this.backoutSP) {
                this.backoutSP.pushSilverpopIntegrationMessage(messageBroker);
            }

        } catch (NamingException e) {

            logger.error(ERROR_LOOKUP_BACKOUT, e);

        } catch (Exception e) {

            logger.error(ERROR_LOOKUP_BACKOUT_GENERAL, e);

        } finally {

            this.closeInitialContext(initialContext);

        }

    }

    /**
     * <p>
     * Metodo que env&iacute;a el mensaje a backout para ejecutar las peticiones
     * de dinero
     * </p>
     * 
     * @param requestMoneyMessage
     */
    public void insertRequestMoneyMessageRequest(
            RequestMoneyMessage requestMoneyMessage) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (null == this.backout) {
                this.backout = (BackoutService) initialContext
                        .lookup(URL_BACKOUT);
            }

            if (null != this.backout) {
                this.backout.pushRequestMoneyMessage(requestMoneyMessage);
            }

        } catch (NamingException e) {

            logger.error(ERROR_LOOKUP_BACKOUT, e);

        } catch (Exception e) {

            logger.error(ERROR_LOOKUP_BACKOUT_GENERAL, e);

        } finally {

            this.closeInitialContext(initialContext);

        }

    }

    /**
     * <p>
     * Metodo que env&iacute;a el mensaje a backout para actualizar las recargas
     * PTM
     * </p>
     * 
     * @param updatePtmRechargeMessage
     */
    public void insertUpdateRechargeRequest(
            UpdatePtmRechargeMessage updatePtmRechargeMessage) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (null == this.rechargeService) {
                this.rechargeService = (UpdateRechargeService) initialContext
                        .lookup(URL_UPDATE_RECHARGE);
            }

            if (null != this.rechargeService) {
                this.rechargeService
                        .pushUpdateRecharge(updatePtmRechargeMessage);
            }

        } catch (NamingException e) {

            logger.error(ERROR_LOOKUP_BACKOUT, e);

        } catch (Exception e) {

            logger.error(ERROR_LOOKUP_BACKOUT_GENERAL, e);

        } finally {

            this.closeInitialContext(initialContext);

        }

    }

    /**
     * <p>
     * Metodo que env&iacute;a el mensaje a backout para hacer el envío de
     * notificaciones SMS para usuarios vinculados entre dos horas de
     * anterioridad.
     * </p>
     * 
     * @param messageBroker
     */
    public void insertRegisterNotificationMessageRequest(
            MessageBroker messageBroker) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (null == this.registerNotification) {
                this.registerNotification = (RegisterNotificationService) initialContext
                        .lookup(URL_REGISTER_NOTIFICATION_BACKOUT);
            }

            if (null != this.registerNotification) {
                this.registerNotification
                        .pushRegisterNotificationMessage(messageBroker);
            }

        } catch (NamingException e) {

            logger.error(ERROR_LOOKUP_BACKOUT, e);

        } catch (Exception e) {

            logger.error(ERROR_LOOKUP_BACKOUT_GENERAL, e);

        } finally {

            this.closeInitialContext(initialContext);

        }

    }

    /**
     * <p>
     * Metodo que env&iacute;a el mensaje a backout para el cierre de cuenta de
     * sistemas externos
     * </p>
     * 
     * @param closeAccountMessage
     */
    public void insertCloseAccountMessageRequest(
            CloseAccountMessage closeAccountMessage) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (null == this.closeAccountService) {
                this.closeAccountService = (CloseAccountService) initialContext
                        .lookup(URL_CLOSE_ACCOUNT_BACKOUT);
            }

            if (null != this.closeAccountService) {
                this.closeAccountService
                        .pushCloseAccountMessage(closeAccountMessage);
            }

        } catch (NamingException e) {

            logger.error(ERROR_LOOKUP_BACKOUT, e);

        } catch (Exception e) {

            logger.error(ERROR_LOOKUP_BACKOUT_GENERAL, e);

        } finally {

            this.closeInitialContext(initialContext);

        }

    }

    /**
     * <p>
     * Metodo que env&iacute;a el mensaje a backout para la actualizaci&oacute;n
     * del perfil en DAON.
     * </p>
     * 
     * @param updateProfileDaonMessage
     */
    public void insertUpdateProfileDaonMessageRequest(
            UpdateProfileDaonMessage updateProfileDaonMessage) {

        InitialContext initialContext = null;

        try {

            initialContext = new InitialContext();

            if (null == this.updateProfileDaonService) {
                this.updateProfileDaonService = (UpdateProfileDaonService) initialContext
                        .lookup(URL_UPDATE_PROFILE_DAON_BACKOUT);
            }

            if (null != this.updateProfileDaonService) {
                this.updateProfileDaonService
                        .pushUpdateProfileDaonMessage(updateProfileDaonMessage);
            }

        } catch (NamingException e) {

            logger.error(ERROR_LOOKUP_BACKOUT, e);

        } catch (Exception e) {

            logger.error(ERROR_LOOKUP_BACKOUT_GENERAL, e);

        } finally {

            this.closeInitialContext(initialContext);

        }

    }

    /**
     * <p>
     * M&eacute;todo que cierra el contexto.
     * </p>
     * 
     * @param initialContext
     *            contexto a cerrar.
     */
    private void closeInitialContext(InitialContext initialContext) {
        try {
            if (null != initialContext) {
                initialContext.close();
            }
        } catch (NamingException e) {
            logger.error(ERROR_LOOKUP_BACKOUT, e);
        }
    }

}
