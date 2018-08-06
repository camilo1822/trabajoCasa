package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import co.bdigital.cmm.jpa.util.ConstantJPA;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the PROMOCION_REGLA database table.
 * 
 */
@Entity
@Table(name = ConstantJPA.COMMON_STRING_PROMOCION_REGLA, schema = ConstantJPA.COMMON_STRING_SHBANCA_DIGITAL)
@NamedQuery(name = ConstantJPA.NAMED_QUERY_PROMOCION_REGLA_GET_MAX_ID, query = ConstantJPA.NAMED_QUERY_PROMOCION_REGLA_GET_MAX_ID_QUERY)
public class PromocionRegla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PROMOCION_REGLA_ID")
	private long promocionReglaId;

	private String asunto;

	@Column(name="COLA_JMS")
	private String colaJms;

	@Column(name="CUENTA_CONTABLE")
	private String cuentaContable;

	private String descripcion;

	private BigDecimal estado;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_FINAL")
	private Timestamp fechaFinal;

	@Column(name="FECHA_INICIO")
	private Timestamp fechaInicio;

	@Column(name="FECHA_MODIFICACION")
	private Timestamp fechaModificacion;

	@Column(name="FRECUENCIA_MINIMA")
	private BigDecimal frecuenciaMinima;

	@Column(name="FRECUENCIA_REINICIO")
	private BigDecimal frecuenciaReinicio;

	@Lob
	private String mensaje;

	private BigDecimal ocurrencia;

	@Column(name="PRESUPUESTO_MAXIMO")
	private BigDecimal presupuestoMaximo;

	@Column(name="TIPO_NOFIFICACION")
	private String tipoNofificacion;

	@Column(name="TIPO_VALOR")
	private String tipoValor;

	@Column(name="USR_CREACION")
	private String usrCreacion;

	@Column(name="USR_MODIFICACION")
	private String usrModificacion;

	private BigDecimal valor;

	@Column(name="VALOR_DISPONIBLE")
	private BigDecimal valorDisponible;

	@Column(name="VALOR_MAX")
	private BigDecimal valorMax;

	@Column(name="VALOR_MIN")
	private BigDecimal valorMin;

	//bi-directional many-to-one association to PromocionOperacion
	@ManyToOne
	@JoinColumn(name="PROMOCION_OPERACION_ID")
	private PromocionOperacion promocionOperacion;

	public PromocionRegla() {
	}

	public long getPromocionReglaId() {
		return this.promocionReglaId;
	}

	public void setPromocionReglaId(long promocionReglaId) {
		this.promocionReglaId = promocionReglaId;
	}

	public String getAsunto() {
		return this.asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getColaJms() {
		return this.colaJms;
	}

	public void setColaJms(String colaJms) {
		this.colaJms = colaJms;
	}

	public String getCuentaContable() {
		return this.cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Timestamp fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Timestamp getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public BigDecimal getFrecuenciaMinima() {
		return this.frecuenciaMinima;
	}

	public void setFrecuenciaMinima(BigDecimal frecuenciaMinima) {
		this.frecuenciaMinima = frecuenciaMinima;
	}

	public BigDecimal getFrecuenciaReinicio() {
		return this.frecuenciaReinicio;
	}

	public void setFrecuenciaReinicio(BigDecimal frecuenciaReinicio) {
		this.frecuenciaReinicio = frecuenciaReinicio;
	}

	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public BigDecimal getOcurrencia() {
		return this.ocurrencia;
	}

	public void setOcurrencia(BigDecimal ocurrencia) {
		this.ocurrencia = ocurrencia;
	}

	public BigDecimal getPresupuestoMaximo() {
		return this.presupuestoMaximo;
	}

	public void setPresupuestoMaximo(BigDecimal presupuestoMaximo) {
		this.presupuestoMaximo = presupuestoMaximo;
	}

	public String getTipoNofificacion() {
		return this.tipoNofificacion;
	}

	public void setTipoNofificacion(String tipoNofificacion) {
		this.tipoNofificacion = tipoNofificacion;
	}

	public String getTipoValor() {
		return this.tipoValor;
	}

	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
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

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorDisponible() {
		return this.valorDisponible;
	}

	public void setValorDisponible(BigDecimal valorDisponible) {
		this.valorDisponible = valorDisponible;
	}

	public BigDecimal getValorMax() {
		return this.valorMax;
	}

	public void setValorMax(BigDecimal valorMax) {
		this.valorMax = valorMax;
	}

	public BigDecimal getValorMin() {
		return this.valorMin;
	}

	public void setValorMin(BigDecimal valorMin) {
		this.valorMin = valorMin;
	}

	public PromocionOperacion getPromocionOperacion() {
		return this.promocionOperacion;
	}

	public void setPromocionOperacion(PromocionOperacion promocionOperacion) {
		this.promocionOperacion = promocionOperacion;
	}

}