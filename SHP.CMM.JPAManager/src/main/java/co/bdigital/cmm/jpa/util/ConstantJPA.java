/**
 * 
 */
package co.bdigital.cmm.jpa.util;

/**
 * La clase ConstantJPA Maneja las constantes para JPAManager.
 * 
 * @author cristian.martinez@pragma.com.co
 *
 */
public class ConstantJPA {

    // PARAMETROS O PROPIEDADES DE CACHE PARA JPA.
    public static final String CACHE_RETRIEVE_MODE = "javax.persistence.cache.retrieveMode";
    public static final String CACHE_STORE_MODE = "javax.persistence.cache.storeMode";

    public static final String CONSULTA_CIFIN_FIND_BY_ESTADO = "ConsultaCifin.findByEstado";
    public static final String ELIMINA_REGISTRO_PROMOCION_USUARIO = "PromocionUsuario.deletePromocionOperacionId";
    public static final String NAMED_QUERY_PROMOCION_USUARIO_GET_PROMOCION_USUARIO_LIMIT = "PromocionUsuario.getPromocionUsuarioLimit";
    public static final String NAMED_QUERY_PROMOCION_REGLA_GET_MAX_ID = "PromocionRegla.getMaxId";
    public static final String NAMED_QUERY_PROMOCION_REGLA_FIND_BY_SERVICE = "PromocionOperacion.findByService";
    public static final String ELIMINA_REGISTRO_PROMOCION_COMERCIO = "PromocionComercio.deletePromocionOperacionId";
    public static final String COUNT_PROMOCION_USUARIO_ROWS = "PromocionUsuario.countPromocionUsuarioRows";
    public static final String COMMON_PRAMETER_PROMOCION_OPERACION_ID = "promOperacionId";
    public static final String COMMON_PRAMETER_LIMIT = "limit";
    public static final String CONSULTA_CIFIN_FIND_BY_ESTADO_REINTENTO_CLIENTE_ESTADO_ID_TIPO_ID = "ConsultaCifin.findByEstadoReintentoClienteEstadoIdTipoId";
    public static final String CONSULTA_CIFIN_ESTADO = "estado";
    public static final String CONSULTA_CIFIN_REINTENTO = "reintento";
    public static final String CONSULTA_CIFIN_CLIENTE_ESTADO_ID = "clienteEstadoId";
    public static final String CONSULTA_CIFIN_CLIENTE_TIPO_ID = "clienteTipoId";
    public static final String COMMON_PARAMETER_IDCLIENT = "idClient";
    public static final String COMMON_PARAMETER_NAME = "name";
    public static final String COMMON_STRING_CORREO_ELECTRONICO = "correoElectronico";
    public static final String CONSULTA_CIFIN_CLIENT_COD_DIVISION_GEOGRAFICA = "clientCodDivisionGeografica";
    public static final String QUERY_DETALLE_CLIENTE_BY_IDCLIENT_AND_NAME = "DetalleCliente.findByIdClientAndName";
    public static final String QUERY_CLIENTE_BY_PHONENUMBER = "Cliente.findByPhoneNumber";
    public static final String COMMON_PARAMETER_PHONENUMBER = "phoneNumber";
    public static final String QUERY_ESTADOBIOMETRICO_BY_CLIENTID = "EstadoBiometrico.findByClientId";
    public static final String COMMON_PARAMETER_CLIENTID = "clienteId";
    public static final String QUERY_INTENTO_BY_PHONENUMBER_AND_TYPE = "Intento.findByPhoneNumberAndType";
    public static final String COMMON_PARAMETER_TYPE = "type";
    public static final String QUERY_PARAMETRO_BY_TIPOPARAMETROID = "Parametro.findByTipoParametroId";
    public static final String COMMON_PARAMETER_TIPOPARAMETROID = "tipoParametroId";
    public static final String QUERY_POLICYCLIDET_BY_IDCLIENTE_AND_IDPOLITICA = "PoliticaCliDetalle.findByIdClienteAndIdpolitica";
    public static final String QUERY_BLOQUEOUSUARIO_BY_IDCLIENTE = "BloqueoUsuario.findByClientId";
    public static final String COMMON_PARAMETER_IDPOLITICA = "idPolitica";
    public static final String COMMON_STRING_TYPE_BLOCKING = "tipoBloqueo";

    // GENERALES
    public static final String COMMON_STRING_THREE = "3";
    public static final String COMMON_STRING_DOSCIENTOS = "200";
    public static final String COMMON_FORMAT_DATE_WITHOUT_TIME = "dd-MMM-yyyy";
    public static final String COMMON_STRING_DATE_LARGE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static final String COMMON_STRING_NEQUI_UPPER = "NEQUI";
    public static final String COMMON_STRING_PHONE = "celular";
    public static final String COMMON_STRING_CLIENT = "cliente";
    public static final String COMMON_STRING_CLIENT_ID = "clienteId";
    public static final String COMMON_STRING_TYPE_IMAGE = "tipoImagen";
    public static final String COMMON_STRING_SPACE = " ";
    public static final String COMMON_STRING_EMPTY = "";
    public static final int COMMON_INT_ZERO = 0;
    public static final int COMMON_INT_ONE = 1;
    public static final String COMMON_STRING_ZERO = "0";
    public static final String COMMON_STRING_ONE = "1";
    public static final String COMMON_STRING_MDW = "MDW";

    // Nombres de metodos
    public static final String PERSIST_PENDING_REVERSE = ":persistPendingReverse:";
    public static final String PERSIST_ACCOUNT_REVERSE = ":persistAccountReverse:";
    public static final String CONSULTA_DIVISION_GEOGRAFICA_FIND_ALL = "DivisionGeografica.findAll";
    public static final String PERSIST_EXP_PERSONA = ":persistExpPersona:";
    public static final String METHOD_PERSIST_DETALLE_CLIENTE = ":persistDetalleCliente:";
    public static final String METHOD_EXIST_EMAIL = ":existEmail:";
    public static final String METHOD_GET_CUSTOMER_BY_EMAIL = ":getClientByEmail:";
    public static final String PERSIST_PENDING_TRANSACTION = ":persistPendingTransaction:";
    public static final String UPDATE_PENDING_TRANSACTIONS_WITH_STATUS = ":updatePendingTransactionsWithStatus:";
    public static final String GET_PENDING_TRANSACTION = ":getPendingTransaction:";
    public static final String METHOD_REMOVE_BLOQUEO_USUARIO = ":removeBloqueoUsuario:";
    public static final String COMMON_STRING_COUNTRY_CODE = "divisionGeografica";
    public static final String COMMON_STRING_ID = "id";
    public static final String METHOD_UPDATE_PROCESS_ID = ":updateProcessId:";
    public static final String METHOD_PERSIST_BLOQUEO_USUARIO = ":persistBloqueoUsuario:";
    public static final String ERROR_INCORRECT_INPUT_PARAMETERS = "Parametros de consulta incorrectos";
    public static final String METHOD_GET_BLOQUEO_USUARIO = ":getBloqueoUsuario:";
    public static final String METHOD_UPDATE_BLOQUEO_USUARIO = ":updateBloqueoUsuario:";
    public static final Object COMMON_STRING_PERSIST_CIFIN_INFO_METHOD = ":persistCifInformacion:";
    public static final Object COMMON_STRING_UPDATE_CIFIN_INFO_METHOD = ":mergeCifInformacion:";
    public static final Object COMMON_STRING_GET_CIFIN_INFO_METHOD = ":getCifInformacion:";
    public static final Object COMMON_STRING_UPDATE_CATS_MIG_STATUS = ":mergeCatsMigStatus:";
    public static final String COMMON_STRING_MERGE_CATS_MIG_METHOD = ":mergeCatsMig:";
    public static final String COMMON_STRING_PERSIST_CATS_MIG_METHOD = ":persistCatsMig:";
    public static final String COMMON_STRING_PERSIST_PROMOCION_USUARIO = ":persistPromocionUsuario:";
    public static final String COMMON_STRING_PERSIST_PROMOCION_REGLA = ":persistPromocionRegla:";
    public static final String METHOD_GET_PROMOTION_OPERATION_BY_SERVICE = ":getPromocionOperacionByService:";
    public static final String COMMON_STRING_PERSIST_PROMOCION_COMERCIO = ":persistPromocionComercio:";
    public static final String COMMON_STRING_METHOD_GET_PROMOCION_OPERACION = ":getPromocionOperacionById:";
    public static final String COMMON_STRING_METHOD_GET_PROMOCION_COMERCIO = ":getPromocionComercio:";
    public static final String COMMON_STRING_METHOD_GET_PROMOCION_USUARIO = ":getPromocionUsuario:";
    public static final String COMMON_STRING_METHOD_GET_PROMOCION_USUARIO_LIMIT = ":getPromocionUsuarioLimit:";
    public static final String COMMON_STRING_DELETE_PROMOCION_USUARIO = ":deleteAllPromocionUsuarioByPromocionOperacion:";
    public static final String COMMON_STRING_METHOD_GET_PROMOCION_USUARIO_SIZE = ":getPromocionUsuarioSize:";
    public static final String COMMON_STRING_METHOD_GET_PROMOCION_REGLA_MAX_ID = ":getPromocionReglaMaxId:";
    public static final String COMMON_STRING_PROMOCIO_OPERACION_ID = "promocionOperacionId";
    public static final String COMMON_STRING_PROMOCIO_OPERACION = "promocionOperacion";
    public static final String COMMON_STRING_PROMOCION_USUARIO_FIND_ALL = "PromocionUsuario.findAll";
    public static final String COMMON_STRING_PROMOCION_USUARIO_SELECT_ALL = "SELECT p FROM PromocionUsuario p";
    public static final String COMMON_STRING_PROMOCION_USUARIO_DELETE_ALL = "DELETE FROM PromocionUsuario e WHERE e.promocionOperacion.promocionOperacionId = :promOperacionId";
    public static final String NAMED_QUERY_PROMOCION_USUARIO_GET_PROMOCION_USUARIO_LIMIT_QUERY = "SELECT e FROM PromocionUsuario e LEFT JOIN e.promocionOperacion p WHERE p.promocionOperacionId = :promOperacionId";
    public static final String COMMON_STRING_PROMOCION_COMERCIO_DELETE_ALL = "DELETE FROM PromocionComercio e WHERE e.id.promocionOperacionId = :promOperacionId";
    public static final String COMMON_STRING_PROMOCION_USUARIO_COUNT = "SELECT COUNT(e.promocionUsuarioId) FROM PromocionUsuario e WHERE e.promocionOperacion.promocionOperacionId = :promOperacionId";
    public static final String COMMON_STRING_PROMOCION_USUARIO = "PROMOCION_USUARIO";
    public static final String COMMON_STRING_PROMOCION_COMERCIO = "PROMOCION_COMERCIO";
    public static final String COMMON_STRING_PROMOCION_REGLA = "PROMOCION_REGLA";
    public static final String COMMON_STRING_SHBANCA_DIGITAL = "SHBANCA_DIGITAL";
    public static final String COMMON_STRING_PROMOCION_OPERACION = "PROMOCION_OPERACION";
    public static final String SQL_NAMED_QUERY_FIND_ALL_ATTENDACES_DAYS_AND_SCHEDULES_BY_NEQUI_POINTS_AND_DAY_TYPE = "DiasAtencion.findAllAttendancesAndSchedulesByNequiPoints";
    public static final String SQL_QUERY_FIND_ALL_ATTENDACES_DAYS_AND_SCHEDULES_BY_NEQUI_POINTS_AND_DAY_TYPE = "SELECT a FROM DiasAtencion a JOIN FETCH a.horarios JOIN a.puntosNequi np WHERE np.codigoInterno IN :nequiPoints AND a.tipoDia = :dayOfWeekShortForm ORDER BY np.codigoInterno";
    public static final String COMMON_STRING_NEQUI_POINTS = "nequiPoints";
    public static final String COMMON_STRING_DAY_OF_WEEK_SHORT_FORM = "dayOfWeekShortForm";
    public static final String GET_ATTENDANCES_DAYS_AND_SCHEDULES_BY_NEQUI_POINTS_AND_DAY_TYPE_METHOD_NAME = "getAttendaceDaysAndSchedulesByNequiPoints";
    public static final String ATTENDANCES_DAYS_TABLE_NAME = "DIAS_ATENCION";
    public static final String SQL_NAMED_QUERY_FIND_ALL_ATTENDANCES_DAYS = "DiasAtencion.findAll";
    public static final String SQL_QUERY_FIND_ALL_ATTENDANCES_DAYS = "SELECT d FROM DiasAtencion d";
    public static final String COMMON_STRING_PERCENTAGE_CHARACTER = "%";

    public static final String COMMON_STRING_COD_INTERNO = "codigoInterno";
    public static final String COMMON_STRING_NOMBRE = "nombreActual";
    public static final String COMMON_STRING_NOMENCLATURA = "nomenclatura";
    public static final String COMMON_STRING_TIPO_PUNTO = "tipoPunto";
    public static final String COMMON_STRING_DIVISION_CODE = "codigoDivision";
    public static final String COMMON_STRING_COD_INTERNO_PUNTO = "codigoInternoPunto";
    public static final String COMMON_STRING_DATE_START = "fechaInicio";
    public static final String COMMON_STRING_DATE_FINISH = "fechaFin";
    public static final String COMMON_STRING_PROM_ACTIVE = "promoActiva";
    public static final String COOMON_STRING_PROM_ACTIVE_YES = "S";

    public static final String QUERY_SQL_GETPUNTOSNEQUI = JPAUtil
            .generateStringConcatenated(
                    "SELECT PN.CODIGO_INTERNO, PN.NOMBRE_ACTUAL, PN.LONGITUD, PN.LATITUD, PN.TIPO_PUNTO, ",
                    "PN.CODIGO_DANE, PN.FORMATO,PN.NOMENCLATURA, PN.TELEFONO,PN.USR_CREACION, ",
                    "PN.FECHA_CREACION, PN.USR_MODIFICACION, PN.FECHA_MODIFICACION ",
                    "FROM ( ", "SELECT Z.CODIGO_INTERNO, ", "Z.TIPO_PUNTO, ",
                    "P.RADIUS, ", "P.DISTANCE_UNIT ",
                    "* RAD2DEG * (ACOS(COS(DEG2RAD * (P.LATPOINT)) ",
                    "* COS(DEG2RAD * (Z.LATITUD)) ",
                    "* COS(DEG2RAD * (P.LONGPOINT - Z.LONGITUD)) ",
                    "+ SIN(DEG2RAD * (P.LATPOINT)) ",
                    "* SIN(DEG2RAD * (Z.LATITUD)))) AS DISTANCE ",
                    "FROM SHBANCA_DIGITAL.PUNTOS_NEQUI Z ", "JOIN ( ",
                    "SELECT ?1 AS LATPOINT, ?2 AS LONGPOINT, ",
                    "?3 AS RADIUS, ?4 AS FILTRO, 111.045 AS DISTANCE_UNIT, ",
                    "57.2957795 AS RAD2DEG, ", "0.0174532925 AS DEG2RAD ",
                    "FROM  DUAL ", ") P ON 1=1 ", "WHERE Z.LATITUD ",
                    "BETWEEN P.LATPOINT  - (P.RADIUS / P.DISTANCE_UNIT) ",
                    "AND P.LATPOINT  + (P.RADIUS / P.DISTANCE_UNIT) ",
                    "AND Z.LONGITUD ",
                    "BETWEEN P.LONGPOINT - (P.RADIUS / (P.DISTANCE_UNIT * COS(DEG2RAD * (P.LATPOINT)))) ",
                    "AND P.LONGPOINT + (P.RADIUS / (P.DISTANCE_UNIT * COS(DEG2RAD * (P.LATPOINT)))) ",
                    "AND P.FILTRO = Z.TIPO_PUNTO ",
                    ") DIS, SHBANCA_DIGITAL.PUNTOS_NEQUI PN ",
                    "WHERE PN.CODIGO_INTERNO = DIS.CODIGO_INTERNO ",
                    "AND DIS.DISTANCE <= DIS.RADIUS AND PN.PAIS_ID = ?5 ORDER BY DIS.DISTANCE");

    public static final String QUERY_SQL_GETPUNTOSNEQUI_SINFILTRO = JPAUtil
            .generateStringConcatenated(
                    "SELECT PN.CODIGO_INTERNO, PN.NOMBRE_ACTUAL, PN.LONGITUD, PN.LATITUD, PN.TIPO_PUNTO, ",
                    "PN.CODIGO_DANE, PN.FORMATO,PN.NOMENCLATURA, PN.TELEFONO,PN.USR_CREACION, ",
                    "PN.FECHA_CREACION, PN.USR_MODIFICACION, PN.FECHA_MODIFICACION ",
                    "FROM ( ", "SELECT Z.CODIGO_INTERNO, ", "P.RADIUS, ",
                    "P.DISTANCE_UNIT ",
                    "* RAD2DEG * (ACOS(COS(DEG2RAD * (P.LATPOINT)) ",
                    "* COS(DEG2RAD * (Z.LATITUD)) ",
                    "* COS(DEG2RAD * (P.LONGPOINT - Z.LONGITUD)) ",
                    "+ SIN(DEG2RAD * (P.LATPOINT)) ",
                    "* SIN(DEG2RAD * (Z.LATITUD)))) AS DISTANCE ",
                    "FROM SHBANCA_DIGITAL.PUNTOS_NEQUI Z ", "JOIN ( ",
                    "SELECT ?1 AS LATPOINT, ?2 AS LONGPOINT, ",
                    "?3 AS RADIUS,        111.045 AS DISTANCE_UNIT, ",
                    "57.2957795 AS RAD2DEG, ", "0.0174532925 AS DEG2RAD ",
                    "FROM  DUAL ", ") P ON 1=1 ", "WHERE Z.LATITUD ",
                    "BETWEEN P.LATPOINT  - (P.RADIUS / P.DISTANCE_UNIT) ",
                    "AND P.LATPOINT  + (P.RADIUS / P.DISTANCE_UNIT) ",
                    "AND Z.LONGITUD ",
                    "BETWEEN P.LONGPOINT - (P.RADIUS / (P.DISTANCE_UNIT * COS(DEG2RAD * (P.LATPOINT)))) ",
                    "AND P.LONGPOINT + (P.RADIUS / (P.DISTANCE_UNIT * COS(DEG2RAD * (P.LATPOINT)))) ",
                    ") DIS, SHBANCA_DIGITAL.PUNTOS_NEQUI PN ",
                    "WHERE PN.CODIGO_INTERNO = DIS.CODIGO_INTERNO ",
                    "AND DIS.DISTANCE <= DIS.RADIUS AND PN.PAIS_ID = ?4 ORDER BY DIS.DISTANCE");

    public static final String NAMED_QUERY_PROMOCION_REGLA_GET_MAX_ID_QUERY = "SELECT MAX(e.promocionReglaId) FROM PromocionRegla e";
    public static final String NAMED_QUERY_PROMOCION_REGLA_FIND_BY_SERVICE_QUERY = "SELECT p FROM PromocionOperacion p WHERE p.servicio = :service and p.paisId = :paisId";
    public static final String COMMON_STRING_SERVICE_PARAMETER = "service";
    public static final String COMMON_STRING_REGION_PARAMETER = "paisId";

    private ConstantJPA() {
    }
}
