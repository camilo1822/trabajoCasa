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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CompensatePhoneNumberRQType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CompensatePhoneNumberRQType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="phoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oldPhoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cifId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="phoneEmailID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompensatePhoneNumberRQType", namespace = "http://www.grupobancolombia.com/BandaDigital/services/Backout/CompensatePhoneNumber", propOrder = {
    "phoneNumber",
    "oldPhoneNumber",
    "cifId",
    "phoneEmailID",
    "type"
})
public class CompensatePhoneNumberRQType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String phoneNumber;
    @XmlElement(required = true)
    protected String oldPhoneNumber;
    @XmlElement(required = true)
    protected String cifId;
    @XmlElement(required = true)
    protected String phoneEmailID;
    @XmlElement(required = true)
    protected String type;

    /**
     * Gets the value of the phoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the value of the phoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

    /**
     * Gets the value of the oldPhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldPhoneNumber() {
        return oldPhoneNumber;
    }

    /**
     * Sets the value of the oldPhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldPhoneNumber(String value) {
        this.oldPhoneNumber = value;
    }

    /**
     * Gets the value of the cifId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCifId() {
        return cifId;
    }

    /**
     * Sets the value of the cifId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCifId(String value) {
        this.cifId = value;
    }

    /**
     * Gets the value of the phoneEmailID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneEmailID() {
        return phoneEmailID;
    }

    /**
     * Sets the value of the phoneEmailID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneEmailID(String value) {
        this.phoneEmailID = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
