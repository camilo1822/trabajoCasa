package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the CONTROL_TRANSACCION database table.
 * 
 */
@Entity
@Table(name="CONTROL_TRANSACCION", schema="SHBANCA_DIGITAL")
@NamedQuery(name="ControlTransaccion.findAll", query="SELECT c FROM ControlTransaccion c")
public class ControlTransaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TRANSACCION_ID")
	private String transaccionId;

	private String celular;

	private BigDecimal estado;

	@Column(name="FECHA_MODIFICACION")
	private Timestamp fechaModificacion;

	@Column(name="FECHA_TRANSACCION")
	private Timestamp fechaTransaccion;

	private String operacion;

	public ControlTransaccion() {
	}

	public String getTransaccionId() {
		return this.transaccionId;
	}

	public void setTransaccionId(String transaccionId) {
		this.transaccionId = transaccionId;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Timestamp getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Timestamp getFechaTransaccion() {
		return this.fechaTransaccion;
	}

	public void setFechaTransaccion(Timestamp fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}

	public String getOperacion() {
		return this.operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

}