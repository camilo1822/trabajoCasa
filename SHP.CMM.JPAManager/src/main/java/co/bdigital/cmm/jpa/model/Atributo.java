package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the ATRIBUTO database table.
 * 
 */
@Entity
@Table(name = "ATRIBUTO", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "Atributo.findAll", query = "SELECT a FROM Atributo a")
public class Atributo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ATRIBUTO_ID")
    private String atributoId;

    private String nombre;

    // bi-directional many-to-one association to ParametroAtributo
    @OneToMany(mappedBy = "atributo")
    private List<ParametroAtributo> parametroAtributos;

    public Atributo() {
    }

    public String getAtributoId() {
        return this.atributoId;
    }

    public void setAtributoId(String atributoId) {
        this.atributoId = atributoId;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ParametroAtributo> getParametroAtributos() {
        return this.parametroAtributos;
    }

    public void setParametroAtributos(List<ParametroAtributo> parametroAtributos) {
        this.parametroAtributos = parametroAtributos;
    }

    public ParametroAtributo addParametroAtributo(
            ParametroAtributo parametroAtributo) {
        getParametroAtributos().add(parametroAtributo);
        parametroAtributo.setAtributo(this);

        return parametroAtributo;
    }

    public ParametroAtributo removeParametroAtributo(
            ParametroAtributo parametroAtributo) {
        getParametroAtributos().remove(parametroAtributo);
        parametroAtributo.setAtributo(null);

        return parametroAtributo;
    }

}