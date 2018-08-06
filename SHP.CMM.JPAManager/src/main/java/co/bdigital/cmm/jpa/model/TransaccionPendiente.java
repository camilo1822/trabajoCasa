package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the TRANSACCION_PENDIENTE database table.
 * 
 */
@Entity
@Table(name = "TRANSACCION_PENDIENTE", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "TransaccionPendiente.findAll", query = "SELECT t FROM TransaccionPendiente t")
public class TransaccionPendiente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TRANSACCION_PENDIENTE_TRANSACCIONPENDIENTEID_GENERATOR", sequenceName = "SHBANCA_DIGITAL.TRANSACCION_PENDIENTE_ID_SEQ1")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSACCION_PENDIENTE_TRANSACCIONPENDIENTEID_GENERATOR")
    @Column(name = "TRANSACCION_PENDIENTE_ID")
    private long transaccionPendienteId;

    private String bolsillo;

    @Column(name = "CELULAR_DESTINO")
    private String celularDestino;

    @Column(name = "CELULAR_ORIGEN")
    private String celularOrigen;

    @Column(name = "CLIENTE_ID")
    private BigDecimal clienteId;

    @Column(name = "COMERCIO_ID")
    private String comercioId;

    private String descripcion;

    private String documento;

    private BigDecimal estado;

    @Column(name = "FECHA_TRANSACCION")
    private Timestamp fechaTransaccion;

    private String mail;

    @Column(name = "NAME_DESTINO")
    private String nameDestino;

    @Column(name = "NAME_ORIGEN")
    private String nameOrigen;

    private String nombre;

    private String referencia;

    @Column(name = "TIPO_TRANSACCION")
    private BigDecimal tipoTransaccion;

    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    private BigDecimal valor;

    @Column(name = "VALOR_BOLSILLO")
    private BigDecimal valorBolsillo;

    // bi-directional many-to-one association to TransaccionGrupo
    @ManyToOne
    @JoinColumn(name = "TRANSACCION_GRUPO_ID")
    private TransaccionGrupo transaccionGrupo;

    // bi-directional many-to-one association to TransaccionPendienteDetalle
    @OneToMany(mappedBy = "transaccionPendiente", fetch = FetchType.LAZY)
    private List<TransaccionPendienteDetalle> transaccionPendienteDetalles;

    public TransaccionPendiente() {
    }

    public long getTransaccionPendienteId() {
        return this.transaccionPendienteId;
    }

    public void setTransaccionPendienteId(long transaccionPendienteId) {
        this.transaccionPendienteId = transaccionPendienteId;
    }

    public String getBolsillo() {
        return this.bolsillo;
    }

    public void setBolsillo(String bolsillo) {
        this.bolsillo = bolsillo;
    }

    public String getCelularDestino() {
        return this.celularDestino;
    }

    public void setCelularDestino(String celularDestino) {
        this.celularDestino = celularDestino;
    }

    public String getCelularOrigen() {
        return this.celularOrigen;
    }

    public void setCelularOrigen(String celularOrigen) {
        this.celularOrigen = celularOrigen;
    }

    public BigDecimal getClienteId() {
        return this.clienteId;
    }

    public void setClienteId(BigDecimal clienteId) {
        this.clienteId = clienteId;
    }

    public String getComercioId() {
        return this.comercioId;
    }

    public void setComercioId(String comercioId) {
        this.comercioId = comercioId;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDocumento() {
        return this.documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public BigDecimal getEstado() {
        return this.estado;
    }

    public void setEstado(BigDecimal estado) {
        this.estado = estado;
    }

    public Timestamp getFechaTransaccion() {
        return this.fechaTransaccion;
    }

    public void setFechaTransaccion(Timestamp fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNameDestino() {
        return this.nameDestino;
    }

    public void setNameDestino(String nameDestino) {
        this.nameDestino = nameDestino;
    }

    public String getNameOrigen() {
        return this.nameOrigen;
    }

    public void setNameOrigen(String nameOrigen) {
        this.nameOrigen = nameOrigen;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getReferencia() {
        return this.referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public BigDecimal getTipoTransaccion() {
        return this.tipoTransaccion;
    }

    public void setTipoTransaccion(BigDecimal tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorBolsillo() {
        return this.valorBolsillo;
    }

    public void setValorBolsillo(BigDecimal valorBolsillo) {
        this.valorBolsillo = valorBolsillo;
    }

    public TransaccionGrupo getTransaccionGrupo() {
        return this.transaccionGrupo;
    }

    public void setTransaccionGrupo(TransaccionGrupo transaccionGrupo) {
        this.transaccionGrupo = transaccionGrupo;
    }

    public List<TransaccionPendienteDetalle> getTransaccionPendienteDetalles() {
        return this.transaccionPendienteDetalles;
    }

    public void setTransaccionPendienteDetalles(
            List<TransaccionPendienteDetalle> transaccionPendienteDetalles) {
        this.transaccionPendienteDetalles = transaccionPendienteDetalles;
    }

    public TransaccionPendienteDetalle addTransaccionPendienteDetalle(
            TransaccionPendienteDetalle transaccionPendienteDetalle) {
        getTransaccionPendienteDetalles().add(transaccionPendienteDetalle);
        transaccionPendienteDetalle.setTransaccionPendiente(this);

        return transaccionPendienteDetalle;
    }

    public TransaccionPendienteDetalle removeTransaccionPendienteDetalle(
            TransaccionPendienteDetalle transaccionPendienteDetalle) {
        getTransaccionPendienteDetalles().remove(transaccionPendienteDetalle);
        transaccionPendienteDetalle.setTransaccionPendiente(null);

        return transaccionPendienteDetalle;
    }

}