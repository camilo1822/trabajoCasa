package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import co.bdigital.cmm.jpa.util.ConstantJPA;


/**
 * The persistent class for the PROMOCION_COMERCIO database table.
 * 
 */
@Entity
@Table(name = ConstantJPA.COMMON_STRING_PROMOCION_COMERCIO, schema = ConstantJPA.COMMON_STRING_SHBANCA_DIGITAL)
@NamedQuery(name = ConstantJPA.ELIMINA_REGISTRO_PROMOCION_COMERCIO, query = ConstantJPA.COMMON_STRING_PROMOCION_COMERCIO_DELETE_ALL)
public class PromocionComercio implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PromocionComercioPK id;

	private BigDecimal estado;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_MODIFICACION")
	private Timestamp fechaModificacion;

	@Column(name="USR_CREACION")
	private String usrCreacion;

	@Column(name="USR_MODIFICACION")
	private String usrModificacion;

	//bi-directional many-to-one association to PromocionOperacion
	@ManyToOne
	@JoinColumn(name="PROMOCION_OPERACION_ID")
	private PromocionOperacion promocionOperacion;

	public PromocionComercio() {
	}

	public PromocionComercioPK getId() {
		return this.id;
	}

	public void setId(PromocionComercioPK id) {
		this.id = id;
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

	public PromocionOperacion getPromocionOperacion() {
		return this.promocionOperacion;
	}

	public void setPromocionOperacion(PromocionOperacion promocionOperacion) {
		this.promocionOperacion = promocionOperacion;
	}

}