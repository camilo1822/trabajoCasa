package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the ADMIN_NOTIFICACION database table.
 * 
 */
@Entity
@Table(name = "ADMIN_NOTIFICACION", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "AdminNotificacion.findAll", query = "SELECT a FROM AdminNotificacion a")
public class AdminNotificacion implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AdminNotificacionPK id;

    @Column(name = "DEVICE_ID")
    private String deviceId;

    private String estado;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    private String tipo;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    // bi-directional many-to-one association to AdminBioDetalle
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "adminNotificacion")
    private List<AdminBioDetalle> adminBioDetalles;

    public AdminNotificacion() {
    }

    public AdminNotificacionPK getId() {
        return this.id;
    }

    public void setId(AdminNotificacionPK id) {
        this.id = id;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public List<AdminBioDetalle> getAdminBioDetalles() {
        return this.adminBioDetalles;
    }

    public void setAdminBioDetalles(List<AdminBioDetalle> adminBioDetalles) {
        this.adminBioDetalles = adminBioDetalles;
    }

    public AdminBioDetalle addAdminBioDetalle(AdminBioDetalle adminBioDetalle) {
        getAdminBioDetalles().add(adminBioDetalle);
        adminBioDetalle.setAdminNotificacion(this);

        return adminBioDetalle;
    }

    public AdminBioDetalle removeAdminBioDetalle(
            AdminBioDetalle adminBioDetalle) {
        getAdminBioDetalles().remove(adminBioDetalle);
        adminBioDetalle.setAdminNotificacion(null);

        return adminBioDetalle;
    }

}