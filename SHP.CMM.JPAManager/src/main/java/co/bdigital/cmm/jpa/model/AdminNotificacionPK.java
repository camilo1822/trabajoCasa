package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ADMIN_NOTIFICACION database table.
 * 
 */
@Embeddable
public class AdminNotificacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="TRN_ID")
	private String trnId;

	@Column(name="CLIENTE_ID", insertable=false, updatable=false)
	private long clienteId;

	public AdminNotificacionPK() {
	}
	public String getTrnId() {
		return this.trnId;
	}
	public void setTrnId(String trnId) {
		this.trnId = trnId;
	}
	public long getClienteId() {
		return this.clienteId;
	}
	public void setClienteId(long clienteId) {
		this.clienteId = clienteId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AdminNotificacionPK)) {
			return false;
		}
		AdminNotificacionPK castOther = (AdminNotificacionPK)other;
		return 
			this.trnId.equals(castOther.trnId)
			&& (this.clienteId == castOther.clienteId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.trnId.hashCode();
		hash = hash * prime + ((int) (this.clienteId ^ (this.clienteId >>> 32)));
		
		return hash;
	}
}