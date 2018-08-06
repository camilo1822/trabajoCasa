package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * The persistent class for the PROMOCION_USUARIO database table.
 * 
 */
@Entity
@Table(name = ConstantJPA.COMMON_STRING_PROMOCION_USUARIO, schema = ConstantJPA.COMMON_STRING_SHBANCA_DIGITAL)
@NamedQueries({
        @NamedQuery(name = ConstantJPA.COMMON_STRING_PROMOCION_USUARIO_FIND_ALL, query = ConstantJPA.COMMON_STRING_PROMOCION_USUARIO_SELECT_ALL),
        @NamedQuery(name = ConstantJPA.ELIMINA_REGISTRO_PROMOCION_USUARIO, query = ConstantJPA.COMMON_STRING_PROMOCION_USUARIO_DELETE_ALL),
        @NamedQuery(name = ConstantJPA.NAMED_QUERY_PROMOCION_USUARIO_GET_PROMOCION_USUARIO_LIMIT, query = ConstantJPA.NAMED_QUERY_PROMOCION_USUARIO_GET_PROMOCION_USUARIO_LIMIT_QUERY),
        @NamedQuery(name = ConstantJPA.COUNT_PROMOCION_USUARIO_ROWS, query = ConstantJPA.COMMON_STRING_PROMOCION_USUARIO_COUNT) })
public class PromocionUsuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PROMOCION_USUARIO_PROMOCIONUSUARIOID_GENERATOR", sequenceName = "SHBANCA_DIGITAL.SEQ_PROMOCION_USUARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROMOCION_USUARIO_PROMOCIONUSUARIOID_GENERATOR")
    @Column(name = "PROMOCION_USUARIO_ID")
    private long promocionUsuarioId;

    private String celular;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    private String identificacion;

    @Column(name = "TIPO_IDENTIFICACION")
    private String tipoIdentificacion;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    // bi-directional many-to-one association to PromocionOperacion
    @ManyToOne(optional = false)
    @JoinColumn(name = "PROMOCION_OPERACION_ID")
    private PromocionOperacion promocionOperacion;

    public PromocionUsuario() {
    }

    public long getPromocionUsuarioId() {
        return this.promocionUsuarioId;
    }

    public void setPromocionUsuarioId(long promocionUsuarioId) {
        this.promocionUsuarioId = promocionUsuarioId;
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
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

    public String getIdentificacion() {
        return this.identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
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

    public PromocionOperacion getPromocionOperacion() {
        return this.promocionOperacion;
    }

    public void setPromocionOperacion(PromocionOperacion promocionOperacion) {
        this.promocionOperacion = promocionOperacion;
    }

}