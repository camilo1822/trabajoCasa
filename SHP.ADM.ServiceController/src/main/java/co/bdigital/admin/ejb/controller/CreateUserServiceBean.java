package co.bdigital.admin.ejb.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.nequi.cmm.consumer.exception.CommonUtilException;
import com.nequi.cmm.consumer.registry.ServiceRegistry;
import com.nequi.cmm.consumer.rest.util.CallServiceUtil;
import com.nequi.cmm.consumer.rest.util.GenericRestClient;
import com.nequi.cmm.consumer.util.UtilJSON;

import co.bdigital.admin.messaging.integration.ldap401.v100.CreateUserLDAPRQType;
import co.bdigital.admin.messaging.integration.ldap401.v100.CreateUserLDAPServiceRequestType;
import co.bdigital.admin.messaging.services.createuser.CreateUserRQType;
import co.bdigital.admin.messaging.services.createuser.CreateUserRSType;
import co.bdigital.admin.messaging.services.createuser.CreateUserServiceRequestType;
import co.bdigital.admin.messaging.services.createuser.CreateUserServiceResponseType;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.WebConsoleUtil;
import co.bdigital.cmm.ejb.exception.MiddlewareException;
import co.bdigital.cmm.ejb.generic.ServiceController;
import co.bdigital.cmm.ejb.generic.ServiceControllerHelper;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AwRol;
import co.bdigital.cmm.jpa.model.AwUsuario;
import co.bdigital.cmm.jpa.model.DivisionGeografica;
import co.bdigital.cmm.jpa.model.Parametro;
import co.bdigital.cmm.jpa.service.impl.AWRolJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.AWUserJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.DivisionGeograficaJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.ParameterJPAServiceIMPL;
import co.bdigital.cmm.jpa.services.AWRolJPAService;
import co.bdigital.cmm.jpa.services.AWUserJPAService;
import co.bdigital.cmm.jpa.services.DivisionGeograficaJPAService;
import co.bdigital.cmm.jpa.services.ParameterJPAService;
import co.bdigital.cmm.messaging.esb.ResponseStatusType;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseBody;
import co.bdigital.cmm.messaging.general.ResponseHeader;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageType;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;
import co.nequi.message.registry.serviceregistry.RegistryRS;
import co.nequi.message.registry.serviceregistry.lookup.LookupResponseType;

/**
 * Session Bean implementation class CreateUserServiceBean
 * 
 * @author hansel.ospino
 * @since 24/11/2016
 * @version 1.0
 */
@Stateless(mappedName = "CreateUserServiceBean")
@LocalBean
public class CreateUserServiceBean extends CallServiceUtil
        implements ServiceController {

    private ServiceControllerHelper sch;

    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;

    private CustomLogger logger;
    @Context
    private HttpServletRequest requestHttp;

    private ParameterJPAService parameterJPAService;
    private DivisionGeograficaJPAService divisionGeograficaJPAService;
    private AWRolJPAService awRolJPAService;
    private AWUserJPAService awUserJPAService;

    /**
     * Metodo para inicializar recursos del EJB.
     */
    @PostConstruct
    void init() {
        logger = new CustomLogger(CreateUserServiceBean.class);
        this.parameterJPAService = ParameterJPAServiceIMPL.getInstance();
        this.sch = ServiceControllerHelper.getInstance();
        this.awRolJPAService = new AWRolJPAServiceIMPL();
        this.awUserJPAService = new AWUserJPAServiceIMPL();
        this.divisionGeograficaJPAService = new DivisionGeograficaJPAServiceIMPL();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.bdigita.mdw.ejb.generic.ServiceController#executeOperation(co.bdigital
     * .mdw.messaging.general.RequestMessageObjectType)
     */
    @Override
    public ResponseMessageObjectType executeOperation(
            RequestMessageObjectType request) {

        ResponseMessageObjectType responseFront = null;
        ResponseStatusType responseStatusType = new ResponseStatusType();
        CreateUserServiceRequestType payloadRequest;
        // Parametro para mapear el rol del LDAP e implementar la asignación de
        // grupo al usuario.
        Parametro ldapRole;
        String newUserEmail;

        if (!this.sch.validateRequest(
                request.getRequestMessage().getRequestBody().getAny(),
                new CreateUserServiceRequestType(),
                ConstantADM.SERVICE_OPERATION_CREATE_USER)) {

            return this.sch.responseErrorMessage(
                    request.getRequestMessage().getRequestHeader(),
                    Constant.ERROR_CODE_PARSE_PAYLOAD,
                    Constant.ERROR_MESSAGE_PARSING_ERROR_IN,
                    Constant.COMMON_SYSTEM_MDW, this.em);

        }

        CreateUserRSType createUserRS = new CreateUserRSType();
        CreateUserServiceResponseType createUserResponse = new CreateUserServiceResponseType();
        createUserResponse.setCreateUserRS(createUserRS);

        try {
            payloadRequest = (CreateUserServiceRequestType) this.sch
                    .parsePayload(
                            request.getRequestMessage().getRequestBody()
                                    .getAny(),
                            new CreateUserServiceRequestType());

            /*
             * Validacion de parametros
             */
            CreateUserRQType payload = payloadRequest.getCreateUserRQ();

            if (null == payload) {

                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        Constant.ERROR_CODE_INVALID_PARAMS, null);

                return this.sch.responseErrorMessage(
                        request.getRequestMessage().getRequestHeader(),
                        Constant.ERROR_CODE_INVALID_PARAMS,
                        Constant.ERROR_MESSAGE_INVALID_PARAMS,
                        Constant.COMMON_SYSTEM_MDW, this.em);
            }

            if (null == payload.getMail()
                    || ConstantADM.AW_USER_MAIL_MAX_LENGTH < payload.getMail()
                            .length()) {

                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        Constant.ERROR_CODE_INVALID_PARAMS, null);

                return this.sch.responseErrorMessage(
                        request.getRequestMessage().getRequestHeader(),
                        Constant.ERROR_CODE_INVALID_PARAMS,
                        Constant.ERROR_MESSAGE_INVALID_PARAMS,
                        Constant.COMMON_SYSTEM_MDW, this.em);
            }

            if (null == payload.getNames()
                    || ConstantADM.AW_USER_NAMES_MAX_LENGTH < payload.getNames()
                            .length()) {

                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        Constant.ERROR_CODE_INVALID_PARAMS, null);

                return this.sch.responseErrorMessage(
                        request.getRequestMessage().getRequestHeader(),
                        Constant.ERROR_CODE_INVALID_PARAMS,
                        Constant.ERROR_MESSAGE_INVALID_PARAMS,
                        Constant.COMMON_SYSTEM_MDW, this.em);
            }

            if (null == payload.getLastName()
                    || ConstantADM.AW_USER_LAST_NAMES_MAX_LENGTH < payload
                            .getLastName().length()) {

                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        Constant.ERROR_CODE_INVALID_PARAMS, null);

                return this.sch.responseErrorMessage(
                        request.getRequestMessage().getRequestHeader(),
                        Constant.ERROR_CODE_INVALID_PARAMS,
                        Constant.ERROR_MESSAGE_INVALID_PARAMS,
                        Constant.COMMON_SYSTEM_MDW, this.em);
            }

            if (null == payload.getRole() || payload.getRole().isEmpty()) {

                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        Constant.ERROR_CODE_INVALID_PARAMS, null);

                return this.sch.responseErrorMessage(
                        request.getRequestMessage().getRequestHeader(),
                        Constant.ERROR_CODE_INVALID_PARAMS,
                        Constant.ERROR_MESSAGE_INVALID_PARAMS,
                        Constant.COMMON_SYSTEM_MDW, this.em);
            }

            newUserEmail = payload.getMail();

            List<Parametro> adminWebLdapParameters = this.parameterJPAService
                    .getRegionParameter(Constant.ADMIN_WEB_LDAP_PARAMETERS,
                            request.getRequestMessage().getRequestHeader()
                                    .getDestination().getServiceRegion(),
                            this.em);

            if (null == adminWebLdapParameters
                    || adminWebLdapParameters.isEmpty()) {
                logger.error(ErrorEnum.DB_ERROR,
                        Constant.ERROR_MESSAGE_DB_PARAMETER_NOT_FOUND, null);
                return this.sch.responseErrorMessage(
                        request.getRequestMessage().getRequestHeader(),
                        Constant.ERROR_CODE_SYSTEM_BD_PARAMETER_NOT_FOUND,
                        Constant.ERROR_MESSAGE_DB_PARAMETER_NOT_FOUND,
                        Constant.COMMON_SYSTEM_BD, Constant.COMMON_SYSTEM_WEB,
                        this.em);
            }

            DivisionGeografica divisionGeografica = this.divisionGeograficaJPAService
                    .findDivisionGeografica(
                            request.getRequestMessage().getRequestHeader()
                                    .getDestination().getServiceRegion(),
                            this.em);

            if (null == divisionGeografica) {
                logger.error(ErrorEnum.DB_ERROR,
                        Constant.ERROR_MESSAGE_DB_PARAMETER_NOT_FOUND, null);
                return this.sch.responseErrorMessage(
                        request.getRequestMessage().getRequestHeader(),
                        Constant.ERROR_CODE_SYSTEM_BD_PARAMETER_NOT_FOUND,
                        Constant.ERROR_MESSAGE_DB_PARAMETER_NOT_FOUND,
                        Constant.COMMON_SYSTEM_ADMIN_WEB,
                        Constant.COMMON_SYSTEM_WEB, this.em);
            }

            AwRol role = this.awRolJPAService
                    .findAwRolById(Long.parseLong(payload.getRole()), this.em);

            if (null == role) {
                logger.error(ErrorEnum.DB_ERROR,
                        Constant.ERROR_MESSAGE_ADMIN_WEB_ROLE_DOESNT_EXIST,
                        null);
                return this.sch.responseErrorMessage(
                        request.getRequestMessage().getRequestHeader(),
                        Constant.ERROR_CODE_CLIENT_BLOCKED_PANIC,
                        Constant.ERROR_MESSAGE_ADMIN_WEB_ROLE_DOESNT_EXIST,
                        Constant.COMMON_SYSTEM_ADMIN_WEB,
                        Constant.COMMON_SYSTEM_WEB, this.em);
            }

            AwUsuario currentUser = this.awUserJPAService
                    .getUserByEmail(payload.getCurrentUser(), this.em);

            if (null == currentUser) {
                logger.error(ErrorEnum.DB_ERROR,
                        Constant.ERROR_MESSAGE_ADMIN_WEB_USER_DOESNT_EXIST,
                        null);
                return this.sch.responseErrorMessage(
                        request.getRequestMessage().getRequestHeader(),
                        Constant.ERROR_CODE_USER_PWD_WRONG,
                        Constant.ERROR_MESSAGE_ADMIN_WEB_USER_DOESNT_EXIST,
                        Constant.COMMON_SYSTEM_ADMIN_WEB,
                        Constant.COMMON_SYSTEM_WEB, this.em);
            }

            if (ConstantADM.ADMINISTRATOR_ROLE_ID == role.getIdRol()) {
                ldapRole = this.sch.getParameterByName(adminWebLdapParameters,
                        ConstantADM.ADMINISTRATOR_ROLE_PARAMETER);
            } else {
                ldapRole = this.sch.getParameterByName(adminWebLdapParameters,
                        ConstantADM.OPERATOR_ROLE_PARAMETER);
            }

            if (null == ldapRole) {
                logger.error(ErrorEnum.DB_ERROR,
                        Constant.ERROR_MESSAGE_DB_PARAMETER_NOT_FOUND, null);
                return this.sch.responseErrorMessage(
                        request.getRequestMessage().getRequestHeader(),
                        Constant.ERROR_CODE_SYSTEM_BD_PARAMETER_NOT_FOUND,
                        Constant.ERROR_MESSAGE_DB_PARAMETER_NOT_FOUND,
                        Constant.COMMON_SYSTEM_ADMIN_WEB,
                        Constant.COMMON_SYSTEM_WEB, this.em);
            }

            // Se valida que el correo no exista para otro usuario.
            AwUsuario newUser = this.awUserJPAService
                    .getUserByEmail(newUserEmail, this.em);

            if (null != newUser) {
                logger.error(ErrorEnum.DB_ERROR,
                        Constant.ERROR_MESSAGE_ADMIN_WEB_USER_EMAIL_ALREADY_EXIST,
                        null);
                return this.sch.responseErrorMessage(
                        request.getRequestMessage().getRequestHeader(),
                        Constant.CLIENT_BLOCKED_MAXIMUM_ATTEMPTS_ERROR_CODE,
                        Constant.ERROR_MESSAGE_ADMIN_WEB_USER_EMAIL_ALREADY_EXIST,
                        Constant.COMMON_SYSTEM_ADMIN_WEB,
                        Constant.COMMON_SYSTEM_WEB, this.em);
            }

            // Llamado al servicio de broker para crear al usuario

            responseFront = this.createUserLdap(request, payloadRequest,
                    createUserResponse, ldapRole.getValor());

            if (!this.sch.successResponse(responseFront)) {
                logger.error(ErrorEnum.BROKER_ERROR,
                        WebConsoleUtil.generateString(
                                responseFront.getResponseMessage()
                                        .getResponseHeader().getStatus()
                                        .getStatusCode(),
                                Constant.COMMON_STRING_BLANK_SPACE,
                                Constant.GUION_MEDIO,
                                Constant.COMMON_STRING_BLANK_SPACE,
                                responseFront.getResponseMessage()
                                        .getResponseHeader().getStatus()
                                        .getStatusDesc()),
                        null);
                return responseFront;
            }

            AwUsuario awUser = this.buildAwUser(payload, divisionGeografica,
                    role);

            this.awUserJPAService.createUser(awUser, currentUser.getMail(),
                    this.em);

            responseFront = new ResponseMessageObjectType();
            responseStatusType.setStatusCode(Constant.COMMON_STRING_ZERO);
            responseStatusType
                    .setStatusDesc(Constant.COMMON_STRING_SUCCESS_MAYUS);
            responseStatusType.setSystem(Constant.COMMON_SYSTEM_MDW);

            // HEADER
            ResponseMessageType responseMessageType = new ResponseMessageType();
            ResponseHeader responseHeader = this.sch
                    .configureResponseFrontHeader(
                            request.getRequestMessage().getRequestHeader(),
                            responseStatusType, this.em);
            responseMessageType.setResponseHeader(responseHeader);
            // BODY
            ResponseBody responseBody = new ResponseBody();
            responseBody.setAny(createUserResponse);
            responseMessageType.setResponseBody(responseBody);
            responseFront.setResponseMessage(responseMessageType);

            // Retorna respuesta a FRONT.
            return this.sch.parseResponseAnyToMapJson(responseFront, request,
                    this.em);

        } catch (MiddlewareException e1) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    Constant.ERROR_MESSAGE_PARSING_ERROR_IN, e1);
            return this.sch.responseErrorMessage(
                    request.getRequestMessage().getRequestHeader(),
                    Constant.ERROR_CODE_PARSE_PAYLOAD,
                    Constant.ERROR_MESSAGE_PARSING_ERROR_IN,
                    Constant.COMMON_SYSTEM_WEB, this.em);

        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY,
                    e);
            return this.sch.responseErrorMessage(
                    request.getRequestMessage().getRequestHeader(),
                    Constant.ERROR_CODE_COMMUNICATION_BD,
                    Constant.ERROR_MESSAGE_DB_QUERY, Constant.COMMON_SYSTEM_MDW,
                    this.em);

        }

    }

    /**
     * @param payloadRequest
     * @param newUserEmail
     * @return CreateUserLDAPServiceRequestType
     */
    private CreateUserLDAPServiceRequestType buildCreateUserLdapRequest(
            CreateUserServiceRequestType payloadRequest, String newUserEmail) {
        CreateUserLDAPServiceRequestType createUserLdapRequest = new CreateUserLDAPServiceRequestType();
        CreateUserLDAPRQType createUserLdapRequestBody = new CreateUserLDAPRQType();
        createUserLdapRequest.setCreateUserLDAPRQ(createUserLdapRequestBody);

        createUserLdapRequestBody.setCifId(newUserEmail);
        createUserLdapRequestBody.setPassword(newUserEmail);
        createUserLdapRequestBody.setEmail(newUserEmail);
        createUserLdapRequestBody.setPhoneNumber(
                null != payloadRequest.getCreateUserRQ().getCellphone()
                        ? payloadRequest.getCreateUserRQ().getCellphone()
                        : null);
        return createUserLdapRequest;
    }

    /**
     * @param payload
     * @param divisionGeografica
     * @param role
     * @return AwUsuario usuario con los datos a crear.
     */
    private AwUsuario buildAwUser(CreateUserRQType payload,
            DivisionGeografica divisionGeografica, AwRol role) {
        AwUsuario awUser = new AwUsuario();
        awUser.setMail(payload.getMail());
        awUser.setApellidos(
                null != payload.getLastName() ? payload.getLastName() : null);
        awUser.setAwRol(role);
        awUser.setCelular(new BigDecimal(payload.getCellphone()));
        awUser.setEstado(Boolean.FALSE);
        awUser.setNombres(
                null != payload.getNames() ? payload.getNames() : null);
        awUser.setDivisionGeografica(divisionGeografica);
        awUser.setMustChangePwd(Boolean.TRUE);
        return awUser;
    }

    /**
     * Metodo que modifica la informacion de ultimo login del usuario.
     * 
     * @param request
     * @param payloadRequest
     * @param createUserResponse
     * @param ldapRole
     * @return ResponseMessageObjectType
     */
    private ResponseMessageObjectType createUserLdap(
            RequestMessageObjectType request,
            CreateUserServiceRequestType payloadRequest,
            CreateUserServiceResponseType createUserResponse, String ldapRole) {

        try {
            Integer connectionTimeOut;
            Integer readTimeout;
            ResponseMessageObjectType responseFront = new ResponseMessageObjectType();

            ServiceRegistry serviceRegitryUtil = ServiceRegistry.getInstance();

            co.nequi.message.registry.serviceregistry.RegistryRQ registryRQ = WebConsoleUtil
                    .getRegistryRQ(
                            request.getRequestMessage().getRequestHeader()
                                    .getMessageID(),
                            request.getRequestMessage().getRequestHeader()
                                    .getRequestDate(),
                            ConstantADM.COMMON_STRING_LDAP_401_SERVICE_NAME,
                            ConstantADM.COMMON_STRING_CREATE_USER_LDAP_401_SERVICE_OPERATION,
                            ConstantADM.COMMON_STRING_CREATE_USER_LDAP_401_SERVICE_REGION,
                            ConstantADM.COMMON_STRING_CREATE_USER_LDAP_401_SERVICE_VERSION);

            co.nequi.message.registry.serviceregistry.lookup.LookupRequestType serviceInfo = registryRQ
                    .getRegistryRequest().getBody().getLookupRequest();

            String registryResponse = serviceRegitryUtil.lookUpRegistryService(
                    registryRQ, RegistryRS.class, serviceInfo.getName(),
                    serviceInfo.getOperation(), serviceInfo.getClassification(),
                    serviceInfo.getRegion(), serviceInfo.getVersion());

            RegistryRS registryRS = UtilJSON.parsePayload(registryResponse,
                    RegistryRS.class);

            co.nequi.message.registry.serviceregistry.HeaderResponseType registryRsHeaders = registryRS
                    .getRegistryResponse().getHeader();

            if (!Constant.COMMON_STRING_ZERO
                    .equals(registryRsHeaders.getStatus().getCode())) {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        registryRsHeaders.getStatus().getDescription(),
                        new MiddlewareException(registryRsHeaders.getStatus()
                                .getDescription()));
                return this.sch.configureResponseFrontMessageType(
                        registryRsHeaders.getStatus().getCode(),
                        registryRsHeaders.getStatus().getDescription(),
                        Constant.COMMON_SYSTEM_MDW, request, this.em);
            }

            LookupResponseType lookupResponse = registryRS.getRegistryResponse()
                    .getBody().getLookupResponse();

            CreateUserLDAPServiceRequestType createUserLdapRequest = buildCreateUserLdapRequest(
                    payloadRequest, payloadRequest.getCreateUserRQ().getMail());

            co.bdigital.admin.messaging.integration.ldap401.v100.RequestMessageObjectType integrationRq = WebConsoleUtil
                    .getLdap401RequestMessage(
                            request.getRequestMessage().getRequestHeader()
                                    .getMessageID(),
                            ConstantADM.REST_SERVICE_CHANEL,
                            serviceInfo.getName(), serviceInfo.getOperation(),
                            request.getRequestMessage().getRequestHeader()
                                    .getDestination().getServiceRegion(),
                            serviceInfo.getVersion());

            co.bdigital.admin.messaging.integration.ldap401.v100.MessageContextType messageContext = new co.bdigital.admin.messaging.integration.ldap401.v100.MessageContextType();

            if (null != ldapRole && !ldapRole.trim().isEmpty()) {

                co.bdigital.admin.messaging.integration.ldap401.v100.PropertyType propertyTypeRQ = new co.bdigital.admin.messaging.integration.ldap401.v100.PropertyType();

                propertyTypeRQ
                        .setKey(Constant.COMMON_STRING_ADMIN_WEB_LDAP_KEY);
                propertyTypeRQ.setValue(ldapRole);

                messageContext.setProperty(propertyTypeRQ);

            }

            integrationRq.getIntegrationRequest().getHeader()
                    .setMessageContext(messageContext);

            integrationRq.getIntegrationRequest().getBody()
                    .setCreateUserLDAPServiceRequest(createUserLdapRequest);

            String endpoint = GenericRestClient.getUrlRestServicesEndPoint(
                    lookupResponse.getEndpoint().getProtocol(),
                    lookupResponse.getEndpoint().getHost(),
                    lookupResponse.getEndpoint().getPort(),
                    lookupResponse.getEndpoint().getContext());

            connectionTimeOut = Integer
                    .parseInt(lookupResponse.getTimeout().getConnection());

            readTimeout = Integer
                    .parseInt(lookupResponse.getTimeout().getRead());

            logger.info(ConstantADM.PAYLOAD_RQ
                    + UtilJSON.parseObjectToString(integrationRq));

            co.bdigital.admin.messaging.integration.ldap401.v100.ResponseMessageObjectType integrationRs = callService(
                    integrationRq, endpoint, connectionTimeOut, readTimeout,
                    lookupResponse.getEndpoint().getAuthBasicUser(),
                    lookupResponse.getEndpoint().getAuthBasicPwd(),
                    co.bdigital.admin.messaging.integration.ldap401.v100.ResponseMessageObjectType.class);

            logger.info(ConstantADM.PAYLOAD_RS
                    + UtilJSON.parseObjectToString(integrationRs));

            ResponseStatusType responseStatusType = new ResponseStatusType();
            responseStatusType
                    .setStatusCode(integrationRs.getIntegrationResponse()
                            .getHeader().getStatus().getCode());
            responseStatusType.setSystem(integrationRs.getIntegrationResponse()
                    .getHeader().getStatus().getSystem());
            responseStatusType
                    .setStatusDesc(integrationRs.getIntegrationResponse()
                            .getHeader().getStatus().getDescription());

            // HEADER
            ResponseMessageType responseMessageType = new ResponseMessageType();
            ResponseHeader responseHeader = this.sch
                    .configureResponseFrontHeader(
                            request.getRequestMessage().getRequestHeader(),
                            responseStatusType, this.em);
            responseMessageType.setResponseHeader(responseHeader);

            // BODY
            ResponseBody responseBody = new ResponseBody();
            responseBody.setAny(createUserResponse);
            responseMessageType.setResponseBody(responseBody);

            // ************************************************************************************
            responseFront.setResponseMessage(responseMessageType);
            // Retornamos el Objeto de Respuesta

            return responseFront;
        } catch (com.nequi.cmm.consumer.exception.MiddlewareException
                | com.nequi.cmm.consumer.exception.RestClientException e) {
            logger.error(ErrorEnum.LDAP_ERROR,
                    Constant.ERROR_MESSAGE_LDAP_COMMUNICATION, e);

            return this.sch.responseErrorMessage(
                    request.getRequestMessage().getRequestHeader(),
                    Constant.ERROR_CODE_COMMUNICATION_LDAP,
                    Constant.ERROR_MESSAGE_LDAP_COMMUNICATION,
                    Constant.COMMON_SYSTEM_MDW, this.em);
        } catch (CommonUtilException | IOException e) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    Constant.ERROR_MESSAGE_PARSING_ERROR_IN, e);
            return this.sch.responseErrorMessage(
                    request.getRequestMessage().getRequestHeader(),
                    Constant.ERROR_CODE_PARSE_PAYLOAD,
                    Constant.ERROR_MESSAGE_PARSING_ERROR_IN,
                    Constant.COMMON_SYSTEM_MDW, this.em);
        }
    }
}
