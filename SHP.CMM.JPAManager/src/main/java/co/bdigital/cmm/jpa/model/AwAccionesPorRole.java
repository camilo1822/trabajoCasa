package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the AW_ACCIONES_POR_ROLES database table.
 * 
 */
@Entity
@Table(name = "AW_ACCIONES_POR_ROLES", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "AwAccionesPorRole.findAll", query = "SELECT a FROM AwAccionesPorRole a")
public class AwAccionesPorRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AwAccionesPorRolePK id;

    private BigDecimal consulta;

    private BigDecimal modificacion;

    // bi-directional many-to-one association to AwAccion
    @ManyToOne
    @JoinColumn(name = "ID_ACCION")
    private AwAccion awAccion;

    // bi-directional many-to-one association to AwRol
    @ManyToOne
    @JoinColumn(name = "ID_ROL")
    private AwRol awRol;

    public AwAccionesPorRole() {
    }

    public AwAccionesPorRolePK getId() {
        return this.id;
    }

    public void setId(AwAccionesPorRolePK id) {
        this.id = id;
    }

    public BigDecimal getConsulta() {
        return this.consulta;
    }

    public void setConsulta(BigDecimal consulta) {
        this.consulta = consulta;
    }

    public BigDecimal getModificacion() {
        return this.modificacion;
    }

    public void setModificacion(BigDecimal modificacion) {
        this.modificacion = modificacion;
    }

    public AwAccion getAwAccion() {
        return this.awAccion;
    }

    public void setAwAccion(AwAccion awAccion) {
        this.awAccion = awAccion;
    }

    public AwRol getAwRol() {
        return this.awRol;
    }

    public void setAwRol(AwRol awRol) {
        this.awRol = awRol;
    }

}