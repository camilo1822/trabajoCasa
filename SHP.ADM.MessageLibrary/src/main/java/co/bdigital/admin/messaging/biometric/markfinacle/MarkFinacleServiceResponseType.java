//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.03 at 05:31:01 PM COT 
//


package co.bdigital.admin.messaging.biometric.markfinacle;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MarkFinacleServiceResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MarkFinacleServiceResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="markFinacleRS" type="{http://www.grupobancolombia.com/BandaDigital/services/MarkFinacle}MarkFinacleRSType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MarkFinacleServiceResponseType", propOrder = {
    "markFinacleRS"
})
public class MarkFinacleServiceResponseType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected MarkFinacleRSType markFinacleRS;

    /**
     * Gets the value of the markFinacleRS property.
     * 
     * @return
     *     possible object is
     *     {@link MarkFinacleRSType }
     *     
     */
    public MarkFinacleRSType getMarkFinacleRS() {
        return markFinacleRS;
    }

    /**
     * Sets the value of the markFinacleRS property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarkFinacleRSType }
     *     
     */
    public void setMarkFinacleRS(MarkFinacleRSType value) {
        this.markFinacleRS = value;
    }

}
