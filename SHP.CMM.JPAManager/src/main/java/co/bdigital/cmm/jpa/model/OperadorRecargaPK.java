package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the OPERADOR_RECARGA database table.
 * 
 */
@Embeddable
public class OperadorRecargaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_OPERADOR")
	private String idOperador;

	@Column(insertable=false, updatable=false)
	private String tipo;

	public OperadorRecargaPK() {
	}
	public String getIdOperador() {
		return this.idOperador;
	}
	public void setIdOperador(String idOperador) {
		this.idOperador = idOperador;
	}
	public String getTipo() {
		return this.tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OperadorRecargaPK)) {
			return false;
		}
		OperadorRecargaPK castOther = (OperadorRecargaPK)other;
		return 
			this.idOperador.equals(castOther.idOperador)
			&& this.tipo.equals(castOther.tipo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idOperador.hashCode();
		hash = hash * prime + this.tipo.hashCode();
		
		return hash;
	}
}