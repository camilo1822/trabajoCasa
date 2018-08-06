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
 * The persistent class for the TIPO_OPERADOR database table.
 * 
 */
@Entity
@Table(name = "TIPO_OPERADOR", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "TipoOperador.findAll", query = "SELECT t FROM TipoOperador t")
public class TipoOperador implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TIPO_OPERADOR_ID")
    private String tipoOperadorId;

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

    // bi-directional many-to-one association to OperadorRecarga
    @OneToMany(mappedBy = "tipoOperador")
    private List<OperadorRecarga> operadorRecargas;

    public TipoOperador() {
    }

    public String getTipoOperadorId() {
        return this.tipoOperadorId;
    }

    public void setTipoOperadorId(String tipoOperadorId) {
        this.tipoOperadorId = tipoOperadorId;
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

    public List<OperadorRecarga> getOperadorRecargas() {
        return this.operadorRecargas;
    }

    public void setOperadorRecargas(List<OperadorRecarga> operadorRecargas) {
        this.operadorRecargas = operadorRecargas;
    }

    public OperadorRecarga addOperadorRecarga(OperadorRecarga operadorRecarga) {
        getOperadorRecargas().add(operadorRecarga);
        operadorRecarga.setTipoOperador(this);

        return operadorRecarga;
    }

    public OperadorRecarga removeOperadorRecarga(
            OperadorRecarga operadorRecarga) {
        getOperadorRecargas().remove(operadorRecarga);
        operadorRecarga.setTipoOperador(null);

        return operadorRecarga;
    }

}