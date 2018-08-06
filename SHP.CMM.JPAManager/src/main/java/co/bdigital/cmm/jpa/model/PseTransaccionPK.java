package co.bdigital.cmm.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the PSE_TRANSACCION database table.
 * 
 */
@Embeddable
public class PseTransaccionPK implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "TICKET_ID", insertable = false, updatable = false)
    private String ticketId;

    @Column(name = "TT_ID", insertable = false, updatable = false)
    private String ttId;

    @Column(name = "TRAZABILITY_CODE", insertable = false, updatable = false)
    private String trazabilityCode;

    public PseTransaccionPK() {
    }

    /**
     * @return the ticketId
     */
    public String getTicketId() {
        return ticketId;
    }

    /**
     * @param ticketId
     *            the ticketId to set
     */
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * @return the ttId
     */
    public String getTtId() {
        return ttId;
    }

    /**
     * @param ttId
     *            the ttId to set
     */
    public void setTtId(String ttId) {
        this.ttId = ttId;
    }

    /**
     * @return the trazabilityCode
     */
    public String getTrazabilityCode() {
        return trazabilityCode;
    }

    /**
     * @param trazabilityCode
     *            the trazabilityCode to set
     */
    public void setTrazabilityCode(String trazabilityCode) {
        this.trazabilityCode = trazabilityCode;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PseTransaccionPK)) {
            return false;
        }
        PseTransaccionPK castOther = (PseTransaccionPK) other;
        return (this.ticketId == castOther.ticketId)
                && (this.ttId == castOther.ttId)
                && (this.trazabilityCode == castOther.trazabilityCode);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + ticketId.hashCode();
        hash = hash * prime + ttId.hashCode();
        hash = hash * prime + trazabilityCode.hashCode();

        return hash;
    }
}