package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the HOMOLOGA_ERROR database table.
 * 
 */
@Entity
@Table(name="HOMOLOGA_ERROR", schema="SHBANCA_DIGITAL")
@NamedQuery(name="HomologaError.findAll", query="SELECT h FROM HomologaError h")
public class HomologaError implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="HOMOLOGA_ERROR_HOMOLOGAERRORID_GENERATOR", sequenceName="HOMOLOGA_ERROR_SEQ1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HOMOLOGA_ERROR_HOMOLOGAERRORID_GENERATOR")
	@Column(name="HOMOLOGA_ERROR_ID")
	private long homologaErrorId;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_MODIFICACION")
	private Timestamp fechaModificacion;

	@Column(name="USR_CREACION")
	private String usrCreacion;

	@Column(name="USR_MODIFICACION")
	private String usrModificacion;

	//bi-directional many-to-one association to CatalogoError
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CATALOGO_CODIGO_ENT", referencedColumnName="CODIGO"),
		@JoinColumn(name="CATALOGO_SISTEMA_ENT", referencedColumnName="SISTEMA_ID")
		})
	private CatalogoError catalogoError1;

	//bi-directional many-to-one association to CatalogoError
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CATALOGO_CODIGO_SAL", referencedColumnName="CODIGO"),
		@JoinColumn(name="CATALOGO_SISTEMA_SAL", referencedColumnName="SISTEMA_ID")
		})
	private CatalogoError catalogoError2;

	public HomologaError() {
	}

	public long getHomologaErrorId() {
		return this.homologaErrorId;
	}

	public void setHomologaErrorId(long homologaErrorId) {
		this.homologaErrorId = homologaErrorId;
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

	public CatalogoError getCatalogoError1() {
		return this.catalogoError1;
	}

	public void setCatalogoError1(CatalogoError catalogoError1) {
		this.catalogoError1 = catalogoError1;
	}

	public CatalogoError getCatalogoError2() {
		return this.catalogoError2;
	}

	public void setCatalogoError2(CatalogoError catalogoError2) {
		this.catalogoError2 = catalogoError2;
	}

}