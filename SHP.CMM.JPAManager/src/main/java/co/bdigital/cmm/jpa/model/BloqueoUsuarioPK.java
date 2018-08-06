package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the BLOQUEO_USUARIO database table.
 * 
 */
@Embeddable
public class BloqueoUsuarioPK implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "CLIENTE_ID", insertable = false, updatable = false)
    private long clienteId;

    @Column(name = "TIPO_BLOQUEO")
    private String tipoBloqueo;

    public BloqueoUsuarioPK() {
    }

    public long getClienteId() {
        return this.clienteId;
    }

    public void setClienteId(long clienteId) {
        this.clienteId = clienteId;
    }

    public String getTipoBloqueo() {
        return this.tipoBloqueo;
    }

    public void setTipoBloqueo(String tipoBloqueo) {
        this.tipoBloqueo = tipoBloqueo;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BloqueoUsuarioPK)) {
            return false;
        }
        BloqueoUsuarioPK castOther = (BloqueoUsuarioPK) other;
        return (this.clienteId == castOther.clienteId)
                && this.tipoBloqueo.equals(castOther.tipoBloqueo);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime
                + ((int) (this.clienteId ^ (this.clienteId >>> 32)));
        hash = hash * prime + this.tipoBloqueo.hashCode();

        return hash;
    }
}