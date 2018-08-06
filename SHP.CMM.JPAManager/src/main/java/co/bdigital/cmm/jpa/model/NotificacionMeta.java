package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the NOTIFICACION_META database table.
 * 
 */
@Entity
@Table(name = "NOTIFICACION_META", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "NotificacionMeta.findAll", query = "SELECT n FROM NotificacionMeta n")
public class NotificacionMeta implements Serializable {
    private static final long serialVersionUID = 1L;

    private String celular;

    @Column(name = "CIF_ID")
    private String cifId;

    @Column(name = "DEBITO_AUTOMATICO")
    private String debitoAutomatico;

    private BigDecimal estado;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_CREACION_META")
    private String fechaCreacionMeta;

    @Column(name = "FECHA_CUMPLIO")
    private String fechaCumplio;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "FECHA_OBJETIVO")
    private String fechaObjetivo;

    @Column(name = "MONTO_AHORRO")
    private String montoAhorro;

    @Column(name = "MONTO_DEBITO_AUTOMATICO")
    private String montoDebitoAutomatico;

    @Column(name = "MONTO_DISPONIBLE")
    private String montoDisponible;

    @Column(name = "NOMBRE_META")
    private String nombreMeta;

    @Id
    @Column(name = "NOTIFICACION_META_ID")
    private BigDecimal notificacionMetaId;

    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;

    @Column(name = "TEXTO_NOTIFICACION")
    private String textoNotificacion;

    @Column(name = "TIPO_BOLSILLO")
    private String tipoBolsillo;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    public NotificacionMeta() {
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCifId() {
        return this.cifId;
    }

    public void setCifId(String cifId) {
        this.cifId = cifId;
    }

    public String getDebitoAutomatico() {
        return this.debitoAutomatico;
    }

    public void setDebitoAutomatico(String debitoAutomatico) {
        this.debitoAutomatico = debitoAutomatico;
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

    public String getFechaCreacionMeta() {
        return this.fechaCreacionMeta;
    }

    public void setFechaCreacionMeta(String fechaCreacionMeta) {
        this.fechaCreacionMeta = fechaCreacionMeta;
    }

    public String getFechaCumplio() {
        return this.fechaCumplio;
    }

    public void setFechaCumplio(String fechaCumplio) {
        this.fechaCumplio = fechaCumplio;
    }

    public Timestamp getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getFechaObjetivo() {
        return this.fechaObjetivo;
    }

    public void setFechaObjetivo(String fechaObjetivo) {
        this.fechaObjetivo = fechaObjetivo;
    }

    public String getMontoAhorro() {
        return this.montoAhorro;
    }

    public void setMontoAhorro(String montoAhorro) {
        this.montoAhorro = montoAhorro;
    }

    public String getMontoDebitoAutomatico() {
        return this.montoDebitoAutomatico;
    }

    public void setMontoDebitoAutomatico(String montoDebitoAutomatico) {
        this.montoDebitoAutomatico = montoDebitoAutomatico;
    }

    public String getMontoDisponible() {
        return this.montoDisponible;
    }

    public void setMontoDisponible(String montoDisponible) {
        this.montoDisponible = montoDisponible;
    }

    public String getNombreMeta() {
        return this.nombreMeta;
    }

    public void setNombreMeta(String nombreMeta) {
        this.nombreMeta = nombreMeta;
    }

    public BigDecimal getNotificacionMetaId() {
        return this.notificacionMetaId;
    }

    public void setNotificacionMetaId(BigDecimal notificacionMetaId) {
        this.notificacionMetaId = notificacionMetaId;
    }

    public String getNumeroCuenta() {
        return this.numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTextoNotificacion() {
        return this.textoNotificacion;
    }

    public void setTextoNotificacion(String textoNotificacion) {
        this.textoNotificacion = textoNotificacion;
    }

    public String getTipoBolsillo() {
        return this.tipoBolsillo;
    }

    public void setTipoBolsillo(String tipoBolsillo) {
        this.tipoBolsillo = tipoBolsillo;
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

}