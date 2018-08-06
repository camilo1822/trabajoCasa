package co.bdigital.cmm.ldap.util;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public class Constant {
    // LDAP
    public static final String COMMON_STRING_ASTERISK_SYMBOL = "*";
    public static final String COMMON_STRING_PLUS_CHARACTER = "+";
    
    // PARAMETERS
    public static final String COMMON_STRING_COMA = ",";
    public static final String COMMON_STRING_UID = "uid=";
    public static final String COMMON_STRING_PWD_MAX_AGE = "pwdMaxAge";
    public static final String COMMON_STRING_AUTH_TYPE = "simple";
    public static final String COMMON_STRING_PWDHISTORY = "pwdHistory";
    public static final String COMMON_STRING_PWDCHANGEDTIME = "pwdChangedTime";
    public static final String COMMON_STRING_PWDFAILURETIME = "pwdFailureTime";
    public static final String COMMON_STRING_LASTLOGINTIME = "x-LastLoginTime";
    public static final String COMMON_STRING_CREATETIMESTAMP = "createTimestamp";
    public static final String COMMON_STRING_PWDACCOUNTLOCKEDTIME = "pwdAccountLockedTime";
    public static final String COMMON_STRING_DN_POLICY = "cn=passwordDefault,ou=Policies,dc=bancadigital,dc=com,dc=co";
    // MGE ERROR
    public static final String COMMON_ERROR_AUTH_FAILURE = "ERROR DE AUTENTICACIÓN";
    public static final String COMMON_ERROR_CLOSE_CONTEXT = "Error Cerrando InitialContext";
    public static final String COMMON_ERROR_LDAP_INIT = "Error de Inicialización LDAP";
    public static final String COMMON_ERROR_ATTR_BLOQUEO = "Error Leyendo Atributo de Bloqueos";
    public static final String COMMON_ERROR_MESSAGE_NO_READ_ATTRIBUTE = "Error Leyendo Atributo: ";
    public static final String COMMON_ERROR_ATTR_FAILED_ATTEMPTS = "Error Leyendo intentos fallidos";
    public static final String COMMON_ERROR_ATTR_LAST_LOGIN = "Error Leyendo Atributo de última fecha de login";
    public static final String COMMON_ERROR_MESSAGE_LDAP_CHANGE_PWD = "Error al cambiar password";
    public static final String COMMON_ERROR_CLOSE_DIRECTORY_CONTEXT = "Error Cerrando Directory Context";
    public static final String COMMON_ERROR_CONSTRUCTION_FAILED = "Construction failed: ";

    // MSG DEBUG
    public static final String COMMON_MSG_PWHASH_HEX = "pwhash, binary represented as hex: ";
    public static final String COMMON_MSG_N = " n";
    public static final String COMMON_MSG_ALL_TOGETHER = "Putting it all together: ";
    public static final String COMMON_MSG_PWHASH_AND_SALT = "binary digest of password plus binary salt: ";
    public static final String COMMON_MSG_PWHASH_AND_SALT_BASE64 = "Now we base64 encode what is respresented above this line ...";
    public static final String COMMON_MSG_SSHA = "{SSHA}";
    
    public static final String ATTRIB_PASSWORD = "userPassword";

    public static final String LDAP_INFO = "[LDAP-INFO]: ";

    private Constant() {

    }
}
