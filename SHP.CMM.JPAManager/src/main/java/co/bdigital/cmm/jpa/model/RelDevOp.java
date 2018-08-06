package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the REL_DEV_OP database table.
 * 
 */
@Entity
@Table(name="REL_DEV_OP", schema="SHBANCA_DIGITAL")
@NamedQuery(name="RelDevOp.findAll", query="SELECT r FROM RelDevOp r")
public class RelDevOp implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RelDevOpPK id;

	//bi-directional many-to-one association to Dispositivo
	@ManyToOne
	private Dispositivo dispositivo;

	public RelDevOp() {
	}

	public RelDevOpPK getId() {
		return this.id;
	}

	public void setId(RelDevOpPK id) {
		this.id = id;
	}

	public Dispositivo getDispositivo() {
		return this.dispositivo;
	}

	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

}