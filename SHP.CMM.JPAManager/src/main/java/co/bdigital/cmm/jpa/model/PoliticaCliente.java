package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the POLITICA_CLIENTE database table.
 * 
 */
@Entity
@Table(name = "POLITICA_CLIENTE", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "PoliticaCliente.findAll", query = "SELECT p FROM PoliticaCliente p")
public class PoliticaCliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PoliticaClientePK id;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    // bi-directional many-to-one association to Politica
    @ManyToOne
    @JoinColumn(name = "ID_POLITICA")
    private Politica politica;

    public PoliticaCliente() {
    }

    public PoliticaClientePK getId() {
        return this.id;
    }

    public void setId(PoliticaClientePK id) {
        this.id = id;
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

    public Politica getPolitica() {
        return this.politica;
    }

    public void setPolitica(Politica politica) {
        this.politica = politica;
    }

}