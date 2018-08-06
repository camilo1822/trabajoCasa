package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the CONSULTA_CIFIN database table.
 * 
 */
@Entity
@Table(name = "CONSULTA_CIFIN", schema = "SHBANCA_DIGITAL")
@NamedQueries({
        @NamedQuery(name = "ConsultaCifin.findAll", query = "SELECT c FROM ConsultaCifin c"),
        @NamedQuery(name = "ConsultaCifin.findByEstado", query = "SELECT c FROM ConsultaCifin c WHERE c.estado = :estado"),
        @NamedQuery(name = "ConsultaCifin.findByEstadoReintentoClienteEstadoIdTipoId", query = "SELECT c FROM ConsultaCifin c WHERE c.estado = :estado AND c.reintento <= :reintento AND c.cliente.estadoId = :clienteEstadoId AND c.cliente.tipoId = :clienteTipoId AND c.cliente.divisionGeografica.codigoDivision = :clientCodDivisionGeografica"),
        @NamedQuery(name = "ConsultaCifin.findInEstadosAndReintentoInClienteEstadoIdInTipoIds", query = "SELECT c FROM ConsultaCifin c WHERE c.estado IN :cifinStatuses AND c.reintento <= :retries AND c.cliente.estadoId IN :clientStatuses AND c.cliente.tipoId IN :documentTypes AND c.cliente.divisionGeografica.codigoDivision = :countryCode") })
public class ConsultaCifin implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CONSULTA_CIFIN_ID")
    private long consultaCifinId;

    private String estado;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Lob
    private String mensaje;

    private String reintento;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    // bi-directional many-to-one association to Cliente
    @ManyToOne
    @JoinColumn(name = "IDCLIENTE")
    private Cliente cliente;

    public ConsultaCifin() {
    }

    public long getConsultaCifinId() {
        return this.consultaCifinId;
    }

    public void setConsultaCifinId(long consultaCifinId) {
        this.consultaCifinId = consultaCifinId;
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

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getReintento() {
        return this.reintento;
    }

    public void setReintento(String reintento) {
        this.reintento = reintento;
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

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}