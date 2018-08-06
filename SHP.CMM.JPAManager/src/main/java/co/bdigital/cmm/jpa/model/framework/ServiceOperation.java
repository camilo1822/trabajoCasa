package co.bdigital.cmm.jpa.model.framework;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the SERVICE_OPERATION database table.
 * 
 */
@Entity
@Table(name = "SERVICE_OPERATION", schema = "FRAMEWORKSOA")
@NamedQuery(name = "ServiceOperation.findAll", query = "SELECT s FROM ServiceOperation s")
public class ServiceOperation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_SERVICE_OPERATION")
    private long idServiceOperation;

    private String description;

    @Column(name = "OPERATION_NAME")
    private String operationName;

    @Column(name = "TIMEOUT_OP")
    private BigDecimal timeoutOp;

    // bi-directional many-to-one association to ServiceCatalog
    @ManyToOne
    @JoinColumn(name = "IDSERVICE_CATALOG")
    private ServiceCatalog serviceCatalog;

    public ServiceOperation() {
    }

    public long getIdServiceOperation() {
        return this.idServiceOperation;
    }

    public void setIdServiceOperation(long idServiceOperation) {
        this.idServiceOperation = idServiceOperation;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperationName() {
        return this.operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public BigDecimal getTimeoutOp() {
        return this.timeoutOp;
    }

    public void setTimeoutOp(BigDecimal timeoutOp) {
        this.timeoutOp = timeoutOp;
    }

    public ServiceCatalog getServiceCatalog() {
        return this.serviceCatalog;
    }

    public void setServiceCatalog(ServiceCatalog serviceCatalog) {
        this.serviceCatalog = serviceCatalog;
    }

}