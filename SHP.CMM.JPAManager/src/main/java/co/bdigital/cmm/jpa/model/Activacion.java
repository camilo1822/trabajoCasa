package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the ACTIVACION database table.
 * 
 */
@Entity
@Table(name="ACTIVACION", schema="SHBANCA_DIGITAL")
@NamedQuery(name="Activacion.findAll", query="SELECT a FROM Activacion a")
public class Activacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACTIVACION_ACTIVACIONID_GENERATOR", sequenceName="ACTIVACION_ID_SEQ1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACTIVACION_ACTIVACIONID_GENERATOR")
	@Column(name="ACTIVACION_ID")
	private long activacionId;
	
	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="CLIENTE_ID")
	private Cliente cliente;

	@Column(name="ESTADO_EMAIL")
	private BigDecimal estadoEmail;

	@Column(name="FECHA_ACTIVACION_EMAIL")
	private Timestamp fechaActivacionEmail;

	@Column(name="FECHA_CREACION_EMAIL")
	private Timestamp fechaCreacionEmail;

	@Column(name="TOKEN_EMAIL")
	private String tokenEmail;

	public Activacion() {
	}

	public long getActivacionId() {
		return this.activacionId;
	}

	public void setActivacionId(long activacionId) {
		this.activacionId = activacionId;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getEstadoEmail() {
		return this.estadoEmail;
	}

	public void setEstadoEmail(BigDecimal estadoEmail) {
		this.estadoEmail = estadoEmail;
	}

	public Timestamp getFechaActivacionEmail() {
		return this.fechaActivacionEmail;
	}

	public void setFechaActivacionEmail(Timestamp fechaActivacionEmail) {
		this.fechaActivacionEmail = fechaActivacionEmail;
	}

	public Timestamp getFechaCreacionEmail() {
		return this.fechaCreacionEmail;
	}

	public void setFechaCreacionEmail(Timestamp fechaCreacionEmail) {
		this.fechaCreacionEmail = fechaCreacionEmail;
	}

	public String getTokenEmail() {
		return this.tokenEmail;
	}

	public void setTokenEmail(String tokenEmail) {
		this.tokenEmail = tokenEmail;
	}

}