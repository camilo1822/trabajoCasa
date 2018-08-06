package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the VERIFICACION_BIOMETRIA database table.
 * 
 */
@Entity
@Table(name = "VERIFICACION_BIOMETRIA", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "VerificacionBiometria.findAll", query = "SELECT v FROM VerificacionBiometria v")
public class VerificacionBiometria implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "VERIFICACION_BIOMETRIA_VERIFICACIONBIOMETRIAID_GENERATOR", sequenceName = "SHBANCA_DIGITAL.SQ_VERIFICACION_BIOMETRIA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VERIFICACION_BIOMETRIA_VERIFICACIONBIOMETRIAID_GENERATOR")
    @Column(name = "VERIFICACION_BIOMETRIA_ID")
    private long verificacionBiometriaId;

    @Column(name = "CIF_ID")
    private String cifId;

    @Column(name = "DEVICE_ID")
    private String deviceId;

    @Column(name = "ERROR_DESC")
    private String errorDesc;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "IMAGEN_ID")
    private String imagenId;

    private String marca;

    private String modelo;

    @Column(name = "PAIS_ID")
    private String paisId;

    @Column(name = "SISTEMA_OPERATIVO")
    private String sistemaOperativo;

    @Column(name = "TIPO_SERVICIO")
    private String tipoServicio;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    @Column(name = "VERSION_SISTEMA_OPERATIVO")
    private String versionSistemaOperativo;

    // bi-directional many-to-one association to Cliente
    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID")
    private Cliente cliente;

    public VerificacionBiometria() {
    }

    public long getVerificacionBiometriaId() {
        return this.verificacionBiometriaId;
    }

    public void setVerificacionBiometriaId(long verificacionBiometriaId) {
        this.verificacionBiometriaId = verificacionBiometriaId;
    }

    public String getCifId() {
        return this.cifId;
    }

    public void setCifId(String cifId) {
        this.cifId = cifId;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getErrorDesc() {
        return this.errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
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

    public String getImagenId() {
        return this.imagenId;
    }

    public void setImagenId(String imagenId) {
        this.imagenId = imagenId;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPaisId() {
        return this.paisId;
    }

    public void setPaisId(String paisId) {
        this.paisId = paisId;
    }

    public String getSistemaOperativo() {
        return this.sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public String getTipoServicio() {
        return this.tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
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

    public String getVersionSistemaOperativo() {
        return this.versionSistemaOperativo;
    }

    public void setVersionSistemaOperativo(String versionSistemaOperativo) {
        this.versionSistemaOperativo = versionSistemaOperativo;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}