package co.bdigital.admin.ejb.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.bdigital.admin.bco.acs502.massivecloseaccount.MassiveCloseAccountInitialBean;
import co.bdigital.admin.ejb.controller.view.CloseAccountServiceBeanLocal;
import co.bdigital.admin.messaging.biometric.closeaccount.CloseAccountDaonBrokerRQType;
import co.bdigital.admin.messaging.biometric.closeaccount.CloseAccountDaonBrokerServiceRequestType;
import co.bdigital.admin.messaging.biometric.markfinacle.MarkFinacleRQType;
import co.bdigital.admin.messaging.biometric.markfinacle.MarkFinacleServiceRequestType;
import co.bdigital.admin.messaging.closeaccount.CloseAccountRQType;
import co.bdigital.admin.messaging.closeaccount.CloseAccountServiceRequestType;
import co.bdigital.admin.messaging.services.bco.acs502.BackoutRQ;
import co.bdigital.admin.messaging.services.bco.acs502.BackoutRequestType;
import co.bdigital.admin.messaging.services.bco.acs502.BcoBodyType;
import co.bdigital.admin.messaging.services.bco.acs502.MassiveCloseAccountServiceRequestType;
import co.bdigital.admin.model.StatusResponse;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.WebConsoleUtil;
import co.bdigital.cmm.consumer.exception.RestClientException;
import co.bdigital.cmm.consumer.service.impl.RestClientImpl;
import co.bdigital.cmm.ejb.generic.ServiceControllerHelper;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.ejb.util.ContextUtilHelper;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.cmm.jpa.model.Parametro;
import co.bdigital.cmm.jpa.service.impl.ClientByPhoneNumberJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.ParameterJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.ServiceClientImpl;
import co.bdigital.cmm.jpa.services.ClientByPhoneNumberJPAService;
import co.bdigital.cmm.jpa.services.ParameterJPAService;
import co.bdigital.cmm.messaging.esb.BodyType;
import co.bdigital.cmm.messaging.esb.RequestHeaderOutMessageType;
import co.bdigital.cmm.messaging.esb.RequestHeaderOutType;
import co.bdigital.cmm.messaging.esb.ResponseHeaderOutMessageType;
import co.bdigital.cmm.messaging.esb.ResponseStatusType;
import co.bdigital.shl.common.service.bean.view.ResourceLocator;
import co.bdigital.shl.common.service.pojo.EmailMessage;
import co.bdigital.shl.common.service.pojo.ResourcesPojo;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * Session Bean implementation class CloseAccountServiceBean
 * 
 * @author juan.molinab
 * @since 04/08/2016
 * @version 1.0
 */
@Stateless
@Local(CloseAccountServiceBeanLocal.class)
public class CloseAccountServiceBean implements CloseAccountServiceBeanLocal {

    private static String SERVICE_OPERATION_CLOSE_ACCOUNT = "closeAccount";

    private ResourceLocator resourceLocator;
    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;
    private CustomLogger logger;
    private ServiceControllerHelper sch;
    private ContextUtilHelper contextUtilHelper;
    private ClientByPhoneNumberJPAService clienteByPhoneJPA;
    private ParameterJPAService parameterJPA;
    // Atributos de peticion hacia IIB
    private static final String NAME = "Cuenta";
    private static final String NAMESPACE = "http://co.bancaDigital/sherpa/servicio/cuenta/v1.0";
    private static final String OPERATION_DELETE_ACCOUNT_FINACLE = "eliminarCuentaFinacle";
    private static final String OPERATION_DELETE_ACCOUNT_EASY = "eliminarCuentaEasy";
    private static final String OPERATION_DELETE_ACCOUNT_LDAP = "eliminarCuentaLDAP";
    private static final String OPERATION_DELETE_ACCOUNT_DB = "eliminarCuentaDB";
    private static final String OPERATION_UPDATE_FINACLE_ACCOUNT = "actualizarFinacleCuenta";
    private static final String OPERATION_DELETE_ACCOUNT_DAON = "eliminarCuentaDaon";
    private static final String REQUEST_CLOSE_ACCOUNT = "closeAccountRQ";
    private static final String RESPONSE_CLOSE_ACCOUNT = "closeAccountRS";
    private static final String REQUEST_MARK_FINACLE = "markFinacleRQ";
    private static final String RESPONSE_MARK_FINACLE = "markFinacleRS";
    private static final String REQUEST_DAON = "closeAccountDaonBrokerRQ";
    private static final String RESPONSE_DAON = "closeAccountDaonRS";

    @EJB
    private MassiveCloseAccountInitialBean massiveCloseAccountInitialBean;

    public CloseAccountServiceBean() {
        logger = new CustomLogger(CloseAccountServiceBean.class);
        sch = ServiceControllerHelper.getInstance();
        contextUtilHelper = ContextUtilHelper.getInstance();
        clienteByPhoneJPA = new ClientByPhoneNumberJPAServiceIMPL();
        parameterJPA = new ParameterJPAServiceIMPL();
    }

    /**
     * Metodo para cerrar la cuenta de un cliente
     * 
     * @param phoneNumber
     *            numero del celular
     * @return response con un estado y descripcion de la operacion
     */
    public StatusResponse closeAccount(String phoneNumber, String region) {

        StatusResponse responseFront = new StatusResponse();
        RequestHeaderOutMessageType requestESB = new RequestHeaderOutMessageType();

        Cliente cliente = null;
        try {

            // *************
            cliente = ServiceClientImpl.getInstance()
                    .getClientbyPhone(phoneNumber, em);

        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
            return this.buildResponseFront(Constant.ERROR_CODE_COMMUNICATION_BD,
                    Constant.ERROR_MESSAGE_DB_QUERY);
        }

        if (null == cliente || (null != cliente.getEstadoId()
                && cliente.getEstadoId().compareTo(new BigDecimal(
                        ConstantADM.COMMON_INT_CLIENT_LINKED_STATUS)) != ConstantADM.COMMON_INT_ZERO)) {

            logger.error(ErrorEnum.DB_ERROR,
                    Constant.ERROR_MESSAGE_DB_QUERY_NO_RESULTS, null);
            return this.buildResponseFront(Constant.ERROR_CODE_NO_RESULTS_BD,
                    Constant.ERROR_MESSAGE_DB_QUERY);
        }

        CloseAccountRQType requestBodyBrokerCloseAccount = new CloseAccountRQType();
        requestBodyBrokerCloseAccount.setPhoneNumber(phoneNumber);
        CloseAccountServiceRequestType requestBrokerCloseAccount = new CloseAccountServiceRequestType();
        requestBrokerCloseAccount
                .setCloseAccountRQ(requestBodyBrokerCloseAccount);

        // Se ejecuta llamado a operación para eliminar cuenta en FINACLE
        responseFront = deleteFinacleAccount(requestBrokerCloseAccount,
                requestESB, region);

        // Llamado a la Queue de WAS para envio de Correo de notificacion
        if (responseFront.getStatusCode()
                .equals(Constant.COMMON_STRING_SUCCESS_MAYUS)) {

            EmailMessage emailMessage = new EmailMessage();
            emailMessage.setPhoneNumber(phoneNumber);
            emailMessage.setTipoEmail(Constant.CODE_MESSAGE_CLOSE_ACCOUNT);
            emailMessage.setOperation(SERVICE_OPERATION_CLOSE_ACCOUNT);
            emailMessage.setParams(Constant.COMMON_STRING_NOTIFICACIONS_EMAIL,
                    cliente.getCorreoElectronico());

            ContextUtilHelper.getInstance()
                    .insertNotificationMessageRequest(emailMessage);
        }

        return responseFront;

    }

    private StatusResponse buildResponseFront(String code,
            String messageDescription) {
        StatusResponse responseFront = new StatusResponse();
        responseFront.setStatusCode(code);
        responseFront.setStatusDesc(messageDescription);
        return responseFront;
    }

    /**
     * Método que consume operaci�n del bus para eliminar cuenta en FINACLE
     * 
     * @param request
     * @param requestESB
     * @param sch
     * @param resourcesPojo
     * @return
     */
    private StatusResponse deleteFinacleAccount(
            CloseAccountServiceRequestType requestBroker,
            RequestHeaderOutMessageType requestESB, String region) {
        // ****************************** REQUEST ESB
        // ****************************************
        // Se configura del Header ESB
        RequestHeaderOutType requestHeaderOutType = new RequestHeaderOutType();
        Date systemDate = new Date();
        requestHeaderOutType.setHeader(sch.configureRequestESBHeader(
                Constant.COMMON_STRING_ADM_CHANNEL_ID, null,
                String.valueOf(systemDate.getTime()), NAME, NAMESPACE,
                OPERATION_DELETE_ACCOUNT_FINACLE));

        // Configuramos la operaci�n:
        requestHeaderOutType.getHeader().setMessageContext(
                sch.configureMessageContext(REQUEST_CLOSE_ACCOUNT,
                        RESPONSE_CLOSE_ACCOUNT));

        // Configuramos las propiedades de internacionalizacion:
        this.sch.addPropertyInternationalizationMessageContext(
                requestHeaderOutType.getHeader().getMessageContext(),
                (null != region ? region : Constant.SERVICE_REGION_CO));

        // Se Configura el Body
        BodyType bodyType = new BodyType();
        bodyType.setAny(requestBroker);
        requestHeaderOutType.setBody(bodyType);
        // Se configura el RequestESB Completo
        requestESB.setRequestHeaderOut(requestHeaderOutType);
        // ************************************************************************************
        // ****************************** Envío de Mensaje al IIB
        // ****************************
        ResponseHeaderOutMessageType responseESB = new ResponseHeaderOutMessageType();

        try {
            ResourcesPojo resourcesPojo;

            this.resourceLocator = contextUtilHelper
                    .instanceResourceLocatorRemote();
            resourcesPojo = this.resourceLocator.getConnectionData(NAMESPACE);

            if (null == resourcesPojo) {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        Constant.ERROR_MESSAGE_SINGLETON, null);
                return this.buildResponseFront(Constant.ERROR_CODE_SINGLETON,
                        Constant.ERROR_MESSAGE_SINGLETON);
            }

            responseESB = RestClientImpl.getInstance(resourcesPojo)
                    .executeOperation(requestESB);

            // Si la operaci�n fue exitosa se procede a ejecutar el resto del
            // proceso, de lo contrario finaliza.
            if (responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()
                    .equalsIgnoreCase(Constant.COMMON_STRING_ZERO)) {

                deleteDetectIDAccount(requestBroker, requestESB, region);
            } else if (responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()
                    .equalsIgnoreCase(Constant.CODE_ERROR_BALANCE_ZERO)) {
                ResponseStatusType reponseErrorBalance = responseESB
                        .getResponseHeaderOut().getHeader().getResponseStatus();
                return this.buildResponseFront(
                        reponseErrorBalance.getStatusCode(),
                        reponseErrorBalance.getErrorMessage());
            }
        } catch (RestClientException e) {
            contextUtilHelper.insertMessageErrorRequest(requestESB);
            logger.error(ErrorEnum.BROKER_ERROR,
                    Constant.ERROR_MESSAGE_IIB_COMMUNICATION, e);
            return this.buildResponseFront(
                    Constant.ERROR_CODE_COMMUNICATION_IIB,
                    Constant.COMMON_STRING_EMPTY_STRING);
        }
        return this.buildResponseFront(Constant.COMMON_STRING_SUCCESS_MAYUS,
                null);
    }

    /**
     * Método asÃ­ncrono que consume operación del Bus para eliminar DETECTID
     * 
     * @param request
     * @param requestESB
     * @param sch
     * @param resourcesPojo
     */
    private void deleteDetectIDAccount(
            CloseAccountServiceRequestType requestBroker,
            RequestHeaderOutMessageType requestESB, String region) {
        // ****************************** REQUEST ESB
        // ****************************************
        // Se configura del Header ESB
        RequestHeaderOutType requestHeaderOutType = new RequestHeaderOutType();
        Date systemDate = new Date();
        requestHeaderOutType.setHeader(sch.configureRequestESBHeader(
                Constant.COMMON_STRING_ADM_CHANNEL_ID, null,
                String.valueOf(systemDate.getTime()), NAME, NAMESPACE,
                OPERATION_DELETE_ACCOUNT_EASY));

        // Configuramos la operaci�n:
        requestHeaderOutType.getHeader().setMessageContext(
                sch.configureMessageContext(REQUEST_CLOSE_ACCOUNT,
                        RESPONSE_CLOSE_ACCOUNT));

        // Configuramos las propiedades de internacionalizacion:
        this.sch.addPropertyInternationalizationMessageContext(
                requestHeaderOutType.getHeader().getMessageContext(),
                (null != region ? region : Constant.SERVICE_REGION_CO));

        // Se Configura el Body
        BodyType bodyType = new BodyType();
        bodyType.setAny(requestBroker);
        requestHeaderOutType.setBody(bodyType);
        // Se configura el RequestESB Completo
        requestESB.setRequestHeaderOut(requestHeaderOutType);
        // ************************************************************************************
        // ****************************** Envío de Mensaje al IIB
        // ****************************

        try {
            ResourcesPojo resourcesPojo;

            this.resourceLocator = contextUtilHelper
                    .instanceResourceLocatorRemote();
            resourcesPojo = this.resourceLocator.getConnectionData(NAMESPACE);

            if (null == resourcesPojo) {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        Constant.ERROR_MESSAGE_SINGLETON, null);
            }

            ResponseHeaderOutMessageType responseESB = RestClientImpl
                    .getInstance(resourcesPojo).executeOperation(requestESB);

            if (!responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()
                    .equalsIgnoreCase(Constant.COMMON_STRING_ZERO)) {
                contextUtilHelper.insertMessageErrorRequest(requestESB);
            }
        } catch (RestClientException e) {
            contextUtilHelper.insertMessageErrorRequest(requestESB);
            logger.error(ErrorEnum.BROKER_ERROR,
                    Constant.ERROR_MESSAGE_IIB_COMMUNICATION, e);
        } finally {
            deleteLdapAccount(requestBroker, requestESB, region);

        }
    }

    /**
     * Método asÃ­ncrono que consume operación del Bus para eliminar cuenta de
     * LDAP
     * 
     * @param request
     * @param requestESB
     * @param sch
     */
    private void deleteLdapAccount(CloseAccountServiceRequestType requestBroker,
            RequestHeaderOutMessageType requestESB, String region) {
        // ****************************** REQUEST ESB
        // ****************************************
        // Se configura del Header ESB
        RequestHeaderOutType requestHeaderOutType = new RequestHeaderOutType();
        Date systemDate = new Date();
        requestHeaderOutType.setHeader(sch.configureRequestESBHeader(
                Constant.COMMON_STRING_ADM_CHANNEL_ID, null,
                String.valueOf(systemDate.getTime()), NAME, NAMESPACE,
                OPERATION_DELETE_ACCOUNT_LDAP));

        // Configuramos la operaci�n:
        requestHeaderOutType.getHeader().setMessageContext(
                sch.configureMessageContext(REQUEST_CLOSE_ACCOUNT,
                        RESPONSE_CLOSE_ACCOUNT));

        // Configuramos las propiedades de internacionalizacion:
        this.sch.addPropertyInternationalizationMessageContext(
                requestHeaderOutType.getHeader().getMessageContext(),
                (null != region ? region : Constant.SERVICE_REGION_CO));

        // Se Configura el Body
        BodyType bodyType = new BodyType();
        bodyType.setAny(requestBroker);
        requestHeaderOutType.setBody(bodyType);
        // Se configura el RequestESB Completo
        requestESB.setRequestHeaderOut(requestHeaderOutType);
        // ************************************************************************************
        // ****************************** Envío de Mensaje al IIB
        // ****************************
        try {
            ResourcesPojo resourcesPojo;

            this.resourceLocator = contextUtilHelper
                    .instanceResourceLocatorRemote();
            resourcesPojo = this.resourceLocator.getConnectionData(NAMESPACE);

            if (null == resourcesPojo) {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        Constant.ERROR_MESSAGE_SINGLETON, null);
            }

            ResponseHeaderOutMessageType responseESB = RestClientImpl
                    .getInstance(resourcesPojo).executeOperation(requestESB);
            if (!responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()
                    .equalsIgnoreCase(Constant.COMMON_STRING_ZERO)) {

                contextUtilHelper.insertMessageErrorRequest(requestESB);
            }
        } catch (RestClientException e) {
            contextUtilHelper.insertMessageErrorRequest(requestESB);
            logger.error(ErrorEnum.BROKER_ERROR,
                    Constant.ERROR_MESSAGE_IIB_COMMUNICATION, e);
        } finally {
            markFinacle(requestBroker, requestESB, region);
        }
    }

    /**
     * Método asíncrono que elimina de manera lógica usuario en la BD
     * 
     * @param request
     * @param requestESB
     * @param sch
     */
    private void deleteBDAccount(CloseAccountServiceRequestType requestBroker,
            RequestHeaderOutMessageType requestESB, String region) {
        // ****************************** REQUEST ESB
        // ****************************************
        // Se configura del Header ESB
        RequestHeaderOutType requestHeaderOutType = new RequestHeaderOutType();
        Date systemDate = new Date();
        requestHeaderOutType.setHeader(sch.configureRequestESBHeader(
                Constant.COMMON_STRING_ADM_CHANNEL_ID, null,
                String.valueOf(systemDate.getTime()), NAME, NAMESPACE,
                OPERATION_DELETE_ACCOUNT_DB));

        // Configuramos la operaci�n:
        requestHeaderOutType.getHeader().setMessageContext(
                sch.configureMessageContext(REQUEST_CLOSE_ACCOUNT,
                        RESPONSE_CLOSE_ACCOUNT));

        // Configuramos las propiedades de internacionalizacion:
        this.sch.addPropertyInternationalizationMessageContext(
                requestHeaderOutType.getHeader().getMessageContext(),
                (null != region ? region : Constant.SERVICE_REGION_CO));

        // Se Configura el Body
        BodyType bodyType = new BodyType();
        bodyType.setAny(requestBroker);
        requestHeaderOutType.setBody(bodyType);
        // Se configura el RequestESB Completo
        requestESB.setRequestHeaderOut(requestHeaderOutType);
        // ************************************************************************************
        // ****************************** Envío de Mensaje al IIB
        // ****************************

        try {
            ResourcesPojo resourcesPojo;

            this.resourceLocator = contextUtilHelper
                    .instanceResourceLocatorRemote();
            resourcesPojo = this.resourceLocator.getConnectionData(NAMESPACE);

            if (null == resourcesPojo) {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        Constant.ERROR_MESSAGE_SINGLETON, null);
            }

            ResponseHeaderOutMessageType responseESB = RestClientImpl
                    .getInstance(resourcesPojo).executeOperation(requestESB);
            if (!responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()
                    .equalsIgnoreCase(Constant.COMMON_STRING_ZERO)) {

                contextUtilHelper.insertMessageErrorRequest(requestESB);
            }
        } catch (RestClientException e) {
            contextUtilHelper.insertMessageErrorRequest(requestESB);
            logger.error(ErrorEnum.BROKER_ERROR,
                    Constant.ERROR_MESSAGE_IIB_COMMUNICATION, e);

        }

    }

    /**
     * Método que hace la marcación de eliminado en Finacle
     * 
     * @param request
     * @param requestESB
     */
    private void markFinacle(CloseAccountServiceRequestType requestBroker,
            RequestHeaderOutMessageType requestESB, String region) {
        // ****************************** REQUEST ESB
        // ****************************************
        // Se configura del Header ESB
        RequestHeaderOutType requestHeaderOutType = new RequestHeaderOutType();
        Date systemDate = new Date();
        requestHeaderOutType.setHeader(sch.configureRequestESBHeader(
                Constant.COMMON_STRING_ADM_CHANNEL_ID, null,
                String.valueOf(systemDate.getTime()), NAME, NAMESPACE,
                OPERATION_UPDATE_FINACLE_ACCOUNT));

        // Configuramos la operaci�n:
        requestHeaderOutType.getHeader().setMessageContext(
                sch.configureMessageContext(REQUEST_MARK_FINACLE,
                        RESPONSE_MARK_FINACLE));

        // Configuramos las propiedades de internacionalizacion:
        this.sch.addPropertyInternationalizationMessageContext(
                requestHeaderOutType.getHeader().getMessageContext(),
                (null != region ? region : Constant.SERVICE_REGION_CO));

        // ***** Se obtiene CIFI por phoneNumber
        Cliente client = null;
        try {
            client = clienteByPhoneJPA.getClientByPhoneNumber(
                    requestBroker.getCloseAccountRQ().getPhoneNumber(), em);

            if (null != client) {
                CloseAccountDaonBrokerServiceRequestType accountDaonBrokerServiceRequestType = new CloseAccountDaonBrokerServiceRequestType();
                CloseAccountDaonBrokerRQType accountDaonBrokerRQType = new CloseAccountDaonBrokerRQType();
                accountDaonBrokerRQType.setCifid(client.getCifId());
                accountDaonBrokerRQType.setPhoneNumber(
                        requestBroker.getCloseAccountRQ().getPhoneNumber());
                accountDaonBrokerServiceRequestType
                        .setCloseAccountDaonBrokerRQ(accountDaonBrokerRQType);
                MarkFinacleServiceRequestType finacleServiceRequestType = new MarkFinacleServiceRequestType();
                MarkFinacleRQType finacleRQType = new MarkFinacleRQType();
                finacleRQType.setPhoneNumber(accountDaonBrokerServiceRequestType
                        .getCloseAccountDaonBrokerRQ().getPhoneNumber());
                DateFormat df = new SimpleDateFormat(
                        Constant.COMMON_STRING_DATE_LARGE_FORMAT);
                Date today = Calendar.getInstance().getTime();
                String reportDate = df.format(today);
                finacleRQType.setInactiveDate(reportDate);
                finacleServiceRequestType.setMarkFinacleRQ(finacleRQType);
                // Se Configura el Body
                BodyType bodyType = new BodyType();
                bodyType.setAny(finacleServiceRequestType);
                requestHeaderOutType.setBody(bodyType);
                // Se configura el RequestESB Completo
                requestESB.setRequestHeaderOut(requestHeaderOutType);
                // ************************************************************************************
                // ****************************** Envío de Mensaje al IIB
                // ****************************
                ResponseHeaderOutMessageType responseESB = new ResponseHeaderOutMessageType();
                try {
                    ResourcesPojo resourcesPojo;

                    this.resourceLocator = contextUtilHelper
                            .instanceResourceLocatorRemote();
                    resourcesPojo = this.resourceLocator
                            .getConnectionData(NAMESPACE);

                    if (null == resourcesPojo) {
                        logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                                Constant.ERROR_MESSAGE_SINGLETON, null);
                    }

                    responseESB = RestClientImpl.getInstance(resourcesPojo)
                            .executeOperation(requestESB);

                } catch (RestClientException e) {
                    contextUtilHelper.insertMessageErrorRequest(requestESB);
                    logger.error(ErrorEnum.BROKER_ERROR,
                            Constant.ERROR_MESSAGE_IIB_COMMUNICATION, e);

                } finally {
                    deleteBDAccount(requestBroker, requestESB, region);
                    if (responseESB.getResponseHeaderOut().getHeader()
                            .getResponseStatus().getStatusCode()
                            .equalsIgnoreCase(Constant.COMMON_STRING_ZERO)) {

                        deleteDaon(requestESB,
                                accountDaonBrokerServiceRequestType, region);
                    } else {

                        contextUtilHelper.insertMessageErrorRequest(requestESB);
                    }

                }
            } else {
                logger.error(ErrorEnum.DB_ERROR,
                        Constant.ERROR_MESSAGE_DB_QUERY_NO_RESULTS, null);
                contextUtilHelper.insertMessageErrorRequest(requestESB);
            }
        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
        }

    }

    /**
     * Método que ejecuta eliminado en Daon
     * 
     * @param request
     * @param requestESB
     * @param accountDaonBrokerServiceRequestType
     * @return
     */
    private void deleteDaon(RequestHeaderOutMessageType requestESB,
            CloseAccountDaonBrokerServiceRequestType accountDaonBrokerServiceRequestType,
            String region) {
        // ****************************** REQUEST ESB
        // ****************************************
        // Se configura del Header ESB
        RequestHeaderOutType requestHeaderOutType = new RequestHeaderOutType();
        Date systemDate = new Date();
        requestHeaderOutType.setHeader(sch.configureRequestESBHeader(
                Constant.COMMON_STRING_ADM_CHANNEL_ID, null,
                String.valueOf(systemDate.getTime()), NAME, NAMESPACE,
                OPERATION_DELETE_ACCOUNT_DAON));

        // Configuramos la operaci�n:
        requestHeaderOutType.getHeader().setMessageContext(
                sch.configureMessageContext(REQUEST_DAON, RESPONSE_DAON));

        // Configuramos las propiedades de internacionalizacion:
        this.sch.addPropertyInternationalizationMessageContext(
                requestHeaderOutType.getHeader().getMessageContext(),
                (null != region ? region : Constant.SERVICE_REGION_CO));

        // Se Configura el Body
        BodyType bodyType = new BodyType();
        bodyType.setAny(accountDaonBrokerServiceRequestType);
        requestHeaderOutType.setBody(bodyType);
        // Se configura el RequestESB Completo
        requestESB.setRequestHeaderOut(requestHeaderOutType);
        // ************************************************************************************
        // ****************************** Envío de Mensaje al IIB
        // ****************************
        ResponseHeaderOutMessageType responseESB = new ResponseHeaderOutMessageType();
        try {
            ResourcesPojo resourcesPojo;

            this.resourceLocator = contextUtilHelper
                    .instanceResourceLocatorRemote();
            resourcesPojo = this.resourceLocator.getConnectionData(NAMESPACE);

            if (null == resourcesPojo) {
                logger.error(ErrorEnum.DB_ERROR,
                        Constant.ERROR_MESSAGE_DB_QUERY_NO_RESULTS, null);
            }

            responseESB = RestClientImpl.getInstance(resourcesPojo)
                    .executeOperation(requestESB);
            if (!responseESB.getResponseHeaderOut().getHeader()
                    .getResponseStatus().getStatusCode()
                    .equalsIgnoreCase(Constant.COMMON_STRING_ZERO)) {

                contextUtilHelper.insertMessageErrorRequest(requestESB);
            }
        } catch (RestClientException e) {
            contextUtilHelper.insertMessageErrorRequest(requestESB);
            logger.error(ErrorEnum.BROKER_ERROR,
                    Constant.ERROR_MESSAGE_IIB_COMMUNICATION, e);

        }

    }

    /**
     * Metodo para devolver una lista con las razones del cierre de cuenta para
     * Easy Solution
     * 
     * @param tipoParametroId
     * @param region
     * @return
     * @throws JPAException
     */
    public List<Parametro> listReasonCode(String tipoParametroId, String region)
            throws JPAException {
        List<Parametro> listReason = parameterJPA
                .getRegionParameter(tipoParametroId, region, em);
        return listReason;
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.bdigital.admin.ejb.controller.view.CloseAccountServiceBeanLocal#
     * massiveCloseAccount(co.bdigital.admin.messaging.services.bco.acs502.
     * MassiveCloseAccountServiceRequestType, java.lang.String)
     */
    @Override
    public StatusResponse massiveCloseAccount(
            MassiveCloseAccountServiceRequestType request, String region) {
        try {

            BackoutRQ backoutRq = new BackoutRQ();
            BackoutRequestType backoutRequest = WebConsoleUtil
                    .buildBackoutRequestType(
                            String.valueOf(new Date().getTime()),
                            ConstantADM.COMMON_STRING_REQUEST_HEADER_NAME_SHP_ADM,
                            region, ConstantADM.COMMON_STRING_CONSUMER_ID_SHP,
                            ConstantADM.COMMON_STRING_CONSUMER_NAME_ADM,
                            ConstantADM.COMMON_STRING_CONTAINER_ID_WAS,
                            ConstantADM.COMMON_STRING_CONTAINER_NAME_WAS);

            backoutRq.setBackoutRequest(backoutRequest);

            BcoBodyType body = new BcoBodyType();
            body.setMassiveCloseAccountServiceRequest(request);
            backoutRequest.setBody(body);

            String massiveCloseAccountRequestAsString = WebConsoleUtil
                    .generateObjectToString(backoutRq);

            this.massiveCloseAccountInitialBean
                    .sendJmsStringMessage(massiveCloseAccountRequestAsString);

        } catch (JMSException | NamingException e) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    ConstantADM.ERROR_COMMUNICATION_BACKOUT, e);
            return this.buildResponseFront(
                    ConstantADM.ERROR_CODE_BACKOUT_COMMUNICATION,
                    ConstantADM.ERROR_COMMUNICATION_BACKOUT);
        }

        return this.buildResponseFront(ConstantADM.STATUS_CODE_SUCCESS,
                ConstantADM.COMMON_STRING_SUCCESS_MAYUS);
    }
}