package co.bdigital.cmm.jpa.model.framework;

import java.io.Serializable;

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

/**
 * The persistent class for the MDW_SERVICE database table.
 * 
 */
@Entity
@Table(name = "MDW_SERVICE", schema = "FRAMEWORKSOA")
@NamedQueries({
        @NamedQuery(name = "MdwService.findAll", query = "SELECT m FROM MdwService m"),
        @NamedQuery(name = "MdwService.findByDomainOperationRegion", query = "SELECT m FROM MdwService m WHERE m.mdwDomain.name = :nameDomain AND m.mdwDomain.system = :system AND m.mdwDomain.version = :versionDomain AND m.mdwDomain.region.regionId = :idRegion AND m.operation = :nameOperation AND m.status = :statusBean") })
public class MdwService implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "MDW_SERVICE_SERVICEID_GENERATOR", sequenceName = "MDW_SERVICE_SERVICE_ID_SEQ1")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MDW_SERVICE_SERVICEID_GENERATOR")
    @Column(name = "SERVICE_ID")
    private long serviceId;

    @Column(name = "CREATION_DATE")
    private Object creationDate;

    private String description;

    @Column(name = "MODIFICATION_DATE")
    private Object modificationDate;

    @Column(name = "\"OPERATION\"")
    private String operation;

    @Column(name = "REMOTE_INTERFACE")
    private String remoteInterface;

    @Column(name = "SESION_BEAN")
    private String sesionBean;

    private Boolean status;

    // bi-directional many-to-one association to MdwDomain
    @ManyToOne
    @JoinColumn(name = "DOMAIN_ID")
    private MdwDomain mdwDomain;

    public MdwService() {
    }

    public long getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public Object getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Object creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getModificationDate() {
        return this.modificationDate;
    }

    public void setModificationDate(Object modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getRemoteInterface() {
        return this.remoteInterface;
    }

    public void setRemoteInterface(String remoteInterface) {
        this.remoteInterface = remoteInterface;
    }

    public String getSesionBean() {
        return this.sesionBean;
    }

    public void setSesionBean(String sesionBean) {
        this.sesionBean = sesionBean;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public MdwDomain getMdwDomain() {
        return this.mdwDomain;
    }

    public void setMdwDomain(MdwDomain mdwDomain) {
        this.mdwDomain = mdwDomain;
    }

}