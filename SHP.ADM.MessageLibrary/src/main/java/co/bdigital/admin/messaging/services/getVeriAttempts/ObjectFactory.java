//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.01 at 01:39:54 PM COT 
//


package co.bdigital.admin.messaging.services.getVeriAttempts;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.bdigital.admin.messaging.services.getVeriAttempts package. 
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

    private final static QName _GetVeriAttemptsServiceRequest_QNAME = new QName("http://www.grupobancolombia.com/BandaDigital/services/GetVeriAttemptsServices", "GetVeriAttemptsServiceRequest");
    private final static QName _GetVeriAttemptsServiceResponse_QNAME = new QName("http://www.grupobancolombia.com/BandaDigital/services/GetVeriAttemptsServices", "GetVeriAttemptsServiceResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.bdigital.admin.messaging.services.getVeriAttempts
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetVeriAttemptsServiceResponseType }
     * 
     */
    public GetVeriAttemptsServiceResponseType createGetVeriAttemptsServiceResponseType() {
        return new GetVeriAttemptsServiceResponseType();
    }

    /**
     * Create an instance of {@link GetVeriAttemptsServiceRequestType }
     * 
     */
    public GetVeriAttemptsServiceRequestType createGetVeriAttemptsServiceRequestType() {
        return new GetVeriAttemptsServiceRequestType();
    }

    /**
     * Create an instance of {@link GetVeriAttemptsRQType }
     * 
     */
    public GetVeriAttemptsRQType createGetVeriAttemptsRQType() {
        return new GetVeriAttemptsRQType();
    }

    /**
     * Create an instance of {@link AttemptsType }
     * 
     */
    public AttemptsType createAttemptsType() {
        return new AttemptsType();
    }

    /**
     * Create an instance of {@link GetVeriAttemptsRSType }
     * 
     */
    public GetVeriAttemptsRSType createGetVeriAttemptsRSType() {
        return new GetVeriAttemptsRSType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVeriAttemptsServiceRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.grupobancolombia.com/BandaDigital/services/GetVeriAttemptsServices", name = "GetVeriAttemptsServiceRequest")
    public JAXBElement<GetVeriAttemptsServiceRequestType> createGetVeriAttemptsServiceRequest(GetVeriAttemptsServiceRequestType value) {
        return new JAXBElement<GetVeriAttemptsServiceRequestType>(_GetVeriAttemptsServiceRequest_QNAME, GetVeriAttemptsServiceRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVeriAttemptsServiceResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.grupobancolombia.com/BandaDigital/services/GetVeriAttemptsServices", name = "GetVeriAttemptsServiceResponse")
    public JAXBElement<GetVeriAttemptsServiceResponseType> createGetVeriAttemptsServiceResponse(GetVeriAttemptsServiceResponseType value) {
        return new JAXBElement<GetVeriAttemptsServiceResponseType>(_GetVeriAttemptsServiceResponse_QNAME, GetVeriAttemptsServiceResponseType.class, null, value);
    }

}