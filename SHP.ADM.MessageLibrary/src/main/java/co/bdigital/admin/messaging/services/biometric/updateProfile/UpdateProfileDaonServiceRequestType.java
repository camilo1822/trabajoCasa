//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.04.10 at 10:09:33 AM COT 
//


package co.bdigital.admin.messaging.services.biometric.updateProfile;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateProfileDaonServiceRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateProfileDaonServiceRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="updateProfileDaonRQ" type="{http://www.grupobancolombia.com/BandaDigital/services/UpdateProfile}UpdateProfileDaonRQType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateProfileDaonServiceRequestType", propOrder = {
    "updateProfileDaonRQ"
})
public class UpdateProfileDaonServiceRequestType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected UpdateProfileDaonRQType updateProfileDaonRQ;

    /**
     * Gets the value of the updateProfileDaonRQ property.
     * 
     * @return
     *     possible object is
     *     {@link UpdateProfileDaonRQType }
     *     
     */
    public UpdateProfileDaonRQType getUpdateProfileDaonRQ() {
        return updateProfileDaonRQ;
    }

    /**
     * Sets the value of the updateProfileDaonRQ property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpdateProfileDaonRQType }
     *     
     */
    public void setUpdateProfileDaonRQ(UpdateProfileDaonRQType value) {
        this.updateProfileDaonRQ = value;
    }

}
