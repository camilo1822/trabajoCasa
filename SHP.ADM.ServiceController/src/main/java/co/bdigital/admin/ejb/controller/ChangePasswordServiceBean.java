package co.bdigital.admin.ejb.controller;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.bdigital.admin.messaging.services.changepassword.ChangePasswordRSType;
import co.bdigital.admin.messaging.services.changepassword.ChangePasswordServiceRequestType;
import co.bdigital.admin.messaging.services.changepassword.ChangePasswordServiceResponseType;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.cmm.ejb.exception.MiddlewareException;
import co.bdigital.cmm.ejb.generic.ServiceController;
import co.bdigital.cmm.ejb.generic.ServiceControllerHelper;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.ejb.util.ContextUtilHelper;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AwUsuario;
import co.bdigital.cmm.jpa.service.impl.AWUserJPAServiceIMPL;
import co.bdigital.cmm.jpa.services.AWUserJPAService;
import co.bdigital.cmm.ldap.exception.LDAPException;
import co.bdigital.cmm.ldap.service.impl.LDAPServiceImpl;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;
import co.bdigital.shl.common.service.bean.view.ResourceLocator;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * 
 * @author eduardo.altamar
 *
 */
@Stateless(mappedName = "ChangePasswordServiceBean")
@LocalBean
public class ChangePasswordServiceBean implements ServiceController {

    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;
    private ServiceControllerHelper sch;
    private CustomLogger logger;
    private AWUserJPAService awUserJPAService;

    @PostConstruct
    void init() {
        logger = new CustomLogger(ChangePasswordServiceBean.class);
        this.sch = ServiceControllerHelper.getInstance();
        this.awUserJPAService = new AWUserJPAServiceIMPL();

    }

    @Override
    public ResponseMessageObjectType executeOperation(
            RequestMessageObjectType request) {

        if (!this.sch.validateRequest(request.getRequestMessage()
                .getRequestBody().getAny(),
                new ChangePasswordServiceRequestType(),
                ConstantADM.SERVICE_OPERATION_CHANGE_PASS)) {

            return this.sch.responseErrorMessage(request.getRequestMessage()
                    .getRequestHeader(), Constant.ERROR_CODE_PARSE_PAYLOAD,
                    Constant.ERROR_MESSAGE_PARSING_ERROR_IN,
                    Constant.COMMON_SYSTEM_MDW, this.em);

        }

        ChangePasswordServiceRequestType requestAnyType = null;

        try {

            requestAnyType = (ChangePasswordServiceRequestType) this.sch
                    .parsePayload(request.getRequestMessage().getRequestBody()
                            .getAny(), new ChangePasswordServiceRequestType());

            AwUsuario currentUser = this.awUserJPAService.getUserByEmail(
                    requestAnyType.getChangePasswordRQ().getEmail(), this.em);

            if (null == currentUser) {
                
                logger.error(ErrorEnum.DB_ERROR,
                        Constant.ERROR_MESSAGE_DB_QUERY_NO_RESULTS, null);
                return this.sch.responseErrorMessage(request
                        .getRequestMessage().getRequestHeader(),
                        Constant.ERROR_CODE_NO_RESULTS_BD,
                        Constant.ERROR_MESSAGE_DB_QUERY,
                        Constant.COMMON_SYSTEM_MDW, this.em);

            }

            ContextUtilHelper contextUtilHelper = ContextUtilHelper
                    .getInstance();
            ResourceLocator resourceLocator = contextUtilHelper
                    .instanceResourceLocatorRemote();

            LDAPServiceImpl serviceLDAP = new LDAPServiceImpl(
                    resourceLocator.getResourcesPojoLdapAdminWeb());

            // Se cambia el pass al usuario
            serviceLDAP.changePass(requestAnyType.getChangePasswordRQ()
                    .getEmail(), requestAnyType.getChangePasswordRQ()
                    .getCurrentPassword(), requestAnyType.getChangePasswordRQ()
                    .getNewPassword());

            if (currentUser.getMustChangePwd()) {
                // se cambia el flag de cambio de contrasena
                currentUser.setMustChangePwd(!currentUser.getMustChangePwd());
                this.awUserJPAService.mergeUser(currentUser, this.em);
            }

        } catch (LDAPException e) {
            logger.error(ErrorEnum.LDAP_ERROR,
                    Constant.ERROR_MESSAGE_LDAP_CHANGE_PWD, null);
            return this.sch.responseErrorMessage(request.getRequestMessage()
                    .getRequestHeader(), Constant.ERROR_CHANGE_PASSWORD_LDAP,
                    Constant.ERROR_MESSAGE_LDAP_CHANGE_PWD,
                    Constant.COMMON_SYSTEM_LDAP, this.em);

        } catch (MiddlewareException e) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    Constant.ERROR_MESSAGE_SSHA, e);
            return this.sch.responseErrorMessage(request.getRequestMessage()
                    .getRequestHeader(), Constant.ERROR_CODE_HISTORIC_PASS,
                    Constant.COMMON_STRING_EMPTY_STRING,
                    Constant.COMMON_SYSTEM_MDW, this.em);
        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR, Constant.ERROR_MESSAGE_DB_QUERY, e);
            return this.sch.responseErrorMessage(request.getRequestMessage()
                    .getRequestHeader(), Constant.ERROR_CODE_COMMUNICATION_BD,
                    Constant.ERROR_MESSAGE_DB_QUERY,
                    Constant.COMMON_SYSTEM_MDW, this.em);

        }

        // Retorna respuesta SUCCESS a FRONT.
        ResponseMessageObjectType responseMessageObjectType = this.sch
                .configureResponseFrontMessageType(Constant.COMMON_STRING_ZERO,
                        Constant.COMMON_STRING_SUCCESS_MAYUS,
                        Constant.COMMON_SYSTEM_MDW, request, this.em);

        ChangePasswordServiceResponseType changePasswordServiceResponseType = new ChangePasswordServiceResponseType();
        changePasswordServiceResponseType
                .setChangePasswordRS(new ChangePasswordRSType());

        responseMessageObjectType.getResponseMessage().getResponseBody()
                .setAny(changePasswordServiceResponseType);

        return this.sch.parseResponseAnyToMapJson(responseMessageObjectType,
                request, this.em);
    }
}
