//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.06 at 10:09:55 AM COT 
//


package co.bdigital.admin.messaging.services.customerLockManagment;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerLockManagmentServiceRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerLockManagmentServiceRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customerLockManagmentRQ" type="{http://www.grupobancolombia.com/BandaDigital/services/UserServices}CustomerLockManagmentRQType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerLockManagmentServiceRequestType", propOrder = {
    "customerLockManagmentRQ"
})
public class CustomerLockManagmentServiceRequestType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected CustomerLockManagmentRQType customerLockManagmentRQ;

    /**
     * Gets the value of the customerLockManagmentRQ property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerLockManagmentRQType }
     *     
     */
    public CustomerLockManagmentRQType getCustomerLockManagmentRQ() {
        return customerLockManagmentRQ;
    }

    /**
     * Sets the value of the customerLockManagmentRQ property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerLockManagmentRQType }
     *     
     */
    public void setCustomerLockManagmentRQ(CustomerLockManagmentRQType value) {
        this.customerLockManagmentRQ = value;
    }

}