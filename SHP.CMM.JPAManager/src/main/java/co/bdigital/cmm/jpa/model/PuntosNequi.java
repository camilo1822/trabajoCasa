package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the PUNTOS_NEQUI database table.
 * 
 */
@Entity
@Table(name = "PUNTOS_NEQUI", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "PuntosNequi.findAll", query = "SELECT p FROM PuntosNequi p")
public class PuntosNequi implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CODIGO_INTERNO")
    private long codigoInterno;

    @Column(name = "CODIGO_DANE")
    private String codigoDane;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    private String formato;

    private BigDecimal latitud;

    private BigDecimal longitud;

    @Column(name = "NOMBRE_ACTUAL")
    private String nombreActual;

    private String nomenclatura;

    private String telefono;

    @Column(name = "TIPO_PUNTO")
    private BigDecimal tipoPunto;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    // bi-directional many-to-one association to DiasAtencion
    @OneToMany(mappedBy = "puntosNequi")
    private List<DiasAtencion> diasAtencions;

    // bi-directional many-to-one association to DivisionGeografica
    @ManyToOne
    @JoinColumn(name = "PAIS_ID")
    private DivisionGeografica divisionGeografica;

    public PuntosNequi() {
    }

    public long getCodigoInterno() {
        return this.codigoInterno;
    }

    public void setCodigoInterno(long codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getCodigoDane() {
        return this.codigoDane;
    }

    public void setCodigoDane(String codigoDane) {
        this.codigoDane = codigoDane;
    }

    public Timestamp getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public String getNombreActual() {
        return this.nombreActual;
    }

    public void setNombreActual(String nombreActual) {
        this.nombreActual = nombreActual;
    }

    public String getNomenclatura() {
        return this.nomenclatura;
    }

    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public BigDecimal getTipoPunto() {
        return this.tipoPunto;
    }

    public void setTipoPunto(BigDecimal tipoPunto) {
        this.tipoPunto = tipoPunto;
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

    public List<DiasAtencion> getDiasAtencions() {
        return this.diasAtencions;
    }

    public void setDiasAtencions(List<DiasAtencion> diasAtencions) {
        this.diasAtencions = diasAtencions;
    }

    public DiasAtencion addDiasAtencion(DiasAtencion diasAtencion) {
        getDiasAtencions().add(diasAtencion);
        diasAtencion.setPuntosNequi(this);

        return diasAtencion;
    }

    public DiasAtencion removeDiasAtencion(DiasAtencion diasAtencion) {
        getDiasAtencions().remove(diasAtencion);
        diasAtencion.setPuntosNequi(null);

        return diasAtencion;
    }

    public DivisionGeografica getDivisionGeografica() {
        return this.divisionGeografica;
    }

    public void setDivisionGeografica(DivisionGeografica divisionGeografica) {
        this.divisionGeografica = divisionGeografica;
    }
}