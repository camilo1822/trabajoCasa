package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.EstadoBiometrico;

/**
 * @author john.perez
 *
 */
public interface EstadoBiometricoJPAService {

    /**
     * Consulta de biometrias que tiene el usuario filtrando por clientId.
     * 
     * @param clientId
     * @param em
     * @return List<EstadoBiometrico>
     * @throws JPAException
     */
    public List<EstadoBiometrico> getBiometricStateByClientId(String clientId,
            EntityManager em) throws JPAException;

}
