package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the PSE_TRANSACCION database table.
 * 
 */
@Entity
@Table(name = "PSE_TRANSACCION", schema = "SHBANCA_DIGITAL")
@Cacheable(false)
public class PseTransaccion implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PseTransaccionPK id;

    @Column(name = "ENTITY_CODE")
    private String entityCode;

    @Column(name = "FINANCIAL_INSTITUTION_CODE")
    private String financialInstitutionCode;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "TRANSACTION_VALUE")
    private String transactionValue;

    @Column(name = "TRANSACTION_CURRENCY_ISO_CODE")
    private String transactionCurrencyIsoCode;

    @Column(name = "VAT_VALUE")
    private String vatValue;

    @Column(name = "VAT_CURRENCY_ISO_CODE")
    private String vatCurrencyIsoCode;

    @Column(name = "PAYMENT_DESCRIPTION")
    private String paymentDescription;

    @Column(name = "SERVICE_CODE")
    private String serviceCode;

    @Column(name = "USER_TYPE")
    private String userType;

    @Column(name = "REFERENCE_NUMBER1")
    private String referenceNumber1;

    @Column(name = "REFERENCE_NUMBER2")
    private String referenceNumber2;

    @Column(name = "REFERENCE_NUMBER3")
    private String referenceNumber3;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "SOLICITE_DATE")
    private Date soliciteDate;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    @Column(name = "TRANSACTION_CYCLE")
    private String transactionCycle;

    @Column(name = "FWD_URL")
    private String fwdUrl;

    @Column(name = "FINACLE_TRANSACCION_ID")
    private String finacleTransactionId;

    @Column(name = "ENTITY_NAME")
    private String entityName;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @Temporal(TemporalType.DATE)
    @Column(name = "BANK_PROCESS_DATE")
    private Date bankProcessDate;

    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID")
    private Cliente clienteId;

    @ManyToOne
    @JoinColumn(name = "TT_ID")
    private TipoTransaccionPse ttId;

    @ManyToOne
    @JoinColumn(name = "ET_ID")
    private EstadoTransaccionPse etId;

    public PseTransaccion() {
    }

    /**
     * @return the id
     */
    public PseTransaccionPK getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(PseTransaccionPK id) {
        this.id = id;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getFinancialInstitutionCode() {
        return financialInstitutionCode;
    }

    public void setFinancialInstitutionCode(String financialInstitutionCode) {
        this.financialInstitutionCode = financialInstitutionCode;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(String transactionValue) {
        this.transactionValue = transactionValue;
    }

    public String getTransactionCurrencyIsoCode() {
        return transactionCurrencyIsoCode;
    }

    public void setTransactionCurrencyIsoCode(String transactionCurrencyIsoCode) {
        this.transactionCurrencyIsoCode = transactionCurrencyIsoCode;
    }

    public String getVatValue() {
        return vatValue;
    }

    public void setVatValue(String vatValue) {
        this.vatValue = vatValue;
    }

    public String getVatCurrencyIsoCode() {
        return vatCurrencyIsoCode;
    }

    public void setVatCurrencyIsoCode(String vatCurrencyIsoCode) {
        this.vatCurrencyIsoCode = vatCurrencyIsoCode;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getReferenceNumber1() {
        return referenceNumber1;
    }

    public void setReferenceNumber1(String referenceNumber1) {
        this.referenceNumber1 = referenceNumber1;
    }

    public String getReferenceNumber2() {
        return referenceNumber2;
    }

    public void setReferenceNumber2(String referenceNumber2) {
        this.referenceNumber2 = referenceNumber2;
    }

    public String getReferenceNumber3() {
        return referenceNumber3;
    }

    public void setReferenceNumber3(String referenceNumber3) {
        this.referenceNumber3 = referenceNumber3;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getSoliciteDate() {
        return soliciteDate;
    }

    public void setSoliciteDate(Date soliciteDate) {
        this.soliciteDate = soliciteDate;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsrCreacion() {
        return usrCreacion;
    }

    public void setUsrCreacion(String usrCreacion) {
        this.usrCreacion = usrCreacion;
    }

    public String getUsrModificacion() {
        return usrModificacion;
    }

    public void setUsrModificacion(String usrModificacion) {
        this.usrModificacion = usrModificacion;
    }

    public String getTransactionCycle() {
        return transactionCycle;
    }

    public void setTransactionCycle(String transactionCycle) {
        this.transactionCycle = transactionCycle;
    }

    public String getFwdUrl() {
        return fwdUrl;
    }

    public void setFwdUrl(String fwdUrl) {
        this.fwdUrl = fwdUrl;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

    public TipoTransaccionPse getTtId() {
        return ttId;
    }

    public void setTtId(TipoTransaccionPse ttId) {
        this.ttId = ttId;
    }

    public EstadoTransaccionPse getEtId() {
        return etId;
    }

    public void setEtId(EstadoTransaccionPse etId) {
        this.etId = etId;
    }

    public String getFinacleTransactionId() {
        return finacleTransactionId;
    }

    public void setFinacleTransactionId(String finacleTransactionId) {
        this.finacleTransactionId = finacleTransactionId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /**
     * @return the bankProcessDate
     */
    public Date getBankProcessDate() {
        return bankProcessDate;
    }

    /**
     * @param bankProcessDate
     *            the bankProcessDate to set
     */
    public void setBankProcessDate(Date bankProcessDate) {
        this.bankProcessDate = bankProcessDate;
    }

    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress
     *            the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}