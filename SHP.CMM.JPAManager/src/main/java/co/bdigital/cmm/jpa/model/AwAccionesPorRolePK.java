package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the AW_ACCIONES_POR_ROLES database table.
 * 
 */
@Embeddable
public class AwAccionesPorRolePK implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "ID_ACCION", insertable = true, updatable = true)
    private long idAccion;

    @Column(name = "ID_ROL", insertable = true, updatable = true)
    private long idRol;

    public AwAccionesPorRolePK() {
    }

    public long getIdAccion() {
        return this.idAccion;
    }

    public void setIdAccion(long idAccion) {
        this.idAccion = idAccion;
    }

    public long getIdRol() {
        return this.idRol;
    }

    public void setIdRol(long idRol) {
        this.idRol = idRol;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AwAccionesPorRolePK)) {
            return false;
        }
        AwAccionesPorRolePK castOther = (AwAccionesPorRolePK) other;
        return (this.idAccion == castOther.idAccion)
                && (this.idRol == castOther.idRol);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + ((int) (this.idAccion ^ (this.idAccion >>> 32)));
        hash = hash * prime + ((int) (this.idRol ^ (this.idRol >>> 32)));

        return hash;
    }
}