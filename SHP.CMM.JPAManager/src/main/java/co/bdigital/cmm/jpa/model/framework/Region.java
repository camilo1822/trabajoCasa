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
 * The persistent class for the REGION database table.
 * 
 */
@Entity
@Table(name = "REGION", schema = "FRAMEWORKSOA")
@NamedQuery(name = "Region.findAll", query = "SELECT r FROM Region r")
public class Region implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "REGION_ID")
    private String regionId;

    private String name;

    private String code;

    // bi-directional many-to-one association to MdwDomain
    @OneToMany(mappedBy = "region")
    private List<MdwDomain> mdwDomains;

    public Region() {
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<MdwDomain> getMdwDomains() {
        return this.mdwDomains;
    }

    public void setMdwDomains(List<MdwDomain> mdwDomains) {
        this.mdwDomains = mdwDomains;
    }

    public MdwDomain addMdwDomain(MdwDomain mdwDomain) {
        getMdwDomains().add(mdwDomain);
        mdwDomain.setRegion(this);

        return mdwDomain;
    }

    public MdwDomain removeMdwDomain(MdwDomain mdwDomain) {
        getMdwDomains().remove(mdwDomain);
        mdwDomain.setRegion(null);

        return mdwDomain;
    }

}