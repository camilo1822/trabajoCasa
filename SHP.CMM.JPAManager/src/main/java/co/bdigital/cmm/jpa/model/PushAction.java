package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the PUSH_ACTION database table.
 * 
 */
@Entity
@Table(name = "PUSH_ACTION", schema = "SHBANCA_DIGITAL")
public class PushAction implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PUSH_ACTION_ID")
    private long pushActionId;

    @Column(name = "TIPO_PUSH_ACTION")
    private String tipoPushAction;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    @Column(name = "TICKET_ID")
    private String ticketId;

    @Column(name = "TRAZABILITY_CODE")
    private String trazabilityCode;

    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID")
    private Cliente clienteId;

    @ManyToOne
    @JoinColumn(name = "ESTADO_PUSH_ACTION_ID")
    private EstadoPushAction estadoPushActionId;

    public PushAction() {
    }

    public long getPushActionId() {
        return pushActionId;
    }

    public void setPushActionId(long pushActionId) {
        this.pushActionId = pushActionId;
    }

    public String getTipoPushAction() {
        return tipoPushAction;
    }

    public void setTipoPushAction(String tipoPushAction) {
        this.tipoPushAction = tipoPushAction;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    /**
     * @return the ticketId
     */
    public String getTicketId() {
        return ticketId;
    }

    /**
     * @param ticketId
     *            the ticketId to set
     */
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

    public EstadoPushAction getEstadoPushActionId() {
        return estadoPushActionId;
    }

    public void setEstadoPushActionId(EstadoPushAction estadoPushActionId) {
        this.estadoPushActionId = estadoPushActionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * @return the trazabilityCode
     */
    public String getTrazabilityCode() {
        return trazabilityCode;
    }

    /**
     * @param trazabilityCode
     *            the trazabilityCode to set
     */
    public void setTrazabilityCode(String trazabilityCode) {
        this.trazabilityCode = trazabilityCode;
    }

}