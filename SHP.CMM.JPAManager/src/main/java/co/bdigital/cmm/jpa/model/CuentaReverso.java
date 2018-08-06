package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the CUENTA_REVERSO database table.
 * 
 */
@Entity
@Table(name = "CUENTA_REVERSO", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "CuentaReverso.findAll", query = "SELECT c FROM CuentaReverso c")
public class CuentaReverso implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "CUENTA_REVERSO_CUENTAREVERSOID_GENERATOR", sequenceName = "SHBANCA_DIGITAL.CUENTA_REVERSO_ID_SEQ1")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUENTA_REVERSO_CUENTAREVERSOID_GENERATOR")
    @Column(name = "CUENTA_REVERSO_ID")
    private long cuentaReversoId;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "FECHA_TRANSACCION")
    private String fechaTransaccion;

    private String modeda;

    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;

    private String operacion;

    @Column(name = "PART_TRN_RMKS")
    private String partTrnRmks;

    @Column(name = "TRN_PARTICULARS")
    private String trnParticulars;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    private BigDecimal valor;

    // bi-directional many-to-one association to ReversoPendiente
    @ManyToOne
    @JoinColumn(name = "REVERSO_PENDIENTE_ID")
    private ReversoPendiente reversoPendiente;

    public CuentaReverso() {
    }

    public long getCuentaReversoId() {
        return this.cuentaReversoId;
    }

    public void setCuentaReversoId(long cuentaReversoId) {
        this.cuentaReversoId = cuentaReversoId;
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

    public String getFechaTransaccion() {
        return this.fechaTransaccion;
    }

    public void setFechaTransaccion(String fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public String getModeda() {
        return this.modeda;
    }

    public void setModeda(String modeda) {
        this.modeda = modeda;
    }

    public String getNumeroCuenta() {
        return this.numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getOperacion() {
        return this.operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getPartTrnRmks() {
        return this.partTrnRmks;
    }

    public void setPartTrnRmks(String partTrnRmks) {
        this.partTrnRmks = partTrnRmks;
    }

    public String getTrnParticulars() {
        return this.trnParticulars;
    }

    public void setTrnParticulars(String trnParticulars) {
        this.trnParticulars = trnParticulars;
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

    public BigDecimal getValor() {
        return this.valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public ReversoPendiente getReversoPendiente() {
        return this.reversoPendiente;
    }

    public void setReversoPendiente(ReversoPendiente reversoPendiente) {
        this.reversoPendiente = reversoPendiente;
    }

}