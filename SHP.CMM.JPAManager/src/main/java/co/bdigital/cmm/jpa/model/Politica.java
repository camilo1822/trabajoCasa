package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the POLITICA database table.
 * 
 */
@Entity
@Table(name = "POLITICA", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "Politica.findAll", query = "SELECT p FROM Politica p")
public class Politica implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_POLITICA")
    private String idPolitica;

    private String descripcion;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    private String nombre;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    // bi-directional many-to-one association to PoliticaCliente
    @OneToMany(mappedBy = "politica")
    private List<PoliticaCliente> politicaClientes;

    // bi-directional many-to-one association to PoliticaCliDetalle
    @OneToMany(mappedBy = "politica")
    private List<PoliticaCliDetalle> politicaCliDetalles;

    // bi-directional many-to-one association to TipoPolitica
    @OneToMany(mappedBy = "politica")
    private List<TipoPolitica> tipoPoliticas;

    public Politica() {
    }

    public String getIdPolitica() {
        return this.idPolitica;
    }

    public void setIdPolitica(String idPolitica) {
        this.idPolitica = idPolitica;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public List<PoliticaCliente> getPoliticaClientes() {
        return this.politicaClientes;
    }

    public void setPoliticaClientes(List<PoliticaCliente> politicaClientes) {
        this.politicaClientes = politicaClientes;
    }

    public PoliticaCliente addPoliticaCliente(PoliticaCliente politicaCliente) {
        getPoliticaClientes().add(politicaCliente);
        politicaCliente.setPolitica(this);

        return politicaCliente;
    }

    public PoliticaCliente removePoliticaCliente(
            PoliticaCliente politicaCliente) {
        getPoliticaClientes().remove(politicaCliente);
        politicaCliente.setPolitica(null);

        return politicaCliente;
    }

    public List<PoliticaCliDetalle> getPoliticaCliDetalles() {
        return this.politicaCliDetalles;
    }

    public void setPoliticaCliDetalles(
            List<PoliticaCliDetalle> politicaCliDetalles) {
        this.politicaCliDetalles = politicaCliDetalles;
    }

    public PoliticaCliDetalle addPoliticaCliDetalle(
            PoliticaCliDetalle politicaCliDetalle) {
        getPoliticaCliDetalles().add(politicaCliDetalle);
        politicaCliDetalle.setPolitica(this);

        return politicaCliDetalle;
    }

    public PoliticaCliDetalle removePoliticaCliDetalle(
            PoliticaCliDetalle politicaCliDetalle) {
        getPoliticaCliDetalles().remove(politicaCliDetalle);
        politicaCliDetalle.setPolitica(null);

        return politicaCliDetalle;
    }

    public List<TipoPolitica> getTipoPoliticas() {
        return this.tipoPoliticas;
    }

    public void setTipoPoliticas(List<TipoPolitica> tipoPoliticas) {
        this.tipoPoliticas = tipoPoliticas;
    }

    public TipoPolitica addTipoPolitica(TipoPolitica tipoPolitica) {
        getTipoPoliticas().add(tipoPolitica);
        tipoPolitica.setPolitica(this);

        return tipoPolitica;
    }

    public TipoPolitica removeTipoPolitica(TipoPolitica tipoPolitica) {
        getTipoPoliticas().remove(tipoPolitica);
        tipoPolitica.setPolitica(null);

        return tipoPolitica;
    }

}