//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.01.30 at 10:32:42 AM COT 
//


package co.bdigital.admin.messaging.services.isb.slack;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SendSlackNotificationServiceResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SendSlackNotificationServiceResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sendSlackNotificationRS" type="{http://www.grupobancolombia.com/BancaDigital/services/SendSlackNotification}SendSlackNotificationRSType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SendSlackNotificationServiceResponseType", propOrder = {
    "sendSlackNotificationRS"
})
public class SendSlackNotificationServiceResponseType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected SendSlackNotificationRSType sendSlackNotificationRS;

    /**
     * Gets the value of the sendSlackNotificationRS property.
     * 
     * @return
     *     possible object is
     *     {@link SendSlackNotificationRSType }
     *     
     */
    public SendSlackNotificationRSType getSendSlackNotificationRS() {
        return sendSlackNotificationRS;
    }

    /**
     * Sets the value of the sendSlackNotificationRS property.
     * 
     * @param value
     *     allowed object is
     *     {@link SendSlackNotificationRSType }
     *     
     */
    public void setSendSlackNotificationRS(SendSlackNotificationRSType value) {
        this.sendSlackNotificationRS = value;
    }

}
