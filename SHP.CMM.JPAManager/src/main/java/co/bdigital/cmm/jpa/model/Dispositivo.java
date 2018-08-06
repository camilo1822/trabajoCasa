package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the DISPOSITIVO database table.
 * 
 */
@Entity
@Table(name="DISPOSITIVO", schema="SHBANCA_DIGITAL")
@NamedQuery(name="Dispositivo.findAll", query="SELECT d FROM Dispositivo d")
public class Dispositivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DISPOSITIVO_DEVID_GENERATOR", sequenceName="CLIENTE_ID_SEQ1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DISPOSITIVO_DEVID_GENERATOR")
	@Column(name="DEV_ID")
	private String devId;

	private String celular;
	
	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_MODIFICACION")
	private Timestamp fechaModificacion;

	@Column(name="USUARIO_CREACION")
	private String usuarioCreacion;

	@Column(name="USUARIO_MODIFICACION")
	private String usuarioModificacion;

	//bi-directional many-to-one association to Comercio
	@ManyToOne
	private Comercio comercio;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="ESTADO_EST_ID1")
	private Estado estado;

	//bi-directional many-to-one association to RelDevOp
	@OneToMany(mappedBy="dispositivo")
	private List<RelDevOp> relDevOps;

	//bi-directional one-to-one association to SeguridadDfh
	@OneToOne(mappedBy="dispositivo")
	private SeguridadDfh seguridadDfh;

	public Dispositivo() {
	}

	public String getDevId() {
		return this.devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
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

	public String getUsuarioCreacion() {
		return this.usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public String getUsuarioModificacion() {
		return this.usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Comercio getComercio() {
		return this.comercio;
	}

	public void setComercio(Comercio comercio) {
		this.comercio = comercio;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<RelDevOp> getRelDevOps() {
		return this.relDevOps;
	}

	public void setRelDevOps(List<RelDevOp> relDevOps) {
		this.relDevOps = relDevOps;
	}

	public RelDevOp addRelDevOp(RelDevOp relDevOp) {
		getRelDevOps().add(relDevOp);
		relDevOp.setDispositivo(this);

		return relDevOp;
	}

	public RelDevOp removeRelDevOp(RelDevOp relDevOp) {
		getRelDevOps().remove(relDevOp);
		relDevOp.setDispositivo(null);

		return relDevOp;
	}

	public SeguridadDfh getSeguridadDfh() {
		return this.seguridadDfh;
	}

	public void setSeguridadDfh(SeguridadDfh seguridadDfh) {
		this.seguridadDfh = seguridadDfh;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

}