package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the DETALLE_CLIENTE database table.
 * 
 */
@Embeddable
public class DetalleClientePK implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "ID_CLIENTE", insertable = true, updatable = true)
    private long idCliente;

    @Column(name = "NOMBRE", insertable = true, updatable = true)
    private String nombre;

    public DetalleClientePK() {
    }

    public long getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DetalleClientePK)) {
            return false;
        }
        DetalleClientePK castOther = (DetalleClientePK) other;
        return (this.idCliente == castOther.idCliente)
                && this.nombre.equals(castOther.nombre);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime
                + ((int) (this.idCliente ^ (this.idCliente >>> 32)));
        hash = hash * prime + this.nombre.hashCode();

        return hash;
    }
}