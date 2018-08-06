package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the AW_AUDITORIA database table.
 * 
 */
@Entity
@Table(name = "AW_AUDITORIA", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "AwAuditoria.findAll", query = "SELECT a FROM AwAuditoria a")
public class AwAuditoria implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "AW_AUDITORIA_IDAUDITORIA_GENERATOR", sequenceName = "SHBANCA_DIGITAL.AW_AUDITORIA_SEQ1")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AW_AUDITORIA_IDAUDITORIA_GENERATOR")
    @Column(name = "ID_AUDITORIA")
    private long idAuditoria;

    private String descripcion;

    private Timestamp fecha;

    // bi-directional many-to-one association to AwAccion
    @ManyToOne
    @JoinColumn(name = "ID_ACCION")
    private AwAccion awAccion;

    // bi-directional many-to-one association to AwUsuario
    @ManyToOne
    @JoinColumn(name = "USUARIO")
    private AwUsuario awUsuario;

    public AwAuditoria() {
    }

    public long getIdAuditoria() {
        return this.idAuditoria;
    }

    public void setIdAuditoria(long idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFecha() {
        return this.fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public AwAccion getAwAccion() {
        return this.awAccion;
    }

    public void setAwAccion(AwAccion awAccion) {
        this.awAccion = awAccion;
    }

    public AwUsuario getAwUsuario() {
        return this.awUsuario;
    }

    public void setAwUsuario(AwUsuario awUsuario) {
        this.awUsuario = awUsuario;
    }

}