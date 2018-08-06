package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the AYUDA database table.
 * 
 */
@Entity
@Table(name = "AYUDA", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "Ayuda.findAll", query = "SELECT a FROM Ayuda a")
public class Ayuda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "AYUDA_ID")
    private long ayudaId;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    private int orden;

    private String titulo;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    // bi-directional many-to-one association to Ayuda
    @ManyToOne
    @JoinColumn(name = "AYUDA_ID_PADRE")
    private Ayuda ayuda;

    // bi-directional many-to-one association to Ayuda
    @OneToMany(mappedBy = "ayuda")
    private List<Ayuda> ayudas;

    // bi-directional many-to-one association to HojaAyuda
    @OneToMany(mappedBy = "ayuda")
    private List<HojaAyuda> hojaAyudas;

    // bi-directional many-to-one association to DivisionGeografica
    @ManyToOne
    @JoinColumn(name = "PAIS_ID")
    private DivisionGeografica divisionGeografica;

    public Ayuda() {
    }

    public long getAyudaId() {
        return this.ayudaId;
    }

    public void setAyudaId(long ayudaId) {
        this.ayudaId = ayudaId;
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

    public int getOrden() {
        return this.orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public Ayuda getAyuda() {
        return this.ayuda;
    }

    public void setAyuda(Ayuda ayuda) {
        this.ayuda = ayuda;
    }

    public List<Ayuda> getAyudas() {
        return this.ayudas;
    }

    public void setAyudas(List<Ayuda> ayudas) {
        this.ayudas = ayudas;
    }

    public Ayuda addAyuda(Ayuda ayuda) {
        getAyudas().add(ayuda);
        ayuda.setAyuda(this);

        return ayuda;
    }

    public Ayuda removeAyuda(Ayuda ayuda) {
        getAyudas().remove(ayuda);
        ayuda.setAyuda(null);

        return ayuda;
    }

    public List<HojaAyuda> getHojaAyudas() {
        return this.hojaAyudas;
    }

    public void setHojaAyudas(List<HojaAyuda> hojaAyudas) {
        this.hojaAyudas = hojaAyudas;
    }

    public HojaAyuda addHojaAyuda(HojaAyuda hojaAyuda) {
        getHojaAyudas().add(hojaAyuda);
        hojaAyuda.setAyuda(this);

        return hojaAyuda;
    }

    public HojaAyuda removeHojaAyuda(HojaAyuda hojaAyuda) {
        getHojaAyudas().remove(hojaAyuda);
        hojaAyuda.setAyuda(null);

        return hojaAyuda;
    }

    public DivisionGeografica getDivisionGeografica() {
        return this.divisionGeografica;
    }

    public void setDivisionGeografica(DivisionGeografica divisionGeografica) {
        this.divisionGeografica = divisionGeografica;
    }

}