package co.bdigital.cmm.jpa.model.framework;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the SERVICE_CATALOG database table.
 * 
 */
@Entity
@Table(name = "SERVICE_CATALOG", schema = "FRAMEWORKSOA")
@NamedQuery(name = "ServiceCatalog.findAll", query = "SELECT s FROM ServiceCatalog s")
public class ServiceCatalog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDSERVICE_CATALOG")
    private long idserviceCatalog;

    @Column(name = "CATALOG_NAME")
    private String catalogName;

    private String description;

    // bi-directional many-to-one association to ServiceOperation
    @OneToMany(mappedBy = "serviceCatalog")
    private List<ServiceOperation> serviceOperations;

    // bi-directional many-to-one association to ServiceVersion
    @OneToMany(mappedBy = "serviceCatalog")
    private List<ServiceVersion> serviceVersions;

    public ServiceCatalog() {
    }

    public long getIdserviceCatalog() {
        return this.idserviceCatalog;
    }

    public void setIdserviceCatalog(long idserviceCatalog) {
        this.idserviceCatalog = idserviceCatalog;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ServiceOperation> getServiceOperations() {
        return this.serviceOperations;
    }

    public void setServiceOperations(List<ServiceOperation> serviceOperations) {
        this.serviceOperations = serviceOperations;
    }

    public ServiceOperation addServiceOperation(
            ServiceOperation serviceOperation) {
        getServiceOperations().add(serviceOperation);
        serviceOperation.setServiceCatalog(this);

        return serviceOperation;
    }

    public ServiceOperation removeServiceOperation(
            ServiceOperation serviceOperation) {
        getServiceOperations().remove(serviceOperation);
        serviceOperation.setServiceCatalog(null);

        return serviceOperation;
    }

    public List<ServiceVersion> getServiceVersions() {
        return this.serviceVersions;
    }

    public void setServiceVersions(List<ServiceVersion> serviceVersions) {
        this.serviceVersions = serviceVersions;
    }

    public ServiceVersion addServiceVersion(ServiceVersion serviceVersion) {
        getServiceVersions().add(serviceVersion);
        serviceVersion.setServiceCatalog(this);

        return serviceVersion;
    }

    public ServiceVersion removeServiceVersion(ServiceVersion serviceVersion) {
        getServiceVersions().remove(serviceVersion);
        serviceVersion.setServiceCatalog(null);

        return serviceVersion;
    }

}