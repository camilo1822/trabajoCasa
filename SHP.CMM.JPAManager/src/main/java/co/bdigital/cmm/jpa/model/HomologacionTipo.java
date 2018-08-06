package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the HOMOLOGACION_TIPO database table.
 * 
 */
@Entity
@Table(name = "HOMOLOGACION_TIPO", schema = "SHBANCA_DIGITAL")
@NamedQuery(name = "HomologacionTipo.findAll", query = "SELECT h FROM HomologacionTipo h")
public class HomologacionTipo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String codigo;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "SISTEMA_DESTINO")
    private String sistemaDestino;

    @Column(name = "SISTEMA_ORIGEN")
    private String sistemaOrigen;

    private String tipo;

    @Column(name = "USR_CREACION")
    private String usrCreacion;

    @Column(name = "USR_MODIFICACION")
    private String usrModificacion;

    @Column(name = "VALOR_DESTINO")
    private String valorDestino;

    @Column(name = "VALOR_ORIGEN")
    private String valorOrigen;

    public HomologacionTipo() {
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getSistemaDestino() {
        return this.sistemaDestino;
    }

    public void setSistemaDestino(String sistemaDestino) {
        this.sistemaDestino = sistemaDestino;
    }

    public String getSistemaOrigen() {
        return this.sistemaOrigen;
    }

    public void setSistemaOrigen(String sistemaOrigen) {
        this.sistemaOrigen = sistemaOrigen;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getValorDestino() {
        return this.valorDestino;
    }

    public void setValorDestino(String valorDestino) {
        this.valorDestino = valorDestino;
    }

    public String getValorOrigen() {
        return this.valorOrigen;
    }

    public void setValorOrigen(String valorOrigen) {
        this.valorOrigen = valorOrigen;
    }

}