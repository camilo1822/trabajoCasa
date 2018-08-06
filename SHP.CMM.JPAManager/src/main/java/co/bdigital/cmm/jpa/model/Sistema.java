package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the SISTEMA database table.
 * 
 */
@Entity
@Table(name="SISTEMA", schema="SHBANCA_DIGITAL")
@NamedQuery(name="Sistema.findAll", query="SELECT s FROM Sistema s")
public class Sistema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SISTEMA_SISTEMAID_GENERATOR", sequenceName="SISTEMA_SEQ1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SISTEMA_SISTEMAID_GENERATOR")
	@Column(name="SISTEMA_ID")
	private long sistemaId;

	private String descripcion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_MODIFICACION")
	private Timestamp fechaModificacion;

	private String nombre;

	@Column(name="USR_CREACION")
	private String usrCreacion;

	@Column(name="USR_MODIFICACION")
	private String usrModificacion;

	//bi-directional many-to-one association to CatalogoError
	@OneToMany(mappedBy="sistema")
	private List<CatalogoError> catalogoErrors;

	public Sistema() {
	}

	public long getSistemaId() {
		return this.sistemaId;
	}

	public void setSistemaId(long sistemaId) {
		this.sistemaId = sistemaId;
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public List<CatalogoError> getCatalogoErrors() {
		return this.catalogoErrors;
	}

	public void setCatalogoErrors(List<CatalogoError> catalogoErrors) {
		this.catalogoErrors = catalogoErrors;
	}

	public CatalogoError addCatalogoError(CatalogoError catalogoError) {
		getCatalogoErrors().add(catalogoError);
		catalogoError.setSistema(this);

		return catalogoError;
	}

	public CatalogoError removeCatalogoError(CatalogoError catalogoError) {
		getCatalogoErrors().remove(catalogoError);
		catalogoError.setSistema(null);

		return catalogoError;
	}

}