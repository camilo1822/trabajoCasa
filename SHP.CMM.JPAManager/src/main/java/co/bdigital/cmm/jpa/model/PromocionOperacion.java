package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

import co.bdigital.cmm.jpa.util.ConstantJPA;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the PROMOCION_OPERACION database table.
 * 
 */
@Entity
@Table(name = ConstantJPA.COMMON_STRING_PROMOCION_OPERACION, schema = ConstantJPA.COMMON_STRING_SHBANCA_DIGITAL)
@NamedQuery(name = ConstantJPA.NAMED_QUERY_PROMOCION_REGLA_FIND_BY_SERVICE, query = ConstantJPA.NAMED_QUERY_PROMOCION_REGLA_FIND_BY_SERVICE_QUERY)
public class PromocionOperacion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PROMOCION_OPERACION_ID")
    private long promocionOperacionId;

    private String descripcion;

    private BigDecimal estado;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    private String operacion;

    @Column(name = "PAIS_ID")
    private String paisId;

    private String servicio;

    @Column(name = "TIPO_PROMOCION_ID")
    private BigDecimal tipoPromocionId;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    // bi-directional many-to-one association to PromocionUsuario
    @OneToMany(mappedBy = "promocionOperacion")
    private List<PromocionUsuario> promocionUsuarios;

    public PromocionOperacion() {
    }

    public long getPromocionOperacionId() {
        return this.promocionOperacionId;
    }

    public void setPromocionOperacionId(long promocionOperacionId) {
        this.promocionOperacionId = promocionOperacionId;
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

    public Timestamp getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getOperacion() {
        return this.operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getPaisId() {
        return this.paisId;
    }

    public void setPaisId(String paisId) {
        this.paisId = paisId;
    }

    public String getServicio() {
        return this.servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public BigDecimal getTipoPromocionId() {
        return this.tipoPromocionId;
    }

    public void setTipoPromocionId(BigDecimal tipoPromocionId) {
        this.tipoPromocionId = tipoPromocionId;
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

    public List<PromocionUsuario> getPromocionUsuarios() {
        return this.promocionUsuarios;
    }

    public void setPromocionUsuarios(List<PromocionUsuario> promocionUsuarios) {
        this.promocionUsuarios = promocionUsuarios;
    }

    public PromocionUsuario addPromocionUsuario(
            PromocionUsuario promocionUsuario) {
        getPromocionUsuarios().add(promocionUsuario);
        promocionUsuario.setPromocionOperacion(this);

        return promocionUsuario;
    }

    public PromocionUsuario removePromocionUsuario(
            PromocionUsuario promocionUsuario) {
        getPromocionUsuarios().remove(promocionUsuario);
        promocionUsuario.setPromocionOperacion(null);

        return promocionUsuario;
    }

}