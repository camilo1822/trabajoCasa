package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the REGISTRO_CLIENTE database table.
 * 
 */
@Embeddable
public class RegistroClientePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CLIENTE_ID", insertable=false, updatable=false)
	private long clienteId;

	private String llave;

	@Column(name="REGISTRO_CATEGORIA_ID", insertable=false, updatable=false)
	private long registroCategoriaId;

	public RegistroClientePK() {
	}
	public long getClienteId() {
		return this.clienteId;
	}
	public void setClienteId(long clienteId) {
		this.clienteId = clienteId;
	}
	public String getLlave() {
		return this.llave;
	}
	public void setLlave(String llave) {
		this.llave = llave;
	}
	public long getRegistroCategoriaId() {
		return this.registroCategoriaId;
	}
	public void setRegistroCategoriaId(long registroCategoriaId) {
		this.registroCategoriaId = registroCategoriaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RegistroClientePK)) {
			return false;
		}
		RegistroClientePK castOther = (RegistroClientePK)other;
		return 
			(this.clienteId == castOther.clienteId)
			&& this.llave.equals(castOther.llave)
			&& (this.registroCategoriaId == castOther.registroCategoriaId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.clienteId ^ (this.clienteId >>> 32)));
		hash = hash * prime + this.llave.hashCode();
		hash = hash * prime + ((int) (this.registroCategoriaId ^ (this.registroCategoriaId >>> 32)));
		
		return hash;
	}
}