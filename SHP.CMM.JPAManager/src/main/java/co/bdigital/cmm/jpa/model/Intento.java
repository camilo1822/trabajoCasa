package co.bdigital.cmm.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the INTENTOS database table.
 * 
 */
@Entity
@Table(name = "INTENTOS", schema = "SHBANCA_DIGITAL")
@NamedQueries({
        @NamedQuery(name = "Intento.findAll", query = "SELECT i FROM Intento i"),
        @NamedQuery(name = "Intento.findByPhoneNumberAndType", query = "SELECT i FROM Intento i WHERE (i.id.celular = :phoneNumber AND i.id.tipoAcceso = :type)") })
public class Intento implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IntentoPK id;

    @Column(name = "FECHA_INTENTO")
    private Timestamp fechaIntento;

    private String intentos;

    public Intento() {
    }

    public IntentoPK getId() {
        return this.id;
    }

    public void setId(IntentoPK id) {
        this.id = id;
    }

    public Timestamp getFechaIntento() {
        return this.fechaIntento;
    }

    public void setFechaIntento(Timestamp fechaIntento) {
        this.fechaIntento = fechaIntento;
    }

    public String getIntentos() {
        return this.intentos;
    }

    public void setIntentos(String intentos) {
        this.intentos = intentos;
    }

}