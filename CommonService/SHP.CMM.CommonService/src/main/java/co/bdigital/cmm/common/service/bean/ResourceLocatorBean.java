package co.bdigital.cmm.common.service.bean;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import co.bdigital.cmm.common.service.constant.ConstantCommonService;
import co.bdigital.cmm.common.service.util.CommonServiceUtil;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.framework.ServiceOperation;
import co.bdigital.cmm.jpa.model.framework.ServiceVersion;
import co.bdigital.cmm.jpa.model.framework.WsProvider;
import co.bdigital.cmm.jpa.service.impl.LdapJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.TimeOutBrokerJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.UrlBrokerJPAServiceIMPL;
import co.bdigital.cmm.jpa.services.LdapJPAService;
import co.bdigital.cmm.jpa.services.TimeOutBrokerJPAService;
import co.bdigital.cmm.jpa.services.UrlBrokerJPAService;
import co.bdigital.shl.common.service.bean.view.ResourceLocator;
import co.bdigital.shl.common.service.exception.CommonServiceSHLException;
import co.bdigital.shl.common.service.pojo.ResourcesPojo;
import co.bdigital.shl.common.service.pojo.ResourcesPojoLDAP;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * Session Bean implementation class ResourceLocatorBean
 */
@Singleton(mappedName = "ResourceLocatorBean")
@Remote(ResourceLocator.class)
@LocalBean
public class ResourceLocatorBean implements ResourceLocator {

    private static final String LDAP_DN = "LDAP_DN";
    private static final String LDAP_NODE = "LDAP_NODE";
    private static final String LDAP_HOST = "LDAP_HOST";
    private static final String LDAP_DN_PA = "LDAP_DN_PA";
    private static final String LDAP_NODE_PA = "LDAP_NODE_PA";
    private static final String AGREEMENT_URL = "AGREEMENT_URL";
    private static final String LDAP_DN_POLICY = "LDAP_DN_POLICY";
    private static final String LDAP_DN_POLICY_PA = "LDAP_DN_POLICY_PA";

    private static final String LDAP_DN_MERCHANT = "LDAP_DN_MERCHANT";
    private static final String LDAP_DN_ADMIN_WEB = "LDAP_DN_ADMIN_WEB";
    private static final String MERCHANT_NODE = "MERCHANT_NODE";

    private static final String LDAP_DN_MERCHANT_PA = "LDAP_DN_MERCHANT_PA";
    private static final String MERCHANT_NODE_PA = "MERCHANT_NODE_PA";

    private EntityManagerFactory emf;
    private EntityManager em;

    // Instancias de Pojo
    private ResourcesPojo pojo;
    private ResourcesPojoLDAP resourcesPojoLDAPCCIOCO;
    private ResourcesPojoLDAP resourcesPojoLDAPCCIOPA;
    private ResourcesPojoLDAP resourcesPojoLDAPCO;
    private ResourcesPojoLDAP resourcesPojoLDAPPA;
    private ResourcesPojoLDAP resourcesPojoLDAPAdminWeb;

    // Datos del broker
    private UrlBrokerJPAService brokerJPAService;
    // Datos del timeOut
    private TimeOutBrokerJPAService timeOutBrokerJPAService;
    // Datos de LDAP
    private LdapJPAService ldapJPAService;

    private CustomLogger logger;
    private HashMap<String, String> cacheMap;
    private String ldapDN;
    private String ldapNode;
    private String ldapDNPA;
    private String ldapNodePA;
    private String ldapUrl;
    private String ldapDNPolicy;
    private String ldapDNPolicyPA;
    private String ldapDNMerchant;
    private String ldapDNAdminWeb;
    private String ldapNodeMerchant;
    private String ldapDNMerchantPA;
    private String ldapNodeMerchantPA;
    private String ldapPassword;
    private String ldapUser;

    @PostConstruct
    void init() {

        logger = new CustomLogger(ResourceLocatorBean.class);
        cacheMap = new HashMap<String, String>();
        brokerJPAService = new UrlBrokerJPAServiceIMPL();
        ldapJPAService = new LdapJPAServiceIMPL();
        timeOutBrokerJPAService = new TimeOutBrokerJPAServiceIMPL();

        try {
            generateValues();
        } catch (CommonServiceSHLException e) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    ConstantCommonService.ERROR_MESSAGE_SINGLETON, e);
        }

    }

    /**
     * Método que genera los valores para alimentar el Pojo de acceso pÃºblico
     * 
     * @throws CommonServiceSHLException
     */
    private void generateValues() throws CommonServiceSHLException {

        WsProvider providerBroker;
        WsProvider providerLdap;

        try {

            emf = Persistence.createEntityManagerFactory(
                    ConstantCommonService.PERSISTENCE_UNIT_FRM_MANAGER);

            em = emf.createEntityManager();

            if (em != null && em.isOpen()) {

                providerBroker = this.getWsProviderInfo(em);

                providerLdap = this.ldapJPAService.getLdapData(em);

                // Ldap Data
                this.ldapUrl = System.getProperty(LDAP_HOST);
                this.ldapDNPolicy = System.getProperty(LDAP_DN_POLICY);
                this.ldapDNPolicyPA = System.getProperty(LDAP_DN_POLICY_PA);
                this.ldapDNMerchant = System.getProperty(LDAP_DN_MERCHANT);
                this.ldapDNAdminWeb = System.getProperty(LDAP_DN_ADMIN_WEB);
                this.ldapNodeMerchant = System.getProperty(MERCHANT_NODE);
                this.ldapDNMerchantPA = System.getProperty(LDAP_DN_MERCHANT_PA);
                this.ldapNodeMerchantPA = System.getProperty(MERCHANT_NODE_PA);
                this.ldapDN = System.getProperty(LDAP_DN);
                this.ldapNode = System.getProperty(LDAP_NODE);
                this.ldapDNPA = System.getProperty(LDAP_DN_PA);
                this.ldapNodePA = System.getProperty(LDAP_NODE_PA);
                this.ldapPassword = providerLdap.getAuthBasicPwd();
                this.ldapUser = providerLdap.getAuthBasicUser();

                String brokerUrl = providerBroker.getWsHost().replace("\n", "")
                        + ":" + providerBroker.getWsPort()
                        + providerBroker.getWsContext();

                String agreementUrl = System.getProperty(AGREEMENT_URL);

                int connectionTimeout = providerBroker.getWsConnectionTimeout()
                        .intValue();

                // Miliseconds
                connectionTimeout = connectionTimeout
                        * ConstantCommonService.CONVERTO_TO_MILISECONDS_1000;

                pojo = new ResourcesPojo(String.valueOf(connectionTimeout),
                        brokerUrl, agreementUrl);
            }

        } catch (JPAException e) {
            logger.error(ErrorEnum.DB_ERROR,
                    ConstantCommonService.ERROR_MESSAGE_DB_QUERY, e);
            throw new CommonServiceSHLException(
                    ConstantCommonService.ERROR_BD_MESSAGE, e);
        } catch (Exception e) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    ConstantCommonService.COMMON_STRING_ERRORMESSAGE, e);
            throw new CommonServiceSHLException(
                    ConstantCommonService.ERROR_WAS_GENERAL, e);
        } finally {
            if (null != em && em.isOpen()) {
                em.close();
            }
            if ((null != emf) && (emf.isOpen())) {
                emf.close();
            }
        }

    }

    /**
     * Método que busca la Url del broker en la BD y retorna el resultado
     * 
     * @return
     * @throws JPAException
     */
    public WsProvider getBrokerEndpoint() throws JPAException {

        WsProvider provider = null;

        try {

            emf = Persistence.createEntityManagerFactory(
                    ConstantCommonService.PERSISTENCE_UNIT_FRM_MANAGER);
            em = emf.createEntityManager();

            if (em != null && em.isOpen()) {

                provider = this.getWsProviderInfo(em);
            }

        } catch (Exception e) {

            throw new JPAException(ConstantCommonService.ERROR_BD_MESSAGE, e);

        } finally {

            if (null != em && em.isOpen()) {

                em.close();
            }
            if ((null != emf) && (emf.isOpen())) {
                emf.close();
            }
        }

        return provider;

    }

    /**
     * Método público que retorna el Pojo una vez inicializado
     * 
     * @return
     */
    @Override
    public ResourcesPojo getPojo() {
        return this.pojo;
    }

    /**
     * Método put del HashMap
     * 
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        synchronized (cacheMap) {
            cacheMap.put(key, value);
        }
    }

    /**
     * Metodo para consulta de timeOut
     * 
     * @deprecated: Not for public use. This method is expected to be retained
     *              only as a package private method. Replaced by
     *              {@link #getBrokerTimeOut(String,String)}
     * 
     * @param key
     * @return
     * @throws JPAException
     */
    @Deprecated
    public String get(String key) throws JPAException {
        synchronized (cacheMap) {
            String mapResult = (String) cacheMap.get(key);

            if (null == mapResult) {
                emf = Persistence.createEntityManagerFactory(
                        ConstantCommonService.PERSISTENCE_UNIT_FRM_MANAGER);
                em = emf.createEntityManager();
                if (null != em && em.isOpen()) {

                    ServiceVersion jpaResult = null;
                    try {
                        jpaResult = timeOutBrokerJPAService.getTimeOut(em, key);
                    } catch (Exception e) {
                        throw new JPAException(
                                ConstantCommonService.ERROR_BD_MESSAGE, e);
                    } finally {
                        if (null != em && em.isOpen()) {
                            em.close();
                        }
                        if ((null != emf) && (emf.isOpen())) {
                            emf.close();
                        }
                    }
                    put(key, jpaResult.getTimeout().toString());

                    return jpaResult.getTimeout().toString();
                }
                return null;
            } else {

                return mapResult;
            }
        }
    }

    /**
     * Metodo para consultar timeOut de las operaciones de Broker
     * 
     * @param name
     * @param operation
     * @return
     * @throws JPAException
     */
    private String getBrokerTimeOut(String name, String operation)
            throws JPAException {

        try {
            StringBuilder key = new StringBuilder(name);
            key.append(ConstantCommonService.COMMON_STRING_VERTICAL_BAR);
            key.append(operation);

            synchronized (cacheMap) {

                String timeOut = cacheMap.get(key.toString());

                if (null == timeOut) {

                    emf = Persistence.createEntityManagerFactory(
                            ConstantCommonService.PERSISTENCE_UNIT_FRM_MANAGER);
                    em = emf.createEntityManager();

                    if (null != em && em.isOpen()) {

                        ServiceOperation serviceOperation = timeOutBrokerJPAService
                                .getOperationTimeOut(em, name, operation);

                        put(key.toString(), timeOut = serviceOperation
                                .getTimeoutOp().toString());

                    }
                }
                return timeOut;
            }

        } catch (Exception e) {
            throw new JPAException(ConstantCommonService.ERROR_BD_MESSAGE, e);
        } finally {
            if (null != em && (em.isOpen())) {
                em.close();
            }
            if ((null != emf) && (emf.isOpen())) {
                emf.close();
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see co.bdigital.shl.common.service.bean.view.ResourceLocator#
     * getConnectionData(java.lang.String)
     */
    @Override
    @Deprecated
    public ResourcesPojo getConnectionData(String namespace) {
        String timeOut = ConstantCommonService.DEFAULT_TIMEOUT;
        if (!namespace.equalsIgnoreCase(ConstantCommonService.STRING_ZERO)) {
            try {
                timeOut = get(namespace);
            } catch (JPAException e) {
                logger.error(ErrorEnum.DB_ERROR,
                        ConstantCommonService.ERROR_MESSAGE_DB_QUERY, e);
            }

            // TimeOut Miliseconds
            int timeOutint = ((Integer.parseInt(timeOut)
                    + Integer.parseInt(ConstantCommonService.STRING_TWO))
                    * ConstantCommonService.CONVERTO_TO_MILISECONDS_1000);

            pojo.setBrokerReadTimeOut(String.valueOf(timeOutint));
        }
        return pojo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.bdigital.shl.common.service.bean.view.ResourceLocator#
     * getConnectionData(java.lang.String, java.lang.String)
     */
    @Override
    public ResourcesPojo getConnectionData(String name, String operation) {

        String timeOut = ConstantCommonService.DEFAULT_TIMEOUT_BROKER;
        try {
            if (null != name && null != operation) {
                timeOut = getBrokerTimeOut(name, operation);
            }
        } catch (Exception e) {
            logger.error(ErrorEnum.DB_ERROR,
                    ConstantCommonService.ERROR_MESSAGE_DB_QUERY, e);
        }
        // TimeOut Miliseconds
        int timeOutint = ((Integer.parseInt(timeOut)
                + Integer.parseInt(ConstantCommonService.STRING_TWO))
                * ConstantCommonService.CONVERTO_TO_MILISECONDS_1000);

        pojo.setBrokerReadTimeOut(String.valueOf(timeOutint));

        return pojo;
    }

    /**
     * Metodo que instancia el Pojo LDAP dependiendo del pais. por defecto sera
     * Colombia en caso de que no encuentre el pais, si la region es nula,
     * retorna el Pojo para Comercio o Merchant.
     * 
     * 
     * @return
     */
    @Override
    public ResourcesPojoLDAP getResourcesPojoLdap(String serviceRegion) {

        if (ConstantCommonService.SERVICE_REGION_PA
                .equalsIgnoreCase(serviceRegion)) {

            if (resourcesPojoLDAPPA == null) {
                // Pojo LDAP Panama
                resourcesPojoLDAPPA = new ResourcesPojoLDAP(ldapUrl, ldapUser,
                        ldapPassword, ldapNodePA, ldapDNPA, ldapDNPolicyPA);
            }

            return resourcesPojoLDAPPA;

        } else {
            if (resourcesPojoLDAPCO == null) {
                // Pojo LDAP por defecto Colombia
                resourcesPojoLDAPCO = new ResourcesPojoLDAP(ldapUrl, ldapUser,
                        ldapPassword, ldapNode, ldapDN, ldapDNPolicy);

            }

            return resourcesPojoLDAPCO;
        }

    }

    /**
     * Metodo que instancia el Pojo LDAP (Merchant) dependiendo del pais. por
     * defecto sera Colombia en caso de que no encuentre el pais
     * 
     * 
     * @return
     */
    @Override
    public ResourcesPojoLDAP getResourcesPojoLdapCCIO(String serviceRegion) {

        if (ConstantCommonService.SERVICE_REGION_PA
                .equalsIgnoreCase(serviceRegion)) {

            if (resourcesPojoLDAPCCIOPA == null) {
                resourcesPojoLDAPCCIOPA = new ResourcesPojoLDAP(ldapUrl,
                        ldapUser, ldapPassword, ldapNodeMerchantPA,
                        ldapDNMerchantPA, ldapDNPolicyPA);
            }
            return resourcesPojoLDAPCCIOPA;

        } else {
            if (resourcesPojoLDAPCCIOCO == null) {
                resourcesPojoLDAPCCIOCO = new ResourcesPojoLDAP(ldapUrl,
                        ldapUser, ldapPassword, ldapNodeMerchant,
                        ldapDNMerchant, ldapDNPolicy);
            }
            return resourcesPojoLDAPCCIOCO;
        }

    }

    /**
     * <p>
     * Metodo que instancia el Pojo LDAP (Web Administrativa) dependiendo del
     * pais. por defecto sera Colombia en caso de que no encuentre el pais.
     * </p>
     * <p>
     * <strong>NOTA:</strong> La configuraci&oacute;n del &aacute;rbol LDAP de
     * la Web Administrativa s&oacute;lo aplica para Colombia.
     * </p>
     * 
     * 
     * @return ResourcesPojoLDAP
     */
    @Override
    public ResourcesPojoLDAP getResourcesPojoLdapAdminWeb() {

        if (null == this.resourcesPojoLDAPAdminWeb) {
            // Pojo LDAP Colombia
            this.resourcesPojoLDAPAdminWeb = new ResourcesPojoLDAP(this.ldapUrl,
                    null, null, this.ldapNode, this.ldapDNAdminWeb,
                    this.ldapDNPolicy);
        }

        return this.resourcesPojoLDAPAdminWeb;

    }

    /**
     * Metodo que obtiene un entity <code>WsProvider</code> del host de Broker,
     * valida si debe consultar el host de alta disponibilidad o el host por
     * omision.
     * 
     * @return <code>WsProvider</code>
     * @throws JPAException
     */
    public WsProvider getWsProviderInfo(EntityManager entityManager)
            throws JPAException {

        String wsProviderWsServiceNameString;
        WsProvider providerBroker;

        wsProviderWsServiceNameString = System
                .getProperty(ConstantCommonService.COMMON_STRING_IIB);

        this.logger.info(
                CommonServiceUtil.generateString(ConstantCommonService.OPEN,
                        ConstantCommonService.COMMON_STRING_IIB_HOST
                                + ConstantCommonService.COLON
                                + wsProviderWsServiceNameString
                                + ConstantCommonService.CLOSE));

        if (null == wsProviderWsServiceNameString) {

            wsProviderWsServiceNameString = ConstantCommonService.COMMON_STRING_IIB;
        }

        providerBroker = this.brokerJPAService.getWsProviderInfo(
                wsProviderWsServiceNameString, entityManager);

        this.logger.info(
                CommonServiceUtil.generateString(ConstantCommonService.OPEN
                        + ConstantCommonService.COMMON_STRING_IIB_HOST
                        + ConstantCommonService.COLON
                        + providerBroker.getWsServiceName()
                        + ConstantCommonService.COLON
                        + providerBroker.getWsHost()
                        + ConstantCommonService.CLOSE));

        return providerBroker;

    }

}
