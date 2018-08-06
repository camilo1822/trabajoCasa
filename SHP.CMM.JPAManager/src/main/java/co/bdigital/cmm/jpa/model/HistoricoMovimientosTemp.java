package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the HISTORICO_MOVIMIENTOS_TEMP database table.
 * 
 */
@Entity
@Table(name="HISTORICO_MOVIMIENTOS_TEMP", schema="SHBANCA_DIGITAL")
@NamedQuery(name="HistoricoMovimientosTemp.findAll", query="SELECT h FROM HistoricoMovimientosTemp h")
public class HistoricoMovimientosTemp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HISTORICO_MOVIMIENTOS_ID")
	private String historicoMovimientosId;

	private String canal;

	private String celular;
	
	@Column(name="NUMERO_CUENTA")
	private String numeroCuenta;

	@Column(name="CODIGO_TRANSACCION")
	private String codigoTransaccion;

	@Column(name="DETALLE_TRANSACCION")
	private String detalleTransaccion;

	@Column(name="FECHA_OPERACION")
	private Timestamp fechaOperacion;

	@Column(name="FECHA_TRANSACCION")
	private Timestamp fechaTransaccion;

	@Column(name="ID_TRANSACCION")
	private String idTransaccion;

	@Column(name="NOMBRE_TRANSACCION")
	private String nombreTransaccion;

	private String valor;

	public HistoricoMovimientosTemp() {
	}

	public String getHistoricoMovimientosId() {
		return this.historicoMovimientosId;
	}

	public void setHistoricoMovimientosId(String historicoMovimientosId) {
		this.historicoMovimientosId = historicoMovimientosId;
	}

	public String getCanal() {
		return this.canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCodigoTransaccion() {
		return this.codigoTransaccion;
	}

	public void setCodigoTransaccion(String codigoTransaccion) {
		this.codigoTransaccion = codigoTransaccion;
	}

	public String getDetalleTransaccion() {
		return this.detalleTransaccion;
	}

	public void setDetalleTransaccion(String detalleTransaccion) {
		this.detalleTransaccion = detalleTransaccion;
	}

	public Timestamp getFechaOperacion() {
		return this.fechaOperacion;
	}

	public void setFechaOperacion(Timestamp fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public Timestamp getFechaTransaccion() {
		return this.fechaTransaccion;
	}

	public void setFechaTransaccion(Timestamp fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}

	public String getIdTransaccion() {
		return this.idTransaccion;
	}

	public void setIdTransaccion(String idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public String getNombreTransaccion() {
		return this.nombreTransaccion;
	}

	public void setNombreTransaccion(String nombreTransaccion) {
		this.nombreTransaccion = nombreTransaccion;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getNumeroCuenta() {
		return this.numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
}