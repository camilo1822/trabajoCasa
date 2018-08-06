package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the COMERCIOS database table.
 * 
 */
@Entity
@Table(name = "COMERCIOS", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "Comercio.findAll", query = "SELECT c FROM Comercio c")
public class Comercio implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "COMERCIOS_ID_GENERATOR", sequenceName = "CLIENTE_ID_SEQ1")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMERCIOS_ID_GENERATOR")
    private String id;

    @Column(name = "CODIGO_DANE")
    private String codigoDane;

    private String correo;

    @Column(name = "CUENTA_CONTABLE")
    private String cuentaContable;

    private String direccion;

    @Column(name = "FEC_CREACION")
    private Timestamp fecCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_CREACION_CCIO")
    private Date fecCreacionCcio;

    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_EXPEDICION")
    private Date fecExpedicion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_NACIMIENTO")
    private Date fecNacimiento;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    private String formato;

    private BigDecimal latitud;

    private BigDecimal longitud;

    private String nombre;

    @Column(name = "NUM_DOC")
    private String numDoc;

    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;

    private String sistema;

    @Column(name = "SITIO_WEB")
    private String sitioWeb;

    private String telefono;

    @Column(name = "TIPO_DOCUMENTO_TD_ID")
    private BigDecimal tipoDocumentoTdId;

    @Column(name = "TIPO_NEGOCIO_TN_ID")
    private BigDecimal tipoNegocioTnId;

    @Column(name = "TIPO_PUNTO_TPP_ID")
    private BigDecimal tipoPuntoTppId;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private Timestamp usuarioModificacion;

    // bi-directional many-to-one association to Estado
    @ManyToOne
    @JoinColumn(name = "ESTADO_EST_ID1")
    private Estado estado;

    // bi-directional many-to-one association to Dispositivo
    @OneToMany(mappedBy = "comercio")
    private List<Dispositivo> dispositivos;

    @Column(name = "BANCO_CODIGO")
    private String bancoCodigo;

    @Column(name = "TIPO_CUENTA")
    private String tipoCuenta;

    // bi-directional many-to-one association to DivisionGeografica
    @ManyToOne
    @JoinColumn(name = "PAIS_ID")
    private DivisionGeografica divisionGeografica;

    @Column(name = "CODIGO")
    private String codigo;

    public Comercio() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigoDane() {
        return this.codigoDane;
    }

    public void setCodigoDane(String codigoDane) {
        this.codigoDane = codigoDane;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Timestamp getFecCreacion() {
        return this.fecCreacion;
    }

    public void setFecCreacion(Timestamp fecCreacion) {
        this.fecCreacion = fecCreacion;
    }

    public Date getFecCreacionCcio() {
        return this.fecCreacionCcio;
    }

    public void setFecCreacionCcio(Date fecCreacionCcio) {
        this.fecCreacionCcio = fecCreacionCcio;
    }

    public Date getFecExpedicion() {
        return this.fecExpedicion;
    }

    public void setFecExpedicion(Date fecExpedicion) {
        this.fecExpedicion = fecExpedicion;
    }

    public Date getFecNacimiento() {
        return this.fecNacimiento;
    }

    public void setFecNacimiento(Date fecNacimiento) {
        this.fecNacimiento = fecNacimiento;
    }

    public Timestamp getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getFormato() {
        return this.formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public BigDecimal getLatitud() {
        return this.latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return this.longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumDoc() {
        return this.numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getSitioWeb() {
        return this.sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public BigDecimal getTipoDocumentoTdId() {
        return this.tipoDocumentoTdId;
    }

    public void setTipoDocumentoTdId(BigDecimal tipoDocumentoTdId) {
        this.tipoDocumentoTdId = tipoDocumentoTdId;
    }

    public BigDecimal getTipoNegocioTnId() {
        return this.tipoNegocioTnId;
    }

    public void setTipoNegocioTnId(BigDecimal tipoNegocioTnId) {
        this.tipoNegocioTnId = tipoNegocioTnId;
    }

    public BigDecimal getTipoPuntoTppId() {
        return this.tipoPuntoTppId;
    }

    public void setTipoPuntoTppId(BigDecimal tipoPuntoTppId) {
        this.tipoPuntoTppId = tipoPuntoTppId;
    }

    public String getUsuarioCreacion() {
        return this.usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Timestamp getUsuarioModificacion() {
        return this.usuarioModificacion;
    }

    public void setUsuarioModificacion(Timestamp usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Dispositivo> getDispositivos() {
        return this.dispositivos;
    }

    public void setDispositivos(List<Dispositivo> dispositivos) {
        this.dispositivos = dispositivos;
    }

    public Dispositivo addDispositivo(Dispositivo dispositivo) {
        getDispositivos().add(dispositivo);
        dispositivo.setComercio(this);

        return dispositivo;
    }

    public Dispositivo removeDispositivo(Dispositivo dispositivo) {
        getDispositivos().remove(dispositivo);
        dispositivo.setComercio(null);

        return dispositivo;
    }

    public DivisionGeografica getDivisionGeografica() {
        return this.divisionGeografica;
    }

    public void setDivisionGeografica(DivisionGeografica divisionGeografica) {
        this.divisionGeografica = divisionGeografica;
    }

    public String getCuentaContable() {
        return cuentaContable;
    }

    public void setCuentaContable(String cuentaContable) {
        this.cuentaContable = cuentaContable;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getBancoCodigo() {
        return bancoCodigo;
    }

    public void setBancoCodigo(String bancoCodigo) {
        this.bancoCodigo = bancoCodigo;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}