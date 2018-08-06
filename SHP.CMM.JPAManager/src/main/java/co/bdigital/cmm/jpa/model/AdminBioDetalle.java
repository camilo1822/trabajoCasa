package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the ADMIN_BIO_DETALLES database table.
 * 
 */
@Entity
@Table(name = "ADMIN_BIO_DETALLES", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "AdminBioDetalle.findAll", query = "SELECT a FROM AdminBioDetalle a")
public class AdminBioDetalle implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "ADMIN_BIO_DETALLES_ADMINBIODETALLEID_GENERATOR", sequenceName = "SHBANCA_DIGITAL.ADMIN_BIODET_SEQ1")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADMIN_BIO_DETALLES_ADMINBIODETALLEID_GENERATOR")

    @Column(name = "ADMIN_BIODETALLE_ID")
    private String adminBiodetalleId;

    private String descripcion;

    private String estado;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    private String tipo;

    @Column(name = "URL_IMAGEN")
    private String urlImagen;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    // bi-directional many-to-one association to AdminNotificacion
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "CLIENTE_ID", referencedColumnName = "CLIENTE_ID"),
            @JoinColumn(name = "TRN_ID", referencedColumnName = "TRN_ID") })
    private AdminNotificacion adminNotificacion;

    public AdminBioDetalle() {
    }

    public String getAdminBiodetalleId() {
        return this.adminBiodetalleId;
    }

    public void setAdminBiodetalleId(String adminBiodetalleId) {
        this.adminBiodetalleId = adminBiodetalleId;
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

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrlImagen() {
        return this.urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
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

    public AdminNotificacion getAdminNotificacion() {
        return this.adminNotificacion;
    }

    public void setAdminNotificacion(AdminNotificacion adminNotificacion) {
        this.adminNotificacion = adminNotificacion;
    }

}