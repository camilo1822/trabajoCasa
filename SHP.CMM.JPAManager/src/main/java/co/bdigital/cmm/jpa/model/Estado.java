package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the ESTADO database table.
 * 
 */
@Entity
@Table(name="ESTADO", schema="SHBANCA_DIGITAL")
@NamedQuery(name="Estado.findAll", query="SELECT e FROM Estado e")
public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ESTADO_ESTID_GENERATOR", sequenceName="CLIENTE_ID_SEQ1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ESTADO_ESTID_GENERATOR")
	@Column(name="EST_ID")
	private long estId;

	@Column(name="EST_NOMBRE")
	private String estNombre;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_MODIFICACION")
	private Timestamp fechaModificacion;

	@Column(name="USUARIO_CREACION")
	private String usuarioCreacion;

	@Column(name="USUARIO_MODIFICACION")
	private String usuarioModificacion;

	//bi-directional many-to-one association to Comercio
	@OneToMany(mappedBy="estado")
	private List<Comercio> comercios;

	//bi-directional many-to-one association to Dispositivo
	@OneToMany(mappedBy="estado")
	private List<Dispositivo> dispositivos;

	public Estado() {
	}

	public long getEstId() {
		return this.estId;
	}

	public void setEstId(long estId) {
		this.estId = estId;
	}

	public String getEstNombre() {
		return this.estNombre;
	}

	public void setEstNombre(String estNombre) {
		this.estNombre = estNombre;
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

	public List<Comercio> getComercios() {
		return this.comercios;
	}

	public void setComercios(List<Comercio> comercios) {
		this.comercios = comercios;
	}

	public Comercio addComercio(Comercio comercio) {
		getComercios().add(comercio);
		comercio.setEstado(this);

		return comercio;
	}

	public Comercio removeComercio(Comercio comercio) {
		getComercios().remove(comercio);
		comercio.setEstado(null);

		return comercio;
	}

	public List<Dispositivo> getDispositivos() {
		return this.dispositivos;
	}

	public void setDispositivos(List<Dispositivo> dispositivos) {
		this.dispositivos = dispositivos;
	}

	public Dispositivo addDispositivo(Dispositivo dispositivo) {
		getDispositivos().add(dispositivo);
		dispositivo.setEstado(this);

		return dispositivo;
	}

	public Dispositivo removeDispositivo(Dispositivo dispositivo) {
		getDispositivos().remove(dispositivo);
		dispositivo.setEstado(null);

		return dispositivo;
	}

}