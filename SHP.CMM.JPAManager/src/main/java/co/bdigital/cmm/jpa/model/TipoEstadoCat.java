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
 * The persistent class for the TIPO_ESTADO_CATS database table.
 * 
 */
@Entity
@Table(name = "TIPO_ESTADO_CATS", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "TipoEstadoCat.findAll", query = "SELECT t FROM TipoEstadoCat t")
public class TipoEstadoCat implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ESTADO_CUENTA")
    private String estadoCuenta;

    private String decripcion;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    private String restrictivo;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    // bi-directional many-to-one association to CatsMig
    @OneToMany(mappedBy = "tipoEstadoCat")
    private List<CatsMig> catsMigs;

    public TipoEstadoCat() {
    }

    public String getEstadoCuenta() {
        return this.estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public String getDecripcion() {
        return this.decripcion;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
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

    public String getRestrictivo() {
        return this.restrictivo;
    }

    public void setRestrictivo(String restrictivo) {
        this.restrictivo = restrictivo;
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

    public List<CatsMig> getCatsMigs() {
        return this.catsMigs;
    }

    public void setCatsMigs(List<CatsMig> catsMigs) {
        this.catsMigs = catsMigs;
    }

    public CatsMig addCatsMig(CatsMig catsMig) {
        getCatsMigs().add(catsMig);
        catsMig.setTipoEstadoCat(this);

        return catsMig;
    }

    public CatsMig removeCatsMig(CatsMig catsMig) {
        getCatsMigs().remove(catsMig);
        catsMig.setTipoEstadoCat(null);

        return catsMig;
    }

}