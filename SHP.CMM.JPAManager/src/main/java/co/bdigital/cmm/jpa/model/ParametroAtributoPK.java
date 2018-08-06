package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the PARAMETRO_ATRIBUTO database table.
 * 
 */
@Embeddable
public class ParametroAtributoPK implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "PARAMETRO_ID", insertable = false, updatable = false)
    private String parametroId;

    @Column(name = "ATRIBUTO_ID", insertable = false, updatable = false)
    private String atributoId;

    public ParametroAtributoPK() {
    }

    public String getParametroId() {
        return this.parametroId;
    }

    public void setParametroId(String parametroId) {
        this.parametroId = parametroId;
    }

    public String getAtributoId() {
        return this.atributoId;
    }

    public void setAtributoId(String atributoId) {
        this.atributoId = atributoId;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ParametroAtributoPK)) {
            return false;
        }
        ParametroAtributoPK castOther = (ParametroAtributoPK) other;
        return (this.parametroId == castOther.parametroId)
                && (this.atributoId == castOther.atributoId);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + parametroId.hashCode();
        hash = hash * prime + atributoId.hashCode();

        return hash;
    }
}