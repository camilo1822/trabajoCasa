package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the AW_USUARIO database table.
 * 
 */
@Entity
@Table(name = "AW_USUARIO", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "AwUsuario.findAll", query = "SELECT a FROM AwUsuario a")
public class AwUsuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String mail;

    private String apellidos;

    private BigDecimal celular;

    private Boolean estado;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_MODIFICACION")
    private Date fechaModificacion;

    @Column(name = "MUST_CHANGE_PWD")
    private Boolean mustChangePwd;

    private String nombres;

    @Column(name = "PAIS_ID")
    private String paisId;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    // bi-directional many-to-one association to AwAuditoria
    @OneToMany(mappedBy = "awUsuario")
    private List<AwAuditoria> awAuditorias;

    // bi-directional many-to-one association to AwRol
    @ManyToOne
    @JoinColumn(name = "ROL")
    private AwRol awRol;

    // bi-directional many-to-one association to DivisionGeografica
    @ManyToOne
    @JoinColumn(name = "PAIS_ID")
    private DivisionGeografica divisionGeografica;

    public AwUsuario() {
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public BigDecimal getCelular() {
        return this.celular;
    }

    public void setCelular(BigDecimal celular) {
        this.celular = celular;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
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

    public Boolean getMustChangePwd() {
        return this.mustChangePwd;
    }

    public void setMustChangePwd(Boolean mustChangePwd) {
        this.mustChangePwd = mustChangePwd;
    }

    public String getNombres() {
        return this.nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPaisId() {
        return this.paisId;
    }

    public void setPaisId(String paisId) {
        this.paisId = paisId;
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

    public List<AwAuditoria> getAwAuditorias() {
        return this.awAuditorias;
    }

    public void setAwAuditorias(List<AwAuditoria> awAuditorias) {
        this.awAuditorias = awAuditorias;
    }

    public AwAuditoria addAwAuditoria(AwAuditoria awAuditoria) {
        getAwAuditorias().add(awAuditoria);
        awAuditoria.setAwUsuario(this);

        return awAuditoria;
    }

    public AwAuditoria removeAwAuditoria(AwAuditoria awAuditoria) {
        getAwAuditorias().remove(awAuditoria);
        awAuditoria.setAwUsuario(null);

        return awAuditoria;
    }

    public AwRol getAwRol() {
        return this.awRol;
    }

    public void setAwRol(AwRol awRol) {
        this.awRol = awRol;
    }

    public DivisionGeografica getDivisionGeografica() {
        return this.divisionGeografica;
    }

    public void setDivisionGeografica(DivisionGeografica divisionGeografica) {
        this.divisionGeografica = divisionGeografica;
    }

}