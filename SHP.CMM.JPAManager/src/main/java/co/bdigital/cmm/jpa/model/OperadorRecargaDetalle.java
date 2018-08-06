package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the OPERADOR_RECARGA_DETALLE database table.
 * 
 */
@Entity
@Table(name = "OPERADOR_RECARGA_DETALLE", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "OperadorRecargaDetalle.findAll", query = "SELECT o FROM OperadorRecargaDetalle o")
public class OperadorRecargaDetalle implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private OperadorRecargaDetallePK id;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "OPERADOR_VALOR")
    private String operadorValor;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    // bi-directional many-to-one association to OperadorRecarga
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ID_OPERADOR", referencedColumnName = "ID_OPERADOR"),
            @JoinColumn(name = "TIPO_OPERADOR", referencedColumnName = "TIPO") })
    private OperadorRecarga operadorRecarga;

    public OperadorRecargaDetalle() {
    }

    public OperadorRecargaDetallePK getId() {
        return this.id;
    }

    public void setId(OperadorRecargaDetallePK id) {
        this.id = id;
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

    public String getOperadorValor() {
        return this.operadorValor;
    }

    public void setOperadorValor(String operadorValor) {
        this.operadorValor = operadorValor;
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

    public OperadorRecarga getOperadorRecarga() {
        return this.operadorRecarga;
    }

    public void setOperadorRecarga(OperadorRecarga operadorRecarga) {
        this.operadorRecarga = operadorRecarga;
    }

}