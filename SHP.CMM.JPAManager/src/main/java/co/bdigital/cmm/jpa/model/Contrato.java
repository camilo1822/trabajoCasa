package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the CONTRATO database table.
 * 
 */
@Entity
@Table(name = "CONTRATO", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "Contrato.findAll", query = "SELECT c FROM Contrato c")
public class Contrato implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CONTRATO_ID")
    private String contratoId;

    @Column(name = "ESTADO_ID")
    private BigDecimal estadoId;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_FIN")
    private Date fechaFin;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_INICIO")
    private Date fechaInicio;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "PAIS_ID")
    private String paisId;

    @Lob
    private String texto;

    @Lob
    @Column(name = "TEXTO_COMPLETO")
    private String textoCompleto;

    @Column(name = "TIPO_CONTRATO")
    private String tipoContrato;

    private String url;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    @Column(name = "\"VERSION\"")
    private String version;

    // bi-directional many-to-one association to DivisionGeografica
    @ManyToOne
    @JoinColumn(name = "PAIS_ID")
    private DivisionGeografica divisionGeografica;

    public Contrato() {
    }

    public String getContratoId() {
        return this.contratoId;
    }

    public void setContratoId(String contratoId) {
        this.contratoId = contratoId;
    }

    public BigDecimal getEstadoId() {
        return this.estadoId;
    }

    public void setEstadoId(BigDecimal estadoId) {
        this.estadoId = estadoId;
    }

    public Timestamp getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getPaisId() {
        return this.paisId;
    }

    public void setPaisId(String paisId) {
        this.paisId = paisId;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTextoCompleto() {
        return this.textoCompleto;
    }

    public void setTextoCompleto(String textoCompleto) {
        this.textoCompleto = textoCompleto;
    }

    public String getTipoContrato() {
        return this.tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public DivisionGeografica getDivisionGeografica() {
        return this.divisionGeografica;
    }

    public void setDivisionGeografica(DivisionGeografica divisionGeografica) {
        this.divisionGeografica = divisionGeografica;
    }
}