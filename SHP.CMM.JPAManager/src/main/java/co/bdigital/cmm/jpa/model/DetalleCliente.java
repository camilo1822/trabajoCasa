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
 * The persistent class for the DETALLE_CLIENTE database table.
 * 
 */
@Entity
@Table(name = "DETALLE_CLIENTE", schema = "SHBANCA_DIGITAL")
@NamedQueries({
        @NamedQuery(name = "DetalleCliente.findAll", query = "SELECT d FROM DetalleCliente d"),
        @NamedQuery(name = "DetalleCliente.findByIdClientAndName", query = "SELECT d FROM DetalleCliente d WHERE d.id.idCliente=:idClient AND UPPER(d.id.nombre)=UPPER(:name)") })
public class DetalleCliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DetalleClientePK id;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    private String valor;

    // uni-directional many-to-one association to Cliente
    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente idCliente;

    public DetalleCliente() {
    }

    public DetalleClientePK getId() {
        return this.id;
    }

    public void setId(DetalleClientePK id) {
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

    public String getValor() {
        return this.valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * @return the idCliente
     */
    public Cliente getIdCliente() {
        return idCliente;
    }

    /**
     * @param idCliente
     *            the idCliente to set
     */
    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

}