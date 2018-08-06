package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the SEGURIDAD database table.
 * 
 */
@Entity
@Table(name="SEGURIDAD", schema="SHBANCA_DIGITAL")
@NamedQuery(name="Seguridad.findAll", query="SELECT s FROM Seguridad s")
public class Seguridad implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String celular;

    @Column(name="CLAVE_PUBLICA_CLIENTE")
    private String clavePublicaCliente;

    @Column(name="CLAVE_PUBLICA_SERVIDOR")
    private String clavePublicaServidor;

    @Column(name="CLAVE_SECRETA")
    private String claveSecreta;

    @Column(name="FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name="FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name="USR_CREACION")
    private String usrCreacion;

    @Column(name="USR_MODIFICACION")
    private String usrModificacion;

    //bi-directional one-to-one association to Cliente
    @OneToOne(mappedBy="seguridad")
    private Cliente cliente;

    public Seguridad() {
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getClavePublicaCliente() {
        return this.clavePublicaCliente;
    }

    public void setClavePublicaCliente(String clavePublicaCliente) {
        this.clavePublicaCliente = clavePublicaCliente;
    }

    public String getClavePublicaServidor() {
        return this.clavePublicaServidor;
    }

    public void setClavePublicaServidor(String clavePublicaServidor) {
        this.clavePublicaServidor = clavePublicaServidor;
    }

    public String getClaveSecreta() {
        return this.claveSecreta;
    }

    public void setClaveSecreta(String claveSecreta) {
        this.claveSecreta = claveSecreta;
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

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}