package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the EXP_PERSONA database table.
 * 
 */
@Entity
@Table(name = "EXP_PERSONA", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "ExpPersona.findAll", query = "SELECT e FROM ExpPersona e")
public class ExpPersona implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "EXP_PERSONA_EXPPERSONAID_GENERATOR", sequenceName = "SHBANCA_DIGITAL.EXP_PERSONA_SEQ1")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXP_PERSONA_EXPPERSONAID_GENERATOR")
    @Column(name = "EXP_PERSONA_ID")
    private long expPersonaId;

    @Column(name = "ACTIVIDAD_ECONOMICA")
    private String actividadEconomica;

    private String antiguedad;

    @Column(name = "CIUDAD_EXPEDICION")
    private String ciudadExpedicion;

    @Column(name = "CODIGO_CIIU")
    private String codigoCiiu;

    @Column(name = "DEPARTAMENTO_EXPEDICION")
    private String departamentoExpedicion;

    private String edad;

    @Column(name = "ESTADO_CIVIL")
    private String estadoCivil;

    @Column(name = "ESTADO_DOC")
    private String estadoDoc;

    @Column(name = "FECHA_CONSULTA")
    private Timestamp fechaConsulta;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_EXPEDICION")
    private Date fechaExpedicion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    private String genero;

    private String llave;

    @Column(name = "NOMBRE_COMPLETO")
    private String nombreCompleto;

    private String nombres;

    @Column(name = "NUMERO_DOC")
    private String numeroDoc;

    @Column(name = "PRIMER_APELLIDO")
    private String primerApellido;

    private String rut;

    @Column(name = "SEGUNDO_APELLIDO")
    private String segundoApellido;

    private String tipo;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    private String validada;

    // bi-directional many-to-one association to ExpPersonaDetalle
    @OneToMany(mappedBy = "expPersona", cascade = CascadeType.ALL)
    @OrderBy("tipo ASC, grupo ASC")
    private List<ExpPersonaDetalle> expPersonaDetalles;

    public ExpPersona() {
    }

    public long getExpPersonaId() {
        return this.expPersonaId;
    }

    public void setExpPersonaId(long expPersonaId) {
        this.expPersonaId = expPersonaId;
    }

    public String getActividadEconomica() {
        return this.actividadEconomica;
    }

    public void setActividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public String getAntiguedad() {
        return this.antiguedad;
    }

    public void setAntiguedad(String antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getCiudadExpedicion() {
        return this.ciudadExpedicion;
    }

    public void setCiudadExpedicion(String ciudadExpedicion) {
        this.ciudadExpedicion = ciudadExpedicion;
    }

    public String getCodigoCiiu() {
        return this.codigoCiiu;
    }

    public void setCodigoCiiu(String codigoCiiu) {
        this.codigoCiiu = codigoCiiu;
    }

    public String getDepartamentoExpedicion() {
        return this.departamentoExpedicion;
    }

    public void setDepartamentoExpedicion(String departamentoExpedicion) {
        this.departamentoExpedicion = departamentoExpedicion;
    }

    public String getEdad() {
        return this.edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getEstadoCivil() {
        return this.estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getEstadoDoc() {
        return this.estadoDoc;
    }

    public void setEstadoDoc(String estadoDoc) {
        this.estadoDoc = estadoDoc;
    }

    public Timestamp getFechaConsulta() {
        return this.fechaConsulta;
    }

    public void setFechaConsulta(Timestamp fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
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

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getLlave() {
        return this.llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public String getNombreCompleto() {
        return this.nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombres() {
        return this.nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNumeroDoc() {
        return this.numeroDoc;
    }

    public void setNumeroDoc(String numeroDoc) {
        this.numeroDoc = numeroDoc;
    }

    public String getPrimerApellido() {
        return this.primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getRut() {
        return this.rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getSegundoApellido() {
        return this.segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUsrCreacion() {
        return this.usrCreacion;
    }

    public void setUsrCreacion(String usrCreacion) {
        this.usrCreacion = usrCreacion;
    }

    public String getUsrModificacion() {
        return this.usrModificacion;
    }

    public void setUsrModificacion(String usrModificacion) {
        this.usrModificacion = usrModificacion;
    }

    public String getValidada() {
        return this.validada;
    }

    public void setValidada(String validada) {
        this.validada = validada;
    }

    public List<ExpPersonaDetalle> getExpPersonaDetalles() {
        return this.expPersonaDetalles;
    }

    public void setExpPersonaDetalles(List<ExpPersonaDetalle> expPersonaDetalles) {
        this.expPersonaDetalles = expPersonaDetalles;
    }

    public ExpPersonaDetalle addExpPersonaDetalle(
            ExpPersonaDetalle expPersonaDetalle) {
        getExpPersonaDetalles().add(expPersonaDetalle);
        expPersonaDetalle.setExpPersona(this);

        return expPersonaDetalle;
    }

    public ExpPersonaDetalle removeExpPersonaDetalle(
            ExpPersonaDetalle expPersonaDetalle) {
        getExpPersonaDetalles().remove(expPersonaDetalle);
        expPersonaDetalle.setExpPersona(null);

        return expPersonaDetalle;
    }

}