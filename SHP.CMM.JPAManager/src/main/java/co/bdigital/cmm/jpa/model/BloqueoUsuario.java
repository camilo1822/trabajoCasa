package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the BLOQUEO_USUARIO database table.
 * 
 */
@Entity
@Table(name = "BLOQUEO_USUARIO", schema = "SHBANCA_DIGITAL")
@NamedQueries({
        @NamedQuery(name = "BloqueoUsuario.findAll", query = "SELECT b FROM BloqueoUsuario b"),
        @NamedQuery(name = "BloqueoUsuario.findByClientId", query = "SELECT e FROM BloqueoUsuario e WHERE e.id.clienteId = :clienteId") })
public class BloqueoUsuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private BloqueoUsuarioPK id;

    @Column(name = "FECHA_BLOQUEO")
    private Timestamp fechaBloqueo;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    // bi-directional many-to-one association to Cliente
    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID")
    private Cliente cliente;

    public BloqueoUsuario() {
    }

    public BloqueoUsuarioPK getId() {
        return this.id;
    }

    public void setId(BloqueoUsuarioPK id) {
        this.id = id;
    }

    public Timestamp getFechaBloqueo() {
        return this.fechaBloqueo;
    }

    public void setFechaBloqueo(Timestamp fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    public Timestamp getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsrCreacion() {
        return this.usrCreacion;
    }

    public void setUsrCreacion(String usrCreacion) {
        this.usrCreacion = usrCreacion;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}