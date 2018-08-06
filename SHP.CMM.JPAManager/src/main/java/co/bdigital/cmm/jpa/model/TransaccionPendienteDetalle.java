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
 * The persistent class for the TRANSACCION_PENDIENTE_DETALLE database table.
 * 
 */
@Entity
@Table(name = "TRANSACCION_PENDIENTE_DETALLE", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "TransaccionPendienteDetalle.findAll", query = "SELECT t FROM TransaccionPendienteDetalle t")
public class TransaccionPendienteDetalle implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TransaccionPendienteDetallePK id;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    private String valor;

    // bi-directional many-to-one association to TransaccionPendiente
    @ManyToOne
    @JoinColumn(name = "TRANSACCION_PENDIENTE_ID")
    private TransaccionPendiente transaccionPendiente;

    public TransaccionPendienteDetalle() {
    }

    public TransaccionPendienteDetallePK getId() {
        return this.id;
    }

    public void setId(TransaccionPendienteDetallePK id) {
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

    public TransaccionPendiente getTransaccionPendiente() {
        return this.transaccionPendiente;
    }

    public void setTransaccionPendiente(
            TransaccionPendiente transaccionPendiente) {
        this.transaccionPendiente = transaccionPendiente;
    }

}