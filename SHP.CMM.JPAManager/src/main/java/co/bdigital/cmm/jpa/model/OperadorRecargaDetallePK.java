package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the OPERADOR_RECARGA_DETALLE database table.
 * 
 */
@Embeddable
public class OperadorRecargaDetallePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_OPERADOR", insertable=false, updatable=false)
	private String idOperador;

	@Column(name="TIPO_OPERADOR", insertable=false, updatable=false)
	private String tipoOperador;

	@Column(name="OPERADOR_LLAVE")
	private String operadorLlave;

	public OperadorRecargaDetallePK() {
	}
	public String getIdOperador() {
		return this.idOperador;
	}
	public void setIdOperador(String idOperador) {
		this.idOperador = idOperador;
	}
	public String getTipoOperador() {
		return this.tipoOperador;
	}
	public void setTipoOperador(String tipoOperador) {
		this.tipoOperador = tipoOperador;
	}
	public String getOperadorLlave() {
		return this.operadorLlave;
	}
	public void setOperadorLlave(String operadorLlave) {
		this.operadorLlave = operadorLlave;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OperadorRecargaDetallePK)) {
			return false;
		}
		OperadorRecargaDetallePK castOther = (OperadorRecargaDetallePK)other;
		return 
			this.idOperador.equals(castOther.idOperador)
			&& this.tipoOperador.equals(castOther.tipoOperador)
			&& this.operadorLlave.equals(castOther.operadorLlave);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idOperador.hashCode();
		hash = hash * prime + this.tipoOperador.hashCode();
		hash = hash * prime + this.operadorLlave.hashCode();
		
		return hash;
	}
}