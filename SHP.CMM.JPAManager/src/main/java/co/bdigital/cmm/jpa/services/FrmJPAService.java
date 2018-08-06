package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.framework.MdwService;

/**
 * @author cristian.martinez
 *
 */
public interface FrmJPAService {

    /**
     * Metodo que retorna un Entity <code>MdwService</code>, que cruce con
     * <code>MdwDomain</code> y <code>Region</code>.
     * 
     * @param em
     * @param nameDomain
     * @param nameOperation
     * @param idRegion
     * @param versionDomain
     * @param system
     * @param statusBean
     * @return <code>MdwService</code>
     * @throws JPAException
     */
    public MdwService findSingleMdwServiceByKey(EntityManager em,
            String nameDomain, String nameOperation, String idRegion,
            String versionDomain, String system, Boolean statusBean)
            throws JPAException;

}
