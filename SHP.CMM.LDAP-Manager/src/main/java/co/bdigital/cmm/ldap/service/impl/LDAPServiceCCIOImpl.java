/**
 * 
 */
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
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import co.bdigital.cmm.ldap.exception.LDAPException;
import co.bdigital.cmm.ldap.service.LDAPServiceCCIO;
import co.bdigital.cmm.ldap.util.Constant;
import co.bdigital.shl.common.service.pojo.ResourcesPojoLDAP;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

import com.ibm.websphere.security.NotImplementedException;
import com.ibm.wsspi.security.auth.callback.Constants;
import com.ibm.wsspi.security.auth.callback.WSMappingCallbackHandlerFactory;

/**
 * @author hildebrando_rios
 *
 */
public class LDAPServiceCCIOImpl implements LDAPServiceCCIO {

    private String merchantDn;
    private String merchantNodo;
    private String hostName;
    private String adminUser;
    private String adminpassword;
    private CustomLogger logger;

    public LDAPServiceCCIOImpl(ResourcesPojoLDAP locator) throws LDAPException {
        logger = new CustomLogger(LDAPServiceCCIOImpl.class);
        try {
            hostName = locator.getLdapUrl();
            adminUser = locator.getLdapUser();
            adminpassword = locator.getLdapPassword();
            merchantNodo = locator.getLdapNodo();
            merchantDn = locator.getLdapDN();
            List<String> lista = new ArrayList<String>();
            lista = getCredentialsLDAP(merchantNodo);
            adminUser = lista.get(0);
            adminpassword = lista.get(1);
        } catch (Exception e) {
            logger.error(ErrorEnum.LDAP_ERROR, Constant.COMMON_ERROR_LDAP_INIT,
                    e);
            throw new LDAPException(Constant.COMMON_ERROR_LDAP_INIT, e);
        }
    }

    @Override
    public boolean authenticateUserCCIO(String user, String password) {

        DirContext dc = null;
        String dn2 = "uid=" + user + "," + merchantDn;
        StringBuilder localDN = new StringBuilder(Constant.COMMON_STRING_UID);
        localDN.append(user);
        localDN.append(Constant.COMMON_STRING_COMA);
        localDN.append(merchantDn);

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, hostName);
        env.put(Context.SECURITY_AUTHENTICATION,
                Constant.COMMON_STRING_AUTH_TYPE);
        env.put(Context.SECURITY_PRINCIPAL, dn2);
        env.put(Context.SECURITY_CREDENTIALS, password);

        try {
            dc = new InitialDirContext(env);
            return Boolean.TRUE;
        } catch (NamingException e) {
            logger.error(Constant.COMMON_ERROR_AUTH_FAILURE, e);
            return Boolean.FALSE;
        } finally {
            try {
                if (dc != null) {
                    dc.close();
                }
            } catch (NamingException e) {
                logger.error(Constant.COMMON_ERROR_CLOSE_CONTEXT, e);
            }
        }
    }

    @Override
    public String getLastLoginTime(String username) throws LDAPException {
        String dn = Constant.COMMON_STRING_UID + username
                + Constant.COMMON_STRING_COMA + this.merchantDn;
        Attribute attribute = loadPropertyConnection(
                Constant.COMMON_STRING_LASTLOGINTIME, dn);
        if (null == attribute) {
            return null;
        }
        try {
            return attribute.get().toString();
        } catch (NamingException e) {
            logger.error(Constant.COMMON_ERROR_ATTR_LAST_LOGIN, e);
            return null;
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
        env.put(Context.PROVIDER_URL, hostName);
        env.put(Context.SECURITY_AUTHENTICATION,
                Constant.COMMON_STRING_AUTH_TYPE);
        env.put(Context.SECURITY_PRINCIPAL, adminUser);
        env.put(Context.SECURITY_CREDENTIALS, adminpassword);

        try {
            return (DirContext) new InitialDirContext(env);
        } catch (NamingException e) {
            logger.error(Constant.COMMON_ERROR_AUTH_FAILURE, e);
            return null;
        }
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
            logger.error(ErrorEnum.LDAP_ERROR,
                    Constant.COMMON_ERROR_MESSAGE_NO_READ_ATTRIBUTE
                            + attributeValue, e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (NamingException e) {
                    logger.error(ErrorEnum.LDAP_ERROR,
                            Constant.COMMON_ERROR_MESSAGE_NO_READ_ATTRIBUTE
                                    + attributeValue, e);
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

        map.put(Constants.MAPPING_ALIAS, wasNodo);
        CallbackHandler callbackHandler = null;

        callbackHandler = WSMappingCallbackHandlerFactory.getInstance()
                .getCallbackHandler(map, null);

        LoginContext loginContext = null;

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
    }

    @Override
    public String getpwdAccountLockedTime(String username, String password)
            throws LDAPException {
        String dn = Constant.COMMON_STRING_UID + username
                + Constant.COMMON_STRING_COMA + this.merchantDn;
        Attribute attribute = loadPropertyConnection(
                Constant.COMMON_STRING_PWDACCOUNTLOCKEDTIME, dn);
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
        String dn = Constant.COMMON_STRING_UID + userId
                + Constant.COMMON_STRING_COMA + this.merchantDn;
        Attribute attribute = loadPropertyConnection(
                Constant.COMMON_STRING_PWDFAILURETIME, dn);
        try {
            return attribute.size();
        } catch (Exception e) {
            logger.error(Constant.COMMON_ERROR_ATTR_FAILED_ATTEMPTS, e);
            return 0;
        }
    }

    @Override
    public Attribute getArrayHistoricalPassword(String userId)
            throws LDAPException {
        String dn = Constant.COMMON_STRING_UID + userId
                + Constant.COMMON_STRING_COMA + this.merchantDn;
        Attribute attribute = loadPropertyConnection(
                Constant.COMMON_STRING_PWDHISTORY, dn);
        return attribute;
    }

    @Override
    public String getPwdChangedTime(String username) throws LDAPException {
        String dn = Constant.COMMON_STRING_UID + username
                + Constant.COMMON_STRING_COMA + this.merchantDn;
        Attribute attribute = loadPropertyConnection(
                Constant.COMMON_STRING_PWDCHANGEDTIME, dn);
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
    public String getCreateTimestamp(String username) throws LDAPException {
        String dn = Constant.COMMON_STRING_UID + username
                + Constant.COMMON_STRING_COMA + this.merchantDn;
        Attribute attribute = loadPropertyConnection(
                Constant.COMMON_STRING_CREATETIMESTAMP, dn);
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
    public String getPolicyPwdMaxAge() throws LDAPException {
        Attribute attribute = loadPropertyConnection(
                Constant.COMMON_STRING_PWD_MAX_AGE,
                Constant.COMMON_STRING_DN_POLICY);
        if (null == attribute) {
            return null;
        }
        try {
            return attribute.get().toString();
        } catch (NamingException e) {
            logger.error(Constant.COMMON_ERROR_ATTR_LAST_LOGIN, e);
            return null;
        }
    }

}
