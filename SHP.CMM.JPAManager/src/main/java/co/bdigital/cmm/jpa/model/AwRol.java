package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the AW_ROL database table.
 * 
 */
@Entity
@Table(name = "AW_ROL", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "AwRol.findAll", query = "SELECT a FROM AwRol a")
public class AwRol implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "AW_ROL_IDROL_GENERATOR", sequenceName = "SHBANCA_DIGITAL.AW_ROL_SEQ1")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AW_ROL_IDROL_GENERATOR")
    @Column(name = "ID_ROL")
    private long idRol;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_MODIFICACION")
    private Date fechaModificacion;

    private String menu;

    private String nombre;

    @Column(name = "PAIS_ID")
    private String paisId;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    // bi-directional many-to-one association to AwAccionesPorRole
    @OneToMany(mappedBy = "awRol", fetch = FetchType.EAGER)
    private List<AwAccionesPorRole> awAccionesPorRoles;

    // bi-directional many-to-many association to AwAccion
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "AW_ACCIONES_POR_ROLES", joinColumns = { @JoinColumn(name = "ID_ROL") }, inverseJoinColumns = { @JoinColumn(name = "ID_ACCION") })
    private List<AwAccion> awAccions;

    // bi-directional many-to-one association to AwUsuario
    @OneToMany(mappedBy = "awRol")
    private List<AwUsuario> awUsuarios;

    public AwRol() {
    }

    public long getIdRol() {
        return this.idRol;
    }

    public void setIdRol(long idRol) {
        this.idRol = idRol;
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

    public String getMenu() {
        return this.menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
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

    public List<AwAccionesPorRole> getAwAccionesPorRoles() {
        return this.awAccionesPorRoles;
    }

    public void setAwAccionesPorRoles(List<AwAccionesPorRole> awAccionesPorRoles) {
        this.awAccionesPorRoles = awAccionesPorRoles;
    }

    public AwAccionesPorRole addAwAccionesPorRole(
            AwAccionesPorRole awAccionesPorRole) {
        getAwAccionesPorRoles().add(awAccionesPorRole);
        awAccionesPorRole.setAwRol(this);

        return awAccionesPorRole;
    }

    public AwAccionesPorRole removeAwAccionesPorRole(
            AwAccionesPorRole awAccionesPorRole) {
        getAwAccionesPorRoles().remove(awAccionesPorRole);
        awAccionesPorRole.setAwRol(null);

        return awAccionesPorRole;
    }

    public List<AwAccion> getAwAccions() {
        return this.awAccions;
    }

    public void setAwAccions(List<AwAccion> awAccions) {
        this.awAccions = awAccions;
    }

    public List<AwUsuario> getAwUsuarios() {
        return this.awUsuarios;
    }

    public void setAwUsuarios(List<AwUsuario> awUsuarios) {
        this.awUsuarios = awUsuarios;
    }

    public AwUsuario addAwUsuario(AwUsuario awUsuario) {
        getAwUsuarios().add(awUsuario);
        awUsuario.setAwRol(this);

        return awUsuario;
    }

    public AwUsuario removeAwUsuario(AwUsuario awUsuario) {
        getAwUsuarios().remove(awUsuario);
        awUsuario.setAwRol(null);

        return awUsuario;
    }

    public String getPaisId() {
        return this.paisId;
    }

    public void setPaisId(String paisId) {
        this.paisId = paisId;
    }

}