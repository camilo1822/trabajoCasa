package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the INTENTOS database table.
 * 
 */
@Embeddable
public class IntentoPK implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "CELULAR")
    private String celular;

    @Column(name = "TIPO_ACCESO")
    private String tipoAcceso;

    public IntentoPK() {
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTipoAcceso() {
        return this.tipoAcceso;
    }

    public void setTipoAcceso(String tipoAcceso) {
        this.tipoAcceso = tipoAcceso;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IntentoPK)) {
            return false;
        }
        IntentoPK castOther = (IntentoPK) other;
        return this.celular.equals(castOther.celular)
                && this.tipoAcceso.equals(castOther.tipoAcceso);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.celular.hashCode();
        hash = hash * prime + this.tipoAcceso.hashCode();

        return hash;
    }
}