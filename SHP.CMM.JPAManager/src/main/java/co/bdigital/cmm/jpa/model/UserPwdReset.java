package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the USER_PWD_RESET database table.
 * 
 */
@Entity
@Table(name="USER_PWD_RESET", schema="SHBANCA_DIGITAL")
@NamedQuery(name="UserPwdReset.findAll", query="SELECT u FROM UserPwdReset u")
public class UserPwdReset implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USER_PWD_RESET_USERPWDRESETID_GENERATOR", sequenceName="USER_PWD_RESET_ID_SEQ1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_PWD_RESET_USERPWDRESETID_GENERATOR")
	@Column(name="USER_PWD_RESET_ID")
	private long userPwdResetId;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="CLIENTE_ID")
	private Cliente cliente;

	private String codigo;

	@Column(name="ESTADO_ID")
	private BigDecimal estadoId;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_MODIFICACION")
	private Timestamp fechaModificacion;

	@Column(name="FECHA_PWD_RESET")
	private Timestamp fechaPwdReset;

	@Column(name="USR_CREACION")
	private String usrCreacion;

	@Column(name="USR_MODIFICACION")
	private String usrModificacion;

	public UserPwdReset() {
	}

	public long getUserPwdResetId() {
		return this.userPwdResetId;
	}

	public void setUserPwdResetId(long userPwdResetId) {
		this.userPwdResetId = userPwdResetId;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getEstadoId() {
		return this.estadoId;
	}

	public void setEstadoId(BigDecimal estadoId) {
		this.estadoId = estadoId;
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

	public Timestamp getFechaPwdReset() {
		return this.fechaPwdReset;
	}

	public void setFechaPwdReset(Timestamp fechaPwdReset) {
		this.fechaPwdReset = fechaPwdReset;
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

}