package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRANSACCION_PENDIENTE_DETALLE database table.
 * 
 */
@Embeddable
public class TransaccionPendienteDetallePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="TRANSACCION_PENDIENTE_ID", insertable=false, updatable=false)
	private long transaccionPendienteId;

	private String nombre;

	public TransaccionPendienteDetallePK() {
	}
	public long getTransaccionPendienteId() {
		return this.transaccionPendienteId;
	}
	public void setTransaccionPendienteId(long transaccionPendienteId) {
		this.transaccionPendienteId = transaccionPendienteId;
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
		if (!(other instanceof TransaccionPendienteDetallePK)) {
			return false;
		}
		TransaccionPendienteDetallePK castOther = (TransaccionPendienteDetallePK)other;
		return 
			(this.transaccionPendienteId == castOther.transaccionPendienteId)
			&& this.nombre.equals(castOther.nombre);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.transaccionPendienteId ^ (this.transaccionPendienteId >>> 32)));
		hash = hash * prime + this.nombre.hashCode();
		
		return hash;
	}
}