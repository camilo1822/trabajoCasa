package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the SEGURIDAD_DFH database table.
 * 
 */
@Entity
@Table(name="SEGURIDAD_DFH", schema="SHBANCA_DIGITAL")
@NamedQuery(name="SeguridadDfh.findAll", query="SELECT s FROM SeguridadDfh s")
public class SeguridadDfh implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEGURIDAD_DFH_SEGURIDADDFHID_GENERATOR", sequenceName="SHBANCA_DIGITAL.SEGURIDAD_DFH_ID_SEQ1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEGURIDAD_DFH_SEGURIDADDFHID_GENERATOR")
	@Column(name="SEGURIDAD_DFH_ID")
	private long seguridadDfhId;
	
	private String celular;

	@Column(name = "CELULAR_CCIO")
	private String celularCcio;

	@Column(name="CLAVE_PUBLICA_CLIENTE")
	private String clavePublicaCliente;

	@Column(name="CLAVE_PUBLICA_SERVIDOR")
	private String clavePublicaServidor;

	@Column(name="CLAVE_SECRETA")
	private String claveSecreta;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_MODIFICACION")
	private Timestamp fechaModificacion;

	@Column(name="USR_CREACION")
	private String usrCreacion;

	@Column(name="USR_MODIFICACION")
	private String usrModificacion;

	//bi-directional one-to-one association to Cliente
	@OneToOne
	@JoinColumn(name="CELULAR", referencedColumnName="CELULAR")
	private Cliente cliente;

	//bi-directional one-to-one association to Dispositivo
	@OneToOne
	@JoinColumn(name="CELULAR_CCIO", referencedColumnName="CELULAR")
	private Dispositivo dispositivo;

	public SeguridadDfh() {
	}

	public long getSeguridadDfhId() {
		return this.seguridadDfhId;
	}

	public void setSeguridadDfhId(long seguridadDfhId) {
		this.seguridadDfhId = seguridadDfhId;
	}

	public String getClavePublicaCliente() {
		return this.clavePublicaCliente;
	}

	public void setClavePublicaCliente(String clavePublicaCliente) {
		this.clavePublicaCliente = clavePublicaCliente;
	}

	public String getClavePublicaServidor() {
		return this.clavePublicaServidor;
	}

	public void setClavePublicaServidor(String clavePublicaServidor) {
		this.clavePublicaServidor = clavePublicaServidor;
	}

	public String getClaveSecreta() {
		return this.claveSecreta;
	}

	public void setClaveSecreta(String claveSecreta) {
		this.claveSecreta = claveSecreta;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getUsrCreacion() {
		return this.usrCreacion;
	}

	public void setUsrCreacion(String usrCreacion) {
		this.usrCreacion = usrCreacion;
	}

	public String getUsrModificacion() {
		return this.usrModificacion;
	}

	public void setUsrModificacion(String usrModificacion) {
		this.usrModificacion = usrModificacion;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Dispositivo getDispositivo() {
		return this.dispositivo;
	}

	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCelularCcio() {
		return celularCcio;
	}

	public void setCelularCcio(String celularCcio) {
		this.celularCcio = celularCcio;
	}

}