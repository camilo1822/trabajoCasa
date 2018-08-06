package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the PARAMETRO database table.
 * 
 */
@Entity
@Table(name = "PARAMETRO", schema = "SHBANCA_DIGITAL")
@NamedQueries({
        @NamedQuery(name = "Parametro.findAll", query = "SELECT p FROM Parametro p"),
        @NamedQuery(name = "Parametro.findByTipoParametroId", query = "SELECT p FROM Parametro p WHERE p.tipoParametro.tipoParametroId = :tipoParametroId ORDER BY p.orden") })
public class Parametro implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PARAMETRO_ID")
    private String parametroId;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    private String nombre;

    private int orden;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    private String valor;

    // bi-directional many-to-many association to Parametro
    @ManyToMany
    @JoinTable(name = "HOMOLOGACION", joinColumns = {
            @JoinColumn(name = "PARAMETRO_ID_SRC") }, inverseJoinColumns = {
                    @JoinColumn(name = "PARAMETRO_ID_DST") })
    private List<Parametro> parametros1;

    // bi-directional many-to-many association to Parametro
    @ManyToMany(mappedBy = "parametros1")
    private List<Parametro> parametros2;

    // bi-directional many-to-one association to TipoParametro
    @ManyToOne
    @JoinColumn(name = "TIPO_PARAMETRO_ID")
    private TipoParametro tipoParametro;

    // bi-directional many-to-one association to ParametroAtributo
    @OneToMany(mappedBy = "parametro")
    private List<ParametroAtributo> parametroAtributos;

    // bi-directional many-to-one association to DivisionGeografica
    @ManyToOne
    @JoinColumn(name = "PAIS_ID")
    private DivisionGeografica divisionGeografica;

    public Parametro() {
    }

    public String getParametroId() {
        return this.parametroId;
    }

    public void setParametroId(String parametroId) {
        this.parametroId = parametroId;
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

    public int getOrden() {
        return this.orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
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

    public String getValor() {
        return this.valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public List<Parametro> getParametros1() {
        return this.parametros1;
    }

    public void setParametros1(List<Parametro> parametros1) {
        this.parametros1 = parametros1;
    }

    public List<Parametro> getParametros2() {
        return this.parametros2;
    }

    public void setParametros2(List<Parametro> parametros2) {
        this.parametros2 = parametros2;
    }

    public TipoParametro getTipoParametro() {
        return this.tipoParametro;
    }

    public void setTipoParametro(TipoParametro tipoParametro) {
        this.tipoParametro = tipoParametro;
    }

    public List<ParametroAtributo> getParametroAtributos() {
        return this.parametroAtributos;
    }

    public void setParametroAtributos(
            List<ParametroAtributo> parametroAtributos) {
        this.parametroAtributos = parametroAtributos;
    }

    public ParametroAtributo addParametroAtributo(
            ParametroAtributo parametroAtributo) {
        getParametroAtributos().add(parametroAtributo);
        parametroAtributo.setParametro(this);

        return parametroAtributo;
    }

    public ParametroAtributo removeParametroAtributo(
            ParametroAtributo parametroAtributo) {
        getParametroAtributos().remove(parametroAtributo);
        parametroAtributo.setParametro(null);

        return parametroAtributo;
    }

    public DivisionGeografica getDivisionGeografica() {
        return this.divisionGeografica;
    }

    public void setDivisionGeografica(DivisionGeografica divisionGeografica) {
        this.divisionGeografica = divisionGeografica;
    }

}