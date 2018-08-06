package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the BILLETERO database table.
 * 
 */
@Entity
@Table(name="BILLETERO", schema="SHBANCA_DIGITAL")
@NamedQuery(name="Billetero.findAll", query="SELECT b FROM Billetero b")
public class Billetero implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BILLETERO_BILLETEROID_GENERATOR", sequenceName="BILLETERO_ID_SEQ1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BILLETERO_BILLETEROID_GENERATOR")
	@Column(name="BILLETERO_ID")
	private long billeteroId;

	private String descripcion;

	private String estado;

	private String nombre;

	private BigDecimal saldo;

	@Column(name="TIPO_BILLETERO")
	private String tipoBilletero;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="CLIENTE_ID")
	private Cliente cliente;

	public Billetero() {
	}

	public long getBilleteroId() {
		return this.billeteroId;
	}

	public void setBilleteroId(long billeteroId) {
		this.billeteroId = billeteroId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getSaldo() {
		return this.saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getTipoBilletero() {
		return this.tipoBilletero;
	}

	public void setTipoBilletero(String tipoBilletero) {
		this.tipoBilletero = tipoBilletero;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}