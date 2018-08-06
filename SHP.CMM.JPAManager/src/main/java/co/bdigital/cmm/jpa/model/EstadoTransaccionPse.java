package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the ESTADO_TRANSACCION_PSE database table.
 * 
 */
@Entity
@Table(name = "ESTADO_TRANSACCION_PSE", schema = "SHBANCA_DIGITAL")
public class EstadoTransaccionPse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ET_ID")
    private String etId;

    @Column(name = "ET_NOMBRE")
    private String etNombre;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    public EstadoTransaccionPse() {
    }

    public String getEtId() {
        return etId;
    }

    public void setEtId(String etId) {
        this.etId = etId;
    }

    public String getEtNombre() {
        return etNombre;
    }

    public void setEtNombre(String etNombre) {
        this.etNombre = etNombre;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsrCreacion() {
        return usrCreacion;
    }

    public void setUsrCreacion(String usrCreacion) {
        this.usrCreacion = usrCreacion;
    }

    public String getUsrModificacion() {
        return usrModificacion;
    }

    public void setUsrModificacion(String usrModificacion) {
        this.usrModificacion = usrModificacion;
    }

}