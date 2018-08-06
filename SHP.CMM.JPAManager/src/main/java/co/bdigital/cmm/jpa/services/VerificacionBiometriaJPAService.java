/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.VerificacionBiometria;

/**
 * @author eduardo.altamar
 *
 */
public interface VerificacionBiometriaJPAService {

    /**
     * Consulta de registros de la verificacion biometrica
     * 
     * @param cifId
     * @return
     * @throws JPAException
     */
    public List<VerificacionBiometria> getBiometricVerificationList(
            String cifId, EntityManager em) throws JPAException;

}
