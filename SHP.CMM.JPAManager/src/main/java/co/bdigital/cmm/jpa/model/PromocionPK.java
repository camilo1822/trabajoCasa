package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the PROMOCION database table.
 * 
 */
@Embeddable
public class PromocionPK implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "CODIGO_INTERNO_PUNTO")
    private long codigoInternoPunto;

    @Column(name = "ID_PROMOCION")
    private long idPromocion;

    public PromocionPK() {
        super();
    }

    public long getCodigoInternoPunto() {
        return this.codigoInternoPunto;
    }

    public void setCodigoInternoPunto(long codigoInternoPunto) {
        this.codigoInternoPunto = codigoInternoPunto;
    }

    public long getIdPromocion() {
        return this.idPromocion;
    }

    public void setIdPromocion(long idPromocion) {
        this.idPromocion = idPromocion;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PromocionPK)) {
            return false;
        }
        PromocionPK castOther = (PromocionPK) other;
        return (this.codigoInternoPunto == castOther.codigoInternoPunto)
                && (this.idPromocion == castOther.idPromocion);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash
                * prime
                + ((int) (this.codigoInternoPunto ^ (this.codigoInternoPunto >>> 32)));
        hash = hash * prime
                + ((int) (this.idPromocion ^ (this.idPromocion >>> 32)));

        return hash;
    }
}