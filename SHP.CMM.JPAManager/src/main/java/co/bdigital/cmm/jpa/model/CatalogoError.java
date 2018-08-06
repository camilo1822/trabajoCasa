package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the CATALOGO_ERROR database table.
 * 
 */
@Entity
@Table(name="CATALOGO_ERROR", schema="SHBANCA_DIGITAL")
@NamedQuery(name="CatalogoError.findAll", query="SELECT c FROM CatalogoError c")
public class CatalogoError implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CatalogoErrorPK id;

	private String descripcion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_MODIFICACION")
	private Timestamp fechaModificacion;

	@Column(name="USR_CREACION")
	private String usrCreacion;

	@Column(name="USR_MODIFICACION")
	private String usrModificacion;

	//bi-directional many-to-one association to Sistema
	@ManyToOne
	@JoinColumn(name="SISTEMA_ID")
	private Sistema sistema;

	//bi-directional many-to-one association to HomologaError
	@OneToMany(mappedBy="catalogoError1")
	private List<HomologaError> homologaErrors1;

	//bi-directional many-to-one association to HomologaError
	@OneToMany(mappedBy="catalogoError2")
	private List<HomologaError> homologaErrors2;

	public CatalogoError() {
	}
	
	public CatalogoError(CatalogoErrorPK id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}



	public CatalogoErrorPK getId() {
		return this.id;
	}

	public void setId(CatalogoErrorPK id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Sistema getSistema() {
		return this.sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public List<HomologaError> getHomologaErrors1() {
		return this.homologaErrors1;
	}

	public void setHomologaErrors1(List<HomologaError> homologaErrors1) {
		this.homologaErrors1 = homologaErrors1;
	}

	public HomologaError addHomologaErrors1(HomologaError homologaErrors1) {
		getHomologaErrors1().add(homologaErrors1);
		homologaErrors1.setCatalogoError1(this);

		return homologaErrors1;
	}

	public HomologaError removeHomologaErrors1(HomologaError homologaErrors1) {
		getHomologaErrors1().remove(homologaErrors1);
		homologaErrors1.setCatalogoError1(null);

		return homologaErrors1;
	}

	public List<HomologaError> getHomologaErrors2() {
		return this.homologaErrors2;
	}

	public void setHomologaErrors2(List<HomologaError> homologaErrors2) {
		this.homologaErrors2 = homologaErrors2;
	}

	public HomologaError addHomologaErrors2(HomologaError homologaErrors2) {
		getHomologaErrors2().add(homologaErrors2);
		homologaErrors2.setCatalogoError2(this);

		return homologaErrors2;
	}

	public HomologaError removeHomologaErrors2(HomologaError homologaErrors2) {
		getHomologaErrors2().remove(homologaErrors2);
		homologaErrors2.setCatalogoError2(null);

		return homologaErrors2;
	}

}