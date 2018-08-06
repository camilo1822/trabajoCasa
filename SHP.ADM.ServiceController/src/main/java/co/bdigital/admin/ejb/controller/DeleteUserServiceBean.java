package co.bdigital.admin.ejb.controller;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import co.bdigital.admin.messaging.services.deleteuser.DeleteUserRSType;
import co.bdigital.admin.messaging.services.deleteuser.DeleteUserServiceRequestType;
import co.bdigital.admin.messaging.services.deleteuser.DeleteUserServiceResponseType;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.cmm.ejb.exception.MiddlewareException;
import co.bdigital.cmm.ejb.generic.ServiceController;
import co.bdigital.cmm.ejb.generic.ServiceControllerHelper;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AwUsuario;
import co.bdigital.cmm.jpa.service.impl.AWUserJPAServiceIMPL;
import co.bdigital.cmm.jpa.services.AWUserJPAService;
import co.bdigital.cmm.messaging.esb.ResponseStatusType;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseBody;
import co.bdigital.cmm.messaging.general.ResponseHeader;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageType;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * Session Bean implementation class DeleteUserServiceBean
 * 
 * @author hansel.ospino
 * @since 28/11/2016
 * @version 1.0
 */
@Stateless(mappedName = "DeleteUserServiceBean")
@LocalBean
public class DeleteUserServiceBean implements ServiceController {

    private ServiceControllerHelper sch;

    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;

    private static CustomLogger logger;
    @Context
    private HttpServletRequest requestHttp;

    private AWUserJPAService awUserJPAService;

    /**
     * Metodo para inicializar recursos del EJB.
     */
    @PostConstruct
    void init() {
        logger = new CustomLogger(DeleteUserServiceBean.class);
        this.sch = ServiceControllerHelper.getInstance();
        this.awUserJPAService = new AWUserJPAServiceIMPL();
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
        DeleteUserServiceRequestType payloadRequest;

        if (!this.sch.validateRequest(request.getRequestMessage()
                .getRequestBody().getAny(), new DeleteUserServiceRequestType(),
                ConstantADM.SERVICE_OPERATION_DELETE_USER)) {

            return ServiceControllerHelper.getInstance().responseErrorMessage(
                    request.getRequestMessage().getRequestHeader(),
                    Constant.ERROR_CODE_PARSE_PAYLOAD,
                    Constant.ERROR_MESSAGE_PARSING_ERROR_IN,
                    Constant.COMMON_SYSTEM_MDW, this.em);

        }

        try {
            payloadRequest = (DeleteUserServiceRequestType) this.sch
                    .parsePayload(request.getRequestMessage().getRequestBody()
                            .getAny(), new DeleteUserServiceRequestType());

            AwUsuario currentUser = this.awUserJPAService.getUserByEmail(
                    payloadRequest.getDeleteUserRQ().getCurrentUser(), this.em);

            if (null == currentUser) {
                logger.error(ErrorEnum.DB_ERROR,
                        Constant.ERROR_MESSAGE_ADMIN_WEB_USER_DOESNT_EXIST,
                        null);
                return this.sch.responseErrorMessage(request
                        .getRequestMessage().getRequestHeader(),
                        Constant.ERROR_CODE_USER_PWD_WRONG,
                        Constant.ERROR_MESSAGE_ADMIN_WEB_USER_DOESNT_EXIST,
                        Constant.COMMON_SYSTEM_ADMIN_WEB,
                        Constant.COMMON_SYSTEM_WEB, this.em);
            }

            AwUsuario userToDelete = this.awUserJPAService.getUserByEmail(
                    payloadRequest.getDeleteUserRQ().getMail(), this.em);

            if (null == userToDelete) {
                logger.error(ErrorEnum.DB_ERROR,
                        Constant.ERROR_MESSAGE_ADMIN_WEB_USER_DOESNT_EXIST,
                        null);
                return this.sch.responseErrorMessage(request
                        .getRequestMessage().getRequestHeader(),
                        Constant.ERROR_CODE_USER_PWD_WRONG,
                        Constant.ERROR_MESSAGE_ADMIN_WEB_USER_DOESNT_EXIST,
                        Constant.COMMON_SYSTEM_ADMIN_WEB,
                        Constant.COMMON_SYSTEM_WEB, this.em);
            }

            userToDelete = this.buildDeleteUser(payloadRequest, userToDelete);
            this.awUserJPAService.mergeUser(userToDelete, this.em);

            DeleteUserRSType deleteUserRS = new DeleteUserRSType();
            DeleteUserServiceResponseType deleteUserResponse = new DeleteUserServiceResponseType();
            deleteUserResponse.setDeleteUserRS(deleteUserRS);

            responseFront = new ResponseMessageObjectType();
            responseStatusType.setStatusCode(Constant.COMMON_STRING_ZERO);
            responseStatusType
                    .setStatusDesc(Constant.COMMON_STRING_SUCCESS_MAYUS);
            responseStatusType.setSystem(Constant.COMMON_SYSTEM_MDW);

            // HEADER
            ResponseMessageType responseMessageType = new ResponseMessageType();
            ResponseHeader responseHeader = this.sch
                    .configureResponseFrontHeader(request.getRequestMessage()
                            .getRequestHeader(), responseStatusType, this.em);
            responseMessageType.setResponseHeader(responseHeader);
            // BODY
            ResponseBody responseBody = new ResponseBody();
            responseBody.setAny(deleteUserResponse);
            responseMessageType.setResponseBody(responseBody);
            responseFront.setResponseMessage(responseMessageType);

            // Retorna respuesta a FRONT.
            return this.sch.parseResponseAnyToMapJson(responseFront, request,
                    this.em);

        } catch (MiddlewareException e1) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    Constant.ERROR_MESSAGE_PARSING_ERROR_IN, e1);
            return this.sch.responseErrorMessage(request.getRequestMessage()
                    .getRequestHeader(), Constant.ERROR_CODE_PARSE_PAYLOAD,
                    Constant.ERROR_MESSAGE_PARSING_ERROR_IN,
                    Constant.COMMON_SYSTEM_WEB, this.em);

        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY, e);
            return this.sch.responseErrorMessage(request.getRequestMessage()
                    .getRequestHeader(), Constant.ERROR_CODE_COMMUNICATION_BD,
                    Constant.ERROR_MESSAGE_DB_QUERY,
                    Constant.COMMON_SYSTEM_MDW, this.em);

        }

    }

    /**
     * @param payloadRequest
     * @param userToDelete
     */
    private AwUsuario buildDeleteUser(
            DeleteUserServiceRequestType payloadRequest, AwUsuario userToDelete) {
        userToDelete.setEstado(Boolean.TRUE);
        userToDelete.setUsuarioModificacion(payloadRequest.getDeleteUserRQ()
                .getCurrentUser());
        userToDelete.setFechaModificacion(new Date());
        return userToDelete;
    }
}
