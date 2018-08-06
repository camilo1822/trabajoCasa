/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.VerificacionBiometria;
import co.bdigital.cmm.jpa.services.VerificacionBiometriaJPAService;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public class VerificacionBiometriaJPAServiceIMPL implements
        VerificacionBiometriaJPAService {

    private static final String COMMON_STRING_CIFID = "cifId";
    private static VerificacionBiometriaJPAServiceIMPL instance;

    /**
     * @return VerificacionBiometriaJPAServiceIMPL
     */
    public static VerificacionBiometriaJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new VerificacionBiometriaJPAServiceIMPL();
        return instance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see co.bdigital.cmm.jpa.services.VerificacionBiometriaJPAService#
     * getBiometricVerificationList(java.lang.String,
     * javax.persistence.EntityManager)
     */
    @Override
    public List<VerificacionBiometria> getBiometricVerificationList(
            String cifId, EntityManager em) throws JPAException {

        final String metodo = "getBiometricVerificationList";
        List<VerificacionBiometria> biometricVerificationList;
        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<VerificacionBiometria> criteriaQuery = criteriaBuilder
                    .createQuery(VerificacionBiometria.class);

            Root<VerificacionBiometria> from = criteriaQuery
                    .from(VerificacionBiometria.class);

            criteriaQuery.where(criteriaBuilder.equal(
                    from.get(COMMON_STRING_CIFID), cifId));
            criteriaQuery.select(from);

            TypedQuery<VerificacionBiometria> typedQuery = em
                    .createQuery(criteriaQuery);

            biometricVerificationList = typedQuery.getResultList();

            return biometricVerificationList;

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }

}
