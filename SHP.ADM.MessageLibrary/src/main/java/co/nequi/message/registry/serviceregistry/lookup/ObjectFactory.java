//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.09 at 11:33:41 AM COT 
//


package co.nequi.message.registry.serviceregistry.lookup;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.nequi.message.registry.serviceregistry.lookup package. 
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

    private final static QName _LookupResponse_QNAME = new QName("http://nequi.co/message/registry/ServiceRegistry/lookup", "lookupResponse");
    private final static QName _LookupRequest_QNAME = new QName("http://nequi.co/message/registry/ServiceRegistry/lookup", "lookupRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.nequi.message.registry.serviceregistry.lookup
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LookupResponseType }
     * 
     */
    public LookupResponseType createLookupResponseType() {
        return new LookupResponseType();
    }

    /**
     * Create an instance of {@link LookupRequestType }
     * 
     */
    public LookupRequestType createLookupRequestType() {
        return new LookupRequestType();
    }

    /**
     * Create an instance of {@link TimeoutType }
     * 
     */
    public TimeoutType createTimeoutType() {
        return new TimeoutType();
    }

    /**
     * Create an instance of {@link EndpointType }
     * 
     */
    public EndpointType createEndpointType() {
        return new EndpointType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LookupResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nequi.co/message/registry/ServiceRegistry/lookup", name = "lookupResponse")
    public JAXBElement<LookupResponseType> createLookupResponse(LookupResponseType value) {
        return new JAXBElement<LookupResponseType>(_LookupResponse_QNAME, LookupResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LookupRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nequi.co/message/registry/ServiceRegistry/lookup", name = "lookupRequest")
    public JAXBElement<LookupRequestType> createLookupRequest(LookupRequestType value) {
        return new JAXBElement<LookupRequestType>(_LookupRequest_QNAME, LookupRequestType.class, null, value);
    }

}