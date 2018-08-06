package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the PSE_BANCO database table.
 * 
 */
@Entity
@Cacheable(true)
@Table(name = "PSE_BANCO", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "PseBanco.findAll", query = "SELECT p FROM PseBanco p")
public class PseBanco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String codigo;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_ACTUALIZACION")
    private Date fechaActualizacion;

    private String nombre;

    public PseBanco() {
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaActualizacion() {
        return this.fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}