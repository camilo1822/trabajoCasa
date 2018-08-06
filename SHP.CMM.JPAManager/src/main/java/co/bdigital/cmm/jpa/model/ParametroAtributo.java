package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the PARAMETRO_ATRIBUTO database table.
 * 
 */
@Entity
@Table(name = "PARAMETRO_ATRIBUTO", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "ParametroAtributo.findAll", query = "SELECT p FROM ParametroAtributo p")
public class ParametroAtributo implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ParametroAtributoPK id;

    private String valor;

    // bi-directional many-to-one association to Atributo
    @ManyToOne
    @JoinColumn(name = "ATRIBUTO_ID")
    private Atributo atributo;

    // bi-directional many-to-one association to Parametro
    @ManyToOne
    @JoinColumn(name = "PARAMETRO_ID")
    private Parametro parametro;

    public ParametroAtributo() {
    }

    public ParametroAtributoPK getId() {
        return this.id;
    }

    public void setId(ParametroAtributoPK id) {
        this.id = id;
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Atributo getAtributo() {
        return this.atributo;
    }

    public void setAtributo(Atributo atributo) {
        this.atributo = atributo;
    }

    public Parametro getParametro() {
        return this.parametro;
    }

    public void setParametro(Parametro parametro) {
        this.parametro = parametro;
    }

}