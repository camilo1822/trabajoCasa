//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.07 at 06:27:23 PM COT 
//

package co.bdigital.cmm.messaging.esb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for RequestHeaderOutMessageType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="RequestHeaderOutMessageType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="requestHeaderOut" type="{http://www.grupobancolombia.com/BandaDigital/services/SHP_MSG_ESB}requestHeaderOutType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestHeaderOutMessageType", propOrder = { "requestHeaderOut" })
public class RequestHeaderOutMessageType implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8472512629870188891L;

    @XmlElement(required = true)
    protected RequestHeaderOutType requestHeaderOut;

    /**
     * Gets the value of the requestHeaderOut property.
     * 
     * @return possible object is {@link RequestHeaderOutType }
     * 
     */
    public RequestHeaderOutType getRequestHeaderOut() {
        return requestHeaderOut;
    }

    /**
     * Sets the value of the requestHeaderOut property.
     * 
     * @param value
     *            allowed object is {@link RequestHeaderOutType }
     * 
     */
    public void setRequestHeaderOut(RequestHeaderOutType value) {
        this.requestHeaderOut = value;
    }

}
