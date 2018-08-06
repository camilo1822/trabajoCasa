package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the CATS_MIG database table.
 * 
 */
@Entity
@Table(name = "CATS_MIG", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "CatsMig.findAll", query = "SELECT c FROM CatsMig c")
public class CatsMig implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "NUM_DOC")
    private long numDoc;

    private BigDecimal estado;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "NOMBRE_CLIENTE")
    private String nombreCliente;

    @Column(name = "NUM_CUENTA")
    private BigDecimal numCuenta;

    private BigDecimal saldo;

    @Column(name = "TIPO_DOC")
    private String tipoDoc;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    // bi-directional many-to-one association to TipoEstadoCat
    @ManyToOne
    @JoinColumn(name = "ESTADO_CUENTA")
    private TipoEstadoCat tipoEstadoCat;

    public CatsMig() {
    }

    public long getNumDoc() {
        return this.numDoc;
    }

    public void setNumDoc(long numDoc) {
        this.numDoc = numDoc;
    }

    public BigDecimal getEstado() {
        return this.estado;
    }

    public void setEstado(BigDecimal estado) {
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

    public String getNombreCliente() {
        return this.nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public BigDecimal getNumCuenta() {
        return this.numCuenta;
    }

    public void setNumCuenta(BigDecimal numCuenta) {
        this.numCuenta = numCuenta;
    }

    public BigDecimal getSaldo() {
        return this.saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getTipoDoc() {
        return this.tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getUsuarioCreacion() {
        return this.usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioModificacion() {
        return this.usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public TipoEstadoCat getTipoEstadoCat() {
        return this.tipoEstadoCat;
    }

    public void setTipoEstadoCat(TipoEstadoCat tipoEstadoCat) {
        this.tipoEstadoCat = tipoEstadoCat;
    }

}