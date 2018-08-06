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
 * The persistent class for the EXP_PERSONA_DETALLE database table.
 * 
 */
@Entity
@Table(name = "EXP_PERSONA_DETALLE", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "ExpPersonaDetalle.findAll", query = "SELECT e FROM ExpPersonaDetalle e")
public class ExpPersonaDetalle implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "EXP_PERSONA_DETALLE_EXPPERSONADETALLEID_GENERATOR", sequenceName = "SHBANCA_DIGITAL.EXP_PERSONA_DETALLE_SEQ1")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXP_PERSONA_DETALLE_EXPPERSONADETALLEID_GENERATOR")
    @Column(name = "EXP_PERSONA_DETALLE_ID")
    private long expPersonaDetalleId;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    private String grupo;

    private String nombre;

    private String tipo;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    private String valor;

    // bi-directional many-to-one association to ExpPersona
    @ManyToOne
    @JoinColumn(name = "EXP_PERSONA_ID")
    private ExpPersona expPersona;

    public ExpPersonaDetalle() {
    }

    public long getExpPersonaDetalleId() {
        return this.expPersonaDetalleId;
    }

    public void setExpPersonaDetalleId(long expPersonaDetalleId) {
        this.expPersonaDetalleId = expPersonaDetalleId;
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

    public String getGrupo() {
        return this.grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getValor() {
        return this.valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public ExpPersona getExpPersona() {
        return this.expPersona;
    }

    public void setExpPersona(ExpPersona expPersona) {
        this.expPersona = expPersona;
    }

}