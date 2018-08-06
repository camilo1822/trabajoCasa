//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.19 at 03:05:12 PM COT 
//


package co.bdigital.cmm.messaging.general;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestHeaderObjectType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestHeaderObjectType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Channel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RequestDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MessageID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ClientID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Destination" type="{http://www.grupobancolombia.com/BandaDigital/services/General}DestinationType"/>
 *         &lt;element name="Security" type="{http://www.grupobancolombia.com/BandaDigital/services/General}SecurityType" minOccurs="0"/>
 *         &lt;element name="Address" type="{http://www.grupobancolombia.com/BandaDigital/services/General}AddressType" minOccurs="0"/>
 *         &lt;element name="MaintenanceMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaintenanceModeMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BlockingNequiAccessMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BlockingNequiAccessModeMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestHeaderObjectType", propOrder = {
    "channel",
    "requestDate",
    "messageID",
    "clientID",
    "destination",
    "security",
    "address",
    "maintenanceMode",
    "maintenanceModeMessage",
    "blockingNequiAccessMode",
    "blockingNequiAccessModeMessage"
})
@XmlSeeAlso({
    RequestHeader.class
})
public class RequestHeaderObjectType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Channel", required = true)
    protected String channel;
    @XmlElement(name = "RequestDate", required = true)
    protected String requestDate;
    @XmlElement(name = "MessageID", required = true)
    protected String messageID;
    @XmlElement(name = "ClientID")
    protected String clientID;
    @XmlElement(name = "Destination", required = true)
    protected DestinationType destination;
    @XmlElement(name = "Security")
    protected SecurityType security;
    @XmlElement(name = "Address")
    protected AddressType address;
    @XmlElement(name = "MaintenanceMode")
    protected String maintenanceMode;
    @XmlElement(name = "MaintenanceModeMessage")
    protected String maintenanceModeMessage;
    @XmlElement(name = "BlockingNequiAccessMode")
    protected String blockingNequiAccessMode;
    @XmlElement(name = "BlockingNequiAccessModeMessage")
    protected String blockingNequiAccessModeMessage;

    /**
     * Gets the value of the channel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannel(String value) {
        this.channel = value;
    }

    /**
     * Gets the value of the requestDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestDate() {
        return requestDate;
    }

    /**
     * Sets the value of the requestDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestDate(String value) {
        this.requestDate = value;
    }

    /**
     * Gets the value of the messageID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageID() {
        return messageID;
    }

    /**
     * Sets the value of the messageID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageID(String value) {
        this.messageID = value;
    }

    /**
     * Gets the value of the clientID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientID() {
        return clientID;
    }

    /**
     * Sets the value of the clientID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientID(String value) {
        this.clientID = value;
    }

    /**
     * Gets the value of the destination property.
     * 
     * @return
     *     possible object is
     *     {@link DestinationType }
     *     
     */
    public DestinationType getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     * 
     * @param value
     *     allowed object is
     *     {@link DestinationType }
     *     
     */
    public void setDestination(DestinationType value) {
        this.destination = value;
    }

    /**
     * Gets the value of the security property.
     * 
     * @return
     *     possible object is
     *     {@link SecurityType }
     *     
     */
    public SecurityType getSecurity() {
        return security;
    }

    /**
     * Sets the value of the security property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityType }
     *     
     */
    public void setSecurity(SecurityType value) {
        this.security = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setAddress(AddressType value) {
        this.address = value;
    }

    /**
     * Gets the value of the maintenanceMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaintenanceMode() {
        return maintenanceMode;
    }

    /**
     * Sets the value of the maintenanceMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaintenanceMode(String value) {
        this.maintenanceMode = value;
    }

    /**
     * Gets the value of the maintenanceModeMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaintenanceModeMessage() {
        return maintenanceModeMessage;
    }

    /**
     * Sets the value of the maintenanceModeMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaintenanceModeMessage(String value) {
        this.maintenanceModeMessage = value;
    }

    /**
     * Gets the value of the blockingNequiAccessMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlockingNequiAccessMode() {
        return blockingNequiAccessMode;
    }

    /**
     * Sets the value of the blockingNequiAccessMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlockingNequiAccessMode(String value) {
        this.blockingNequiAccessMode = value;
    }

    /**
     * Gets the value of the blockingNequiAccessModeMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlockingNequiAccessModeMessage() {
        return blockingNequiAccessModeMessage;
    }

    /**
     * Sets the value of the blockingNequiAccessModeMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlockingNequiAccessModeMessage(String value) {
        this.blockingNequiAccessModeMessage = value;
    }

}