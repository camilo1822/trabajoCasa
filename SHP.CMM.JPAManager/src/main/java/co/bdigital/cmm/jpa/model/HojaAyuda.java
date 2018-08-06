package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the HOJA_AYUDA database table.
 * 
 */
@Entity
@Table(name="HOJA_AYUDA", schema="SHBANCA_DIGITAL")
@NamedQuery(name="HojaAyuda.findAll", query="SELECT h FROM HojaAyuda h")
public class HojaAyuda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="HOJA_AYUDA_HOJAAYUDAID_GENERATOR", sequenceName="HOJA_AYUDA_ID_SEQ1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HOJA_AYUDA_HOJAAYUDAID_GENERATOR")
	@Column(name="HOJA_AYUDA_ID")
	private long hojaAyudaId;

	@Lob
	private String contenido;

	private String descripcion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_MODIFICACION")
	private Timestamp fechaModificacion;

	@Column(name="USR_CREACION")
	private String usrCreacion;

	@Column(name="USR_MODIFICACION")
	private String usrModificacion;

	//bi-directional many-to-one association to Ayuda
	@ManyToOne
	@JoinColumn(name="AYUDA_ID")
	private Ayuda ayuda;

	public HojaAyuda() {
	}

	public long getHojaAyudaId() {
		return this.hojaAyudaId;
	}

	public void setHojaAyudaId(long hojaAyudaId) {
		this.hojaAyudaId = hojaAyudaId;
	}

	public String getContenido() {
		return this.contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
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

	public Ayuda getAyuda() {
		return this.ayuda;
	}

	public void setAyuda(Ayuda ayuda) {
		this.ayuda = ayuda;
	}

}