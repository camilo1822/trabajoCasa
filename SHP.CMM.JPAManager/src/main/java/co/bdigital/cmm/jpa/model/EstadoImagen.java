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
 * The persistent class for the ESTADO_IMAGEN database table.
 * 
 */
@Entity
@Table(name = "ESTADO_IMAGEN", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "EstadoImagen.findAll", query = "SELECT e FROM EstadoImagen e")
public class EstadoImagen implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ESTADO_IMAGEN_ID")
    private long estadoImagenId;

    private String descripcion;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    // bi-directional many-to-one association to ImagenS3
    @OneToMany(mappedBy = "estadoImagen")
    private List<ImagenS3> imagenS3s;

    public EstadoImagen() {
    }

    public long getEstadoImagenId() {
        return this.estadoImagenId;
    }

    public void setEstadoImagenId(long estadoImagenId) {
        this.estadoImagenId = estadoImagenId;
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

    public List<ImagenS3> getImagenS3s() {
        return this.imagenS3s;
    }

    public void setImagenS3s(List<ImagenS3> imagenS3s) {
        this.imagenS3s = imagenS3s;
    }

    public ImagenS3 addImagenS3(ImagenS3 imagenS3) {
        getImagenS3s().add(imagenS3);
        imagenS3.setEstadoImagen(this);

        return imagenS3;
    }

    public ImagenS3 removeImagenS3(ImagenS3 imagenS3) {
        getImagenS3s().remove(imagenS3);
        imagenS3.setEstadoImagen(null);

        return imagenS3;
    }

}