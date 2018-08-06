package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the OPERADOR_RECARGA database table.
 * 
 */
@Entity
@Table(name = "OPERADOR_RECARGA", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "OperadorRecarga.findAll", query = "SELECT o FROM OperadorRecarga o")
public class OperadorRecarga implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private OperadorRecargaPK id;

    private String bolsa;

    private String descripcion;

    private String estado;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "MONTO_MAX")
    private String montoMax;

    @Column(name = "MONTO_MIN")
    private String montoMin;

    @Column(name = "NOMBRE_OPERADOR")
    private String nombreOperador;

    private String observaciones;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    // bi-directional many-to-one association to DivisionGeografica
    @ManyToOne
    @JoinColumn(name = "ID_PAIS")
    private DivisionGeografica divisionGeografica;

    // bi-directional many-to-one association to TipoOperador
    @ManyToOne
    @JoinColumn(name = "TIPO")
    private TipoOperador tipoOperador;

    // bi-directional many-to-one association to OperadorRecargaDetalle
    @OneToMany(mappedBy = "operadorRecarga")
    private List<OperadorRecargaDetalle> operadorRecargaDetalles;

    public OperadorRecarga() {
    }

    public OperadorRecargaPK getId() {
        return this.id;
    }

    public void setId(OperadorRecargaPK id) {
        this.id = id;
    }

    public String getBolsa() {
        return this.bolsa;
    }

    public void setBolsa(String bolsa) {
        this.bolsa = bolsa;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getMontoMax() {
        return this.montoMax;
    }

    public void setMontoMax(String montoMax) {
        this.montoMax = montoMax;
    }

    public String getMontoMin() {
        return this.montoMin;
    }

    public void setMontoMin(String montoMin) {
        this.montoMin = montoMin;
    }

    public String getNombreOperador() {
        return this.nombreOperador;
    }

    public void setNombreOperador(String nombreOperador) {
        this.nombreOperador = nombreOperador;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public DivisionGeografica getDivisionGeografica() {
        return this.divisionGeografica;
    }

    public void setDivisionGeografica(DivisionGeografica divisionGeografica) {
        this.divisionGeografica = divisionGeografica;
    }

    public TipoOperador getTipoOperador() {
        return this.tipoOperador;
    }

    public void setTipoOperador(TipoOperador tipoOperador) {
        this.tipoOperador = tipoOperador;
    }

    public List<OperadorRecargaDetalle> getOperadorRecargaDetalles() {
        return this.operadorRecargaDetalles;
    }

    public void setOperadorRecargaDetalles(
            List<OperadorRecargaDetalle> operadorRecargaDetalles) {
        this.operadorRecargaDetalles = operadorRecargaDetalles;
    }

    public OperadorRecargaDetalle addOperadorRecargaDetalle(
            OperadorRecargaDetalle operadorRecargaDetalle) {
        getOperadorRecargaDetalles().add(operadorRecargaDetalle);
        operadorRecargaDetalle.setOperadorRecarga(this);

        return operadorRecargaDetalle;
    }

    public OperadorRecargaDetalle removeOperadorRecargaDetalle(
            OperadorRecargaDetalle operadorRecargaDetalle) {
        getOperadorRecargaDetalles().remove(operadorRecargaDetalle);
        operadorRecargaDetalle.setOperadorRecarga(null);

        return operadorRecargaDetalle;
    }

}