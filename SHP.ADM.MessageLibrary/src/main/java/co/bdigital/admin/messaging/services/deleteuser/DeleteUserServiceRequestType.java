//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.01 at 04:58:29 PM COT 
//


package co.bdigital.admin.messaging.services.deleteuser;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeleteUserServiceRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeleteUserServiceRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="deleteUserRQ" type="{http://www.grupobancolombia.com/BandaDigital/services/UserManagementServices}DeleteUserRQType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeleteUserServiceRequestType", propOrder = {
    "deleteUserRQ"
})
public class DeleteUserServiceRequestType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected DeleteUserRQType deleteUserRQ;

    /**
     * Gets the value of the deleteUserRQ property.
     * 
     * @return
     *     possible object is
     *     {@link DeleteUserRQType }
     *     
     */
    public DeleteUserRQType getDeleteUserRQ() {
        return deleteUserRQ;
    }

    /**
     * Sets the value of the deleteUserRQ property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeleteUserRQType }
     *     
     */
    public void setDeleteUserRQ(DeleteUserRQType value) {
        this.deleteUserRQ = value;
    }

}
