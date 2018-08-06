package co.bdigital.admin.ldap.util;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.ejb.util.SSHA;
import co.bdigital.cmm.ldap.exception.LDAPException;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

public class LDAPAccess {

    private static final String INITIAL_CONTEXT = "com.sun.jndi.ldap.LdapCtxFactory";

    private static CustomLogger logger;

    /*
     * Variables para normalizar el nombre de los atributos en LDAP
     */
    private static final String ATTRIB_CN = "cn";
    private static final String ATTRIB_SN = "sn";
    private static final String ATTRIB_UID = "uid";
    private static final String ATTRIB_PASSWORD = "userPassword";
    private static final String ATTRIB_MEMBER = "uniqueMember";
    // Atributos generales
    private static final String ATTRIB_PASS_SSHA = "{SSHA}";
    private static final String ATTRIB_OBJECT_CLASEE = "objectClass";
    private static final String ATTRIB_TOP = "top";
    private static final String ATTRIB_INTE_ORG_PERSON = "inetOrgPerson";
    private static final String ATTRIB_ORG_PERSON = "organizationalPerson";
    private static final String ATTRIB_PERSON = "person";
    private static final String ATTRIB_PWD_ACCOUNT_LOCKED = "pwdAccountLockedTime";
    private static final String ATTRIB_LAST_LOGIN_TIME = "x-LastLoginTime";
    private String COMMON_STRING_AUTH_TYPE = "simple";

    private String initCtx;
    private String host;

    private String mgrDN;
    private String mgrPW;
    private String sec_aut;
    private String searchBase;
    private String searchBaseGroups;
    private DirContext ctx = null;

    /**
     * Inicializa las variables necesarias para realizar la conexi&oacute;n con
     * el servidor LDAP
     * 
     * @param host
     *            Nombre o direcci&oacute;n IP del servidor LDAP
     * @param port
     *            Puerto de conexi&oacute;n con LDAP
     * @param mgrDN
     *            DN de usuario administrador
     * @param mgrPW
     *            Password de usuario administrador
     * @param sec_aut
     *            Metodo de autenticaci&oacute;n
     * @param searchBase
     *            Nodo ra&oacute;z desde donde se har&oacute;n las busquedas
     * @param searchBaseGroup
     */
    public LDAPAccess(String host, String port, String mgrDN, String mgrPW,
            String sec_aut, String searchBase, String searchBaseGroup) {
        logger = new CustomLogger(LDAPAccess.class);
        this.initCtx = INITIAL_CONTEXT;
        this.host = host + Constant.COLON + port;
        this.mgrDN = mgrDN;
        this.mgrPW = mgrPW;
        this.sec_aut = sec_aut;
        this.searchBase = searchBase;
        this.searchBaseGroups = searchBaseGroup;
    }

    /**
     * Inicializa las variables necesarias para realizar la conexi&oacute;n con
     * el servidor LDAP
     * 
     * @param url
     *            URL para la conexión al LDAP (host:port).
     * @param mgrDN
     *            DN de usuario administrador
     * @param mgrPW
     *            Password de usuario administrador
     * @param sec_aut
     *            Metodo de autenticaci&oacute;n
     * @param searchBase
     *            Nodo ra&oacute;z desde donde se har&oacute;n las busquedas
     * @param searchBaseGroup
     */
    public LDAPAccess(String url, String mgrDN, String mgrPW, String sec_aut,
            String searchBase, String searchBaseGroup) {
        logger = new CustomLogger(LDAPAccess.class);
        this.initCtx = INITIAL_CONTEXT;
        this.host = url;
        this.mgrDN = mgrDN;
        this.mgrPW = mgrPW;
        this.sec_aut = sec_aut;
        this.searchBase = searchBase;
        this.searchBaseGroups = searchBaseGroup;
    }

    /**
     * Inicializa las variables necesarias para realizar la conexi&oacute;n con
     * el servidor LDAP
     * 
     * @param url
     *            URL para la conexión al LDAP (host:port).
     * @param mgrDN
     *            DN de usuario administrador
     * @param mgrPW
     *            Password de usuario administrador
     * @param sec_aut
     *            Metodo de autenticaci&oacute;n
     * @param searchBase
     *            Nodo ra&oacute;z desde donde se har&oacute;n las busquedas
     * @param searchBaseGroup
     */
    public LDAPAccess(String url, String mgrDN, String mgrPW, String searchBase,
            String searchBaseGroup) {
        logger = new CustomLogger(LDAPAccess.class);
        this.initCtx = INITIAL_CONTEXT;
        this.host = url;
        this.mgrDN = mgrDN;
        this.mgrPW = mgrPW;
        this.sec_aut = co.bdigital.cmm.ldap.util.Constant.COMMON_STRING_AUTH_TYPE;
        this.searchBase = searchBase;
        this.searchBaseGroups = searchBaseGroup;
    }

    /**
     * Establece conexi&oacute;n con el servidor LDAP
     * 
     * @throws javax.naming.NamingException
     * @return Una nueva instancia de la clase DirContext
     */
    private DirContext getInitialDirContext() throws NamingException {
        if (ctx == null) {
            Hashtable<String, String> env = new Hashtable<String, String>();
            // Se especifica la clase a usar para nuestro JNDI provider
            env.put(Context.INITIAL_CONTEXT_FACTORY, initCtx);
            env.put(Context.PROVIDER_URL, host);
            env.put(Context.SECURITY_AUTHENTICATION, sec_aut);
            env.put(Context.SECURITY_PRINCIPAL, mgrDN);
            env.put(Context.SECURITY_CREDENTIALS, mgrPW);
            // Trae una referencia a un directory context
            DirContext ctx = new InitialDirContext(env);
            return ctx;
        } else {
            return this.ctx;
        }
    }

    /**
     * Permite cambiar el password del usuario.
     * 
     * @param userID
     * @param currentPass
     * @param newPass
     * @return Codigo de estado de la operaci&oacute;n.
     */
    public String changePass(String userID, String currentPass,
            String newPass) {
        try {
            ctx = getInitialDirContext();
            ModificationItem[] mods = new ModificationItem[2];

            mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
                    new BasicAttribute(ATTRIB_PASSWORD, currentPass));

            String passEncrypted = SSHA.getInstance().createDigest(newPass);

            mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
                    new BasicAttribute(ATTRIB_PASSWORD, passEncrypted));

            // Perform the update
            ctx.modifyAttributes(ATTRIB_UID + Constant.EQUALS + userID
                    + Constant.COMMA + searchBase, mods);
            ctx.close();

        } catch (NamingException e) {
            logger.error(ErrorEnum.LDAP_ERROR,
                    Constant.ERROR_MESSAGE_LDAP_CHANGE_PWD, e);
            String errorCode = manageLdapException(e);
            return errorCode;
        } finally {
            if (null != ctx) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    logger.error(ErrorEnum.LDAP_ERROR,
                            Constant.ERROR_MESSAGE_LDAP_COMMUNICATION, e);
                }
                ctx = null;
            }
        }
        return Constant.COMMON_STRING_ZERO;
    }

    /**
     * Metodo remover el bloqueo un usuario
     * 
     * @param cifId
     * @return
     * @throws LDAPException
     */
    public boolean unBlockUser(String cifId) throws LDAPException {
        boolean resultado;
        List<ModificationItem> mods = new ArrayList<ModificationItem>();

        // Se solicita el remover el item de bloqueo de la cuenta
        mods.add(new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
                new BasicAttribute(ATTRIB_PWD_ACCOUNT_LOCKED)));
        // Actualizacion de datos en TDS
        resultado = modifyUserAttribute(cifId,
                mods.toArray(new ModificationItem[mods.size()]));
        return resultado;
    }

    /**
     * Maneja las excepciones causadas por errores en asignaci&oacute;n de
     * claves.
     * 
     * @throws LDAPException
     * @return Codigo de error.
     * @param e
     */
    private String manageLdapException(Exception e) {
        String error = e.getMessage();
        for (EnumLDAPErrors ldapError : EnumLDAPErrors.values()) {
            if (error.startsWith(ldapError.getLdapErrorDescription())) {
                return ldapError.getErrorCode();
            }
        }
        return EnumLDAPErrors.INTERNAL_ERROR.getErrorCode();
    }

    /**
     * <p>
     * Metodo que permite crear un usuario en LDAP.
     * </p>
     * 
     * @param uid
     * @param password
     * @return Codigo de estado de la operaci&oacute;n.
     */
    public String createUser(String uid, String password) {

        try {
            ctx = getInitialDirContext();
            String entryDN = ATTRIB_UID + Constant.EQUALS + uid + Constant.COMMA
                    + searchBase;
            ctx.createSubcontext(entryDN, this.buildAtrributes(uid, password));
        } catch (NoSuchAlgorithmException e) {
            // TODO: Se debe validar si ya existe el usaurio.
            logger.error(ErrorEnum.LDAP_ERROR,
                    Constant.ERROR_MESSAGE_LDAP_COMMUNICATION, e);
            return EnumLDAPErrors.USER_GROUP_ERROR.getErrorCode();
        } catch (Exception e) {
            // TODO: Se debe validar si ya existe el usaurio.
            String errorCode = "";
            errorCode = e.getMessage().substring(0, 20);
            if (errorCode.startsWith(
                    EnumLDAPErrors.USER_EXISTS.getLdapErrorDescription())) {
                logger.error(ErrorEnum.LDAP_ERROR,
                        Constant.ERROR_MESSAGE_LDAP_USER_ALREADY_EXISTS, e);
                errorCode = EnumLDAPErrors.USER_EXISTS.getErrorCode();
            } else {
                logger.error(ErrorEnum.LDAP_ERROR,
                        Constant.ERROR_MESSAGE_LDAP_COMMUNICATION, e);
                errorCode = EnumLDAPErrors.INTERNAL_ERROR.getErrorCode();
            }
            return errorCode;
        } finally {
            if (null != ctx) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    logger.error(ErrorEnum.LDAP_ERROR,
                            Constant.ERROR_MESSAGE_LDAP_COMMUNICATION, e);
                }
                ctx = null;
            }
        }
        return Constant.COMMON_STRING_ZERO;
    }

    /**
     * Metodo que permite asociar un usuario a un grupo.
     *
     * @param groupName
     * @param userName
     * @return
     * @throws LDAPException
     */
    public boolean addUsertoGroup(String groupName, String userName)
            throws LDAPException {
        boolean resultado;
        String base = searchBase;
        String baseGroup = searchBaseGroups;
        String userNameCompuest = ATTRIB_UID + Constant.EQUALS + userName
                + Constant.COMMA + base;
        String groupNameCompuest = groupName + Constant.COMMA + baseGroup;
        try {
            ctx = getInitialDirContext();
            // Create a LDAP add attribute for the member attribute
            ModificationItem[] mods = new ModificationItem[1];
            mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
                    new BasicAttribute(ATTRIB_MEMBER, userNameCompuest));
            // update the group
            ctx.modifyAttributes(groupNameCompuest, mods);
            resultado = true;
        } catch (NamingException e) {
            resultado = false;
            String error = "";
            error = e.getMessage().substring(0, 20);
            if (error.startsWith(EnumLDAPErrors.USER_EXISTS_IN_GROUP
                    .getLdapErrorDescription())) {
                error = EnumLDAPErrors.USER_EXISTS_IN_GROUP.getErrorCode();
            } else {
                error = e.getMessage();
            }
            throw new LDAPException(error, e);
        } catch (Exception e) {
            throw new LDAPException(
                    EnumLDAPErrors.USER_GROUP_ERROR.getErrorCode(), e);
        } finally {
            if (null != ctx) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    // TODO: Se debe enviar a Logger
                    e.printStackTrace();
                }
                ctx = null;
            }
        }
        return resultado;
    }

    /**
     * Metodo que permite actualizar los datos de un usuario en el LDAP.
     */
    private boolean modifyUserAttribute(String userID,
            ModificationItem[] modItems) throws LDAPException {
        boolean resultado = true;
        try {
            ctx = getInitialDirContext();
            ctx.modifyAttributes(ATTRIB_UID + Constant.EQUALS + userID
                    + Constant.COMMA + this.searchBase, modItems);
            resultado = true;
        } catch (Exception e) {
            throw new LDAPException(
                    EnumLDAPErrors.MODIFY_ATTRIBUTE.getErrorCode());
        } finally {
            if (null != ctx) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    // TODO: Se debe enviar a Logger
                    e.printStackTrace();
                }
                ctx = null;
            }
        }
        return resultado;
    }

    /**
     * Metodo para bloquear un usuario
     * 
     * @param uid
     * @return
     * @throws LDAPException
     */
    public boolean blockUser(String uid) throws LDAPException {
        boolean resultado;
        if (uid == null) {
            throw new LDAPException(EnumLDAPErrors.USER_NULL.getErrorCode());
        }

        List<ModificationItem> mods = new ArrayList<ModificationItem>();

        // Bloqueo
        TimeZone timeZone = TimeZone
                .getTimeZone(Constant.COMMON_FORMAT_TIME_ZONE_FROM_LDAP);
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                Constant.COMMON_FORMAT_DATE_FROM_LDAP);
        mods.add(new ModificationItem(DirContext.ADD_ATTRIBUTE,
                new BasicAttribute(ATTRIB_PWD_ACCOUNT_LOCKED,
                        simpleDateFormat.format(calendar.getTime()))));
        // Actualizacion de datos en TDS
        resultado = modifyUserAttribute(uid,
                mods.toArray(new ModificationItem[mods.size()]));
        return resultado;
    }

    /**
     * Metodo paracambiar la informaci&oacute;n de Login
     * 
     * @param uid
     *            Informaci&oacute;n del usuario
     * @return
     * @throws LDAPException
     */
    public boolean updateInfoLogin(String uid) throws LDAPException {
        boolean resultado;
        if (uid == null) {
            throw new LDAPException(EnumLDAPErrors.USER_NULL.getErrorCode());
        }

        List<ModificationItem> mods = new ArrayList<ModificationItem>();
        // Cambio de incio Sesi&oacute;n
        TimeZone timeZone = TimeZone
                .getTimeZone(Constant.COMMON_FORMAT_TIME_ZONE_FROM_LDAP);
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                Constant.COMMON_FORMAT_DATE_FROM_LDAP);
        mods.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                new BasicAttribute(ATTRIB_LAST_LOGIN_TIME,
                        simpleDateFormat.format(calendar.getTime()))));
        // Actualizacion de datos en LDAP
        resultado = modifyUserAttribute(uid,
                mods.toArray(new ModificationItem[mods.size()]));
        return resultado;
    }

    /**
     * Metodo para cambiar una lista de atributos
     * 
     * @param uid
     *            UID que identifica al usuario en LDAP.
     * @param atributos
     *            Mapa con los atributos y los valores de cada atributo.
     * @return
     * @throws LDAPException
     */
    public boolean updateAttributes(String uid, Map<String, String> atributos)
            throws LDAPException {
        boolean resultado;
        if (uid == null) {
            throw new LDAPException(EnumLDAPErrors.USER_NULL.getErrorCode());
        }

        List<ModificationItem> mods = new ArrayList<ModificationItem>();

        for (Entry<String, String> atributo : atributos.entrySet()) {
            mods.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                    new BasicAttribute(atributo.getKey(),
                            atributo.getValue())));
        }
        // Actualizacion de datos en LDAP
        resultado = modifyUserAttribute(uid,
                mods.toArray(new ModificationItem[mods.size()]));
        return resultado;
    }

    /**
     * Metodo para crear los atributos necesarios para la creaci&oacute;n de un
     * usaurio.
     * 
     * @param usuario
     *            Informaci&oacute;n del usuario
     * @return Todos los atributos necesarios para la creaci&oacute;n del
     *         usuario.
     * @throws NoSuchAlgorithmException
     */
    private Attributes buildAtrributes(String uid, String password)
            throws NoSuchAlgorithmException {
        SSHA.getInstance().setAlgorithm("SHA");
        String passEncrypted = SSHA.getInstance().createDigest(password, true);

        StringBuilder pass = new StringBuilder();
        pass.append(LDAPAccess.ATTRIB_PASS_SSHA);
        pass.append(passEncrypted);

        // adici&oacute;n de nuevo usuario
        Attribute userCn = new BasicAttribute(ATTRIB_CN, uid);
        Attribute userSn = new BasicAttribute(ATTRIB_SN, uid);
        Attribute userUid = new BasicAttribute(ATTRIB_UID, uid);
        Attribute userPassword = new BasicAttribute(ATTRIB_PASSWORD,
                pass.toString());
        Attribute oc = new BasicAttribute(ATTRIB_OBJECT_CLASEE);
        oc.add(ATTRIB_TOP);
        oc.add(ATTRIB_INTE_ORG_PERSON);
        oc.add(ATTRIB_ORG_PERSON);
        oc.add(ATTRIB_PERSON);

        Attributes entry = new BasicAttributes(true);
        entry.put(userCn);
        entry.put(userSn);
        entry.put(userUid);
        entry.put(userPassword);

        entry.put(oc);
        return entry;
    }

    public boolean authenticateUserLDAP(String userID, String passwd)
            throws NamingException {

        DirContext dcLoc = null;
        String dn2 = ATTRIB_UID + Constant.EQUALS + userID + Constant.COMMA
                + searchBase;
        Hashtable<String, String> env = new Hashtable<String, String>();

        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, host);
        env.put(Context.SECURITY_AUTHENTICATION, COMMON_STRING_AUTH_TYPE);
        env.put(Context.SECURITY_PRINCIPAL, dn2);
        env.put(Context.SECURITY_CREDENTIALS, passwd);

        try {
            dcLoc = new InitialDirContext(env);
            return Boolean.TRUE;
        } catch (NamingException e) {
            return Boolean.FALSE;

        } finally {
            if (dcLoc != null) {
                dcLoc.close();
            }
        }
    }

}
