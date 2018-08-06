package co.bdigital.admin.ejb.controller;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.bdigital.admin.ejb.controller.view.ControlListValidationServiceBeanLocal;
import co.bdigital.admin.messaging.validatecustomer.CustomerType;
import co.bdigital.admin.messaging.validatecustomer.ValidateCustomerListsBrokerRQType;
import co.bdigital.admin.messaging.validatecustomer.ValidateCustomerListsServiceBrokerRequestType;
import co.bdigital.cmm.consumer.exception.RestClientException;
import co.bdigital.cmm.consumer.service.impl.RestClientImpl;
import co.bdigital.cmm.ejb.generic.ServiceControllerHelper;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.ejb.util.ContextUtilHelper;
import co.bdigital.cmm.messaging.esb.BodyType;
import co.bdigital.cmm.messaging.esb.RequestHeaderOutMessageType;
import co.bdigital.cmm.messaging.esb.RequestHeaderOutType;
import co.bdigital.cmm.messaging.esb.ResponseHeaderOutMessageType;
import co.bdigital.shl.common.service.bean.view.ResourceLocator;
import co.bdigital.shl.common.service.pojo.ResourcesPojo;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;

/**
 * Session Bean implementation class ControlListValidationServiceBean
 * 
 * @author eduardo.altamar@pragma.com.co
 * @since 09/11/2016
 * @version 1.0
 */
@Stateless
@Local(ControlListValidationServiceBeanLocal.class)
public class ControlListValidationServiceBean
        implements ControlListValidationServiceBeanLocal {

    private CustomLogger logger;

    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;
    private ServiceControllerHelper sch;
    private ResourcesPojo resourcesPojo;
    private ResourceLocator resourceLocator;
    private ContextUtilHelper contextUtilHelper;

    @PostConstruct
    void init() {
        logger = new CustomLogger(ControlListValidationServiceBean.class);
        sch = ServiceControllerHelper.getInstance();
        contextUtilHelper = ContextUtilHelper.getInstance();

    }

    /**
     * Metodo para validar un cliente en las listas de control
     * 
     * @param customerList
     * @param region
     * @return <code> ArrayList<CustomerType> </code>
     * 
     */
    public ArrayList<CustomerType> validateCustomerList(
            ArrayList<CustomerType> customerList, String region) {

        if (null != customerList && !customerList.isEmpty()) {

            for (CustomerType customerType : customerList) {

                ResponseHeaderOutMessageType responseValidateCustomer = validateCustomer(
                        customerType, region);

                customerType.setStatusCode(responseValidateCustomer
                        .getResponseHeaderOut().getHeader().getResponseStatus()
                        .getStatusCode());

                customerType.setDescription(responseValidateCustomer
                        .getResponseHeaderOut().getHeader().getResponseStatus()
                        .getStatusDesc());

            }

        }

        return customerList;

    }

    /**
     * Metodo de Broker que valida en listas de control
     * 
     * @param customer
     * @param region
     * @return <code>ResponseHeaderOutMessageType</code>
     */
    private ResponseHeaderOutMessageType validateCustomer(CustomerType customer,
            String region) {

        ValidateCustomerListsServiceBrokerRequestType validateCustomerListsServiceBrokerRequestType = new ValidateCustomerListsServiceBrokerRequestType();
        ValidateCustomerListsBrokerRQType validateCustomerListsBrokerRQType = new ValidateCustomerListsBrokerRQType();
        validateCustomerListsBrokerRQType.setIdNumber(customer.getIdNumber());
        validateCustomerListsBrokerRQType.setTypeId(customer.getTypeId());
        validateCustomerListsBrokerRQType.setName1(customer.getNames());
        validateCustomerListsBrokerRQType.setLastName1(customer.getLastNames());
        validateCustomerListsServiceBrokerRequestType
                .setValidateCustomerListsBrokerRQ(
                        validateCustomerListsBrokerRQType);

        ResponseHeaderOutMessageType responseFront = createBrokerRequest(
                validateCustomerListsServiceBrokerRequestType,
                Constant.NAME_CUSTOMER_VALIDATION,
                Constant.NAMESPACE_SECURITY_SERVICES_CUSTOMER_VALIDATION,
                Constant.OPERATION_VALIDATE_CUSTOMER_LISTS,
                Constant.REQUEST_VALIDATE_CUSTOMER_LISTS_RQ,
                Constant.RESPONSE_VALIDATE_CUSTOMER_LISTS_RS, region);

        return responseFront;

    }

    /**
     * Metodo para llamado a Broker
     * 
     * @param requestBroker
     * @param name
     * @param namespace
     * @param operation
     * @param requestName
     * @param responseName
     * @param region
     * @return <code>ResponseHeaderOutMessageType</code>
     */
    private ResponseHeaderOutMessageType createBrokerRequest(
            Object requestBroker, String name, String namespace,
            String operation, String requestName, String responseName,
            String region) {

        // Se configura del Header ESB
        RequestHeaderOutMessageType requestHeaderOutMessageType = new RequestHeaderOutMessageType();
        RequestHeaderOutType requestHeaderOutType = new RequestHeaderOutType();

        requestHeaderOutType.setHeader(sch.configureRequestESBHeader(
                Constant.COMMON_STRING_ADM_CHANNEL_ID, null,
                String.valueOf(new Date().getTime()), name, namespace,
                operation));

        // Configuramos la operación:
        requestHeaderOutType.getHeader().setMessageContext(
                sch.configureMessageContext(requestName, responseName));

        // Configuramos las propiedades de internacionalizacion:
        this.sch.addPropertyInternationalizationMessageContext(
                requestHeaderOutType.getHeader().getMessageContext(),
                (null != region ? region : Constant.SERVICE_REGION_CO));

        // Se Configura el Body
        BodyType bodyType = new BodyType();
        bodyType.setAny(requestBroker);
        requestHeaderOutType.setBody(bodyType);
        // Se configura el RequestESB Completo
        requestHeaderOutMessageType.setRequestHeaderOut(requestHeaderOutType);
        // ************************************************************************************
        // ****************************** Envío de Mensaje al IIB
        // ****************************
        ResponseHeaderOutMessageType responseHeaderOutMessageType = null;
        try {

            if (null == resourcesPojo) {
                this.resourceLocator = contextUtilHelper
                        .instanceResourceLocatorRemote();
                resourcesPojo = this.resourceLocator
                        .getConnectionData(namespace);
            }

            responseHeaderOutMessageType = RestClientImpl
                    .getInstance(resourcesPojo)
                    .executeOperation(requestHeaderOutMessageType);

        } catch (RestClientException e) {
            logger.error(ErrorEnum.BROKER_ERROR,
                    Constant.ERROR_MESSAGE_IIB_COMMUNICATION, e);

        }

        return responseHeaderOutMessageType;
    }

}