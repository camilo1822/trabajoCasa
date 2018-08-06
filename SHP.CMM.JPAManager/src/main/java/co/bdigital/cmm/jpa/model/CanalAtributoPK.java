package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CANAL_ATRIBUTO database table.
 * 
 */
@Embeddable
public class CanalAtributoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CANAL_ID", insertable=false, updatable=false)
	private String canalId;

	private String codigo;

	public CanalAtributoPK() {
	}
	public String getCanalId() {
		return this.canalId;
	}
	public void setCanalId(String canalId) {
		this.canalId = canalId;
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
		if (!(other instanceof CanalAtributoPK)) {
			return false;
		}
		CanalAtributoPK castOther = (CanalAtributoPK)other;
		return 
			this.canalId.equals(castOther.canalId)
			&& this.codigo.equals(castOther.codigo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.canalId.hashCode();
		hash = hash * prime + this.codigo.hashCode();
		
		return hash;
	}
}