package co.bdigital.cmm.ldap.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import co.bdigital.cmm.ldap.exception.LDAPException;
import co.bdigital.cmm.ldap.service.LDAPService;
import co.bdigital.cmm.ldap.util.Constant;
import co.bdigital.cmm.ldap.util.LDAPCommonUtil;
import co.bdigital.cmm.ldap.util.SSHA;
import co.bdigital.shl.common.service.pojo.ResourcesPojoLDAP;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

import com.ibm.websphere.security.NotImplementedException;
import com.ibm.wsspi.security.auth.callback.Constants;
import com.ibm.wsspi.security.auth.callback.WSMappingCallbackHandlerFactory;

/**
 * clase que implementa la Interface de servcios sobre LDAP
 * 
 * @author ricardo.paredes
 * @since 19/12/2015
 * @version 1.0
 *
 */
public class LDAPServiceImpl implements LDAPService {

    private String dnPolicy;
    private String hostName;
    private String dn;
    private String adminUserDn;
    private String adminpassword;
    private String wasNodo;
    private CustomLogger logger;

    // constantes

    /**
     * @throws LDAPException
     * 
     */
    public LDAPServiceImpl(ResourcesPojoLDAP locator) throws LDAPException {
        this.logger = new CustomLogger(LDAPServiceImpl.class);
        try {
            this.hostName = locator.getLdapUrl();
            this.wasNodo = locator.getLdapNodo();
            this.dn = locator.getLdapDN();
            List<String> lista = new ArrayList<String>();
            lista = getCredentialsLDAP(this.wasNodo);
            this.adminUserDn = lista.get(0);
            this.adminpassword = lista.get(1);
            this.dnPolicy = locator.getLdapDNPolicy();
        } catch (Exception e) {
            this.logger.error(ErrorEnum.LDAP_ERROR,
                    Constant.COMMON_ERROR_LDAP_INIT, e);
            throw new LDAPException(Constant.COMMON_ERROR_LDAP_INIT, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.bdigital.mdw.ldap.service.LDAPService#authenticateUserLDAP(java.lang
     * .String, java.lang.String)
     */
    @Override
    public boolean authenticateUserLDAP(String userID, String passwd) {

        DirContext dcLoc = null;
        String dn2 = Constant.COMMON_STRING_UID + userID
                + Constant.COMMON_STRING_COMA + this.dn;
        Hashtable<String, String> env = new Hashtable<String, String>();

        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, hostName);
        env.put(Context.SECURITY_AUTHENTICATION,
                Constant.COMMON_STRING_AUTH_TYPE);
        env.put(Context.SECURITY_PRINCIPAL, dn2);
        env.put(Context.SECURITY_CREDENTIALS, passwd);

        try {
            dcLoc = new InitialDirContext(env);
            return Boolean.TRUE;
        } catch (NamingException e) {
            this.logger.error(Constant.COMMON_ERROR_AUTH_FAILURE, e);
            return Boolean.FALSE;
        } finally {
            try {
                if (null != dcLoc) {
                    dcLoc.close();
                }
            } catch (NamingException e) {
                this.logger.error(
                        Constant.COMMON_ERROR_CLOSE_DIRECTORY_CONTEXT, e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.bdigital.mdw.ldap.service.LDAPService#authenticateUserLDAP(java.lang
     * .String, java.lang.String)
     */

    public DirContext authenticateUserLDAPAdmin() {

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, this.hostName);
        env.put(Context.SECURITY_AUTHENTICATION,
                Constant.COMMON_STRING_AUTH_TYPE);
        env.put(Context.SECURITY_PRINCIPAL, this.adminUserDn);
        env.put(Context.SECURITY_CREDENTIALS, this.adminpassword);

        try {
            return new InitialDirContext(env);
        } catch (NamingException e) {
            this.logger.error(Constant.COMMON_ERROR_AUTH_FAILURE, e);
            return null;
        }
    }

    /**
     * Retorna las credenciales del LDAP obteniendolas de el WAS
     * 
     * @return
     * @throws NotImplementedException
     * @throws LoginException
     * @throws MdwCommonException
     */
    private List<String> getCredentialsLDAP(String wasNodo)
            throws NotImplementedException, LoginException {
        Map<String, String> map = new HashMap<String, String>();
        LoginContext loginContext = null;

        try {
            map.put(Constants.MAPPING_ALIAS, wasNodo);
            CallbackHandler callbackHandler = null;

            callbackHandler = WSMappingCallbackHandlerFactory.getInstance()
                    .getCallbackHandler(map, null);

            loginContext = new LoginContext("DefaultPrincipalMapping",
                    callbackHandler);

            loginContext.login();

            Subject subject = loginContext.getSubject();
            Set<Object> credentials = subject.getPrivateCredentials();

            PasswordCredential passwordCredential = (PasswordCredential) credentials
                    .iterator().next();

            String user = passwordCredential.getUserName();
            String password = new String(passwordCredential.getPassword());
            List<String> lista = new ArrayList<String>();
            lista.add(user);
            lista.add(password);
            return lista;
        } finally {
            if (null != loginContext) {
                loginContext.logout();
            }
        }
    }

    @Override
    public String getpwdAccountLockedTime(String username, String password)
            throws LDAPException {
        String dnLoc = Constant.COMMON_STRING_UID + username
                + Constant.COMMON_STRING_COMA + this.dn;
        Attribute attribute = loadPropertyConnection(
                Constant.COMMON_STRING_PWDACCOUNTLOCKEDTIME, dnLoc);
        if (null == attribute) {
            return null;
        }
        try {
            return attribute.get().toString();
        } catch (NamingException e) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    Constant.COMMON_ERROR_ATTR_BLOQUEO, e);
            return null;
        }
    }

    @Override
    public int getUserFailedLoginAttempts(String userId) throws LDAPException {
        String dnLoc = Constant.COMMON_STRING_UID + userId
                + Constant.COMMON_STRING_COMA + this.dn;
        Attribute attribute = this.loadPropertyConnection(
                Constant.COMMON_STRING_PWDFAILURETIME, dnLoc);
        try {
            return attribute.size();
        } catch (Exception e) {
            this.logger.error(Constant.COMMON_ERROR_ATTR_FAILED_ATTEMPTS, e);
            return 0;
        }
    }

    /**
     * Metodo que devuelve el historico de passwords del ldap
     * 
     * @param userId
     * @return Attribute
     * @throws LDAPException
     */
    @Override
    public Attribute getArrayHistoricalPassword(String userId)
            throws LDAPException {
        String dnLoc = Constant.COMMON_STRING_UID + userId
                + Constant.COMMON_STRING_COMA + this.dn;
        return this.loadPropertyConnection(Constant.COMMON_STRING_PWDHISTORY,
                dnLoc);
    }

    /**
     * Retorna el Atributo de la conexion con LDAP actual
     * 
     * @param atributo
     *            Nombre del Atributo que se desea obtener
     * @return Attribute La informacion correspondiente
     */
    private Attribute loadPropertyConnection(String attributeValue,
            String dnUser) {
        Attribute property = null;
        Attributes attrs = null;
        DirContext in = null;
        try {
            in = authenticateUserLDAPAdmin();
            
            if (null != in) {

                attrs = in
                        .getAttributes(dnUser,
                                new String[] {
                                        Constant.COMMON_STRING_ASTERISK_SYMBOL,
                                        Constant.COMMON_STRING_PLUS_CHARACTER });
            }

        } catch (NamingException e) {
            this.logger.error(ErrorEnum.LDAP_ERROR,
                    Constant.COMMON_ERROR_MESSAGE_NO_READ_ATTRIBUTE
                            + attributeValue, e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (NamingException e) {
                    this.logger.error(ErrorEnum.LDAP_ERROR,
                            Constant.COMMON_ERROR_CLOSE_DIRECTORY_CONTEXT, e);
                }
            }
        }
        if (attrs == null) {
            property = null;
        } else {
            property = attrs.get(attributeValue);
        }

        return property;
    }

    @Override
    public String getLastLoginTime(String username) throws LDAPException {
        String dnLoc = Constant.COMMON_STRING_UID + username
                + Constant.COMMON_STRING_COMA + this.dn;
        Attribute attribute = loadPropertyConnection(
                Constant.COMMON_STRING_LASTLOGINTIME, dnLoc);
        if (null == attribute) {
            return null;
        }
        try {
            return attribute.get().toString();
        } catch (NamingException e) {
            this.logger.error(Constant.COMMON_ERROR_ATTR_LAST_LOGIN, e);
            return null;
        }
    }

    /**
     * Metodo que consulta el tiempo de expiracion de un password de la politica
     * del LDAP.
     * 
     * @return String
     * @throws LDAPException
     */
    @Override
    public String getPolicyPwdMaxAge() throws LDAPException {
        Attribute attribute = loadPropertyConnection(
                Constant.COMMON_STRING_PWD_MAX_AGE, this.dnPolicy);
        if (null == attribute) {
            return null;
        }
        try {
            return attribute.get().toString();
        } catch (NamingException e) {
            this.logger.error(Constant.COMMON_ERROR_ATTR_LAST_LOGIN, e);
            return null;
        }
    }

    /**
     * Metodo que consulta la fecha de cambio de password.
     * 
     * @param userId
     * @return String
     * @throws LDAPException
     */
    @Override
    public String getPwdChangedTime(String username) throws LDAPException {
        String dnLoc = Constant.COMMON_STRING_UID + username
                + Constant.COMMON_STRING_COMA + this.dn;
        Attribute attribute = loadPropertyConnection(
                Constant.COMMON_STRING_PWDCHANGEDTIME, dnLoc);
        if (null == attribute) {
            return null;
        }
        try {
            return attribute.get().toString();
        } catch (NamingException e) {
            this.logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    Constant.COMMON_ERROR_ATTR_BLOQUEO, e);
            return null;
        }
    }

    @Override
    public String getCreateTimestamp(String username) throws LDAPException {
        String dnLoc = Constant.COMMON_STRING_UID + username
                + Constant.COMMON_STRING_COMA + this.dn;
        Attribute attribute = loadPropertyConnection(
                Constant.COMMON_STRING_CREATETIMESTAMP, dnLoc);
        if (null == attribute) {
            return null;
        }
        try {
            return attribute.get().toString();
        } catch (NamingException e) {
            this.logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    Constant.COMMON_ERROR_ATTR_BLOQUEO, e);
            return null;
        }
    }

    /**
     * <p>
     * Permite cambiar el password del usuario.
     * </p>
     * 
     * @param userID
     * @param currentPass
     *            clave actual que puede estar plana.
     * @param newPass
     *            nueva clave que ya debe estar encriptada.
     * @return Codigo de estado de la operaci&oacute;n.
     * @throws LDAPException
     */
    public void changePass(String userID, String currentPass, String newPass)
            throws LDAPException {
        DirContext ctx = null;
        try {
            ctx = this.getUserLDAPContext(userID, currentPass);

            if (null == ctx) {
                LDAPException ldapException = new LDAPException(
                        Constant.COMMON_ERROR_AUTH_FAILURE);
                this.logger.error(ErrorEnum.LDAP_ERROR,
                        Constant.COMMON_ERROR_AUTH_FAILURE, ldapException);
                throw ldapException;
            }

            ModificationItem[] mods = new ModificationItem[2];

            mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
                    new BasicAttribute(Constant.ATTRIB_PASSWORD, currentPass));

            String passEncrypted = SSHA.getInstance().createDigest(newPass);

            mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
                    new BasicAttribute(Constant.ATTRIB_PASSWORD, passEncrypted));

            // Perform the update
            ctx.modifyAttributes(Constant.COMMON_STRING_UID + userID
                    + Constant.COMMON_STRING_COMA + this.dn, mods);

        } catch (NamingException e) {
            this.logger.error(ErrorEnum.LDAP_ERROR,
                    Constant.COMMON_ERROR_MESSAGE_LDAP_CHANGE_PWD, e);
            throw new LDAPException(
                    Constant.COMMON_ERROR_MESSAGE_LDAP_CHANGE_PWD, e);
        } finally {
            this.closeContext(ctx);
        }
    }

    /**
     * <p>
     * Utilidad para crear un contexto que tenga la conexi&oacute;n al LDAP.
     * </p>
     * 
     * @param userID
     *            identificador del usuario para hacer la conexi&oacute;n.
     * @param password
     *            contrase&ntilde;a para el userID
     * @return DirContext contexto con la conexi&oacute;n del usuario al LDAP.
     */
    private DirContext getUserLDAPContext(String userID, String password) {

        this.logger.debug(LDAPCommonUtil.buildString(Constant.LDAP_INFO,
                Constant.COMMON_STRING_UID, userID));

        DirContext dcLoc = null;
        String dn2 = Constant.COMMON_STRING_UID + userID
                + Constant.COMMON_STRING_COMA + this.dn;
        Hashtable<String, String> env = new Hashtable<String, String>();

        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, hostName);
        env.put(Context.SECURITY_AUTHENTICATION,
                Constant.COMMON_STRING_AUTH_TYPE);
        env.put(Context.SECURITY_PRINCIPAL, dn2);
        env.put(Context.SECURITY_CREDENTIALS, password);

        try {
            dcLoc = new InitialDirContext(env);

        } catch (NamingException e) {
            this.logger.error(Constant.COMMON_ERROR_AUTH_FAILURE, e);
        }
        return dcLoc;
    }

    /**
     * <p>
     * M&eacute;todo que cierra el contexto de directorio.
     * </p>
     * 
     * @param ctx
     *            Contexto a cerrar.
     * @throws NamingException
     */
    private void closeContext(DirContext ctx) throws LDAPException {
        try {
            if (null != ctx) {
                ctx.close();
            }
        } catch (NamingException e) {
            throw new LDAPException(
                    Constant.COMMON_ERROR_CLOSE_DIRECTORY_CONTEXT, e);
        }
    }
}
