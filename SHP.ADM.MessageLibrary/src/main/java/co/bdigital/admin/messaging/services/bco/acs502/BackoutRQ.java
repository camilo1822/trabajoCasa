//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.07.06 at 07:42:41 AM COT 
//


package co.bdigital.admin.messaging.services.bco.acs502;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://nequi.co/message/backout/services/}backoutRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "backoutRequest"
})
@XmlRootElement(name = "backoutRQ")
public class BackoutRQ
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected BackoutRequestType backoutRequest;

    /**
     * Gets the value of the backoutRequest property.
     * 
     * @return
     *     possible object is
     *     {@link BackoutRequestType }
     *     
     */
    public BackoutRequestType getBackoutRequest() {
        return backoutRequest;
    }

    /**
     * Sets the value of the backoutRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link BackoutRequestType }
     *     
     */
    public void setBackoutRequest(BackoutRequestType value) {
        this.backoutRequest = value;
    }

}
