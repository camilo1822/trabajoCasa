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
 * The persistent class for the IMAGEN_S3 database table.
 * 
 */
@Entity
@Table(name = "IMAGEN_S3", schema = "SHBANCA_DIGITAL")
@NamedQueries({
        @NamedQuery(name = "ImagenS3.findAll", query = "SELECT i FROM ImagenS3 i"),
        @NamedQuery(name = "ImagenS3.findByClienteClienteId", query = "SELECT i FROM ImagenS3 i WHERE i.cliente.clienteId = :clienteId") })
public class ImagenS3 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IMAGEN_ID")
    private String imagenId;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "TIPO_IMAGEN")
    private String tipoImagen;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    // bi-directional many-to-one association to Cliente
    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID")
    private Cliente cliente;

    // bi-directional many-to-one association to EstadoImagen
    @ManyToOne
    @JoinColumn(name = "ESTADO_IMAGEN_ID")
    private EstadoImagen estadoImagen;

    public ImagenS3() {
    }

    public String getImagenId() {
        return this.imagenId;
    }

    public void setImagenId(String imagenId) {
        this.imagenId = imagenId;
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

    public String getTipoImagen() {
        return this.tipoImagen;
    }

    public void setTipoImagen(String tipoImagen) {
        this.tipoImagen = tipoImagen;
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

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public EstadoImagen getEstadoImagen() {
        return this.estadoImagen;
    }

    public void setEstadoImagen(EstadoImagen estadoImagen) {
        this.estadoImagen = estadoImagen;
    }

}