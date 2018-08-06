package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the HORARIO database table.
 * 
 */
@Entity
@Table(name="HORARIO", schema="SHBANCA_DIGITAL")
@NamedQuery(name="Horario.findAll", query="SELECT h FROM Horario h")
public class Horario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOR_ID")
	private long horId;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_MODIFICACION")
	private Timestamp fechaModificacion;

	@Column(name="HORA_FIN")
	private BigDecimal horaFin;

	@Column(name="HORA_INICIO")
	private BigDecimal horaInicio;

	private String jornada;

	@Column(name="USUARIO_CREACION")
	private String usuarioCreacion;

	@Column(name="USUARIO_MODIFICACION")
	private String usuarioModificacion;

	//bi-directional many-to-one association to DiasAtencion
	@ManyToOne
	@JoinColumn(name="DIAS_ATENCION_DAT_ID")
	private DiasAtencion diasAtencion;

	public Horario() {
	}

	public long getHorId() {
		return this.horId;
	}

	public void setHorId(long horId) {
		this.horId = horId;
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

	public BigDecimal getHoraFin() {
		return this.horaFin;
	}

	public void setHoraFin(BigDecimal horaFin) {
		this.horaFin = horaFin;
	}

	public BigDecimal getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(BigDecimal horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getJornada() {
		return this.jornada;
	}

	public void setJornada(String jornada) {
		this.jornada = jornada;
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

	public DiasAtencion getDiasAtencion() {
		return this.diasAtencion;
	}

	public void setDiasAtencion(DiasAtencion diasAtencion) {
		this.diasAtencion = diasAtencion;
	}

}