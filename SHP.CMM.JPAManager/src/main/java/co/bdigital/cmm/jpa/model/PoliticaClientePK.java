package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the POLITICA_CLIENTE database table.
 * 
 */
@Embeddable
public class PoliticaClientePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_POLITICA", insertable=false, updatable=false)
	private String idPolitica;

	@Column(name="ID_CLIENTE", insertable=false, updatable=false)
	private long idCliente;

	public PoliticaClientePK() {
	}
	public String getIdPolitica() {
		return this.idPolitica;
	}
	public void setIdPolitica(String idPolitica) {
		this.idPolitica = idPolitica;
	}
	public long getIdCliente() {
		return this.idCliente;
	}
	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PoliticaClientePK)) {
			return false;
		}
		PoliticaClientePK castOther = (PoliticaClientePK)other;
		return 
			this.idPolitica.equals(castOther.idPolitica)
			&& (this.idCliente == castOther.idCliente);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idPolitica.hashCode();
		hash = hash * prime + ((int) (this.idCliente ^ (this.idCliente >>> 32)));
		
		return hash;
	}
}