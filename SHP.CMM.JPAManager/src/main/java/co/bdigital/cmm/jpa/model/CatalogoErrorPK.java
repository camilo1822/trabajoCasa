package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CATALOGO_ERROR database table.
 * 
 */
@Embeddable
public class CatalogoErrorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="SISTEMA_ID", insertable=false, updatable=false)
	private long sistemaId;

	private String codigo;

	public CatalogoErrorPK() {
	}
	public long getSistemaId() {
		return this.sistemaId;
	}
	public void setSistemaId(long sistemaId) {
		this.sistemaId = sistemaId;
	}
	public String getCodigo() {
		return this.codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CatalogoErrorPK)) {
			return false;
		}
		CatalogoErrorPK castOther = (CatalogoErrorPK)other;
		return 
			(this.sistemaId == castOther.sistemaId)
			&& this.codigo.equals(castOther.codigo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.sistemaId ^ (this.sistemaId >>> 32)));
		hash = hash * prime + this.codigo.hashCode();
		
		return hash;
	}
}