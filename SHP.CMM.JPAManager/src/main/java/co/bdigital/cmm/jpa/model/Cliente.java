package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the CLIENTE database table.
 * 
 */
@Entity
@Table(name = "CLIENTE", schema = "SHBANCA_DIGITAL")
@NamedQueries({
        @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
        @NamedQuery(name = "Cliente.findByPhoneNumber", query = "SELECT c FROM Cliente c WHERE c.celular = :phoneNumber") })
@Cacheable(false)
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CLIENTE_ID")
    private long clienteId;

    @Column(name = "ACEPTA_CONTRATO")
    private String aceptaContrato;

    @Column(name = "ADDRESS_ID")
    private String addressId;

    private String apellido1;

    private String apellido2;

    private BigDecimal biometria;

    private String celular;

    @Column(name = "CIF_ID")
    private String cifId;

    @Column(name = "CONTRATO_ID")
    private BigDecimal contratoId;

    @Column(name = "CORREO_ACTIVO")
    private Boolean correoActivo;

    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;

    @Column(name = "DEVICE_ID")
    private String deviceId;

    @Column(name = "DOCUMENT_ID")
    private String documentId;

    @Column(name = "EMAIL_ID")
    private String emailId;

    @Column(name = "ENVIO_CONTRATO")
    private String envioContrato;

    @Column(name = "ESTADO_ID")
    private BigDecimal estadoId;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_EXPEDICION")
    private Date fechaExpedicion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNacimiento;

    private String nickname;

    private String nombre1;

    private String nombre2;

    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;

    @Column(name = "NUMERO_ID")
    private String numeroId;

    @Column(name = "PAIS_ID")
    private String paisId;

    @Column(name = "PHONE_ID")
    private String phoneId;

    @Column(name = "TERMINOS_CONDICIONES")
    private String terminosCondiciones;

    @Column(name = "TIPO_CLIENTE")
    private String tipoCliente;

    @Column(name = "TIPO_ID")
    private String tipoId;

    @Column(name = "TOKEN_ID")
    private String tokenId;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_DELETE_TMP")
    private String usrDeleteTmp;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    // bi-directional one-to-one association to SeguridadDfh
    @OneToOne(mappedBy = "cliente")
    private SeguridadDfh seguridadDfh;

    // bi-directional one-to-one association to Seguridad
    @OneToOne
    @JoinColumn(name = "CELULAR")
    private Seguridad seguridad;

    // bi-directional many-to-one association to BloqueoUsuario
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<BloqueoUsuario> bloqueoUsuarios;
    
 // bi-directional many-to-one association to RegistroCliente
    @OneToMany(mappedBy = "cliente")
    private List<RegistroCliente> registroClientes;

    // bi-directional many-to-one association to DivisionGeografica
    @ManyToOne
    @JoinColumn(name = "PAIS_ID")
    private DivisionGeografica divisionGeografica;

    // bi-directional many-to-one association to ImagenS3
    @OneToMany(mappedBy = "cliente")
    private List<ImagenS3> imagenS3s;

    // bi-directional many-to-one association to ConsultaCifin
    @OneToMany(mappedBy = "cliente")
    private List<ConsultaCifin> consultaCifins;
    // bi-directional many-to-one association to DetalleCliente
    @OneToMany(mappedBy = "idCliente")
    private List<DetalleCliente> detalleClientes;

    public Cliente() {
    }

    public long getClienteId() {
        return this.clienteId;
    }

    public void setClienteId(long clienteId) {
        this.clienteId = clienteId;
    }

    public String getAceptaContrato() {
        return this.aceptaContrato;
    }

    public void setAceptaContrato(String aceptaContrato) {
        this.aceptaContrato = aceptaContrato;
    }

    public String getAddressId() {
        return this.addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getApellido1() {
        return this.apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return this.apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public BigDecimal getBiometria() {
        return this.biometria;
    }

    public void setBiometria(BigDecimal biometria) {
        this.biometria = biometria;
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCifId() {
        return this.cifId;
    }

    public void setCifId(String cifId) {
        this.cifId = cifId;
    }

    public BigDecimal getContratoId() {
        return this.contratoId;
    }

    public void setContratoId(BigDecimal contratoId) {
        this.contratoId = contratoId;
    }

    public Boolean getCorreoActivo() {
        return this.correoActivo;
    }

    public void setCorreoActivo(Boolean correoActivo) {
        this.correoActivo = correoActivo;
    }

    public String getCorreoElectronico() {
        return this.correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getEmailId() {
        return this.emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEnvioContrato() {
        return this.envioContrato;
    }

    public void setEnvioContrato(String envioContrato) {
        this.envioContrato = envioContrato;
    }

    public BigDecimal getEstadoId() {
        return this.estadoId;
    }

    public void setEstadoId(BigDecimal estadoId) {
        this.estadoId = estadoId;
    }

    public Timestamp getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaExpedicion() {
        return this.fechaExpedicion;
    }

    public void setFechaExpedicion(Date fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public Timestamp getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNombre1() {
        return this.nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return this.nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getNumeroCuenta() {
        return this.numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNumeroId() {
        return this.numeroId;
    }

    public void setNumeroId(String numeroId) {
        this.numeroId = numeroId;
    }

    public String getPaisId() {
        return this.paisId;
    }

    public void setPaisId(String paisId) {
        this.paisId = paisId;
    }

    public String getPhoneId() {
        return this.phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getTerminosCondiciones() {
        return this.terminosCondiciones;
    }

    public void setTerminosCondiciones(String terminosCondiciones) {
        this.terminosCondiciones = terminosCondiciones;
    }

    public String getTipoCliente() {
        return this.tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getTipoId() {
        return this.tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    public String getTokenId() {
        return this.tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getUsrCreacion() {
        return this.usrCreacion;
    }

    public void setUsrCreacion(String usrCreacion) {
        this.usrCreacion = usrCreacion;
    }

    public String getUsrDeleteTmp() {
        return this.usrDeleteTmp;
    }

    public void setUsrDeleteTmp(String usrDeleteTmp) {
        this.usrDeleteTmp = usrDeleteTmp;
    }

    public String getUsrModificacion() {
        return this.usrModificacion;
    }

    public void setUsrModificacion(String usrModificacion) {
        this.usrModificacion = usrModificacion;
    }

    public DivisionGeografica getDivisionGeografica() {
        return this.divisionGeografica;
    }

    public void setDivisionGeografica(DivisionGeografica divisionGeografica) {
        this.divisionGeografica = divisionGeografica;
    }

    public List<BloqueoUsuario> getBloqueoUsuarios() {
        return this.bloqueoUsuarios;
    }

    public void setBloqueoUsuarios(List<BloqueoUsuario> bloqueoUsuarios) {
        this.bloqueoUsuarios = bloqueoUsuarios;
    }

    public BloqueoUsuario addBloqueoUsuario(BloqueoUsuario bloqueoUsuario) {
        getBloqueoUsuarios().add(bloqueoUsuario);
        bloqueoUsuario.setCliente(this);

        return bloqueoUsuario;
    }

    public BloqueoUsuario removeBloqueoUsuario(BloqueoUsuario bloqueoUsuario) {
        getBloqueoUsuarios().remove(bloqueoUsuario);
        bloqueoUsuario.setCliente(null);

        return bloqueoUsuario;
    }

    public List<ImagenS3> getImagenS3s() {
        return this.imagenS3s;
    }

    public void setImagenS3s(List<ImagenS3> imagenS3s) {
        this.imagenS3s = imagenS3s;
    }

    public List<ConsultaCifin> getConsultaCifins() {
        return this.consultaCifins;
    }

    public void setConsultaCifins(List<ConsultaCifin> consultaCifins) {
        this.consultaCifins = consultaCifins;
    }

    public List<DetalleCliente> getDetalleClientes() {
        return this.detalleClientes;
    }

    public void setDetalleClientes(List<DetalleCliente> detalleClientes) {
        this.detalleClientes = detalleClientes;
    }

    public DetalleCliente addDetalleCliente(DetalleCliente detalleCliente) {
        getDetalleClientes().add(detalleCliente);
        detalleCliente.setIdCliente(this);

        return detalleCliente;
    }

    public DetalleCliente removeDetalleCliente(DetalleCliente detalleCliente) {
        getDetalleClientes().remove(detalleCliente);
        detalleCliente.setIdCliente(null);

        return detalleCliente;
    }
    
    public List<RegistroCliente> getRegistroClientes() {
        return this.registroClientes;
    }

    public void setRegistroClientes(List<RegistroCliente> registroClientes) {
        this.registroClientes = registroClientes;
    }

}