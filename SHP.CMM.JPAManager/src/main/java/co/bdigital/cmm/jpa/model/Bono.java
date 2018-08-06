package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the bono database table.
 * 
 */
@Entity
@Table(name = "BONO", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "Bono.findAll", query = "SELECT b FROM Bono b")
public class Bono implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BONO_ID")
    private String bonoId;

    private String descripcion;

    @Column(name = "ESTADO_ID")
    private BigInteger estadoId;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    private String nombre;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    private double valor;

    // bi-directional many-to-one association to DivisionGeografica
    @ManyToOne
    @JoinColumn(name = "PAIS_ID")
    private DivisionGeografica divisionGeografica;

    public Bono() {
    }

    public String getBonoId() {
        return this.bonoId;
    }

    public void setBonoId(String bonoId) {
        this.bonoId = bonoId;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigInteger getEstadoId() {
        return this.estadoId;
    }

    public void setEstadoId(BigInteger estadoId) {
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

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public DivisionGeografica getDivisionGeografica() {
        return this.divisionGeografica;
    }

    public void setDivisionGeografica(DivisionGeografica divisionGeografica) {
        this.divisionGeografica = divisionGeografica;
    }

}