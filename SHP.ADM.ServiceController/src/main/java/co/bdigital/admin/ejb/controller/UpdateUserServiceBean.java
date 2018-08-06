package co.bdigital.admin.ejb.controller;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import co.bdigital.admin.messaging.services.updateuser.UpdateUserRQType;
import co.bdigital.admin.messaging.services.updateuser.UpdateUserRSType;
import co.bdigital.admin.messaging.services.updateuser.UpdateUserServiceRequestType;
import co.bdigital.admin.messaging.services.updateuser.UpdateUserServiceResponseType;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.cmm.ejb.exception.MiddlewareException;
import co.bdigital.cmm.ejb.generic.ServiceController;
import co.bdigital.cmm.ejb.generic.ServiceControllerHelper;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AwRol;
import co.bdigital.cmm.jpa.model.AwUsuario;
import co.bdigital.cmm.jpa.model.DivisionGeografica;
import co.bdigital.cmm.jpa.service.impl.AWRolJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.AWUserJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.DivisionGeograficaJPAServiceIMPL;
import co.bdigital.cmm.jpa.services.AWRolJPAService;
import co.bdigital.cmm.jpa.services.AWUserJPAService;
import co.bdigital.cmm.jpa.services.DivisionGeograficaJPAService;
import co.bdigital.cmm.messaging.esb.ResponseStatusType;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseBody;
import co.bdigital.cmm.messaging.general.ResponseHeader;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageType;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * Session Bean implementation class UpdateUserServiceBean
 * 
 * @author hansel.ospino
 * @since 28/11/2016
 * @version 1.0
 */
@Stateless(mappedName = "UpdateUserServiceBean")
@LocalBean
public class UpdateUserServiceBean implements ServiceController {

    private ServiceControllerHelper sch;

    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;

    private CustomLogger logger;
    @Context
    private HttpServletRequest requestHttp;

    private DivisionGeograficaJPAService divisionGeograficaJPAService;
    private AWRolJPAService awRolJPAService;
    private AWUserJPAService awUserJPAService;

    /**
     * Metodo para inicializar recursos del EJB.
     */
    @PostConstruct
    void init() {
        logger = new CustomLogger(UpdateUserServiceBean.class);
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

        ResponseMessageObjectType responseFront = new ResponseMessageObjectType();
        ResponseStatusType responseStatusType = new ResponseStatusType();
        UpdateUserServiceRequestType payloadRequest;

        if (!this.sch.validateRequest(
                request.getRequestMessage().getRequestBody().getAny(),
                new UpdateUserServiceRequestType(),
                ConstantADM.SERVICE_OPERATION_UPDATE_USER)) {

            return this.sch.responseErrorMessage(
                    request.getRequestMessage().getRequestHeader(),
                    Constant.ERROR_CODE_PARSE_PAYLOAD,
                    Constant.ERROR_MESSAGE_PARSING_ERROR_IN,
                    Constant.COMMON_SYSTEM_MDW, this.em);

        }

        try {
            payloadRequest = (UpdateUserServiceRequestType) this.sch
                    .parsePayload(
                            request.getRequestMessage().getRequestBody()
                                    .getAny(),
                            new UpdateUserServiceRequestType());
            /*
             * Validacion de parametros
             */
            UpdateUserRQType payload = payloadRequest.getUpdateUserRQ();

            if (null == payload) {

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

            // Se obtiene el usuario actual que está ejecutando la operación.
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

            // Se obtiene el usuario a editar
            AwUsuario userToUpdate = this.awUserJPAService
                    .getUserByEmail(payload.getMail(), this.em);

            if (null == userToUpdate) {
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

            userToUpdate = this.buildAwUser(payload, userToUpdate, currentUser,
                    role);

            userToUpdate = this.awUserJPAService.mergeUser(userToUpdate,
                    this.em);

            UpdateUserRSType updateUserRS = new UpdateUserRSType();
            UpdateUserServiceResponseType updateUserResponse = new UpdateUserServiceResponseType();
            updateUserResponse.setUpdateUserRS(updateUserRS);

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
            responseBody.setAny(updateUserResponse);
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
     * 
     * @param payload
     * @param userToUpdate
     * @param currentUser
     * @param role
     * @return AwUsuario usuario con la informaci&oacute;n a actualizar.
     */
    private AwUsuario buildAwUser(UpdateUserRQType payload,
            AwUsuario userToUpdate, AwUsuario currentUser, AwRol role) {
        userToUpdate.setApellidos(null != payload.getLastName()
                ? payload.getLastName().trim() : null);
        userToUpdate.setAwRol(role);
        userToUpdate.setEstado(
                null != payload.getStatus() && Constant.COMMON_STRING_UNO
                        .equalsIgnoreCase(payload.getStatus()));
        userToUpdate.setNombres(
                null != payload.getNames() ? payload.getNames().trim() : null);
        userToUpdate.setUsuarioModificacion(currentUser.getMail());
        userToUpdate.setFechaModificacion(new Date());
        return userToUpdate;
    }
}
