package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the CORREO_ADMIN database table.
 * 
 */
@Embeddable
public class CorreoAdminPK implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "CORREO", insertable = false, updatable = false)
    private String correo;

    @Column(name = "TIPO", insertable = false, updatable = false)
    private String tipo;

    public CorreoAdminPK() {
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo
     *            the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo
     *            the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CorreoAdminPK)) {
            return false;
        }
        CorreoAdminPK castOther = (CorreoAdminPK) other;
        return (this.correo == castOther.correo)
                && (this.tipo == castOther.tipo);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + correo.hashCode();
        hash = hash * prime + tipo.hashCode();

        return hash;
    }
}