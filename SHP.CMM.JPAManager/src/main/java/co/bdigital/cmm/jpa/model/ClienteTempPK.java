package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the CLIENTE_TEMP database table.
 * 
 */
@Embeddable
public class ClienteTempPK implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "CELULAR", insertable = false, updatable = false)
    private String celular;

    @Column(name = "PAIS_ID", insertable = false, updatable = false)
    private String divisionGeografica;

    public ClienteTempPK() {
    }

    /**
     * @return the celular
     */
    public String getCelular() {
        return celular;
    }

    /**
     * @param celular
     *            the celular to set
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * @return the divisionGeografica
     */
    public String getDivisionGeografica() {
        return divisionGeografica;
    }

    /**
     * @param divisionGeografica
     *            the divisionGeografica to set
     */
    public void setDivisionGeografica(String divisionGeografica) {
        this.divisionGeografica = divisionGeografica;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ClienteTempPK)) {
            return false;
        }
        ClienteTempPK castOther = (ClienteTempPK) other;
        return (this.celular == castOther.celular)
                && (this.divisionGeografica == castOther.divisionGeografica);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + celular.hashCode();
        hash = hash * prime + divisionGeografica.hashCode();

        return hash;
    }
}