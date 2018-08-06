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
 * The persistent class for the TRANSACCION_GRUPO database table.
 * 
 */
@Entity
@Table(name = "TRANSACCION_GRUPO", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "TransaccionGrupo.findAll", query = "SELECT t FROM TransaccionGrupo t")
public class TransaccionGrupo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TRANSACCION_GRUPO_ID")
    private long transaccionGrupoId;

    private String estado;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    private String nombre;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    // bi-directional many-to-one association to TransaccionPendiente
    @OneToMany(mappedBy = "transaccionGrupo")
    private List<TransaccionPendiente> transaccionPendientes;

    public TransaccionGrupo() {
    }

    public long getTransaccionGrupoId() {
        return this.transaccionGrupoId;
    }

    public void setTransaccionGrupoId(long transaccionGrupoId) {
        this.transaccionGrupoId = transaccionGrupoId;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public List<TransaccionPendiente> getTransaccionPendientes() {
        return this.transaccionPendientes;
    }

    public void setTransaccionPendientes(
            List<TransaccionPendiente> transaccionPendientes) {
        this.transaccionPendientes = transaccionPendientes;
    }

    public TransaccionPendiente addTransaccionPendiente(
            TransaccionPendiente transaccionPendiente) {
        getTransaccionPendientes().add(transaccionPendiente);
        transaccionPendiente.setTransaccionGrupo(this);

        return transaccionPendiente;
    }

    public TransaccionPendiente removeTransaccionPendiente(
            TransaccionPendiente transaccionPendiente) {
        getTransaccionPendientes().remove(transaccionPendiente);
        transaccionPendiente.setTransaccionGrupo(null);

        return transaccionPendiente;
    }

}