package co.bdigital.cmm.jpa.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.framework.MdwService;
import co.bdigital.cmm.jpa.services.FrmJPAService;

/**
 * @author cristian.martinez
 *
 */
public class FrmJPAServiceIMPL implements FrmJPAService {

    private static final int SIZE_UNO = 1;
    private static final int POS_ZERO = 0;

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
            throws JPAException {

        Query query;
        List<MdwService> listMdwService;
        MdwService mdwService;

        try {

            em.getEntityManagerFactory().getCache().evict(MdwService.class);

            query = em
                    .createNamedQuery("MdwService.findByDomainOperationRegion");

            query.setParameter("nameDomain", nameDomain);
            query.setParameter("system", system);
            query.setParameter("versionDomain", versionDomain);
            query.setParameter("idRegion", idRegion);
            query.setParameter("nameOperation", nameOperation);
            query.setParameter("statusBean", statusBean);

            listMdwService = query.getResultList();

            if (null == listMdwService || listMdwService.isEmpty()
                    || listMdwService.size() > SIZE_UNO) {
                return null;
            }

            mdwService = listMdwService.get(POS_ZERO);

        } catch (NoResultException e) {

            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + this.getClass().getName(), e);

        } catch (Exception e) {

            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + this.getClass().getName(), e);

        }

        return mdwService;
    }
}
