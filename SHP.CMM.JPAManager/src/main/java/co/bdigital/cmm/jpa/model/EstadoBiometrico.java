package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the ESTADO_BIOMETRICO database table.
 * 
 */
@Entity
@Table(name = "ESTADO_BIOMETRICO", schema = "SHBANCA_DIGITAL")
@NamedQueries({
        @NamedQuery(name = "EstadoBiometrico.findAll", query = "SELECT e FROM EstadoBiometrico e"),
        @NamedQuery(name = "EstadoBiometrico.findByClientId", query = "SELECT e FROM EstadoBiometrico e WHERE e.clienteId = :clienteId") })
public class EstadoBiometrico implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "ESTADO_BIOMETRICO_ESTADOBIOMETRICOID_GENERATOR", sequenceName = "ESTADO_BIOMETRICO_SEQ1")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ESTADO_BIOMETRICO_ESTADOBIOMETRICOID_GENERATOR")
    @Column(name = "ESTADO_BIOMETRICO_ID")
    private long estadoBiometricoId;

    @Column(name = "BIOMETRIA_TIPO")
    private String biometriaTipo;

    @Column(name = "CLIENTE_ID")
    private java.math.BigDecimal clienteId;

    private String estado;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    public EstadoBiometrico() {
    }

    public long getEstadoBiometricoId() {
        return this.estadoBiometricoId;
    }

    public void setEstadoBiometricoId(long estadoBiometricoId) {
        this.estadoBiometricoId = estadoBiometricoId;
    }

    public String getBiometriaTipo() {
        return this.biometriaTipo;
    }

    public void setBiometriaTipo(String biometriaTipo) {
        this.biometriaTipo = biometriaTipo;
    }

    public java.math.BigDecimal getClienteId() {
        return this.clienteId;
    }

    public void setClienteId(java.math.BigDecimal clienteId) {
        this.clienteId = clienteId;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
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

}