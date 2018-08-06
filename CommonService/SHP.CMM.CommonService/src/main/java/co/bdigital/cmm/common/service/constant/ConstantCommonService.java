package co.bdigital.cmm.common.service.constant;

/**
 * La clase Constant Maneja las constantes del proyecto Security Manager.
 * 
 * @author cristian.martinez
 */
public class ConstantCommonService {

    // Constantes para utilidades del TraceManager
    public static final String CLOSE = "]";
    public static final String OPEN = "[";

    // PARAMETROS GENERALES
    public static final String COMMON_STRING_ERRORMESSAGE = "errorMessage";
    public static final String COLON = ":";

    // MENSAJES DE ERROR MDW
    public static final String ERROR_MESSAGE_DB_QUERY = "Error Consultando BD";

    // MENSAJE PARA EL POST CONSTRUCT - RESOURCELOCATOR
    public static final String ERROR_MESSAGE_SINGLETON = "Ocurrió un error en el postConstruct del singleton (ResourceLocator)";
    public static final String ERROR_WAS_MESSAGE = "Ocurrió un error obteniendo recursos del WAS";
    public static final String ERROR_BD_MESSAGE = "Ocurrió un error obteniendo recursos desde BD";
    public static final String ERROR_WAS_GENERAL = "Ocurrió un error obteniendo recursos desde BD/WAS";
    public static final String DEFAULT_TIMEOUT = "450";
    public static final String DEFAULT_TIMEOUT_BROKER = "30";
    public static final String STRING_ZERO = "0";
    public static final String STRING_TWO = "2";
    public static final Integer CONVERTO_TO_MILISECONDS_1000 = 1000;

    // Region
    public static final String SERVICE_REGION_PA = "P001";
    public static final String SERVICE_REGION_CO = "C001";
    public static final String COMMON_STRING_EMPTY = "";
    public static final String COMMON_STRING_VERTICAL_BAR = "|";

    // Host de BROKER
    public static final String COMMON_STRING_IIB = "IIB";
    public static final String COMMON_STRING_IIB_HOST = "IIB HOST";

    // Unidad de persistencia
    public static final String PERSISTENCE_UNIT_FRM_MANAGER = "FRMManager";

    public static final String ERROR_PARSING = "Error al Parsear el JSON:";
    public static final String COMMON_STRING_LEFT_KEY = "{";
    public static final String COMMON_STRING_RIGHT_KEY = "}";
    public static final String COMMON_STRING_LEFT_SQUARE = "[";
    public static final String COMMON_STRING_RIGHT_SQUARE = "]";

    public static final int COMMON_INT_ZERO = 0;
    public static final int COMMON_INT_ONE = 1;
    public static final int COMMON_INT_TWO = 2;
    public static final int COMMON_INT_THREE = 3;

    public static final char COMMON_CHAR_LEFT_SQUARE = '[';

    public static final String[] COMMON_ARRAY_GENERAL_INFO = { "direccion",
            "telefono", "email", "alerta", "indicadorMovilidad", "celular" };

    private ConstantCommonService() {
    }

}
