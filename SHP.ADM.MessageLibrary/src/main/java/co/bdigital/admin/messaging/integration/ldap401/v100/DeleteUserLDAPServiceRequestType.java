//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.11 at 09:58:50 AM COT 
//


package co.bdigital.admin.messaging.integration.ldap401.v100;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeleteUserLDAPServiceRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeleteUserLDAPServiceRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="deleteUserLDAPRQ" type="{http://nequi.co/message/integration/services/DeleteUserLDAPServices}DeleteUserLDAPRQType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeleteUserLDAPServiceRequestType", namespace = "http://nequi.co/message/integration/services/DeleteUserLDAPServices", propOrder = {
    "deleteUserLDAPRQ"
})
public class DeleteUserLDAPServiceRequestType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected DeleteUserLDAPRQType deleteUserLDAPRQ;

    /**
     * Gets the value of the deleteUserLDAPRQ property.
     * 
     * @return
     *     possible object is
     *     {@link DeleteUserLDAPRQType }
     *     
     */
    public DeleteUserLDAPRQType getDeleteUserLDAPRQ() {
        return deleteUserLDAPRQ;
    }

    /**
     * Sets the value of the deleteUserLDAPRQ property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeleteUserLDAPRQType }
     *     
     */
    public void setDeleteUserLDAPRQ(DeleteUserLDAPRQType value) {
        this.deleteUserLDAPRQ = value;
    }

}
