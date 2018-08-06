package co.bdigital.admin.services.usermanagementservices;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.bdigital.admin.ejb.controller.ChangePasswordServiceBean;
import co.bdigital.admin.ejb.controller.CreateUserServiceBean;
import co.bdigital.admin.ejb.controller.DeleteUserServiceBean;
import co.bdigital.admin.ejb.controller.UpdateUserServiceBean;
import co.bdigital.admin.services.processor.impl.UserManagementServicesProcessor;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.cmm.ejb.generic.ServiceController;
import co.bdigital.cmm.ejb.util.MessageTracerUtil;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;
import co.bdigital.shl.tracer.CustomLogger;

/**
 * La clase {@link UserManagementServices} es para proveer servicios de gestion
 * de usuarios por REST.
 * 
 * <p>
 * La clase UserManagementServices ofrece la publicacion de los metodos
 * <strong>createUser</strong>, <strong>updateUser</strong> y
 * <strong>deleteUser</strong> mediante REST para ser consumido por Front-End.
 * </p>
 * 
 * @author hansel.ospino@pragma.com.co
 * @since 24/11/2016
 * @version 1.0
 */

@javax.ws.rs.Path("/services/UserManagementServices")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserManagementServices {

    private static final String SERVICE_NAME = "UserManagementServices";
    private CustomLogger logger;
    private UserManagementServicesProcessor processor;

    @EJB
    private CreateUserServiceBean createUserServiceBean;

    @EJB
    private DeleteUserServiceBean deleteUserServiceBean;

    @EJB
    private UpdateUserServiceBean updateUserServiceBean;

    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;

    /**
     * Metodo para inicializar recursos del EJB.
     */
    @PostConstruct
    void init() {

        logger = new CustomLogger(UserManagementServices.class);
        this.processor = new UserManagementServicesProcessor();
    }

    /**
     * @author cristian.martinez@pragma.com.co
     * @param request
     * @param serviceBean
     *            [controlador para usar en el llamado del servicio]
     * @return Respuesta del servicio consumido
     */
    /**
     * @author hansel.ospino
     * @param request
     * @param serviceBean
     *            [controllador para usar en el llamado del servicio]
     * @return Respuesta del servicio consumido
     */
    private ResponseMessageObjectType callService(
            RequestMessageObjectType request, ServiceController serviceBean,
            String operacion) {

        logger.debug("MessageReceived at:" + new Date().getTime());
        processor.setHeaderOperation(
                request.getRequestMessage().getRequestHeader(), SERVICE_NAME,
                operacion);

        ResponseMessageObjectType response = null;

        // Traza el Header de Entrada
        MessageTracerUtil.traceRequestMessage(request, logger);

        response = processor.processOperation(request, serviceBean);

        MessageTracerUtil.traceResponseMessage(response, logger);
        return response;

    }

    /**
     * Metodo que expone la operacion ConstantADM.SERVICE_OPERATION_CREATE_USER
     * 
     * Procedimiento que crea un usuario de la web administrativa
     * 
     * @path "/" + {@link ConstantADM.SERVICE_OPERATION_CREATE_USER}
     * @param request
     * @return ResponseMessageObjectType
     */
    @Path("/" + ConstantADM.SERVICE_OPERATION_CREATE_USER)
    @javax.ws.rs.POST
    public ResponseMessageObjectType createUser(
            RequestMessageObjectType request) {
        return callService(request, createUserServiceBean,
                ConstantADM.SERVICE_OPERATION_CREATE_USER);
    }

    @EJB
    private ChangePasswordServiceBean changePasswordServiceBean;

    /**
     * Metodo que expone la operacion para el cambio de contraseña
     * 
     * 
     * @path "/" + {@link ConstantADM.SERVICE_OPERATION_CHANGE_PASSWORD}
     * @param request
     * @return
     */
    @Path("/" + ConstantADM.SERVICE_OPERATION_CHANGE_PASS)
    @javax.ws.rs.POST
    public ResponseMessageObjectType changePassword(
            RequestMessageObjectType request) {
        return callService(request, changePasswordServiceBean,
                ConstantADM.SERVICE_OPERATION_CHANGE_PASS);
    }

    /**
     * Metodo que expone la operacion ConstantADM.SERVICE_OPERATION_DELETE_USER
     * 
     * procedimiento que elimina logicamente un usuario de la web
     * administrativa.
     * 
     * @path "/" + {@link ConstantADM.SERVICE_OPERATION_DELETE_USER}
     * @param request
     * @return ResponseMessageObjectType
     */
    @Path("/" + ConstantADM.SERVICE_OPERATION_DELETE_USER)
    @javax.ws.rs.POST
    public ResponseMessageObjectType deleteUser(
            RequestMessageObjectType request) {
        return callService(request, deleteUserServiceBean,
                ConstantADM.SERVICE_OPERATION_DELETE_USER);
    }

    /**
     * Metodo que expone la operacion ConstantADM.SERVICE_OPERATION_UPDATE_USER
     * 
     * procedimiento que actualiza un usuario de la web administrativa.
     * 
     * @path "/" + {@link ConstantADM.SERVICE_OPERATION_UPDATE_USER}
     * @param request
     * @return ResponseMessageObjectType
     */
    @Path("/" + ConstantADM.SERVICE_OPERATION_UPDATE_USER)
    @javax.ws.rs.POST
    public ResponseMessageObjectType updateUser(
            RequestMessageObjectType request) {
        return callService(request, updateUserServiceBean,
                ConstantADM.SERVICE_OPERATION_UPDATE_USER);
    }
}
