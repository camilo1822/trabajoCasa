package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * The persistent class for the DIAS_ATENCION database table.
 * 
 */
@Entity
@Table(name = ConstantJPA.ATTENDANCES_DAYS_TABLE_NAME, schema = ConstantJPA.COMMON_STRING_SHBANCA_DIGITAL)
@NamedQueries({
        @NamedQuery(name = ConstantJPA.SQL_NAMED_QUERY_FIND_ALL_ATTENDANCES_DAYS, query = ConstantJPA.SQL_QUERY_FIND_ALL_ATTENDANCES_DAYS),
        @NamedQuery(name = ConstantJPA.SQL_NAMED_QUERY_FIND_ALL_ATTENDACES_DAYS_AND_SCHEDULES_BY_NEQUI_POINTS_AND_DAY_TYPE, query = ConstantJPA.SQL_QUERY_FIND_ALL_ATTENDACES_DAYS_AND_SCHEDULES_BY_NEQUI_POINTS_AND_DAY_TYPE) })
public class DiasAtencion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "DAT_ID")
    private long datId;

    @Column(name = "COMERCIO_ID")
    private String comercioId;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "HAT_ID")
    private BigDecimal hatId;

    @Column(name = "TIPO_DIA")
    private String tipoDia;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    // bi-directional many-to-one association to PuntosNequi
    @ManyToOne
    @JoinColumn(name = "PUNTO_ID")
    private PuntosNequi puntosNequi;

    // bi-directional many-to-one association to Horario
    @OneToMany(mappedBy = "diasAtencion")
    private List<Horario> horarios;

    public DiasAtencion() {
        /*
         * Empty constructor.
         */
    }

    public long getDatId() {
        return this.datId;
    }

    public void setDatId(long datId) {
        this.datId = datId;
    }

    public String getComercioId() {
        return this.comercioId;
    }

    public void setComercioId(String comercioId) {
        this.comercioId = comercioId;
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

    public BigDecimal getHatId() {
        return this.hatId;
    }

    public void setHatId(BigDecimal hatId) {
        this.hatId = hatId;
    }

    public String getTipoDia() {
        return this.tipoDia;
    }

    public void setTipoDia(String tipoDia) {
        this.tipoDia = tipoDia;
    }

    public String getUsuarioCreacion() {
        return this.usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioModificacion() {
        return this.usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public PuntosNequi getPuntosNequi() {
        return this.puntosNequi;
    }

    public void setPuntosNequi(PuntosNequi puntosNequi) {
        this.puntosNequi = puntosNequi;
    }

    public List<Horario> getHorarios() {
        return this.horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public Horario addHorario(Horario horario) {
        getHorarios().add(horario);
        horario.setDiasAtencion(this);

        return horario;
    }

    public Horario removeHorario(Horario horario) {
        getHorarios().remove(horario);
        horario.setDiasAtencion(null);

        return horario;
    }

}