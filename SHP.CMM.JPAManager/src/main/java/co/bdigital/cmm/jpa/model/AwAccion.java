package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the AW_ACCION database table.
 * 
 */
@Entity
@Table(name = "AW_ACCION", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "AwAccion.findAll", query = "SELECT a FROM AwAccion a")
public class AwAccion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_ACCION")
    private long idAccion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_MODIFICACION")
    private Date fechaModificacion;

    private String nombre;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    private String valor;

    // bi-directional many-to-one association to AwAccionesPorRole
    @OneToMany(mappedBy = "awAccion")
    private List<AwAccionesPorRole> awAccionesPorRoles;

    // bi-directional many-to-one association to AwAuditoria
    @OneToMany(mappedBy = "awAccion")
    private List<AwAuditoria> awAuditorias;

    // bi-directional many-to-many association to AwRol
    @ManyToMany(mappedBy = "awAccions")
    private List<AwRol> awRols;

    public AwAccion() {
    }

    public long getIdAccion() {
        return this.idAccion;
    }

    public void setIdAccion(long idAccion) {
        this.idAccion = idAccion;
    }

    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getValor() {
        return this.valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public List<AwAccionesPorRole> getAwAccionesPorRoles() {
        return this.awAccionesPorRoles;
    }

    public void setAwAccionesPorRoles(List<AwAccionesPorRole> awAccionesPorRoles) {
        this.awAccionesPorRoles = awAccionesPorRoles;
    }

    public AwAccionesPorRole addAwAccionesPorRole(
            AwAccionesPorRole awAccionesPorRole) {
        getAwAccionesPorRoles().add(awAccionesPorRole);
        awAccionesPorRole.setAwAccion(this);

        return awAccionesPorRole;
    }

    public AwAccionesPorRole removeAwAccionesPorRole(
            AwAccionesPorRole awAccionesPorRole) {
        getAwAccionesPorRoles().remove(awAccionesPorRole);
        awAccionesPorRole.setAwAccion(null);

        return awAccionesPorRole;
    }

    public List<AwAuditoria> getAwAuditorias() {
        return this.awAuditorias;
    }

    public void setAwAuditorias(List<AwAuditoria> awAuditorias) {
        this.awAuditorias = awAuditorias;
    }

    public AwAuditoria addAwAuditoria(AwAuditoria awAuditoria) {
        getAwAuditorias().add(awAuditoria);
        awAuditoria.setAwAccion(this);

        return awAuditoria;
    }

    public AwAuditoria removeAwAuditoria(AwAuditoria awAuditoria) {
        getAwAuditorias().remove(awAuditoria);
        awAuditoria.setAwAccion(null);

        return awAuditoria;
    }

    public List<AwRol> getAwRols() {
        return this.awRols;
    }

    public void setAwRols(List<AwRol> awRols) {
        this.awRols = awRols;
    }

}