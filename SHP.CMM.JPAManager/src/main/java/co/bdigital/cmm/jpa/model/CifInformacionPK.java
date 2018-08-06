package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CIF_INFORMACION database table.
 * 
 */
@Embeddable
public class CifInformacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String documento;

	@Column(name="TIPO_DOCUMENTO")
	private String tipoDocumento;

	public CifInformacionPK() {
	}
	public String getDocumento() {
		return this.documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getTipoDocumento() {
		return this.tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CifInformacionPK)) {
			return false;
		}
		CifInformacionPK castOther = (CifInformacionPK)other;
		return 
			this.documento.equals(castOther.documento)
			&& this.tipoDocumento.equals(castOther.tipoDocumento);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.documento.hashCode();
		hash = hash * prime + this.tipoDocumento.hashCode();
		
		return hash;
	}
}