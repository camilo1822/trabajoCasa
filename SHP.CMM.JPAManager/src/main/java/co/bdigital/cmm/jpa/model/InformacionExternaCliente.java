package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the INFORMACION_EXTERNA_CLIENTE database table.
 * 
 */
@Entity
@Table(name = "INFORMACION_EXTERNA_CLIENTE", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "InformacionExternaCliente.findAll", query = "SELECT i FROM InformacionExternaCliente i")
public class InformacionExternaCliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "INFOEXTCLIENTE_INFOEXTCLIENTEID_GENERATOR", sequenceName = "INFO_EXT_CLIENT_SEQ1")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INFOEXTCLIENTE_INFOEXTCLIENTEID_GENERATOR")
    @Column(name = "INFO_EXT_CLIENTE_ID")
    private long infoExtClienteId;

    @Lob
    private String descripcion;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    // bi-directional many-to-one association to Cliente
    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID")
    private Cliente cliente;

    // bi-directional many-to-one association to Sistema
    @ManyToOne
    @JoinColumn(name = "SISTEMA_ID")
    private Sistema sistema;

    public InformacionExternaCliente() {
    }

    public long getInfoExtClienteId() {
        return this.infoExtClienteId;
    }

    public void setInfoExtClienteId(long infoExtClienteId) {
        this.infoExtClienteId = infoExtClienteId;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Sistema getSistema() {
        return this.sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

}