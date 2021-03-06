//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.01.31 at 05:21:36 PM COT 
//


package co.bdigital.admin.messaging.services.getcustomerdetails;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.bdigital.admin.messaging.services.getcustomerdetails package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetCustomerStatusRequest_QNAME = new QName("http://www.grupobancolombia.com/BandaDigital/services/ResetPasswordServices", "GetCustomerStatusRequest");
    private final static QName _GetCustomerStatusResponse_QNAME = new QName("http://www.grupobancolombia.com/BandaDigital/services/ResetPasswordServices", "GetCustomerStatusResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.bdigital.admin.messaging.services.getcustomerdetails
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCustomerStatusRequestType }
     * 
     */
    public GetCustomerStatusRequestType createGetCustomerStatusRequestType() {
        return new GetCustomerStatusRequestType();
    }

    /**
     * Create an instance of {@link GetCustomerStatusResponseType }
     * 
     */
    public GetCustomerStatusResponseType createGetCustomerStatusResponseType() {
        return new GetCustomerStatusResponseType();
    }

    /**
     * Create an instance of {@link GetCustomerStatusRSType }
     * 
     */
    public GetCustomerStatusRSType createGetCustomerStatusRSType() {
        return new GetCustomerStatusRSType();
    }

    /**
     * Create an instance of {@link CustomerDetailsReport }
     * 
     */
    public CustomerDetailsReport createCustomerDetailsReport() {
        return new CustomerDetailsReport();
    }

    /**
     * Create an instance of {@link GetCustomerStatusRQType }
     * 
     */
    public GetCustomerStatusRQType createGetCustomerStatusRQType() {
        return new GetCustomerStatusRQType();
    }

    /**
     * Create an instance of {@link CustomerDetailsReportType }
     * 
     */
    public CustomerDetailsReportType createCustomerDetailsReportType() {
        return new CustomerDetailsReportType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerStatusRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.grupobancolombia.com/BandaDigital/services/ResetPasswordServices", name = "GetCustomerStatusRequest")
    public JAXBElement<GetCustomerStatusRequestType> createGetCustomerStatusRequest(GetCustomerStatusRequestType value) {
        return new JAXBElement<GetCustomerStatusRequestType>(_GetCustomerStatusRequest_QNAME, GetCustomerStatusRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerStatusResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.grupobancolombia.com/BandaDigital/services/ResetPasswordServices", name = "GetCustomerStatusResponse")
    public JAXBElement<GetCustomerStatusResponseType> createGetCustomerStatusResponse(GetCustomerStatusResponseType value) {
        return new JAXBElement<GetCustomerStatusResponseType>(_GetCustomerStatusResponse_QNAME, GetCustomerStatusResponseType.class, null, value);
    }

}
