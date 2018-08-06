package co.bdigital.cmm.jpa.model.framework;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the MDW_DOMAIN database table.
 * 
 */
@Entity
@Table(name = "MDW_DOMAIN", schema = "FRAMEWORKSOA")
@NamedQuery(name = "MdwDomain.findAll", query = "SELECT m FROM MdwDomain m")
public class MdwDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "MDW_DOMAIN_DOMAINID_GENERATOR", sequenceName = "MDW_DOMAIN_DOMAIN_ID_SEQ1")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MDW_DOMAIN_DOMAINID_GENERATOR")
    @Column(name = "DOMAIN_ID")
    private long domainId;

    @Column(name = "CONTAINER_BEAN")
    private String containerBean;

    @Column(name = "CREATION_DATE")
    private Object creationDate;

    @Column(name = "MODIFICATION_DATE")
    private Object modificationDate;

    private String name;

    @Column(name = "\"SYSTEM\"")
    private String system;

    @Column(name = "\"VERSION\"")
    private String version;

    // bi-directional many-to-one association to Region
    @ManyToOne
    @JoinColumn(name = "REGION_ID")
    private Region region;

    // bi-directional many-to-one association to MdwService
    @OneToMany(mappedBy = "mdwDomain")
    private List<MdwService> mdwServices;

    public MdwDomain() {
    }

    public long getDomainId() {
        return this.domainId;
    }

    public void setDomainId(long domainId) {
        this.domainId = domainId;
    }

    public String getContainerBean() {
        return this.containerBean;
    }

    public void setContainerBean(String containerBean) {
        this.containerBean = containerBean;
    }

    public Object getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Object creationDate) {
        this.creationDate = creationDate;
    }

    public Object getModificationDate() {
        return this.modificationDate;
    }

    public void setModificationDate(Object modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<MdwService> getMdwServices() {
        return this.mdwServices;
    }

    public void setMdwServices(List<MdwService> mdwServices) {
        this.mdwServices = mdwServices;
    }

    public MdwService addMdwService(MdwService mdwService) {
        getMdwServices().add(mdwService);
        mdwService.setMdwDomain(this);

        return mdwService;
    }

    public MdwService removeMdwService(MdwService mdwService) {
        getMdwServices().remove(mdwService);
        mdwService.setMdwDomain(null);

        return mdwService;
    }

}