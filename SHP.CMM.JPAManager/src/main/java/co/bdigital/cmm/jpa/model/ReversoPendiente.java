package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the REVERSO_PENDIENTE database table.
 * 
 */
@Entity
@Table(name = "REVERSO_PENDIENTE", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "ReversoPendiente.findAll", query = "SELECT r FROM ReversoPendiente r")
public class ReversoPendiente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "REVERSO_PENDIENTE_REVERSOPENDIENTEID_GENERATOR", sequenceName = "SHBANCA_DIGITAL.REVERSO_PENDIENTE_SEQ1")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVERSO_PENDIENTE_REVERSOPENDIENTEID_GENERATOR")
    @Column(name = "REVERSO_PENDIENTE_ID")
    private long reversoPendienteId;

    private String celular;

    @Column(name = "CUENTA_CLIENTE")
    private String cuentaCliente;

    @Column(name = "CUENTA_CONTABLE")
    private String cuentaContable;

    private String descripcion;

    private String estado;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "TIPO_REVERSO")
    private String tipoReverso;

    @Column(name = "TRAN_INFO")
    private String tranInfo;

    @Column(name = "TRANSACCION_ID")
    private String transaccionId;

    @Column(name = "ORIGINAL_DATA_ELEMENTS")
    private String originalDataElements;

    @Column(name = "TRAN_CODE")
    private String tranCode;

    @Column(name = "TRANS_PART2")
    private String transPart2;

    @Column(name = "TRN_SUB_TYPE")
    private String trnSubType;

    @Column(name = "TRN_TYPE")
    private String trnType;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    private BigDecimal valor;

    @Column(name = "PAIS_ID")
    private String paisId;

    // bi-directional many-to-one association to CuentaReverso
    @OneToMany(mappedBy = "reversoPendiente")
    private List<CuentaReverso> cuentaReversos;

    public ReversoPendiente() {
    }

    public long getReversoPendienteId() {
        return this.reversoPendienteId;
    }

    public void setReversoPendienteId(long reversoPendienteId) {
        this.reversoPendienteId = reversoPendienteId;
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCuentaCliente() {
        return this.cuentaCliente;
    }

    public void setCuentaCliente(String cuentaCliente) {
        this.cuentaCliente = cuentaCliente;
    }

    public String getCuentaContable() {
        return this.cuentaContable;
    }

    public void setCuentaContable(String cuentaContable) {
        this.cuentaContable = cuentaContable;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getTipoReverso() {
        return this.tipoReverso;
    }

    public void setTipoReverso(String tipoReverso) {
        this.tipoReverso = tipoReverso;
    }

    public String getTranInfo() {
        return this.tranInfo;
    }

    public void setTranInfo(String tranInfo) {
        this.tranInfo = tranInfo;
    }

    public String getTransaccionId() {
        return this.transaccionId;
    }

    public void setTransaccionId(String transaccionId) {
        this.transaccionId = transaccionId;
    }

    public String getOriginalDataElements() {
        return this.originalDataElements;
    }

    public void setOriginalDataElements(String originalDataElements) {
        this.originalDataElements = originalDataElements;
    }

    public String getTranCode() {
        return this.tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getTransPart2() {
        return this.transPart2;
    }

    public void setTransPart2(String transPart2) {
        this.transPart2 = transPart2;
    }

    public String getTrnSubType() {
        return this.trnSubType;
    }

    public void setTrnSubType(String trnSubType) {
        this.trnSubType = trnSubType;
    }

    public String getTrnType() {
        return this.trnType;
    }

    public void setTrnType(String trnType) {
        this.trnType = trnType;
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

    public BigDecimal getValor() {
        return this.valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getPaisId() {
        return paisId;
    }

    public void setPaisId(String paisId) {
        this.paisId = paisId;
    }

    public List<CuentaReverso> getCuentaReversos() {
        return this.cuentaReversos;
    }

    public void setCuentaReversos(List<CuentaReverso> cuentaReversos) {
        this.cuentaReversos = cuentaReversos;
    }

    public CuentaReverso addCuentaReverso(CuentaReverso cuentaReverso) {
        getCuentaReversos().add(cuentaReverso);
        cuentaReverso.setReversoPendiente(this);

        return cuentaReverso;
    }

    public CuentaReverso removeCuentaReverso(CuentaReverso cuentaReverso) {
        getCuentaReversos().remove(cuentaReverso);
        cuentaReverso.setReversoPendiente(null);

        return cuentaReverso;
    }

}