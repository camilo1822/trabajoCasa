package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the REL_DEV_OP database table.
 * 
 */
@Embeddable
public class RelDevOpPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="DISPOSITIVO_DEV_ID", insertable=false, updatable=false)
	private String dispositivoDevId;

	@Column(name="OPERADOR_OP_ID", insertable=false, updatable=false)
	private long operadorOpId;

	public RelDevOpPK() {
	}
	public String getDispositivoDevId() {
		return this.dispositivoDevId;
	}
	public void setDispositivoDevId(String dispositivoDevId) {
		this.dispositivoDevId = dispositivoDevId;
	}
	public long getOperadorOpId() {
		return this.operadorOpId;
	}
	public void setOperadorOpId(long operadorOpId) {
		this.operadorOpId = operadorOpId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelDevOpPK)) {
			return false;
		}
		RelDevOpPK castOther = (RelDevOpPK)other;
		return 
			this.dispositivoDevId.equals(castOther.dispositivoDevId)
			&& (this.operadorOpId == castOther.operadorOpId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.dispositivoDevId.hashCode();
		hash = hash * prime + ((int) (this.operadorOpId ^ (this.operadorOpId >>> 32)));
		
		return hash;
	}
}