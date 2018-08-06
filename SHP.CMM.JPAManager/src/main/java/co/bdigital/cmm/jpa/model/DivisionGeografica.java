package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the DIVISION_GEOGRAFICA database table.
 * 
 */
@Entity
@Table(name = "DIVISION_GEOGRAFICA", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "DivisionGeografica.findAll", query = "SELECT d FROM DivisionGeografica d")
public class DivisionGeografica implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CODIGO_DIVISION")
    private String codigoDivision;

    private String abreviatura;

    @Column(name = "CIUDAD_CANDIDATA")
    private String ciudadCandidata;

    @Column(name = "CODIGO_POSTAL")
    private String codigoPostal;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    private String nombre;

    private String padre;

    @Column(name = "TIPO_DIVISION")
    private String tipoDivision;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    // bi-directional many-to-one association to OperadorRecarga
    @OneToMany(mappedBy = "divisionGeografica")
    private List<OperadorRecarga> operadorRecargas;

    public DivisionGeografica() {
    }

    public String getCodigoDivision() {
        return this.codigoDivision;
    }

    public void setCodigoDivision(String codigoDivision) {
        this.codigoDivision = codigoDivision;
    }

    public String getAbreviatura() {
        return this.abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getCiudadCandidata() {
        return this.ciudadCandidata;
    }

    public void setCiudadCandidata(String ciudadCandidata) {
        this.ciudadCandidata = ciudadCandidata;
    }

    public String getCodigoPostal() {
        return this.codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
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

    public String getPadre() {
        return this.padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getTipoDivision() {
        return this.tipoDivision;
    }

    public void setTipoDivision(String tipoDivision) {
        this.tipoDivision = tipoDivision;
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

    public List<OperadorRecarga> getOperadorRecargas() {
        return this.operadorRecargas;
    }

    public void setOperadorRecargas(List<OperadorRecarga> operadorRecargas) {
        this.operadorRecargas = operadorRecargas;
    }

    public OperadorRecarga addOperadorRecarga(OperadorRecarga operadorRecarga) {
        getOperadorRecargas().add(operadorRecarga);
        operadorRecarga.setDivisionGeografica(this);

        return operadorRecarga;
    }

    public OperadorRecarga removeOperadorRecarga(
            OperadorRecarga operadorRecarga) {
        getOperadorRecargas().remove(operadorRecarga);
        operadorRecarga.setDivisionGeografica(null);

        return operadorRecarga;
    }

}