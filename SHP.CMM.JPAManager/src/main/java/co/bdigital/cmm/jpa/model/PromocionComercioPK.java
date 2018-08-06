package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PROMOCION_COMERCIO database table.
 * 
 */
@Embeddable
public class PromocionComercioPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="COMERCIO_ID")
	private String comercioId;

	@Column(name="TERMINAL_ID")
	private String terminalId;

	@Column(name="PROMOCION_OPERACION_ID", insertable=false, updatable=false)
	private long promocionOperacionId;

	public PromocionComercioPK() {
	}
	public String getComercioId() {
		return this.comercioId;
	}
	public void setComercioId(String comercioId) {
		this.comercioId = comercioId;
	}
	public String getTerminalId() {
		return this.terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public long getPromocionOperacionId() {
		return this.promocionOperacionId;
	}
	public void setPromocionOperacionId(long promocionOperacionId) {
		this.promocionOperacionId = promocionOperacionId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PromocionComercioPK)) {
			return false;
		}
		PromocionComercioPK castOther = (PromocionComercioPK)other;
		return 
			this.comercioId.equals(castOther.comercioId)
			&& this.terminalId.equals(castOther.terminalId)
			&& (this.promocionOperacionId == castOther.promocionOperacionId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.comercioId.hashCode();
		hash = hash * prime + this.terminalId.hashCode();
		hash = hash * prime + ((int) (this.promocionOperacionId ^ (this.promocionOperacionId >>> 32)));
		
		return hash;
	}
}