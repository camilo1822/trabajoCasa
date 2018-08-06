package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the PARAMETRO_ATRIBUTO database table.
 * 
 */
@Entity
@Table(name = "CORREO_ADMIN", schema = "SHBANCA_DIGITAL")
public class CorreoAdmin implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CorreoAdminPK id;

    @Column(name = "ESTADO")
    private Long estado;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;

    // bi-directional many-to-one association to DivisionGeografica
    @ManyToOne
    @JoinColumn(name = "PAIS_ID")
    private DivisionGeografica divisionGeografica;

    public CorreoAdmin() {
    }

    /**
     * @return the id
     */
    public CorreoAdminPK getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(CorreoAdminPK id) {
        this.id = id;
    }

    /**
     * @return the estado
     */
    public Long getEstado() {
        return estado;
    }

    /**
     * @param estado
     *            the estado to set
     */
    public void setEstado(Long estado) {
        this.estado = estado;
    }

    /**
     * @return the fechaCreacion
     */
    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion
     *            the fechaCreacion to set
     */
    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the fechaModificacion
     */
    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * @param fechaModificacion
     *            the fechaModificacion to set
     */
    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    /**
     * @return the usuarioCreacion
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * @param usuarioCreacion
     *            the usuarioCreacion to set
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * @return the usuarioModificacion
     */
    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    /**
     * @param usuarioModificacion
     *            the usuarioModificacion to set
     */
    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    /**
     * @return the divisionGeografica
     */
    public DivisionGeografica getDivisionGeografica() {
        return divisionGeografica;
    }

    /**
     * @param divisionGeografica
     *            the divisionGeografica to set
     */
    public void setDivisionGeografica(DivisionGeografica divisionGeografica) {
        this.divisionGeografica = divisionGeografica;
    }
}