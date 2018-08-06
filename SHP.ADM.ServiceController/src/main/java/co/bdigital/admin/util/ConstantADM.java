package co.bdigital.admin.util;

import java.math.BigDecimal;

/**
 * La clase ConstantADM Maneja las constantes que se van a buscar en los
 * archivos de propiedades
 * 
 * @author david.caicedo
 */

public class ConstantADM {

    // PARAMETROS GENERALES
    public static final String COMMON_STRING_SUCCESS_MAYUS = "SUCCESS";
    public static final String STATUS_CODE_DEFAULT_ERROR = "500";
    public static final String COMMON_SYSTEM_INTEGRATION = "IS_AMAZONS3_401";

    public static final String COMMON_STRING_REPLACE_KEY_FIRST = "{0}";
    public static final String COMMON_STRING_REPLACE_KEY_SECOND = "{1}";

    public static final String COMMON_STRING_TRUE = "true";
    public static final String COMMON_STRING_FALSE = "false";
    public static final String COMMON_STRING_DASH = "-";
    public static final String COMMON_STRING_DOT = ":";
    public static final String COMMON_STRING_SLASH = "/";
    public static final char COMMON_SUB_GUION = '_';
    public static final String COMMON_STRING_ZERO = "0";
    public static final byte INDEX_PROTOCOL_PROVIDER_TOKEN = 0;
    public static final byte INDEX_HOST_PROVIDER_TOKEN = 1;
    public static final String SERVICE_OPERATION_UNCHECK = "uncheckGMF";
    public static final String GMF_SERVICES = "FullEnrollmentService601";
    public static final String COMMON_STRING_CHANNEL_WEB = "WebAdmin";
    public static final String COMMON_STRING_REGION_COL = "C001";
    public static final String COMMON_STRING_RESPONSE = "Respuesta:";
    public static final char COMMON_CHAR_UNDERSCORE = '_';
    public static final String COMMON_STRING_CODE_CLOSE_ACCOUNT = "200";
    public static final String ERROR_USER_NOT_FOUND = "La cuenta ingresada no existe.";
    public static final String MESSAGE_SUCCESS_MARK = "La cuenta fue marcada exitosamente";
    public static final String MESSAGE_SUCCESS_UNMARK = "La cuenta fue desmarcada exitosamente";
    public static final String MESSAGE_TYPE_PROMOTION = "Tipo de promocion: ";

    // CONTANTES PARA EL LOGIN DEL USUARIO
    public static final String LOGIN_SERVLET_INICIO = " [SERVLET-LoginService][HttpServlet] - INICIO ";
    public static final String LOGIN_SERVLET_DO_POST = " doPost ";
    public static final String J_USER_NAME = "j_username";
    public static final String J_PASS = "j_password";
    public static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    public static final String IS_LOGIN = "isLogin";
    public static final String IS_CHANGE_PASS = "isChangePassword";
    public static final String LOGIN_SERVLET_ERROR_AUTHENTICATION = " Error de authentication WAS. ";
    public static final String LOGIN_SERVLET_ERROR_LOGOUT = " Error en el logout WAS";
    public static final String ERROR_REDIRECT_CREATE_USER = " Error al redireccionar  a la pagina creacion usuario ";
    public static final String ERROR_REDIRECT_HOME = " Error al redireccionar  a la pagina del home";
    public static final String URL_SERVLET_AUTHENTICATION = "/loginServiceAuthentication";
    public static final String URL_SERVLET_LOGOUT = "/logoutService";
    public static final String STRING_EMPTY = "";
    public static final String STRING_EMPTY_VALUE = " ";
    public static final String STRING_SPACE = " ";
    public static final String WELCOME_MESSAGE = "Bienvenido";
    public static final String LOGIN_ERROR = "Login Error";
    public static final String WRONG_USER = "Usuario Incorrecto";
    public static final String LOGGED_IN = "loggedIn";
    public static final String LOGGED_OUT = "loggetOut";
    public static final String URL = "ruta";
    public static final String URL_LOGIN = "login.xhtml";
    public static final String URL_CHANGE_PASS = "changePassword.xhtml";
    public static final String DISABLED_USER = "Usuario Inhabilitado";
    public static final String STRING_POINT = ".";
    public static final String STRING_SPLIT_POINT = "\\.";
    public static final String STRING_SPLIT_POINT_DATE = " ";
    public static final String NOT_FOUND_ROL_USER = "Rol del usuario sin privilegios.";
    public static final String COMMA = ",";
    public static final String COMMON_STRING_AND_TRANSLATED = "y";

    // CONTANTES PARA GUARDAR OBJETOS EN SESION
    public static final String USER_NAME_SESION = "userNameSesion";
    public static final String ACTIONS_BY_ROL_SESION = "actionsByRolSesion";
    public static final String COUNTRY_ID = "countryId";
    public static final String CUSTOMER_LIST_CONTROL = "customerListValidate";
    public static final String LIST_ATTEMPTS_SESION = "listAttemptsSesion";
    public static final String PATH_FILE_UPLOAD = "pathFileUpload";
    public static final String FIRST_CHANGE_PASS = "firstChangePass";
    public static final String SHOW_FIRST_BUTTON = "showFirstButton";
    public static final String SHOW_LAST_BUTTON = "showLastButton";
    public static final String NUMBER_PHONE_SESION = "numberPhone";
    public static final String URL_IMG_OLD_SESION = "urlImgOldSesion";
    public static final String URL_IMG_NEW_SESION = "urlImgNewSesion";
    public static final String FIRST_VAR_SESION = "firstVarSesion";
    public static final String MAP_PERMISSIONS_BY_ROL_SESION = "mapPermissionsByRolSesion";
    public static final String PERMISSIONS_BY_ROL_SESION = "permissionsByRolSesion";

    // CONTANTES PARA LAS EXCEPCIONES
    public static final String TIME_UNIT_SLEEP = "Time Unit Sleep";
    public static final String ERROR_GET_LIST_USER = "Error al consultar los usuarios";
    public static final String ERROR_GET_LIST_COMERCE = "Error al consultar los comercios";
    public static final String ERROR_GET_LIST_USERS = "Error al consultar los usuarios";
    public static final String ERROR_GET_LIST_BLOCK = "Error al consultar los bloqueos";
    public static final String ERROR_GET_LIST_ROL = "Error al consultar los roles";
    public static final String ERROR_GET_USER_BY_EMAIL = "Error al consultar el usuario por email";
    public static final String ERROR_SAVE_AUDIT = "Error al guardar auditoria";
    public static final String CHOOSE_FILE_UPLOAD = "Elegir y subir archivo antes de realizar esta acción";
    public static final String FILE_NOT_FOUNT_UPLOAD = "Archivo no encontrado para la lectura de datos en listas de control";
    public static final String ERROR_READ_FILE_UPLOAD = "Error en la lectura del archivo en listas de control";
    public static final String ERROR_VALIDATE_LIST_CONTROL = "Error al validar los documentos en Listas de Control";
    public static final String ERROR_EMPTY_LINE_UPLOAD = "Documento vacio o fila sin datos";
    public static final String ERROR_MISSING_COLUMNS = "Falta algun dato en una de las filas del documento";
    public static final String ERROR_TYPE_DOCUMENT_INVALID_UPLOAD = "Tipo de Documento invalido";
    public static final String ERROR_NUMBER_DOCUMENT_INVALID_UPLOAD = "El tipo de dato del documento debe ser numerico";
    public static final String ERROR_REDIRECT_CHANGE_PASS = "Error al direccionar a la pagina del cambio de contraseña";
    public static final String ERROR_EXECUTE_SERVICE_REST = "Error al llamar servicio Rest";
    public static final String ERROR_PARSING = "Error al Parsear el JSON:";
    public static final String ERROR_GET_BIOMETRY_VERIFICATION_ON_DEMAND = "Error al consultar los detalles de verificación de biometría.";
    public static final String ERROR_GET_URL_IMG_OLD = "No se pudo obtener la imagen almacenada en Daon actualmente";
    public static final String ERROR_SEND_PUSH_BIOMETRY_VERIFICATION = "Error al generar push de verificación de biometría.";
    public static final String ERROR_CONVERT_IMG_BASE64 = "Error al convertir la imagen a base64";
    public static final String ERROR_INSERT_NEW_ROL = "Error al crear un nuevo rol";
    public static final String ERROR_INSERT_PERMISSIONS_BY_ROL = "Error al crear permisos al rol";
    public static final String ERROR_UPDATE_ROL = "Error al actualizar un rol con nuevos permisos";
    public static final String ERROR_GET_CLIENT_BY_NUMBER = "Error al consultar el cliente por numero de celular";
    public static final String ERROR_NOT_FOUND_REGISTRY_DB = "11-9L";
    public static final String ERROR_FOUND_REGISTRY_DB = "No se encuentra el registro en base de datos.";
    public static final String ERROR_LOADING_CONFIGURATION_PARAMETERS = "Error al consultar parametros de configuración.";
    public static final String ERROR_READ_UPLOAD_FILE = "Error en la lectura del archivo.";
    public static final String ERROR_INVALID_COLUMNS_UPLOAD_FILE = "Error en la lectura del archivo. Se encontraron {0} columna(s). Se esperan {1}.";
    public static final String ERROR_INVALID_CO_DOCUMENT = "Error en la lectura del archivo. El documento debe ser númerico.";
    public static final String ERROR_INVALID_PHONE = "Error en la lectura del archivo. El celular debe ser númerico.";
    public static final String ERROR_INVALID_REGISTRY = "Error en la lectura del archivo. El archivo debe contener celular o tipo de documento y documento.";
    public static final String ERROR_INVALID_DOCUMENT_TYPE = "Error en la lectura del archivo. El tipo de documento debe ser alfabetico.";
    public static final String ERROR_INVALID_PA_DOCUMENT = "Error en la lectura del archivo. El documento debe tener números separados por guión.";
    public static final String ERROR_LOAD_PROMTIONS_DOCUMENT = "Ocurrio un error al subir al archivo, intente mas tarde.";
    public static final String ERROR_CLOSING_RESOURCES_TO_UPLOAD_FILE = "Error cerrando recursos para lectura de archivo.";
    public static final String ERROR_MESSAGE_PARSING_ERROR_IN = "Error de Parseo de Payload Request";
    public static final String ERROR_COMMUNICATION_BACKOUT = "Error de comunicación Backout.";
    public static final String ERROR_MESSAGE_REST_COMMUNICATION = "Error consumo servicio REST.";
    public static final String ERROR_INVALID_LONGITUD = " se presento error en el campo longitud (debe ser numero negativo y debe tener un punto) en los puntos con los siguientes Codigos Internos: ";
    public static final String ERROR_INVALID_LATITUD = " se presento error en el campo latitud (debe ser numero positivo y debe tener un punto) en los puntos con los siguientes Codigos Internos: ";
    public static final String ERROR_INVALID_ACTION = " se presento error en el campo accion (debe ser una de las letras C-U-D) en los puntos con los siguientes Codigos Internos: ";
    public static final String ERROR_INVALID_COUNTRY = " se presento error en el campo region en los puntos con los siguientes Codigos Internos: ";
    public static final String ERROR_INVALID_DATA = "Error en la lectura del archivo. Mirar canal de slack ";
    public static final String ERROR_SLACK = "Error al enviar mensaje a Slack.";

    public static final String ERROR_CODE_PARSE_PAYLOAD = "17L";
    public static final String ERROR_CODE_BACKOUT_COMMUNICATION = "44L";
    public static final String ERROR_GET_LIST_DOCUMENT_TYPES = "Error al consultar los tipos de documentos";
    public static final String ERROR_CODE_COMMUNICATION_MDW = "11-500";

    // CONTANTES PARA El ARBOL DE NAVEGACION
    public static final String TREE_CLOSE_ACCOUNT = "Cerrar Cuenta";
    public static final String TREE_MODIFY_CLIENT = "Modificar Cliente";
    public static final String TREE_MANAGE_USER = "Administrar Usuarios";
    public static final String TREE_DEFAULT_NODE = "Root";
    public static final String TREE_MENU = "Menu";

    // CONTANTES PARA LAS URL DE LAS PAGINAS
    public static final String URL_CLOSE_ACCOUNT = "closeAccount.xhtml";
    public static final String URL_CHANGE_INFO_CLIENT = "changeInfoClient.xhtml";
    public static final String URL_MANAGE_USER = "manageUser.xhtml";
    public static final String URL_LOGIN_AUTHO = "/login.xhtml";
    public static final String URL_REDIRECT_CREATE_USER = "views/createUser.xhtml";
    public static final String URL_REDIRECT_MANAGE_USER = "views/manageUser.xhtml";
    public static final String URL_CONTROL_LIST = "controlList.xhtml";
    public static final String URL_REDIRECT_CHANGE_PASS = "views/changePassword.xhtml";
    public static final String URL_RESET_BIOMETRY = "biometryReset.xhtml";
    public static final String CONTEXT_URL_PAGES = "views/";

    // CONTANTES PARA LOS UTIL
    public static final String BASE_PATH_LOGIN = "/admin/faces/";
    public static final String BASE_PATH = "/faces/views/";
    public static final String RESOURCES_PATH = "resources";
    public static final String FILE_PATH = "files";
    public static final String EXTENT_CSV_FILE = ".csv";

    public static final String COMMON_STRING_CHANNEL = "NequiApp";
    public static final String COMMON_STRING_REQUEST_HEADER_NAME_SHP_ADM = "SHP.ADM.WebConsole";
    public static final String COMMON_STRING_CONSUMER_ID_SHP = "SHP-";
    public static final String COMMON_STRING_CONSUMER_NAME_ADM = "ADM-";
    public static final String COMMON_STRING_CONTAINER_ID_WAS = "WAS-Node";
    public static final String COMMON_STRING_CONTAINER_NAME_WAS = "WebSphere Application Server";
    public static final String COMMON_STRING_OP_GET_PROMOTION = "getPromotion";
    public static final String COMMON_STRING_OP_GET_RULES = "getRulesDescription";
    public static final String COMMON_STRING_OP_GET_SERVICE = "PromotionServices";
    public static final String COMMON_STRING_OP_GET_SERVICE_BROKER = "BrokerServices";
    public static final String COMMON_STRING_OP_GET_CERTIFICATE = "getBankCertificate";
    public static final String COMMON_STRING_SERVICE_VERSION = ConstantADM.COMMON_STRING_SERVICE_VERSION_ONE_ZERO_ZERO;

    // CONTANTES PARA LA ACTUALIZACION DEL CLIENTE
    public static final String STATUS_CODE_SUCCESS = "0";
    public static final String INVALID_USER = "Usuario Invalido";
    public static final String USER_NOT_FOUND = "No se encuentra un usuario con este numero de documento";
    public static final String PROCESS_SUCCESS = "Éxito";
    public static final String USER_UPDATED = "Usuario Actualizado";
    public static final String PHONE_NUMBER_FIELD = "Campo Numero Celular";
    public static final String ENTER_NUMBER_PHONE = "Por favor ingresar un numero de celular";
    public static final String CLOSE_EXIT_ACCOUNT = "Se ha cerrado la cuenta: ";
    public static final String WORD_ERROR = "Error: ";
    public static final String WORD_SUCCESS = "Exito: ";
    public static final String COMMON_SUCCESS_DEFAULT_MESSAGE = "Operación realizada exitosamente.";

    // CONSTANTES PARA LAS LISTAS DE CONTROL
    public static final String FILE_UPLOAD_SUCCESS = "Archivo subido satisfactoriamente.";
    public static final String FILE_UPLOAD_ERROR_MESSAGE = "Ocurrió un error al intentar cargar el archivo";
    public static final String DELETE_PROMOTIONS_SUCCESS = "Promociones eliminadas satisfactoriamente";
    public static final String DELETE_COMERCE_SUCCESS = "Comercios eliminados satisfactoriamente";
    public static final String PERSIST_RULE_SUCCESS = "Regla creada exitosamente.";
    public static final String DELETE_PROMOTIONS_ERROR = "Ocurrió un error al eliminar los registros de la base de datos.";
    public static final String PERSIST_RULE_ERROR = "Ocurrió un error al crear regla";
    public static final String FILE_UPLOAD_ERROR = "No se pudo subir el archivo";
    public static final String DOCUMENT_TYPE_CC = "CC";
    public static final String DOCUMENT_TYPE_TI = "TI";
    public static final String DOCUMENT_TYPE_PP = "PP";
    public static final String NAME_NEQUI_POINTS = "PTONE-";
    public static final String FILE_DELETE_SUCCESS = "Archivo eliminado satisfactoriamente.";
    public static final String FILE_DELETE_ERROR = "No se pudo eliminar el archivo.";
    public static final String FILE_UPLOAD_MORNING = " Recuerde que el archivo se cargará en la jornada 1.";
    public static final String FILE_UPLOAD_EVENING = " Recuerde que el archivo se cargará en la jornada 2.";

    // CONSTANTES PARA LA ADMINISTRACION DE USUARIOS
    public static final String SAVE_EXIT_USER = "Usuario Exito";
    public static final String SAVE_EXIT_USER_DESC = "Almacenamiento de usuario satisfactorio";
    public static final String SAVE_ERROR_USER = "Usuario Error";
    public static final String SAVE_ERROR_USER_DESC = "No se pudo guardar el usuario, intentar de nuevo";
    public static final String UPDATE_EXIT_USER_DESC = "Usuario actualizado con Éxito";
    public static final String UPDATE_ERROR_USER_DESC = "No se pudo Actualizar el Usuario";
    public static final String DELETE_EXIT_USER_DESC = "Usuario bloqueado con Éxito";
    public static final String DELETE_ERROR_USER_DESC = "No se pudo Bloquear el Usuario, intente luego";
    public static final String CHANGE_PASS_EXIT = "Se cambio el Password con éxito";
    public static final String CHANGE_PASS_ERROR = "Ocurrio un error al cambiar el password";

    // CONSTANTES PARA LAS ACCIONES EN LA AUDITORIA
    public static final long ID_ACTION_LOGIN = 5;
    public static final long COMMON_LONG_ONE = 1;
    public static final long ID_ACTION_CLOSE_ACCOUNT = 2;
    public static final String DESC_ACTION_LOGIN = "Inicio de Sesion Web Admin";
    public static final String HEADER_FILE_PDF = "Listas de control - NEQUI";
    public static final String DATA_AUDIT = "Dato: ";
    public static final long ID_ACTION_BIOMETRY_VERIFICATION = 7;
    public static final long ID_RESET_BIOMETRIA = 8;
    public static final String DESC_ACTION_UPDATE_BIOMETRIA = "Actualizacion Biometria";
    public static final String DESC_ACTION_BLOCK_USER = "Cliente Bloqueado";

    // CONSTANTES PARA LOS LLAMADOS A SERVICIOS REST
    public static final String REST_SERVICE_FACADE_MDW = "SHP.MDW.ServiceFacade/rest/services/";
    public static final String REST_SERVICE_USER_MANAGEMENT = "rest/services/UserManagementServices/";
    public static final String USER_SERVICES_LOCKUSER_NAME = "UserServices/LockUserServices";
    public static final String USER_SERVICES_PROFILE_NAME = "ProfileService";

    public static final String REST_SERVICE_OPERATION_CREATE_USER = "createUser";
    public static final String REST_SERVICE_OPERATION_CHANGE_PASS = "changePassword";
    public static final String REST_SERVICE_CHANEL = "WADMIN";
    public static final String REST_SERVICE_DATE_FORMAT = "yyyy-MM-dd";
    public static final String REST_SERVICE_CONTENT_TYPE = "content-type";
    public static final String REST_SERVICE_TYPE_APPLICATION = "application/json; charset=utf-8";

    public static final String SERVICE_OPERATION_CREATE_USER = "createUser";
    public static final String SERVICE_OPERATION_CHANGE_PASS = "changePassword";
    public static final String SERVICE_OPERATION_UPDATE_USER = "updateUser";
    public static final String SERVICE_OPERATION_DELETE_USER = "deleteUser";

    public static final String SERVICE_OPERATION_UNFREEZENACCOUNT = "unfreezenAccount";

    public static final String SERVICE_OPERATION_CLOSE_ACCOUNT = "closeAccount";

    public static final String ADMINISTRATOR_ROLE_PARAMETER = "Grupo Administradores";
    public static final String OPERATOR_ROLE_PARAMETER = "Grupo Operadores";
    public static final long ADMINISTRATOR_ROLE_ID = 1L;
    public static final long OPERATOR_ROLE_ID = 2L;

    public static final String LDAP_ACCESS_REQUESTED = " [BEAN-LdapAccessServiceBean][BEAN] - INICIO: LDAP access requested ";

    public static final String COMMON_STRING_REQUEST_MESSAGE = "RequestMessage";
    public static final String COMMON_STRING_REQUEST_BODY = "RequestBody";
    public static final String COMMON_STRING_CAPITALIZED_ANY = "Any";
    public static final String COMMON_STRING_ANY = "any";
    public static final String COMMON_STRING_RESPONSE_MESSAGE = "ResponseMessage";
    public static final String COMMON_STRING_RESPONSE_HEADER = "ResponseHeader";
    public static final String COMMON_STRING_RESPONSE_BODY = "ResponseBody";
    public static final String COMMON_STRING_RESPONSE_ANY = "any";
    public static final String COMMON_STRING_RESPONSE_STATUS = "Status";
    public static final String COMMON_STRING_RESPONSE_STATUS_COD = "StatusCode";
    public static final String COMMON_STRING_RESPONSE_STATUS_DES = "StatusDesc";
    public static final String COMMON_STRING_RESPONSE_INTEGRATION_MESSAGE = "integrationResponse";
    public static final String COMMON_STRING_RESPONSE_INTEGRATION_HEADER = "header";
    public static final String COMMON_STRING_RESPONSE_INTEGRATION_STATUS = "status";
    public static final String COMMON_STRING_RESPONSE_INTEGRATION_STATUS_COD = "code";
    public static final String COMMON_STRING_RESPONSE_INTEGRATION_STATUS_DES = "description";
    public static final String COMMON_STRING_RESPONSE_INTEGRATION_BODY = "body";

    public static final String PROPERTY_PATH_FILE_CSV = "FILE_SYNC_IN";
    public static final String URL_WEB_ADMIN = "UrlWebAdmin: ";
    public static final String REQUEST_WEB_ADMIN = "RequestWebAdmin: ";
    public static final String RESPONSE_WEB_ADMIN = "responseWebAdmin: ";

    // CONSTANTES PARA LA REASON CODE DEL CIERRE DE CUENTA
    public static final String TYPE_PARAMETER_ID_REASON_CODE = "62";
    public static final String TYPE_PARAMETER_DELETETION_ACCOUNT_FINACLE_REASON_CODE = "100";
    public static final String REASON_CODE_FIELD = "Campo Razon Cierre";
    public static final String ENTER_REASON_CODE = "Por favor seleccione una razón de cierre";
    public static final String TYPE_PARAMETER_DELETETION_ACCOUNT_FILE_COLUMN_ORDER = "101";
    public static final String TYPE_PARAMETER_NEQUI_POINTS_FILE_COLUMN_ORDER = "142";
    public static final String TYPE_PARAMETER_DELETETION_ACCOUNT_FILE_PHONE_NUMBER_COLUMN = "CELULAR";
    public static final String TYPE_PARAMETER_DELETETION_ACCOUNT_FILE_REASON_CODE_COLUMN = "RAZON CIERRE";
    public static final String TYPE_PARAMETER_DELETETION_ACCOUNT_FILE_DESCRIPCION_COLUMN = "DESCRIPCION";
    public static final String FILE_PROCESSING_SUCCESS = "Archivo procesado satisfactoriamente.";
    public static final String BACKOUT_PROCESS_IN_EXECUTION = "Operación en ejecución en segundo plano.";

    // CONSTANTE PARA LA CONFIGURACION DEL BASIC AUTHENTICATE PARA EL CONSUMO
    // SERVICIOS REST
    public static final String BASIC_AUTH_USER = "WS_USER";
    public static final String BASIC_AUTH_PASS = "g7OYb9HPkIcL$8F";
    public static final String BASIC_AUTHORIZATION_WORD = "Authorization";
    public static final String BASIC_WORD = "Basic ";

    public static final String BIOMETRY_VERIFICATION_MANAGED_BEAN = "biometryVerificationMGBean";
    public static final String SERVICE_OPERATION_SEND_PUSH = "sendPush";
    public static final String SEND_PUSH_SUCCESS = "Se ha enviado el push.";
    public static final String SERVICE_OPERATION_GET_BIOMETRY_ON_DEMAND = "getBiometryOnDemand";
    public static final String SERVICE_OPERATION_GET_VERI_ATTEMPTS = "getVeriAttempts";
    public static final String SERVICE_OPERATION_GET_PROFILE_BY_ID = "getProfileByID";
    public static final String SERVICE_OPERATION_DAON_UPDATE_PROFILE = "updateProfileDaon";
    public static final String SERVICE_OPERATION_CUSTOMER_LOCK = "customerLockManagment";
    public static final String SERVICE_OPERATION_BLOCK_CLIENT = "blockClient";
    public static final String SERVICE_OPERATION_GET_CUSTOMER_LOCKS = "getCustomerLocks";
    public static final String SERVICE_OPERATION_UPDATE_PROFILE_STATUS = "updateProfileStatus";
    public static final String SERVICE_OPERATION_GET_FILE = "getFile";

    // CONSTANTES PARA EL RESETEO DE BIOMETRIA
    public static final String COMMON_SUCCESS_LIST_ATTEMP_IMG_OLD = "Intentos e imagen de Biometria cargados satisfactoriamente";
    public static final String COMPLETED_SUCCESS_STATUS = "COMPLETED_SUCCESSFUL";
    public static final String COMPLETED_FAILURE_STATUS = "COMPLETED_FAILURE";
    public static final String REFRESH_NEW_IMG_SUCCESS = "Se cargo con éxito la nueva imagen enrolada";
    public static final String NOT_FOUND_NEW_IMG_URL = "No se encontro nueva imagen enrolada";
    public static final String ACTION_UPDATE_PROFILE_DAON = "face";
    public static final String UPDATE_PROFILE_DAON_IMG = "Se actualizo la imagen en Daon correctamente";
    public static final String IMG_TYPE_UPDATE_PROFILE_DAON = "jpg";
    public static final String CUSTOMER_LOCK_TYPE = "BLO_ADM";
    public static final String CUSTOMER_LOCK_ACTION_CREATE = "create";
    public static final String CUSTOMER_LOCK_ACTION_DELETE = "delete";
    public static final String RESET_WITHOUT_OTP_SUCCESS = "Se inició el proceso de reseteo de biometría sin OTP";
    public static final String BLOCK_CLIENT_PANIC = "LOCK_TYPE_FREEZE";
    public static final String BLOCK_CLIENT_PANIC_SUCCESS = "Cliente Bloqueado Satisfactoriamente";
    public static final String WORD_SLASH_SPLIT_URL = "/";
    public static final String MAP_KEY_URL_S3 = "keyUrlS3";
    public static final String MAP_BUCKET_URL_S3 = "bucketUrlS3";
    public static final String PROVIDER_HOST_DELIMITER = "://";

    // CONSTANTES PARA LA ADMINISTRACION DE ROLES Y PERMISOS
    public static final String ERROR_CREATE_PERMISSIONS_BY_ROL = "Error en Perfil";
    public static final String ERROR_CREATE_PERMISSIONS_BY_ROL_DESC = "Error al crear un perfil y asociar permisos";
    public static final String SUCCESS_CREATE_PERMISSIONS_BY_ROL = "Creación de Perfil éxitosa";
    public static final String SUCCESS_CREATE_PERMISSIONS_BY_ROL_DESC = "Se ha creado el perfil y asociado los permisos satisfactoriamente";
    public static final String ERROR_GET_LIST_ACTION_BY_ROL = "Error al las acciones y permisos de un rol";
    public static final String ERROR_UPDATE_PERMISSIONS_BY_ROL = "Error al actualizar el Perfil";
    public static final String ERROR_UPDATE_PERMISSIONS_BY_ROL_DESC = "Se han actualizado los nuevos permisos con éxito";
    public static final String SUCCESS_UPDATE_PERMISSIONS_BY_ROL = "Modificación de Perfil éxitosa";
    public static final String SUCCESS_UPDATE_PERMISSIONS_BY_ROL_DESC = "Se ha Modificado el perfil satisfactoriamente";
    public static final String WORD_COMMA = ", ";

    // CONSTANTES PARA LAS VALIDACIONES DE CAMPOS
    public static final String VALIDATION_TYPE_EMAIL_TITLE = "Error e-mail";
    public static final String VALIDATION_TYPE_EMAIL_DESC = " no es un e-mail valido";

    public static final String VALIDATION_TYPE_BUDGET_TITLE = "Error presupuesto máximo";
    public static final String VALIDATION_TYPE_BUDGET_DESC = "El presupuesto máximo no puede ser menor al valor disponible";

    // Constantes para ManagedBeans
    public static final String CLEAN_FORM_SAVE_USER = "fieldUser:formCreateUser:panelCreateUser";
    public static final String UPDATE_USER_DIALOG_ID = " formUpdateUser:dlgUserUpdate";
    public static final String UPDATE_PRMOTION_DIALOG = " formCreateProfile:dlgPromUp";

    // CONSTANTES PARA DESBLOQUEO DE CLIENTE EN FINACLE
    public static final String UNLOCK_FINACLE_TYPE_PARAMETER_ID = "105";

    public static final byte AW_USER_NAMES_MAX_LENGTH = 60;
    public static final byte AW_USER_LAST_NAMES_MAX_LENGTH = 60;
    public static final byte AW_USER_MAIL_MAX_LENGTH = 60;

    public static final String ADMIN_WEB_LOGGER_HEADER = "[ADMIN-WEB]: ";

    // CONSTANTES ENUM TypeIdClientEnum
    public static final String TYPE_ID_CC_CODE = "CC";
    public static final String TYPE_ID_CC_DESC = "Cédula";
    public static final String TYPE_ID_TI_CODE = "TI";
    public static final String TYPE_ID_TI_DESC = "Tarjeta Identidad";
    public static final String TYPE_ID_PP_CODE = "PP";
    public static final String TYPE_ID_PP_DESC = "Cédula de extranjería";

    public static final String UTIL_SERVICES_VERSION_1_0_0 = ConstantADM.COMMON_STRING_SERVICE_VERSION_ONE_ZERO_ZERO;
    public static final String UTIL_SERVICES_VERSION_1_1_0 = "1.1.0";
    public static final String MAX_RESULT = "0";
    // Generales Backout
    public static final String COMMON_STRING_QUEUE_FACTORY = "jms/SHP-CF";
    public static final String COMMON_STRING_ENVIROMENT = "java:comp/env";

    // Colas
    public static final String COMMON_STRING_QUEUE_MASSIVE_CLOSE_ACCOUNT = "jms/BCO-MassiveCloseAccount.Q";

    public static final String COMMON_FORMAT_DATE_TO_FRONT = "yyyy-MM-dd HH:mm:ss";

    public static final byte COMMON_INT_ZERO = 0;
    public static final byte COMMON_INT_THREE = 3;
    public static final byte COMMON_INT_CLIENT_LINKED_STATUS = COMMON_INT_THREE;

    // CONSTANTES PARA DESBLOQUEO
    public static final String TITLE_UNLOCK_SUCCESS = "Desbloqueado";
    public static final String MESSAGE_UNLOCK_SUCCESS = "Bloqueo levantado con éxito";

    public static final String TITLE_UNLOCK_ERROR = "Fallido";
    public static final String MESSAGE_UNLOCK_ERROR = "No se pudo levantar el bloqueo";

    public static final String COMMON_UNLOCK_NAME = "Account";
    public static final String COMNON_UNLOCK_NAMESPACE = "http://co.bancaDigital/nequi/services/AccountServices/Account/v1.0";
    public static final String COMMON_UNLOCK_OPERATION = "unfreezeAccount";
    public static final String COMMON_UNLOCK_RQ = "unfreezeAccountRQ";
    public static final String COMMON_UNLOCK_RS = "unfreezeAccountRS";

    // TIPOS DE BLOQUEO
    public static final String COMMON_STRING_LOCK_FINACLE = "FINACLE";
    public static final String COMMON_STRING_LOCK_LDAP = "LDAP";
    public static final String COMMON_STRING_LOCK_OTP = "OTP";
    public static final String COMMON_STRING_LOCK_PANIC = "PANIC";
    public static final String COMMON_STRING_LOCK_DB = "DB";
    public static final String TYPE_PARAMETER_ID_DOCUMENT_TYPES = "107";

    public static final String SERVICE_REGISTRY_NAME = "ServiceRegistry";
    public static final String COMMON_STRING_CONSUMER_ID = "SHP-MDW-BIS";
    public static final String COMMON_STRING_CONSUMER_NAME = "ServiceController";
    public static final String COMMON_STRING_CONTAINER_NAME = "WAS8.5";
    public static final String COMMON_STRING_CONTAINER_ID_AS_001 = "AS-001";
    public static final String INTEGRATION_CLASSIFICATION = "INTEGRATION";
    public static final String BUSINESS_CLASSIFICATION = "BUSINESS";
    public static final String ERROR_MESSAGE_LOOKUP = "Ocurrió un error al hacer lookup con serviceRegistry";
    public static final String COMMON_STRING_SERVICE_NAME_PROM_SERVICES = "PromotionServices";
    public static final String COMMON_STRING_GET_OPERATION = "getPromotionDetail";
    public static final String COMMON_STRING_UPDATE_OPERATION = "updatePromotionRule";
    public static final String COMMON_STRING_GET_RULES_DESCRIPTION = "getRulesDescription";
    public static final String COMMON_STRING_SERVICE_REGION_CORE = "001";
    public static final String COMMON_STRING_SERVICE_VERSION_ONE_ZERO_ZERO = "1.0.0";
    public static final String ERROR_GET_PROMOTION = "Error al consultar";
    public static final String ERROR_GET_PDF = "Error al consultar pdf";
    public static final String ERROR_GET_PROMOTION_DESC = "Error al traer las reglas de la promocion";
    public static final String ERROR_GET_PDF_DESC = "Error al intentar descargar el PDF";
    public static final String PAGE_EDIT = "getPromotion.xhtml";
    public static final String PAGE_GET = "getPromotion.xhtml";
    public static final String ERROR_GMF = "Error al realizar operación de GMF";

    // Promociones
    public static final String SUCCESS_UPDATE_PROMOTION = "Modificación de promoción exitosa";
    public static final String SUCCESS_UPDATE_PROMOTION_DESC = "Se ha Modificado la promoción satisfactoriamente";
    public static final String ERROR_UPDATE_PROMOTION = "Error al actualizar la promoción";
    public static final String ERROR_UPDATE_PROMOTION_DESC = "No se pudo actualizar la promoción";
    public static final String STRING_COD_PROM = "2";
    public static final String STRING_TYPE_PROMOTION_BONO = "2";
    public static final String STRING_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String STRING_FORMAT_DATE = "yyyy-MM-dd";
    public static final String STRING_FORMAT_TIME = "HH:mm:ss";
    public static final String STRING_CONFIRMATION = "confirmation";
    public static final String STRING_GET_PROMOTION = "getPromotion";
    public static final String STRING_CONFIRMATION_UPDATE = "confirmationUpdate";
    public static final String STRING_UNO = "1";
    public static final String ERROR_RANGE_FREQUENCY_RESTART_DESC = "La frecuencia de reincio debe ser menor o igual a los días que dure la promoción";

    public static final Double COMMON_DOUBLE_ZERO = 0.0d;
    public static final BigDecimal COMMON_BIG_DECIMAL_ZERO = new BigDecimal(
            COMMON_DOUBLE_ZERO);
    public static final Double COMMON_DOUBLE_ONE = 1.0d;
    public static final BigDecimal COMMON_BIG_DECIMAL_ONE = new BigDecimal(
            COMMON_DOUBLE_ONE);

    public static final String COMMON_STRING_TYPE_PARAMETER_BANK_ID = "99";
    public static final String COMMON_STRING_PARAMETER_REPORTS = "78";
    public static final String COMMON_STRING_PARAMETER_BANK_ID = "BANKID";
    public static final String COMMON_STRING_PLUS = "+";
    public static final String COMMON_STRING_LDAP_401_SERVICE_NAME = "LDAPService";
    public static final String COMMON_STRING_CREATE_USER_LDAP_401_SERVICE_OPERATION = "createUserLDAP";
    public static final String COMMON_STRING_CREATE_USER_LDAP_401_SERVICE_REGION = "001";
    public static final String COMMON_STRING_CREATE_USER_LDAP_401_SERVICE_VERSION = COMMON_STRING_SERVICE_VERSION_ONE_ZERO_ZERO;

    /* SERVICIO BROKER PARA CONSULTAR CUSTOMER DETAILS */
    public static final String COMMON_STRING_NAME_CUSTOMER = "CustomerDetails";
    public static final String COMMON_STRING_NAMESPACE_CUSTOMER = "http://co.bancaDigital/nequi/services/UserServices/CustomerDetails/v1.0";
    public static final String COMMON_STRING_OPERATION_CUSTOMER = "getCustomerStatus";
    public static final String REQUEST_CUSTOMER_DETAILS = "getCustomerStatusRQ";
    public static final String RESPONSE_CUSTOMER_DETAILS = "getCustomerStatusRS";

    // MENSAJE PARA EL POST CONSTRUCT - RESOURCELOCATOR
    public static final String ERROR_MESSAGE_SINGLETON = "Ocurrió un error en el postConstruct del singleton (ResourceLocator)";
    public static final String ERROR_CODE_SINGLETON = "24L";
    public static final String COMMON_SYSTEM_MDW = "MDW";
    public static final String ERROR_MESSAGE_IIB_COMMUNICATION = "Error de Proveedor: Broker";
    public static final String COMMON_STRING_REPORT_NAME_BANK_CERTIFICATION = "certificado_bancario";
    public static final String COMMON_STRING_PARAMETER_REPORTS_JASPER_BANK_CERTIFICATION = "ReportPathBankCert";
    public static final String FORMAT_DATE_FINACLE = "dd-MMM-yy";
    public static final String FORMAT_DATE_PATTERN = "EEEE d 'de' MMMM 'de' yyyy hh:mm aaa";
    public static final String FORMAT_DATE_PATTERN_BANK_CERTIFICATION = "EEEE, d 'de' MMMM 'de' yyyy";
    public static final String FORMAT_DATE_DOCUMENT = "dd-MM-yyyy";

    public static final String COMMON_STRING_CC = "CC";
    public static final String COMMON_STRING_CE = "CE";
    public static final String COMMON_STRING_PS = "PS";
    public static final String COMMON_STRING_TI = "TI";

    public static final String COMMON_STRING_DESC_CC = "cédula de ciudadanía";
    public static final String COMMON_STRING_DESC_CE = "cédula de extranjería";
    public static final String COMMON_STRING_DESC_PS = "pasaporte";
    public static final String COMMON_STRING_DESC_TI = "tarjeta de identidad";

    public static final String COMMON_STRING_ESTADO_A = "A";
    public static final String COMMON_STRING_ESTADO_I = "I";
    public static final String COMMON_STRING_ESTADO_D = "D";

    public static final String COMMON_STRING_ESTADO_ACTIVO = "ACTIVA";
    public static final String COMMON_STRING_ESTADO_INACTIVO = "INACTIVA";
    public static final String COMMON_STRING_ESTADO_DORMIDO = "DORMIDA";

    public static final String COMMON_STRING_ES_LOWER_CASE = "es";
    public static final String COMMON_STRING_ES_UPPER_CASE = "ES";

    public static final String PATTERN_STRING_FORMAT_DATE = "FORMAT_DATE";
    public static final String PATTERN_STRING_REPORT_DATE = "REPORT_DATE";

    public static final String ERROR_MESSAGE_REPORT_BUILD = "Ocurrió un error al ejecutar el reporte en Jasper Report.";
    public static final String PDF_CONSTRUCTOR = "attachment; filename=\"certificado_bancario.pdf\"";
    public static final String CONTENT_DISPOSITION = "Content-disposition";
    public static final String APPLICATION_TYPE_PDF = "application/pdf";

    public static final String ERROR_MESSAGE_GENERIC_ERROR = "Error genérico.";

    public static final String PAYLOAD_RQ = "[PAYLOAD RQ: ]";
    public static final String PAYLOAD_RS = "[PAYLOAD RS: ]";

    public static final String COMMON_STRING_ESTADO_PROMO_ACTIVO = "Activo";
    public static final String COMMON_STRING_ESTADO_PROMO_INACTIVO = "Inactivo";
    public static final String COMMON_NUM_ESTADO_PROMO_ACTIVO = "1";

    public static final String ORIGINAL = "áéíóúàèìòùäëïöüâêîôûñçÁÉÍÓÚÀÈÌÒÙÄËÏÖÜÂÊÎÔÛÑÇåÅ";
    public static final String TO_REPLACE = "aeiouaeiouaeiouaeiouncAEIOUAEIOUAEIOUAEIOUNCaA";

    public static final String TYPE_PARAMETER_ID_POINTS_TYPES = "141";
    public static final String ERROR_GET_LIST_POINTS_TYPES = "Error al consultar los tipos de puntos Nequi";
    public static final String COMMON_STRING_CO = "CO";
    public static final String COMMON_STRING_PA = "PA";
    public static final String POINT_TYPE_FIELD = "Campo Tipo de punto";
    public static final String SELECT_POINT_TYPE = "Por favor seleccione un tipo de punto";

    public static final String SELECT_VALUE_NEQUI_POINTS = "formNequiPoints:selectPoints_input";

    public static final byte COMMON_INT_TWELVE = 12;
    public static final String FORMAT_DATE_NEQUI_POINTS = "ddMMyyyy";

    public static final int COMMON_SEVEN = 7;
    public static final int COMMON_ONE = 1;
    public static final int COMMON_NEGATIVE_ONE = -1;
    public static final int COMMON_EIGHT = 8;
    public static final int COMMON_NINE = 9;
    public static final int COMMON_TEN = 10;
    public static final int COMMON_TWO = 2;
    public static final int TWENTY_SEVEN = 27;
    public static final int TWENTY_EIGHT = 28;
    public static final String REGULAR_EXPRESION_LONGITUD = "^(\\-\\d+(\\.\\d+)?)";
    public static final String REGULAR_EXPRESION_LATITUD = "^(\\d+(\\.\\d+)?)";
    public static final String REGULAR_EXPRESION_ACTION = "^[C|U|D|]$";
    public static final String CODIGO_INTERNO = "CODIGO INTERNO";

    // Variables para slack
    public static final String NAME_NOTIFICATION = "Notification";
    public static final String NAMESPACE_UTIL_SERVICES_NOTIFICATION = "http://co.bancaDigital/nequi/services/UtilServices/Notification/v1.0";
    public static final String OPERATION_SEND_SLACK_NOTIFICATION = "sendMigration";
    public static final String OPERATION_SEND_SLACK_NOTIFICATION_RQ = "sendSlackNotificationRQ";
    public static final String OPERATION_SEND_SLACK_NOTIFICATION_RS = "sendSlackNotificationRS";
    public static final String COMMON_STRING_UTIL_SERVICES = "UtilServices";
    public static final String COMMON_STRING_UTIL_SERVICES_VERSION = "1.0";
    public static final String COMMON_STRING_SLACK_PARAMETERS = "59";
    public static final String COMMON_STRING_SLACK_CHANNEL_NAME = "NotificarPTONE";

    public static final String SYSTEM_TEMPORARY_ROUTE = "java.io.tmpdir";

    public static final String SERVICE_OPERATION_GET_BANK_CERTIFIED = "getBankCertified";
    public static final String GET_BANK_CERTIFIED_SERVICE_NAME = "GetBankCertifiedServices";
    public static final byte COMMON_ZERO = 0;
    public static final int HOURS_IN_A_DAY = 24;
    public static final int MINUTES_IN_AN_HOUR = 60;
    public static final int ONE_THOUSAND = 1000;

    public static final String REGULAR_EXPRESION_COLOMBIA_DOCUMENT = "^[0-9]*$";
    public static final String REGULAR_EXPRESION_PANAMA_DOCUMENT = "^[0-9-]*$";
    public static final String REGULAR_EXPRESION_ALPHABETICAL = "^[a-zA-Z]*$";
    public static final String COMMON_STRING_NULL = "null";

    public static final String COMMON_STRING_SPECIAL_CHARACTERS_PARAMETER_TYPE = "152";
    public static final String COMMON_STRING_NORMAL_CHARACTERS_PARAMETER_NAME = "CaracteresNormales";
    public static final String COMMON_STRING_SPECIAL_CHARACTERS_PARAMETER_NAME = "CaracteresEspeciales";

    public static final String ERROR_GET_SPECIAL_CHARACTERS_PARAMETERS = "Error al consultar los parámetros de caracteres especiales.";
    public static final String COMMON_STRING_ROL_ADMINISTRADOR = "Administrador";

    public static final int MASSIVE_CLOSE_ACCOUNT_MAX_LENGTH = 200;
    public static final int PROMTIONS_DOCUMENT_MAX_LENGTH = 500;
    public static final String MASSIVE_CLOSE_ACCOUNT_MAX_LENGTH_ERROR = "Formato inválido, el archivo no debe contener más de 200 registros.";
    public static final String PROMTIONS_DOCUMENT_MAX_LENGTH_ERROR = "Formato inválido, el archivo no debe contener más de 500 registros.";
    public static final String COMMON_STRING_USER_PROMOTION_REQUIRED_COLUMNS = "Tipo de documento, documento y celular";
    public static final String COMMON_STRING_CCIO_PROMOTION_REQUIRED_COLUMNS = "Comercio, terminal y estado";
    public static final String REGULAR_EXPRESION_NUMBERS = REGULAR_EXPRESION_COLOMBIA_DOCUMENT;
    public static final String COMMON_STRING_JPA_MANAGER = "JPAManager";
    public static final String COMMON_STRING_GET_PROMOTION_IS_TRACEABLE = "getPromtionIsTraceable";
    public static final String COMMON_STRING_GET_PROMOTION_IS_DEBUGABLE = "getPromtionIsDebugable";
    public static final String ERROR_MESSAGE_VALIDATE_PROMOTION_CUSTOMER_INFO = "El registro tiene el(los) error(es):";

    public static final String DEFAULT_TERMINAL = "all";
    public static final String DEFAULT_STATUS = STRING_UNO;
    public static final String COMMON_STRING_COMERCE_REQUIRED_FIELD = "El campo comercio es obligatorio.";
    public static final String COMMON_STRING_ERROR_SATUS_FIELD = "El estado debe ser 1(Activo) o 0(Inactivo)";
    public static final String COMMON_STRING_PROMO_INACTIVA = COMMON_STRING_ZERO;
    public static final String COMMON_STRING_WEB_ADMIN_PARAMETERS_ID = "175";
    public static final String COMMON_STRING_USERS_LIMIT_DEFAULT = "100";
    public static final String COMMON_STRING_USERS_LIMIT_PARAMETER_NAME = "usersLimit";
    public static final String COMMON_STRING_NOTIFICATION_TYPE_PUSH = "PUSH";
    public static final String COMMON_STRING_VALUE_TYPE_PERCENTAGE = "%";
    public static final String COMMON_STRING_RESARCIMIENTO = "resarcimiento";

    private ConstantADM() {

    }

}
