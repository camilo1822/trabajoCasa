package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the PARAMETRO_ATRIBUTO database table.
 * 
 */
@Entity
@Table(name = "CLIENTE_TEMP", schema = "SHBANCA_DIGITAL")
public class ClienteTemp implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ClienteTempPK id;

    private String apellido1;

    private String apellido2;

    @Column(name = "PROCESS_ID")
    private String processId;
    
    @Column(name = "CARNE_RESIDENTE")
    private String carneResidente;

    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;

    private String direccion;

    @Column(name = "DOCUMENT_IMAGE_BACK")
    private String documentImageBack;

    @Column(name = "DOCUMENT_IMAGE_FRONT")
    private String documentImageFront;

    @Column(name = "DOCUMENTO_ESCANEADO")
    private Boolean documentoEscaneado;

    private String extranjero;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_EXP_CARNE")
    private Date fechaExpCarne;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_EXPEDICION")
    private Date fechaExpedicion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNacimiento;

    private String nickname;

    private String nombre1;

    private String nombre2;

    @Column(name = "NUMERO_ID")
    private String numeroId;

    @Column(name = "TIPO_ID")
    private String tipoId;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    private String departamento;

    private String barrio;

    @Column(name = "TIPO_RESIDENCIA")
    private String tipoResidencia;

    @Column(name = "DESCRIPCION_RESIDENCIA")
    private String descripcionResidencia;
    
    @Column(name = "LUGAR_EXPEDICION")
    private String lugarExpedicion;

    public ClienteTemp() {
    }

    public ClienteTempPK getId() {
        return this.id;
    }

    public void setId(ClienteTempPK id) {
        this.id = id;
    }

    public String getApellido1() {
        return this.apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return this.apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getCarneResidente() {
        return this.carneResidente;
    }

    public void setCarneResidente(String carneResidente) {
        this.carneResidente = carneResidente;
    }

    public String getCorreoElectronico() {
        return this.correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDocumentImageBack() {
        return this.documentImageBack;
    }

    public void setDocumentImageBack(String documentImageBack) {
        this.documentImageBack = documentImageBack;
    }

    public String getDocumentImageFront() {
        return this.documentImageFront;
    }

    public void setDocumentImageFront(String documentImageFront) {
        this.documentImageFront = documentImageFront;
    }

    public Boolean getDocumentoEscaneado() {
        return this.documentoEscaneado;
    }

    public void setDocumentoEscaneado(Boolean documentoEscaneado) {
        this.documentoEscaneado = documentoEscaneado;
    }

    public String getExtranjero() {
        return this.extranjero;
    }

    public void setExtranjero(String extranjero) {
        this.extranjero = extranjero;
    }

    public Timestamp getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaExpCarne() {
        return this.fechaExpCarne;
    }

    public void setFechaExpCarne(Date fechaExpCarne) {
        this.fechaExpCarne = fechaExpCarne;
    }

    public Date getFechaExpedicion() {
        return this.fechaExpedicion;
    }

    public void setFechaExpedicion(Date fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public Timestamp getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNombre1() {
        return this.nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return this.nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getNumeroId() {
        return this.numeroId;
    }

    public void setNumeroId(String numeroId) {
        this.numeroId = numeroId;
    }

    public String getTipoId() {
        return this.tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
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

    public String getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getBarrio() {
        return this.barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getTipoResidencia() {
        return this.tipoResidencia;
    }

    public void setTipoResidencia(String tipoResidencia) {
        this.tipoResidencia = tipoResidencia;
    }

    public String getDescripcionResidencia() {
        return this.descripcionResidencia;
    }

    public void setDescripcionResidencia(String descripcionResidencia) {
        this.descripcionResidencia = descripcionResidencia;
    }

    public String getLugarExpedicion() {
        return lugarExpedicion;
    }

    public void setLugarExpedicion(String lugarExpedicion) {
        this.lugarExpedicion = lugarExpedicion;
    }
    
    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
    
}