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
 * Java class for responseHeaderOutType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="responseHeaderOutType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Header" type="{http://www.grupobancolombia.com/BandaDigital/services/SHP_MSG_ESB}HeaderResponseType"/&gt;
 *         &lt;element name="Body" type="{http://www.grupobancolombia.com/BandaDigital/services/SHP_MSG_ESB}BodyType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responseHeaderOutType", propOrder = { "header", "body" })
public class ResponseHeaderOutType implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1796045782237345897L;

    @XmlElement(name = "Header", required = true)
    protected HeaderResponseType header;
    @XmlElement(name = "Body", required = true)
    protected BodyType body;

    /**
     * Gets the value of the header property.
     * 
     * @return possible object is {@link HeaderResponseType }
     * 
     */
    public HeaderResponseType getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *            allowed object is {@link HeaderResponseType }
     * 
     */
    public void setHeader(HeaderResponseType value) {
        this.header = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return possible object is {@link BodyType }
     * 
     */
    public BodyType getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *            allowed object is {@link BodyType }
     * 
     */
    public void setBody(BodyType value) {
        this.body = value;
    }

}
