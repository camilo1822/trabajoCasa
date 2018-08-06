package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the POLITICA_CLI_DETALLE database table.
 * 
 */
@Entity
@Table(name = "POLITICA_CLI_DETALLE", schema = "SHBANCA_DIGITAL")
@NamedQueries({
        @NamedQuery(name = "PoliticaCliDetalle.findAll", query = "SELECT p FROM PoliticaCliDetalle p"),
        @NamedQuery(name = "PoliticaCliDetalle.findByIdClienteAndIdpolitica", query = "SELECT p FROM PoliticaCliDetalle p WHERE (p.idCliente = :idClient AND p.politica.idPolitica = :idPolitica)") })
public class PoliticaCliDetalle implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_DETALLE")
    private String idDetalle;

    private String atributo;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "ID_CLIENTE")
    private java.math.BigDecimal idCliente;

    private String tipo;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    private String valor;

    // bi-directional many-to-one association to Politica
    @ManyToOne
    @JoinColumn(name = "ID_POLITICA")
    private Politica politica;

    public PoliticaCliDetalle() {
    }

    public String getIdDetalle() {
        return this.idDetalle;
    }

    public void setIdDetalle(String idDetalle) {
        this.idDetalle = idDetalle;
    }

    public String getAtributo() {
        return this.atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
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

    public java.math.BigDecimal getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(java.math.BigDecimal idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public Politica getPolitica() {
        return this.politica;
    }

    public void setPolitica(Politica politica) {
        this.politica = politica;
    }

}