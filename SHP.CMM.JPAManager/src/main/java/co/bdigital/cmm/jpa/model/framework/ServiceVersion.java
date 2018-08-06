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
 * The persistent class for the SERVICE_VERSION database table.
 * 
 */
@Entity
@Table(name = "SERVICE_VERSION", schema = "FRAMEWORKSOA")
@NamedQuery(name = "ServiceVersion.findAll", query = "SELECT s FROM ServiceVersion s")
public class ServiceVersion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDSERVICE_VERSION")
    private long idserviceVersion;

    private String description;

    private String endpoint;

    private String namespace;

    @Column(name = "\"TIMEOUT\"")
    private BigDecimal timeout;

    // bi-directional many-to-one association to ServiceCatalog
    @ManyToOne
    @JoinColumn(name = "IDSERVICE_CATALOG")
    private ServiceCatalog serviceCatalog;

    public ServiceVersion() {
    }

    public long getIdserviceVersion() {
        return this.idserviceVersion;
    }

    public void setIdserviceVersion(long idserviceVersion) {
        this.idserviceVersion = idserviceVersion;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public ServiceCatalog getServiceCatalog() {
        return this.serviceCatalog;
    }

    public void setServiceCatalog(ServiceCatalog serviceCatalog) {
        this.serviceCatalog = serviceCatalog;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public BigDecimal getTimeout() {
        return this.timeout;
    }

    public void setTimeout(BigDecimal timeout) {
        this.timeout = timeout;
    }

}